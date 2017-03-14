package zonazulcc;

/**
 * Implementacion de la clase ItemDistanciaJSON
 * Una PlazaZonaAzul es un plaza de aparcamiento limitada temporalmente 
 * en función de la cantidad abonada.
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ItemPlazaZonaAzulJSON {

	private String situadoEnVia;
	private String horarioZonaAzul;
	private String costeZonaAzul;
	private Double precioAnulacionDenuncia;
	private Double longitud;
	private Double latitud;
	
	/**
	 * constructor
	 */
	public ItemPlazaZonaAzulJSON() {
	}


	/**
	 * costructor parametrizado
	 * @param situadoEnVia
	 * @param horarioZonaAzul
	 * @param costeZonaAzul
	 * @param precioAnulacionDenuncia
	 * @param longitud
	 * @param latitud
	 */
	public ItemPlazaZonaAzulJSON(String situadoEnVia, String horarioZonaAzul,
			String costeZonaAzul, Double precioAnulacionDenuncia,
			Double longitud, Double latitud) {
		super();
		this.situadoEnVia = situadoEnVia;
		this.horarioZonaAzul = horarioZonaAzul;
		this.costeZonaAzul = costeZonaAzul;
		this.precioAnulacionDenuncia = precioAnulacionDenuncia;
		this.longitud = longitud;
		this.latitud = latitud;

	}

	// getters y setters
	public String getSituadoEnVia() {
		return situadoEnVia;
	}

	public void setSituadoEnVia(String situadoEnVia) {
		this.situadoEnVia = situadoEnVia;
	}

	public String getHorarioZonaAzul() {
		return horarioZonaAzul;
	}

	public void setHorarioZonaAzul(String horarioZonaAzul) {
		this.horarioZonaAzul = horarioZonaAzul;
	}

	public String getCosteZonaAzul() {
		return costeZonaAzul;
	}

	public void setCosteZonaAzul(String costeZonaAzul) {
		this.costeZonaAzul = costeZonaAzul;
	}

	public Double getPrecioAnulacionDenuncia() {
		return precioAnulacionDenuncia;
	}

	public void setPrecioAnulacionDenuncia(Double precioAnulacionDenuncia) {
		this.precioAnulacionDenuncia = precioAnulacionDenuncia;
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

	
}
