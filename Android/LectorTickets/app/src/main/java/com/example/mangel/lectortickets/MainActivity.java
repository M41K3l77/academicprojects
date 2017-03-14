package com.example.mangel.lectortickets;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * activity principal MainActivity
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // string con el path a los archivos *.traineddata que son el lenguaje del reconocedor tess-two
    public static String DATA_PATH;
    // token permiso del usuario para comunicarse con el servidor
    public static String TOKEN="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // se cargan lenguajes para reconocedor tess-two
        this.cargarLenguajes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * metodo para lanzar la ctivity del login
     * @param v
     */
    public void loginAcess(View v) {
        Intent acessLogin = new Intent(this, LoginActivity.class);
        startActivity(acessLogin);
    }

    /**
     * metodo para lanzar la ctivity del registro
     * @param v
     */
    public void signUpAcess(View v){
        Intent acessSignUp = new Intent(this, SignUpActivity.class);
        startActivity(acessSignUp);
    }

    /**
     * Metodo para cargar lenguajes del reconocedor tess-two. Solo usamos uno, spa (español).
     */
    private void cargarLenguajes(){
        // DATA_PATH = Path to the storage
        // lang = for which the language data exists, usually "eng" DATA_PATH
        DATA_PATH = Environment.getExternalStorageDirectory() + "/assets/";
        String[] lang = {"spa"};
        Log.i(TAG, "en cargar lenguajes");
        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};
        // se crea carpeta tessdata
        for (String path : paths) {
            File dir = new File(path);
            Log.i(TAG, "creando tessdata dir");
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }else{
                Log.i(TAG, "dir existe");
            }

        }
        // se añaden lenguajes
        for(int i=0;i < lang.length;i++){
            Log.i(TAG, "añadiendo lenguajes");
            if (!(new File(DATA_PATH + "tessdata/" + lang[i] + ".traineddata")).exists()) {
                //Log.i("traineddata", "traineddata no existe");
                //Log.i(TAG, "añadiendo lenguaje: " + lang[i]);
                try {
                    AssetManager assetManager = getAssets();
                    InputStream in = assetManager.open("tessdata/" + lang[i] + ".traineddata");
                    OutputStream out = new FileOutputStream(DATA_PATH
                            + "tessdata/" + lang[i] + ".traineddata");

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.flush();
                    in.close();
                    out.close();

                    Log.v(TAG, "Copied " + lang[i] + " traineddata");
                    //Log.i(TAG, "añadido lenguaje: " + lang[i]);
                } catch (IOException e) {
                    Log.e(TAG, "Was unable to copy " + lang[i] + " traineddata " + e.toString());
                }
            }
        }
    }
}
