/**

	INTRODUCCIÓN A LA PROGRAMACIÓN / FUNDAMENTOS DE PROGRAMACIÓN
	Curso 2011/2012

	Nombre: entorno.h
	Descripción: Especificación del TAD Entorno para el proyecto REFLEKT.
	     	 	 Encargado del manejo de la interfaz del juego.
	Autor:	Profesores de las asignaturas
	Fecha:	03/11/2011

*/


#ifndef ENTORNO_H_
#define ENTORNO_H_


#include <allegro.h>

// -------------------------------------------------------------
// Definicion de constantes públicas
// -------------------------------------------------------------
const int PAUSA = 300000;   //Tiempo en milisegundos que dura una pausa en un parpadeo
const int PAUSA2 = 500000;   //Tiempo en milisegundos que dura una pausa en el borrado
const int MAX_CADENA      = 20;

// Número de imágenes de fichas que se usan en el tablero y su significado
const int MAX_FICHAS      = 6;

// Dimensiones máximas y mínimas del tablero
const int MAX_DIMENSION = 10;
const int MIN_DIMENSION =  1;
const int MAX_NUM_FILAS		= 10;
const int MAX_NUM_COLUMNAS	= 8;

// Máximo tamaño de un mensaje a imprimir en el entorno
const int MAX_MENSAJE   = 50;

// -------------------------------------------------------------
// Definicion de tipos
// -------------------------------------------------------------

/**
 *   Este es el tipo devuelto por la operacion LeerTecla que indica la tecla
 *   de los cursores que se ha pulsado
 */
enum TipoTecla {TNada, TIzquierda, TDerecha, TArriba, TAbajo, TSalir, TEnter, TX};

/**
 *   Zonas en el entorno para imprimir mensajes. Existen 4 zonas delimitadas para imprimir un
 *   mensaje en la pantalla del entorno. Se pueden usar para imprimir mensajes de estado de
 *   diferentes tipos (p.e. fichas borradas, puntos obtenidos, etc.)
 */
enum TipoZona {Zona1, Zona2, Zona3, Zona4};


/**
 *   Tipo para los mensajes a imprimir en pantalla
 */
typedef char TipoMensaje [MAX_MENSAJE];


// -------------------------------------------------------------
// Declaración de interfaz (módulos) PÚBLICA
// -------------------------------------------------------------


/**
	PRE:  MIN_DIMENSION <= filas <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columnas <= MAX_NUM_COLUMNAS,
	POST: Inicia la pantalla de juego, incluyendo una rejilla cuadrada de filas*columnas
	   Devuelve:
	       true:  Si todo se inicio correctamente
	       false: Si hay algún problema al iniciar
*/
bool TEntornoIniciar (int filas, int col);


/**
	PRE:
	POST: Destruye el contexto del entorno gráfico.
*/
void TEntornoTerminar();


/**
	PRE: Archivo reflekt.cnf correcto y en la carpeta raíz del proyecto
	     (NO SE COMPRUEBAN ERRORES EN LOS DATOS)
	POST: Devuelve:
	         TRUE:  si se carga correctamente la configuración del juego,
		     FALSE: en caso contrario.

		Si la configuración se lee de forma correcta se devolverá:
		   n_filas:       	número de filas del tablero (sin contar el reflejo)
		   n_columnas:		número de columnas del tablero
		   fichas:  		número de fichas diferentes
		   tiempo:          tiempo en segundos que dura la partida
		   max_borrar:		número de fichas a borrar para ganar la partida
		   comodin:         número para posibles ampliaciones
		   aleatorio:       Si se genera la configuración inicial de forma aleatoria
		   	   	   	   	   	o se lee del archivo
		   casillas:        estructura que almacena la config. inicial
		   	   	   	   	    del tablero tanto si se ha cargado desde el fichero
		   	   	   	   	    como si se ha generado aleatoriamente

		Por defecto, el archivo reflekt.cnf se encuentra en el directorio  [proyecto].
 */
bool TEntornoCargarConfiguracion (int 		&n_filas,
								  int 		&n_columnas,
								  int       &fichas,
		                          int       &tiempo,
		                          int       &max_borrar,
		                          int 		&comodin,
		                          int       &aleatorio,
		                          int       casillas  [MAX_NUM_FILAS][MAX_NUM_COLUMNAS]);




/**
	PRE:  fila y columna son coordenadas válidas en el entorno.
	PRE:  MIN_DIMENSION <= filas <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columnas <= MAX_NUM_COLUMNAS,
	POST: Establece una casilla con coordenadas fila, columna como activa con un rectángulo rojo.
*/
void TEntornoActivarCasilla(int fila, int columna);


/**
 *  PRE:  MIN_DIMENSION <= fila <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columna <= MAX_NUM_COLUMNAS,
    PRE: fila y columna son coordenadas válidas en el entorno.
	POST: Establece una casilla con coordenadas fila, columna en su estado habitual, sin resaltar.
*/
void TEntornoDesactivarCasilla(int fila, int columna);

/**
 *  PRE:  MIN_DIMENSION <= fila <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columna <= MAX_NUM_COLUMNAS,
    PRE: fila y columna son coordenadas válidas en el entorno.
	POST: Marca una casilla con coordenadas fila, columna co un marco amarillo
	para indicar que ha sido seleccionada para un intercambio
*/
void TEntornoMarcarCasilla(int fila, int columna);


/**
	PRE:  MIN_DIMENSION <= fila <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columna <= MAX_NUM_COLUMNAS,
    PRE: fila y columna son coordenadas válidas en el entorno.
	POST: Coloca en una casilla la ficha indicada por "valor"
	si posicion es 0 la casilla tiene un color más vivo (para la parte
	superior del tablero) y si es 1, un tono más apagado (para la parte
	reflejada)
	*/
void TEntornoPonerCasilla (int fila, int columna, int valor, int posicion);

/**
	PRE:  MIN_DIMENSION <= fila <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columna <= MAX_NUM_COLUMNAS,
    PRE: fila y columna son coordenadas válidas en el entorno.
	POST: Provoca un efecto de parpadeo en la casilla que ocupa la
	posción (fila, columna) con la ficha indicada por valor
	si posicion es 0 la casilla tiene un color más vivo (para la parte
	superior del tablero) y si es 1, un tono más apagado (para la parte
	reflejada)
	*/
void TEntornoParpadearCasilla(int fila, int columna,int valor,int posicion);

/**
	PRE:  MIN_DIMENSION <= fila1,fila2 <= MAX_NUM_FILAS/2,
	PRE:  MIN_DIMENSION <= col1,col2 <= MAX_NUM_COLUMNAS,
    PRE: fila1, fila2, col1 y col2 son coordenadas válidas en el entorno
    y se ubican en la mitad superior del tablero.
	POST: Provoca un efecto de parpadeo de dos casillas que ocupan las
	posciones (fila1, col1)y (fila2,col2) con las fichas indicada por
	valor1 y valor2 respectivamente

	*/
void TEntornoParpadearDosCasillas(int fila1, int col1,int fila2, int col2, int valor1,int valor2);


/**
 * 	PRE:  MIN_DIMENSION <= fila <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columna <= MAX_NUM_COLUMNAS,
    PRE: fila y columna son coordenadas válidas en el entorno.
	POST: Borra de una casilla la ficha que la ocupa
*/
void TEntornoEliminarCasilla (int fila, int columna);


/**
PRE: El módulo recibe la zona en la que imprimir el mensaje (TipoZona) y una cadena de caracteres.
POST:Escribe el mensaje pasado por parámetro en el entorno, concretamente, en la zona especificada.
*/
void TEntornoMostrarMensaje (TipoZona zona, TipoMensaje msg);


/**
PRE:  cad es una cadena de caracteres a mostrar
POST: Muestra un mensaje para indicar que se ha conseguido (o no) el objetivo.
*/
void TEntornoMostrarMensajeFin (TipoMensaje cad);


/**
PRE:
POST:Devuelve un valor enumerado de TipoTecla con la tecla que se ha pulsado
*/
TipoTecla TEntornoLeerTecla();


/*  PRE:  MIN_DIMENSION <= filas <= MAX_NUM_FILAS,
	PRE:  MIN_DIMENSION <= columnas <= MAX_NUM_COLUMNAS,
	representan el tamaño completo del tablero, incluyendo la parte reflejada
	Dibuja una barra de color rojo que separa la parte superior del tablero
	de la zona reflejada
 * */
void TEntornoRefrescarFrontera(int filas, int columnas);

/* POST: Dibuja inicialmente la barra que mide el transcurso del tiempo de juego
 * */
void TEntornoTiempoInicio();

/* 0 <= tam <= 25
 * Acorta la barra roja que mide el transcurso del tiempo de juego
*/
void TEntornoMedirTiempo(int tam);

#endif

