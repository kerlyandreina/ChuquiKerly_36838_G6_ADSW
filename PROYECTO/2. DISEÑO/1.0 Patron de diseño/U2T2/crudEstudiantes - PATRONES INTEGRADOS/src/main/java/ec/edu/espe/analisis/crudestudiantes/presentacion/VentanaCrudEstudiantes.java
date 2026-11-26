package ec.edu.espe.analisis.crudestudiantes.presentacion;

import ec.edu.espe.analisis.crudestudiantes.logica.Iterador;
import ec.edu.espe.analisis.crudestudiantes.logica.MediadorCrud;
import ec.edu.espe.analisis.crudestudiantes.modelo.Estudiante;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/** Ventana Swing para operar el CRUD de estudiantes mediante el Mediator. */
public final class VentanaCrudEstudiantes extends JFrame {
    private static final Color AZUL = new Color(35, 77, 130);
    private static final Color FONDO = new Color(244, 247, 251);
    private static final Color TEXTO = new Color(37, 48, 63);
    private static final Color SECUNDARIO = new Color(101, 116, 137);
    private static final String[][] DATOS_DEMO = {
        {"1001", "Ana Torres", "20"},
        {"1002", "David Mena", "22"},
        {"1003", "Camila Ruiz", "19"}
    };

    private final MediadorCrud mediador;
    private final JTextField campoId = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoEdad = new JTextField();
    private final JComboBox<String> estadoDestino = new JComboBox<>(new String[]{"Activo", "Inactivo"});
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "NOMBRE", "EDAD", "ESTADO"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modeloTabla);
    private final JLabel mensaje = new JLabel("Registra un estudiante o carga los datos de ejemplo.");
    private final JLabel etiquetaVacia = new JLabel("Aún no hay estudiantes registrados.", SwingConstants.CENTER);
    private final JButton botonEjemplos = crearBoton("Cargar datos de ejemplo", new Color(229, 237, 248), AZUL);
    private boolean ejemplosCargados;

    public VentanaCrudEstudiantes(MediadorCrud mediador) {
        super("Gestión de estudiantes");
        this.mediador = mediador;
        configurarVentana();
        construirInterfaz();
        conectarEventos();
        refrescarTabla();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setSize(1120, 760);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout());
        add(crearCabecera(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setBackground(FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));
        contenido.add(crearPanelFormulario(), BorderLayout.NORTH);
        contenido.add(crearPanelListado(), BorderLayout.CENTER);
        add(contenido, BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(AZUL);
        cabecera.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 4));
        titulos.setOpaque(false);
        JLabel titulo = new JLabel("Gestión de estudiantes");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        JLabel subtitulo = new JLabel("Registro académico · operaciones CRUD");
        subtitulo.setForeground(new Color(219, 230, 245));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titulos.add(titulo);
        titulos.add(subtitulo);

        JLabel sesion = new JLabel("SESIÓN LOCAL", SwingConstants.CENTER);
        sesion.setOpaque(true);
        sesion.setBackground(new Color(51, 96, 151));
        sesion.setForeground(Color.WHITE);
        sesion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        sesion.setBorder(BorderFactory.createEmptyBorder(9, 13, 9, 13));
        cabecera.add(titulos, BorderLayout.CENTER);
        cabecera.add(sesion, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearPanelFormulario() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 14));

        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 3));
        titulos.setOpaque(false);
        JLabel titulo = new JLabel("Datos del estudiante");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);
        JLabel ayuda = new JLabel("Completa los campos para registrar o actualizar un registro.");
        ayuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ayuda.setForeground(SECUNDARIO);
        titulos.add(titulo);
        titulos.add(ayuda);

        JPanel campos = new JPanel(new GridLayout(1, 3, 14, 0));
        campos.setOpaque(false);
        campos.add(crearCampo("ID", campoId));
        campos.add(crearCampo("Nombre completo", campoNombre));
        campos.add(crearCampo("Edad", campoEdad));

        JPanel acciones = new JPanel(new GridLayout(2, 4, 8, 8));
        acciones.setOpaque(false);
        JButton registrar = crearBoton("Registrar", AZUL, Color.WHITE);
        JButton actualizar = crearBoton("Actualizar", new Color(38, 139, 104), Color.WHITE);
        JButton eliminar = crearBoton("Eliminar", new Color(183, 65, 65), Color.WHITE);
        JButton cambiarEstado = crearBoton("Cambiar estado", new Color(238, 241, 246), TEXTO);
        JButton limpiar = crearBoton("Limpiar", new Color(238, 241, 246), TEXTO);
        JPanel selectorEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        selectorEstado.setOpaque(false);
        JLabel etiquetaEstado = new JLabel("Nuevo estado:");
        etiquetaEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        etiquetaEstado.setForeground(SECUNDARIO);
        selectorEstado.add(etiquetaEstado);
        selectorEstado.add(estadoDestino);
        acciones.add(registrar);
        acciones.add(actualizar);
        acciones.add(eliminar);
        acciones.add(limpiar);
        acciones.add(selectorEstado);
        acciones.add(cambiarEstado);
        acciones.add(botonEjemplos);
        JPanel espacio = new JPanel();
        espacio.setOpaque(false);
        acciones.add(espacio);

        JPanel inferior = new JPanel(new BorderLayout(10, 0));
        inferior.setOpaque(false);
        inferior.add(acciones, BorderLayout.CENTER);

        registrar.addActionListener(evento -> registrarEstudiante());
        actualizar.addActionListener(evento -> actualizarEstudiante());
        eliminar.addActionListener(evento -> eliminarEstudiante());
        cambiarEstado.addActionListener(evento -> cambiarEstado());
        limpiar.addActionListener(evento -> limpiarFormulario());

        tarjeta.add(titulos, BorderLayout.NORTH);
        tarjeta.add(campos, BorderLayout.CENTER);
        tarjeta.add(inferior, BorderLayout.SOUTH);
        return tarjeta;
    }

    private JPanel crearPanelListado() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 12));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("Estudiantes registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);
        JLabel nota = new JLabel("Selecciona una fila para editar o cambiar el estado.");
        nota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nota.setForeground(SECUNDARIO);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(nota, BorderLayout.SOUTH);

        tabla.setRowHeight(34);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(231, 236, 243));
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabla.getTableHeader().setBackground(new Color(237, 242, 249));
        tabla.getTableHeader().setForeground(SECUNDARIO);
        tabla.getTableHeader().setPreferredSize(new Dimension(100, 36));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(95);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);

        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        etiquetaVacia.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        etiquetaVacia.setForeground(SECUNDARIO);
        etiquetaVacia.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(desplazamiento, BorderLayout.CENTER);
        tarjeta.add(etiquetaVacia, BorderLayout.SOUTH);
        return tarjeta;
    }

    private JPanel crearPie() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(Color.WHITE);
        pie.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(12, 28, 12, 28)));
        mensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mensaje.setForeground(SECUNDARIO);
        JLabel persistencia = new JLabel("Los registros solo se conservan durante esta ejecución.");
        persistencia.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        persistencia.setForeground(SECUNDARIO);
        pie.add(mensaje, BorderLayout.WEST);
        pie.add(persistencia, BorderLayout.EAST);
        return pie;
    }

    private JPanel crearCampo(String etiqueta, JTextField campo) {
        JPanel contenedor = new JPanel(new BorderLayout(0, 6));
        contenedor.setOpaque(false);
        JLabel texto = new JLabel(etiqueta);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        texto.setForeground(TEXTO);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setForeground(TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(214, 222, 233)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        contenedor.add(texto, BorderLayout.NORTH);
        contenedor.add(campo, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearTarjeta() {
        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 233, 240)),
                BorderFactory.createEmptyBorder(17, 19, 17, 19)));
        return tarjeta;
    }

    private static JButton crearBoton(String texto, Color fondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setForeground(colorTexto);
        boton.setBackground(fondo);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setMargin(new Insets(9, 13, 9, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(9, 13, 9, 13));
        return boton;
    }

    private void conectarEventos() {
        tabla.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarFormularioDesdeSeleccion();
            }
        });
        botonEjemplos.addActionListener(evento -> cargarDatosEjemplo());
    }

    private void registrarEstudiante() {
        Integer id = entero(campoId.getText(), "ID");
        Integer edad = entero(campoEdad.getText(), "Edad");
        if (id == null || edad == null) {
            return;
        }
        String resultado = mediador.agregarEstudiante(id, campoNombre.getText(), edad);
        mostrarResultado(resultado);
        if (resultado.startsWith("Agregado")) {
            refrescarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarEstudiante() {
        Integer id = entero(campoId.getText(), "ID");
        Integer edad = entero(campoEdad.getText(), "Edad");
        if (id == null || edad == null) {
            return;
        }
        String resultado = mediador.actualizarEstudiante(id, campoNombre.getText(), edad);
        mostrarResultado(resultado);
        if (resultado.startsWith("Actualizado")) {
            refrescarTabla();
        }
    }

    private void eliminarEstudiante() {
        Integer id = entero(campoId.getText(), "ID");
        if (id == null) {
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas eliminar al estudiante con ID " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        String resultado = mediador.eliminarEstudiante(id);
        mostrarResultado(resultado);
        if (resultado.startsWith("Eliminado")) {
            refrescarTabla();
            limpiarFormulario();
        }
    }

    private void cambiarEstado() {
        Integer id = entero(campoId.getText(), "ID");
        if (id == null) {
            return;
        }
        int opcion = "Activo".equals(estadoDestino.getSelectedItem()) ? 1 : 2;
        String resultado = mediador.cambiarEstado(id, opcion);
        mostrarResultado(resultado);
        if (resultado.startsWith("Estado actualizado")) {
            refrescarTabla();
        }
    }

    private void cargarDatosEjemplo() {
        int agregados = 0;
        for (String[] estudiante : DATOS_DEMO) {
            String resultado = mediador.agregarEstudiante(
                    Integer.parseInt(estudiante[0]), estudiante[1], Integer.parseInt(estudiante[2]));
            if (resultado.startsWith("Agregado")) {
                agregados++;
            }
        }
        ejemplosCargados = true;
        botonEjemplos.setEnabled(false);
        refrescarTabla();
        mostrarResultado("Datos de ejemplo cargados: " + agregados + " estudiante(s).");
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        // PATRÓN ITERATOR: la vista llena las filas sin acceder al LinkedHashMap del control.
        Iterador iterador = mediador.crearIterador();
        while (iterador.haySiguiente()) {
            Estudiante estudiante = iterador.siguiente();
            modeloTabla.addRow(new Object[]{estudiante.getId(), estudiante.getNombre(),
                estudiante.getEdad(), estudiante.nombreEstado()});
        }
        etiquetaVacia.setVisible(modeloTabla.getRowCount() == 0);
        if (!ejemplosCargados) {
            botonEjemplos.setEnabled(true);
        }
    }

    private void cargarFormularioDesdeSeleccion() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada < 0) {
            return;
        }
        int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
        campoId.setText(modeloTabla.getValueAt(filaModelo, 0).toString());
        campoNombre.setText(modeloTabla.getValueAt(filaModelo, 1).toString());
        campoEdad.setText(modeloTabla.getValueAt(filaModelo, 2).toString());
        estadoDestino.setSelectedItem(modeloTabla.getValueAt(filaModelo, 3).toString());
    }

    private Integer entero(String valor, String campo) {
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException error) {
            mostrarResultado("Error: " + campo + " debe ser un número entero.");
            return null;
        }
    }

    private void limpiarFormulario() {
        campoId.setText("");
        campoNombre.setText("");
        campoEdad.setText("");
        estadoDestino.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void mostrarResultado(String resultado) {
        mensaje.setText(resultado);
        mensaje.setForeground(resultado.startsWith("Error") ? new Color(174, 55, 55) : SECUNDARIO);
    }
}
