//============================================================================
// Name        : quePassa.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <fstream>
#include <cstdlib>
#include <sstream>
#include "Quepassa.h"
#include "Ficheros.h"
#include "Pruebas.h"
using namespace std;

// Complejidad O(n)
int Menu(){
	int opcion;
	do {
		cout << "--------------------------------------------------------------" << endl;
		cout << "1:  Crear contacto en agenda     " << endl;
		cout << "2:  Borrar contacto de la agenda     " << endl;
		cout << "3:  Buscar contacto en la agenda     " << endl;
		cout << "4:  Crear Conversacion Nueva     " << endl;
		cout << "5:  Enviar mensaje en conversacion seleccionada        " << endl;
		cout << "6:  Enviar mensaje en conversacion actual    " << endl;
		cout << "7:  Enviar respuesta en conversacion seleccionada           " << endl;
		cout << "8:  Enviar respuesta en actual       " << endl;
		cout << "9:  Desactivar conversacion      " << endl;
		cout << "10: Borrar conversacion seleccionada     " << endl;
		cout << "11: Mostrar Conversacion actual     " << endl;
		cout << "12: Mostrar conversacion seleccionada     " << endl;
		cout << "13: Mostrar todas las conversaciones completas     " << endl;
		cout << "14: Mostrar todas las conversaciones simplificadas     " << endl;
		cout << "15: Mostrar agenda     " << endl;
		cout << "16: Mostrar las conversaciones introduciendo parte del nombre del contacto" << endl;
		cout << "17: Borrar conversaciones de contactos introduciendo parte del nombre del contacto" << endl;

		cout << "18: Terminar             " << endl;
		cout << "--------------------------------------------------------------" << endl;

		cin >> opcion;

	} while ((opcion < 0) || (opcion > 18));

	return opcion;

}

// Complejidad O(n*n*n)
void QuepassaProgram(Quepassa &Q, Ficheros &F){
	cout << "______________________QUEPASSA_______________________" << endl;
	cout << "-------------------Quepassa v.1.0--------------------" << endl;
	cout << "         Programa para el envio de mensajes" << endl;
	cout << "2012 curso de Estructura de Datos y de la Informacion" << endl;
	cout << "             Miguel Angel Hogado Ceballos            " << endl;
	cout << "_____________________________________________________" << endl;

		Persona P;// La uso como auxiliar
		string NomContacto;
		string ApellContacto;
		int Telefono;
		string Email;
		string NomUsuario;
		string texto;
		string Permiso="no";
		int NumeroConversacion;
	    bool fin = false;
		int opcion = 0;
		// Llamada a la carga del inicio del programa con ficheros si no estan vacios
		string FichContactos="Contactos.txt";
		F.CargarDatosDelFicheroContactos(FichContactos, Q);

		string FichMensajes="Mensajes.txt";
		F.CargarDatosDelFicheroMensajes(FichMensajes, Q);

		while (!fin) {
			opcion = Menu();
			cin.ignore();
			switch (opcion) {
			case 1:{// el corchete es para que no salte el error salto a la etiqueta case
				    // cruza la inicialización, por usar constructor por copia
				// Crear contacto en agenda
				cout <<"Introduce Datos del contacto"<<endl;
				cout <<"Nombre del contacto :"; getline(cin,NomContacto);
				cout <<"Apellidos del contacto :"; getline(cin,ApellContacto);
				cout <<"Telefono del contacto :";
				cin >> Telefono;
				cin.ignore();// Para que no se coma Email
				cout <<"e-mail del contacto :"; getline(cin,Email);
				Persona Usuario(NomContacto, ApellContacto, Telefono, Email);
				if(Q.InsertarPersonaOrden(Usuario)==true && Q.InsertarPersonaEnArbol(Usuario)==true){
					Q.BuscarPersona(NomContacto, P);// Lista
					Q.BuscarPersonaEnArbol(NomContacto, P);// Arbol
					P.MostrarDatosPersona();
				}
				else{
					cout << "No se pudo crear contacto, quizas el contacto ya existia"
							" " "o introdujo datos erroneos" << endl;
				}

			}
				break;
			case 2:
				// Borrar contacto de la agenda
				cout <<"Nombre del contacto a eliminar :"; getline(cin,NomContacto);
				if(Q.EliminarPersona(NomContacto)==true && Q.BorrarPersonaEnArbol(NomContacto)==true){
					cout << "Contacto eliminado" << endl;
				}
				else{
					cout << "No se pudo eliminar el contacto, quizas no exista en la agenda"
							" " "o introdujo un nombre erroneo" << endl;
				}
				break;
			case 3:
				// Buscar contacto en la agenda
				cout <<"Nombre del contacto a buscar :"; getline(cin,NomContacto);
				if(Q.BuscarPersona(NomContacto, P)==true && Q.BuscarPersonaEnArbol(NomContacto, P)){
					Q.BuscarPersonaEnArbol(NomContacto, P);
					P.MostrarDatosPersona();
				}
				else{
					cout << "Nombre del contacto no encontrado" << endl;
				}

				break;
			case 4:
				// Crear Conversacion Nueva
				cout <<"Nombre del usuario :"; getline(cin,NomUsuario);
				cout <<"Nombre del contacto :"; getline(cin,NomContacto);
				cout <<"Escribe mensaje de texto :"; getline(cin,texto);
				if(Q.NuevaConversacion(NomUsuario, NomContacto, texto)==true){
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				else{
					cout << "No se pudo crear la conversacion, usuario o contacto erroneo/s" << endl;
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
					// Si no hay conversaciones activas no mostrará nada
				}


				break;
			case 5:
				// Enviar mensaje en conversacion seleccionada
				cout <<"Numero de la conversacion a la que se envia el mensaje del usuario :";
				cin >> NumeroConversacion;
				cin.ignore();
				cout <<"Nombre del usuario :"; getline(cin,NomUsuario);
				cout <<"Mensaje de texto :"; getline(cin,texto);
				cout << "`si´ para confirmar el envio del mensaje" << endl;
				cout <<"Confirmar envio :"; getline(cin,Permiso);
				if(Q.EnviarMensaje(NumeroConversacion, NomUsuario, texto, Permiso)==true){
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				else{
					cout << "No se pudo enviar el txt, no se confirmo el mensaje o"
							" " "usuario/nº de conversacion erroneo" << endl;
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
					// En caso de no enviarse volveria a la conversacion previa
				}

				break;
			case 6:
				// Enviar mensaje en conversacion actual
				cout <<"Mensaje de texto :"; getline(cin,texto);
				cout << "`si´ para confirmar el envio del mensaje" << endl;
				cout <<"Confirmar envio :"; getline(cin,Permiso);
				if(Q.EnviarMensaje(texto, Permiso)==true){
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				else{
					cout << "No se pudo enviar el txt, no se confirmo el mensaje"
							" " "no hay conversacion/s, o no esta/n activa/s" << endl;
				}
				break;
			case 7:
				// Enviar respuesta en conversacion seleccionada
				cout <<"Numero de la conversacion a la que se envia la respuesta de contacto : ";
				cin >> NumeroConversacion;
				cin.ignore();
				cout <<"Nombre del contacto :"; getline(cin,NomContacto);
				cout <<"Mensaje de texto :"; getline(cin,texto);
				cout << "`si´ para confirmar el envio del mensaje" << endl;
				cout <<"Confirmar envio :"; getline(cin,Permiso);
				if(Q.EnviarRespuesta(NumeroConversacion, NomContacto, texto, Permiso)==true){
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				else{
					cout << "No se pudo enviar el txt, no se confirmo el mensaje"
							" " "no se confirmo el mensaje o contacto/nº de conversacion erroneo" << endl;
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
					// En caso de no enviarse volveria a la conversacion previa
				}
				break;
			case 8:
				// Enviar respuesta en actual
				cout <<"Mensaje de texto :"; getline(cin,texto);
				cout << "`si´ para confirmar el envio de la respuesta" << endl;
				cout <<"Confirmar envio :"; getline(cin,Permiso);
				if(Q.EnviarRespuesta(texto, Permiso)!=true){
					cout << "--------------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				else{
					cout << "No se pudo enviar el txt, no se confirmo el mensaje"
							" " "no hay conversacion/s, o no esta/n activa/s" << endl;
				}
				break;
			case 9:
				// Desactivar conversacion
				cout <<"Numero de la conversacion a desactivar:";
				cin >> NumeroConversacion;
				if(Q.DesactivarConvDeLista(NumeroConversacion)==true){
					cout << "------------------------------------------------------" << endl;
					Q.MostrarTodasLasConversacionesSimples();
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();// En caso de haber alguna activa
				}
				else{
					cout << "No se pudo desactivar la conversacion, No existe o ya esta desactivada" << endl;
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();// En caso de no desactivarse volvemos a la conv actv si la hubiere
				}
				break;
			case 10:
				// Borrar conversacion seleccionada
				cout <<"Numero de la conversacion a borrar: ";
				cin >> NumeroConversacion;
				if(Q.BorrarConvSeleccionada(NumeroConversacion)==true){
					cout << "------------------------------------------------------" << endl;
					Q.MostrarTodasLasConversacionesSimples();
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();// En caso de haber alguna activa
				}
				else{
					cout << "No se pudo borrar la conversacion, No existe o no esta desactivada" << endl;
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();// volvemos a la activa si la hubiere
				}
				break;
			case 11:
				// Mostrar Conversacion actual
				cout << "------------------------------------------------------" << endl;
				Q.MostrarConvActual();
				break;

			case 12:
				// Mostrar conversacion seleccionada
				cout <<"Numero de la conversacion a mostrar:" << endl;
				cin >> NumeroConversacion;
				cout <<"-------------Conversacion seleccionada-----------" << endl;
				Q.MostrarUnaConversacion(NumeroConversacion);
				break;
			case 13:
				// Mostrar todas las conversaciones completas
				cout << "------------------------------------------------------" << endl;
				Q.MostrarTodasLasConversacionesCompletas();
				break;
			case 14:
				// Mostrar todas las conversaciones simplificadas
				cout <<"---------Todas las conversaciones simplificadas--------" << endl;
				Q.MostrarTodasLasConversacionesSimples();
				cout <<"-------------------------------------------------" << endl;
				break;
			case 15:
				// Mostrar Agenda
				cout << "------------------------------------------------------" << endl;
				cout <<"Mostrando todos los contactos de la agenda:" << endl;
				//Q.MostrarAgenda(); // Mostraria La Agenda lista de personas
				Q.MostrarArbolContactos();// Muestra los contactos en el arbol de contactos
				break;
			case 16:
				// Mostrar todas las conversaciones relacionadas a un contacto
				// usando parte del nombre
				cout <<"---Conversaciones de los contactos relacionados---" << endl;
				cout <<"Introduce el nombre o parte de el :"; getline(cin,NomContacto);
				if(Q.MostrarConvDePersonas(NomContacto)==false){
					cout <<"No hay coincidencia con el nombre o parte de el" << endl;
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				break;
			case 17:
				// Borrar todas las conversaciones relacionadas a un contacto
				// usando parte del nombre
				cout <<"Mostrando todos los contactos de la agenda:" << endl;
				cout <<"Introduce el nombre o parte de el :"; getline(cin,NomContacto);
				if(Q.BorrarConvDePersonas(NomContacto)==true){
					cout << "------------------------------------------------------" << endl;
					Q.MostrarTodasLasConversacionesSimples();
				}
				else{
					cout <<"No hay coincidencia con el nombre o parte de el" << endl;
					cout << "------------------------------------------------------" << endl;
					Q.MostrarConvActual();
				}
				break;
			case 18:
				// Final del programa
				cout << "-----------------Programa Quepassa apagado----------------" << endl;
				F.GuardarDatosEnElFicheroContactos(Q,  "Contactos.txt");
				F.GuardarDatosEnElFicheroMensajes(Q,  "Mensajes.txt");
				fin = true;
				break;
			}
		}
}

int main() {
	// En caso de querer ejecutar el juego de pruebas simplemente eliminar
	// los dobles slashes del objeto Pruebas P y P.TodasLasPruebas(), y en tal
	// caso poner doble slashes en Quepassa Q, Ficheros F y QuepassaProgram(Q)
//	Pruebas P;
//	P.TodasLasPruebas();
	Quepassa Q;
	Ficheros F;
	QuepassaProgram(Q, F);

	return 0;
}
