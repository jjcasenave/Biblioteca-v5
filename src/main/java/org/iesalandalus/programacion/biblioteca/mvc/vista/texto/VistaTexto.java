/*Refactoriza la clase Vista, 
 * renombrándola, extrayendo su interfaz y colocando las clases y la 
 * interfaz en los paquetes adecuados. Realiza un commit.*/

package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public class VistaTexto implements IVista {

	IControlador controlador;

	public VistaTexto() {
		Opcion.setVista(this);
	}

	@Override
	public void setControlador(IControlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		Consola.mostrarCabecera("GESTIÓN DE RESERVAS DE LA BIBLIOTECA");
		int opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			Opcion opcionElegida = Opcion.getOpcionSegunOrdinal(opcion);
			opcionElegida.ejecutar();
		} while (opcion != Opcion.SALIR.ordinal());
	}

	@Override
	public void terminar() {
		controlador.terminar();
	}

	@Override
	public void insertarAlumno() {
		Consola.mostrarCabecera("INSERTAR ALUMNO");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("\nAlumno Insertado Correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void buscarAlumno() {
		Consola.mostrarCabecera("BUSCAR ALUMNO");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			if (alumno == null) {
				System.out.println("\nEl alumno no existe");
			} else {
				System.out.println(alumno.toString());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarAlumno() {
		Consola.mostrarCabecera("BORRAR ALUMNO");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("\nAlumno borrado correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarAlumnos() {
		Consola.mostrarCabecera("LISTADO DE ALUMNOS");
		List<Alumno> listaAlumnos = controlador.getAlumnos();
		if (listaAlumnos != null) {
			for (Alumno alumno : listaAlumnos) {
					System.out.println(alumno);
			}
		} else {
			System.out.println("No hay alumnos que mostrar");
		}
	}

	@Override
	public void insertarLibro() {
		Consola.mostrarCabecera("INSERTAR LIBRO");
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("\nLibro Insertado Correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void buscarLibro() {
		Consola.mostrarCabecera("BUSCAR LIBRO");
		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			if (libro == null) {
				System.out.println("\nEl libro no existe");
			} else {
				System.out.println(libro.toString());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarLibro() {
		Consola.mostrarCabecera("BORRAR LIBRO");
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("\nLibro borrado correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarLibros() {
		Consola.mostrarCabecera("LISTADO DE LIBROS");
		List<Libro> listaLibros = controlador.getLibros();
		if (listaLibros != null) {
			for (Libro libro : listaLibros) {
					System.out.println(libro);
			}
		} else {
			System.out.println("No hay libros que mostrar");
		}
	}

	@Override
	public void prestarLibro() {
		Consola.mostrarCabecera("PRÉSTAMO DE LIBRO");
		try {
			// controlador.prestar(Consola.leerPrestamo());
			Prestamo prestamo = Consola.leerPrestamo();
			Prestamo prestamoValidado = new Prestamo(controlador.buscar(prestamo.getAlumno()), controlador.buscar(prestamo.getLibro()),	 prestamo.getFechaPrestamo());
			controlador.prestar(prestamoValidado);
			System.out.println("\nLibro Prestado Correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void devolverLibro() {
		Consola.mostrarCabecera("DEVOLUCIÓN DE LIBRO");
		Prestamo prestamo = Consola.leerPrestamo();
		System.out.print("Introduzca la fecha de devolución");
		LocalDate fechaDevolucion = Consola.leerFecha();
		try {
			controlador.devolver(prestamo, fechaDevolucion);
			System.out.println("\nLibro devuelto correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void buscarPrestamo() {
		Consola.mostrarCabecera("BUSCAR PRÉSTAMO");
		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			if (prestamo == null) {
				System.out.println("\nEl préstamo no existe");
			} else {
				System.out.println(prestamo.toString());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarPrestamo() {
		Consola.mostrarCabecera("BORRAR PRÉSTAMO");
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("\nPréstamo borrado correctamente");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarPrestamos() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS");
		List<Prestamo> prestamos = controlador.getPrestamos();
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay préstamos para mostrar");
		}
	}

	@Override
	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR ALUMNO");
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerAlumno());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay préstamos de dicho alumno");
		}
	}

	@Override
	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR LIBRO");
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerLibro());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay préstamos de dicho libro");
		}
	}

	@Override
	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR FECHA");
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerFecha());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay préstamos en dicha fecha");
		}
	}

	@Override
	public void mostrarEstadisticaPorCurso() {
		Consola.mostrarCabecera("ESTADISTICAS MENSUALES POR CURSO");
		Map<Curso, Integer> mapa = controlador.getEstadisticasMensualPorCurso(Consola.leerFecha());
		System.out.println();
		System.out.println(mapa);
	}

}