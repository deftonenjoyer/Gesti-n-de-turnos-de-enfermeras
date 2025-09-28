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

public class AgregarEnfermeraController implements Initializable {
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private TextField tfID;
    @FXML private TextField tfNombre;
    @FXML private Button btnAgregar;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onAgregar(ActionEvent event) {
        String idText = tfID.getText().trim();
        String nombre = tfNombre.getText().trim();

        if (idText.isEmpty() || nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Campos incompletos", "Debes ingresar ID y nombre.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            if (sistemaHospital.getEnfermeraPorId(id) != null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error",
                        "ID duplicado", "Ya existe una enfermera con ID " + id);
                return;
            }

            Enfermera nueva = new Enfermera(id, nombre);
            sistemaHospital.agregarEnfermeraPorNombre(nombre);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Enfermera agregada", "ID: " + id + "\nNombre: " + nombre);

            tfID.clear();
            tfNombre.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Formato inválido", "El ID debe ser un número entero.");
        }
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