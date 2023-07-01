package com.mycompany.crud_java.controlador;

import com.mycompany.crud_java.modelo.Cliente;
import com.mycompany.crud_java.modelo.ClienteDAO;
import com.mycompany.crud_java.vista.VistaPrincipal;
import java.awt.Component;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JOSE
 */
public class VistaController implements ActionListener{
    ClienteDAO dao = new ClienteDAO();
    Cliente cliente = new Cliente();
    VistaPrincipal vista = new VistaPrincipal();
 
    String pathImage;
    DefaultTableModel defaultTableModel; //Codigo que crea el modelo de la tabla
   
    
    /**
     * Método constructor
     * @param v 
     */
    public VistaController(VistaPrincipal v) {
        this.vista = v;
        
        //asociamos un escuchador de eventos a cada botón
        this.vista.btn_listar.addActionListener(this);
        this.vista.btn_guardar.addActionListener(this);
        this.vista.btn_eliminar.addActionListener(this);
        this.vista.btn_actualizar.addActionListener(this);
      
    }
    
    
 
    
    /**
     * Reinicia los campos
     */
    public void reset() {
        vista.text_id.setText("");
        vista.text_nombre.setText("");
        vista.text_telefono.setText("");
        vista.text_correo.setText("");
        vista.text_nombre.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
     
        if (e.getSource() == vista.btn_listar) {
            listar(vista.tabla);
            reset();
        }
        
        if (e.getSource() == vista.btn_guardar) {     
            agregar();
        }
        
        
        if (e.getSource() == vista.btn_actualizar) {     
            actualizar();
          
        }
         
        if (e.getSource() == vista.btn_eliminar) {     
            eliminar();
          
        }
          
        
    }
    
    /**
     * Genera una lista de usuarios
     * @param jtable 
     */
     public void listar(JTable jtable) {
        limpiarTabla();
        
       defaultTableModel = new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex==6){
                    return JLabel.class;
                }

                return super.getColumnClass(columnIndex); 
            }
        };  //Codigo que crea el modelo de la tabla
         
        //jtable.setDefaultRenderer(Object.class, new ImageRenderer());
        //defaultTableModel = (DefaultTableModel) jtable.getModel(); //recupera el modelo de tabla generado en el constructor
        
        //cargamos los títulos
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("NOMBRE");
        defaultTableModel.addColumn("TELÉFONO");
        defaultTableModel.addColumn("CORREO");
        defaultTableModel.addColumn("GÉNERO");
        defaultTableModel.addColumn("ESTADO");
       
        jtable.setModel(defaultTableModel);
        
        ArrayList<Cliente> listaClientes = dao.listar(); //Linea que ejecuta la consulta sql y almacena los datos
        
        Object[] objeto = new Object[7]; //Variable que almacena los datos de la consulta
        
        for (int i = 0; i < listaClientes.size(); i++) {
            
            objeto[0] = listaClientes.get(i).getId();
            objeto[1] = listaClientes.get(i).getNombre();
            objeto[2] = listaClientes.get(i).getTelefono();
            objeto[3] = listaClientes.get(i).getCorreo();
            objeto[4] = listaClientes.get(i).getGenero();
            objeto[5] = listaClientes.get(i).getEstado();
            //objeto[6] = listaClientes.get(i).getImagen();
            
            ImageIcon icon = new ImageIcon(new ImageIcon(listaClientes.get(i).getImagen())
                    .getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
           
            objeto[6]=new JLabel(icon);
             
            //agrega la fila a la tabla
            defaultTableModel.addRow(objeto);
            
        }
        
        jtable.setRowHeight(50);
        jtable.setRowMargin(2);
       // jtable.getColumnModel().getColumn(6).setCellRenderer(new ImageRenderer());
        centrarCeldas(jtable);
       
    }
     
     
     public void agregar() {
        String nombre = vista.text_nombre.getText().trim();
        String telefono = vista.text_telefono.getText().trim();
        String correo = vista.text_correo.getText().trim();
        String genero="";
        String estado="";
         
        if( !nombre.isEmpty() && !telefono.isEmpty() && !correo.isEmpty()){
            
            if(vista.jradio_hombre.isSelected()){
                genero=vista.jradio_hombre.getText();
            }

            if(vista.jradio_mujer.isSelected()){
                genero=vista.jradio_mujer.getText();
            }
            
            estado=vista.jc_lista_estado.getSelectedItem().toString();
            
            cliente.setNombre(nombre);
            cliente.setCorreo(correo);
            cliente.setTelefono(telefono);
            cliente.setEstado(estado);
            cliente.setGenero(genero);
           
            int res = dao.agregar(cliente);

            if (res == 1) {
                JOptionPane.showMessageDialog(vista, "Usuario Agregado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error");
            }
            limpiarTabla();
            listar(vista.tabla);
            reset();
            
            
        }else{
            JOptionPane.showMessageDialog(vista, "Error: no puede haber campos vacios");
        }
        
       
    }
     
     
    public void actualizar() {
        
     
            String id = vista.text_id.getText().trim();
            String nom = vista.text_nombre.getText().trim();
            String tel = vista.text_telefono.getText().trim();
            String correo = vista.text_correo.getText().trim();
            String genero="";
            String estado="";
            
            if(vista.jradio_hombre.isSelected()){
                genero=vista.jradio_hombre.getText();
            }

            if(vista.jradio_mujer.isSelected()){
                genero=vista.jradio_mujer.getText();
            }
            
            estado=vista.jc_lista_estado.getSelectedItem().toString();
            
              if( !id.isEmpty() && !nom.isEmpty() && !tel.isEmpty() && !correo.isEmpty()){
                   
                    cliente.setId(Integer.parseInt(id));
                    cliente.setNombre(nom);
                    cliente.setCorreo(correo);
                    cliente.setTelefono(tel);
                    cliente.setEstado(estado);
                    cliente.setGenero(genero);
                    
                    dao.actualizar(cliente);


                    JOptionPane.showMessageDialog(vista, "Usuario actualizado satisfactoriamente");

                     limpiarTabla(); 
                     listar(vista.tabla);
                    reset();
                  
              }else{
                  
                  JOptionPane.showMessageDialog(vista, "Error: no puede haber campos vacios");
              }    
       
    }
    
    
    public void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "No ha seleccionado ninguna fila");
        } else {
            int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
            dao.eliminar(id);
            System.out.println("ID fila: " + id);
            JOptionPane.showMessageDialog(vista, "Usuario eliminado satisfactoriamente");
        }
        limpiarTabla();
        listar(vista.tabla);
        reset();
    }
    
   
     

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            defaultTableModel.removeRow(i);
            i = i - 1;
        }
    }

   
    
}
