
//Conversacion.h//
#include <iostream>
#include "ColaMsg.h"
#include "Persona.h"
using namespace std;
#ifndef CONVERSACION_H_
#define CONVERSACION_H_

class Conversacion {
	int NumConver;
	Persona Usuario;
	Persona Contacto;
	ColaMsg MsgAlmacenados;
	bool EstadoConv;

public:
	// Constructores //

	// Constructor por defecto
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase}
	// Complejidad: O(1)
	Conversacion();

	// constructor copia
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de los valores
	// de los atributos de otra instancia ya creada}
	// Uno de los atributos es una cola dinamica por ello se pasa como referencia
	// Complejidad: O(1)
	Conversacion (Conversacion &Charla);

	// Métodos modificadores //

	// Pre= { La instancia debe estar creada}
	// Post= {copia ColaMsg parámetro de entrada/salida sobre el atributo ColaMsg}
	// Parámetros Entrada: ColaMsg= Msg que se lo asignamos al atributo MsgAlmacenados
	// Complejidad: O(n)
	void PutMsgAlmacenados (ColaMsg &Msg);

	// Pre= { La instancia debe estar creada}
	// Post= {Encola Mensaje parámetro de entrada sobre el atributo MsgAlmacenados}
	// Parámetros Entrada: Mensaje= Mens que lo encolamos en el atributo MsgAlmacenados
	// Complejidad: O(1)
	void PutMensaje(const Mensaje &Mens);

	// Pre= { La instancia debe estar creada. int <> empty}
	// Post= {copia el int parámetro de entrada sobre el atributo int}
	// Parámetros Entrada: NumConver= int que se lo asignamos al atributo NumConver
	// Complejidad: O(1)
	void PutNumConver (const int &NumConver);

	// Pre= { La instancia debe estar creada. Persona <> empty}
	// Post= {copia la Persona parámetro de entrada sobre el atributo Persona Usuario}
	// Parámetros Entrada: U= Persona que se lo asignamos al atributo Usuario
	// Complejidad: O(1)
	void PutUsuario (const Persona &U);

	// Pre= { La instancia debe estar creada. Persona <> empty}
	// Post= {copia la Persona parámetro de entrada sobre el atributo Persona Contacto}
	// Parámetros Entrada: C= Persona que se lo asignamos al atributo Contacto
	// Complejidad: O(1)
	void PutContacto (const Persona &C);

	// Pre= { La instancia debe estar creada }
	// Post= {Cambia el bool atributo de true a false}
	// Desactiva la conversacion
	// Complejidad: O(1)
	void TerminarConversacion ();// Poner EstadoConv a false

	// Métodos consultores //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(EstadoConv) como valor de retorno del método
	// Complejidad: O(1)
	bool EstadoConversacion () const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve la ColaMsg(MsgAlmacenados) como valor de retorno del método
	// Complejidad: O(n)
	void GetMsgAlmacenados (ColaMsg &Col);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el int(NumConver) como valor de retorno del método
	// Complejidad: O(1)
	int GetNumConver ()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el Persona(Usuario) como valor de retorno del método
	// Complejidad: O(1)
	Persona GetUsuario ()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el Persona(Contacto) como valor de retorno del método
	// Complejidad: O(1)
	Persona GetContacto ()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el Mensaje(Consulta y desencola el primero de MsgAlmacenados)
	//       como valor de retorno del método
	// Complejidad: O(1)
//	Mensaje GetMensaje();// añadido

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(vacia de MsgAlmacenados) como valor de retorno del método
	// Complejidad: O(1)
//	bool ColaMsgVacia();// Añadido

	// Ampliacion de operador = //

	// Pre= { Las instancias deben estar creadas }
	// Post= {Asigna los valores de los atributos del objeto(que se pasa como parametro
	//        a la derecha del operador = al objeto a la izquierda del operador =}
	// Parámetros Entrada: Conversacion, Conversacion que está a la derecha del signo =
	// Complejidad: O(n)
	void operator = (Conversacion &C);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       pero del atributo MsgAlmacenados solo muestra el primer mensaje
	// Complejidad: O(1)
	void MostrarConversacionSimple ();

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       del atributo MsgAlmacenados muestra todos los mensajes
	// Complejidad: O(1)
	void MostrarConversacionCompleta ();

	// Destructor //

	// Pre ={La instancia debe estar creada}
	// Post= {Destruye la instancia de la clase}
	// Complejidad: O(n)
	~Conversacion();

	// 3ª fase

	// Contar numero de mensajes en conversacion
	int NumeroDeMsgEnConv();

//	 Pre= { La instancia debe estar creada. bool <> empty}
//	 Post= {copia el bool parámetro de entrada sobre el atributo bool}
//	 Parámetros Entrada: EC= bool que se lo asignamos al atributo EstadoConv
//	 Complejidad: O(1)
	void PutEstadoCon (const bool &EC);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(condicionado a vacia de MsgAlmacenados) como valor
	//       de retorno del método. Borra los mensajes de MsgAlmacenados
	// Complejidad: O(n)
	bool BorrarMensajes();
};

#endif /* Conversacion_H_ */
