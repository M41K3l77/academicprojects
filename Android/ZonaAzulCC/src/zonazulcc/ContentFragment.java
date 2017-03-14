package zonazulcc;

import com.example.zonaazulcc.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Implementacion de la clase ContentFragment
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ContentFragment extends Fragment {

	TextView mContent;
	Activity mActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View inf = inflater.inflate(R.layout.content_fragment, container, false);
		mContent = (TextView) inf.findViewById(R.id.content_text);
		return inf; 

	}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		mActivity = activity;
	}


	@Override
	public void onResume() {
		
		super.onResume();
		

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		boolean mapa = prefs.getBoolean("pref_mapa", true);
	
		
	}


	

}
