package zonazulcc;

import handler.DatabaseHandler;
import handler.JSONResponseHandlerCalles;
import handler.JSONResponseHandlerDistancia;
import handler.JSONResponseHandlerPlazas;
import handler.MyTabContenFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.ItemCalle;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.example.zonaazulcc.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import adapter.ItemCalleAdapter;
import adapter.TabsPagerAdapter;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.content.Intent;
/**
 * Implementacion de la clase MainActivity
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class MainActivity extends FragmentActivity implements
		OnTabChangeListener, OnPageChangeListener, OnMarkerClickListener,
		OnInfoWindowClickListener, LocationListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private TabHost mTabHost;

	// Variables interaccion fragments
	private ItemCalle calleActual;
	private ItemCalle calleCercana;
	private boolean alarmaActivada = false;
	private boolean inSettings = false;
	public static String calleMasCercana = "";

	// URL OpenData Plazas Zona Azul
	private final static String URL_OPEN_DATA_PLAZAS = "http://opendata.caceres.es/storage/f/2014-09-22T09%3A51%3A09.904Z/plazas-zona-azul-caceres-json.json";

	// URL OpenData Vias
	private final static String URL_OPEN_DATA_VIAS = "http://opendata.caceres.es/dataset/3841d22f-b6f9-474a-8ace-fce8304bcecd/resource/1a7a2d35-01a0-4e39-9192-749c7be0d05d/download/viascaceresjson.json";

	public static final String TAG = "MainActivity";
	private ProgressBar mProgressBar;

	// Listas Objetos obtenidos desde JSON Open Data
	private List<ItemPlazaZonaAzulJSON> listaPlazas = new LinkedList<ItemPlazaZonaAzulJSON>();
	private List<ItemCalleJSON> listaVias = new LinkedList<ItemCalleJSON>();

	// Lista Objetos Modelo
	private List<ItemCalle> listaCalles = new LinkedList<ItemCalle>();

	// The Map Object
	private GoogleMap mapa;
	private int tipoMapa;

	// Localizaciones
	private LatLng LOCATION_CACERES = new LatLng(39.470777, -6.378121);
	private Location LOCATION_CURRENT;
	// tiempo minimo, por defecto, entre lecturas
	private long mMinTime = 5000;
	// distancia minima, por defecto, entre lectura antigua y una nueva
	private float mMinDistance = 1000.0f;
	private LocationManager mLocationManager;

	// Lista de Markers
	private List<Marker> listaMarkers = new LinkedList<Marker>();
	private Marker lastMarker = null;
	private Marker lastMarkerCalleLista = null;
	private Marker closerMarker = null;

	// Lista Objetos API Matriz de Distancias Google
	private List<ItemDistanciaJSON> listaDistancias = new LinkedList<ItemDistanciaJSON>();

	// Base de datos
	static DatabaseHandler dataBaseHandler;

	// Alarma	
	public static final long TWO_MINS = 2 * 60 * 1000;
	public static final long FIVE_MINS = 5 * 60 * 1000;
	public static final long TEN_MINS = 10 * 60 * 1000;
	public static long adelantoAlarma=FIVE_MINS;
	public static String tono="bells";

	// fragments
	List<Fragment> fragments;
	CallesFragment callesFragment;
	TiqueFragment tiqueFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Base de datos inicializar
		dataBaseHandler = new DatabaseHandler(this);
		// set preferences default valuesSET
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		// Para evitar problemas con la red
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Llamamos al metodo Listener para la posicion actual
		iniciarListener();

		if (null == (mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE))) {
			finish();
		}		

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		// se pediran los datos al opendata de las calles si la base de datos esa vacia
		if (dataBaseHandler.getAllCalles().size() == 0) {
			// Rescatamos Datos en una tarea asincrona
			new HttpGetTaskPlazas().execute(URL_OPEN_DATA_PLAZAS);

			// Rescatamos Datos en una tarea asincrona
			new HttpGetTaskVias().execute(URL_OPEN_DATA_VIAS);
		}

		// Tab Initialization
		initialiseTabHost();
		// View Initialization
		initializeView();

	}

	/**
	 * @post Genera una Lista con los Objetos del Modelo de la aplicacion
	 * @param <b>_listaVias<b> Lista de objetos serializados desde el recurso de
	 *        OpenData "Vias"
	 * @param <b>_listaPlazas<b> Lista de objetos serializados desde el recurso
	 *        de OpenData "Plazas Zona Azul"
	 * @return <b>aux<b> Lista de objetos Modelo generada
	 * @complejidad O(n)
	 */
	private List<ItemCalle> generarDatosModelo(List<ItemCalleJSON> _listaVias,
			List<ItemPlazaZonaAzulJSON> _listaPlazas) {

		// Lista a devolver
		List<ItemCalle> aux = new LinkedList<ItemCalle>();

		// Variables Objeto auxiliares
		ItemCalleJSON viaAux = new ItemCalleJSON();
		ItemPlazaZonaAzulJSON plazaAux = new ItemPlazaZonaAzulJSON();

		// Variables auxiliares: iterador y bandera
		int j;
		boolean primeraCoincidenciaPlaza;

		for (int i = 0; i < _listaVias.size(); i++) {

			viaAux = _listaVias.get(i);
			j = 0;
			primeraCoincidenciaPlaza = true;
			ItemCalle calleModelo = new ItemCalle();

			while (j < _listaPlazas.size()) {

				plazaAux = _listaPlazas.get(j);

				if (viaAux.getUri().equals(plazaAux.getSituadoEnVia())) {

					if (primeraCoincidenciaPlaza) {
						calleModelo.setCoste(plazaAux.getCosteZonaAzul());
						calleModelo.setCosteAnulacion(plazaAux
								.getPrecioAnulacionDenuncia());
						calleModelo.setHorario(plazaAux.getHorarioZonaAzul());
						calleModelo.setLatitud(viaAux.getLatitud());
						calleModelo.setLongitud(viaAux.getLongitud());
						calleModelo.setNombreDeCalle(viaAux.getNombreDeCalle());
						calleModelo.setNumeroDePlazasCalle(calleModelo
								.getNumeroDePlazasCalle() + 1);
						aux.add(calleModelo);
						primeraCoincidenciaPlaza = false;

					} else {
						calleModelo.setNumeroDePlazasCalle(calleModelo
								.getNumeroDePlazasCalle() + 1);
					}

					_listaPlazas.remove(plazaAux);

				} else {
					j++;
				}

			}

		}

		return aux;
	}

	private class HttpGetTaskPlazas extends
			AsyncTask<String, Void, List<ItemPlazaZonaAzulJSON>> {

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		// Actualiza la vista, se ejecuta en el UIThread
		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected List<ItemPlazaZonaAzulJSON> doInBackground(String... params) {

			HttpGet request = new HttpGet(params[0]);
			JSONResponseHandlerPlazas responseHandler = new JSONResponseHandlerPlazas();

			try {

				// Get PlazasZonaAzul data in JSON format
				// Parse data into a list of Plazas

				return mClient.execute(request, responseHandler);

			} catch (ClientProtocolException e) {
				Log.i(TAG, "ClientProtocolException");
			} catch (IOException e) {
				Log.i(TAG, "IOException");
			}

			return null;

		}

		@Override
		protected void onPostExecute(List<ItemPlazaZonaAzulJSON> result) {

			// Recorremos la lista de objetos PLAZAS

			for (ItemPlazaZonaAzulJSON rec : result) {

				getListaPlazas().add(rec);
			}

			Log.i("Total PLAZAS leidas en JSON: ",
					Integer.toString(getListaPlazas().size()));
			if (null != mClient)
				mClient.close();

		}

	}

	private class HttpGetTaskVias extends
			AsyncTask<String, Void, List<ItemCalleJSON>> {

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		// Actualiza la vista, se ejecuta en el UIThread
		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected List<ItemCalleJSON> doInBackground(String... params) {

			HttpGet request = new HttpGet(params[0]);
			JSONResponseHandlerCalles responseHandler = new JSONResponseHandlerCalles();

			try {

				// Get Vias data in JSON format
				// Parse data into a list of Vias

				return mClient.execute(request, responseHandler);

			} catch (ClientProtocolException e) {
				Log.i(TAG, "ClientProtocolException");
			} catch (IOException e) {
				Log.i(TAG, "IOException");
			}

			return null;

		}

		// Actualiza el Mapa, es el metodo apropiado para hacerlo, desde la
		// AsyncTask
		@Override
		protected void onPostExecute(List<ItemCalleJSON> result) {

			// Recorremos la lista de objetos VIAS y anhadimos objetos a la
			// lista

			for (ItemCalleJSON rec : result) {

				getListaVias().add(rec);
			}

			Log.i("Total ViaS leidas en JSON: ",
					Integer.toString(getListaVias().size()));
			if (null != mClient)
				mClient.close();

			setListaCalles(generarDatosModelo(getListaVias(), getListaPlazas()));
			/*
			 * Insertar en la base de datos los datos recogidos del Open Data
			 * coger de la base de datos
			 */
			if (dataBaseHandler.getAllCalles().size() == 0) {
				for (int i = 0; i < listaCalles.size(); i++) {
					MainActivity.dataBaseHandler.addCalle(listaCalles.get(i));
				}
			}
			// carga de datos en base de datos
			callesFragment.setCallesLista(MainActivity.dataBaseHandler
					.getAllCalles());

			/*
			 * Una vez cargada base de datos cargamos la lista en el adaptador
			 * de calles fragment ya que esta listo para mostrarse
			 */
			callesFragment.setItemCalleAdapter(new ItemCalleAdapter(
					MainActivity.this, dataBaseHandler.getAllCalles()));
			ListView listViewCalles = callesFragment.getListViewCalles();
			listViewCalles.setAdapter(callesFragment.getItemCalleAdapter());

			// cargar markers
			setUpMap();
		}
	}

	private class HttpGetTaskDistancia extends
			AsyncTask<String, Void, List<ItemDistanciaJSON>> {

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		// Actualiza la vista, se ejecuta en el UIThread
		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected List<ItemDistanciaJSON> doInBackground(String... params) {

			HttpGet request = new HttpGet(params[0]);
			JSONResponseHandlerDistancia responseHandler = new JSONResponseHandlerDistancia();

			try {

				return mClient.execute(request, responseHandler);

			} catch (ClientProtocolException e) {
				Log.i(TAG, "ClientProtocolException");
			} catch (IOException e) {
				Log.i(TAG, "IOException");
			}

			return null;

		}

		// Calcula el Marker más cercano a nuestra Localizacion
		@Override
		protected void onPostExecute(List<ItemDistanciaJSON> result) {

			int distanciaAux;
			int distanciaMin = 99999999;
			int posicion = 0;
			String distancia = "";
			String tiempo = "";

			// Recorremos la lista de objetos Distancia
			for (ItemDistanciaJSON rec : result) {

				listaDistancias.add(rec);
			}

			if (null != mClient)
				mClient.close();

			for (int i = 0; i < listaDistancias.size(); i++) {

				distanciaAux = listaDistancias.get(i).getDistanceV();

				// Calculamos la mas cercana
				if (distanciaAux < distanciaMin) {
					distanciaMin = distanciaAux;
					posicion = listaDistancias.get(i).getPosicion();
					distancia = listaDistancias.get(i).getDistance();
					tiempo = listaDistancias.get(i).getDuration();
				}
			}

			// Cambiar la camara del mapa hacia la calleCercana y activar su
			// marker
			calleCercana = dataBaseHandler.getAllCalles().get(posicion);
			calleMasCercana = ("Distancia: " + distancia + " - Tiempo: " + tiempo);
			Log.i("", calleMasCercana);

			Marker markerMasCercano = null;
			boolean encontrado = false;
			int indiceEncontrado = 0;
			int i = 0;
			while (i < listaMarkers.size()) {
				if (listaMarkers.get(i).getTitle()
						.equals(calleCercana.getNombreDeCalle())) {
					encontrado = true;
					indiceEncontrado = i;
				}
				listaMarkers
						.get(i)
						.setIcon(
								BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

				i++;
			}
			if (encontrado) {
				markerMasCercano = listaMarkers.get(indiceEncontrado);
				markerMasCercano.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				markerMasCercano.showInfoWindow();
				// Show the shortest location in Google map
				mapa.moveCamera(CameraUpdateFactory.newLatLng(markerMasCercano
						.getPosition()));
				// Zoom in the google map
				CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(
						markerMasCercano.getPosition(), 15);
				mapa.animateCamera(camUpdate);
			}

			// Retroalimentacion Usuario
			Toast.makeText(getApplicationContext(), calleMasCercana,
					Toast.LENGTH_LONG).show();

		}

	}

	// Method to add a TabHost
	private static void AddTab(MainActivity activity, TabHost tabHost,
			TabHost.TabSpec tabSpec) {
		tabSpec.setContent(new MyTabContenFactory(activity));
		tabHost.addTab(tabSpec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// en caso de que se pase de pagina(fragment)
	@Override
	public void onPageSelected(int arg0) {
		this.mTabHost.setCurrentTab(arg0);
	}

	// Manages the Tab changes, synchronizing it with Pages
	@Override
	public void onTabChanged(String tag) {
		int position = this.mTabHost.getCurrentTab();
		this.viewPager.setCurrentItem(position);

	}

	/**
	 * metodo para inicializar la vista de los fragments, por defecto
	 * se mostrara el fragment central que contiene el mapa
	 */
	private void initializeView() {
		List<Fragment> fList = new ArrayList<Fragment>();

		// Respetar orden de insercion en la lista
		MapaFragment mapaFragment = new MapaFragment(MainActivity.this);
		callesFragment = new CallesFragment(MainActivity.this);
		tiqueFragment = new TiqueFragment(MainActivity.this);

		fList.add(callesFragment);
		fList.add(mapaFragment);

		fList.add(tiqueFragment);

		fragments = fList;

		viewPager = (ViewPager) findViewById(R.id.viewpager);

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), fragments);

		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(MainActivity.this);

		// Se indica el Tab Inicial, dentro del array, 1 corresponde al fragment del mapa
		viewPager.setCurrentItem(1);

	}

	/**
	 * metodo para inicializar y crear los tabs
	 */
	private void initialiseTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		// Put here your Tabs, respetar orden de insercion de las tabs
		// El nombre del indicador se coge de values/strings segun el idioma
		MainActivity.AddTab(
				this,
				this.mTabHost,
				this.mTabHost.newTabSpec("Calles").setIndicator(
						getString(R.string.Calles)));
		MainActivity.AddTab(
				this,
				this.mTabHost,
				this.mTabHost.newTabSpec("Mapa").setIndicator(
						getString(R.string.Mapa)));
		MainActivity.AddTab(
				this,
				this.mTabHost,
				this.mTabHost.newTabSpec("Ticket").setIndicator(
						getString(R.string.Ticket)));

		mTabHost.setOnTabChangedListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();
		if (itemId == R.id.action_refresh) {
			onResume();
			return true;
		} else if (itemId == R.id.action_settings) {
			inSettings = true;
			getFragmentManager().beginTransaction()
					.replace(android.R.id.content, new SettingsFragment())
					.addToBackStack(null).commit();

			getFragmentManager().executePendingTransactions();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onBackPressed() {
		if (inSettings) {
			backFromSettingsFragment();
			return;
		}
		super.onBackPressed();
	}

	private void backFromSettingsFragment() {
		inSettings = false;
		getFragmentManager().popBackStack();
	}

	public List<ItemPlazaZonaAzulJSON> getListaPlazas() {
		return listaPlazas;
	}

	public void setListaPlazas(List<ItemPlazaZonaAzulJSON> listaPlazas) {
		this.listaPlazas = listaPlazas;
	}

	public List<ItemCalleJSON> getListaVias() {
		return listaVias;
	}

	public void setListaVias(List<ItemCalleJSON> listaVias) {
		this.listaVias = listaVias;
	}

	public List<ItemCalle> getListaCalles() {
		return listaCalles;
	}

	public void setListaCalles(List<ItemCalle> listaCalles) {
		this.listaCalles = listaCalles;
	}

	/**
	 * metodo para el manejo y configuracion del mapa de google
	 */
	public void setUpMapIfNeeded() {
		if (mapa == null) {
			// Intenta obtener el mapa del SupportMapFragment.
			MapFragment mf = (MapFragment) getFragmentManager()
					.findFragmentById(R.id.map);

			if (mf != null) {
				mapa = mf.getMap();
			}

			if (mapa != null) {
				// Enable MyLocation layer of Google map
				mapa.setMyLocationEnabled(true);

				// Get Location manager object from System service
				// LOCATION_SERVICE
				LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				// Create a criteria object to retrieve provider
				Criteria criteria = new Criteria();

				// Get the name of the best provider
				String provider = locManager.getBestProvider(criteria, true);

				// Get current location
				Location myLocation = locManager.getLastKnownLocation(provider);
				LOCATION_CURRENT = myLocation;
				setUpMap();
			}
		}

	}

	/**
	 * metodo para configuracion del mapa, carga de markers y camara de googlemap
	 */
	public void setUpMap() {
		lastMarkerCalleLista = null;
		CameraUpdate camUpdate = null;
		if (null != mapa) {

			switch (tipoMapa) {

			case 1:
				mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				break;
			case 2:
				mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				break;
			case 4:
				mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				break;
			default:
				mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				break;
			}

			// Para escuchar pulsaciones en los Markers
			mapa.setOnMarkerClickListener(this);
			mapa.setOnInfoWindowClickListener(this);

			List<ItemCalle> streetList = dataBaseHandler.getAllCalles();
			for (int i = 0; i < streetList.size(); i++) {

				if (calleActual != null
						&& streetList.get(i).getNombreDeCalle()
								.equals(calleActual.getNombreDeCalle())) {
					lastMarkerCalleLista = mapa
							.addMarker(new MarkerOptions()
									.position(
											new LatLng(streetList.get(i)
													.getLatitud(), streetList
													.get(i).getLongitud()))
									.title(streetList.get(i).getNombreDeCalle())
									.snippet(
											"Num de Plazas: "
													+ streetList
															.get(i)
															.getNumeroDePlazasCalle())
									.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
					listaMarkers.add(lastMarkerCalleLista);
					lastMarkerCalleLista.showInfoWindow();
				} else {

					// Insertamos marcadores a nuestra lista de markers y al
					// mapa
					listaMarkers
							.add(mapa
									.addMarker(new MarkerOptions()
											.position(
													new LatLng(
															streetList
																	.get(i)
																	.getLatitud(),
															streetList
																	.get(i)
																	.getLongitud()))
											.title(streetList.get(i)
													.getNombreDeCalle())
											.snippet(
													"Num de Plazas: "
															+ streetList
																	.get(i)
																	.getNumeroDePlazasCalle())
											.icon(BitmapDescriptorFactory
													.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
				}

			}

			if (lastMarkerCalleLista != null) {
				mapa.moveCamera(CameraUpdateFactory
						.newLatLng(lastMarkerCalleLista.getPosition()));
				// Zoom in the google map
				camUpdate = CameraUpdateFactory.newLatLngZoom(
						lastMarkerCalleLista.getPosition(), 15);
				lastMarkerCalleLista = null;
			} else {
				// Show the current location in Google map
				mapa.moveCamera(CameraUpdateFactory.newLatLng(LOCATION_CACERES));
				// Zoom in the google map
				camUpdate = CameraUpdateFactory.newLatLngZoom(LOCATION_CACERES,
						15);
			}

			mapa.animateCamera(camUpdate);
		}
	}

	@Override
	protected void onResume() {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Comprobamos si se desean aplicar las preferencias, en caso de no
		// estar definida la propiedad por defecto indicamos false.
		boolean usandoPref = pref.getBoolean(SettingsFragment.KEY_PREF_USER,
				false);

		// Si el usuario desea aplicar las preferencias leemos el resto de
		// preferencias.
		if (usandoPref) {
			String prefMapa = pref.getString(SettingsFragment.KEY_PREF_MAPA,
					SettingsFragment.KEY_PREF_MAPA_DEFAULT);

			// Recogemos la preferencia de tipo de mapa, y asignamos el tipo en
			// función de la preferencia. Si no se hubiera definido la propiedad
			// por defecto aplicariamos MAP_TYPE_NORMAL

			if (prefMapa.equals("MAP_TYPE_NORMAL"))
				setTipoMapa(1);
			else if (prefMapa.equals("MAP_TYPE_SATELLITE"))
				setTipoMapa(2);
			else if (prefMapa.equals("MAP_TYPE_HYBRID"))
				setTipoMapa(4);
			else
				setTipoMapa(1);

			String prefAlarma = pref.getString(
					SettingsFragment.KEY_PREF_ALARMA,
					SettingsFragment.KEY_PREF_ALARMA_DEFAULT);

			if (prefAlarma.equals("2"))
				adelantoAlarma = TWO_MINS;
			else if (prefAlarma.equals("5"))
				adelantoAlarma = FIVE_MINS;
			else if (prefAlarma.equals("10"))
				adelantoAlarma = TEN_MINS;
			else
				adelantoAlarma = FIVE_MINS;

			String prefTono = pref.getString(SettingsFragment.KEY_PREF_TONO,
					SettingsFragment.KEY_PREF_TONO_DEFAULT);

			if (prefTono.equals("bells"))
				tono = "bells";
			else if (prefTono.equals("com_sms"))
				tono = "com_sms";
			else if (prefTono.equals("funny_alarm"))
				tono = "funny_alarm";
			else if (prefTono.equals("xperia_z_themes"))
				tono = "xperia_z_themes";
			else
				tono = "xperia_z_themes";
		}
//
//		Log.i("TIPO DE MAPA: ", String.valueOf(tipoMapa));
//		Log.i("ADELANTO DE ALARMA: ", String.valueOf(adelantoAlarma)
//				+ " milisegundos");
//		Log.i("TONO DE ALARMA: ", tono);

		setUpMap();

		// Check NETWORK_PROVIDER for an existing location reading.
		// Only keep this last reading if it is fresh - less than 5 minutes old.
		LOCATION_CURRENT = mLocationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (LOCATION_CURRENT != null) {
			if (age(mLocationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) > FIVE_MINS) {
				LOCATION_CURRENT = null;
			}
		}

		// register to receive location updates from NETWORK_PROVIDER
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, mMinTime, mMinDistance, this);

		super.onResume();

	}

	@Override
	protected void onPause() {

		// unregister for location updates
		mLocationManager.removeUpdates(this);

		super.onPause();
	}

	public ItemCalle getCalleActual() {
		return calleActual;
	}

	public void setCalleActual(ItemCalle calleActual) {
		this.calleActual = calleActual;
	}

	/**
	 * @pre Actividad creada correctamente
	 * @post Comprueba si la Alarma esta Activada o no
	 * @return <b>True<b> si se cumple la condicion, <b>False<b> en caso
	 *         contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean isAlarmaActivada() {
		return alarmaActivada;
	}

	public void setAlarmaActivada(boolean alarmaActivada) {
		this.alarmaActivada = alarmaActivada;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		if (LOCATION_CURRENT != null && marker != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("https://www.google.es/maps/dir/"
					+ LOCATION_CURRENT.getLatitude() + ","
					+ LOCATION_CURRENT.getLongitude() + "/"
					+ marker.getPosition().latitude + ","
					+ marker.getPosition().longitude));
			startActivity(intent);
		}

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (marker != null && calleActual == null) {
			calleActual = new ItemCalle();
			calleActual.setNombreDeCalle(marker.getTitle());
			calleActual.setLatitud(marker.getPosition().latitude);
			calleActual.setLongitud(marker.getPosition().longitude);
			lastMarker = marker;
			lastMarker.showInfoWindow();
			if (!alarmaActivada) {
				Log.i("ALARMA: ", " Desactivada");
				TiqueFragment.textViewCalle.setText(calleActual
						.getNombreDeCalle());
			} else {
				Log.i("ALARMA: ", " Activada");
				TiqueFragment.textViewCalle.setText(tiqueFragment
						.getCalleDeLaAlarma());
			}
		} else if (marker != null && calleActual != null) {
			calleActual.setNombreDeCalle(marker.getTitle());
			calleActual.setLatitud(marker.getPosition().latitude);
			calleActual.setLongitud(marker.getPosition().longitude);
			lastMarker = marker;
			lastMarker.showInfoWindow();
			if (!alarmaActivada) {
				Log.i("ALARMA: ", " Desactivada");
				TiqueFragment.textViewCalle.setText(calleActual
						.getNombreDeCalle());
			} else {
				Log.i("ALARMA: ", " Activada");
				TiqueFragment.textViewCalle.setText(tiqueFragment
						.getCalleDeLaAlarma());
			}
		}

		return false;
	}

	// Concatena las LatLng de las calles de la BD
	public String obtenerCadenaOrigenes() {

		String res = "";

		List<ItemCalle> streetList = dataBaseHandler.getAllCalles();

		for (int k = 0; k < streetList.size(); k++) {
			res = res + streetList.get(k).getLatitud() + ","
					+ streetList.get(k).getLongitud() + "|";
		}

		res = res.substring(0, res.length() - 1);

		// El PIPE "|" es un caracter ilegal en las peticiones HttpClient
		String fixedUrlStr = res.replace("|", "%7C");

		return fixedUrlStr;
	}

	// Hace peticiones a la API de Distancias de Google entre mi Localizacion y
	// la Lista de Calles (modelo)
	public void calcularMasCercana() {

		if (LOCATION_CURRENT != null) {
			String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="
					+ obtenerCadenaOrigenes()
					+ "&destinations="
					+ LOCATION_CURRENT.getLatitude()
					+ ","
					+ LOCATION_CURRENT.getLongitude()
					+ "&mode=driving&language=es&sensor=false";

			Log.i("URL Consulta Matriz Distancias: ", url);

			new HttpGetTaskDistancia().execute(url);
		}

	}

	public Marker getCloserMarker() {
		return closerMarker;
	}

	public void setCloserMarker(Marker closerMarker) {
		this.closerMarker = closerMarker;
	}

	public static String getCalleMasCercana() {
		return calleMasCercana;
	}

	public static void setCalleMasCercana(String calleMasCercana) {
		MainActivity.calleMasCercana = calleMasCercana;
	}

	public ItemCalle getCalleCercana() {
		return calleCercana;
	}

	public void setCalleCercana(ItemCalle calleCercana) {
		this.calleCercana = calleCercana;
	}

	public int getTipoMapa() {
		return tipoMapa;
	}

	public void setTipoMapa(int tipoMapa) {
		this.tipoMapa = tipoMapa;
	}

	public static long getAdelantoAlarma() {
		return adelantoAlarma;
	}

	public static void setAdelantoAlarma(long adelantoAlarma) {
		MainActivity.adelantoAlarma = adelantoAlarma;
	}

	@Override
	public void onLocationChanged(Location location) {
		// Handle location updates
		// Cases to consider
		// 1) If there is no last location, keep the current location.
		if (LOCATION_CURRENT == null) {
			LOCATION_CURRENT = location;
		}
		// 2) If the current location is older than the last location, ignore
		// the current location
		else if (age(location) > age(LOCATION_CURRENT)) {
			// pass
		}
		// 3) If the current location is newer than the last locations, keep the
		// current location.
		else if (age(location) < age(LOCATION_CURRENT)) {
			LOCATION_CURRENT = location;
		}

	}

	private long age(Location location) {
		return System.currentTimeMillis() - location.getTime();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	private final PhoneStateListener listenerTelefono = new PhoneStateListener() {

		/**
		 * @post Monitoriza el estado de la CONEXION DE LOS DATOS del
		 *       dispositivo
		 * @param <b>estado<b> Entero que indica el estado de la conexion
		 * @complejidad <b>O(1)<b>
		 */
		public void onDataConnectionStateChanged(int estado) {

			String estadoTelefono = "Desconocido";

			switch (estado) {
			// Trafico IP disponible
			case TelephonyManager.DATA_CONNECTED:
				estadoTelefono = "CONECTADO";
				break;
			// La conexion esta actualmente levantandose
			case TelephonyManager.DATA_CONNECTING:
				estadoTelefono = "CONECTANDO";
				break;
			// El trafico IP no esta disponible
			case TelephonyManager.DATA_DISCONNECTED:
				estadoTelefono = "DESCONECTADO";
				break;
			// Hay conexion activada pero el trafico IP no esta disponible
			// temporalmente
			case TelephonyManager.DATA_SUSPENDED:
				estadoTelefono = "SUSPENDIDA";
				break;
			}

			super.onDataConnectionStateChanged(estado);
		}

		/**
		 * @post Monitoriza el estado del SERVICIO del dispositivo
		 * @param <b>estadoServicio<b> Instancia de la clase ServiceState que
		 *        indica el estado del servicio del dispositivo
		 * @complejidad <b>O(1)<b>
		 */
		public void onServiceStateChanged(ServiceState estadoServicio) {

			String estadoTelefono = "Desconocido";

			switch (estadoServicio.getState()) {
			// Solo se permiten llamadas de Emergencia (112)
			case ServiceState.STATE_EMERGENCY_ONLY:
				estadoTelefono = "SOLO LLAMADAS DE EMERGENCIA";
				break;
			// Estado normal
			case ServiceState.STATE_IN_SERVICE:
				estadoTelefono = "EN SERVICIO";
				break;
			// Se encuentra sin senal
			case ServiceState.STATE_OUT_OF_SERVICE:
				estadoTelefono = "FUERA DE SERVICIO";
				break;
			// La radiosenal se encuentra apagada
			case ServiceState.STATE_POWER_OFF:
				estadoTelefono = "APAGADO";
				break;
			}

			super.onServiceStateChanged(estadoServicio);
		}

		/**
		 * @post Monitoriza la INTENSIDAD DE LA SENAL DE TRAFICO de datos en el
		 *       dispositivo
		 * @param <b>signalStrength<b> Instancia de la Clase SignalStrength
		 * @complejidad <b>O(1)<b>
		 */
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {

			int signal = signalStrength.getGsmSignalStrength();
			Log.i("Valor de Intensidad Senal", Integer.toString(signal));

			super.onSignalStrengthsChanged(signalStrength);
		}

	};

	/**
	 * metodo para la inicializacion del escuchador de la posicion actual del usuario
	 */
	private void iniciarListener() {

		// accedemos a los servicios del telefono
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		// Proveedores de Localizacion
		LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		List<String> listaProviders = locManager.getAllProviders();

		LocationProvider provider = locManager.getProvider(listaProviders
				.get(0));

		// Elegimos el mejor proveedor
		Criteria req = new Criteria();
		req.setAccuracy(Criteria.ACCURACY_FINE);
		req.setAltitudeRequired(true);

		// Mejor proveedor por criterio
		String mejorProviderCrit = locManager.getBestProvider(req, false);

		// Lista de proveedores por criterio
		List<String> listaProvidersCrit = locManager.getProviders(req, false);

		// Si el GPS no esta habilitado
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			mostrarAvisoGpsDeshabilitado();
		}

		// Indicamos los eventos que esperamos que el telefono escuche
		int eventos = PhoneStateListener.LISTEN_CELL_LOCATION
				| PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
				| PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
				| PhoneStateListener.LISTEN_SERVICE_STATE;

		// Ponemos el telefono a escuchar
		tm.listen(listenerTelefono, eventos);
	}

	/**
	 * @post Muestra un mensaje al usuario informando que tiene el GPS
	 *       deshabilitado
	 * @complejidad <b>O(1)<b>
	 */
	public void mostrarAvisoGpsDeshabilitado() {
		Context context = getApplicationContext();
		CharSequence text = "GPS OFF";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
