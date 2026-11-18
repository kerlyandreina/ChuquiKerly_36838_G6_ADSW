package ec.edu.espe.analisis.crudestudiantes.presentacion;

import ec.edu.espe.analisis.crudestudiantes.logica.ControlEstudiante;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.List;
import java.util.Scanner;

/** Punto de entrada de la aplicacion CRUD para terminal. */
public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ControlEstudiante control = new ControlEstudiante();

        try (Scanner entrada = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                Integer opcion = leerEntero(entrada, "Seleccione una opcion: ");
                if (opcion == null) {
                    break;
                }

                switch (opcion) {
                    case 1:
                        continuar = registrar(entrada, control);
                        break;
                    case 2:
                        continuar = actualizar(entrada, control);
                        break;
                    case 3:
                        continuar = eliminar(entrada, control);
                        break;
                    case 4:
                        listar(control);
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            }
        }

        System.out.println("Aplicacion finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== CRUD DE ESTUDIANTES ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Actualizar estudiante");
        System.out.println("3. Eliminar estudiante");
        System.out.println("4. Listar estudiantes");
        System.out.println("0. Salir");
    }

    private static boolean registrar(Scanner entrada, ControlEstudiante control) {
        Integer id = leerEntero(entrada, "ID: ");
        if (id == null) {
            return false;
        }
        String nombre = leerTexto(entrada, "Nombre: ");
        if (nombre == null) {
            return false;
        }
        Integer edad = leerEntero(entrada, "Edad: ");
        if (edad == null) {
            return false;
        }

        System.out.println(control.agregarEstudiante(id, nombre, edad));
        return true;
    }

    private static boolean actualizar(Scanner entrada, ControlEstudiante control) {
        Integer id = leerEntero(entrada, "ID del estudiante: ");
        if (id == null) {
            return false;
        }
        String nombre = leerTexto(entrada, "Nuevo nombre: ");
        if (nombre == null) {
            return false;
        }
        Integer edad = leerEntero(entrada, "Nueva edad: ");
        if (edad == null) {
            return false;
        }

        System.out.println(control.actualizarEstudiante(id, nombre, edad));
        return true;
    }

    private static boolean eliminar(Scanner entrada, ControlEstudiante control) {
        Integer id = leerEntero(entrada, "ID del estudiante: ");
        if (id == null) {
            return false;
        }

        System.out.println(control.eliminarEstudiante(id));
        return true;
    }

    private static void listar(ControlEstudiante control) {
        List<Estudiante> estudiantes = control.listarTodos();
        System.out.println("--- LISTA DE ESTUDIANTES ---");
        if (estudiantes.isEmpty()) {
            System.out.println("(No hay estudiantes registrados)");
            return;
        }
        estudiantes.forEach(System.out::println);
    }

    private static Integer leerEntero(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!entrada.hasNextLine()) {
                return null;
            }
            try {
                return Integer.valueOf(entrada.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        return entrada.hasNextLine() ? entrada.nextLine() : null;
    }
}