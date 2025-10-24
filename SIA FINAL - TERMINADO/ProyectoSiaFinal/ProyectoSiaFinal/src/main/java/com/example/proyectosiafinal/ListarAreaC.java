package com.example.proyectosiafinal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ListarAreaC {

    @FXML private ListView<String> lvAreas;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;
    private final ObservableList<String> datos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        lvAreas.setPlaceholder(new Label("No hay áreas registradas."));
        lvAreas.setItems(datos);

        btnSalir.setOnAction(e -> btnSalir.getScene().getWindow().hide());

        assert lvAreas != null : "fx:id=lvAreas no fue inyectado. Revisa el FXML";
        assert btnSalir != null : "fx:id=btnSalir no fue inyectado. Revisa el FXML";

        Platform.runLater(this::cargarAreas);
    }

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
        cargarAreas();
    }

    public void cargarAreas() {
        datos.clear();
        if (sistemaHospital == null) {
            System.out.println("[Lista] sistemaHospital es null (¿no lo pasaste al abrir la ventana?).");
            return;
        }

        int sizeLista = sistemaHospital.getAreasList().size();
        System.out.println("[Lista] Áreas -> total: " + sizeLista);

        if (sizeLista > 0) {
            sistemaHospital.getAreasList().forEach(area ->
                    datos.add(String.format("Área: %s", area.getNombre()))
            );
        }

        if (datos.isEmpty()) {
            System.out.println("[Lista] No hay áreas para mostrar. " +
                    "¿Llamaste a cargarDatos()? ¿Estás usando la MISMA instancia de SistemaHospital?");
        }
    }

    public void refrescar() { cargarAreas(); }
}
