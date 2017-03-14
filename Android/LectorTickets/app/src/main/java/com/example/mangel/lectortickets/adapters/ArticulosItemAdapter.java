package com.example.mangel.lectortickets.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mangel.lectortickets.R;
import com.example.mangel.lectortickets.TicketRecognitionActivity;
import com.example.mangel.lectortickets.model.Articulo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

/**
 * Project LectorTickets
 * Created by M.Angel on 23/07/2016.
 * Adaptador de la lista de articulos de la listview en la cual se presentan los articulos
 * reconocidos, se controla el color del texto de los articulos según haya sido reconocido o no. A
 * de mas tiene los listeners para modificar cantidad y precio de los articulos (solo articulos reconocidos que estan en verde).
 */
public class ArticulosItemAdapter extends ArrayAdapter<Articulo> {


    private final Context context;
    // lista del adaptador
    private final ArrayList<Articulo> itemArticuloArrayList;

    // lista y mapa de articulos para los colores, segun en que lista este el articulo del itemArticuloArrayList
    // se le asignara un color diferente
    private ArrayList<String> arrayArticulosNoReconocidos;// articulos no reconocidos
    private Map<String, ArrayList<String>> mapasugerencias;// sugerencias (clave nombre en la itemArticuloArrayList y valores lista de nombres sugeridos)
    private ArrayList<String> arrayArticulosReconocidos;// articulos reconocidos

    /**
     * Constructor
     * @param context
     * @param itemArticuloArrayList
     */
    public ArticulosItemAdapter(Context context, ArrayList<Articulo> itemArticuloArrayList,
                                ArrayList<String> arrayArticulosNoReconocidos, Map<String, ArrayList<String>> mapasugerencias,
                                ArrayList<String> arrayArticulosReconocidos) {
        super(context, R.layout.list_item_articulo, itemArticuloArrayList);
        this.context = context;
        this.itemArticuloArrayList = itemArticuloArrayList;
        this.arrayArticulosNoReconocidos=arrayArticulosNoReconocidos;
        this.mapasugerencias=mapasugerencias;
        this.arrayArticulosReconocidos=arrayArticulosReconocidos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView (list_item_articulo) from inflater
        View rowView = inflater.inflate(R.layout.list_item_articulo, parent, false);

        // 3. Get the three text view from the rowView (list_item_articulo)
        TextView nombre = (TextView) rowView.findViewById(R.id.name_articulo);
        TextView cantidad_value = (TextView) rowView.findViewById(R.id.cantidad_articulo_value);
        TextView precio_value = (TextView) rowView.findViewById(R.id.precio_articulo_value);

        // 4. Set the text for textView
        // se muestra nombre, cantidad y precio del elemento (articulo) con color por defecto
        // mas adelante cambiará su color dependiendo de si es un articulo reconocido o no.
        nombre.setText(itemArticuloArrayList.get(position).getNombre());
        String cantiadArticulo=String.valueOf(itemArticuloArrayList.get(position).getCantidad());
        cantidad_value.setText(cantiadArticulo);
        String precioArticulo=String.valueOf(itemArticuloArrayList.get(position).getPrecio());
        precio_value.setText(precioArticulo);
        // segun si el articulo existe o no en la DB (parte servidora) y si hay sugerencias tendrá un color u otro
        //Log.i("AdapterListView", "nombre articulo: "+nombre.toString());
        if(this.arrayArticulosNoReconocidos != null && this.mapasugerencias != null){
            //Log.i("AdapterListView", "adapter map naranja: "+this.mapasugerencias);
            //Log.i("AdapterListView", "adapter lista roja: "+this.arrayArticulosNoReconocidos);
            // si es un elemento que está en la lista de artculos no reconocidos, se mostrará el nombre en rojo
            if(this.arrayArticulosNoReconocidos.contains(itemArticuloArrayList.get(position).getNombre())){// rojo
                nombre.setTextColor(ContextCompat.getColor(context, R.color.itemred));
                nombre.setTag(position);
                cantidad_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                cantidad_value.setTag(position);
                precio_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                precio_value.setTag(position);
                cantidad_value.setFocusableInTouchMode(false);
                precio_value.setFocusableInTouchMode(false);
                // si es un elemento que está en el mapa de artculos sugeridos, se mostrará el nombre en naranja
            }else if(this.mapasugerencias.containsKey(itemArticuloArrayList.get(position).getNombre())){// naranja
                nombre.setTextColor(ContextCompat.getColor(context, R.color.itemorange));
                nombre.setTag(position);
                cantidad_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                cantidad_value.setTag(position);
                precio_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                precio_value.setTag(position);
                cantidad_value.setFocusableInTouchMode(false);
                precio_value.setFocusableInTouchMode(false);
                // si es un elemento que está en la lista de artculos reconocidos, se mostrará el nombre en verde
            }else if(this.arrayArticulosReconocidos.contains(itemArticuloArrayList.get(position).getNombre())){// verde
                nombre.setTextColor(ContextCompat.getColor(context, R.color.itemgreen));
                nombre.setTag(position);
                cantidad_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                cantidad_value.setTag(position);
                precio_value.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
                precio_value.setTag(position);
                cantidad_value.setFocusableInTouchMode(true);
                precio_value.setFocusableInTouchMode(true);
                // listener para cambiar el valor de la cantidad. OnEditorActionListener para saber cuando se ha hecho el done con keyboard
                cantidad_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // si se hace done en el keyboard se quita foco del textview y se evita
                        // mostrar keyboard en caso de que el foco pase a otro textview.
                        // Aunque parezca contradictorio, android al quitar el foco, lo
                        // intenta pasar a otro textview y hay que forzar a que no se muestre el keyboard InputMethodManager.HIDE_NOT_ALWAYS
                        if(actionId== EditorInfo.IME_ACTION_DONE){
                            // se recupera el valor de la view
                            String cantidad=((TextView) v).getText().toString();
                            //Log.i("listadapter","nueva cantidad: "+cantidad);
                            // se recupera el articulo
                            Articulo articulo=ArticulosItemAdapter.this.getItem((int)((TextView) v).getTag());
                            // si string vacio (por que el usuario haya dejado el edittext sin valor), el articulo
                            // no se modifica y en la vista se vuelve a mostra el valor original.
                            if(cantidad.length()==0){
                                ((TextView) v).setText(String.valueOf(articulo.getCantidad()));
                            }else{
                                // se modifica el valor (cantidad) del articulo
                                articulo.setCantidad(Integer.valueOf(cantidad));
                                // se recalcula el valor total de la lista de la compra
                                ((TicketRecognitionActivity)ArticulosItemAdapter.this.context).calcularTotal();
                            }
                            //Clear focus here from edittext
                            v.clearFocus();
                            // Esta parte de aqui es para que una vez hecha una modificacion en la cantidad,
                            // el keyboard desaparezca y no se pueda modificar otro elemento ya que
                            // puede pasar foco al primer elemento de la vista (esto es cosa de android y no del codigo).
                            View view =  ((Activity)(ArticulosItemAdapter.this.context)).getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)ArticulosItemAdapter.this.context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                            // se notifica el cambio en el articulo de la lista de articulos para actualizar
                            // la vista.
                            ArticulosItemAdapter.this.notifyDataSetChanged();
                        }
                        return false;
                    }
                });
                // si se hace done en el keyboard se quita foco del textview y se evita
                // mostrar keyboard en caso de que el foco pase a otro textview
                // Aunque parezca contradictorio, android al quitar el foco, lo
                // intenta pasar a otro textview y hay que forzar a que no se muestre el keyboard InputMethodManager.HIDE_NOT_ALWAYS
                precio_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId== EditorInfo.IME_ACTION_DONE){
                            // se recupera valor de la vista
                            String precio = ((TextView) v).getText().toString();
                            // se recupera articulo de la lista
                            Articulo articulo=ArticulosItemAdapter.this.getItem((int)((TextView) v).getTag());
                            // si string vacio (por que el usuario haya dejado el edittext sin valor), el articulo
                            // no se modifica y en la vista se vuelve a mostra el valor original.
                            if(precio.length()==0){
                                // si se deja vacio el edittext, se pone el valor original
                                ((TextView) v).setText(String.valueOf(articulo.getPrecio()));
                            }else{
                                // se modifica el valor (precio del articulo)
                                BigDecimal precioArticulo = new BigDecimal(precio);
                                precioArticulo=precioArticulo.setScale(2, RoundingMode.DOWN);
                                ((TextView) v).setText(precioArticulo.toString());
                                articulo.setPrecio(precioArticulo);
                                // se recalcula el valor total de la lista de la compra
                                ((TicketRecognitionActivity)ArticulosItemAdapter.this.context).calcularTotal();
                            }
                            //Clear focus here from edittext
                            v.clearFocus();
                            // Esta parte de aqui es para que una vez hecha una modificacion en la cantidad,
                            // el keyboard desaparezca y no se pueda modificar otro elemento ya que
                            // puede pasar foco al primer elemento de la vista (esto es cosa de android y no del codigo).
                            View view =  ((Activity)(ArticulosItemAdapter.this.context)).getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)ArticulosItemAdapter.this.context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                            // se notifica el cambio en el articulo de la lista de articulos para actualizar
                            // la vista.
                            ArticulosItemAdapter.this.notifyDataSetChanged();
                        }
                        return false;
                    }
                });
            }
        }
        // 5. return rowView
        return rowView;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Articulo getItem(int position) {
        return this.itemArticuloArrayList.get(position);
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return this.itemArticuloArrayList.size();
    }
}
