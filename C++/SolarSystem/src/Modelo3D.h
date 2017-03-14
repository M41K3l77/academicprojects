/*
 * Modelo.h
 *
 *  Created on: 6 de oct. de 2015
 *      Author: miguel
 */

#ifndef MODELO3D_H_
#define MODELO3D_H_
#include <GL/glut.h>
#include <unistd.h>
#include <cstdio>
#include <iostream>
#include <cstdlib>
#include <cstring>
#include <vector>
#include <math.h>
#include "Cara.h"
#include "Punto3D.h"
#include "Color.h"
#include "Light.h"
#include "MaterialProperty.h"
using namespace std;
// Lista de caras de la figura
typedef vector <Cara> ListaFaces;
// Lista de puntos de la figura
typedef vector <Punto3D> ListaPoints3D;
// Material del modelo
typedef struct{
	float ambientMaterial[4];
	float diffuseMaterial[4];
	float specularMaterial[4];
	float emissionMaterial[4];
	float shine[1];
}Material;
// Nos dice si el modelo se representara en alanbre, solida o iluminacion
typedef enum {WIRE, SOLID, ILUMINATED} VisorType;
// propiedad material
typedef enum {SUN, MERCURY, VENUS, EARTH, MOON, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO} celestialBodyMaterialProperty;

// Clase Modelo de la figura que representaremos en 3D
class Modelo3D {
private:
	int numCaras;
	int numVertices;
	ListaFaces ListaCaras;
	ListaPoints3D ListaPuntos3D;
	Material material;
	// distancia al sol
	float distanceFromStar;
	// scala radio del planeta para tamanho del mismo
	float radiusScale;
	// velocidad de translacion
	float orbitSpeed;
	// velociad de rotacion
	float rotationSpeed;
	// Color figura en alambre y solido
	Color figureColor;
	// lunas del modelo
	vector <Modelo3D> lunas;
	// propiedades del material
	MaterialProperty materialProperty;
	void copy4Array(vector < float > materialProperty, float (&arrayMaterial)[4]);
public:
	// Constructores
	Modelo3D();
	Modelo3D(const float &distanceFromStar, const float &orbitSpeed, const float &rotationSpeed, const float &radiusScale, const celestialBodyMaterialProperty &materialProperty);
	// gettters y setters
	void setNCaras(const int &numCaras);
	void setNVertices(const int &numVertices);
	int getNCaras() const;
	int getNVertices() const;
	const ListaFaces& getListaCaras() const;
	void setListaCaras(const ListaFaces& listaCaras);
	const ListaPoints3D& getListaPuntos3D() const;
	void setListaPuntos3D(const ListaPoints3D& listaPuntos3D);
	float getOrbitSpeed() const;
	void setOrbitSpeed(float orbitSpeed);
	void setMaterialProperties(const celestialBodyMaterialProperty &materialPropertyNumber);
	void setMaterial();
	float getDistanceFromStar() const;
	void setDistanceFromStar(float distanceFromStar);
	void renderModel3D(const VisorType &visorType, const GLenum &primitiveAparience, const float &tiempoTranscurrido, const bool &axisActivate);
	void renderLuna(const VisorType &visorType, const GLenum &primitiveAparience, const float &tiempoTranscurrido, const bool &axisActivate);
	void renderOrbit(const VisorType &visorType);
	// Carga el archivo donde estan los datos del modelo a representar
	void Load_Model(char fileName[50]);
	float getRotationSpeed() const;
	void setRotationSpeed(float rotationSpeed);
	float getRadiusScale() const;
	void setRadiusScale(float radiusScale);
	void setFigureColor(const Color& figureColor);
	// dibuja ejes al modelo
	void drawFigureAxis(const VisorType &visorType, const bool &axisActivate);
	vector<Modelo3D>& getLunas();
	// Destructor
	~Modelo3D();

};

#endif /* MODELO_H_ */
