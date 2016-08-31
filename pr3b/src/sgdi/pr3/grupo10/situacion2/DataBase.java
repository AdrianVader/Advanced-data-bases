// SGDI - Pr3 - Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira - Los alumnos declaramos que el c�digo de la presente pr�ctica est� desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion2;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * Conecta con la base de datos y
 * proporciona m�todos para interactuar con ella
 * @author Adri�n Rabad�n Jurado, Teresa Rodr�guez Ferreira
 */
public class DataBase {

	private MongoClient client;
	private DB db;
	
	/**
	 * Constructor para la base de datos
	 */
	public DataBase(String dbName){
		
		try {
			this.client = new MongoClient();
		} catch (UnknownHostException e) {e.printStackTrace();}
		this.db = this.client.getDB(dbName);
		System.out.println("Conectado a la base de datos.");
        
	}
	
	
	/**
	 *  Inserta un objeto en la colecci�n
	 */
	public void insert(String collection, BasicDBObject obj){
		DBCollection coll = this.db.getCollection(collection);
		
		coll.insert(obj);
	}
	
	/**
	 * Actualiza un objeto entero en la colecci�n
	 */
	public void update(String collection, BasicDBObject objOld, BasicDBObject objNew){
		DBCollection coll = this.db.getCollection(collection);
		
		coll.update(objOld, objNew);
	}
	
	/**
	 * Borra un objeto de la colecci�n
	 */
	public void remove(String collection, BasicDBObject obj){
		DBCollection coll = this.db.getCollection(collection);
		
		coll.remove(obj);
	}

	/**
	 * Vac�a una colecci�n, borrando su contenido
	 */
	public void removeAll(String collection){
		DBCollection coll = this.db.getCollection(collection);
		
		coll.drop();
	}

	/**
	 * Muestra los nombres de todas las colecciones, o un mensaje si no existen colecciones
	 */
	public void printAllCollectionNames(){
		Set<String> colls = this.db.getCollectionNames();

		if(colls.size() > 0){
			for (String s : colls) {
			    System.out.println(s);
			}
		}else{System.err.println("No hay colecciones!");}
	}
	
	/**
	 * Muestra todos los objetos (muestra el objeto entero) de una colecci�n
	 */
	public void showAll(String collection){
		DBCollection coll = this.db.getCollection(collection);
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
	}
	
	/**
	 * Muestra un objeto de la colecci�n
	 */
	public void show(String collection, DBObject obj){
		DBCollection coll = this.db.getCollection(collection);
		DBCursor cursor = coll.find(obj);
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
	}
	
	/**
	 * @return True si existe el objeto en la colecci�n
	 */
	public boolean findOne(String collection, DBObject obj){
		DBCollection coll = this.db.getCollection(collection);
		DBObject object = coll.findOne(obj);
		if(object != null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @return El campo indicado del objeto de la colecci�n
	 */
	public Object getCampo(String campo, String collection, DBObject obj) {
		DBCollection coll = this.db.getCollection(collection);
		DBObject object = coll.findOne(obj);
		return object.get(campo);
	}

	/**
	 * @return El objeto de la colecci�n
	 */
	public BasicDBObject get(String collection, BasicDBObject obj) {
		DBCollection coll = this.db.getCollection(collection);
		DBObject object = coll.findOne(obj);
		return (BasicDBObject) object;
	}
	
	/**
	 * @return El objeto de la colecci�n dado su _id
	 */
	public BasicDBObject getById(String collection, ObjectId objId) {
		DBCollection coll = this.db.getCollection(collection);
		DBObject object = coll.findOne(objId);
		return (BasicDBObject) object;
	}
	
	/**
	 * @return Un cursor con todos los objetos de la colecci�n
	 */
	public DBCursor getAll(String collection, BasicDBObject obj) {
		DBCollection coll = this.db.getCollection(collection);
		DBCursor cursor = coll.find(obj);
		return cursor;
	}
	
	/**
	 * 
	 * Elimina todas las valoraciones de una pel�cula/serie/temporada/episodio
	 */
	public void borraValoraciones(String collection, BasicDBObject obj){
		
		ArrayList<String> arrayIdValoraciones = (ArrayList<String>) DBHelper.dataBase.getCampo("valoraciones", collection, obj);
		BasicDBObject valoracion;
		
		Iterator<String> itValoraciones = arrayIdValoraciones.iterator();
		while(itValoraciones.hasNext()){
			valoracion = DBHelper.dataBase.getById(Constantes.VALORACIONES, new ObjectId(itValoraciones.next()));
			DBHelper.dataBase.remove(Constantes.VALORACIONES, valoracion);
		}
	}
	
}
