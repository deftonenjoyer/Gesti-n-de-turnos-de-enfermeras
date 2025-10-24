package com.example.proyectosiafinal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    private SistemaHospital sistemaHospital;

    @Override
    public void start(Stage stage) throws IOException {
        sistemaHospital = new SistemaHospital();
        sistemaHospital.cargarDatos();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu_inicial.fxml"));
        Parent root = fxmlLoader.load();
        MenuController menuController = fxmlLoader.getController();
        menuController.setSistemaHospital(sistemaHospital);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Menú Principal");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> sistemaHospital.guardarDatos());
        stage.show();
    }

    @Override
    public void stop() {
        if (sistemaHospital != null) {
            sistemaHospital.guardarDatos();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

