package ec.edu.espe.analisis.crudestudiantes.modelo;

/** Estado concreto del PATRÓN STATE que permite modificar nombre y edad. */
public class EstadoActivo implements EstadoEstudiante {
    @Override
    public String actualizar(Estudiante estudiante, String nombre, int edad) {
        estudiante.actualizarDatos(nombre, edad);
        return "Actualizado exitosamente: ID " + estudiante.getId() + ".";
    }

    @Override
    public String nombre() {
        return "Activo";
    }
}
