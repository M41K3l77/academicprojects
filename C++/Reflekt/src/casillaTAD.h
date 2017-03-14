//casillaTAD.h//
#ifndef CASILLAS
#define CASILLAS
// La estructura de Casilla está formada por un entero y un buleano
typedef struct Casilla {
	int  color;
	bool estado;
}Casilla;

// Pre: dado un entero que representará un color
// Post: nos creará una casilla con un entero y un bool en true (nunca se creará una
// casilla en false) por la propia dinámica del juego
// Devuelve Casilla
// Complejidad O(1)
Casilla CrearFicha(int colorido);

// Pre: Dada una Casilla ya creada satisfactoriamente
// Post: Nos devuelve el estado de la ficha true o false(devolverá false solo si
// ha habido un cambio de estado)
// Devuelve bool
// Complejidad O(1)
bool EstadoFicha(Casilla ficha);

// Pre: Dada una Casilla ya creada satisfactoriamente
// Post: Nos devuelve el color de la ficha representado por un entero
// Devuelve entero
// Complejidad O(1)
int ColorFicha(Casilla ficha);

// Pre: Dada una Casilla ya creada satisfactoriamente
// Post: Cambia su estado a false si el estado con el que entra
// Casilla es true. Nunca hara un cambio de estado a true
// ya que además nunca será necesario
// Devuelve Casilla
// Complejidad O(1)
void CambiarEstadoFicha(Casilla &ficha);

#endif
