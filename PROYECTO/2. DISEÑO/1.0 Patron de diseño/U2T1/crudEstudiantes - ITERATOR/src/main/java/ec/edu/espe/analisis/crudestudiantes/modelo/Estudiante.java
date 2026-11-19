package ec.edu.espe.analisis.crudestudiantes.modelo;

public class Estudiante {
    public int id;
    public String nombre;
    public int edad;

    public Estudiante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String toString() {
        return "Estudiante [ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad + "]";
    }
}
