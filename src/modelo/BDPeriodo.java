/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Periodo;
import entidad.PeriodoCampania;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDPeriodo {

    public ArrayList<PeriodoCampania> get_periodo_all_byactivos() {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<PeriodoCampania> lista_periodo = new ArrayList<PeriodoCampania>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_periodo_all where int_estado=1;";
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                PeriodoCampania pc = new PeriodoCampania();
                pc.setPeriodo_id(rs.getInt("int_id"));
                pc.setVar_periodo(rs.getString("var_periodo"));
                pc.setInt_mesInicio(rs.getInt("int_mesInicio"));
                pc.setInt_mesFin(rs.getInt("int_mesFin"));
                pc.setInt_estado(rs.getInt("int_estado"));
                pc.setNom_estado(rs.getString("nom_estado"));
                lista_periodo.add(pc);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_periodo;
    }
    
    public ArrayList<PeriodoCampania> get_periodo_all(String condicion) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<PeriodoCampania> lista_periodo = new ArrayList<PeriodoCampania>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_periodo_all where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                PeriodoCampania pc = new PeriodoCampania();
                pc.setPeriodo_id(rs.getInt("int_id"));
                pc.setVar_periodo(rs.getString("var_periodo"));
                pc.setInt_mesInicio(rs.getInt("int_mesInicio"));
                pc.setInt_mesFin(rs.getInt("int_mesFin"));
                pc.setInt_estado(rs.getInt("int_estado"));
                pc.setNom_estado(rs.getString("nom_estado"));
                pc.setNom_mesInicio(rs.getString("mesinicio"));
                pc.setNom_mesFin(rs.getString("mesfin"));
                lista_periodo.add(pc);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return lista_periodo;
    }
    
    public PeriodoCampania get_peridocampania_byagricultor(int idCliente, Date fechaRegistro) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        PeriodoCampania pc = new PeriodoCampania();
        try {
            cnn = BD.getConnection();
            String sql = "call spC_PeriodoCampania_byCliente(?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, idCliente);
            cstmt.setDate(2, fechaRegistro);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                pc.setInt_campania(rs.getInt("campania"));
                pc.setPeriodo_id(rs.getInt("periodo_id"));
                pc.setVar_periodo(rs.getString("var_periodo"));
                pc.setNom_mesInicio(rs.getString("nombre_mesInicio"));
                pc.setInt_mesInicio(rs.getInt("int_mesInicio"));
                pc.setNom_mesFin(rs.getString("nombre_mesFin"));
                pc.setInt_mesFin(rs.getInt("int_mesFin"));
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return pc;
    }
    
    public boolean Registrar(Periodo p)throws Exception{
        boolean resultado=false;
        Connection cn=null;
        CallableStatement cstm=null;
        try {
            cn = BD.getConnection();
            cn.setAutoCommit(false);
            String sql = "call spI_Periodo(?,?,?);";
            cstm = cn.prepareCall(sql);
            cstm.setString(1, p.getVar_periodo());
            cstm.setInt(2, p.getInt_mesInicio());
            cstm.setInt(3, p.getInt_mesFin());
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
