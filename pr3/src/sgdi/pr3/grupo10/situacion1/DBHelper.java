// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


/**
 * Esta clase se encargar� de la interacci�n (entrada/salida) con la base de datos (MongoDB), dispondr� de un m�todo por cada consulta. 
 * @author Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira
 */
public final class DBHelper {

	public static DataBase dataBase;
	
	

	public static void changeDataBase(String dataBaseName){
		
		DBHelper.dataBase = new DataBase(dataBaseName);
		
	}
	
	
	// ***********************************************************************
	// 1. Insertar/modificar/borrar una pel�cula.
	// ***********************************************************************
	
	/**
	 * 1. Insertar una pel�cula.
	 */
	public static void insertaPelicula(BasicDBObject obj){
		DBHelper.dataBase.insert(Constantes.PELICULAS, obj);
	}
	
	/**
	 * 1. Modificar una pel�cula.
	 */
	public static void modificaPelicula(BasicDBObject objOld, BasicDBObject objNew){
		DBHelper.dataBase.update(Constantes.PELICULAS, objOld, objNew);
	}
	
	/**
	 * 1. Borrar una pel�cula.
	 * Elimina sus valoraciones y
	 * borra la pel�cula de la base de datos
	 */
	public static void borraPelicula(BasicDBObject obj){
		
		DBHelper.dataBase.borraValoraciones(Constantes.PELICULAS, obj);
		
		DBHelper.dataBase.remove(Constantes.PELICULAS, obj);
	}
	
	
	
	// ***********************************************************************
	// 2. Insertar/modificar/borrar una serie de TV.
	// ***********************************************************************
		
	/**
	 * 2. Insertar una serie de TV.
	 */
	public static void insertaSerie(BasicDBObject serie){
		DBHelper.dataBase.insert(Constantes.SERIES, serie);
	}
	
	/**
	 * 2. Modificar una serie de TV.
	 */
	public static void modificaSerie(BasicDBObject objOld, BasicDBObject objNew){
		DBHelper.dataBase.update(Constantes.SERIES, objOld, objNew);
	}
	
	/**
	 * 2. Borrar una serie de TV.
	 * Elimina las valoraciones de la serie,
	 * sus temporadas (junto con sus episodios) y
	 * borra la serie de la base de datos
	 */
	public static void borraSerie(BasicDBObject serie){
		
		DBHelper.dataBase.borraValoraciones(Constantes.SERIES, serie);
		
		// Busco las temporadas de la serie
		ArrayList<String> arrayIdTemporadas = (ArrayList<String>) serie.get("temporadas");
		
		// Obtengo el nombre de la serie
		String titulo = serie.getString("titulo");
		
		BasicDBObject temporada;
		Iterator<String> itTemporadas = arrayIdTemporadas.iterator();
		while(itTemporadas.hasNext()){
			temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(itTemporadas.next()));
			borraTemporada(temporada, titulo);
		}
		
		DBHelper.dataBase.remove(Constantes.SERIES, serie);
	}
	
	
	
	// ***********************************************************************
	// 3. Insertar/modificar/borrar una temporada de una serie de TV.
	// ***********************************************************************
	
	/**
	 * 3. Insertar una temporada de una serie de TV.
	 */
	public static void insertaTemporada(BasicDBObject temporada, String nombreSerie){
		DBHelper.dataBase.insert(Constantes.TEMPORADAS, temporada);
		
		// Buscar la temporada a�adida, coger el _id y meterlo en la serie a la que pertenece.
		
		// Consigo un objeto identificador de la temporada (campo _id en MongoDB).
		String idTemporada = DBHelper.dataBase.getCampo("_id", Constantes.TEMPORADAS, temporada).toString();
		
		// Consigo la serie a la que pertenece esta temporada.
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", nombreSerie));
		
		// Obtengo el array de IDs de temporadas de esa serie y a�ado el id de la nueva.
		ArrayList<String> arrayIdTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
		arrayIdTemporadas.add(idTemporada);
		
		// Creo un BasicDBObject que actualizar� �nicamente el campo deseado (temporadas).
		BasicDBObject actualizado = new BasicDBObject("$set", new BasicDBObject("temporadas", arrayIdTemporadas));
		
		// Actualizo la serie.
		DBHelper.dataBase.update(Constantes.SERIES, serie, actualizado);
	}
	
	/**
	 * 3. Modificar una temporada de una serie de TV.
	 */
	public static void modificaTemporada(BasicDBObject objOld, BasicDBObject objNew){
		DBHelper.dataBase.update(Constantes.TEMPORADAS, objOld, objNew);
	}
	
	/**
	 * 3. Borrar una temporada de una serie de TV.
	 * Elimina las valoraciones de la temporada,
	 * quita la temporada de la serie a la que pertenece,
	 * elimina todos los episodios que contiene y
	 * borra la temporada de la base de datos
	 */
	public static void borraTemporada(BasicDBObject temporada, String nombreSerie){
		
		DBHelper.dataBase.borraValoraciones(Constantes.TEMPORADAS, temporada);
		
		// Consigo un objeto identificador de la temporada (campo _id en MongoDB).
		String idTemporada = DBHelper.dataBase.getCampo("_id", Constantes.TEMPORADAS, temporada).toString();
		
		// Consigo la serie a la que pertenece esta temporada.
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", nombreSerie));
		
		// Obtengo el array de IDs de temporadas de esa serie y elimino el id de la que quiero borrar.
		ArrayList<String> arrayIdTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas", Constantes.SERIES, serie);
		arrayIdTemporadas.remove(idTemporada);
		
		// Obtengo el array de IDs de episodios de esa temporada
		ArrayList<String> arrayIdEpisodios = (ArrayList<String>) DBHelper.dataBase.getCampo("episodios", Constantes.TEMPORADAS,temporada);
		
		// Recorro los IDs de los episodios, y los voy borrando de la base de datos
		Iterator<String> it = arrayIdEpisodios.iterator();
		BasicDBObject episodio;
		while(it.hasNext()){
			episodio = DBHelper.dataBase.getById(Constantes.EPISODIOS, new ObjectId(it.next()));
			borraEpisodio(episodio);
		}
		
		// Elimino la temporada
		DBHelper.dataBase.remove(Constantes.TEMPORADAS, DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(idTemporada)));
	}
	
	
	
	// ***********************************************************************
	// 4. Insertar/modificar/borrar un episodio de una temporada de una serie de TV.
	// ***********************************************************************
	
	/**
	 * 4. Insertar un episodio de una temporada de una serie de TV.
	 */
	public static void insertaEpisodio(BasicDBObject episodio, String idTemporada){
		DBHelper.dataBase.insert(Constantes.EPISODIOS, episodio);
		
		// Consigo un objeto identificador del episodio (campo _id en MongoDB).
		String idEpisodio = DBHelper.dataBase.getCampo("_id", Constantes.EPISODIOS, episodio).toString();
		
		// Consigo la temporada a la que pertenece este episodio.
		BasicDBObject temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(idTemporada));
		
		// Obtengo el array de IDs de episodios de esa temporada y a�ado el id del nuevo.
		ArrayList<String> arrayEpisodios = (ArrayList<String>) DBHelper.dataBase.getCampo("episodios", Constantes.TEMPORADAS, temporada);
		arrayEpisodios.add(idEpisodio);
		
		// Creo un BasicDBObject que actualizar� �nicamente el campo deseado (temporadas).
		BasicDBObject actualizado = new BasicDBObject("$set", new BasicDBObject("episodios", arrayEpisodios));
		
		// Actualizo la serie.
		DBHelper.dataBase.update(Constantes.TEMPORADAS, temporada, actualizado);
	}
	
	/**
	 * 4. Modificar un episodio de una temporada de una serie de TV.
	 */
	public static void modificaEpisodio(BasicDBObject objOld, BasicDBObject objNew){
		DBHelper.dataBase.update(Constantes.EPISODIOS, objOld, objNew);
	}
	
	/**
	 * 4. Borrar un episodio de una temporada de una serie de TV.
	 * Elimina las valoraciones del episodio,
	 * quita el episodio de la temporada en la que est� contenido y
	 * elimina el episodio de la base de datos
	 */
	public static void borraEpisodio(BasicDBObject episodio){
		
		DBHelper.dataBase.borraValoraciones(Constantes.EPISODIOS, episodio);
		
		// Buscar el episodio, coger el _id y borrarlo de la lista de episodios de la temporada a la que pertenece.
		
		// Consigo un objeto identificador del episodio (campo _id en MongoDB).
		String idEpisodio = DBHelper.dataBase.getCampo("_id", Constantes.EPISODIOS, episodio).toString();
		
		//Consigo la serie a la que pertenece ese episodio
		String nombreSerie = (String) DBHelper.dataBase.getCampo("nombreSerie", Constantes.EPISODIOS, episodio);
		BasicDBObject serie = DBHelper.dataBase.get(Constantes.SERIES, new BasicDBObject("titulo", nombreSerie));
		
		// Consigo los IDs de las temporadas de esa serie
		ArrayList<String> arrayIdTemporadas = (ArrayList<String>) DBHelper.dataBase.getCampo("temporadas",Constantes.SERIES , serie);
		Iterator<String> it = arrayIdTemporadas.iterator();
		
		// Recorro las temporadas para ver si alguna contiene el episodio que buscamos
		BasicDBObject temporada = null;
		ArrayList<String> arrayIdEpisodios = null;
		boolean encontrado = false;
		while(it.hasNext() && (!encontrado)){
			temporada = DBHelper.dataBase.getById(Constantes.TEMPORADAS, new ObjectId(it.next()));
			arrayIdEpisodios = (ArrayList<String>) DBHelper.dataBase.getCampo("episodios", Constantes.TEMPORADAS, temporada);
			if(arrayIdEpisodios.contains(idEpisodio)){
				encontrado = true;
			}
		}
		
		if(encontrado){
			arrayIdEpisodios.remove(idEpisodio);
			DBHelper.dataBase.update(Constantes.TEMPORADAS, temporada, new BasicDBObject("$set", new BasicDBObject("episodios", arrayIdEpisodios)));
			DBHelper.dataBase.remove(Constantes.EPISODIOS, DBHelper.dataBase.getById(Constantes.EPISODIOS, new ObjectId(idEpisodio)));
		}
		else{
			System.err.println("No se ha encontrado el episodio en la serie, no se ha podido borrar.");
		}
		
	}
	
	
	
	// ***********************************************************************
	// 5. A�adir una valoraci�n a una pel�cula, serie, temporada o episodio.
	// ***********************************************************************
	
	/**
	 * 5. A�adir una valoraci�n a una pel�cula, serie, temporada o episodio.
	 * @param coleccion La colecci�n a la que se refiere (es una pel�cula? una serie?...)
	 * @param target El objeto concreto al que se aplicar� la valoraci�n
	 * @param valoracion el objeto valoracion que se va a introducir
	 */
	public static void meteValoracion(String coleccion, BasicDBObject target, BasicDBObject valoracion){
		
		// Inserta la valoraci�n en la base de datos
		DBHelper.dataBase.insert(Constantes.VALORACIONES, valoracion);
		String idValoracion = DBHelper.dataBase.getCampo("_id", Constantes.VALORACIONES, valoracion).toString();
		
		// Recupera las valoraciones del objeto al que se va a a�adir otra valoraci�n
		//ArrayList<String> arrayIdValoraciones = (ArrayList<String>) DBHelper.dataBase.getCampo("valoraciones", coleccion, target);
		ArrayList<String> arrayIdValoraciones = (ArrayList<String>) DBHelper.dataBase.getCampo("valoraciones", coleccion, target);
		arrayIdValoraciones.add(idValoracion);
		DBHelper.dataBase.update(coleccion, target, new BasicDBObject("$set", new BasicDBObject("valoraciones", arrayIdValoraciones)));
		
	}
	
	
	
	// ***********************************************************************
	// 6. Conocer el nombre de los actores que aparecen en una pel�cula o serie de TV (se proporciona el t�tulo).
	// ***********************************************************************
	
	/**
	 * 6. Conocer el nombre de los actores que aparecen en una pel�cula o serie de TV (se proporciona el t�tulo).
	 */
	public static void nombreActores(String coleccion, String titulo){
		// El valor de coleccion se reduce a Constantes.PELICULAS o Constantes.EPISODIOS, que son las que contienen actores
		BasicDBObject peliOEpisodio = new BasicDBObject();
		if(coleccion.equals(Constantes.PELICULAS)){
			peliOEpisodio = DBHelper.dataBase.get(coleccion, new BasicDBObject("titulo", titulo));
		}else if(coleccion.equals(Constantes.EPISODIOS)){
			peliOEpisodio = DBHelper.dataBase.get(coleccion, new BasicDBObject("nombreSerie", titulo));
		}else{
			System.err.println("La colecci�n no es ni pel�cula ni serie");
		}
		//ArrayList<BasicDBObject> listaActores = (ArrayList<BasicDBObject>) peliOEpisodio.get("actores");
		ArrayList<BasicDBObject> listaActores = (ArrayList<BasicDBObject>) DBHelper.dataBase.getCampo("actores", coleccion, peliOEpisodio);
		
		Iterator<BasicDBObject> it = listaActores.iterator();
		while(it.hasNext()){
			BasicDBObject act = it.next();
			System.out.println(act.get("actor"));
		}
	}
	
	
	
	// ***********************************************************************
	// 7. Conocer el t�tulo de las pel�culas o series de TV en las que aparece un determinado actor (se proporciona el nombre).
	// ***********************************************************************
	
	/**
	 * 7. Conocer el t�tulo de las pel�culas o series de TV en las que aparece un determinado actor (se proporciona el nombre).
	 */
	public static void peliOSerieApareceActor(String coleccion, String nombre){
		DBCursor cursor = DBHelper.dataBase.getAll(coleccion, new BasicDBObject("actores.actor", nombre));
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if(coleccion.equals(Constantes.PELICULAS)){
				System.out.println(obj.get("titulo"));
			}else if(coleccion.equals(Constantes.EPISODIOS)){
				System.out.println(obj.get("nombreSerie"));
			}else{
				System.err.println("La colecci�n no pertenece a peli o serie");
			}
		}
	}
	
	
	
	// ***********************************************************************
	// 8. Dado el nombre de un actor y el t�tulo de una pel�cula o episodio de serie de TV, encontrar el personaje que interpreta.
	// ***********************************************************************
	
	/**
	 * 8. Dado el nombre de un actor y el t�tulo de una pel�cula o episodio de serie de TV, encontrar el personaje que interpreta.
	 */
	public static void encuentraPersonaje(String nombreActor, String titulo){
		
		// Primero miraremos si el t�tulo corresponde a una serie o a una pel�cula, para elegir la colecci�n a utilizar.
		String coleccion = "";
		BasicDBObject query = new BasicDBObject("titulo", titulo);
		boolean esEpisodio = false, esPelicula = false;
		if(DBHelper.dataBase.findOne(Constantes.EPISODIOS, query)){
			esEpisodio = true;
			coleccion = Constantes.EPISODIOS;
		}
		if(DBHelper.dataBase.findOne(Constantes.PELICULAS, query)){
			esPelicula = true;
			coleccion = Constantes.PELICULAS;
		}
		if(!esEpisodio && !esPelicula){
			System.err.println("No hay episodios o pel�culas con el t�tulo proporcionado");
		}
		else if (esEpisodio && esPelicula){
			System.out.println("Existe un episodio y una pel�cula con este nombre, elige una: ");
			System.out.println("1 : Pelicula");
			System.out.println("2 : Episodio");
			System.out.print(">");
			Scanner sc = new Scanner(System.in);
			String resp = sc.nextLine();
			switch(resp.toLowerCase()){
			case "1":
				coleccion = Constantes.PELICULAS;
				break;
			case "2":
				coleccion = Constantes.EPISODIOS;
				break;
			default :
				System.err.println("Cancelado");
				coleccion = "";
				break;
				
			}
		}
		
		if(!coleccion.equals("")){
			BasicDBObject obj = DBHelper.dataBase.get(coleccion, new BasicDBObject("actores.actor", nombreActor));
			ArrayList<BasicDBObject> listaActores = (ArrayList<BasicDBObject>) obj.get("actores");
			Iterator<BasicDBObject> it = listaActores.iterator();
			boolean encontrado = false;
			BasicDBObject actor;
			while(it.hasNext() && !encontrado){
				actor = it.next();
				if(actor.get("actor").equals(nombreActor)){
					System.out.println(actor.get("personaje"));
					encontrado = true;
				}
			}
		}
	}
	
	
	
	// ***********************************************************************
	// 9. Consultar las pel�culas grabadas en un determinado pa�s.
	// ***********************************************************************
	
	/**
	 * 9. Consultar las pel�culas grabadas en un determinado pa�s.
	 */
	public static void peliculasDeUnPais(String nombrePais){
		DBCursor cursor = DBHelper.dataBase.getAll(Constantes.PELICULAS, new BasicDBObject("paisesEnLosQueSeHaGrabado", nombrePais));
		DBObject obj;
		while(cursor.hasNext()){
			obj = cursor.next();
			System.out.println(obj.get("titulo"));
		}
	}
	
	
	
	// ***********************************************************************
	// 10. Conocer todas las valoraciones para una pel�cula, serie, temporada o episodio.
	// ***********************************************************************
	
	/**
	 * 10. Conocer todas las valoraciones para una pel�cula, serie, temporada o episodio.
	 */
	public static void muestraValoraciones(String coleccion, BasicDBObject obj){
		
		ArrayList<String> listaValoraciones = (ArrayList<String>) DBHelper.dataBase.getCampo("valoraciones", coleccion, obj);
		Iterator<String> it = listaValoraciones.iterator();
		String valoracion;
		while(it.hasNext()){
			valoracion = it.next();
			BasicDBObject objValoracion = DBHelper.dataBase.getById(Constantes.VALORACIONES, new ObjectId(valoracion));
			System.out.println();
			System.out.println(objValoracion.get("titulo"));
			System.out.println(objValoracion.get("puntuacion"));
			System.out.println(objValoracion.get("mensaje"));
		}
	}
	
}
