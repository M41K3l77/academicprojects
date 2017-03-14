/*
 * Menu.cpp
 *
 *  Created on: 10 de oct. de 2015
 *      Author: miguel
 */

#include "Menu.h"

Menu::Menu() {
	// Colores que usaremos en el menu
	Color negro(0, 0, 0);
	Color verde(0, 255, 0);
	Color azul(0, 0, 255);
	Color rojo(255, 0, 0);
	Color amarillo(255, 255, 0);
	Color blanco(255, 255, 255);
	// Se añaden los colores al vector
	// los seis primeros son para el fondo y el resto para el dibujo
	colores.push_back(negro);		colores.push_back(verde);
	colores.push_back(azul);		colores.push_back(rojo);
	colores.push_back(amarillo);	colores.push_back(blanco);
	colores.push_back(negro);		colores.push_back(verde);
	colores.push_back(azul);		colores.push_back(rojo);
	colores.push_back(amarillo);	colores.push_back(blanco);

}
/**
 * Metodo que crea el menu principal y submenus dibujo, fondo y visor
 * El metodo onMenu no se puede tener en la clase por lo que se pasa
 * como parametro
 */
void Menu::makeMenu(void (*onMenu)(int)) {
	int menuFondo, menuDibujo, menuVisor, menuPrincipal, menuApariencia;
	menuApariencia = glutCreateMenu(onMenu);
	glutAddMenuEntry("GL_FLAT", 15);
	glutAddMenuEntry("GL_SMOOTH", 16);
	menuVisor = glutCreateMenu(onMenu);
	glutAddMenuEntry("Alambre", 12);
	glutAddMenuEntry("Solido", 13);
	glutAddMenuEntry("Iluminado", 14);
	menuFondo = glutCreateMenu(onMenu);
	glutAddMenuEntry("Negro", 0);
	glutAddMenuEntry("Verde", 1);
	glutAddMenuEntry("Azul", 2);
	glutAddMenuEntry("Rojo", 3);
	glutAddMenuEntry("Amarillo", 4);
	glutAddMenuEntry("Blanco", 5);
	menuDibujo = glutCreateMenu(onMenu);
	glutAddMenuEntry("Negro", 6);
	glutAddMenuEntry("Verde", 7);
	glutAddMenuEntry("Azul", 8);
	glutAddMenuEntry("Rojo", 9);
	glutAddMenuEntry("Amarillo", 10);
	glutAddMenuEntry("Blanco", 11);
	menuPrincipal = glutCreateMenu(onMenu);
	glutAddSubMenu("Apariencia dibujo", menuApariencia);
	glutAddSubMenu("Tipo de visor", menuVisor);
	glutAddSubMenu("Color de fondo", menuFondo);
	glutAddSubMenu("Color de dibujo", menuDibujo);
	// Se asigna el boton derecho del raton para activar el menu
	glutAttachMenu(GLUT_RIGHT_BUTTON);
}

Color Menu::getColorDibujo(const int &opcion) {
	return this->colores[opcion];
}

Color Menu::getColorFondo(const int &opcion) {
	return this->colores[opcion];
}

Menu::~Menu() {
	// TODO Auto-generated destructor stub
}

