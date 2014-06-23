/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Comite;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDComite {
    
    public ArrayList<Comite> get_comite_all(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Comite> listComite = new ArrayList<Comite>();
        try {
            cnn = BD.getConnection();
            String sql="select * from  get_comite_all where " + condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Comite c = new Comite();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setInt_estado(rs.getInt("int_estado"));
                if(rs.getInt("int_estado") == 1){
                    c.setVar_estado("Activo");
                }else{
                    c.setVar_estado("Inactivo");
                }
                listComite.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listComite;
    }
    
     public ArrayList<Comite> get_comite_byActivos(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Comite> listComite = new ArrayList<Comite>();
        try {
            cnn = BD.getConnection();
            String sql="select * from  get_comite_all where int_estado='1' and " + condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Comite c = new Comite();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setInt_estado(rs.getInt("int_estado"));
                if(rs.getInt("int_estado") == 1){
                    c.setVar_estado("Activo");
                }else{
                    c.setVar_estado("Inactivo");
                }
                listComite.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listComite;
    }
     
    public boolean Registrar(Comite c)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Comite(?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, c.getVar_nombre());
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
