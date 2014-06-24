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
    public ArrayList<Material> get_material_all() {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Material> lista_material = new ArrayList<Material>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_material_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Material m = new Material();
                m.setInt_id(rs.getInt("int_id"));
                m.setVar_nombre(rs.getString("var_nombre"));
                m.setInt_cantidad(rs.getInt("int_cantidad"));
                m.setVar_descripcion(rs.getString("var_descripcion"));
                m.setInt_estado(rs.getInt("int_estado"));
                lista_material.add(m);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_material;
    }
}
