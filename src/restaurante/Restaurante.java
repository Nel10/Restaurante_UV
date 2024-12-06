package restaurante;

import Controlador.Controladorrestaurante.LoginController;
import Vista.FrmLogin;

public class Restaurante {

    public static void main(String[] args) {
        FrmLogin iniciar = new FrmLogin();
        //LoginController loginController = new LoginController(iniciar,loginView);
        iniciar.setVisible(true);
    }
    
}
