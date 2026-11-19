package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.datos.RepositorioEstudiante;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import ec.edu.espe.analisis.crudestudiantes.datos.Iterador;

public class ControlEstudiante {
    public final RepositorioEstudiante repositorio = new RepositorioEstudiante();

    public String agregarEstudiante(int id, String nombre, int edad) {
        if (!validarDatos(id, nombre, edad)) {
            return "Error: ID y edad deben ser mayores a 0; el nombre no puede estar vacio.";
        }
        if (repositorio.existeId(id)) {
            return "Error: Ya existe un estudiante con el ID " + id + ".";
        }

        Estudiante estudiante = new Estudiante(id, nombre.trim(), edad);
        repositorio.guardar(estudiante);
        return "Agregado exitosamente: " + estudiante.nombre;
    }

    public String actualizarEstudiante(int id, String nombre, int edad) {
        if (repositorio.buscarPorId(id) == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (!validarDatos(id, nombre, edad)) {
            return "Error: Datos invalidos para la actualizacion.";
        }

        repositorio.actualizar(new Estudiante(id, nombre.trim(), edad));
        return "Actualizado exitosamente: ID " + id + ".";
    }

    public String eliminarEstudiante(int id) {
        Estudiante estudiante = repositorio.buscarPorId(id);
        if (estudiante == null) {
            return "Error: No existe un estudiante con el ID " + id + ".";
        }
        if (estudiante.edad < 18) {
            return "Error: No se puede eliminar a " + estudiante.nombre
                    + " porque es menor de 18 anos (Edad: " + estudiante.edad + ").";
        }

        repositorio.eliminar(id);
        return "Eliminado exitosamente: " + estudiante.nombre + " (ID " + id + ").";
    }

    public Iterador crearIterador() {
        return repositorio.crearIterador();
    }

    public boolean validarDatos(int id, String nombre, int edad) {
        return id > 0 && nombre != null && !nombre.trim().isEmpty() && edad > 0;
    }
}