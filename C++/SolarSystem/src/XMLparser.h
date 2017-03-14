/*
 * XMLparser.h
 *
 *  Created on: 12 de nov. de 2015
 *      Author: miguel
 */

#ifndef SRC_XMLPARSER_H_
#define SRC_XMLPARSER_H_

#if defined( _MSC_VER )
#if !defined( _CRT_SECURE_NO_WARNINGS )
#define _CRT_SECURE_NO_WARNINGS		// This test file is not intended to be secure.
#endif
#endif

#include <map>
#include <string>
#include "Modelo3D.h"
#include "tinyxml2.h"
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <vector>
#include <string>
#include <utility>
#include <algorithm>
using namespace tinyxml2;
using namespace std;
class XMLparser {
private:
	// mapa propiedades string enum
	map<string,celestialBodyMaterialProperty> myMapOfProperties;
	// modelos desde el xml
	vector <Modelo3D> modelos;
	// posiciones de la camara
	vector < vector < float > > cameraPositions;
	void createMapOfProperties();
	celestialBodyMaterialProperty materialPropertyEnum(const string &materialPropertyString);
public:
	XMLparser();
	const vector<Modelo3D>& getModelos() const;
	const vector<vector<float> >& getCameraPositions() const;
	void parserSolarSystem();
	~XMLparser();
};

#endif /* SRC_XMLPARSER_H_ */
