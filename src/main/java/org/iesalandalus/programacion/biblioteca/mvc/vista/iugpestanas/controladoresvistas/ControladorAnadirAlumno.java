package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirAlumno {
	
	private static String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
	private static String ER_CORREO = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
	
	private IControlador controladorMVC;
	private ObservableList<Alumno> alumnos;
	
	@FXML private TextField tfCorreo;
	@FXML private TextField tfNombre;
	@FXML private RadioButton rbPrimero;
	@FXML private RadioButton rbSegundo;
	@FXML private RadioButton rbTercero;
	@FXML private RadioButton rbCuarto;
	@FXML private Button btAtras;
	@FXML private Button btAnadir;
	
	private ToggleGroup tgCurso = new ToggleGroup();

	@FXML
	private void initialize() {
		rbPrimero.setToggleGroup(tgCurso);
		rbSegundo.setToggleGroup(tgCurso);
		rbTercero.setToggleGroup(tgCurso);
		rbCuarto.setToggleGroup(tgCurso);
		tfNombre.textProperty().addListener((ob, ov, nv) -> comprobarTexto(ER_NOMBRE, tfNombre));
		tfCorreo.textProperty().addListener((ob, ov, nv) -> comprobarTexto(ER_CORREO, tfCorreo));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setAlumnos(ObservableList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	@FXML
	void anadirAlumno() {
		Alumno alumno = null;
		try {
			alumno = getAlumno();
			controladorMVC.insertar(alumno);
			alumnos.setAll(controladorMVC.getAlumnos());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Alumno", "Alumno añadido de forma correcta.", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Alumno", e.getMessage());
		}
	}

	@FXML
	void atras() {
		((Stage) btAtras.getScene().getWindow()).close();
	}
	
	
	public void inicializa() {
		tfNombre.setText("");
		comprobarTexto(ER_NOMBRE, tfNombre);
		tfCorreo.setText("");
		comprobarTexto(ER_CORREO, tfCorreo);
		tgCurso.selectToggle(rbPrimero);
	}
	
	private void comprobarTexto(String er, TextField campo) {
		String texto = campo.getText();
		if (texto.matches(er)) {
			campo.setStyle("-fx-border-color: green");
		} else {
			campo.setStyle("-fx-border-color: red");
		}
		if (tfNombre.getText().matches(ER_NOMBRE)&&tfCorreo.getText().matches(ER_CORREO)) {
			btAnadir.setDisable(false);
		} else {
			btAnadir.setDisable(true);
		}
	}
	
	private Alumno getAlumno() {
		String nombre = tfNombre.getText();
		String correo = tfCorreo.getText();
		Curso curso = null;
		if (rbPrimero.isSelected()) {
			curso = Curso.PRIMERO;
		} else if (rbSegundo.isSelected()) {
			curso = Curso.SEGUNDO;
		} else if (rbTercero.isSelected()) {
			curso = Curso.TERCERO;
		} else {
			curso = Curso.CUARTO;
		}
		return new Alumno(nombre, correo, curso);
	}
	
}
