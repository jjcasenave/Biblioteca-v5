/*Implementa la clase AudioLibro que extienda la clase Libro y que se comporte como se indica en el enunciado. 
 *Para que todo siga funcionando correctamente deberás modificar alguna que otra clase más. Realiza un commit.*/

package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;
/**
 * 
 * @author Juanjo
 *
 */
public class AudioLibro extends Libro {

	private static int MINUTOS_PARA_RECOMPENSA = 15;
	private static float PUNTOS_PREMIO = 0.25f;
	private int duracion;
	
	public AudioLibro(String titulo, String autor, int duracion) {
		super (titulo, autor);
		setDuracion(duracion);
	}
	
	public AudioLibro(AudioLibro copia) {
		super(copia);
		setDuracion(copia.getDuracion());
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración debe ser mayor que cero.");
		}
		this.duracion = duracion;
	}
	
	public float getPuntos() {
		return (duracion / MINUTOS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO;
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
		return String.format("título=%s, autor=%s, duración=%s", titulo, autor, duracion);
	}
}