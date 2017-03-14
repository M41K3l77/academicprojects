/*
 * Color.h
 *
 *  Created on: 10 de oct. de 2015
 *      Author: miguel
 */

#ifndef COLOR_H_
#define COLOR_H_
// Clase para guardar la composicion de un color en RGB
class Color {
private:
	int R;
	int G;
	int B;
public:
	// constructores
	Color();
	Color(const int &r, const int &g, const int &b);
	// getters y setters
	int getB() const;
	void setB(int b);
	int getG() const;
	void setG(int g);
	int getR() const;
	void setR(int r);
	void setRGB(const int &r, const int &g, const int &b);
	// destructor
	~Color();
};

#endif /* COLOR_H_ */
