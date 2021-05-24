package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorAnadirPrestamo {
	
	private IControlador controladorMVC;
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ControladorVentanaPrincipal padre;
	
    @FXML    private TableView<Libro> tvLibros;
    @FXML	 private TableColumn<Libro, String> tcTLtipo;
    @FXML    private TableColumn<Libro, String> tcTLtitulo;
    @FXML    private TableColumn<Libro, String> tcTLautor;
    
    @FXML    private ListView<Alumno> lvAlumnos;
    @FXML    private Button btAtras;
    @FXML    private Button btAnadir;
    @FXML	 private DatePicker dpFecha;

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
    	
    	tcTLtipo.setCellValueFactory(libro -> new SimpleStringProperty(libro.getValue().getNombreClase()));
    	tcTLtitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    	tcTLautor.setCellValueFactory(new PropertyValueFactory<>("autor"));
    	tvLibros.setItems(libros);
    	tvLibros.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	
    	lvAlumnos.setItems(alumnos);
    	lvAlumnos.setCellFactory(l -> new CeldaAlumno());
    	lvAlumnos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
    public void setPadre(ControladorVentanaPrincipal padre) {
    	this.padre = padre;
    }
    
    @FXML
    void anadirPrestamo() {
    	Prestamo prestamo = null;
    	Alumno alumno = lvAlumnos.getSelectionModel().getSelectedItem();
    	LocalDate dia = dpFecha.getValue();
    	Libro libro = tvLibros.getSelectionModel().getSelectedItem();
    	try {
    		prestamo = new Prestamo(alumno, libro, dia);
    		controladorMVC.prestar(prestamo);
    		padre.actualizarPrestamosEnCurso();
    		Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Préstamo", "Préstamo añadido de forma correcta.", propietario);
    	} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Prestamo", e.getMessage());
		}
    }

    @FXML
    void atras() {
    	((Stage) btAtras.getScene().getWindow()).close();
    }
    
    public void inicializa() {
    	lvAlumnos.getSelectionModel().clearSelection();
    	tvLibros.getSelectionModel().clearSelection();
    	dpFecha.setValue(LocalDate.now());
    }
    
    public void actualizaLibros() {
    	tvLibros.getSelectionModel().clearSelection();
    	libros.setAll(controladorMVC.getLibros());
    }
    
    public void actualizaAlumnos() {
    	lvAlumnos.getSelectionModel().clearSelection();
    	alumnos.setAll(controladorMVC.getAlumnos());
    }

}
