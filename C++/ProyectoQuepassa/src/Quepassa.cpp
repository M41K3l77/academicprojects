#include "Quepassa.h"

Quepassa::Quepassa (){
	//cout << "Construyendo Quepassa por defecto" << endl;
	ContConv=0;
	NumeroMayorDeConversaciones=0;
}

Quepassa::Quepassa (Quepassa &Q){
	L=Q.L;
	Agenda=Q.Agenda;
	ContConv=Q.ContConv;
	NumeroMayorDeConversaciones=Q.NumeroMayorDeConversaciones;
}

void Quepassa::PutListaConv(ListaConv &Lista){
	L=Lista;
}

void Quepassa::PutAgenda(ListaPersona &A){
	Agenda=A;
}

void Quepassa::PutContadorDeConv(const int &N){
	ContConv=N;
}

void Quepassa::PutNumeroMayorDeConversaciones(const int &N){
	NumeroMayorDeConversaciones=N;
}

void Quepassa::GetListaConv(ListaConv &ListCo){
	ListCo=L;
}

ListaPersona Quepassa::GetAgenda(){
	return Agenda;
}

int Quepassa::GetContadorDeConv()const{
	return ContConv;
}

int Quepassa::GetNumeroMayorDeConversaciones()const{
	return NumeroMayorDeConversaciones;
}

bool Quepassa::BuscarPersona(const string &Pers, Persona &P){
	bool encontrado=false;
	if(!Agenda.estaVacia()){
		Persona PEnAgenda;
		P.PutNombre(Pers);
		Agenda.Inicio();
		while(!Agenda.finLista() && !encontrado){
			Agenda.consultar(PEnAgenda);
			if(P==PEnAgenda){
				encontrado=true;
				P=PEnAgenda;
			}
			else{
				Agenda.avanzar();
			}
		}
	}
	return encontrado;
}

bool Quepassa::InsertarPersonaOrden(Persona &P) {
	bool insertado=false;
	// Si la lista está vacia, lo inserta directamente.
	if(Agenda.estaVacia ()){
		Agenda.insertar(P);
		insertado=true;
	}
	else{
		// Variable para consultar los datos de la lista
		Persona dato;
		// Booleano para indicar que hemos encontrado la posicion donde insertar el nuevo dato.
		bool encontrado=false;
		// Movemos Punto de interes al inicio.
		Agenda.Inicio();
		bool iguales=false;// En caso de intentar introducir un contacto ya creado con == nombre
		// Condiciones para avanzar en la lista
		while(!Agenda.finLista() && !encontrado){
			Agenda.consultar(dato);
			if(P<dato){
				// Si la Persona a insertar es menor que la que se compara entoces insertamos
				encontrado=true;
				Agenda.insertar (P);
				insertado=true;
			}
			// Si son iguales, nos salimos de la lista sin hacer nada
			else if(P==dato){
				iguales=true;
				Agenda.Fin();
				Agenda.avanzar();
			}
			else{
				Agenda.avanzar();
			}
		}
		// Si al recorrer la lista(agenda) no encontramos ninguna menor, se insertaría al final
		// si solo si al recorrer la agenda no encontró ninguna persona igual(nombre)
		if(iguales==false && insertado==false){
			Agenda.insertar (P);
			insertado=true;
		}
	}
	Agenda.Inicio();
	return insertado;
}
// Se ha modificado para que en esta tercera fase tenga en cuenta el arbol de contactos
bool Quepassa::EliminarPersona(const string &Pers){
	bool borrado=false;
	if(!ArbolContactos.vacio() && !Agenda.estaVacia()){// !Agenda.estaVacia()
		Persona P;
		// Buscar a la persona en la Agenda y el arbol de contactos
		if(BuscarPersona(Pers, P)==true && BuscarPersonaEnArbol(Pers, P)==true){// BuscarPersona(Pers, P)
			// Si la encuentra la borra
			Agenda.borrar();
			ArbolContactos.borrar(P);// Agenda.borrar();
			borrado=true;
			if(borrado==true){
				///Dessactivar conversaciones en las que coincida pers con usuar o contacto
				if(!L.estaVacia()){
					if(!L.finLista()){
						Conversacion AuxC;
						int Num;// al estar el PI a una conv significa que estará activa
						        // y por tanto guardamos su numero para posteriormente volver a ella
						L.consultar(AuxC);
						Num=AuxC.GetNumConver();
						L.Inicio();
						L.consultar(AuxC);
						bool estado=true;
						estado=AuxC.EstadoConversacion();
						Persona U;
						Persona C;
						int N=0;// Numero de conversacion que desactivaremos(inicializada a 0
						while(!L.finLista() && estado==true){
							L.consultar(AuxC);
							U=AuxC.GetUsuario();
							C=AuxC.GetContacto();
							if(U==P || C==P){
								N=AuxC.GetNumConver();
								estado=AuxC.EstadoConversacion();
								// Si coincide un usuario o contacto en la conversacion
								// con el contacto borrado, se desactiva la conversación
								if(estado==true){
									DesactivarConvDeLista(N);
									// Al desactivar, se mueve la conversacion y el PI se mueve
									// automaticamente a una conv activa(la primera)
								}
								else{
									// Si el estado es false significa que llegamos a las conv desactivadas
									// y por tanto podemos salir del bucle
									L.Fin();
									L.avanzar();
								}
								if(!L.finLista()){
									// si no estamos fuera de la lista consultamos estado
									L.consultar(AuxC);
									estado=AuxC.EstadoConversacion();
								}
							}
							else{
								// En caso de no haber coincidencia con usuario o contacto
								// comprobamos estado de la conversacion y avanzamos
								L.consultar(AuxC);
								estado=AuxC.EstadoConversacion();
								L.avanzar();
							}
						}
						// Al acabar el bucle si estamos fuera de la lista
						// buscaremos la conversacion activa en la que estabamos
						if(L.finLista()){
							// En caso de que hubiera sido desactivada al borrar un contacto
							// nos situaremos en la ultima activa si la hubiere
							if(BuscarConversacionActiva(Num)==false){
								L.Inicio();
								bool Auxestado=true;
								L.consultar(AuxC);
								Auxestado=AuxC.EstadoConversacion();
								if(Auxestado==false){
									L.Fin();
									L.avanzar();
								}
							}
						}
					}
					else{// Se puede borrar si se quiere
						cout << "Todas las conversaciones estan desactivadas" << endl;
					}

				}
				else{// Se puede borrar si se quiere
					cout << "No hay conversaciones en la lista" << endl;
				}
			}
		}
	}
	return borrado;
}

bool Quepassa::BuscarConversacionNoActiva(const int &N){
	bool encontrada=false;
	if(!L.estaVacia()){
		L.Fin();
		int Num;
		Conversacion AuxC;
		while(!L.finLista() && !encontrada){
			L.consultar(AuxC);
			Num=AuxC.GetNumConver();
			if(Num==N){
				bool EstadoConv=true;
				EstadoConv=AuxC.EstadoConversacion();
				if(EstadoConv==false){
					encontrada=true;
				}
				else{
					L.Inicio();// Si encuentra la conv pero no está desactivada nos salimos
					L.retroceder();// directamente de la lista
				}
			}
			else{
				L.retroceder();
			}
		}
	}
	return encontrada;
}

bool Quepassa::BuscarConversacionActiva(const int &N){
	bool encontrada=false;
	if(!L.estaVacia()){
		L.Inicio();
		int Num;
		Conversacion AuxC;
		while(!L.finLista() && !encontrada){
			L.consultar(AuxC);
			Num=AuxC.GetNumConver();
			if(Num==N){
				bool EstadoConv=true;
				EstadoConv=AuxC.EstadoConversacion();
				if(EstadoConv==true){
					encontrada=true;
				}
				else{
					L.Fin();
					L.avanzar();
				}
			}
			else{
				L.avanzar();
			}
		}
	}
	return encontrada;
}

void Quepassa::MoverConversacion(Conversacion &C){// Conv tiene que venir desactivada
	// Este es el caso en el que teniamos solo una conv y para moverla, la
	// hemos copiado, borrado original, desactivado la copia e insertamos copia
	if(L.estaVacia()){
		L.insertar(C);
		L.avanzar();// PI queda fuera de la lista ya que no hay conv activa
	}
	else{
		Conversacion AuxC;
		bool estado=true;
		L.Fin();
		L.consultar(AuxC);
		estado=AuxC.EstadoConversacion();
		if(estado==true){
			L.avanzar();
			L.insertar(C);
			L.Inicio();// PI queda en la conversacion activa mas reciente(si la que se desactiva
		}              // es la conv actual
		else{
			bool encontrada=false;
			bool Auxestado=true;
			L.Fin();
			while(!L.finLista() && !encontrada){//!L.enInicio()
				L.consultar(AuxC);
				Auxestado=AuxC.EstadoConversacion();
				if(Auxestado==true){
					encontrada=true;
					L.avanzar();// se avanza para quedar sobre la ultima desactivada y mantener organizacion
					L.insertar(C);
					L.Inicio();// PI queda en la conversacion activa mas reciente(si la que se desactiva
				}              // es la conv actual
				else{
					L.retroceder();
				}
			}
			if(encontrada==false){// Estoy en el inicio y todas estan desactivadas
				L.Inicio();
				L.insertar(C);
				L.Fin();
				L.avanzar();
			}
		}
	}
}

bool Quepassa::DesactivarConvDeLista(const int &N){
	bool desactivada=false;
	if(!L.estaVacia()){
		// Estaremos en una conv activa
		if(!L.finLista()){
			Conversacion C;
			int Num=0;
			L.consultar(C);
			// Numero de la conv activa
			Num=C.GetNumConver();
			if(N==Num){
				// En caso de estar ya situados en la conv, la desact. y la movemos
				// con las desactivadas
				Persona Usuario;
				Persona Contacto;
				L.consultar(C);
				C.TerminarConversacion();
				L.borrar();
				// La movemos a las desactivadas, este metodo se encargara de poner el PI
				// sobre una activa si la hubiere
				MoverConversacion(C);
				desactivada=true;
			}
			else{
				// Si no estamos situados en la conv, habra que buscarla
				if(BuscarConversacionActiva(N)==true){
					Persona Usuario;
					Persona Contacto;
					L.consultar(C);
					C.TerminarConversacion();
					L.borrar();
					MoverConversacion(C);
					desactivada=true;
			    }
				// en caso de no encontrarla volvemos a la activa en la que estabamos
				else{
					BuscarConversacionActiva(Num);
				}
			}

		}
	}
	return desactivada;
}
// la borra solo si esta desactivada, si todas estan desactivadas el PI vuelve a NULL
// sino vuelve a la activada
bool Quepassa::BorrarConvSeleccionada(const int &N){
	bool borrada=false;
	if(!L.estaVacia()){
		if(L.finLista()){
			if(BuscarConversacionNoActiva(N)==true){
				L.borrar();
				borrada=true;
				ContConv=ContConv-1;
				L.Fin();
				L.avanzar();
			}
			else{
				L.Fin();
				L.avanzar();
			}
		}
		else{
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			Num=AuxC.GetNumConver();
			if(BuscarConversacionNoActiva(N)==true){
				L.borrar();
				borrada=true;
				ContConv=ContConv-1;
			}
			BuscarConversacionActiva(Num);
		}
	}
	return borrada;
}
// Solo cuando tengamos 10 conv y halla que insertar una nueva o en borrar empiz nomb o parte de el
bool Quepassa::BorrarConvAutomatica(){
	bool borrado=false;
	if(!L.estaVacia()){
		L.Fin();
		bool estado=true;
		Conversacion AuxC;
		L.consultar(AuxC);
		estado=AuxC.EstadoConversacion();
		if(estado==false){
			L.borrar();
			borrado=true;
		}
	}
	return borrado;
}

bool Quepassa::NuevaConversacion(const string &usuario, const string &contacto, const string &Msg){
	bool creada=false;
	Persona Contac;
	Persona Usuar;
	if(BuscarPersona(usuario, Usuar)==true && BuscarPersonaEnArbol(usuario, Usuar)==true){
		if(BuscarPersona(contacto, Contac)==true && BuscarPersonaEnArbol(contacto, Contac)==true){
			Mensaje M;
			Conversacion C;
			if(!L.estaVacia()){
				if(ContConv<10){
					if(L.finLista()){
						M.PutMsg(Msg);
						M.PutPersona(Usuar);
						C.PutMensaje(M);
						C.PutUsuario(Usuar);
						C.PutContacto(Contac);
						int NMDC=NumeroMayorDeConversaciones+1;
						C.PutNumConver(NMDC);
						NumeroMayorDeConversaciones=NMDC;
						L.insertar(C);
						ContConv=ContConv+1;
						creada=true;
					}
					else{// Este igual sobra por que apuntaran a la creada(se crean si o si)
						M.PutMsg(Msg);
						M.PutPersona(Usuar);
						C.PutMensaje(M);
						C.PutUsuario(Usuar);
						C.PutContacto(Contac);
						int NMDC=NumeroMayorDeConversaciones+1;
						C.PutNumConver(NMDC);
						NumeroMayorDeConversaciones=NMDC;
						L.insertar(C);
						ContConv=ContConv+1;
						creada=true;
					}
				}
				else{
					if(L.finLista()){// todas estan desactivadas
						BorrarConvAutomatica();
						M.PutMsg(Msg);
						M.PutPersona(Usuar);
						C.PutMensaje(M);
						C.PutUsuario(Usuar);
						C.PutContacto(Contac);
						int NMDC=NumeroMayorDeConversaciones+1;
						C.PutNumConver(NMDC);
						NumeroMayorDeConversaciones=NMDC;
						L.Inicio();
						L.insertar(C);
						ContConv=ContConv+1;
						creada=true;
					}
					else{
						int Num;
						Conversacion AuxC;// Conv a la que apunta el PI y Quizas deba volver a ella
						L.consultar(AuxC);
						Num=AuxC.GetNumConver();
						if(BorrarConvAutomatica()==true){
							M.PutMsg(Msg);
							M.PutPersona(Usuar);
							C.PutMensaje(M);
							C.PutUsuario(Usuar);
							C.PutContacto(Contac);
							int NMDC=NumeroMayorDeConversaciones+1;
							C.PutNumConver(NMDC);
							NumeroMayorDeConversaciones=NMDC;
							L.Inicio();
							L.insertar(C);
							ContConv=ContConv+1;
							creada=true;
						}
						else{
							BuscarConversacionActiva(Num);
						}
					}
				}
			}
			else{
				M.PutMsg(Msg);
				M.PutPersona(Usuar);
				C.PutMensaje(M);
				C.PutUsuario(Usuar);
				C.PutContacto(Contac);
				int NMDC=NumeroMayorDeConversaciones+1;
				C.PutNumConver(NMDC);
				NumeroMayorDeConversaciones=NMDC;
				L.insertar(C);
				ContConv=ContConv+1;
				creada=true;
			}
		}
	}
	return creada;
}

bool Quepassa::EnviarMensaje(const int &Nconv, const string &usuario, const string &Msg, const string &Permiso){
	bool enviado=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			Persona P;// Se usará para el supuesto usuario que quiere enviar el mensaje
			P.PutNombre(usuario);
			Persona auxP;// Se usará para el usuario de la conversacion
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa en caso de tener que volver a ella
			Num=AuxC.GetNumConver();
			// Buscamos conv a la que se le quiere enviar el mensaje
			if(BuscarConversacionActiva(Nconv)==true){
				L.consultar(AuxC);
				auxP=AuxC.GetUsuario();
				// Comparamos la persona que quiere enviar el mensaje(usuario) con los usuarios que
				// hay en la conversación(solo compara el nombre)
				if(P==auxP){
					// Confirmacion para el envio del mensaje
					if(ConfirmacionMensaje(Permiso)==true){
						Mensaje M;
						M.PutMsg(Msg);
						M.PutPersona(auxP);
						AuxC.PutMensaje(M);
						L.modificar(AuxC);
						enviado=true;
					}
					else{
						BuscarConversacionActiva(Num);
					}
				}
				else{
					BuscarConversacionActiva(Num);
				}
			}
			else{
				BuscarConversacionActiva(Num);
			}
		}
	}
	return enviado;
}
// Se supone que actuo como usuario en plan automatico
bool Quepassa::EnviarMensaje(const string &Msg, const string &Permiso){
	bool enviado=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			// Se pide confirmación del envio del mensaje
			if(ConfirmacionMensaje(Permiso)==true){
				Persona P;
				Mensaje M;
				Conversacion AuxC;
				L.consultar(AuxC);
				P=AuxC.GetUsuario();
				M.PutMsg(Msg);
				M.PutPersona(P);
				AuxC.PutMensaje(M);
				L.modificar(AuxC);// Se envia el mensaje
				enviado=true;
			}
		}
	}
	return enviado;
}

bool Quepassa::EnviarRespuesta(const int &Nconv, const string &contacto, const string &Respuesta, const string &Permiso){
	bool enviado=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			Persona P;// Se usará para el supuesto contacto que quiere enviar el mensaje
			P.PutNombre(contacto);
			Persona auxP;// Se usará para el contacto de la conversacion
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa en caso de tener que volver a ella
			Num=AuxC.GetNumConver();
			// Buscamos conv a la que se le quiere enviar la respuesta
			if(BuscarConversacionActiva(Nconv)==true){
				L.consultar(AuxC);
				auxP=AuxC.GetContacto();
				// Comparamos la persona que quiere enviar la respuesta(contacto) con los contactos que
				// hay en la conversación(solo compara el nombre)
				if(P==auxP){
					// Confirmacion para el envio de la respuesta
					if(ConfirmacionMensaje(Permiso)==true){
						Mensaje M;
						M.PutMsg(Respuesta);
						M.PutPersona(auxP);
						AuxC.PutMensaje(M);
						L.modificar(AuxC);
						enviado=true;
					}
					else{
						BuscarConversacionActiva(Num);
					}
				}
				else{
					BuscarConversacionActiva(Num);
				}
			}
			else{
				BuscarConversacionActiva(Num);
			}
		}
	}
	return enviado;
}

// Se supone que actuo como contacto en plan automatico.
bool Quepassa::EnviarRespuesta(const string &Msg, const string &Permiso){
	bool enviado=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			// Se pide confirmación del envio del mensaje
			if(ConfirmacionMensaje(Permiso)==true){
				Persona P;
				Mensaje M;
				Conversacion AuxC;
				L.consultar(AuxC);
				P=AuxC.GetContacto();
				M.PutMsg(Msg);
				M.PutPersona(P);
				AuxC.PutMensaje(M);
				L.modificar(AuxC);// Se envia la respuesta
				enviado=true;
			}
		}
	}
	return enviado;
}

void Quepassa::MostrarAgenda(){
	if(!Agenda.estaVacia()){
		Persona PEnAgenda;
		Agenda.Inicio();
		while(!Agenda.finLista()){
			Agenda.consultar(PEnAgenda);
			PEnAgenda.MostrarDatosPersona();
		    Agenda.avanzar();
		}
	}
	Agenda.Inicio();
}

void Quepassa::MostrarTodasLasConversacionesCompletas(){
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa a la que hay que volver
			Num=AuxC.GetNumConver();
			L.Inicio();
			while(!L.finLista()){
				L.consultar(AuxC);
				AuxC.MostrarConversacionCompleta();
				L.avanzar();
			}
			BuscarConversacionActiva(Num);
	    }
		else{
			// Si el PI esta fuera de la lista(todas estaran desactivadas)
			// el PI volverá a estar fuera de la lusta
			L.Inicio();
			Conversacion AuxC;
			while(!L.finLista()){
				L.consultar(AuxC);
				AuxC.MostrarConversacionCompleta();
				L.avanzar();
			}
		}
	}
}

void Quepassa::MostrarTodasLasConversacionesSimples(){
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa a la que hay que volver
			Num=AuxC.GetNumConver();
			L.Inicio();
			while(!L.finLista()){
				Conversacion AuxC;
				L.consultar(AuxC);
				AuxC.MostrarConversacionSimple();
				L.avanzar();
			}
			BuscarConversacionActiva(Num);
	    }
		else{
			// Si el PI esta fuera de la lista(todas estaran desactivadas)
			// el PI volverá a estar fuera de la lusta
			L.Inicio();
			while(!L.finLista()){
				Conversacion AuxC;
				L.consultar(AuxC);
				AuxC.MostrarConversacionSimple();
				L.avanzar();
			}
		}
	}
}

void Quepassa::MostrarUnaConversacion(const int &N){
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa a la que hay que volver
			Num=AuxC.GetNumConver();
			if(N!=Num){
				L.Inicio();
				int auxNum=0;
				bool encontrada=false;
				while(!L.finLista() && !encontrada){
					L.consultar(AuxC);
					// Numero de la conversacion que se va consultando
					auxNum=AuxC.GetNumConver();
					if(auxNum==N){
						// Si la encontramos, la mostramos y volvemos a la actual
						L.consultar(AuxC);
						AuxC.MostrarConversacionCompleta();
						encontrada=true;
						//BuscarConversacionActiva(Num);// creo que sobra
					}
					else{
						L.avanzar();
					}
				}
				BuscarConversacionActiva(Num);// En caso de pedir una conv que no exista y acabar fuera
				                              // de la lista volveriamos a la que estabamos
			}
			else{
				// Si ya estuvieramos en la conversacion solo hay que mostrarla
				L.consultar(AuxC);
				AuxC.MostrarConversacionCompleta();
			}
		}
		else{//mostrar la desactivada (PI Null)
			Conversacion AuxC;
			if(BuscarConversacionNoActiva(N)==true){
				L.consultar(AuxC);
				AuxC.MostrarConversacionCompleta();
			}
			L.Fin();
			L.avanzar();
		}
	}
}

void Quepassa::MostrarConvActual(){
	if(!L.estaVacia()){
		if(!L.finLista()){
			Conversacion AuxC;
			L.consultar(AuxC);
			AuxC.MostrarConversacionCompleta();
		}
	}
}

bool Quepassa::ConfirmacionMensaje(const string &Permiso){
	bool confirmacion=false;
	if(Permiso=="si"){
		confirmacion=true;
	}
	return confirmacion;
}
// Muestra conv de un contacto o parte de su nombre
// Los nombres que se comparan se pasaran a mayusculas y si hay coincidencia
// se mostra/n la/s conversacion/es, ademas cuando cojemos los nombres,
// los recortamos para que tengan la misma longitud que string Pers
// en ningun caso se modifican los atributos(los nombres)
// los atributos siguen manteniendo la/s mayuscula/s y/o minuscula/s que tuvieran
bool Quepassa::MostrarConvDePersonas(const string &Pers){
	bool mostrar=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			Num=AuxC.GetNumConver();// para volver a la conv activa en la que estamos
			string a=Pers;
			unsigned int Lon=a.length();
			for(unsigned int i = 0; i < Lon; i++){
				a[i] = toupper(a[i]);
			}
			L.Inicio();
			Persona U;
			Persona C;
			string u;
			string c;
			while(!L.finLista()){
				L.consultar(AuxC);
				U=AuxC.GetUsuario();
				C=AuxC.GetContacto();
				u=U.GetNombre();
				c=C.GetNombre();
				if(u.length()>=Lon){
					u=u.substr(0, Lon);
				}
				else{
					Lon=u.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					u[j] = toupper(u[j]);
				}
				Lon=a.length();
				if(c.length()>=Lon){
					c=c.substr(0, Lon);
				}
				else{
					Lon=c.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					c[j] = toupper(c[j]);
				}
				Lon=a.length();
				if((u.compare(a)==0) || (c.compare(a)==0)){
					AuxC.MostrarConversacionCompleta();
					mostrar=true;
					L.avanzar();
				}
				else{
					L.avanzar();
				}
			}
			BuscarConversacionActiva(Num);
		}
		else{
			Conversacion AuxC;
			string a=Pers;
			unsigned int Lon=a.length();
			for(unsigned int i = 0; i < Lon; i++){
				a[i] = toupper(a[i]);
			}
			L.Inicio();
			Persona U;
			Persona C;
			string u;
			string c;
			while(!L.finLista()){
				L.consultar(AuxC);
				U=AuxC.GetUsuario();
				C=AuxC.GetContacto();
				u=U.GetNombre();
				c=C.GetNombre();
				if(u.length()>=Lon){
					u=u.substr(0, Lon);
				}
				else{
					Lon=u.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					u[j] = toupper(u[j]);
				}
				Lon=a.length();
				if(c.length()>=Lon){
					c=c.substr(0, Lon);
				}
				else{
					Lon=c.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					c[j] = toupper(c[j]);
				}
				Lon=a.length();
				if((u.compare(a)==0) || (c.compare(a)==0)){
					AuxC.MostrarConversacionCompleta();
					mostrar=true;
					L.avanzar();
				}
				else{
					L.avanzar();
				}
			}
		}
	}
	return mostrar;
}
// Borra las conv de personas coincidentes con parte de un nombre
// Basicamente funciona como el metodo anterior MostrarConvDePersonas(const string &Pers)
// pero cuando encuentra una coincidencia borra la conversacion(si solo si está desactivada)
bool Quepassa::BorrarConvDePersonas(const string &Pers){
	bool borradas=false;
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			Num=AuxC.GetNumConver();// para volver a la conv activa en la que estamos
			string a=Pers;
			unsigned int Lon=a.length();
			for(unsigned int i = 0; i < Lon; i++){
				a[i] = toupper(a[i]);
			}
			L.Fin();
			Persona U;
			Persona C;
			string u;
			string c;
			L.consultar(AuxC);
			bool estado=AuxC.EstadoConversacion();
			while(!L.finLista() && estado==false){
				L.consultar(AuxC);
				U=AuxC.GetUsuario();
				C=AuxC.GetContacto();
				u=U.GetNombre();
				c=C.GetNombre();
				if(estado==false){
					// si estado=false significa que esta la conv desactivada
					if(!L.finLista()){
						estado=AuxC.EstadoConversacion();
					}
					if(u.length()>=Lon){
						u=u.substr(0, Lon);
					}
					else{
						Lon=u.length();
					}
					for(unsigned int j = 0; j < Lon; j++){
						u[j] = toupper(u[j]);
					}
					Lon=a.length();
					if(c.length()>=Lon){
						c=c.substr(0, Lon);
					}
					else{
						Lon=c.length();
					}
					for(unsigned int j = 0; j < Lon; j++){
						c[j] = toupper(c[j]);
					}
					Lon=a.length();
					if((u.compare(a)==0) || (c.compare(a)==0)){
						// si encuentra alguna coincidencia en la conv la borra
						L.borrar();
						borradas=true;
						if(L.finLista()){
							// Como vamos recorriendo la lista de atras hacia adelante,
							// al borrar podemos quedar fuera de la lista antes de tiempo
							// por lo que se vuelve a la lista con L.Fin()
							L.Fin();
						}
						else{
							L.retroceder();
						}
					}
					else{
						L.retroceder();
					}
				}
				else{
					L.retroceder();
				}
			}
			BuscarConversacionActiva(Num);
		}
		else{
			Conversacion AuxC;
			string a=Pers;
			unsigned int Lon=a.length();
			for(unsigned int i = 0; i < Lon; i++){
				a[i] = toupper(a[i]);
			}
			L.Fin();
			Persona U;
			Persona C;
			string u;
			string c;
			while(!L.finLista()){
				L.consultar(AuxC);
				U=AuxC.GetUsuario();
				C=AuxC.GetContacto();
				u=U.GetNombre();
				c=C.GetNombre();
				if(u.length()>=Lon){
					u=u.substr(0, Lon);
				}
				else{
					Lon=u.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					u[j] = toupper(u[j]);
				}
				Lon=a.length();
				if(c.length()>=Lon){
					c=c.substr(0, Lon);
				}
				else{
					Lon=c.length();
				}
				for(unsigned int j = 0; j < Lon; j++){
					c[j] = toupper(c[j]);
				}
				Lon=a.length();
				if((u.compare(a)==0) || (c.compare(a)==0)){
					L.borrar();
					borradas=true;
					if(L.finLista()){
						// Como vamos recorriendo la lista de atras hacia adelante,
						// al borrar podemos quedar fuera de la lista antes de tiempo
						// por lo que se vuelve a la lista con L.Fin()
						L.Fin();
					}
					else{
						L.retroceder();
					}
				}
				else{
					L.retroceder();
				}
			}
		}
	}
	return borradas;
}
// Metodo de la defensa del proyecto
// Muestra la conversacion con mas mensajes
void Quepassa::MostrarConvMasMsg(){
	if(!L.estaVacia()){
		if(!L.finLista()){
			int Num=0;
			Conversacion AuxC;
			L.consultar(AuxC);
			// Numero de la conversacion activa a la que hay que volver
			Num=AuxC.GetNumConver();
			ColaMsg Col;
			Mensaje dato;
			bool estado=true;
			int contadorAux=0;// Realmente es un mayor
			int numConv=0;
			L.Inicio();
			while(!L.finLista() && estado==true){
				L.consultar(AuxC);
				estado=AuxC.EstadoConversacion();
				if(estado==true){
					AuxC.GetMsgAlmacenados(Col);
					int contador=0;
					while(!Col.Vacia()){
						Col.Primero(dato);
						Col.Desencolar();
						contador=contador+1;
					}
					if(contador>contadorAux){
						contadorAux=contador;
						numConv=AuxC.GetNumConver();
					}
				}
				L.avanzar();
			}
			BuscarConversacionActiva(Num);// Para no perder el puntero antes de mostrar la conv
			if(numConv>0){
				MostrarUnaConversacion(numConv);
			}
	    }
	}
}

////// 3ª fase del proyecto //////

bool Quepassa::InsertarPersonaEnArbol(Persona &P){
	bool insertado=false;
	if(ArbolContactos.insertar(P)==true){
		insertado=true;
	}
	return insertado;
}

bool Quepassa::BorrarPersonaEnArbol(const string &P){
	bool borrado=false;
	if(!ArbolContactos.vacio()){
		Persona Pers;
		Pers.PutNombre(P);
		if(ArbolContactos.pertenece(Pers)==true){
			// Es un void, borramos
			ArbolContactos.borrar(Pers);
			borrado=true;
		}
	}
	return borrado;
}
// Busca y muestra un contacto en el arbol
bool Quepassa::BuscarPersonaEnArbol(const string &Pers, Persona &P){
	bool encontrado=false;
	if(!ArbolContactos.vacio()){
		P.PutNombre(Pers);
		if(ArbolContactos.pertenece(P)==true){
			encontrado=true;
			//ArbolContactos.mostrarDato(P);
		}
	}
	return encontrado;
}

void Quepassa::MostrarArbolContactos(){
	if(!ArbolContactos.vacio()){
		ArbolContactos.inOrden();
	}
}

bool Quepassa::ArbolDeContactosVacio(){
	return ArbolContactos.vacio();
}

void Quepassa::BorrarArbolContactos(){
	ArbolContactos.BorrarArbolCompleto();
}

void Quepassa::BorrarAgendaDeContactos(){
	Agenda.Inicio();
	while (!Agenda.estaVacia()){
		Agenda.borrar();
	}
}

bool Quepassa::ListaDeContactosVacia(){
	return Agenda.estaVacia();
}

void Quepassa::AvanzarEnLaAgenda(){
	Agenda.avanzar();
}

void Quepassa::InicioEnLaAgenda(){
	Agenda.Inicio();
}

bool Quepassa::FueraDeLaAgenda(){
	return Agenda.finLista();
}

void Quepassa::ConsultarPersonaEnAgenda(Persona &P){
	Agenda.consultar(P);
}

void Quepassa::CopiarArbolDeContactos(Arbol &A){
	ArbolContactos.CopiarArbolInOrden(A);
}

int Quepassa::NumeroConversacionActual(){
	int NumeroConvActual=0;

	if(!L.estaVacia()){
		if(!L.finLista()){
			Conversacion C;
			L.consultar(C);
			NumeroConvActual=C.GetNumConver();
		}
	}
	return NumeroConvActual;
}

bool Quepassa::ListaDeConvVacia(){
	return L.estaVacia();
}

// Solo para usar en Ficheros
void Quepassa::BorrarTodasLasConv(){
	if(!L.estaVacia()){
		L.Inicio();
		while(!L.estaVacia()){
			L.borrar();
		}
	}
}

Quepassa::~Quepassa (){
	//cout << "Destruyendo Quepassa" << endl;
	////// Todo lo de abajo sobra, no es necesario, ya se implementaron sus destructores///////
//	L.Inicio();// Necesario ya que para borrar la lista debe estar el puntero al principio
//	           // para borrar todos los nodos
//	while(!L.estaVacia()){
//		L.borrar();
//	}
//	Agenda.Inicio();// Necesario ya que para borrar la lista debe estar el puntero al principio
//    // para borrar todos los nodos
//	while(!Agenda.estaVacia()){
//		Agenda.borrar();
//	}
}
