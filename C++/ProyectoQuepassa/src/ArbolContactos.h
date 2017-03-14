/**
* ArbolContactos.h
* Declaración de la clase Árbol Binario de Búsqueda
* author: Profesores de la asignatura EDI
*
* Curso 10/11
* Revisado curso 11/12
*
*/

#ifndef _ARBOL_H
#define _ARBOL_H

#include <iostream>
#include "Persona.h"


using namespace std;

// tipoDat será de clase Persona
typedef Persona TipoDatoArbol;

/**
*  class Arbol
*  Esta clase define un Árbol Binario de Búsqueda de Persona
*
*/
/*
 * Implemetación dinámica del arbol
 * cada nodo del arbol está compuesto por un entero llamado "datoRaiz"  y dos punteros
 * cada nodo del arbol es tratado como un objeto arbol
 * Nota: la mayoría de los métodos trabajan de forma recursiva.
 *
 */

class Arbol{
    TipoDatoArbol datoRaiz; // tipo de datos que forma el arbol
    Arbol *hIzq, *hDer;    // ptro al arbol dcho e izdo de cada nodo
    bool esVacio;

	// Pre: La instancia debe estar creada
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : dato:TipoDatoArbol
	// Post: Borra el dato deseado y devuelve el arbol resultante
    Arbol *borrarOrden(TipoDatoArbol& dato);

public:

     // Inicia el arbol vacio
     // Pre:
     // Post: {Crea/inicializa la instancia de la clase(arbol vacio)}
     // Complejidad: O(1)
    Arbol();

    // Inicia el arbol acorde con los parametros proporcionados
    // Pre:
    // Post: {Crea/inicializa la instancia de la clase(arbol)}
    // Complejidad: O(1)
    Arbol(Arbol *hIzq,TipoDatoArbol& datoRaiz,Arbol *hDer);

    /* devuelve el subarbol izq de un nodo */
    Arbol *hijoIzq();

    /*devuelve el subarbol derecho de un nodo*/
    Arbol *hijoDer();

    // Devuelve el dato raiz
    TipoDatoArbol& raiz();

    // Devuelve el valor del atributo esVacio (true vacio, false si no lo esta)
    bool vacio();

    // Inserta un dato en el arbol
    // En caso de insercion satisfactoria devuelve bool verdadero
    // en caso contrario devuelve false
    bool insertar(TipoDatoArbol& dato);

    // Borra un dato que se pasa como referencia del arbol
    // internamente llamara al metodo privado para reordenar
    // el arbol si fuera necesario
    void borrar (TipoDatoArbol& dato);
    /*Elimina uno a uno los nodos del arbol*/
    ~Arbol();

    // ejercicios propuestos
    // Muestra los datos en orden (izq, raiz, derch)
    void inOrden();

    // Muestra los datos en orden (raiz, izq, derch)
    void preOrden();

    // Muestra los datos en orden (izq, derch, raiz)
    void postOrden();

    // Se pasa como referencia el dato que se quiere comprobar
    // devuelve bool verdadero si el dato esta en el arbol y
    // false si no lo esta
    bool pertenece (TipoDatoArbol& dato);

    // Devuelve la distancia entre la raiz y el nodo mas alejado
    int profundidad();

    // Nos muestra un dato que se pasa como referencia
    // si pertenece al arbol
    void mostrarDato(TipoDatoArbol& dato);// si el dato existe, lo muestra

    // Borra todos los nodos del arbol
    void BorrarArbolCompleto();

    // Hace una copia del arbol (el recorrido del arbol es inOrden)
    void CopiarArbolInOrden(Arbol &A);

    // Nos devuelve el dato menor del arbol
    TipoDatoArbol DatoMenorDelArbol();// Solo usar en copia de arboles
//    int numNodos();
//    int suma();
};
#endif
