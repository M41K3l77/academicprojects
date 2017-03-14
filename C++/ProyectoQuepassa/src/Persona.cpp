#include "Persona.h"

Persona::Persona(){
	//cout << "construyendo Persona por defecto" << endl;
	Nombre="Sin Registrar";
	Apellido="Sin Registrar";
	Telefono=0;
	Email="Sin Registrar";
}

Persona::Persona (const string &N, const string &A, const int &T, const string &E){
	//cout << "construyendo Persona por parametros" << endl;
	Nombre=N;
	Apellido=A;
	Telefono=T;
	Email=E;
}

Persona::Persona (const Persona &U){
	//cout << "construyendo Persona por copia" << endl;
	Nombre=U.Nombre;
	Apellido=U.Apellido;
	Telefono=U.Telefono;
	Email=U.Email;
}

void Persona::PutNombre (const string &N){
	Nombre=N;
}

void Persona::PutApellido (const string &A){
	Apellido=A;
}

void Persona::PutTelefono (const int &T){
	Telefono=T;
}

void Persona::PutEmail (const string &E){
	Email=E;
}

string Persona::GetNombre()const{
	return Nombre;
}

string Persona::GetApellido()const{
	return Apellido;
}

int Persona::GetTelefono()const{
	return Telefono;
}

string Persona::GetEmail()const{
	return Email;
}

// El Nombre debe ser unico por lo tanto es el atributo que usare para saber si dos personas
// son iguales, los nombres se insertaran en el programa Quepassa en mayusculas o/y minusculas
// y los respetara, pero no permitira tener dos nombres iguales uno en mayusculas y otro en
// minusculas o cualquiera de sus combinaciones(Juan, JUAN, juan, juAn...) por lo que la
// comparacion se hara en mayusculas(en ningun caso se vera modificado el valor del atributo)
bool Persona::operator == (const Persona &U){
	bool iguales=false;
	string a=Nombre;
	string b=U.Nombre;
	for(unsigned int i = 0; i < a.length(); i++){
		a[i] = toupper(a[i]);
	}
	for(unsigned int i = 0; i < b.length(); i++){
		b[i] = toupper(b[i]);
	}
	if(a==b){//Pasamos los dos string a mayusculas y vemos si son iguales
		iguales=true;
	}
	return iguales;
}

// Mismo caso que el operador ==
bool Persona::operator < (const Persona &U){
	bool menor=false;
	string a=Nombre;
	string b=U.Nombre;
	for(unsigned int i = 0; i < a.length(); i++){
		a[i] = toupper(a[i]);
	}
	for(unsigned int i = 0; i < b.length(); i++){
		b[i] = toupper(b[i]);
	}
	if(a<b){//Pasamos los dos string a mayusculas y vemos si son menores
		menor=true;
	}
	return menor;
}

// Para la tercera fase necesario para el arbol de contactos binario
bool Persona::operator > (const Persona &U){
	bool mayor=false;
	string a=Nombre;
	string b=U.Nombre;
	for(unsigned int i = 0; i < a.length(); i++){
		a[i] = toupper(a[i]);
	}
	for(unsigned int i = 0; i < b.length(); i++){
		b[i] = toupper(b[i]);
	}
	if(a>b){//Pasamos los dos string a mayusculas y vemos si son menores
		mayor=true;
	}
	return mayor;
}

// Para la tercera fase necesario para el arbol de contactos binario
bool Persona::operator != (const Persona &U){
	bool distinto=false;
	string a=Nombre;
	string b=U.Nombre;
	for(unsigned int i = 0; i < a.length(); i++){
		a[i] = toupper(a[i]);
	}
	for(unsigned int i = 0; i < b.length(); i++){
		b[i] = toupper(b[i]);
	}
	if(a!=b){//Pasamos los dos string a mayusculas y vemos si son menores
		distinto=true;
	}
	return distinto;
}

bool Persona::operator <= (const Persona &U){
	bool MenorOigual=false;
	string a=Nombre;
	string b=U.Nombre;
	for(unsigned int i = 0; i < a.length(); i++){
		a[i] = toupper(a[i]);
	}
	for(unsigned int i = 0; i < b.length(); i++){
		b[i] = toupper(b[i]);
	}
	if(a<=b){//Pasamos los dos string a mayusculas y vemos si son menores
		MenorOigual=true;
	}
	return MenorOigual;
}

void Persona::operator = (const Persona &U){
	Nombre=U.Nombre;
	Apellido=U.Apellido;
	Telefono=U.Telefono;
	Email=U.Email;
}

// Muestra todos los atributos del objeto
void Persona::MostrarDatosPersona ()const{
	cout << "----------------CONTACTO------------------"<< endl;
	cout << "Nombre Usuario: " << Nombre << endl;
	cout << "Apellido      : "<< Apellido << endl;
	cout << "Telefono      : "<< Telefono << endl;
	cout << "e-mail        : "<< Email << endl;
	cout << "------------------------------------------"<< endl;
}

// Solo muestra el atributo Nombre del objeto
void Persona::MostrarNombrePersona ()const{
	cout << "----------------CONTACTO------------------"<< endl;
	cout << "Nombre Usuario: " << Nombre << endl;
	cout << "------------------------------------------"<< endl;
}

Persona::~Persona(){
	//cout << "Destruyendo Persona" << endl;
}
