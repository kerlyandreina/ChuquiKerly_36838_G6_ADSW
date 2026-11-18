package ec.edu.espe.analisis.crudestudiantes.modelo;

import java.util.Objects;

/** Datos de un estudiante. */
public class Estudiante {
    private final int id;
    private final String nombre;
    private final int edad;

    public Estudiante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
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

    @Override
    public String toString() {
        return "Estudiante [ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad + "]";
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Estudiante)) {
            return false;
        }
        Estudiante otro = (Estudiante) objeto;
        return id == otro.id && edad == otro.edad && Objects.equals(nombre, otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, edad);
    }
}