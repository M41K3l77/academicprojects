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
#include <AR/ar.h>
#include <AR/gsub.h>
#include <AR/video.h>
#include <AR/param.h>
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
// tiempos
// tasa de refresco para el timer en milisegundos
int refreshRate=40;
// interruptor luz que ilumina planetas, hace falta por que en cada vuelta de bucle necesitamos saber
// en que estado quedo esa luz (light0 en nuestro caso) y poder apagarla o encenderla(anhadido por la segunda marca)
bool stateOfPlanetsLight=true;
/**
 * En xInicio e yInicio guardamos la posicion en que se acciono el boton principal
 * para mas adelante tenerlo como referencia en el calculo de la rotacion
 */
void MouseEvent(int button, int state, int x, int y) {
	// si el boton principal del raton esta presionado guardamos sus coordenadas en la venta
	if ( (button == GLUT_LEFT_BUTTON) & (state == GLUT_DOWN) ) {
		xInicio = x; yInicio = y; // Actualizamos valores
		cout << "xInicio: " << xInicio << " yInicio: " << yInicio << endl;
		// si el boton principal del raton se suelta se actualizan variables para girar planetario
	}else if((button == GLUT_LEFT_BUTTON) & (state == GLUT_UP) ){
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
		// foco 0
		cout << "tecla numerica 1 pulsada" << endl;
		if(glIsEnabled(myWorld.getLights()[0].getLight())){
			cout << "tecla numerica 1 desactivada light0" << endl;
			myWorld.getLights()[0].deactivateLight();
			stateOfPlanetsLight=false;
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 1 activada light0" << endl;
				myWorld.getLights()[0].setWhiteLight();
				stateOfPlanetsLight=true;
			}
		}
		break;
	case '2':
		// foco 1
		cout << "tecla numerica 2 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[1].getLight())){
			cout << "tecla numerica 2 desactivada light1" << endl;
			myWorld.getLights()[1].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 2 activada light1" << endl;
				myWorld.getLights()[1].setRedLight();
			}
		}
		break;
	case '3':
		// foco 2
		cout << "tecla numerica 3 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[2].getLight())){
			cout << "tecla numerica 3 desactivada light2" << endl;
			myWorld.getLights()[2].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 3 activada light2" << endl;
				myWorld.getLights()[2].setGreenLight();
			}
		}
		break;
	case '4':
		// foco 4
		cout << "tecla numerica 4 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[3].getLight())){
			cout << "tecla numerica 4 desactivada light3" << endl;
			myWorld.getLights()[3].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 4 activada light3" << endl;
				myWorld.getLights()[3].setBlueLight();
			}
		}
		break;
	case '5':
		// foco 5
		cout << "tecla numerica 5 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[4].getLight())){
			cout << "tecla numerica 5 desactivada light4" << endl;
			myWorld.getLights()[4].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 5 activada light4" << endl;
				myWorld.getLights()[4].setRedGreenLight();
			}
		}
		break;
	case '6':
		// foco 5
		cout << "tecla numerica 6 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[5].getLight())){
			cout << "tecla numerica 6 desactivada light5" << endl;
			myWorld.getLights()[5].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 6 activada light5" << endl;
				myWorld.getLights()[5].setRedBlueLight();
			}
		}
		break;
	case '7':
		// foco 6
		cout << "tecla numerica 7 pulsado" << endl;
		if(glIsEnabled(myWorld.getLights()[6].getLight())){
			cout << "tecla numerica 7 desactivada light6" << endl;
			myWorld.getLights()[6].deactivateLight();
		}else{
			if(myWorld.getVisorType() == ILUMINATED){// y ademas iluminacion activada en menu
				cout << "tecla numerica 7 activada light6" << endl;
				myWorld.getLights()[6].setGreenBlueLight();
			}
		}
		break;
	case '+':
		if(myWorld.getZoomControl() < myWorld.getLimitZoomControl()-1){
			// zoom + aumentar zoom
			myWorld.setZoom(myWorld.getZoom()+0.05f);
			myWorld.setZoomControl(myWorld.getZoomControl()+1);
			cout << "zoom: " << myWorld.getZoomControl() << "x" << endl;
		}
		break;
	case '-':
		if(myWorld.getZoomControl() > (myWorld.getLimitZoomControl()-1)*-1){
			// zoom - disminuir zoom
			myWorld.setZoom(myWorld.getZoom()-0.05f);
			myWorld.setZoomControl(myWorld.getZoomControl()-1);
			cout << "zoom: " << myWorld.getZoomControl() << "x" << endl;
		}
		break;
	case '*':
		if(myWorld.getTimeControl() < myWorld.getLimitTimeControl()-1){
			// speed time * tiempo pasa mas deprisa
			myWorld.setSpeedTime(myWorld.getSpeedTime()+0.04f);
			myWorld.setTimeControl(myWorld.getTimeControl()+1);
			cout << "speed: " << myWorld.getTimeControl() << "x" << endl;
		}
		break;
	case '/':
		if(myWorld.getTimeControl() > (myWorld.getLimitTimeControl()-1)*-1){
			// speed time / tiempo pasa mas despacio, valores negativos es ir hacia
			// atras en el tiempo ;)
			myWorld.setSpeedTime(myWorld.getSpeedTime()-0.04f);
			myWorld.setTimeControl(myWorld.getTimeControl()-1);
			cout << "speed: " << myWorld.getTimeControl() << "x" << endl;
		}
		break;
	case 'e':
	case 'E':
		// mostrar ejes
		if(myWorld.isAxisActivate()){
			myWorld.setAxisActivate(false);
			cout << "ejes desactivados" << endl;
		}else{
			myWorld.setAxisActivate(true);
			cout << "ejes activados" << endl;
		}
		break;
	case 'w':
	case 'W':
		// alambre
		glDisable(GL_LIGHTING);// se deshabilita iluminacion para alambre
		myWorld.setVisorType(WIRE);
		break;
	case 's':
	case 'S':
		// solido
		glDisable(GL_LIGHTING);// se deshabilita iluminacion para alambre
		myWorld.setVisorType(SOLID);
		break;
	case 'i':
	case 'I':
		// iluminado por defecto smooth
		glEnable(GL_LIGHTING);// se habilita iluminacion para modo iluminacion
		myWorld.setVisorType(ILUMINATED);
		break;
	case 'n':
	case 'N':
		// color negro pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(0));
		break;
	case 'v':
	case 'V':
		// color verde pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(1));
		break;
	case 'a':
	case 'A':
		// color azul pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(2));
		break;
	case 'r':
	case 'R':
		// color rojo pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(3));
		break;
	case 'y':
	case 'Y':
		// color amarillo pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(4));
		break;
	case 'b':
	case 'B':
		// color blanco pasa alambre y solido
		myWorld.setColorFiguras(myWorld.getColorDibujo(5));
		break;
	}

	usleep(1000);
}

// timer para el tiempo
static void timer(int value){
	myWorld.setTiempoTranscurrido(myWorld.getTiempoTranscurrido()+myWorld.getSpeedTime());
	glutPostRedisplay();
	// refreshRate milliseconds
	glutTimerFunc(refreshRate, timer, 0);
}

// ==== Definicion de la estructura TObject ===================================
struct TObject{
  int id;                      // Identificador del patron
  int visible;                 // Es visible el objeto?
  double width;                // Ancho del patron
  double center[2];            // Centro del patron  
  double patt_trans[3][4];     // Matriz asociada al patron
  void (* drawme)(void);       // Puntero a funcion drawme
};

// ===== Declaración de la estructura objects y del nº de objetos nobjects===============
struct TObject *objects = NULL;
int nobjects = 0;
// ==== Funcion de error ===============
static void print_error (char *error) {
	printf(error); exit(0);
}

// ==== addObject (Añade objeto a la lista de objetos) ==============
void addObject(char *p, double w, double c[2], void (*drawme)(void)) 
{
  int pattid;

  if((pattid=arLoadPatt(p)) < 0) 
    print_error ("Error en carga de patron\n");

  nobjects++;
  objects = (struct TObject *) 
    realloc(objects, sizeof(struct TObject)*nobjects);

  objects[nobjects-1].id = pattid;
  objects[nobjects-1].width = w;
  objects[nobjects-1].center[0] = c[0];
  objects[nobjects-1].center[1] = c[1];
  objects[nobjects-1].drawme = drawme;
}
// ======== cleanup =================================================
static void cleanup(void) {
	arVideoCapStop();            // Libera recursos al salir ...
	arVideoClose();
	argCleanup();
	free(objects);
}

// ======== draw ====================================================
/**
 * Funcion para dibujar los objetos de la escena
*/
static void draw( void ) {

	double  gl_para[16], OpenGlMat[16];   // Esta matriz 4x4 es la usada por OpenGL
	int i;

	argDrawMode3D();              // Cambiamos el contexto a 3D
	argDraw3dCamera(0, 0);        // Y la vista de la camara a 3D
	glClear(GL_DEPTH_BUFFER_BIT); // Limpiamos buffer de profundidad
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_NORMALIZE);
	glEnable(GL_CULL_FACE);
	glDepthFunc(GL_LEQUAL);
	for (i=0; i<nobjects; i++) {
    		if (objects[i].visible) {   // Si el objeto es visible(una marca i)
			if(objects[i].id == 0){// si visible marca que cambia el planetario
				objects[i].drawme();
				//cout << "identificador del patron en pantalla " << objects[i].id << endl;
			}else if(objects[i].id == 1){// si visible marca de planetario
				argConvGlpara(objects[i].patt_trans, gl_para);   
      				glMatrixMode(GL_MODELVIEW);           
      				glLoadMatrixd(gl_para);   // Cargamos su matriz de transf.            
			
				myWorld.getLights()[0].updateLightPosition(0.0f, 0.0f, 0.0f, 1.0f);
				myWorld.getLights()[0].setActive(stateOfPlanetsLight);
				myWorld.getLights()[7].updateLightPosition(objects[i].patt_trans[0][3], objects[i].patt_trans[1][3], objects[i].patt_trans[2][3], 1.0f);
				//cout << "identificador del patron en pantalla " << objects[i].id << endl;
      				objects[i].drawme();      // Llamamos a su función de dibujar
			}
    		}else if(objects[i].visible==0 && objects[i].id == 0){// para cuando la marca que cambia el planetario no es visible
			myWorld.setSecondPatternActivated(false);
			myWorld.getLights()[0].setActive(stateOfPlanetsLight);
		}
  	}

	glDisable(GL_DEPTH_TEST);
}

// ======== init ====================================================
static void init( void ) {
	ARParam  wparam, cparam;   // Parametros intrinsecos de la camara
  	int xsize, ysize;          // Tamano del video de camara (pixels)
  	double c[2] = {0.0, 0.0};  // Centro de patron (por defecto)
  	myWorld.initMundo();// nuestro mundo init
  	// Abrimos dispositivo de video
  	if(arVideoOpen("-dev=/dev/video0") < 0) exit(0);  
  	if(arVideoInqSize(&xsize, &ysize) < 0) exit(0);

  	// Cargamos los parametros intrinsecos de la camara
  	if(arParamLoad("data/camera_para.dat", 1, &wparam) < 0)   
    	print_error ("Error en carga de parametros de camara\n");
  
  	arParamChangeSize(&wparam, xsize, ysize, &cparam);
  	arInitCparam(&cparam);   // Inicializamos la camara con "cparam"

  	// Inicializamos la lista de objetos, id de insercion: al primero se le asignara el 0, al segundo el 1 y asi
	// sucesivamente.
	addObject("data/pequenho.patt", 90.0, c, Mundo::eraseSunAndLights);// modificar planerario en algo
  	addObject("data/simple.patt", 120.0, c, Mundo::drawMyWorld);// pintar planetario 
  	argInit(&cparam, 1.0, 0, 0, 0, 0);   // Abrimos la ventana
}

// ======== mainLoop ================================================
static void mainLoop(void) {
	ARUint8 *dataPtr;
	ARMarkerInfo *marker_info;
	int marker_num, i, j, k;
  // Capturamos un frame de la camara de video
  if((dataPtr = (ARUint8 *)arVideoGetImage()) == NULL) {
    // Si devuelve NULL es porque no hay un nuevo frame listo
    arUtilSleep(2);  return;  // Dormimos el hilo 2ms y salimos
  }
	argDrawMode2D();
  	argDispImage(dataPtr, 0,0);    // Dibujamos lo que ve la camara
  // Detectamos la marca en el frame capturado (return -1 si error)
  if(arDetectMarker(dataPtr, 100, &marker_info, &marker_num) < 0) {
    	cleanup(); exit(0);   // Si devolvio -1, salimos del programa!
  }
  arVideoCapNext();      // Frame pintado y analizado... A por otro!
  // Vemos donde detecta el patron con mayor fiabilidad
  for (i=0; i<nobjects; i++) {
    for(j = 0, k = -1; j < marker_num; j++) {
      if(objects[i].id == marker_info[j].id) {
	if (k == -1) k = j;
	else if(marker_info[k].cf < marker_info[j].cf) k = j;
      }
    }
    
    if(k != -1) {   // Si ha detectado el patron en algun sitio...
      objects[i].visible = 1;
      arGetTransMat(&marker_info[k], objects[i].center, 
		    objects[i].width, objects[i].patt_trans);
    } else { objects[i].visible = 0; }  // El objeto no es visible
  }
  draw();           // Dibujamos los objetos de la escena
  argSwapBuffers(); // Cambiamos el buffer con lo que tenga dibujado
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);    // Creamos la ventana OpenGL con Glut
	init();                   // Llamada a nuestra funcion de inicio
	arVideoCapStart();        // Creamos un hilo para captura de video
	timer(0);// temporizador
	argMainLoop( MouseEvent, keyPressed, mainLoop );   // Asociamos callbacks...
	return 0;
}
