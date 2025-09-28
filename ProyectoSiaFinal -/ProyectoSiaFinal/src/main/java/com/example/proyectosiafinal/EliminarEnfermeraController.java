package com.example.proyectosiafinal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class EliminarEnfermeraController {

    @FXML private Button btnSalir;
    @FXML private Button btnEliminar;
    @FXML private TextField txtBuscar;

    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML
    public void initialize() {
        btnEliminar.setOnAction(this::eliminar);
        btnSalir.setOnAction(e -> btnSalir.getScene().getWindow().hide());
    }

    private void eliminar(ActionEvent event) {
        if (sistemaHospital == null) {
            alert(Alert.AlertType.ERROR, "Error", "Sistema no inicializado",
                    "No se recibió la instancia de SistemaHospital.");
            return;
        }

        String texto = (txtBuscar.getText() == null) ? "" : txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Validación", "Campo vacío",
                    "Ingrese un ID numérico o un nombre.");
            return;
        }

        try {
            int id = Integer.parseInt(texto);
            boolean ok = sistemaHospital.eliminarEnfermeraPorId(id);
            if (ok) {
                alert(Alert.AlertType.INFORMATION, "Eliminación exitosa",
                        "Se eliminó la enfermera", "ID: " + id);
            } else {
                alert(Alert.AlertType.INFORMATION, "Sin cambios",
                        "No se encontró enfermera", "ID: " + id);
            }
            return;
        } catch (NumberFormatException ignore) {

        }

        int count = sistemaHospital.eliminarEnfermeraPorNombre(texto);
        if (count > 0) {
            alert(Alert.AlertType.INFORMATION, "Eliminación exitosa",
                    "Se eliminaron " + count + " registro(s)",
                    "Nombre: " + texto);
        } else {
            alert(Alert.AlertType.INFORMATION, "Sin cambios",
                    "No se encontró enfermera", "Nombre: " + texto);
        }
    }

    private void alert(Alert.AlertType type, String title, String header, String content) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
