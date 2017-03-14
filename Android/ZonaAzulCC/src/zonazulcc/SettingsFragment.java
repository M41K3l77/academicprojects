package zonazulcc;

import com.example.zonaazulcc.R;
import com.google.android.gms.maps.GoogleMap;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Implementacion de la clase SettingsFragment
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	
	public static final String KEY_PREF_USER = "pref_user";
	public static final String KEY_PREF_MAPA = "pref_tipo_mapa";
	public static final String KEY_PREF_ALARMA = "pref_tipo_alarma";
	public static final String KEY_PREF_TONO = "pref_tipo_tono";
	public static final String KEY_PREF_MAPA_DEFAULT = "pref_mapa_default";
	public static final String KEY_PREF_ALARMA_DEFAULT = "pref_alarma_default";
	public static final String KEY_PREF_TONO_DEFAULT = "pref_tono_default";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

			
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		view.setBackgroundColor(getResources().getColor(
				android.R.color.background_light));

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		return view;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		if (key.equals(KEY_PREF_MAPA)) {
			Preference connectionPref = findPreference(key);
			connectionPref.setSummary(sharedPreferences.getString(key, ""));
		}

		if (key.equals(KEY_PREF_ALARMA)) {
			Preference connectionPref = findPreference(key);
			connectionPref.setSummary(sharedPreferences.getString(key, ""));
		}

		if (key.equals(KEY_PREF_TONO)) {
			Preference connectionPref = findPreference(key);
			connectionPref.setSummary(sharedPreferences.getString(key, ""));
		}

	
	}
	
	@Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
 
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

	
}