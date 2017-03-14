#include "Conversacion.h"
#include <iostream>
using namespace std;
// Cuando creemos una conversacion, automaticamente se pondra activa
// ademas por defecto tendra el numero cero y este cambiara segun el numero
// de la ultima conversacion creada (en clase Quepassa)
Conversacion::Conversacion(){
	NumConver=0;
	EstadoConv=true;
}

Conversacion::Conversacion (Conversacion &Charla){
	NumConver=Charla.NumConver;
	Usuario=Charla.Usuario;
	Contacto=Charla.Contacto;
	EstadoConv=Charla.EstadoConv;
	MsgAlmacenados=Charla.MsgAlmacenados;
}

void Conversacion::PutMsgAlmacenados (ColaMsg &Msg){
	MsgAlmacenados=Msg;
}
// añadido
void Conversacion::PutMensaje(const Mensaje &Mens){
	MsgAlmacenados.Encolar(Mens);
}

// añadido
// Mensaje Conversacion::GetMensaje(){
//	 Mensaje M;
//	MsgAlmacenados.Primero(M);
//	MsgAlmacenados.Desencolar();
//	 return M;
//}
// añadido
// bool Conversacion::ColaMsgVacia(){
//	 bool vacia=false;
//	 if(MsgAlmacenados.Vacia()==true){
//		 vacia=true;
//	 }
//	 return vacia;
// }

void Conversacion::PutNumConver (const int &NumConver){
	this->NumConver=NumConver;
}

void Conversacion::PutUsuario (const Persona &U){
	Usuario=U;
}

void Conversacion::PutContacto (const Persona &C){
	Contacto=C;
}

void Conversacion::TerminarConversacion (){
	EstadoConv=false;
}

bool Conversacion::EstadoConversacion ()const{
	return EstadoConv;
}

void Conversacion::GetMsgAlmacenados (ColaMsg &Col){
	Col=MsgAlmacenados;
}

int Conversacion::GetNumConver ()const{
	return NumConver;
}

Persona Conversacion::GetUsuario ()const{
	return Usuario;
}

Persona Conversacion::GetContacto ()const{
	return Contacto;
}

void Conversacion::operator = (Conversacion &C){
	NumConver=C.NumConver;
	Usuario=C.Usuario;
	Contacto=C.Contacto;
	EstadoConv=C.EstadoConv;
	MsgAlmacenados=C.MsgAlmacenados;
}

void Conversacion::MostrarConversacionSimple (){
	Mensaje M;
	MsgAlmacenados.Primero(M);
	if(EstadoConv==true){
		cout << "*" << " ";
	}
	else{
		cout << " ";
	}
	cout << NumConver << " ";
	M.Mostrar();
}

void Conversacion::MostrarConversacionCompleta (){
	ColaMsg AuxMsgAlmacenados;
	Mensaje M;
	if(EstadoConv==true){
		cout << "*" << " ";
	}
	else{
		cout << " ";
	}
	cout << NumConver << " ";
	while(!MsgAlmacenados.Vacia()){
		MsgAlmacenados.Primero(M);
		M.Mostrar();
		MsgAlmacenados.Desencolar();
		if(!MsgAlmacenados.Vacia()){// Para evitar mostrar un igual en una linea que no existe
			cout << "     =";
		}
		AuxMsgAlmacenados.Encolar(M);
	}
	while(!AuxMsgAlmacenados.Vacia()){
		AuxMsgAlmacenados.Primero(M);
		AuxMsgAlmacenados.Desencolar();
		MsgAlmacenados.Encolar(M);
	}
}

Conversacion::~Conversacion(){
	//cout << "Destruyendo Conversacion" << endl;
	while(!MsgAlmacenados.Vacia()){
		MsgAlmacenados.Desencolar();
	}
}

// 3ª fase
int Conversacion::NumeroDeMsgEnConv(){
	int ContadorDeMensajes=0;
	ColaMsg AuxMsgAlmacenados;
	Mensaje M;

	while(!MsgAlmacenados.Vacia()){
		MsgAlmacenados.Primero(M);
		ContadorDeMensajes++;
		MsgAlmacenados.Desencolar();
		AuxMsgAlmacenados.Encolar(M);
	}
	while(!AuxMsgAlmacenados.Vacia()){
		AuxMsgAlmacenados.Primero(M);
		AuxMsgAlmacenados.Desencolar();
		MsgAlmacenados.Encolar(M);
	}
	return ContadorDeMensajes;
}

void Conversacion::PutEstadoCon (const bool &EC){
	EstadoConv=EC;
}

bool Conversacion::BorrarMensajes(){
	bool borrado=false;
	if(!MsgAlmacenados.Vacia()){
		while(!MsgAlmacenados.Vacia()){
			MsgAlmacenados.Desencolar();
		}
	}
	return borrado;
}
