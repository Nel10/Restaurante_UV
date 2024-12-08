/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.UsuarioModelo;
import Vista.AdminVista;
import Vista.LoginVista;
import Vista.MeseroVista;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author holberton
 */
public class LoginControlador {
    private LoginVista lgvista;
    private UsuarioModelo usuarioModelo;
    
    // Variables para controlar la vista de mesero y administrador
    private MeseroVista meseroVista;
    private AdminVista adminVista;

    public LoginControlador(LoginVista lgvista) {
        this.lgvista = lgvista;
        this.usuarioModelo = new UsuarioModelo();
        
        inicializarEventos();
    }

    private void inicializarEventos() {
        lgvista.getBtnEntrar().addActionListener(this::validarCampos);  // Suponiendo que tienes un botón con el nombre btnLogin
    }
    
    public void validarCampos(ActionEvent e) {
        String usuario = lgvista.getTxtUsuario().getText();
        String contraseña = new String(lgvista.getTxtContraseña().getPassword());
        
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(lgvista, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.contains("@")) {
            JOptionPane.showMessageDialog(lgvista, "El campo 'Usuario' debe contener un correo electrónico válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contraseña.length() < 6) {
            JOptionPane.showMessageDialog(lgvista, "La contraseña debe tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        loginEntrar(usuario, contraseña);
    }

    public void loginEntrar(String usuario, String contrasena) {
        // Validamos las credenciales con el modelo
        if (usuarioModelo.validarUsuario(usuario, contrasena)) {
            // Verificamos si el usuario es administrador o mesero
            if (usuarioModelo.esAdministrador(usuario)) {
                // Si el usuario es administrador, solo mostramos la vista de Admin si no está ya abierta
                if (adminVista == null) {
                    adminVista = new AdminVista();
                    adminVista.setVisible(true);
                }
            } else {
                // Si el usuario es mesero, solo mostramos la vista de Mesero si no está ya abierta
                if (meseroVista == null) {
                    meseroVista = new MeseroVista();
                    meseroVista.setVisible(true);
                }
            }
            lgvista.setVisible(false);  // Cerrar la ventana de login al iniciar sesión
        } else {
            JOptionPane.showMessageDialog(lgvista, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}