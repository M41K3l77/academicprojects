#include "Pruebas.h"
#include <iostream>
using namespace std;


void Pruebas::PruebaClasePersona(){
	Persona h;
	Persona j;
	string a="JUAN";
	string b="juan";
	cout << "Introducimos los nombres JUAN y juan" << endl;
	h.PutNombre (a);
	j.PutNombre (b);
	cout << "Las comparaciones serán en mayusculas para evitar nombres iguales" << endl;
	if(h==j){
		cout << "los nombres son iguales" << endl;
	}
	else{
		cout << "Son nombres diferentes" << endl;
	}
	cout << h.GetNombre() << " " << j.GetNombre() << endl;
	cout << "Creamos una persona con su nombre, apellido, telefono y email" << endl;
	Persona k("Manuel", "Garcia", 555333222, "micorreo@loquesea.es");
	cout << "-------------Datos completos--------------" << endl;
	k.MostrarDatosPersona();
	cout << "-----------------Nombre-------------------" << endl;
	k.MostrarNombrePersona();
}

void Pruebas::PruebaClaseMensaje(){
	Mensaje h;
	Mensaje j;
	Persona Manuel("Manuel", "Garcia", 555333222, "micorreo@loquesea.es");
	cout << "El mensaje original tiene mas de 50 caracteres" << endl;
	string MensajeOriginal="Esto es una prueba90123456789012345678901234567890esto sobra";
	h.PutMsg (MensajeOriginal);
	h.PutPersona (Manuel);
	cout << "Mensaje original: " << MensajeOriginal << endl;
	cout << "Se muestran solo los 50 primeros caracteres como se requiere en el proyecto" << endl;
	h.Mostrar();
	j=h;
	cout << "Se muestra a la persona asociada al mensaje" << endl;
	j.GetPersona().MostrarDatosPersona();
}

void Pruebas::PruebaClaseColaMsg(){
	ColaMsg C1;
	ColaMsg C2;
	Mensaje h;
	Mensaje j;
	Persona Manuel("Manuel", "Garcia", 555333222, "micorreo@loquesea.es");
	h.PutMsg ("Esto es una prueba90123456789012345678901234567890esto sobra");
	h.PutPersona (Manuel);
	C1.Encolar(h);
	C1.Primero(j);
	C2=C1;
	cout << "Se crea un mensaje, se encola en una cola, a su vez esta primera cola se copia a una segunda"
			" " "y se muestra el mensaje" << endl;
	cout << "----------------mensaje en una cola de mensajes-------------------" << endl;
	cout << "Mensaje en la primera cola" << endl;
	j.Mostrar();
	cout << endl;
	C2.Primero(j);
	cout << "Prueba ampliacion del operador = :" << endl;
	cout << "Mensaje en la segunda cola" << endl;
	j.Mostrar();
	cout << endl;
	C1.Desencolar();
	C2.Desencolar();
	cout << "Se eliminaron los mensajes" << endl;
	if(C1.Vacia() && C1.Vacia()){
		cout << "---------------Las colas de mensajes estan vacias-------------" << endl;
	}
}

void Pruebas::PruebaClaseConversacion(){
	Conversacion X;
	int NumConver=674;
	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Persona Contacto("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Mensaje U;
	Mensaje C;
	U.PutMsg ("Como estas juan?");
	U.PutPersona (Usuario);
	C.PutMsg ("Vamos tirando Manuel!");
	C.PutPersona (Contacto);
	X.PutMensaje(U);
	X.PutMensaje(C);

	X.PutNumConver (NumConver);
	X.PutUsuario (Usuario);
	X.PutContacto (Contacto);
	cout << "Se muestra una conversacion simple activada: " << endl;
	X.MostrarConversacionSimple ();
	cout << "Se muestra una conversacion completa activada: " << endl;
	X.MostrarConversacionCompleta ();
	X.TerminarConversacion ();
	cout << "Se muestra una conversacion simple desactivada: " << endl;
	X.MostrarConversacionSimple ();
	cout << "Se muestra una conversacion completa desactivada: " << endl;
	X.MostrarConversacionCompleta ();
}

void Pruebas::PruebaClaseListaPersona(){
	ListaPersona Agenda;
	Persona R;
	// Se crean un usuario y tres contactos para la prueba
	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Persona Contacto("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Persona Contacto2("Pedro", "Izquierdo", 555766543, "micorreo3@loquesea.es");
	Persona Contacto3("Almudena", "Cerezo", 555236589, "micorreo4@loquesea.es");
	// Se prueban diferentes metodos de la clase ListaPersona
	Agenda.insertar(Usuario);
	Agenda.insertar(Contacto);
	Agenda.insertar(Contacto2);
	Agenda.insertar(Contacto3);
	cout << "Los contactos no se muestran segun el orden de insercion"
			" " "para poder provar los diferentes metodos de la clase" << endl;
	cout << "Lista de contactos de la Agenda: " << endl;
	cout << endl;
	Agenda.avanzar();
	Agenda.consultar(R);
	R.MostrarDatosPersona();
	Agenda.Fin();
	Agenda.consultar(R);
	R.MostrarDatosPersona();
	Agenda.retroceder();
	Agenda.consultar(R);
	R.MostrarDatosPersona();
	Agenda.Inicio();
	Agenda.consultar(R);
	R.MostrarDatosPersona();
}

void Pruebas::PruebaClaseListaConv(){
	ListaConv lista;
	Conversacion X;
	Conversacion Y;
	Conversacion Z;
	Conversacion R;
	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Persona Contacto("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Persona Contacto2("Pedro", "Izquierdo", 555766543, "micorreo3@loquesea.es");
	Persona Contacto3("Almudena", "Cerezo", 555236589, "micorreo4@loquesea.es");
	Mensaje U;
	Mensaje C;
	Mensaje C2;
	Mensaje C3;

////-Primera Converacion-////
	U.PutMsg ("Como estas juan?");
	U.PutPersona (Usuario);
	C.PutMsg ("Vamos tirando Manuel!");
	C.PutPersona (Contacto);
	ColaMsg MsgAlmacenados;
	MsgAlmacenados.Encolar(U);
	MsgAlmacenados.Encolar(C);
	X.PutMsgAlmacenados (MsgAlmacenados);
	X.PutNumConver (1);
	X.PutUsuario (Usuario);
	X.PutContacto (Contacto);
	lista.insertar(X);
////-Segunda Converacion-////
	U.PutMsg ("Donde andas Pedro?");
	U.PutPersona (Usuario);
	C2.PutMsg ("Estamos en el bar de abajo!");
	C2.PutPersona (Contacto2);
	ColaMsg MsgAlmacenados2;
	MsgAlmacenados2.Encolar(U);
	MsgAlmacenados2.Encolar(C2);
	Y.PutMsgAlmacenados (MsgAlmacenados2);
	Y.PutNumConver (2);
	Y.PutUsuario (Usuario);
	Y.PutContacto (Contacto2);
	lista.insertar(Y);
////-Tercera Converacion-////
	U.PutMsg ("Almudena, mandame el fichero!");
	U.PutPersona (Usuario);
	C3.PutMsg ("Mejor otro dia!");
	C3.PutPersona (Contacto3);
	ColaMsg MsgAlmacenados3;
	MsgAlmacenados3.Encolar(U);
	MsgAlmacenados3.Encolar(C3);
	Z.PutMsgAlmacenados (MsgAlmacenados3);
	Z.PutNumConver (3);
	Z.PutUsuario (Usuario);
	Z.PutContacto (Contacto3);
	lista.insertar(Z);
////-Movimiento del PI y consulta-////
	cout << "Prueba general de ListaConv: " << endl;
	cout << endl;
	cout << endl;
	cout << "Lista de tres conversaciones: " << endl;
	cout << endl;
	lista.consultar(R);
	R.MostrarConversacionCompleta ();
	lista.avanzar();
	lista.consultar(R);
	R.MostrarConversacionCompleta ();
	lista.Fin();
	lista.consultar(R);
	R.MostrarConversacionCompleta ();
	cout << "------------------------------------" << endl;
	lista.Inicio();
	lista.borrar();
	lista.borrar();
	lista.borrar();
	if(!lista.estaVacia()){
		R.MostrarConversacionCompleta ();
	}
	else{
		cout << "Se eliminaron las tres conversaciones" << endl;
	}
}

void Pruebas::PruebaClaseQuepassa(){
	Quepassa Q;
	//ListaPersona Agenda;
	string Permiso="si";
	// Introducimos contactos en la agenda
	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Q.InsertarPersonaOrden(Usuario);
	Q.InsertarPersonaEnArbol(Usuario);
	Persona Contacto1("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Q.InsertarPersonaOrden(Contacto1);
	Q.InsertarPersonaEnArbol(Contacto1);
	Persona Contacto2("Pedro", "Izquierdo", 555766543, "micorreo3@loquesea.es");
	Q.InsertarPersonaOrden(Contacto2);
	Q.InsertarPersonaEnArbol(Contacto2);
	Persona Contacto3("Almudena", "Cerezo", 555236589, "micorreo4@loquesea.es");
	Q.InsertarPersonaOrden(Contacto3);
	Q.InsertarPersonaEnArbol(Contacto3);
	Persona Contacto4("Manuel", "Co", 555754589, "micorreo5@loquesea.es");
	Q.InsertarPersonaOrden(Contacto4);
	Q.InsertarPersonaEnArbol(Contacto4);
	Persona Contacto5("Andres", "Cezo", 552376589, "micorreo6@loquesea.es");
	Q.InsertarPersonaOrden(Contacto5);
	Q.InsertarPersonaEnArbol(Contacto5);
	Persona Contacto6("Jeromin", "Cz", 552777589, "micorreo7@loquesea.es");
	Q.InsertarPersonaOrden(Contacto6);
	Q.InsertarPersonaEnArbol(Contacto6);
	Persona Contacto7("JuanPedro", "As", 512877589, "micorreo8@loquesea.es");
	Q.InsertarPersonaOrden(Contacto7);
	Q.InsertarPersonaEnArbol(Contacto7);
	cout << "A continuacion se muestra la agenda con los contactos ordenado: " << endl;
	cout << endl;
	//Q.MostrarAgenda();
	Q.MostrarArbolContactos();
	///////////////////////////Conversacion

	Q.NuevaConversacion("Manuel", "Juan", "como estas, Juan?");
	Q.NuevaConversacion("Juan", "Pedro", "como estas, pedro?");
	Q.NuevaConversacion("Pedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Almudena", "Manuel", "Donde estas, manuel?");
	Q.NuevaConversacion("Manuel", "Juan", "como estas, Juan?");
	Q.NuevaConversacion("Juan", "Pedro", "como estas, pedro?");
	Q.NuevaConversacion("JuanPedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Almudena", "Manuel", "Donde estas, manuel?");
	Q.NuevaConversacion("Pedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Jeromin", "Almudena", "Donde estas, Almudena?");
	Q.EnviarMensaje(2, "Juan", "Vamos al cine?", Permiso);
	Q.EnviarMensaje(4, "Almudena", "Manuel, traeme las pilas!", Permiso);
	Q.EnviarRespuesta(1, "Juan", "Vamos tirando!", Permiso);
	Q.EnviarRespuesta(3, "Almudena", "Estoy en la plaza!", Permiso);
	cout << "A continuacion se muestran 10 conversaciones creadas: " << endl;
	cout << endl;
	Q.MostrarTodasLasConversacionesCompletas();
	cout << "------------------------------------------------" << endl;
	cout << "A continuacion se muestra la conversacion actual que será la 3 la ultima que se usó: " << endl;
	Q.MostrarConvActual();
	cout << "------------------------------------------------" << endl;
	if(Q.NuevaConversacion("Almudena", "Juan", "quedamos a las 03:00, juan?")==false){
		cout << "no se pudo crear por que están todas activas" << endl;
		cout << "como podemos comprobar la conversación actual no ha cambiado" << endl;
		Q.MostrarConvActual();
	}
	cout << "------------------------------------------------" << endl;
	cout << "Desactivamos la conversacion 5" << endl;
	cout << "E intentamos crear la nueva conversacion" << endl;
	if(Q.DesactivarConvDeLista(5)==true){
		Q.MostrarTodasLasConversacionesSimples();
		cout << "Vemos que la conversacion 5 quedo desactivada y movida al final de la lista" << endl;
		cout << "------------------------------------------------" << endl;
		if(Q.NuevaConversacion("Almudena", "Juan", "quedamos a las 03:00?")==true){
			Q.MostrarTodasLasConversacionesSimples();
			cout << "------------------------------------------------" << endl;
			Q.MostrarConvActual();
			cout << "Vemos que la conversacion actual ahora es la 11 que es la creada nueva" << endl;
		}
	}
	cout << "------------------------------------------------" << endl;
	cout << "desactivamos otra conversación" << endl;
	Q.DesactivarConvDeLista(9);
	Q.DesactivarConvDeLista(7);
	Q.MostrarTodasLasConversacionesSimples();
	cout << "Como podemos apreciar la conversacion desactivada mas atigua (9) queda al final de la lista" << endl;
	cout << "------------------------------------------------" << endl;
	cout << "Eliminamos contacto Manuel" << endl;
	cout << "Recordar que ya habia dos conversaciones desactivadas" << endl;
	Q.EliminarPersona("Manuel");
	Q.MostrarTodasLasConversacionesCompletas();
	cout << "Al eliminar un contacto de la agenda todas sus conversaciones ya sea usuario o contacto se desactivan" << endl;
	cout << "------------------------------------------------" << endl;
	cout << "vamos a borrar una conversacion desactivada(la 8): " << endl;
	if(Q.BorrarConvSeleccionada(8)==true){
		Q.MostrarTodasLasConversacionesSimples();
		cout << "Se ha borrado la conversacion 8" << endl;
	}
	cout << "------------------------------------------------" << endl;
	cout << "Vamos a mostrar las conversaciones introduciendo parte de un nombre (Jua)" << endl;
	Q.MostrarConvDePersonas("Jua");
	cout << "Como podemos comprobar solo muestra las conversaciones en las que participan"
			" " "usuarios y/o contactos que empiecen por `Jua´" << endl;
	cout << "por lo que no aparece la de Jeromin" << endl;
	cout << "para estar seguros mostraremos todas las conversaciones" << endl;
	Q.MostrarTodasLasConversacionesSimples();
	cout << "------------------------------------------------" << endl;
	cout << "A continuacion borraremos conversaciones usando parte de un nombre (Jua)" << endl;
	Q.DesactivarConvDeLista(10);
	Q.DesactivarConvDeLista(6);
	Q.DesactivarConvDeLista(12);
	cout << "Para ello desactivaremos la 10 y la 6 y las mostramos" << endl;
	Q.MostrarTodasLasConversacionesSimples();
	cout << "Ahora procedemos a borrar usando parte de un nombre" << endl;
	Q.BorrarConvDePersonas("Jua");
	Q.MostrarTodasLasConversacionesSimples();
	cout << "Como se puede ver se han borrado las conversaciones desactivadas donde participavan"
			" " "Juan y Juanpedro pero no la de jeromin" << endl;
	Q.DesactivarConvDeLista(11);
	Q.DesactivarConvDeLista(3);
	Q.BorrarConvDePersonas("Al");
	Q.MostrarTodasLasConversacionesSimples();
}

void Pruebas::PruebaArbolContactos(){

	Quepassa Q;
	Persona P;

	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Persona Contacto1("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Persona Contacto2("Pedro", "Izquierdo", 555766543, "micorreo3@loquesea.es");
	Persona Contacto3("Almudena", "Cerezo", 555236589, "micorreo4@loquesea.es");
	Persona Contacto4("Manuel", "Co", 555754589, "micorreo5@loquesea.es");
	Persona Contacto5("Andres", "Cezo", 552376589, "micorreo6@loquesea.es");
	Persona Contacto6("Jeromin", "Cz", 552777589, "micorreo7@loquesea.es");
	Persona Contacto7("JuanPedro", "As", 512877589, "micorreo8@loquesea.es");
	Q.InsertarPersonaOrden(Usuario);
	Q.InsertarPersonaEnArbol(Usuario);
	Q.InsertarPersonaOrden(Contacto1);
	Q.InsertarPersonaEnArbol(Contacto1);
	Q.InsertarPersonaOrden(Contacto2);
	Q.InsertarPersonaEnArbol(Contacto2);
	Q.InsertarPersonaOrden(Contacto3);
	Q.InsertarPersonaEnArbol(Contacto3);
	Q.InsertarPersonaOrden(Contacto4);
	Q.InsertarPersonaEnArbol(Contacto4);
	Q.InsertarPersonaOrden(Contacto5);
	Q.InsertarPersonaEnArbol(Contacto5);
	Q.InsertarPersonaOrden(Contacto6);
	Q.InsertarPersonaEnArbol(Contacto6);
	Q.InsertarPersonaOrden(Contacto7);
	Q.InsertarPersonaEnArbol(Contacto7);

	Q.MostrarArbolContactos();
	cout << "------------------------------------------------" << endl;
	cout << "Eliminamos el contacto Almudena:" << endl;
	Q.EliminarPersona("almudena");
	Q.BorrarPersonaEnArbol("almudena");
	Q.MostrarArbolContactos();
	cout << "------------------------------------------------" << endl;
	cout << "Mostramos un contacto(Manuel):" << endl;
	if(Q.BuscarPersonaEnArbol("Manuel",P)==true){
		P.MostrarDatosPersona();
	}
}

void Pruebas::PruebaFicheros(){
	Quepassa Q;
	Quepassa R;
	Ficheros F;

	string Permiso="si";
	Persona Usuario("Manuel", "Garcia", 555333222, "micorreo1@loquesea.es");
	Persona Contacto1("Juan", "Esparraguero", 555111222, "micorreo2@loquesea.es");
	Persona Contacto2("Pedro", "Izquierdo", 555766543, "micorreo3@loquesea.es");
	Persona Contacto3("Almudena", "Cerezo", 555236589, "micorreo4@loquesea.es");
	Persona Contacto4("Manuel", "Co", 555754589, "micorreo5@loquesea.es");
	Persona Contacto5("Andres", "Cezo", 552376589, "micorreo6@loquesea.es");
	Persona Contacto6("Jeromin", "Cz", 552777589, "micorreo7@loquesea.es");
	Persona Contacto7("JuanPedro", "As", 512877589, "micorreo8@loquesea.es");
	Q.InsertarPersonaOrden(Usuario);
	Q.InsertarPersonaEnArbol(Usuario);
	Q.InsertarPersonaOrden(Contacto1);
	Q.InsertarPersonaEnArbol(Contacto1);
	Q.InsertarPersonaOrden(Contacto2);
	Q.InsertarPersonaEnArbol(Contacto2);
	Q.InsertarPersonaOrden(Contacto3);
	Q.InsertarPersonaEnArbol(Contacto3);
	Q.InsertarPersonaOrden(Contacto4);
	Q.InsertarPersonaEnArbol(Contacto4);
	Q.InsertarPersonaOrden(Contacto5);
	Q.InsertarPersonaEnArbol(Contacto5);
	Q.InsertarPersonaOrden(Contacto6);
	Q.InsertarPersonaEnArbol(Contacto6);
	Q.InsertarPersonaOrden(Contacto7);
	Q.InsertarPersonaEnArbol(Contacto7);

	Q.NuevaConversacion("Manuel", "Juan", "como estas, Juan?");
	Q.NuevaConversacion("Juan", "Pedro", "como estas, pedro?");
	Q.NuevaConversacion("Pedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Almudena", "Manuel", "Donde estas, manuel?");
	Q.NuevaConversacion("Manuel", "Juan", "como estas, Juan?");
	Q.NuevaConversacion("Juan", "Pedro", "como estas, pedro?");
	Q.NuevaConversacion("JuanPedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Almudena", "Manuel", "Donde estas, manuel?");
	Q.NuevaConversacion("Pedro", "Almudena", "como estas, almudena?");
	Q.NuevaConversacion("Jeromin", "Almudena", "Donde estas, Almudena?");
	Q.EnviarMensaje(2, "Juan", "Vamos al cine?", Permiso);
	Q.EnviarMensaje(4, "Almudena", "Manuel, traeme las pilas!", Permiso);
	Q.EnviarRespuesta(1, "Juan", "Vamos tirando!", Permiso);
	Q.EnviarRespuesta(3, "Almudena", "Estoy en la plaza!", Permiso);
	F.GuardarDatosEnElFicheroContactos(Q,  "Contactos.txt");
	F.GuardarDatosEnElFicheroMensajes(Q,  "Mensajes.txt");
	F.CargarDatosDelFicheroContactos("Contactos.txt", R);
	F.CargarDatosDelFicheroMensajes("Mensajes.txt", R);
	cout << "Se ha creado Q(Quepassa) con una serie de conversaciones y contactos y se han guardado "
			" " "en los *.txt correspondientes y a continuacion desde estos ficheros,"
					" " "se cargan en R(Quepassa)" << endl;
	cout << "------------------------------------------------" << endl;
	R.MostrarArbolContactos();
	cout << "------------------------------------------------" << endl;
	R.MostrarTodasLasConversacionesCompletas();
}

int Pruebas::MenuPrueba(){
	int opcion;
	do {
		cout << "--------------------------------------------------------------" << endl;
		cout << "1:  PruebaClasePersona()     " << endl;
		cout << "2:  PruebaClaseMensaje()     " << endl;
		cout << "3:  PruebaClaseColaMsg()    " << endl;
		cout << "4:  PruebaClaseConversacion()     " << endl;
		cout << "5:  PruebaClaseListaPersona()      " << endl;
		cout << "6:  PruebaClaseListaConv()    " << endl;
		cout << "7:  PruebaClaseQuepassa()         " << endl;
		cout << "8:  PruebaArbolContactos()         " << endl;
		cout << "9:  PruebaFicheros()         " << endl;

		cout << "10: Terminar             " << endl;
		cout << "--------------------------------------------------------------" << endl;

		cin >> opcion;

	} while ((opcion < 0) || (opcion > 10));

	return opcion;
}
void Pruebas::TodasLasPruebas(){
	cout << "Pruebas de las clases y sus métodos del programa Quepassa" << endl;
	cout << "_________________________________________________________" << endl;
    bool fin = false;
	int opcion = 0;
	while (!fin) {
		opcion = MenuPrueba();
		cin.ignore();
		switch (opcion) {
		case 1:
			PruebaClasePersona();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 2:
			PruebaClaseMensaje();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 3:
			PruebaClaseColaMsg();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 4:
			PruebaClaseConversacion();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 5:
			PruebaClaseListaPersona();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 6:
			PruebaClaseListaConv();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 7:{
			PruebaClaseQuepassa();
			cout << "--------------------------------------------------------------" << endl;
		}
			break;
		case 8:
			PruebaArbolContactos();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 9:
			PruebaFicheros();
			cout << "--------------------------------------------------------------" << endl;
			break;
		case 10:
			// Final del programa
			fin = true;
			break;
		}
	}

}
