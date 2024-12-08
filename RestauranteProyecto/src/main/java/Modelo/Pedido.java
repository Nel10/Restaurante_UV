/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author holberton
 */
public class Pedido {
    private int id_pedido;
    private int id_mesero;
    private int id_mesa;
    private Timestamp fecha_creacion;
    private String estado;
    private int tiempo_estimado;
    private List<Producto> productos;
    private Connection conexion;
    

    public Pedido(int id_mesero, int id_mesa, Timestamp fecha_creacion, String estado, int tiempo_estimado, Connection conexion) {
        this.id_mesero = id_mesero;
        this.id_mesa = id_mesa;
        this.fecha_creacion = new Timestamp(System.currentTimeMillis());  // Asigna la fecha actual
        this.estado = estado;
        this.tiempo_estimado = tiempo_estimado;
        this.productos = new ArrayList<>();
        this.conexion = conexion;  // Asigna la conexión recibida
    }

    // Constructor con ID de pedido (cuando ya existe un pedido en la base de datos)
    public Pedido(int id_pedido, int id_mesero, int id_mesa, Timestamp fecha_creacion, String estado, int tiempo_estimado, Connection conexion) {
        this.id_pedido = id_pedido;
        this.id_mesero = id_mesero;
        this.id_mesa = id_mesa;
        this.fecha_creacion = fecha_creacion;
        this.estado = estado;
        this.tiempo_estimado = tiempo_estimado;
        this.productos = new ArrayList<>();
        this.conexion = conexion;  // Asigna la conexión recibida
    }
    
    // Método para agregar un producto al pedido (en la base de datos y en la lista interna)
    public void agregarProducto(Producto producto, int cantidad, Connection conn) throws SQLException {
        // Añadir el producto a la lista interna
        for (int i = 0; i < cantidad; i++) {
            this.productos.add(producto);
        }

        // Agregar producto a la base de datos
        try {
            Pedido.agregarProductoAPedido(conn, this.id_pedido, producto.getId(), cantidad);
        } catch (SQLException e) {
            // Manejar la excepción de la base de datos
            throw new SQLException("Error al agregar producto al pedido: " + e.getMessage());
        }
    }

    // Método para obtener la lista de productos del pedido
    public List<Producto> getProductos() {
        return productos;
    }

    // Obtener el ID del pedido
    public int getId_pedido() {
        return id_pedido;
    }

    // Método para obtener el tiempo estimado del pedido
    public int getTiempo_estimado() {
        return tiempo_estimado;
    }

    // Obtener el estado del pedido
    public String getEstado() {
        return estado;
    }

    // Cambiar el estado del pedido
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método para insertar un registro en la base de datos
    private static int insertarRegistro(Connection conn, String query, Object... params) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Recuperamos el ID generado
            }
            return -1;
        }
    }

    // Crear un pedido en la base de datos y obtener el ID generado
    public static int crearPedido(Connection conn, int idMesero, int idMesa, int tiempoEstimado) throws SQLException {
        String query = "INSERT INTO Pedidos (id_mesero, id_mesa, estado, tiempo_estimado) VALUES (?, ?, 'EN PROCESO', ?)";
        return insertarRegistro(conn, query, idMesero, idMesa, tiempoEstimado);
    }

    // Insertar el producto en la tabla Productos_Pedidos (relación muchos a muchos)
    public static void agregarProductoAPedido(Connection conn, int idPedido, int idProducto, int cantidad) throws SQLException {
        String query = "INSERT INTO Productos_Pedidos (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        insertarRegistro(conn, query, idPedido, idProducto, cantidad);
    }

    // Método para obtener los productos de un pedido desde la base de datos
    public static List<Producto> obtenerProductosDePedido(Connection conn, int idPedido) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.id_producto, p.nombre, p.precio, pp.cantidad "
                     + "FROM Productos p "
                     + "JOIN Productos_Pedidos pp ON p.id_producto = pp.id_producto "
                     + "WHERE pp.id_pedido = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                Producto producto = new Producto(idProducto, nombre, precio, "", "");
                for (int i = 0; i < cantidad; i++) {
                    productos.add(producto);
                }
            }
        }
        return productos;
    }
}