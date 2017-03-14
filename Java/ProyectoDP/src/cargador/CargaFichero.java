package cargador;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import edificio.Estacion;
/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 1.0 -  02/11/2011 
 * @author Profesores DP
 */
public class CargaFichero {
	/**
	 * Metodo main desde el que se ejecuta el programa DPINITIATIVE
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		/**  
		estacion es la instancia del patrón Singleton
		si sólo hubiera una planta, la instancia de Planta implementaría el patrón Singleton
		*/
		Estacion estacion = null;
		estacion=Estacion.obtenerInstancia();
		/**  
		instancia asociada al fichero de entrada inicio.txt
		*/
		Cargador cargador = new Cargador(estacion);
		try {
			/**  
			método que procesa línea a línea el fichero de entrada inicio.txt
			*/
		     FicheroCarga.procesarFichero("inicio.txt", cargador);
		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepcion capturada al procesar fichero: "+valor.getMessage());
		}
		catch (IOException valor)  {
			System.err.println ("Excepcion capturada al procesar fichero: "+valor.getMessage());
		}
		
		PrintStream orgStream = null;
		PrintStream fileStream = null;
		try {

			// Se guarda el stream original
			orgStream = System.out;
			// El "logSimulacion.txt",false es para que lo sobreescriba
			fileStream = new PrintStream(new FileOutputStream("registro.log",false));

			// Se escribe directamente en el *.txt
			System.setOut(fileStream);

			// Redirecting runtime exceptions to file
			System.setErr(fileStream);
			estacion.realizarSimulacion();
			//throw new Exception("Test Exception");

			}catch (FileNotFoundException archivoNoEncontrado){
			System.out.println("Error en redireccionamiento de la IO");
			archivoNoEncontrado.printStackTrace();
			}catch (Exception ex){
			ex.printStackTrace();
			//Se imprime en el *.txt
			//System.out.println("Redirecting output & exceptions to file");
			}finally{
			//Se vuelve a la consola
			System.setOut(orgStream);
			//se devuelve el control a la terminal de eclipse
			System.out.println("Fin de simulacion y vuelta a la consola");
			}		

	}
}
