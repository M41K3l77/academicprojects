package laberinto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import edificio.Planta;
import genAleatorio.GenAleatorios;

public class Laberinto {
	/** Lista de paredes del laberinto*/
	private List<Pared> listaParedes;
	/** Caminos del laberinto*/
	private Caminos caminos;
	/** Grafo del laberinto*/
	private Grafo grafo;	
	
	/**
	 * Constructor parametrizado del laberinto.
	 * @param planta
	 */
	public Laberinto(Planta planta){
		this.listaParedes=new LinkedList<Pared>();
		this.caminos=new Caminos(planta);
		this.grafo=new Grafo(planta.getAlto(),planta.getAncho());
	}
	
	/**
	 * Metodo para insertar un grafo al laberinto.
	 * Pre: La instancia Laberinto debe estar creada.
	 * Post: Añade un grafo al laberinto.
	 * @param grafo, grafo(Grafo) que se añade al laberinto.
	 * Complejidad: O(1)
	 */
	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}
	
	/**
	 * Metodo que nos da el grafo del laberinto.
	 * Pre: La instancia Laberinto debe estar creada.
	 * Post: Devuelve el grafo.
	 * @return grafo
	 * Complejidad: O(1)
	 */
	public Grafo getGrafo() {
		return grafo;
	}
	
	/**
	 * Metodo que nos da los caminos del laberinto.
	 * Pre: La instancia Laberinto debe estar creada.
	 * Post: Devuelve los caminos.
	 * @return caminos
	 * Complejidad: O(1)
	 */
	public Caminos getCaminos() {
		return caminos;
	}
	
	/**
	 * Metodo para insertar una lista de paredes al laberinto.
	 * Pre: La instancia Laberinto debe estar creada.
	 * Post: Añade una lista de paredes al laberinto.
	 * @param listaParedes, listaParedes(List<Pared>) que se añade al laberinto.
	 * Complejidad: O(1)
	 */
	public void setListaParedes(List<Pared> listaParedes) {
		this.listaParedes = listaParedes;
	}

	/**
	 * Metodo que nos da las paredes del laberinto.
	 * Pre: La instancia Laberinto debe estar creada.
	 * Post: Devuelve las paredes.
	 * @return listaParedes
	 * Complejidad: O(1)
	 */
	public List<Pared> getListaParedes() {
		return listaParedes;
	}
	
	/**
	 * Metodo que genera la lista de paredes acorde a la planta. Cada
	 * pared esta pormada por dos salas contiguas.
	 * Pre: La instancia planta debe de estar creada
	 * Metodo que genera las paredes segun la planta.
	 * @param planta
	 * @return creada, true si se genera correctamente
	 * Complejidad: O(n2)
	 */
	private boolean generarListaParedes(Planta planta){
		int nsala=0;
		int auxiliarsala=0;
		boolean creada=false;
		
		for (int i = 0; i < planta.getAlto(); i++) {
			for (int j = 0; j < planta.getAncho(); j++) {
				nsala=planta.devolverSala(i, j).getNumeroSala();
				i--;
				if(i>=0){
					auxiliarsala=planta.devolverSala(i, j).getNumeroSala();
					this.listaParedes.add(new Pared(nsala, auxiliarsala));
				}
				i++;
				j++;
				if(j<planta.getAncho()){
					auxiliarsala=planta.devolverSala(i, j).getNumeroSala();
					this.listaParedes.add(new Pared(nsala, auxiliarsala));
					
				}
				j--;
				i++;
				if(i<planta.getAlto()){
					auxiliarsala=planta.devolverSala(i, j).getNumeroSala();
					this.listaParedes.add(new Pared(nsala, auxiliarsala));
				}
				i--;
				j--;
				if(j>=0){
					auxiliarsala=planta.devolverSala(i, j).getNumeroSala();
					this.listaParedes.add(new Pared(nsala, auxiliarsala));
				}
				j++;
			}
		}
		return creada;
	}
	
	/**
	 * Metodo que inserta los nodos(seran enteros que representan cada sala)
	 * en el grafo del laberinto.
	 * Pre: La instancia planta debe de estar creada
	 * Metodo para insertar los nodos en el grafo de la planta.
	 * @param planta
	 * Complejidad: O(n)
	 */
	private void insertarNodosEnGrafo(Planta planta){
		for (int i=0;i<planta.getAlto()*planta.getAncho();i++) {
			this.grafo.nuevoNodo(i);
		}
	}
	
	/**
	 * Metodo para tirar paredes del laberinto y no se creen vacios
	 * en el laberinto.
	 * Pre: la instancia planta debe de estar creada
	 * Metodo para tirar las paredes del laberinto. Va cogiendo pared a pared de la lista de paredes y las va tirando(crea un
	 * arco y marca con el mismo numero las salas que estan comunicadas)
	 * @param planta
	 * Complejidad: O(n3)
	 */
	private void tirarParedes(Planta planta){
		int posicionAleatoria=0;
		Pared pared=null;
		int i=0,j=0,k=0,l=0;
		
		while(!listaParedes.isEmpty()){//mientras que la lista de paredes que hemos creado no este vacia
			
			//generamos un numero para ver cual pared cogemos de la lista
			posicionAleatoria=GenAleatorios.generarNumero(listaParedes.size());
			//seleccionamos la pared
			pared=listaParedes.get(posicionAleatoria);			
			listaParedes.remove(posicionAleatoria);
			//cogemos la posicion de la sala del nodo 1 y del 2
			i=pared.getNodo1()/planta.getAncho();
			j=pared.getNodo1()%planta.getAncho();
			k=pared.getNodo2()/planta.getAncho();
			l=pared.getNodo2()%planta.getAncho();
			//si las marcas son distintas podemos tirar la pared
			if(planta.devolverSala(i, j).getMarcaSala()!=planta.devolverSala(k, l).getMarcaSala()){
				//creamos los arcos
				this.grafo.nuevoArco(pared.getNodo1(), pared.getNodo2(), 1);
				this.grafo.nuevoArco(pared.getNodo2(),pared.getNodo1(), 1);
				//y hay que ponerle la misma marca alas salas que halla con el mismo numero
				int aux=planta.devolverSala(i, j).getMarcaSala();
				//le asignamos el valor a la sala que esta al lado
				planta.devolverSala(i, j).setMarcaSala(planta.devolverSala(k, l).getMarcaSala());
				//y luego vamos buscando en la matriz el valor que tenia antes guardado en auxiliar para que todas las que tenian ese numero se cambien
				for (int m = 0; m < planta.getAlto(); m++) {
					for (int m2 = 0; m2 < planta.getAncho(); m2++) {
						if(planta.devolverSala(m, m2).getMarcaSala()==aux){
							planta.devolverSala(m, m2).setMarcaSala(planta.devolverSala(k, l).getMarcaSala());
						}
					}
				}
			}
		}		
	}
	
	/**
	 * Metodo que comprueba si el cuadrante NorEste permite el 
	 * derribo de una pared.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return norteEste, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedesNorteEste(Planta planta,Integer nodoSala){
		boolean norteEste=false;
		Integer n,ne,e;
		n=nodoSala-(new Integer(planta.getAncho()));
		ne=nodoSala-(new Integer(planta.getAncho()))+1;
		e=nodoSala+1;
		if(encontrarPared(n, ne, e,nodoSala, planta)){
			norteEste=true;
		}		
		return norteEste;
	}
	
	/**
	 * Metodo que comprueba si el cuadrante NorOeste permite el 
	 * derribo de una pared.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return norteOeste, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedesNorteOeste(Planta planta,Integer nodoSala){
		boolean norteOeste=false;
		Integer n,no,o;
		n=nodoSala-(new Integer(planta.getAncho()));
		no=nodoSala-(new Integer(planta.getAncho()))-1;
		o=nodoSala-1;
		if(encontrarPared(n, no, o,nodoSala, planta)){
			norteOeste=true;
		}
		return norteOeste;
	}
	
	/**
	 * Metodo que comprueba si el cuadrante SurOeste permite el 
	 * derribo de una pared.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return surOeste, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedesSurOeste(Planta planta,Integer nodoSala){
		boolean surOeste=false;
		Integer s,so,o;
		s=nodoSala+(new Integer(planta.getAncho()));
		so=nodoSala+(new Integer(planta.getAncho()))-1;
		//caso general
		o=nodoSala-1;
		if(encontrarPared(s, so, o,nodoSala, planta)){
			surOeste=true;
		}
		return surOeste;
	}
	
	/**
	 * Metodo que comprueba si el cuadrante SurEste permite el 
	 * derribo de una pared.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return surEste, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedesSurEste(Planta planta,Integer nodoSala){
		boolean surEste=false;
		Integer s,se,e;
		s=nodoSala+(new Integer(planta.getAncho()));
		se=nodoSala+(new Integer(planta.getAncho()))+1;
		//caso general
		e=nodoSala+1;
		if(encontrarPared(s, se, e,nodoSala, planta)){
			surEste=true;
		}
		return surEste;
	}
	
	/**
	 * Metodo que nos dice si entre 4 salas que forman un cuadrado hay
	 * mas de una pared. Y si es así devuelve true y se podra tirar una pared
	 * y no se producirá vacio(esto no es del todo cierto, ya que se hacen dos
	 * comprobaciones para evitar vacio en un cuadrado que se solape).
	 * Pre: las instancias n1,n2,n3,nodoSala y planta deben de estar creadas
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param nodoSala
	 * @param planta
	 * Complejidad: O(n)
	 * @return encontrado
	 */
	private boolean encontrarPared(Integer n1,Integer n2,Integer n3,Integer nodoSala,Planta planta){
		int contadorParedes=0;
		boolean encontrado=false;
		ArrayList<Integer>nodos=new ArrayList<Integer>();
		nodos.add(n1);//n
		nodos.add(n2);//no
		nodos.add(n3);//o
		nodos.add(nodoSala);
		int i=0;
		while(i<nodos.size()-1){
			// comprobamos que ambos nodos son positivos
			if(nodos.get(i)>0 && nodos.get(i+1)>0){
				// si no son adyacentes es que hay pared
				if(!this.grafo.adyacente(nodos.get(i),nodos.get(i+1))){					
					contadorParedes++;
				}
			}
			i++;
		}
		// recuperamos el indice perdido y comparamos ultimo con primero
		i--;
		if(nodos.get(0)>0 && nodos.get(i)>0){
			if(!this.grafo.adyacente(nodos.get(i+1),nodos.get(0))){
				contadorParedes++;
			}
		}
		// hay pared para tirar si hay mas de una
		if(contadorParedes>1){
			encontrado=true;
		}
		return encontrado;
	}
	
	/**
	 * Metodo que comprueba si hay pared al norte, y si la hay entonces
	 * comprobara que no se produce ningun vacio al tirarla. hace doble
	 * comprobacion.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return tirada, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedNorte(Planta planta, Integer nodoSala){
		boolean hayParedNorte=false;
		boolean tirada=false;
		if((nodoSala-planta.getAncho())>=0){
			if(!this.grafo.adyacente(nodoSala,nodoSala-planta.getAncho())){//si no son adyacentes es que hay pared
				hayParedNorte=true;
			}
		}		
		if(hayParedNorte){			
			if(comprobarParedesNorteEste(planta, nodoSala) && comprobarParedesNorteOeste(planta, nodoSala)){
				//se tira
				tirada=true;
				this.grafo.nuevoArco(nodoSala,nodoSala-planta.getAncho(), 1);
				this.grafo.nuevoArco(nodoSala-planta.getAncho(),nodoSala, 1);
			}
		}
		return tirada;
	}
	
	/**
	 * Metodo que comprueba si hay pared al Este, y si la hay entonces
	 * comprobara que no se produce ningun vacio al tirarla. hace doble
	 * comprobacion.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return tirada, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedEste(Planta planta, Integer nodoSala){
		boolean tirada=false;
		boolean hayParedEste=false;
		//fila del nodo
		int fila=nodoSala/planta.getAncho();
		Integer auxNodoSala=nodoSala+1;
		int filaAux=auxNodoSala/planta.getAncho();
		if(fila==filaAux){
			if(!this.grafo.adyacente(nodoSala,auxNodoSala)){
				hayParedEste=true;
			}
		}
		if(hayParedEste){
			
			if(comprobarParedesNorteEste(planta, nodoSala) && comprobarParedesSurEste(planta, nodoSala)){
				//se tira
				tirada=true;
				this.grafo.nuevoArco(nodoSala,nodoSala+1, 1);
				this.grafo.nuevoArco(nodoSala+1,nodoSala, 1);
			}
		}
		return tirada;	
	}
	
	/**
	 * Metodo que comprueba si hay pared al Sur, y si la hay entonces
	 * comprobara que no se produce ningun vacio al tirarla. hace doble
	 * comprobacion.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return tirada, true si hay pared en esa direccion
	 * Complejidad: O(n)
	 */
	private boolean comprobarParedSur(Planta planta, Integer nodoSala){
		boolean tirada=false;
		boolean hayParedSur=false;
		if((nodoSala+planta.getAncho())<(planta.getAlto()*planta.getAncho())){
			if(!this.grafo.adyacente(nodoSala,nodoSala-planta.getAncho())){//si no son adyacentes es que hay pared
				hayParedSur=true;
			}
		}
		if(hayParedSur){
			
			if(comprobarParedesSurEste(planta, nodoSala) && comprobarParedesSurOeste(planta, nodoSala)){
				//se tira
				tirada=true;
				this.grafo.nuevoArco(nodoSala,nodoSala+planta.getAncho(), 1);
				this.grafo.nuevoArco(nodoSala+planta.getAncho(),nodoSala, 1);
			}
		}
		return tirada;		
	}
	
	/**
	 * Metodo que comprueba si hay pared al Oeste, y si la hay entonces
	 * comprobara que no se produce ningun vacio al tirarla. hace doble
	 * comprobacion.
	 * Pre: las instancias planta y nodoSala deben de estar creadas
	 * @param planta
	 * @param nodoSala
	 * @return tirada, true si hay pared en esa direccion
	 * Complejidad: O(1)
	 */
	private boolean comprobarParedOeste(Planta planta, Integer nodoSala){
		boolean tirada=false;
		boolean hayParedOeste=false;
		//fila del nodo
		int fila=nodoSala/planta.getAncho();
		Integer auxNodoSala=nodoSala-1;
		int filaAux=auxNodoSala/planta.getAncho();
		if(fila==filaAux){
			if(!this.grafo.adyacente(nodoSala,auxNodoSala)){
				hayParedOeste=true;
			}
		}
		if(hayParedOeste){
			if(comprobarParedesNorteOeste(planta, nodoSala) && comprobarParedesSurOeste(planta, nodoSala)){
				//se tira
				tirada=true;
				this.grafo.nuevoArco(nodoSala,nodoSala-1, 1);
				this.grafo.nuevoArco(nodoSala-1,nodoSala, 1);				
			}
		}
		return tirada;
	}
	
	/**
	 * Metodo que crea una lista con el numero de salas
	 * para crear atajos
	 * @param planta
	 * @param listaSalasParaAtajo
	 * Complejidad:O(n)
	 */
	private void crearListaDeSalas(Planta planta, List<Integer> listaSalasParaAtajo){
		for (int i=0; i<(planta.getAlto()*planta.getAncho());i++) {
			listaSalasParaAtajo.add(new Integer(i));
		}
	}
	
	/**
	 * Metodo para seleccionar un nodo aleatorio
	 * @param planta
	 * @param listaSalasParaAtajo
	 * @return nodoSala
	 * Complejidad: O(n) es una suposicion
	 */
	public Integer nodoDeListaDeSalasParaAtajo(Planta planta, List<Integer> listaSalasParaAtajo){
		// Sala candidata a tirar pared
		Integer nodoSala=null;
		// Indice de la sala candidata
		int indiceLista=0;		
		// Se selecciona aleatoriamente una sala
		indiceLista=GenAleatorios.generarNumero(listaSalasParaAtajo.size());		
		// Una vez seleccionado el nodo se elimina para no volver a tenerlo en cuenta
		//listaSalasParaAtajo.remove(nodoSala);
		nodoSala=new Integer(listaSalasParaAtajo.get(indiceLista));

		return nodoSala;
	}
	/**
	 * Metodo que crea atajo/s en el laberinto en una proporcion
	 * al 5% del numero de salas de la planta.
	 * Pre: la instancia planta debe de estar creada.
	 * Post: Crea el atajo del laberinto.
	 * @param planta
	 * @return atajo, true si se creó correctamente.
	 * Complejidad: O(n)
	 */
	private boolean crearAtajo(Planta planta){
		boolean atajo=false;
		// seleccionar un nodo de forma aleatoria de entre una lista de salas.
		List<Integer> listaSalasParaAtajo=new LinkedList<Integer>();
		// Se crea una lista con el numero de salas
		crearListaDeSalas(planta, listaSalasParaAtajo);
		int numeroParedesATirar=(planta.getAlto()*planta.getAncho()*5)/100;
		
		int limite=0;
		while(!listaSalasParaAtajo.isEmpty() && limite<numeroParedesATirar){
			// Se crea el atajo, es decir se tira la pared si se cumplen las circustancias.
			Integer nodoSala=nodoDeListaDeSalasParaAtajo(planta, listaSalasParaAtajo);
			
			if(comprobarParedNorte(planta, nodoSala)){
				atajo=true;				
			}else{
				if(comprobarParedEste(planta, nodoSala)){
					atajo=true;
				}else{
					if(comprobarParedSur(planta, nodoSala)){
						atajo=true;
					}else{
						if(comprobarParedOeste(planta, nodoSala)){
							atajo=true;
						}else{
							atajo=false;
						}
					}
					
				}
			}
			if(atajo==true){
				limite++;
			}
		}		
		return atajo;
	}
	
	/**
	 * Metodo que pinta el laberinto.
	 * Pre: La instancia planta debe de estar creada.	 * 
	 * @param planta
	 * Complejidad: O(n2)
	 */
	public void mostrarLaberinto(Planta planta){
        System.out.print(" ");        
		for(int i=0;i<(planta.getAncho());i++) System.out.print("_ ");
		System.out.println();
		
		for(int i=0;i<planta.getAlto();i++){
			System.out.print("|");
			int sala=0;
			sala=0;			  
		   for(int j=0;j<planta.getAncho();j++){
			   sala=(i*planta.getAncho())+j;// sala actual
			   if(planta.devolverSala(i, j).haypersonajes()){
				   planta.devolverSala(i, j).mostrarPersonajes();				   
			   }else{
				   if(sala+planta.getAncho()<(planta.getAncho()*planta.getAlto())){ //es que esta dentro de los limites
					   if(this.grafo.getArcos()[sala][sala+planta.getAncho()]!=1){
						   
						   System.out.print("_");
					   }else{
						  
						   System.out.print(" ");
					   }
				   }else{
					   
					   System.out.print("_");
				   }
			   }
			   
			   System.out.print("");
			   if(j<planta.getAncho()){
				   if(sala+1<(planta.getAncho()*planta.getAlto())){
					   if(this.grafo.getArcos()[sala][sala+1]!=1){
						   
						   System.out.print("|");
					   }else{
						   
						   System.out.print(" ");
					   }
				   }else{
					   
					   System.out.print("|"); 
				   }
			   }
		  }		   
		System.out.println();
	  }		
   }
	/**
	 * Metodo que pinta el laberinto vacio.
	 * Pre: La instancia planta debe de estar creada.
	 * @param planta
	 * Complejidad: O(n2)
	 */
	public void mostrarLaberintoVacio(Planta planta){
        System.out.print(" ");        
		for(int i=0;i<(planta.getAncho());i++) System.out.print("_ ");
		System.out.println();
		
		for(int i=0;i<planta.getAlto();i++){
			System.out.print("|");
			int sala=0;
			sala=0;			  
		   for(int j=0;j<planta.getAncho();j++){
			   sala=(i*planta.getAncho())+j;// sala actual
			   if(sala+planta.getAncho()<(planta.getAncho()*planta.getAlto())){ //es que esta dentro de los limites
				   if(this.grafo.getArcos()[sala][sala+planta.getAncho()]!=1){
					   
					   System.out.print("_");
				   }else{
					  
					   System.out.print(" ");
				   }
			   }else{
				   
				   System.out.print("_");
			   }			   
			   System.out.print("");
			   if(j<planta.getAncho()){
				   if(sala+1<(planta.getAncho()*planta.getAlto())){
					   if(this.grafo.getArcos()[sala][sala+1]!=1){
						   
						   System.out.print("|");
					   }else{
						   
						   System.out.print(" ");
					   }
				   }else{
					   
					   System.out.print("|"); 
				   }
			   }
		  }		   
		System.out.println();
	  }		
   }
	
	/**
	 * Metodo aceptable de la ruta de escape.
	 * Pre: la instancia visitados, etapa,dato deben de estar creadas
	 * @param visitados: Nodos ya visitados
	 * @param etapa por la que va la funcion
	 * @param dato a insertar en los visitados
	 * @return acept, true si se pudo insertar
	 * Complejidad: O(n)
	 */
	 private boolean aceptableRutaEscape(List <Integer> visitados,Integer etapa,Integer dato){
 		boolean acept=true;
 		Integer aux=0;
 		int i=0;
 		while(i<etapa && acept){
 			aux=visitados.get(i);
 			if(aux.equals(dato)){
 				acept=false;
 			}
 			else i++;
 		}
 		return acept;
	 }
	 
	/**
	 * Metodo que calcula la/s ruta/s de escape del laberinto y se añaden a caminos.
	 * Pre: las instancias planta, visitados, origen,destino,etapa deben de estar creadas
	 * @param planta de la estacion
	 * @param visitados nodos visitados
	 * @param origen de donde salimos
	 * @param destino 
	 * @param etapa por donde va la funcion.
	 * Post: Nos crea una ruta desde un origen a un destino
	 * Complejidad: O(n2)
	 */
	private void rutasEscape(Planta planta,List <Integer> visitados, Integer origen,Integer destino,
		Integer etapa){
		
   	  // Donde guardaremos los adyacentes a un nodo
   	  	Set <Integer> ady;
   	  // Hara de auxiliar
   	  	Integer w;
   	  	ady=new TreeSet<Integer>();
   	  // insertamos en nodo visitado en la lista de visitados   	 
   	  // Guardamos los adyacentes del vertice en ady
   	  	planta.getLaberinto().getGrafo().adyacentes(origen, ady);
   	  	while (!ady.isEmpty()) {
   	  		
   	  		Iterator<Integer> it=ady.iterator();
   	  		w=it.next();
   	  		ady.remove(w);
   	  		if(aceptableRutaEscape(visitados,etapa,w)){
	   			visitados.remove(w);
	   			visitados.add(etapa, w);
	   			if(w==destino){
	   				List<Integer> visitadosAux=new LinkedList<Integer>();
	   				int i=0;
	   				 while(!visitados.get(i).equals(destino)){
	   					 visitadosAux.add(visitados.get(i));
	   					 i++;
	   				 }
	   			    // Añadido para quitar origen y destino	   				
	   				visitadosAux.remove(planta.getSalaInnicio());	   				
	   				this.caminos.addCamino(visitadosAux);
	   			}else{
   				rutasEscape(planta,visitados,w,destino, etapa+1);
	   			}
   	  		}		
   	  	}   		 
     }
	
	/**
	 * Metodo que crea el laberinto de la planta usando la teoria de grafos.
	 * Pre: la instancia planta debe de estar creada
	 * Post:Metodo para tirar paredes, crear atajo,generar caminos de un origen a destino,salas mas frecuentadas
	 * @param planta de la estacion
	 * Complejidad: O(n2)
	 * 
	 */
	public void crearLaberintoDePlanta(Planta planta){
		// Se generan las paredes de la planta
		this.generarListaParedes(planta);
		// Se insertan los nodos(salas) en el grafo
		this.insertarNodosEnGrafo(planta);
		// Se tiran paredes de tal forma que no se cree vacio
		tirarParedes(planta);
		// Se crea/n atajo/s acorde al numero de salas de la planta
		crearAtajo(planta);
		// Warhall y Floyd
		this.grafo.warshall();
		this.grafo.floyd();
		
		List <Integer> visitados=new LinkedList<Integer>();
		Integer etapa=1;
		Integer origen=(planta.getSalaInnicio());
		Integer destino=(planta.getSalaFinal());
		
		visitados.add(origen);
		// Se añaden los caminos posibles para posteriormente calcular
		// salas mas frecuentadas
		this.rutasEscape(planta, visitados, origen, destino, etapa);	
		this.getCaminos().contarSalasFrecuentes(planta);		    
   }
}
