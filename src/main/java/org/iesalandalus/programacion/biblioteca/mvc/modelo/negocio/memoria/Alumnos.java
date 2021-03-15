package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;

public class Alumnos implements IAlumnos {

	private List<Alumno> coleccionAlumnos;

	public Alumnos() throws IllegalArgumentException, NullPointerException {
		coleccionAlumnos = new ArrayList<>();
	}

	public List<Alumno> get() throws IllegalArgumentException, NullPointerException{
		List<Alumno> alumnosOrdenados = copiaProfundaAlumnos();
		alumnosOrdenados.sort(Comparator.comparing(Alumno::getCorreo));
		return alumnosOrdenados;
	}

	private List<Alumno> copiaProfundaAlumnos() throws IllegalArgumentException, NullPointerException {
		List<Alumno> copiaAlumnos = new ArrayList<>();
		for (Alumno alumno : coleccionAlumnos) {
			copiaAlumnos.add(new Alumno(alumno));
		}
		return copiaAlumnos;
	}

	public int getTamano() {
		return coleccionAlumnos.size();
	}

	public void insertar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			coleccionAlumnos.add(new Alumno(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese correo.");
		}
	}

	public Alumno buscar(Alumno alumno) throws IllegalArgumentException, NullPointerException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			return null;
		} else {
			return new Alumno(coleccionAlumnos.get(indice));
		}
	}

	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese correo.");
		} else {
			coleccionAlumnos.remove(indice);
		}
	}

}