package estructuras;


/**
* Implementacion de los metodos de la clase generica Lista.
*
* @version 2.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Lista <L extends Comparable <L>> {
	/** Puntero al primer elemento de la Lista*/
	private Nodo first;
	
	/** Puntero al ultimo elemento de la Lista*/
	private Nodo last;
	
	/** Puntero al nodo de interes*/
	private Nodo pI;
	
	/** Tamaño de la Lista*/
	int size=0;
	
    private class Nodo {
    	/** Dato almacenado en cada nodo */
        private L dato;
    	/** Enlace al siguiente elemento */
        private Nodo next;
    	/** Enlace al elemento anterior */
        private Nodo prev;

        /**
         * Creacion del nodo.
         * @param dato, dato que insertamos en el nodo.
         */
        Nodo (L dato) {
            this.dato = dato;
            this.next = null;
            this.prev = null;
        }
        
        /**
         * Creacion del nodo parametrizado.
         * @param prev referencia asignada a this.prev.
         * @param dato, dato que insertamos en el nodo.
         * @param next, referencia asignada a this.next.
         */
        Nodo(Nodo prev, L dato, Nodo next) {
            this.dato = dato;
            this.next = next;
            this.prev = prev;
        }
    }//class Nodo

		
	/**
	 * Metodo constructor por defecto de la clase Lista.
	 * Crea una lista vacia de tamaño cero.
	 */
	public Lista() {
		first = last = pI = null;
		size = 0;
	}

	
	/**
	 * Metodo constructor parametrizado de la clase Lista.
	 *
	 * @param valor es el nuevo elemento en la lista.
	 */	
	public Lista(L dato) {
		first = last = pI = null;
		size = 0;
		insertDato(dato);
	}
	
	
	/**
	 * Metodo que devuelve el elemento del inicio de la Lista.
	 *
	 * @return first.dato, el primer elemento de la lista.
	 */
	public L getFirst() {
		return first.dato;
	}

	/**
	 * Metodo que devuelve el ultimo elemento de la Lista
	 *
	 * @return last.dato, el ultimo elemento de la lista.
	 */
	public L getLast() {
		return last.dato;
	}

	/**
	 * Metodo para comprobar si la lista esta vacia o no.
	 *
	 * @return (size==0), true si esta vacia o false en caso contrario.
	 */
	public boolean estaVacia (){
		return (size==0);
	}
	
	/**
	 * Metodo para comprobar el tamaño de la lista.
	 *
	 * @return size, longitud de la lista.
	 */
	public int size (){
		return size;
	}


	/**
	 * Permite insertar al final de la lista.
	 *
	 * @param dato, valor que se va a insertar.
	 */
	public void addLast(L dato) {
        Nodo l = last;
        Nodo nodo = new Nodo(l, dato, null);
        last = nodo;
        if (l == null)
            first = nodo;
        else
            l.next = nodo;
        size++;
	}
	
	/**
	 * Metodo para avanzar en la lista. El punto de interes apunta al siguiente
	 * dato. Pre: El puntero del punto de interes no puede estar a null.
	 */
	public void moveForward(){
		if(pI!=null){
			pI=pI.next;
		}			
	}
	
	/**
	 * Metodo para retroceder en la lista. El punto de interes apunta al anterior
	 * dato. Pre: El puntero del punto de interes no puede estar a null.
	 */
	public void moveBack(){
		if(pI!=null){
			pI=pI.prev;
		}			
	}
	
	/**
	 * Mueve el pI al inicio de la lista.
	 */
	public void moveToFirst(){
		pI=first;
	}
	
	/**
	 * Mueve el PI al final de la lista.
	 */
	public void moveToEnd(){
		pI=last;
	}
	
	/**
	 * devuelve verdadero si PI esta al inicio de la lista
	 */
	public boolean atFirst(){
		return(pI==first);
	}
	
	/**
	 * Metodo que nos dice si estamos al final de la lista.
	 * @return (pI==last), verdad si pI apunta al ultimo dato.
	 */
	public boolean atEnd(){
		return(pI==last);
	}
	
	/**
	 * Metodo que nos dice si el pI esta o no esta en la lista.
	 * @return (pI==null), verdad si el pI esta fuera de la lista.
	 */
	public boolean outOfList(){
		return(pI==null);
	}
	
	/**
	 * Metodo para consulta el dato al que apunta pI.
	 *
	 * @return pI.dato, el dato contenido en el nodo al que apunta el pI.
	 */
	public L getDatoPi (){
		return pI.dato;
	}
	
	/**
	 * Insertar un dato en la lista (permite insertar al final de la lista)
	 * incrementa en uno el tamaño de la lista (size).
	 *  
	 * @param dato valor que se va a insertar en la lista.
	 */
	public void insertDato(L dato){
		Nodo nuevo=new Nodo(dato);
		if(pI==null){
			if(size==0){
				pI=nuevo;
				first=nuevo;
				last=nuevo;
			}else{
				last.next=nuevo;
				nuevo.prev=last;
				last=nuevo;
				pI=nuevo;
			}
		}else{
			if(atFirst()){
				nuevo.next=pI;
				pI.prev=nuevo;
				first=nuevo;
				pI=pI.prev;
			}else{
				nuevo.next=pI;
				nuevo.prev=pI.prev;
				pI.prev.next=nuevo;
				pI.prev=nuevo;
				pI=pI.prev;
			}
		}
		size++;
	}
	
	/**
	 * Metodo insertar en orden.
	 * @param dato que queremos insertar en la lista
	 * incrementa en uno el tamaño de la lista (size) atraves del
	 * metodo void insertDato(L dato).
	 */	
	public void insertarEnOrden(L dato){
		Boolean enc=false;
		L d;
		int n=0;
		
		d=null;
		if(getSize()==0){
			insertDato(dato);
		}
		else{
			moveToFirst();
			while(!outOfList()&& enc==false){
				d=getDatoPi();
				n=d.compareTo(dato);// es -1 si el dato de la lista s menor que el dato que queremos insertar
				if(n==-1 || n==0){  // si el dato comparado es menor
					moveForward();
				}
				else{
					enc=true;
				}				
			}
			insertDato(dato);
		}
	}
	
	/**
	 * Busca un dato en la lista dejando el punto de interes apuntando al dato
	 * y si no lo encuentra el punto de interes volvera a apuntar al dato original.
	 * Develve si lo ha encrontrado o no.
	 *  
	 * @param dato valor que se va a insertar
	 * @return encontrado booleano si esta o no esta.
	 */
	public boolean buscarDato(L dato){
		boolean encontrado=false;
		if(!estaVacia()){
			Nodo auxPunt=pI;// para que si no esta el dato vuelva al pi
			L d;
			moveToFirst();
			while(!outOfList() && !encontrado){
				d=null;
				d=getDatoPi();
				if(d.equals(dato)){
					encontrado=true;
				}else{
					moveForward();
				}				
			}
			if(encontrado==false){
				pI=auxPunt;// si el dato no esta para que vuelva al pi
			}
		}		
		return encontrado;
	}	
	
	/**
	 * Modificamos el dato al que apunta el punto de interes.
	 *  
	 * @param dato, valor que se va a modificar.
	 * @return modificado, boolean que nos devuelve si ha sido modificado el dato.
	 */
	public boolean modificarDato(L dato){
		boolean modificado=false;
		if(!estaVacia()){
			pI.dato=dato;
			modificado=true;
		}		
		return modificado;
	}
	
	/**
	 * Metodo que elimina un nodo de la lista. Al borrar el nodo,
	 * se decrementa en uno la longitud de la lista.
	 */
	public void borrarNodo(){
		if(!estaVacia()){
			Nodo aux=pI;
			if(pI==first){
				first=first.next;
			}
			if(atEnd()){
				last=last.prev;
			}
			pI=pI.next;
			if(aux.prev!=null){
				aux.prev.next=pI;				
			}
			if(aux.next!=null){
				aux.next.prev=aux.prev;				
			}
			aux.next=null;
			aux.prev=null;
			size--;			
	   }
	}
	
	/**
	 * Metodo que devuelve el tamaño de la lista.
	 * @return size tamaño de la lista.
	 */
	public int getSize(){
		return(size);
	}
}



