package com.example.mangel.lectortickets.jsonobject;

import com.google.gson.annotations.SerializedName;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * Clase para realizar el registro del usuario a traves de una lla mada Restful (Retrofit)
 * Call<Object> signUp(@Body SignUp signup)
 */
public class SignUp {

    // username del usuario
    @SerializedName("username")
    private String username;
    // email del usuario
    @SerializedName("email")
    private String email;

    public SignUp() {
    }

    public SignUp(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "SignUp [username = "+username+", email = "+email+"]";
    }
}