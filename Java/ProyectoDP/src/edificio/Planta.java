package edificio;

import java.util.List;
import laberinto.Laberinto;
import personaje.Personaje;
import estructuras.Lista;
import excepciones.*;
import genAleatorio.GenAleatorios;

/**
* Implementacion de los metodos de la clase Planta.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Planta {
	
	

	/** Matriz con las salas de la planta*/
	private Sala[][] matrizSalas;
	/** Sala donde se escapan los personajes que salen por la puerta*/
	private Sala salaEscapados;
	/** Ancho de la planta*/
	private int ancho;
	/** Alto de la planta */
	private int alto;
	/** Puerta de la planta*/
	private Puerta door;
	/** Turno actual de la accion o simulacion del juego*/
	private int turnoAccion;
	/** Sala de inicio*/
	private int salaInicio;
	/** Sala final de la planta*/
	private int salaFinal;
	/** Condiciones para que se abra la puerta: Condicion del nivel*/
	private int condicionAperturaPuertaNivelArbol;
	/** Numero de llaves diferentes que hay en la planta */
	private int numeroLLavesPlanta;	
	/** Grafo*/
	//private Grafo grafo;
	private Laberinto laberinto;
	
	private static Planta instancia = null;
	/**
	 * devuelve la �nica instancia de la planta. Si no existe, la crea (una sola vez) y la devuelve
	 * @return instancia
	 */
	public static Planta obtenerInstancia(int alto, int ancho, int salaInicio, int salaFinal, int turnoAccion,
			int condicionAperturaPuertaNivelArbol, int numeroLLavesPlanta){//devuelve la instancia de la clase.Me da el acceso a la instancia
		  // TODO: Implementar el m�todo
		 
		  if(instancia==null){//creo el objeto de tipo edificio
			  instancia=new Planta( alto, ancho, salaInicio, salaFinal, turnoAccion,
						 condicionAperturaPuertaNivelArbol, numeroLLavesPlanta);
			  
		  }
		  return instancia;
	}
	//el metodo es estatic que podemos invocar a ese metodo sin crear ninguna instancia, ya que el constructor es privado
	//si no no se podria
	
	/**
	 * Contructor por defecto de la clase Planta.
	 */
	private Planta(){
		this.alto=6;
		this.ancho=6;
		this.numeroLLavesPlanta=30;
		this.door= new Puerta();
		this.salaInicio=0;
		this.salaFinal=(this.alto*this.ancho)-1;
		this.turnoAccion=1;
		this.condicionAperturaPuertaNivelArbol=2;
		this.matrizSalas=null;
		this.matrizSalas=new Sala[this.alto][this.ancho];
		for(int i=0;i<this.alto;i++){
			for(int j=0;j<this.ancho;j++){
				this.matrizSalas[i][j]=new Sala((this.ancho*i)+j);				
			}
		}
		this.salaEscapados=new Sala(1111);
		
		this.laberinto=new Laberinto(this);
		
		laberinto.crearLaberintoDePlanta(this);
		
	}
	/**
	 * Pre: Todas las instancias deben de estar creadas.
	 * Constructor parametrizado de planta.
	 * @param alto, alto(int) de la planta.
	 * @param ancho ancho(int) de la planta.
	 * @param door, door(Puerta) de la planta.
	 * @param salaInicio, salaInicio(int) de la planta.
	 * @param salaFinal, salaFinal(int) de la planta.
	 * @param turnoAccion, turnoAccion(int) de la simulacion.
	 * @param salaescapados, salaescapados(int) de la planta.
	 * @param condicionAperturaPuertaNivelArbol, condicionAperturaPuertaNivelArbol (int)
	 * de la planta.
	 * @param numeroLLavesPlanta, numeroLLavesPlanta(int) diferentes que se usaran
	 * en la simulacion.
	 */
	private Planta(int alto, int ancho, int salaInicio, int salaFinal, int turnoAccion,
			int condicionAperturaPuertaNivelArbol, int numeroLLavesPlanta){
		this.alto=alto;
		this.ancho=ancho;
		this.numeroLLavesPlanta=numeroLLavesPlanta;		
		this.door= new Puerta();
		this.salaInicio=salaInicio;
		this.salaFinal=salaFinal;
		this.turnoAccion=turnoAccion;
		this.condicionAperturaPuertaNivelArbol=condicionAperturaPuertaNivelArbol;		
		this.matrizSalas=new Sala[this.alto][this.ancho];
		for(int i=0;i<alto;i++){
			for(int j=0;j<ancho;j++){
				this.matrizSalas[i][j]=new Sala((this.ancho*i)+j);
			}
		}
		this.salaEscapados=new Sala(1111);
		this.laberinto=new Laberinto(this);
		laberinto.crearLaberintoDePlanta(this);
	}
	
	public Sala[][] getMatrizSalas() {
		return matrizSalas;
	}

	public void setMatrizSalas(Sala[][] matrizSalas) {
		this.matrizSalas = matrizSalas;
	}

	/**
	 * Post: devuelve el laberinto de la planta
	 * @return laberinto
	 * Complejidad: O(1)
	 */
	public Laberinto getLaberinto() {
		return laberinto;
	}
	/**
	 * Pre: la instancia laberinto debe de estar creada.
	 * Post: Modifica el atributo laberinto
	 * @param laberinto
	 * Complejidad: O(1)
	 */
	public void setLaberinto(Laberinto laberinto) {
		this.laberinto = laberinto;
	}

	
	
	/**
	 * Metodo que devuelve la sala de la planta que se la indica
	 * @param i de la matriz
	 * @param j de la matriz
	 * @return la sala de la planta.
	 * Pre: la instancia i,j deben de estar creadas.
	 * Post: devuelve la sala de la planta.
	 * Complejidad: O(1)
	 */
	public Sala devolverSala(int i,int j){
		return this.matrizSalas[i][j];
	}
	/**
	 * Metodo para a�adir la sala de escapados en la planta.
	 * Pre: Las instancias Planta y salaEscapados deben estar creadas.
	 * Post: A�ade una sala de escapados a la planta.
	 * @param salaescapados, salaescapados(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setSalaescapados(Sala salaEscapados) {
		this.salaEscapados = salaEscapados;
	}
	/**
	 * Metodo que devuelve la sala de escapados de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve sala de escapados de la planta.
	 * @return salaEscapados, salaEscapados(int) de la planta.
	 * Complejidad:O(1)
	 */
	public Sala getSalaescapados() {
		return salaEscapados;
	}
	/**
	 * Metodo que me dice si hay llaves en la cerradura de la sala
	 * Pre: la cerradura debe de estar creado
	 * pos: devuelve true o false
	 * @return boolean 
	 * Complejidad O(1)
	 */
	public boolean hayLlavesEnCerradura(){
		return this.door.getCerradura().vacio();
	}
	/**
	 * Metodo que devuelve el numero de llaves de la cerradura
	 * Pre: la instancia cerradura debe de estar creada
	 * Post: devuelve el numero de llaves
	 * @return int con el numero
	 * Complejidad: O(1)
	 */
	public int cuantasLlavesEnCerradura(){
		return this.door.getCerradura().numeroDeNodos();
	}
	/**
	 * Metodo para a�adir el numero de llaves diferentes que habra en la planta.
	 * Pre: Las instancias Planta y numeroLLavesPlanta deben estar creadas.
	 * Post: A�ade el numero de llaves diferentes a la planta.
	 * @param numeroLLavesPlanta, numeroLLavesPlanta(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setNumeroLLavesPlanta(int numeroLLavesPlanta) {
		this.numeroLLavesPlanta = numeroLLavesPlanta;
	}
	/**
	 * Metodo que devuelve el numero de llaves diferentes que hay planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el numero diferente de llaves que hay en la planta.
	 * @return numeroLLavesPlanta, numeroLLavesPlanta(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getNumeroLLavesPlanta() {
		return numeroLLavesPlanta;
	}

	/**
	 * Metodo para a�adir el numero de la sala que hara de salida.
	 * Pre: Las instancias Planta y salaFinal deben estar creadas.
	 * Post: A�ade a la planta el numero de la sala que hara de salida.
	 * @param salaFinal, salaFinal(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setSalaFinal(int salaFinal) {
		this.salaFinal = salaFinal;
	}

	/**
	 * Metodo que devuelve el numero de la sala que hace de salida.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el numero de la sala de salida de la planta.
	 * @return salaFinal, salaFinal(int) de la planta.
	 */
	public int getSalaFinal() {
		return salaFinal;
	}

	/**
	 * Metodo para a�adir el numero de la sala que hara de inicio.
	 * Pre: Las instancias Planta y salaInicio deben estar creadas.
	 * Post: A�ade a la planta el numero de la sala que hara de inicio.
	 * @param salaInicio, salaInicio(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setSalaInnicio(int salaInicio) {
		this.salaInicio = salaInicio;
	}
	
	/**
	 * Metodo que devuelve el numero de la sala que hace de inicio.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el numero de la sala de inicio de la planta.
	 * @return salaInicio, salaInicio(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getSalaInnicio() {
		return salaInicio;
	}

	/**
	 * Metodo para a�adir la puerta a la planta.
	 * Pre: Las instancias Planta y door deben estar creadas.
	 * Post: A�ade door a la planta.
	 * @param door, door(Puerta) de la planta.
	 * Complejidad: O(1)
	 */
	public void setDoor(Puerta door) {
		this.door = door;
	}

	/**
	 * Metodo que devuelve la puerta de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve door de la planta.
	 * @return door, door(Puerta) de la planta.
	 * Complejidad: O(1)
	 */
	public Puerta getDoor() {
		return door;
	}

	/**
/	 * Metodo para a�adir el alto de la planta.
	 * Pre: Las instancias Planta y alto deben estar creadas.
	 * Post: A�ade el alto a la planta.
	 * @param alto, alto(int) de la planta.
	 * Complejidad: O(1)
/	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}
	/**
	 * Metodo que devuelve el alto de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el alto de la planta.
	 * @return alto, alto(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Metodo para a�adir el ancho de la planta.
	 * Pre: Las instancias Planta y ancho deben estar creadas.
	 * Post: Inserta el alto a la planta.
	 * @param ancho, ancho(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Metodo que devuelve el ancho de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el ancho de la planta.
	 * @return ancho, ancho(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getAncho() {
		return ancho;
	}
	
	/**
	 * Metodo para a�adir condicion de apertura de la puerta de la planta
	 * segun un nivel dado, este nivel ira referenciado a la profundidad del
	 * arbol de la cerradura de la puerta.
	 * Pre: Las instancias Planta y condicionAperturaPuertaNivelArbol deben
	 *  estar creadas.
	 * Post: Inserta la condicion de apertura de la planta.
	 * @param condicionAperturaPuertaNivelArbol, condicionAperturaPuertaNivelArbol(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setCondicionAperturaPuertaNivelArbol(int condicionAperturaPuertaNivelArbol) {
		this.condicionAperturaPuertaNivelArbol = condicionAperturaPuertaNivelArbol;
	}
	
	/**
	 * Metodo que devuelve la condicion de apertura de la puerta de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el numero de la condicion de apertura de la planta.
	 * @return condicionAperturaPuertaNivelArbol, condicionAperturaPuertaNivelArbol(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getCondicionAperturaPuertaNivelArbol() {
		return this.condicionAperturaPuertaNivelArbol;
	}

	/**
	 * Metodo para a�adir el turno a la planta.
	 * Pre: Las instancias Planta y turnoAccion deben estar creadas.
	 * Post: A�ade el turno a la planta.
	 * @param turnoAccion, turnoAccion(int) de la planta.
	 * Complejidad: O(1)
	 */
	public void setTurnoAccion(int turnoAccion) {
		this.turnoAccion = turnoAccion;
	}

	/**
	 * Metodo que devuelve el turno de la planta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Devuelve el turno de la planta.
	 * @return turnoAccion, turnoAccion(int) de la planta.
	 * Complejidad: O(1)
	 */
	public int getTurnoAccion() {
		return this.turnoAccion;
	}
	
	/**
	 * Metodo para a�adir un personaje a una sala de la planta.
	 * pre: las instancias planta y personaje deben estar creadas, el
	 *  numeroSala debe ser valido.
	 * post: Inserta un personaje en la matriz de salas de la planta.
	 * @param planta, planta(Planta) de la estacion.
	 * @param numeroSala, numeroSala(int) en la que se inserta el personaje.
	 * @return insertado, insertado(boolean) si es true.
	 * Complejidad: O(1)
	 */
	public boolean anadirPersonajePlanta(Personaje personaje,int numeroSala){
		boolean insertado=false;
		// Coordenada i de la sala en la que se inserta el personaje
		int fila=numeroSala/this.ancho;
		// Coordenada j de la sala en la que se inserta el personaje
		int columna=numeroSala%this.ancho;
		if(this.matrizSalas[fila][columna].anadirPersonaje(personaje)){
			insertado=true;
			personaje.setSalaActual(numeroSala);
		}
		return insertado;
	}	
	
	/**
	 * Metodo para el repartir las llaves por las diferentes plantas, se
	 * hace con un vector de las salas que tienen llaves y otro vector con las 
	 * llaves que se utilizan. Las llaves se van repartiendo de 5 en 5 en las salas
	 *  que contiene el vector idsalasconllaves.
	 * pre: las instancias idsalasConLlaves, vectorLlavesRepartir y Planta deben 
	 * estar creadas.
	 * post: Quedan repartidas las llaves por las salas de la planta.
	 * Metodo para el repartir las llaves por las diferentes plantas
	 * Se hace con un vector de las salas que tienen llaves y otro vector con las llaves que se utilizan
	 * Las llaves se van repartiendo de 5 en 5 en las salas que contiene el vector idsalasconllaves
	 * @param idsalasconllaves que es el vector de las salas en las que se quieren insertar las llaves
	 * @param vectorLlaverRepartir que son las llaves que se reparten
	 * Complejidad: O(n2)
	 */
	public void repartirLlaves(List<Sala> idSalasConLlaves, int[] vectorLlavesRepartir){
		int fila=0;
		int columna=0;
		int h=0;		
		for(int i=0;i<idSalasConLlaves.size();i++){
		
			fila=idSalasConLlaves.get(i).getNumeroSala()/this.ancho;
			columna=idSalasConLlaves.get(i).getNumeroSala()%this.ancho;
			int j=0;
			while(j<5 && h<vectorLlavesRepartir.length){ 
				
				Llave llave=new Llave(vectorLlavesRepartir[h]);
				matrizSalas[fila][columna].anadirLlaveEnCestoSala(llave);
				j++;
				h++;
			}
		}
		
	}
	
	/**
	 * Metodo que crea la combinacio de llaves que se usaran en la
	 * cerradura de la puerta.
	 * Pre: las instancias Planta y combinacion deben estar creadas.
	 * Post: Combinacion con la lista de llaves que se guardara en
	 * la cerradura como condicion de apertura.
	 * @param combinacion, combinacion(Lista<Llave>) de la cerradura.
	 * @return insertado, insertado(boolean) true si se ha creado bien la combinacion.	 *
	 * Complejidad: O(n) 
	 */
	public boolean crearCombinacionDeCerradura(Lista<Llave> combinacion)throws ExceptionCombinacionDeCerraduraIncorrecta{
		boolean insertado=false;
		// lista transitoria con llaves impares
		//Creamos una lista L
		Lista<Llave> L=new Lista<Llave>();
		Llave key;
		int contadorDeLlaves=0;// Contador para saber el numero de llaves por el
		                       // que estara formada la cerradura
		for(int i=1;i<this.numeroLLavesPlanta;i=i+2){
			key=new Llave(i);
			// Insertamos en la lista las llaves impares
			L.insertarEnOrden(key);
			contadorDeLlaves++;
		}// Ya tenemos la lista l llena de llaves impares
		L.moveToFirst();// movemos pI al inicio de la lista
		int posicion=0;// posicion de la llave en la lista a insertar en la cerradura
		while(!L.estaVacia()){			
			// Generamos una posicion aleatoria
			posicion=GenAleatorios.generarNumero(L.size());
			L.moveToFirst();
			//Avanzamos en la lista de llaves impares hasta llegar a la posicion que nos ha indicado el numero aleatorio
			for(int j=0;j<posicion;j++){
				// avanzaremos el pI las veces que nos indique la posicion
				L.moveForward();
			}
			// una vez en la posicion indicada, anhadimos la llave
			if(!L.estaVacia()){
				combinacion.insertDato(L.getDatoPi());
			}
			// borramos llave de la lista
			L.borrarNodo();
		}
		if(combinacion.size() == contadorDeLlaves){
			insertado=true;
		}else{
			insertado=true;
			throw new ExceptionCombinacionDeCerraduraIncorrecta ("Combinacion creada incorrectamente");			
		}
		return insertado;
	}
	
	/**
	 * Metodo que inserta una combinacion ya creada en la cerradura de
	 * la puerta.
	 * Pre: Las instancias Planta y combinacion deben estar creadas.
	 * Post: Se insertan las llaves de la combinacion en el arbol de cerradura
	 * de la puerta.
	 * @param combinacion, combinacion(Lista<Llave>) a insertar en la cerradura
	 * de la puerta de la planta.
	 * @return insertado, insertado(boolean) true si se ha insertado correctemente
	 * la combinacion en la cerradura de la puerta.
	 * Complejidad: O(n)
	 */
	public boolean insertarCombinacionEnCerradura(Lista<Llave> combinacion)throws ExceptionCerraduraNoValida{
		boolean insertado=false;
		Llave key=new Llave(-1);
		combinacion.moveToFirst();
		// Recorremos la lista de la combinacion y los
		// vamos insertando en el arbol cerradura de la puerta
		while(!combinacion.outOfList()){
			key=combinacion.getDatoPi();
			door.addLlaveCerradura(key);
			combinacion.moveForward();
		}
		combinacion.moveToFirst();		
		if(door.getCerradura().numeroDeNodos()== combinacion.size()){
			// si el numero de llaves que se insertan en la cerradura es igual
			// al numero de llaves de la combinacion, entonces se inserto correctamente
			insertado=true;
		}else{
			insertado=true;
			throw new ExceptionCerraduraNoValida ("Cerradura no se inserto correctamente");
		}
		return insertado;
	}
	
	/**
	 * Metodo en el que se va recorriendo sala a sala de la planta de
	 * menor a mayor(por numero de sala) y si hubiera personajes realizarian
	 * sus tareas.
	 * Pre: La instancia de la Planta debe estar creada.
	 * Post: Los personajes de la planta realizan sus acciones si
	 * es su turno y se incrementa en uno el turno de la planta.
	 * @throws ExceptionCerraduraNoValida 
	 * @throws ExceptionCombinacionDeCerraduraIncorrecta 
	 * Complejidad: O(n2)
	 */
	public void plantaAccionPersonajes() throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		for(int i=0;i<this.alto;i++){
			for(int j=0;j<this.ancho;j++){
				devolverSala(i, j).salaAccionesPersonajes(this);							
			}
		}
		this.turnoAccion++;		
	}
	
	/**
	 * Metodo para pintar las llaves en una planta.
	 * pre: La instancia planta debe estar creada.
	 * post: muestra por consola la planta con sus llaves.
	 * Pinta las llaves de las salas.
	 * Complejidad: O(n2)
	 */
	public void pintarllaves(){
		for (int i = 0;  i< this.getAlto(); i++) {
			for (int j = 0; j < this.getAncho(); j++) {
				this.devolverSala(i, j).mostrarLlaves();
			}
		}
	}
}



	
