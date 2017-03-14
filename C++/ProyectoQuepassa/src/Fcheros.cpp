#include "Ficheros.h"
#include <iostream>
using namespace std;

// Metodos de Contactos

void Ficheros::extraerCamposContactos(string linea, string &Nombre, string &Apellido, string &Telefono, string &Email){
	int posicion, posicion2, posicion3;
	posicion=linea.find("#");
	Nombre.c_str();
	Nombre=linea.substr(0, posicion);
	posicion2=linea.find("#", posicion+1);
	Apellido.c_str();
	Apellido=linea.substr(posicion+1, posicion2-posicion-1);
	posicion3=linea.find("#", posicion2+1);
	Telefono.c_str();
	Telefono=linea.substr(posicion2+1, posicion3-posicion2-1);
	Email.c_str();
	Email=linea.substr(posicion3+1);
}

// Carga de ficheros
// De momento solo contactos
void Ficheros::CargarDatosDelFicheroContactos(string fichero, Quepassa &Q){
	string linea;
	string Nombre;
	string Apellido;
	string Telefono;
	int Tlf=0;
	string Email;

	if(!Q.ArbolDeContactosVacio()){// Hay que usar una lista vacia y arbol
		Q.BorrarArbolContactos();
	}
	if(!Q.ListaDeContactosVacia()){
		Q.BorrarAgendaDeContactos();
	}
	ifstream f;
	//Persona Pers; // no hace falta se hace mas adelante parametrizado
	f.open(fichero.c_str());
	if(f.is_open()){
		while(!f.eof()){
			getline(f, linea);
			if (!f.eof()){
			extraerCamposContactos(linea, Nombre, Apellido, Telefono, Email);
			Tlf=atoi(Telefono.c_str());// Numero de telefono string a int
			Persona Pers(Nombre, Apellido, Tlf, Email);
			Q.InsertarPersonaEnArbol(Pers); // Arbol
			Q.InsertarPersonaOrden(Pers); // Lista
			}
		}
		f.close();
	}
}

// Pasamos los datos al fichero de texto
void Ficheros::GuardarDatosEnElFicheroContactos(Quepassa &Q,  string fichero){
	ofstream f;
	Persona Pers;
	string Nombre;
	string Apellido;
	string Telefono;
	int Tlf=0;
	string Email;
	Arbol CopiaContactos;
	Q.CopiarArbolDeContactos(CopiaContactos);

	f.open(fichero.c_str(), ios::trunc);
	if(f.is_open()){
		while(!CopiaContactos.vacio()){
			Pers=CopiaContactos.DatoMenorDelArbol();
			Nombre=Pers.GetNombre();
			Apellido=Pers.GetApellido();
			Tlf=Pers.GetTelefono();
			stringstream Entero;// para poder pasar el entero a string // usa #include <sstream>
			Entero << Tlf;
			Telefono=Entero.str();
			Email=Pers.GetEmail();

			f << Nombre << '#' << Apellido << '#' << Telefono << "#" << Email << endl;//De lista a fichero

			CopiaContactos.borrar(Pers);
		}
		f.close();
	}
}


// Metodos de Mensajes...

// Numero de conversaciones, numero mayor usado para ID conv y numero de la conv actual
void Ficheros::extraerCamposQuepassaCabecera(string linea, string &ContConv, string &NMDeConv, string &NumConvActual){
	int posicion, posicion2;
	posicion=linea.find("#");
	ContConv.c_str();
	ContConv=linea.substr(0, posicion);
	posicion2=linea.find("#", posicion+1);
	NMDeConv.c_str();
	NMDeConv=linea.substr(posicion+1, posicion2-posicion-1);
	NumConvActual.c_str();
	NumConvActual=linea.substr(posicion2+1);
}

void Ficheros::extraerCamposConvCabecera(string linea, string &EstadoConv, string &NumConv, string &Usuario, string &Contacto, string &NumMsgEnConv){
	int posicion, posicion2, posicion3, posicion4;
	posicion=linea.find("#");
	EstadoConv.c_str();
	EstadoConv=linea.substr(0, posicion);
	posicion2=linea.find("#", posicion+1);
	NumConv.c_str();
	NumConv=linea.substr(posicion+1, posicion2-posicion-1);
	posicion3=linea.find("#", posicion2+1);
	Usuario.c_str();
	Usuario=linea.substr(posicion2+1, posicion3-posicion2-1);
	posicion4=linea.find("#", posicion3+1);
	Contacto.c_str();
	Contacto=linea.substr(posicion3+1, posicion4-posicion3-1);
	NumMsgEnConv.c_str();
	NumMsgEnConv=linea.substr(posicion4+1);
}

void Ficheros::extraerCamposMsgConv(string linea, string &Contacto, string &Msg){
	int posicion;
	posicion=linea.find("#");
	Contacto.c_str();
	Contacto=linea.substr(0, posicion);
	Msg.c_str();
	Msg=linea.substr(posicion+1);
}

void Ficheros::CargarDatosDelFicheroMensajes(string fichero, Quepassa &Q){

	string linea;
	string ContadorConversacion;
	int ContConv=0;
	string NumeroMayorDeConversaciones;
	int NMDeConv=0;
	string NumConvActual;
	int NumConvActl=0;
	string NumMsgEnConv; // Veces que habra que usar getline por conversacion
	int NumMEnConv=0;
	Conversacion C;
	string EstadoConv;
	bool EstdCon=false;
	string NumConv;
	int NConv=0;// Numero de una conversacion
	string U;
	string Contact;
	Persona Usuario;
	Persona Contacto;
	string Msg;
	string Individuo; // Para comparar U y Contact cuando proceda
	Mensaje Mesagge;
	ListaConv L;


	if(!Q.ListaDeConvVacia()){
		Q.BorrarTodasLasConv();
	}
	ifstream f;
	f.open(fichero.c_str());
	if(f.is_open()){
		getline(f, linea);
		if (!f.eof()){
			extraerCamposQuepassaCabecera(linea, ContadorConversacion, NumeroMayorDeConversaciones, NumConvActual);
			ContConv=atoi(ContadorConversacion.c_str());// Numero de string a int
			NMDeConv=atoi(NumeroMayorDeConversaciones.c_str());// Numero de string a int
			// Se usara mas adelante para poner el PI(pto de interes de la ListConv donde proceda)
			NumConvActl=atoi(NumConvActual.c_str());// Numero de string a int
			Q.PutContadorDeConv(ContConv);
			Q.PutNumeroMayorDeConversaciones(NMDeConv);

			while(!f.eof()){
				getline(f, linea);
				if (!f.eof()){
					extraerCamposConvCabecera(linea, EstadoConv, NumConv, U, Contact, NumMsgEnConv);
					if(EstadoConv=="Activa"){
						EstdCon=true;
					}

					NConv=atoi(NumConv.c_str());// Numero de string a int
					NumMEnConv=atoi(NumMsgEnConv.c_str());// Numero de string a int
					Q.BuscarPersonaEnArbol(U, Usuario);
					Q.BuscarPersonaEnArbol(Contact, Contacto);
					C.PutEstadoCon (EstdCon);
					C.PutNumConver(NConv);
					C.PutUsuario(Usuario);
					C.PutContacto(Contacto);
					C.BorrarMensajes();

					//cout << NumMEnConv << endl;//////////////

					for(int i=0;i<NumMEnConv;i++){
						getline(f, linea);
						extraerCamposMsgConv(linea, Individuo, Msg);
						if(Individuo==U){
							Mesagge.PutMsg(Msg);
							Mesagge.PutPersona(Usuario);
							C.PutMensaje(Mesagge);
						}
						else{
							Mesagge.PutMsg(Msg);
							Mesagge.PutPersona(Contacto);
							C.PutMensaje(Mesagge);
						}
					}

					L.insertar(C);
					L.avanzar();
				}
			}
		}

		f.close();
		Q.PutListaConv(L);
		// Para que el PI vaya a la conversacion activa cuando se apago el programa Quepassa
		if(NumConvActl!=0){
			Q.BuscarConversacionActiva(NumConvActl);
		}
	}

}

void Ficheros::GuardarDatosEnElFicheroMensajes(Quepassa &Q,  string fichero){
	ofstream f;
	Persona Usuario;
	string U;
	Persona Contacto;
	string Contact;
	int NumConversacion=0;
	string NumC;
	ColaMsg ColaDeMensajes;
	Mensaje M;
	string Msg;
	bool EstadoConversacion=false;
	int ContadorConversacion=0;
	int NumeroMayorDeConversaciones=0;
	ListaConv CopiaListaConv;
	int NumConvActual=0;
	Conversacion C;
	string estConver;
	int numeroDeMensajes=0;

	f.open(fichero.c_str(), ios::trunc);
	if(f.is_open()){

		ContadorConversacion=Q.GetContadorDeConv();
		NumeroMayorDeConversaciones=Q.GetNumeroMayorDeConversaciones();
		NumConvActual=Q.NumeroConversacionActual();// Numero de la Conv actual a la que retornar el PI
		f << ContadorConversacion << '#' << NumeroMayorDeConversaciones <<  '#' << NumConvActual << endl;
		Q.GetListaConv(CopiaListaConv);
		if(NumConvActual!=0){
			Q.BuscarConversacionActiva(NumConvActual);
		}
		CopiaListaConv.Inicio();

		while(!CopiaListaConv.finLista()){

			CopiaListaConv.consultar(C);
			numeroDeMensajes=C.NumeroDeMsgEnConv();
			NumConversacion=C.GetNumConver();
			Usuario=C.GetUsuario();
			U=Usuario.GetNombre();
			Contacto=C.GetContacto();
			Contact=Contacto.GetNombre();
			C.GetMsgAlmacenados(ColaDeMensajes);
			EstadoConversacion=C.EstadoConversacion();
			if(EstadoConversacion==true){
				estConver="Activa";
			}
			else{
				estConver="Inactiva";
			}
			stringstream Entero;// para poder pasar el entero a string // usa #include <sstream>
			Entero << NumConversacion;
			NumC=Entero.str();
//
			f << estConver << '#' << NumC << '#' << U << "#" << Contact << "#" << numeroDeMensajes << endl;//De lista a fichero
			while(!ColaDeMensajes.Vacia()){
				ColaDeMensajes.Primero(M);
				ColaDeMensajes.Desencolar();
				Contacto=M.GetPersona();// Podra ser el usuario o el contacto
				Contact=Contacto.GetNombre();
				Msg=M.GetMsg();
				f << Contact << '#' << Msg << endl;
			}

			CopiaListaConv.avanzar();
		}
		f.close();
	}
}
