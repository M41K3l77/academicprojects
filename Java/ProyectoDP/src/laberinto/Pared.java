package laberinto;

/**
* Implementacion de los metodos de la clase Pared.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC2
*/
public class Pared{
	
	/** Clase pared: tiene dos enteros que son las salas compuestas por la pared*/
	private int nodo1;
	private int nodo2;
	
	/**
	 * Constructor parametrizado de la clase Pared
	 * @param nodo1
	 * @param nodo2
	 */
	public Pared (int nodo1, int nodo2){
		this.nodo1=nodo1;
		this.nodo2=nodo2;
	}	
	
	/**
	 * Metodo que nos devuelve un numero que representa una sala.
	 * Pre: La instancia pared debe estar creada.
	 * Post: Devuelve un numero que representa una sala contigua a la pared.
	 * @return nodo1
	 * Complejidad: O(1)
	 */
	public int getNodo1() {
		return nodo1;
	}

	/**
	 * Metodo para insertar un numero de sala(int) a la pared.
	 * Pre: Las instancia Pared debe estar creada.
	 * Post: Añade un numero de sala a la pared.
	 * Complejidad: O(1)
	 * @param nodo1
	 */
	public void setNodo1(int nodo1) {
		this.nodo1 = nodo1;
	}

	/**
	 * Metodo que nos devuelve un numero que representa una sala.
	 * Pre: La instancia pared debe estar creada.
	 * Post: Devuelve un numero que representa una sala contigua a la pared.
	 * @return nodo2
	 * Complejidad: O(1)
	 */
	public int getNodo2() {
		return nodo2;
	}

	/**
	 * Metodo para insertar un numero de sala(int) a la pared.
	 * Pre: Las instancia Pared debe estar creada.
	 * Post: Añade un numero de sala a la pared.
	 * Complejidad: O(1)
	 * @param nodo2
	 */
	public void setNodo2(int nodo2) {
		this.nodo2 = nodo2;
	}
	
	/**
	 * Metodo para comparar dos Paredes.
	 */
	@Override
	public boolean equals(Object dato2){
    	if (this == dato2) 
    		return true;
    	// Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
    	if (!(dato2 instanceof Pared))
    		return false;
    	// Hacemos un casting... se ve en mas detalle en sesiones de herencia
    	Pared datoAux = (Pared) dato2;
		return (this.nodo1 == datoAux.nodo1 && this.nodo2 == datoAux.nodo2);
	}
	
	public String toString(){
		return nodo1+"-"+nodo2+" " ;
	}	
}

