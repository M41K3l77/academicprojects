package laberinto;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
* Implementacion de los metodos de la clase ComparadorList.
*
* @version 1.0
* @author
* <b> Alumnos Carlos M. Bueno Lujan y Miguel A. Holgado Ceballos </b><br>
* Proyecto Asignatura Desarrollo de Programas<br/>
* Curso 12/13
* Grupo: GrupoDPCMyM
* Entrega: EC2
*/
public class ComparadorList implements Comparator<List<Integer>>{

	/**
	 * Metodo compare sobrecargado para poder 
	 * comparar listas(en nuestro trabajo seran arrayList).
	 */
	@Override
	public int compare(List<Integer> arg0, List<Integer> arg1) {
		// TODO Auto-generated method stub
		if(arg0.size()<arg1.size()){
			// es menor el arraylist que tiene menor longitud
			return -1;
		}else if(arg0.size()>arg1.size()){
			// es mayor el arraylist que tiene mayor longitud
			return 1;
		}else{
			Iterator<Integer> i=arg0.iterator();
			Iterator<Integer> j=arg1.iterator();
			Integer a=null;
			Integer b=null;
			boolean iguales=true;
			boolean mayor=false;
			while (i.hasNext() && iguales){
				a=i.next();
				b=j.next();
				if(a!=b){
					iguales=false;
					if(a>b){
						mayor=true;
					}
				}
			}
			if(iguales){
				// Si tienen la misma longitud y los mismos datos en el mismo orden, son iguales
				return 0;
			}else{
				// Si tienen la misma longitud pero diferentes datos u orden de los mismos,
				// se pone como lista mayor el que tenga del primer dato desigual el mayor
				if(mayor){
					return 1;
				}else{
					return -1;
				}
			}
		}
	}
}