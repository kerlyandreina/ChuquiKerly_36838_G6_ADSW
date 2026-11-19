package ec.edu.espe.analisis.crudestudiantes.datos;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;

public interface Iterador {
    boolean haySiguiente();
    Estudiante siguiente();
}
