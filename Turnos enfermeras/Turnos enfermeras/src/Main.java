import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        SistemaHospital hospital = new SistemaHospital();
        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("=== Sistema Gestion de turnos ===");
            System.out.println("1. Gestion de enfermeras");
            System.out.println("2. Gestion de Areas");
            System.out.println("3. Gestion de turnos");
            System.out.println("4. Planificacion de Turnos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");

            int opcion = Integer.parseInt(lector.readLine());
            switch (opcion) {
                case 1:
                    hospital.gestionarEnfermeras();
                    break;
                case 2:
                    hospital.gestionarAreas();
                    break;
                case 3:
                    System.out.println("Gestion de turnos");
                    break;
                case 4:
                    System.out.println("Planificacion de Turnos");
                    break;
                case 5:
                    System.out.println("Reportes");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema");
                    System.exit(0);
            }




        }



    }
} 
