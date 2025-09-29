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

public class AgregarTurnoController implements Initializable {
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private TextField tfAreaTurno;
    @FXML private TextField tfHorarioTurno;
    @FXML private TextField tfCapacidadEnfermerasTurno;
    @FXML private Button btnAgregar;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @FXML
    private void onAgregar(ActionEvent event) {
        if (sistemaHospital == null) {
            mostrar(Alert.AlertType.ERROR, "Error", "Sistema no inicializado", "No se recibió SistemaHospital.");
            return;
        }
        String areaNombre = val(tfAreaTurno);
        String horario    = val(tfHorarioTurno);
        String capStr     = val(tfCapacidadEnfermerasTurno);
        if (areaNombre.isEmpty() || horario.isEmpty() || capStr.isEmpty()) {
            mostrar(Alert.AlertType.WARNING, "Validación", "Campos incompletos",
                    "Debes ingresar área, horario y capacidad.");
            return;
        }
        int capacidad;
        try {
            capacidad = Integer.parseInt(capStr);
            if (capacidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrar(Alert.AlertType.ERROR, "Formato inválido", "Capacidad inválida",
                    "Debe ser un entero mayor que 0.");
            return;
        }
        String horarioNorm = horario.trim().toUpperCase();
        if (!horarioNorm.equals("DIA") && !horarioNorm.equals("NOCHE")) {
            mostrar(Alert.AlertType.WARNING, "Validación", "Horario no válido",
                    "Usa 'DIA' o 'NOCHE'.");
            return;
        }
        Area area = sistemaHospital.getAreasList().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(areaNombre))
                .findFirst().orElse(null);

        if (area == null) {
            mostrar(Alert.AlertType.ERROR, "Área inexistente",
                    "No se encontró el área", "Agrega el área primero: " + areaNombre);
            return;
        }
        sistemaHospital.agregarTurno(area, horarioNorm, capacidad);
        sistemaHospital.guardarDatos();

        mostrar(Alert.AlertType.INFORMATION, "Éxito", "Turno agregado",
                "Área: " + area.getNombre() + "\nHorario: " + horarioNorm + "\nCapacidad: " + capacidad);

        tfAreaTurno.clear();
        tfHorarioTurno.clear();
        tfCapacidadEnfermerasTurno.clear();
        tfAreaTurno.requestFocus();
    }

    @FXML
    private void onSalir(ActionEvent event) {
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    private String val(TextField tf) {
        String s = tf.getText();
        return (s == null) ? "" : s.trim();
    }

    private void mostrar(Alert.AlertType t, String title, String header, String content) {
        Alert a = new Alert(t);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
