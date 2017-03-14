/*
 * Color.cpp
 *
 *  Created on: 10 de oct. de 2015
 *      Author: miguel
 */

#include "Color.h"

Color::Color(){
	this->R=0;
	this->G=0;
	this->B=0;
}

Color::Color(const int &r, const int &g, const int &b) {
	this->R=r;
	this->G=g;
	this->B=b;
}

int Color::getB() const {
	return B;
}

void Color::setB(int b) {
	B = b;
}

int Color::getG() const {
	return G;
}

void Color::setG(int g) {
	G = g;
}

int Color::getR() const {
	return R;
}

void Color::setR(int r) {
	R = r;
}

void Color::setRGB(const int &r, const int &g, const int &b) {
	R = r;
	G = g;
	B = b;
}

Color::~Color() {
	// TODO Auto-generated destructor stub
}

