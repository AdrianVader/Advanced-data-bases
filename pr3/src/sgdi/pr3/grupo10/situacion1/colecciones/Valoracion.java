// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion1.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import sgdi.pr3.grupo10.situacion1.Constantes;
import sgdi.pr3.grupo10.situacion1.DBHelper;

import com.mongodb.BasicDBObject;

public class Valoracion {


	private String titulo;
	private int puntuacion;
	private String mensaje;
	public String coleccion;
	public BasicDBObject target;

	
	// Constructor vac�o.
	public Valoracion(){}
	
	// Constructor para todos los campos.
	public Valoracion(String titulo, int puntuacion, String mensaje){
	
		this.titulo = titulo;
		this.puntuacion = puntuacion;
		this.mensaje = mensaje;
		
	}
	
	
	
	public BasicDBObject convierteFormato(){
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("puntuacion", this.puntuacion);
		
		obj.append("mensaje", this.mensaje);
		
		return obj;
	}
	
	
	
	public BasicDBObject pideDatos(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("�A qu� deseas a�adir una valoraci�n? ");
		System.out.println("1 -> Pel�cula");
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
		
		System.out.print("Introduce los datos de la valoraci�n: ");
		System.out.print("Introduce un t�tulo: ");
		this.titulo = sc.nextLine();
			
		System.out.print("Introduce la puntuaci�n (entero de 0 a 10): ");
		this.puntuacion = Integer.parseInt(sc.nextLine());
		
		System.out.print("Introduce un mensaje explicando la puntuaci�n: ");
		this.mensaje = sc.nextLine();
		
		return this.convierteFormato();
		
	}
	

}
