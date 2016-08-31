// SGDI - Pr3 - Adrián Rabadán Jurado, Teresa Rodríguez Ferreira - Los alumnos declaramos que el código de la presente práctica está desarrollado exclusivamente por nosotros.

package sgdi.pr3.grupo10.situacion1;

import java.util.ArrayList;
import java.util.Scanner;

import sgdi.pr3.grupo10.situacion1.colecciones.*;

import com.mongodb.BasicDBObject;


public class Main {
	
	public static boolean ejecutaComando(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe un comando de los siguientes:");
		System.out.println("1 -> Insertar");
		System.out.println("2 -> Modificar");
		System.out.println("3 -> Borrar");
		System.out.println("4 -> Añadir una valoración a una película, serie, temporada o episodio");
		System.out.println("5 -> Conocer el nombre de los actores que aparecen en una película o serie de TV");
		System.out.println("6 -> Conocer el título de las películas o series de TV en las que  aparece un determinado actor");
		System.out.println("7 -> Dado el nombre de un actor y el título de una película o episodio de serie de TV, encontrar el personaje que interpreta");
		System.out.println("8 -> Consultar las películas grabadas en un determinado país");
		System.out.println("9 -> Conocer todas las valoraciones para una película, serie, temporada o episodio");
		System.out.println("0 -> Salir");
		System.out.print(">");
		String comando = sc.nextLine();
		
		switch(comando){
		
		case "1":
			System.out.println("¿ Qué tipo ?");
			System.out.println("1 -> Película");
			System.out.println("2 -> Serie");
			System.out.println("3 -> Temporada");
			System.out.println("4 -> Episodio");
			System.out.println("0 -> Cancelar");
			System.out.print(">");
			boolean correctoInsertar = false;
			
			while(!correctoInsertar){
				comando = sc.nextLine();
				switch(comando){
				case "1":
					DBHelper.insertaPelicula(new Pelicula().pideDatos());
					correctoInsertar = true;
					break;
				case "2":
					DBHelper.insertaSerie(new Serie().pideDatos());
					correctoInsertar = true;
					break;
				case "3":
					Temporada t = new Temporada();
					BasicDBObject objT = null;
					try {
						objT = t.pideDatos();
					} catch (Exception e2) {e2.printStackTrace();}
					DBHelper.insertaTemporada(objT, t.nombreSerie);
					correctoInsertar = true;
					break;
				case "4":
					Episodio e = new Episodio();
					BasicDBObject objE = null;
					try {
						objE = e.pideDatos();
					} catch (Exception e1) {e1.printStackTrace();}
					DBHelper.insertaEpisodio(objE, e.idTemporada);
					correctoInsertar = true;
					break;
				case "0":
					correctoInsertar = true;
					break;
				default:
					System.err.println("No reconozco ese comando");
					correctoInsertar = false;
					break;
				}
			}
			
			break;
			
		case "2":
			System.out.println("¿ Qué tipo ?");
			System.out.println("1 -> Película");
			System.out.println("2 -> Serie");
			System.out.println("3 -> Temporada");
			System.out.println("4 -> Episodio");
			System.out.println("0 -> Cancelar");
			System.out.print(">");
			
			boolean correctoModificar = false;
			
			while(!correctoModificar){
				comando = sc.nextLine();
				switch(comando){
				case "1":
					System.out.println("Se modificará la película con el mismo título");
					Pelicula p = new Pelicula();
					BasicDBObject objP = p.pideDatos();
					DBHelper.modificaPelicula(new BasicDBObject("titulo", p.titulo), objP);
					correctoModificar = true;
					break;
				case "2":
					Serie s = new Serie();
					BasicDBObject objS = s.pideDatos();
					DBHelper.modificaSerie(new BasicDBObject("titulo", s.titulo), objS);
					correctoModificar = true;
					break;
				case "3":
					Temporada t = new Temporada();
					BasicDBObject objT = null;
					try {
						objT = t.pideDatos();
					} catch (Exception e1) {e1.printStackTrace();}
					DBHelper.modificaTemporada(t.objTemporada, objT);
					correctoModificar = true;
					break;
				case "4":
					Episodio e = new Episodio();
					BasicDBObject objE = null;
					try {
						objE = e.pideDatos();
					} catch (Exception e1) {e1.printStackTrace();}
					DBHelper.modificaEpisodio(e.objEpisodio, objE);
					correctoModificar = true;
					break;
				case "0":
					correctoModificar = true;
					break;
				default:
					System.err.println("No reconozco ese comando");
					correctoModificar = false;
					break;
				}
			}
			
			break;
			
		case "3":
			System.out.println("¿ Qué tipo ?");
			System.out.println("1 -> Película");
			System.out.println("2 -> Serie");
			System.out.println("3 -> Temporada");
			System.out.println("4 -> Episodio");
			System.out.println("0 -> Cancelar");
			System.out.print(">");
			
			boolean correctoBorrar = false;
			
			while(!correctoBorrar){
				comando = sc.nextLine();
				switch(comando){
				case "1":
					DBHelper.borraPelicula(new Pelicula().pideBasico());
					correctoBorrar = true;
					break;
				case "2":
					DBHelper.borraSerie(new Serie().pideBasico());
					correctoBorrar = true;
					break;
				case "3":
					Temporada t = new Temporada();
					BasicDBObject objT = t.pideBasico();
					DBHelper.borraTemporada(objT, t.nombreSerie);
					correctoBorrar = true;
					break;
				case "4":
					DBHelper.borraEpisodio(new Episodio().pideBasico());
					correctoBorrar = true;
					break;
				case "0":
					correctoBorrar = true;
					break;
				default:
					System.err.println("No reconozco ese comando");
					correctoBorrar = false;
					break;
				}
			}
			
			break;
			
		case "4":
			Valoracion v = new Valoracion();
			BasicDBObject objV = v.pideDatos();
			DBHelper.meteValoracion(v.coleccion, v.target, objV);
			break;
			
		case "5":
			System.out.println("Introduce 1 si deseas película, 2 si quieres serie, 0 para salir");
			String coleccion = "";
			boolean valido = false;
			Scanner scan = new Scanner(System.in);
			while(!valido){
				switch(scan.nextLine()){
				case "1":
					coleccion = Constantes.PELICULAS;
					valido = true;
					break;
				case "2":
					coleccion = Constantes.EPISODIOS;
					valido = true;
					break;
				case "0":
					valido = true;
					break;
				default:
					valido = false;
					break;
				}
			}
			System.out.print("Introduce el título: ");
			String titulo = scan.nextLine();
			
			DBHelper.nombreActores(coleccion, titulo);
			break;
			
		case "6":
			System.out.println("Introduce 1 si deseas película, 2 si quieres serie, 0 para salir");
			String col = "";
			boolean ok = false;
			Scanner scn = new Scanner(System.in);
			while(!ok){
				switch(scn.nextLine()){
				case "1":
					col = Constantes.PELICULAS;
					ok = true;
					break;
				case "2":
					col = Constantes.EPISODIOS;
					ok = true;
					break;
				case "0":
					ok = true;
					break;
				default:
					ok = false;
					break;
				}
			}
			System.out.print("Introduce el nomrbe: ");
			String nombre = scn.nextLine();
			
			DBHelper.peliOSerieApareceActor(col, nombre);
			break;
		
		case "7":
			Scanner scnn = new Scanner(System.in);
			System.out.print("Introduce el nomrbe del actor: ");
			String nombreActor = scnn.nextLine();
			System.out.print("Introduce el título de la serie o película: ");
			String tituloSP = scnn.nextLine();
			
			DBHelper.encuentraPersonaje(nombreActor, tituloSP);
			break;
		
		case "8":
			Scanner s = new Scanner(System.in);
			System.out.print("Introduce el nomrbe del país: ");
			String nombrePais = s.nextLine();
			DBHelper.peliculasDeUnPais(nombrePais);
			break;
		
		case "9":
			System.out.println("¿ Qué tipo ?");
			System.out.println("1 -> Película");
			System.out.println("2 -> Serie");
			System.out.println("3 -> Temporada");
			System.out.println("4 -> Episodio");
			System.out.println("0 -> Cancelar");
			System.out.print(">");
			
			BasicDBObject obj = null;
			String c = "";
			boolean correcto = false;
			Scanner scanner = new Scanner(System.in);
			while(!correcto){
				switch(scanner.nextLine()){
				case "1":
					Pelicula pelicula = new Pelicula();
					obj = pelicula.pideBasico();
					c = Constantes.PELICULAS;
					correcto = true;
					break;
				case "2":
					Serie serie = new Serie();
					obj = serie.pideBasico();
					c = Constantes.SERIES;
					correcto = true;
					break;
				case "3":
					Temporada temporada = new Temporada();
					obj = temporada.pideBasico();
					c = Constantes.TEMPORADAS;
					correcto = true;
					break;
				case "4":
					Episodio episodio = new Episodio();
					obj = episodio.pideBasico();
					c = Constantes.EPISODIOS;
					correcto = true;
					break;
				case "0":
					correcto = true;
					break;
				default:
					System.err.println("No reconozco ese comando");
					correcto = false;
					break;
				}
			}
			
			DBHelper.muestraValoraciones(c, obj);
			break;
			
		case "0":
			return false;
			
		default:
			System.err.println("No reconozco ese comando");
			break;
		
		}
		
		return true;
		
	}

	public static void rellenaBaseDatos(){
		
		ArrayList<String> directoresPelicula = new ArrayList<String>();
		directoresPelicula.add("Luc Besson");
		ArrayList<String> PaisesPelicula = new ArrayList<String>();
		PaisesPelicula.add("Francia");
		ArrayList<BasicDBObject> actoresPelicula = new ArrayList<BasicDBObject>();
		actoresPelicula.add(new BasicDBObject("actor", "Bruce Willis").append("personaje", "Korben Dallas"));
		actoresPelicula.add(new BasicDBObject("actor", "Gary Oldman").append("personaje", "Jean-Baptiste Emanuel Zorg"));
		actoresPelicula.add(new BasicDBObject("actor", "Ian Holm").append("personaje", "Sacerdote Vito Cornelius"));
		actoresPelicula.add(new BasicDBObject("actor", "Milla Jovovich").append("personaje", "Leeloo"));
		actoresPelicula.add(new BasicDBObject("actor", "Chris Tucker").append("personaje", "Ruby Rhod"));
		actoresPelicula.add(new BasicDBObject("actor", "Luke Perry").append("personaje", "Billy"));
		ArrayList<String> guionistasPelicula = new ArrayList<String>();
		guionistasPelicula.add("Luc Besson");
		guionistasPelicula.add("Robert Mark Kamen");
		DBHelper.insertaPelicula(new BasicDBObject("titulo", "El quinto elemento")
			.append("añoDeEstreno", 1997)
			.append("directores", directoresPelicula)
			.append("paisesEnLosQueSeHaGrabado", PaisesPelicula)
			.append("sinopsis", "Cada 5.000 años se abre una puerta entre dos dimensiones. En una dimensión existe el Universo y la vida. En la otra dimensión existe un elemento que no está hecho ni de tierra, ni de fuego, ni de aire, ni de agua, sino que es una anti-energía, la anti-vida: es el quinto elemento.")
			.append("actores", actoresPelicula)
			.append("valoraciones", new ArrayList<BasicDBObject>())
				);

		BasicDBObject peli = DBHelper.dataBase.get(Constantes.PELICULAS, new BasicDBObject("titulo", "El quinto elemento"));
		DBHelper.meteValoracion(Constantes.PELICULAS, peli, new BasicDBObject("titulo", "Valoración Teresa")
			.append("puntuacion", 9)
			.append("mensaje", "Verde Superverde!")
				);
		
		peli = DBHelper.dataBase.get(Constantes.PELICULAS, new BasicDBObject("titulo", "El quinto elemento"));
		DBHelper.meteValoracion(Constantes.PELICULAS, peli, new BasicDBObject("titulo", "Valoración Adrián")
		.append("puntuacion", 10)
		.append("mensaje", "Mola mucho ^^, Leeloo multipass.")
			);
		
		DBHelper.insertaSerie(new BasicDBObject("titulo", "Big Bang Theory")
			.append("añoDeInicio", 2007)
			.append("sinopsis", "Leonard y Sheldon son dos cerebros privilegiados que comparten piso. Aunque ambos se han doctorado en física y son capaces de calcular las probabilidades sobre la existencia de universos paralelos, no tienen ni la menor idea de cómo relacionarse con el resto del mundo, especialmente con las chicas. Penny, una vecina recién llegada que se instala en el piso de enfrente, es el polo opuesto a los dos amigos, de modo que su llegada altera la tranquila vida sentimental de Leonard y el desorden obsesivo-compulsivo de Sheldon. Penny es una chica tan normal que también perturba al resto de la pandilla: mientras que a Wolowitz (Simon Helberg) se le disparan las hormonas cada vez que la ve, Rajesh (Kunal Nayyar) se muestra incapaz de emitir un solo sonido en su presencia.")
			.append("temporadas", new ArrayList<BasicDBObject>())
			.append("valoraciones", new ArrayList<BasicDBObject>())
				);

		BasicDBObject temporada = new BasicDBObject("titulo", "Temporada 1")
		.append("añoDeEstereno", 2007)
		.append("sinopsis", "Temporada 1 de Big Bang Theory")
		.append("episodios", new ArrayList<BasicDBObject>())
		.append("valoraciones", new ArrayList<BasicDBObject>());
		DBHelper.insertaTemporada(temporada, 
				"Big Bang Theory");
		
		ArrayList<BasicDBObject> actoresEpisodio = new ArrayList<BasicDBObject>();
		actoresEpisodio.add(new BasicDBObject("actor", "Johnny Galecki").append("personaje", "Leonard Leakey Hofstadter"));
		actoresEpisodio.add(new BasicDBObject("actor", "Jim Parsons").append("personaje", "Sheldon Cooper"));
		actoresEpisodio.add(new BasicDBObject("actor", "Kaley Cuoco").append("personaje", "Penny"));
		actoresEpisodio.add(new BasicDBObject("actor", "Simon Helberg").append("personaje", "Howard Wolowitz"));
		actoresEpisodio.add(new BasicDBObject("actor", "Kunal Nayyar").append("personaje", "Raj Koothrappali"));
		ArrayList<String> guionistasEpisodio = new ArrayList<String>();
		guionistasEpisodio.add("Chuck Lorre");
		guionistasEpisodio.add("Bill Prady");
		ArrayList<String> directoresEpisodio = new ArrayList<String>();
		directoresEpisodio.add("Mark Cendrowski");
		DBHelper.insertaEpisodio(new BasicDBObject("titulo", "Piloto")
			.append("sinopsis", "Los brillantes físicos Leonard y Sheldon son compañeros de piso y conocen a su nueva vecina Penny, quien les enseña tantas cosas sobre la vida como ellos saben de la ciencia.")
			.append("fechaDeEmision ", "24/09/2007")
			.append("nombreSerie", "Big Bang Theory")
			.append("actores", actoresEpisodio)
			.append("guionistas", guionistasEpisodio)
			.append("directores", directoresEpisodio)
			.append("valoraciones", new ArrayList<BasicDBObject>()),  // Lista vacía de valoraciones, no hay valoraciones.
				DBHelper.dataBase.getCampo("_id", Constantes.TEMPORADAS, temporada).toString());
		
	}
	
	public static void vaciaBaseDatos(){
		DBHelper.dataBase.removeAll(Constantes.EPISODIOS);
		DBHelper.dataBase.removeAll(Constantes.PELICULAS);
		DBHelper.dataBase.removeAll(Constantes.SERIES);
		DBHelper.dataBase.removeAll(Constantes.TEMPORADAS);
		DBHelper.dataBase.removeAll(Constantes.VALORACIONES);
	}
	

	
	public static void main(String[] args){
			
		DBHelper.changeDataBase("sgdi_pr3_grupo10_situacion1");
		System.out.println();
		vaciaBaseDatos();
		rellenaBaseDatos();
		DBHelper.dataBase.showAll(Constantes.PELICULAS);
		DBHelper.dataBase.showAll(Constantes.VALORACIONES);
		DBHelper.dataBase.showAll(Constantes.SERIES);
		DBHelper.dataBase.showAll(Constantes.TEMPORADAS);
		DBHelper.dataBase.showAll(Constantes.EPISODIOS);
		System.out.println();
		System.out.println();
		
		
		while(ejecutaComando()){
			System.out.println();
			DBHelper.dataBase.showAll(Constantes.PELICULAS);
			DBHelper.dataBase.showAll(Constantes.VALORACIONES);
			DBHelper.dataBase.showAll(Constantes.SERIES);
			DBHelper.dataBase.showAll(Constantes.TEMPORADAS);
			DBHelper.dataBase.showAll(Constantes.EPISODIOS);
			System.out.println();
		}
		
	}
	
}
