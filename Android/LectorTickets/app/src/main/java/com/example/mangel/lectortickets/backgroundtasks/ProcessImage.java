package com.example.mangel.lectortickets.backgroundtasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mangel.lectortickets.MainActivity;
import com.example.mangel.lectortickets.TicketRecognitionActivity;
import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Project LectorTickets
 * Created by M.Angel on 01/07/2016.
 * Se procesa la imagen y por lo tanto se obtiene el texto en crudo de la imagen.
 * Debido a las limitaciones de la libreria tess-two (reconocedor) el texto hay que obtenerlo de dos veces.
 * El texto donde se supone que esta el nombre del establecimiento y el area completa con establecimiento
 * y articulos. Por algun motivo la fuente de la palabra del establecimiento da problemas y devuelve dos
 * caracteres por cada uno excepto si se selecciona un area pequeña que lo contenga.
 */
public class ProcessImage extends AsyncTask <Void, Integer, Boolean> {

    private TicketRecognitionActivity activity;
    // dialog para saber el avance en esta AsyncTask
    private ProgressDialog dialog;
    private Context context;
    // imagen a procesar por tess-two
    private Bitmap imageToProcess;
    // instancia de la libreria tess-two
    private TessBaseAPI baseApi;
    // rectangulo del area seleccionada de la imagen (en caso de que exista)
    private Rect selectedArea;
    private static final String TAG = "ProcessImage";
    // atributo para saber si se estan reconociendo articulos
    // en realidad es para saber que se esta reconociendo la segunda parte de la imagen ya
    // que en esta AsyncTask lo que se reconoce es texto crudo.
    private boolean isRecognizingItems;

    /**
     * Constructor
     * @param activity
     */
    public ProcessImage(TicketRecognitionActivity activity, Rect selectedArea) {
        this.activity = activity;
        this.context = activity;
        this.selectedArea=selectedArea;
        this.dialog = new ProgressDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
        //Log.i("PreProcessImage","constructor PreProcessImage");
        this.isRecognizingItems=false;
        // se obtiene imagen a procesar
        this.imageToProcess=this.activity.getImageToProcess();

    }

    @Override
    protected void onPreExecute() {
        //Log.i("ProcessImage","onPreExecute");
        this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.dialog.setMessage("Procesando...");
        // la tarea sera cancelable pero se pone a true en procesarImagen()
        this.dialog.setCancelable(false);
        this.dialog.setMax(100);
        this.dialog.setProgress(0);
        this.dialog.getWindow().setGravity(Gravity.CENTER);
        this.dialog.show();
        // se añade listener cancelable
        this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(ProcessImage.this.baseApi != null){
                    //Log.i(TAG, "Cancelar OCR");
                    // si se cancela la tarea, entonces hay que detener al reconocedor de texto.
                    ProcessImage.this.baseApi.stop();
                }
                // se cancela la tarea.
                ProcessImage.this.cancel(true);
                // se mata la activity que creo la asyntask para volver a sacar una imagen
                // Se podria pensar que esta llamada deberia ponerse en @Override protected void onCancelled()
                // pero por algun motivo ello hace a veces muy lento la cancelacion
                ProcessImage.this.activity.finish();

            }
        });

        // se comprueba que existe la imagen a procesar
        if (imageToProcess != null) {
            // se añade notificador al reconocedor de texto para saber su progreso
            TessBaseAPI.ProgressNotifier ProgressNotifier= new TessBaseAPI.ProgressNotifier() {
                @Override
                public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {
                    //Log.i(TAG, "porcentage procesado: "+progressValues.getPercent());
                    // se pasa el progreso a la AsyncTask
                    ProcessImage.this.publishProgress(progressValues.getPercent());
                }
            };
            // se asigna el notificador de prograso al reconocedor
            this.baseApi = new TessBaseAPI(ProgressNotifier);

        }else{
            Log.i(TAG, "no hay imagen para procesar");
        }
        // se inicia el reconocedor con el archivo del lenguaje (español)
        this.baseApi.init(MainActivity.DATA_PATH, "spa");
        // lista de caracteres reconocibles
        this.baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "aAbBcCdDeEfFgGhHiIjJkKlLmMnNñÑoOpPqQrRsStTuUvVwWxXyYzZ´`',./-0123456789");
        // lista de caracteres que no se reconoceran
        this.baseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "ﬀﬁﬂﬃﬄﬅﬆ");
        // modo del reconocedor (texto en bloque ya que interesa que lo devuelba en lineas)
        this.baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK);
        this.dialog.setMessage("Reconociendo Establecimiento...");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
            // se procesa imagen binarizada del ticket
            procesarImagen();
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... prog) {
        int progreso = prog[0].intValue();
        //Log.i("ProcessImage", "progreso "+progreso+" %");
        if(this.isRecognizingItems){
            //Log.i("ProcessImage", "procesando articulos if boolean: "+this.isRecognizingItems);
            this.dialog.setMessage("Procesando articulos...");
        }/*else{
            Log.i("ProcessImage", "reconociendo establecimiento: "+this.isRecognizingItems);
        }*/
        this.dialog.setProgress(progreso);
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if(this.dialog!=null && this.dialog.isShowing()){
            this.dialog.dismiss();
        }
        // se aplica plantilla de supermercado y se rellena lista de articulos de la listview
        this.activity.ProcessImageFinished();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Toast.makeText(activity, "Procesado de imagen cancelado!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo que se llama desde el doInBackground para reconocer el texto de la imagen.
     * IMPORTANTE: Puede haber o no area de la imagen seleccionada (imagen completa o no),
     * a demas se reconoceran dos rectangulos. El primero sera la zona donde se supone que esta el nombre del
     * establecimiento y el segundo de donde se sacaran los articulos. Este segundo es el area completa
     * para intentar volver a reconocer el nombre del establecimiento. Aqui solo se obtiene texto en crudo
     * por lo que no se sabrá hasta más adelante si se ha reconocido el establecimiento.
     * El tener el reconocimiento del establecimiento por duplicado es por que la librería
     * del reconocedor le cuesta reconocerlo. Reconoce dos caracteres por cada caracter en la
     * imagen (solo pasa para el establecimiento, debe ser por la fuente de la misma).
     */
    public void procesarImagen(){
        if (imageToProcess != null) {
            // se pone la imagen a reconocer
            baseApi.setImage(imageToProcess);
            // dialog de progreso es cancelable
            this.dialog.setCancelable(true);
            // se mira si hay area seleccionada
            if(selectedArea!=null){
                // se pone la imagen
                // rectangulo de la zona donde se supone que se encuentra el establecimiento
                Rect establecimiento= new Rect(0,0,selectedArea.width(), imageToProcess.getHeight()/4);
                // se reconoce zona de establecimiento
                baseApi.setRectangle(establecimiento);
                // obligatorio si se quiere saber el progreso del reconocedor
                String hocrText = baseApi.getHOCRText(0);
                // se añade texto reconocido
                this.activity.getRecognizedText().set(0, baseApi.getUTF8Text());
                // se avisa que se empieza a obtener texto que contiene articulos
                this.isRecognizingItems=true;
                // se reconoce area seleccionada
                baseApi.setRectangle(selectedArea);
                // obligatorio si se quiere saber el progreso del reconocedor
                hocrText = baseApi.getHOCRText(0);
                // se añade texto reconocido
                this.activity.getRecognizedText().set(1, baseApi.getUTF8Text());
                //Log.i(TAG, "area seleccionada en process image: "+selectedArea.toString());
                //Log.i(TAG, "area seleccionada mercadona: "+establecimiento.toString());
            }else{
                // se reconoce imagen completa
                // rectangulo de la zona donde se supone que se encuentra el establecimiento
                Rect establecimiento= new Rect(0,0,imageToProcess.getWidth(), imageToProcess.getHeight()/4);
                // se reconoce zona de establecimiento
                this.baseApi.setRectangle(establecimiento);
                // obligatorio si se quiere saber el progreso del reconocedor
                String hocrText = baseApi.getHOCRText(0);
                // se añade texto reconocido
                this.activity.getRecognizedText().set(0, baseApi.getUTF8Text());
                // se avisa que se empieza a obtener texto que contiene articulos
                this.isRecognizingItems=true;
                // se reconoce zona total
                Rect allImage= new Rect(0,0,imageToProcess.getWidth(), imageToProcess.getHeight());
                baseApi.setRectangle(allImage);
                // obligatorio si se quiere saber el progreso del reconocedor
                hocrText = baseApi.getHOCRText(0);
                // se añade texto reconocido
                this.activity.getRecognizedText().set(1, baseApi.getUTF8Text());
                //Log.i(TAG, "area total en process image: "+imageToProcess.getWidth()+" "+imageToProcess.getHeight());
            }
            baseApi.end();
        }


    }

}
