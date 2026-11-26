package ec.edu.espe.analisis.crudestudiantes.presentacion;

import ec.edu.espe.analisis.crudestudiantes.logica.ControlEstudiante;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrud;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrudEstudiantes;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    private Main() {
    }

    public static void main(String[] argumentos) {
        // Cada ejecución crea un control nuevo con la colección en memoria vacía.
        // PATRÓN MEDIATOR: la vista envía sus solicitudes al CRUD por este contrato.
        MediadorCrud mediador = new MediadorCrudEstudiantes(new ControlEstudiante());

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception error) {
                // Si el sistema no ofrece un tema nativo, Swing usa el tema predeterminado.
            }
            new VentanaCrudEstudiantes(mediador).setVisible(true);
        });
    }
}
