/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Constante;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDConstante {
    
    public ArrayList<Constante> get_constante_all_byClase(int clase) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Constante> lista_constante = new ArrayList<Constante>();
        try {
            cnn = BD.getConnection();
            String sql = "select c.* from constante c where c.int_valor<>0 and c.int_clase = "+clase;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Constante c = new Constante();
                c.setInt_id(rs.getInt("int_id"));
                c.setInt_valor(rs.getInt("int_valor"));
                c.setVar_descripcion(rs.getString("var_descripcion"));
                c.setInt_clase(rs.getInt("int_clase"));
                lista_constante.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_constante;
    }
    
    
    
}
