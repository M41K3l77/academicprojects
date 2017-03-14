/**

	INTRODUCCIÓN A LA PROGRAMACIÓN / FUNDAMENTOS DE PROGRAMACIÓN
	Curso 2011/2012

	Nombre: reloj.h
	Descripción: Implementación del TAD Reloj para el proyecto REFLEKT.
	     	 	 Encargado de la gestión del reloj.
	Autor:	Profesores de las asignaturas
	Fecha:	03/11/2011

*/
#include <fstream>
#include <cstring>
#include "reloj.h"

const int NUM_BLOQUES_RELOJ = 25;

TReloj CrearReloj (int seg){
	TReloj R;
	R.v_inicial = time(NULL);
	R.seg_actual = 0;
	R.seg_total = seg;
	return R;
};

bool ActualizarReloj (TReloj &R, int &p, char msg[40]){

	 R.seg_actual = difftime((long)time(NULL), (int)R.v_inicial);
	 sprintf(msg, "Tiempo: %.2d:%.2d", R.seg_actual /60, R.seg_actual%60);
	 p =  R.seg_actual * NUM_BLOQUES_RELOJ / R.seg_total ;
	 if (R.seg_actual > R.seg_total){
		p=NUM_BLOQUES_RELOJ;
		return false;
	}
	else return true;

}





