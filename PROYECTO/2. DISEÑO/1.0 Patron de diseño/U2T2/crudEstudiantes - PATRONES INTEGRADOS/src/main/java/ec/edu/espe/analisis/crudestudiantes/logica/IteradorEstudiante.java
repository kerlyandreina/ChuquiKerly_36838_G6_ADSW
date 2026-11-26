package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterador concreto del PATRÓN ITERATOR: conserva la posición y entrega
 * estudiantes de una instantánea sin exponer la colección del controlador.
 */
public final class IteradorEstudiante implements Iterador {
    private final List<Estudiante> estudiantes;
    private int posicion;

    public IteradorEstudiante(Collection<Estudiante> estudiantes) {
        this.estudiantes = List.copyOf(estudiantes);
        this.posicion = 0;
    }

    @Override
    public boolean haySiguiente() {
        return posicion < estudiantes.size();
    }

    @Override
    public Estudiante siguiente() {
        if (!haySiguiente()) {
            throw new NoSuchElementException("No hay más estudiantes para recorrer.");
        }
        return estudiantes.get(posicion++);
    }
}
