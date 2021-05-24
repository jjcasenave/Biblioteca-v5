package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;

public interface IVista {

	void setControlador(IControlador controlador);

	void comenzar();

	void terminar();

}