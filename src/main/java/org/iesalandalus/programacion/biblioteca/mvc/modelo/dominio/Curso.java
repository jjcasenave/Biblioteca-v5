/*Crea el enumerado Curso, en el paquete adecuado con las siguientes instancias (PRIMERO, SEGUNDO, TERCERO y CUARTO).
 *Cada instancia aceptará en su constructor el texto a mostrar por el método toString (1º ESO, 2º ESO, 3º ESO y 4º ESO).
 */

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
