package ec.edu.espe.analisis.crudestudiantes.modelo;

/**
 * PATRÓN STATE: define el comportamiento que cambia según el estado
 * del estudiante.
 */
public interface EstadoEstudiante {
    String actualizar(Estudiante estudiante, String nombre, int edad);

    String nombre();
}
