package com.example.mangel.lectortickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mangel.lectortickets.interfaces.ClienteRestService;
import com.example.mangel.lectortickets.jsonobject.Token;
import com.example.mangel.lectortickets.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * Activity para el logueo de un usuario.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    // contiene el nombre del usuario a loguear
    private EditText mUsernameView;
    // contiene la password del usuario a loguear
    private EditText mPasswordView;
    // dialog que se muestra cuando se intenta el logueo
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // se crea dialog
        this.dialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
        // formulario con el nombre y password
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        // boton para el logueo
        Button mSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        // listener boton logueo
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "intento de logueo");
                final User user=new User(mUsernameView.getText().toString().trim(), mPasswordView.getText().toString().trim());
                // Intento de logueo con los datos introducidos por el usuario
                LoginActivity.this.attemptLogin(user);
            }
        });
    }

    /**
     * Metodo logueo de un usuario. Se usa el servicio Restful del servidor a traves de la
     * interfaz ClienteRestService y metodo Call<Token> login(@Body User user), se obtendra el token
     * que dara autorizacion al usuario a interaccionar con el servidor.
     * @param user es el usuario que intenta loguearse (con el nombre y password del formulario).
     */
    private void attemptLogin(final User user) {
        this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.dialog.setMessage("logueando...");
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
        // llamada al servicio de login
        Call<Token> call = clienteRestService.login(user);
        // callback (es asincrono) para recivir el token
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                //Log.i(TAG, "Se realizo peticion login REST");
                // Si se obtiene respuesta correcta
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    //Log.i(TAG, "Peticion atendida login: "+response.code());
                    Toast.makeText(LoginActivity.this, "Usuario logueado correctamente!",
                            Toast.LENGTH_SHORT).show();
                    // guardar token y lanzar TicketActivity
                    // importante recoger el string como se hace a continuación para no tener errores de tipos (object/String)
                    Token token=response.body();
                    MainActivity.TOKEN=token.getToken();
                    // se ha logueado por lo que pasar a activity ticket
                    LoginActivity.this.dialog.dismiss();
                    Intent ticketLogin = new Intent(LoginActivity.this, TicketActivity.class);
                    startActivity(ticketLogin);
                    LoginActivity.this.finish();
                } else {// error devuelto por el servidor
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    // 403 prohibido
                    if(response.code()==403){
                        Toast.makeText(LoginActivity.this, "Error name or password!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en login userpass "+response.message()+" "+response.code());
                    }else{// para cualquier otro error
                        Toast.makeText(LoginActivity.this, "problemas con el login!",
                                Toast.LENGTH_SHORT).show();
                        //Log.i(TAG, "Problemas en login "+response.message()+" "+response.code());
                    }
                    LoginActivity.this.dialog.dismiss();
                }

            }

            /**
             * puede que al servidor no le haya dado tiempo a enviar la respuesta, lo tenemos
             * asincrono con 10 segundos por defecto
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Tiempo excedido, volver a intentar!!!",
                        Toast.LENGTH_LONG).show();
                //Log.i(TAG, "Fallo peticion REST");
                //t.printStackTrace();
                LoginActivity.this.dialog.dismiss();
            }
        });
    }

}

