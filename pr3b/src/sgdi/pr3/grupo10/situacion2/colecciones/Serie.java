// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import sgdi.pr3.grupo10.situacion2.Constantes;
import sgdi.pr3.grupo10.situacion2.DBHelper;

import com.mongodb.BasicDBObject;

public class Serie {

	public String titulo; // Cadena que supondremos �nica para todas las instancias creadas.
	private int a�oDeInicio; // Formato AAAA, suponemos que siempre es correcto.
	private String sinopsis; // Cadena de texto que resumir� la serie.
	private ArrayList<String> temporadas; // Lista de identificadores de temporadas que contiene la serie
	
	
	// Constructor vac�o.
	public Serie(){this.temporadas = null;}
	
	// Constructor para todos los campos.
	public Serie(String titulo, int a�oDeInicio, String sinopsis, ArrayList<String> temporadas){
		
		this.titulo = titulo;
		this.a�oDeInicio = a�oDeInicio;
		this.sinopsis = sinopsis;
		this.temporadas = temporadas;
		
	}
	
	
	
	public BasicDBObject convierteFormato(){
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("a�oDeInicio", this.a�oDeInicio);
		
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
		
		System.out.print("Introduce un t�tulo: ");
		this.titulo = sc.nextLine();
			
		System.out.print("Introduce el a�o de inicio de la serie (AAAA): ");
		this.a�oDeInicio = Integer.parseInt(sc.nextLine());
		
		System.out.print("Escribe la sinopsis: ");
		this.sinopsis = sc.nextLine();
		
		/*System.out.print("Quieres introducir temporadas? (s/n): ");
		System.out.print("Introduce las temporadas (linea vac�a indica fin): ");
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
		
		System.out.print("Introduce el t�tulo: ");
		String titulo = sc.nextLine();
		
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", titulo));
		
		return serie;
	}
	
}
