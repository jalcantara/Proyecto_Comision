/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Cargo;
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
public class BDCargo {
    public ArrayList<Cargo> get_cargo_all(String condicion) {
        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_cargo_all where "+condicion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cargo c = new Cargo();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_descripcion(rs.getString("var_descripcion"));
                c.setInt_estado(rs.getInt("int_estado"));
                if(rs.getInt("int_estado")== 1){
                    c.setVar_estado("Activo");
                }else{
                    c.setVar_estado("Inactivo");
                }
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
    public boolean Registrar(String descripcion)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Cargo(?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, descripcion);
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
            System.out.println("error al registrar constancia " + e.toString());
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
