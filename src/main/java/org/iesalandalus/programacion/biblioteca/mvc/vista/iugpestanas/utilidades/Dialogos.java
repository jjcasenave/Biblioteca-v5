package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades;

import java.util.Optional;

import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogos {
	
	private static final String CSS = "estilos/estilos.css";
	private static final String ID_BT_ACEPTAR = "btAceptar";
	private static final String ID_BT_CANCELAR = "btCancelar";
	
	private Dialogos() {
		//Evitamos que se puedan instanciar objetos
	}
	
	public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.ERROR);
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoError(String titulo, String contenido) {
		Dialogos.mostrarDialogoError(titulo, contenido, null);
	}
	
	public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoInformacion(String titulo, String contenido) {
		Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
	}
	
	public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(null);
		dialogo.getDialogPane().setHeader(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido) {
		Dialogos.mostrarDialogoInformacionPersonalizado(titulo, contenido, null);
	}
	
	public static void mostrarDialogoAdvertencia(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.WARNING);
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoAdvertencia(String titulo, String contenido) {
		Dialogos.mostrarDialogoAdvertencia(titulo, contenido, null);
	}
	
	public static String mostrarDialogoTexto(String titulo, String contenido) {
		TextInputDialog dialogo = new TextInputDialog();
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);

		Optional<String> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() ? respuesta.get() : null);
	}
	
	public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		dialogo.getDialogPane().getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}
	
}
