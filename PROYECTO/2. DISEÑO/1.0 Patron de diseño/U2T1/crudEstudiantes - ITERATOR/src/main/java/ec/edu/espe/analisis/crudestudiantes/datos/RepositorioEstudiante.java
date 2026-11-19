package ec.edu.espe.analisis.crudestudiantes.datos;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioEstudiante {
    public final Map<Integer, Estudiante> estudiantes = new LinkedHashMap<>();

    public boolean existeId(int id) {
        return estudiantes.containsKey(id);
    }

    public void guardar(Estudiante estudiante) {
        estudiantes.put(estudiante.id, estudiante);
    }

    public Estudiante buscarPorId(int id) {
        return estudiantes.get(id);
    }

    public void actualizar(Estudiante estudiante) {
        estudiantes.put(estudiante.id, estudiante);
    }

    public void eliminar(int id) {
        estudiantes.remove(id);
    }

    public Iterador crearIterador() {
        return new IteradorEstudiante(new ArrayList<Estudiante>(estudiantes.values()));
    }
}