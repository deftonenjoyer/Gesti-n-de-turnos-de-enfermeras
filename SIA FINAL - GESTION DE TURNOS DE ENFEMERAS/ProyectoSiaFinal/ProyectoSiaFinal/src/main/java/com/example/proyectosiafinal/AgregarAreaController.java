package com.example.proyectosiafinal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarAreaController implements Initializable {
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private TextField tfAreaTurno;
    @FXML private Button btnAgregar;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onAgregar(ActionEvent event) {
        if (sistemaHospital == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Sistema no inicializado", "No se recibió la instancia de SistemaHospital.");
            return;
        }

        String nombre = (tfAreaTurno.getText() == null) ? "" : tfAreaTurno.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Campo vacío", "Debes ingresar el nombre del área.");
            return;
        }

        if (sistemaHospital.existeArea(nombre)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Área duplicada",
                    "Nombre ya existente", "Ya existe un área con el nombre: " + nombre);
            return;
        }

        sistemaHospital.agregarAreaPorNombre(nombre);
        sistemaHospital.guardarDatos();

        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                "Área agregada", "Se agregó el área: " + nombre);

        tfAreaTurno.clear();
        tfAreaTurno.requestFocus();
    }

    @FXML
    private void onSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String header, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
