/*
 * Camera.h
 *
 *  Created on: 20 de oct. de 2015
 *      Author: miguel
 */

#ifndef CAMERA_H_
#define CAMERA_H_

#include <iostream>
#include <vector>
using namespace std;
class Camera {
private:
	float eyeX;
	float eyeY;
	float eyeZ;
	float pos0X;
	float pos0Y;
	float pos0Z;
	float upX;
	float upY;
	float upZ;
public:
	Camera();
	Camera(const float &eyeX, const float &eyeY, const float &eyeZ, const float &pos0X, const float &pos0Y, const float &pos0Z, const float &upX, const float &upY, const float &upZ);
	void setCameraValues(const vector <float> &cameraValues);
	void setCamera();
	~Camera();
};

#endif /* CAMERA_H_ */
