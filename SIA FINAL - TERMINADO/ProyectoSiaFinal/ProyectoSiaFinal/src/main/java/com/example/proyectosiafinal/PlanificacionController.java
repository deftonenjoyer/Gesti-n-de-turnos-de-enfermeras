package com.example.proyectosiafinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PlanificacionController {

    @FXML private ListView<String> lvEnfermeras;
    @FXML private ListView<String> lvTurnos;
    @FXML private Button btnAsignar;
    @FXML private Button btnSalir;

    private SistemaHospital sistemaHospital;
    private ObservableList<String> datosEnfermeras = FXCollections.observableArrayList();
    private ObservableList<String> datosTurnos = FXCollections.observableArrayList();

    public void setSistemaHospital(SistemaHospital sistemaHospital) {
        this.sistemaHospital = sistemaHospital;
        cargarDatos();
    }

    private void cargarDatos() {
        datosEnfermeras.clear();
        datosTurnos.clear();

        for (Enfermera e : sistemaHospital.getEnfermerasDisponibles()) {
            datosEnfermeras.add("ID: " + e.getIdEnfermera() + " | " + e.getNombreEnfermera());
        }
        for (Turno t : sistemaHospital.getTurnosConCupos()) {
            datosTurnos.add(t.getArea().getNombre() + " | " + t.getTipoTurno() +
                    " (Cupo: " + (t.getEnfermerasNecesarias() - t.getEnfermerasAsignadas().size()) + ")");
        }

        lvEnfermeras.setItems(datosEnfermeras);
        lvTurnos.setItems(datosTurnos);
    }

    @FXML
    private void initialize() {
        btnAsignar.setOnAction(e -> asignar());
        btnSalir.setOnAction(e -> cerrarVentana());
    }

    private void asignar() {
        int idxE = lvEnfermeras.getSelectionModel().getSelectedIndex();
        int idxT = lvTurnos.getSelectionModel().getSelectedIndex();

        if (idxE < 0 || idxT < 0) {
            mostrarAlerta("Debes seleccionar una enfermera y un turno.");
            return;
        }

        Enfermera enfermera = sistemaHospital.getEnfermerasDisponibles().get(idxE);
        Turno turno = sistemaHospital.getTurnosConCupos().get(idxT);

        if (sistemaHospital.asignarEnfermeraATurno(enfermera.getIdEnfermera(), turno)) {
            mostrarAlerta("Enfermera asignada con éxito.");
            cargarDatos();
        } else {
            mostrarAlerta("No se pudo asignar. Verifica disponibilidad o cupos.");
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

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
