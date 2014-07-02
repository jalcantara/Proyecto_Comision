/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Agricultor;
import entidad.Lateral;
import entidad.ListaLateral;
import entidad.SubLateral;
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
    
    public ArrayList<ListaLateral> get_listalateral_all(){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaLateral> listLateral = new ArrayList<ListaLateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_listalateral_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                ListaLateral l = new ListaLateral();
                l.setInt_id(rs.getInt(1));
                l.setCliente_id(rs.getInt(2));
                l.setDec_conmedida(rs.getDouble(3));
                l.setDec_sinmedida(rs.getDouble(4));
                l.setInt_numhectareas(rs.getInt(5));
                l.setVar_descripcion(rs.getString(6));
                l.setVar_descripcion_sublateral(rs.getString(7));
                
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public ArrayList<ListaLateral> get_listalateral_byactivocliente(String condicion,int idCliente){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaLateral> listLateral = new ArrayList<ListaLateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_listalateral_all where "+condicion+" and cliente_id="+idCliente;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                ListaLateral l = new ListaLateral();
                l.setInt_id(rs.getInt(1));
                l.setCliente_id(rs.getInt(2));
                l.setDec_conmedida(rs.getDouble(3));
                l.setDec_sinmedida(rs.getDouble(4));
                l.setInt_numhectareas(rs.getInt(5));
                l.setVar_descripcion(rs.getString(6));
                l.setVar_descripcion_sublateral(rs.getString(7));
                
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public ArrayList<Lateral> get_lateral_all(){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_lateral_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Lateral l = new Lateral();
                l.setInt_id(rs.getInt(1));
                l.setVar_descripcion(rs.getString(2));
                l.setVar_estado(rs.getString(3));
                
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public ArrayList<SubLateral> get_sublateral_all(){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<SubLateral> listLateral = new ArrayList<SubLateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_sublateral_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                SubLateral l = new SubLateral();
                l.setInt_id(rs.getInt(1));
                l.setVar_descripcion(rs.getString(2));
                l.setVar_estado(rs.getString(3));
                
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
