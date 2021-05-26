package org.iesalandalus.programacion.biblioteca.mvc;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.Controlador;
import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IModelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.Modelo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public class Biblioteca  {

	public static void main(String[] args) {
		IModelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
		IVista vista = procesarArgumentosVista(args);
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}
	
	private static IVista procesarArgumentosVista(String[] args) {
		IVista vista = FactoriaVista.IUGPESTANAS.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vpestanas")) {
				vista = FactoriaVista.IUGPESTANAS.crear();
			}
			else if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();
			}
		}
		return vista;
	}
	
	private static IFuenteDatos procesarArgumentosFuenteDatos(String[] args) {
		IFuenteDatos fuenteDatos = FactoriaFuenteDatos.MONGODB.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-fdficheros")) {
				fuenteDatos = FactoriaFuenteDatos.FICHEROS.crear();
			} 
			else if (argumento.equalsIgnoreCase("-fdmongodb")) {
				fuenteDatos = FactoriaFuenteDatos.MONGODB.crear();
			}
		}
		return fuenteDatos;
	}
}