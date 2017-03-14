/*
 * Light.h
 *
 *  Created on: 14 de oct. de 2015
 *      Author: miguel
 */

#ifndef LIGHT_H_
#define LIGHT_H_

#include <GL/glut.h>
using namespace std;

class Light {
private:
	// luz
	GLenum light;
	// posicion de la luz
	float lightPos[4];

public:
	Light();
	Light(const GLenum &light);
	GLenum getLight();
	void setLight(GLenum light);
	void setLightPost(const float &x, const float &y, const float &z, const float &w);
	void activateLighting();
	void deactivateLighting();
	void checkActivateLight();
	void activateLight();
	void deactivateLight();
	void setWhiteLight();
	void setRedLight();
	void setGreenLight();
	void setBlueLight();
	void setRedGreenLight();
	void setRedBlueLight();
	void setGreenBlueLight();
	void setSpecialLight();
	bool isActive() const;
	void setActive(bool active);
	~Light();
};

#endif /* LIGHT_H_ */
