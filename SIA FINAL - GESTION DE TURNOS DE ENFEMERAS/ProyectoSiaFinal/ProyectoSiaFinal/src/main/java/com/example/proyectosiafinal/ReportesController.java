package com.example.proyectosiafinal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReportesController {

    @FXML private ComboBox<String> cbReporte;
    @FXML private TextArea taResultado;
    @FXML private Button btnGenerar;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
        inicializarCombo();
    }

    @FXML
    private void initialize() {
        taResultado.setEditable(false);
        btnGenerar.setOnAction(e -> generar());
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void inicializarCombo() {
        cbReporte.setItems(FXCollections.observableArrayList(
                "Turnos con vacantes",
                "Enfermeras sin turno",
                "Cobertura por área",
                "Asignaciones por turno",
                "Diagnóstico de integridad"
        ));
        cbReporte.getSelectionModel().selectFirst();
    }

    private void generar() {
        if (sistemaHospital == null) {
            taResultado.setText("No hay modelo.");
            return;
        }
        String tipo = cbReporte.getValue();
        java.util.List<String> lineas = switch (tipo) {
            case "Turnos con vacantes"   -> sistemaHospital.reporteTurnosConVacantes();
            case "Enfermeras sin turno"  -> sistemaHospital.reporteEnfermerasSinTurno();
            case "Cobertura por área"    -> sistemaHospital.reporteCoberturaPorArea();
            case "Asignaciones por turno"-> sistemaHospital.reporteAsignacionesPorTurno();
            case "Diagnóstico de integridad" -> sistemaHospital.reporteDiagnosticoIntegridad();
            default -> java.util.List.of("Reporte no soportado.");
        };
        taResultado.setText(String.join("\n", lineas));
    }
    private void cerrarVentana() {
        try {
            if (sistemaHospital != null) {
                sistemaHospital.guardarDatos();
            }
            SistemaHospital fresh = new SistemaHospital();
            fresh.cargarDatos();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/proyectosiafinal/menu_inicial.fxml")
            );
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof MenuController mic) {
                mic.setSistemaHospital(fresh);
            }

            Stage currentStage = (Stage) btnSalir.getScene().getWindow();
            currentStage.setTitle("Menú Principal");
            currentStage.setScene(new Scene(root));

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo volver al menú:\n" + ex.getMessage()).showAndWait();
        }
    }
}
