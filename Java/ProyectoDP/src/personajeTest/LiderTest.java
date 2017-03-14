package personajeTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import personaje.Lider;
import direccion.Direccion;
import edificio.Llave;
import edificio.Planta;
import estructuras.Lista;
import estructuras.Pila;
import excepciones.ExceptionCerraduraNoValida;
import excepciones.ExceptionCombinacionDeCerraduraIncorrecta;

public class LiderTest {
	List<Direccion> direcciones=new ArrayList<Direccion>();
	Lider l1=null;
	Lider l2=null;
	Lider l3=null;
	Lider l4=null;
	Planta planta1=null;
	@Before
	public void SetUpPlantayPersonaje() throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		System.out.println("Setupplanta");
		planta1=Planta.obtenerInstancia(6, 6, 0, 35, 0, 5, 30);
		planta1.setTurnoAccion(1);
		Llave llave1=new Llave(1);
		Llave llave2=new Llave(2);
		Lista<Llave> combinacion=new Lista<Llave>();
		combinacion.insertDato(llave1);
		combinacion.insertDato(llave2);
		
		// una vez configurada la puerta la cerramos
		planta1.getDoor().setEstadoPuerta(1);		
		
		System.out.println("setuppersonaje");
		Pila<Llave> llavesper1=new Pila<Llave>();
		llavesper1.insertarDato(llave1);
		llavesper1.insertarDato(llave2);
		//creo un personaje y le pongo nombre, marca, sala actual,turno
		l1=new Lider();
		l1.setNombre("Carlos");
		l1.setMarca("L");
		l1.setSalaActual(0);
		l1.setTurno(1);
		//creo otro personaje
		l2=new Lider();
		l2.setMarca("l");
		l2.setLLaves(llavesper1);
		
		
		llavesper1.insertarDato(llave1);
		llavesper1.insertarDato(llave2);
		//le asigno unas llaves al lider1
		l1.setLLaves(llavesper1);
		Direccion[] dirLider={Direccion.S, Direccion.S, Direccion.E, Direccion.E
				,Direccion.N, Direccion.E, Direccion.N, Direccion.E, Direccion.S
				, Direccion.E, Direccion.S, Direccion.S, Direccion.O, Direccion.S
				, Direccion.S, Direccion.E};
		direcciones.addAll(Arrays.asList(dirLider));
		// y unas direcciones que tomar
		l1.setdirecciones(direcciones);	
	}
	
	@Test
	public void testNombreyMarca(){
		System.out.println("Testnombreymarca");
		assertNull(l4);
		assertNotNull(l1);
		assertTrue(l1.getNombre()=="Carlos");
		assertTrue(l1.getMarca()=="L");
	}
	@Test
	public void testMoverse(){
		System.out.println("Testmoverse");
		//añado al personaje
		planta1.anadirPersonajePlanta(l1,0);
		assertTrue(planta1.getTurnoAccion()==l1.getTurno());
		
		assertTrue(l1.getTurno()==1);
		assertTrue(l1.getSalaActual()==0);
		//VEO QUE NO HAY LLAVES EN LA SALA
		assertTrue(planta1.devolverSala(0, 1).getCestoLlaves().size()==0);
		//EL MOVIMIENTO Y LA RECOGO
		l1.moverse(planta1);
		
		
		//COMPRUEBO QUE SE HA MOVIDO COMPROBANDO SUS SU TURNOS
		assertTrue(l1.getTurno()==2);
		//ya no esta en la 0
		assertFalse(l1.getSalaActual()==0);
		//y esta en la seis por que tenia la direccion sur
		assertTrue(l1.getSalaActual()==6);
		//VEO QUE AHORA TIENE UNA LLAVE MENOS
		assertTrue(planta1.devolverSala(1, 0).getCestoLlaves().size()==0);
		planta1.setTurnoAccion(2);
		assertTrue(!(planta1.devolverSala(1, 0).cuantosPersonajes()==0));
		
		
	}
	
	@Test
	public void comprobarPuertayEscaparse() throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		System.out.println("Testcomprobarpuerta");
		assertTrue(l1.comprbarSiHayPuerta(planta1)==false);
		//AÑADO OTRO PERSONAJE A LA ULTIMA SALA Y COMPRUEBO QUE HALLA PUERTA
		planta1.anadirPersonajePlanta(l2, 34);
		l2.setSalaActual(35);
		l2.setTurno(3);
		planta1.setTurnoAccion(3);
		
		
		assertTrue(l2.comprbarSiHayPuerta(planta1)==true);
		//quito una llave
		l2.accionPersonajePuerta(planta1);
		
		//quito otra llave
		l2.accionPersonajePuerta(planta1);
		
		
		// Solo comprueba el el metodo funciona correctamente
		// en realidad como precondicion tendriamos que solo se puede
		// escapar si esta en la sala final
		assertTrue(l2.personajeSalirDePlanta(planta1)==true);
	
	}
}
