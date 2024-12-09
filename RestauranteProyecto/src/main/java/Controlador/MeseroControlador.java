/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.ConexionBD;
import Modelo.Pedido;
import Modelo.Producto;
import Vista.MeseroVista;
import java.sql.*;

/**
 *
 * @author holberton
 */
public class MeseroControlador {
    private MeseroVista meseVista;
    private Pedido pedido;
    private Connection conexion;
    
    public MeseroControlador(MeseroVista meseVista, Connection conexion) {
        this.meseVista = meseVista;
        ConexionBD conexionBD = new ConexionBD(); 
        this.conexion = conexionBD.getConexion();
        this.pedido = new Pedido(1, 1, new Timestamp(System.currentTimeMillis()), "EN PROCESO", 5, conexion);
        meseVista.actualizarVista(pedido);
 
        inicializarEventos();
    }

    // Asegúrate de cerrar la conexión cuando ya no la necesites
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    private void inicializarEventos()  {
        
        meseVista.getCbxEntradas().addActionListener(e -> agregarProducto("Entradas"));
        meseVista.getCbxFritos().addActionListener(e -> agregarProducto("Fritos"));
        meseVista.getCbxBebidas().addActionListener(e -> agregarProducto("Bebidas"));
        meseVista.getCbxSancocho().addActionListener(e -> agregarProducto("Sancocho"));
        meseVista.getCbxEspeciales().addActionListener(e -> agregarProducto("Especiales"));
        // Botón para crear pedido
        meseVista.getBtnCrearPedido().addActionListener(e -> crearPedido());
    }

    public void agregarProducto(String tipoProducto) {
        // Aquí se obtiene el producto seleccionado
        String productoSeleccionado = meseVista.getProductoSeleccionado(tipoProducto);
        Producto producto = buscarProductoEnBaseDeDatos(productoSeleccionado);
        int cantidad = meseVista.getCantidadSeleccionada(tipoProducto);

        // Aquí agregamos el producto al pedido
        if (producto != null) {
            try {
                pedido.agregarProducto(producto, cantidad, conexion);  // Agregar el producto al pedido
                meseVista.actualizarVista(pedido);  // Actualizamos la vista con el nuevo pedido
            } catch (SQLException e) {
                e.printStackTrace();
                meseVista.mostrarMensaje("Error al agregar producto: " + e.getMessage());
            }
        } else {
            meseVista.mostrarMensaje("Producto no encontrado.");
        }
    }

    public void crearPedido() {
        try {
            // Verificar si la conexión es válida
            if (conexion == null) {
                meseVista.mostrarMensaje("Error: No se ha establecido la conexión a la base de datos.");
                return;
            }

            // Obtener mesa seleccionada
            int idMesa = meseVista.getIdMesaSeleccionada();

            if (idMesa == -1) {
                meseVista.mostrarMensaje("Por favor, selecciona una mesa.");
                return;
            }

            // Obtener cantidad total de productos seleccionados
            int cantidadTotal = obtenerCantidadTotal();
            if (cantidadTotal == 0) {
                meseVista.mostrarMensaje("Por favor, selecciona productos antes de crear el pedido.");
                return;
            }

            // Calcular tiempo estimado (basado en la cantidad total)
            int tiempoEstimado = calcularTiempoEstimado(cantidadTotal);

            // Crear el pedido en la base de datos (ID generado automáticamente por la base de datos)
            int idPedido = Pedido.crearPedido(conexion, 1, idMesa, tiempoEstimado);

            // Verificar si se creó correctamente el pedido
            if (idPedido != -1) {
                // Actualizar el objeto Pedido con el ID generado por la base de datos
                pedido = new Pedido(idPedido, 1, idMesa, new Timestamp(System.currentTimeMillis()), "EN PROCESO", tiempoEstimado, conexion);

                // Agregar los productos al pedido
                agregarProductosAlPedido(idPedido);

                // Actualizar la vista
                meseVista.mostrarMensaje("Pedido creado con éxito!");
                meseVista.actualizarVista(pedido);
            } else {
                meseVista.mostrarMensaje("Hubo un error al generar el ID del pedido.");
            }
        } catch (SQLException e) {
            meseVista.mostrarMensaje("Error al crear el pedido: " + e.getMessage());
            e.printStackTrace(); // Agregar esta línea si quieres más detalles del error
        }
    }

    private void agregarProductosAlPedido(int idPedido) {
        // Agregar productos seleccionados al pedido
        agregarProductoAlPedido(idPedido, "Entradas");
        agregarProductoAlPedido(idPedido, "Fritos");
        agregarProductoAlPedido(idPedido, "Bebidas");
        agregarProductoAlPedido(idPedido, "Sancocho");
        agregarProductoAlPedido(idPedido, "Especiales");
    }

    private void agregarProductoAlPedido(int idPedido, String tipoProducto) {
        String productoSeleccionado = meseVista.getProductoSeleccionado(tipoProducto);
        int cantidad = meseVista.getCantidadSeleccionada(tipoProducto);

        // Verificar que se haya seleccionado un producto y cantidad
        if (productoSeleccionado != null && cantidad > 0) {
            Producto producto = buscarProductoEnBaseDeDatos(productoSeleccionado);
            if (producto != null) {
                try {
                    // Agregar el producto al pedido en la base de datos
                    Pedido.agregarProductoAPedido(conexion, idPedido, producto.getId(), cantidad);
                } catch (SQLException e) {
                    e.printStackTrace();  // Aquí manejas la excepción
                    meseVista.mostrarMensaje("Error al agregar el producto al pedido: " + e.getMessage());
                }
            }
        }
    }

    private int obtenerCantidadTotal() {
        // Sumar la cantidad seleccionada para cada tipo de producto
        int cantidadTotal = 0;
        cantidadTotal += meseVista.getCantidadSeleccionada("Entradas");
        cantidadTotal += meseVista.getCantidadSeleccionada("Fritos");
        cantidadTotal += meseVista.getCantidadSeleccionada("Bebidas");
        cantidadTotal += meseVista.getCantidadSeleccionada("Sancocho");
        cantidadTotal += meseVista.getCantidadSeleccionada("Especiales");
        return cantidadTotal;
    }

    private int calcularTiempoEstimado(int cantidadTotal) {
        if (cantidadTotal == 1) {
            return 5; // 5 minutos para 1 producto
        } else if (cantidadTotal == 2) {
            return 15; // 15 minutos para 2 productos
        } else if (cantidadTotal >= 3) {
            return 20; // 20 minutos para 3 o más productos
        }
        return 0; // Si no hay productos seleccionados, el tiempo estimado es 0
    }

    private Producto buscarProductoEnBaseDeDatos(String nombreProducto) {
        // Aquí debes consultar la base de datos para obtener el producto (por ejemplo, de la tabla de productos)
        try {
            String query = "SELECT * FROM Productos WHERE nombre = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombreProducto);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getString("categoria")
                    );
                }
            }
        } catch (SQLException e) {
            meseVista.mostrarMensaje("Error al buscar producto: " + e.getMessage());
        }
        return null; // Si no se encuentra el producto
    }
}