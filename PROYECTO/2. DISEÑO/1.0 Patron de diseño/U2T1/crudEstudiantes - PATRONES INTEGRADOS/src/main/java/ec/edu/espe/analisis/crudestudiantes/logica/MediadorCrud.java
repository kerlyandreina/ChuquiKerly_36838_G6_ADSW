package ec.edu.espe.analisis.crudestudiantes.logica;

/**
 * PATRÓN MEDIATOR: contrato de comunicación entre la presentación y el
 * servicio de CRUD. La presentación no conoce el repositorio.
 */
public interface MediadorCrud {
    String agregarEstudiante(int id, String nombre, int edad);

    String actualizarEstudiante(int id, String nombre, int edad);

    String eliminarEstudiante(int id);

    String cambiarEstado(int id, int opcion);

    Iterador crearIterador();
}
