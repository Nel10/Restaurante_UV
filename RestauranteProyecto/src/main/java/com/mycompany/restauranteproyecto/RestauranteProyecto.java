/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restauranteproyecto;

import Controlador.LoginControlador;
import Vista.LoginVista;

/**
 *
 * @author holberton
 */
public class RestauranteProyecto {

    public static void main(String[] args) {     
        LoginVista lgvista = new LoginVista(null);
        LoginControlador lgcontrolador = new LoginControlador(lgvista);
        
        lgvista.setLgcontrolador(lgcontrolador);
        lgvista.setVisible(true);
    }
}

