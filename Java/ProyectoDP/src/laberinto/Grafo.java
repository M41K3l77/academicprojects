package laberinto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* @file grafo.h
* Declaracion de la clase grafo
* @author
*   <b> Profesores DP </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 11/12 </b>
*/
public class Grafo {
	private int MAXVERT;
	private static final int INFINITO = 9999;
	private static final int NOVALOR = -1;
	
	/** Numero de nodos del grafo */
    private int numNodos;        

    /** Vector que almacena los nodos del grafo */
    private int[] nodos;           

    /** Matriz de adyacencia, para almacenar los arcos del grafo */
    private int[][] arcos;  
	
	/** Matriz de Camino (Warshall - Path) */
    private boolean [][] warshallC;   

    /** Matriz de Costes (Floyd - Cost) */
    private int[][] floydC;   
	
    /** Matriz de Camino (Floyd - Path) */
    private int[][] floydP;

    /**
    * Metodo constructor por defecto de la clase grafo
    * @param "" No recibe parametros
    * @return No retorna ningun valor
    */
    public Grafo(int alto,int ancho) {
    	this.MAXVERT=alto*ancho;
        int x,y;
        numNodos=0;
        nodos=new int[MAXVERT];
        arcos = new int[MAXVERT][MAXVERT];
        warshallC = new boolean[MAXVERT][MAXVERT];
        floydC = new int[MAXVERT][MAXVERT]; 
        floydP = new int[MAXVERT][MAXVERT];
        for (x=0;x<MAXVERT;x++)
        	nodos[x]= NOVALOR;

        for (x=0;x<MAXVERT;x++)
            for (y=0;y<MAXVERT;y++){
                arcos[x][y]=INFINITO;
                warshallC[x][y]=false;
                floydC[x][y]=INFINITO;
                floydP[x][y]=NOVALOR;
            }
      
        // Diagonales
        for (x=0;x<MAXVERT;x++){
            arcos[x][x]=0;
            warshallC[x][x]=false;
            floydC[x][x]=0;
            //floydP[x][x]=NO_VALOR;
        }
    }
    
    /**
     * 
     * @return
     */
    public int getMAXVERT() {
		return MAXVERT;
	}

    /**
     * 
     * @param mAXVERT
     */
	public void setMAXVERT(int mAXVERT) {
		MAXVERT = mAXVERT;
	}
    
	/**
	 * 
	 * @return
	 */
	public int[][] getArcos() {
		return arcos;
	}

	/**
	 * 
	 * @param arcos
	 */
	public void setArcos(int[][] arcos) {
		this.arcos = arcos;
	}
	
    /**
    * Metodo que comprueba si el grafo esta vacio
    * @param "" No recibe parametros
    * @return Retorna un valor booleano que indica si el grafo esta o no vacio
    */
    public boolean esVacio () {
        return (numNodos==0);
    }

    /**
    * Metodo que inserta un nuevo arco en el grafo
    * @param origen es el nodo de origen del arco nuevo
    * @param destino es el nodo de destino del arco nuevo
    * @param valor es el peso del arco nuevo 
    * @return true si se pudo insertar
    */
    public boolean nuevoArco(int origen, int destino, int valor) {
        boolean resultado= false;
        //System.out.println("Estoy en nuevo arco"+" "+numNodos);
        if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos))	{
            arcos[origen][destino]=valor; 
            resultado=true;
        }
        return resultado;
    }

    /**
    * Metodo que borra un arco del grafo
    * @param origen es el nodo de origen del arco nuevo
    * @param destino es el nodo de destino del arco nuevo
    * @return true si se pudo borrar
    */
    public boolean borraArco(int origen, int destino) {
        boolean resultado = false;
        if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) {
        	arcos[origen][destino]=INFINITO;	
            resultado=true;
        }
        return resultado;
    }

    /**
    * Metodo que comprueba si dos nodos son adyacentes
    * @param origen es el primer nodo
    * @param destino es el segundo nodo
    * @return Retorna un valor booleano que indica si los dos nodos son adyacentes
    */
    public boolean adyacente (int origen, int destino) {
        boolean resultado= false;
        if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos))      
    		resultado = (arcos[origen][destino]!=INFINITO); 
        return resultado;
    }

    /**
    * Metodo que retorna el peso de un arco
    * @param origen es el primer nodo del arco
    * @param destino es el segundo nodo del arco
    * @return Retorna un valor entero que contiene el peso del arco
    */
    public int getArco (int origen, int destino) {
        int arco=NOVALOR;
        if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) 	
    		arco=arcos[origen][destino];				     
        return arco;
    }

    /**
    * Metodo que inserta un nuevo nodo en el grafo
    * @param n es el nodo que se desea insertar
    * @return true si se pudo insertar
    */
    public boolean nuevoNodo(int n) {
        boolean resultado=false;

        if (numNodos<MAXVERT){
            nodos[numNodos]=n;
            numNodos++;
            resultado=true;
        }
        return resultado;
    }

    /**
    * Metodo que elimina un nodo del grafo
    * @param nodo nodo que se desea eliminar
    * @return true si se pudo borrar
    */
    public boolean borraNodo(int nodo) {
        boolean resultado=false;
    	int pos = nodo; 

        if ((numNodos>0) && (pos <= numNodos)) {
            int x,y;
            // Borrar el nodo
            for (x=pos; x<numNodos-1; x++){		
                nodos[x]=nodos[x+1];
    			System.out.println(nodos[x+1]);
    		}
            // Borrar en la Matriz de Adyacencia
            // Borra la fila
            for (x=pos; x<numNodos-1; x++)		
                for (y=0;y<numNodos; y++)
                    arcos[x][y]=arcos[x+1][y];
            // Borra la columna
            for (x=0; x<numNodos; x++)
                for (y=pos;y<numNodos-1; y++)	
                    arcos[x][y]=arcos[x][y+1];
            // Decrementa el numero de nodos
            numNodos--;
            resultado=true;
        }
        return resultado;
    }

    /**
    * Metodo que muestra el vector de nodos del grafo
    * @param "" No recibe parametros
    * @return No retorna ningun valor
    */
    public void mostrarNodos() {
        System.out.println("NODOS:");
        for (int x=0;x<numNodos;x++)
        	System.out.print(nodos[x] + " ");                   
        System.out.println();
    }

    /**
    * Metodo que muestra los arcos del grafo (la matriz de adyacencia)
    * @param "" No recibe parametros
    * @return No retorna ningun valor
    */
    public void mostrarArcos()
    {
        int x,y;

        System.out.println("ARCOS:");
        for (x=0;x<numNodos;x++) {
            for (y=0;y<numNodos;y++) {
                //cout.width(3);
                if (arcos[x][y]!=INFINITO)
                    System.out.format("%4d",arcos[x][y]);
                else
                    System.out.format("%4s","#");
            }
            System.out.println();
        }
    }


    /**
    * Metodo que devuelve el conjunto (en una cola) de nodos adyacentes al nodo actual
    * @param origen es el nodo actual
    * @param ady En este conjunto se almacenar�n los nodos adyacentes al nodo origen
    * @return No retorna ningun valor
    */
    public void adyacentes(int origen, Set<Integer> ady){
       if ((origen >= 0) && (origen < numNodos)) {
    		for (int i=0;i<numNodos;i++) {
           	 	if ((arcos[origen][i]!=INFINITO) && (arcos[origen][i]!=0))	
              		ady.add(i);	
          	}
    	}
    }

    
    /**
     * Metodo que muestra la matriz de Warshall
     * @param "" No recibe parametros
     * @return No retorna ningun valor
     */
     public void mostrarPW()
     {
         int x,y;

         System.out.println("warshallC:");
         for (x=0;x<numNodos;x++) {
             for (y=0;y<numNodos;y++)
                 System.out.format("%6b",warshallC[x][y]);
             System.out.println();
         }
     }

     /**
     * Metodo que muestra las matrices de coste y camino de Floyd
     * @param "" No recibe parametros
     * @return No retorna ningun valor
     */
     public void mostrarFloydC()
     {
         int x,y;
         System.out.println("floydC:");
         for (y=0;y<numNodos;y++) {
             for (x=0;x<numNodos;x++) {
                 System.out.format("%4d",floydC[x][y]);
             }
             System.out.println();
         }

         System.out.println("floydP:");
         for (x=0;x<numNodos;x++) {
             for (y=0;y<numNodos;y++) {
                 System.out.format("%4d",floydP[x][y]);
             }
             System.out.println();
         }
     }

     /**
     * Metodo que realiza el algoritmo de Warshall sobre el grafo
     * @param "" No recibe parametros
     * @return No retorna ningun valor
     */
     public void warshall() {
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<numNodos;i++)
             for (j=0;j<numNodos;j++)
                 warshallC[i][j]=(arcos[i][j]!=INFINITO);

         // Iterar
         for (k=0;k<numNodos;k++)
             for (i=0;i<numNodos;i++)
                 for (j=0;j<numNodos;j++)
                     warshallC[i][j]=(warshallC[i][j] || (warshallC[i][k] && warshallC[k][j]));
     }

     /**
     * Metodo que realiza el algoritmo de Floyd sobre el grafo
     * @param "" No recibe parametros
     * @return No retorna ningun valor
     */
     public void floyd (){
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<numNodos;i++)
             for (j=0;j<numNodos;j++){
                 floydC[i][j]=arcos[i][j];
     			floydP[i][j]=NOVALOR; 	
     		}

         // Iterar
         for (k=0;k<numNodos;k++)
             for (i=0;i<numNodos;i++)
                 for (j=0;j<numNodos;j++)
                     if (i!=j)
                         if ((floydC[i][k]+floydC[k][j] < floydC[i][j])) {
                             floydC[i][j]=floydC[i][k]+floydC[k][j];		
                             floydP[i][j]=k;
                         }
     }

     /**
      * Metodo que devuelve el siguiente nodo en la ruta entre un origen y un destino
      * @param origen es el primer nodo
      * @param destino es el segundo nodo
      * @param origen es el primer nodo
      * @param sig parametro de entrada salida que devuelve el siguiente nodo en la ruta entre origen y destino
      * @return No retorna ningun valor
      */
      public int siguiente(int origen, int destino){
      	int sig=-1; // Si no hay camino posible
          if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) {
      		if (warshallC[origen][destino]){ // Para comprobar que haya camino
      	    	if (floydP[origen][destino]!=NOVALOR)
      				sig = siguiente(origen, floydP[origen][destino]);	
          		else
          			sig=destino;
      		}
      	}
          return sig;
      }

      /**
       * Metodo que guarda el camino minimo entre dos nodos
       * en una lista.
       * @param origen
       * @param destino
       * @return caminoMin
       * Complejidad: O(n)
       */
      public boolean caminoMinimo(int origen, int destino, List <Integer> rutaminima){
    	  boolean caminoMin=false;
    	  if(origen==destino){
    		  rutaminima.add(destino);
    		  caminoMin=true;
    	  }else{
    		  rutaminima.add(origen);
    		  caminoMin=caminoMinimo(this.siguiente(origen, destino), destino, rutaminima);
    	  }
		return caminoMin;    	  
      }
    
      /**
       * Metodo que muestra el nodo con mas caminos.
       * Complejidad: O(n2)
       */
      public void nodoMasCaminos(){
    	  int elCamino=0;
    	  int maxCaminos=0;
    	  for(int i=0;i<MAXVERT;i++){
    		  int sumatorioCaminos=0;
    		  for(int j=0;j<MAXVERT;j++){
    			  if(this.arcos[i][j]<INFINITO){
    				  sumatorioCaminos=sumatorioCaminos+this.arcos[i][j];
    			  }
    		  }
    		  if(sumatorioCaminos>maxCaminos){
    			  maxCaminos=sumatorioCaminos;
    			  elCamino=i;
    		  }
    	  }
    	  System.out.println("Camino mas frecuentado "+elCamino);
      }
	
      /**
       * Metodo que muestra los dos mas alejados entre si.
       * Complejidad: O(n)
       */
      public void dosNodosMasAlejadosEntreSi(){
    	  int fila=-1;
    	  int columna=-1;
    	  int distanciaMayor=0;
    	  int auxDistancia=0;
    	  for(int i=0;i<this.numNodos;i++){
    		  for(int j=0;j<this.numNodos;j++){
    			  auxDistancia=this.floydC[i][j];
    			  if(auxDistancia>distanciaMayor){
    				  distanciaMayor=auxDistancia;
    				  fila=i;
    		    	  columna=j;
    			  }
    		  }
    	  }
    	  System.out.println("distancia mayor: "+distanciaMayor);
    	  System.out.println("nodos: "+fila+" "+columna);
      }
      
      /**
       * Pre: la intancia n debe de estar creada
       * Metodo que muestra los n caminos mas cortos.
       * @param n
       * Complejidad: O(n2)
       */
      public void nCaminosMasCortos(int n){
    	  int mayor=-1;
    	  int l=0;//indice auxiliar
    	  List<Integer> listaArcos=new ArrayList<Integer>();
    	  for(int i=0;i<this.numNodos;i++){
    		  for(int j=i+1;j<this.numNodos;j++){
    			  if(i!=j && this.arcos[i][j]!=INFINITO){
    				  // Lista con distancia nodos adyacentes y
    				  // los dos nodos en cuestion
    				  listaArcos.add(this.arcos[i][j]);
    				  listaArcos.add(i);
    				  listaArcos.add(j);
    				  if(listaArcos.size()>(n*3)){
    					  for(int k=0;k<listaArcos.size();k=k+3){
    						  if(listaArcos.get(k)>mayor){
    							  mayor=listaArcos.get(k);
    							  l=k;
    						  }    						  
    					  }
    					  listaArcos.remove(l);
    					  listaArcos.remove(l);
    					  listaArcos.remove(l);
    					  mayor=-1;// hay que resetear el mayor
    				  }
    			  }
    		  }
    	  }
      }      
}

