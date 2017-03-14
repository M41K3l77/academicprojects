package cargador;

import java.util.ArrayList;
import java.util.List;
import direccion.Direccion;
import personaje.Intruso;
import personaje.Lider;
import personaje.Trabajador;
import edificio.Estacion;
import edificio.Llave;
import edificio.Planta;

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 1.0 -  02/11/2011 
 * @author Profesores DP
 */
public class Cargador {
	/**  
	número de elementos distintos que tendrá la simulación - Planta, Intruso, Trabajador y Líder
	*/
	static final int NUMELTOSCONF  = 4;//tocar si hay mas lineas
	/**  
	atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	/**  
	referencia a la instancia del patrón Singleton
	*/
	private Estacion estacion;
	
	/**
	 *  constructor parametrizado 
	 *  @param e referencia a la instancia del patrón Singleton
	 */
	public Cargador(Estacion e)  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("PLANTA", 7);
		mapeo[1]= new DatoMapeo("LIDER", 4);
		mapeo[2]= new DatoMapeo("TRABAJADOR", 4);//"INTRUSO"
		mapeo[3]= new DatoMapeo("INTRUSO", 4);//"TRABAJADOR"
		//aqui a�adimos mas campos
		estacion = e;
	}
	
	/**
	 *  busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está 
	 *  @param elto elemento a buscar en el array
	 *  @return res posición en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto))      {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  método que crea las distintas instancias de la simulación 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 */
	public void crear(String elto, int numCampos, String vCampos[])	{
	    //Si existe elemento y el número de campos es correcto, procesarlo... si no, error
	    int numElto = queElemento(elto);

	    //Comprobación de datos básicos correctos
	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
	        //procesar
	        switch(queElemento(elto))
	        {
	        case 0:	   
	            crearPlanta(numCampos,vCampos);
	            break;
	        case 1:
	            crearLider(numCampos,vCampos);
	            break;
	        case 2:
	        	crearTrabajador(numCampos,vCampos);
	            break;
	        case 3:
	        	crearIntruso(numCampos,vCampos);//crearTrabajador(numCampos,vCampos);
	            break;
	     	}
	        //a�adir otro case si insertamos otro intruso,lider...
	    }
	    else{
	        System.out.println("ERROR Cargador::crear: Datos de configuracion incorrectos... " + elto + "," + numCampos+"\n");
	    }
	}

	/**
	 *  método que crea una instancia de la clase Planta
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearPlanta(int numCampos, String[] vCampos)
	{
	   	    
	    Planta planta=null;	    

	    // En nuestro caso no se usa, solo hay una planta
	    String numeroDeLaPlanta=vCampos[1];
	    int idPlanta = Integer.parseInt(numeroDeLaPlanta);
	    
	    String anchoDeLaPlanta=vCampos[2];
	    int ancho = Integer.parseInt(anchoDeLaPlanta);
	    
	    String altoDeLaPlanta=vCampos[3];
	    int alto = Integer.parseInt(altoDeLaPlanta);
	    
	    String entrada=vCampos[4];
	    int salaInicio = Integer.parseInt(entrada);
	    
	    String salida=vCampos[5];
	    int salaFinal = Integer.parseInt(salida);
	    
	    String condicionAperturaPuerta=vCampos[6];
	    int aperturaPuertaCondicion = Integer.parseInt(condicionAperturaPuerta);
	    
	   
	    planta=Planta.obtenerInstancia(alto, ancho, salaInicio, salaFinal, 1, aperturaPuertaCondicion, 30);
	    // A�adimos la planta a la estacion
	    
	    this.estacion.setPlanta(planta);
	}
	/**
	 *  método que crea una instancia de la clase Lider
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearLider(int numCampos, String[] vCampos)
	{
	    //Registrar lider en la estacion
	    Lider lider=new Lider();
	    String nombreLider=vCampos[1];
	    lider.setNombre(nombreLider);
	    String marcaLider=vCampos[2];
	    lider.setMarca(marcaLider);
	    String turnoLider=vCampos[3];
	    int turno = Integer.parseInt(turnoLider);
	    lider.setTurno(turno);
	    
	    List <Direccion>direcciones=new ArrayList<Direccion>();		
		Integer etapa=1;
		List<Integer>nesoEtapa=new ArrayList<Integer>();// importante para que coja norte en la primera
		nesoEtapa.add(estacion.getPlanta().getSalaInnicio());
		List <Integer> visitados=new ArrayList<Integer>();
		visitados.add(estacion.getPlanta().getSalaInnicio());
		lider.movimientoLider(estacion.getPlanta(), visitados, estacion.getPlanta().getSalaInnicio(),
				estacion.getPlanta().getSalaFinal(), etapa, nesoEtapa);
		lider.traducirDirecciones(estacion.getPlanta(), visitados, direcciones);
		lider.setdirecciones(direcciones);
	    
	    //implementar sigleton en la planta
	    this.estacion.getPlanta().anadirPersonajePlanta(lider, this.estacion.getPlanta().getSalaInnicio());
	    this.estacion.getPersonajesIniciales().add(lider);	   
	    
	}
	/**
	 *  método que crea una instancia de la clase Intruso
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearIntruso(int numCampos, String[] vCampos)
	{	   
	    //Registrar intruso en la estacion
	    Intruso intruso=new Intruso();
	    String nombreIntruso=vCampos[1];
	    intruso.setNombre(nombreIntruso);
	    String marcaIntruso=vCampos[2];
	    intruso.setMarca(marcaIntruso);
	    String turnoIntruso=vCampos[3];
	    int turno = Integer.parseInt(turnoIntruso);
	    intruso.setTurno(turno);
	    		
	    List <Direccion> direcciones=new ArrayList<Direccion>();
	    
	    List <Integer> visitados=new ArrayList<Integer>();
	    intruso.movimientoIntruso(this.estacion.getPlanta(), this.estacion.getPlanta().getAncho()-1, visitados);
		
	    intruso.traducirDirecciones(estacion.getPlanta(), visitados, direcciones);
	    
		intruso.setdirecciones(direcciones);
		int [] vectorLlavesIntruso={1,3,5,7,9,11,13,15,17,19,21,23,25,27,29};
		for(int i=0;i<vectorLlavesIntruso.length;i++){
			Llave llave1=new Llave(vectorLlavesIntruso[i]);
			intruso.getLLaves().insertarDato(llave1);
		}
	    // implementar sigleton en la planta
	    // Se inserta en la esquina superior derecha	    
	    this.estacion.getPlanta().anadirPersonajePlanta(intruso, this.estacion.getPlanta().getAncho()-1);
	    this.estacion.getPersonajesIniciales().add(intruso);
	}	
	/**
	 *  método que crea una instancia de la clase Trabajador
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearTrabajador(int numCampos, String[] vCampos)
	{	    
	    // Registrar trabajador en la estacion
	    Trabajador trabajador=new Trabajador();
	    String nombreTrabajador=vCampos[1];
	    trabajador.setNombre(nombreTrabajador);
	    String marcaTrabajador=vCampos[2];
	    trabajador.setMarca(marcaTrabajador);
	    String turnoTrabajador=vCampos[3];
	    int turno = Integer.parseInt(turnoTrabajador);
	    trabajador.setTurno(turno);
	    
	    List <Direccion>direcciones=new ArrayList<Direccion>();
	    List <Integer> visitados=new ArrayList<Integer>();
	    trabajador.movimientoTrabajador(estacion.getPlanta(), visitados);
	    trabajador.traducirDirecciones(estacion.getPlanta(), visitados, direcciones);
	    trabajador.setdirecciones(direcciones);
	    
	    this.estacion.getPlanta().anadirPersonajePlanta(trabajador, this.estacion.getPlanta().getSalaInnicio());
	    this.estacion.getPersonajesIniciales().add(trabajador);
	}	
}
