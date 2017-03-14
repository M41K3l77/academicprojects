/**
*  arbol.cpp
*  Implementación de la clase Árbol Binario de Búsqueda
*  author   Profesores de la asignatura EDI
*  Curso 10/11
*  Revisado en: Curso 11/12
*/

#include "ArbolContactos.h"

/**
* Constructor por defecto. Inicializa un objeto de tipo Arbol
* \param "" No recibe parametros
* \return Devuelve un objeto de tipo Arbol inicializado
*/
Arbol::Arbol(){
    esVacio = true;
    hIzq = NULL;
    hDer = NULL;
}

/**
* Constructor parametrizado. Inicializa un objeto de tipo Arbol
* \param hIzq Hijo izquierdo del árbol a crear
* \param dato dato a insertar en la raíz
* \param hDer Hijo derecho del árbol a crear
* \return Devuelve un objeto de tipo Arbol inicializado
*/
Arbol::Arbol(Arbol *hIzq,TipoDatoArbol& dato,Arbol *hDer){
    esVacio = false;
    this->datoRaiz=dato;
    this->hIzq = hIzq;
    this->hDer = hDer;
}

/**
* \return Devuelve un objeto Arbol con el subárbol izquierdo
*/
Arbol *Arbol::hijoIzq(){
    return hIzq;
}

/**
* \return Devuelve un objeto Arbol con el subárbol derecho
*/
Arbol *Arbol::hijoDer(){
    return hDer; // devuelve el puntero al subarbol
}

/**
* \return Devuelve el valor almacenado en la raíz
*/
TipoDatoArbol& Arbol::raiz(){
    return datoRaiz;
}

/**
* \return Devuelve true si el árbol está vacío
*/
bool Arbol::vacio(){
    return esVacio;
}

/**
* Inserta el valor dato en el Árbol Binario de Búsqueda
* \param dato Valor a insertar
* \return Retorna true si se insertó correctamente
*/
bool Arbol::insertar(TipoDatoArbol& dato){

    bool resultado=true;
    if (vacio()) {
        datoRaiz = dato;
		esVacio = false;
	}
    else
    {
        if (datoRaiz != dato)
        {
            Arbol *aux;
            if ((dato)<(datoRaiz))
            {
                if ((aux=hijoIzq())==NULL)
                    hIzq = aux = new Arbol();
            }
            else
            {
                if ((aux=hijoDer())==NULL)
                    hDer = aux = new Arbol();
            }
            resultado = aux->insertar(dato);
        }
        else
            resultado=false;
    }

    return resultado;
}


/**
* Elimina el nodo que contenga el valor dato
* \param dato Valor a eliminar
* \return No devuelve nada
*/
void Arbol::borrar(TipoDatoArbol& dato){
    if (!vacio()){
		if (dato<datoRaiz)
			hIzq = hIzq->borrarOrden(dato);
        else
			if (dato>datoRaiz)
				hDer = hDer->borrarOrden(dato);
            else //En este caso el dato es el datoRaiz
            {
                if (hIzq==NULL && hDer==NULL)
                {
                    esVacio = true;
                }
                else
                    borrarOrden(dato);
            }
    }
}

/**
* Función privada auxiliar para realizar el borrado de un nodo. Reestructura el árbol tras el borrado
* \param dato Valor a eliminar
* \return Devuelve el árbol resultado del borrado
*/
Arbol *Arbol::borrarOrden(TipoDatoArbol& dato){

    TipoDatoArbol datoaux;
    Arbol *retorno=this, *aborrar, *candidato, *antecesor;

    if (!vacio())
    {
		if (dato<datoRaiz)  {
		    if (hIzq != NULL)
			hIzq = hIzq->borrarOrden(dato);
		}
		else
			if (dato>datoRaiz) {
			    if (hDer != NULL)
				hDer = hDer->borrarOrden(dato);
			}
            else {
                aborrar=this;
                if ((hDer==NULL)&&(hIzq==NULL))
                { /*si es hoja*/
                    delete aborrar;
                    retorno=NULL;
                }
                else
                {
                    if (hDer==NULL)
                    { /*Solo hijo izquierdo*/
                        aborrar=hIzq;
                        datoaux=datoRaiz;
                        datoRaiz=hIzq->raiz();
                        hIzq->datoRaiz = datoaux;
                        hIzq=hIzq->hijoIzq();
                        hDer=aborrar->hijoDer();

                        retorno=this;
                    }
                    else
                        if (hIzq==NULL)
                        { /*Solo hijo derecho*/
                            aborrar=hDer;
                            datoaux=datoRaiz;
                            datoRaiz=hDer->raiz();
                            hDer->datoRaiz = datoaux;
                            hDer=hDer->hijoDer();
                            hIzq=aborrar->hijoIzq();

                            retorno=this;
                        }
                        else
                        { /* Tiene dos hijos */
                            candidato = hijoIzq();
                            antecesor = this;
                            while (candidato->hijoDer())
                            {
                                antecesor = candidato;
                                candidato = candidato->hijoDer();
                            }

                            /*Intercambio de datos de candidato*/
                            datoaux = datoRaiz;
                            datoRaiz = candidato->raiz();
                            candidato->datoRaiz=datoaux;
                            aborrar = candidato;
                            if (antecesor==this)
                                hIzq=candidato->hijoIzq();
                            else
                                antecesor->hDer=candidato->hijoIzq();
                        } //Eliminar solo ese nodo, no todo el subarbol
                    aborrar->hIzq=NULL;
                    aborrar->hDer=NULL;
                    delete aborrar;
                }
            }
    }
    return retorno;
}

/**
* Destructor por defecto. Destruye un objeto de tipo Arbol
* \param "" No recibe parametros
* \return No devuelve nada
*/
Arbol::~Arbol(){

    Arbol *aux;
    //cout << endl<<"Paso por el destructor"<< endl;
    if (!vacio()){
        if (aux=hijoIzq())
            delete aux;
        if (aux=hijoDer())
            delete aux;

        esVacio = true;
    }
}




/**
* Muestra por pantalla los elementos del árbol realizando un recorrido InOrden
* \param "" No recibe parametros
* \return No devuelve nada
*/

void Arbol::inOrden(){  // izd, raid, derecho

    Arbol *aux;
    if (!vacio()){
        if (aux = hijoIzq())
            aux->inOrden();

        //cout << datoRaiz <<" - "; // No es un entero
        datoRaiz.MostrarDatosPersona();

        if (aux = hijoDer())
            aux->inOrden();
    }
}


/**
* Muestra por pantalla los elementos del árbol realizando un recorrido PreOrden
* \param "" No recibe parametros
* \return No devuelve nada
*/

void Arbol::preOrden(){
    Arbol *aux;
    if (!vacio())
    {
    	//cout << datoRaiz <<" - "; // No es un entero
    	datoRaiz.MostrarDatosPersona();

        if (aux = hijoIzq())
            aux->inOrden();

        if (aux = hijoDer())
            aux->inOrden();
    }
}

/**
* Muestra por pantalla los elementos del árbol realizando un recorrido PostOrden
* \param "" No recibe parametros
* \return No devuelve nada
*/

void Arbol::postOrden(){
    Arbol *aux;
    if (!vacio())
    {
        if (aux = hijoIzq())
            aux->inOrden();

        if (aux = hijoDer())
            aux->inOrden();

        //cout << datoRaiz <<" - "; // No es un entero
        datoRaiz.MostrarDatosPersona();
    }
}


/**
* Indica si el valor dato existe en el Árbol Binario de Búsqueda
* \param dato Valor a Buscar
* \return Devuelve true si encuentra un nodo con ese valor
*/
bool Arbol::pertenece (TipoDatoArbol& dato){

    Arbol *aux;
    bool encontrado=false;
    if (!vacio()){
    	if(dato==raiz()){
    		dato=raiz();// Añadido ya que supongo que son iguales si coincide solo el nombre
    		encontrado=true;// por lo que necesito pasar el resto de los datos a dato(apellido, telefono...)
    	}
    	else{
        	if(dato<raiz()){
        			aux=hijoIzq();
        	}
        	else{
            		aux=hijoDer();
        	}
        	if(aux!=NULL){
        		encontrado=aux->pertenece (dato);
        	}
    	}
    }
    return encontrado;
}


//  profundidad de un árbol: profundidad de la hoja más profunda.
int Arbol::profundidad(){
    Arbol *aux;
    int resultado=0, pIz=0, pDer=0;

    if(!vacio()){
    	if(aux=hijoIzq()){
    		pIz=aux->profundidad();
    	}
    	if(aux=hijoDer()){
    		pDer=aux->profundidad();
    	}
    	if(pIz>pDer){
    		resultado=pDer+1;
    	}
    	else{
    		resultado=pIz+1;
    	}
    }
    return resultado;
}

void Arbol::mostrarDato(TipoDatoArbol& dato){
    Arbol *aux;
    //bool encontrado=false;
    if (!vacio()){
    	if(dato==raiz()){
    		//encontrado=true;
    		dato.MostrarDatosPersona();
    	}
    	else{
        	if(dato<raiz()){
        			aux=hijoIzq();
        	}
        	else{
            		aux=hijoDer();
        	}
        	if(aux!=NULL){
        		aux->mostrarDato (dato);// encontrado=
        	}
    	}
    }
    //return encontrado;
}

void Arbol::BorrarArbolCompleto(){
    Arbol *aux;

    if (!vacio()){
        if (aux=hijoIzq())
            delete aux;
        if (aux=hijoDer())
            delete aux;

        esVacio = true;
    }
}

void Arbol::CopiarArbolInOrden(Arbol &A){
    Arbol *aux;
    if (!vacio()){

        if (aux = hijoIzq())
            aux->CopiarArbolInOrden(A);

        A.insertar(datoRaiz);//datoRaiz;
        //datoRaiz.MostrarNombrePersona();


        if (aux = hijoDer())
            aux->CopiarArbolInOrden(A);
    }
}

// Solo Para copia de arboles, ya que lo acabaremos borrando
TipoDatoArbol Arbol::DatoMenorDelArbol(){
    Arbol *aux;
    TipoDatoArbol Contacto;
    TipoDatoArbol Menor;
    if (!vacio()){

        if (aux = hijoIzq())
            aux->DatoMenorDelArbol();

        Contacto=datoRaiz;
        if(datoRaiz<=Contacto){
        	Menor=datoRaiz;
        }

        if (aux = hijoDer())
            aux->DatoMenorDelArbol();
    }
    return Menor;
}
