/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Material;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDMaterial {
    public ArrayList<Material> get_material_all(String condicion) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Material> lista_material = new ArrayList<Material>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_material_all where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Material m = new Material();
                m.setInt_id(rs.getInt("int_id"));
                m.setVar_nombre(rs.getString("var_nombre"));
                m.setInt_cantidad(rs.getInt("int_cantidad"));
                m.setVar_descripcion(rs.getString("var_descripcion"));
                m.setInt_estado(rs.getInt("int_estado"));
                if(rs.getInt("int_estado")==1){
                    m.setVar_estado("Activo");
                }else{
                    m.setVar_estado("Inactivo");
                }
                lista_material.add(m);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_material;
    }
    public boolean Registrar(Material m)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Material(?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, m.getVar_nombre());
            cstm.setInt(2, m.getInt_cantidad());
            cstm.setString(3, m.getVar_descripcion());
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
