package com.example.mangel.lectortickets.jsonobject;

import com.google.gson.annotations.SerializedName;

/**
 * Project LectorTickets
 * Created by M.Angel on 11/08/2016.
 * Clase usada en (CheckedArticulos) como lista del mismo.
 * La instancia dira si el nombre del articulo a comprobar existe en la DB, si el articulo no existe
 * el atributo exist estara a false. Si no existe el nombre exactamente pero hay sugerencias, el atributo
 * exist estara a false y el atributo suggesteditem contentra una de las sugerencias. Si existe el
 * nombre del articulo exactamente, entonces el atributo exist valdra true y suggesteditem no tendra valor.
 * NOTA: El servidor puede devolver hasta 3 sugerencias(limite puesto en la parte servidora) para el nombre
 * de un articulo que se envio a comprobar y que no existe literalmente.
 * Ejm: Si tuvieramos 3 sugerencias para articulo "X", tendriamos 3 intancias con exist a false,
 * checkeditem valdria "X" en cada una de ellas y suggesteditem con uno de los valores sugeridos.
 * Logicamente sería mas correcto que CheckedArticulos fuera un mapa o una lista de listas pero debido
 * a las limitaciones de webratio (o mi limitado conocimiento del mismo) se tiene que devolver
 * los datos en una lista. La logica implementada se encarga de la criba de los datos y su agrupamiento
 * segun exista el articulo, no exista o sea la sugerencia de un articulo.
 */
public class Checkeditem {

    // nombre del articulo sugerido en caso de que no haya encontrado exactamente el articulo
    // en la DB
    @SerializedName("suggesteditem")
    private String suggesteditem;
    // nombre del articulo que se comprobo
    @SerializedName("checkeditem")
    private String checkeditem;
    // nos dice si el articulo existe exactamente con ese nombre (checkeditem) en la DB.
    @SerializedName("exist")
    private boolean exist;

    public Checkeditem() {
    }

    public Checkeditem(String suggesteditem, String checkeditem, boolean exist) {
        this.suggesteditem = suggesteditem;
        this.checkeditem = checkeditem;
        this.exist = exist;
    }

    public String getSuggesteditem() {
        return suggesteditem;
    }

    public void setSuggesteditem(String suggesteditem) {
        this.suggesteditem = suggesteditem;
    }

    public String getCheckeditem() {
        return checkeditem;
    }

    public void setCheckeditem(String checkeditem) {
        this.checkeditem = checkeditem;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    @Override
    public String toString() {
        return "Checkeditem{" +
                "suggesteditem='" + suggesteditem + '\'' +
                ", checkeditem='" + checkeditem + '\'' +
                ", exist=" + exist +
                '}';
    }
}
