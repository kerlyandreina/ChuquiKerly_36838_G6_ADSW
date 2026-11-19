package ec.edu.espe.analisis.crudestudiantes.datos;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.util.List;

public class IteradorEstudiante implements Iterador {
    public List<Estudiante> estudiantes;
    public int posicion;

    public IteradorEstudiante(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        this.posicion = 0;
    }

    public boolean haySiguiente() {
        return posicion < estudiantes.size();
    }

    public Estudiante siguiente() {
        Estudiante estudiante = estudiantes.get(posicion);
        posicion = posicion + 1;
        return estudiante;
    }
}
