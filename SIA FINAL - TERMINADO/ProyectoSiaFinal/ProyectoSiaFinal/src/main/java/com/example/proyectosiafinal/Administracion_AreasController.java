package com.example.proyectosiafinal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Administracion_AreasController implements Initializable {

    private SistemaHospital sistemaHospital;

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
    }

    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnListar;
    @FXML private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregar.setOnAction(e -> abrirVentana("Agregar_Area.fxml", "Agregar Areas"));
        btnEliminar.setOnAction(e -> abrirVentana("Eliminar_Area.fxml", "Eliminar_Areas"));
        btnListar.setOnAction(e -> abrirVentana("Lista_Areas.fxml", "Listar Areas"));
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof AgregarAreaController) {
                ((AgregarAreaController) controller).setSistemaHospital(sistemaHospital);
            } else if (controller instanceof EliminarAreaController) {
                ((EliminarAreaController) controller).setSistemaHospital(sistemaHospital);
            } else if (controller instanceof ListarAreaC) {
                ((ListarAreaC) controller).setSistemaHospital(sistemaHospital);
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

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.setTitle("Menú Principal");
            stage.setScene(new Scene(root));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
