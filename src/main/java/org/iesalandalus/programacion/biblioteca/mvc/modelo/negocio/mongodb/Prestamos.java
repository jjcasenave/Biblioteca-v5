package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb;

import static com.mongodb.client.model.Updates.set;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Prestamos implements IPrestamos {

	private static final String COLECCION = "prestamos";
	
	private MongoCollection<Document> coleccionPrestamos;

	@Override
	public void comenzar() {
		coleccionPrestamos = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Prestamo> get() {
		List<Prestamo> prestamos = new ArrayList<>();
		for (Document documentoPrestamo : coleccionPrestamos.find().sort(MongoDB.getCriterioOrdenacionPrestamo())) {
			prestamos.add(MongoDB.getPrestamo(documentoPrestamo));
		}
		return prestamos;
	}

	@Override
	public int getTamano() {
		return (int)coleccionPrestamos.countDocuments();
	}

	@Override
	public List<Prestamo> get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		List<Prestamo> prestamosAlumno = new ArrayList<>();
		for (Document documentoPrestamo : coleccionPrestamos.find(MongoDB.getCriterioBusquedaPrestamo(alumno)).sort(MongoDB.getCriterioOrdenacionPrestamo())) {
			prestamosAlumno.add(MongoDB.getPrestamo(documentoPrestamo));
		}
		return prestamosAlumno;
	}

	@Override
	public List<Prestamo> get(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		List<Prestamo> prestamosLibro = new ArrayList<>();
		for (Document documentoPrestamo : coleccionPrestamos.find(MongoDB.getCriterioBusquedaPrestamo(libro)).sort(MongoDB.getCriterioOrdenacionPrestamo())) {
			prestamosLibro.add(MongoDB.getPrestamo(documentoPrestamo));
		}
		return prestamosLibro;
	}

	@Override
	public List<Prestamo> get(LocalDate fecha) {
		if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		List<Prestamo> prestamosMensuales = new ArrayList<>();
		for (Document documentoPrestamo : coleccionPrestamos.find(MongoDB.getCriterioBusquedaPrestamo(fecha)).sort(MongoDB.getCriterioOrdenacionPrestamo())) {
			prestamosMensuales.add(MongoDB.getPrestamo(documentoPrestamo));
		}
		return prestamosMensuales;
	}

	@Override
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer> estadisticasMesualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMesualesPorCurso.put(cursoAlumno, estadisticasMesualesPorCurso.get(cursoAlumno) + prestamo.getPuntos());
		}
		return estadisticasMesualesPorCurso;
	}
	
	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> estadisticasMesualesPorCurso = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			estadisticasMesualesPorCurso.put(curso, 0);
		}
		return estadisticasMesualesPorCurso;
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un prÃ©stamo nulo.");
		}
		if (buscar(prestamo) == null) {
			coleccionPrestamos.insertOne(MongoDB.getDocumento(prestamo));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un prÃ©stamo igual.");
		}
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un prÃ©stamo nulo.");
		}
		if (buscar(prestamo) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningÃºn prÃ©stamo igual.");
		} else {
			coleccionPrestamos.updateOne(MongoDB.getCriterioBusquedaPrestamo(prestamo), 
					set(MongoDB.FECHA_DEVOLUCION, fechaDevolucion.format(MongoDB.FORMATO_FECHA)));
		}
	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un prÃ©stamo nulo.");
		}
		Document documentoPrestamo = coleccionPrestamos.find().filter(MongoDB.getCriterioBusquedaPrestamo(prestamo)).first();
		return MongoDB.getPrestamo(documentoPrestamo);
	}

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un prÃ©stamo nulo.");
		}
		if (buscar(prestamo) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningÃºn prÃ©stamo igual.");
		} else {
			coleccionPrestamos.deleteOne(MongoDB.getCriterioBusquedaPrestamo(prestamo));
		}
	}

}