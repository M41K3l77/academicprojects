package edificioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import edificio.Estacion;
import edificio.Planta;

public class EstacionTest {

private Estacion estacion=null;	
private Planta planta=null;
private Estacion estacion2=null;
private Estacion estacion3=null;
	
	@Before
	public void setUp(){
		System.out.println("Ejecutando @Before setUp");
		estacion=Estacion.obtenerInstancia();
		planta=Planta.obtenerInstancia(6, 6, 0, 35, 0, 5, 30);
		estacion.setPlanta(planta);
		estacion3=Estacion.obtenerInstancia();
		estacion3.setPlanta(planta);
	}
	
	@Test
	public void testEstacion(){
		System.out.println("Ejecutando @Test testEstacion()");
		assertFalse(estacion==null);
		assertTrue(estacion!=null);
		estacion2=estacion;
		// Recordar que en estacion y planta tenemos patron singleton
		// por lo que solo habra una estacion y una planta
		assertSame(estacion2, estacion);
		assertSame(estacion3, estacion);
	}	
}