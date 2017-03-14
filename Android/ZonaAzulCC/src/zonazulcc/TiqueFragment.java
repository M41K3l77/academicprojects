package zonazulcc;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.zonaazulcc.R;
import com.google.android.gms.maps.GoogleMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
/**
 * Implementacion de la clase TiqueFragment
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class TiqueFragment extends Fragment implements OnTimeSetListener {

	// Alarma
	public Intent i;
	public PendingIntent accion;
	public AlarmManager alarmManager;

	private Switch mPickTime=null;
	private TimePicker mTimePicker=null;

	static TextView textViewCalle;
	private TextView textViewHoraInicio;
	private TextView textViewHoraInicioRes;
	private String horaInicio;
	private String horaFin;
	private TextView textViewHoraFinRes;
	private MainActivity mainActivity;
	private String calleDeLaAlarma;
	public TiqueFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tique, container,
				false);

		textViewCalle = (TextView) rootView.findViewById(R.id.textViewCalle);
		textViewHoraInicio = (TextView) rootView
				.findViewById(R.id.textViewHoraInicio);
		textViewHoraInicioRes = (TextView) rootView
				.findViewById(R.id.textViewHoraInicioRes);
		textViewHoraFinRes = (TextView) rootView
				.findViewById(R.id.textViewHoraFinRes);
		
		
		
		//if(mPickTime==null){
			mPickTime = (Switch) rootView.findViewById(R.id.switchAlarma);
			if(!mainActivity.isAlarmaActivada()){
				mPickTime.setActivated(false);// Por defecto desactivado
			}
			
		//}
			mTimePicker = (TimePicker) rootView.findViewById(R.id.timePicker1);
			mPickTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					// Este intent invoca a la Actividad DemoActivity, que lanza la
					// ventana de Dialogo
					i = new Intent("zonazulcc.demoactivity");

					// Editamos el pendingIntent
					accion = PendingIntent.getActivity(getActivity()
							.getBaseContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);

					// Cojemos la referencia al Servicio de ALARMAS del Sistema
					alarmManager = (AlarmManager) getActivity().getBaseContext()
							.getSystemService(Service.ALARM_SERVICE);

					if (isChecked && !mainActivity.isAlarmaActivada()){//&& !mainActivity.isAlarmaActivada()

						mainActivity.setAlarmaActivada(true);
						if(mainActivity.getCalleActual()!=null){
							calleDeLaAlarma=new String(mainActivity.getCalleActual().getNombreDeCalle());
						}
						
						// Fijamos hora INICIO
						Calendar c = Calendar.getInstance();
						int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
						int minute = c.get(Calendar.MINUTE);
						int year = c.get(Calendar.YEAR);
						int month = c.get(Calendar.MONTH);
						int day = c.get(Calendar.DAY_OF_MONTH);

						if (minute < 10){
							textViewHoraInicioRes
									.setText(hourOfDay + ":0" + minute);
							horaInicio=new String(hourOfDay+":0"+minute);
						}else{
							textViewHoraInicioRes.setText(hourOfDay + ":" + minute);
							horaInicio=new String(hourOfDay+":"+minute);
						}
						// Fijamos hora FIN
						int hourAlarma = mTimePicker.getCurrentHour();
						int minuteAlarma = mTimePicker.getCurrentMinute();

						if (minuteAlarma < 10){
							textViewHoraFinRes.setText(hourAlarma + ":0"
									+ minuteAlarma);
							horaFin=new String(hourAlarma+":0"+minuteAlarma);
						}else{
							textViewHoraFinRes.setText(hourAlarma + ":"
									+ minuteAlarma);
							horaFin=new String(hourAlarma+":"+minuteAlarma);
						}
						
						// Mostramos al usuario
						textViewHoraInicio.setVisibility(View.VISIBLE);
						textViewHoraInicioRes.setVisibility(View.VISIBLE);
						textViewHoraFinRes.setVisibility(View.VISIBLE);
						mTimePicker.setVisibility(View.INVISIBLE);

						/*
						 * --------------------- Crear alarma
						 * --------------------------
						 */

						// Creamos un objeto Calendario con los datos de la alarma
						// elegidos por el usuario
						GregorianCalendar calendar = new GregorianCalendar(year,
								month, day, hourAlarma, minuteAlarma);

						// Pasamos la fecha y hora a milisegundos
						long alarm_time = calendar.getTimeInMillis();
						

						// Editamos la alarma, con la hora y la accion asociada (restamos retardo)						
						alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time-mainActivity.getAdelantoAlarma(),accion);

						// Retroalimentacion Usuario
						Toast.makeText(getActivity().getBaseContext(),
								"Alarma activada exitosamente", Toast.LENGTH_LONG)
								.show();

					} else if(!isChecked) {

						mainActivity.setAlarmaActivada(false);
				
						// Desactivar alarma
						alarmManager.cancel(accion);

						textViewHoraInicio.setVisibility(View.INVISIBLE);
						textViewHoraInicioRes.setVisibility(View.INVISIBLE);
						textViewHoraFinRes.setVisibility(View.INVISIBLE);
						mTimePicker.setVisibility(View.VISIBLE);

					}

				}
			});
		return rootView;
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

	}
	public String getCalleDeLaAlarma() {
		return calleDeLaAlarma;
	}

	@Override
    public void onResume() {
        super.onResume();
        if(mainActivity.getCalleActual()!=null){
        	if(!mainActivity.isAlarmaActivada()){
        		textViewCalle.setText(mainActivity.getCalleActual().getNombreDeCalle());
        	}else{
        		textViewCalle.setText(calleDeLaAlarma);
        	}
        }
        if(mainActivity.isAlarmaActivada()){
        	mTimePicker.setVisibility(View.INVISIBLE);
        	textViewHoraInicioRes.setText(horaInicio);
        	textViewHoraInicioRes.setVisibility(View.VISIBLE);
        	textViewHoraFinRes.setText(horaFin);
        	textViewHoraFinRes.setVisibility(View.VISIBLE);        	
        }
    }
}
