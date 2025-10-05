package cinemagenta.controller;

import cinemagenta.model.Pelicula;
import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.view.VentanaPrincipal;
import java.util.List;
import javax.swing.JOptionPane;

public class CineController {

    private final VentanaPrincipal view;
    private final PeliculaDAO dao;

    public CineController(VentanaPrincipal view, PeliculaDAO dao) {
        this.view = view;
        this.dao = dao;
        
        // Asignamos los listeners desde el controlador a los botones de la vista
        this.view.btnAgregar.addActionListener(e -> agregarPelicula());
        this.view.btnModificar.addActionListener(e -> actualizarPelicula());
        this.view.btnEliminar.addActionListener(e -> eliminarPelicula());
        this.view.btnListar.addActionListener(e -> listarPeliculas());
        this.view.btnLimpiar.addActionListener(e -> view.limpiarCampos());
        // El botón de buscar ahora llama al nuevo método
        this.view.btnBuscar.addActionListener(e -> buscarPeliculaPorCriterios());
    }

    public void iniciar() {
        view.setVisible(true);
        listarPeliculas();
    }

    private void listarPeliculas() {
        List<Pelicula> peliculas = dao.listarPeliculas();
        view.actualizarTabla(peliculas);
    }

    private void agregarPelicula() {
        try {
            String titulo = view.getTxtTitulo().getText();
            String director = view.getTxtDirector().getText();
            String anioStr = view.getTxtAnio().getText();
            String duracionStr = view.getTxtDuracion().getText();
            String genero = view.getTxtGenero().getText();

            if (titulo.isEmpty() || director.isEmpty() || genero.isEmpty() || anioStr.isEmpty() || duracionStr.isEmpty()) {
                view.mostrarMensaje("Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int anio = Integer.parseInt(anioStr);
            int duracion = Integer.parseInt(duracionStr);

            Pelicula nuevaPelicula = new Pelicula(titulo, director, anio, duracion, genero);
            
            if (dao.agregarPelicula(nuevaPelicula)) {
                view.mostrarMensaje("Película agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                view.limpiarCampos();
                listarPeliculas();
            } else {
                view.mostrarMensaje("No se pudo agregar la película.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            view.mostrarMensaje("El año y la duración deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPelicula() {
        try {
            if (view.getTxtId().getText().isEmpty()) {
                view.mostrarMensaje("Por favor, seleccione una película de la tabla para modificar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(view.getTxtId().getText());
            String titulo = view.getTxtTitulo().getText();
            String director = view.getTxtDirector().getText();
            int anio = Integer.parseInt(view.getTxtAnio().getText());
            int duracion = Integer.parseInt(view.getTxtDuracion().getText());
            String genero = view.getTxtGenero().getText();

            if (titulo.isEmpty() || director.isEmpty() || genero.isEmpty()) {
                view.mostrarMensaje("Todos los campos deben estar llenos para modificar.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pelicula peliculaModificada = new Pelicula(id, titulo, director, anio, duracion, genero);

            if (dao.actualizarPelicula(peliculaModificada)) {
                view.mostrarMensaje("Película actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                view.limpiarCampos();
                listarPeliculas();
            } else {
                view.mostrarMensaje("No se pudo actualizar la película.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            view.mostrarMensaje("El ID, año y duración deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPelicula() {
        if (view.getTxtId().getText().isEmpty()) {
            view.mostrarMensaje("Por favor, seleccione una película de la tabla para eliminar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(view, "¿Está seguro de que desea eliminar esta película?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(view.getTxtId().getText());

                if (dao.eliminarPelicula(id)) {
                    view.mostrarMensaje("Película eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    view.limpiarCampos();
                    listarPeliculas();
                } else {
                    view.mostrarMensaje("No se pudo eliminar la película. Verifique que exista.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                view.mostrarMensaje("El ID de la película no es válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * MÉTODO DE BÚSQUEDA ACTUALIZADO
     * Filtra las películas según el género y rango de años seleccionados por el usuario.
     */
    private void buscarPeliculaPorCriterios() {
        try {
            // 1. Obtenemos los criterios de la vista
            String genero = (String) view.getComboGeneroBusqueda().getSelectedItem();
            int anioDesde = Integer.parseInt(view.getTxtAnioDesde().getText());
            int anioHasta = Integer.parseInt(view.getTxtAnioHasta().getText());

            // 2. Llamamos al nuevo método del DAO
            List<Pelicula> peliculasEncontradas = dao.buscarPorCriterios(genero, anioDesde, anioHasta);

            // 3. Actualizamos la tabla con los resultados
            view.actualizarTabla(peliculasEncontradas);

            // 4. Mostrar un mensaje si no se encontraron resultados
            if (peliculasEncontradas.isEmpty()) {
                view.mostrarMensaje("No se encontraron películas que coincidan con los criterios.", "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            view.mostrarMensaje("Los años para la búsqueda deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}