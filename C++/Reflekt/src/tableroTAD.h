//tableroTAD.h//
#ifndef TABLERO
#define TABLERO
#include "casillaTAD.h"
#include "entorno.h"

// Estructura auxiliar matriz de enteros colores
// con sus dimensiones máximas
int const Max_fil=10;
int const Max_col=8;
typedef int MatrizEnteros [Max_fil][Max_col];

// Estructura contenedor de casillas de 10 filas por 8 columnas
int const Maxfilas=10;
int const Maxcolumnas=8;
typedef Casilla TableroMax [Maxfilas][Maxcolumnas];
typedef struct Tablero{
	TableroMax table;
	// filas ocupadas con información util
	int nfilas;
	// columnas ocupadas con información util
	int ncolumnas;
}Tablero;

// Pre:
// Post: Inicializa un tablero con filas y columnas ocupadas con
// informacióm util a cero.
// Devuelve nada.
// Complejidad O(1).
void InicializarTablero (Tablero &Tab);

// Pre: Inicializado un tablero correctamente.
// Post: Creamos un tablero de fichas aleatorias definiendo las filas y columnas útiles
// y un máximo número de colores(fichas de colores diferentes).
// Devuelve nada.
// Complejidad O(n*n) suponemos compljidad de rand() es O(1).
void CrearTableroAleatorio (Tablero &Tab, int filas, int columnas, int NumColoresMax);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Insertamos una ficha nueva en la parte superior del tablero
// y NumColoresMax nos limitará el valor máximo que puede tomar esa ficha.
// Devuelve nada.
// Complejidad O(1).
void InsFichEnTableroSup (Tablero &Tab, int Fil, int Col, int NumColoresMax);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Insertamos una ficha nueva en la parte superior del tablero
// y NumColoresMax nos limitará el valor máximo que puede tomar esa ficha.
// Devuelve nada.
// Complejidad O(1).
void InsFichEnTableroInf (Tablero &Tab, int Fil, int Col, int NumColoresMax);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Cambiamos el estado de la ficha (este cambio será siempre a false).
// Devuelve nada.
// Complejidad O(1).
void VaciarFichEnTablero (Tablero &Tab, int Fil, int Col);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Devuelve el color de la Casilla (ficha).
// Devuelve entero.
// Complejidad O(1).
int ColorFichEnTablero (Tablero Tab, int Fil, int Col);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Comprueba si son iguales los colores de cada ficha
// de la parte superior del tablero con las reflejadas en el inferior y si es así
// cambia su estado a false y devuelve que hay fichas iguales.
// Devuelve bool.
// Complejidad O(n*n).
bool TableroFichIgulFals (Tablero &Tab);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Las fichas que están a true ocupan la/s casilla/s
// de la/s ficha/s que están a false de la misma columna.
// además añade las nuevas fichas en los huecos dejados en la fila cero y
// fila máxima-1
// Devuelve nada.
// Con los conocimientos dados no podemos detenminarla ya que tiene recursividad O(?).
void OcuparCasillasVacias(Tablero &Tab, int NumColoresMax, int &puntos);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Intercambia dos fichas dadas de posicion en el tablero,
// también lo hace en el tablero gráfico.
// devuelve nada
// Complejidad O(1), suponemos que TEntornoEliminarCasilla
// y TEntornoPonerCasilla tienen complejidad O(1).
void IntercambiarDosFichas(Tablero &Tab, int Fil1, int Col1, int Fil2, int Col2);

// Pre: Dada una matriz de enteros de Max_fil y Max_col.
// Post: Se le da el valor cero a cada una de las posiciones de la matriz.
// Devuelve nada.
// Complejidad O(n*n).
void InicializarMatrizAuxiliar(MatrizEnteros &M);

// Pre: Inicializado un tablero correctamente.
// Post: Pasa los valores de los colores del archivo *.cnf cargados
// en una matriz de enteros al Tablero &Tab, decir que se cargan con estado a true.
// Devuelve nada.
// Complejidad O(n*n).
void PasarColores(Tablero &Tab, MatrizEnteros M);

// Pre: Inicializado y creado un tablero correctamente.
// Post: Coloca cada ficha(bitmap) en el tablero visual
// deacuerdo con su valor numérico en el tablero.
// Devuelve nada.
// Complejidad O(n*n).
void ActualizarTableroVisual(Tablero &Tab);

// Pre:
// Post: devuelve el número de filas útiles.
// Devuelve entero.
// Complejidad O(1).
int NumFilas(Tablero Tab);

// Pre:
// Post: Devuelve el número de columnas útiles.
// Devuelve entero.
// Complejidad O(1).
int NumColumnas(Tablero Tab);

// Pre: Inicializado un tablero correctamente.
// Post: crea un tablero con un número de filas y columnas útiles
// en caso de no ser un tablero aleatorio.
// Devuelve entero.
// Complejidad O(1).
void CambiarNumFilCol(Tablero &Tab, int filasup, int Column);

///////////////////////////////////////////////////////////////////////////////
// Modulo auxiliar para las pruebas, no afecta al juego. Es para las pruebas///
///////////////////////////////////////////////////////////////////////////////
// Pre: Inicializado y creado un tablero correctamente.
// Post: Muestra por consola el tablero (distribucion de fichas
// con sus colores y estados.
// Devuelve nada.
// Complejidad O(n*n).
void MostrarTablero(Tablero Tab, int fila, int col);

#endif
