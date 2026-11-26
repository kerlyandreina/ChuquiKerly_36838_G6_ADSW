package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;

/**
 * PATRÓN ITERATOR: abstracción de recorrido disponible para la lógica
 * sin depender de la colección de la capa de datos.
 */
public interface Iterador {
    boolean haySiguiente();

    Estudiante siguiente();
}
