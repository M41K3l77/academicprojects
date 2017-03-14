/*
 * Light.cpp
 *
 *  Created on: 14 de oct. de 2015
 *      Author: miguel
 *	solo usaremos los basicos:
 *	GLfloat SpotExponent=0;
 *	glLightfv(this->light,GL_AMBIENT,lightAmbient);
 *	glLightfv(this->light,GL_DIFFUSE,lightDiffuse);
 *	glLightfv(this->light,GL_SPECULAR,lightSpecular);
 *	glLightfv(this->light,GL_POSITION,this->lightPos);
 *	glLightf(this->light,GL_SPOT_EXPONENT, 0.0f);
 *	glLightf(this->light,GL_SPOT_CUTOFF,180.0f);
 *	glLightf(this->light,GL_SPOT_DIRECTION, 0.0f) ;
 *	glLightf(this->light,GL_CONSTANT_ATTENUATION, 1.0f);
 *	glLightf(this->light,GL_LINEAR_ATTENUATION, 0.0f);
 *	glLightf(this->light,GL_QUADRATIC_ATTENUATION, 0.0f);
 *
 */

#include "Light.h"
#include <iostream>
Light::Light() {
	this->light=GL_LIGHT0;
}

Light::Light(const GLenum &light) {
	this->light=light;
}

void Light::setLightPost(const float &x, const float &y, const float &z, const float &w){
	this->lightPos[0]=x;
	this->lightPos[1]=y;
	this->lightPos[2]=z;
	this->lightPos[3]=w;
}

GLenum Light::getLight(){
	return light;
}

void Light::setLight(GLenum light) {
	this->light = light;
}


void Light::activateLighting(){
	glEnable(GL_LIGHTING);
}

void Light::deactivateLighting(){
	glDisable(GL_LIGHTING);
}

void Light::checkActivateLight(){
	if(glIsEnabled(this->light)){
		glDisable(this->light);
	}
}

void Light::activateLight(){
	glEnable(this->light);
}

void Light::deactivateLight(){
	glDisable(this->light);
}

/**
 * Metodo de apoyo para dejar la luz como estaba, digamos que hace de memoria,
 * la necesitamos por que hemos anhadido una segunda marca que desabilita luces
 * en el proyecto solo se usa para la luz que ilumina los planetas (light0)
*/
void Light::setActive(bool active){
	if(active){
		glEnable(this->light);
	}else{
		glDisable(this->light);
	}	
}

void Light::updateLightPosition(const float &x, const float &y, const float &z, const float &w){
	this->lightPos[0]=x;
	this->lightPos[1]=y;
	this->lightPos[2]=z;
	this->lightPos[3]=w;
	if(glIsEnabled(this->light)){
		glDisable(this->light);
		glLightfv(this->light, GL_POSITION, this->lightPos);
		glEnable(this->light);
	}
	
}
void Light::setWhiteLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
	float lightSpecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);

	glEnable(this->light);
}

void Light::setRedLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {1.0f, 0.0f, 0.0f, 1.0f};
	float lightSpecular[] = {1.0f, 0.0f, 0.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

void Light::setGreenLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {0.0f, 1.0f, 0.0f, 1.0f};
	float lightSpecular[] = {0.0f, 1.0f, 0.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

void Light::setBlueLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {0.0f, 0.0f, 1.0f, 1.0f};
	float lightSpecular[] = {0.0f, 0.0f, 1.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

void Light::setRedGreenLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {1.0f, 1.0f, 0.0f, 1.0f};
	float lightSpecular[] = {1.0f, 1.0f, 0.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

void Light::setRedBlueLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {1.0f, 0.0f, 1.0f, 1.0f};
	float lightSpecular[] = {1.0f, 0.0f, 1.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

void Light::setGreenBlueLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {0.0f, 1.0f, 1.0f, 1.0f};
	float lightSpecular[] = {0.0f, 1.0f, 1.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}
/**
 * Esta luz es especial y no se puede desactivar en iluminacion ya que es la
 * que ilumina el sol
 */
void Light::setSpecialLight(){
	this->checkActivateLight();
	float lightAmbient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	float lightDiffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
	float lightSpecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
	glLightfv(this->light, GL_AMBIENT, lightAmbient);
	glLightfv(this->light, GL_DIFFUSE, lightDiffuse);
	glLightfv(this->light, GL_SPECULAR, lightSpecular);
	// GL_POSITION se corresponde con el valor de la coordenada homogénea (X, Y, Z, W) dónde colocar la luz.
	glLightfv(this->light, GL_POSITION, this->lightPos);
	glEnable(this->light);
}

Light::~Light() {
	// TODO Auto-generated destructor stub
}

