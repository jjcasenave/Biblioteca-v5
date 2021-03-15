/*Haz que la clase Libro sea abstracta y que se adecúe al diagrama de clases. Realiza un commit.*/

package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public abstract class Libro {

	protected String titulo;
	protected String autor;

	public Libro(String titulo, String autor) {
		setTitulo(titulo);
		setAutor(autor);
	}

	public Libro(Libro copia) {
		if (copia == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setTitulo(copia.getTitulo());
		setAutor(copia.getAutor());
	}

	public static Libro getLibroFicticio(String titulo, String autor) {
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (titulo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		if(autor.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		return new LibroEscrito(titulo, autor, 100);
	}

	public abstract float getPuntos();
	
	public String getTitulo() {
		return titulo;
	}

	protected void setTitulo(String titulo) {
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		} else {
			this.titulo = titulo;
		}
	}

	public String getAutor() {
		return autor;
	}

	protected void setAutor(String autor) {
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		} else {
			this.autor = autor;
		}
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
		return "título=" + titulo + ", autor=" + autor;
	}

}