package ec.edu.espe.analisis.crudestudiantes.modelo;

import java.util.Objects;

public class Estudiante {
    public final int id;
    public String nombre;
    public int edad;
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

    public void cambiarEstado(EstadoEstudiante estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo.");
    }

    public String nombreEstado() {
        return estado.nombre();
    }

    @Override
    public String toString() {
        return "Estudiante [ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad
                + ", Estado=" + nombreEstado() + "]";
    }
}
