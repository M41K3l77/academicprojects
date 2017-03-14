package com.example.mangel.lectortickets.jsonobject;

import com.example.mangel.lectortickets.jsonobject.ArticuloConsulta;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Project LectorTickets
 * Created by M.Angel on 11/08/2016.
 * Clase que se usa en la llamada Restful (Call<CheckedArticulos> checkArticulos(@Body CheckArticulos articulosConsultar))
 * de Retrofit (que se encargara del parseo a JSON) y que contiene
 * los nombres de los articulos a comprobar si existen en la DB del servidor
 * , a demas contiene el nombre del establecimiento de dichos articulos y el token
 * del usuario para saber si tiene permiso para realizar la operacion.
 */
public class CheckArticulos {

    // establecimiento de los articulos
    @SerializedName("establecimiento")
    private String establecimiento;
    // lista de nombres de los articulos a comprobar en la DB
    @SerializedName("checkitemlist")
    private ArrayList<ArticuloConsulta> checkitemlist;
    // token del usuario para saber si esta logueado
    @SerializedName("token")
    private String token;

    public CheckArticulos() {
    }

    public CheckArticulos(String establecimiento, ArrayList<ArticuloConsulta> checkitemlist, String token) {
        this.establecimiento = establecimiento;
        this.checkitemlist = checkitemlist;
        this.token = token;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public ArrayList<ArticuloConsulta> getCheckitemlist() {
        return checkitemlist;
    }

    public void setCheckitemlist(ArrayList<ArticuloConsulta> checkitemlist) {
        this.checkitemlist = checkitemlist;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CheckArticulos{" +
                "establecimiento='" + establecimiento + '\'' +
                ", checkitemlist=" + checkitemlist +
                ", token='" + token + '\'' +
                '}';
    }
}
