/*
 * Cara.h
 *
 *  Created on: 1 de oct. de 2015
 *      Author: miguel
 */

#ifndef CARA_H_
#define CARA_H_
// Estructura para la normal de la cara
typedef struct{
	float x;
	float y;
	float z;
}TNormal;
// Clase para guardar una cara de la figura
class Cara {
	// Los tres vertices de una cara
	int a, b, c;
	// Normal de la cara
	TNormal normal;
public:
	Cara();
	Cara(const int &a, const int &b, const int &c);
	Cara(const int &a, const int &b, const int &c, const TNormal &normal);
	int getA() const;
	int getB() const;
	int getC() const;
	const TNormal& getNormal() const;
	void setA(int a);
	void setB(int b);
	void setC(int c);
	void setNormal(const TNormal& normal);
	~Cara();
};

#endif /* CARA_H_ */
