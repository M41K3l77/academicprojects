package edificioTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import personaje.Lider;
import edificio.Llave;
import edificio.Planta;
import estructuras.Lista;
import excepciones.ExceptionCerraduraNoValida;
import excepciones.ExceptionCombinacionDeCerraduraIncorrecta;

public class PlantaTest {

	private Planta planta1=null;	
	
	@Before
	public void setUp(){
		System.out.println("Ejecutando @Before setUp");
		planta1=Planta.obtenerInstancia(6, 6, 0, 35, 0, 5, 30);
	}
	
	@Test
	public void testanadirPersonajePlanta(){
		System.out.println("Ejecutando @Test testanadirPersonajePlanta()");
		assertTrue(planta1.anadirPersonajePlanta(new Lider(),0)==true);
	}
	
	@Test
	public void testCerradura() throws ExceptionCerraduraNoValida, ExceptionCombinacionDeCerraduraIncorrecta{
		int alturaArbol=2;
		Lista<Llave> combinacion=new Lista<Llave>();
		//creamos la convinacion
		planta1.crearCombinacionDeCerradura(combinacion);
		assertTrue(!combinacion.estaVacia());
		
		//insertamos la combinacion en la puerta de la cerradura
		planta1.insertarCombinacionEnCerradura(combinacion);
		assertTrue(!combinacion.estaVacia());
		assertFalse(planta1.getDoor().getCerradura().numeroDeNodos()==0);
		// una vez configurada la puerta la cerramos
		planta1.getDoor().setEstadoPuerta(1);
		planta1.setCondicionAperturaPuertaNivelArbol(alturaArbol);		
	}
}
