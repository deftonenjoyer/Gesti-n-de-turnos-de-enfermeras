import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SistemaHospital {
    private List<Enfermera> enfermeras;
    private List<Turno> turnos;
    private List<Area> areasList;
    private List<Reporte> reportes;

    public SistemaHospital() {
        enfermeras = new ArrayList<>();
        turnos = new ArrayList<>();
        areasList = new ArrayList<>();
        reportes = new ArrayList<>();

    }

    public Enfermera getEnfermeraPorId(int idEnfermera) {
        for (Enfermera e : enfermeras) {
            if (e.getIdEnfermera() == idEnfermera) {
                return e;
            }
        }
        return null;
    }

    public void listarEnfermeras() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("=== Lista de Enfermeras ===");
        if (enfermeras.isEmpty()) {
            System.out.println("No hay enfermeras registradas.");
        } else {
            for (Enfermera e : enfermeras) {
                System.out.println("ID: " + e.getIdEnfermera() + " | Nombre: " + e.getNombreEnfermera());
            }
        }
        System.out.println("Ingrese 0 para volver al menú anterior:");
        int opcion = Integer.parseInt(lector.readLine());
        while (opcion != 0) {
            System.out.println("Opción inválida. Presione 0 para volver al menú anterior:");
            opcion = Integer.parseInt(lector.readLine());
        }
    }


    public void agregarEnfermera() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Introduzca el ID de la Enferma a agregar");
        int id = Integer.parseInt(lector.readLine());
        System.out.println("Introduzca el nombre del Enferma a agregar");
        String nombre = lector.readLine();
        Enfermera e = new Enfermera(id, nombre);
        enfermeras.add(e);
        System.out.println("Enferma agregada con exito: " + id + " " + nombre);
    }

    public void EliminarEnfermera() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Introduzca el ID de la Enferma a eliminar");
        int id = Integer.parseInt(lector.readLine());

        boolean eliminado = enfermeras.removeIf(e -> e.getIdEnfermera() == id);

        if (eliminado) {
            System.out.println("Enfermera eliminada con éxito: " + id);
        } else {
            System.out.println("No se encontró enfermera con ID: " + id);
        }
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
                    EliminarEnfermera();
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
        System.out.println("\n--- Listado de Áreas ---");
        if (areasList.isEmpty()) {
            System.out.println("No hay áreas registradas.");
        } else {
            for (Area area : areasList) {
                System.out.println("- " + area.getNombre());
            }
        }
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
}