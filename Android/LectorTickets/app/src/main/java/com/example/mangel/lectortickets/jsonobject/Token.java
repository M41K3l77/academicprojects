package com.example.mangel.lectortickets.jsonobject;

import com.google.gson.annotations.SerializedName;

/**
 * Project LectorTickets
 * Created by M.Angel on 07/08/2016.
 * Se usa en clase CheckArticulos para comprobar en la parte servidora que el
 * usuario esta autorizado a comprobar la lista de articulos.
 */
public class Token {

    // token que identifica al usuario en la parte servidora como usuario autorizado
    // para realizar una operacion
    @SerializedName("token")
    private String token="";

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }
}
