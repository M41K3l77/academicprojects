package com.example.mangel.lectortickets.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Project LectorTickets
 * Created by M.Angel on 13/07/2016.
 * Articulo del ticket del usuario.
 */
public class Articulo {
    // nombre del articulo
    @SerializedName("nombre")
    String nombre;
    // cantidad del articulo
    @SerializedName("cantidad")
    Integer cantidad;
    // precio del articulo
    @SerializedName("precio")
    BigDecimal precio;

    public Articulo(String nombre, Integer cantidad, BigDecimal precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + getPrecio().toString() +
                '}';
    }
}
