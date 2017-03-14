package com.example.mangel.lectortickets;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mangel.lectortickets.interfaces.ClienteRestService;
import com.example.mangel.lectortickets.jsonobject.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * activity TicketActivity, en esta activity se lanza la activity para sacar foto
 * con la camara del ticket y se puede desloguear de la aplicacion.
 */
public class TicketActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "TicketActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        // para versiones Marshmallow (23 o mayor) el permiso de escribir en external storage
        // se pide en tiempo de ejecución
        if(this.isStoragePermissionGranted()){
            Log.i(TAG, "Se tiene Permiso external storage");
            Toast.makeText(TicketActivity.this, "Se tiene Permiso external storage",
                    Toast.LENGTH_SHORT).show();
        }else{
            Log.i(TAG, "No se tiene Permiso external storage");
            Toast.makeText(TicketActivity.this, "No se tiene Permiso external storage",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permite realizar una captura con la camara del movil
     * @param v
     */
    public void dispatchTakePictureIntent(View v) {
        // se controla si la version sdk es igual o superior a 23 ya que para ella
        // hay que dar permiso explicito.
        if (Build.VERSION.SDK_INT >= 23) {
            // se comprueba si se dio permiso
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // se lanza activity de la camara y que se espera resultado
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }else{
                Toast.makeText(TicketActivity.this, "Se necesita permiso de escritura en tarjeta SD",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // para versiones < 23 el permiso solo se da en el manifest
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // se lanza activity de la camara y que se espera resultado
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_user_settings) {
            // logout
            this.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Cuando se ha realizado la foto se lanza la activity TicketRecognitionActivity.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // CALL THIS METHOD TO GET THE ACTUAL PATH of the last image captured
            Intent ticketIntent=new Intent(getApplicationContext(), TicketRecognitionActivity.class);
            // photopath es el path donde se encuentra la imagen
            ticketIntent.putExtra("photopath",this.getOriginalImagePath());
            startActivity(ticketIntent);
        }
    }

    /**
     * metodo que devuelve path de la ultima imagen tomada por la camara, dicho path se le pasa
     * al ticketIntent (TicketRecognitionActivity) para el reconocimiento de texto en la imagen capturada.
     * @return
     */
    public String getOriginalImagePath() {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        int column_index_data = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();

        return cursor.getString(column_index_data);
    }

    @Override
    public void onBackPressed() {
        // hacer deslogueo
        this.logout();
        //super.onBackPressed();
    }

    /**
     * metodo para desloguear al usuario.
     */
    public void logout(){
        // URL base del servicio Resful
        String BASE_URL="https://businessintelligence.herokuapp.com/RestfulWebServices/AcessManagement/";
        //Log.i(TAG, BASE_URL+"Logout");
        // objeto Retrofict para llamar al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // uso de la interfaz
        ClienteRestService clienteRestService = retrofit.create(ClienteRestService.class);
        Token token=new Token(MainActivity.TOKEN);
        //Log.i("logout de aplic token", token.toString());
        // llamada al servicio de logout
        Call<Object> call = clienteRestService.logout(token);
        // callback (es asincrono)
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //Log.i(TAG, "Se realizo peticion logout REST");
                // Si se obtiene respuesta correcta
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    //Log.i(TAG, "Peticion atendida logout: "+response.code());
                    Toast.makeText(TicketActivity.this, "logout!",
                            Toast.LENGTH_SHORT).show();
                    TicketActivity.this.finish();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    if(response.code()==404){
                        Toast.makeText(TicketActivity.this, "no estabas logueado!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en login userpass "+response.message()+" "+response.code());
                    }else{
                        Toast.makeText(TicketActivity.this, "problemas logout!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en logout "+response.message()+" "+response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                //Log.i(TAG, "Fallo peticion REST");
                //t.printStackTrace();
            }
        });
        TicketActivity.this.finish();
    }

    /**
     * Permisos para external storage versiones 23 o mas de android.
     * implements ActivityCompat.OnRequestPermissionsResultCallback.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    /**
     * Metodo para saber si tenemos permiso de acceso a external storage versiones 23
     * o superior de android.
     * @return
     */
    private  boolean isStoragePermissionGranted() {
        // saber version sdk
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
