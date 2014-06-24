/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Cargo;
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
    public ArrayList<Cargo> get_cargo_all() {
        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_cargo_all;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cargo c = new Cargo();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_descripcion(rs.getString("var_descripcion"));
                c.setInt_estado(rs.getInt("int_estado"));
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
