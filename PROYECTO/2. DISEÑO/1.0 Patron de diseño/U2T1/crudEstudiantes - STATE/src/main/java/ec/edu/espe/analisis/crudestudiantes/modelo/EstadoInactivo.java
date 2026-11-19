package ec.edu.espe.analisis.crudestudiantes.modelo;

public class EstadoInactivo implements EstadoEstudiante {
    public String actualizar(Estudiante estudiante, String nombre, int edad) {
        return "Error: No se puede actualizar al estudiante porque esta inactivo.";
    }

    public String nombre() {
        return "Inactivo";
    }
}
