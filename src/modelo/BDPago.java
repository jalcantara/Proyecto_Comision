/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Pago;
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
public class BDPago {
    public ArrayList<Pago> get_pagos_bycliente(String condicion) {
        ArrayList<Pago> lista = new ArrayList<Pago>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_pagos_bycliente where" + condicion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pago p = new Pago();
                p.setInt_id(rs.getInt("idPago"));
                p.setVar_cuenta(rs.getString("var_cuenta"));
                p.setVar_descripcion(rs.getString("var_descripcion"));
                p.setDat_fechregistro(rs.getDate("dat_fechRegistro"));
                p.setDec_monto(rs.getDouble("dec_monto"));
                p.setVar_observacion(rs.getString("var_observacion"));
                p.setVar_boucherpago(rs.getString("var_boucherpago"));
                p.setInt_estado(rs.getString("estado"));
                lista.add(p);
            }
            cnn.close();
            ps.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        } finally {
        }
        return lista;
    }
    public boolean RegistrarPagos(Pago p) {
        boolean resultado = false;
        Connection cnn = null;
        CallableStatement cstmt = null;       
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql = "call spU_RegistrarPagos(?,?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, p.getInt_id());
            cstmt.setString(2, p.getVar_boucherpago());
            cstmt.setString(3, p.getVar_observacion());            
            cstmt.execute(); 
            resultado=true;
            cnn.commit();
        } catch (SQLException a) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.toString());
            } finally {
                resultado = false;
            }
            System.out.println("error al registrar constancia " + a.toString());
        } finally {
            try {
                cstmt.close();
                cnn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }
    public boolean AnularPago(int id) {
        boolean resultado = false;
        Connection cnn = null;
        CallableStatement cstmt = null;       
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql = "call spU_AnularPago(?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, id);                      
            cstmt.execute(); 
            resultado=true;
            cnn.commit();
        } catch (SQLException a) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.toString());
            } finally {
                resultado = false;
            }
            System.out.println("error al registrar constancia " + a.toString());
        } finally {
            try {
                cstmt.close();
                cnn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }
}
