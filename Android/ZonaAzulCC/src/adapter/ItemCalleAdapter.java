package adapter;

import java.util.ArrayList;

import model.ItemCalle;
import com.example.zonaazulcc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Implementacion de la clase ItemCalleAdapter
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ItemCalleAdapter extends ArrayAdapter<ItemCalle>{
	
	private final Context context;
    private final ArrayList<ItemCalle> itemCalleArrayList;
    
    /**
	 * Constructor, se le pasa el fragment manager y la lista de fragment
	 * @param fm
	 * @param fragments
	 */
    public ItemCalleAdapter(Context context, ArrayList<ItemCalle> itemCalleArrayList) {
    	 
        super(context, R.layout.rowcalles, itemCalleArrayList);
        this.context = context;
        this.itemCalleArrayList = itemCalleArrayList;
    }
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.rowcalles, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.nombrecalle);
        TextView valueView = (TextView) rowView.findViewById(R.id.numeroplazascalle);

        // 4. Set the text for textView 
        labelView.setText(itemCalleArrayList.get(position).getNombreDeCalle());
        String numPlazas=String.valueOf(itemCalleArrayList.get(position).getNumeroDePlazasCalle());
        valueView.setText(numPlazas+" plazas");

        // 5. retrn rowView
        return rowView;
    }

    /**
	  * devuelve la calle(ItemCalle) en la posicion (position) en la lista itemCalleArrayList
	  */
    @Override
	public ItemCalle getItem(int position) {
		return this.itemCalleArrayList.get(position);
	}    

    /**
	 * devuelve el tamanho de la lista de itemCalleArrayList(numero de calles del modelo)
	 */
	@Override
	public int getCount() {
		return this.itemCalleArrayList.size();
	}
	
}
