// SGDI - Pr3 - Adrián Rabadán Jurado, Teresa Rodríguez Ferreira - Los alumnos declaramos que el código de la presente práctica está desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.bson.types.ObjectId;

import sgdi.pr3.grupo10.situacion2.Constantes;
import sgdi.pr3.grupo10.situacion2.DBHelper;

import com.mongodb.BasicDBObject;

public class Episodio {

	private String titulo;
	private String nombreSerie;	// Título de la serie a la que pertenece
	private String nombreTemporada;	// Título de la temporada a la que pertenece
	private String fechaDeEmision; // Formato DD/MM/AAAA, suponemos que siempre es correcto.
	private String sinopsis; // Cadena de texto que resumirá la temporada.
	private ArrayList<String[]> actores; // Guardaremos una lista que contendrá el nombre [y apellidos] del actor y el personaje.
	private ArrayList<String> guionistas; // Lista de nombres [y apellidos] de los guionistas.
	private ArrayList<String> directores; // Guardaremos una lista que contendrá el nombre [y apellidos] de los directores.
	public String idTemporada;
	public BasicDBObject objEpisodio;
	
	
	// Constructor vacío.
	public Episodio(){}
	
	// Constructor para todos los campos.
	public Episodio(String titulo, String nombreSerie, String nombreTemporada, String fechaDeEmision, String sinopsis, ArrayList<String[]> actores, ArrayList<String> guionistas, ArrayList<String> directores){
		
		this.titulo = titulo;
		this.nombreTemporada = nombreTemporada;
		this.fechaDeEmision = fechaDeEmision;
		this.nombreSerie = nombreSerie;
		this.sinopsis = sinopsis;
		this.actores = actores;
		this.guionistas = guionistas;
		this.directores = directores;
		
	}
	
	
	
	public BasicDBObject convierteFormato() {
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("fechaDeEmision", this.fechaDeEmision);
		
		obj.append("sinopsis", this.sinopsis);
		
		obj.append("nombreSerie", this.nombreSerie);
		
		// Necesitamos pasar las tuplas (actor, personaje) a documentos JSON (BasicDBObject).
		ArrayList<BasicDBObject> act = new ArrayList<BasicDBObject>();
		Iterator<String[]> itAct = this.actores.iterator();
		while(itAct.hasNext()){
			String[] actor = itAct.next();
			act.add(new BasicDBObject("actor", actor[0]).append("personaje", actor[1]));
		}
		obj.append("actores", act);
		
		obj.append("guionistas", this.guionistas);
		
		obj.append("directores", this.directores);
		
		obj.append("valoraciones", new ArrayList<BasicDBObject>());
		
		return obj;
	}
	
	
	
	public BasicDBObject pideDatos() throws Exception{
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce un título: ");
		this.titulo = sc.nextLine();
		
		System.out.print("Introduce el título de la serie a la que pertenece: ");
		this.nombreSerie = sc.nextLine();
		
		// Comprobar que ya existe esa serie
		BasicDBObject serie = null;
		try {
			serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", this.nombreSerie));
			if(serie == null){
				throw new Exception("La serie introducida no existe");
			}
		}catch(Exception e){
			throw new Exception("La serie introducida no existe");
		}
		
		System.out.print("Introduce el título de la temporada a la que pertenece: ");
		this.nombreTemporada = sc.nextLine();
		
		// Comprobar que ya existe esa temporada
		BasicDBObject temporada = null;
		try {
			ArrayList<String> listaTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
			Iterator<String> itT = listaTemporadas.iterator();
			boolean encontradoT = false;
			while(itT.hasNext() && !encontradoT){
				temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(itT.next()));
				if(temporada.get("titulo").equals(this.nombreTemporada)){
					ArrayList<String> listaEpisodios = (ArrayList<String>) temporada.get("episodios");
					Iterator<String> itE = listaEpisodios.iterator();
					boolean encontradoE = false;
					while(itE.hasNext() && !encontradoE){
						BasicDBObject episodio = DBHelper.dataBase.getById(Constantes.EPISODIOS, new ObjectId(itE.next()));
						if(episodio.get("titulo").equals(this.titulo)){
							this.objEpisodio = episodio;
							encontradoE = true;
						}
					}
					encontradoT = true;
				}
			}
			if(!encontradoT){
				throw new Exception("La serie introducida no existe");
			}
			this.idTemporada = DBHelper.dataBase.getCampo("_id", Constantes.TEMPORADAS, temporada).toString();
		}catch(Exception e){
			throw new Exception("La temporada introducida no existe");
		}
			
		System.out.print("Introduce la fecha de emisión (DD/MM/AAAA): ");
		this.fechaDeEmision = sc.nextLine();
		
		System.out.print("Escribe la sinopsis: ");
		this.sinopsis = sc.nextLine();
		
		System.out.println("Introduce un actor, y a continuación su personaje (linea vacía indica fin): ");
		System.out.print("Actor >");
		String actor = sc.nextLine();
		this.actores = new ArrayList<String[]>();
		String personaje;
		while(!actor.equals("")){
			personaje = "";
			while(personaje.equals("")){
				System.out.print("Personaje >");
				personaje = sc.nextLine();
			}
			String[] tupla = {actor, personaje};
			this.actores.add(tupla);
			System.out.print("Actor >");
			actor = sc.nextLine();
		}
		
		
		System.out.print("Introduce los guionistas (linea vacía indica fin): ");
		String guionista = sc.nextLine();
		this.guionistas = new ArrayList<String>();
		while(!guionista.equals("")){
			this.guionistas.add(guionista);
			System.out.print(">");
			guionista= sc.nextLine();
		}
		
		System.out.print("Introduce los direcores (linea vacía indica fin): ");
		String director = sc.nextLine();
		this.directores = new ArrayList<String>();
		while(!director.equals("")){
			this.directores.add(director);
			System.out.print(">");
			director = sc.nextLine();
		}
		
		return this.convierteFormato();
		
	}

	public BasicDBObject pideBasico() {
		

		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce el título de la serie: ");
		String tituloSerie = sc.nextLine();
		System.out.print("Introduce el título de la temporada: ");
		String tituloTemporada = sc.nextLine();
		System.out.print("Introduce el título del episodio: ");
		String tituloEpisodio = sc.nextLine();
		
		BasicDBObject objEpisodio = null;
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", tituloSerie));
		ArrayList<String> listaTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
		Iterator<String> itT = listaTemporadas.iterator();
		boolean encontradoT = false;
		BasicDBObject temporada = new BasicDBObject();
		while(itT.hasNext() && !encontradoT){
			temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(itT.next()));
			if(((String) DBHelper.dataBase.getCampo("titulo", Constantes.TEMPORADAS, temporada)).equalsIgnoreCase(tituloTemporada)){
				ArrayList<String> listaEpisodios = (ArrayList<String>) DBHelper.dataBase.getCampo("episodios", Constantes.TEMPORADAS, temporada);
				Iterator<String> itE = listaEpisodios.iterator();
				boolean encontradoE = false;
				while(itE.hasNext() && !encontradoE){
					BasicDBObject episodio = DBHelper.dataBase.getById(Constantes.EPISODIOS, new ObjectId(itE.next()));
					if(((String) DBHelper.dataBase.getCampo("titulo", Constantes.EPISODIOS, episodio)).equalsIgnoreCase(tituloEpisodio)){
						objEpisodio = episodio;
						encontradoE = true;
					}
				}
				encontradoT = true;
			}
		}
		this.idTemporada = DBHelper.dataBase.getCampo("_id", Constantes.TEMPORADAS, temporada).toString();
		
		return objEpisodio;
	}
	
}
