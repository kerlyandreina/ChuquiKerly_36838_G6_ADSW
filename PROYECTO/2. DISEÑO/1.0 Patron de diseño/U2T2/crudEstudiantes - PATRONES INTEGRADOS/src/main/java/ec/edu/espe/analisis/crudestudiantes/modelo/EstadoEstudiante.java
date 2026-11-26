package ec.edu.espe.analisis.crudestudiantes.modelo;

/**
 * PATRÓN STATE: contrato de los comportamientos de actualización que varían
 * según el estado del estudiante.
 */
public interface EstadoEstudiante {
    String actualizar(Estudiante estudiante, String nombre, int edad);

    String nombre();
}
