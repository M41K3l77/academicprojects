/*
 * Modelo.cpp
 *
 *  Created on: 6 de oct. de 2015
 *      Author: miguel
 */
#include "Modelo3D.h"

Modelo3D::Modelo3D() {
	this->numCaras=0;
	this->numVertices=0;
	this->distanceFromStar=0;
	this->orbitSpeed=0;
	this->rotationSpeed=0;
	this->radiusScale=0;
	this->figureColor.setRGB(255, 255, 255);
	this->setMaterialProperties(SUN);
}

Modelo3D::Modelo3D(const float &distanceFromStar, const float &orbitSpeed, const float &rotationSpeed, const float &radiusScale, const celestialBodyMaterialProperty &materialProperty){
	this->numCaras=0;
	this->numVertices=0;
	this->distanceFromStar=distanceFromStar;
	this->orbitSpeed=orbitSpeed;
	this->rotationSpeed=rotationSpeed;
	this->radiusScale=radiusScale;
	this->figureColor.setRGB(255, 255, 255);
	this->setMaterialProperties(materialProperty);
}

void Modelo3D::setNCaras(const int &numCaras){
	this->numCaras=numCaras;
}

void Modelo3D::setNVertices(const int &numVertices){
	this->numVertices=numVertices;
}

int Modelo3D::getNCaras() const {
	return numCaras;
}

int Modelo3D::getNVertices() const {
	return numVertices;
}

const ListaFaces& Modelo3D::getListaCaras() const {
	return ListaCaras;
}

void Modelo3D::setListaCaras(const ListaFaces& listaCaras) {
	ListaCaras = listaCaras;
}

const ListaPoints3D& Modelo3D::getListaPuntos3D() const {
	return ListaPuntos3D;
}

void Modelo3D::setListaPuntos3D(const ListaPoints3D& listaPuntos3D) {
	ListaPuntos3D = listaPuntos3D;
}


float Modelo3D::getDistanceFromStar() const {
	return distanceFromStar;
}

void Modelo3D::setDistanceFromStar(float distanceFromStar) {
	this->distanceFromStar = distanceFromStar;
}

float Modelo3D::getOrbitSpeed() const {
	return orbitSpeed;
}

void Modelo3D::setOrbitSpeed(float orbitSpeed) {
	this->orbitSpeed = orbitSpeed;
}
/**
 * metodo auxiliar para copiar arrays
 */
void Modelo3D::copy4Array(vector < float > materialProperty, float (&arrayMaterial)[4]){
	arrayMaterial[0]=materialProperty[0];
	arrayMaterial[1]=materialProperty[1];
	arrayMaterial[2]=materialProperty[2];
	arrayMaterial[3]=materialProperty[3];
}
/**
 * Metodo que pone las propiedades del material elegido al material de la figura.
 */
void Modelo3D::setMaterialProperties(const celestialBodyMaterialProperty &materialPropertyNumber){
	switch (materialPropertyNumber) {
	case SUN:
		this->copy4Array(materialProperty.sunProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.sunProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.sunProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.sunProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.sunProperties()[4][0];
		break;
	case MERCURY:
		this->copy4Array(materialProperty.mercuryProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.mercuryProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.mercuryProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.mercuryProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.mercuryProperties()[4][0];
		break;
	case VENUS:
		this->copy4Array(materialProperty.venusProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.venusProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.venusProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.venusProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.venusProperties()[4][0];
		break;
	case EARTH:
		this->copy4Array(materialProperty.earthProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.earthProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.earthProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.earthProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.earthProperties()[4][0];
		break;
	case MOON:
		this->copy4Array(materialProperty.moonProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.moonProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.moonProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.moonProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.moonProperties()[4][0];
		break;
	case MARS:
		this->copy4Array(materialProperty.marsProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.marsProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.marsProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.marsProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.marsProperties()[4][0];
		break;
	case JUPITER:
		this->copy4Array(materialProperty.jupiterProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.jupiterProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.jupiterProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.jupiterProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.jupiterProperties()[4][0];
		break;
	case SATURN:
		this->copy4Array(materialProperty.saturnProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.saturnProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.saturnProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.saturnProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.saturnProperties()[4][0];
		break;
	case URANUS:
		this->copy4Array(materialProperty.uranusProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.uranusProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.uranusProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.uranusProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.uranusProperties()[4][0];
		break;
	case NEPTUNE:
		this->copy4Array(materialProperty.neptuneProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.neptuneProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.neptuneProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.neptuneProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.neptuneProperties()[4][0];
		break;
	case PLUTO:
		this->copy4Array(materialProperty.plutoProperties()[0], this->material.ambientMaterial);
		this->copy4Array(materialProperty.plutoProperties()[1], this->material.diffuseMaterial);
		this->copy4Array(materialProperty.plutoProperties()[2], this->material.specularMaterial);
		this->copy4Array(materialProperty.plutoProperties()[3], this->material.emissionMaterial);
		this->material.shine[0]=materialProperty.plutoProperties()[4][0];
		break;
	default:
		break;
	}
}

void Modelo3D::setMaterial(){
	glMaterialfv(GL_FRONT, GL_AMBIENT, this->material.ambientMaterial);// selecciona intensidad luz ambiente que refleja
	glMaterialfv(GL_FRONT, GL_DIFFUSE, this->material.diffuseMaterial);// selecciona intensidad luz difusa que refleja del material
	glMaterialfv(GL_FRONT, GL_SPECULAR, this->material.specularMaterial);// selecciona intensidad luz especular que refleja del material
	glMaterialfv(GL_FRONT, GL_EMISSION, this->material.emissionMaterial);// selecciona intensidad luz emitida por el material
	glMaterialfv(GL_FRONT, GL_SHININESS, this->material.shine); // seleeciona el exponente especular del material
}
/**
 * Pintar orbita
 */
void Modelo3D::renderOrbit(const VisorType &visorType){
	if(visorType==ILUMINATED){// iluminacion activada
		glDisable(GL_LIGHTING);
	}

	glColor3f(1.0f, 1.0f, 1.0f);
	glBegin(GL_LINE_STRIP);
	// recordar que es en radianes. 360 grados son 6,283185308 radianes
	for(int angle = 0; angle < 361; angle++) {
		glVertex3f((sin(angle*0.017453217))*(this->distanceFromStar), 0, (cos(angle*0.017453217))*(this->distanceFromStar));
	}
	glEnd();
	if(visorType==ILUMINATED){
		glEnable(GL_LIGHTING);
	}
}
/**
 * Renderizar luna
 */
void Modelo3D::renderLuna(const VisorType &visorType, const GLenum &primitiveAparience, const float &tiempoTranscurrido, const bool &axisActivate){
	glPushMatrix();
	// cmath trabaja angulos en radianes
	float angle=0;
	// obtenemos angulo a partir del tiempo transcurrido
	if(this->orbitSpeed != 0){// no es el sol
		angle=2*3.14159265*tiempoTranscurrido/this->orbitSpeed;
	}
	// el render orbit debe estar aqui antes de tocar la matriz con el translatef
	renderOrbit(visorType);
	// segun el angulo conseguimos la coordenada x y z
	glTranslatef((sin(angle))*(this->distanceFromStar), 0, (cos(angle))*(this->distanceFromStar));
	// Si tiene rotacion se le hace rotar sobre el eje y acorde al tiempo transcurrido
	if(this->rotationSpeed != 0){
		glRotatef(360*tiempoTranscurrido/this->rotationSpeed, 0.0, 1.0, 0.0);
	}
	// No es el radio, es la escala a la que va la figura
	glScalef(this->radiusScale, this->radiusScale, this->radiusScale);
	// dibujar ejes si procede
	this->drawFigureAxis(visorType, axisActivate);
	// dibujamos en alambre, solido o iluminado segun proceda
	switch(visorType){
	// alambre
	case WIRE:
		glColor3ub(this->figureColor.getR(), this->figureColor.getG(), this->figureColor.getB());
		glBegin(GL_LINE_STRIP);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		break;
		// solido
	case SOLID:
		glColor3ub(this->figureColor.getR(), this->figureColor.getG(), this->figureColor.getB());
		glBegin(GL_TRIANGLES);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			// importante añadir la normal
			glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		glBegin(GL_LINE_STRIP);
		glColor3f(0.0f, 0.0f, 0.0f);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			// importante añadir la normal
			glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		break;
		// iluminacion
	case ILUMINATED:
		this->setMaterial();
		glShadeModel(primitiveAparience);// se activa el tipo de sombreado
		glBegin(GL_TRIANGLES);
		switch (primitiveAparience) {
		case GL_FLAT:
			for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
				// importante añadir la normal
				glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
			}
			break;
		case GL_SMOOTH:
			for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
			}
			break;
		}
		glEnd();
		break;
	}
	glPopMatrix();
}

void Modelo3D::renderModel3D(const VisorType &visorType, const GLenum &primitiveAparience, const float &tiempoTranscurrido, const bool &axisActivate){
	glPushMatrix();
	// cmath trabaja angulos en radianes
	float angle=0;
	if(this->orbitSpeed != 0){// no es el sol
		angle=2*3.14159265*tiempoTranscurrido/this->orbitSpeed;
	}
	// el render orbit debe estar aqui antes de tocar la matriz con el translatef
	renderOrbit(visorType);
	glTranslatef((sin(angle))*(this->distanceFromStar), 0, (cos(angle))*(this->distanceFromStar));
	if(this->rotationSpeed != 0){
		glRotatef(360*tiempoTranscurrido/this->rotationSpeed, 0.0, 1.0, 0.0);
	}
	// distanceFromStar, orbitSpeed, rotationSpeed, radius
	glScalef(this->radiusScale, this->radiusScale, this->radiusScale);
	this->drawFigureAxis(visorType, axisActivate);
	switch(visorType){
	// alambre
	case WIRE:
		glColor3ub(this->figureColor.getR(), this->figureColor.getG(), this->figureColor.getB());
		glBegin(GL_LINE_STRIP);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		break;
		// solido
	case SOLID:
		glColor3ub(this->figureColor.getR(), this->figureColor.getG(), this->figureColor.getB());
		glBegin(GL_TRIANGLES);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			// importante añadir la normal
			glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		// pintamos un enrejado ecima para que no quede como una patata
		glBegin(GL_LINE_STRIP);
		glColor3f(0.0f, 0.0f, 0.0f);
		for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
			// importante añadir la normal
			glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
			glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
		}
		glEnd();
		break;
		// iluminacion
	case ILUMINATED:
		this->setMaterial();
		glShadeModel(primitiveAparience);// se activa el tipo de sombreado
		glBegin(GL_TRIANGLES);
		switch (primitiveAparience) {
		case GL_FLAT:
			for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
				// importante añadir la normal
				glNormal3f(this->ListaCaras[i].getNormal().x, this->ListaCaras[i].getNormal().y, this->ListaCaras[i].getNormal().z);
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
			}
			break;
		case GL_SMOOTH:
			for (unsigned i = 0; i < this->ListaCaras.size(); i++) {
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getA()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getA()].getZ());
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getB()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getB()].getZ());
				glNormal3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
				glVertex3f(this->ListaPuntos3D[this->ListaCaras[i].getC()].getX(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getY(), this->ListaPuntos3D[this->ListaCaras[i].getC()].getZ());
			}
			break;
		}
		glEnd();
		break;
	}
	// render lunas
	glScalef(1/this->radiusScale, 1/this->radiusScale, 1/this->radiusScale);
	for (unsigned j = 0; j < this->lunas.size(); j++) {
		this->lunas[j].renderLuna(visorType, primitiveAparience, tiempoTranscurrido, axisActivate);
	}
	glPopMatrix();
}

/**
 * Metodo que carga los datos de la figura a representar
 */
void Modelo3D::Load_Model(char fileName[50])

{ FILE *fich;
int NVertex, NFaces,VertexNumber,FaceNumber,N,A,B,C ;
float X,Y,Z,  ax,ay,az,bx,by,bz,len;
TNormal Normal;
char cad1[20],cad2[20],cad3[20],cad4[20]; char cadena[100]; // Lo suf. larga para leer una línea

if ((fich = fopen(fileName, "r")) == NULL) // open for reading  // open for reading
{ cout<<" Error en la apertura. Es posible que el fichero no exista \n "<<endl; exit(1); }
while (fgets(cadena, 100, fich) != NULL)
{
	if   (strncmp(cadena,"Named",5)==0) // Nvertex and NFaces in file
	{ fscanf(fich,"%[Tri-mesh A-Za-z:-,: ]%d%[ ]%[Faces]:%d\n",cad1,&NVertex,cad2,cad3,&NFaces);
	setNCaras(NFaces);
	setNVertices(NVertex);
	}
	ListaCaras.resize(getNCaras());
	ListaPuntos3D.resize(getNVertices());
	if (strncmp(cadena,"Vertex list:",12)==0) // Vertex List in file
		for (N=1; N<=NVertex; N++)
		{
			fscanf(fich,"%[A-Za-z ]%d: %[X:] %f %[Y:] %f %[Z:] %f    \n",cad1,&VertexNumber,cad2,&X,cad3,&Y,cad4,&Z);
			ListaPuntos3D[VertexNumber]=Punto3D(X,Y,Z);

		}
	if (strncmp(cadena,"Face list:",10)==0) // Face List in model file
		for (N=0; N<NFaces; N++)
		{ fscanf(fich,"%[Face]%d: %[A:]%d %[B:]%d %[C:]%d\n",cad1,&FaceNumber,cad2,&A,cad3,&B,cad4,&C);
		fgets(cadena, 100, fich);
		// Cálculo del vector normal a cada cara (Nx,Ny,Nz)........NEW¡¡¡¡
		ListaCaras[FaceNumber]=Cara(A,B,C,Normal);
		ax = ListaPuntos3D[ListaCaras[FaceNumber].getA()].getX()-ListaPuntos3D[ListaCaras[FaceNumber].getB()].getX();  //  X[A] - X[B];
		ay = ListaPuntos3D[ListaCaras[FaceNumber].getA()].getY()-ListaPuntos3D[ListaCaras[FaceNumber].getB()].getY();  //  Y[A] - Y[B];
		az = ListaPuntos3D[ListaCaras[FaceNumber].getA()].getZ()-ListaPuntos3D[ListaCaras[FaceNumber].getB()].getZ();  //  Z[A] - Z[B];
		bx = ListaPuntos3D[ListaCaras[FaceNumber].getB()].getX()-ListaPuntos3D[ListaCaras[FaceNumber].getC()].getX();  //  X[B] - X[C];
		by = ListaPuntos3D[ListaCaras[FaceNumber].getB()].getY()-ListaPuntos3D[ListaCaras[FaceNumber].getC()].getY();  //  Y[B] - Y[C];
		bz = ListaPuntos3D[ListaCaras[FaceNumber].getB()].getZ()-ListaPuntos3D[ListaCaras[FaceNumber].getC()].getZ();  //  Z[B] - Z[C];
		Normal.x= (ay * bz) - (az * by);
		Normal.y= (az * bx) - (ax * bz);
		Normal.z = (ax * by) - (ay * bx);
		len=sqrt( (Normal.x*Normal.x)+(Normal.y*Normal.y)+(Normal.z*Normal.z) );
		Normal.x= Normal.x/len; Normal.y= Normal.y/len; Normal.z= Normal.z/len;
		ListaCaras[FaceNumber]=Cara(A,B,C,Normal);
		}
}
fclose(fich);

}

float Modelo3D::getRotationSpeed() const {
	return rotationSpeed;
}

float Modelo3D::getRadiusScale() const {
	return radiusScale;
}

void Modelo3D::setFigureColor(const Color& figureColor) {
	this->figureColor = figureColor;
}

void Modelo3D::setRadiusScale(float radiusScale) {
	this->radiusScale = radiusScale;
}

void Modelo3D::setRotationSpeed(float rotationSpeed) {
	this->rotationSpeed = rotationSpeed;
}

vector<Modelo3D>& Modelo3D::getLunas() {
	return lunas;
}

/**
 * Pinta los ejes a la figura
 */
void  Modelo3D::drawFigureAxis(const VisorType &visorType, const bool &axisActivate){
	if(axisActivate){
		if(visorType==ILUMINATED){// iluminacion activada
			glDisable(GL_LIGHTING);
		}
		//dibujar ejes
		glBegin(GL_LINES);
		// dibujar eje x en rojo
		glColor3f(1.0, 0.0, 0.0);
		glVertex3f(-100.0, 0.0, 0.0);
		glVertex3f(100.0, 0.0, 0.0);
		// dibujar eje y en verde
		glColor3f(1.0, 1.0, 0.0);
		glVertex3f(0.0, -100.0, 0.0);
		glVertex3f(0.0, 100.0, 0.0);
		// dibujar eje z en azul
		glColor3f(0.0, 0.0, 1.0);
		glVertex3f(0.0, 0.0, -100.0);
		glVertex3f(0.0, 0.0, 100.0);
		glEnd();
		// fin ejes
		glColor3f(1.0f,1.0f,1.0f);// volvemos a color por defecto
		if(visorType==ILUMINATED){
			glEnable(GL_LIGHTING);
		}
	}
}

Modelo3D::~Modelo3D() {
	// TODO Auto-generated destructor stub
}
