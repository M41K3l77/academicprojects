/*
 * MaterialProperty.h
 *
 *  Created on: 31 de oct. de 2015
 *      Author: miguel
 */

#ifndef SRC_MATERIALPROPERTY_H_
#define SRC_MATERIALPROPERTY_H_
#include <vector>
using namespace std;
class MaterialProperty {
public:
	MaterialProperty();
	vector < vector < float > > sunProperties();
	vector < vector < float > > mercuryProperties();
	vector < vector < float > > venusProperties();
	vector < vector < float > > moonProperties();
	vector < vector < float > > earthProperties();
	vector < vector < float > > marsProperties();
	vector < vector < float > > jupiterProperties();
	vector < vector < float > > saturnProperties();
	vector < vector < float > > uranusProperties();
	vector < vector < float > > neptuneProperties();
	vector < vector < float > > plutoProperties();
	~MaterialProperty();
};

#endif /* SRC_MATERIALPROPERTY_H_ */
