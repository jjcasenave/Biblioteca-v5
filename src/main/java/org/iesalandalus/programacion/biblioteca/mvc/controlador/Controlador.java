/*Refactoriza la clase Controlador, extrayendo su interfaz. Realiza un commit*/

package org.iesalandalus.programacion.biblioteca.mvc.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IModelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.Modelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria.FactoriaFuenteDatosMemoria;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

public class Controlador implements IControlador {

	IVista vista;
	IModelo modelo;

	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
		}
		IFuenteDatos iFuenteDatos = new FactoriaFuenteDatosMemoria();
		this.modelo = new Modelo(iFuenteDatos);
		this.vista = new VistaTexto();
		this.vista.setControlador(this);
	}

	@Override
	public void comenzar() {
		vista.comenzar();
	}

	@Override
	public void terminar() {
		System.out.println("¡HASTA PRONTO!");
	}

	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		modelo.insertar(alumno);
	}

	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException {
		modelo.insertar(libro);
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.prestar(prestamo);
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, IllegalArgumentException, NullPointerException {
		modelo.devolver(prestamo, fechaDevolucion);
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		return modelo.buscar(alumno);
	}

	@Override
	public Libro buscar(Libro libro) {
		return modelo.buscar(libro);
	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
		return modelo.buscar(prestamo);
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		modelo.borrar(alumno);
	}

	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		modelo.borrar(libro);
	}

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.borrar(prestamo);
	}

	@Override
	public List<Alumno> getAlumnos() {
		return modelo.getAlumnos();
	}

	@Override
	public List<Libro> getLibros() {
		return modelo.getLibros();
	}

	@Override
	public List<Prestamo> getPrestamos() {
		return modelo.getPrestamos();
	}

	@Override
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return modelo.getPrestamos(alumno);
	}

	@Override
	public List<Prestamo> getPrestamos(Libro libro) {
		return modelo.getPrestamos(libro);
	}

	@Override
	public List<Prestamo> getPrestamos(LocalDate fecha) {
		return modelo.getPrestamos(fecha);
	}
	
	@Override
	public Map<Curso, Integer> getEstadisticasMensualPorCurso(LocalDate fecha) {
		return modelo.getEstadisticaMensualPorCurso(fecha);
	}
}