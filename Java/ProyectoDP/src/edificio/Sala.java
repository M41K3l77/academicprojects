package edificio;

import java.util.LinkedList;
import java.util.Queue;
import estructuras.*;
import excepciones.ExceptionCerraduraNoValida;
import excepciones.ExceptionCombinacionDeCerraduraIncorrecta;
import personaje.Personaje;



public class Sala implements Comparable <Sala>{
	/** numero de sala */
	private int numeroSala;
	/** marca de la sala para el derribo de paredes */
	private int marcaSala;
	
	/** Lista de llaves de la sala (cesto de llaves)*/
	private Lista<Llave> cestoLlaves;
	
	/** Cola de personajes de la sala (personajes)*/
	private Queue<Personaje> personajes;
	
	/** Frecuencia por la que se pasa por la sala*/
	private Integer frecuencia=0;
	
	/**
	 * Constructor por defecto de la sala.
	 * Crea una sala con numero de sala cero, con un cesto de
	 * llaves(Lista de llaves) vacia y cola de personajes vacia.
	 */
	public Sala(){
		this.numeroSala=0;
		this.cestoLlaves=new Lista<Llave>();
		this.personajes=new LinkedList<Personaje>();
		this.marcaSala=0;
	}
	
	/**
	 * Constructor parametrizado de sala.
	 * Pre: La instnacia numerosala debe de estar creada
	 * Post: Crea una sala con un numero de sala ya dado,
	 * el cesto de llaves y personajes se crean vacios.
	 * @param numerosala, numerosala(int) que asignamos a la sala.
	 */
	public Sala(int numerosala){
		this.numeroSala=numerosala;
		this.cestoLlaves=new Lista<Llave>();
		this.personajes=new LinkedList<Personaje>();
		this.marcaSala=numerosala;
	}
	
	/**
	 * Metodo para añadir el numero de sala en sala.
	 * Pre: las instancias sala y numeroSala deben estar creadas.
	 * Post: Asigna a la sala un numero de sala dado.
	 * @param numeroSala, numeroSala(int) que le asignaremos a la sala.
	 * Complejidad: O(1)
	 */
	public void setNumeroSala(int numeroSala) {
		this.numeroSala = numeroSala;
	}
	
	/**
	 * Metodo que nos dice el numero que tiene asignado la sala.
	 * Pre: La instancia sala debe estar creada.
	 * Post: Devuelve el numero de la sala.
	 * @return numeroSala, numeroSala(int) numero de la sala.
	 * Complejidad: O(1)
	 */
	public int getNumeroSala() {
		return numeroSala;	
	}
	/**
	 * Metodo para añadir la marca a la sala.
	 * Pre: la instancia marcaSala debe de estar creada.
	 * Post: Asigna a la marca un numero dado
	 * @param marcaSala que le asignamo a la marca
	 * Complejidad: O(1)
	 */
	public void setMarcaSala(int marcaSala) {
		this.marcaSala = marcaSala;
	}
	/**
	 * Metodo que nos dice la marca de la sala que tiene asignado.
	 * Pre: la instancia debe de estar creada.
	 * Post: Devuende el numero de marca de la sala.
	 * @return marcaSala (la marca de la sala).
	 * Complejidad: O(1)
	 */
	public int getMarcaSala() {
		return marcaSala;
	}
	
	/**
	 * Metodo que devuelve el cesto de llaves.
	 * Pre: La sala debe estar creada.
	 * Post: cesto de llaves de la sala.
	 * @return cestoLlaves, cestoLlaves(Lista<Llave>) donde guardamos
	 *  las llaves de la sala.
	 *  Complejidad: O(1)
	 */
	public Lista<Llave> getCestoLlaves(){
		return cestoLlaves;
	}
	
	/**
	 * Metodo que añade un personaje a la sala.
	 * Pre: la instancia pers y sala deben estar creadas.
	 * Post: Añade un personaje a la cola de personajes de la sala.
	 * @param pers, pers(Personaje) que añadimos a la cola de
	 * personajes de la sala.
	 * @return insertado, insertado(boolean) si es true.
	 * Complejidad: O(1)
	 */
	public boolean anadirPersonaje(Personaje pers){
		boolean insertado=false;
		if(personajes.add(pers)){
			insertado=true;
		}
		return insertado;
	}
	
	/**
	 * Metodo que elimina un personaje a la sala.
	 * Pre: la instancia sala debe estar creada.
	 * Post: Elimina un personaje de la cola de personajes de la sala.
	 * Complejidad: O(1)
	 */
	public void eliminarPersonajeCola(){
		personajes.poll();
	}
	
	
	/**
	 * Post: Devuelve el numero de personajes que hay en la sala
	 * @return numero de personajes
	 * Complejidad: O(1)
	 */
	public  int cuantosPersonajes(){
		return this.personajes.size();
	}
	
	
	/**
	 * Metodo para obtener la primera llave disponible en la sala.
	 * Pre: La sala debe estar creada y el cesto de llaves no puede
	 *  estar vacio.
	 * Post: Primera llave del cesto de llaves de la sala.
	 * @return this.cestoLlaves.getDatoPi(), this.cestoLlaves.getDatoPi()(Llave)
	 * la primera de la sala.
	 * Complejidad: O(1)
	 */
	public Llave devolverPrimeraLLaveSala(){
		this.cestoLlaves.moveToFirst();
		return this.cestoLlaves.getDatoPi();
		
	}
	
	/**
	 * Metodo que añade una llave al cesto de llaves de la sala.
	 * Pre: la instancia sala y llave deben estar creadas.
	 * Post: Insertar una llave en el cesto de llaves.
	 * @param llave, llave(Llave) que insertaremos en el cesto de llaves.
	 * Complejidad: O(1)
	 */
	public void anadirLlaveEnCestoSala(Llave llave){
		cestoLlaves.insertarEnOrden(llave);
	}
	
	/**
	 * Metodo que elimina una llave del cesto de llaves. Eliminamos la primera
	 * porque se van cogiendo las llaves en orden.
	 * Pre: La instancia sala debe estar creada.
	 * post: Elimina una llave del cesto de llaves.	 *
	 * Complejidad: O(1) 
	 */
	public void eliminarllave(){
		cestoLlaves.moveToFirst();
		cestoLlaves.borrarNodo();
		
	}
	/**
	 * Metodo que devuelve el primer personaje del grupo.
	 * Pre: la instancia cola de personajes debe de estar creada
	 * Post: devuelve el primer personaje de la cola sin eliminarlo
	 * Complejidad: O(1)
	 * @return
	 */
	public Personaje primerpersonaje(){
		return personajes.peek();
	}
	/**
	 * Metodo que elimina el primer persnonaje de su grupo.
	 * Pre: la instancia cola de personajes debe de estar creada.
	 * Post: elimina el primer personaje
	 * Complejidad: O(1)
	 */
	public void eliminarPrimerPersonaje(){
		personajes.poll();		
	}
	/**
	 * Metodo que nos dice si hay personajes en la sala.
	 * Pre: La instacia sala debe estar creada.
	 * Post:
	 * @return hay, hay(booleano) true si hay personajes en la sala.
	 * Complejidad: O(1)
	 */
	public boolean haypersonajes(){
		boolean hay=false;
		if(!this.personajes.isEmpty()){
			hay=true;
		}
		return hay;
	}
	
	/**
	 * Metodo para pintar la marca del primer personaje que este en la sala.
	 * Pre: La instancia sala debe estar creada.
	 * Post: Muestra la marca del personaje.
	 * Complejidad: O(1)
	 */
	public void mostrarPersonajes(){
		if(this.personajes.size()>1){
			System.out.print(this.personajes.size());
		}else{
			System.out.print(this.personajes.peek().getMarca());
		}
		
	}
	
	/**
	 * Metodo que muestra las llaves del cesto de llaves de la sala.
	 * Pre: La instancia sala debe estar creada.
	 * Post: Se muestran las llaves de la sala.
	 * Complejidad: O(n)
	 *
	 */	
	public void mostrarLlaves(){
		
		if(!cestoLlaves.estaVacia()){
			System.out.print("("+"sala:"+ this.getNumeroSala()+ ": ");
			cestoLlaves.moveToFirst();
			while(!cestoLlaves.outOfList()){
				if(!cestoLlaves.atEnd()){
					System.out.print(cestoLlaves.getDatoPi()+ " ");
				}else{
					System.out.print(cestoLlaves.getDatoPi());
				}				
				cestoLlaves.moveForward();
			}
			cestoLlaves.moveToFirst();
			System.out.print(")");
			System.out.println();
		}
	}
	
	/**
	 * Metodo que me dice si hay llaves en cesto de la sala
	 * Pre: el cesto debe de estar creado
	 * pos: devuelve true o false
	 * @return boolean 
	 * Complejidad O(1)
	 */
	public boolean hayLlavesEnCesto(){
		return this.cestoLlaves.estaVacia();
	}
	
	/**
	 * Metodo que devuelve el numero de llaves del cesto
	 * Pre: la instancia cesto debe de estar creada
	 * Post: devuelve el numero de llaves
	 * @return int con el numero
	 * Complejidad: O(1)
	 */
	public int cuantasLlavesEnCesto(){
		return this.cestoLlaves.size();
	}
	
	/**
	 * Metodo accion del personaje en el que cada personaje comprobara
	 * si hay puerta en la sala para intentar abrirla, si la puerta esta
	 * abierta el personaje escapa, si no el personaje se mueve.
	 * Pre: las instancias planta y sala deben estar creadas.
	 * Post: Accion de los personajes en una determinada sala de la planta.
	 * Complejidad: O(n)
	 * @param planta, planta(Planta) donde esta la sala.
	 * @throws ExceptionCerraduraNoValida 
	 * @throws ExceptionCombinacionDeCerraduraIncorrecta 
	 */
	public void salaAccionesPersonajes(Planta planta) throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		// Longitud de la cola de personajes
		int personajesTotalesIniciales=personajes.size();
		// Si hay personajes en la sala...
		if(!personajes.isEmpty()){
			Personaje per=null;
			int j=0;
			while(j<personajesTotalesIniciales){
				//devolver primero
				per=personajes.peek();
				//llamada al metodo de personaje para que lo haga desde alli sus acciones que le 
				//corresponda
				per.accionPersonaje(planta);
				j++;
			}
		}					
	}

	/**
	 * Metodo que pinta todos los personajes de la planta.
	 * Pre: La instancia Sala debe estar creada.
	 * Post: Pinta los personajes debajo de la planta.
	 * Complejidad: O(n)
	 */
	public void pintarTodosPersonajesSala(){
		Queue<Personaje> c=new LinkedList<Personaje>();
		Personaje per=null;
		
		while(haypersonajes()){
			per=this.personajes.peek();
			per.pintarPersonaje();
			c.add(per);
			this.personajes.poll();		
		}
		while(!c.isEmpty()){
			per=c.peek();
			anadirPersonaje(per);
			c.poll();			
		}
	}
	
	 /**
	  * Pre:la instancia frecuencia debe de estar creada.
	  * Post: Modifica la frecuencia de la sala.
	  * Complejidad: O(1)
	  * @param frecuencia
	  */
	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	/**
	 * Post: Devuelve la frecuencia de la sala
	 * Complejidad: O(1)
	 * @return frecuencia
	 */
	public Integer getFrecuencia() {
		return frecuencia;
	}
	
	/**
	 * Post: Metodo que incrementa la frecuancia de la sala.
	 * Complejidad: O(1)
	 */
	public void incFrecuencia(){
		this.frecuencia++;
	}
	
	/**
	 * Pre: la instancia dato2 debe de estar creada.
	 * Post: Metodo que reimplementa el metodo compareTo. Compara las frecuencias
	 * Complejidad: O(1)
	 * 
	 */
	@Override
	public int compareTo(Sala dato2){
		if (this.frecuencia == dato2.getFrecuencia())
			return 0;
		if (this.frecuencia < dato2.getFrecuencia())
			return -1;
		return 1;
	}
}
