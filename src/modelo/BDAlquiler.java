/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Alquiler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author joseph
 */
public class BDAlquiler {
    
    /*public boolean insertarAlquiler(Alquiler a){
        int valor = 0;
        Connection cnn = null;
        CallableStatement cstmt = null;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            cstmt = cnn.prepareCall("{call ........ (?,?,?,?)}");
            cstmt.setInt(1, a.getCuenta().getInt_id());
            cstmt.setInt(2, a.getCliente().getInt_id());
            cstmt.setDate(3, a.getDat_fechinicio());
            cstmt.setDate(4, a.getDat_fechfin());         
            /*cstmt.registerOutParameter("codigo", Types.INTEGER);
            cstmt.executeUpdate();
            cnn.commit();
            valor = cstmt.getInt("codigo");
        } catch (SQLException s) {
            try {
                cnn.rollback();
            } catch (SQLException b) {
            }
            System.out.println("aquí es :/ " + s);
        } finally {
            try {
                cstmt.close();
                cnn.close();
            } catch (SQLException ex) {}
        }
        return valor;
    }*/
}
