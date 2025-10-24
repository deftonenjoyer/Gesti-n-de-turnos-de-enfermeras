package com.example.proyectosiafinal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<String> lineas;

        if ("Turnos con vacantes".equals(tipo)) {
            lineas = sistemaHospital.reporteTurnosConVacantes();
        } else if ("Enfermeras sin turno".equals(tipo)) {
            lineas = sistemaHospital.reporteEnfermerasSinTurno();
        } else if ("Cobertura por área".equals(tipo)) {
            lineas = sistemaHospital.reporteCoberturaPorArea();
        } else if ("Asignaciones por turno".equals(tipo)) {
            lineas = sistemaHospital.reporteAsignacionesPorTurno();
        } else if ("Diagnóstico de integridad".equals(tipo)) {
            lineas = sistemaHospital.reporteDiagnosticoIntegridad();
        } else {
            lineas = Collections.singletonList("Reporte no soportado.");
        }

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
            if (controller instanceof MenuController) {
                MenuController mic = (MenuController) controller;
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
