package cinemagenta.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import cinemagenta.util.ConexionBD;
import cinemagenta.model.Pelicula;

public class PeliculaDAO {

    public boolean agregarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar película: " + e.getMessage());
            return false;
        }
    }

    public List<Pelicula> listarPeliculas() {
        List<Pelicula> listaPeliculas = new ArrayList<>();
        String sql = "SELECT * FROM Cartelera";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pelicula p = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getInt("duracion"),
                    rs.getString("genero")
                );
                listaPeliculas.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar películas: " + e.getMessage());
        }
        return listaPeliculas;
    }
    
    /**
     * NUEVO MÉTODO DE BÚSQUEDA POR CRITERIOS
     * Busca películas filtrando por género y un rango de años.
     * @param genero El género a buscar. Si es "Todos", no se filtra por género.
     * @param anioDesde El año inicial del rango.
     * @param anioHasta El año final del rango.
     * @return Una lista de películas que coinciden con los criterios.
     */
    public List<Pelicula> buscarPorCriterios(String genero, int anioDesde, int anioHasta) {
        List<Pelicula> listaPeliculas = new ArrayList<>();
        // Construcción dinámica de la consulta SQL
        StringBuilder sql = new StringBuilder("SELECT * FROM Cartelera WHERE anio BETWEEN ? AND ?");
        
        if (!"Todos".equalsIgnoreCase(genero)) {
            sql.append(" AND genero = ?");
        }

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // Asignación de parámetros
            ps.setInt(1, anioDesde);
            ps.setInt(2, anioHasta);
            if (!"Todos".equalsIgnoreCase(genero)) {
                ps.setString(3, genero);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pelicula p = new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("anio"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                    );
                    listaPeliculas.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar películas por criterios: " + e.getMessage());
        }
        return listaPeliculas;
    }

    public boolean actualizarPelicula(Pelicula pelicula) {
        String sql = "UPDATE Cartelera SET titulo = ?, director = ?, anio = ?, duracion = ?, genero = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            ps.setInt(6, pelicula.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPelicula(int id) {
        String sql = "DELETE FROM Cartelera WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar película: " + e.getMessage());
            return false;
        }
    }
}