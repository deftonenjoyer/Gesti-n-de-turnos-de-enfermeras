package com.example.proyectosiafinal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    private SistemaHospital sistemaHospital;
    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private Button btnEnfermeras;
    @FXML private Button btnTurnos;
    @FXML private Button btnAreas;
    @FXML private Button btnPlanificacion;
    @FXML private Button btnReportes;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEnfermeras.setOnAction(e -> abrirEnElMismoStage("Administracion_Enfermeras.fxml", "Administrar Enfermeras"));
        btnTurnos.setOnAction(e -> abrirEnElMismoStage("Administracion_Turnos.fxml", "Administrar Turnos"));
        btnAreas.setOnAction(e -> abrirEnElMismoStage("Administracion_Areas.fxml", "Administrar Áreas"));
        btnPlanificacion.setOnAction(e -> abrirEnElMismoStage("PlanificacionTurnos.fxml", "Planificar Turnos"));
        btnReportes.setOnAction(e -> abrirEnElMismoStage("Reportes.fxml", "Reportes"));
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void abrirEnElMismoStage(String fxmlAbsoluto, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlAbsoluto));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AdministracionEnfermerasController) {
                AdministracionEnfermerasController c = (AdministracionEnfermerasController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof Administracion_TurnosController) {
                Administracion_TurnosController c = (Administracion_TurnosController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof Administracion_AreasController) {
                Administracion_AreasController c = (Administracion_AreasController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof PlanificacionController) {
                PlanificacionController c = (PlanificacionController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof ReportesController) {
                ReportesController c = (ReportesController) controller;
                c.setSistemaHospital(sistemaHospital);
            }

            Stage stage = (Stage) btnEnfermeras.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        try {
            if (sistemaHospital != null) {
                sistemaHospital.guardarDatos();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Platform.exit();
        }
    }
}
