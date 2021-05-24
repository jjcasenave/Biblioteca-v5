package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorBuscarPor {
	
	private IControlador controladorMVC;
	private ControladorVentanaPrincipal padre;
	
	private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();

    @FXML    private RadioButton rbAlumno;
    @FXML    private RadioButton rbLibro;
    @FXML    private RadioButton rbFecha;
    @FXML    private DatePicker dpFecha;
    
    @FXML    private ListView<Alumno> lvAlumnos;
    
    @FXML    private TableView<Libro> tvLibros;
    @FXML    private TableColumn<Libro, String> tcAutor;
    @FXML    private TableColumn<Libro, String> tcTitulo;
    
    @FXML    private TableView<Prestamo> tvPrestamos;
    @FXML    private TableColumn<Prestamo, String> tcTPtipo;
    @FXML    private TableColumn<Prestamo, String> tcTPtitulo;
    @FXML    private TableColumn<Prestamo, String> tcTPalumno;
    @FXML    private TableColumn<Prestamo, String> tcTPfechaPrestamo;
    @FXML    private TableColumn<Prestamo, String> tcTPfechaDevolucion;
    @FXML    private Button btAtras;
    
    private ToggleGroup tgEleccion = new ToggleGroup();
    
    private Stage insertarFechaDevolucion;
    private ControladorFechaDevolucion cFechaDevolucion;
    
    private class CeldaAlumno extends ListCell<Alumno> {
    	@Override
    	public void updateItem(Alumno alumno, boolean vacio) {
    		super.updateItem(alumno, vacio);
    		if (vacio) {
    			setText("");
    		} else {
    			setText(alumno.getCorreo());
    		}
    	}
    }
    
    @FXML
    private void initialize() {
    	rbAlumno.setToggleGroup(tgEleccion);
    	rbLibro.setToggleGroup(tgEleccion);
    	rbFecha.setToggleGroup(tgEleccion);
    	tgEleccion.selectedToggleProperty().addListener((ob, ov, nv) -> comprobarSeleccion());
    	
    	lvAlumnos.setItems(alumnos);
    	lvAlumnos.setCellFactory(l -> new CeldaAlumno());
    	lvAlumnos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	lvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> actualizaPrestamos());
    	
    	tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    	tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
    	tvLibros.setItems(libros);
    	tvLibros.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> actualizaPrestamos());
    	
    	tcTPtipo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getNombreClase()));
    	tcTPtitulo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
    	tcTPalumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getCorreo()));
    	tcTPfechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaPrestamo().toString()));
    	tcTPfechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaDevolucionString()));
    	tvPrestamos.setItems(prestamos);
    	
    	dpFecha.valueProperty().addListener((ob, ov, nv) -> actualizaPrestamos());
    }

    public void setControladorMVC(IControlador controladorMVC) {
    	this.controladorMVC = controladorMVC;
    }
    
    public void setPadre(ControladorVentanaPrincipal padre) {
    	this.padre = padre;
    }
    
    public void inicializa() {
    	tgEleccion.selectToggle(rbFecha);
    	dpFecha.setValue(LocalDate.now());
    }
    
    private void comprobarSeleccion() {
    	if (rbAlumno.isSelected()) {
    		lvAlumnos.setDisable(false);
    		tvLibros.setDisable(true);
    		dpFecha.setDisable(true);
    	} else if (rbLibro.isSelected()) {
    		lvAlumnos.setDisable(true);
    		tvLibros.setDisable(false);
    		dpFecha.setDisable(true);
		} else if (rbFecha.isSelected()) {
			lvAlumnos.setDisable(true);
    		tvLibros.setDisable(true);
    		dpFecha.setDisable(false);
		}
    }
    private void actualizaPrestamos() {
    	prestamos.clear();
    	tvPrestamos.getSelectionModel().clearSelection();
    	if (rbAlumno.isSelected()) {
    		Alumno alumno = null;
    		alumno = lvAlumnos.getSelectionModel().getSelectedItem();
    		prestamos.setAll(controladorMVC.getPrestamos(alumno));
    	} else if (rbLibro.isSelected()) {
    		Libro libro = null;
    		libro = tvLibros.getSelectionModel().getSelectedItem();
    		prestamos.setAll(controladorMVC.getPrestamos(libro));
    	} else if (rbFecha.isSelected()) {
			LocalDate fecha = dpFecha.getValue();
			prestamos.setAll(controladorMVC.getPrestamos(fecha));
		}
    	
    }
    
    public void actualizaLibros() {
    	tvLibros.getSelectionModel().clearSelection();
    	libros.setAll(controladorMVC.getLibros());
    }
    
    public void actualizaAlumnos() {
    	lvAlumnos.getSelectionModel().clearSelection();
    	alumnos.setAll(controladorMVC.getAlumnos());
    }
        
    @FXML
    void atras() {
    	((Stage) btAtras.getScene().getWindow()).close();
    }

    @FXML
    void borrarPrestamo() {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion("Borrar Préstamo", "¿Seguro que desea borrar el préstamo?", null)) {
    			controladorMVC.borrar(prestamo);
    			prestamos.remove(prestamo);
    			actualizaPrestamos();
    			padre.actualizarPrestamosEnCurso();
    			padre.actualizaPrestamosDevueltos();
    			Dialogos.mostrarDialogoInformacion("Borrar Préstamo", "Préstamo borrado con éxito");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Borrar Prestamo", e.getMessage());
    	}
    }

    @FXML
    void devolverPrestamo() {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
    			if (prestamo != null) {
    				crearDevolucion(prestamo);
    				insertarFechaDevolucion.showAndWait();
    				padre.actualizarPrestamosEnCurso();
    				padre.actualizaPrestamosDevueltos();
    			}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Devolver Préstamo", e.getMessage());
    	}
    }
    
    private void crearDevolucion(Prestamo prestamo) throws IOException {
    	if (insertarFechaDevolucion == null) {
    		insertarFechaDevolucion = new Stage();
    		FXMLLoader cargadorFechaDevolucion = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/DevolverPrestamo.fxml"));
    		VBox raizFechaDevolucion = cargadorFechaDevolucion.load();
    		cFechaDevolucion = cargadorFechaDevolucion.getController();
    		cFechaDevolucion.setControladorMVC(controladorMVC);
    		cFechaDevolucion.setPrestamo(prestamo);
    		cFechaDevolucion.inicializa();
    		
    		Scene escenaDevolucion = new Scene(raizFechaDevolucion);
    		insertarFechaDevolucion.setTitle("Devolución de Préstamo");
    		insertarFechaDevolucion.initModality(Modality.APPLICATION_MODAL);
    		insertarFechaDevolucion.setScene(escenaDevolucion);
    	} else {
    		cFechaDevolucion.setPrestamo(prestamo);
    		cFechaDevolucion.inicializa();
    	}
    }
    
}
