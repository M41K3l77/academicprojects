package com.example.mangel.lectortickets.jsonobject;

import com.google.gson.annotations.SerializedName;

/**
 * Project LectorTickets
 * Created by M.Angel on 11/08/2016.
 * Clase con el nombre del articulo a comprobar en la DB, la clase CheckArticulos contiene
 * la lista de instancias de esta clase que se usan para saber si el articulo existe en la DB.
 */
public class ArticuloConsulta {

    // nombre del articulo a comprobar en la DB
    @SerializedName("checkitem")
    private String checkitem;

    /**
     * Constructor
     */
    public ArticuloConsulta() {
    }

    /**
     *
     * @param checkitem
     */
    public ArticuloConsulta(String checkitem) {
        this.checkitem = checkitem;
    }

    /**
     *
     * @return
     */
    public String getCheckitem() {
        return checkitem;
    }

    /**
     *
     * @param checkitem
     */
    public void setCheckitem(String checkitem) {
        this.checkitem = checkitem;
    }

    @Override
    public String toString() {
        return "ArticuloConsulta{" +
                "checkitem='" + checkitem + '\'' +
                '}';
    }
}
