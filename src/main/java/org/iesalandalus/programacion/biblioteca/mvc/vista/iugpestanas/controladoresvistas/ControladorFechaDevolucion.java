package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ControladorFechaDevolucion {

	private Prestamo prestamo;
	private IControlador controladorMVC;
	
    @FXML    private Button btAtras;
    @FXML    private Button btDevolver;
    @FXML    private DatePicker dpFechaDevolucion;
    
    public void setControladorMVC(IControlador controladorMVC) {
    	this.controladorMVC = controladorMVC;
    }

    @FXML
    void atras() {
    	((Stage) btAtras.getScene().getWindow()).close();
    }

    @FXML
    void devolver() {
    	LocalDate fechaDevolucion = dpFechaDevolucion.getValue();
    	try {
    		controladorMVC.devolver(prestamo, fechaDevolucion);
    		Stage propietario = ((Stage) btDevolver.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Préstamo", "Préstamo devuelto de forma correcta.", propietario);
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Devolver Préstamo", e.getMessage());
    	}
    }
    
    public void inicializa() {
    	dpFechaDevolucion.setValue(LocalDate.now());
    }

    public void setPrestamo(Prestamo prestamo) {
    	this.prestamo = prestamo;
    }
    
}