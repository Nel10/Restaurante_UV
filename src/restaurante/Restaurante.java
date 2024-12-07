package restaurante;

//import Controlador.Controladorrestaurante.LoginController;
import Controlador.Controladorrestaurante;
import Modelo.login;
import Vista.FrmLogin;

public class Restaurante {

    public static void main(String[] args) {
        //login login = null;
        //En la clase madre creamos un objeto de control
        //Controlador.Controladorrestaurante ObjC1 = new Controladorrestaurante(login priv);
        FrmLogin iniciar = new FrmLogin();
        //LoginController loginController = new LoginController(iniciar,loginView);
        iniciar.setVisible(true);
    }
    
}
