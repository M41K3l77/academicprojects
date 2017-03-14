
//Persona.h//
#include <iostream>
#include <string.h>
using namespace std;
#ifndef PERSONA_H_
#define PERSONA_H_

class Persona {
	string Nombre;
	string Apellido;
	int Telefono;
	string Email;

public:
	// Constructores //

	// Constructor por defecto
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase}
	Persona();

	// constructor parametrizado
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de valores
	// de atributos ya seleccionados}
	Persona (const string &N, const string &A, const int &T, const string &E);

	// constructor copia
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de los valores
	// de los atributos de otra instancia ya creada}
	Persona (const Persona &U);

	// métodos modificadores //

	// Pre= { La instancia debe estar creada. string <> empty}
	// Post= {copia el string parámetro de entrada sobre el atributo string}
	// Parámetros Entrada: N= string que se lo asignamos al atributo Nombre
	// Complejidad: O(1)
	void PutNombre   (const string &N);

	// Pre= { La instancia debe estar creada. string <> empty}
	// Post= {copia el string parámetro de entrada sobre el atributo string}
	// Parámetros Entrada: A= string que se lo asignamos al atributo Apellido
	// Complejidad: O(1)
	void PutApellido (const string &A);

	// Pre= { La instancia debe estar creada. int <> empty}
	// Post= {copia el int parámetro de entrada sobre el atributo int}
	// Parámetros Entrada: T= int que se lo asignamos al atributo Telefono
	// Complejidad: O(1)
	void PutTelefono  (const int &T);

	// Pre= { La instancia debe estar creada. string <> empty}
	// Post= {copia el string parámetro de entrada sobre el atributo string}
	// Parámetros Entrada: E= string que se lo asignamos al atributo Email
	// Complejidad: O(1)
	void PutEmail (const string &E);

	// métodos selectores //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el string(Nombre) como valor de retorno del método
	// Complejidad: O(1)
	string GetNombre()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el string(Apellido) como valor de retorno del método
	string GetApellido()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el int(Telefono) como valor de retorno del método
	int GetTelefono()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el string(Email) como valor de retorno del método
	string GetEmail()const;

	// Ampliacion de operadores //

	// Pre= { Las instancias deben estar creadas }
	// Post= {Compara Nombre del objeto a la izquierda del operador == con el Nombre
	//        del objeto a la derecha, que es el que se pasa como parametro y si
    //        son iguales ambas, entonces son igual, devuelve bool}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo de comparacion
	// Complejidad: O(n) (es O(n) ya que tenemos bucle for para pasar a mayusculas)
	bool operator == (const Persona &U);

	// Pre= { Las instancias deben estar creadas }
	// Post= {Compara Nombre del objeto a la izquierda del operador < con el Nombre
	//        del objeto a la derecha que es el que se pasa como parametro,Nombre es de tipo
	//        string por lo que es una comparacion lexicografica y si la que se pasa como parametro
    //        es mayor, entonces devulve true y si no devuelve false}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo menor <
	// Complejidad: O(n) (es O(n) ya que tenemos bucle for para pasar a mayusculas)
	bool operator < (const Persona &U);

	// Pre= { Las instancias deben estar creadas }
	// Post= {Compara Nombre del objeto a la izquierda del operador > con el Nombre
	//        del objeto a la derecha que es el que se pasa como parametro,Nombre es de tipo
	//        string por lo que es una comparacion lexicografica y si la que se pasa como parametro
    //        es mayor, entonces devulve true y si no devuelve false}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo mayor >
	// Complejidad: O(n) (es O(n) ya que tenemos bucle for para pasar a mayusculas)
	bool operator > (const Persona &U);

	// Pre= { Las instancias deben estar creadas }
	// Post= {Compara Nombre del objeto a la izquierda del operador != con el Nombre
	//        del objeto a la derecha que es el que se pasa como parametro,Nombre es de tipo
	//        string por lo que es una comparacion lexicografica y si la que se pasa como parametro
    //        es distinta, entonces devulve true y si no devuelve false}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo distinto !=
	// Complejidad: O(n) (es O(n) ya que tenemos bucle for para pasar a mayusculas)
	bool operator != (const Persona &U);

	// Pre= { Las instancias deben estar creadas }
	// Post= {Compara Nombre del objeto a la izquierda del operador <= con el Nombre
	//        del objeto a la derecha que es el que se pasa como parametro,Nombre es de tipo
	//        string por lo que es una comparacion lexicografica y si la que se pasa como parametro
    //        es menor o igual, entonces devulve true y si no devuelve false}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo distinto <=
	// Complejidad: O(n) (es O(n) ya que tenemos bucle for para pasar a mayusculas)
	bool operator <= (const Persona &U);

	// Pre= { Las instancias deben estar creadas }
	// Post= {Asigna los valores de los atributos del objeto(que se pasa como parametro
	//        a la derecha del operador = al objeto a la izquierda del operador =}
	// Parámetros Entrada: Persona, Persona que está a la derecha del signo =
	// Complejidad: O(1)
	void operator = (const Persona &U);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	// Complejidad: O(1)
	void MostrarDatosPersona ()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	// Complejidad: O(1)
	void MostrarNombrePersona ()const;

	// Destructor //

	// Pre ={La instancia debe estar creada}
	// Post= {Destruye la instancia de la clase}
	// Complejidad: O(1)
	~Persona();
};

#endif /* PERSONA_H_ */
