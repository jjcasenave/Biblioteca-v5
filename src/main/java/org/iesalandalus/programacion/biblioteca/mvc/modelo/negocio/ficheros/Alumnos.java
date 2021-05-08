package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;

public class Alumnos implements IAlumnos {

	private static final String NOMBRE_FICHERO_ALUMNOS = "datos/alumnos.dat";
	private List<Alumno> coleccionAlumnos;

	public Alumnos() throws IllegalArgumentException, NullPointerException {
		coleccionAlumnos = new ArrayList<>();
	}

	@Override
	public void comenzar() {
		leer();

	}

	private void leer() {
		File ficheroAlumnos = new File(NOMBRE_FICHERO_ALUMNOS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAlumnos))) {
			Alumno alumno = null;
			do {
				alumno = (Alumno) entrada.readObject();
				insertar(alumno);
			} while (alumno != null);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No se encuentra la clase a leer.");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se puede abrir el fichero de alumnos.");
		} catch (EOFException e) {
			System.out.println("Fichero de alumnos leído correctamente.");
		} catch (IOException e) {
			System.out.println("ERROR: Comportamiento inesperado en entrada/salida");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void terminar() {
		escribir();
	}

	private void escribir() {
		File ficheroAlumnos = new File(NOMBRE_FICHERO_ALUMNOS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAlumnos))) {
			for (Alumno alumno : coleccionAlumnos) {
				salida.writeObject(alumno);
			}
			System.out.println("Fichero de alumnos creado de forma satisfactoria.");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se puede crear el fichero de alumnos.");
		} catch (IOException e) {
			System.out.println("ERROR: Comportamiento inesperado de entrada/salida.");
		}
	}

	public List<Alumno> get() throws IllegalArgumentException, NullPointerException {
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

	public void insertar(Alumno alumno)
			throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
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