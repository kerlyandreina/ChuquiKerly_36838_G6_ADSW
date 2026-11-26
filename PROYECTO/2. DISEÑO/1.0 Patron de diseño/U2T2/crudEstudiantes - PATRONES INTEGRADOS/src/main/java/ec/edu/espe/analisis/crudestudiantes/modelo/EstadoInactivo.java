package ec.edu.espe.analisis.crudestudiantes.modelo;

/** Estado concreto del PATRÓN STATE que rechaza modificaciones del estudiante. */
public class EstadoInactivo implements EstadoEstudiante {
    @Override
    public String actualizar(Estudiante estudiante, String nombre, int edad) {
        return "Error: No se puede actualizar al estudiante porque está inactivo.";
    }

    @Override
    public String nombre() {
        return "Inactivo";
    }
}
