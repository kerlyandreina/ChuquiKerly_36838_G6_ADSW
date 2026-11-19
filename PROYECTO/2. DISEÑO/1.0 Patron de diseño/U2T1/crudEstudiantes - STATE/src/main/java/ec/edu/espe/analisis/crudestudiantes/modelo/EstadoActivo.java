package ec.edu.espe.analisis.crudestudiantes.modelo;

public class EstadoActivo implements EstadoEstudiante {
    public String actualizar(Estudiante estudiante, String nombre, int edad) {
        estudiante.nombre = nombre;
        estudiante.edad = edad;
        return "Actualizado exitosamente: ID " + estudiante.id + ".";
    }

    public String nombre() {
        return "Activo";
    }
}
