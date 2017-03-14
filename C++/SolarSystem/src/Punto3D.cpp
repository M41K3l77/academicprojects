/*
 * Punto3D.cpp
 *
 *  Created on: 1 de oct. de 2015
 *      Author: miguel
 */

#include "Punto3D.h"

Punto3D::Punto3D() {
	this->x=0;
	this->y=0;
	this->z=0;
}
Punto3D::Punto3D(const float &x, const float &y, const float &z) {
	this->x=x;
	this->y=y;
	this->z=z;
}
float Punto3D::getX() const {
	return (this->x);
}
float Punto3D::getY() const {
	return (this->y);
}
float Punto3D::getZ() const {
	return (this->z);
}
void Punto3D::setX(const float &x) {
	this->x=x;
}
void Punto3D::setY(const float &y) {
	this->y=y;
}
void Punto3D::setZ(const float &z) {
	this->z=z;
}
Punto3D::~Punto3D() {
	// TODO Auto-generated destructor stub
}

