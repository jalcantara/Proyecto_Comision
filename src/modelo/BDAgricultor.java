/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Agricultor;
import entidad.Lateral;
import entidad.ListaAgricultorLateral;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDAgricultor {

    public ArrayList<Agricultor> get_agricultor_all() {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Agricultor> lista_agricultor = new ArrayList<Agricultor>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_cliente_all;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Agricultor c = new Agricultor();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                c.setVar_direccion(rs.getString("var_direccion"));
                c.setVar_email(rs.getString("var_email"));
                c.setVar_dni(rs.getString("var_dni"));
                c.setVar_sexo(rs.getString("var_sexo"));
                c.setInt_estado(rs.getInt("int_estado"));
                c.setVar_telefono(rs.getString("var_telefono"));
                c.setVar_celular(rs.getString("var_celular"));
                c.setVar_estado("nom_estado");
                lista_agricultor.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_agricultor;
    }
    public ArrayList<Agricultor> get_agricultorlateral_all(String condicion) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Agricultor> lista_agricultor = new ArrayList<Agricultor>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_clientelateral where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Agricultor c = new Agricultor();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                c.setVar_direccion(rs.getString("var_direccion"));
                c.setVar_email(rs.getString("var_email"));
                c.setVar_dni(rs.getString("var_dni"));
                c.setVar_sexo(rs.getString("var_sexo"));
                c.setInt_estado(rs.getInt("int_estado"));
                c.setVar_telefono(rs.getString("var_telefono"));
                c.setVar_celular(rs.getString("var_celular"));
                c.setVar_estado("nom_estado");
                c.setNumLaterales(rs.getInt("numlaterales"));
                c.setInt_numhectareas(rs.getInt("int_numhectareas"));
                lista_agricultor.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_agricultor;
    }

    public ArrayList<String> get_latreles_all() {
        Connection cnn = null;
        PreparedStatement ps = null;
        ArrayList<String> lista_laterales = new ArrayList<>();
        try {
            cnn = BD.getConnection();
            String sql = "select var_lateral from lista_lateral";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista_laterales.add(rs.getString("var_lateral"));
            }
            ps.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_laterales;
    }

    public ArrayList<String> get_sublatreles_all() {
        Connection cnn = null;
        PreparedStatement ps = null;
        ArrayList<String> lista_sublaterales = new ArrayList<>();
        try {
            cnn = BD.getConnection();
            String sql = "select var_sublateral from lista_lateral";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista_sublaterales.add(rs.getString("var_sublateral"));
            }
            ps.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_sublaterales;
    }

    public ArrayList<Agricultor> get_agricultores_byActivos(String condicion) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Agricultor> listCliente = new ArrayList<Agricultor>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_cliente_all where int_estado= '1' and " + condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Agricultor c = new Agricultor();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                c.setVar_direccion(rs.getString("var_direccion"));
                c.setVar_email(rs.getString("var_email"));
                c.setVar_dni(rs.getString("var_dni"));
                c.setVar_sexo(rs.getString("var_sexo"));
                c.setInt_estado(rs.getInt("int_estado"));
                c.setVar_telefono(rs.getString("var_telefono"));
                c.setVar_celular(rs.getString("var_celular"));
                c.setVar_estado("nom_estado");
                listCliente.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listCliente;
    }
    
    public ArrayList<ListaAgricultorLateral> get_agricultorlateral_byid(int id) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaAgricultorLateral> lista_agricultor = new ArrayList<ListaAgricultorLateral>();
        try {
            cnn = BD.getConnection();
            String sql = "call spC_clientelateral_byid(?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                ListaAgricultorLateral c = new ListaAgricultorLateral();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                c.setVar_direccion(rs.getString("var_direccion"));
                c.setVar_email(rs.getString("var_email"));
                c.setVar_dni(rs.getString("var_dni"));
                c.setVar_sexo(rs.getString("var_sexo"));
                c.setInt_estado(rs.getInt("int_estado"));
                c.setVar_telefono(rs.getString("var_telefono"));
                c.setVar_celular(rs.getString("var_celular"));
                c.setInt_idlateral(rs.getInt("int_idlateral"));
                c.setVar_lateral(rs.getString("var_lateral"));
                c.setVar_sublateral(rs.getString("var_sublateral"));
                c.setDec_conmedida(rs.getDouble("dec_sinmedida"));
                c.setDec_sinmedida(rs.getDouble("dec_conmedida"));
                lista_agricultor.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_agricultor;
    }
    public ArrayList<Agricultor> get_agricultores_antiguos() {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Agricultor> listCliente = new ArrayList<Agricultor>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_traspaso_byclienteantiguo;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Agricultor c = new Agricultor();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                listCliente.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listCliente;
    }

    public ArrayList<Agricultor> get_agricultores_nuevos() {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<Agricultor> listCliente = new ArrayList<Agricultor>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_traspaso_byclientenuevo;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Agricultor c = new Agricultor();
                c.setInt_id(rs.getInt("int_id"));
                c.setVar_nombre(rs.getString("var_nombre"));
                c.setVar_apepaterno(rs.getString("var_apepaterno"));
                c.setVar_apematerno(rs.getString("var_apematerno"));
                listCliente.add(c);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listCliente;
    }

    public boolean RegistrarAgricultor(Agricultor a,ArrayList<Lateral> listaLaterales) {

        Connection cn = null;
        CallableStatement cstm = null;
        CallableStatement cstm1 = null;
        boolean resultado = true;
        int id_agricultor = 0;
        ResultSet rs=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Cliente(?,?,?,?,?,?,?,?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setInt(1, a.getInt_id());
            cstm.setString(2, a.getVar_nombre());
            cstm.setString(3, a.getVar_apematerno());
            cstm.setString(4, a.getVar_apepaterno());
            cstm.setString(5, a.getVar_direccion());
            cstm.setString(6, a.getVar_email());
            cstm.setString(7, a.getVar_dni());
            cstm.setString(8, a.getVar_sexo());
            cstm.setString(9, a.getVar_telefono());
            cstm.setString(10, a.getVar_celular());
            rs=cstm.executeQuery();
            if(rs.next()){
                id_agricultor = rs.getInt("int_id");
            }
            
             // Registrar Lista Laterales
            for (Lateral l : listaLaterales) {
                String sql1 = "call spI_ListaLateral(?,?,?,?,?,?,?);";
                cstm1 = cn.prepareCall(sql1);
                cstm1.setInt(1, l.getInt_id());
                cstm1.setInt(2, id_agricultor);
                cstm1.setString(3, l.getVar_lateral());
                cstm1.setString(4, l.getVar_sublateral());
                cstm1.setDouble(5, l.getDec_conmedida());
                cstm1.setDouble(6, l.getDec_sinmedida());
                cstm1.setInt(7, l.getInt_numhectareas());
                cstm1.execute();
            }
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error de Rollback" + ex.getMessage());
            } finally {
                resultado = false;
            }
            System.out.println("Error de Registro de Agricultor" + e.getMessage());

        } finally {
            try {
                cstm.close();
                cstm1.close();
                cn.close();
            } catch (SQLException e) {
                System.out.println("Error de Finally" + e.getMessage());
            }
        }
        return resultado;
    }
}
