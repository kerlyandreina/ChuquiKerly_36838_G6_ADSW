package ec.edu.espe.analisis.crudestudiantes.configuracion;

import ec.edu.espe.analisis.crudestudiantes.datos.RepositorioEstudianteMemoria;
import ec.edu.espe.analisis.crudestudiantes.logica.ControlEstudiante;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrud;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrudEstudiantes;
import ec.edu.espe.analisis.crudestudiantes.logica.RepositorioEstudiante;

/**
 * Punto de composición: aquí se eligen las implementaciones concretas.
 * El resto de la aplicación trabaja con contratos.
 */
public final class FabricaCrudEstudiantes {
    private FabricaCrudEstudiantes() {
    }

    public static MediadorCrud crearMediador() {
        RepositorioEstudiante repositorio = new RepositorioEstudianteMemoria();
        ControlEstudiante control = new ControlEstudiante(repositorio);
        return new MediadorCrudEstudiantes(control);
    }
}
