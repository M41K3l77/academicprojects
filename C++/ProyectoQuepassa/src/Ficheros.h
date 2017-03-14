
//Ficheros.h//
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <sstream>
#include "Quepassa.h"

using namespace std;
#ifndef FICHEROS_H_
#define FICHEROS_H_

// Clase para cargar y guardar la informacion de o en los archivos *.txt contactos y mensajes
class Ficheros {

	////// Metodos Privados //////

	// Pre= { La instancia debe estar creada }
	// Post= {Separa la Linea string en cuatro campos y cada campo lo asigna a los parametros pasados
	//        por referecia}
	// Parámetros E  : Linea= string
	// Parámetros E/S: Nombre= string, Apellido=string, Telefono= string, Email= string
	void extraerCamposContactos(string linea, string &Nombre, string &Apellido, string &Telefono, string &Email);

	// Pre= { La instancia debe estar creada }
	// Post= {Separa la Linea string en tres campos y cada campo lo asigna a los parametros pasados
	//        por referecia}
	// Parámetros E  : Linea= string
	// Parámetros E/S: ContConv= string, NMDeConv=string, NumConvActual= string
	void extraerCamposQuepassaCabecera(string linea, string &ContConv, string &NMDeConv, string &NumConvActual);

	// Pre= { La instancia debe estar creada }
	// Post= {Separa la Linea string en cinco campos y cada campo lo asigna a los parametros pasados
	//        por referecia}
	// Parámetros E  : Linea= string
	// Parámetros E/S: EstadoConv= string, NMDeConv=string, Usuario= string, Contacto= string, NumMsgEnConv= string
	void extraerCamposConvCabecera(string linea, string &EstadoConv, string &NumConv, string &Usuario, string &Contacto, string &NumMsgEnConv);

	// Pre= { La instancia debe estar creada }
	// Post= {Separa la Linea string en dos campos y cada campo lo asigna a los parametros pasados
	//        por referecia}
	// Parámetros E  : Linea= string
	// Parámetros E/S: EstadoConv= string, Contacto=string, Msg= string
	void extraerCamposMsgConv(string linea, string &Contacto, string &Msg);

public:

	// Pre= { La instancia debe estar creada }
	// Post= {Obtiene toda la informacion del fichero 'fichero'(de contactos) y la carga en el objeto Q:Quepassa
	//        por referecia}
	// Parámetros E  : fichero= string
	// Parámetros E/S: Q= Quepassa
	void CargarDatosDelFicheroContactos(string fichero, Quepassa &Q);

	// Pre= { La instancia debe estar creada }
	// Post= {Obtiene toda la informacion del objeto Q:Quepassa y la guarda en el fichero:string(de contactos)
	//        por referecia}
	// Parámetros E  : fichero= string
	// Parámetros E/S: Q= Quepassa
	void GuardarDatosEnElFicheroContactos(Quepassa &Q,  string fichero);

	// Pre= { La instancia debe estar creada }
	// Post= {Obtiene toda la informacion del fichero 'fichero'(de mensajes) y la carga en el objeto Q:Quepassa
	//        por referecia}
	// Parámetros E  : fichero= string
	// Parámetros E/S: Q= Quepassa
	void CargarDatosDelFicheroMensajes(string fichero, Quepassa &Q);

	// Pre= { La instancia debe estar creada }
	// Post= {Obtiene toda la informacion del objeto Q:Quepassa y la guarda en el fichero:string(de mensajes)
	//        por referecia}
	// Parámetros E  : fichero= string
	// Parámetros E/S: Q= Quepassa
	void GuardarDatosEnElFicheroMensajes(Quepassa &Q,  string fichero);

};
#endif /* FICHEROS_H_ */
