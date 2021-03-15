package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public interface IModelo {

	void insertar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	void insertar(Libro libro) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	void prestar(Prestamo prestamo)
			throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	void devolver(Prestamo prestamo, LocalDate fechaDevolucion)
			throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	Alumno buscar(Alumno alumno) throws IllegalArgumentException, NullPointerException;

	Libro buscar(Libro libro) throws IllegalArgumentException, NullPointerException;

	Prestamo buscar(Prestamo prestamo) throws IllegalArgumentException, NullPointerException;

	void borrar(Alumno alumno) throws OperationNotSupportedException;

	void borrar(Libro libro) throws OperationNotSupportedException;

	void borrar(Prestamo prestamo) throws OperationNotSupportedException;

	List<Alumno> getAlumnos();

	List<Libro> getLibros();

	List<Prestamo> getPrestamos();

	List<Prestamo> getPrestamos(Alumno alumno);

	List<Prestamo> getPrestamos(Libro libro);

	List<Prestamo> getPrestamos(LocalDate fechaPrestamo);

	Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha);

}