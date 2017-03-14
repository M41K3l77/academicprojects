package model;
/**
 * Implementacion de los metodos de la clase ItemCalle.
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ItemCalle implements Comparable {
	private String nombreDeCalle;
	private String coste;
	private String horario;
	private Double latitud;
	private Double longitud;
	private Double costeAnulacion;
	private int numeroDePlazasCalle;

	/**
	 * constructor por defecto
	 */
	public ItemCalle() {
		this.latitud=new Double(0.0);
		this.longitud=new Double(0.0);
		this.costeAnulacion=new Double(0.0);
		this.numeroDePlazasCalle = 0;

	}
	
	/**
	 * constructor parametrizado
	 * @param calle
	 */
	public ItemCalle(ItemCalle calle) {
		this.nombreDeCalle = calle.nombreDeCalle;
		this.coste = calle.coste;
		this.horario = calle.horario;
		this.latitud = calle.latitud;
		this.longitud = calle.longitud;
		this.costeAnulacion = calle.costeAnulacion;
		this.numeroDePlazasCalle = calle.numeroDePlazasCalle;

	}

	/**
	 * constructor parametrizado
	 * @param nombreDeCalle
	 * @param coste
	 * @param horario
	 * @param latitud
	 * @param longitud
	 * @param costeAnulacion
	 * @param numeroDePlazasCalle
	 */
	public ItemCalle(String nombreDeCalle, String coste, String horario,
			Double latitud, Double longitud, Double costeAnulacion,
			int numeroDePlazasCalle) {
		super();
		this.nombreDeCalle = nombreDeCalle;
		this.coste = coste;
		this.horario = horario;
		this.latitud = latitud;
		this.longitud = longitud;
		this.costeAnulacion = costeAnulacion;
		this.numeroDePlazasCalle = numeroDePlazasCalle;
	}

	// getters y setters
	public String getNombreDeCalle() {
		return nombreDeCalle;
	}

	public void setNombreDeCalle(String nombreDeCalle) {
		this.nombreDeCalle = nombreDeCalle;
	}

	public int getNumeroDePlazasCalle() {
		return numeroDePlazasCalle;
	}

	public void setNumeroDePlazasCalle(int numeroDePlazasCalle) {
		this.numeroDePlazasCalle = numeroDePlazasCalle;
	}

	public String getCoste() {
		return coste;
	}

	public void setCoste(String coste) {
		this.coste = coste;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getCosteAnulacion() {
		return costeAnulacion;
	}

	public void setCosteAnulacion(Double costeAnulacion) {
		this.costeAnulacion = costeAnulacion;
	}

	@Override
	public String toString() {
		return "Calle [calle=" + nombreDeCalle + ", numero de plazas="
				+ numeroDePlazasCalle + "]";
	}

	/**
	 * comparador
	 */
	public int compareTo(Object o) {
		ItemCalle calle = (ItemCalle) o;
		return this.nombreDeCalle.compareToIgnoreCase(calle.nombreDeCalle);
	}
}
