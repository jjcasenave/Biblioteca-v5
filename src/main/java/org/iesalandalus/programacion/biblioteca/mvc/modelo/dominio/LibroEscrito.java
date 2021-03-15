/*Implementa la clase LibroEscrito que extienda la clase Libro y que se comporte exactamente igual que antes se comportaba la clase Libro.
*Para que todo siga funcionando correctamente deberás modificar alguna que otra clase más. Realiza un commit.
*/

package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * 
 * @author Juanjo
 *
 */

public class LibroEscrito extends Libro {

	private static int PAGINAS_PARA_RECOMPENSA = 25;
	private static float PUNTOS_PREMIO = 0.5f;
	private int numPaginas;

	public LibroEscrito(String titulo, String autor, int numeroDePaginas) {
		super(titulo, autor);
		setNumPaginas(numeroDePaginas);

	}

	public LibroEscrito(LibroEscrito copia) {
		super(copia);
		setNumPaginas(copia.getNumPaginas());
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	private void setNumPaginas(int numeroDePaginas) {
		if (numeroDePaginas <= 0) {
			throw new IllegalArgumentException("ERROR: El número de páginas debe ser mayor que cero.");
		}
		this.numPaginas = numeroDePaginas;
	}

	public float getPuntos() {
		return (numPaginas / PAGINAS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof Libro)) {
			return false;
		}
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("título=%s, autor=%s, número de páginas=%s", titulo, autor, numPaginas);
	}

}