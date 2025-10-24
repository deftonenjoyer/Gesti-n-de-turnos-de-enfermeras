package com.example.proyectosiafinal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;

public class AdministracionEnfermerasController {

    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnListar;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;
    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML
    private void initialize() {
        btnAgregar.setOnAction(e -> abrirVentana("Agregar_Enfermera.fxml", "Agregar Enfermera"));
        btnEliminar.setOnAction(e -> abrirVentana("EliminarEnfermera.fxml", "Eliminar Enfermera"));
        btnListar.setOnAction(e -> abrirVentana("Lista_Enfermeras.fxml", "Listar Enfermeras"));
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AgregarEnfermeraController) {
                AgregarEnfermeraController c = (AgregarEnfermeraController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof EliminarEnfermeraController) {
                EliminarEnfermeraController c = (EliminarEnfermeraController) controller;
                c.setSistemaHospital(sistemaHospital);
            } else if (controller instanceof ListarEnfermerasC) {
                ListarEnfermerasC c = (ListarEnfermerasC) controller;
                c.setSistemaHospital(sistemaHospital);
            }

            Stage newStage = new Stage();
            newStage.setTitle(titulo);
            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
