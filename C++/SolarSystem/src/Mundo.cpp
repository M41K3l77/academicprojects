/*
 * Mundo.cpp
 *
 *  Created on: 20 de oct. de 2015
 *      Author: miguel
 */

#include "Mundo.h"
#include <vector>
#include <cmath>
#include "XMLparser.h"
using namespace std;
Mundo::Mundo() {
	// TODO Auto-generated constructor stub
	this->alfa=0;
	this->beta=0;
	this->visorType=ILUMINATED;// por defecto iluminacion activada
	this->primitiveAparience=GL_SMOOTH;
	this->colorFondo.setRGB(0, 0, 0);
	this->colorFiguras.setRGB(255, 255, 255);
	this->cameraNumber=0;
	this->iniciarAplicacion=false;
}
/**
 * Inicializacion del mundo apartir del parseo del xml
 */
void Mundo::initMundo(){
	this->createLights();
	XMLparser xmlParser;
	xmlParser.parserSolarSystem();
	cout << "parseando xml" << endl;
	this->modelos=xmlParser.getModelos();
	this->camerasPositions=xmlParser.getCameraPositions();
}

float Mundo::getAlfa() const {
	return alfa;
}

void Mundo::setAlfa(float alfa) {
	this->alfa = alfa;
}

float Mundo::getBeta() const {
	return beta;
}

void Mundo::setBeta(float beta) {
	this->beta = beta;
}

VisorType Mundo::getVisorType() const {
	return visorType;
}

void Mundo::setVisorType(VisorType visorType) {
	this->visorType = visorType;
}

void Mundo::setPrimitiveAparience(const GLenum &primitiveAparience) {
	this->primitiveAparience = primitiveAparience;
}
/**
 * Dar el color elegido a cada figura siempre y cuando no estemos
 * en modo iluminacion
 */
void Mundo::setColorFiguras(const Color& colorFiguras) {
	this->colorFiguras = colorFiguras;
	for (unsigned i = 0; i < this->modelos.size(); i++) {
		this->modelos[i].setFigureColor(this->colorFiguras);
		for (unsigned j = 0; j < this->modelos[i].getLunas().size(); j++) {
			this->modelos[i].getLunas()[j].setFigureColor(this->colorFiguras);
		}
	}
}

void Mundo::setColorFondo(const Color& colorFondo) {
	this->colorFondo = colorFondo;
}

void Mundo::setCameraNumber(const int &cameraNumber) {
	this->cameraNumber = cameraNumber;
}

vector<Light>& Mundo::getLights() {
	return lights;
}

void Mundo::setLights(const vector<Light>& lights) {
	this->lights = lights;
}
/**
 * colocar mamara en la posicion previamente seleccionada
 */
void Mundo::chooseCamera(){
	this->camera.setCameraValues(this->camerasPositions[this->cameraNumber]);
	this->camera.setCamera();
}
/**
 * Creamos las luces(focos) y las guardamos en un vector
 */
void Mundo::createLights(){
	Light light0;	light0.setLightPost(0.0f, 0.0f, 0.0f, 1.0f);		light0.setLight(GL_LIGHT0);	lights.push_back(light0);
	Light light1;	light1.setLightPost(0.0f, 0.0f, 0.0f, 1.0f);		light1.setLight(GL_LIGHT1);	lights.push_back(light1);
	Light light2;	light2.setLightPost(100.0f, 0.0f, 0.0f, 1.0f);		light2.setLight(GL_LIGHT2);	lights.push_back(light2);
	Light light3;	light3.setLightPost(0.0f, 100.0f, 0.0f, 1.0f);		light3.setLight(GL_LIGHT3);	lights.push_back(light3);
	Light light4;	light4.setLightPost(0.0f, -100.0f, 0.0f, 1.0f);		light4.setLight(GL_LIGHT4);	lights.push_back(light4);
	Light light5;	light5.setLightPost(0.0f, 0.0f, -100.0f, 1.0f);		light5.setLight(GL_LIGHT5);	lights.push_back(light5);
	Light light6;	light6.setLightPost(-100.0f, 100.0f, 100.0f, 1.0f);	light6.setLight(GL_LIGHT6);	lights.push_back(light6);
	Light light7;	light7.setLightPost(100.0f, 100.0f, 100.0f, 1.0f);	light7.setLight(GL_LIGHT7);	lights.push_back(light7);
}

/**
 * Metodo para poder tener iluminacion con una luz por defecto al arrancar la aplicacion.
 * De lo contrario las matrices se lian y la posicion de la luz no seria la correcta.
 */
void Mundo::activateIluminationByDefault(){
	if(this->iniciarAplicacion==false){
		lights[0].setWhiteLight();// para el resto de objetos
		lights[7].setSpecialLight();// para ver el sol
		glEnable(GL_LIGHTING);
		this->iniciarAplicacion=true;
	}
}
/**
 * Metodo que renderiza nuestro mundo
 */
void  Mundo::drawMyWorld(const float &tiempoTranscurrido, const int &screenwidth, const int &screenHeight, const float &zoom, const bool &axisActivate){

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// cambio color de fondo segun menu
	glClearColor(colorFondo.getR()/255, colorFondo.getG()/255, colorFondo.getB()/255, 1.0f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	// El primer parametro es el angulo de vision, el segundo parametro es el aspecto(valor 1.0f para no tener deformacion)
	// en nuestro caso tenemos en cuenta el cambio de tamanho de la ventana por parte del usuario,
	// el tercero es cuan de cerca vemos y el cuarto hasta cuan lejos vemos
	gluPerspective(90.0f, (float)screenwidth/(float)screenHeight, 0.1f, 500.0f);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	// Ponemos la camara
	this->chooseCamera();
	// si se acaba de arrancar la aplicacion se activa iluminacion con una luz por defecto
	// si se intenta hacer antes del drawmodel se lian las matrices!!!!!!!!!!!!!! y la posicion
	// de la luz no es la que deberia
	this->activateIluminationByDefault();
	// escala los elementos zoom
	glScalef(zoom, zoom, zoom);
	// dibujamos los ejes fijos
	this->drawAbsoluteAxis(axisActivate);
	// rotar sobre eje x e y. con el movimiento del raton
	glRotatef(alfa, 1.0, 0.0, 0.0);
	glRotatef(beta, 0.0, 1.0, 0.0);
	// render sistema solar
	for (unsigned i = 0; i < this->modelos.size(); i++) {
		if(i==0 && this->visorType==ILUMINATED){// si estamos en modo iluminado se ilumina al sol
			lights[7].activateLight();// para ver el sol
			modelos[i].renderModel3D(this->visorType, this->primitiveAparience, tiempoTranscurrido, axisActivate);
			lights[7].deactivateLight();
		}else{
			modelos[i].renderModel3D(this->visorType, this->primitiveAparience, tiempoTranscurrido, axisActivate);
		}
	}
	glFlush();
	glutSwapBuffers();// Pasamos del buffer de escritura al lienzo (de un buffer a otro)
}

/**
 * Pinta los ejes absolutos
 */
void Mundo::drawAbsoluteAxis(const bool &axisActivate){
	// comprobar que el usuario quiere ver los ejes(por teclado)
	if(axisActivate){
		if(visorType==ILUMINATED){// iluminacion activada
			glDisable(GL_LIGHTING);
		}
		//dibujar ejes
		glBegin(GL_LINES);
		// dibujar eje x en verde
		glColor3f(0.0, 1.0, 0.0);
		glVertex3f(-10.0, 0.0, 0.0);
		glVertex3f(10.0, 0.0, 0.0);
		// dibujar eje y en
		glColor3f(1.0, 0.0, 1.0);
		glVertex3f(0.0, -10.0, 0.0);
		glVertex3f(0.0, 10.0, 0.0);
		// dibujar eje z en naranja
		glColor3f(1.0, 0.5, 0.0);
		glVertex3f(0.0, 0.0, -10.0);
		glVertex3f(0.0, 0.0, 10.0);
		glEnd();
		// fin ejes
		if(visorType==ILUMINATED){
			glEnable(GL_LIGHTING);
		}
	}
}

vector<Modelo3D>& Mundo::getModelos(){
	return modelos;
}

void Mundo::setModelos(const vector<Modelo3D>& modelos) {
	this->modelos = modelos;
}

Mundo::~Mundo() {
	// TODO Auto-generated destructor stub
}
