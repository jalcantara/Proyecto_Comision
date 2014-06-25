/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.ListaUsuario;
import entidad.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDUsuario {

    public boolean Registrar(Usuario u) {
        Connection cn = null;
        CallableStatement cstm = null;
        boolean resultado = true;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Usuario(?,?,?,?,?,?,?,?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, u.getVar_user());
            cstm.setString(2, u.getVar_password());
            cstm.setString(3, u.getVar_dni());
            cstm.setString(4, u.getVar_nombres());
            cstm.setString(5, u.getVar_apellidos());
            cstm.setInt(6, u.getCargo_id());
            cstm.setString(7, u.getVar_direccion());
            cstm.setString(8, u.getVar_email());
            cstm.setString(9, u.getVar_telefono());
            cstm.setDate(10, u.getDat_fechanacimiento());
            cstm.execute();
            cn.commit();
        } catch (SQLException a) {
            try {
                cn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.getMessage());
            } finally {
                resultado = false;
            }
            System.out.println("" + a);
        } finally {
            try {
                cstm.close();
                cn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }
    
    
    
    
    public ArrayList<ListaUsuario> get_usuario_all(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaUsuario> listUsuario = new ArrayList<ListaUsuario>();
        try {
            cnn = BD.getConnection();
            String sql="select * from  get_usuario_all where " + condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                ListaUsuario a = new ListaUsuario();
                a.setInt_id(rs.getInt("int_id"));
                a.setVar_user(rs.getString("var_user"));
                a.setVar_password(rs.getString("var_password"));
                a.setVar_dni(rs.getString("var_dni"));
                a.setVar_nombres(rs.getString("var_nombres"));
                a.setVar_apellidos(rs.getString("var_apellidos"));
                a.setVar_descripcion(rs.getString("var_descripcion"));
                a.setVar_telefono(rs.getString("var_telefono"));
                listUsuario.add(a);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listUsuario;
    }

}
