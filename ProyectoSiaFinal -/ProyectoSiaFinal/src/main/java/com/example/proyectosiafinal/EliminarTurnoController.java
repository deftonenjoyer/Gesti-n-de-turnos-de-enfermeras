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

    @FXML
    private TextField tfAreaTruno;

    @FXML
    private TextField tfHorarioTurno;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnSalir;

    @FXML
    private void initialize() {
        btnEliminar.setOnAction(e -> eliminarTurno());
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void eliminarTurno() {
        String area = tfAreaTruno.getText().trim();
        String horario = tfHorarioTurno.getText().trim();

        if (area.isEmpty() || horario.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un área y un horario.");
            return;
        }

        // Aquí va la lógica de eliminación en tu modelo
        // Ejemplo si tienes un SistemaHospital con lista de turnos:
        // boolean eliminado = SistemaHospital.getInstance().eliminarTurno(area, horario);

        boolean eliminado = true; // <-- solo ejemplo
        if (eliminado) {
            mostrarAlerta("Éxito", "Turno eliminado correctamente.");
            tfAreaTruno.clear();
            tfHorarioTurno.clear();
        } else {
            mostrarAlerta("Error", "No se encontró un turno con esos datos.");
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
