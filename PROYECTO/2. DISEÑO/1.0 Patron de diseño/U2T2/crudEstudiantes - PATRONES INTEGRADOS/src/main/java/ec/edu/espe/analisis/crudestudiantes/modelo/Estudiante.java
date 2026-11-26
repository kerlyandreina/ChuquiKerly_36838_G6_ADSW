package ec.edu.espe.analisis.crudestudiantes.modelo;

import java.util.Objects;

/** Contexto del PATRÓN STATE: delega la actualización en el estado actual. */
public class Estudiante {
    private final int id;
    private String nombre;
    private int edad;
    private EstadoEstudiante estado;

    public Estudiante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.estado = new EstadoActivo();
    }

    /**
     * PATRÓN STATE: delega la regla de actualización al estado actual.
     */
    public String actualizar(String nombre, int edad) {
        return estado.actualizar(this, nombre, edad);
    }

    /** PATRÓN STATE: reemplaza el comportamiento que se usará al actualizar. */
    public void cambiarEstado(EstadoEstudiante estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo.");
    }

    public String nombreEstado() {
        return estado.nombre();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    /** Solo los estados del mismo paquete pueden modificar los datos del contexto. */
    void actualizarDatos(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Estudiante [ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad
                + ", Estado=" + nombreEstado() + "]";
    }
}
