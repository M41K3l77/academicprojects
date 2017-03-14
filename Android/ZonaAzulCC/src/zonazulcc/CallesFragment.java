package zonazulcc;

import java.util.ArrayList;

import com.example.zonaazulcc.R;

import model.ItemCalle;
import adapter.ItemCalleAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
/**
 * Implementacion de la clase CallesFragment
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class CallesFragment extends Fragment{
 
	ItemCalleAdapter itemCalleAdapter=null;
	ArrayList<ItemCalle> callesLista =null;

	ListView listViewCalles=null;
	View rootView=null;
	ArrayList<ItemCalle> listFilteredCalles=null;
	MainActivity mainActivity=null;
    
	/**
	 * costructor
	 * @param mainActivity
	 */
	public CallesFragment(MainActivity mainActivity) {
		this.mainActivity=mainActivity;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
        rootView=inflater.inflate(R.layout.fragment_calles, container, false);
        
        // 1. pass context and data to the custom adapter
        itemCalleAdapter = new ItemCalleAdapter(getActivity(), MainActivity.dataBaseHandler.getAllCalles());

        //2. setListAdapter
        listViewCalles = (ListView)rootView.findViewById(R.id.list);
        listViewCalles.setAdapter(itemCalleAdapter);
        
        listViewCalles.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View View, int position, long id) {
              
            	//Guardamos la calle seleccionada
            	mainActivity.setCalleActual(itemCalleAdapter.getItem(position));
            	mainActivity.setUpMap();
            	ViewPager viewPager= (ViewPager) getActivity().findViewById(R.id.viewpager);

            	viewPager.setCurrentItem(1);

            }
        });
        
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add(R.string.action_search);
        item.setIcon(R.drawable.ic_action_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside editText component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint(R.string.action_search);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        	
            @Override
            public boolean onQueryTextSubmit(String s) {
            	return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            	doSearch(newText);
                return true;
            }
            
        });
        item.setActionView(sv);
    }
    /**
     * metodo que carga las calles segun el texto introducido por el usuario
     * s es la cadena de texto introducida por el usuario
     * @param s
     */
    private void doSearch(String s) {
		// TODO Auto-generated method stub;
	    itemCalleAdapter = new ItemCalleAdapter(getActivity(), encontradaDataCalles(s));
	    
	    listViewCalles = (ListView)rootView.findViewById(R.id.list);
	    listViewCalles.setAdapter(itemCalleAdapter);
		
	}

    /**
     * metodo que devuelve una lista de calles que concuerdan con la
     * cadena de texto introducida por el usuario
     * @return listFilteredCalles
     */
    private ArrayList<ItemCalle> encontradaDataCalles(String street){
    	
    	listFilteredCalles = new ArrayList<ItemCalle>();
    	callesLista=MainActivity.dataBaseHandler.getAllCalles();
    	if(callesLista!=null){
    		for( ItemCalle calle : callesLista ) {
                if( calle.getNombreDeCalle().toLowerCase().contains(street.toLowerCase())) {
                	listFilteredCalles.add(calle);
                }
    		}
        }        
        return listFilteredCalles;
    }

 // getters y setters
	public View getRootView() {
		return rootView;
	}

	public ListView getListViewCalles() {
		return listViewCalles;
	}

	public ArrayList<ItemCalle> getCallesLista() {
		return callesLista;
	}    
    
	public void setCallesLista(ArrayList<ItemCalle> callesLista) {
		this.callesLista = callesLista;
	}
	

	public ItemCalleAdapter getItemCalleAdapter() {
		return itemCalleAdapter;
	}

	public void setItemCalleAdapter(ItemCalleAdapter itemCalleAdapter) {
		this.itemCalleAdapter = itemCalleAdapter;
	}
}
