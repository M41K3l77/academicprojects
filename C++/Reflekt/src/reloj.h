/**

	INTRODUCCIÓN A LA PROGRAMACIÓN / FUNDAMENTOS DE PROGRAMACIÓN
	Curso 2011/2012

	Nombre: reloj.h
	Descripción: Especificación del TAD Reloj para el proyecto REFLEKT.
	     	 	 Encargado de la gestión del reloj.
	Autor:	Profesores de las asignaturas
	Fecha:	03/11/2011

*/


#ifndef TADRELOJ_H_
#define TADRELOJ_H_
#include <iostream>
#include <ctime>
using namespace std;

/* Definición de la estructura para almacenar la información del reloj
 * */

struct TReloj{
	time_t v_inicial;  //tiempo inicial ejecucion del programa
	long seg_actual;   //segundos transcuridos desde el comienzo del juego
	long seg_total; 	//duración de la partida en segundos

};

/* Crea un reloj que comienza en 0 y llega hasta s segundos
 * */
TReloj CrearReloj (int s);

/* El reloj se actualiza a partir del tiempo del sistema
 * devuelve un mensaje con el tiempo transcurrido
 * y un valor para aumentar el tamaño de la barra indicadora del
 * tiempo, devuelve falso si ha transcurrido el tiempo programado
 * */
bool ActualizarReloj (TReloj &R, int &p, char msg[40]);



#endif /* TADRELOJ_H_ */
