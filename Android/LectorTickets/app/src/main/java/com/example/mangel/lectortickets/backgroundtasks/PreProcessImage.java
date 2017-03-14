package com.example.mangel.lectortickets.backgroundtasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mangel.lectortickets.R;
import com.example.mangel.lectortickets.TicketRecognitionActivity;
import com.example.mangel.lectortickets.utils.ExifUtils;
import com.googlecode.eyesfree.opticflow.ImageBlur;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Convert;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.leptonica.android.WriteFile;

import java.io.File;

/**
 * Project LectorTickets
 * Created by M.Angel on 01/07/2016.
 * AsyncTask para pre-procesar la imagen del ticket. Como resultado, la imagen
 * queda binarizada y lista para el procesado de la misma.
 */
public class PreProcessImage extends AsyncTask <String, Integer, Boolean> {
    private TicketRecognitionActivity activity;
    // dialog para saber el avance en esta AsyncTask
    private ProgressDialog dialog;
    private Context context;

    // imagen a procesar en AsyncTask ProcessImage
    private Bitmap imageToProcess;
    // imagen a preprocesar en esta AsyncTask
    private Pix imageToPreProcess;
    // path de la imagen a pre-procesar
    private String imagePath;
    // vista para la imagen a procesar
    private ImageView ticketView;
    private static final String TAG = "PreProcessImage";

    /**
     * Constructor
     * @param activity
     */
    public PreProcessImage(TicketRecognitionActivity activity) {
        this.activity = activity;
        this.context = activity;
        this.dialog = new ProgressDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
        //Log.i("PreProcessImage","constructor PreProcessImage");
        ticketView = (ImageView) this.activity.findViewById(R.id.imagen_del_ticket);
    }

    @Override
    protected void onPreExecute() {
        //Log.i("PreProcessImage","onPreExecute");
        this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.dialog.setMessage("Preprocesando...");
        // la tarea es cancelable
        this.dialog.setCancelable(true);
        // se añade listener cancelable
        this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // si se cancela el dialog, se cancela la AsyncTask
                PreProcessImage.this.cancel(true);
            }
        });
        this.dialog.setMax(100);
        this.dialog.setProgress(0);
        this.dialog.getWindow().setGravity(Gravity.CENTER);
        // se muestra dialog de progreso
        this.dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // se obtiene path donde esta la imagen del ticket
        imagePath=params[0].toString();
        //Log.i(TAG,"file path "+imagePath);
        // se pre-procesa imagen del ticket
        this.preProcesarImagen();
        return true;
    }

    /**
     * Se actualiza el progreso.
     * @param prog
     */
    @Override
    protected void onProgressUpdate(Integer... prog) {
        int progreso = prog[0].intValue();
        this.dialog.setProgress(progreso);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // importante, se carga imagen preporcesada en la vista
        ticketView.setImageBitmap(imageToProcess);
        if(this.dialog!=null && this.dialog.isShowing()){
            this.dialog.dismiss();
        }
        // ya no nos hace falta y se libera memoria
        if(imageToPreProcess!=null){
            imageToPreProcess.recycle();
        }
        // Se comunica a la activity que se acabo la asynctask y por
        // lo tanto se activan botones de procesar y cancelar imagen de la
        // vista.
        this.activity.PreProcessImageFinished();
    }

    /**
     * Cuando se cancela la tarea, se termina la activity.
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(imageToPreProcess!=null){
            imageToPreProcess.recycle();
        }
        Toast.makeText(activity, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        this.activity.finish();
    }

    /**
     * Metodo que realiza el preprocesado de la imagen.
     * Se le llama desde el doInBackground, al finalizar el hilo se tiene una imagen lista
     * para su procesado. Se obtiene el skew y se le aplica a la imagen para corregir el angulo
     * de inclinacion de las lineas de texto que pueda haber en la imagen. La imagen queda binarizada.
     */
    public void preProcesarImagen(){
        // archivo de imagen
        File f = new File(imagePath);

        // necesario, por algun motivo la imagen que se toma de la cámara queda girada por
        // lo que hay que ponerla con el angulo que se tomo y no con el que se guardo.
        float rotation=ExifUtils.getImageOrientation(f.toString());
        //Log.i(TAG,"orientacion file image "+rotation);
        // imagen en bitmap que se muestra
        Bitmap imageInFile;
        imageInFile = BitmapFactory.decodeFile(f.getPath());
        //Log.i(TAG,"Image file dimensions "+imageInFile.getDensity()+" "+imageInFile.getWidth()+" "+imageInFile.getHeight()+" "+imageInFile.getByteCount());

        // se le da el giro para tenerla con la orientacion con la que se tomo la imagen.
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        imageInFile = Bitmap.createBitmap(imageInFile, 0, 0, imageInFile.getWidth(), imageInFile.getHeight(), matrix, true);

        // leemos la imagen de tipo bitmap (funcion de leptonica) y la pasamos a formato Pix.
        imageToPreProcess = ReadFile.readBitmap(imageInFile);
        //Log.i(TAG, "is blur " + ImageBlur.isBlurred(WriteFile.writeBytes8(imageToPreProcess), imageInFile.getWidth(), imageInFile.getHeight()));
        // la convertimos a 8 bpp
        imageToPreProcess = Convert.convertTo8(imageToPreProcess);
        // se binariza la imagen
        // se usa Binarize.sauvolaBinarizeTiled con todos los parametros por que si no la de por defecto
        // clarea demasiado la imagen
        imageToPreProcess = Binarize.sauvolaBinarizeTiled(imageToPreProcess, 8, 0.12f, 1, 1);
        // como la imagen puede tener una inclinacion de unos grados, se calcula.
        float rotateAngle = Skew.findSkew(imageToPreProcess);
        // se corrige angulo (inclinacion de las lineas de texto)
        imageToPreProcess = Rotate.rotate(imageToPreProcess, rotateAngle);
        // Se carga la imagen a procesar
        imageToProcess = WriteFile.writeBitmap(imageToPreProcess);

        //Log.i(TAG, "imageToProcess densidad: "+imageToProcess.getDensity());
        //Log.i(TAG, "Skew de la imagen "+rotateAngle+" bpp: "+imageToPreProcess.getDepth());
        //Log.i(TAG,"Pix image "+imageToPreProcess.getHeight()+" "+imageToPreProcess.getWidth());
        this.activity.setImageToProcess(imageToProcess);
    }
}
