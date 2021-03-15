package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class Prestamos implements IPrestamos {

	private List<Prestamo> coleccionPrestamos;

	public Prestamos() throws IllegalArgumentException, NullPointerException {
		coleccionPrestamos = new ArrayList<>();
	}

	public List<Prestamo> get() {
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		List<Prestamo> copiaPrestamos = copiaProfundaPrestamos();
		copiaPrestamos.sort(comparadorPrestamo);
		return copiaPrestamos;
		
	}

	private List<Prestamo> copiaProfundaPrestamos() throws IllegalArgumentException, NullPointerException{
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}

	public int getTamano() {
		return coleccionPrestamos.size();
	}
	
	public List<Prestamo> get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getAlumno().equals(alumno)) {
				copiaPrestamos.add(new Prestamo(prestamo));
			}
		}
		copiaPrestamos.sort(comparadorPrestamo);
		return copiaPrestamos;
	}

	public List<Prestamo> get(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno);
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getLibro().equals(libro)) {
				copiaPrestamos.add(new Prestamo(prestamo));
			}
		}
		copiaPrestamos.sort(comparadorPrestamo);
		return copiaPrestamos;
	}

	public List<Prestamo> get(LocalDate fecha) {
		if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (mismoMes(fecha, prestamo.getFechaPrestamo())) {
				copiaPrestamos.add(new Prestamo(prestamo));
			}
		}
		copiaPrestamos.sort(comparadorPrestamo);
		return copiaPrestamos;
	}
	
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer> estadisticasMensualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMensualesPorCurso.put(cursoAlumno, estadisticasMensualesPorCurso.get(cursoAlumno) + prestamo.getPuntos());
		}
		return estadisticasMensualesPorCurso;
	}
	
	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> mapa = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			mapa.put(curso, 0);
		}
		return mapa;
	}

	private boolean mismoMes(LocalDate fechaUno, LocalDate fechaDos) {
		boolean fechaIgual = false;
		int anoUno = fechaUno.getYear();
		int anoDos = fechaDos.getYear();
		if ( anoUno==anoDos && fechaUno.getMonth().equals(fechaDos.getMonth())) {
			fechaIgual = true;
		}
		return fechaIgual;
	}

	

	public void prestar(Prestamo prestamo) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			coleccionPrestamos.add(new Prestamo(prestamo));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");
		}
	}

	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");
		}
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.get(indice).devolver(fechaDevolucion);
		}

	}

	public Prestamo buscar(Prestamo prestamo) throws IllegalArgumentException, NullPointerException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == - 1) {
			return null;
		} else {
			return new Prestamo(coleccionPrestamos.get(indice));
		}
	}

	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.remove(indice);
		}
	}
}