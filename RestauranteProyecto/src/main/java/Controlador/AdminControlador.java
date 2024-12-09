/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.ConexionBD;
import Modelo.Mesa;
import Modelo.MesaModelo;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.UsuarioModelo;
import Vista.AdminVista;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Gfmt
 */
public class AdminControlador {
    //Inicializar
    private AdminVista adminVista;
    private Pedido pedidoAdmin;
    private Producto producto;
    private Mesa mesa;
    private MesaModelo mesamodelo;
    private UsuarioModelo USerMod;
    
    private Connection conexion;

    public AdminControlador(AdminVista adminVista, Pedido pedidoAdmin, Producto producto, Mesa mesa, MesaModelo mesamodelo, UsuarioModelo USerMod, Connection conexion) {
        this.adminVista = adminVista;
        this.pedidoAdmin = pedidoAdmin;
        this.producto = producto;
        this.mesa = mesa;
        this.mesamodelo = mesamodelo;
        this.USerMod = USerMod;
        this.conexion = conexion;
        
        ConexionBD conexionBD = new ConexionBD();
        //adminVista.actualizarVista(pedidoAdmin)
        
        
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
        
        //adminVista.getCbxEntradas().addActionListener(e -> agregarProducto("Entradas"));
        //adminVista.getCbxFritos().addActionListener(e -> agregarProducto("Fritos"));
        //adminVista.getCbxBebidas().addActionListener(e -> agregarProducto("Bebidas"));
        //adminVista.getCbxSancocho().addActionListener(e -> agregarProducto("Sancocho"));
        //adminVista.getCbxEspeciales().addActionListener(e -> agregarProducto("Especiales"));
        // Botón para crear pedido
        //adminVista.getBtnCrearPedido().addActionListener(e -> crearPedido());
    }
    
}
