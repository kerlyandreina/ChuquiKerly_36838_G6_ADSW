package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.List;

public interface MediadorCrud {
    String agregarEstudiante(int id, String nombre, int edad);
    String actualizarEstudiante(int id, String nombre, int edad);
    String eliminarEstudiante(int id);
    List<Estudiante> listarTodos();
}
