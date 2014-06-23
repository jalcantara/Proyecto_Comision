/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Asignar_Costo;
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
public class BDAsignar_Costo {

    public boolean Registrar(Asignar_Costo c) {
        Connection cn = null;
        CallableStatement cstm = null;
        boolean resultado = false;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_AsignarCosto_Cuenta(?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setInt(1, c.getCuenta_id());
            cstm.setDouble(2, c.getDec_monto());
            cstm.setString(3, c.getVar_concepto());
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
    
    public ArrayList<Asignar_Costo> get_asignarcosto_cuenta_all() {
        ArrayList<Asignar_Costo> lista = new ArrayList<Asignar_Costo>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_asignarcosto_cuenta_all");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Asignar_Costo ac = new Asignar_Costo();
                ac.setInt_id(rs.getInt("int_id"));
                ac.setVar_nombre(rs.getString("var_nombre"));
                ac.setVar_concepto(rs.getString("var_concepto"));
                ac.setDec_monto(rs.getDouble("dec_monto"));
                lista.add(ac);
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
