package com.example.proyectosiafinal;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Administracion_TurnosController{
    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }



    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnCambiar;
    @FXML
    private Button btnSalir;

    @FXML
    public void initialize() {
        btnAgregar.setOnAction(e -> abrirVentana("Agregar_Turnos.fxml", "Agregar Turnos"));
        btnEliminar.setOnAction(e -> abrirVentana("Eliminar_Turno.fxml", "Eliminar Turnos"));
        btnListar.setOnAction(e -> abrirVentana("Lista_Turnos.fxml", "Listar Turnos"));
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof AgregarTurnoController) {
                ((AgregarTurnoController) controller).setSistemaHospital(sistemaHospital);
            } else if (controller instanceof EliminarTurnoController) {
                ((EliminarTurnoController) controller).setSistemaHospital(sistemaHospital);
            } else if (controller instanceof ListarTurnosC) {
                ((ListarTurnosC) controller).setSistemaHospital(sistemaHospital);
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
            if (controller instanceof MenuController mic) {
                mic.setSistemaHospital(fresh);
            }

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.setTitle("Menú Principal");
            stage.setScene(new Scene(root));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
