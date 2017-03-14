#include "ColaMsg.h"
#include <iostream>
using namespace std;

	ColaMsg::ColaMsg(){
		//cout << "Construyendo cola de mensajes" << endl;
		frente=NULL;
		fin=NULL;
	}

	void ColaMsg::operator = (ColaMsg &C){
		ColaMsg Caux;// Cola auxiliar que ayudará en el duplicado de datos
		tipoDato d;
		// Debido a que vamos a copiar una cola que se pasa como referencia,
		// hay primero que vaciar nuestra cola para no acumular mensajes que ya
		// pudiera haber
		 while (!Vacia()){
		        Desencolar();
		 }
		 while (!C.Vacia()){
		     C.Primero(d);
		     Encolar(d);
		     Caux.Encolar(d);
		     C.Desencolar();
		 }
		 while (!Caux.Vacia()){// Para no perder los datos de la cola &C
		     Caux.Primero(d);
		     C.Encolar(d);
		     Caux.Desencolar();
		  }
	}

	void ColaMsg::Encolar(const tipoDato &d){
		Tpuntero nodo=new tipoNodo;
		nodo->dato=d;
		nodo->siguiente=NULL;
		if(Vacia()){
			frente=nodo;
		}
		else{
			fin->siguiente=nodo;
		}
		fin=nodo;
	}

	void ColaMsg::Primero(tipoDato &d){
		if(!Vacia()){
			d=frente->dato;
		}
	}

	void ColaMsg::Desencolar(){
		Tpuntero nodoaux=frente;
		frente=nodoaux->siguiente;
		delete nodoaux;
	}

	bool ColaMsg::Vacia(){
		return (frente==NULL);
	}

	ColaMsg::~ColaMsg(){
		//cout << "Destruyendo cola de mensajes" << endl;
		while(!Vacia()){
			Desencolar();
		}
		fin=NULL;
		frente=NULL;
	}
