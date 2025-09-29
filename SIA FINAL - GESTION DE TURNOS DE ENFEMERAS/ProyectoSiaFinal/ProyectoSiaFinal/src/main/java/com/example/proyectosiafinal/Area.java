package com.example.proyectosiafinal;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private String nombre;

    public Area(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Área: " + nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area that = (Area) o;
        return nombre != null && nombre.equalsIgnoreCase(that.nombre);
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.toLowerCase().hashCode() : 0;
    }


}





