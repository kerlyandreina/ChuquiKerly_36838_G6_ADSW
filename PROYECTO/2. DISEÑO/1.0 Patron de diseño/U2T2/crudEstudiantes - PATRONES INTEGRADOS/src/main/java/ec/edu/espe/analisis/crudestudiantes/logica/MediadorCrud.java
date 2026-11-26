package ec.edu.espe.analisis.crudestudiantes.logica;

/**
 * PATRÓN MEDIATOR: contrato por el que la presentación solicita operaciones
 * del CRUD sin invocar directamente el controlador.
 */
public interface MediadorCrud {
    String agregarEstudiante(int id, String nombre, int edad);

    String actualizarEstudiante(int id, String nombre, int edad);

    String eliminarEstudiante(int id);

    String cambiarEstado(int id, int opcion);

    Iterador crearIterador();
}
