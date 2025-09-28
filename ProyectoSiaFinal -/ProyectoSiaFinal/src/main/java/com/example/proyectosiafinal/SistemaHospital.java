package com.example.proyectosiafinal;

import java.io.*;
import java.util.*;

public class SistemaHospital {
    private List<Enfermera> enfermeras;
    private List<Turno> turnos;
    private List<Area> areasList;
    private List<Reporte> reportes;
    private Map<Integer, Enfermera> mapEnfermeras;
    private static final String ARCHIVO = "hospital.csv";

    public SistemaHospital() {
        enfermeras = new ArrayList<>();
        turnos = new ArrayList<>();
        areasList = new ArrayList<>();
        reportes = new ArrayList<>();
        mapEnfermeras = new HashMap<>();

    }
    public void guardarDatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hospital.csv"))) {
            // Enfermeras
            for (Enfermera e : enfermeras) {
                bw.write("ENFERMERA;" + e.getIdEnfermera() + ";" + e.getNombreEnfermera());
                bw.newLine();
            }

            // Áreas
            for (Area a : areasList) {
                bw.write("AREA;" + a.getNombre());
                bw.newLine();
            }

            // Turnos
            for (Turno t : turnos) {
                bw.write("TURNO;" + t.getArea().getNombre() + ";" + t.getTipoTurno() + ";" + t.getEnfermerasNecesarias());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        File archivo = new File("hospital.csv");
        if (!archivo.exists()) return; // si no existe, inicia vacío

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                switch (partes[0]) {
                    case "ENFERMERA":
                        int id = Integer.parseInt(partes[1]);
                        String nombre = partes[2];
                        Enfermera e = new Enfermera(id, nombre);
                        enfermeras.add(e);
                        mapEnfermeras.put(id, e);
                        break;
                    case "AREA":
                        Area a = new Area(partes[1]);
                        areasList.add(a);
                        break;
                    case "TURNO":
                        Area areaTurno = null;
                        for (Area ar : areasList) {
                            if (ar.getNombre().equals(partes[1])) {
                                areaTurno = ar;
                                break;
                            }
                        }
                        if (areaTurno != null) {
                            String tipoTurno = partes[2];
                            int capacidad = Integer.parseInt(partes[3]);
                            Turno t = new Turno(areaTurno, tipoTurno, capacidad);
                            turnos.add(t);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Enfermera getEnfermeraPorId(int idEnfermera) {
        return mapEnfermeras.get(idEnfermera);
    }

    public void listarEnfermeras() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("=== Lista de Enfermeras ===");
        if (mapEnfermeras.isEmpty()) {
            System.out.println("No hay enfermeras registradas.");
        } else {
            for (Map.Entry<Integer, Enfermera> entry : mapEnfermeras.entrySet()) {
                System.out.println("ID: " + entry.getKey() + " | Nombre: " + entry.getValue().getNombreEnfermera());
            }
        }
        System.out.println("Ingrese 0 para volver al menú anterior:");
        int opcion = Integer.parseInt(lector.readLine());
        while (opcion != 0) {
            System.out.println("Opción inválida. Presione 0 para volver al menú anterior:");
            opcion = Integer.parseInt(lector.readLine());
        }
    }

    public void agregarEnfermeraPorNombre(String nombre) {
        int nuevoId = 1;
        if (!mapEnfermeras.isEmpty()) {
            nuevoId = mapEnfermeras.keySet().stream().max(Integer::compareTo).get() + 1;
        }
        Enfermera nueva = new Enfermera(nuevoId, nombre);
        enfermeras.add(nueva);
        mapEnfermeras.put(nuevoId, nueva);
    }


    public void agregarEnfermera() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        int id;
        while (true) {
            try {
                System.out.println("Introduzca el ID de la Enfermera a agregar:");
                String input = lector.readLine();
                id = Integer.parseInt(input);

                if (mapEnfermeras.containsKey(id)) {
                    System.out.println("\n\nError, ID existente. Intente otro.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n\nError: no ingresó un número. Intente nuevamente.");
            }
        }
        System.out.println("Introduzca el nombre de la Enfermera a agregar:");
        String nombre = lector.readLine();
        Enfermera e = new Enfermera(id, nombre);
        enfermeras.add(e);
        mapEnfermeras.put(id, e);
        System.out.println("Enfermera agregada con éxito: " + id + " " + nombre);
    }

    public void EliminarEnfermera() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Introduzca el ID de la Enferma a eliminar");
        int id = Integer.parseInt(lector.readLine());

        Enfermera eliminada = mapEnfermeras.remove(id);
        if(eliminada != null){
            enfermeras.removeIf(e -> e.getIdEnfermera() == id);
            System.out.println("Enferma eliminada con exito: " + id);
        }else{
            System.out.println("Error, ID no existente.");
        }
    }

    public void EliminarEnfermera(String nombre) {
        boolean eliminado = false;
        List<Integer> idsAEliminar = new ArrayList<>();
        for (Map.Entry<Integer, Enfermera> entry : mapEnfermeras.entrySet()) {
            if (entry.getValue().getNombreEnfermera().equalsIgnoreCase(nombre)) {
                idsAEliminar.add(entry.getKey());
            }
        }
        for (Integer id : idsAEliminar) {
            Enfermera e = mapEnfermeras.remove(id);
            enfermeras.removeIf(enf -> enf.getIdEnfermera() == id);
            eliminado = true;
            System.out.println("Enfermera eliminada con éxito: " + e.getNombreEnfermera() + " (ID: " + e.getIdEnfermera() + ")");
        }

        if (!eliminado) {
            System.out.println("Error, no se encontró ninguna enfermera con el nombre: " + nombre);
        }
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



    public void gestionarEnfermeras() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--Gestion de enfermeras--");
            System.out.println("1. Agregar enfermera");
            System.out.println("2. Eliminar enfermera");
            System.out.println("3  Listar enfermeras");
            System.out.println("0  Volver al menu principal");

            int opcion = Integer.parseInt(lector.readLine());

            switch (opcion) {
                case 1:
                    agregarEnfermera();
                    break;

                case 2:
                    System.out.println("¿Desea eliminar por ID o por nombre? (I/N)");
                    String opcionEliminar = lector.readLine();

                    if (opcionEliminar.equalsIgnoreCase("I")) {
                        EliminarEnfermera();
                    } else if (opcionEliminar.equalsIgnoreCase("N")) {
                        System.out.println("Ingrese el nombre de la enfermera a eliminar:");
                        String nombre = lector.readLine();
                        EliminarEnfermera(nombre);
                    } else {
                        System.out.println("Opción inválida, vuelva a intentarlo.");
                    }
                    break;

                case 3:
                    listarEnfermeras();
                    break;

            }
            if (opcion == 0) {
                break;


            }
        }


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

    public void agregarArea() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese el nombre de la nueva área: ");
        String nombre = lector.readLine();
        Area nuevaArea = new Area(nombre);

        for (Area area : areasList) {
            if (area.getNombre().equalsIgnoreCase(nuevaArea.getNombre())) {
                System.out.println("Error: El área con el nombre '" + nuevaArea.getNombre() + "' ya existe.");
                return;
            }
        }
        areasList.add(nuevaArea);
        System.out.println("Área '" + nuevaArea.getNombre() + "' agregada con éxito.");
    }
    public boolean eliminarAreaPorNombre(String nombre) {
        if (nombre == null) return false;
        boolean eliminado = areasList.removeIf(a -> a.getNombre().equalsIgnoreCase(nombre.trim()));
        if (eliminado) guardarDatos();
        return eliminado;
    }



    public void eliminarArea() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese el nombre del área a eliminar: ");
        String nombre = lector.readLine();

        boolean eliminado = areasList.removeIf(area -> area.getNombre().equalsIgnoreCase(nombre));

        if (eliminado) {
            System.out.println("Área '" + nombre + "' eliminada con éxito.");
        } else {
            System.out.println("Error: No se encontró el área '" + nombre + "'.");
        }
    }

    public void listarAreas() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n--- Listado de Áreas ---");
        if (areasList.isEmpty()) {
            System.out.println("No hay áreas registradas.");
        } else {
            for (Area area : areasList) {
                System.out.println("- " + area.getNombre());
            }
        }
    }
    public java.util.List<Area> getAreasList() {
        return new java.util.ArrayList<>(areasList);
    }



    public void gestionarAreas() throws IOException {
        BufferedReader hh = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--Gestión de Áreas--");
            System.out.println("1. Agregar área");
            System.out.println("2. Eliminar área");
            System.out.println("3  Listar área");
            System.out.println("0  Volver al menú principal");

            int opcion = Integer.parseInt(hh.readLine());

            switch (opcion) {
                case 1:
                    agregarArea();
                    break;

                case 2:
                    eliminarArea();
                    break;

                case 3:
                    listarAreas();
                    break;
            }
            if (opcion == 0) {
                break;
            }
        }
    }

    public java.util.List<Turno> getTurnos() {
        return new java.util.ArrayList<>(turnos);
    }





    public void listarTurnos() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("--Listar Turnos--");
        if(turnos.isEmpty()){
            System.out.println("No hay turnos encontrados");
            return;
        }
        for(Turno t : turnos){
            System.out.println("Area " + t.getArea().getNombre());
            System.out.println("Horario " + t.getTipoTurno());
            System.out.println("Capacidad Enfermeras "+ t.getEnfermerasNecesarias());

            if(t.getEnfermerasAsignadas().isEmpty()){
                System.out.println("No hay enfermeras asignadas");

            }
            else{
                System.out.println("Enfermeras asignadas: ");
                for(Enfermera e : t.getEnfermerasAsignadas()) {
                    System.out.println("Id: " + e.getIdEnfermera() + " \nNombre: " + e.getNombreEnfermera());
                }
            }



        }


    }

    public void agregarTurno(Area area, String tipoTurno, int capacidadEnfermeras){
        Turno t = new Turno(area, tipoTurno, capacidadEnfermeras);
        turnos.add(t);
    }
    public Area getAreaPorNombre(String nombre) {
        if (nombre == null) return null;
        for (Area a : areasList) if (a.getNombre().equalsIgnoreCase(nombre.trim())) return a;
        return null;
    }


    public List<Enfermera> getEnfermeras() {
        return new ArrayList<>(enfermeras);
    }

    public Map<Integer, Enfermera> getMapEnfermeras() {
        return new HashMap<>(mapEnfermeras);
    }


    public void agregarTurno() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n");
        System.out.println("--Agregar Turno--");
        System.out.println("Ingrese Area de Turno");
        Area area = new Area(lector.readLine());
        System.out.println("Ingrese Horario de Turno"); //Mañana o Noche
        String tipoTurno = lector.readLine();
        System.out.println("Ingrese Capacidad Enfermeras"); //5
        int enfermerasNecesarias = Integer.parseInt(lector.readLine());
        Turno t = new Turno(area, tipoTurno, enfermerasNecesarias);
        turnos.add(t);
        System.out.println("Turno agregado con éxito...");
    }

    public void eliminarTurno() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n");
        System.out.println("--Eliminar Turno--");
        System.out.println("Ingrese Area de Turno");
        Area area = new Area(lector.readLine());
        System.out.println("Ingrese Horario de Turno");
        String tipoTurno = lector.readLine();
        boolean eliminado = turnos.removeIf(t -> t.getArea().equals(area) &&  t.getTipoTurno().equals(tipoTurno));

        if (eliminado) {
            System.out.println("Truno Eliminado con éxito");
        }else{
            System.out.println("Error al eliminar");
        }

    }

    public void gestionarTurnos() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--Gestion de Turnos--");
            System.out.println("1. Agregar turno");
            System.out.println("2. Eliminar turno");
            System.out.println("3  Listar turno");
            System.out.println("0  Volver al menu principal");

            int opcion = Integer.parseInt(lector.readLine());

            switch (opcion) {
                case 1:
                    agregarTurno();
                    break;

                case 2:
                    eliminarTurno();
                    break;

                case 3:
                    listarTurnos();
                    break;

            }
            if (opcion == 0) {
                break;


            }
        }


    }

}