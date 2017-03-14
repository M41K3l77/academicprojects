package com.example.mangel.lectortickets.interfaces;

import com.example.mangel.lectortickets.jsonobject.CheckArticulos;
import com.example.mangel.lectortickets.jsonobject.CheckedArticulos;
import com.example.mangel.lectortickets.jsonobject.SignUp;
import com.example.mangel.lectortickets.model.Ticket;
import com.example.mangel.lectortickets.jsonobject.Token;
import com.example.mangel.lectortickets.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Project LectorTickets
 * Created by M.Angel on 03/07/2016.
 * Interface de Retrofit (obligatoria) para poder realizar las llamadas Rest al servidor.
 */
public interface ClienteRestService {

    @POST("Ticket")
    Call<Object> sendTicket(@Body Ticket ticket);

    @POST("Login")
    Call<Token> login(@Body User user);

    @POST("Logout")
    Call<Object> logout(@Body Token token);

    @POST("checkarticulos")
    Call<CheckedArticulos> checkArticulos(@Body CheckArticulos articulosConsultar);

    @POST("signup")
    Call<Object> signUp(@Body SignUp signup);
}
