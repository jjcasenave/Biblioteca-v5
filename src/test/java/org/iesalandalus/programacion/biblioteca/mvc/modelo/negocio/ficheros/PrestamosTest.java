package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrestamosTest {

	private static final String ERROR_PRESTAR_PRESTAMO_NULO = "ERROR: No se puede prestar un préstamo nulo.";
	private static final String ERROR_DEVOLVER_PRESTAMO_NULO = "ERROR: No se puede devolver un préstamo nulo.";
	private static final String ERROR_ALUMNO_NULO = "ERROR: El alumno no puede ser nulo.";
	private static final String ERROR_LIBRO_NULO = "ERROR: El libro no puede ser nulo.";
	private static final String ERROR_FECHA_NULA = "ERROR: La fecha no puede ser nula.";
	private static final String ERROR_BORRAR_PRESTAMO_NULO = "ERROR: No se puede borrar un préstamo nulo.";
	private static final String ERROR_BUSCAR_PRESTAMO_NULO = "ERROR: No se puede buscar un préstamo nulo.";
	private static final String ERROR_PRESTAMO_EXISTE = "ERROR: Ya existe un préstamo igual.";
	private static final String ERROR_PRESTAMO_NO_EXISTE = "ERROR: No existe ningún préstamo igual.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String PRESTAMO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un préstamo nulo.";
	private static final String ALUMNO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un alumno nulo.";
	private static final String LIBRO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un libro nulo.";
	private static final String FECHA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una fecha nula.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String PRESTAMOS_NO_CREADOS = "Debería haber creado los préstamos correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String PRESTAMO_NO_ESPERADO = "El préstamo devuelto no es el que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	private static final String PUNTOS_NO_ESPERADOS = "Los puntos de la estadística no son los esperados.";
	
	private static Prestamo prestamo1;
	private static Prestamo prestamo2;
	private static Prestamo prestamo3;
	private static Prestamo prestamo4;
	private static Prestamo prestamo5;
	private static Prestamo prestamo6;
	private static Prestamo prestamo7;
	private static Prestamo prestamo8;
	private static Prestamo prestamoRepetido;
	private static LocalDate primerDiaMesPasado = LocalDate.now().withDayOfMonth(1).minusMonths(1);
	private static LocalDate segundoDiaMesPasado = primerDiaMesPasado.plusDays(1);
	private static LocalDate tercerDiaMesPasado = primerDiaMesPasado.plusDays(2);
	private static LocalDate primerDiaMesHaceDosMeses = primerDiaMesPasado.minusMonths(1);
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		Libro libro1 = new LibroEscrito("Cien años de soledad", "Gabriel García Márquez", 471);
		Libro libro2 = new LibroEscrito("El retrato de Dorian Gray", "Oscar Wilde", 275);
		Libro libro3 = new AudioLibro("El alquimista", "Paulo Coelho", 224);
		Libro libro4 = new AudioLibro("El alquimista", "Benjamin Jonso", 176);
		Alumno alumno1 = new Alumno("Bob Esponja", "bob@gmail.com", Curso.PRIMERO);
		Alumno alumno2 = new Alumno("Patricio Estrella", "patricio@gmail.com", Curso.SEGUNDO);
		Alumno alumno3 = new Alumno("Señor Cangrejo", "cangrejo@gmail.com", Curso.TERCERO);
		prestamo1 = new Prestamo(alumno1, libro1, segundoDiaMesPasado);
		prestamo2 = new Prestamo(alumno1, libro2, primerDiaMesHaceDosMeses);
		prestamo3 = new Prestamo(alumno2, libro2, segundoDiaMesPasado);
		prestamo4 = new Prestamo(alumno2, libro1, segundoDiaMesPasado.minusYears(1));
		prestamo5 = new Prestamo(alumno1, libro3, segundoDiaMesPasado);
		prestamo6 = new Prestamo(alumno1, libro4, segundoDiaMesPasado);
		prestamo7 = new Prestamo(alumno3, libro3, primerDiaMesPasado);
		prestamo8 = new Prestamo(alumno3, libro4, primerDiaMesPasado);
		prestamoRepetido = new Prestamo(prestamo1);
	}
	
	@Test
	public void constructorCreaPrestamosCorrectamente() {
		Prestamos prestamos = new Prestamos();
		assertThat(PRESTAMOS_NO_CREADOS, prestamos, not(nullValue()));
		assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
	}
	
	@Test
	public void prestarPrestamoValidoConPrestamosVaciosRealizaPrestamoCorrectamente() {
		IPrestamos prestamos = new Prestamos();
		try {
			prestamos.prestar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo1), not(sameInstance(prestamo1)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarDosPrestamosValidosRealizaPrestamosCorrectamente() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.prestar(prestamo2);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo1), not(sameInstance(prestamo1)));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo2), not(sameInstance(prestamo2)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	private void prestarPrestamoValido(IPrestamos prestamos, Prestamo prestamo) {
		try {
			prestamos.prestar(prestamo);
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarTresPrestamosValidosRealizaPrestamosCorrectamente() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.prestar(prestamo3);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo1), not(sameInstance(prestamo1)));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo2), not(sameInstance(prestamo2)));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo3), is(prestamo3));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo3), not(sameInstance(prestamo3)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarPrestamoNuloLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		try {
			prestamos.prestar(null);
			fail(PRESTAMO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void prestarPrestamoRepetidoLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		try {
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo3);
		try {
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getDevuelvePrestamosOrdenadosPorFechaAlumnoLibro() {
		IPrestamos prestamos = insertarOchoPrestamos();
		try {
			List<Prestamo> prestamosOrdenados = prestamos.get();
			assertThat(TAMANO_NO_ESPERADO, prestamosOrdenados.size(), is(8));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(0), is(prestamo4));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(1), is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(2), is(prestamo8));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(3), is(prestamo7));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(4), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(5), is(prestamo6));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(6), is(prestamo5));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosOrdenados.get(7), is(prestamo3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}

	private IPrestamos insertarOchoPrestamos() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		prestarPrestamoValido(prestamos, prestamo4);
		prestarPrestamoValido(prestamos, prestamo5);
		prestarPrestamoValido(prestamos, prestamo6);
		prestarPrestamoValido(prestamos, prestamo7);
		prestarPrestamoValido(prestamos, prestamo8);
		return prestamos;
	}

	@Test
	public void getAlumnoValidoDevuelvePrestamosAlumnoOrdenadosPorFechaLibro() {
		IPrestamos prestamos = insertarOchoPrestamos();
		try {
			List<Prestamo> prestamosAlumnoOrdenados = prestamos.get(Alumno.getAlumnoFicticio("bob@gmail.com"));
			assertThat(TAMANO_NO_ESPERADO, prestamosAlumnoOrdenados.size(), is(4));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosAlumnoOrdenados.get(0), is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosAlumnoOrdenados.get(1), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosAlumnoOrdenados.get(2), is(prestamo6));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosAlumnoOrdenados.get(3), is(prestamo5));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getAlumnoNuloLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		List<Prestamo> prestamosAlumno = null;
		try {
			Alumno alumno = null;
			prestamosAlumno = prestamos.get(alumno);
			fail(ALUMNO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_ALUMNO_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosAlumno, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getLibroValidoDevuelvePrestamosLibroOrdenadosPorFechaAlumno() {
		IPrestamos prestamos = insertarOchoPrestamos();
		try {
			List<Prestamo> prestamosLibroOrdenados = prestamos.get(Libro.getLibroFicticio("Cien años de soledad", "Gabriel García Márquez"));
			assertThat(TAMANO_NO_ESPERADO, prestamosLibroOrdenados.size(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosLibroOrdenados.get(0), is(prestamo4));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosLibroOrdenados.get(1), is(prestamo1));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getLibroNuloLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		List<Prestamo> prestamosLibro = null;
		try {
			Libro libro = null;
			prestamosLibro = prestamos.get(libro);
			fail(LIBRO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosLibro, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getFechaValidaDevuelvePrestamosMesOrdenadosAlumnoLibro() {
		IPrestamos prestamos = insertarOchoPrestamos();
		try {
			List<Prestamo> prestamosFechaOrdenados = prestamos.get(segundoDiaMesPasado);
			assertThat(TAMANO_NO_ESPERADO, prestamosFechaOrdenados.size(), is(6));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(0), is(prestamo8));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(1), is(prestamo7));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(2), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(3), is(prestamo6));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(4), is(prestamo5));
			assertThat(PRESTAMO_NO_ESPERADO, prestamosFechaOrdenados.get(5), is(prestamo3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getFechaNulaLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		prestarPrestamoValido(prestamos, prestamo3);
		List<Prestamo> prestamosFecha = null;
		try {
			LocalDate fecha = null;
			prestamosFecha = prestamos.get(fecha);
			fail(FECHA_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_FECHA_NULA));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosFecha, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getEstadisticasMensualesPorCursoFechaValidaDevuelveCorrectamenteEstadisticasMensualesPorCurso() {
		IPrestamos prestamos = insertarOchoPrestamos();
		for (Prestamo prestamo : prestamos.get()) {
			try {
				prestamos.devolver(prestamo, tercerDiaMesPasado);
			} catch (OperationNotSupportedException e) {
				fail(EXCEPCION_NO_PROCEDE);
			}
		}
		Map<Curso, Integer> estadisticasMensualesPorCurso = prestamos.getEstadisticaMensualPorCurso(primerDiaMesPasado);
		assertThat(PUNTOS_NO_ESPERADOS, estadisticasMensualesPorCurso.get(Curso.PRIMERO), is(17));
		assertThat(PUNTOS_NO_ESPERADOS, estadisticasMensualesPorCurso.get(Curso.SEGUNDO), is(6));
		assertThat(PUNTOS_NO_ESPERADOS, estadisticasMensualesPorCurso.get(Curso.TERCERO), is(4));
		assertThat(PUNTOS_NO_ESPERADOS, estadisticasMensualesPorCurso.get(Curso.CUARTO), is(0));
	}
	
	@Test
	public void devolverPrestamoExistenteDevuelvePrestamoCorrectamente() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.devolver(prestamo2, segundoDiaMesPasado);
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2).getFechaDevolucion(), is(segundoDiaMesPasado));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void devolverPrestamoNuloLanzaException() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.devolver(null, segundoDiaMesPasado);
			fail(OPERACION_NO_PERMITIDA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_DEVOLVER_PRESTAMO_NULO));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void devolverPrestamoNoExistenteLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.devolver(prestamo1, segundoDiaMesPasado);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarPrestamoExistenteBorraPrestamoCorrectamente() throws OperationNotSupportedException {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.borrar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.borrar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.borrar(prestamo2);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarPrestamoNoExistenteLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.borrar(prestamo2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		prestarPrestamoValido(prestamos, prestamo2);
		try {
			prestamos.borrar(prestamo3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarPrestamoNuloLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.borrar(null);
			fail(PRESTAMO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarPrestamoNuloLanzaExcepcion() {
		IPrestamos prestamos = new Prestamos();
		prestarPrestamoValido(prestamos, prestamo1);
		try {
			prestamos.buscar(null);
			fail(PRESTAMO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}