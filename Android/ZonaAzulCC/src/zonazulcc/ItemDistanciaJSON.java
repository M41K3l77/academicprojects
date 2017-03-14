package zonazulcc;
/**
 * Implementacion de la clase ItemDistanciaJSON
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class ItemDistanciaJSON {

	private String duration;
	private String distance;
	private int durationV;
	private int distanceV;
	private int posicion;
	

	/**
	 * constructor parametrizado
	 * @param duration
	 * @param distance
	 * @param durationV
	 * @param distanceV
	 * @param posicion
	 */
	public ItemDistanciaJSON(String duration, String distance, int durationV,
			int distanceV, int posicion) {
		super();
		this.duration = duration;
		this.distance = distance;
		this.durationV = durationV;
		this.distanceV = distanceV;
		this.posicion = posicion;
	}
	
	// getters y setters
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getDurationV() {
		return durationV;
	}
	public void setDurationV(int durationV) {
		this.durationV = durationV;
	}
	public int getDistanceV() {
		return distanceV;
	}
	public void setDistanceV(int distanceV) {
		this.distanceV = distanceV;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}



}
