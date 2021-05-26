package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Libros implements ILibros {
	
	private static final String COLECCION = "libros";
	
	private MongoCollection<Document> coleccionLibros;

	@Override
	public void comenzar() {
		coleccionLibros = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Libro> get() {
		List<Libro> libros = new ArrayList<>();
		for (Document documentoLibro : coleccionLibros.find().sort(MongoDB.getCriterioOrdenacionLibro())) {
			libros.add(MongoDB.getLibro(documentoLibro));
		}
		return libros;
	}

	@Override
	public int getTamano() {
		return (int)coleccionLibros.countDocuments();
	}

	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		if (buscar(libro) == null) {
			coleccionLibros.insertOne(MongoDB.getDocumento(libro));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese tÃ­tulo y autor.");
		}	
	}

	@Override
	public Libro buscar(Libro libro) {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");
		}
		Document documentoLibro = coleccionLibros.find().filter(MongoDB.getCriterioBusquedaLibro(libro)).first();
		return MongoDB.getLibro(documentoLibro);
	}

	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");
		}
		if (buscar(libro) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningÃºn libro con ese tÃ­tulo y autor.");
		} else {
			coleccionLibros.deleteOne(MongoDB.getCriterioBusquedaLibro(libro));
		}
	}

}