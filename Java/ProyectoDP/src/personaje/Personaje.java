package personaje;

import java.util.ArrayList;
import java.util.List;
import edificio.Planta;
import edificio.Llave;
import estructuras.Pila;
import excepciones.ExceptionCerraduraNoValida;
import excepciones.ExceptionCombinacionDeCerraduraIncorrecta;
import direccion.Direccion;

/**
* Implementacion de los metodos de la clase Personaje.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public abstract class Personaje implements Comparable<Personaje> {
	
	/** Nombre del personaje*/
	protected String nombre;
	
	/**Marca del personaje*/
	protected String marca;
	
	/** Turno por el que va el personaje*/
	protected int turno;
	
	/** Sala en la que se encuentra el personaje*/
	protected int salaActual;
	
	/** Pila de llaves del personaje*/
	protected Pila<Llave> llaves;
	
	/** EDL DIRECCIONES(MOVIMIENTOS NSEO)*/
	protected  List<Direccion> direcciones;
	
	

	/**
	 * Constructor por defecto
	 */
	public Personaje(){
		
		/** Nombre del personaje*/
		this.nombre="Sin nombre";
		/** Marca distintiva del personaje*/
		this.marca="Sin marca";
		/** Turno por el que va el personaje*/
		this.turno=0;
		/** Sala en la que esta el personaje*/
		this.salaActual=0;
		/** Pila de llaves que tiene el personaje*/
		this.llaves=new Pila<Llave>();
		/** Array de direcciones que debe tomar el personaje*/
		this.direcciones=new ArrayList<Direccion>();
		
	}
	
	/**
	 * Pre: las instancias nombre,marca,turno,salaActual, Llaves y direcciones deben de estar creadas
	 * Constructor pamametrizado
	 * @param nombre, nombre del personaje.
	 * @param marca, marca del personaje.
	 * @param turno, turno del personaje.
	 * @param salaActual, sala actual en la que esta el personaje.
	 * @param Llaves, pila de llaves del personaje.
	 * @param direcciones, (List<Direccion>) de direcciones que seguira el personaje (ruta).
	 */
	public Personaje(String nombre, String marca, int turno, int salaActual, Pila<Llave> Llaves, List<Direccion> direcciones){
		this.nombre=nombre;
		this.marca=marca;
		this.turno=turno;
		this.salaActual=salaActual;
		this.llaves=Llaves;
		this.direcciones=direcciones;
	}
	
	/**
	 * Metodo que devuelve la pila de llaves del personaje.
	 * Pre: La instancia debe estar creada.
	 * Post: Me devuelve las llaves de ese personaje.
	 * @return llaves, las llaves del personaje(Pila<Llave>).
	 * Complejidad: O(1)
	 */
	public Pila<Llave> getLLaves() {
		return llaves;
	}

	/**
	 * Metodo que añade una pila de llaves al personaje.
	 * Pre: la instancia llaves debe de estar creada.
	 * Post: Añade una pila de llaves al personaje.
	 * @param llaves para añadir al personaje (Pila<Llave>).
	 * Complejidad: O(1)
	 */
	public void setLLaves(Pila<Llave> llaves) {
		this.llaves = llaves;
	}

	/**
	 * Metodo que devuelve el array de direcciones del personaje.
	 * Pre: La instancia debe estar creada.
	 * post: Devuelve las direcciones del personaje.
	 * @return direcciones, array de direcciones(List<Direccion>).
	 * Complejidad: O(1)
	 */
	public List<Direccion> getDirecciones() {
		return direcciones;
	}
	
	/**
	 * Metodo que añade un array de direcciones al personaje.
	 * pre: la instancia direcciones debe de estar creada.
	 * post: Añade las direcciones al personaje.
	 * @param direcciones, array de direcciones(List<Direccion>) a añadir al personaje.
	 * Complejidad: O(1)
	 */
	public void setdirecciones(List<Direccion> direcciones){
		this.direcciones=direcciones;
	}
	
	/**
	 * Metodo que devuelve el numero de la sala en la que esta el personaje.
	 * Pre: La instancia debe estar creada.
	 * Post: Devuelve la sala actual en la que se encuentra el personaje.
	 * @return salaActual, numero de la sala actual(int) del personaje.
	 * Complejidad: O(1)
	 */
	public int getSalaActual() {
		return salaActual;
	}
	
	/**
	 * Metodo para actualizar el numero de la sala en la que esta el personaje.
	 * Pre: la instancia salaActual debe de estar creada.
	 * Post: Modifica la sala actual al personaje.
	 * @param salaActual, el nuevo numero que le asignamos a la sala(int) en
	 * la que esta el personaje.
	 * Complejidad: O(1)
	 */
	public void setSalaActual(int salaActual) {
		this.salaActual = salaActual;
	}
	
	/**
	 * Metodo que devuelve el numero del turno del personaje.
	 * Pre: La instancia debe estar creada.
	 * Post: Devuelve el turno del personaje.
	 * @return turno, turno(int) del personaje.
	 * Complejidad: O(1)
	 */
	public int getTurno() {
		return turno;
	}
	
	/**
	 * Metodo para actualizar el turno(int) del personaje.
	 * Pre: la instancia turno debe de estar creada.
	 * Post: Modifica el turno del personaje.
	 * @param turno, turno(int) que le asignamos al personaje.
	 * Complejidad: O(1)
	 */
	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	/**
	 * Metodo que devuelve el nombre del personaje.
	 * Pre: La instancia debe estar creada.
	 * post: Devuelve el nombre del personaje.
	 * @return nombre, nombrer(string) del personaje.
	 * Complejidad: O(1)
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Metodo para darle un nombre al personaje.
	 * pre: la instancia nombre debe de estar creada.
	 * post: Modifica el nombre del personaje.
	 * @param nombre, nombre(string) que le agregamos al personaje.
	 * Complejidad: O(1)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Metodo que devuelve la marca del personaje.
	 * Pre: Lainstancia debe estar creada.
	 * Post: Devuelve la marca del personaje.
	 * @return marca, marca(string) del personaje.
	 * Complejidad: O(1)
	 */
	public String getMarca() {
		return marca;
	}
	
	/**
	 * Metodo para darle una marca(string) al personaje.
	 * Pre: la instancia marca debe de estar creada.
	 * Post: Modifica la marca del personaje.
	 * @param marca, marca(string) que le agregamos al personaje.
	 * Complejidad: O(1)
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
		
	/**
	 *Metodo abstracto que lo implementan las subclases
	 */
	public abstract void accionPersonajeLlave(Planta planta);
		
	
	
	/**
	 *Metodo abstracto que lo implementan las subclases
	 * @param planta, planta(Planta) de la estacion.
	 * @throws ExceptionCerraduraNoValida 
	 * @throws ExceptionCombinacionDeCerraduraIncorrecta 
	 */
	public abstract void accionPersonajePuerta(Planta planta) throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta;
	
	/**
	 * Pre: Las instancias planta,i,j,per deben estar creadas.
	 * Post: Movimiento del personaje hacia el Norte.
	 * @param planta en la que esta el personaje.
	 * @param i, i(int) posicion de la sala.
	 * @param j, j(int) posicion de la sala.
	 * @param per, per(Personaje) que se esta moviendo.+
	 * Complejidad: O(1)
	 */
	private void movimientoPersonajeNorte(Planta planta, int i,int j, Personaje per){
		i--;
		if(i<0){// Si nos salimos de la matriz el personaje se encola 
			    //en su misma cola
			    // pero con turno incrementado
			planta.devolverSala(i+1, j).anadirPersonaje(per);
			
		}else{// si me he podido mover encolamos el personaje
			  // en la sala que le toque segun i j
			// y actualizamos su numero de sala
			per.setSalaActual((planta.getAncho()*i)+j);
			planta.devolverSala(i, j).anadirPersonaje(per);
		}
		// Se incrementa el turno al personaje, esto es obligatorio
		// haya cambiado o no de sala ya que era su turno.
		per.setTurno(per.getTurno()+1);
	}
	
	/**
	 * Pre: Las instancias planta,i,j,per deben estar creadas.
	 * Post: Movimiento del personaje hacia el Sur.
	 * @param planta en la que esta el personaje.
	 * @param i, i(int) posicion de la sala.
	 * @param j, j(int) posicion de la sala.
	 * @param per, per(Personaje) que se esta moviendo.
	 * Complejidad: O(1)
	 */
	private void movimientoPersonajeSur(Planta planta, int i,int j, Personaje per){
		i++;				
		if(i>=planta.getAlto()){
			planta.devolverSala(i-1, j).anadirPersonaje(per);
		}else{
			setSalaActual((planta.getAncho()*i)+j);
			planta.devolverSala(i, j).anadirPersonaje(per);
		}
		per.setTurno(per.getTurno()+1);
		
	}
	
	/**
	 * Pre: Las instancias planta,i,j,per deben estar creadas.
	 * Post: Movimiento del personaje hacia el Este.
	 * @param planta en la que esta el personaje.
	 * @param i, i(int) posicion de la sala.
	 * @param j, j(int) posicion de la sala.
	 * @param per, per(Personaje) que se esta moviendo.
	 * Complejidad: O(1)
	 */
	private void movimientoPersonajeEste(Planta planta, int i,int j, Personaje per){
		j++;				
		if(j>=planta.getAncho()){
			planta.devolverSala(i, j-1).anadirPersonaje(per);
		}else{
			setSalaActual((planta.getAncho()*i)+j);
			planta.devolverSala(i, j).anadirPersonaje(per);
		}
		per.setTurno(per.getTurno()+1);
	}
	
	/**
	 * Pre: Las instancias planta,i,j,per deben estar creadas.
	 * Post: Movimiento del personaje hacia el Oeste.
	 * @param planta en la que esta el personaje.
	 * @param i, i(int) posicion de la sala.
	 * @param j, j(int) posicion de la sala.
	 * @param per, per(Personaje) que se esta moviendo.
	 * Complejidad: O(1)
	 */
	private void movimientoPersonajeOeste(Planta planta, int i,int j, Personaje per){
		j--;				
		if(j<0){
			planta.devolverSala(i, j+1).anadirPersonaje(per);
		}else{
			planta.devolverSala(i, j).anadirPersonaje(per);
			setSalaActual((planta.getAncho()*i)+j);
		}
		per.setTurno(per.getTurno()+1);
	}
	
	/**
	 * Metodo que se encarga del movimiento del personaje entre las salas
	 * de una planta.
	 * pre: La intancia planta debe de estar creada.
	 * post: Cambiara al personaje de sala en caso de que se cumplan las
	 * condiciones para ello. Si no es su turno se volvera a encolar en la misma
	 * sala y si es su turno pero tiene una direccion erronea tambien
	 * se encolara en la sala que estaba pero incrementando su turno en uno.
	 * actualiza el numero de la sala a la que pasara el personaje y su
	 * turno si se mueve.
	 * @param planta, planta(Planta) de la estacion.
	 *Nota: aqui se recoge directamente la llave si se mueve
	 *Complejidad: O(1)
	 */
	public void moverse(Planta planta){
		Personaje per=null;
		// coordenadas del personaje en la panta
		int i=0;
		int j=0;
		// coordenadas actuales del personaje
		i=this.getSalaActual()/planta.getAncho();
		j=this.getSalaActual()%planta.getAncho();
		// Cogemos el personaje de la cola de personajes
		per=planta.devolverSala(i, j).primerpersonaje();
		// si el personaje es un intruso y el intruso se queda sin direcciones se vuelven a cargar todas las 
		//direcciones que calculo al ininio
		if (per instanceof Intruso){
			if(per.getDirecciones().size()==0){
			     List <Integer> visitados=new ArrayList<Integer>();
			    ((Intruso) per).movimientoIntruso(planta, planta.getAncho()-1, visitados);
			    per.traducirDirecciones(planta, visitados, this.direcciones);
				per.setdirecciones(this.direcciones);
			}
		}
		// Lo desencolamos en espera de procesar su movimiento
		planta.devolverSala(i, j).eliminarPersonajeCola();
		// Comprobar que hay direccion a la que ir
		if(!per.direcciones.isEmpty()){// planta.getTurnoAccion()<per.direcciones.size()
			// si coincide el turno
			if(planta.getTurnoAccion()==per.getTurno()){
				// Actualiza el numero de la sala y el turno dependiendo
				// de la direccion que le toque coger N, S, E u O.
				if(per.direcciones.get(0)==Direccion.N){
					this.movimientoPersonajeNorte(planta,i,j,per);
					per.direcciones.remove(0);
				}else if(per.direcciones.get(0)==Direccion.S){
					this.movimientoPersonajeSur(planta,i,j,per);
					per.direcciones.remove(0);
				}else if(per.direcciones.get(0)==Direccion.E){
					this.movimientoPersonajeEste(planta,i,j,per);
					per.direcciones.remove(0);
				}else if(per.direcciones.get(0)==Direccion.O){
					this.movimientoPersonajeOeste(planta,i,j,per);
					per.direcciones.remove(0);
				}
				// Si el turno coincide recogo llave, si no, no recogo llave.
			accionPersonajeLlave(planta);
			}else{// si no coincide el turno lo encolo en su cola otra vez
				// Y no se le aumenta el turno por que no era su turno
				planta.devolverSala(i, j).anadirPersonaje(per);
			}
		}else{// personajes sin direccion a la que ir
			  // Si llega el turno de un personaje y no tiene direccion a
			  // la que ir, el personaje hara sus acciones pero se encolara
			  // en la sala en la que estaba con un turno incrementado.
			if(planta.getTurnoAccion()==per.getTurno()){
				accionPersonajeLlave(planta);
				planta.devolverSala(i, j).anadirPersonaje(per);
				per.setTurno(per.getTurno()+1);
			}else{
				planta.devolverSala(i, j).anadirPersonaje(per);
			}
		}		
	}
	
	/**
	 * Metodo que comprueba si hay puerta en la sala en la que esta.
	 * pre: la instancia planta debe de estar creada.
	 * post: Dice si hay puerta en la sala que esta el personaje.
	 * @param planta, planta(Planta) de la estacion.
	 * @return hay, hay(boolean) puerta en la sala.
	 * si coincide la sala en la que esta el personaje con la sala final
	 *  es que hay puerta.
	 *  Complejidad: O(1)
	 */
	public boolean comprbarSiHayPuerta(Planta planta){
		boolean hay=false;
		if(this.salaActual==planta.getSalaFinal()){
			hay=true;
		}
		return hay;	
	}	 

	/**
	 * Metodo que mueve un personaje(Lider y trabajador) a la sala de
	 * escapados.
	 * Pre: La puerta debe estar abierta y el personaje debe estar en
	 * la ultima sala. No dejara escapar al intruso. La instancia planta
	 * debe de estar creada.
	 * Post:Metodo que pasa el personaje de la sala final a la sala de escapados.
	 * @param planta, planta(Planta) de la estacion.
	 * @return escapado, escapado(boolean) verdad si ya no esta en la sala.
	 * Complejidad: O(1)
	 */
	public boolean personajeSalirDePlanta(Planta planta){
		boolean escapado=true;
		// Añadimos el personaje a la sala de escapados.
		planta.getSalaescapados().anadirPersonaje(this);
		int i=0;
		int j=0;
		// coordenadas actuales del personaje
		i=this.getSalaActual()/planta.getAncho();
		j=this.getSalaActual()%planta.getAncho();
		planta.devolverSala(i, j).eliminarPersonajeCola();
		// actualizar numero de la sala actual en la que esta el personaje
		this.setSalaActual(planta.getSalaescapados().getNumeroSala());
		return escapado;
	}
	
	/**
	 * 
	 * Pre: La instancia planta debe de estar creada, el presonaje también debe estar creado.
	 * Post:Realiza las diferentes acciones del personeje llamadas en sala.
	 * @param planta en la que esta el personaje.
	 * @throws ExceptionCerraduraNoValida
	 * @throws ExceptionCombinacionDeCerraduraIncorrecta
	 * Complejidad: O(1)
	 */
	public void accionPersonaje(Planta planta) throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		boolean escapado=false;
		//Si hay puerta para intenta abrirla
		this.accionPersonajePuerta(planta);
		// Si hay puerta y esta abierta el personaje escapa
		if(this.comprbarSiHayPuerta(planta)){
			if(planta.getDoor().getEstadoPuerta()==2){
				if(this.personajeSalirDePlanta(planta)==true){
					//personajes.desencolar();
				    escapado=true;
				}					    
			}					
		}				
		// Esta condicion es importante por que si no se pone
		// intentara mover un personaje que ya no esta en la cola
		if(escapado==false){
			// si el personaje no se escapo se puede mover y
			// el desencolar del personaje se hace en moverser(planta)
			this.moverse(planta);
		}
	}
	
	
	/**
	 * Pre: las instancias planta, caminoSalas, caminoDirecciones deben de estar creadas
	 * Post: Metodo para tracucir las direcciones sacadas por nos personajes para ponerlas en su ruta
	 * @param planta
	 * @param caminoSalas
	 * @param caminoDirecciones
	 * @return true si se realiza bien
	 * Complejidad: O(n)
	 */	
	public boolean traducirDirecciones(Planta planta, List <Integer> caminoSalas, List <Direccion> caminoDirecciones){
		boolean nesoDireccion=false;
		
		for (int i = 0; i < (caminoSalas.size()-1); i++) {
			if((caminoSalas.get(i+1)-caminoSalas.get(i))==(-planta.getAncho())){// N
				caminoDirecciones.add(Direccion.N);
			}else if((caminoSalas.get(i+1)-caminoSalas.get(i))==(planta.getAncho())){// S
				caminoDirecciones.add(Direccion.S);
			}else if((caminoSalas.get(i+1)-caminoSalas.get(i))==(1)){// E
				caminoDirecciones.add(Direccion.E);
			}else if((caminoSalas.get(i+1)-caminoSalas.get(i))==(-1)){// O
				caminoDirecciones.add(Direccion.O);
			}else{
				System.out.println("fallo en la direccion");
			}			
		}
		// Si se partiera de una sala aislada devolveria false
		if(!caminoDirecciones.isEmpty()){
			nesoDireccion=true;
		}
		return nesoDireccion;		
	}
	
	/**
	 * Metodo para pintar un personaje y sus llaves debajo de la planta.
	 * Pre: La instancia personaje debe estar creada.
	 * Post: Pinta la marca de un personaje y sus llaves.
	 * Complejidad: O(n)
	 */
	public void pintarPersonaje(){
		if(this instanceof Intruso){
			System.out.print("("+"intruso"+":");
		}else if(this instanceof Lider){
			System.out.print("("+"lider"+":");
		}else if(this instanceof Trabajador){
			System.out.print("("+"trabajador"+":");
		}
		
		System.out.print(this.marca+":");
		System.out.print(this.getSalaActual()+":");
		System.out.print(this.getTurno()+": ");
		Pila<Llave> p=new Pila<Llave>();
		Llave key=null;
		while(!this.llaves.estaVacia()){
			key=this.llaves.getDatoCima();
			if(this.llaves.getSize()>1){
				System.out.print(key+" ");
			}else{
				System.out.print(key);
			}			
			p.insertarDato(key);
			this.llaves.sacarDato();			
		}
		while(!p.estaVacia()){
			key=p.getDatoCima();
			this.llaves.insertarDato(key);
			p.sacarDato();			
		}
		System.out.print(")");
		System.out.println();
	}
	
	/**
	 * Metodo para pintar un personaje y sus llaves debajo de la planta.
	 * Pre: La instancia personaje debe estar creada.
	 * Post: Pinta la marca de un personaje y sus llaves.
	 * Complejidad: O(n)
	 */
	public void pintarPersonajeEscapado(){
		if(this instanceof Intruso){
			System.out.print("("+"intruso"+":");// se mostraria en caso de escapar por error
		}else if(this instanceof Lider){
			System.out.print("("+"lider"+":");
		}else if(this instanceof Trabajador){
			System.out.print("("+"trabajador"+":");
		}
		
		System.out.print(this.marca+":");
		System.out.print(this.getSalaActual()+":");
		System.out.print(this.getTurno()+":");
		Pila<Llave> p=new Pila<Llave>();
		Llave key=null;
		while(!this.llaves.estaVacia()){
			key=this.llaves.getDatoCima();
			if(this.llaves.getSize()>1){
				System.out.print(key+" ");
			}else{
				System.out.print(key);
			}			
			p.insertarDato(key);
			this.llaves.sacarDato();			
		}
		while(!p.estaVacia()){
			key=p.getDatoCima();
			this.llaves.insertarDato(key);
			p.sacarDato();			
		}
		System.out.print(")");
		System.out.println();
	}
	
	/**
	 * Metodo que muestra las direcciones deun personaje.
	 * Pre: La instancia personaje debe estar creada.
	 * Post: Pinta las direcciones del personaje.
	 * Complejidad: O(n)
	 */
	public void mostrarDireccionesPersonaje(){
		for (int i = 0; i < this.direcciones.size(); i++) {
			if(i<this.direcciones.size()-1){
				System.out.print(this.direcciones.get(i).toString()+" ");
			}else{
				System.out.print(this.direcciones.get(i).toString());
			}			
		}
	}
	
	/**
	 * Se implementara si alguna vez hay que comparar personajes.
	 */
	@Override
	public int compareTo(Personaje o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
