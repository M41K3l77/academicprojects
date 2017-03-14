package adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Implementacion de la clase TabsPagerAdapter
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
	
	private List<Fragment> fragments;
	
	/**
	 * Constructor, se le pasa el fragment manager y la lista de fragment
	 * @param fm
	 * @param fragments
	 */
	 public TabsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
	        super(fm);
	        this.fragments = fragments;
	    }

	 /**
	  * devuelve el fragmen en la posicion (position) en la lista de fragments
	  */
	 @Override
	 public Fragment getItem(int position) {
		 return this.fragments.get(position);
	 }

	 /**
	  * devuelve el tamanho de la lista de fragments
	  */
	 @Override
	 public int getCount() {
		 return this.fragments.size();
	 }
}
