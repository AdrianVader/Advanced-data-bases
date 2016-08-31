// SGDI - Pr3 - Adrián Rabadán Jurado, Teresa Rodríguez Ferreira - Los alumnos declaramos que el código de la presente práctica está desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2.colecciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.bson.types.ObjectId;

import sgdi.pr3.grupo10.situacion2.Constantes;
import sgdi.pr3.grupo10.situacion2.DBHelper;

import com.mongodb.BasicDBObject;

public class Temporada {

	private String titulo;
	public String nombreSerie;	// Título de la serie a la que pertenece
	private int añoDeEstreno; // Formato AAAA, suponemos que siempre es correcto.
	private String sinopsis; // Cadena de texto que resumirá la temporada.
	private ArrayList<String> episodios; // Lista de identificadores de episodios que contiene la temporada
	public BasicDBObject objTemporada;
	
	
	// Constructor vacío.
	public Temporada(){this.episodios = null;}
	
	// Constructor para todos los campos.
	public Temporada(String titulo, String nombreSerie, int añoDeEstreno, String sinopsis, ArrayList<String> episodios){
		
		this.titulo = titulo;
		this.nombreSerie = nombreSerie;
		this.añoDeEstreno = añoDeEstreno;
		this.sinopsis = sinopsis;
		this.episodios = episodios;
		
	}
	


	public BasicDBObject convierteFormato() {
		
		BasicDBObject obj = new BasicDBObject("titulo", this.titulo);
		
		obj.append("añoDeEstreno", this.añoDeEstreno);
		
		obj.append("sinopsis", this.sinopsis);
		
		if(this.episodios == null){
			obj.append("episodios", new ArrayList<BasicDBObject>());
		}else{
			// Falta convertir al formato.
			//obj.append("episodios", this.episodios);
		}
		
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
		try {
			BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", this.nombreSerie));
			ArrayList<String> listaTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
			Iterator<String> it = listaTemporadas.iterator();
			boolean encontrado = false;
			while(it.hasNext() && !encontrado){
				BasicDBObject temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(it.next()));
				if(temporada.get("titulo").equals(this.titulo)){
					this.objTemporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, (ObjectId) temporada.get("_id"));
					encontrado = true;
				}
			}
		}catch(Exception e){
			throw new Exception("La serie introducida no existe");
		}
			
		System.out.print("Introduce el año de estreno de la temporada (AAAA): ");
		this.añoDeEstreno = Integer.parseInt(sc.nextLine());
		
		System.out.print("Escribe la sinopsis: ");
		this.sinopsis = sc.nextLine();
		
		/*System.out.print("Quieres introducir episodios? (s/n): ");
		System.out.print("Introduce los episodios (linea vacía indica fin): ");
		String temporada = sc.nextLine();
		ArrayList<String> temporadas = new ArrayList<String>();
		while(!temporada.equals("")){
			temporadas.add(temporada);
			System.out.print(">");
			temporada = sc.nextLine();
		}*/
		
		return this.convierteFormato();
		
	}

	public BasicDBObject pideBasico(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce el título de la serie: ");
		String tituloSerie = sc.nextLine();
		this.nombreSerie = tituloSerie; 
		System.out.print("Introduce el título de la temporada: ");
		String tituloTemporada = sc.nextLine();
		
		BasicDBObject objTemporada = null;
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", tituloSerie));
		ArrayList<String> listaTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
		Iterator<String> it = listaTemporadas.iterator();
		boolean encontrado = false;
		while(it.hasNext() && !encontrado){
			BasicDBObject temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(it.next()));
			if(temporada.get("titulo").equals(tituloTemporada)){
				objTemporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, (ObjectId) temporada.get("_id"));
				encontrado = true;
			}
		}
		
		return objTemporada;
	}
	
}
