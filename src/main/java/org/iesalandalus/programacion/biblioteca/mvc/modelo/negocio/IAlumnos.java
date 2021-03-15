package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;

public interface IAlumnos {

	List<Alumno> get() throws IllegalArgumentException, NullPointerException;

	int getTamano();

	void insertar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	Alumno buscar(Alumno alumno) throws IllegalArgumentException, NullPointerException;

	void borrar(Alumno alumno) throws OperationNotSupportedException;

}