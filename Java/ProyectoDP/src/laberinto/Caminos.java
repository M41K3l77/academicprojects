package laberinto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import edificio.Planta;
import edificio.Sala;

/**
* Implementacion de los metodos de la clase Caminos.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega: EC2
*/
public class Caminos {	

	/** Numero de salas de la planta*/
	private int numSalas;
	/** lista de las salas de la planta en las que tendremos las frecuencias*/
	private List<Sala> salas=new ArrayList<Sala>();
	/** Conjunto de las listas de las salas de la planta
	 *  Cada vez que se repita una sala en la sala se incrementara la frecuencia
	 *  y asi sacar salas mas frecuentadas*/
	private Set<List<Integer>> conjuntoSalas= new TreeSet<List<Integer>>(new ComparadorList());
	
	/**
	 * Metodo constructor parametrizado de la clase.
	 * @param numSalas
	 * Complejidad: O(n)
	 */
	public Caminos(Planta planta){
		// El numero de salas de la lista viene dada por el alto y ancho de la planta
		
		this.numSalas=planta.getAlto()*planta.getAncho();
		for(int i=0;i<this.numSalas;i++){
			// Se crean las referencias a las salas de la planta en la lista salas
			Sala sala=null;
			this.salas.add(sala);
		}
	}
	/**
	 * Metodo que devuelve el conjunto de salas.
	 * @return conjuntoSalas
	 * Complejidad: O(1)
	 */
	public Set<List<Integer>> getConjuntoSalas() {
		return conjuntoSalas;
	}
		
	/**
	 * Metodo que añade un camino al conjunto de caminos.		 
	 * Pre: la instancia camino debe de estar creada.
	 * Post: Inserta un camino en el conjunto de caminos.
	 * @param camino, (List<Integer>) que insertamos en el conjuntoSalas.
	 * Complejidad: O(1)
	 */
	public void addCamino(List<Integer> camino){		
		// Añade un camino a caminos
		this.conjuntoSalas.add(camino);
	}
	
	/**
	 * Metodo que recorre el conjunto de salas y aumenta la frecuencia de la sala cada vez
	 * que se encuentra el identificador de la sala en los diferentes arrays de caminos
	 * la lista de las salas queda ordenada segun frecuencia de paso por las salas.		 
	 * Pre: la instancia Caminos y planta deben de estar creadas.
	 * Post: La lista de salas queda ordenada segun la frecuencia de paso por
	 * las mismas de mayor a menor.
	 * @param planta, (Planta) que tenemos en la etacion.
	 * Complejidad: O(n2)
	 */
	public void contarSalasFrecuentes(Planta planta){
		// Cada referencia de la lista salas apunta a una sala de la planta
		int l=0;
		int m=0;
		for(int k=0;k<this.salas.size();k++){
			l=k/(planta.getAncho());
			m=k%(planta.getAncho());
			this.salas.set(k, planta.devolverSala(l, m));
		}
		
		// Se recorre el conjunto de salas y se incrementa la frecuencia de
		// la cada vez que aparezca la sala en los caminos.
		Iterator<List<Integer>> i = this.conjuntoSalas.iterator();
		List<Integer> auxCamino=null;
		while (i.hasNext()) {
			auxCamino=i.next();
			Iterator<Integer> j = auxCamino.iterator();
			Integer dato=null;
			while(j.hasNext()){
				dato=j.next();
				this.salas.get(dato).incFrecuencia();
			}
		}
		Comparator <Sala> r = Collections.reverseOrder();
		// Ordenacion de la lista descendentemente por la frecuencia
		Collections.sort(this.salas, r);
	}
	
	/**
	 * Metodo que muestra la lista de salas segun frecuencia de paso por las salas.
	 */
	public void mostrarSalasMasFrecuentes(){
		// recorrer lista de salas ya ordenadas por frecuencia
		Sala auxSala=null;
		Iterator<Sala> k= this.salas.iterator();
		System.out.print("Salas mas frecuentadas: ");
		while (k.hasNext()){
			auxSala=k.next();
			System.out.print(auxSala.getNumeroSala()+" ");
		}
		System.out.println();
	}
	
	/**
	 * Metodo que nos da el numero de salas de la planta.
	 * Pre: La instancia Caminos debe estar creada.
	 * Post: Devuelve el numero de salas.
	 * @return numSalas.
	 * Complejidad: O(1)
	 */
	public int getNumsalas() {
		return numSalas;
	}

	/**
	 * Metodo para insertar una lista de salas en Caminos.
	 * Pre: Las instancia Caminos debe estar creada.
	 * Post: Añade un lista de salas a Caminos.
	 * @param salas, Salas(List<Sala>) que se añaden a Caminos.
	 * Complejidad: O(1)
	 */
	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	/**
	 * Metodo que nos da la lista de salas.
	 * Pre: La instancia Caminos debe estar creada.
	 * Post: Devuelve lista de salas.
	 * @return salas.
	 * Complejidad: O(1)
	 */
	public List<Sala> getSalas() {
		return salas;
	}
}
