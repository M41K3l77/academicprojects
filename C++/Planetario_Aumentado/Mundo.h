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
	static vector <Modelo3D> modelos;
	static float alfa;
	static float beta;
	// Nos dice si el modelo se representara en alambre, solida o iluminacion
	static VisorType visorType;
	// constante(flat) o suave (smooth)
	static GLenum primitiveAparience;
	// color del fondo y de las figuras
	Color colorFondo;
	Color colorFiguras;
	// focos
	static vector < Light > lights;
	// posiciones de la camara
	vector < vector < float > > camerasPositions;
	//camara
	Camera camera;
	// posicion de la camara elegida
	int cameraNumber;
	// para controlar la iluminacion por defecto al arrancar
	static bool iniciarAplicacion;
	// vector con los colores para la figura
	vector < Color > colores;
	// booleano para activar o desactivar ejes por teclado
	static bool axisActivate;
	//zoom
	static float zoom;
	int zoomControl;// para limitar el zoom
	int limitZoomControl;// limite para el zoom
	// Contrlo del tiempo transcurrido
	static float tiempoTranscurrido;
	// incremento del tiempo
	float speedTime;
	// control de hata cuanto se puede aumentar la velocidad del tiempo
	int timeControl;
	int limitTimeControl;
	// controlar segunda marca para cambios en el planetario
	static bool secondPatternActivated;
	void createLights();
	void chooseCamera();
public:
	Mundo();
	void initMundo();
	float getAlfa() const;
	void setAlfa(float Alfa);
	float getBeta() const;
	void setBeta(float Beta);
	VisorType getVisorType() const;
	void setVisorType(VisorType visorType);
	void setPrimitiveAparience(const GLenum &primitiveAparience);
	void setColorFiguras(const Color& colorFiguras);
	void setColorFondo(const Color& colorFondo);
	void setCameraNumber(const int &cameraNumber);
	void setAngulo(float angulo);
	float getAngulo() const;
	float getSpeedTime() const;
	void setSpeedTime(const float &speedTime);
	float getTiempoTranscurrido() const;
	void setTiempoTranscurrido(const float &tiempoTranscurrido);
	int getLimitTimeControl() const;
	void setLimitTimeControl(const int &limitTimeControl);
	int getTimeControl() const;
	void setTimeControl(const int &timeControl);
	int getLimitZoomControl() const;
	void setLimitZoomControl(const int &limitZoomControl);
	float getZoom() const;
	void setZoom(const float &zoom);
	int getZoomControl() const;
	void setZoomControl(const int &zoomControl);
	bool isAxisActivate() const;
	void setAxisActivate(const bool &axisActivate);
	vector<Light>& getLights();
	void setLights(const vector<Light>& lights);
	static void activateIluminationByDefault();
	// dibuja ejes absolutos
	static void drawAbsoluteAxis();
	// Dibuja el modelo
	static void drawMyWorld();
	// para realidad aumentada segunda marca
	static void eraseSunAndLights();
	void setSecondPatternActivated(const int &secondPatternActivated);
	vector<Modelo3D>& getModelos();
	void setModelos(const vector<Modelo3D>& modelos);
	// devuelve el color seleccionado para las figuras
	Color getColorDibujo(const int &opcion);
	~Mundo();
};
#endif /* MUNDO_H_ */
