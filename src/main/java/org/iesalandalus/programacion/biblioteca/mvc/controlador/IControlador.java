package org.iesalandalus.programacion.biblioteca.mvc.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public interface IControlador {

	void comenzar();

	void terminar();

	void insertar(Alumno alumno) throws OperationNotSupportedException;

	void insertar(Libro libro) throws OperationNotSupportedException;

	void prestar(Prestamo prestamo) throws OperationNotSupportedException;

	void devolver(Prestamo prestamo, LocalDate fechaDevolucion)
			throws OperationNotSupportedException, IllegalArgumentException, NullPointerException;

	Alumno buscar(Alumno alumno);

	Libro buscar(Libro libro);

	Prestamo buscar(Prestamo prestamo);

	void borrar(Alumno alumno) throws OperationNotSupportedException;

	void borrar(Libro libro) throws OperationNotSupportedException;

	void borrar(Prestamo prestamo) throws OperationNotSupportedException;

	List<Alumno> getAlumnos();

	List<Libro> getLibros();

	List<Prestamo> getPrestamos();

	List<Prestamo> getPrestamos(Alumno alumno);

	List<Prestamo> getPrestamos(Libro libro);

	List<Prestamo> getPrestamos(LocalDate fecha);

	Map<Curso, Integer> getEstadisticasMensualPorCurso(LocalDate fecha);

}