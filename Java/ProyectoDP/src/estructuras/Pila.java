package estructuras;



/**
* Implementacion de los metodos de la clase generica Pila.
*
* @version 2.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Pila <P> {
	
	/** Puntero a la cima de la pila*/
	private Nodo cima;
	/** entero que da la profundidad de la pila*/
	private int size;
	
	
    private class Nodo {
    	/** Dato almacenado en cada nodo */
        private P dato;// 
    	/** Enlace al siguiente elemento */
        private Nodo siguiente;

        /**
         * /**
         * Creacion del nodo.
         * @param dato, es el dato que insertamos en el nodo.
         */
        Nodo (P dato) {
            this.dato = dato;// 
            this.siguiente = null;
        }
    }//class Nodo
    
	/**
	 * Crea una pila vacia de longitud cero.
	 * Metodo constructor por defecto de la clase Pila.
	 */
	public Pila() {
		cima = null;
		size=0;
	}

	/**
	 * Metodo constructor parametrizado de la clase Pila
	 *
	 * @param dato es el nuevo elemento en la pila
	 */
	public Pila(P dato) {
		Nodo nodo = new Nodo(dato);
		nodo.siguiente = cima;
		cima = nodo;
		size=1;
	}
	
	/**
	 * Metodo que devuelve el elemento en la cima de la pila.
	 *
	 * @return cima.dato, la cima de la pila
	 */
	public P getDatoCima(){
		return cima.dato;
	}
	
	/**
	 * Metodo que devuelve la profundidad de la pila.
	 *
	 * @return size, longitud de la pila
	 */
	public int getSize(){
		return size;
	}

	/**
	 * Metodo para comprobar si la pila esta vacia o no.
	 *
	 * @return (cima==null), true si esta vacia o false en caso contrario.
	 */
	public boolean estaVacia (){
		return (cima==null);
	}
	

	/**
	 * Metodo que permite insertar un dato.
	 *
	 * @param dato, valor que se va a insertar.
	 */
	public void insertarDato(P dato) {
		Nodo nodo = new Nodo(dato);
		nodo.siguiente = cima;
		cima = nodo;
		size++;
	}


	/**
	* Elimina un dato de la pila. Se elimina el dato que esta
	*  en la cima y decrementa la longitud de la pila.
	*/
	public void sacarDato() {
		Nodo puntNodo;
		if (!estaVacia()){	
			puntNodo = cima;
			cima = cima.siguiente;
			puntNodo.siguiente=null;
			size--;
		}
	}
}
