// SGDI - Pr3 - Adrián Rabadán Jurado, Teresa Rodríguez Ferreira - Los alumnos declaramos que el código de la presente práctica está desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.Scanner;

import sgdi.pr3.grupo10.situacion2.Constantes;

import com.mongodb.BasicDBObject;

public class Valoracion {


	private String titulo;
	private int puntuacion;
	private String mensaje;
	public String usuario;
	public String coleccion;
	public BasicDBObject target;

	
	// Constructor vacío.
	public Valoracion(){}
	
	// Constructor para todos los campos.
	public Valoracion(String titulo, int puntuacion, String mensaje, String usuario){
	
		this.titulo = titulo;
		this.puntuacion = puntuacion;
		this.mensaje = mensaje;
		this.usuario = usuario;
		
	}
	
	
	
	public BasicDBObject convierteFormato(){
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("puntuacion", this.puntuacion);
		
		obj.append("mensaje", this.mensaje);
		
		obj.append("usuario", this.usuario);
		
		return obj;
	}
	
	
	
	public BasicDBObject pideDatos(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("¿Qué usuario ha realizado la valoración? ");
		this.usuario = sc.nextLine();
		
		System.out.println("¿A qué deseas añadir una valoración? ");
		System.out.println("1 -> Película");
		System.out.println("2 -> Serie");
		System.out.println("3 -> Temporada");
		System.out.println("4 -> Episodio");
		System.out.println("0 -> Cancelar");
		String comando = sc.nextLine();
		
		boolean fin = false;
		while(!fin){
			switch(comando){
			case "1":
				this.coleccion = Constantes.PELICULAS;
				Pelicula p = new Pelicula();
				this.target = p.pideBasico();
				fin = true;
				break;
			case "2":
				this.coleccion = Constantes.SERIES;
				Serie s = new Serie();
				this.target = s.pideBasico();
				fin = true;
				break;
			case "3":
				this.coleccion = Constantes.TEMPORADAS;
				Temporada t = new Temporada();
				this.target = t.pideBasico();
				fin = true;
				break;
			case "4":
				this.coleccion = Constantes.EPISODIOS;
				Episodio e = new Episodio();
				this.target = e.pideBasico();
				fin = true;
				break;
			case "0":
				fin = true;
				break;
			default:
				fin = false;
				break;
			}
		}
		
		System.out.print("Introduce los datos de la valoración: ");
		System.out.print("Introduce un título: ");
		this.titulo = sc.nextLine();
			
		System.out.print("Introduce la puntuación (entero de 0 a 10): ");
		this.puntuacion = Integer.parseInt(sc.nextLine());
		
		System.out.print("Introduce un mensaje explicando la puntuación: ");
		this.mensaje = sc.nextLine();
		
		return this.convierteFormato();
		
	}
	

}