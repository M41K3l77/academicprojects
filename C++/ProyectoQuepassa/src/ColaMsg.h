
#ifndef COLAMSG_H
#define COLAMSG_H

#include <iostream>
#include "Mensaje.h"
using namespace std;

// tipoDato será de clase Mensaje
typedef Mensaje tipoDato;

// Nodo que contendrá el Mensaje(tipoDato) y puntero que apuntará a siguiente(nodo) o a null
typedef struct tipoNodo{
	tipoDato dato;
	tipoNodo *siguiente;
}tipoNodo;

// *Tpuntero será un puntero a tipoNodo
typedef tipoNodo *Tpuntero;

class ColaMsg {

  private:
	// Punteros de la Cola dinamica
	Tpuntero frente;// Apuntará(si lo hay) al primer nodo
	Tpuntero fin;// Apuntará(si lo hay) al ultimo nodo
  public:

	// Constructor //

	// Constructor por defecto
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase(cola vacia)}
	ColaMsg();

	// Métodos modificadores //

	// Pre= { La instancia debe estar creada(la cola)}
	// Post= {copia el tipoDato parámetro de entrada en un nodo nuevo
	//        y este nodo lo inserta en la cola por su fin}
	// Parámetros Entrada: tipoDato d que lo encapsulamos en tipoNodo
	// e insertamos en ColaMsg
	// Complejidad: O(1)
	void Encolar(const tipoDato &d);

	// Pre= { La instancia debe estar creada(la cola)}
	// Post= {Si la cola no está vacia, borra el nodo al que
	//        apunta el puntero frente}
	// Parámetros Entrada: ninguno
	// Complejidad: O(1)
	void Desencolar();

	// Método selector //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : d
	// Post: Devuelve el tipoDato(d) como valor de retorno del método
	//       que es el Mensaje contenido en el nodo al que apunta frente
	// Complejidad: O(1)
	void Primero(tipoDato &d);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve true o false como valor de retorno del método
	//       que es true si está vacía(frente apunta a null) o false si
	//       no lo está
	// Complejidad: O(1)
	bool Vacia();

	// Operador asignacion = //

	// Pre= { Las instancias deben estar creadas }
	// Post= {Asigna los valores de los atributos del objeto(que se pasa como parametro
	//        a la derecha del operador = al objeto a la izquierda del operador =}
	// Parámetros Entrada: ColaMsg, ColaMsg que está a la derecha del signo =
	// Complejidad: O(n)
	void operator = (ColaMsg &C);

	// Destructor //

	// Pre ={La instancia debe estar creada}
	// Post= {Destruye la instancia de la clase}
	// Complejidad: O(n)
	~ColaMsg();
};

#endif /* COLAMSG_H_ */
