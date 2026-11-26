package ec.edu.espe.analisis.crudestudiantes.logica;

import java.util.Objects;

/**
 * PATRÓN MEDIATOR (Concrete Mediator): centraliza las solicitudes de la vista
 * y las delega al controlador, donde se ejecutan las reglas del CRUD.
 */
public final class MediadorCrudEstudiantes implements MediadorCrud {
    private final ControlEstudiante control;

    public MediadorCrudEstudiantes(ControlEstudiante control) {
        this.control = Objects.requireNonNull(control, "El control no puede ser nulo.");
    }

    @Override
    public String agregarEstudiante(int id, String nombre, int edad) {
        return control.agregarEstudiante(id, nombre, edad);
    }

    @Override
    public String actualizarEstudiante(int id, String nombre, int edad) {
        return control.actualizarEstudiante(id, nombre, edad);
    }

    @Override
    public String eliminarEstudiante(int id) {
        return control.eliminarEstudiante(id);
    }

    @Override
    public String cambiarEstado(int id, int opcion) {
        return control.cambiarEstado(id, opcion);
    }

    @Override
    public Iterador crearIterador() {
        return control.crearIterador();
    }
}
