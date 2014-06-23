/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Detalle_Constancia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author joseph
 */
public class BDDetalle_Constancia {
    /*public boolean insertarDetalleConstancia(Detalle_Constancia d) {
        boolean resultado=false;
        int idConstancia=0;
        Connection cnn = null;
        CallableStatement cstmt = null;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql="call spI_DetalleConstancia(?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, d.getCuenta_id());
            cstmt.setInt(2, d.getConstancia_id());
            cstmt.executeUpdate();
            resultado=true;
            cnn.commit();
        } catch (SQLException a) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
                System.out.println("" + b.getMessage());
            } finally {
                resultado = false;
            }
            System.out.println("" + a);
        } finally {
            try {
                cstmt.close();
                cnn.close();
            } catch (SQLException ex) {
                System.out.println("" + ex.getMessage());
            }
        }
        return resultado;
    }*/
}
