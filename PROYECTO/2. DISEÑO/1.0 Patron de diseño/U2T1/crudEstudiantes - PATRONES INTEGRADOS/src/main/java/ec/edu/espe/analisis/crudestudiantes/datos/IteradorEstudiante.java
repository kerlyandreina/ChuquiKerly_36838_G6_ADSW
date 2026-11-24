package ec.edu.espe.analisis.crudestudiantes.datos;

import ec.edu.espe.analisis.crudestudiantes.logica.Iterador;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * PATRÓN ITERATOR: mantiene la posición y entrega los estudiantes uno a uno.
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
