package com.example.mangel.lectortickets.jsonobject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Project LectorTickets
 * Created by M.Angel on 11/08/2016.
 * Clase que contiene una lista de articulos (Checkeditem) comprobados por parte
 * del servidor. La instancia es devuelta en
 * la llamada Restful Call<CheckedArticulos> checkArticulos(@Body CheckArticulos articulosConsultar)
 * ver: Clase Checkeditem.
 */
public class CheckedArticulos {

    // lista con los elementos comprobados en la DB
    @SerializedName("checkeditemlist")
    private List<Checkeditem> checkeditemlist = new ArrayList<Checkeditem>();

    public CheckedArticulos() {
    }

    public CheckedArticulos(List<Checkeditem> checkeditemlist) {
        this.checkeditemlist = checkeditemlist;
    }

    public List<Checkeditem> getCheckeditemlist() {
        return checkeditemlist;
    }

    public void setCheckeditemlist(List<Checkeditem> checkeditemlist) {
        this.checkeditemlist = checkeditemlist;
    }

    @Override
    public String toString() {
        return "CheckedArticulos{" +
                "checkeditemlist=" + checkeditemlist +
                '}';
    }
}
