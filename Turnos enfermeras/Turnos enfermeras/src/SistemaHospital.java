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
        for(Enfermera e: enfermeras) {
            if(e.getIdEnfermera() == idEnfermera) {
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
        }
        else {
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
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Introduzca el ID de la Enferma a agregar");
        int id = Integer.parseInt(lector.readLine());
        System.out.println("Introduzca el nombre del Enferma a agregar");
        String nombre = lector.readLine();
        Enfermera e = new Enfermera(id, nombre);
        enfermeras.add(e);
        System.out.println("Enferma agregada con exito: "+ id + " " + nombre);
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

    public void gestionarEnfermeras() throws IOException{
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--Gestion de enfermeras--");
            System.out.println("1. Agregar enfermera");
            System.out.println("2. Eliminar enfermera");
            System.out.println("3  Listar enfermeras");
            System.out.println("0  Volver al menu principal");

            int opcion = Integer.parseInt(lector.readLine());

            switch(opcion){
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
}
