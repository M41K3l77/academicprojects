/*
 * Mundo.h
 *
 *  Created on: 20 de oct. de 2015
 *      Author: miguel
 */

#ifndef MUNDO_H_
#define MUNDO_H_

#include <GL/glut.h>
#include <vector>
#include "Camera.h"
#include "Light.h"
#include "Color.h"
#include "Modelo3D.h"
using namespace std;
class Mundo {
private:
	vector <Modelo3D> modelos;
	float alfa;
	float beta;
	// Nos dice si el modelo se representara en alambre, solida o iluminacion
	VisorType visorType;
	// constante(flat) o suave (smooth)
	GLenum primitiveAparience;
	// color del fondo y de las figuras
	Color colorFondo;
	Color colorFiguras;
	// focos
	vector < Light > lights;
	// posiciones de la camara
	vector < vector < float > > camerasPositions;
	//camara
	Camera camera;
	// posicion de la camara elegida
	int cameraNumber;
	// para controlar la iluminacion por defecto al arrancar
	bool iniciarAplicacion;
	void createLights();
	void chooseCamera();
public:
	Mundo();
	void initMundo();
	float getAlfa() const;
	void setAlfa(float alfa);
	float getBeta() const;
	void setBeta(float beta);
	VisorType getVisorType() const;
	void setVisorType(VisorType visorType);
	void setPrimitiveAparience(const GLenum &primitiveAparience);
	void setColorFiguras(const Color& colorFiguras);
	void setColorFondo(const Color& colorFondo);
	void setCameraNumber(const int &cameraNumber);
	void setAngulo(float angulo);
	float getAngulo() const;
	vector<Light>& getLights();
	void setLights(const vector<Light>& lights);
	void activateIluminationByDefault();
	// dibuja ejes absolutos
	void drawAbsoluteAxis(const bool &axisActivate);
	// Dibuja el modelo
	void drawMyWorld(const float &tiempoTranscurrido, const int &screenwidth, const int &screenHeight, const float &zoom, const bool &axisActivate);
	vector<Modelo3D>& getModelos();
	void setModelos(const vector<Modelo3D>& modelos);
	~Mundo();
};

#endif /* MUNDO_H_ */
