//Quepassa.h//

#ifndef QUEPASSA_H
#define QUEPASSA_H
#include <iostream>
#include "ListaConv.h"
#include "ListaPersona.h"
#include "ArbolContactos.h"
using namespace std;


class Quepassa{

	// Atributo lista de conversaciones
	ListaConv L;

	// Atributo lista de personas(una agenda)
	ListaPersona Agenda;

	// Atributo entero para llevar la cuenta del número de conversaciones
	// Necesario por que no podemos tener mas de 10 conversaciones
	int ContConv;

	// Atributo entero para saber cual fué el último número de conversacion
	// que se asignó a una conversación y que no se repita
	int NumeroMayorDeConversaciones;
	// Ejm: si la ultima conv en crearse fue la 24, si se crea una nueva, llevará como
	// número==>NumeroMayorDeConversaciones+1; y a su vez este atributo lo
	// incrementaremos en uno

	// Tercera fase, arbol de contactos
	Arbol ArbolContactos;

	////////////////////// Métodos privados de la clase ////////////////////////

	// Pre: La instancia debe estar creada y la lista L no debe estar vacia
	//      de lo contrario devolverá false(ello no implica error, simplemente quiere
	//      decir que no borró por que no había nada que borrar)
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(borrado) como valor de retorno del método, nos dirá
	//       si se borro la conversacion o no. Solo podrá borrar conversaciones desactivadas
	//       y estas estarán al final de la lista, además por como se ordena la lista, la
	//       conversacion desactivada más antigua será la ultima de la lista, por lo que si
	//       tenemos una o mas conversaciones desactivadas, la mas antigua(la del fin) será
	//       la borrada
	// Complejidad: O(n)
	bool BorrarConvAutomatica();

	// Pre: La instancia debe estar creada y la lista L no debe estar vacia y almenos
	//      una conversacion activa(si no devolverá false)
	// Parámetros: Entrada : Permiso:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(confirmacion) como valor de retorno del método, nos dirá
	//       si se envió el mensaje/respuesta o no. Solo podrá enviar el mensaje
	//       si el string Permiso vale "si", cualquier otro valor devolverá false
	// Complejidad: O(1)
	bool ConfirmacionMensaje(const string &Permiso);

	// Pre: La instancia debe estar creada
	// Parámetros: Entrada : N:int
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(encontrada) como valor de retorno del método, nos dirá
	//       si se encontro la conversacion desactivada o no. en caso de que no esté
	//       desactivada pondremos el puntero fuera de la lista para ahorrarnos consultas
	// Complejidad: O(n*n) (devido a un bucle O(n) con una asignacion ampliada O(n) dentro)
	bool BuscarConversacionNoActiva(const int &N);

	// Pre: La instancia debe estar creada y la conversacion desactivada
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : C:Conversacion
	// Post: Moveremos una conversacion previamente desactivada para colocarla con el
	//       resto de conversaciones desactivadas si las hubiere. Si no las hay,
	//       la colocará al final, de lo contrario irá buscando una posición desde atras
	//       hacia adelante. Si encuentra una activa, avanzará una posición y la insertará
	//       si por el contrario no hay ninguna quedará insertada al principio y por lo
	//       tanto será la ultima conversación desactivada
	// Complejidad: O(n*n) (devido a un bucle(busqueda de posición) O(n) con una asignacion ampliada O(n) dentro)
	void MoverConversacion(Conversacion &C);

public:

	// Constructores //

	// Constructor por defecto
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase}
	// Complejidad: O(1)
	Quepassa ();

	// constructor copia
	// Pre ={}
	// Post= {Crea/inicializa la instancia de la clase a partir de los valores
	// de los atributos de otra instancia ya creada}
	// Uno de los atributos es una lista dinamica (con una cola dinamica dentro)
	// por ello se pasa como referencia
	// Complejidad: O(1)
	Quepassa (Quepassa &Q);

	// Métodos modificadores //

	// Pre= { La instancia debe estar creada. ListaConv <> empty}
	// Post= {copia la ListaConv parámetro de entrada sobre el atributo ListaConv}
	// Parámetros Entrada: Lista= ListaConv que se lo asignamos al atributo L
	// Complejidad: O(n*n)(devido a la ampiacion del operador = en la cola de mensajes)
	void PutListaConv(ListaConv &Lista);

	// Pre= { La instancia debe estar creada. ListaPersona <> empty}
	// Post= {copia la ListaPersona parámetro de entrada sobre el atributo ListaPersona}
	// Parámetros Entrada: A= ListaPersona que se lo asignamos al atributo Agenda
	// Complejidad: O(n)(devido a la ampiacion del operador = en la cola de mensajes)
	void PutAgenda(ListaPersona &A);// falta ampliar operador =

	// Pre= { La instancia debe estar creada. int <> empty}
	// Post= {copia el int parámetro de entrada sobre el atributo int,
	//        lleva la cuenta del número de conversaciones que tenemos}
	// Parámetros Entrada: N= int que se lo asignamos al atributo ContConv
	// Complejidad: O(1)
	void PutContadorDeConv(const int &N);

	// Pre= { La instancia debe estar creada. int <> empty}
	// Post= {copia el int parámetro de entrada sobre el atributo int,
	//        guarda el número de la ultima conversación creada}
	// Parámetros Entrada: N= int que se lo asignamos al atributo NumeroMayorDeConversaciones
	// Complejidad: O(1)
	void PutNumeroMayorDeConversaciones(const int &N);

	// Métodos selectores //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el ListaConv(L) como valor de retorno del método
	// Complejidad: O(n*n) (devido a la ampiacion del operador = en la cola de mensajes)
	void GetListaConv(ListaConv &ListCo);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el ListaPersona(Agenda) como valor de retorno del método
	// Complejidad: O(n)
	ListaPersona GetAgenda();// falta ampliar operador = ademas debe ser void

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el int(ContConv) como valor de retorno del método
	// Complejidad: O(1)
	int GetContadorDeConv()const;

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el int(NumeroMayorDeConversaciones) como valor de retorno del método
	// Complejidad: O(1)
	int GetNumeroMayorDeConversaciones()const;

	// Resto de métodos //

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Pers
	// 			   Salida  : No hay
	// 			   E/S     : P
	// Post: Devuelve bool(encontrado), en caso de encontrar una persona en la agenda
	//       con el nombre(string:Pers) tendremos de salida sus datos en Persona:P
	//       en caso de no encontrarla devolverá false y el contenido de Persona:P será
	//       irrelevante ya que no se usará
	// Complejidad: O(n)
	bool BuscarPersona(const string &Pers, Persona &P);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Pers
	// 			   Salida  : No hay
	// 			   E/S     : P
	// Post: Devuelve bool(insertado), si la persona no existe en la Agenda la inserta
	//       teniendo en cuenta si es mayor o menor a las que ya hay(en el caso de estar
	//       vacia la inserta directamente), la comparación es lexicográfica y de orden
	//       alfabeticamente ascendenta(a,b,c...z)
	// Complejidad: O(n*n) (devido a un bucle de busqueda y ampiacion del operador < y == en
    //              la clase Persona)
	bool InsertarPersonaOrden(Persona &P);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Pers
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(borrado), en caso de encontrar una persona en la agenda
	//       con el nombre(string:Pers) procederá a su eliminacion de la Agenda
	//       y en tal caso buscará en L(lista de conv) conversaciones donde esté
	//       presente como contacto o usuario para desactivarlas y que no permita
	//       portanto el envio de mensajes o respuesta
	// Complejidad: O(n*n)
	bool EliminarPersona(const string &Pers);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : N:int
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(desactivada), buscaremos la conversacion por su número
	//       la desactivará y la moverá junto con el resto de desactivadas sino las
	//       hubiera se movería al final
	// Complejidad: O(n*n)
	bool DesactivarConvDeLista(const int &N);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : N:int
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(borrada), buscaremos la conversacion por su número
	//       entre las desactivadas ya que serán las unicas que se pueden borrar
	//       En caso de borrarse, se decrementará en uno el atributo ContConv
	//       que es el que lleva la cuenta de conversaciones en la lista de conv
	// Complejidad: O(n*n)
	bool BorrarConvSeleccionada(const int &N);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : usuario:string, contacto:string, Msg:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(creada), Buscará al usuario y al contacto en la Agenda
	//       si encuentra a ambos y la lista de conversaciones está vacia, la inserta
	//       (la crea), si no está vacia comprobará si tenemos 10 conversaciones o menos,
	//       la creará quedando esta como conversacion actual. Si ya tenemos 10
	//       conversaciones, comprobará si tenemos alguna desactivada, si no tenemos
	//       alguna desactivada, no la creará, si tenemos alguna desactivada, procederá a
	//       su borrado(será la que esté al final de la lista) e insertará la nueva
	//       conversación
	// Complejidad: O(n)
	bool NuevaConversacion(const string &usuario, const string &contacto, const string &Msg);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Nconv:int, usuario:string, Msg:string, Permiso:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(enviado), si la lista de conv no está vacia y no estamos
	//       fuera de la lista, comparará el que quiere enviar el mensaje(Persona), con
	//       el usuario que está en la conversación previamente buscada por su numero, si
	//       hay coincidencia se pedirá confirmación, si es positiva, se enviará el nuevo
	//       mensaje. En caso de no cumplirse alguna de las condiciones, se volvería a la
	//       conversacion activa que estuvieramos previamente
	// Complejidad: O(n*n)
	bool EnviarMensaje(const int &Nconv, const string &usuario, const string &Msg, const string &Permiso);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Msg:string, Permiso:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(enviado), si la lista de conv no está vacia y no estamos
	//       fuera de la lista, y previa solicitud de permiso, enviará el mensaje a la
	//       conversación actual
	// Complejidad: O(n)
	bool EnviarMensaje(const string &Msg, const string &Permiso);// Lo envia a conversacion actual

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Nconv:int, contacto:string, Respuesta:string, Permiso:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(enviado), si la lista de conv no está vacia y no estamos
	//       fuera de la lista, comparará el que quiere enviar la respuesta(Persona), con
	//       el contacto que está en la conversación previamente buscada por su numero, si
	//       hay coincidencia se pedirá confirmación, si es positiva, se enviará la nueva
	//       respuesta. En caso de no cumplirse alguna de las condiciones, se volvería a la
	//       conversacion activa que estuvieramos previamente
	// Complejidad: O(n*n)
	bool EnviarRespuesta(const int &Nconv, const string &contacto, const string &Respuesta, const string &Permiso);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Msg:string, Permiso:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve bool(enviado), si la lista de conv no está vacia y no estamos
	//       fuera de la lista, y previa solicitud de permiso, enviará la respuesta a la
	//       conversación actual
	// Complejidad: O(n)
	bool EnviarRespuesta(const string &Msg, const string &Permiso);// Lo envia a conversacion actual

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       es decir todos los contactos/usuarios con sus respectivos datos
	//       de la Agenda(lista dinamica)
	// Complejidad: O(n)
	void MostrarAgenda();

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       es decir todas las conversaciones con sus respectivos datos(todos los mensajes)
	//       de la lista de conversaciones(lista dinamica)
	// Complejidad: O(n*n)(ya que re corremos la lista dinamica y cola de mensajes dinamica)
	void MostrarTodasLasConversacionesCompletas();

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       es decir todas las conversaciones con sus respectivos datos(primer mensaje)
	//       de la lista de conversaciones(lista dinamica)
	// Complejidad: O(n)
	void MostrarTodasLasConversacionesSimples();

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : N:int
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       Se muestra por pantalla la conversacion que tenga el numero N
	//       como atributo(NumConver)
	// Complejidad: O(n*n)
	void MostrarUnaConversacion(const int &N);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : No hay
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       Se muestra por pantalla la conversacion actual(a la que apunta PI de
	//       la lista de conversacion)(si no hay conversacion activa no hará nada)
	// Complejidad: O(n)
	void MostrarConvActual();

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Pers:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Muestra por pantalla los atributos de la instancia que lo invoque
	//       Se muestra por pantalla la conversacion/es en las que halla un usuario
	//       o contacto coincidente en parte o totalmente con el string introducido
	//       este string podra ser parte o igual a un nombrede un usuario/contacto
	//       si no hay coincidencia no hará nada
	//       devuelve bool(mostrar)
	// Complejidad: O(n*n)
	bool MostrarConvDePersonas(const string &Pers);

	// Pre: La instancia debe estar creada.
	// Parámetros: Entrada : Pers:string
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Borra las conversacion/es en las que halla un usuario
	//       o contacto coincidente en parte o totalmente con el string introducido
	//       este string podra ser parte o igual a un nombrede un usuario/contacto
	//       si no hay coincidencia no hará nada
	//       devuelve bool(borradas)
	// Complejidad: O(n*n)
	bool BorrarConvDePersonas(const string &Pers);

	// Defensa 2ª parte del proyecto
	// Mostrar la conversacion con mas mensajes
	void MostrarConvMasMsg();

	/////////////// 3ª fase del proyecto ///////////////

	bool InsertarPersonaEnArbol(Persona &P);

	bool BorrarPersonaEnArbol(const string &P);

	bool BuscarPersonaEnArbol(const string &Pers, Persona &P);

	void MostrarArbolContactos();

	bool ArbolDeContactosVacio();

	void BorrarArbolContactos();

	void BorrarAgendaDeContactos();

	bool ListaDeContactosVacia();

	// El puntero
	void AvanzarEnLaAgenda();

	void InicioEnLaAgenda();

	bool FueraDeLaAgenda();

	void ConsultarPersonaEnAgenda(Persona &P);

	void CopiarArbolDeContactos(Arbol &A);

	int NumeroConversacionActual();

	// Pre: La instancia debe estar creada
	// Parámetros: Entrada : N:int
	// 			   Salida  : No hay
	// 			   E/S     : No hay
	// Post: Devuelve el bool(encontrada) como valor de retorno del método, nos dirá
	//       si se encontro la conversacion desactivada o no. en caso de que no esté
	//       desactivada pondremos el puntero fuera de la lista para ahorrarnos consultas
	// Complejidad: O(n*n) (devido a un bucle O(n) con una asignacion ampliada O(n) dentro)
	bool BuscarConversacionActiva(const int &N);// Era privado pero lo necesitamos en Ficheros.h

	bool ListaDeConvVacia();

	void BorrarTodasLasConv();


	// Destructor //

	// Pre ={La instancia debe estar creada}
	// Post= {Destruye la instancia de la clase}
	// Complejidad: O(n*n)
    ~Quepassa ();
};

#endif/* QUEPASSA_H_ */
