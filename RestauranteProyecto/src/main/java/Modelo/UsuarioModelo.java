/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author holberton
 */
public class UsuarioModelo {
    private Connection conexion;

    public UsuarioModelo() {
        try {
            String url = "jdbc:mysql://localhost:3306/restaurantee";
            String usuario = "root";
            String contraseña = "";
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
    
    //convertir la contraseña a MD5 metodo seguro
    public String convertirAMD5(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(contrasena.getBytes(StandardCharsets.UTF_8));  // Usa UTF-8 para un mejor soporte de caracteres
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Convierte el byte a su representación hexadecimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al convertir la contraseña a MD5: " + e.getMessage());
            return null;
        }
    }
    // Método que valida las credenciales del usuario
    public boolean validarUsuario(String usuario, String contraseña) {
        String contraseñaCifrada = convertirAMD5(contraseña);
        String query = "SELECT * FROM usuarios WHERE email = ? AND contraseña = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, usuario);
            statement.setString(2, contraseñaCifrada);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();  // Devuelve true si encuentra el usuario
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar si el usuario es administrador
    public boolean esAdministrador(String usuario) {
        String query = "SELECT rol FROM usuarios WHERE email = ?";

        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, usuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return "admin".equals(resultSet.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el rol del usuario: " + e.getMessage());
        }
        return false;
    }

    // Cerrar la conexión
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
