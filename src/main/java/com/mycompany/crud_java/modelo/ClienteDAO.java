/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java.modelo;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JOSE
 */
public class ClienteDAO {
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conexion = new Conexion();
    Cliente u=new Cliente();
    
    

    public ArrayList listar() {
        ArrayList<Cliente> arrayClientes = new ArrayList<>();
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement("SELECT * FROM CLIENTES");
            rs = ps.executeQuery();
            
            while (rs.next()) { //Bucle que recorre la consulta obtenida
                Cliente c=new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setGenero(rs.getString("genero"));
                c.setEstado(rs.getString("estado"));
                c.setImagen(rs.getString("imagen"));
                arrayClientes.add(c);
            }
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                
                if (ps != null) ps.close();
                
                if (con != null) con.close();
            }
            catch(SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        return arrayClientes;
    }
    
    
     public int agregar(Cliente cliente) {  
        int resultado=0;
        
        String sql="INSERT INTO CLIENTES (nombre, telefono, correo, genero, estado, imagen) VALUES (?,?,?,?,?,?)";
        
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);            
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getTelefono());
            ps.setString(3,cliente.getCorreo());
            ps.setString(4, cliente.getGenero());
            ps.setString(5, cliente.getEstado());
            ps.setString(6, cliente.getImagen());
            
            resultado=ps.executeUpdate();   
            
            if(resultado==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                
                if (ps != null) ps.close();
                
                if (con != null) con.close();
            }
            catch(SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            }
        } 
        return resultado;
    }
     
     
     public int actualizar(Cliente cliente) {  
        int res=0;
        String sql="UPDATE CLIENTES SET nombre=?, telefono=?, correo=?, genero=?, estado=?, imagen=? WHERE id=?";       
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);  
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getTelefono());
            ps.setString(3,cliente.getCorreo());
            ps.setString(4, cliente.getGenero());
            ps.setString(5, cliente.getEstado());
            ps.setString(6, cliente.getImagen());
            ps.setInt(7,cliente.getId());
           
            res=ps.executeUpdate();   
            
            if(res==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                
                if (ps != null) ps.close();
                
                if (con != null) con.close();
            }
            catch(SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        return res;
    }
     
     
    public int eliminar(int id){
        int res=0;
        String sql="DELETE FROM CLIENTES WHERE id="+id;
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            res= ps.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                
                if (ps != null) ps.close();
                
                if (con != null) con.close();
            }
            catch(SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        return res;
    }
    
        
}
