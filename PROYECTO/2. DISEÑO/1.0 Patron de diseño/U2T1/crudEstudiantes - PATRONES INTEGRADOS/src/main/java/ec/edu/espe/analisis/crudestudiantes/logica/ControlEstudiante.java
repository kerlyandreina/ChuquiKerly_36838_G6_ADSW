package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.EstadoActivo;
import ec.edu.espe.analisis.crudestudiantes.modelo.EstadoInactivo;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.Objects;

/**
 * Servicio de aplicación con las reglas y casos de uso del CRUD.
 * PRINCIPIO SRP: su responsabilidad es aplicar las reglas de estudiantes;
 * la mediación y la persistencia están en otras clases.
 */
public class ControlEstudiante {
    private final RepositorioEstudiante repositorio;

    /**
     * PRINCIPIO DIP: recibe el contrato de repositorio, no una implementación concreta.
     */
    public ControlEstudiante(RepositorioEstudiante repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio no puede ser nulo.");
    }

    public String agregarEstudiante(int id, String nombre, int edad) {
        if (!validarDatos(id, nombre, edad)) {
            return "Error: ID y edad deben ser mayores que 0; el nombre no puede estar vacío.";
        }
        if (repositorio.existeId(id)) {
            return "Error: Ya existe un estudiante con el ID " + id + ".";
        }

        Estudiante estudiante = new Estudiante(id, nombre.trim(), edad);
        repositorio.guardar(estudiante);
        return "Agregado exitosamente: " + estudiante.nombre;
    }

    public String actualizarEstudiante(int id, String nombre, int edad) {
        Estudiante estudiante = repositorio.buscarPorId(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (!validarDatos(id, nombre, edad)) {
            return "Error: Datos inválidos para la actualización.";
        }

        // PATRÓN STATE: el estudiante delega la actualización en su estado actual.
        return estudiante.actualizar(nombre.trim(), edad);
    }

    public String cambiarEstado(int id, int opcion) {
        Estudiante estudiante = repositorio.buscarPorId(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }

        if (opcion == 1) {
            estudiante.cambiarEstado(new EstadoActivo());
        } else if (opcion == 2) {
            estudiante.cambiarEstado(new EstadoInactivo());
        } else {
            return "Error: Estado no válido.";
        }
        return "Estado actualizado: " + estudiante.nombreEstado() + ".";
    }

    public String eliminarEstudiante(int id) {
        Estudiante estudiante = repositorio.buscarPorId(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (estudiante.edad < 18) {
            return "Error: No se puede eliminar a " + estudiante.nombre
                    + " porque es menor de 18 años (Edad: " + estudiante.edad + ").";
        }

        repositorio.eliminar(id);
        return "Eliminado exitosamente: " + estudiante.nombre + " (ID " + id + ").";
    }

    public Iterador crearIterador() {
        return repositorio.crearIterador();
    }

    private boolean validarDatos(int id, String nombre, int edad) {
        return id > 0 && nombre != null && !nombre.isBlank() && edad > 0;
    }
}
