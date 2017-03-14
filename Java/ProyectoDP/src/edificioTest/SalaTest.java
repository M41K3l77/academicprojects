package edificioTest;
import static org.junit.Assert.*;

import edificio.Llave;
import edificio.Sala;
import org.junit.Before;
import org.junit.Test;
import personaje.Lider;
import personaje.Personaje;

public class SalaTest {	

	private Sala s1;
	private Sala s2;
	
	@Before
	public void setUp(){
		System.out.println("Ejecutando @Before setUp");
		s1= new Sala(0);
		s2= new Sala(1);
		Personaje lider1=new Lider();
		s1.anadirPersonaje(lider1);
	}

	@Test
	public void testAtributos(){
		System.out.println("Ejecutando @Test testAtributos");
		assertNotSame(s1, s2);
		Sala s3=s2;
		assertSame(s3, s2);
	}
	
	@Test
	public void testHaypersonajes(){
		System.out.println("Ejecutando @Test testHaypersonajes");
		assertTrue(s1.haypersonajes());
		assertFalse(s2.haypersonajes());
	}
	
	@Test
	public void testdevolverPrimeraLlaveSala(){
		System.out.println("Ejecutando @Test testdevolverPrimeraLlave");
		Llave key=new Llave(1);
		s1.anadirLlaveEnCestoSala(key);
		assertNotNull(s1.devolverPrimeraLLaveSala());
	}
	
	@Test
	public void testanadirPersonajeCola(){
		System.out.println("Ejecutando @Test testanadirPersonajeCola");
		assertTrue(s1.anadirPersonaje(new Lider()));
	}
}
