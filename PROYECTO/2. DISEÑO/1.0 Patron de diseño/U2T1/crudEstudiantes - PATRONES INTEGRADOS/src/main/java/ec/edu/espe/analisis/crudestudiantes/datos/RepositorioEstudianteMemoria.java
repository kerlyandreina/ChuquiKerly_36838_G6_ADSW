package ec.edu.espe.analisis.crudestudiantes.datos;

import ec.edu.espe.analisis.crudestudiantes.logica.Iterador;
import ec.edu.espe.analisis.crudestudiantes.logica.RepositorioEstudiante;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementación en memoria del contrato de repositorio.
 * PRINCIPIO DIP: depende de abstracciones de lógica y las implementa;
 * ControlEstudiante no necesita conocer esta clase ni el mapa interno.
 */
public final class RepositorioEstudianteMemoria implements RepositorioEstudiante {
    private final Map<Integer, Estudiante> estudiantes = new LinkedHashMap<>();

    @Override
    public boolean existeId(int id) {
        return estudiantes.containsKey(id);
    }

    @Override
    public void guardar(Estudiante estudiante) {
        estudiantes.put(estudiante.id, estudiante);
    }

    @Override
    public Estudiante buscarPorId(int id) {
        return estudiantes.get(id);
    }

    @Override
    public void actualizar(Estudiante estudiante) {
        estudiantes.put(estudiante.id, estudiante);
    }

    @Override
    public void eliminar(int id) {
        estudiantes.remove(id);
    }

    /**
     * PATRÓN ITERATOR: entrega un recorrido independiente de la colección interna.
     */
    @Override
    public Iterador crearIterador() {
        return new IteradorEstudiante(estudiantes.values());
    }
}
