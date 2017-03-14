package zonazulcc;
/**
 * Implementacion de la clase ItemCalleJSON
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ItemCalleJSON {

	private String uri;
	private String nombreDeCalle;
	private Double latitud;
	private Double longitud;
	private int numPlazasAzul;

	
	/**
	 * costructor
	 */
	public ItemCalleJSON() {
	}


	/**
	 * constructor parametrizado
	 * @param uri
	 * @param nombreDeCalle
	 * @param latitud
	 * @param longitud
	 * @param numPlazasAzul
	 */
	public ItemCalleJSON(String uri, String nombreDeCalle, Double latitud,
			Double longitud, int numPlazasAzul) {
		super();
		this.uri = uri;
		this.nombreDeCalle = nombreDeCalle;
		this.latitud = latitud;
		this.longitud = longitud;
		this.numPlazasAzul = numPlazasAzul;
	}

	// getters y setters
	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getNombreDeCalle() {
		return nombreDeCalle;
	}


	public void setNombreDeCalle(String nombreDeCalle) {
		this.nombreDeCalle = nombreDeCalle;
	}


	public Double getLongitud() {
		return longitud;
	}


	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}


	public Double getLatitud() {
		return latitud;
	}


	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	
	public int getNumPlazasAzul() {
		return numPlazasAzul;
	}


	public void setNumPlazasAzul(int numPlazasAzul) {
		this.numPlazasAzul = numPlazasAzul;
	}

	
}
