/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

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
            String sql = "call spI_Usuario(?,?,?,?,?,?,?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, u.getVar_user());
            cstm.setString(2, u.getVar_password());
            cstm.setString(3, u.getVar_dni());
            cstm.setString(4, u.getVar_nombres());
            cstm.setInt(5, u.getCargo_id());
            cstm.setString(6, u.getVar_direccion());
            cstm.setString(7, u.getVar_email());
            cstm.setString(8, u.getVar_telefono());
            cstm.setDate(9, u.getDat_fechanacimiento());
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

}
