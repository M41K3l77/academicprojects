package edificioTest;
import org.junit.*;

import edificio.Llave;
import edificio.Puerta;
import static org.junit.Assert.*;

public class PuertaTest {
	Puerta puerta1=null;
	Puerta puerta2=null;
	Puerta puerta3=null;
	Puerta puerta4=null;
	Puerta puerta5=null;
	Llave llave1=new Llave(1);
	Llave llave2=new Llave(2);
	Llave llave3=new Llave(3);
	Llave llave4=new Llave(4);
	Llave llave5=new Llave(5);
	
	@Before
	public void SetUp(){
		System.out.println("Before");
		puerta1=new Puerta();
		puerta2=new Puerta();
		puerta3=new Puerta();
		puerta1.setEstadoPuerta(0);
		puerta1.addLlaveCerradura(llave1);
		puerta1.addLlaveCerradura(llave2);
		puerta1.addLlaveCerradura(llave3);
		puerta1.addLlaveLlavero(llave1);
		puerta1.addLlaveLlavero(llave2);
		puerta2.setEstadoPuerta(1);

	}
	
	@Test
	public void TestSimplePuerta(){
		System.out.println("Test");
		assertTrue(puerta1.getEstadoPuerta()==0);
		assertTrue(puerta2.getEstadoPuerta()==1);
		assertFalse(puerta2.getEstadoPuerta()==0);
		assertTrue(puerta1.getCerradura().numeroHojas()==1);
		assertTrue(puerta1.getCerradura().numeroDeNodos()==3);
		assertFalse(puerta1.getCerradura().numeroDeNodos()==5);
		assertTrue(puerta1.getCerradura().numeroHijos()==1);
		assertFalse(puerta1.getCerradura().numeroHijos()==4);
		assertTrue(puerta1.getCerradura().profundidad()==3);
		assertTrue(puerta1.getCerradura().numeroNodosInternos()==2);
		assertFalse(puerta1.getCerradura().numeroNodosInternos()==1);
		puerta1.getCerradura().borrar(llave1);
		assertTrue(puerta1.getCerradura().profundidad()==2);
		assertFalse(puerta1.getCerradura().pertenece(llave1));
		assertTrue(puerta1.getCerradura().numeroDeNodos()==2);
		assertNotNull(puerta1);
		assertNull(puerta4);
		
		//con llavero
		assertTrue(puerta1.getLlavero().pertenece(llave1));
		assertTrue(puerta1.getLlavero().pertenece(llave2));
		assertFalse(puerta1.getLlavero().pertenece(llave5));
		assertTrue(puerta1.getLlavero().numeroHojas()==1);
		assertTrue(puerta2.getEstadoPuerta()==1);
		assertTrue(puerta1.getLlavero().profundidad()==2);
		
		
	}
	
	@Test
	public void  testbuscarLlaveEnLlavero(){
		System.out.println("@Test testbuscarLlaveEnLlavero()");
		assertTrue(puerta1.buscarLlaveEnLlavero(llave2));
		assertFalse(puerta1.buscarLlaveEnLlavero(llave3));
	}
}
