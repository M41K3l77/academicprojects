/*
 * Menu.h
 *
 *  Created on: 10 de oct. de 2015
 *      Author: miguel
 */

#ifndef MENU_H_
#define MENU_H_
#include "Color.h"
#include <vector>
#include <GL/glut.h>
#include <unistd.h>
using namespace std;
/**
 * Clase menu para seleccionar el color de la figura y el del fondo y
 * el tipo de visor (alambre o solido)
 */
class Menu {
private:
	// vector con los colores para el fondo y la figura
	vector < Color > colores;
public:
	// Constructor
	Menu();
	// Crea el menu
	void makeMenu(void (*onMenu)(int));
	// getters
	// devuelve el color seleccionado para el dibujo
	Color getColorDibujo(const int &opcion);
	// devuelve el color seleccionado para el fondo
	Color getColorFondo(const int &opcion);
	// destructor
	virtual ~Menu();
};

#endif /* MENU_H_ */
