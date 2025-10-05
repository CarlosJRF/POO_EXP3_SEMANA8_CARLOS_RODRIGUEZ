package cinemagenta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final String USER = "root";
    private static final String PASS = "*C1j9r9f3#"; 
    
    private ConexionBD() {}

    /**
     * Obtiene una nueva conexión a la base de datos.
     * @return Un objeto Connection.
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Se registra el driver de MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se retorna una nueva conexión.
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            // Si el driver no se encuentra, se lanza una excepción.
            System.err.println("Error: No se encontró el driver JDBC.");
            throw new SQLException("Error de driver JDBC", e);
        }
    }
}