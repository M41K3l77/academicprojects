package com.example.mangel.lectortickets.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Project LectorTickets
 * Created by M.Angel on 14/04/2016.
 * Ticket del usuario
 */
public class Ticket {
    // fecha del ticket reconocido
    @SerializedName("fecha")
    String fecha;
    // lista de articulos del ticket reconocido
    @SerializedName("listaArticulos")
    ArrayList <Articulo> listaArticulos;
    // establecimiento del ticket reconocido
    @SerializedName("establecimiento")
    String establecimiento;
    // token del usuario para que el servidor sepa que tiene permiso para enviar el ticket
    @SerializedName("token")
    String token;

    public Ticket(String fechaticket, ArrayList<Articulo> listaArticulos, String establecimiento, String token) {
        this.fecha= fechaticket;
        this.listaArticulos = listaArticulos;
        this.establecimiento=establecimiento;
        this.token=token;
    }

    public ArrayList<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "fecha='" + fecha + '\'' +
                ", listaArticulos=" + listaArticulos +
                ", establecimiento='" + establecimiento + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
