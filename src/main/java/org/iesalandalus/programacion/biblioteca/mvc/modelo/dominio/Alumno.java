package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class Alumno {

	private static String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
	private static String ER_CORREO = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
	private String nombre;
	private String correo;
	private Curso curso;

	public Alumno(String nombre, String correo, Curso curso) {
		setNombre(nombre);
		setCorreo(correo);
		setCurso(curso);
	}

	public Alumno(Alumno copia) {
		if (copia == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(copia.getNombre());
		setCorreo(copia.getCorreo());
		setCurso(copia.getCurso());
	}

	public static Alumno getAlumnoFicticio(String correo) throws NullPointerException {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		Alumno alumnoFicticio = new Alumno("Juanjo Casenave", correo, Curso.PRIMERO);
		return alumnoFicticio;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	private String formateaNombre(String nombre) {
		String nombreFormateado = "";
		int longitudDelNombre = nombre.length();
		int letraActual = 0;
		while (letraActual < longitudDelNombre) {
			nombreFormateado += nombre.toUpperCase().charAt(letraActual);
			letraActual++;
			if (letraActual < longitudDelNombre) {
				while (letraActual < longitudDelNombre && nombre.charAt(letraActual) != ' ') {
					nombreFormateado += nombre.toLowerCase().charAt(letraActual);
					letraActual++;
				}
				if (letraActual < longitudDelNombre) {
					nombreFormateado += " ";
					letraActual++;
					while (letraActual < longitudDelNombre && nombre.charAt(letraActual) == ' ') {
						letraActual++;
					}
				}
			}
		}
		return nombreFormateado;
	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		} else {
			this.correo = correo;
		}
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		if (curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		if (curso == Curso.PRIMERO | curso == Curso.SEGUNDO | curso == Curso.TERCERO | curso == Curso.CUARTO) {
			this.curso = curso;
		} else {
			throw new IllegalArgumentException("ERROR 3");
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] iniciales = { "", "", "", "" };
		int numeroArray = 0;

		for (int letra = 0; letra < nombre.length(); letra++) {
			if (Character.isUpperCase(nombre.charAt(letra))) {
				if (numeroArray < 4) {
					iniciales[numeroArray] += nombre.charAt(letra);
					numeroArray++;
				}
			}
		}
		return "nombre=" + nombre + " (" + iniciales[0] + iniciales[1] + iniciales[2] + iniciales[3] + "), correo="
				+ correo + ", curso=" + curso;
	}

}