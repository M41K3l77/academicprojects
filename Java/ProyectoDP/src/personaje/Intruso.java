package personaje;

import java.util.List;

import edificio.Llave;
import edificio.Planta;
import edificio.Puerta;
//import edificio.Sala;
import estructuras.Lista;
import estructuras.Pila;
import excepciones.ExceptionCerraduraNoValida;
import excepciones.ExceptionCombinacionDeCerraduraIncorrecta;
import direccion.Direccion;

/**
* Implementacion de los metodos de la clase Intruso.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Intruso extends Personaje{
	
	/**
	 * Metodo constructor de Intruso. Al estar todos los atributos en personaje
	 * solo hay que llamar al constructor de Personaje (super clase).
	 */
	public Intruso(){
		super();		
	}
	
	/**
	 * Constructor parametrizado de Intruso.
	 * Pre: las instancias nombre, marca, turno, salaActual, Llaves
	 * y direcciones deben estar creadas.
	 * Post: Crea un Intruso.
	 * @param nombre, nombre(String) del intruso.
	 * @param marca, marca(String) del intruso.
	 * @param turno, turno(int) de movimientos del intruso.
	 * @param salaActual, salaActual(int) en la que esta el intruso.
	 * @param Llaves, pila(Pila<Llave>) de llaves que tiene el intruso.
	 * @param direcciones, direcciones(List<Direccion>) del intruso.
	 */
	public Intruso(String nombre, String marca, int turno, int salaActual, Pila<Llave> Llaves, List<Direccion> direcciones){		
		super("Nombre", "&", 0, 0, Llaves, direcciones);		
	}
	
	/**
	 * Metodo Redefinido en el que el intruso suelta una llave en
	 * las salas pares que vaya visitando.
	 * Pre: la instancia planta debe de estar creada. 
	 * Post: Inserta una llave en el cesto de llaves de
	 * la sala si esa sala es par.
	 * @param planta, planta(Planta) en la que esta el personaje.
	 * Complejidad: O(1)
	 */
	public void accionPersonajeLlave(Planta planta){
		// fila y columna son las coordenadas de la sala en
		// la que esta el personaje
		int fila=0;
		int columna=0;
		fila=getSalaActual()/planta.getAncho();
		columna=getSalaActual()%planta.getAncho();
		// numSala es el numero de la sala en la que esta el personaje
		int numSala=planta.getAncho()*fila+columna;
		// si no esta vacia la pila de llaves del intruso y ademas el numero de sala es impar
		// pierde una llave
		if(!this.llaves.estaVacia() && numSala%2==0 ){
			// Añadimos la primera llave de la pila de llaves
			// del intruso al cesto de llaves
			planta.devolverSala(fila, columna).anadirLlaveEnCestoSala(llaves.getDatoCima());
			// borramos la llave de la pila de llaves del intruso
			this.llaves.sacarDato();
		}
	}	
	
	/**
	 * Metodo Redefinido en el que el intruso si esta en una sala con 
	 * puerta la cierra.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Se cambia la puerta de la planta por una nueva con nueva
	 * combinacion para la cerradura y nuevo llavero de la puerta.
	 * @param planta, planta(Planta) en la que esta el personaje.
	 * @throws ExceptionCerraduraNoValida 
	 * @throws ExceptionCombinacionDeCerraduraIncorrecta 
	 * Complejidad: O(1)
	 */
	public void accionPersonajePuerta(Planta planta) throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		// Comprobamos si hay puerta
		if(comprbarSiHayPuerta(planta)==true){
			// Si hay puerta se pone puerta nueva
			Puerta door=new Puerta();
			planta.setDoor(door);
			// Se crea e inserta combinacion, numero de llaves 30
			// quizas deberia ser un atributo.
			Lista<Llave> combinacion=new Lista<Llave>();
			// se crea la combinacion a partir del numero de llaves que tenga la planta
			// Ese numero es un atributo de planta
			planta.crearCombinacionDeCerradura(combinacion);
			// Combinacion es de entrada/salida, se inserta la
			// combinacion en la puerta.
			planta.insertarCombinacionEnCerradura(combinacion);
			planta.getDoor().setEstadoPuerta(1);
		}
	}
	
	/**
	 * Metodo redefinido para Intruso y que no escape de la planta.
	 * Pre: La instancia planta debe de estar creada.
	 * Post: Metodo redefinido para intruso y que no salga de la 
	 * planta.
	 * @return escapado, escapado(boolean) a false.
	 * Complejidad: O(1)
	 */
	public boolean personajeSalirDePlanta(Planta planta){
		boolean escapado=false;
		return escapado;
	}
	/**
	 * Metodo para Introducir en la lista la nueva sala a visitar teniendo
	 * en cuenta la sala de la que se viene.
	 * Como referencia se toma ENOS
	 * @param nuevoOrigen
	 * @param visitados
	 * @param planta
	 * Complejidad: O(1)
	 */
	private void enosIntruso(Integer nuevoOrigen, List<Integer> visitados,Planta planta)
	{
		if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+1, nuevoOrigen)){//este
			visitados.add(nuevoOrigen+1);			
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-planta.getAncho(), nuevoOrigen)){//norte
			visitados.add(nuevoOrigen-planta.getAncho());
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-1, nuevoOrigen)){//oeste
			visitados.add(nuevoOrigen-1);
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+planta.getAncho(), nuevoOrigen)){//sur
			visitados.add(nuevoOrigen+planta.getAncho());
		}
	}
	/**
	 * Metodo para Introducir en la lista la nueva sala a visitar teniendo en cuenta la sala de la que se viene
	 * Como referencia se toma OSEM
	 * @param nuevoOrigen
	 * @param visitados
	 * @param planta
	 * Complejidad: O(1)
	 */
	private void osenIntruso(Integer nuevoOrigen, List<Integer> visitados,
			Planta planta) {
		if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-1, nuevoOrigen)){//oeste
			visitados.add(nuevoOrigen-1);			
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+planta.getAncho(), nuevoOrigen)){//sur
			visitados.add(nuevoOrigen+planta.getAncho());
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+1, nuevoOrigen)){//este
			visitados.add(nuevoOrigen+1);
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-planta.getAncho(), nuevoOrigen)){//norte
			visitados.add(nuevoOrigen-planta.getAncho());
		}
	}
	/**
	 * Metodo para Introducir en la lista la nueva sala a visitar teniendo en cuenta la sala de la que se viene
	 * Como referencia se toma SENO
	 * @param nuevoOrigen
	 * @param visitados
	 * @param planta
	 * Complejidad: O(1)
	 */
	private void senoIntruso(Integer nuevoOrigen, List<Integer> visitados,
			Planta planta) {
		if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+planta.getAncho(), nuevoOrigen)){//sur
			visitados.add(nuevoOrigen+planta.getAncho());			
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+1, nuevoOrigen)){//este
			visitados.add(nuevoOrigen+1);
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-planta.getAncho(), nuevoOrigen)){//norte
			visitados.add(nuevoOrigen-planta.getAncho());
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-1, nuevoOrigen)){//oeste
			visitados.add(nuevoOrigen-1);
		}
		
	}
	/**
	 * Metodo para Introducir en la lista la nueva sala a visitar teniendo en cuenta la sala de la que se viene
	 * Como referencia se toma NOSE
	 * @param nuevoOrigen
	 * @param visitados
	 * @param planta
	 * Complejidad: O(1)
	 */
	private void noseIntruso(Integer nuevoOrigen, List<Integer> visitados,
			Planta planta) {
		if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-planta.getAncho(), nuevoOrigen)){//norte
			visitados.add(nuevoOrigen-planta.getAncho());		
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen-1, nuevoOrigen)){//oeste
			visitados.add(nuevoOrigen-1);
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+planta.getAncho(), nuevoOrigen)){//sur
			visitados.add(nuevoOrigen+planta.getAncho());
		}else if(planta.getLaberinto().getGrafo().adyacente(nuevoOrigen+1, nuevoOrigen)){//este
			visitados.add(nuevoOrigen+1);
		}
		
		
	}
	/**
	 * Metodo para el calculo de un camino parcial, dicho camino se guarda en visitados
	 * desde un origen dado a un destino dado.
	 * @param origen
	 * @param destino
	 * @param visitados
	 * @param planta
	 * Complejidad: O(n)
	 */
	private void movimientoParcialIntruso(Integer origen, Integer destino,
			List<Integer> visitados,Planta planta) {
		
		int i=visitados.size()-2;//para que empieze en la posicion de comienzo para 
		//obtener direccion
		while(origen!=destino){
			if(visitados.get(i)-visitados.get(i+1)==planta.getAncho()){ //comprobamos si viene del sur
				enosIntruso(visitados.get(i+1),visitados,planta);
			}else if(visitados.get(i)-visitados.get(i+1)==-planta.getAncho()){ //comprobamos norte
				osenIntruso(visitados.get(i+1),visitados,planta);
			}else if(visitados.get(i)-visitados.get(i+1)==1){//comprobamos si viene del este
				noseIntruso(visitados.get(i+1),visitados,planta);
			}else if(visitados.get(i)-visitados.get(i+1)==-1){//oeste
				senoIntruso(visitados.get(i+1),visitados,planta);
			}
			//System.out.println(visitados.toString());
			origen=visitados.get(visitados.size()-1);
			//System.out.println(origen);
			i++;
		}		
	}	

	/**
	 * Metodo para calcular los movimientos del intruso, en la
	 * lista de visitados tendremos las salas que debera ir visitando
	 * en su recorrido por el laberinto.
	 * @param planta
	 * @param origen
	 * @param visitados
	 * Complejidad: O(n)
	 */
	public void movimientoIntruso(Planta planta, Integer origen, List<Integer> visitados){
		// Los diferentes destino son las salas a las que tendra que
		// ir llegando en cada recorrido parcial
		Integer destino1=planta.getAncho()-planta.getAncho();
		Integer destino2=(planta.getAlto()*planta.getAncho()-planta.getAncho());
		Integer destino3=(planta.getAlto()*planta.getAncho()-1);
		// se añade la sala sur para que al empezar ya tenga un sentido de direccion
		// ese sentidi es ficticio y posteriormente se borrara para que no se tenga
		// en cuenta
		visitados.add(origen+planta.getAncho());
		// Se añade origen y asi ya tenemos un sentido
		// el personaje viene del sur(es ficticio, no importa que pueda o no pueda)
		visitados.add(origen);
		// Las cuatro llamadas para recorrer el laberinto completo
		// en el que se van intercambiando los destinos por origenes
		movimientoParcialIntruso(origen,destino1,visitados,planta);		
		movimientoParcialIntruso(destino1,destino2,visitados,planta);
		movimientoParcialIntruso(destino2,destino3,visitados,planta);
		movimientoParcialIntruso(destino3,origen,visitados,planta);
		// La primera sala que se añadio se borra ya que solo se usa
		// como referencia para la direccion inicial del personaje
		visitados.remove(0);		
	}
}
