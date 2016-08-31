// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.mongodb.BasicDBObject;

public class Usuario {

	private String nombre;
	private String fechaNacimiento;
	private String eMail;
	private ArrayList<String> episodios;
	private ArrayList<String[]> historial;
	
	// Constructor vac�o.
		public Usuario(){this.historial = null;}
		
		// Constructor para todos los campos.
		public Usuario(String nombre, String fechaNacimiento, String eMail, ArrayList<String> episodios, ArrayList<String[]> historial){
		
			this.nombre = nombre;
			this.fechaNacimiento = fechaNacimiento;
			this.eMail = eMail;
			this.episodios = episodios;
			this.historial = historial;
			
		}
		
		
		
		public BasicDBObject convierteFormato(){
			
			BasicDBObject obj = new BasicDBObject("nombre", this.nombre);
			
			obj.append("fechaNacimiento", this.fechaNacimiento);
			
			obj.append("eMail", this.eMail);
			
			obj.append("episodios", new ArrayList<BasicDBObject>());
			
			// Necesitamos pasar las tuplas (pelicula, cine, sala) a documentos JSON (BasicDBObject).
			if(this.historial != null){
				ArrayList<BasicDBObject> act = new ArrayList<BasicDBObject>();
				Iterator<String[]> itAct = this.historial.iterator();
				while(itAct.hasNext()){
					String[] datos = itAct.next();
					act.add(new BasicDBObject("pelicula", datos[0]).append("cine", datos[1]).append("sala", datos[2]));
				}
				obj.append("historial", act);
			}else{
				obj.append("historial", new ArrayList<BasicDBObject>());
			}
			
			return obj;
		}
		
		
		
		public BasicDBObject pideDatos(){
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce un nombre de usuario (�nico): ");
			this.nombre = sc.nextLine();
				
			System.out.print("Introduce la fecha de nacimiento (DD/MM/AAAA): ");
			this.fechaNacimiento = sc.nextLine();
			
			System.out.print("Introduce una direcci�n de correo electr�nico: ");
			this.eMail = sc.nextLine();
			
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
