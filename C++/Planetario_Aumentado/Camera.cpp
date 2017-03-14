/*
 * Camera.cpp
 *
 *  Created on: 20 de oct. de 2015
 *      Author: miguel
 */

#include <GL/glut.h>

#include "Camera.h"

Camera::Camera(){
	this->eyeX=2.5;
	this->eyeY=2.5;
	this->eyeZ=2.5;
	this->pos0X=0;
	this->pos0Y=0;
	this->pos0Z=0;
	this->upX=2.5;
	this->upY=-2.5;
	this->upZ=2.5;
}

Camera::Camera(const float &eyeX, const float &eyeY, const float &eyeZ, const float &pos0X, const float &pos0Y, const float &pos0Z, const float &upX, const float &upY, const float &upZ) {
	this->eyeX=eyeX;
	this->eyeY=eyeY;
	this->eyeZ=eyeZ;
	this->pos0X=pos0X;
	this->pos0Y=pos0Y;
	this->pos0Z=pos0Z;
	this->upX=upX;
	this->upY=upY;
	this->upZ=upZ;

}
/**
 * Metodo para dar los valores de las coordenadas de la camara
 */
void Camera::setCameraValues(const vector <float> &cameraValues){
	this->eyeX=cameraValues[0];
	this->eyeY=cameraValues[1];
	this->eyeZ=cameraValues[2];
	this->pos0X=cameraValues[3];
	this->pos0Y=cameraValues[4];
	this->pos0Z=cameraValues[5];
	this->upX=cameraValues[6];
	this->upY=cameraValues[7];
	this->upZ=cameraValues[8];
}
/**
 * Metodo que hace la llamada a gluLookAt
 */
void Camera::setCamera(){
	gluLookAt(this->eyeX, this->eyeY, this->eyeZ, this->pos0X, this->pos0Y, this->pos0Z, this->upX, this->upY, this->upZ);
}

Camera::~Camera() {
	// TODO Auto-generated destructor stub
}

