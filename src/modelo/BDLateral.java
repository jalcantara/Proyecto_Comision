/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Agricultor;
import entidad.Lateral;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDLateral {
    
    public ArrayList<Lateral> get_lateral_all(){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_listalateral_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Lateral l = new Lateral();
                l.setInt_id(rs.getInt(1));
                l.setCliente_id(rs.getInt(2));
                l.setVar_lateral(rs.getString(3));
                l.setVar_sublateral(rs.getString(4));
                l.setDec_conmedida(rs.getDouble(5));
                l.setDec_sinmedida(rs.getDouble(6));
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public ArrayList<Lateral> get_lateral_byactivocliente(String condicion,int idCliente){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_listalateral_all where "+condicion+" and cliente_id="+idCliente;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Lateral l = new Lateral();
                l.setInt_id(rs.getInt(1));
                l.setCliente_id(rs.getInt(2));
                l.setVar_lateral(rs.getString(3));
                l.setVar_sublateral(rs.getString(4));
                l.setDec_conmedida(rs.getDouble(5));
                l.setDec_sinmedida(rs.getDouble(6));
                l.setInt_numhectareas(rs.getInt(7));
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
}
