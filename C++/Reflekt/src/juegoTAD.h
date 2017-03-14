//juegoTAD.h//
#ifndef JUEGO_REFLEKT
#define JUEGO_REFLEKT
#include <iostream>
#include <stdio.h>
#include "entorno.h"
#include "reloj.h"
#include "tableroTAD.h"
#include <allegro.h>

typedef struct Juego{
	Tablero Tab;
	int FilasJuego;
	int ColumnasJuego;
	int PuntosJuego;
	int TiempoJuego;
	int Max_borrarJuego;
	int NumColoresMax;
	int comodin;
	int aleatorio;
}Juego;

// Pre: Inicializado un Juego y cargada una configuracón
// Post: Se crea una partida del juego REFLEKT acorde con
// el archivo reflekt.cfn
// devuelve nada
// Complejidad O(?) ya que llama a un modulo del tableroTAD que tiene
// recursividad(OcuparCasillasVacias)
void Partida (Juego &Jug);

#endif
