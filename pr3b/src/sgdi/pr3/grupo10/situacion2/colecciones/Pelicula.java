// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import sgdi.pr3.grupo10.situacion2.Constantes;
import sgdi.pr3.grupo10.situacion2.DBHelper;

import com.mongodb.BasicDBObject;

public class Pelicula {

	public String titulo; // Cadena que supondremos �nica para todas las instancias creadas.
	private int a�oDeEstreno; // Formato AAAA, suponemos que siempre es correcto.
	private ArrayList<String> directores; // Lista de nombres [y apellidos]. Como no se realizar�n consultas con estos no pertenecen a otra colecci�n.
	private ArrayList<String> paisesEnLosQueSeHaGrabado;  // Lista de paises.  Como no se realizar�n consultas con estos no pertenecen a otra colecci�n.
	private String sinopsis; // Cadena de texto que resumir� la pel�cula.
	private ArrayList<String[]> actores; // Guardaremos una lista que contendr� el nombre [y apellidos] del actor y el personaje.
	private ArrayList<String> guionistas; // Lista de nombres [y apellidos]. Como no se realizar�n consultas con estos no pertenecen a otra colecci�n.
	
	
	// Constructor vac�o.
	public Pelicula(){}
	
	// Constructor para todos los campos.
	public Pelicula(String titulo, int a�oDeEstreno, ArrayList<String> directores, ArrayList<String> paisesEnLosQueSeHaGrabado, String sinopsis, ArrayList<String[]> actores, ArrayList<String> guionistas){
	
		this.titulo = titulo;
		this.a�oDeEstreno = a�oDeEstreno;
		this.directores = directores;
		this.paisesEnLosQueSeHaGrabado = paisesEnLosQueSeHaGrabado;
		this.sinopsis = sinopsis;
		this.actores = actores;
		this.guionistas = guionistas;
		
	}
	
	
	
	public BasicDBObject convierteFormato(){
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("a�oDeEstreno", this.a�oDeEstreno);
		
		obj.append("directores", this.directores);
		
		obj.append("paisesEnLosQueSeHaGrabado", this.paisesEnLosQueSeHaGrabado);
		
		obj.append("sinopsis", this.sinopsis);
		
		// Necesitamos pasar las tuplas (actor, personaje) a documentos JSON (BasicDBObject).
		ArrayList<BasicDBObject> act = new ArrayList<BasicDBObject>();
		Iterator<String[]> itAct = this.actores.iterator();
		while(itAct.hasNext()){
			String[] actor = itAct.next();
			act.add(new BasicDBObject("actor", actor[0]).append("personaje", actor[1]));
		}
		obj.append("actores", act);
		
		obj.append("guionistas", this.guionistas);
		
		obj.append("valoraciones", new ArrayList<BasicDBObject>());
		
		return obj;
	}
	
	
	
	public BasicDBObject pideDatos(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce un t�tulo: ");
		this.titulo = sc.nextLine();
			
		System.out.print("Introduce el a�o del estreno (AAAA): ");
		this.a�oDeEstreno = Integer.parseInt(sc.nextLine());
		
		System.out.print("Introduce los direcores (linea vac�a indica fin): >");
		String director = sc.nextLine();
		this.directores = new ArrayList<String>();
		while(!director.equals("")){
			this.directores.add(director);
			System.out.print(">");
			director = sc.nextLine();
		}
		
		System.out.print("Introduce los paises en los que se ha grabado (linea vac�a indica fin): >");
		String pais = sc.nextLine();
		this.paisesEnLosQueSeHaGrabado = new ArrayList<String>();
		while(!pais.equals("")){
			this.paisesEnLosQueSeHaGrabado.add(pais);
			System.out.print(">");
			pais = sc.nextLine();
		}
		
		System.out.print("Escribe la sinopsis: ");
		String sinopsis = sc.nextLine();
		
		System.out.println("Introduce un actor, y a continuaci�n su personaje (linea vac�a indica fin):");
		System.out.print("Actor >");
		String actor = sc.nextLine();
		this.actores = new ArrayList<String[]>();
		while(!actor.equals("")){
			String personaje = "";
			while(personaje.equals("")){
				System.out.print("Personaje >");
				personaje = sc.nextLine();
			}
			String[] tupla = {actor, personaje};
			this.actores.add(tupla);
			System.out.print("Actor >");
			actor = sc.nextLine();
		}
		
		System.out.print("Introduce los guionistas (linea vac�a indica fin): >");
		String guionista = sc.nextLine();
		this.guionistas = new ArrayList<String>();
		while(!guionista.equals("")){
			this.guionistas.add(guionista);
			System.out.print(">");
			guionista = sc.nextLine();
		}
		
		return this.convierteFormato();
		
	}

	public BasicDBObject pideBasico() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce el t�tulo: ");
		this.titulo = sc.nextLine();
		
		BasicDBObject pelicula = DBHelper.dataBase.get(Constantes.PELICULAS, new BasicDBObject("titulo", this.titulo));
		
		return pelicula;
	}
	
}
