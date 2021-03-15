package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.*;


public class FactoriaFuenteDatosFicheros implements IFuenteDatos {

	public IAlumnos crearAlumnos() {
		return new Alumnos();
	}
	public ILibros crearLibros() {
		return new Libros();
	}
	public IPrestamos crearPrestamos() {
		return new Prestamos();
	}
	
}