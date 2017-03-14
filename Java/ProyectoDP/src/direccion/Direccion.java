package direccion;

/**
* Implementacion de los metodos de la clase enumerada Direccion.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public enum Direccion {	
	
	/** Norte*/
	N,
	/** Sur*/
	S,
	/** Este*/
	E,
	/** Oeste*/
	O;
		
	/**
	 * Metodo toString para mostrar la direccion.
	 */
	@Override
	public String toString(){
		return this.name();
	}
}
