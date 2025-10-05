package cinemagenta.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import cinemagenta.model.Pelicula;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends JFrame {

    // Componentes públicos para que el Controlador los pueda acceder
    public JTextField txtId, txtTitulo, txtDirector, txtAnio, txtDuracion, txtGenero;
    public JButton btnAgregar, btnModificar, btnEliminar, btnListar, btnLimpiar;
    public JTable tablaPeliculas;
    public DefaultTableModel modeloTabla;
    
    // --- NUEVOS COMPONENTES PARA BÚSQUEDA ---
    public JComboBox<String> comboGeneroBusqueda;
    public JTextField txtAnioDesde;
    public JTextField txtAnioHasta;
    public JButton btnBuscar;

    public VentanaPrincipal() {
        setTitle("Administrador de Películas - CineMagenta");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // --- PANEL DEL FORMULARIO (Norte/Arriba) --- 
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Película"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        panelFormulario.add(txtId, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        
        panelFormulario.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        txtTitulo = new JTextField(10);
        panelFormulario.add(txtTitulo, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        
        panelFormulario.add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        txtDirector = new JTextField(3);
        panelFormulario.add(txtDirector, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        
        panelFormulario.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        txtAnio = new JTextField(10);
        panelFormulario.add(txtAnio, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        
        panelFormulario.add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        txtDuracion = new JTextField(10);
        panelFormulario.add(txtDuracion, gbc);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        
        panelFormulario.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        txtGenero = new JTextField(10);
        panelFormulario.add(txtGenero, gbc);
        
        // --- PANEL DE BOTONES  ---
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar Todo"); // Cambiamos el texto para mayor claridad
        btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnLimpiar);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Contendrá la búsqueda y la tabla) ---
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));

        // --- PANEL DE BÚSQUEDA MODIFICADO ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Filtro de Búsqueda"));
        
        panelBusqueda.add(new JLabel("Género:"));
        String[] generos = {"Todos", "Comedia", "Drama", "Acción", "Ciencia Ficción", "Terror", "Suspenso", "Aventura"};
        comboGeneroBusqueda = new JComboBox<>(generos);
        panelBusqueda.add(comboGeneroBusqueda);
        
        panelBusqueda.add(new JLabel("  Desde el año:"));
        txtAnioDesde = new JTextField(5);
        txtAnioDesde.setText("1950"); // Valor por defecto
        panelBusqueda.add(txtAnioDesde);
        
        panelBusqueda.add(new JLabel("  Hasta el año:"));
        txtAnioHasta = new JTextField(5);
        txtAnioHasta.setText("2025"); // Valor por defecto
        panelBusqueda.add(txtAnioHasta);
        
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        
        panelCentral.add(panelBusqueda, BorderLayout.NORTH);

        // --- PANEL DE LA TABLA --- 
        String[] columnas = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPeliculas = new JTable(modeloTabla);
        panelCentral.add(new JScrollPane(tablaPeliculas), BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        tablaPeliculas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaPeliculas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                    txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                    txtDirector.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                    txtAnio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                    txtDuracion.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                    txtGenero.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                }
            }
        });
    }

    public void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
    }
    
    public void actualizarTabla(List<Pelicula> peliculas) {
        modeloTabla.setRowCount(0);
        for (Pelicula p : peliculas) {
            Object[] fila = {
                p.getId(), p.getTitulo(), p.getDirector(),
                p.getAnio(), p.getDuracion(), p.getGenero()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    // Getters para que el controlador acceda a los componentes
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtTitulo() { return txtTitulo; }
    public JTextField getTxtDirector() { return txtDirector; }
    public JTextField getTxtAnio() { return txtAnio; }
    public JTextField getTxtDuracion() { return txtDuracion; }
    public JTextField getTxtGenero() { return txtGenero; }
    public JComboBox<String> getComboGeneroBusqueda() { return comboGeneroBusqueda; }
    public JTextField getTxtAnioDesde() { return txtAnioDesde; }
    public JTextField getTxtAnioHasta() { return txtAnioHasta; }
}