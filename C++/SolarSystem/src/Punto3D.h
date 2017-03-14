/*
 * Punto3D.h
 *
 *  Created on: 1 de oct. de 2015
 *      Author: miguel
 */

#ifndef PUNTO3D_H_
#define PUNTO3D_H_
// Clase para un punto en 3D
class Punto3D {
private:
	// Coordenadas del punto
	float x;
	float y;
	float z;
public:
	// Constructores
	Punto3D();
	Punto3D(const float &x, const float &y, const float &z);
	// getters y setters
	float getX() const;
	float getY() const;
	float getZ() const;
	void setX(const float &x);
	void setY(const float &y);
	void setZ(const float &z);
	// Destructor
	~Punto3D();
};

#endif /* PUNTO3D_H_ */
