package com.example.proyectosiafinal;

import java.io.*;
import java.util.*;

public class SistemaHospital {
    private List<Enfermera> enfermeras;
    private List<Turno> turnos;
    private List<Area> areasList;
    private Map<Integer, Enfermera> mapEnfermeras;
    private static final String ARCHIVO = "hospital.csv";

    public SistemaHospital() {
        enfermeras = new ArrayList<>();
        turnos = new ArrayList<>();
        areasList = new ArrayList<>();
        mapEnfermeras = new HashMap<>();
    }

    public void guardarDatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hospital.csv"))) {
            for (Enfermera e : enfermeras) {
                bw.write("ENFERMERA;" + e.getIdEnfermera() + ";" + e.getNombreEnfermera());
                bw.newLine();
            }
            for (Area a : areasList) {
                bw.write("AREA;" + a.getNombre());
                bw.newLine();
            }
            for (Turno t : turnos) {
                bw.write("TURNO;" + t.getArea().getNombre() + ";" + t.getTipoTurno() + ";" + t.getEnfermerasNecesarias());
                bw.newLine();
            }
            for (int i = 0; i < turnos.size(); i++) {
                Turno t = turnos.get(i);
                for (Enfermera e : t.getEnfermerasAsignadas()) {
                    bw.write("ASIG;" + i + ";" + e.getIdEnfermera());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        File archivo = new File("hospital.csv");
        if (!archivo.exists()) return;
        enfermeras.clear();
        areasList.clear();
        turnos.clear();
        mapEnfermeras.clear();

        List<String[]> asignPend = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                switch (partes[0]) {
                    case "ENFERMERA": {
                        int id = Integer.parseInt(partes[1]);
                        String nombre = partes[2];
                        Enfermera e = new Enfermera(id, nombre);
                        enfermeras.add(e);
                        mapEnfermeras.put(id, e);
                        break;
                    }
                    case "AREA": {
                        areasList.add(new Area(partes[1]));
                        break;
                    }
                    case "TURNO": {
                        String nombreArea = partes[1];
                        Area areaTurno = null;
                        for (Area ar : areasList) {
                            if (ar.getNombre().equals(nombreArea)) {
                                areaTurno = ar; break;
                            }
                        }
                        if (areaTurno != null) {
                            String tipoTurno = partes[2];
                            int capacidad = Integer.parseInt(partes[3]);
                            turnos.add(new Turno(areaTurno, tipoTurno, capacidad));
                        }
                        break;
                    }
                    case "ASIG": {
                        asignPend.add(partes);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String[] p : asignPend) {
            int turnoIndex = Integer.parseInt(p[1]);
            int enfId = Integer.parseInt(p[2]);
            if (turnoIndex >= 0 && turnoIndex < turnos.size()) {
                Turno t = turnos.get(turnoIndex);
                Enfermera enf = mapEnfermeras.get(enfId);
                if (t != null && enf != null) {
                    t.asignarEnfermera(enf);
                    enf.asignarTurno(t);
                }
            }
        }
    }

    public boolean agregarEnfermera(int id, String nombre) {
        if (mapEnfermeras.containsKey(id)) return false;
        Enfermera e = new Enfermera(id, nombre);
        enfermeras.add(e);
        mapEnfermeras.put(id, e);
        guardarDatos();
        return true;
    }


    public boolean eliminarEnfermeraPorId(int id) {
        Enfermera eliminada = mapEnfermeras.remove(id);
        if (eliminada != null) {
            enfermeras.removeIf(e -> e.getIdEnfermera() == id);
            guardarDatos();
            return true;
        }
        return false;
    }

    public int eliminarEnfermeraPorNombre(String nombre) {
        List<Integer> ids = new ArrayList<>();
        for (Map.Entry<Integer, Enfermera> entry : mapEnfermeras.entrySet()) {
            if (entry.getValue().getNombreEnfermera().equalsIgnoreCase(nombre)) {
                ids.add(entry.getKey());
            }
        }
        for (Integer id : ids) {
            mapEnfermeras.remove(id);
            enfermeras.removeIf(e -> e.getIdEnfermera() == id);
        }
        if (!ids.isEmpty()) {
            guardarDatos();
        }
        return ids.size();
    }

    public void agregarAreaPorNombre(String nombre){
        Area nuevaArea = new Area(nombre);
        for (Area area : areasList) {
            if (area.getNombre().equalsIgnoreCase(nuevaArea.getNombre())) {
                return;
            }
        }
        areasList.add(nuevaArea);
    }

    public boolean existeArea(String nombre) {
        for (Area area : areasList) {
            if (area.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }


    public boolean eliminarAreaPorNombre(String nombre) {
        if (nombre == null) return false;
        boolean eliminado = areasList.removeIf(a -> a.getNombre().equalsIgnoreCase(nombre.trim()));
        if (eliminado) guardarDatos();
        return eliminado;
    }

    public java.util.List<Area> getAreasList() {
        return new java.util.ArrayList<>(areasList);
    }


    public java.util.List<Turno> getTurnos() {
        return new java.util.ArrayList<>(turnos);
    }


    public void agregarTurno(Area area, String tipoTurno, int capacidadEnfermeras){
        Turno t = new Turno(area, tipoTurno, capacidadEnfermeras);
        turnos.add(t);
    }
    public boolean eliminarTurnoPorAreaYTipo(String nombreArea, String tipoTurno) {
        if (nombreArea == null || tipoTurno == null) return false;
        boolean eliminado = false;

        Iterator<Turno> it = turnos.iterator();
        while (it.hasNext()) {
            Turno t = it.next();
            String areaDeTurno = (t.getArea() != null) ? t.getArea().getNombre() : null;
            if (areaDeTurno != null
                    && areaDeTurno.equalsIgnoreCase(nombreArea.trim())
                    && t.getTipoTurno() != null
                    && t.getTipoTurno().equalsIgnoreCase(tipoTurno.trim())) {

                it.remove();
                eliminado = true;
            }
        }

        if (eliminado) {
            guardarDatos(); // persiste en CSV
        }
        return eliminado;
    }


    public List<Enfermera> getEnfermeras() {
        return new ArrayList<>(enfermeras);
    }

    public Map<Integer, Enfermera> getMapEnfermeras() {
        return new HashMap<>(mapEnfermeras);
    }

    public List<Enfermera> getEnfermerasDisponibles() {
        List<Enfermera> disponibles = new ArrayList<>();
        for (Enfermera e : enfermeras) {
            if (!e.tieneAlgunTurno()) {
                disponibles.add(e);
            }
        }
        return disponibles;
    }

    public List<Turno> getTurnosConCupos() {
        List<Turno> faltantes = new ArrayList<>();
        for (Turno t : turnos) {
            if (t.getEnfermerasAsignadas().size() < t.getEnfermerasNecesarias()) {
                faltantes.add(t);
            }
        }
        return faltantes;
    }

    public boolean asignarEnfermeraATurno(int enfermeraId, Turno turno) {
        Enfermera e = mapEnfermeras.get(enfermeraId);
        if (e == null || turno == null) return false;
        if (e.tieneAlgunTurno()) return false;
        if (turno.getEnfermerasAsignadas().size() >= turno.getEnfermerasNecesarias()) return false;
        turno.asignarEnfermera(e);
        e.asignarTurno(turno);
        guardarDatos();
        return true;
    }

    public java.util.List<String> reporteTurnosConVacantes() {
        java.util.List<String> out = new java.util.ArrayList<>();
        if (turnos.isEmpty()) { out.add("No hay turnos."); return out; }
        for (Turno t : turnos) {
            int asignadas = t.getEnfermerasAsignadas().size();
            int cap = t.getEnfermerasNecesarias();
            if (asignadas < cap) {
                String area = (t.getArea() != null) ? t.getArea().getNombre() : "(sin área)";
                out.add(String.format("Área: %s | Turno: %s | %d/%d (faltan %d)",
                        area, t.getTipoTurno(), asignadas, cap, (cap - asignadas)));
            }
        }
        if (out.isEmpty()) out.add("Todos los turnos están completos.");
        return out;
    }

    public java.util.List<String> reporteEnfermerasSinTurno() {
        java.util.List<String> out = new java.util.ArrayList<>();
        if (enfermeras.isEmpty()) { out.add("No hay enfermeras registradas."); return out; }
        for (Enfermera e : enfermeras) {
            if (!e.tieneAlgunTurno()) {
                out.add(String.format("ID: %d | %s", e.getIdEnfermera(), e.getNombreEnfermera()));
            }
        }
        if (out.isEmpty()) out.add("No hay enfermeras disponibles; todas tienen turno.");
        return out;
    }

    public java.util.List<String> reporteCoberturaPorArea() {
        java.util.Map<String, int[]> acum = new java.util.HashMap<>();
        for (Turno t : turnos) {
            String area = (t.getArea() != null) ? t.getArea().getNombre() : "(sin área)";
            int asignadas = t.getEnfermerasAsignadas().size();
            int cap = Math.max(0, t.getEnfermerasNecesarias());
            int[] v = acum.computeIfAbsent(area, k -> new int[2]);
            v[0] += asignadas;
            v[1] += cap;
        }
        java.util.List<String> out = new java.util.ArrayList<>();
        if (acum.isEmpty()) { out.add("No hay turnos registrados."); return out; }
        acum.entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .forEach(e -> {
                    String area = e.getKey();
                    int totalAsig = e.getValue()[0];
                    int totalCap  = e.getValue()[1];
                    double pct = (totalCap == 0) ? 100.0 : (100.0 * totalAsig / totalCap);
                    out.add(String.format("Área: %s | Cobertura: %.1f%%  (%d/%d)", area, pct, totalAsig, totalCap));
                });
        return out;
    }

    public java.util.List<String> reporteAsignacionesPorTurno() {
        java.util.List<String> out = new java.util.ArrayList<>();
        if (turnos.isEmpty()) { out.add("No hay turnos."); return out; }
        for (Turno t : turnos) {
            String area = (t.getArea() != null) ? t.getArea().getNombre() : "(sin área)";
            String header = String.format("Área: %s | Turno: %s | Capacidad: %d",
                    area, t.getTipoTurno(), t.getEnfermerasNecesarias());
            out.add(header);
            if (t.getEnfermerasAsignadas().isEmpty()) {
                out.add("  - (sin enfermeras asignadas)");
            } else {
                for (Enfermera e : t.getEnfermerasAsignadas()) {
                    out.add(String.format("  - ID: %d | %s", e.getIdEnfermera(), e.getNombreEnfermera()));
                }
            }
        }
        return out;
    }

    public java.util.List<String> reporteDiagnosticoIntegridad() {
        java.util.List<String> out = new java.util.ArrayList<>();
        java.util.Set<Integer> set = new java.util.HashSet<>();
        java.util.List<Integer> dups = new java.util.ArrayList<>();
        for (Enfermera e : enfermeras) {
            if (!set.add(e.getIdEnfermera())) dups.add(e.getIdEnfermera());
        }
        if (!dups.isEmpty()) out.add("IDs duplicados en lista de enfermeras: " + dups);
        java.util.Map<Integer, Integer> conteo = new java.util.HashMap<>();
        for (Turno t : turnos) {
            for (Enfermera e : t.getEnfermerasAsignadas()) {
                conteo.merge(e.getIdEnfermera(), 1, Integer::sum);
            }
        }
        java.util.List<Integer> multi = new java.util.ArrayList<>();
        for (var en : conteo.entrySet()) if (en.getValue() > 1) multi.add(en.getKey());
        if (!multi.isEmpty()) out.add("Enfermeras asignadas a múltiples turnos: " + multi);

        java.util.List<String> capZero = new java.util.ArrayList<>();
        for (Turno t : turnos) {
            if (t.getEnfermerasNecesarias() <= 0) {
                String area = (t.getArea() != null) ? t.getArea().getNombre() : "(sin área)";
                capZero.add(area + " - " + t.getTipoTurno());
            }
        }
        if (!capZero.isEmpty()) out.add("Turnos con capacidad <= 0: " + capZero);

        if (out.isEmpty()) out.add("Sin problemas detectados.");
        return out;
    }

}
