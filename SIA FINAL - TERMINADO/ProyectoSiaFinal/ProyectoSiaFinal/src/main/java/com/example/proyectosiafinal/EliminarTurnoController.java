package com.example.proyectosiafinal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EliminarTurnoController {
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private TextField tfAreaTurno;
    @FXML private TextField tfHorarioTurno;
    @FXML private Button btnEliminar;
    @FXML private Button btnSalir;

    @FXML
    private void initialize() {
        btnEliminar.setOnAction(e -> eliminarTurno());
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void eliminarTurno() {
        if (sistemaHospital == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El sistema no está inicializado.");
            return;
        }
        String area = tfAreaTurno.getText() != null ? tfAreaTurno.getText().trim() : "";
        String horario = tfHorarioTurno.getText() != null ? tfHorarioTurno.getText().trim() : "";
        if (area.isEmpty() || horario.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Debe ingresar un área y un horario.");
            return;
        }
        boolean ok = sistemaHospital.eliminarTurnoPorAreaYTipo(area, horario);
        if (ok) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Turno eliminado correctamente.");
            tfAreaTurno.clear();
            tfHorarioTurno.clear();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "No encontrado", "No se encontró un turno con esos datos.");
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
