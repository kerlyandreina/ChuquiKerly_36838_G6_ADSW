package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.EstadoActivo;
import ec.edu.espe.analisis.crudestudiantes.modelo.EstadoInactivo;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reúne los casos de uso del CRUD de estudiantes y conserva los registros
 * en memoria. Los patrones del proyecto se aplican en la mediación,
 * el estado de cada estudiante y el recorrido de esta colección.
 */
public class ControlEstudiante {
    // LinkedHashMap conserva el orden de inserción; se usa como colección en memoria.
    private final Map<Integer, Estudiante> estudiantes = new LinkedHashMap<>();

    public String agregarEstudiante(int id, String nombre, int edad) {
        if (!validarDatos(id, nombre, edad)) {
            return "Error: ID y edad deben ser mayores que 0; el nombre no puede estar vacío.";
        }
        if (estudiantes.containsKey(id)) {
            return "Error: Ya existe un estudiante con el ID " + id + ".";
        }

        Estudiante estudiante = new Estudiante(id, nombre.trim(), edad);
        estudiantes.put(id, estudiante);
        return "Agregado exitosamente: " + estudiante.getNombre();
    }

    public String actualizarEstudiante(int id, String nombre, int edad) {
        Estudiante estudiante = estudiantes.get(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (!validarDatos(id, nombre, edad)) {
            return "Error: Datos inválidos para la actualización.";
        }

        // PATRÓN STATE: el estudiante delega la decisión de actualizar en su estado actual.
        return estudiante.actualizar(nombre.trim(), edad);
    }

    public String cambiarEstado(int id, int opcion) {
        Estudiante estudiante = estudiantes.get(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }

        // PATRÓN STATE: cada opción selecciona el estado concreto que regirá la actualización.
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
        Estudiante estudiante = estudiantes.get(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (estudiante.getEdad() < 18) {
            return "Error: No se puede eliminar a " + estudiante.getNombre()
                    + " porque es menor de 18 años (Edad: " + estudiante.getEdad() + ").";
        }

        estudiantes.remove(id);
        return "Eliminado exitosamente: " + estudiante.getNombre() + " (ID " + id + ").";
    }

    /**
     * PATRÓN ITERATOR: crea un recorrido independiente de la colección interna
     * para que la vista consulte estudiantes sin recibir el mapa.
     */
    public Iterador crearIterador() {
        return new IteradorEstudiante(estudiantes.values());
    }

    private boolean validarDatos(int id, String nombre, int edad) {
        return id > 0 && nombre != null && !nombre.isBlank() && edad > 0;
    }
}
