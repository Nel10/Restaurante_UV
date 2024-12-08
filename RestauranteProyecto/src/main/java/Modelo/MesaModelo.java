/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author holberton
 */
public class MesaModelo {
    private Connection conexion;

    public MesaModelo(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean agregarMesa(String nombre, int capacidad) {
        String query = "INSERT INTO mesas (nombre, capacidad) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setInt(2, capacidad);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar mesa: " + e.getMessage());
            return false;
        }
    }

    public List<Mesa> obtenerMesasDisponibles() throws SQLException {
        String query = "SELECT * FROM mesas WHERE estado = 'disponible'";
        List<Mesa> mesas = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                mesas.add(new Mesa(rs.getInt("id_mesa"), rs.getString("nombre"), rs.getInt("capacidad"),
                        rs.getString("estado"), rs.getString("tipo")));
            }
        }
        return mesas;
    }

    public boolean actualizarEstadoMesa(int id, String estado) {
        String query = "UPDATE mesas SET estado = ? WHERE id_mesa = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, estado);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de la mesa: " + e.getMessage());
            return false;
        }
    }
}