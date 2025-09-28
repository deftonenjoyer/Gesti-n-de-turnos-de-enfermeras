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

public class EliminarAreaController implements Initializable {
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private TextField tfArea;
    @FXML private Button btnEliminar;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @FXML
    private void onEliminar(ActionEvent event) {
        if (sistemaHospital == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Sistema no inicializado",
                    "No se recibió la instancia de SistemaHospital.");
            return;
        }

        String nombreArea = (tfArea.getText() == null) ? "" : tfArea.getText().trim();
        if (nombreArea.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Campo vacío", "Debes ingresar el nombre del área a eliminar.");
            return;
        }

        boolean ok = sistemaHospital.eliminarAreaPorNombre(nombreArea);
        if (ok) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Área eliminada", "Se eliminó el área: " + nombreArea);
            tfArea.clear();
            tfArea.requestFocus();
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sin cambios",
                    "No encontrada", "No existe un área con el nombre: " + nombreArea);
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
