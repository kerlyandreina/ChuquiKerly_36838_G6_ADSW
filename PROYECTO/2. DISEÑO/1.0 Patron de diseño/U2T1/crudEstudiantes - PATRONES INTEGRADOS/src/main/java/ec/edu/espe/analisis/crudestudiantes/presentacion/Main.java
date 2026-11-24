package ec.edu.espe.analisis.crudestudiantes.presentacion;

import ec.edu.espe.analisis.crudestudiantes.configuracion.FabricaCrudEstudiantes;
import ec.edu.espe.analisis.crudestudiantes.logica.Iterador;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrud;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.Scanner;

public class Main {
    private Main() {
    }

    public static void main(String[] argumentos) {
        // DIP: la vista usa el contrato; la fábrica conecta las implementaciones concretas.
        MediadorCrud mediador = FabricaCrudEstudiantes.crearMediador();

        try (Scanner entrada = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                Integer opcion = leerEntero(entrada, "Seleccione una opción: ");
                if (opcion == null) {
                    break;
                }

                switch (opcion) {
                    case 1:
                        continuar = registrar(entrada, mediador);
                        break;
                    case 2:
                        continuar = actualizar(entrada, mediador);
                        break;
                    case 3:
                        continuar = eliminar(entrada, mediador);
                        break;
                    case 4:
                        listar(mediador);
                        break;
                    case 5:
                        continuar = cambiarEstado(entrada, mediador);
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
        }

        System.out.println("Aplicación finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== CRUD DE ESTUDIANTES ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Actualizar estudiante");
        System.out.println("3. Eliminar estudiante");
        System.out.println("4. Listar estudiantes");
        System.out.println("5. Cambiar estado de estudiante");
        System.out.println("0. Salir");
    }

    private static boolean registrar(Scanner entrada, MediadorCrud mediador) {
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

        System.out.println(mediador.agregarEstudiante(id, nombre, edad));
        return true;
    }

    private static boolean actualizar(Scanner entrada, MediadorCrud mediador) {
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

        System.out.println(mediador.actualizarEstudiante(id, nombre, edad));
        return true;
    }

    private static boolean eliminar(Scanner entrada, MediadorCrud mediador) {
        Integer id = leerEntero(entrada, "ID del estudiante: ");
        if (id == null) {
            return false;
        }

        System.out.println(mediador.eliminarEstudiante(id));
        return true;
    }

    private static boolean cambiarEstado(Scanner entrada, MediadorCrud mediador) {
        Integer id = leerEntero(entrada, "ID del estudiante: ");
        if (id == null) {
            return false;
        }
        Integer estado = leerEntero(entrada, "Estado (1. Activo, 2. Inactivo): ");
        if (estado == null) {
            return false;
        }

        System.out.println(mediador.cambiarEstado(id, estado));
        return true;
    }

    private static void listar(MediadorCrud mediador) {
        // PATRÓN ITERATOR: la vista recorre estudiantes sin recibir una colección.
        Iterador iterador = mediador.crearIterador();
        System.out.println("--- LISTA DE ESTUDIANTES ---");
        if (!iterador.haySiguiente()) {
            System.out.println("(No hay estudiantes registrados)");
            return;
        }
        while (iterador.haySiguiente()) {
            Estudiante estudiante = iterador.siguiente();
            System.out.println(estudiante);
        }
    }

    private static Integer leerEntero(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!entrada.hasNextLine()) {
                return null;
            }
            try {
                return Integer.valueOf(entrada.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        return entrada.hasNextLine() ? entrada.nextLine() : null;
    }
}
