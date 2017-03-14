package estructuras;

import java.util.List;

/**
* Implementacion de los metodos de la clase generica Arbol.
*
* @version 2.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Arbol <A extends Comparable <A>>{

	/** Dato almacenado en cada nodo del arbol. */
	private A datoRaiz;
	
	/** Atributo que indica si el arbol esta vacio. */
	private boolean esVacio;
	
	/** Hijo izquierdo del nodo actual y es a su vez un arbol */
	private Arbol<A> hIzq;
	
	/** Hijo derecho del nodo actual y es a su vez un arbol  */
	private Arbol<A> hDer;
	
	/**
	 * Constructor por defecto de la clase Arbol <A> Generico. Inicializa un arbol vacio.
	 */
	public Arbol(){
	    this.esVacio=true;
	    this.hIzq = null;
	    this.hDer = null;
	}

	/**
	 * Construcctor parametrizado. Crea un Arbol apartir de los parametros
	 * pasados por referencia.
	 *
	 * @param hIzq El hijo izquierdo del arbol que se esta creando 
	 * @param datoRaiz Raiz del arbol que se esta creando
	 * @param hDer El hijo derecho del arbol que se esta creando
	 */
	public Arbol (Arbol<A> hIzq, A datoRaiz, Arbol<A> hDer){
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer=hDer;
		this.esVacio=false;
	}
	
	/**
	 * Devuelve el hijo izquierdo del arbol.
	 *
	 * @return El hijo izquierdo del arbol.
	 */
	public Arbol<A> getHijoIzq(){
		return hIzq;
	}
	
	/**
	 * Devuelve el hijo derecho del arbol.
	 *
	 * @return Hijo derecho del arbol.
	 */
	public Arbol<A> getHijoDer(){
		return hDer;
	}
	
	/**
	 * Devuelve la raiz del arbol.
	 *
	 * @return La raiz del arbol.
	 */
	public A getRaiz(){
		return datoRaiz;
	}
	
	/**
	 * Comprueba si el arbol esta vacio.
	 * @return esVacio, verdadero si el arbol esta vacio,
	 * falso en caso contrario.
	 */
	public boolean vacio(){
		return esVacio;
	}
	
	/**
	 * Inserta un nuevo dato en el arbol.
	 *
	 * @param dato es el dato a insertar en el arbol.
	 * @return resultado, verdadero si el dato se ha insertado correctamente,
	 * falso en caso contrario.
	 */
	public boolean insertar(A dato){
	    boolean resultado=true;
	    if (vacio()) {// Condicion de parada
	        datoRaiz = dato;
			esVacio = false;
		}
	    else {
	        if (!(this.datoRaiz.equals(dato))) {
	            Arbol<A> aux;
	            if (dato.compareTo(this.datoRaiz)<0) { //dato < datoRaiz
	                if ((aux=getHijoIzq())==null)
	                    hIzq = aux = new Arbol<A>();// al estar vacio pasa a la llamada recursiva y lo inserta
	            }
	            else {									//dato > datoRaiz
	                if ((aux=getHijoDer())==null)
	                    hDer = aux = new Arbol<A>();
	            }
	            resultado = aux.insertar(dato);
	        }
	        else
	            resultado=false;
	    }
	    return resultado;
	}
	
	/**
	 * Metodo que devuelve el numero de nodos que tiene el arbol.
	 *
	 * @return numNodos es el numero de nodos del arbol.
	 */
	public int numeroDeNodos(){
		Arbol <A>aux;
		int numNodos=0;
		int numHizq=0;
		int numDer=0;
		
		if(!vacio()){			
			if ((aux=getHijoIzq())!=null) {
				numHizq=aux.numeroDeNodos();
	        }	        
	        if ((aux=getHijoDer())!=null){
	        	numDer=aux.numeroDeNodos();
	        }
	        numNodos=1+numHizq+numDer;
		}
		return numNodos;
	}
	
	/**
	 * Metodo que devuelve el numero de hijos que tiene el arbol..
	 * 
	 * @return numero de hijos del arbol
	 */
	public int numeroHijos(){
		int n=0;
		@SuppressWarnings("unused")
		Arbol <A>aux;
		if((aux=getHijoIzq())!=null) n++;
		if((aux=getHijoDer())!=null) n++;
		return n;
	}
	
	/**
	 * 
	 * @return si es hoja el arbol(nodo)
	 */
	public boolean esHoja(){
		return(numeroHijos()==0 && !esVacio);
	}
	
	/**
	 * 
	 * @return numero de nodos hoja del arbol
	 */
	public int numeroHojas(){
		int cont=0;
		Arbol <A>aux;
		if(!esVacio){
			if(esHoja()){
				cont=1;
			}
			else{
				if((aux=getHijoIzq())!=null){
					cont=cont+aux.numeroHojas();
				}
				if((aux=getHijoDer())!=null){
					cont=cont+aux.numeroHojas();
					
				}
			}
		}
		return cont;
	}
	/**
	 * Metodo que devuelve el numero de nodos internos.
	 * @return numero de nodos internos
	 */
	public int numeroNodosInternos(){
		return (numeroDeNodos()-numeroHojas());
	}
	
	/**
	 * Metodo que comprueba si un dato se encuentra almacenado en el arbol.
	 *
	 * @param dato el dato a buscar.
	 * @return encontrado, verdadero si el dato se encuentra en el arbol,
	 * falso en caso contrario.
	 */
	public boolean pertenece(A dato){
	    Arbol <A>aux=null;
	    boolean encontrado=false;
	    if (!vacio()) {
	        if (this.datoRaiz.equals(dato))
	            encontrado = true;
	        else {
	            if (dato.compareTo(this.datoRaiz)<0)	//dato < datoRaiz
	                aux=getHijoIzq();
	            else									//dato > datoRaiz
	                aux = getHijoDer();
	            if (aux!=null)
	                encontrado = aux.pertenece(dato);
	        }
	    }
	    return encontrado;
	}
	
	/**
	 * Metodo borrar un dato del arbol.
	 *
	 * @param dato El dato que se quiere borrar.
	 */
	public void borrar(A dato){
	    if (!vacio()) {
	        if (dato.compareTo(this.datoRaiz)<0){			//dato<datoRaiz
	        	if(hIzq!=null){
	        		hIzq = hIzq.borrarOrden(dato);
	        	}					
			}	
	        else
	            if (dato.compareTo(this.datoRaiz)>0) {		//dato>datoRaiz 
	            	if(hDer!=null){
	            		hDer = hDer.borrarOrden(dato);
	            	}	            		
				}
	            else //En este caso el dato es datoRaiz
	            {
	                if (hIzq==null && hDer==null)
	                {
	                    esVacio=true;
	                }
	                else
	                    borrarOrden(dato);
	            }
	    }
	}
	

	/**
	 * Borrar un dato. Este metodo es utilizado por el metodo borrar anterior.
	 *
	 * @param dato El dato a borrar.
	 * @return Devuelve el arbol resultante despues de haber realizado el borrado.
	 */
	private Arbol<A> borrarOrden(A dato)
	{
	    A datoaux;
	    Arbol <A>retorno=this;
	    Arbol <A>aborrar, candidato, antecesor;

	    if (!vacio()) {
	        if (dato.compareTo(this.datoRaiz)<0){		// dato<datoRaiz
	        	if(hIzq!=null)
		    	        hIzq = hIzq.borrarOrden(dato);
	        }
			else
	            if (dato.compareTo(this.datoRaiz)>0) {	// dato>datoRaiz
	            	if(hDer!=null)
	    	           hDer = hDer.borrarOrden(dato);
	            }
				else {
	                aborrar=this;
	                if ((hDer==null)&&(hIzq==null)) { /*si es hoja*/
	                    retorno=null;
	                }
	                else {
	                    if (hDer==null) { /*Solo hijo izquierdo*/
	                        aborrar=hIzq;
	                        datoaux=this.datoRaiz;
	                        datoRaiz= hIzq.getRaiz();     
	                        hIzq.datoRaiz = datoaux;
	                        hIzq=hIzq.getHijoIzq();
	                        hDer=aborrar.getHijoDer();

	                        retorno=this;
	                    }
	                    else
	                        if (hIzq==null) { /*Solo hijo derecho*/
	                            aborrar=hDer;
	                            datoaux=datoRaiz;
	                            datoRaiz=hDer.getRaiz();
	                            hDer.datoRaiz = datoaux;
	                            hDer=hDer.getHijoDer();
	                            hIzq=aborrar.getHijoIzq();

	                            retorno=this;
	                        }
	                        else { /* Tiene dos hijos */
	                            candidato = this.getHijoIzq();
	                            antecesor = this;
	                            while (candidato.getHijoDer()!=null) {
	                                antecesor = candidato;
	                                candidato = candidato.getHijoDer();
	                            }

	                            /*Intercambio de datos de candidato*/
	                            datoaux = datoRaiz;
	                            datoRaiz = candidato.getRaiz();              
	                            candidato.datoRaiz=datoaux;
	                            aborrar = candidato;
	                            if (antecesor==this)
	                                hIzq=candidato.getHijoIzq();
	                            else
	                                antecesor.hDer=candidato.getHijoIzq();
	                        } //Eliminar solo ese nodo, no todo el subarbol
	                    aborrar.hIzq=null;
	                    aborrar.hDer=null;
	                }
	            }
	    }
	    return retorno;
	}
	
	
	/**
	 * Metodo recorrido inOrden del arbol.
	 * hijo izquierdo, raiz, hijo derecho.
	 */
	public void inOrden(){
	    Arbol <A>aux=null;
	    if (!vacio()) {
	        if ((aux=getHijoIzq())!=null) {
	            aux.inOrden();
	        }
	        System.out.print(this.datoRaiz+" ");	        
	        if ((aux=getHijoDer())!=null){
	            aux.inOrden();
	        }
	    }
	}
	
	/**
	 * Metodo copiar inOrden del arbol a una lista.
	 * hijo izquierdo, raiz, hijo derecho.
	 */
	public void copiarInOrden(List <A>listaCopia){
	    Arbol <A>aux=null;
	    if (!vacio()) {
	        if ((aux=getHijoIzq())!=null) {
	            aux.copiarInOrden(listaCopia);
	        }
	        listaCopia.add(datoRaiz);	        
	        if ((aux=getHijoDer())!=null){
	            aux.copiarInOrden(listaCopia);
	        }
	    }
	}
	
	/**
	 * metodo que recorre el arbol en preorden.
	 *  raiz, hijo izquierdo, hijo derecho.
	 */
	public void preOrden(){
	    Arbol <A>aux=null;
	    if (!vacio()) {
	    	System.out.println(this.datoRaiz);
	    	
	        if ((aux=getHijoIzq())!=null) {
	            aux.preOrden();
	        }	        
	        if ((aux=getHijoDer())!=null){
	            aux.preOrden();
	        }    
	    }
	}
	
	/**
	 * metodo que recorre el arbol en posorden.
	 *  hijo izquierdo, hijo derecho, raiz.
	 */
	public void posOrden(){
	    Arbol <A>aux=null;
	    if (!vacio()) {	    	
	        if ((aux=getHijoIzq())!=null) {
	            aux.posOrden();
	        }	        
	        if ((aux=getHijoDer())!=null){
	            aux.posOrden(); 
	        }
	        System.out.println(this.datoRaiz);
	    }
	}
	
	/**
	 * metodo que nos dice la profundidad del arbol.
	 * @return resultado+1 es la profundidad del arbol.
	 */
	public int profundidad(){
		Arbol <A>aux;		
		int resultado=0;
		int profHIzq=0;
		int profHDer=0;
		if(!vacio()){
			if((aux=getHijoIzq())!=null){
				profHIzq=aux.profundidad();
			}
			if((aux=getHijoDer())!=null){
				profHDer=aux.profundidad();
			}
			if(profHIzq>profHDer){
				resultado=profHIzq;
			}else{
				resultado=profHDer;
			}
		}		
		return resultado+1;
	}
		
	/**
	 * Metodo que muestra un dato determinado si esta en el arbol.
	 * @param dato es el dato que queremos mostrar
	 * @return encontrado, si esta o no esta el dato en el arbol.
	 */
	public boolean mostrarDato(A dato){
		boolean encontrado=false;
	    Arbol <A>aux;
	    if (!vacio()){
	    	if(this.datoRaiz.equals(dato)){
	    		encontrado=true;
	    		dato=this.datoRaiz;	    		
	    		System.out.println(dato);
	    	}
	    	else{
	    		if (dato.compareTo(this.datoRaiz)<0){
	    			aux=getHijoIzq();
	        	}
	        	else{
	        		aux=getHijoDer();
	        	}
	        	if(aux!=null){
	        		encontrado=aux.mostrarDato (dato);
	        	}
	    	}
	    }
	    return encontrado;
	}	
	
	/**
	 * Metodo que borra el arbol (realmente crea uno nuevo vacio).
	 * @param arbolABorrar es el arbol a borrar.
	 * @return arbolABorrar es el propio arbol vacio.
	 */
	public Arbol<A> borrarArbolCompleto(Arbol<A> arbolABorrar){
	    if (!arbolABorrar.vacio()) {
	    	arbolABorrar=new Arbol<A>();
	    }
	    return arbolABorrar;
	}
}
