package ec.edu.espe.analisis.crudestudiantes.logica;

import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;

/**
 * PRINCIPIO DIP: abstracción de persistencia que pertenece a la lógica
 * de la aplicación. Las implementaciones de datos cumplen este contrato.
 */
public interface RepositorioEstudiante {
    boolean existeId(int id);

    void guardar(Estudiante estudiante);

    Estudiante buscarPorId(int id);

    void actualizar(Estudiante estudiante);

    void eliminar(int id);

    Iterador crearIterador();
}
