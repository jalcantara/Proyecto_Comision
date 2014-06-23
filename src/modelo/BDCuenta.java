/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Cuenta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDCuenta {

    public boolean Registrar(Cuenta c) {
        Connection cn = null;
        CallableStatement cstm = null;
        boolean resultado = false;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Cuenta(?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, c.getVar_codigo());
            cstm.setString(2, c.getVar_nombre());
            cstm.setString(3, c.getVar_numcuenta());
            cstm.execute();
            resultado = true;
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

    public ArrayList<Cuenta> get_cuenta_all(String condicion) {
        ArrayList<Cuenta> lista = new ArrayList<Cuenta>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_cuenta_all where" + condicion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cuenta c = new Cuenta();
                c.setInt_id(rs.getInt(1));
                c.setVar_codigo(rs.getString(2));
                c.setVar_nombre(rs.getString(3));
                c.setVar_numcuenta(rs.getString(4));

                lista.add(c);
            }
            cnn.close();
            ps.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        } finally {
        }
        return lista;
    }
}
