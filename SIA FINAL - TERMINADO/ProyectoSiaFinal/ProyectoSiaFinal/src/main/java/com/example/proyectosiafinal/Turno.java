package com.example.proyectosiafinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Turno {
    private Area area;
    private String tipoTurno;
    private final List<Enfermera> enfermerasAsignadas;
    private int enfermerasNecesarias;

    public Turno(Area area, String tipoTurno, int enfermerasNecesarias){
        this.area = area;
        this.tipoTurno = tipoTurno;
        this.enfermerasNecesarias = enfermerasNecesarias;
        this.enfermerasAsignadas = new ArrayList<>();
    }

    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }

    public String getTipoTurno() { return tipoTurno; }

    public List<Enfermera> getEnfermerasAsignadas(){ return Collections.unmodifiableList(enfermerasAsignadas); }

    public int getEnfermerasNecesarias(){ return enfermerasNecesarias; }


    boolean asignarEnfermera(Enfermera e) {
        if (e == null) return false;
        if (enfermerasAsignadas.contains(e)) return false;
        if (enfermerasAsignadas.size() >= enfermerasNecesarias) return false;
        enfermerasAsignadas.add(e);
        return true;
    }

    @Override
    public String toString() {
        String areaNombre = (area != null) ? area.getNombre() : "(sin área)";
        return "Área: " + areaNombre +
                " | Turno: " + tipoTurno +
                " | Capacidad: " + enfermerasAsignadas.size() + "/" + enfermerasNecesarias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turno)) return false;
        Turno that = (Turno) o;
        return tipoTurno != null && tipoTurno.equalsIgnoreCase(that.tipoTurno) &&
                area != null && area.equals(that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, tipoTurno != null ? tipoTurno.toLowerCase() : "");
    }


}
