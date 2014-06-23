/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Constancia;
import entidad.ListaConstancia;
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
public class BDConstancia {

    public boolean insertarConstancia(Constancia c,ArrayList<Integer> lista_cuentas) {

        boolean resultado = true;
        int idConstancia = 0;
        Connection cnn = null;
        CallableStatement cstmt = null;
        CallableStatement cstmt1 = null;
        CallableStatement cstmt2 = null;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql = "call spI_Constancia(?,?,?,?,?,?,?,?,?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, c.getComite_id());
            cstmt.setInt(2, c.getLateral_id());
            cstmt.setInt(3, c.getInt_campania());
            cstmt.setInt(4, c.getPeriodo_id());
            cstmt.setString(5, c.getVar_tipoconstancia());
            cstmt.setTimestamp(6, c.getDat_fechRealizacion());
            cstmt.setDouble(7, c.getDec_nrohectaria());
            cstmt.setTimestamp(8, c.getDat_fechRegistro());
            cstmt.setInt(9, c.getInt_tipocultivo());
            cstmt.setInt(10, 0);
            cstmt.registerOutParameter("idconstancia", Types.INTEGER);
            cstmt.execute();
            idConstancia = cstmt.getInt("idconstancia");
            // Registrar Detalle
            for (int i = 0; i < lista_cuentas.size(); i++) {
                String sql1 = "call spI_DetalleConstancia(?,?);";
                cstmt1 = cnn.prepareCall(sql1);
                cstmt1.setInt(1, lista_cuentas.get(i));
                cstmt1.setInt(2, idConstancia);
                cstmt1.execute();
            }     
            // Registrar Pagos
            String sql2 = "call spI_Pagos(?,?,?);";
            cstmt2 = cnn.prepareCall(sql2);
            cstmt2.setInt(1, 1); // es el codigo del usuario cambiar despues
            cstmt2.setInt(2, idConstancia);
            cstmt2.setDouble(3, c.getDec_nrohectaria());
            cstmt2.execute();
            cnn.commit();
        } catch (SQLException a) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.toString());
            } finally {
                resultado = false;
            }
            System.out.println("error al registrar constancia " + a.toString());
        } finally {
            try {
                cstmt.close();
                cstmt1.close();
                cstmt2.close();
                cnn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }

    public ArrayList<ListaConstancia> get_constancia_byfiltro(String condicion) {
        ArrayList<ListaConstancia> lista = new ArrayList<ListaConstancia>();
        try {
            Connection cnn = BD.getConnection();
            PreparedStatement ps = null;
            ps = cnn.prepareStatement("select * from get_constancia_byfiltro where" + condicion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ListaConstancia l = new ListaConstancia();
                l.setConstancia_id(rs.getInt("constancia_id"));
                l.setVar_serie(rs.getString("var_serie"));
                l.setVar_numero(rs.getString("var_numero"));
                l.setVar_nombre(rs.getString("var_nombre"));
                l.setVar_apepaterno(rs.getString("var_apepaterno"));
                l.setVar_apematerno(rs.getString("var_apematerno"));
                l.setPeriodo_id(rs.getInt("int_periodo"));
                l.setVar_periodo(rs.getString("var_periodo"));
                l.setDat_fechRegistro(rs.getDate("dat_fechRegistro"));
                l.setVar_lateral(rs.getString("var_lateral"));
                l.setNroHectarea(rs.getDouble("nroHectarea"));
                l.setNroCamapania(rs.getInt("nroCampania"));
                l.setTipoSiembra(rs.getString("tipoSiembra"));
                l.setFechaSiembra(rs.getDate("fechaSiembra"));
                lista.add(l);
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
