package personajeTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import personaje.Intruso;
import direccion.Direccion;
import edificio.Llave;
import edificio.Planta;
import estructuras.Lista;
import estructuras.Pila;
import excepciones.ExceptionCerraduraNoValida;

public class IntrusoTest {
	//int numLlaves = 30;
	List<Direccion> direcciones=new ArrayList<Direccion>();
	Intruso intruso1=null;
	Intruso intruso2=null;	
	Planta planta=null;
	
	@Before
	public void SetUpPlantayPersonaje() throws ExceptionCerraduraNoValida{
		System.out.println("Setupplanta");
		//int alturaArbol = 1;
		planta=Planta.obtenerInstancia(6, 6, 0, 35, 0, 5, 30);
		planta.setTurnoAccion(1);
		Llave llave1=new Llave(1);
		Llave llave2=new Llave(2);
		Lista<Llave> combinacion=new Lista<Llave>();
		combinacion.insertDato(llave1);
		combinacion.insertDato(llave2);
		
		//insertamos la combinacion en la puerta de la cerradura
		planta.insertarCombinacionEnCerradura(combinacion);
		// una vez configurada la puerta la cerramos
		//planta.getDoor().setEstadoPuerta(1);
		//planta.setCondicionAperturaPuertaNivelArbol(alturaArbol);
				
		System.out.println("setuppersonaje");
		Pila<Llave> llavesper1=new Pila<Llave>();
		llavesper1.insertarDato(llave1);
		llavesper1.insertarDato(llave2);
		//creo un intruso y le pongo nombre, marca, sala actual,turno
		intruso1=new Intruso();
		intruso1.setNombre("intruso1");
		intruso1.setMarca("I");
		intruso1.setSalaActual(5);
		intruso1.setTurno(1);
		//creo otro personaje
		intruso2=new Intruso();
		intruso2.setMarca("i");
		intruso2.setLLaves(llavesper1);
		
		
//		llavesper1.insertarDato(llave1);
//		llavesper1.insertarDato(llave2);
		//le asigno unas llaves al lider1
		intruso1.setLLaves(llavesper1);
		Direccion[] dirIntruso1={Direccion.O, Direccion.O, Direccion.O, Direccion.O,
				Direccion.O, Direccion.E, Direccion.E, Direccion.E, Direccion.E,
				Direccion.N, Direccion.N, Direccion.E, Direccion.N, Direccion.N,
				Direccion.O, Direccion.N, Direccion.O, Direccion.S, Direccion.O,
				Direccion.S, Direccion.O, Direccion.O, Direccion.N, Direccion.N,
				Direccion.S, Direccion.S, Direccion.E, Direccion.E, Direccion.N,
				Direccion.E, Direccion.N, Direccion.E, Direccion.S, Direccion.E,
				Direccion.N, Direccion.S, Direccion.S, Direccion.S, Direccion.O,
				Direccion.S, Direccion.S, Direccion.E};
		direcciones.addAll(Arrays.asList(dirIntruso1));
		// y unas direcciones que tomar
		intruso1.setdirecciones(direcciones);
		
	}
	@Test
	public void testMoverse(){
		System.out.println("Testmoverse");
		//añado al personaje
		planta.anadirPersonajePlanta(intruso1,5);
		assertTrue(planta.getTurnoAccion()==intruso1.getTurno());
		
		assertTrue(intruso1.getTurno()==1);
		assertTrue(intruso1.getSalaActual()==5);
		//VEO QUE HABIA 5 LLAVES ANTES DE MOVERME Y RECOGERLAS
		assertTrue(planta.devolverSala(0, 5).getCestoLlaves().size()==0);
		planta.setTurnoAccion(1);
		intruso1.moverse(planta);
		
		//COMPRUEBO QUE SE HA MOVIDO COMPROBANDO SUS TURNOS
		assertTrue(intruso1.getTurno()==2);
		//ya no esta en la 0
		assertFalse(intruso1.getSalaActual()==0);
		//y esta en la seis por que tenia la direccion sur
		assertTrue(intruso1.getSalaActual()==4);
		//VEMOS QUE LA SALA QUEDA VACIA
		assertTrue(planta.devolverSala(0, 5).cuantosPersonajes()==0);		
	}
}
