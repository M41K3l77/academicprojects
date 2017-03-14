/*
 * XMLparser.cpp
 *
 *  Created on: 12 de nov. de 2015
 *      Author: miguel
 */

#include "XMLparser.h"

XMLparser::XMLparser() {
	// TODO Auto-generated constructor stub

}
/**
 * Metodo para poder mapear las propiedades de string a celestialBodyMaterialProperty (enum)
 */
void XMLparser::createMapOfProperties(){
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("SUN", SUN));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("MERCURY", MERCURY));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("VENUS", VENUS));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("EARTH", EARTH));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("MOON", MOON));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("MARS", MARS));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("JUPITER", JUPITER));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("SATURN", SATURN));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("URANUS", URANUS));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("NEPTUNE", NEPTUNE));
	myMapOfProperties.insert(std::pair<string,celestialBodyMaterialProperty>("PLUTO", PLUTO));
}
/**
 * Metodo que obtiene el enum a partir de un string
 */
celestialBodyMaterialProperty XMLparser::materialPropertyEnum(const string &materialPropertyString){
	celestialBodyMaterialProperty myMaterial;
	std::map<string,celestialBodyMaterialProperty>::iterator it;
	it=myMapOfProperties.find(materialPropertyString);
	if(it!=myMapOfProperties.end()){
		myMaterial=it->second;
	}else{
		cout << "el material " << materialPropertyString << " no existe" << endl;
	}
	return myMaterial;
}
/**
 * Metodo principal para parsear el *.xml
 */
void XMLparser::parserSolarSystem(){
	createMapOfProperties();
	// cargar fichero
	XMLDocument doc;
	doc.LoadFile( "SolarSystem.xml" );
	// root element
	XMLElement* rootElement = doc.RootElement();
	cout << "elemento root: " << rootElement->Value() << endl;
	// auxiliar
	XMLElement * pListPlanetsAux;
	// estrella
	XMLElement *star= rootElement->FirstChildElement("star");
	cout << "Estrella: " << star->GetText() << endl;
	// atributos de la estrella
	cout << "valores atributos: ";
	const XMLAttribute* attr = star->FirstAttribute();
	float distanceFromStar;
	attr->QueryFloatValue(&distanceFromStar);
	attr = attr->Next();
	float orbitSpeed;
	attr->QueryFloatValue(&orbitSpeed);
	attr = attr->Next();
	float rotationSpeed;
	attr->QueryFloatValue(&rotationSpeed);
	attr = attr->Next();
	float radiusScale;
	attr->QueryFloatValue(&radiusScale);
	attr = attr->Next();
	string materialProperty=attr->Value();
	std::transform(materialProperty.begin(), materialProperty.end(), materialProperty.begin(), ::toupper);
	celestialBodyMaterialProperty objectMaterialProperty=materialPropertyEnum(materialProperty);
	attr = attr->Next();
	const string figureASC=attr->Value();
	cout << "distancia al objeto que orbita: " << distanceFromStar << ", velocidad de orbita: "
			<< orbitSpeed << ", velocidad de rotacion: "<< rotationSpeed << ", escala tamaño objeto: "
			<< radiusScale << ", propiedades del material del objeto: "<< materialProperty << ", archivo ASC de del objeto: "<< figureASC << endl;
	// carga del modelo
	Modelo3D ObjetoCeleste(distanceFromStar, orbitSpeed, rotationSpeed, radiusScale, objectMaterialProperty);
	ObjetoCeleste.Load_Model(strdup(figureASC.c_str()));
	modelos.push_back(ObjetoCeleste);
	// fin atributos estrella
	// planetas y sus lunas
	XMLElement * pListPlanets = star->NextSiblingElement("planet");
	// Recorremos todos los planetas
	while (pListPlanets != NULL){
		cout << "planeta valores atributos: ";
		// atributos de los planetas print o parse
		const XMLAttribute *attr = pListPlanets->FirstAttribute();
		float distanceFromStar;
		attr->QueryFloatValue(&distanceFromStar);
		attr = attr->Next();
		float orbitSpeed;
		attr->QueryFloatValue(&orbitSpeed);
		attr = attr->Next();
		float rotationSpeed;
		attr->QueryFloatValue(&rotationSpeed);
		attr = attr->Next();
		float radiusScale;
		attr->QueryFloatValue(&radiusScale);
		attr = attr->Next();
		string materialProperty=attr->Value();
		std::transform(materialProperty.begin(), materialProperty.end(), materialProperty.begin(), ::toupper);
		celestialBodyMaterialProperty objectMaterialProperty=materialPropertyEnum(materialProperty);
		attr = attr->Next();
		string figureASC=attr->Value();
		cout << "distancia al objeto que orbita: " << distanceFromStar << ", velocidad de orbita: "
					<< orbitSpeed << ", velocidad de rotacion: "<< rotationSpeed << ", escala tamaño objeto: "
					<< radiusScale << ", propiedades del material del objeto: "<< materialProperty << ", archivo ASC de del objeto: "<< figureASC << endl;
		// carga del modelo planeta
		Modelo3D ObjetoCeleste(distanceFromStar, orbitSpeed, rotationSpeed, radiusScale, objectMaterialProperty);
		ObjetoCeleste.Load_Model(strdup(figureASC.c_str()));
		modelos.push_back(ObjetoCeleste);
		// fin atributo de un planeta
		// si hay luna se baja de nivel y en un aux nos quedamos a donde apuntaba el planeta
		pListPlanetsAux = pListPlanets;
		// lunas del planeta
		XMLElement *moon= pListPlanets->FirstChildElement("moon");
		while(moon != NULL){
			cout << "luna valores atributos: ";
			// atributos luna
			const XMLAttribute *attr = moon->FirstAttribute();
			float distanceFromStar;
			attr->QueryFloatValue(&distanceFromStar);
			attr = attr->Next();
			float orbitSpeed;
			attr->QueryFloatValue(&orbitSpeed);
			attr = attr->Next();
			float rotationSpeed;
			attr->QueryFloatValue(&rotationSpeed);
			attr = attr->Next();
			float radiusScale;
			attr->QueryFloatValue(&radiusScale);
			attr = attr->Next();
			string materialProperty=attr->Value();
			std::transform(materialProperty.begin(), materialProperty.end(), materialProperty.begin(), ::toupper);
			celestialBodyMaterialProperty objectMaterialProperty=materialPropertyEnum(materialProperty);
			attr = attr->Next();
			string figureASC=attr->Value();
			cout << "distancia al objeto que orbita: " << distanceFromStar << ", velocidad de orbita: "
						<< orbitSpeed << ", velocidad de rotacion: "<< rotationSpeed << ", escala tamaño objeto: "
						<< radiusScale << ", propiedades del material del objeto: "<< materialProperty << ", archivo ASC de del objeto: "<< figureASC << endl;
			// carga del modelo, este caso una luna
			Modelo3D ObjetoCeleste(distanceFromStar, orbitSpeed, rotationSpeed, radiusScale, objectMaterialProperty);
			ObjetoCeleste.Load_Model(strdup(figureASC.c_str()));
			modelos[modelos.size()-1].getLunas().push_back(ObjetoCeleste);
			// fin atributos luna
			// siguiente luna si la hay
			moon = moon->NextSiblingElement("moon");
		}
		// pasamos al siguiente planeta, se tira del auxiliar por que el puntero ha profundizado en nivel
		pListPlanets = pListPlanetsAux->NextSiblingElement("planet");
	}
	// parseo posiciones camara
	XMLElement * pListCameraPositions = star->NextSiblingElement("camera");
	while(pListCameraPositions != NULL){
		vector <float> cameraposition;
		cout << "camara valores coordenadas: ";
		// parseamos los atributos de la camara
		for (const XMLAttribute* attrCameraPosition = pListCameraPositions->FirstAttribute(); attrCameraPosition!=0; attrCameraPosition = attrCameraPosition->Next()){
			float coordenada;
			attrCameraPosition->QueryFloatValue(&coordenada);
			cameraposition.push_back(coordenada);
			cout << coordenada << " ";
		}
		cout << endl;
		this->cameraPositions.push_back(cameraposition);
		// pasamos a la siguiente camara si la hay
		pListCameraPositions = pListCameraPositions->NextSiblingElement("camera");
	}
}

const vector<Modelo3D>& XMLparser::getModelos() const {
	return modelos;
}

const vector<vector<float> >& XMLparser::getCameraPositions() const {
	return cameraPositions;
}

XMLparser::~XMLparser() {
	// TODO Auto-generated destructor stub
}

