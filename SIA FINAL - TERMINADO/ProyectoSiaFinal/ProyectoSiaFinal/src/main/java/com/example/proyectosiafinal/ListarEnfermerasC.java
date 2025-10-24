package com.example.proyectosiafinal;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ListarEnfermerasC {

    @FXML private ListView<String> lvEnfermeras;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;
    private final ObservableList<String> datos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {lvEnfermeras.setPlaceholder(new Label("No hay enfermeras registradas."));
        lvEnfermeras.setItems(datos);

        btnSalir.setOnAction(e -> btnSalir.getScene().getWindow().hide());

        assert lvEnfermeras != null : "fx:id=lvEnfermeras no fue inyectado. Revisa el FXML";
        assert btnSalir != null     : "fx:id=btnSalir no fue inyectado. Revisa el FXML";

        Platform.runLater(this::cargarEnfermeras);
    }

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
        cargarEnfermeras();
    }

    public void cargarEnfermeras() {
        datos.clear();
        if (sistemaHospital == null) {
            System.out.println("[Lista] sistemaHospital es null (¿no lo pasaste al abrir la ventana?).");
            return;
        }

        int sizeLista = sistemaHospital.getEnfermeras().size();
        int sizeMapa  = sistemaHospital.getMapEnfermeras().size();
        if (sizeMapa > 0) {
            sistemaHospital.getMapEnfermeras().entrySet().stream()
                    .sorted((a, b) -> Integer.compare(a.getKey(), b.getKey()))
                    .forEach(e -> datos.add(
                            String.format("ID: %d  |  %s", e.getKey(), e.getValue().getNombreEnfermera())
                    ));
        } else if (sizeLista > 0) {
            sistemaHospital.getEnfermeras().forEach(e ->
                    datos.add(String.format("ID: %d  |  %s", e.getIdEnfermera(), e.getNombreEnfermera()))
            );
        }

        if (datos.isEmpty()) {
            System.out.println("[Lista] No hay enfermeras para mostrar.");
        }
    }

    public void refrescar() { cargarEnfermeras(); }
}

