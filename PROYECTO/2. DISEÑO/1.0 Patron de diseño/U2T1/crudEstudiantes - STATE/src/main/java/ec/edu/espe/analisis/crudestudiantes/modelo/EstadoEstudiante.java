package ec.edu.espe.analisis.crudestudiantes.modelo;

public interface EstadoEstudiante {
    String actualizar(Estudiante estudiante, String nombre, int edad);
    String nombre();
}
