/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

/**
 *
 * @author Gfmt
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexion;

    public ConexionBD() {
        try {
            String url = "jdbc:mysql://localhost:3306/restaurantee"; // Cambia el nombre de la base de datos si es necesario
            String usuario = "root"; // Cambia el usuario si es necesario
            String contraseña = ""; // Cambia la contraseña si es necesario
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}