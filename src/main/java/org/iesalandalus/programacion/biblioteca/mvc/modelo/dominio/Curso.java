package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public enum Curso {

	PRIMERO("1"), SEGUNDO("2"), TERCERO("3"), CUARTO("4");

	String cadenaAMostrar;

	private Curso(String curso) {
		this.cadenaAMostrar = curso + "º ESO";
	}

	public String toString() {
		return cadenaAMostrar;
	}
}

