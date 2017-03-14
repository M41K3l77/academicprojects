#include "ListaPersona.h"


ListaPersona::ListaPersona ()
{
	frente = NULL;
	fin = NULL;
	PI = NULL;
}

bool ListaPersona::estaVacia (){
	return (frente == NULL);
}


void ListaPersona::Inicio (){
	PI = frente;
}


/**
* Metodo moverFin. Coloca el puntero de interés en el último elemento de la lista
*/
void ListaPersona::Fin (){
	PI= fin;
}


/**
* Metodo avanzar. Avanza el puntero de interés una posición
* \param "" No recibe parámetros
* \return No retorna ningún valor
*/
void ListaPersona::avanzar (){
	if (PI != NULL) {
		PI = PI->siguiente;
	}
}

/**
* Metodo retrocede. Retrocede en una posición el puntero de interés
r
*/
void ListaPersona::retroceder (){
	if (PI != NULL) {
		PI = PI->anterior;
	}
}


/**
* Metodo enIncio. Devuelve verdadero si el puntero de interés apunta al primer elemento
*/
bool ListaPersona::enInicio (){
	return (PI == frente);
}

/**
* Metodo enFin. Devuelve verdadero si el puntero de interés apunta al último elemento
*/
bool ListaPersona::enFin (){
	return (PI == fin);
}

/**
* Metodo finLista. Comprueba si el puntero de interés está fuera de la lista. Este método se ha  añadido para realizar posteriores recorridos o inserciones en orden

*/
bool ListaPersona::finLista (){
	return (!PI);
}

/**
* Metodo consultar. Devuelve el dato apuntado por el puntero de interés
* \param "" No recibe parámetros
* \return Retorna el dato al que apunta el puntero de interés
*/
void ListaPersona::consultar (TipoD &dato){
	if (PI != NULL) {
		dato= PI->dato;
	}

}

/**
* Metodo insertar. Inserta un elemento en la lista. Siempre se inserta delante del puntero de interés
* \param dato Dato a insertar en la lista
* \return No retorna ningún valor
*/
bool ListaPersona::insertar (const TipoD &dato){
	bool insertado=false;
/*	La creación de un nuevo nodo implica:
	Solicitar el espacio a la memoria principal
	Verificar la validez del espacio (la memoria puede no entregarlo)
	Depositar la información básica del nodo y fijar las referencias inicialmente a NULL
	Insertarlo dentro de la estructura reorganizando las direcciones de memoria

*/

	TPunter nuevo = new TipoNod;
    nuevo->dato=dato;
    nuevo->siguiente=NULL;
    nuevo->anterior=NULL;

// casos a tener en cuenta
// 1º caso  pi es NULL
//          si lista está vacía -> inserción del primer nodo
//          si lista No está via -> inserción al final de la lista
if(PI==NULL){
	if(estaVacia ()){
		frente=nuevo;
		fin=nuevo;
		PI=nuevo;
		insertado=true;
	}
	else{
		nuevo->anterior=fin;
		fin->siguiente=nuevo;
		fin=nuevo;
		PI=nuevo;
		insertado=true;
	}

}
else{
	if(enInicio ()){
		PI->anterior=nuevo;
		nuevo->siguiente=PI;
		frente=nuevo;
		PI=PI->anterior;
		insertado=true;
	}
	else{
		(PI->anterior)->siguiente=nuevo;
		nuevo->siguiente=PI;
		nuevo->anterior=PI->anterior;
		PI->anterior=nuevo;
		PI=PI->anterior;
		insertado=true;
	}
}
//
// 2º caso pi no es NULL
//          el dato a insertar es el 1º de la lista (si el anterior de pi es NULL)
//          el dato se inserta en un nodo intermedio de la lista (modifico 4 enlaces)

// el pi siempre queda apuntado al nuevo dato
return insertado;
}




bool ListaPersona::borrar (){
	bool borrado=false;
	TPunter pAux;
	if (!estaVacia()){
        // el nodo a borrar es el primero
		pAux=PI;
		if (PI==frente)
				frente = frente->siguiente;
		if (enFin())
			fin = fin->anterior;

		PI = PI->siguiente;

		if (pAux->anterior!=NULL)
			(pAux->anterior)->siguiente = PI;
		if (pAux->siguiente!=NULL)
			PI->anterior = pAux->anterior;
		delete pAux;
		borrado=true;
	}
	return borrado;
}



void ListaPersona::modificar(const TipoD &dato){
	PI-> dato=dato;
}

/**
* Destructor de la lista. Libera la memoria reservada para la lista
* \return No retorna ningún valor
*/
ListaPersona::~ListaPersona(){

	Inicio();
	while (!estaVacia()){
		borrar();
	}

	frente= NULL;
	fin = NULL;
	PI = NULL;
}
