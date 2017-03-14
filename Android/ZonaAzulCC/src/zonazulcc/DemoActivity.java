package zonazulcc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DemoActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);	
				
		//Creamos una instancia de la clase AlertDemo
		AlertDemo alert = new AlertDemo();
		
		//Abrimos la ventana de dialogo
		alert.show(getSupportFragmentManager(), "AlertDemo");		
	}
}
