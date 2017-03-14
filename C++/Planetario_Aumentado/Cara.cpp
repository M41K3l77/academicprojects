/*
 * Cara.cpp
 *
 *  Created on: 1 de oct. de 2015
 *      Author: miguel
 */

#include "Cara.h"
// Constructores
Cara::Cara() {
	this->a=0;
	this->b=0;
	this->c=0;
}

Cara::Cara(const int &a, const int &b, const int &c) {
	this->a=a;
	this->b=b;
	this->c=c;
}

Cara::Cara(const int &a, const int &b, const int &c, const TNormal &normal) {
	this->a=a;
	this->b=b;
	this->c=c;
	this->normal=normal;
}
// getters y setters
int Cara::getA() const {
	return a;
}

void Cara::setA(int a) {
	this->a = a;
}

int Cara::getB() const {
	return b;
}

void Cara::setB(int b) {
	this->b = b;
}

int Cara::getC() const {
	return c;
}

void Cara::setC(int c) {
	this->c = c;
}

const TNormal& Cara::getNormal() const {
	return normal;
}

void Cara::setNormal(const TNormal& normal) {
	this->normal = normal;
}
// Destructor
Cara::~Cara() {
	// TODO Auto-generated destructor stub
}

