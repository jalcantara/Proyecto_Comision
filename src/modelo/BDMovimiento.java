/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidad.Movimiento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author joseph
 */
public class BDMovimiento {

    public boolean insertarMovimiento(Movimiento m) {

        boolean resultado = true;
        Connection cnn = null;
        CallableStatement cstmt = null;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql = "call spI_Movimiento(?,?,?,?,?,?,?,?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, m.getUsuario_id());
            cstmt.setString(2, m.getVar_concepto());
            cstmt.setDouble(3, m.getDec_monto());
            cstmt.setInt(4, m.getInt_tipoOperacion());
            cstmt.setTimestamp(5, m.getDat_fecregistro());
            cstmt.setInt(6, m.getInt_tipoComprobante());
            cstmt.setString(7, m.getVar_numeroComprobante());
            cstmt.setDouble(8, m.getDec_cantidad());
            cstmt.setInt(9, m.getInt_proveedor());
            cstmt.execute();
            cnn.commit();
        } catch (SQLException a) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.toString());
            } finally {
                resultado = false;
            }
            System.out.println("error al registrar movimiento " + a.toString());
        } finally {
            try {
                cstmt.close();
                cnn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }

}
