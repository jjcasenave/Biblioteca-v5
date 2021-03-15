package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;

public class Libros implements ILibros {

	private List<Libro> coleccionLibros;
	
	public Libros() throws IllegalArgumentException, NullPointerException {
		coleccionLibros = new ArrayList<>();
	}
	
	public List<Libro> get() {
		List<Libro> librosOrdenados = copiaProfundaLibros();
		librosOrdenados.sort(Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor));
		return librosOrdenados;
	}
	private List<Libro> copiaProfundaLibros() throws NullPointerException, IllegalArgumentException {
		List<Libro> copiaLibros = new ArrayList<>();
		for (Libro libro : coleccionLibros) {
			if (libro instanceof LibroEscrito) {
				copiaLibros.add(new LibroEscrito((LibroEscrito) libro));
			} else if (libro instanceof AudioLibro) {
				copiaLibros.add(new AudioLibro((AudioLibro) libro));
			}
		}
		return copiaLibros;
	}
	
	public int getTamano() {
		return coleccionLibros.size();
	}
	
	public void insertar(Libro libro) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (libro == null) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			if (libro instanceof LibroEscrito) {
				coleccionLibros.add(new LibroEscrito((LibroEscrito) libro));
			} else if (libro instanceof AudioLibro) {
				coleccionLibros.add(new AudioLibro((AudioLibro) libro));
			}
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");
		}
	}
	
	public Libro buscar(Libro libro) throws IllegalArgumentException, NullPointerException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			return null;
		} else {
			if (libro instanceof LibroEscrito) {
				return new LibroEscrito((LibroEscrito) coleccionLibros.get(indice));
			} else {
				return new AudioLibro((AudioLibro) coleccionLibros.get(indice));
			}
		}
	}
	
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");
		} else {
			coleccionLibros.remove(indice);
		}
		
	}
}