/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author JOSE
 */
public class Conexion {
    String url="jdbc:mysql://localhost:3306/bbdd_clientes";
    String user="root",pass="";    
    Connection con;
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
            System.out.println("conexión establecida");
        } catch (Exception e) {   
            System.out.println("Eror: error en la conexión");
        }
        return con;
    }
}