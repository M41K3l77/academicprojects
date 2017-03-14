package edificio;

import java.util.LinkedList;
import java.util.List;
import estructuras.*;

/**
* Implementacion de los metodos de la clase Puerta.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Puerta {
	
	/** estado: 0 no configurada, 1 cerrada, 2 abierta*/
	private int estado;
	/** cerradura de la puerta*/
	private Arbol<Llave> cerradura;
	/** Llavero de llaves comprobadas en la cerradura*/
	private Arbol<Llave> llavero;	
	
	/**
	 * Constructor por defecto de la clase. Inicializa una puerta.
	 */
	public Puerta(){
	    this.estado=0;
	    this.cerradura=new Arbol<Llave>();
	    this.llavero=new Arbol<Llave>();
	}
	
	/**
	 * Constructor parametrizado de la clase puerta.
	 * Pre: Las instancias estado(int), cerradura(Arbol<Llave>) y
	 * llavero(Arbol<Llave>) deben estar creadas.
	 * Post: Inicializa una puerta.
	 * @param estado, estado(int) que le damos a la puerta.
	 * @param cerradura, cerradura(Arbol<Llave>) que le damos a la puerta.
	 * @param llavero, llavero(Arbol<Llave>) que le damos a la puerta.
	 * Complejidad: O(1)
	 */
	public Puerta(int estado, Arbol<Llave> cerradura, Arbol<Llave> llavero){
	    this.estado=estado;
	    this.cerradura=cerradura;
	    this.llavero=llavero;
	}	
	
	/**
	 * Metodo que da el estado(0, 1 y 2) a la puerta.
	 * Pre: las instancia puerta y estado deben de estar creadas.
	 * post:modifica el estado de la puerta 0 1 2.
	 * @param estado, estado(int) que le damos a la puerta.
	 * Complejidad: O(1)
	 */
	public void setEstadoPuerta(int estado){
	    this.estado=estado;
	}	
	
	/**
	 * Metodo que nos dice el estado en el que se encuentra la puerta.
	 * Pre: La instancia Puerta debe estar creada.
	 * post: Devuelve el estado de la puerta.
	 * @return estado, estado(int) actual de la puerta.
	 * Complejidad: O(1)
	 */
	public int getEstadoPuerta(){
	    return this.estado;
	}
	
	/**
	 * Metodo que nos da la cerradura(Arbol<Llave>) de la puerta.
	 * Pre: La instancia Puerta debe estar creada.
	 * Post: Devuelve la cerradura de la puerta.
	 * @return cerradura, cerradura(Arbol<Llave>) de la puerta.
	 * Complejidad: O(1)
	 */
	public Arbol<Llave> getCerradura(){
	    return cerradura;
	}
	
	/**
	 * Metodo que nos da el Llavero(Arbol<Llave>) de la puerta en el
	 * que se guardan las llaves que ya se han usado en la cerradura.
	 * Pre: La instancia Puerta debe estar creada.
	 * Post: Devuelve el llavero de la puerta.
	 * @return llavero, llavero(Arbol<Llave>) de la puerta.
	 * Complejidad: O(1)
	 */
	public Arbol<Llave> getLlavero(){
	    return llavero;
	}
	
	/**
	 * Metodo que inserta una llave en la cerradura(como parte de la combinacion).
	 * Pre: las instancias Puerta y Llave deben estar creadas, la
	 * cerradura no admitira llaves duplicadas.
	 * Post: Añade llave al arbol de la cerradura si se puede.
	 * @param llave, llave(Llave) que formara parte de la combinacion
	 * de la cerradura.
	 * @return insertado, insertado(boolean) true si se ha insertado correctamente.
	 * Complejidad: O(1)
	 */
	public boolean addLlaveCerradura(Llave llave){
		boolean insertado=false;
		if(cerradura.insertar(llave)==true){
			insertado=true;
		}	    
	    return insertado;
	}
	
	/**
	 * Metodo que borra una llave de la cerradura(combinacion).
	 * Pre: las instancias puerta y llave deben estar creadas.
	 * Post: Borra llave del arbol de la cerradura.
	 * @param llave, llave(Llave) que forma parte de la cerradura(combinacion).
	 * Complejidad: O(1)
	 */
	public void borrarLlaveCerradura(Llave llave){
	    cerradura.borrar(llave);
	}
	
	/**
	 * Metodo que inserta una llave en el llavero(como llave ya probada en la cerradura).
	 * Pre: las instancias Puerta y Llave deben estar creadas, la
	 * llavero no admitira llaves duplicadas.
	 * Post: Añade llave al arbol del llavero si se puede.
	 * @param llave, llave(Llave) que formara parte de las llaves ya probadas.
	 * @return insertado, insertado(boolean) true si se ha insertado correctamente.
	 * Complejidad: O(1)
	 */
	public boolean addLlaveLlavero(Llave llave){
		boolean insertado=false;
		if(llavero.insertar(llave)==true){
			insertado=true;
		}	    
	    return insertado;
	}
	
	/**
	 * Metodo que borra una llave del llavero(llaves ya probadas).
	 * Pre: las instancias puerta y llave deben estar creadas.
	 * Post: Borra llave del arbol del llavero.
	 * @param llave, llave(Llave) que forma parte del llavero(llaves ya probadas).
	 * Complejidad: O(1)
	 */
	public void borrarLlaveLlavero(Llave llave){
	    llavero.borrar(llave);
	}
	
	/**
	 * Metodo que comprueba si una llave que va a usar un personaje en la
	 * cerradura ya ha sido usada buscandola en el llavero de la puerta.
	 * Pre: las instancias Puerta y key deben estar creadas.
	 * post: Indica si key se encuentra en el llavero de la puerta.
	 * @param key, key(Llave) a buscar en el llavero de la puerta.
	 * @return encontrada, encontrada(boolean) true si se encuentra
	 * key en el llavero.
	 * Complejidad: O(log(n))
	 */
	public boolean buscarLlaveEnLlavero(Llave key){
		boolean encontrada=false;
		if(this.getLlavero().pertenece(key)==true){
			encontrada=true;
		}
		return encontrada;
	}	
	
	/**
	 * Metodo que devuelve el estado de la puerta de la planta.
	 * Pre: La instancia Puerta debe estar creada.
	 * Post: Devuelve el estado de la puerta en un string.
	 * Complejidad: O(1)
	 * @return estadoPuerta
	 */
	public String mostrarEstadoPuerta(){
		String estadoPuerta="Sin estado";
		if(this.estado==0){
			estadoPuerta="no configurada";
		}else if(this.estado==1){
			estadoPuerta="cerrada";
		}else{
			estadoPuerta="abierta";
		}
		return estadoPuerta;
	}
	
	/**
	 * Metodo que muestra la cerradura y el llavero de la puerta.
	 * Pre: La instancia Puerta debe estar creada.
	 * Post: Muestra llavero de la puerta.
	 * Complejidad: O(n)
	 */
	public void pintarLlavero(){
		List <Llave>llaveroAux=new LinkedList<Llave>();
		// Copia en orden las llaves del llavero a una lista para
		// mostrarlo posteriormente. realmente se podia haber utilizado el inOnden del arbol
		// pero se hace este paso intermedio para que el log de salida se ajuste mejor
		// al solicitado.
		this.llavero.copiarInOrden(llaveroAux);
		int i=0;
		while (i<llaveroAux.size()) {
			if(i<llaveroAux.size()-1){
				System.out.print(llaveroAux.get(i)+" ");
			}else{
				System.out.print(llaveroAux.get(i));
			}
			i++;
		}		
	}
}
