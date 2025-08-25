import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("=== Sistema Gestion de turnos ===");
            System.out.println("1. Gestion de enfermeras");
            System.out.println("2. Gestion de Areas");
            System.out.println("3. Planificacion de Turnos");
            System.out.println("4. Gestion de turnos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");

            int opcion = Integer.parseInt(lector.readLine());
            switch (opcion) {
                case "1":
                    System.out.println("Gestion de enfermeras");
                case "2":
                    System.out.println("Gestion de areas");
                case "3":
                    System.out.println("Planificacion de turnos");
                case "4":
                    System.out.println("Gestion de turnos");
                case "5":
                    System.out.println("Reportes");
                case "0":
                    System.out.println("Saliendo del sistema");
                    System.exit(0);
            }




        }



    }
} 
