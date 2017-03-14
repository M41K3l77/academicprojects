/**

	INTRODUCCIÓN A LA PROGRAMACIÓN / FUNDAMENTOS DE PROGRAMACIÓN
	Curso 2011/2012

	Nombre: entorno.cpp
	Descripción: Implementación del TAD Entorno para el proyecto REFLEKT.
	     	 	 Encargado del manejo de la interfaz del juego.
	Autor:	Profesores de las asignaturas
	Fecha:	03/11/2011

*/

#include "entorno.h"
#include "reloj.h"
#include <iostream>
#include <fstream>
#include <cstring>


using namespace std;

// -------------------------------------------------------------
// Definicion de constantes privadas
// -------------------------------------------------------------
// Definición de colores que usa el entorno
const int COLOR_BLANCO    = 0;
const int COLOR_ROJO      = 1;
const int COLOR_NEGRO     = 2;
const int COLOR_AMARILLO  = 3;
const int COLOR_VERDE     = 4;

// Tamaño de la ventana del juego
const int ANCHO_VENTANA   = 800;
const int ALTO_VENTANA    = 600;

// Definicón de constantes para posicionar los números en el tablero
const int DISTANCIA_COLS  = 55;  // Distancia entre columnas
const int DISTANCIA_FILAS = 55;  // Distancia entre filas
const int ORIGEN_X        = 20;  // Origen de las x
const int ORIGEN_Y        = 20;  // Origen de las y
// -------------------------------------------------------------
// Definición del reloj utilizado para cronometrar el tiempo
// -------------------------------------------------------------



// Imágenes de fichas que se usan en el tablero
BITMAP *fichas[MAX_FICHAS];  // vector que contiene enlace a las imágenes de las fichas
BITMAP *fichasreflejo[MAX_FICHAS];

// -------------------------------------------------------------
// Declaración de módulos PRIVADOS
// -------------------------------------------------------------



// define el color en función de los valores makecol - allegro library
int makecolor2 (int color);

// Dibuja en la pantalla el borde de un tablero con el ancho indicado
void TEntornoPintarRejilla (int f, int c);

// Invierte fila/columna.  El tablero gráfico (entorno) se dibuja por
// Columnas/Filas, mientras en C++ se trata con matrices en Filas/Columnas.
// Para que sea transparente al uso de matrices se invierte en el entorno
void InvertirFC (int &fila, int &columna);


// -------------------------------------------------------------
// Definición de módulos PRIVADOS
// -------------------------------------------------------------

int makecolor2 (int color) {

  int col;

  switch (color) {
    case COLOR_BLANCO:   col = makecol( 255, 255, 255); break;
    case COLOR_ROJO:     col = makecol( 255,   0,   0); break;
    case COLOR_NEGRO:    col = makecol( 100, 100, 100); break;
    case COLOR_AMARILLO: col = makecol( 200, 200,  50); break;
    default:             col = makecol( 255, 255, 255); break; //color blanco
  }

  return col;
}


void TEntornoPintarRejilla (int filas, int columnas) {
    // Comenzamos en la 0,0
	int  i,mitad;
	int  color = COLOR_ROJO;
	acquire_screen();

	// horizontales
	mitad = filas/2;
	for (i = 0; i < mitad; i++)
	  line( screen, ORIGEN_X+0*DISTANCIA_COLS, ORIGEN_Y+i*DISTANCIA_FILAS, ORIGEN_X+columnas*DISTANCIA_COLS, ORIGEN_Y+i*DISTANCIA_FILAS, makecol( 255, 255, 255));
	
	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS-1, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS-1, makecolor2(color));
	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS, makecolor2(color));
	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS+1, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS+1, makecolor2(color));

	for (i = mitad; i <= filas; i++)
		  line( screen, ORIGEN_X+0*DISTANCIA_COLS, ORIGEN_Y+i*DISTANCIA_FILAS, ORIGEN_X+columnas*DISTANCIA_COLS, ORIGEN_Y+i*DISTANCIA_FILAS, makecol( 255, 255, 255));

	//verticales
	for (i = 0;i <= columnas; i++)
	  line( screen, ORIGEN_X+i*DISTANCIA_COLS, ORIGEN_Y+0*DISTANCIA_FILAS, ORIGEN_X+i*DISTANCIA_COLS, ORIGEN_Y+filas*DISTANCIA_FILAS, makecol( 255, 255, 255));
	
	textout_ex (screen, font, "***   REFLEKT   ***",    500,  50, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "PROYECTO DE PROGRAMACIÓN",  500,  70, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "TECLAS:",                   500, 120, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Arriba: Fecha arriba",      500, 140, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Abajo:  Flecha abajo",      500, 160, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Drcha:  Flecha derecha",    500, 180, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Izqda:  Flecha izquierda",  500, 200, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Salir:  Escape",            500, 220, makecol(255, 255, 255), makecol(0, 0, 0));
	textout_ex (screen, font, "Encender/Apagar: Enter",    500, 240, makecol(255, 255, 255), makecol(0, 0, 0));

	release_screen();

}

void InvertirFC (int &fila, int &columna) {

	int aux = columna;
	columna = fila;
	fila    = aux;

}




// -------------------------------------------------------------
// Definición de la interfaz PÚBLICA
// -------------------------------------------------------------

bool TEntornoIniciar (int filas, int col) {

  char nomfig[20];

  // Iniciar el entorno
  allegro_init();
  install_keyboard();
  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0);

  // Pintar regilla
  TEntornoPintarRejilla(filas, col);

  //inicio del vector de fichas a NULL
  for (int i = 0; i < MAX_FICHAS; i++) {
    sprintf(nomfig, "f%d.bmp", i);  // asigna el nombre de la figura a  cargar
    fichas[i] = load_bitmap(nomfig, NULL);
    //cout << "cargada ficha: " << "f" << i << ".bmp" << endl;
  }
  //inicio del vector de fichas reflejadas a NULL
    for (int i = 0; i < MAX_FICHAS; i++) {
      sprintf(nomfig, "fr%d.bmp", i);  // asigna el nombre de la figura a  cargar
      fichasreflejo[i] = load_bitmap(nomfig, NULL);
      //cout << "cargada ficha: " << "fr" << i << ".bmp" << endl;
    }

  return true;
}


void TEntornoTerminar () {

  for (int i = 0; i < MAX_FICHAS; i++)
     if (fichas[i] != NULL)
      destroy_bitmap(fichas[i]);

}

// Cargar la configuración inicial desde un archivo
bool TEntornoCargarConfiguracion (int     &n_filas,
								  int     &n_columnas,
								  int     &n_fichas,
		                          int     &tiempo_juego,
		                          int 	  &max_borrar,
		                          int     &comodin,
		                          int     &aleatorio,
		                          int     casillas  [MAX_NUM_FILAS][MAX_NUM_COLUMNAS]) {

	ifstream  entrada;
	char      cad[MAX_DIMENSION + 1];
	int i,j;

	entrada.open("reflekt.cnf");
	if (entrada == 0 ){
		cout << "Fichero de configuración no encontrado (<proyecto>/reflekt.cnf)." << endl;
		cout << "Formato:"           << endl;
		cout << "\t[Num- de filas del tablero]" << endl;
		cout << "\t[Num- de columnas del tablero]" << endl;
		cout << "\t[Num- de fichas diferentes]" << endl;
		cout << "\t[Tiempo de cada juego]"      << endl;
		cout << "\t[Num de fichas a borrar]"      << endl;
		cout << "\t[Comodin para posibles ampliaciones del juego]"      << endl;
		cout << "\t[Random]"         << endl;
		cout << "\t[Tablero]"        << endl;
		return false;
	}

	entrada.getline(cad, 10);
	n_filas = atoi(cad);  // número de filas de la mitad superior del tablero

	entrada.getline(cad, 10);
	n_columnas = atoi(cad);

	entrada.getline(cad, 10);
	n_fichas = atoi(cad);

	entrada.getline(cad, 10);
	tiempo_juego = atoi(cad);

	entrada.getline(cad, 10);
	max_borrar = atoi(cad);

	entrada.getline(cad, 10);
	comodin = atoi(cad);

	entrada.getline(cad, 10);
	aleatorio = atoi(cad);

	if (aleatorio == 0)

	  for ( i = 0; i < MAX_NUM_FILAS; i++) {
	      entrada.getline(cad, MAX_CADENA);
	      for ( j = 0; j < MAX_NUM_COLUMNAS; j++)
	    	  casillas[i][j] = cad[j] - 48;
	  }
	else
		for (i = 0; i < MAX_NUM_FILAS; i++)
			for (j = 0; j < MAX_NUM_COLUMNAS; j++)
			    	  casillas[i][j] = rand()%MAX_FICHAS;


	entrada.close();
		
	return true;
}

void TEntornoActivarCasilla (int fila, int columna) {

    char cad[20];
    int  color = COLOR_ROJO;
    
    InvertirFC (fila, columna);

    sprintf(cad, "%d-%d", fila, columna);

    // Pinta de trazo distinto la fila-columna
    acquire_screen();
    
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS,   ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS,   makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+1, ORIGEN_Y + columna*DISTANCIA_COLS+1, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-1, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-1, makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+2, ORIGEN_Y + columna*DISTANCIA_COLS+2, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-2, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-2, makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+3, ORIGEN_Y + columna*DISTANCIA_COLS+3, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-3, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-3, makecolor2(color));

     
    release_screen();
}

void TEntornoDesactivarCasilla(int fila, int columna) {

	int color = COLOR_BLANCO;

	InvertirFC (fila, columna);

  // Pinta de trazo distinto la ficha- columna
  acquire_screen();

  rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS,   ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS,   makecolor2(color));
  rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+1, ORIGEN_Y + columna*DISTANCIA_COLS+1, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-1, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-1, makecol(0,0,0));
  rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+2, ORIGEN_Y + columna*DISTANCIA_COLS+2, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-2, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-2, makecol(0,0,0));
  rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+3, ORIGEN_Y + columna*DISTANCIA_COLS+3, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-3, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-3, makecol(0,0,0));

  release_screen();
}

void TEntornoMarcarCasilla (int fila, int columna) {

    char cad[20];
    int  color = COLOR_AMARILLO;

    InvertirFC (fila, columna);

    sprintf(cad, "%d-%d", fila, columna);

    // Pinta de trazo distinto la fila-columna
    acquire_screen();

    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS,   ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS,   ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS,   makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+1, ORIGEN_Y + columna*DISTANCIA_COLS+1, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-1, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-1, makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+2, ORIGEN_Y + columna*DISTANCIA_COLS+2, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-2, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-2, makecolor2(color));
    rect( screen, ORIGEN_X + fila*DISTANCIA_FILAS+3, ORIGEN_Y + columna*DISTANCIA_COLS+3, ORIGEN_X + fila*DISTANCIA_FILAS+DISTANCIA_FILAS-3, ORIGEN_Y + columna*DISTANCIA_COLS +DISTANCIA_COLS-3, makecolor2(color));


    release_screen();
}


// Establece la ficha en función de su tipo
void TEntornoPonerCasilla(int fila, int columna,int valor,int posicion) {


    acquire_screen();
    if (posicion == 0)
    // recupera el gráfico del vector de fichas la ficha que corresponde.
    draw_sprite( screen, fichas[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    else draw_sprite( screen, fichasreflejo[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);

    release_screen();


}
// Hace que la ficha parpadee en el tablero
void TEntornoParpadearCasilla(int fila, int columna,int valor,int posicion) {
int i;
int rep=3;


    if (posicion == 0)
    	for (i=0;i<rep;i++){
    		acquire_screen();
    		draw_sprite( screen, fichas[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
    		acquire_screen();
    		draw_sprite( screen, fichasreflejo[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
    		acquire_screen();
    		draw_sprite( screen, fichas[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    	}
    else
    	for (i=0;i<rep;i++){
    		acquire_screen();
    		draw_sprite( screen, fichasreflejo[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
    		acquire_screen();
    		draw_sprite( screen, fichas[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
    		acquire_screen();
    		draw_sprite( screen, fichasreflejo[valor],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    	}



}

void TEntornoParpadearDosCasillas(int fila, int columna,int fila2, int columna2, int valor1,int valor2) {
int i;
int rep=4;

       	for (i=0;i<rep;i++){
    		acquire_screen();
    		draw_sprite( screen, fichasreflejo[valor1],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		acquire_screen();
    		draw_sprite( screen, fichasreflejo[valor2],ORIGEN_X+(columna2)*DISTANCIA_COLS + 3 , ORIGEN_Y+(fila2)*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
    		acquire_screen();
    		draw_sprite( screen, fichas[valor1],ORIGEN_X+columna*DISTANCIA_COLS + 3 , ORIGEN_Y+fila*DISTANCIA_FILAS +3);
    		release_screen();
    		acquire_screen();
    		draw_sprite( screen, fichas[valor2],ORIGEN_X+(columna2)*DISTANCIA_COLS + 3 , ORIGEN_Y+(fila2)*DISTANCIA_FILAS +3);
    		release_screen();
    		usleep(PAUSA);
       	}
}
void TEntornoEliminarCasilla(int fila, int columna) {

  rectfill( screen, ORIGEN_X+columna*DISTANCIA_COLS+1, ORIGEN_Y+fila*DISTANCIA_FILAS+1, ORIGEN_X+columna*DISTANCIA_COLS +DISTANCIA_COLS-1,ORIGEN_Y+fila*DISTANCIA_FILAS+DISTANCIA_FILAS-1 , makecol( 0, 0, 0));
}

void TEntornoMostrarMensaje (TipoZona zona, TipoMensaje msg) {

	int fila;

	switch (zona) {

		case Zona1: fila = 500; break;
		case Zona2: fila = 520; break;
		case Zona3: fila = 540; break;
		case Zona4: fila = 560; break;
		default:  return;

	}

	textout_ex( screen, font, "                                        ",  500,fila , makecol( 255,   0, 0), makecol(0, 0, 0));
	textout_ex( screen, font, msg,500,fila , makecol( 255, 255, 0), makecol(0, 0, 0));

}


void TEntornoMostrarMensajeFin (TipoMensaje cad) {

  int  i, j;

  // Borrar un rectangulo
  rectfill (screen, 200, 200, 620, 440, makecol(0, 0, 0));

  // Efecto
  for (i = 0, j = 0; i < 40 && j < 40; i+=4, j+=4) {
	  rect( screen, i + 220, j + 240, 600 - i, 400 - j, makecol( 255,   0,   0));
	  rect( screen, i + 222, j + 242, 598 - i, 398 - j, makecol( 255, 255, 255));
	  usleep(25000); // 25 milisegundos
  }

  // Mensaje
  textout_ex(screen, font, cad,  280, 320 , makecol( 255, 255, 0), makecol(0, 0, 0));

}


TipoTecla TEntornoLeerTecla () {

	TipoTecla tecla = TNada;

	readkey();
	
	if      (key[KEY_UP])    tecla = TArriba;
	else if (key[KEY_DOWN])  tecla = TAbajo;
	else if (key[KEY_RIGHT]) tecla = TDerecha;
	else if (key[KEY_LEFT])  tecla = TIzquierda;
	else if (key[KEY_ENTER]) tecla = TEnter;
	else if (key[KEY_ESC])   tecla = TSalir;
	else if (key[KEY_X])     tecla = TX;
	
	clear_keybuf();

	return tecla;

}


void TEntornoRefrescarFrontera(int filas, int columnas){
	int  color = COLOR_ROJO;
	int mitad = filas/2;
	acquire_screen();

	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS-1, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS-1, makecolor2(color));
	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS, makecolor2(color));
	line( screen, ORIGEN_X+0*DISTANCIA_COLS-15, ORIGEN_Y+mitad*DISTANCIA_FILAS+1, ORIGEN_X+columnas*DISTANCIA_COLS+15, ORIGEN_Y+mitad*DISTANCIA_FILAS+1, makecolor2(color));

	release_screen();
}

void TEntornoTiempoInicio(){
/* este módulo pinta la barra que mide el tiempo inicialmente */

		int color=COLOR_BLANCO;
		int color2=COLOR_ROJO;


		rect( screen, 498,430,752,453,makecolor2(color));
		rect( screen, 499,431,751,452,makecolor2(color));
		rectfill( screen, 500,432,750,451,makecolor2(color2));


}

void TEntornoMedirTiempo(int tam){
	/* este módulo va acortando la barra roja  que mide el tiempo
	 * que va transcurriendo */
	//tam toma valores de 0 a 25

		int color=COLOR_BLANCO;

		rectfill( screen, 750-10*tam,432,750,451,makecolor2(color));
}

