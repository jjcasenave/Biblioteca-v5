package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.junit.BeforeClass;
import org.junit.Test;

public class LibrosTest {

	private static final String ERROR_INSERTAR_LIBRO_NULO = "ERROR: No se puede insertar un libro nulo.";
	private static final String ERROR_BORRAR_LIBRO_NULO = "ERROR: No se puede borrar un libro nulo.";
	private static final String ERROR_BUSCAR_LIBRO_NULO = "ERROR: No se puede buscar un libro nulo.";
	private static final String ERROR_LIBRO_EXISTE = "ERROR: Ya existe un libro con ese título y autor.";
	private static final String ERROR_LIBRO_BORRAR_NO_EXISTE = "ERROR: No existe ningún libro con ese título y autor.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String LIBRO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un libro nulo.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String LIBROS_NO_CREADOS = "Debería haber creado los libros correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String LIBRO_NO_ESPERADO = "El libro devuelto no es el que debería ser.";
	
	private static Libro libro1;
	private static Libro libro2;
	private static Libro libro3;
	private static Libro libro4;
	private static Libro libro5;
	private static Libro libroRepetido;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		libro1 = new LibroEscrito("Cien años de soledad", "Gabriel García Márquez", 471);
		libro2 = new LibroEscrito("El retrato de Dorian Gray", "Oscar Wilde", 275);
		libro3 = new LibroEscrito("Viaje al centro de la tierra", "Julio Verne", 320);
		libro4 = new AudioLibro("El alquimista", "Paulo Coelho", 224);
		libro5 = new AudioLibro("El alquimista", "Benjamin Jonso", 176);
		libroRepetido = new LibroEscrito((LibroEscrito)libro1);
	}
	
	@Test
	public void constructorCreaLibrosCorrectamente() {
		Libros libros = new Libros();
		assertThat(LIBROS_NO_CREADOS, libros, not(nullValue()));
		assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
	}
	
	@Test
	public void getDevuelveLibrosOrdenadosPorTituloYAutor() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro2);
		insertarLibroValido(libros, libro3);
		insertarLibroValido(libros, libro4);
		insertarLibroValido(libros, libro5);
		try {
			List<Libro> librosOrdenados = libros.get();
			assertThat(TAMANO_NO_ESPERADO, librosOrdenados.size(), is(5));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(0), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(1), is(libro5));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(2), is(libro4));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(3), is(libro2));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(4), is(libro3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro5);
		insertarLibroValido(libros, libro4);
		insertarLibroValido(libros, libro3);
		insertarLibroValido(libros, libro2);
		insertarLibroValido(libros, libro1);
		try {
			List<Libro> librosOrdenados = libros.get();
			assertThat(TAMANO_NO_ESPERADO, librosOrdenados.size(), is(5));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(0), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(1), is(libro5));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(2), is(libro4));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(3), is(libro2));
			assertThat(LIBRO_NO_ESPERADO, librosOrdenados.get(4), is(libro3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarLibroValidoConLibrosVaciosInsertaLibroCorrectamente() {
		ILibros libros = new Libros();
		try {
			libros.insertar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro1), not(sameInstance(libro1)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarDosLibrosValidosInsertaLibrosCorrectamente() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		try {
			libros.insertar(libro2);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro1), not(sameInstance(libro1)));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro2), not(sameInstance(libro2)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}

	private void insertarLibroValido(ILibros libros, Libro libro) {
		try {
			libros.insertar(libro);
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTresLibrosValidosInsertaLibrosCorrectamente() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro2);
		try {
			libros.insertar(libro3);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro1), not(sameInstance(libro1)));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro2), not(sameInstance(libro2)));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro3), is(libro3));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro3), not(sameInstance(libro3)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarLibroNuloLanzaExcepcion() {
		ILibros libros = new Libros();
		try {
			libros.insertar(null);
			fail(LIBRO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_INSERTAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarLibroRepetidoLanzaExcepcion() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro2);
		insertarLibroValido(libros, libro3);
		try {
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro2);
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro3);
		try {
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro2);
		insertarLibroValido(libros, libro3);
		insertarLibroValido(libros, libro1);
		try {
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarLibroExistenteBorraLibroCorrectamente() throws OperationNotSupportedException {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		try {
			libros.borrar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro4);
		try {
			libros.borrar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro4), is(libro4));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro4);
		try {
			libros.borrar(libro4);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro4), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarLibroNoExistenteLanzaExcepcion() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		try {
			libros.borrar(libro2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		libros = new Libros();
		insertarLibroValido(libros, libro1);
		insertarLibroValido(libros, libro2);
		try {
			libros.borrar(libro3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarLibroNuloLanzaExcepcion() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		try {
			libros.borrar(null);
			fail(LIBRO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarLibroNuloLanzaExcepcion() {
		ILibros libros = new Libros();
		insertarLibroValido(libros, libro1);
		try {
			libros.buscar(null);
			fail(LIBRO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}