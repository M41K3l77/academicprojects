package personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edificio.Llave;
import edificio.Planta;
import estructuras.Pila;
import direccion.Direccion;

/**
* Implementacion de los metodos de la clase Lider.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega:EC1
*/
public class Lider extends Personaje{
	
	/**
	 * Metodo constructor de Lider. Al estar todos los atributos en personaje
	 * solo hay que llamar al constructor de Personaje (super clase).
	 */
	public Lider(){
		super();		
	}
	
	/**
	 * Constructor parametrizado de Lider.
	 * Pre: las instancias nombre, marca, turno, salaActual, Llaves
	 * y direcciones deben estar creadas.
	 * Post: Crea un Lider.
	 * @param nombre, nombre(String) del lider
	 * @param marca, marca(String) del lider.
	 * @param turno, turno(int) de movimientos del lider.
	 * @param salaActual, salaActual(int) en la que esta el lider.
	 * @param Llaves, pila(Pila<Llave>) de llaves que tiene el lider.
	 * @param direcciones, direcciones(List<Direccion>) del Lider.
	 */
	public Lider(String nombre, String marca, int turno, int salaActual, Pila<Llave> Llaves, List<Direccion> direcciones){		
		super("Nombre", "@", 0, 0, Llaves, direcciones);		
	}
	
	/**
	 * 
	 * Pre: la instancia planta debe de estar creada. 
	 * Post: Quita una llave en el cesto de llaves de
	 * la sala 
	 * @param planta, planta(Planta) en la que esta el personaje.
	 * Complejidad: O(1)
	 */
	public void accionPersonajeLlave(Planta planta){
		// Ver numero de sala del personaje
		int fila=0;
		int columna=0;
		fila=getSalaActual()/planta.getAncho();
		columna=getSalaActual()%planta.getAncho();
		// Con el numero de sala si hay llaves en la sala recoger una
		// Comprobamos que tiene llaves
		if(!planta.devolverSala(fila, columna).getCestoLlaves().estaVacia()){
			// Añadimos la primera llave del cesto de llaves de la sala a
			// la pila de llaves del personaje
			this.llaves.insertarDato(planta.devolverSala(fila, columna).devolverPrimeraLLaveSala());
			// Eliminamos la llave del cesto de llaves
			planta.devolverSala(fila, columna).eliminarllave();			
		}
	}
	
	/**
	 * Metodo que realiza la accion del personaje en la puerta. Se comprueba
	 * si hay puerta, si la hay comprobamos si la llavea a utilizar a sido usada
	 * y si ha sido usada se tira si no se prueba. Si se cumplen las condiciones
	 * de apertura de la puerta, se abre la puerta.
	 * Pre: La instancia planta debe estar creada.
	 * Post: Intenta abrir la puerta, si se abre la puerta el estado de la
	 * puerta pasa a valor 2 (puerta abierta).
	 * Complejidad: O(n)
	 */
	public void accionPersonajePuerta(Planta planta){
		// Comprobamos si hay puerta
		if(comprbarSiHayPuerta(planta)==true){
			// Comprobamos que el personaje tiene llaves
			if(!this.llaves.estaVacia()){
				Llave key;
				// cogemos la llave
				key=this.llaves.getDatoCima();
				// Comprobamos si la llave ha sido usada
				if(planta.getDoor().buscarLlaveEnLlavero(key)==true){
					// Si la llave ha sido probada se tira
					this.llaves.sacarDato();
				}else{// si no ha sido probada se inserta en llavero y cerradura
					if(planta.getDoor().addLlaveLlavero(key)==true){
						planta.getDoor().borrarLlaveCerradura(key);
						// se elimina la llave del personaje
						this.llaves.sacarDato();
					}
					// si se cumplen condiciones abrir puerta
					if(!planta.hayLlavesEnCerradura()){
						// Condiciones de apertura de la puerta
						if(planta.getDoor().getCerradura().profundidad()<planta.getCondicionAperturaPuertaNivelArbol()
								&& planta.getDoor().getCerradura().numeroNodosInternos()>=planta.getDoor().getCerradura().numeroHojas()){
							// Estado 2 de la cerradura es abierto
							planta.getDoor().setEstadoPuerta(2);						
						}
					}					
				}
			}
		}
	}
	/**
	 * Pre: Las instancias visitados, etapa y dato deben de estar creadas
	 * Post: Metodo para saber si puedo insertar un nodo en visitados
	 * @param visitados
	 * @param etapa
	 * @param dato
	 * @return true si lo inserto
	 * Complejidad: O(n)
	 */
	private boolean aceptableRutaEscapeLider(List <Integer> visitados,Integer etapa,Integer dato){
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
	 * Pre: las instancias planta, visitados, origen,destinon,etapa,nesoEtapa deben de estar creadas
	 * Post: Metodo para calcular la ruta del lider
	 * @param planta
	 * @param visitados
	 * @param origen
	 * @param destino
	 * @param etapa
	 * @param nesoEtapa
	 * @return true cuando encuentra la ruta
	 * Complejidad: O(n2)
	 */
	public boolean movimientoLider(Planta planta,List <Integer> visitados, Integer origen,Integer destino,
			Integer etapa, List <Integer> nesoEtapa){
			
			if (nesoEtapa.contains(4)){
				nesoEtapa.clear();
				nesoEtapa.add(0);
			}
			
			boolean exito=false;
			List <Integer> visitadosAux=new ArrayList<Integer>();
	   	  // Donde guardaremos los adyacentes a un nodo
	   	  	Set <Integer> ady;
	   	  	Integer n;
	   	  // Hara de auxiliar
	   	  	Integer w;
	   	  	ady=new TreeSet<Integer>();
	   	  // insertamos en nodo visitado en la lista de visitados   	 
	   	  // Guardamos los adyacentes del vertice en ady
	   	  	planta.getLaberinto().getGrafo().adyacentes(origen, ady);	   	  	
	   	  	
	   	  	while (!ady.isEmpty() && !exito) {
	   	  		w=nesoElegir(ady,origen,nesoEtapa,planta);	   	  		
	   	  	    ady.remove(w);
	   	  		if(aceptableRutaEscapeLider(visitados,etapa,w)){
	   	  			for (int i = 0; i < etapa; i++) {
						visitadosAux.add(visitados.get(i));
					}
		   			visitados.clear();
		   			for (int i = 0; i < etapa; i++) {
						visitados.add(visitadosAux.get(i));
					}
		   			visitados.add(w);
		   			if(w==destino){
		   				exito=true;
		   			}else{ 
		   				n=nesoEtapa.get(0);
		   				nesoEtapa.clear();
		   				n++;
		   				nesoEtapa.add(n);
		   				exito=movimientoLider(planta,visitados,w,destino, etapa+1, nesoEtapa);	   					
		   			}		   			
	   	  		}		
	   	  	}
			return exito;	   		 
	     }
	
	/**
	 * Pre: las instancias ady,origen,nesoEtapa y planta deben de estar creadas
	 * Post: Metodo para que escoja en cada sala la opcion NESO
	 * @param ady
	 * @param origen
	 * @param nesoEtapa
	 * @param planta
	 * @return la sala elegida
	 * Complejidad: O(n)
	 */
     private Integer nesoElegir(Set <Integer> ady,Integer origen,List<Integer> nesoEtapa,Planta planta){
    	 Integer n=0;
    	 Integer adyacenteElegido=0;
    	 boolean enc=false;
    	 int i=0;
    	 while(i<4 && !enc) {
			if(nesoEtapa.contains(0)){
				adyacenteElegido=origen-planta.getAncho();
				if(ady.contains(adyacenteElegido)) enc=true;
			  }else if(nesoEtapa.contains(1)){
				  adyacenteElegido=origen+1;
					if(ady.contains(adyacenteElegido)) enc=true;
			       }else if(nesoEtapa.contains(2)){
			    	   adyacenteElegido=origen+planta.getAncho();
					      if(ady.contains(adyacenteElegido)) enc=true;
			             }else if(nesoEtapa.contains(3)){
			            	 adyacenteElegido=origen-1;
					           if(ady.contains(adyacenteElegido)) enc=true;
				               }
			i++;
			if(!enc){
				n=nesoEtapa.get(0);
				nesoEtapa.clear();
				n++;
				nesoEtapa.add(n);
			}			
			if (nesoEtapa.contains(4)){
				nesoEtapa.clear();
				nesoEtapa.add(0);
			}
    	  }
		return adyacenteElegido;    	 
     }
}
