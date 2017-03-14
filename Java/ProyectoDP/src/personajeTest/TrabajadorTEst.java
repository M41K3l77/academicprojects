package personajeTest;

import personaje.Trabajador;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



/**
* Implementacion pruebas jUnit de la clase trabajador.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
*/
public class TrabajadorTEst {
	// Se han realizado pruebas mas simples para la clase trabajador ya que
	// los metodos principales que usan son iguales
	private Trabajador t1;
	private Trabajador t2;
	private Trabajador t3;
	
	@Before
	public void setUp(){
		t1=new Trabajador();
		t2=new Trabajador();
		t3=null;
	}
	
	@Test
	public void TestTrabajador(){		
		assertNotNull(t1);
		assertNotNull(t2);
		assertNull(t3);
		assertNotSame(t1, t2);
		t3=t2;
		assertSame(t2, t3);		
	}
}
