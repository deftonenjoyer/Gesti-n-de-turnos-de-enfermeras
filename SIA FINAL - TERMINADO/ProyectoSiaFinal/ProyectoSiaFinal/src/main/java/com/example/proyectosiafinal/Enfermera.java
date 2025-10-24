package com.example.proyectosiafinal;

import java.util.ArrayList;
import java.util.List;

public class Enfermera {
    private int idEnfermera;
    private String nombreEnfermera;
    private List<Turno> turnoAsignado;

    public Enfermera(int idEnfermera, String nombreEnfermera) {
        this.idEnfermera = idEnfermera;
        this.nombreEnfermera = nombreEnfermera;
        this.turnoAsignado = new ArrayList<>();
    }

    public int getIdEnfermera() {
        return idEnfermera;
    }
    public void setIdEnfermera(int idEnfermera) {
        this.idEnfermera = idEnfermera;
    }

    public String getNombreEnfermera() {
        return nombreEnfermera;
    }
    public void setNombreEnfermera(String nombreEnfermera) {
        this.nombreEnfermera = nombreEnfermera;
    }

    public void asignarTurno(Turno turno) {
        turnoAsignado.add(turno);
    }

    public boolean tieneAlgunTurno() {
        return !turnoAsignado.isEmpty();
    }
    public String toString() {
        return "ID: " + idEnfermera + " | Nombre: " + nombreEnfermera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enfermera)) return false;
        Enfermera that = (Enfermera) o;
        return idEnfermera == that.idEnfermera;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idEnfermera);
    }

}
