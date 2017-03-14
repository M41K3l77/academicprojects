
//Mensaje.h//
#include <iostream>
#include "Persona.h"
using namespace std;
#ifndef MENSAJE_H_
#define MENSAJE_H_

class Mensaje {
	string Msg;
	int longitud;
	Persona P;

public:
	// Constructores //

	// Constructor por defecto
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase}
	Mensaje();

	// constructor parametrizado
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de valores
	// de atributos ya seleccionados}
	Mensaje (const string &Mensag, const Persona &Pers);

	// constructor copia
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de los valores
	// de los atributos de otra instancia ya creada}
	Mensaje (const Mensaje &Mesage);

	// Métodos modificadores //

	// Pre= { La instancia debe estar creada. string <> empty}
	// Post= {copia el string parámetro de entrada sobre el atributo string}
	// Parámetros Entrada: Msg= string que se lo asignamos al atributo Msg
	// Complejidad: O(1)
	void PutMsg (const string &Msg);

	// Pre= { La instancia debe estar creada. Persona <> empty}
	// Post= {copia la Persona parámetro de entrada sobre el atributo Persona}
	// Parámetros Entrada: Msg= string que se lo asignamos al atributo Msg
	// Complejidad: O(1)
	void PutPersona (const Persona &Pers);

	// Métodos selectores //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el string(Msg) como valor de retorno del método
	// Complejidad: O(1)
	string GetMsg()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve la Persona(P) como valor de retorno del método
	// Complejidad: O(1)
	Persona GetPersona()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el int(longitud) como valor de retorno del método
	// Complejidad: O(1)
	int ContarLetras()const;

	// operador //

	// Pre= { Las instancias deben estar creadas }
	// Post= {Asigna los valores de los atributos del objeto(que se pasa como parametro
	//        a la derecha del operador = al objeto a la izquierda del operador =}
	// Parámetros Entrada: Mensaje, Mensaje que está a la derecha del signo =
	// Complejidad: O(1)
	void operator = (const Mensaje &Msg);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	// Complejidad: O(1)
	void Mostrar ()const;

	// Destructor //

	// Pre ={La instancia debe estar creada}
	// Post= {Destruye la instancia de la clase}
	// Complejidad: O(1)
	~Mensaje();
};

#endif /* MENSAJE_H_ */
