// SGDI - Pr3 - Adrián Rabadán Jurado, Teresa Rodríguez Ferreira - Los alumnos declaramos que el código de la presente práctica está desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion1.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import sgdi.pr3.grupo10.situacion1.Constantes;
import sgdi.pr3.grupo10.situacion1.DBHelper;

import com.mongodb.BasicDBObject;

public class Serie {

	public String titulo; // Cadena que supondremos única para todas las instancias creadas.
	private int añoDeInicio; // Formato AAAA, suponemos que siempre es correcto.
	private String sinopsis; // Cadena de texto que resumirá la serie.
	private ArrayList<String> temporadas; // Lista de identificadores de temporadas que contiene la serie
	
	
	// Constructor vacío.
	public Serie(){this.temporadas = null;}
	
	// Constructor para todos los campos.
	public Serie(String titulo, int añoDeInicio, String sinopsis, ArrayList<String> temporadas){
		
		this.titulo = titulo;
		this.añoDeInicio = añoDeInicio;
		this.sinopsis = sinopsis;
		this.temporadas = temporadas;
		
	}
	
	
	
	public BasicDBObject convierteFormato(){
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("añoDeInicio", this.añoDeInicio);
		
		obj.append("sinopsis", this.sinopsis);
		
		if(this.temporadas == null){
			obj.append("temporadas", new ArrayList<BasicDBObject>());
		}else{
			// Falta convertir al formato.
			//obj.append("temporadas", this.temporadas);
		}
		
		obj.append("valoraciones", new ArrayList<BasicDBObject>());
		
		return obj;
	}
	

	
	public BasicDBObject pideDatos(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce un título: ");
		this.titulo = sc.nextLine();
			
		System.out.print("Introduce el año de inicio de la serie (AAAA): ");
		this.añoDeInicio = Integer.parseInt(sc.nextLine());
		
		System.out.print("Escribe la sinopsis: ");
		this.sinopsis = sc.nextLine();
		
		/*System.out.print("Quieres introducir temporadas? (s/n): ");
		System.out.print("Introduce las temporadas (linea vacía indica fin): ");
		String temporada = sc.nextLine();
		ArrayList<String> temporadas = new ArrayList<String>();
		while(!temporada.equals("")){
			temporadas.add(temporada);
			System.out.print(">");
			temporada = sc.nextLine();
		}*/
		
		return this.convierteFormato();
		
	}

	public BasicDBObject pideBasico() {

		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce el título: ");
		String titulo = sc.nextLine();
		
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", titulo));
		
		return serie;
	}
	
}
