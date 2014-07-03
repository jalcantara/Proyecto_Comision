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
            String sql="select * from get_listalateral_all where "+condicion+" and estadosublateral='1' and var_estado='1' and cliente_id="+idCliente;
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
    public ArrayList<Lateral> get_lateral_all(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_lateral_all where "+condicion;
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
    public ArrayList<Lateral> gettabla_lateral_all(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_lateral_all where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                Lateral l = new Lateral();
                l.setInt_id(rs.getInt(1));
                l.setVar_descripcion(rs.getString(2));
                if(rs.getString(3).equalsIgnoreCase("1")){
                    l.setVar_estado("Activo");
                }else{
                    l.setVar_estado("Inactivo");
                }
                
                
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public ArrayList<SubLateral> get_sublateral_all(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<SubLateral> listLateral = new ArrayList<SubLateral>();
        try {
            cnn = BD.getConnection();
            String sql="select * from get_sublateral_all where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                SubLateral l = new SubLateral();
                l.setInt_id(rs.getInt(1));
                l.setVar_descripcion(rs.getString(2));
                if(rs.getString(3).equalsIgnoreCase("1")){
                    l.setVar_estado("Activo");
                }else{
                    l.setVar_estado("Inactivo");
                }
                
                
                listLateral.add(l);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listLateral;
    }
    public boolean Registrar(Lateral l)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Lateral(?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, l.getVar_descripcion());
            
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
    public boolean Registrar_SubLateral(String descripcion)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_SubLateral(?);";
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
    public boolean QuitarLateral(int id)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spU_Lateral(?);";
            cstm = cn.prepareCall(sql);
            cstm.setInt(1, id);            
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
    public boolean QuitarSubLateral(int id)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spU_SubLateral(?);";
            cstm = cn.prepareCall(sql);
            cstm.setInt(1, id);            
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
