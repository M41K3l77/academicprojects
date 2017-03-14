// desarrollo de las cabeceras de casillasTAD.h

#include <iostream>
#include "casillaTAD.h"
using namespace std;


Casilla CrearFicha(int colorido){
	// Se declara una variable tipo Casilla
	Casilla ficha;
	// Se le asigna un color a la ficha
	ficha.color=colorido;
	// Se le asigna true a la ficha, esto significa que mientras la ficha
	// esté a true no se puede ocupar por otra que esté en el tablero
	// o una nueva
	ficha.estado=true;

	return (ficha);
}

bool EstadoFicha(Casilla ficha){
	// Devuelve el estado de la ficha
	return (ficha.estado);
}
int ColorFicha(Casilla ficha){
	// Devuelve el color de la ficha
	return (ficha.color);
}

void CambiarEstadoFicha(Casilla &ficha){
	// Cambia el estado de la ficha a false, estó significa que podrá
	// ser reemplazada por otra ficha del tablero o por una nueva
	// Decir además que el único cambio de estado de las fichas será
	// de true a false en el caso de que vayan a ser "eliminadas"
	ficha.estado=false;
}
