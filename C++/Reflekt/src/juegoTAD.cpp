// desarrollo de las cabeceras de juegoTAD.h

#include "juegoTAD.h"
///////////////////////////
//INICIO MODULOS PRIVADOS//
///////////////////////////
// Pre:
// Post: Se inicializa un Juego.
// Devuelve nada.
// Complejidad O(1).
void InicializarJuego (Juego &Jug){
	Jug.FilasJuego=0;
	Jug.ColumnasJuego=0;
	Jug.PuntosJuego=0;
	Jug.TiempoJuego=0;
	Jug.Max_borrarJuego=0;
	Jug.NumColoresMax=0;
	Jug.aleatorio=0;
	Jug.comodin=0;
	InicializarTablero (Jug.Tab);
}
// Pre: Inicializado un Juego.
// Post: Se carga una configuración desde *.cnf
// Devuelve nada.
// Complejidad O(n*n).
void CargarJuego(Juego &Jug){
	// Se declara una matriz de enteros para cargar colores desde *.cnf.
	MatrizEnteros M;
	// Se inicializa la matriz a cero.
	InicializarMatrizAuxiliar(M);
	// Se carga la configuración.
	TEntornoCargarConfiguracion (Jug.FilasJuego, Jug.ColumnasJuego, Jug.NumColoresMax,
			Jug.TiempoJuego, Jug.Max_borrarJuego, Jug.comodin, Jug.aleatorio,
			  				  		               M);
	// Si el tablero que se carga es aleatorio.
	if(Jug.aleatorio==1){
		CrearTableroAleatorio (Jug.Tab, 2*(Jug.FilasJuego), Jug.ColumnasJuego, Jug.NumColoresMax);
	  }
	else{// Si no es aleatorio
		CambiarNumFilCol(Jug.Tab, Jug.FilasJuego, Jug.ColumnasJuego);
	// Ademas de pasar los colores en el modulo las fichas son creadas a true.
	    PasarColores(Jug.Tab, M);
		}
}
// Pre:
// Post: Mostrar mensajes en la ventana gráfica.
// Devuelve nada.
// Complejidad O(1).
void Puntuacion(int puntos, int max_borrar){
	// Declaración de variables TipoMensaje.
	TipoMensaje msg2;
	TipoMensaje msg3;
	TipoMensaje msg4;
	// Variable entera(fichas borradas)
	int fichbor;
	fichbor=puntos/10;
	// conversión de los enteros a caracteres para mostrarlos en TipoMensaje (%d).
	sprintf(msg2, "Puntos: %d", puntos);
	sprintf(msg3, "Fichas borradas: %d", fichbor);
	sprintf(msg4, "Fichas a borrar: %d", max_borrar);
	// Llamadas al módulo del TAD entorno para mostrar los mensajes
	TEntornoMostrarMensaje (Zona2,  msg2);
	TEntornoMostrarMensaje (Zona3,  msg3);
	TEntornoMostrarMensaje (Zona4,  msg4);
}
// Pre: Inicializado y creado un Juego
// Post: Movimiento de fichas e inserción si así se requiriera
// Devuelve nada.
// Complejidad O(?). Ya que llama a un módulo con recursividad.
void MovimientoFichas(TipoTecla &tecla, int fila, int col, Juego &Jug){
    				 TEntornoMarcarCasilla(fila,col);
    				 tecla=TEntornoLeerTecla();
    				 // Según la flecha de dirección que se pulse hay que poner un límite
    				 // diferente para no salirnos del tablero
    				 if(tecla==TDerecha && col<NumColumnas(Jug.Tab)-1){
    					IntercambiarDosFichas(Jug.Tab, fila, col, fila, col+1);
    					TEntornoParpadearDosCasillas(fila, col,fila, col+1, ColorFichEnTablero (Jug.Tab, fila, col),ColorFichEnTablero (Jug.Tab, fila, col+1));
    				    if((TableroFichIgulFals (Jug.Tab))==false) {
    		              IntercambiarDosFichas(Jug.Tab, fila, col, fila, col+1);

    					  }
    				    else if((TableroFichIgulFals (Jug.Tab))==true) {
    				    	OcuparCasillasVacias(Jug.Tab, Jug.NumColoresMax, Jug.PuntosJuego);
    				    	Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
    				           }
    				   }
    				 else if(tecla==TIzquierda && col>0){
    				 	IntercambiarDosFichas(Jug.Tab, fila, col, fila, col-1);
    				 	TEntornoParpadearDosCasillas(fila, col,fila, col-1, ColorFichEnTablero (Jug.Tab, fila, col),ColorFichEnTablero (Jug.Tab, fila, col-1));
    				 	if((TableroFichIgulFals (Jug.Tab))==false) {
    				 		IntercambiarDosFichas(Jug.Tab, fila, col, fila, col-1);
    				 		}
    				 	else if((TableroFichIgulFals (Jug.Tab))==true) {
    				 		OcuparCasillasVacias(Jug.Tab, Jug.NumColoresMax, Jug.PuntosJuego);
    				 		Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
    				 			}
    				 	 }
    				 else if(tecla==TArriba && fila>0){
    				 	IntercambiarDosFichas(Jug.Tab, fila, col, fila-1, col);
    				 	TEntornoParpadearDosCasillas(fila, col,fila-1, col, ColorFichEnTablero (Jug.Tab, fila, col),ColorFichEnTablero (Jug.Tab, fila-1, col));
    				 	if((TableroFichIgulFals (Jug.Tab))==false) {
    				 		IntercambiarDosFichas(Jug.Tab, fila, col, fila-1, col);
    				 		}
    				 	else if((TableroFichIgulFals (Jug.Tab))==true) {
    				 		OcuparCasillasVacias(Jug.Tab, Jug.NumColoresMax, Jug.PuntosJuego);
    				 		Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
    				 		    }
    				 	 }
    				 else if(tecla==TAbajo && fila<(NumFilas(Jug.Tab)-1)/2){
    				 	IntercambiarDosFichas(Jug.Tab, fila, col, fila+1, col);
    				 	TEntornoParpadearDosCasillas(fila, col,fila+1, col, ColorFichEnTablero (Jug.Tab, fila, col),ColorFichEnTablero (Jug.Tab, fila+1, col));
    				 	if((TableroFichIgulFals (Jug.Tab))==false) {
    				 	  IntercambiarDosFichas(Jug.Tab, fila, col, fila+1, col);
    				 	  }
    				 	else if((TableroFichIgulFals (Jug.Tab))==true) {
    				 		OcuparCasillasVacias(Jug.Tab, Jug.NumColoresMax, Jug.PuntosJuego);
    				 		Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
    				 			}
    				 	}
    				 TEntornoRefrescarFrontera(NumFilas(Jug.Tab), NumColumnas(Jug.Tab));
    				 // Importante no borrar esta llamada ya que remarca la casilla en rojo
    				 // despues de cada acción ya sea intercambio fallido de casilla o
    				 // intercambio con exito
    				 TEntornoActivarCasilla(fila, col);
}
// Pre: Inicializado y creado un Juego
// Post: Movimiento por el tablero
// Devuelve nada.
// Complejidad O(?). Ya que llama a un módulo con recursividad.
void MovimientoEnTablero(TipoTecla &tecla, int fila, int col, Juego &Jug, bool &gameOver, TReloj &reloj, int &p, TipoMensaje msg){
	  while (!gameOver) {

	       tecla = TEntornoLeerTecla();

		    if(tecla==TDerecha){
			   TEntornoDesactivarCasilla(fila, col);
			   					   if (col < NumColumnas(Jug.Tab)-1) col++;
			   					   TEntornoActivarCasilla(fila, col);
			   					   TEntornoRefrescarFrontera(NumFilas(Jug.Tab), NumColumnas(Jug.Tab));
		   }
		   else if(tecla==TIzquierda){
			   TEntornoDesactivarCasilla(fila, col);
			   					   if (col > 0) col--;
			   					   TEntornoActivarCasilla(fila, col);
			   					   TEntornoRefrescarFrontera(NumFilas(Jug.Tab), NumColumnas(Jug.Tab));
		   			   }
		   else if(tecla==TArriba){
			   TEntornoDesactivarCasilla(fila, col);
			   					   if (fila > 0) fila--;
			   					   TEntornoActivarCasilla(fila, col);
			   					   TEntornoRefrescarFrontera(NumFilas(Jug.Tab), NumColumnas(Jug.Tab));
		   			   			   }
		   else if(tecla==TAbajo){
			   TEntornoDesactivarCasilla(fila, col);
			   					   if (fila < (NumFilas(Jug.Tab)-1)/2) fila++;
			   					   TEntornoActivarCasilla(fila, col);
			   					   TEntornoRefrescarFrontera(NumFilas(Jug.Tab), NumColumnas(Jug.Tab));
		   			   			   			   }
		   else if(tecla==TSalir){
			                    gameOver=true;
		   				   }
		   else if(tecla==TEnter){
			   // Llamada a módulo para el movimiento de fichas(intercambio, borrado...)
			   MovimientoFichas(tecla, fila, col, Jug);
		   }
		    //cada vez que pulsemo una tecla se actualiza el reloj
		    if (ActualizarReloj(reloj,p,msg)){
		    	TEntornoMedirTiempo(p);
		    	TEntornoMostrarMensaje(Zona1, msg);
		    	}
		    else {
		    //si se acaba el tiempo el bucle termina
		    	  gameOver=true;
		    	}
		    if(Jug.PuntosJuego/10>=Jug.Max_borrarJuego){
		    		    	// se vuelve a colocar el modulo aqui para que muestre la puntuacion final
		    		    	Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
		    		    	gameOver=true;
		    		    	}
  }
}
// Pre: Inicializado y creado un Juego
// Post: Mostrará mensaje de fin de partida según las diferentes circustancias de su final.
// Devuelve nada.
// Complejidad O(n). Ya que llama a un módulo del entorno TEntornoTerminar();.
void FinalDePartida(Juego Jug, TipoTecla tecla){
	// Se gana la partida si se consiguen los puntos requeridos
	  if(Jug.PuntosJuego/10>=Jug.Max_borrarJuego){
		  TipoMensaje cad="¡¡¡ H A S  G A N A D O!!!!!";
		  TEntornoMostrarMensajeFin (cad);
		  usleep(PAUSA);
		  usleep(PAUSA);
		  usleep(PAUSA);
		  usleep(PAUSA);
	  }
	  // Se pierde la partida si transcurrido el no se consiguen los puntos requeridos
	  else if(Jug.PuntosJuego/10<Jug.Max_borrarJuego && tecla!=TSalir){
		  TipoMensaje cad="¡¡¡ H A S  P E R D I D O !!!!!";
		  TEntornoMostrarMensajeFin (cad);
		  usleep(PAUSA);
		  usleep(PAUSA);
		  usleep(PAUSA);
		  usleep(PAUSA);
	  }
	  // Si se pulsa Esc se sale de la partida
	  else if(tecla==TSalir){
	  	  TipoMensaje cad="¡¡¡ INTENTALO  OTRA  VEZ !!!!!";
	  	  TEntornoMostrarMensajeFin (cad);
	  	  usleep(PAUSA);
	  	  usleep(PAUSA);
	  	  usleep(PAUSA);
	  	  usleep(PAUSA);
	  		}

	  TEntornoTerminar();
}
////////////////////////
//FIN MODULOS PRIVADOS//
////////////////////////

void Partida (Juego &Jug){
	TReloj reloj;
	int p;
	TipoMensaje msg;
	int fila;
	int col;
	bool gameOver=false;
	TipoTecla tecla=TNada;
	srand(time(NULL));
	InicializarJuego (Jug);
	CargarJuego(Jug);
	TEntornoIniciar ((NumFilas(Jug.Tab)), (NumColumnas(Jug.Tab)));
	reloj = CrearReloj(Jug.TiempoJuego);
	TEntornoTiempoInicio();
	ActualizarReloj(reloj, p, msg);
	TEntornoMostrarMensaje(Zona1, msg);
	Puntuacion(Jug.PuntosJuego, Jug.Max_borrarJuego);
	ActualizarTableroVisual(Jug.Tab);
	fila=0;
	col=0;
	TEntornoActivarCasilla(fila, col);
	// Desplazamiento en el tablero e intercambio y eliminación de fichas
	MovimientoEnTablero(tecla, fila, col, Jug, gameOver, reloj, p, msg);

	  // Módulo final de la partida, según las condiciones de salida se mostrará
	  // un mensaje de ganador, perdedor o salida voluntaria del juego antes del tiempo
	  FinalDePartida(Jug, tecla);

}
