#include "ListaConv.h"


ListaConv::ListaConv (){
	frente = NULL;
	fin = NULL;
	PI = NULL;
}

bool ListaConv::estaVacia (){
	return (frente == NULL);
}


void ListaConv::Inicio (){
	PI = frente;
}


/**
* Metodo moverFin. Coloca el puntero de interés en el último elemento de la lista
*/
void ListaConv::Fin (){
	PI= fin;
}


/**
* Metodo avanzar. Avanza el puntero de interés una posición
* \param "" No recibe parámetros
* \return No retorna ningún valor
*/
void ListaConv::avanzar (){
	if (PI != NULL) {
		PI = PI->siguiente;
	}
}

/**
* Metodo retrocede. Retrocede en una posición el puntero de interés
r
*/
void ListaConv::retroceder (){
	if (PI != NULL) {
		PI = PI->anterior;
	}
}


/**
* Metodo enIncio. Devuelve verdadero si el puntero de interés apunta al primer elemento
*/
bool ListaConv::enInicio (){
	return (PI == frente);
}

/**
* Metodo enFin. Devuelve verdadero si el puntero de interés apunta al último elemento
*/
bool ListaConv::enFin (){
	return (PI == fin);
}

/**
* Metodo finLista. Comprueba si el puntero de interés está fuera de la lista. Este método se ha  añadido para realizar posteriores recorridos o inserciones en orden

*/
bool ListaConv::finLista (){
	return (!PI);
}

/**
* Metodo consultar. Devuelve el dato apuntado por el puntero de interés
* \param "" No recibe parámetros
* \return Retorna el dato al que apunta el puntero de interés
*/
void ListaConv::consultar (TipoDat &dato){
	if (PI != NULL) {
		dato= PI->dato;
	}

}

/**
* Metodo insertar. Inserta un elemento en la lista. Siempre se inserta delante del puntero de interés
* \param dato Dato a insertar en la lista
* \return No retorna ningún valor
*/
bool ListaConv::insertar (TipoDat &dato){
	bool insertado=false;
/*	La creación de un nuevo nodo implica:
	Solicitar el espacio a la memoria principal
	Verificar la validez del espacio (la memoria puede no entregarlo)
	Depositar la información básica del nodo y fijar las referencias inicialmente a NULL
	Insertarlo dentro de la estructura reorganizando las direcciones de memoria

*/

	TPunt nuevo = new TNodo;
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

bool ListaConv::borrar (){
	bool borrado=false;
	TPunt pAux;
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

void ListaConv::modificar(TipoDat &dato){
	PI-> dato=dato;
}

/**
* Destructor de la lista. Libera la memoria reservada para la lista
* \return No retorna ningún valor
*/
ListaConv::~ListaConv(){

	Inicio();
	while (!estaVacia()){
		borrar();
	}

	frente= NULL;
	fin = NULL;
	PI = NULL;
}

// 3ª fase
void ListaConv::operator = (ListaConv &L){

	TipoDat d;
	if(!L.estaVacia()){
		Inicio ();
		L.Inicio();
		while (!L.finLista ()){
			L.consultar (d);
			insertar(d);
			avanzar ();
			L.avanzar();
		}
	}
}
