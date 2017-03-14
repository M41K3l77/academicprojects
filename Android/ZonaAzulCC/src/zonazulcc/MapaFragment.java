package zonazulcc;

import com.example.zonaazulcc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
/**
 * Implementacion de la clase MapaFragment
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class MapaFragment extends Fragment {

	private Button botonInfo;
	private Button botonCerca;
	private LinearLayout info;
	private boolean pressInfo = false;

	MainActivity mainActivity = null;

	/**
	 * constructor parametrizado
	 * @param mainActivity
	 */
	public MapaFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_mapa, container,
				false);

		info = (LinearLayout) rootView.findViewById(R.id.LinearLayoutInfo);
		info.setVisibility(View.INVISIBLE);

		// boton de informacion
		botonInfo = (Button) rootView.findViewById(R.id.informacion);
		// listener para el boton de informacion
		botonInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (botonInfo.isPressed() && !pressInfo) {
					info.setVisibility(View.VISIBLE);
					pressInfo = true;
				} else {
					info.setVisibility(View.INVISIBLE);
					pressInfo = false;
				}

			}
		});

		// boton para la calle mas cercana
		botonCerca = (Button) rootView.findViewById(R.id.cerca);
		// listener para el boton de la calle mas cercana
		botonCerca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainActivity.calcularMasCercana();
			}
		});
		// mostrar la informacion requerida
		mainActivity.setUpMapIfNeeded();
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		mainActivity.setUpMapIfNeeded();
	}

}