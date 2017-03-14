package personaje;

import java.util.List;
import edificio.Llave;
import edificio.Planta;
import estructuras.Pila;
import direccion.Direccion;

/**
* Implementacion de los metodos de la clase Trabajador.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Trabajador extends Personaje{
	
	/**
	 * Metodo constructor de Trabajador. Al estar todos los atributos en personaje
	 * solo hay que llamar al constructor de Personaje (super clase).
	 */	
	public Trabajador(){
		super();		
	}
	
	/**
	 * Constructor parametrizado de Trabajador.
	 * Pre: las instancias nombre, marca, turno, salaActual, Llaves
	 * y direcciones deben estar creadas.
	 * Post: Crea un Trabajador.
	 * @param nombre, nombre(String) del trabajador.
	 * @param marca, marca(String) del trabajador.
	 * @param turno, turno(int) de movimientos del trabajador.
	 * @param salaActual, salaActual(int) en la que esta el trabajador.
	 * @param Llaves, pilaPila<Llave> de llaves que tiene el trabajador.
	 * @param direcciones, direcciones(List<Direccion>) del Trabajador.
	 */
	public Trabajador(String nombre, String marca, int turno, int salaActual, Pila<Llave> Llaves, List<Direccion> direcciones){		
		super("Nombre", "T", 0, 0, Llaves,direcciones);		
	}
	
	/**
	 * Metodo que devuelve el camino minimo del trabajador en
	 * una lista de enteros(numero de las salas que tiene que recorrer)
	 * @param planta
	 * @param caminoTrabajador
	 * @return camino
	 * Complejidad: O(n)
	 */
	public boolean movimientoTrabajador(Planta planta, List <Integer> caminoTrabajador){
		boolean camino=false;
		if(planta.getLaberinto().getGrafo().caminoMinimo(planta.getSalaInnicio(), planta.getSalaFinal(), caminoTrabajador)){
			camino=true;
		}		
		return camino;		
	}
	
	/** 
	 * Pre: la instancia planta debe de estar creada. 
	 * Post: Quita una llave en el cesto de llaves de
	 * la sala 
	 * @param planta, planta(Planta) en la que esta el personaje.
	 * Complejidad: O(1)
	 */
	public void accionPersonajeLlave(Planta planta){
		// Ver numero de sala del personaje
		int fila=0;
		int columna=0;
		fila=getSalaActual()/planta.getAncho();
		columna=getSalaActual()%planta.getAncho();
		// Con el numero de sala si hay llaves en la sala recoger una
		// Comprobamos que tiene llaves
		if(!planta.devolverSala(fila, columna).getCestoLlaves().estaVacia()){
			// Añadimos la primera llave del cesto de llaves de la sala a
			// la pila de llaves del personaje
			this.llaves.insertarDato(planta.devolverSala(fila, columna).devolverPrimeraLLaveSala());
			// Eliminamos la llave del cesto de llaves
			planta.devolverSala(fila, columna).eliminarllave();			
		}
	}
	
	/**
	 * Metodo que realiza la accion del personaje en la puerta. Se comprueba
	 * si hay puerta, si la hay comprobamos si la llavea a utilizar a sido usada
	 * y si ha sido usada se tira si no se prueba. Si se cumplen las condiciones
	 * de apertura de la puerta, se abre la puerta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Intenta abrir la puerta, si se abre la puerta el estado de la
	 * puerta pasa a valor 2 (puerta abierta).
	 * Complejidad: O(n)
	 */
	public void accionPersonajePuerta(Planta planta){
		// Comprobamos si hay puerta
		if(comprbarSiHayPuerta(planta)==true){
			// Comprobamos que el personaje tiene llaves
			if(!this.llaves.estaVacia()){
				Llave key;
				// cogemos la llave
				key=this.llaves.getDatoCima();
				// Comprobamos si la llave ha sido usada
				if(planta.getDoor().buscarLlaveEnLlavero(key)==true){
					// Si la llave ha sido probada se tira
					this.llaves.sacarDato();
				}else{// si no ha sido probada se inserta en llavero y cerradura
					if(planta.getDoor().addLlaveLlavero(key)==true){
						planta.getDoor().borrarLlaveCerradura(key);
						// se elimina la llave del personaje
						this.llaves.sacarDato();
					}
					// si se cumplen condiciones abrir puerta
					if(!planta.hayLlavesEnCerradura()){
						// Condiciones de apertura de la puerta
						if(planta.getDoor().getCerradura().profundidad()<planta.getCondicionAperturaPuertaNivelArbol()
								&& planta.getDoor().getCerradura().numeroNodosInternos()>=planta.getDoor().getCerradura().numeroHojas()){
							// Estado 2 de la cerradura es abierto
							planta.getDoor().setEstadoPuerta(2);						
						}
					}					
				}
			}
		}
	}
}
