/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author holberton
 */
public class Mesa {
    private int id_mesa;
    private String nombre;
    private int capacidad;
    private String estado;
    private String tipo;

    public Mesa(int id_mesa, String nombre, int capacidad, String estado, String tipo) {
        this.id_mesa = id_mesa;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
        this.tipo = tipo;
    }
    public Mesa() {
        // Constructor vacío, necesario para instanciar objetos sin parámetros.
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public static List<Mesa> obtenerMesasDisponibles(Connection conn) throws SQLException {
        String query = "SELECT * FROM Mesas WHERE estado = 'disponible'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Mesa> mesas = new ArrayList<>();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId_mesa(rs.getInt("id_mesa"));
                mesa.setNombre(rs.getString("nombre"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setTipo(rs.getString("tipo"));
                mesa.setEstado(rs.getString("estado"));
                mesas.add(mesa);
            }
            return mesas;
        }
    }
}
