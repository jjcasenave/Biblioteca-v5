package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public enum Opcion {

	INSERTAR_ALUMNO("Insertar alumno") {
		public void ejecutar() {
			vista.insertarAlumno();
		}
	}, 
	BUSCAR_ALUMNO("Buscar alumno") {
		public void ejecutar() {
			vista.buscarAlumno();
		}
	},
	BORRAR_ALUMNO("Borrar alumno") {
		public void ejecutar() {
			vista.borrarAlumno();
		}
	},
	LISTAR_ALUMNOS("Listar alumnos") {
		public void ejecutar() {
			vista.listarAlumnos();
		}
	},
	INSERTAR_LIBRO("Insertar libro") {
		public void ejecutar() {
			vista.insertarLibro();
		}
	},
	BUSCAR_LIBRO("Buscar libro") {
		public void ejecutar() {
			vista.buscarLibro();
		}
	},
	BORRAR_LIBRO("Borrar libro") {
		public void ejecutar() {
			vista.borrarLibro();
		}
	},
	LISTAR_LIBROS("Listar libros") {
		public void ejecutar() {
			vista.listarLibros();
		}
	},
	PRESTAR_LIBRO("Prestar libro") {
		public void ejecutar() {
			vista.prestarLibro();
		}
	},
	DEVOLVER_LIBRO("Devolver libro") {
		public void ejecutar() {
			vista.devolverLibro();
		}
	},
	BUSCAR_PRESTAMO("Buscar préstamo") {
		public void ejecutar() {
			vista.buscarPrestamo();
		}
	},
	BORRAR_PRESTAMO("Borrar préstamo") {
		public void ejecutar() {
			vista.borrarPrestamo();
		}
	},
	LISTAR_PRESTAMOS("Listar préstamos") {
		public void ejecutar() {
			vista.listarPrestamos();
		}
	},
	LISTAR_PRESTAMOS_ALUMNOS("Listar préstamos de un alumno") {
		public void ejecutar() {
			vista.listarPrestamosAlumno();
		}
	},
	LISTAR_PRESTAMOS_LIBROS("Listar préstamos de un libro") {
		public void ejecutar() {
			vista.listarPrestamosLibro();
		}
	},
	LISTAR_PRESTAMOS_FECHAS("Listar préstamos de un mes") {
		public void ejecutar() {
			vista.listarPrestamosFecha();
		}
	},
	MOSTRAR_ESTADISTICA_MENSUAL_POR_CURSO("Mostrar estadísticas mensuales") {
		public void ejecutar() {
			vista.mostrarEstadisticaPorCurso();
		}
	},
	SALIR("Salir") {
		public void ejecutar() {
			vista.terminar();
		}
	};
	
	private String mensaje;
	private static IVista vista;
	
	private Opcion(String opcion) {
		this.mensaje = opcion;
	}
	
	public abstract void ejecutar();
	
	protected static void setVista(IVista vista) {
		Opcion.vista = vista;
	}
	
	public static Opcion getOpcionSegunOrdinal(int opcion) {
		if (esOrdinalValido(opcion))
			return values()[opcion];
		else
			throw new IllegalArgumentException("Ordinal de la opción no válido");
	}
	
	public static boolean esOrdinalValido(int opcion) {
		return opcion <= values().length -1 && opcion >= 0;
	}
	
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensaje);
	}
}