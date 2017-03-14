/*
 * MaterialProperty.cpp
 *
 *  Created on: 31 de oct. de 2015
 *      Author: miguel
 */

#include "MaterialProperty.h"

MaterialProperty::MaterialProperty() {
	// TODO Auto-generated constructor stub

}

/**
 * //Yellow plastic
float[] mat_ambient ={0.0f,0.0f,0.0f,1.0f };
float[] mat_diffuse ={0.5f,0.5f,0.0f,1.0f };
float[] mat_specular ={0.60f,0.60f,0.50f,1.0f };
float shine = 32.0f ;
 *
 */
vector < vector < float > > MaterialProperty::sunProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.0f); material_ambient.push_back(0.0f);material_ambient.push_back(0.0f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; 	material_diffuse.push_back(0.5f);material_diffuse.push_back(0.5f);material_diffuse.push_back(0.0f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.6f);material_specular.push_back(0.6f);material_specular.push_back(0.5f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.6f);material_emission.push_back(0.6f);material_emission.push_back(0.15f);  material_emission.push_back(1.0);
	vector < float > shine;				shine.push_back(10.0f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/**
 * //Red rubber
 */
vector < vector < float > > MaterialProperty::mercuryProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.05f); material_ambient.push_back(0.0f);material_ambient.push_back(0.0f); material_ambient.push_back(1.0);
	vector < float > material_diffuse; material_diffuse.push_back(0.5f);material_diffuse.push_back(0.4f);material_diffuse.push_back(0.4f);  material_diffuse.push_back(1.0);
	vector < float > material_specular; material_specular.push_back(0.7f);material_specular.push_back(0.4f);material_specular.push_back(0.4f);  material_specular.push_back(1.0);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0);
	vector < float > shine;				shine.push_back(10.0f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/**
 * //Perl
 */
vector < vector < float > > MaterialProperty::venusProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.25f); material_ambient.push_back(0.20725f);material_ambient.push_back(0.20725f); material_ambient.push_back(0.922f);
	vector < float > material_diffuse; material_diffuse.push_back(1.0f);material_diffuse.push_back(0.829f);material_diffuse.push_back(0.829f);  material_diffuse.push_back(0.922f);
	vector < float > material_specular; material_specular.push_back(0.296648f);material_specular.push_back(0.296648f);material_specular.push_back(0.296648f);  material_specular.push_back(0.922f);
	vector < float > material_emission; material_emission.push_back(0.0);material_emission.push_back(0.0);material_emission.push_back(0.0);  material_emission.push_back(1.0);
	vector < float > shine;				shine.push_back(67);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}
/**
//Turquoise
float[] mat_ambient ={ 0.1f, 0.18725f, 0.1745f, 0.8f };
float[] mat_diffuse ={0.396f, 0.74151f, 0.69102f, 0.8f };
float[] mat_specular ={0.297254f, 0.30829f, 0.306678f, 0.8f };
float shine = 12.8f;

 *
 */
vector < vector < float > > MaterialProperty::earthProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.1f); material_ambient.push_back(0.18725f);material_ambient.push_back(0.1745f); material_ambient.push_back(0.8f);
	vector < float > material_diffuse; material_diffuse.push_back(0.396f);material_diffuse.push_back(0.74151f);material_diffuse.push_back(0.69102f);  material_diffuse.push_back(0.8f);
	vector < float > material_specular; material_specular.push_back(0.297254f);material_specular.push_back(0.30829f);material_specular.push_back(0.306678f);  material_specular.push_back(0.8f);
	vector < float > material_emission; material_emission.push_back(0.0);material_emission.push_back(0.0);material_emission.push_back(0.0);  material_emission.push_back(1.0);
	vector < float > shine;				shine.push_back(12.8f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

vector < vector < float > > MaterialProperty::moonProperties(){
	vector < float > material_ambient;  material_ambient.push_back(1.0); material_ambient.push_back(1.0);material_ambient.push_back(1.0); material_ambient.push_back(1.0);
	vector < float > material_diffuse; material_diffuse.push_back(1.0);material_diffuse.push_back(1.0);material_diffuse.push_back(1.0);  material_diffuse.push_back(1.0);
	vector < float > material_specular; material_specular.push_back(1.0);material_specular.push_back(1.0);material_specular.push_back(1.0);  material_specular.push_back(1.0);
	vector < float > material_emission; material_emission.push_back(0.0);material_emission.push_back(0.0);material_emission.push_back(0.0);  material_emission.push_back(1.0);
	vector < float > shine;				shine.push_back(100);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}
/**
 * red rubber
 */
vector < vector < float > > MaterialProperty::marsProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.05f); material_ambient.push_back(0.0f);material_ambient.push_back(0.0f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.5f);material_diffuse.push_back(0.4f);material_diffuse.push_back(0.4f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.7f);material_specular.push_back(0.04f);material_specular.push_back(0.04f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(0.078125f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/**
 * // Bronze
 */
vector < vector < float > > MaterialProperty::jupiterProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.2125f); material_ambient.push_back(0.1275f);material_ambient.push_back(0.054f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.714f);material_diffuse.push_back(0.4284f);material_diffuse.push_back(0.18144f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.393548f);material_specular.push_back(0.271906f);material_specular.push_back(0.166721f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(25.6f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/**
 * brass
 */
vector < vector < float > > MaterialProperty::saturnProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.329412f); material_ambient.push_back(0.223529f);material_ambient.push_back(0.027451f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.780392);material_diffuse.push_back(0.568627f);material_diffuse.push_back(0.113725f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.992157f);material_specular.push_back(0.941176f);material_specular.push_back(0.807843f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(0.21794872f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/*
 * urano
*/
vector < vector < float > > MaterialProperty::uranusProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.54f); material_ambient.push_back(0.91f);material_ambient.push_back(1.0f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.3f);material_diffuse.push_back(0.39f);material_diffuse.push_back(0.63f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.35f);material_specular.push_back(0.45f);material_specular.push_back(0.72f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(59.0f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/*
 *  neptuno
 *
 */
vector < vector < float > > MaterialProperty::neptuneProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.26f); material_ambient.push_back(0.37f);material_ambient.push_back(0.53f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.3f);material_diffuse.push_back(0.22f);material_diffuse.push_back(0.44f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.37f);material_specular.push_back(0.47f);material_specular.push_back(0.7f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.0f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(18.0f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

/*
 * pluton
 */
vector < vector < float > > MaterialProperty::plutoProperties(){
	vector < float > material_ambient;  material_ambient.push_back(0.45f); material_ambient.push_back(0.33f);material_ambient.push_back(0.12f); material_ambient.push_back(1.0f);
	vector < float > material_diffuse; material_diffuse.push_back(0.3f);material_diffuse.push_back(0.22f);material_diffuse.push_back(0.44f);  material_diffuse.push_back(1.0f);
	vector < float > material_specular; material_specular.push_back(0.22f);material_specular.push_back(0.36f);material_specular.push_back(0.72f);  material_specular.push_back(1.0f);
	vector < float > material_emission; material_emission.push_back(0.0f);material_emission.push_back(0.0f);material_emission.push_back(0.17f);  material_emission.push_back(1.0f);
	vector < float > shine;				shine.push_back(10.0f);
	vector < vector < float > > materials;
	materials.push_back(material_ambient);
	materials.push_back(material_diffuse);
	materials.push_back(material_specular);
	materials.push_back(material_emission);
	materials.push_back(shine);
	return materials;
}

MaterialProperty::~MaterialProperty() {
	// TODO Auto-generated destructor stub
}

