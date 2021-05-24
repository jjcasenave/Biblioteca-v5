package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.*;

public interface IFuenteDatos {
	
	IAlumnos crearAlumnos();
	ILibros crearLibros();
	IPrestamos crearPrestamos();
	
}