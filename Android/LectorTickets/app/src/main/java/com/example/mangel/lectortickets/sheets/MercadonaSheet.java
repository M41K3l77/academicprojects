package com.example.mangel.lectortickets.sheets;

import com.example.mangel.lectortickets.MainActivity;
import com.example.mangel.lectortickets.model.Articulo;
import com.example.mangel.lectortickets.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project LectorTickets
 * Created by M.Angel on 17/07/2016.
 * Plantilla para el establecimiento MERCADONA.
 * Esta clase debe implementar el metodo (public abstract Ticket formalizarTicket(String recognizedText))
 * de la clase abstracta TicketSheet
 */
public class MercadonaSheet extends TicketSheet {
    // nombre del establecimiento
    private String establecimiento;
    private static final String TAG = "MercadonaSheet";

    /**
     * Constructor
     */
    public MercadonaSheet() {
        super();
        this.establecimiento="MERCADONA";
    }

    /**
     * Metodo que implementa Ticket formalizarTicket(String recognizedText) de la clase abstracta TicketSheet.
     * @param recognizedText es el texto en crudo obtenido por el reconocedor tess-two.
     * @return Ticket, que es el ticket reconocido.
     */
    @Override
    public Ticket formalizarTicket(String recognizedText) {
        // El string con el texto en crudo se trata para que
        // se tengan los datos en lienas
        String[] lines = recognizedText.split(System.getProperty("line.separator"));
        // Quitamos espacios en blanco que no nos sirvan
        for (int i = 0; i < lines.length; i++) {
            lines[i]=lines[i].trim().replaceAll("\\s+", " ").replaceAll(",", ".").replaceAll("\\p{Pd}", "-");// el ultimo es el em dash
        }
        // vemos texto recibido en lienas
        /*for (int i = 0; i < lines.length; i++) {
            Log.i(TAG, "line " + i + ": " + lines[i]);
        }*/
        // obtener fecha del ticket
        String fecha=this.buscarFecha(lines);
        // obtener lista de articulos
        ArrayList <Articulo> listaArticulos=this.lineasConArticulos(lines);

        // vemos lista de articulos
        /*for(int i=0; i<listaArticulos.size();i++){
            Log.i(TAG, "articulo del ticket " + i + ": " + listaArticulos.get(i).toString());
        }*/

        Ticket ticket=new Ticket(fecha, listaArticulos, this.establecimiento, MainActivity.TOKEN);

        return ticket;
    }

    /**
     * Metodo que devuelve la lista de articulos a partir de una expresion regular que identifica
     * a una liena de articulo.
     * @param lines contiene el texto en lineas
     * @return articuloLines que es la lista de articulos del ticket.
     */
    private  ArrayList<Articulo> lineasConArticulos(String[] lines){
        ArrayList<Articulo> articuloLines=new ArrayList<Articulo>();
        // patron con la expresion regular que identifica a un articulo
        Pattern p = Pattern.compile("(\\d+)[ ](\\d+)[ ]([a-zA-ZnÑ][\\.]?[a-zA-ZñÑ0-9\\-\\/'´` ]*)+[ ](\\d+[.]\\d+)( \\d+[.]\\d+)?");   // the pattern to search for line with articule
        // se recorren todas las lineas para obtener los articulos
        for(int i=0;i<lines.length;i++){
            Matcher m = p.matcher(lines[i]);
            // la linea contiene articulo
            if(m.find()){
                //Log.i(TAG, "articulo lines por partes " + i + ": " + m.group(1)+" "+m.group(2)+" "+m.group(3)+" "+m.group(5)+" grupos: "+m.groupCount()+" el 4: "+m.group(4));
                String lineaArticulo=m.group();
                /* nombre del articulo*/
                String nombreArticulo=lineaArticulo.replaceAll("(\\d+[.]\\d+)", "").replaceAll("(\\d+)[ ](\\d+)","").trim();
                // en la linea de ese articulo la cantidad es 1
                if(m.group(2).equals("1")){
                    // hay que ver si el articulo ya existe en la lista de articulos
                    // si el articulo ya está anhadido solo hay que sumarle la cantidad, de lo contrario hay que anhadir el articulo completo
                    // este caso es especial porque puede estar repetido con diferente precio por lo que se pone la media
                    boolean encontrado=false;
                    int j=0;
                    while (j<articuloLines.size() && !encontrado ){

                        if(nombreArticulo.equals(articuloLines.get(j).getNombre())){
                            // se actualiza cantidad y precio
                            encontrado=true;
                            //Log.i(TAG, "producto encontrado " + i + ": " + articuloLines.get(j).toString()+" precio nuevo producto: "+m.group(4));
                            //Log.i(TAG, "cantidad antes " + i + ": " + nombreArticulo+" cantidad: "+articuloLines.get(j).getCantidad());
                            // se actualiza cantidad
                            articuloLines.get(j).setCantidad(articuloLines.get(j).getCantidad()+Integer.valueOf(m.group(2)));
                            //Log.i(TAG, "cantidad despues " + i + ": " + nombreArticulo+" cantidad: "+articuloLines.get(j).getCantidad());
                            // se actualiza precio ya que el mismo articulo puede tener diferentes precios
                            // por compromiso se pone la media.
                            BigDecimal precioMedio=(articuloLines.get(j).getPrecio().add(new BigDecimal((m.group(4))))).divide(new BigDecimal("2"));
                            precioMedio=precioMedio.setScale(2, RoundingMode.DOWN);
                            articuloLines.get(j).setPrecio(precioMedio);
                        }else{
                            j++;
                        }

                    }
                    // si el articulo no esta en la lista se inserta
                    if(!encontrado){
                        BigDecimal precioArticulo=new BigDecimal(m.group(4));
                        precioArticulo=precioArticulo.setScale(2, RoundingMode.DOWN);
                        // se añade articulo con su nombre, cantidad y precio
                        articuloLines.add(new Articulo(nombreArticulo, Integer.valueOf(m.group(2)), precioArticulo));
                    }
                }else{// si cantidad en la linea del articulo es mayor a 1
                    // si el articulo ya está anhadido solo hay que sumarle la cantidad, de lo contrario hay que anhadir el articulo completo
                    // solo hay que cambiar la cantidad ya que el precio es fijo
                    boolean encontrado=false;
                    int j=0;
                    while (j<articuloLines.size() && !encontrado ){
                        if(nombreArticulo.equals(articuloLines.get(j).getNombre())){
                            // se actualiza cantidad y precio
                            encontrado=true;
                            articuloLines.get(j).setCantidad(articuloLines.get(j).getCantidad()+Integer.valueOf(m.group(2)));
                        }else{
                            j++;
                        }
                    }
                    // si el articulo no esta en la lista se inserta
                    if(!encontrado){
                        BigDecimal precioArticulo=new BigDecimal(m.group(4));
                        precioArticulo=precioArticulo.setScale(2, RoundingMode.DOWN);
                        articuloLines.add(new Articulo(nombreArticulo, Integer.valueOf(m.group(2)), precioArticulo));
                    }
                }
            }else{
                //Log.i(TAG, "line " + i + ": " + "linea sin articulo");
            }
        }
        return articuloLines;
    }



    /**
     * Metodo para buscar la fecha en el texto reconocido
     * @param lines contiene el texto en lineas
     * @return fecha (String) del ticket
     */
    private String buscarFecha(String[] lines){
        String fecha=null;
        // ([: ]?) el reconocedor puede no haber reconocido los dos puntos o sustituirlos por espacio. el group devolvería null
        // patron para buscar la fecha
        Pattern p = Pattern.compile("(\\d{2})\\/(\\d{2})\\/(\\d{4}) (\\d{2}).?(\\d{2})");
        int i=0;
        boolean encontrado=false;
        while (i < lines.length && !encontrado) {// tiene que ser un while
            Matcher m = p.matcher(lines[i]);
            if(m.find()){
                //Log.i(TAG, "line " + i + ": " + m.group());
                // una vez encontrado el string con la fecha hay que tratarlo y ponerla en el
                // formato adecuado para la DB
                fecha=this.tratarFecha(m.group(3), m.group(2), m.group(1), m.group(4), m.group(5));
                //Log.i(TAG, "fecha en linea " + i + ": " + fecha);
                encontrado=true;
            }else{
                //Log.i(TAG, "line " + i + ": " + "no se encontro la fecha");
                i++;
            }
        }
        return fecha;
    }

    /**
     * Devuelve la fecha con el formato adecuado para el servicio REST
     * @param anho
     * @param mes
     * @param dia
     * @param hora
     * @param minutos
     * @return fechaTicket (String) fecha del ticket con el formato adecuado
     */
    private String tratarFecha(String anho, String mes, String dia, String hora, String minutos){
        String fechaTicket=anho+"-"+mes+"-"+dia+"T"+hora+":"+minutos+":00";
        return fechaTicket;
    }
}
