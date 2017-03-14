package com.example.mangel.lectortickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mangel.lectortickets.interfaces.ClienteRestService;
import com.example.mangel.lectortickets.jsonobject.SignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * Activity para el registro de un usuario.
 */
public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    // contiene el nombre del usuario a registrar
    private EditText username_sign_up_editText;
    // contiene el email del usuario a registrar
    private EditText email_signup_editText;
    // dialog que se muestra cuando se intenta el registro
    private ProgressDialog dialog;
    // patron para email
    private static final String emailPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // // se crea dialog
        this.dialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
        // formulario con el nombre y email
        username_sign_up_editText=(EditText) findViewById(R.id.username_sign_up_editText);
        email_signup_editText=(EditText) findViewById(R.id.email_signup_editText);
    }

    /**
     * Metodo en el que se recogen los datos para el registro y se hace la llamada a attemptSignUp.
     * @param v
     */
    public void signUp (View v) {

        SignUp signup=new SignUp();
        String username=username_sign_up_editText.getText().toString().trim();
        signup.setUsername(username);
        String email=email_signup_editText.getText().toString().trim().toLowerCase();
        signup.setEmail(email);
        if(username != null && email != null && email.matches(emailPattern)){
            //Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().create();
            //Log.i(TAG, "peticion json signup: "+gson.toJson(signup));
            // se intenta el registro
            this.attemptSignUp(signup);
        }
    }

    /**
     * Metodo registro de un usuario. Se usa el servicio Restful del servidor a traves de la
     * interfaz ClienteRestService y metodo Call<Object> signUp(@Body SignUp signup).
     * @param signup datos que se envian para el registro de un usuario.
     */
    private void attemptSignUp(SignUp signup) {
        this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.dialog.setMessage("sign up...");
        this.dialog.getWindow().setGravity(Gravity.CENTER);
        this.dialog.show();

        // URL base del servicio Resful
        String BASE_URL="https://businessintelligence.herokuapp.com/RestfulWebServices/AcessManagement/";
        //Log.i(TAG, BASE_URL);
        // objeto Retrofict para llamar al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // uso de la interfaz
        ClienteRestService clienteRestService = retrofit.create(ClienteRestService.class);
        // llamada al servicio de registro
        Call<Object> call = clienteRestService.signUp(signup);
        // callback (es asincrono) para el registro
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //Log.i(TAG, "Se realizo peticion login REST");
                // Si se obtiene respuesta correcta
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    // se ha registrado por lo que pasa a activity login
                    Toast.makeText(SignUpActivity.this, "mira tu correo para la contraseña!",
                            Toast.LENGTH_LONG).show();
                    SignUpActivity.this.dialog.dismiss();
                    // se ha logueado por lo que pasar a activity LoginActivity
                    Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(login);
                    SignUpActivity.this.finish();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    // error 406 no aceptable
                    if(response.code()==406){
                        Toast.makeText(SignUpActivity.this, "Error user name or email!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en signup username email "+response.message()+" "+response.code());
                    }else{
                        Toast.makeText(SignUpActivity.this, "problemas signup!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en signup "+response.message()+" "+response.code());
                    }
                    SignUpActivity.this.dialog.dismiss();
                }

            }

            /**
             * puede que al servidor no le haya dado tiempo a enviar la respuesta, lo tenemos
             * asincrono con 10 segundos por defecto, debe esperar unos minutos para ver si el
             * servidor envio de verdad el correo con la contraseña.
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Si en unos minutos no recibes el correo con el password, "+
                        "repite el registro!",
                        Toast.LENGTH_SHORT).show();
                //Log.i(TAG, "Fallo peticion REST");
                //t.printStackTrace();
                SignUpActivity.this.dialog.dismiss();
            }
        });
    }
}
