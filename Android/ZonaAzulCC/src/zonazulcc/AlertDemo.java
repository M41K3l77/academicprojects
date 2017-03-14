package zonazulcc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.zonaazulcc.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import android.media.MediaPlayer;


public class AlertDemo extends DialogFragment {
	public MediaPlayer sound;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Encendemos la pantalla y desbloqueamos para mostrar el dialogo de
		// alerta
		getActivity().getWindow().addFlags(
				LayoutParams.FLAG_TURN_SCREEN_ON
						| LayoutParams.FLAG_DISMISS_KEYGUARD);

		// Creamos el constructor del dialogo
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Escojemos el tono de alarma
		switch (MainActivity.tono) {
		case "bells":
			sound = MediaPlayer.create(getActivity().getApplicationContext(),
					R.raw.bells);
			break;
		case "com_sms":
			sound = MediaPlayer.create(getActivity().getApplicationContext(),
					R.raw.com_sms);
			break;
		case "funny_alarm":
			sound = MediaPlayer.create(getActivity().getApplicationContext(),
					R.raw.funny_alarm);
			break;
		case "xperia_z_themes":
			sound = MediaPlayer.create(getActivity().getApplicationContext(),
					R.raw.xperia_z_themes);
			break;
		default:
			sound = MediaPlayer.create(getActivity().getApplicationContext(),
					R.raw.xperia_z_themes);
			break;
		}

		if (sound.isPlaying()) {
			sound.stop();
		} else {
			try {
				sound.start();
				sound.setLooping(true);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}

		final FrameLayout frameView = new FrameLayout(getActivity()
				.getApplicationContext());
		builder.setView(frameView);

		final AlertDialog alertDialog = builder.create();
		LayoutInflater inflater = alertDialog.getLayoutInflater();

		// Calendar c2 = new GregorianCalendar();
		//
		// int hora = c2.get(Calendar.HOUR_OF_DAY);
		// int minutos = c2.get(Calendar.MINUTE);

		// Editamos el contenido del dialogo
		if (MainActivity.adelantoAlarma == MainActivity.TWO_MINS)
			builder.setMessage("2 minutos");
		else if (MainActivity.adelantoAlarma == MainActivity.FIVE_MINS)
			builder.setMessage("5 minutos");
		else if (MainActivity.adelantoAlarma == MainActivity.TEN_MINS)
			builder.setMessage("5 minutos");
		else
			builder.setMessage("5 minutos");

		builder.setTitle("Su Ticket expira en:");

		// Definimos escuchador del boton OK
		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				// Salimos de la App al pulsar OK
				sound.stop();
				getActivity().finish();
			}
		});

		// Creamos la ventana de dialogo
		return builder.create();
	}

	// La app debe salir si el usuario pulsa el boton de atras
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().finish();
	}

}
