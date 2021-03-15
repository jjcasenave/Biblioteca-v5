package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;

public interface IVista {

	void setControlador(IControlador controlador);

	void comenzar();

	void terminar();

	void insertarAlumno();

	void buscarAlumno();

	void borrarAlumno();

	void listarAlumnos();

	void insertarLibro();

	void buscarLibro();

	void borrarLibro();

	void listarLibros();

	void prestarLibro();

	void devolverLibro();

	void buscarPrestamo();

	void borrarPrestamo();

	void listarPrestamos();

	void listarPrestamosAlumno();

	void listarPrestamosLibro();

	void listarPrestamosFecha();

	void mostrarEstadisticaPorCurso();

}