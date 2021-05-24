package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;
import java.util.Map;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorEstadisticas {
	
	private IControlador controladorMVC;

    @FXML    private DatePicker dpFecha;
    @FXML    private TextField tfPrimero;
    @FXML    private TextField tfSegundo;
    @FXML    private TextField tfTercero;
    @FXML    private TextField tfCuarto;
    @FXML    private Button btAtras;

    @FXML
    private void initialize() {
    	dpFecha.valueProperty().addListener((ob, ov, nv) -> comprobarFecha(dpFecha));
    }
    
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
    @FXML
    void atras() {
    	((Stage) btAtras.getScene().getWindow()).close();
    }
    
    public void inicializa() {
    	dpFecha.setValue(LocalDate.now());
    	Map<Curso, Integer> mapa = controladorMVC.getEstadisticasMensualPorCurso(LocalDate.now());
    	tfPrimero.setText(mapa.get(Curso.PRIMERO).toString());
    	tfSegundo.setText(mapa.get(Curso.SEGUNDO).toString());
    	tfTercero.setText(mapa.get(Curso.TERCERO).toString());
    	tfCuarto.setText(mapa.get(Curso.CUARTO).toString());
    }

    private void comprobarFecha(DatePicker dp) {
    	Map<Curso, Integer> mapa = controladorMVC.getEstadisticasMensualPorCurso(dp.getValue());
    	tfPrimero.setText(mapa.get(Curso.PRIMERO).toString());
    	tfSegundo.setText(mapa.get(Curso.SEGUNDO).toString());
    	tfTercero.setText(mapa.get(Curso.TERCERO).toString());
    	tfCuarto.setText(mapa.get(Curso.CUARTO).toString());
    }
    
}

