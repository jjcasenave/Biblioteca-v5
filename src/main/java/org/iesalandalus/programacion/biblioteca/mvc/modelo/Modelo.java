package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class Modelo implements IModelo {

	private IPrestamos prestamos;
	private ILibros libros;
	private IAlumnos alumnos;
	
	public Modelo(IFuenteDatos fuenteDatos) {
		prestamos = fuenteDatos.crearPrestamos();
		libros = fuenteDatos.crearLibros();
		alumnos = fuenteDatos.crearAlumnos();
	}
	
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException , IllegalArgumentException, NullPointerException {
		if (alumno == null ) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		alumnos.insertar(alumno);
	}
	
	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (libro == null ) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		libros.insertar(libro);
	}
	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (prestamo == null ) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		if (alumnos.buscar(prestamo.getAlumno()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el alumno del préstamo.");
		} 
		if (libros.buscar(prestamo.getLibro()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el libro del préstamo.");
		} else {
			prestamos.prestar(prestamo);
		}
	}
	
	@Override
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (prestamos.buscar(prestamo) == null) {
			throw new OperationNotSupportedException("ERROR: No se puede devolver un préstamo no prestado.");
		}
		prestamos.devolver(prestamo, fechaDevolucion);
	}
	
	@Override
	public  Alumno buscar(Alumno alumno) throws IllegalArgumentException, NullPointerException {
		return alumnos.buscar(alumno);
	}
	
	@Override
	public Libro buscar(Libro libro) throws IllegalArgumentException, NullPointerException {
		return libros.buscar(libro);
	}
	
	@Override
	public Prestamo buscar(Prestamo prestamo) throws IllegalArgumentException, NullPointerException {
		return prestamos.buscar(prestamo);
	}
	
	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.buscar(alumno);
		List<Prestamo> alumnosParaBorrar = prestamos.get(alumno);
		for (Prestamo prestamo : alumnosParaBorrar) {
			prestamos.borrar(prestamo);
		}
		alumnos.borrar(alumno);
	}
	
	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		libros.buscar(libro);
		List<Prestamo> librosParaBorrar = prestamos.get(libro);
		for (Prestamo prestamo : librosParaBorrar) {
			prestamos.borrar(prestamo);
		}
		libros.borrar(libro);
	}
	
	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		prestamos.borrar(prestamo);
	}
	
	@Override
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}
	
	@Override
	public List<Libro> getLibros() {
		return libros.get();
	}
	
	@Override
	public List<Prestamo> getPrestamos() {
		return prestamos.get();
	}
	
	@Override
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return prestamos.get(alumno);
	}
	
	@Override
	public List<Prestamo> getPrestamos(Libro libro) {
		return prestamos.get(libro);
	}
	
	@Override
	public List<Prestamo> getPrestamos(LocalDate fechaPrestamo) {
		return prestamos.get(fechaPrestamo);
	}
	
	@Override
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		return prestamos.getEstadisticaMensualPorCurso(fecha);
	}
}