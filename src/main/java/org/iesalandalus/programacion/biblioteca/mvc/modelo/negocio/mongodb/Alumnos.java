package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Alumnos implements IAlumnos {
	
	private static final String COLECCION = "alumnos";
	
	private MongoCollection<Document> coleccionAlumnos;

	@Override
	public void comenzar() {
		coleccionAlumnos = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Alumno> get() {
		List<Alumno> alumnos = new ArrayList<>();
		for (Document documentoAlumno : coleccionAlumnos.find().sort(MongoDB.getCriterioOrdenacionAlumno())) {
			alumnos.add(MongoDB.getAlumno(documentoAlumno));
		}
		return alumnos;
	}

	@Override
	public int getTamano() {
		return (int)coleccionAlumnos.countDocuments();
	}

	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		if (buscar(alumno) == null) {
			coleccionAlumnos.insertOne(MongoDB.getDocumento(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese correo.");
		}
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		Document documentoAlumno = coleccionAlumnos.find().filter(MongoDB.getCriterioBusquedaAlumno(alumno)).first();
		return MongoDB.getAlumno(documentoAlumno);
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		if (buscar(alumno) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningÃºn alumno con ese correo.");
		} else {
			coleccionAlumnos.deleteOne(MongoDB.getCriterioBusquedaAlumno(alumno));
		}
	}

}