package edificio;

/**
* Implementacion de los metodos de la clase Llave.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Llave implements Comparable<Llave>{
	/** Codigo de la llave*/
	private int codigoLlave;
	
	/**
	 * Constructor por defecto de Llave. Inicializa Llave.
	 */
	public Llave(){
		this.codigoLlave=0;
		
	}
	/**
	 * Constructor parametrizado de Llave
	 * @param codigoLlave, codigoLlave(int) de la llave.
	 * Complejidad: O(1)
	 */
	public Llave(int codigoLlave){
		this.codigoLlave=codigoLlave;		
	}

	/**
	 * Metodo para insertar un codigo de llave(int) a la llave.
	 * Pre: Las instancia Llave y codigoLlave deben estar creadas.
	 * Post: Añade un codigo de llave a la llave.
	 * @param codigoLlave, codigoLlave(int) que se añade a la llave.
	 * Complejidad: O(1)
	 */
	public void setCodigoLlave(int codigoLlave) {
		this.codigoLlave = codigoLlave;
	}
	
	/**
	 * Metodo que nos da el codigo de la llave.
	 * Pre: La instancia Llave debe estar creada.
	 * Post: Devuelve el codigo de la llave.
	 * @return codigoLlave, codigoLlave(int) que tiene la llave.
	 * Complejidad: O(1)
	 */
	public int getCodigoLlave() {
		return codigoLlave;
	}
	
	/**
	 * Metodo para comparar dos llaves, se compara por su codigo de llave
	 * que es un entero.
	 * Pre: el parametro llave debe de estar inicializado.
	 * Post: Compara 2 llaves por su codigo de llave.
	 * @param llave, llave(Llave) que queremos comparar.
	 * @return 0 si son iguales , -1 menor y 1 mayor.
	 * Complejidad: O(1)
	 */
	@Override
	public int compareTo(Llave llave){
		if (this.codigoLlave == llave.getCodigoLlave())
			return 0;
		if (this.codigoLlave < llave.getCodigoLlave())
			return -1;
		return 1;
	}
	
	/**
	 * Metodo que compara dos llaves pero solo nos indica si son
	 * iguales o diferentes.
	 * Pre: El objeto llave debe de estar inicializado.
	 * Post: Me devuelve true cuando los dos objetos son iguales, es decir, que 
	 * coincidan sus codigos de llave.
	 * @param llave, llave(Object) que es de tipo objeto. Es la llave que
	 * queremos comparar.
	 * @return boolean que nos devuelve true si son
	 * iguales por el codigo de llave.
	 * Complejidad: O(1)
	 */
	@Override
	public boolean equals(Object llave){
    	if (this == llave) 
    		return true;
    	// Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
    	if (!(llave instanceof Llave))
    		return false;
    	// Hacemos un casting
    	Llave llv = (Llave) llave;    //comparamos si dos llaves son iguales x el codigo de llave
		return this.codigoLlave == llv.getCodigoLlave();
	}
	
	/**
	 * Metodo para mostrar una llave.
	 * Pre: la instancia llave debe estar inicializado.
	 * post: Convierte el atributo entero codigoLlave en un String.
	 * @return "key "+codigoLlave, "key "+codigoLlave(String) de la llave.
	 * Complejidad: O(1)
	 */
	public String toString(){
		return ""+codigoLlave;
	}
}
