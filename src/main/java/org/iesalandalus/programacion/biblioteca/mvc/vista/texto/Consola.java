package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {

	}

	public static void mostrarMenu() {
		System.out.println();
		mostrarCabecera("MENÚ DE OPCIONES");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
		System.out.println();
	}

	public static void mostrarCabecera(String cadena) {
		System.out.println(cadena);
		int longitudCadena = cadena.length();
		for (int i = 0; i < longitudCadena; i++) {
			System.out.print("-");
		}
		System.out.println("\n");
	}

	public static int elegirOpcion() {
		int opcion;
		do {
			System.out.print("ELIJA UNA OPCIÓN: ");
			opcion = Entrada.entero();
			System.out.println();
			if (!Opcion.esOrdinalValido(opcion)) {
				System.out.println("OPCIÓN INCORRECTA: Vuelva a intentarlo");
			}
		} while (!Opcion.esOrdinalValido(opcion));
		return opcion;
	}

	public static Alumno leerAlumno() {
		boolean alumnoValidado = false;
		Alumno alumno = null;
		String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
		String ER_CORREO = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
		String nombre = null;
		String correo = null;
		int curso = 0;
		Curso cursoInstancia;
		do {
			do {
				System.out.print("Introduzca el nombre del alumno: ");
				nombre = Entrada.cadena();
				if (!nombre.matches(ER_NOMBRE)) {
					System.out.println("ERROR: Nombre no válido, vuelva a introducirlo.");
				}
			} while (!nombre.matches(ER_NOMBRE));
			do {
				System.out.print("Introduzca el correo del alumno: ");
				correo = Entrada.cadena();
				if (!correo.matches(ER_CORREO)) {
					System.out.println("ERROR: Correo no válido, vuelva a introducirlo.");
				}
			} while (!correo.matches(ER_CORREO));
			do {
				System.out.print("Introduzca el curso del alumno (1-4): ");
				curso = Entrada.entero();
				if (curso < 1 || curso > 4) {
					System.out.println("ERROR: Curso no válido, vuelva  a introducirlo.");
				}
			} while (curso < 1 || curso > 4);

			switch (curso) {
			case 1:
				cursoInstancia = Curso.PRIMERO;
			case 2:
				cursoInstancia = Curso.SEGUNDO;
			case 3:
				cursoInstancia = Curso.TERCERO;
			default:
				cursoInstancia = Curso.CUARTO;
			}
			try {
				alumno = new Alumno(nombre, correo, cursoInstancia);
				alumnoValidado = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (!alumnoValidado);
		return alumno;

	}

	public static Alumno leerAlumnoFicticio() {
		String correo = null;
		String ER_CORREO = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
		do {
			System.out.print("Introduzca el correo del alumno: ");
			correo = Entrada.cadena();
			if (!correo.matches(ER_CORREO)) {
				System.out.println("ERROR: Correo no válido, vuelva a introducirlo.");
			}
		} while (!correo.matches(ER_CORREO));
		return Alumno.getAlumnoFicticio(correo);
	}

	public static Libro leerLibro() {
		boolean autorVacio = true;
		boolean tituloVacio = true;
		String titulo = null;
		String autor = null;
		int numeroDePaginas = 0;
		int duracion = 0;
		int eleccionLibro = 0;
		AudioLibro audioLibro = null;
		LibroEscrito libroEscrito = null;
		do {
			System.out.print("Introduzca el tipo de libro (AUDIOLIBRO=1 | LIBROESCRITO=2) :");
			eleccionLibro = Entrada.entero();
			if (eleccionLibro != 1 && eleccionLibro != 2) {
				System.out.println("ERROR: Elección del tipo de libro incorrecta");
			}
		} while (eleccionLibro != 1 && eleccionLibro != 2);
		do {
			System.out.print("Introduzca el título del libro: ");
			titulo = Entrada.cadena();
			int tamanoTitulo = titulo.length();
			for (int i = 0; i < tamanoTitulo; i++) {
				if (titulo.charAt(i) != ' ') {
					tituloVacio = false;
				}
			}
			if (tituloVacio == true) {
				System.out.println("ERROR: El título no puede estar vacío.");
			}
		} while (tituloVacio == true);
		do {
			System.out.print("Introduzca el autor del libro: ");
			autor = Entrada.cadena();
			int tamanoAutor = autor.length();
			for (int i = 0; i < tamanoAutor; i++) {
				if (autor.charAt(i) != ' ') {
					autorVacio = false;
				}
			}
			if (autorVacio == true) {
				System.out.println("ERROR: El autor no puede estar vacío.");
			}
		} while (autorVacio == true);
		if (eleccionLibro == 1) {
			do {
				System.out.print("Introduzca la duración: ");
				duracion = Entrada.entero();
				if (duracion <= 0) {
					System.out.println("ERROR: Duración no válida, vuelva  a introducirlo.");
				}
				audioLibro = new AudioLibro(titulo, autor, duracion);
				return audioLibro;
			} while (duracion <= 0);
		} else {
			do {
				System.out.print("Introduzca el número de páginas: ");
				numeroDePaginas = Entrada.entero();
				if (numeroDePaginas <= 0) {
					System.out.println("ERROR: Número de páginas no válido, vuelva  a introducirlo.");
				}
			} while (numeroDePaginas <= 0);
			libroEscrito = new LibroEscrito(titulo, autor, numeroDePaginas);
			return libroEscrito;
		}
	}

	public static Libro leerLibroFicticio() {
		boolean autorVacio = true;
		boolean tituloVacio = true;
		String titulo = null;
		String autor = null;
		int eleccionLibro = 0;
		do {
			System.out.print("Introduzca el tipo de libro (AUDIOLIBRO=1 | LIBROESCRITO=2) :");
			eleccionLibro = Entrada.entero();
			if (eleccionLibro != 1 && eleccionLibro != 2) {
				System.out.println("ERROR: Elección del tipo de libro incorrecta");
			}
		} while (eleccionLibro != 1 && eleccionLibro != 2);
		do {
			System.out.print("Introduzca el título del libro: ");
			titulo = Entrada.cadena();
			int tamanoTitulo = titulo.length();
			for (int i = 0; i < tamanoTitulo; i++) {
				if (titulo.charAt(i) != ' ') {
					tituloVacio = false;
				}
			}
			if (tituloVacio == true) {
				System.out.println("ERROR: El título no puede estar vacío.");
			}
		} while (tituloVacio == true);
		do {
			System.out.print("Introduzca el autor del libro: ");
			autor = Entrada.cadena();
			int tamanoAutor = autor.length();
			for (int i = 0; i < tamanoAutor; i++) {
				if (autor.charAt(i) != ' ') {
					autorVacio = false;
				}
			}
			if (autorVacio == true) {
				System.out.println("ERROR: El autor no puede estar vacío.");
			}
		} while (autorVacio == true);
		if (eleccionLibro == 1) {
			return new AudioLibro(titulo, autor, 1);
		} else {
			return LibroEscrito.getLibroFicticio(titulo, autor);
		}
		/*return LibroEscrito.getLibroFicticio(titulo, autor);*/
	}

	public static Prestamo leerPrestamo() {
		Alumno alumno = leerAlumnoFicticio();
		Libro libro = leerLibroFicticio();
		System.out.print("Introduzca la fecha de préstamo");
		LocalDate fechaPrestamo = leerFecha();
		return new Prestamo(alumno, libro, fechaPrestamo);
	}

	public static Prestamo leerPrestamoFicticio() {
		return Prestamo.getPrestamoFicticio(leerAlumnoFicticio(), leerLibroFicticio());
	}

	public static LocalDate leerFecha() {
		LocalDate dia = null;
		do {
			String cadenaFormato = "dd/MM/yyyy";
			DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern(cadenaFormato);
			System.out.printf(" con el formato (%s): ", cadenaFormato);
			String diaLeido = Entrada.cadena();
			try {
				dia = LocalDate.parse(diaLeido, formatoDia);
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato del día no es correcto.");
			}
		} while (dia == null);
		return dia;
	}

}