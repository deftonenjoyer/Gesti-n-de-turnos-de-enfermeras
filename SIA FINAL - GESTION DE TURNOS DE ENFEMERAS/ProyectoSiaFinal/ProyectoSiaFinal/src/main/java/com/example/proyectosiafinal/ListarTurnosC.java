package com.example.proyectosiafinal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ListarTurnosC {

    @FXML private ListView<String> lvTurnos;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;
    private final ObservableList<String> datos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        lvTurnos.setPlaceholder(new Label("No hay turnos registrados."));
        lvTurnos.setItems(datos);

        btnSalir.setOnAction(e -> btnSalir.getScene().getWindow().hide());

        assert lvTurnos != null : "fx:id=lvTurnos no fue inyectado. Revisa el FXML";
        assert btnSalir  != null : "fx:id=btnSalir no fue inyectado. Revisa el FXML";

        Platform.runLater(this::cargarTurnos);
    }

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
        cargarTurnos();
    }

    public void cargarTurnos() {
        datos.clear();
        if (sistemaHospital == null) {
            System.out.println("[ListaTurnos] sistemaHospital es null (¿no lo pasaste al abrir la ventana?).");
            return;
        }

        var lista = sistemaHospital.getTurnos();
        if (lista != null && !lista.isEmpty()) {
            lista.forEach(t -> {
                String area = (t.getArea() != null) ? t.getArea().getNombre() : "(sin área)";
                String tipo = (t.getTipoTurno() != null) ? t.getTipoTurno() : "(sin horario)";
                int cap = t.getEnfermerasNecesarias();
                int asignadas = (t.getEnfermerasAsignadas() != null) ? t.getEnfermerasAsignadas().size() : 0;

                String fila = String.format(
                        "Área: %s | Turno: %s | Capacidad: %d | Asignadas: %d",
                        area, tipo, cap, asignadas
                );
                datos.add(fila);
            });
        } else {
            System.out.println("[ListaTurnos] No hay turnos para mostrar.");
        }
    }

    public void refrescar() { cargarTurnos(); }
}
