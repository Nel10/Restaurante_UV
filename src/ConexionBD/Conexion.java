package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {

    String usuario = "root";
    String clave="";
    Connection con;

    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurante",
                    usuario, clave);
        } catch (java.sql.SQLException sqle) {            
            System.out.println("Error al conectar: " + sqle);
        } catch (ClassNotFoundException ex) {            
            System.out.println("Clase no encontrada");
        }
        return null;
    }
    
    
    
    //Consultas no usadas aqui
    /*
    public void ejecutar(String sql){
        try {             
            PreparedStatement stm = this.con.prepareStatement(sql);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }  
    
    public ResultSet ejecutarConsulta(String sql){
        try { 
            PreparedStatement stm = this.con.prepareStatement(sql);
            return stm.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;         
    }
    
    public void cerrarConexion(){
        try{            
            this.con.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    */
}