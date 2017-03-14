//============================================================================
// Name        : El_Mundo.cpp
// Author      : Miguel Angel Hogado Ceballos a partir de codigo proporcionado por el profesor
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
#include <iostream>
#include <GL/glut.h>
#include <GL/glu.h>
#include <GL/gl.h>
#include <unistd.h>
#include "Mundo.h"
#include "Menu.h"
using namespace std;
// definicion teclado
#define ESC 27
//
// modelo a representar
Mundo myWorld;
//ventana
int window;
// posicion en la ventana del raton
int xInicio, yInicio;
// reshape para cambiar tamaño de la ventana y que no se deformen las figuras
int screenwidth, screenHeight;
// menu, tiene todo lo relacionado con el menu excepto la funcion onMenu
// por que no puede ser funcion miembro
Menu menu;
// tiempos
// tasa de refresco para el timer en milisegundos
int refreshRate=40;
// del que sacaremos posicion en la orbita de cada objeto y su rotacion
// ver dichos calculos en Modelo3D.cpp renderModel3D
float tiempoTranscurrido=0.0f;
// incremento del tiempo
float speedTime=0.04f;
// control de hata cuanto se puede aumentar la velocidad del tiempo
int timeControl=0;
int limitTimeControl=6;
//zoom
// uado en Mundo.cpp glScalef(zoom, zoom, zoom) y asi escalar el sistema solar
float zoom=1.0f;
int zoomControl=0;// para limitar el zoom
int limitZoomControl=16;// limite para el zoom
//
// booleano para activar o desactivar ejes por teclado
bool axisActivate=false;
// funcion que usa opengl para dibujar nuestro mundo
void display(){
	// dibujar Mundo
	myWorld.drawMyWorld(tiempoTranscurrido, screenwidth, screenHeight, zoom, axisActivate);
}
/**
 * En xInicio e yInicio guardamos la posicion en que se acciono el boton principal
 * para mas adelante tenerlo como referencia en el calculo de la rotacion
 */
void onMouse(int button, int state, int x, int y) {
	// si el boton izquierdo del raton esta presionado guardamos sus coordenadas en la venta
	if ( (button == GLUT_LEFT_BUTTON) & (state == GLUT_DOWN) ) {
		xInicio = x; yInicio = y; // Actualizamos valores
	}
}
/**
 * En este metodo calculamos el giro sobre el eje x e y
 */
void onMotion(int x, int y) {
	// Se actualiza alfa
	// (y - yInicio) nos dice cuanto se ha movido en el eje y (incremento/decremento)
	myWorld.setAlfa(myWorld.getAlfa() + (y - yInicio));
	// Se actualiza beta
	// (x - xInicio) nos dice cuanto se ha movido en el eje x (incremento/decremento)
	myWorld.setBeta(myWorld.getBeta() + (x - xInicio));
	// Las coordenadas donde se dejo de presionar el boton seran las de inicio
	xInicio = x; yInicio = y;
	glutPostRedisplay();
}

/**
 * Función de eventos de teclado para teclas normales
 * Controla camara, zoon, velocidad del tiempo y ejes de las
 * figuras asi como es escape (ESC)
 */
void keyPressed(unsigned char key, int x, int y){
	switch (key) {
	case ESC:
		// Cerramos la ventana y salimos
		glutDestroyWindow(window);
		exit(0);
		break;
	case '1':
		// camara0
		myWorld.setCameraNumber(0);
		myWorld.setAlfa(0);// hay que setear los alfa y beta para que la posicion de la camara
		myWorld.setBeta(0);// no sea relativa sino absoluta. y ello para todas las camaras.
		cout << "elegida camara 1" << endl;
		break;
	case '2':
		// camara1
		myWorld.setCameraNumber(1);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 2" << endl;
		break;
	case '3':
		// camara2
		myWorld.setCameraNumber(2);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 3" << endl;
		break;
	case '4':
		// camara3
		myWorld.setCameraNumber(3);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 4" << endl;
		break;
	case '5':
		// camara4
		myWorld.setCameraNumber(4);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 5" << endl;
		break;
	case '6':
		// camara5
		myWorld.setCameraNumber(5);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 6" << endl;
		break;
	case '7':
		// camara6
		myWorld.setCameraNumber(6);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 7" << endl;
		break;
	case '8':
		// camara7
		myWorld.setCameraNumber(7);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 8" << endl;
		break;
	case '9':
		// camara8
		myWorld.setCameraNumber(8);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 9" << endl;
		break;
	case '0':
		// camara9
		myWorld.setCameraNumber(9);
		myWorld.setAlfa(0);
		myWorld.setBeta(0);
		cout << "elegida camara 10" << endl;
		break;
	case '+':
		if(zoomControl < limitZoomControl-1){
			// zoom + aumentar zoom
			zoom=zoom+0.04;
			zoomControl++;
			cout << "zoom: " << zoomControl << "x" << endl;
		}
		break;
	case '-':
		if(zoomControl > (limitZoomControl-1)*-1){
			zoom=zoom-0.04;
			zoomControl--;
			cout << "zoom: " << zoomControl << "x" << endl;
		}
		break;
	case '*':
		if(timeControl < limitTimeControl-1){
			// speed time * tiempo pasa mas deprisa
			speedTime=speedTime+0.04;
			timeControl++;
			cout << "speed: " << timeControl << "x" << endl;
		}
		break;
	case '/':
		if(timeControl > (limitTimeControl-1)*-1){
			// speed time / tiempo pasa mas despacio, valores negativos es ir hacia
			// atras en el tiempo ;)
			speedTime=speedTime-0.04;
			timeControl--;
			cout << "speed: " << timeControl << "x" << endl;
		}
		break;
	case 'e':
		// mostrar ejes
		if(axisActivate){
			axisActivate=false;
			cout << "ejes desactivados" << endl;
		}else{
			axisActivate=true;
			cout << "ejes activados" << endl;
		}
		break;
	}
	usleep(1000);
}
/**
 * Funcion de teclado para teclas especiales
 * Controla los focos. Se pueden controlar siete de los ocho focos.
 */
void specialkeyboard(int key, int x, int y){
	switch (key) {
	case GLUT_KEY_F1:

		cout << "interruptor f1 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[0].getLight())){
			cout << "interruptor f1 desactivada light0" << endl;
			myWorld.getLights()[0].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f1 activada light0" << endl;
				myWorld.getLights()[0].setWhiteLight();
			}
		}
		break;
	case GLUT_KEY_F2:
		cout << "interruptor f2 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[1].getLight())){
			cout << "interruptor f2 desactivada light1" << endl;
			myWorld.getLights()[1].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f2 activada light1" << endl;
				myWorld.getLights()[1].setRedLight();
			}
		}
		break;
	case GLUT_KEY_F3:
		cout << "interruptor f3 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[2].getLight())){
			cout << "interruptor f3 desactivada light2" << endl;
			myWorld.getLights()[2].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f3 activada light2" << endl;
				myWorld.getLights()[2].setGreenLight();
			}
		}
		break;
	case GLUT_KEY_F4:
		cout << "interruptor f4 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[3].getLight())){
			cout << "interruptor f4 desactivada light3" << endl;
			myWorld.getLights()[3].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f4 activada light3" << endl;
				myWorld.getLights()[3].setBlueLight();
			}
		}
		break;
	case GLUT_KEY_F5:
		cout << "interruptor f5 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[4].getLight())){
			cout << "interruptor f5 desactivada light4" << endl;
			myWorld.getLights()[4].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f5 activada light4" << endl;
				myWorld.getLights()[4].setRedGreenLight();
			}
		}
		break;
	case GLUT_KEY_F6:
		cout << "interruptor f6 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[5].getLight())){
			cout << "interruptor f6 desactivada light5" << endl;
			myWorld.getLights()[5].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f6 activada light5" << endl;
				myWorld.getLights()[5].setRedBlueLight();
			}
		}
		break;
	case GLUT_KEY_F7:
		cout << "interruptor f7 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[6].getLight())){
			cout << "interruptor f7 desactivada light6" << endl;
			myWorld.getLights()[6].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "interruptor f7 activada light6" << endl;
				myWorld.getLights()[6].setGreenBlueLight();
			}
		}
		break;
	default:
		break;
	}
	usleep(1000);
}
/** menu, esta funcion no se puede tener en la clase menu ya que
 *  no puede ser funcion miembro por sus caracteristicas
 *  por lo que se pasara como parametro a la funcion makeMenu() de la clase MENU
 */
void onMenu(int opcion) {
	if(opcion >= 0 && opcion < 6){// fondo 0-5, dibujo 6-11
		myWorld.setColorFondo(menu.getColorFondo(opcion));
	}else if(opcion >= 6 && opcion < 12){
		myWorld.setColorFiguras(menu.getColorDibujo(opcion));
	}else if(opcion >= 12 && opcion < 15){// tipo de visor
		switch (opcion) {
		case 12:
			glDisable(GL_LIGHTING);// se deshabilita iluminacion para alambre
			myWorld.setVisorType(WIRE);
			break;
		case 13:
			glDisable(GL_LIGHTING);// se deshabilita iluminacion para solido
			myWorld.setVisorType(SOLID);
			break;
		case 14:
			glEnable(GL_LIGHTING);// se habilita iluminacion para modo iluminacion
			myWorld.setVisorType(ILUMINATED);
			break;
		}
	}else if(opcion >= 15 && opcion < 17){// tipo de apariencia con iluminacion
		if(opcion == 15){
			myWorld.setPrimitiveAparience(GL_FLAT);// constante
		}else if(opcion == 16){
			myWorld.setPrimitiveAparience(GL_SMOOTH);// suave
		}
	}
	glutPostRedisplay();
}

void InitGL(){
	// para poder usar los datos de la normal de los vertices
	glEnable(GL_NORMALIZE);
	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);
}

// timer para el tiempo
static void timer(int value){
	tiempoTranscurrido=tiempoTranscurrido+speedTime;
	glutPostRedisplay();
	// refreshRate milliseconds
	glutTimerFunc(refreshRate, timer, 0);
}

// para redimensionar ventana
void reshape(int w, int h){
	screenwidth=w;
	screenHeight=h;
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);
}

int main(int argc, char **argv) {
	glutInit(&argc,argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(600,600);
	glutInitWindowPosition(100,200);
	window= glutCreateWindow("Entrega Sistema Solar");
	glutDisplayFunc(&display); //puntero a la función de pintado
	glutIdleFunc(&display); // pinta cuando no esté haciendo nada
	// glutMouseFunc sets the mouse callback for the current window. When a user
	// presses and releases mouse buttons in the window, each press and each release
	// generates a mouse callback. Es decir nos pone al tanto de las acciones del raton
	glutMouseFunc(&onMouse);
	// This function will call onMotion(int x, int y) for each mouse move
	// when any button is pressed down, where x and y are the screen coordinates.
	glutMotionFunc(&onMotion);
	glutKeyboardFunc(keyPressed);// puntero a la función de eventos de teclado
	glutReshapeFunc(reshape);
	glutSpecialFunc(&specialkeyboard);// puntero a la función de eventos de teclado
	// crear menu
	menu.makeMenu(&onMenu);
	InitGL();
	myWorld.initMundo();
	timer(0);
	glutMainLoop();   //bucle principal.....todo preparado..... cedemos el control
	return 0;
}

