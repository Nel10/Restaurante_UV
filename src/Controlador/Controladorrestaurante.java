/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.Config;
import Modelo.DetallePedido;
import Modelo.Eventos;
import Modelo.LoginDao;
import Modelo.Pedidos;
import Modelo.PedidosDao;
import Modelo.Platos;
import Modelo.PlatosDao;
import Modelo.Salas;
import Modelo.SalasDao;
import Modelo.Tables;
import Modelo.login;
import Vista.FrmLogin;
import Vista.Sistema;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Gfmt
 */
public class Controladorrestaurante implements ActionListener, KeyListener{//Se implementan los listener de los botones
    
    //Declaracionde componentes en lo grafico vista login
    login lg = new login();
    LoginDao login = new LoginDao();
    private Timer tiempo;
    int contador;
    int segundos = 30;
    
    
    // Declaración de componentes gráficos vista Sistema
    private JLabel labelLogo;
    private JTextField txtIdHistorialPedido, txtIdConfig, txtIdPedido, txtIdPlato, txtIdSala, txtTempIdSala, txtTempNumMesa, txtNombreSala, txtMesas, txtNombrePlato, txtPrecioPlato, txtBuscarPlato;
    private JTextArea txtMensaje;
    private JTable TablePedidos, TableUsuarios, tableMenu, tblTemPlatos, tableSala, tableFinalizar, TablePlatos ;
    private JLabel LabelVendedor, totalMenu, totalFinalizar, txtFechaHora, txtSalaFinalizar, txtNumMesaFinalizar;
    private JTabbedPane jTabbedPane1;
    private JPanel PanelSalas, PanelMesas;
    private JButton btnSala, btnConfig, btnFinalizar, btnPdfPedido;
    private JTextField txtNombre, txtCorreo, txtRucConfig, txtNombreConfig, txtTelefonoConfig, txtDireccionConfig;
    private JPasswordField txtPass; // Campo para contraseñas
    private JComboBox<String> cbxRol; // Combobox para roles
    private JTextField txtComentario; // Agregado para evitar error en "Agregar"
    
    
   
    
    //Objeto numero en cada Clase de Modelo y definir ciertas variables necesarias
    Salas sl = new Salas();
    SalasDao slDao = new SalasDao();
    Config conf = new Config();
    Eventos event = new Eventos();

    Platos pla = new Platos();
    PlatosDao plaDao = new PlatosDao();

    Pedidos ped = new Pedidos();
    PedidosDao pedDao = new PedidosDao();
    DetallePedido detPedido = new DetallePedido();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();

    LoginDao lgDao = new LoginDao();
    int item;
    double Totalpagar = 0.00;

    Date fechaActual = new Date();
    String fechaFormato = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
    
    

    
    
    
     
    
    //Objeto de las vistas 
    Vista.FrmLogin ObjV1;
    Vista.Sistema ObjV2;

    
    //Se creo el constructor
    public Controladorrestaurante(login priv) {
        // Inicialización de las variables gráficas no definidas
        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtPass = new JPasswordField();
        cbxRol = new JComboBox<>(new String[]{"Administrador", "Asistente", "Usuario"});
        txtRucConfig = new JTextField();
        txtNombreConfig = new JTextField();
        txtTelefonoConfig = new JTextField();
        txtDireccionConfig = new JTextField();
        txtComentario = new JTextField();
        JProgressBar barra = new JProgressBar();
        
        
        
        
        
        
        
        //Desde aqui
        if (labelLogo != null){
            ImageIcon img =null;
            //ImageIcon img = new ImageIcon(getClass().getResource("/Img/logo.png"));
            Image igmEscalada = img.getImage().getScaledInstance(labelLogo.getWidth(), labelLogo.getHeight(), Image.SCALE_SMOOTH);
            Icon icono = new ImageIcon(igmEscalada);
            labelLogo.setIcon(icono);
        }
        
        //this.setIconImage(img.getImage());
        //this.setLocationRelativeTo(null);
        txtIdHistorialPedido.setVisible(false);
        txtIdConfig.setVisible(false);
        
        if (priv.getRol().equals("Asistente")) {
            btnSala.setEnabled(false);
            btnConfig.setEnabled(false);
            LabelVendedor.setText(priv.getNombre());
        } else {
            LabelVendedor.setText(priv.getNombre());
        }
        
        // Ocultar elementos no usados inicialmente
        txtIdConfig.setVisible(false);
        txtIdHistorialPedido.setVisible(false);
        txtIdPedido.setVisible(false);
        txtIdPlato.setVisible(false);
        txtIdSala.setVisible(false);
        txtTempIdSala.setVisible(false);
        txtTempNumMesa.setVisible(false);
        jTabbedPane1.setEnabled(false);
        
        //Hasta Aqui
        
        panelSalas();
        ObjV1 = new Vista.FrmLogin();
        //ObjV2 = new Vista.Sistema();
    }
    
    
    
    
    
    
    //Funciones internas de la vista Login    
    
    public void validar(){
        String correo = txtCorreo.getText();
        String pass = String.valueOf(txtPass.getPassword());
        if (!"".equals(correo) || !"".equals(pass)) {
            
            lg = login.log(correo, pass);
            if (lg.getCorreo()!= null && lg.getPass() != null) {                                
                JOptionPane.showMessageDialog(null, "Bienvenido");
            }else{
                JOptionPane.showMessageDialog(null, "Correo o la Contraseña incorrecta");
            }
        }
    }
    
    /*
    public class BarraProgreso implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            contador++;
            barra.setValue(contador);
            if (contador == 100) {
                tiempo.stop();
                if (barra.getValue() == 100) {
                    Sistema sis = new Sistema(lg);
                    sis.setVisible(true);
                    dispose();
                }
            }
        }
    }
    */
    
    public class LoginController {
        private FrmLogin view;
        public LoginController(FrmLogin view) {
            this.view = view;
            initialize();
        }
        private void initialize() {// Configurar la vista
            view.setLocationRelativeTo(null);
            view.getTxtCorreo().setText("admin");
            view.getTxtPass().setText("admin");
            view.getBarra().setVisible(false);

            // Configurar el ícono
            ImageIcon img = new ImageIcon(getClass().getResource("/Img/logo.png"));
            view.setIconImage(img.getImage());
        }
    }

    
    
    
    //Funciones Internas del programa De la vista sistema
    
    //Para calcular el total a pagar de un cliente
    private void TotalPagar(JTable tabla, JLabel label) {
        Totalpagar = 0.00;
        int numFila = tabla.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tabla.getModel().getValueAt(i, 4)));
            Totalpagar += cal;
        }
        label.setText(String.format("%.2f", Totalpagar));
    }
    
    //Importante porque sirve para limpiar los datos mostrados- limpiar
    private void LimpiarTableMenu() {
        tmp = (DefaultTableModel) tableMenu.getModel();
        int fila = tableMenu.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    public void ListarConfig() {
        conf = lgDao.datosEmpresa();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtMensaje.setText("" + conf.getMensaje());
    }
    
    //Crea la lista de los pedidos
    private void ListarPedidos() {
        Tables color = new Tables();
        List<Pedidos> Listar = pedDao.listarPedidos();
        modelo = (DefaultTableModel) TablePedidos.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getSala();
            ob[2] = Listar.get(i).getUsuario();
            ob[3] = Listar.get(i).getNum_mesa();
            ob[4] = Listar.get(i).getFecha();
            ob[5] = Listar.get(i).getTotal();
            ob[6] = Listar.get(i).getEstado();
            modelo.addRow(ob);
        }
        colorHeader(TablePedidos);
        TablePedidos.setDefaultRenderer(Object.class, color);
    }
    
    //Limpiar table--
    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    //Para la lista de Usuarios
    private void ListarUsuarios() {
        List<login> Listar = lgDao.ListarUsuarios();
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCorreo();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        colorHeader(TableUsuarios);
    }
    
    //Para listar Salas
    private void ListarSalas() {
        List<Salas> Listar = slDao.Listar();
        modelo = (DefaultTableModel) tableSala.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getMesas();
            modelo.addRow(ob);
        }
        colorHeader(tableSala);
    }

    private void colorHeader(JTable tabla) {
        tabla.setModel(modelo);
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    //Para limpiar sala
    private void LimpiarSala() {
        txtIdSala.setText("");
        txtNombreSala.setText("");
        txtMesas.setText("");
    }

    //Para limpiar platos
    private void LimpiarPlatos() {
        txtIdPlato.setText("");
        txtNombrePlato.setText("");
        txtPrecioPlato.setText("");
    }
    
    private void panelSalas() {
        List<Salas> Listar = slDao.Listar();
        for (int i = 0; i < Listar.size(); i++) {
            int id = Listar.get(i).getId();
            int cantidad = Listar.get(i).getMesas();
            JButton boton = new JButton(Listar.get(i).getNombre(), new ImageIcon(getClass().getResource("/Img/salas.png")));
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boton.setHorizontalTextPosition(JButton.CENTER);
            boton.setVerticalTextPosition(JButton.BOTTOM);
            boton.setBackground(new Color(204, 204, 204));
            PanelSalas.add(boton);
            boton.addActionListener((ActionEvent e) -> {
                LimpiarTable();
                PanelMesas.removeAll();
                panelMesas(id, cantidad);
                jTabbedPane1.setSelectedIndex(2);
            });
        }
    }
    
    //crear mesas
    private void panelMesas(int id_sala, int cant) {
        for (int i = 1; i <= cant; i++) {
            int num_mesa = i;
            //verificar estado
            JButton boton = new JButton("MESA N°: " + i, new ImageIcon(getClass().getResource("/Img/mesa.png")));
            boton.setHorizontalTextPosition(JButton.CENTER);
            boton.setVerticalTextPosition(JButton.BOTTOM);
            int verificar = pedDao.verificarStado(num_mesa, id_sala);
            if (verificar > 0) {
                boton.setBackground(new Color(255, 51, 51));
            } else {
                boton.setBackground(new Color(0, 102, 102));
            }
            boton.setForeground(Color.WHITE);
            boton.setFocusable(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            PanelMesas.add(boton);
            boton.addActionListener((ActionEvent e) -> {
                if (verificar > 0) {
                    LimpiarTable();
                    verPedido(verificar);
                    verPedidoDetalle(verificar);
                    btnFinalizar.setEnabled(true);
                    btnPdfPedido.setEnabled(false);
                    jTabbedPane1.setSelectedIndex(4);
                } else {
                    LimpiarTable();
                    ListarPlatos(tblTemPlatos);
                    jTabbedPane1.setSelectedIndex(3);
                    txtTempIdSala.setText("" + id_sala);
                    txtTempNumMesa.setText("" + num_mesa);
                }
            });
        }
    }
    
    // platos
    private void ListarPlatos(JTable tabla) {
        List<Platos> Listar = plaDao.Listar(txtBuscarPlato.getText(), fechaFormato);
        modelo = (DefaultTableModel) tabla.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getPrecio();
            modelo.addRow(ob);
        }
        colorHeader(tabla);
    }

    //registrar pedido
    private void RegistrarPedido() {
        try{
            int id_sala = Integer.parseInt(txtTempIdSala.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido");
        }
        int num_mesa = Integer.parseInt(txtTempNumMesa.getText());
        double monto = Totalpagar;
        int id_sala = 0;//Agregado
        ped.setId_sala(id_sala);
        ped.setNum_mesa(num_mesa);
        ped.setTotal(monto);
        ped.setUsuario(LabelVendedor.getText());
        pedDao.RegistrarPedido(ped);
    }
    
    private void detallePedido() {
        int id = pedDao.IdPedido();
        for (int i = 0; i < tableMenu.getRowCount(); i++) {
            String nombre = tableMenu.getValueAt(i, 1).toString();
            int cant = Integer.parseInt(tableMenu.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(tableMenu.getValueAt(i, 3).toString());
            detPedido.setNombre(nombre);
            detPedido.setCantidad(cant);
            detPedido.setPrecio(precio);
            detPedido.setComentario(tableMenu.getValueAt(i, 5).toString());
            detPedido.setId_pedido(id);
            pedDao.RegistrarDetalle(detPedido);

        }
    }

    public void verPedidoDetalle(int id_pedido) {
        List<DetallePedido> Listar = pedDao.verPedidoDetalle(id_pedido);
        modelo = (DefaultTableModel) tableFinalizar.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCantidad();
            ob[3] = Listar.get(i).getPrecio();
            ob[4] = Listar.get(i).getCantidad() * Listar.get(i).getPrecio();
            ob[5] = Listar.get(i).getComentario();
            modelo.addRow(ob);
        }
        colorHeader(tableFinalizar);
    }
    
    //Ver el Pedido
    public void verPedido(int id_pedido) {
        ped = pedDao.verPedido(id_pedido);
        totalFinalizar.setText("" + ped.getTotal());
        txtFechaHora.setText("" + ped.getFecha());
        txtSalaFinalizar.setText("" + ped.getSala());
        txtNumMesaFinalizar.setText("" + ped.getNum_mesa());
        txtIdPedido.setText("" + ped.getId());
    }
    
    
    
    
    
    
    //Desde aqui eventos botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals ("Salas")){
            LimpiarTable();
            ListarSalas();
            jTabbedPane1.setSelectedIndex(1);
        }
        
        if (e.getActionCommand().equals("Config")){
            jTabbedPane1.setSelectedIndex(6);
            ListarConfig();
        }
        
        if (e.getActionCommand().equals("Pedidos")){
            LimpiarTable();
            ListarPedidos();
            jTabbedPane1.setSelectedIndex(5);
        }
        
        if (e.getActionCommand().equals("Usuarios")){
            LimpiarTable();
            ListarUsuarios();
            jTabbedPane1.setSelectedIndex(7);
        }
        JTable TablePlatos = null;//Agregado
        
        if (e.getActionCommand().equals("Platos")){
            jTabbedPane1.setSelectedIndex(8);
            LimpiarTable();
            ListarPlatos(TablePlatos);
        }
        
        if (e.getActionCommand().equals("RegistrarSala")){
            if (txtNombreSala.getText().equals("") || txtMesas.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
            } else {
                sl.setNombre(txtNombreSala.getText());
                sl.setMesas(Integer.parseInt(txtMesas.getText()));
                slDao.RegistrarSala(sl);
                JOptionPane.showMessageDialog(null, "Sala Registrado");
                LimpiarSala();
                LimpiarTable();
                ListarSalas();
            }
        }
        
        if (e.getActionCommand().equals("ActualizarSala")){
            if ("".equals(txtIdSala.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
            } else {
                if (!"".equals(txtNombreSala.getText())) {
                    sl.setNombre(txtNombreSala.getText());
                    sl.setId(Integer.parseInt(txtIdSala.getText()));
                    slDao.Modificar(sl);
                    JOptionPane.showMessageDialog(null, "Sala Modificado");
                    LimpiarSala();
                    LimpiarTable();
                    ListarSalas();
                }
            }
        }
        
        if (e.getActionCommand().equals("NuevoSala")){
            LimpiarSala();
        }
        
        if (e.getActionCommand().equals("EliminarSala")){
            if (!"".equals(txtIdSala.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
                if (pregunta == 0) {
                    int id = Integer.parseInt(txtIdSala.getText());
                    slDao.Eliminar(id);
                    LimpiarSala();
                    LimpiarTable();
                    ListarSalas();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila");
            }
        }
        
        if (e.getActionCommand().equals("Realizar Pedido")){
            if (tableMenu.getRowCount() > 0) {
                RegistrarPedido();
                detallePedido();
                LimpiarTableMenu();
                JOptionPane.showMessageDialog(null, "PEDIDO REGISTRADO");
                jTabbedPane1.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null, "NO HAY PRODUCTO EN LA PEDIDO");
            }
        }
        
        if (e.getActionCommand().equals("Agregar")){
            if (txtComentario.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                int id = Integer.parseInt(tableMenu.getValueAt(tableMenu.getSelectedRow(), 0).toString());
                for (int i = 0; i < tableMenu.getRowCount(); i++) {
                    if (tableMenu.getValueAt(i, 0).equals(id)) {
                        tmp.setValueAt(txtComentario.getText(), i, 5);
                        txtComentario.setText("");
                        tableMenu.clearSelection();
                        return;
                    }
                }
            }
        }
        
        if (e.getActionCommand().equals("EliminarTempPlato")){
            modelo = (DefaultTableModel) tableMenu.getModel();
            modelo.removeRow(tableMenu.getSelectedRow());
            TotalPagar(tableMenu, totalMenu);
        }
        
        if (e.getActionCommand().equals("Finalizar")){
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de finalizar");
            if (pregunta == 0) {
                if (pedDao.actualizarEstado(Integer.parseInt(txtIdPedido.getText()))) {
                    pedDao.pdfPedido(Integer.parseInt(txtIdPedido.getText()));
                }
            }
        }
        
        if (e.getActionCommand().equals("PdfPedido")){
            if (txtIdHistorialPedido.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                pedDao.pdfPedido(Integer.parseInt(txtIdHistorialPedido.getText()));
                txtIdHistorialPedido.setText("");
            }
        }
        
        if (e.getActionCommand().equals("Add Plato")){
            if (tblTemPlatos.getSelectedRow() >= 0) {
                int id = Integer.parseInt(tblTemPlatos.getValueAt(tblTemPlatos.getSelectedRow(), 0).toString());
                String descripcion = tblTemPlatos.getValueAt(tblTemPlatos.getSelectedRow(), 1).toString();
                double precio = Double.parseDouble(tblTemPlatos.getValueAt(tblTemPlatos.getSelectedRow(), 2).toString());
                double total = 1 * precio;
                item = item + 1;
                tmp = (DefaultTableModel) tableMenu.getModel();
                for (int i = 0; i < tableMenu.getRowCount(); i++) {
                    if (tableMenu.getValueAt(i, 0).equals(id)) {
                        int cantActual = Integer.parseInt(tableMenu.getValueAt(i, 2).toString());
                        int nuevoCantidad = cantActual + 1;
                        double nuevoSub = precio * nuevoCantidad;
                        tmp.setValueAt(nuevoCantidad, i, 2);
                        tmp.setValueAt(nuevoSub, i, 4);
                        TotalPagar(tableMenu, totalMenu);
                        return;
                    }
                }
                ArrayList lista = new ArrayList();
                lista.add(item);
                lista.add(id);
                lista.add(descripcion);
                lista.add(1);
                lista.add(precio);
                lista.add(total);
                Object[] O = new Object[6];
                O[0] = lista.get(1);
                O[1] = lista.get(2);
                O[2] = lista.get(3);
                O[3] = lista.get(4);
                O[4] = lista.get(5);
                O[5] = "";
                tmp.addRow(O);
                tableMenu.setModel(tmp);
                TotalPagar(tableMenu, totalMenu);
            } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
            }
        }
        
        if (e.getActionCommand().equals("Modificar")){
            if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
                conf.setRuc(txtRucConfig.getText());
                conf.setNombre(txtNombreConfig.getText());
                conf.setTelefono(txtTelefonoConfig.getText());
                conf.setDireccion(txtDireccionConfig.getText());
                conf.setMensaje(txtMensaje.getText());
                conf.setId(Integer.parseInt(txtIdConfig.getText()));
                lgDao.ModificarDatos(conf);
                JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
                //ListarConfig();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
        
        if (e.getActionCommand().equals("Registrar")){
            if (txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtPass.getPassword().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
            } else {
                login lg = new login();
                String correo = txtCorreo.getText();
                String pass = String.valueOf(txtPass.getPassword());
                String nom = txtNombre.getText();
                String rol = cbxRol.getSelectedItem().toString();
                lg.setNombre(nom);
                lg.setCorreo(correo);
                lg.setPass(pass);
                lg.setRol(rol);
                lgDao.Registrar(lg);
                JOptionPane.showMessageDialog(null, "Usuario Registrado");
            }
        }
        
        if (e.getActionCommand().equals("GuardarPlato")){
            if (!"".equals(txtNombrePlato.getText()) || !"".equals(txtPrecioPlato.getText())) {
                pla.setNombre(txtNombrePlato.getText());
               pla.setPrecio(Double.parseDouble(txtPrecioPlato.getText()));
               pla.setFecha(fechaFormato);
               if (plaDao.Registrar(pla)) {
                   JOptionPane.showMessageDialog(null, "Plato Registrado");
                   LimpiarTable();
                   ListarPlatos(TablePlatos);
                   LimpiarPlatos();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
        
        if (e.getActionCommand().equals("EditarPlato")){
            if ("".equals(txtIdPlato.getText())) {
                JOptionPane.showMessageDialog(null, "Seleecione una fila");
            } else {
                if (!"".equals(txtNombrePlato.getText()) || !"".equals(txtPrecioPlato.getText())) {
                    pla.setNombre(txtNombrePlato.getText());
                    pla.setPrecio(Double.parseDouble(txtPrecioPlato.getText()));
                    pla.setId(Integer.parseInt(txtIdPlato.getText()));
                    if (plaDao.Modificar(pla)) {
                        JOptionPane.showMessageDialog(null, "Plato Modificado");
                        LimpiarTable();
                        ListarPlatos(TablePlatos);
                        LimpiarPlatos();
                    }
                }
            }
        }
        
        if (e.getActionCommand().equals("EliminarPlato")){
            if (!"".equals(txtIdPlato.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
                if (pregunta == 0) {
                    int id = Integer.parseInt(txtIdPlato.getText());
                    plaDao.Eliminar(id);
                    LimpiarTable();
                    LimpiarPlatos();
                    ListarPlatos(TablePlatos);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            }
        }
        
        if (e.getActionCommand().equals("NuevoPlato")){
            LimpiarPlatos();
        }  
        
        if (e.getActionCommand().equals("Login")){
            validar();
        }
        
        if (e.getActionCommand().equals("Salir")){
            System.exit(0);
        }
        
    }    
    
    //Hasta aqui botones
    
    
    /* Controlado 
    TablePedidos.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
        TablePedidosMouseClicked(evt);
        }
    */
    
    
    
    
    
    
    
    private void TablePedidosMouseClicked(MouseEvent evt) {
        // Verificar si se hizo clic en una fila
        int fila = TablePedidos.rowAtPoint(evt.getPoint());
        if (fila >= 0) { // Asegúrate de que la fila es válida
            int id_pedido = Integer.parseInt(TablePedidos.getValueAt(fila, 0).toString());
        
            // Lógica que deseas ejecutar
            LimpiarTable();
            verPedido(id_pedido);
            verPedidoDetalle(id_pedido);
            jTabbedPane1.setSelectedIndex(4);
            btnFinalizar.setEnabled(false);
            txtIdHistorialPedido.setText("" + id_pedido);
        }
    }
    
    private void tableSalaMouseClicked(java.awt.event.MouseEvent evt){
        int fila = tableSala.rowAtPoint(evt.getPoint());
        txtIdSala.setText(tableSala.getValueAt(fila, 0).toString());
        txtNombreSala.setText(tableSala.getValueAt(fila, 1).toString());
        txtMesas.setText(tableSala.getValueAt(fila, 2).toString());
    }
    
    private void labelLogoMouseClicked(java.awt.event.MouseEvent evt){
        jTabbedPane1.setSelectedIndex(0);
        PanelSalas.removeAll();
        panelSalas();
    }
    
    private void TablePlatosMouseClicked(java.awt.event.MouseEvent evt){
        int fila = TablePlatos.rowAtPoint(evt.getPoint());
        txtIdPlato.setText(TablePlatos.getValueAt(fila, 0).toString());
        txtNombrePlato.setText(TablePlatos.getValueAt(fila, 1).toString());
        txtPrecioPlato.setText(TablePlatos.getValueAt(fila, 2).toString());
    }

    private void txtBuscarPlatoKeyReleased(java.awt.event.KeyEvent evt) {                                           
        LimpiarTable();
        ListarPlatos(tblTemPlatos);
    }                                          

    private void txtPrecioPlatoKeyTyped(java.awt.event.KeyEvent evt) {                                        
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioPlato);
    }    
    
    @Override
    public void keyReleased(java.awt.event.KeyEvent evt) {
        
        Object source = evt.getSource();{
        
        
        if (source == txtBuscarPlato) {
            LimpiarTable();
            ListarPlatos(tblTemPlatos);
        }
        
    }
        
        
    }

    // Evento para cuando se presiona una tecla en el campo txtPrecioPlato
    @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        Object source = evt.getSource();
        
        if (source == txtPrecioPlato) {
            event.numberDecimalKeyPress(evt, txtPrecioPlato);  // Suponiendo que 'event' es una clase que maneja validaciones
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     
}
