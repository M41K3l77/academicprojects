package com.example.mangel.lectortickets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangel.lectortickets.adapters.ArticulosItemAdapter;
import com.example.mangel.lectortickets.backgroundtasks.PreProcessImage;
import com.example.mangel.lectortickets.backgroundtasks.ProcessImage;
import com.example.mangel.lectortickets.interfaces.ClienteRestService;
import com.example.mangel.lectortickets.jsonobject.CheckedArticulos;
import com.example.mangel.lectortickets.model.Articulo;
import com.example.mangel.lectortickets.jsonobject.ArticuloConsulta;
import com.example.mangel.lectortickets.jsonobject.CheckArticulos;
import com.example.mangel.lectortickets.model.Ticket;

import com.example.mangel.lectortickets.sheets.MercadonaSheet;
import com.example.mangel.lectortickets.viewelements.SelectedView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * activity TicketRecognitionActivity, en esta activity se encarga del reconocimiento del ticket
 * apoyandose asynctasks.
 */
public class TicketRecognitionActivity extends AppCompatActivity {

    private static final String TAG = "TicketRecogActivity";
    // path de la imagen a reconocer
    private String imagePath;
    // imagen a procesar
    private Bitmap imageToProcess;
    // botones de la vista
    private Button procesar_imagen;
    private Button cancelar_imagen;
    private Button enviar_ticket;
    private Button anular_ticket;
    // asynctask para procesado de la imagen
    private ProcessImage procesadoImagen;
    // elementos visuales
    private TextView title_imagen_ticket;
    private SelectedView area_imagen_del_ticket;// esta view solo contiene el area seleccionada por el usuario sobre la imagen
    private ImageView imagen_del_ticket;
    private Rect selectedArea;
    // ticket
    public Ticket ticket;
    // listview donde se muestran articulos reconocidos
    public ListView articulosListView;
    // adaptador de la listview de los articulos
    private ArticulosItemAdapter articulosItemAdapter;
    // texto reconocido
    private ArrayList<String> recognizedText;
    // patrones para los establecimientos
    private final String[][]patronesEstablecimientos={{"([M|N][E][R][C][A][D|O][O|D|U|Ú|0][N][A])", "(CARREFOUR)"}, {"MERCADONA", "CARREFOUR"}};
    // establecimiento reconocido del ticket
    private String establecimientoReconocido;
    // dialog para cuando se esta haciendo el reconocimiento (tess-two esta trabajando)
    private Dialog itemsSugeridosDialog;
    private Dialog establecimientosSugeridosDialog;
    // listas y mapa con los nombres de los articulos recividos por el servidor cuando
    // se consultan los articulos para saber si existen en la base de datos
    private ArrayList<String> arrayArticulosNoReconocidos;
    private ArrayList<String> arrayArticulosReconocidos;
    private Map <String, ArrayList<String>> mapasugerencias;
    // atributo para la posicion listview que se usa en el dialog de sugerencias
    public int positionItemListviewArticulos;
    // pickers y formato de fechas
    private Date timestampPickers;// esta es importante ya que se tiene tanto fecha como hora y sobre ella se va pivotando
    private SimpleDateFormat timeStampFormatter;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat yearFormatter;
    private SimpleDateFormat monthFormatter;
    private SimpleDateFormat dayFormatter;
    private SimpleDateFormat hourFormatter;
    private SimpleDateFormat minuteFormatter;
    // codigos de respuesta cuando se lanzan los pickers de fecha y hora para
    // para modificar fecha y/u hora del ticket
    static final int DATE_PICKER_REQUEST = 1;  // The request code for Date Picker
    static final int TIME_PICKER_REQUEST = 2;  // The request code for Time Picker


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_image);
        this.establecimientosSugeridosDialog=null;
        this.itemsSugeridosDialog=null;
        this.recognizedText=new ArrayList<>(2);
        this.recognizedText.add(new String("zona nombre supermercado"));
        this.recognizedText.add(new String("toda la zona"));
        title_imagen_ticket= (TextView) findViewById(R.id.title_imagen_ticket);
        // boton de procesar hay que esconderlo hasta que se tenga la imagen preprocesada
        procesar_imagen= (Button) findViewById(R.id.procesar_imagen);
        cancelar_imagen= (Button) findViewById(R.id.cancelar_imagen);
        procesar_imagen.setEnabled(false);
        cancelar_imagen.setEnabled(false);

        Bundle args = getIntent().getExtras();
        // recogemos el path de la imagen
        imagePath=args.getString("photopath");
        procesadoImagen=null;

        area_imagen_del_ticket = (SelectedView) findViewById(R.id.selectedView);

        if (null != area_imagen_del_ticket) {
            // imageview que se usa para pintar el area seleccionada de la imagen del ticket
            // Nota: en un imageview tenemos la imagen del ticket y en otro tenemos el rectangulo
            // que representa el area seleccionada.
            area_imagen_del_ticket.setOnUpCallback(new SelectedView.OnUpCallback() {
                // nos da el rectangulo que se pinto en la imageview
                @Override
                public void onRectFinished(final Rect rect) {
                    // rectangulo que sera el area seleccionada
                    Rect mirectangulo= null;
                    if(rect != null){// se selecciono area a reconocer
                        // el rectangulo viene como entero pero lo necesitamos en flotante por que
                        // el metodo getCoordinates(rectF) asi lo requiere.
                        RectF rectF = new RectF(rect);
                        rectF=TicketRecognitionActivity.this.getCoordinates(rectF);
                        mirectangulo= new Rect();
                        // aqui ya tenemos el area seleccionada sobre la imagen procesada
                        // recordar que una cosa son las coordenadas sobre la pantalla que es
                        // lo que se obtiene de onRectFinished(final Rect rect) y otra cosa es
                        // las coordenadas sobre la imagen real y para ello se llama a TicketRecognitionActivity.this.getCoordinates(rectF)
                        // mas arriba.
                        mirectangulo.set((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
                        Toast.makeText(getApplicationContext(), "Area seleccionada (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                                    Toast.LENGTH_LONG).show();
                        // se asigna el area seleccionada.
                        TicketRecognitionActivity.this.selectedArea=mirectangulo;
                        if(TicketRecognitionActivity.this.imagen_del_ticket != null){// guarda necesaria para asegurarse que existe la imagenview
                            TicketRecognitionActivity.this.imagen_del_ticket.setBackgroundColor(ContextCompat.getColor(TicketRecognitionActivity.this, R.color.primary));
                        }
                    }else{// no hay area seleccionada y por lo tanto se reconocera imagen completa
                        if(TicketRecognitionActivity.this.imagen_del_ticket != null){// guarda necesaria para asegurarse que existe la imagenview
                            TicketRecognitionActivity.this.imagen_del_ticket.setBackgroundColor(Color.rgb(255, 255, 255));
                        }
                            Toast.makeText(TicketRecognitionActivity.this, "Se reconocerá imagen completa!",
                                    Toast.LENGTH_SHORT).show();


                    }
                    // si rect es null (no hay area seleccionada) se reconocera la imagen completa.
                    TicketRecognitionActivity.this.selectedArea=mirectangulo;
                }
            });
        }
        // formatos de fechas
        this.timeStampFormatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.timeFormatter = new SimpleDateFormat("HH:mm:ss");
        this.yearFormatter = new SimpleDateFormat("yyyy");
        this.monthFormatter = new SimpleDateFormat("MM");
        this.dayFormatter = new SimpleDateFormat("dd");
        this.hourFormatter = new SimpleDateFormat("HH");
        this.minuteFormatter = new SimpleDateFormat("mm");
        // lanzamos asynctask de preprocesado de imagen
        this.preProcesarImagen();
    }

    /**
     * Metodo que lanza la AsyncTask de preprocesado de la imagen en el onCreate()
     */
    public void preProcesarImagen(){
        // lanzamos asynctask de preprocesado de imagen
        PreProcessImage preProcesadoImagen= new PreProcessImage(this);
        preProcesadoImagen.execute(imagePath);
    }

    /**
     * Metodo que lanza la AsyncTask de procesado de la imagen para obtener la information
     * que contiene. El valor del atributo selectedArea se obtiene del onRectFinished(final Rect rect),
     * si es null se tomara la imagen completa.
     * @param view
     */
    public void procesarImagen(View view) {
        // la vista no cambia pero se esta ejecutando el hilo del procesado, si este se cancela, estos
        // botones deben estar desabilitados
        procesar_imagen.setEnabled(false);
        cancelar_imagen.setEnabled(false);
        procesadoImagen= new ProcessImage(this, this.selectedArea);
        procesadoImagen.execute();

    }

    /**
     * Metodo para realizar la peticion POST al servidor del ticket.
     * @param ticket es el ticket del usuario
     */
    public void restPostTicket(Ticket ticket){
        // URL base del servicio Resful
        String BASE_URL="https://businessintelligence.herokuapp.com/RestfulWebServices/TicketManagement/";
        //Log.i(TAG, BASE_URL);
        // objeto Retrofict para llamar al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // uso de la interfaz
        ClienteRestService clienteRestService = retrofit.create(ClienteRestService.class);
        // llamada al servicio de login
        Call<Object> call = clienteRestService.sendTicket(ticket);
        // callback (es asincrono) para enviar ticket
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //Log.i(TAG, "Se realizo peticion REST");
                // Si se obtiene respuesta correcta
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    //Log.i(TAG, "Peticion atendida correctamente: "+response.code());
                    Toast.makeText(TicketRecognitionActivity.this, "Enviado ticket!",
                            Toast.LENGTH_LONG).show();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    if(response.code() == 403){// En esta situacion se estaba logueado pero ha caducado el token, hay que hacer relogin
                        Intent acessLogin = new Intent(TicketRecognitionActivity.this, LoginActivity.class);
                        startActivity(acessLogin);
                        TicketRecognitionActivity.this.finish();
                    }else{
                        Toast.makeText(TicketRecognitionActivity.this, "problemas con el ticket!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en la peticion "+response.message()+" "+response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(TicketRecognitionActivity.this, "Si no aparece tu ticket en la web vuelve a enviarlo!",
                        Toast.LENGTH_LONG).show();
                //Log.i(TAG, "Fallo peticion REST");
                //t.printStackTrace();
            }
        });
    }

    /**
     * Metodo para enviar al servidor el ticket del usuario. Tickect no puede ser null, tiene
     * que tener al menos un articulo y la fecha del ticket debe ser valida. Una vez enviado el ticket,
     * se vuelve a activity anterior por si el usuario quisiera enviar otro ticket.
     * @param view
     */
    public void enviarTicket(View view){
        if(this.ticket != null && !this.ticket.getListaArticulos().isEmpty() && this.fechaIsValid()){
            this.restPostTicket(this.ticket);
        }else{
            Toast.makeText(TicketRecognitionActivity.this, "No hay articulos para enviar o fecha no valida!",
                    Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }

    /**
     * metodo para desechar la imagen tomada del ticket.
     * @param view
     */
    public void anularImagenTicket(View view){
        Toast.makeText(TicketRecognitionActivity.this, "Imagen descartada!",
                Toast.LENGTH_SHORT).show();
        //Log.i(TAG, "Cancelar imagen desde botton");
        this.finish();
    }

    /**
     * metodo para desechar el ticket reconocido.
     * @param view
     */
    public void anularTicket(View view){
        Toast.makeText(TicketRecognitionActivity.this, "Ticket anulado!",
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Cancelar ticket desde botton");
        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     *
     * @return imageToProcess
     */
    public Bitmap getImageToProcess() {
        return imageToProcess;
    }

    /**
     *
     * @param imageToProcess
     */
    public void setImageToProcess(Bitmap imageToProcess) {
        this.imageToProcess = imageToProcess;
    }

    /**
     * metodo para devolver el rectangulo sobre la imagen real de la imageview.
     * @param selection
     * @return
     */
    private RectF getCoordinates(RectF selection) {
        return transformCoordinates(selection);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getRecognizedText() {
        return recognizedText;
    }

    /**
     * metodo que devuelve el calculo del rectangulo sobre la imagen real a partir del rectangulo
     * obtenido de la imageview de la imagen procesada.
     * @param selection es el area seleccionada sobre la imageview.
     * @return coordinates es el rectangulo que representa el area seleccionada sobre la imagen real.
     */
    private RectF transformCoordinates(RectF selection) {
        imagen_del_ticket = (ImageView) findViewById(R.id.imagen_del_ticket);

        Matrix matrix = new Matrix();
        imagen_del_ticket.getImageMatrix().invert(matrix);
        matrix.postTranslate(imagen_del_ticket.getScrollX(), imagen_del_ticket.getScrollY());
        RectF coordinates = new RectF(selection.left, selection.top, selection.right, selection.bottom);
        matrix.mapRect(coordinates, selection);

        //Log.i(TAG, "getScrollX getScrollY: "+area_imagen_del_ticket.getScrollX()+" "+area_imagen_del_ticket.getScrollY());
        //Log.i(TAG, "ImgVieW alto: "+imagen_del_ticket.getHeight()+" ancho: "+imagen_del_ticket.getWidth());
        //Log.i(TAG, "Area ImgVieW alto: "+area_imagen_del_ticket.getHeight()+" ancho: "+area_imagen_del_ticket.getWidth());
        /*if(imagen_del_ticket != null){
            // estos dos deben dar lo mismo
            Log.i(TAG, "Pic original previa ImgVieW alto: "+imageToProcess.getHeight()+" ancho: "+imageToProcess.getWidth());
            Log.i(TAG, "Pic original inside ImgVieW alto: "+imagen_del_ticket.getDrawable().getIntrinsicHeight()+" ancho: "+imagen_del_ticket.getDrawable().getIntrinsicWidth());
        }*/
        //Log.i(TAG, "ImgVieW measured alto: "+imagen_del_ticket.getMeasuredHeight()+" ancho: "+imagen_del_ticket.getMeasuredWidth());// tamaño imageview
        //Log.i(TAG, "Escala ImgVieW X: "+imagen_del_ticket.getScaleX()+" Y: "+imagen_del_ticket.getScaleY());
        //Log.i(TAG, "coordenadas a transformar: "+selection.toString());
        //Log.i(TAG, "coordenadas transformadas: "+coordinates.toString());

        return coordinates;
    }

    /**
     * Metodo para activar botones al finalizar la AsyncTask del preprocesado onPostExecute(Boolean result).
     * La AsyncTask es cancelable por lo que si los botones estuvieran abilitados ello podria
     * provocar un fallo en la aplicacion.
     */
    public void PreProcessImageFinished() {
        this.procesar_imagen= (Button) this.findViewById(R.id.procesar_imagen);
        this.cancelar_imagen= (Button) this.findViewById(R.id.cancelar_imagen);
        this.procesar_imagen.setEnabled(true);
        this.cancelar_imagen.setEnabled(true);
        Toast.makeText(this, "desliza el dedo sobre la imagen!",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Metodo llamado desde AsyncTask del procesado onPostExecute(Boolean result)
     * para rellenar la listview con los articulo del ticket, en el se activa la segunda vista
     * que contiene la listview y por supuesto se aplica la plantilla del supermercado que corresponda.
     * En un principio el reconocimiento del establecimiento es automatico, pero si no se reconoce el
     * establecimiento, se le da la opcion al usuario de elegir uno en un dialog antes de aplicar la
     * plantilla del establecimiento. Si no se reconocio automaticamente el establecimiento y no se elige
     * manualmente, se anula el reconocimiento del ticket.
     */
    public void ProcessImageFinished() {
        setContentView(R.layout.activity_ticket_text);
        // preparamos listview para los articulos
        this.articulosListView = (ListView) this.findViewById(R.id.articulosListView);

        enviar_ticket = (Button) this.findViewById(R.id.enviar_ticket);
        anular_ticket = (Button) this.findViewById(R.id.anular_ticket);
        enviar_ticket.setEnabled(false);
        anular_ticket.setEnabled(false);
        Toast.makeText(this, "Procesado terminado!",
                Toast.LENGTH_SHORT).show();

        title_imagen_ticket = (TextView) this.findViewById(R.id.title_imagen_ticket);
        title_imagen_ticket.setText("Ticket");

        // seleccion de plantilla de establecimiento
        if(!this.recognizedText.isEmpty()){
            this.establecimientoReconocido="";
            boolean encontrado=false;
            // buscando establecimiento en el texto reconocido
            int i=0;
            while (i<patronesEstablecimientos.length && !encontrado){
                Pattern pEstablecimientos = Pattern.compile(patronesEstablecimientos[0][i]);   // the pattern to search for establecimiento (mercadona, carrefour...)
                int j=0;
                while (j<this.recognizedText.size() && !encontrado){
                    Matcher mEstablecimientos = pEstablecimientos.matcher(this.recognizedText.get(j));
                    if(mEstablecimientos.find()){
                        establecimientoReconocido=patronesEstablecimientos[1][i];
                        Log.i("Establec reconocido: ", establecimientoReconocido);
                        encontrado=true;
                    }else{
                        j++;
                    }
                }
                i++;
            }
            if(!encontrado){
                // si no se reconoce el establecimiento en el supuesto ticket, se da la opción de hacerlo manualmente.
                this.seleccionManualEstablecimiento();
                Toast.makeText(this, "Establecimiento no reconocido!",
                        Toast.LENGTH_SHORT).show();
            }else{
                // se encontro establecimiento y se aplica plantilla
                this.aplicarPlantillaEstablecimiento();
                // se crea el ticket del usuario en la vista
                this.crearTicketDelUsuario();
            }
        }else{// no hay texto a reconocer y se vuelve a activity anterior
            //Log.i(TAG, "No hay texto reconocido, tamaño de la lista: "+this.recognizedText.isEmpty()+" "+this.recognizedText.toString());
            Toast.makeText(this, "No hay texto en la imagen!",
                    Toast.LENGTH_SHORT).show();
            this.finish();
        }
        //

        // texto reconocido
        Log.v(TAG, "texto reconocido: "+this.recognizedText);
        // una vez reconocido el texto se habilita el boton de anular ticket por si el usuario
        // quisiera anularlo
        anular_ticket.setEnabled(true);
    }

    /**
     * Metodo para consultar los articulo y recibir sugerencias de los que no coincidan en la DB del
     * servidor.
     */
    private void checkArticulosInServer() {
        // mapa con las sugerencias para cada articulo que no existe exactamente igual en la DB servidor
        this.mapasugerencias=new ArrayMap<String, ArrayList<String>>();
        // articulos no reconocidos
        this.arrayArticulosNoReconocidos=new ArrayList<String>();
        // articulos reconocidos
        this.arrayArticulosReconocidos= new ArrayList<String>();
        // lista con los nombres de los articulos a consultar en la DB del servidor para saber
        // si existen en la misma
        ArrayList<ArticuloConsulta> articulos=new ArrayList<ArticuloConsulta>();
        //Log.i(TAG, "tamaño lista articulos en el objeto ticket: "+this.ticket.getListaArticulos().size());
        for(int i=0;i<this.ticket.getListaArticulos().size();i++){
            articulos.add(new ArticuloConsulta(this.ticket.getListaArticulos().get(i).getNombre()));
            //Log.i(TAG, "articulo a consultar: "+articulos.get(i));
        }
        // objeto que se enviara a traves de Retrofit para consultar los articulos
        CheckArticulos articulosConsultar=new CheckArticulos(this.ticket.getEstablecimiento(),articulos, MainActivity.TOKEN);
        ////////////////////
        /*Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().create();
        Log.i(TAG, "peticion json: "+gson.toJson(articulosConsultar));*/
        ///////////////////////////
        // URL base del servicio Resful
        String BASE_URL="https://businessintelligence.herokuapp.com/RestfulWebServices/TicketManagement/";
        //Log.i(TAG, BASE_URL);
        // objeto Retrofict para llamar al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // uso de la interfaz
        ClienteRestService clienteRestService = retrofit.create(ClienteRestService.class);
        // llamada al servicio de checkArticulos
        Call<CheckedArticulos> call = clienteRestService.checkArticulos(articulosConsultar);
        // callback (es asincrono) para recivir las sugerencias
        call.enqueue(new Callback<CheckedArticulos>() {
            @Override
            public void onResponse(Call<CheckedArticulos> call, Response<CheckedArticulos> response) {
                Log.i(TAG, "Se realizo peticion REST");
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    // recogemos respuesta que contiene la lista de sugerencias
                    CheckedArticulos sugerencias=response.body();
                    //Log.i(TAG, "Sugerencias: "+sugerencias.toString());
                    for(int i=0;i<sugerencias.getCheckeditemlist().size();i++){
                        if(sugerencias.getCheckeditemlist().get(i).isExist()){// si existe en la base de datos va a lista verde
                            arrayArticulosReconocidos.add(sugerencias.getCheckeditemlist().get(i).getCheckeditem());
                        }else{// si no, los que van al mapa van a naranja y los que quedan fuera a rojo (no hay sugerencias para ellos)
                            if(sugerencias.getCheckeditemlist().get(i).getSuggesteditem().isEmpty()){// si sugerencia vacia va a lista roja
                                TicketRecognitionActivity.this.arrayArticulosNoReconocidos.add(sugerencias.getCheckeditemlist().get(i).getCheckeditem());

                            }else{// hay sugerencia va al mapa
                                if(mapasugerencias.containsKey(sugerencias.getCheckeditemlist().get(i).getCheckeditem())){// si existe el articulo con la clave
                                    // se añade sugerencia y solo si no esta repetida
                                    if(!mapasugerencias.get(sugerencias.getCheckeditemlist().get(i).getCheckeditem()).contains(sugerencias.getCheckeditemlist().get(i).getSuggesteditem())){
                                        mapasugerencias.get(sugerencias.getCheckeditemlist().get(i).getCheckeditem()).add(sugerencias.getCheckeditemlist().get(i).getSuggesteditem());
                                    }
                                }else{//si el articulo consultado no existe como clave, se crea la entrada en el mapa
                                    // pero solo si hay sugerencias para el mismo
                                    if(!sugerencias.getCheckeditemlist().get(i).isExist()){
                                        ArrayList <String> articulosSugeridos= new ArrayList<String>();
                                        articulosSugeridos.add(sugerencias.getCheckeditemlist().get(i).getSuggesteditem());
                                        mapasugerencias.put(sugerencias.getCheckeditemlist().get(i).getCheckeditem(), articulosSugeridos);
                                    }
                                }
                            }
                        }
                    }
                    // Se actualiza la vista de la listview
                    // se usa .invalidateViews() por que no pone o quita ningun elemento si no que
                    // cambia solo los colores de texto de los elementos.
                    TicketRecognitionActivity.this.articulosListView.invalidateViews();
                    //Log.i(TAG, "mapa sugerencias: "+mapasugerencias.toString());
                } else {
                    //Log.i(TAG, "error sugerencias: "+response.code());
                    Toast.makeText(TicketRecognitionActivity.this, "no se han recibido sugerencias!",
                            Toast.LENGTH_SHORT).show();
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    if(response.code() == 403){// En esta situacion se estaba logueado pero ha caducado el token, hay que hacer relogin
                        Intent acessLogin = new Intent(TicketRecognitionActivity.this, LoginActivity.class);
                        startActivity(acessLogin);
                        TicketRecognitionActivity.this.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckedArticulos> call, Throwable t) {
                Toast.makeText(TicketRecognitionActivity.this, "comprueba tu conexion a internet!",
                        Toast.LENGTH_SHORT).show();
                //Log.i(TAG, "Fallo peticion REST sugerencias");
                //t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Se comprueba si se vuelve de la activity de la fecha o de la hora
        if (requestCode == DATE_PICKER_REQUEST) {
            // se ha modificado la fecha en el selector de fecha
            if(resultCode == Activity.RESULT_OK){
                // se obtiene la fecha devuelta
                String resultDate=data.getStringExtra("resultDate");
                //Log.i(TAG,"resultDate: "+resultDate);
                // se muestra en la vista
                TextView dateView= (TextView) findViewById(R.id.dateView);
                dateView.setText(resultDate);
                // se actualiza en el ticket
                this.ticket.setFecha(resultDate+'T'+this.timeFormatter.format(this.timestampPickers));
                try {
                    // se actualiza fecha:hora completa
                    this.timestampPickers= this.timeStampFormatter.parse(this.ticket.getFecha());
                } catch (ParseException e) {// si hay problemas en el parseo
                    this.timestampPickers=new Date();
                }
                //Log.i(TAG,"date cambiada: "+this.ticket.getFecha());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else if(requestCode == TIME_PICKER_REQUEST){
            // se ha modificado la hora en el selector de horas y minutos
            if(resultCode == Activity.RESULT_OK){
                // se obtiene la hora (y minutos) devuelta
                String resultTime=data.getStringExtra("resultTime");
                // se muestra en la vista
                TextView timeView= (TextView) findViewById(R.id.timeView);
                timeView.setText(resultTime);
                // se actualiza en el ticket
                this.ticket.setFecha(this.dateFormatter.format(this.timestampPickers)+'T'+resultTime);
                try {
                    // se actualiza fecha:hora completa
                    this.timestampPickers= this.timeStampFormatter.parse(this.ticket.getFecha());
                } catch (ParseException e) {// si hay problemas en el parseo
                    this.timestampPickers=new Date();
                }
                //Log.i(TAG,"time cambiado: "+this.ticket.getFecha());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    /**
     * metodo para lanzar la activity del selector de fechas para cambiar la fecha
     * desde la vista (pulsando sobre la fecha).
     * @param v
     */
    public void changeDate (View v){
        if(this.timestampPickers != null){
            // al selector se le pasa año, mes y dia
            Intent datePickerIntent = new Intent(this,DatePickerActivity.class);
            int dateYearDefault=Integer.parseInt(this.yearFormatter.format(this.timestampPickers));
            datePickerIntent.putExtra("dateYearDefault", dateYearDefault);
            int dateMonthDefault=Integer.parseInt(this.monthFormatter.format(this.timestampPickers));
            //Log.i(TAG,"mesparse: "+this.monthFormatter.format(this.timestampPickers)+" entero: "+dateMonthDefault);
            // los meses empiezan en 0
            datePickerIntent.putExtra("dateMonthDefault", dateMonthDefault-1);
            int dateDayDefault=Integer.parseInt(this.dayFormatter.format(this.timestampPickers));
            datePickerIntent.putExtra("dateDayDefault", dateDayDefault);
            startActivityForResult(datePickerIntent, DATE_PICKER_REQUEST);
        }
    }

    /**
     * metodo para lanzar la activity del selector de horas (y minutos) para cambiar la hora
     * desde la vista (pulsando sobre la hora).
     * @param v
     */
    public void changeTime (View v){
        if(this.timestampPickers != null){
            Intent timePickerIntent = new Intent(this,TimePickerActivity.class);
            int timeHourDefault=Integer.parseInt(this.hourFormatter.format(this.timestampPickers));
            timePickerIntent.putExtra("timeHourDefault", timeHourDefault);
            int timeMinuteDefault=Integer.parseInt(this.minuteFormatter.format(this.timestampPickers));
            timePickerIntent.putExtra("timeMinuteDefault", timeMinuteDefault);
            startActivityForResult(timePickerIntent, TIME_PICKER_REQUEST);
        }
    }

    /**
     * Metodo para calcular valor total del ticket y mostrarselo al usuario. Es el total de todos
     * los articulos indistintamente si son articulos reconocidos o no, o tienen sugerencias.
     * Es publico por que se usa tambien en ArticulosItemAdapter ya que alli se puede cambiar cantidades
     * y precios de los articulos.
     */
    public void calcularTotal(){
        // Actualizar resultados y listview
        BigDecimal valorTicket= new BigDecimal("0.00");
        valorTicket=valorTicket.setScale(2, RoundingMode.DOWN);
        for(int j=0;j<this.ticket.getListaArticulos().size();j++){
            valorTicket=valorTicket.add(this.ticket.getListaArticulos().get(j).getPrecio().multiply(new BigDecimal(this.ticket.getListaArticulos().get(j).getCantidad())));
        }
        TextView valorDelTicket= (TextView) TicketRecognitionActivity.this.findViewById(R.id.valor_ticket);
        valorDelTicket.setText("Total: "+valorTicket.toString());
    }


    /**
     * Metodo para saber si una fecha de un ticket es valida, no es valida si la fecha
     * del ticket es superior a la fecha actual. El ticket no puede ser null y su fecha
     * debe existir.
     * @return
     */
    private boolean fechaIsValid(){
        boolean isValid=false;
        if(this.ticket!=null){
            if(this.ticket.getFecha() != null){
                String nowDate=this.dateFormatter.format(new Date());
                Date dateNow=null;
                try {
                    dateNow=this.dateFormatter.parse(nowDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date fechaDelTicket=null;
                try {
                    fechaDelTicket=this.dateFormatter.parse(this.ticket.getFecha());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(dateNow != null && fechaDelTicket!=null){
                    if(fechaDelTicket.getTime() <= dateNow.getTime()){
                        isValid=true;
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * Método para asignar un supermercado de forma manual en caso de que no se haya reconocido
     * en el ticket. Este metodo se ha implementado debido a las limitaciones del reconocedor
     * tess-two. Solo se usa si no se ha reconocido de forma automatica el establecimiento.
     */
    private void seleccionManualEstablecimiento(){

        this.establecimientosSugeridosDialog = new AlertDialog.Builder(TicketRecognitionActivity.this, android.R.style.Theme_DeviceDefault_Dialog)
                .setTitle("Establecimientos")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel button
                        // si no se desea elegir establecimiento se vuelve a activity anterior
                        TicketRecognitionActivity.this.finish();
                    }
                })
                .setItems(TicketRecognitionActivity.this.patronesEstablecimientos[1], new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position) {
                        ListView lw = ((AlertDialog)establecimientosSugeridosDialog).getListView();
                        lw.getAdapter().getItem(position).toString(); // seleccion
                        // lo pasamos a la variable del ticket
                        TicketRecognitionActivity.this.establecimientoReconocido=lw.getAdapter().getItem(position).toString();
                        // se aplica plantilla del establecimiento
                        TicketRecognitionActivity.this.aplicarPlantillaEstablecimiento();
                        // se crea el ticket del usuario en la vista
                        TicketRecognitionActivity.this.crearTicketDelUsuario();
                    }
                })
                .create();
        // se muestra dialog con los establecimientos a elegir (los establecimientos a elegir se tienen en patronesEstablecimientos)
        this.establecimientosSugeridosDialog.show();

        //return establecimientoReconocido;
    }

    /**
     * Metodo para aplicar plantilla del establecimiento reconocido.
     */
    private void aplicarPlantillaEstablecimiento(){
        switch (establecimientoReconocido){
            case "MERCADONA" :
                // aplicacion de la plantilla
                MercadonaSheet mercadonaSheet= new MercadonaSheet();
                //Se pasa el tickect a TicketRecognitionActivity para que se pueda enviar
                this.ticket=mercadonaSheet.formalizarTicket(this.recognizedText.get(1));
                // aqhi ya se tiene el ticket por lo que se puede hacer la llamada al servicio rest con las concordancias
                // en caso de no usar sqlite
                if(this.ticket.getListaArticulos().size()==0){
                    Toast.makeText(this, "No hay articulos en el ticket!",
                            Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                if(!fechaIsValid()){
                    Toast.makeText(this, "Revisa la fecha del ticket",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Plantilla Establecimiento No Existe!",
                        Toast.LENGTH_SHORT).show();
                this.finish();
                break;
        }
    }

    /**
     * Metodo en el que se crea el ticket del usuario. setOnItemClickListener para articulosItemAdapter
     * en el que si un articulo tiene sugerencias se puede cambiar a una sugerido. Dichas sugerencias
     * se muestran en un dialog.
     */
    private void crearTicketDelUsuario(){
        if(this.ticket != null){
            // consultar articulos en servidor para ver si existen, es asincrono (Retrofit)
            this.checkArticulosInServer();
            Log.v(TAG, "mi ticket: "+this.ticket.toString());
            // aqui habria que rellenar la listview con los articulos
            // Sets the data behind this ListView
            this.articulosItemAdapter =new ArticulosItemAdapter(this, this.ticket.getListaArticulos(), this.arrayArticulosNoReconocidos, this.mapasugerencias, this.arrayArticulosReconocidos);
            this.articulosListView.setAdapter(this.articulosItemAdapter);
            // listener onclick item
            this.articulosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View View, int position, long id) {
                    positionItemListviewArticulos=position;
                    Log.i(TAG, "pulsado item list");
                    //operacion con el item seleccionado
                    ArrayList<String> arraysugerencias=mapasugerencias.get(((Articulo) articulosListView.getAdapter().getItem(position)).getNombre());
                    if(arraysugerencias != null && arraysugerencias.size()>0){
                        //Log.i(TAG, "Sugerencias para item el dialog: "+arraysugerencias.toString());
                        // sugerencias para el articulo
                        String[] itemsSugeridos= arraysugerencias.toArray(new String[0]);
                        // las sugerencias se muestran en un dialog
                        TicketRecognitionActivity.this.itemsSugeridosDialog = new AlertDialog.Builder(TicketRecognitionActivity.this, android.R.style.Theme_DeviceDefault_Dialog)
                                .setTitle("Sugerencias")
                                .setNegativeButton("Cancel", null)
                                .setItems(itemsSugeridos, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dlg, int position) {
                                        // listview del dialog donde se presentan las sugerencias// this.ticket.getListaArticulos() positionItemListviewArticulos
                                        ListView lw = ((AlertDialog)itemsSugeridosDialog).getListView();
                                        //Log.i(TAG, "posicion de la sugerencia: "+position+" "+lw.getAdapter().getItem(position).toString());
                                        //buscar el sugerido en la lista de articulos ya reconocidos, si esta, se suma 1 a la cantidad y se borra el reconocido
                                        if(TicketRecognitionActivity.this.arrayArticulosReconocidos.contains(lw.getAdapter().getItem(position).toString())){
                                            //Articulo articulo=TicketRecognitionActivity.this.ticket.getListaArticulos().get(TicketRecognitionActivity.this.positionItemListviewArticulos);
                                            // buscamos el sugerido en la lista de articulos para sumar la cantidad +1
                                            int i=0;
                                            boolean encontrado=false;
                                            while(i<TicketRecognitionActivity.this.ticket.getListaArticulos().size() && !encontrado){
                                                if(TicketRecognitionActivity.this.ticket.getListaArticulos().get(i).getNombre().equals(lw.getAdapter().getItem(position).toString())){
                                                    // se suman las cantidades del articulo que ya estaba enla lista con el sugerido que coincide
                                                    TicketRecognitionActivity.this.ticket.getListaArticulos().get(i).setCantidad(TicketRecognitionActivity.this.ticket.getListaArticulos().get(i).getCantidad()+TicketRecognitionActivity.this.ticket.getListaArticulos().get(positionItemListviewArticulos).getCantidad());
                                                    encontrado=true;
                                                }else{
                                                    i++;
                                                }
                                            }
                                            // lo borramos, ya que pasa a ser articulo reconocido
                                            TicketRecognitionActivity.this.ticket.getListaArticulos().remove(TicketRecognitionActivity.this.positionItemListviewArticulos);
                                        }else{
                                            // En la lista de articulos se le pone el nombre sugerido
                                            TicketRecognitionActivity.this.ticket.getListaArticulos().get(TicketRecognitionActivity.this.positionItemListviewArticulos).setNombre(lw.getAdapter().getItem(position).toString());
                                            // se añade a lista de articulos reconocidos
                                            TicketRecognitionActivity.this.arrayArticulosReconocidos.add(lw.getAdapter().getItem(position).toString());
                                        }
                                        // Actualizar resultados y listview
                                        TicketRecognitionActivity.this.calcularTotal();
                                        // se notifica el cambio al adaptador
                                        TicketRecognitionActivity.this.articulosItemAdapter.notifyDataSetChanged();
                                    }
                                })
                                .create();
                        // se muestra dialog de articulos sugeridos
                        TicketRecognitionActivity.this.itemsSugeridosDialog.show();
                    }
                }
            });
            // se pone total en la vista del ticket
            this.calcularTotal();
            TextView establecimiento = (TextView) this.findViewById(R.id.title_imagen_ticket);
            // poner nombre del establecimiento en la vista
            establecimiento.setText(this.ticket.getEstablecimiento());
            // se activa boton de enviar ticket, el ticket se puede enviar aunque no se haya hecho
            // caso de las sugerencias (los articulo que en el servidor no coincidan, no se guardaran en DB).
            enviar_ticket.setEnabled(true);
            // date and time pickers
            TextView dateView= (TextView) findViewById(R.id.dateView);
            TextView timeView= (TextView) findViewById(R.id.timeView);
            if(this.ticket.getFecha()!=null){// fecha reconocida
                this.timestampPickers=null;
                try {
                    this.timestampPickers= this.timeStampFormatter.parse(this.ticket.getFecha());
                } catch (ParseException e) {// si hay problemas en el parseo
                    this.timestampPickers=new Date();
                }
                // se carga en la vista fecha y hora del ticket
                String dateAsString = this.dateFormatter.format(this.timestampPickers);
                String timeAsString = this.timeFormatter.format(this.timestampPickers);
                dateView.setText(dateAsString.trim());
                timeView.setText(timeAsString.trim());
            }else{// fecha no reconocida, se pone una por defecto
                this.timestampPickers=new Date();
                String dateAsString = this.dateFormatter.format(this.timestampPickers);
                String timeAsString = this.timeFormatter.format(this.timestampPickers);
                dateView.setText(dateAsString.trim());
                timeView.setText(timeAsString.trim());
                // se guarda en el ticket
                this.ticket.setFecha(this.timeStampFormatter.format(this.timestampPickers));
            }
            //
        }else{
            Log.i(TAG, "No hay ticket para rellenar la listview");
            // este caso no deberia darse nunca por que la plantilla devuelve un ticket instanciado
            // pero con android nunca se sabe ;)
            this.finish();
        }
    }
}
