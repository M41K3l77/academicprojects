// desarrollo de las cabeceras de tableroTAD.h

#include <iostream>
#include <allegro.h>
#include "casillaTAD.h"
#include "tableroTAD.h"

using namespace std;

// inicializa un tablero con filas y columnas útiles a cero
void InicializarTablero (Tablero &Tab){
	Tab.nfilas=0;
	Tab.ncolumnas=0;
}

void CrearTableroAleatorio (Tablero &Tab, int filas, int columnas, int NumColoresMax){
	Tab.nfilas=filas;
	Tab.ncolumnas=columnas;
	int i;
	int j;
	for (i=0;i<(Tab.nfilas);i++){
		for (j=0;j<Tab.ncolumnas;j++){
			// Se asignan las fichas aleatorias al tablero
			Tab.table[i][j]=CrearFicha(rand()%NumColoresMax);
			// Al crear fichas aleatorias se puede dar el caso de fichas iguales
			// antes de empezar el juego, para evitarlo usamos un while
			// que seguira insertando fichas en las casillas de la parte inferior
			// del tablero que sean iguales a su reflejada en la parte superior
			while((ColorFicha(Tab.table[i][j]) == ColorFicha(Tab.table[Tab.nfilas-1-i][j]))){
					InsFichEnTableroInf (Tab, Tab.nfilas-1-i, j, NumColoresMax);
				}
			 }
		}
}

void InsFichEnTableroSup (Tablero &Tab,int Fil, int Col, int NumColoresMax){
	Casilla y;
	// Creamos una ficha aleatoria
	y=CrearFicha(rand()%NumColoresMax);
	// Asignamos la ficha creada a una posición en el tablero ya dada
	Tab.table[Fil][Col]=y;
}

// Si bien este modulo es igual al anterior y ambos crean una ficha aleatoria y
// la insertan en una posición dada, en el módulo OcuparCasillasVacias
// me simplifica el razonamiento.
void InsFichEnTableroInf (Tablero &Tab, int Fil, int Col, int NumColoresMax){
	Casilla y;
	// Creamos una ficha aleatoria
	y=CrearFicha(rand()%NumColoresMax);
	// Asignamos la ficha creada a una posición en el tablero ya dada
	Tab.table[Fil][Col]=y;
}

// Cambia de estado de una ficha del tablero a false
void VaciarFichEnTablero (Tablero &Tab, int Fil, int Col){
	CambiarEstadoFicha(Tab.table[Fil][Col]);
}

// Nos dice el color de una ficha del tablero
int ColorFichEnTablero (Tablero Tab, int Fil, int Col){
	return ColorFicha(Tab.table[Fil][Col]);
}
// Recorre todas las columnas comparando sus casillas con las reflejadas y las pone a false
bool TableroFichIgulFals (Tablero &Tab){

	int i;
	int j;
	// Comenzamos suponiendo que ninguna ficha está en false
	bool HayFichasIguales=false;
	// Recorremos la parte superior del tablero comparando estas fichas
	// con las fichas de la parte inferior del tablero
		for (i=0;i<Tab.nfilas/2;i++){
			for(j=0;j<Tab.ncolumnas;j++){
				if((ColorFicha(Tab.table[i][j]) == ColorFicha(Tab.table[Tab.nfilas-1-i][j]))){
					// tener en cuenta que el unico cambio de estado es a false
					// Si hay fichas reflejadas iguales se cambian su estado
					CambiarEstadoFicha(Tab.table[i][j]);
					CambiarEstadoFicha(Tab.table[Tab.nfilas-1-i][j]);
					// si se encuentra almenos una pareja de fichas reflejadas
					// HayFichasIguales cambia a true
					HayFichasIguales=true;
			            }
			    }
		}
		return HayFichasIguales;
}
// Son ocupadas las Casillas vacias
void OcuparCasillasVacias(Tablero &Tab, int NumColoresMax, int &puntos){
	int i;
	int j;

	// cambié el orden de filas y columnas para evitar la sensacion de casillas cruzadas
	// cuando hay que borrar de dos columnas diferentes
	// Es decir en lugar de ir de izq a der y abajo a arriba
	// el bucle va de abajo hacia arriba y de izq a derecha
	   // Bucle para recorrer la parte superior del tablero
		for (j=0;j<Tab.ncolumnas;j++){
			for(i=(Tab.nfilas/2)-1;i>=0;i--){
				// En este if se mira desde la (filas/2)-1 hasta la fila 1
				if((EstadoFicha(Tab.table[i][j]) == false) && i>0){
					// se eliminan las fichas reflejadas en el tablero visual
					TEntornoEliminarCasilla(i, j);
					TEntornoEliminarCasilla(Tab.nfilas-1-i, j);
					// Efecto de pausa en el tablero visual se usara varias veces
					// en este módulo
					usleep(PAUSA);
					usleep(PAUSA);
					// Se copia la ficha superior sobre la casilla que tiene una ficha en false
					Tab.table[i][j]=Tab.table[i-1][j];
					// Se copia la ficha inferior sobre la casilla que tiene una ficha en false
					Tab.table[Tab.nfilas-1-i][j]=Tab.table[Tab.nfilas-i][j];
					// Se dibujan las nuevas fichas copiadas
					TEntornoPonerCasilla (i, j, ColorFichEnTablero (Tab, i, j), 0);
					TEntornoPonerCasilla (Tab.nfilas-1-i, j, ColorFichEnTablero (Tab, Tab.nfilas-1-i, j), 1);
					// Se cambian los estados de las casillas donde estaban las fichas que se
					// usaron para copiarlas y permitir que se sigan moviendo las fichas en
					// la columna
					CambiarEstadoFicha(Tab.table[i-1][j]);
					CambiarEstadoFicha(Tab.table[Tab.nfilas-i][j]);
			            }
				// en este if se mira la fila cero que es donde se insertan nuevas fichas
				// junto con filamaxima-1
				if((EstadoFicha(Tab.table[i][j]) == false) && i==0 ){
					// se eliminan las fichas reflejadas en el tablero visual
					TEntornoEliminarCasilla(0, j);
					TEntornoEliminarCasilla(Tab.nfilas-1, j);
					usleep(PAUSA);
					usleep(PAUSA);
					// Se insertan fichas aleatorias nuevas en el tablero y en el tablero visual
					InsFichEnTableroSup (Tab, 0, j, NumColoresMax);
					TEntornoPonerCasilla (0, j, ColorFichEnTablero (Tab, 0, j), 0);
				    InsFichEnTableroInf (Tab, Tab.nfilas-1, j, NumColoresMax);
				    TEntornoPonerCasilla (Tab.nfilas-1, j, ColorFichEnTablero (Tab, Tab.nfilas-1, j), 1);
				    usleep(PAUSA);
				    usleep(PAUSA);
				    // Cada vez que se inserten fichas nuevas se suman 10 puntos
				    puntos=puntos+10;
				    // En el caso de que las fichas nuevas insertadas sean iguales se
				    // las cambia de estado para su posterior eliminacion en una llamada recursiva de
				    // este módulo
				    if(ColorFicha(Tab.table[0][j]) == ColorFicha(Tab.table[Tab.nfilas-1][j])){
				    	CambiarEstadoFicha(Tab.table[0][j]);
				    	CambiarEstadoFicha(Tab.table[Tab.nfilas-1][j]);
				    }
				  }
			    }
		}
		// bucle con llamada recursiva solo si sigue habiendo fichas a false
		// y por lo tanto deben ser ocupadas.
		j=0;
		while(j<Tab.ncolumnas){
			i=(Tab.nfilas/2)-1;
			while(i>=0){
				if((EstadoFicha(Tab.table[i][j]) == false)){
				  OcuparCasillasVacias(Tab, NumColoresMax, puntos);
					}
			i--;
			}
		j++;
		}
}

void IntercambiarDosFichas(Tablero &Tab, int Fil1, int Col1, int Fil2, int Col2){
	// Creamos una ficha auxiliar para el intercambio
	Casilla aux;
	// Asignamos los valores de la primera ficha a intercambiar a la ficha auxiliar
	aux=Tab.table[Fil1][Col1];
	// Borramos la primera ficha en el tablero visual
	TEntornoEliminarCasilla(Fil1, Col1);
	// asignamos los valores de la segunda ficha a intercambiar a la primera
	Tab.table[Fil1][Col1]=Tab.table[Fil2][Col2];
	// Dibujamos en el tablero visual la nueva primera ficha
	TEntornoPonerCasilla (Fil1, Col1, ColorFichEnTablero (Tab, Fil1, Col1), 0);
	// Borramos la segunda ficha en el tablero visual
	TEntornoEliminarCasilla(Fil2, Col2);
	// Le asignamos los valores de la ficha auxiliar a la segunda ficha
	Tab.table[Fil2][Col2]=aux;
	// Dibujamos en el tablero visual la nueva segunda ficha
	TEntornoPonerCasilla (Fil2, Col2, ColorFichEnTablero (Tab, Fil2, Col2), 0);
}

void InicializarMatrizAuxiliar(MatrizEnteros &M){
	int i;
	int j;
	// Bucles para asignar el valor cero a la matriz
	for (i=0;i<Max_fil;i++){
				for(j=0;j<Max_col;j++){
					M[i][j]=0;
					}
				}
}

// Pasar valores de matriz  de enteros colores a Matriz tipo casilla colores
void PasarColores(Tablero &Tab, MatrizEnteros M){
	int i;
	int j;
	// Bucles para crear las fichas según el *.cnf
	for (i=0;i<Tab.nfilas;i++){
				for(j=0;j<Tab.ncolumnas;j++){
					// Se asignan las fichas creadas según el *.cnf
					Tab.table[i][j]=CrearFicha(M[i][j]);
					}
				}
}

void ActualizarTableroVisual(Tablero &Tab){
	int fila;
	int col;
	// Color de la ficha
	int valor;
	// Pinta las fichas en el tablero visual según nuestro tablero
	for(fila=0;fila<Tab.nfilas;fila++){
		for(col=0;col<Tab.ncolumnas;col++){
			if(fila<(Tab.nfilas/2)){
			  valor=ColorFicha(Tab.table[fila][col]);
			  // Pinta las fichas de la parte superior del tablero
			  TEntornoPonerCasilla (fila, col, valor, 0);
		     }
			else if(fila>=(Tab.nfilas/2)){
			       valor=ColorFicha(Tab.table[fila][col]);
			       // Pinta las fichas en la parte inferior del tablero
				   TEntornoPonerCasilla (fila, col, valor, 1);
				   }
			 }
        }
}

int NumFilas(Tablero Tab){
	return Tab.nfilas;
}

int NumColumnas(Tablero Tab){
	return Tab.ncolumnas;
}

void CambiarNumFilCol(Tablero &Tab, int filasup, int Column){
	// Asigna filas y columnas útiles a nuestro tablero predeterminado en *.cnf
	Tab.nfilas=2*filasup;
	Tab.ncolumnas=Column;
}
/////////////////////////////////////////////////////////////////////////////////
// Modulo auxiliar para las pruebas, no afecta y no es necesario para el juego //
/////////////////////////////////////////////////////////////////////////////////
void MostrarTablero(Tablero Tab, int fila, int col){
	int i;
	int j;
	// Muestra tablero en consola
	for (i=0;i<fila;i++){
			cout << endl;
				for (j=0;j<col;j++){
					cout << ColorFicha(Tab.table[i][j]);
					cout << EstadoFicha(Tab.table[i][j]) << " ";
				}
			}
	cout << endl;
}
