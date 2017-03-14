#ifndef LISTAPERSONA_H
#define LISTAPERSONA_H
#include <iostream>
#include "Persona.h"

using namespace std;

// tipoD será de clase Mensaje
typedef Persona TipoD;

// Nodo que contendrá la Persona(tipoD) y puntero que apuntará a siguiente(nodo) o a null
// y puntero que apuntará a anterior(nodo) o a null
typedef struct TipoNod{
	TipoD dato;
	TipoNod *siguiente;
	TipoNod *anterior;
}TipoNod;

// *Tpunter será un puntero a TipoNod
typedef TipoNod *TPunter;
// Tipo para declarar punteros al nodo

class ListaPersona{
	TPunter frente;	// Puntero al dato del principio de la lista
	TPunter fin;	    // Puntero al dato del final de la lista
	TPunter PI;       // Puntero a un determinado dato de la lista

public:
   /**
    * Inicia la lista a vacia
    * Pre:
    * Post: {Crea/inicializa la instancia de la clase(lista vacia)}
    * Complejidad: O(1)
    */
   ListaPersona ();

   /**
    * Nos informa si existe información en la lista o no
    * Pre: {La instancia debe estar creada}
    * Post: {devolvera true si está vacia(frente apunta a NULL), de lo
    *        contrario devolverá false}
    * Complejidad: O(1)
    */
   bool estaVacia();


   /**
    * Mueve el punto de interés al primer elemento de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {El punto de interes PI apuntará donde apunte frente}
    * Complejidad: O(1)
    */
   void Inicio();

   /**
    * Mueve el punto de interés al final de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {El punto de interes PI apuntará donde apunte fin}
    * Complejidad: O(1)
    */
   void Fin();

   /**
    * Avanza el punto de interés al siguiente elemento de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {El punto de interes PI apuntará al siguiente nodo (si no estamos en NULL)}
    * Complejidad: O(1)
    */
   void avanzar();

   /**
    * Retrocede el punto de interés al anterior elemento de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {El punto de interes PI apuntará al nodo anterior (si no estamos en NULL)}
    * Complejidad: O(1)
    */
   void retroceder();

   // MÉTODOS QUE CONSULTAN EL ESTADO DEL PI

   /**
    * Nos dice si el punto de interés está o no al inicio de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {devolvera true si PI apunta donde apunte frente, de lo
    *        contrario devolverá false}
    * Complejidad: O(1)
    */
   bool enInicio();
   /**
    * Nos dice si el punto de interés está o no al final de la lista.
    * Pre: {La instancia debe estar creada}
    * Post: {devolvera true si PI apunta donde apunte fin, de lo
    *        contrario devolverá false}
    * Complejidad: O(1)
    */
   bool enFin();

   /**
    * Nos dice si el punto de interés está sobre el punto más a la derecha de la lista (sobre el final de la lista.)
    * Comprueba si el puntero de interés está fuera de la lista.
    * Este método se ha  añadido para realizar posteriores recorridos o inserciones en orden
    *
    * Pre: {La instancia debe estar creada}
    * Post: {devolvera true si PI apunta a NULL, de lo
    *        contrario devolverá false}
    * Complejidad: O(1)
    */

   bool finLista();


   /**
    * Inserta el dato delante del elemento señalado por el punto de interés.
    * Deja el punto de interés sobre el nuevo elemento
    * Pre: { La Lista debe estar creada}
    * Post: { Crea un nodo nuevo con el dato de tipo tipoD y lo inserta en la lista}
    * Complejidad: O(1)
    */
   bool insertar(const TipoD &dato);

   /**
    * Devuelve la información señalada por el punto de interés.
    * Pre: {La instancia debe estar creada}
    * Post: { Devuelve el dato de tipo tipoD del nodo al que apunta PI}
    * Complejidad: O(1)
    */
   void consultar(TipoD &dato);

   /**
    * Modifica la lista eliminando el elemento señalado por el punto de interés,
    * dejando el punto de interés sobre el elemento siguiente al eliminado.
    * Pre: {La instancia debe estar creada}
    * Post: {Borra el nodo al que apunta el PI}
    * Complejidad: O(1)
    */
   bool borrar();

   /**
    * Modifica el elemento señalado por el punto de interés,
    * Pre: {La instancia debe estar creada}
    * Post: {Cambia el valor del nodo al que apunta el PI por el nuevo dato}
    * Complejidad: O(1)
    */
    void modificar (const TipoD &dato);

    /**
     * Libera la memoria reservada para la lista
     * Pre: {La instancia debe estar creada}
     * Post: {Borra todos los nodos y los 3 punteros apuntando a NULL}
     * Complejidad: O(n)
     */
     ~ListaPersona();
};

#endif/* LISTAPERSONA_H_ */
