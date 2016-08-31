// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.mongodb.BasicDBObject;

public class Cine {

	private String nombre;
	private String direccion;
	private ArrayList<String[]> salas;
	
	// Constructor vac�o.
		public Cine(){this.salas = null;}
		
		// Constructor para todos los campos.
		public Cine(String nombre, String direccion, ArrayList<String[]> salas){
		
			this.nombre = nombre;
			this.direccion = direccion;
			this.salas = salas;
			
		}
		
		
		
		public BasicDBObject convierteFormato(){
			
			BasicDBObject obj = new BasicDBObject("nombre", this.nombre);
			
			obj.append("direccion", this.direccion);
			
			// Necesitamos pasar las tuplas (sala, aforo, pelicula) a documentos JSON (BasicDBObject).
			if(this.salas != null){
				ArrayList<BasicDBObject> act = new ArrayList<BasicDBObject>();
				Iterator<String[]> itAct = this.salas.iterator();
				while(itAct.hasNext()){
					String[] datos = itAct.next();
					act.add(new BasicDBObject("sala", datos[0]).append("aforo", datos[1]).append("pelicula", datos[2]));
				}
				obj.append("salas", act);
			}else{
				obj.append("salas", new ArrayList<BasicDBObject>());
			}
			
			return obj;
		}
		
		
		
		public BasicDBObject pideDatos(){
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce un nombre: ");
			this.nombre = sc.nextLine();
				
			System.out.print("Introduce una direcci�n: ");
			this.direccion = sc.nextLine();
			
			return this.convierteFormato();
			
		}

		/*public BasicDBObject pideBasico() {
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el t�tulo: ");
			String titulo = sc.nextLine();
			
			BasicDBObject pelicula = DBHelper.dataBase.get(Constantes.PELICULAS, new BasicDBObject("titulo", titulo));
			
			return pelicula;
		}*/
	
}
