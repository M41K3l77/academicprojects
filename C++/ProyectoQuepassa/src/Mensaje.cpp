#include "Mensaje.h"
#include <string>

Mensaje::Mensaje(){
	//cout << "construyendo Mensaje por defecto" << endl;
	Msg="Sin mensaje, no mas de 50 caracteres";
	longitud=Msg.length();
}

Mensaje::Mensaje (const string &Mensag, const Persona &Pers){
	//cout << "construyendo Mensaje por parametros" << endl;
	Msg=Mensag;
	longitud=Msg.length();
	P=Pers;
}

Mensaje::Mensaje (const Mensaje &Mesage){
	Msg=Mesage.Msg;
	longitud=Msg.length();
	P=Mesage.P;
}

void Mensaje::PutMsg (const string &Mensag){
	Msg=Mensag;
	longitud=Msg.length();
	if(longitud>50){
		Msg=Msg.substr(0, 50);
	}
}

void Mensaje::PutPersona (const Persona &Pers){
	P=Pers;
}

string Mensaje::GetMsg()const{
	return Msg;
}

Persona Mensaje::GetPersona()const{
	return P;
}

int Mensaje::ContarLetras()const{
	return longitud;
}

void Mensaje::operator = (const Mensaje &Mesage){
	Msg=Mesage.Msg;
	longitud=Msg.length();
	P=Mesage.P;
}

void Mensaje::Mostrar ()const{
	cout << P.GetNombre() << " " << "''" << Msg << "''" << endl;
}

Mensaje::~Mensaje(){
	//cout << "Destruyendo mensaje" << endl;
}
