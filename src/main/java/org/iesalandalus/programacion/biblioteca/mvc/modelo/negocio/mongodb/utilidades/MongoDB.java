package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb.utilidades;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static final String SERVIDOR = "localhost";	
	private static final int PUERTO = 27017;
	private static final String BD = "biblioteca";
	private static final String USUARIO = "biblioteca";
	private static final String CONTRASENA = "biblioteca-2021";
	private static final String URI = String.format("mongodb://%s:%s@%s:%d/?authSource=%s&authMechanism=SCRAM-SHA-1", USUARIO, CONTRASENA, SERVIDOR, PUERTO, BD);
	
	public static final String ALUMNO = "alumno";
	public static final String NOMBRE = "nombre";
	public static final String CORREO = "correo";
	public static final String CURSO = "curso";
	
	public static final String LIBRO = "libro";
	public static final String TITULO = "titulo";
	public static final String AUTOR = "autor";
	public static final String NUMERO_PAGINAS = "numeroPaginas";
	public static final String DURACION = "duracion";
	
	public static final String PRESTAMO = "prestamo";
	public static final String ALUMNO_NOMBRE = ALUMNO + "." + NOMBRE;
	public static final String ALUMNO_CORREO = ALUMNO + "." + CORREO;
	public static final String ALUMNO_CURSO = ALUMNO + "." + CURSO;
	public static final String LIBRO_TITULO = LIBRO + "." + TITULO;
	public static final String LIBRO_AUTOR = LIBRO + "." + AUTOR;
	public static final String LIBRO_NUMERO_PAGINAS = LIBRO + "." + NUMERO_PAGINAS;
	public static final String LIBRO_DURACION = LIBRO + "." + DURACION;
	public static final String FECHA_PRESTAMO = "fechaPrestamo";
	public static final String FECHA_DEVOLUCION = "fechaDevolucion";
	
	private static MongoClient conexion = null;
	
	private MongoDB() {
		// Evitamos que se cree el constructor por defecto
	}
	
	public static MongoDatabase getBD() {
		if (conexion == null) {
			establecerConexion();
		}
		return conexion.getDatabase(BD);
	}
	
	private static MongoClient establecerConexion() {
	    Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    mongoLogger.setLevel(Level.SEVERE);
		if (conexion == null) {
			conexion = MongoClients.create(URI);
			System.out.println("ConexiÃ³n a MongoDB realizada correctamente.");	
		}
		return conexion;
	}
	
	public static void cerrarConexion() {
		if (conexion != null) {
			conexion.close();
			conexion = null;
			System.out.println("ConexiÃ³n a MongoDB cerrada.");
		}
	}
	
	public static Document getDocumento(Alumno alumno) {
		if (alumno == null) {
			return null;
		}
		String nombre = alumno.getNombre();
		String correo = alumno.getCorreo();
		Curso curso = alumno.getCurso();
		return new Document().append(NOMBRE, nombre).append(CORREO, correo).append(CURSO, curso.name());
	}
	
	public static Bson getCriterioBusquedaAlumno(Alumno alumno) {
		return eq(MongoDB.CORREO, alumno.getCorreo());
	}
	
	public static Document getCriterioOrdenacionAlumno() {
		return new Document().append(NOMBRE, 1);
	}

	public static Alumno getAlumno(Document documentoAlumno) {
		if (documentoAlumno == null) {
			return null;
		}
		return new Alumno(documentoAlumno.getString(NOMBRE), documentoAlumno.getString(CORREO), Curso.valueOf(documentoAlumno.getString(CURSO)));
	}
	
	public static Document getDocumento(Libro libro) {
		if (libro == null) {
			return null;
		}
		String titulo = libro.getTitulo();
		String autor = libro.getAutor();
		Document documentoLibro =  new Document().append(TITULO, titulo).append(AUTOR, autor);
		if (libro instanceof LibroEscrito) {
			documentoLibro.append(NUMERO_PAGINAS, ((LibroEscrito) libro).getNumPaginas());
		} else if (libro instanceof AudioLibro) {
			documentoLibro.append(DURACION, ((AudioLibro) libro).getDuracion());
		}
		return documentoLibro;
	}
	
	public static Bson getCriterioBusquedaLibro(Libro libro) {
		return and(eq(MongoDB.TITULO, libro.getTitulo()), eq(MongoDB.AUTOR, libro.getAutor()));
	}
	
	public static Document getCriterioOrdenacionLibro() {
		return new Document().append(TITULO, 1).append(AUTOR, 1);
	}

	public static Libro getLibro(Document documentoLibro) {
		if (documentoLibro == null) {
			return null;
		}
		Libro libro = null;
		if (documentoLibro.containsKey(NUMERO_PAGINAS)) {
			libro = new LibroEscrito(documentoLibro.getString(TITULO), documentoLibro.getString(AUTOR), documentoLibro.getInteger(NUMERO_PAGINAS));
		} else if (documentoLibro.containsKey(DURACION)) {
			libro = new AudioLibro(documentoLibro.getString(TITULO), documentoLibro.getString(AUTOR), documentoLibro.getInteger(DURACION));
		}
		return libro;
	}
	
	public static Document getDocumento(Prestamo prestamo) {
		if (prestamo == null) {
			return null;
		}
		Document dAlumno = getDocumento(prestamo.getAlumno());
		Document dLibro = getDocumento(prestamo.getLibro());
		String fechaPrestamo = prestamo.getFechaPrestamo().format(FORMATO_FECHA);
		Document documentoPrestamo =  new Document().append(ALUMNO, dAlumno).append(LIBRO, dLibro).append(FECHA_PRESTAMO, fechaPrestamo);
		if (prestamo.getFechaDevolucion() != null) {
			documentoPrestamo.append(FECHA_DEVOLUCION, prestamo.getFechaDevolucion().format(FORMATO_FECHA));
		} 
		return documentoPrestamo;
	}
	
	public static Document getCriterioOrdenacionPrestamo() {
		return new Document().append(FECHA_PRESTAMO, 1).append(ALUMNO_NOMBRE, 1).append(LIBRO_TITULO, 1).append(LIBRO_AUTOR, 1);
	}
	
	public static Bson getCriterioBusquedaPrestamo(Prestamo prestamo) {
		return and(eq(MongoDB.ALUMNO_CORREO, prestamo.getAlumno().getCorreo()),
				   eq(MongoDB.LIBRO_AUTOR, prestamo.getLibro().getAutor()),
				   eq(MongoDB.LIBRO_TITULO, prestamo.getLibro().getTitulo()));
	}
	
	public static Bson getCriterioBusquedaPrestamo(Alumno alumno) {
		return eq(MongoDB.ALUMNO_CORREO, alumno.getCorreo());
	}
	
	public static Bson getCriterioBusquedaPrestamo(Libro libro) {
		return and(eq(MongoDB.LIBRO_AUTOR, libro.getAutor()),
				   eq(MongoDB.LIBRO_TITULO, libro.getTitulo()));
	}
	
	public static Bson getCriterioBusquedaPrestamo(LocalDate fecha) {
		String mes = fecha.format(DateTimeFormatter.ofPattern("MM/yyyy"));
		return regex(MongoDB.FECHA_PRESTAMO, mes);
	}

	public static Prestamo getPrestamo(Document documentoPrestamo) {
		if (documentoPrestamo == null) {
			return null;
		}
		Alumno alumno = getAlumno((Document) documentoPrestamo.get(ALUMNO));
		Libro libro = getLibro((Document) documentoPrestamo.get(LIBRO));
		LocalDate fechaPrestamo = LocalDate.parse(documentoPrestamo.getString(FECHA_PRESTAMO), FORMATO_FECHA);
		Prestamo prestamo = new Prestamo(alumno, libro, fechaPrestamo);
		if (documentoPrestamo.containsKey(FECHA_DEVOLUCION)) {
			prestamo.devolver(LocalDate.parse(documentoPrestamo.getString(FECHA_DEVOLUCION), FORMATO_FECHA));
		}
		return prestamo;
	}
}
