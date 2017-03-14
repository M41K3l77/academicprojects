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
float Mundo::alfa=0;
float Mundo::beta=0;
VisorType Mundo::visorType=ILUMINATED;
GLenum Mundo::primitiveAparience=GL_SMOOTH;
bool Mundo::iniciarAplicacion=false;
bool Mundo::axisActivate=false;
float Mundo::zoom=1.0f;
float Mundo::tiempoTranscurrido=0.0f;
vector <Modelo3D> Mundo::modelos;
vector < Light > Mundo::lights;
bool Mundo::secondPatternActivated=false;
Mundo::Mundo() {
	// TODO Auto-generated constructor stub
	this->colorFondo.setRGB(0, 0, 0);
	this->colorFiguras.setRGB(255, 255, 255);
	this->cameraNumber=0;
	this->zoomControl=0;// para limitar el zoom
	this->limitZoomControl=16;// limite para el zoom
	// incremento del tiempo
	this->speedTime=0.04f;
	// control de hata cuanto se puede aumentar la velocidad del tiempo
	this->timeControl=0;
	this->limitTimeControl=16;
	Color negro(0, 0, 0);
	Color verde(0, 255, 0);
	Color azul(0, 0, 255);
	Color rojo(255, 0, 0);
	Color amarillo(255, 255, 0);
	Color blanco(255, 255, 255);
	// Se añaden los colores al vector
	colores.push_back(negro);	colores.push_back(verde);
	colores.push_back(azul);	colores.push_back(rojo);
	colores.push_back(amarillo);	colores.push_back(blanco);
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

void Mundo::setAlfa(float Alfa) {
	alfa = Alfa;
}

float Mundo::getBeta() const {
	return beta;
}

void Mundo::setBeta(float Beta) {
	beta = Beta;
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

Color Mundo::getColorDibujo(const int &opcion) {
	return this->colores[opcion];
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

float Mundo::getSpeedTime() const {
	return speedTime;
}

void Mundo::setSpeedTime(const float &speedTime) {
	this->speedTime = speedTime;
}

float Mundo::getTiempoTranscurrido() const {
	return tiempoTranscurrido;
}

void Mundo::setTiempoTranscurrido(const float &tiempoTranscurrido) {
	this->tiempoTranscurrido = tiempoTranscurrido;
}

int Mundo::getLimitTimeControl() const {
	return limitTimeControl;
}

void Mundo::setLimitTimeControl(const int &limitTimeControl) {
	this->limitTimeControl = limitTimeControl;
}

int Mundo::getTimeControl() const {
	return timeControl;
}

void Mundo::setTimeControl(const int &timeControl) {
	this->timeControl = timeControl;
}

int Mundo::getLimitZoomControl() const {
	return limitZoomControl;
}

void Mundo::setLimitZoomControl(const int &limitZoomControl) {
	this->limitZoomControl = limitZoomControl;
}

float Mundo::getZoom() const {
	return zoom;
}

void Mundo::setZoom(const float &zoom) {
	this->zoom = zoom;
}

int Mundo::getZoomControl() const {
	return zoomControl;
}

void Mundo::setZoomControl(const int &zoomControl) {
	this->zoomControl = zoomControl;
}

bool Mundo::isAxisActivate() const {
	return axisActivate;
}

void Mundo::setAxisActivate(const bool &axisActivate) {
	this->axisActivate = axisActivate;
}

void Mundo::setSecondPatternActivated(const int &secondPatternActivated){
	this->secondPatternActivated=secondPatternActivated;
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
	if(iniciarAplicacion==false){
		lights[0].setWhiteLight();// para el resto de objetos
		lights[7].setSpecialLight();// para ver el sol
		glEnable(GL_LIGHTING);
		iniciarAplicacion=true;
	}
}
/**
 * Metodo que renderiza nuestro mundo
 */
void Mundo::drawMyWorld(){

	// si se acaba de arrancar la aplicacion se activa iluminacion con una luz por defecto
	// si se intenta hacer antes del drawmodel se lian las matrices!!!!!!!!!!!!!! y la posicion
	// de la luz no es la que deberia
	activateIluminationByDefault();
	// escala los elementos zoom
	glScalef(zoom, zoom, zoom);
	// dibujamos los ejes fijos
	drawAbsoluteAxis();
	// rotar sobre eje x e y. con el movimiento del raton
	glRotatef(alfa, 1.0, 0.0, 0.0);
	glRotatef(beta, 0.0, 1.0, 0.0);
	// render sistema solar
	for (unsigned i = 0; i < modelos.size(); i++) {
		if(secondPatternActivated){// si la segunda marca entra en accion se aplican cambios
			if(i==0){
				lights[0].deactivateLight();
				lights[7].deactivateLight();
			}else{
				modelos[i].renderModel3D(visorType, primitiveAparience, tiempoTranscurrido, axisActivate);
			}			
		}else{// si no hay cambios por la segunda marca se pinta tal cual
			if(i==0 && visorType==ILUMINATED){// si estamos en modo iluminado se ilumina al sol
				lights[7].activateLight();// para ver el sol
				modelos[i].renderModel3D(visorType, primitiveAparience, tiempoTranscurrido, axisActivate);
				lights[7].deactivateLight();
			}else{
				modelos[i].renderModel3D(visorType, primitiveAparience, tiempoTranscurrido, axisActivate);
			}
		}		
	}
}

/**
 * Pinta los ejes absolutos
 */
void Mundo::drawAbsoluteAxis(){
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

void Mundo::eraseSunAndLights(){
	secondPatternActivated=true;
}
Mundo::~Mundo() {
	// TODO Auto-generated destructor stub
}
