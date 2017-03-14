package edificio;

import java.util.ArrayList;
import java.util.List;
import personaje.Personaje;
import estructuras.Lista;
import excepciones.*;

/**
* Implementacion de los metodos de la clase Estacion.
*
* @version 1.0
* @author
* <b> Alumnos Carols M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Estacion{
	
	private Planta planta;
	/** Lista con los personajes*/
	private List<Personaje> personajesIniciales;
	
	private static Estacion instancia=null;
	
	private Estacion(){		
		this.planta=null;
		personajesIniciales=new ArrayList<Personaje>();
	}
	public static Estacion obtenerInstancia(){
		if(instancia==null){//creo el objeto de tipo edificio
			  instancia=new Estacion();			  
		  }
		  return instancia;
	}

	/**
	 * 
	 * @param planta
	 */
	public void setPlanta(Planta planta) {
		this.planta = planta;
	}
	
	/**
	 * 
	 * @return
	 */
	public Planta getPlanta() {
		return planta;
	}	
	
	public List<Personaje> getPersonajesIniciales() {
		return personajesIniciales;
	}
	
	public void setPersonajesIniciales(List<Personaje> personajesIniciales) {
		this.personajesIniciales = personajesIniciales;
	}
	
	/**
	 * Metodo para realizar la simulacion de la planta.
	 */
	public void realizarSimulacion(){
		
		int maxNumTurnos=100;
		Lista<Llave> combinacion=new Lista<Llave>();
		try {
			planta.crearCombinacionDeCerradura(combinacion);
		} catch (ExceptionCombinacionDeCerraduraIncorrecta e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();			
		}
		try {
			planta.insertarCombinacionEnCerradura(combinacion);
		} catch (ExceptionCerraduraNoValida e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		int [] vectorllavesarepartir={0,1,1,2,3,3,4,5,5,6,7,7,8,9,9,10,11,11,
				12,13,13,14,15,15,16,17,17,18,19,19,20,21,21,22,23,23,24,25,
				25,26,27,27,28,29,29};
		
		planta.repartirLlaves(planta.getLaberinto().getCaminos().getSalas(), vectorllavesarepartir);
		
		planta.getDoor().setEstadoPuerta(1);
		System.out.println("(planta 0)");
		planta.getLaberinto().mostrarLaberintoVacio(planta);
		System.out.println("(distribucion llaves)");
		planta.pintarllaves();
		int i=0;
		while(i<getPersonajesIniciales().size()){
			System.out.print("(ruta:"+Estacion.obtenerInstancia().getPersonajesIniciales().get(i).getMarca()+": ");
			Estacion.obtenerInstancia().getPersonajesIniciales().get(i).mostrarDireccionesPersonaje();
			System.out.println(")");
			i++;
		}				
		 i=0;
		// El bucle se realiza mientras no lleguemos al maximo de turnos o
		// no escapen el lider y trabajador		 
		while(i<maxNumTurnos && planta.getSalaescapados().cuantosPersonajes()<2 ){
			System.out.println("("+"turno:"+ planta.getTurnoAccion()+")");
			System.out.println("(salaescapados:"+ this.getPlanta().getSalaescapados().getNumeroSala()+")");
			System.out.println("(planta:0"+":"+this.getPlanta().getSalaInnicio()+":"+this.getPlanta().getSalaFinal()+")");
			System.out.print("("+"puerta:"+ planta.getDoor().mostrarEstadoPuerta()+":"+this.planta.getCondicionAperturaPuertaNivelArbol()+":");
			planta.getDoor().pintarLlavero();
			System.out.println(")");
			planta.getLaberinto().mostrarLaberinto(planta);
			planta.pintarllaves();
			int j=0;
			while(j<getPersonajesIniciales().size()){
				Estacion.obtenerInstancia().getPersonajesIniciales().get(j).pintarPersonaje();				
				j++;
			}
			try {
				planta.plantaAccionPersonajes();
			} catch (ExceptionCerraduraNoValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExceptionCombinacionDeCerraduraIncorrecta e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;			
		}
		System.out.println("(fin de la simulacion)");
		System.out.println("(planta:0"+":"+this.getPlanta().getSalaInnicio()+":"+this.getPlanta().getSalaFinal()+")");
		System.out.print("("+"puerta:"+ planta.getDoor().mostrarEstadoPuerta()+":"+this.planta.getCondicionAperturaPuertaNivelArbol()+":");
		planta.getDoor().pintarLlavero();
		System.out.println(")");
		planta.getLaberinto().mostrarLaberinto(planta);
		planta.pintarllaves();
		System.out.println("(miembros escapados)");
		planta.getSalaescapados().pintarTodosPersonajesSala();
	}	
}
