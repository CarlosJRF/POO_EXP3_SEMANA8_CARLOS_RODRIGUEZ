package cinemagenta.main;

import javax.swing.SwingUtilities;
import cinemagenta.controller.CineController;
import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.view.VentanaPrincipal;

/**
 * Este es el punto de inicio
 * Se crean instancias de PeliculaDAO y de la VentanaPrincipal
 * Se crea el controller pasandole ambos y se inicia
 */

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            // 1. Crear las instancias de cada capa
            PeliculaDAO dao = new PeliculaDAO();
            VentanaPrincipal view = new VentanaPrincipal();
            
            // 2. Crear el controlador y pasarle la vista y el DAO
            CineController controller = new CineController(view, dao);
            
            // 3. Iniciar la aplicación a través del controlador
            controller.iniciar();
        });
    }
}