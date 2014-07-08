/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.NoCliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDNoCliente {
    public boolean Registrar(NoCliente nc)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_NoCliente(?,?,?,?,?,?,?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, nc.getVar_nombre());
            cstm.setString(2, nc.getVar_apematerno());
            cstm.setString(3, nc.getVar_apepaterno());
            cstm.setString(4, nc.getVar_direccion());
            cstm.setString(5, nc.getVar_dni());
            cstm.setString(6, nc.getVar_telefono());
            cstm.setString(7, nc.getVar_celular());
            cstm.setString(8, nc.getVar_ruc());
            cstm.setString(9, nc.getVar_razonsocial());
            cstm.execute();
            resultado=true;
            cn.commit();
        } 
        catch (Exception e) {
            try {
                cn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.toString());
            } finally {
                resultado = false;
            }
            System.out.println("error al registrar no-cliente " + e.toString());
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
    
    public ArrayList<NoCliente> get_nocliente_all(String condicion) {
        ArrayList<NoCliente> lista = new ArrayList<NoCliente>();
        try {
            Connection cnn = BD.getConnection();
            CallableStatement cstm = null;
            String sql="select * from get_nocliente_all where "+condicion;
            cstm=cnn.prepareCall(sql);
            ResultSet rs = cstm.executeQuery();
            while (rs.next()) {
                NoCliente c=new NoCliente();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nocliente"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                c.setVar_direccion(rs.getString("var_direccion"));
                c.setVar_dni(rs.getString("var_dni"));
                c.setInt_estado(rs.getInt("int_estado"));
                c.setVar_telefono(rs.getString("var_telefono"));
                c.setVar_celular(rs.getString("var_celular"));
                c.setVar_ruc(rs.getString("var_ruc"));
                lista.add(c);
            }
            cnn.close();
            cstm.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        } finally {
        }
        return lista;
    }
}
