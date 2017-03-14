/**

	INTRODUCCIÓN A LA PROGRAMACIÓN / FUNDAMENTOS DE PROGRAMACIÓN
	Curso 2011/2012

	Nombre: main.cpp
	Descripción: Proyecto Reflekt
	Autor: Miguel Angel Holgado Ceballos
	Fecha:	23/01/2012

*/

#include <iostream>
#include "juegoTAD.h"

using namespace std;

void ModuloDePruebasCasillas(){
	int colorido=3;
	Casilla ficha;
	ficha=CrearFicha(colorido);
	cout << "Color y estado de la ficha: ";
	cout << ColorFicha(ficha);
	cout << EstadoFicha(ficha) << endl;
	CambiarEstadoFicha(ficha);
	cout << "Color y estado de la ficha despues de cambiar estado: ";
	cout << ColorFicha(ficha);
	cout << EstadoFicha(ficha) << endl;
	cout << endl;
}
void ModuloDePruebasEntorno(){
	// Matriz de tipo MatrizEnteros, que se usará para cargar los colores
	// del archivo *.cnf
		MatrizEnteros t;
		TipoZona Zona;
	// Variables de tipo TipoMensaje para mostrar texto
		TipoMensaje cad="FIN ModuloDePruebasEntorno()";
		TipoMensaje msg="Desplazate por el tablero o pulsa Esc";
    // Variables de tipo entero para el archivo *.cnf
		int n_filas;
		int n_columnas;
		int n_fichas;
		int tiempo_juego;
		int borrar;
		int comodin;
		int aleatorio;
	// Variables de tipo entero para movernos por el tablero
		int fil;
		int col;
	// Variable tipo entero para el color de las fichas en la
	// Matriz de enteros
		int valor;
	// Buleano para salir del bucle del movimiento por el tablero
		bool salir=false;
	// Variable TipoTecla para las teclas del teclado habilitadas
	// en el juego
		TipoTecla tecla;
	// Variable tipo Zona, posición en la ventana gráfica donde
	// colocar un texto
		Zona=Zona1;
	// Carga del archivo *.cnf
		cout << "Si devuelve '1' la carga es correcta: "<< TEntornoCargarConfiguracion (n_filas,
									 n_columnas,
									 n_fichas,
				                     tiempo_juego,
				                     borrar,
				                     comodin,
				                     aleatorio,
				                     t) << endl;
		cout << "Datos del archivo de cnf devueltos: " << n_filas << " " << n_columnas
		<< " " << n_fichas << " " << tiempo_juego << " " << borrar
		<< " " << comodin << " " << aleatorio << endl;
		// Inicia el entorno gráfico teniendo en cuenta el nº de filas y columnas
		// del archivo *.cnf
		TEntornoIniciar (2*n_filas, n_columnas);
		 for(fil=0;fil<2*n_filas;fil++){
					  for(col=0;col<n_columnas;col++){
						  if(fil<(2*n_filas/2)){
							  valor=(t[fil][col]);
		// Coloca las fichas en la parte superior del tablero de la ventana
		//gráfica según su color en la matriz
							  TEntornoPonerCasilla (fil, col, valor, 0);
						  }
						  else if(fil>=(2*n_filas/2)){
							  valor=(t[fil][col]);
		// Coloca las fichas en la parte inferior del tablero de la ventana
		//gráfica según su color en la matriz
							  TEntornoPonerCasilla (fil, col, valor, 1);
						  }
					  }
				}
		 TEntornoPonerCasilla (7, 0, 1, 1);
	// Este modulo activa una casilla a partir de una posicion dada
		 TEntornoActivarCasilla (0, 0);
		 usleep(PAUSA);
		 usleep(PAUSA);
		 usleep(PAUSA);
		 usleep(PAUSA);
	// Este modulo desactiva una casilla a partir de una posicion dada
		 TEntornoDesactivarCasilla(0, 0);
		 usleep(PAUSA);
	// Este modulo marca una casilla a partir de una posicion dada
		 TEntornoMarcarCasilla (0, 0);
		 usleep(PAUSA);
		 TEntornoDesactivarCasilla(0, 0);
		 usleep(PAUSA);
	// Este modulo hace parpadear una casilla a partir de su posicion segun fila y columna,
	// valor de la ficha y si está en la parte superior del tablero o la reflejada
		 TEntornoParpadearCasilla(0,0,0,0);
		 usleep(PAUSA);
	// Este modulo hace parpadear dos casillas a partir de su posicion segun fila y columna
	// valor de la ficha
		 TEntornoParpadearDosCasillas(0, 0, 0, 1, 0, 1);
		 usleep(PAUSA);
		 TEntornoPonerCasilla (0, 0, 1, 0);
		 TEntornoPonerCasilla (0, 1, 0, 0);
		 usleep(PAUSA);
		 TEntornoEliminarCasilla(0, 0);
		 TEntornoEliminarCasilla(7, 0);
	// Posición inicial en el tablero visual
		 fil=0;
		 col=0;
		 TEntornoActivarCasilla(fil, col);
	// Muestra un mensaje en una zona dada en la ventana gráfica
		 TEntornoMostrarMensaje (Zona1, msg);

	// Bucle while para movernos por el tablero, para salir del bucle solo hay que
	// pulsar la tecla de Esc
		  while (!salir) {

		       tecla = TEntornoLeerTecla();

			    if(tecla==TDerecha){
				   TEntornoDesactivarCasilla(fil, col);
				   					   if (col < n_columnas-1) col++;
				   					   TEntornoActivarCasilla(fil, col);
				   					   TEntornoRefrescarFrontera(2*n_filas, n_columnas);
			   }
			   else if(tecla==TIzquierda){
				   TEntornoDesactivarCasilla(fil, col);
				   					   if (col > 0) col--;
				   					   TEntornoActivarCasilla(fil, col);
				   					   TEntornoRefrescarFrontera(2*n_filas, n_columnas);
			   			   }
			   else if(tecla==TArriba){
				   TEntornoDesactivarCasilla(fil, col);
				   					   if (fil > 0) fil--;
				   					   TEntornoActivarCasilla(fil, col);
				   					   TEntornoRefrescarFrontera(2*n_filas, n_columnas);
			   			   			   }
			   else if(tecla==TAbajo){
				   TEntornoDesactivarCasilla(fil, col);
				   					   if (fil < (2*n_filas-1)/2) fil++;
				   					   TEntornoActivarCasilla(fil, col);
				   					   TEntornoRefrescarFrontera(2*n_filas, n_columnas);
			   			   			   			   }
			   else if(tecla==TSalir){
			   					   salir=true;
			   				   }
		  }
		 TEntornoMostrarMensajeFin (cad);
		 usleep(PAUSA);
		 usleep(PAUSA);
		 usleep(PAUSA);
		 usleep(PAUSA);
		 TEntornoTerminar();
		 allegro_exit();
}

void ModuloDepruebasTablero(){
	TipoMensaje msg="¡¡¡¡ MIRAR PRUEBA EN CONSOLA!!!!";
	TipoZona Zona;
	// Variables de tipo entero para manejar los módulos del tableroTAD
	// y el entorno.
	int comodin;
	int tiempo;
	int aleatorio;
	int puntos=0;
	int filas=6;
	int columnas=6;
	int NumColoresMax=6;
	int max_borrar=8;
	// Variable TipoZona a la que se le asigna un area en la ventana gráfica.
	Zona=Zona1;
	// Matriz de tipo enteros para cargar colores desde el *.cnf.
	MatrizEnteros M;
	// Variable tipo Tablero.
	Tablero Tab;
	// Inicia el entorno gráfico.
	TEntornoIniciar (filas, columnas);
	TEntornoMostrarMensaje (Zona, msg);
	InicializarTablero (Tab);
	cout << "Inicializamos un tablero con 0 filas y 0 columnas útiles: ";
	cout << NumFilas(Tab) << " ";
	cout << NumColumnas(Tab) << endl;
	CrearTableroAleatorio (Tab, filas, columnas, NumColoresMax);
	// pone en el tablero de la ventana visual
	// las fichas que hay en Tab
	ActualizarTableroVisual(Tab);
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	cout << "creamos un tablero aleatorio de 6x6: ";
	MostrarTablero(Tab, filas, columnas);
	cout << endl;
	// Insertamos fichas aleatorias nuevas en Tab en las posiciones
	InsFichEnTableroSup (Tab, 1, 3, NumColoresMax);
	InsFichEnTableroInf (Tab, 4, 3, NumColoresMax);
	// Colocamos las nuevas fichas insertadas en Tab en el
	// tablero visual
	TEntornoPonerCasilla (1, 3, ColorFichEnTablero (Tab, 1, 3), 0);
	// Parpadeo de dos casillas
	TEntornoParpadearDosCasillas(1, 0, 2, 0, ColorFichEnTablero (Tab, 1, 0), ColorFichEnTablero (Tab, 2, 0));
	// intercambio de dos fichas
	IntercambiarDosFichas(Tab, 1, 0, 2, 0);
	cout << "insertamos 2 fichas aleatorias en posiciones(1, 3) y (4, 3) "
			"e intercambiamos de posición las fichas en posición (1, 0) y (2, 0):";
	MostrarTablero(Tab, filas, columnas);
	cout << endl;
	usleep(PAUSA);
    usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	VaciarFichEnTablero (Tab, 1, 3);
	VaciarFichEnTablero (Tab, 4, 3);
	cout << "Cambio de estado de las fichas (1, 3) y (4, 3): ";
	MostrarTablero(Tab, filas, columnas);
	cout << endl;
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	// Este modulo cambia el estado de las fichas que sean iguales a las reflejadas
	// a false
	TableroFichIgulFals (Tab);
	cout << "Cambio de estado a false de fichas y sus reflejadas iguales: ";
	MostrarTablero(Tab, filas, columnas);
	cout << endl;
	OcuparCasillasVacias(Tab, NumColoresMax, puntos);
	cout << "Se van copiando las casillas no borrables sobre las borrables "
			"y se insertan nuevas fichas: ";
	MostrarTablero(Tab, filas, columnas);
	cout << endl;
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	IntercambiarDosFichas(Tab, 1, 3, 1, 4);
	cout << "*.cnf cargado con exito: " << TEntornoCargarConfiguracion (filas,
								         columnas,
								         NumColoresMax,
			                             tiempo,
			                             max_borrar,
			                             comodin,
			                             aleatorio,
			                             M) << endl;
	filas=3; // mitad de las filas a crear es 3 por que queremos 6
	columnas=6;
	CambiarNumFilCol(Tab, filas, columnas);
	PasarColores(Tab, M);
	// Hay que volver a pintar las fichas en el tablero visual
	// Como se puede apreciar los bitmaps no son todos iguales
	// por lo que se ven superpuestos, para borrar las fichas
	// viejas antes de poner las nuevas habrá que usar un modulo
	// para el REFLEKT básico no hace falta
	//ActualizarTableroVisual(Tab, 2*filas, columnas);
	cout << "Tablero cargado desde *.cfn: ";
	MostrarTablero(Tab, 2*filas, columnas);
	cout << endl;
	// Muestra en la ventana grafica puntos, fichas borradas y fichas a borrar
	//Puntuacion(puntos, max_borrar);
	cout << "Número de filas y columnas útiles: ";
	cout << NumFilas(Tab) << " ";
	cout << NumColumnas(Tab) << endl;
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	usleep(PAUSA);
	TEntornoTerminar();
	allegro_exit();
}

int main () {
	Juego Jug;
	//ModuloDePruebasCasillas();
	//ModuloDePruebasEntorno();
	//ModuloDepruebasTablero();
	Partida(Jug);
	return 0;
}
