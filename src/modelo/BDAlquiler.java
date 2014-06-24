/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Alquiler;
import entidad.Detalle_Alquiler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDAlquiler {
    
    public boolean insertarAlquiler(int idCliente,ArrayList<Detalle_Alquiler> lista_detalle){
        boolean resultado = false;
        Connection cnn = null;
        CallableStatement cstmt = null;
        CallableStatement cstm1=null;
        CallableStatement cstm2=null;
        int id_alquiler=0;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql="call spI_Alquiler (?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, 2);
            cstmt.setInt(2, idCliente);
            ResultSet rs=cstmt.executeQuery();
            if(rs.next()){
                id_alquiler = rs.getInt("int_id");
            }
            for (int i = 0; i < lista_detalle.size(); i++) {
                String sql1 = "call spI_DetalleAlquiler(?,?,?,?,?,?);";
                cstm1 = cnn.prepareCall(sql1);
                cstm1.setInt(1, id_alquiler);
                cstm1.setInt(2, lista_detalle.get(i).getMaterial_id());
                cstm1.setInt(3, lista_detalle.get(i).getInt_cantidad());
                cstm1.setDouble(4, lista_detalle.get(i).getDec_monto());
                cstm1.setTimestamp(5, lista_detalle.get(i).getDat_fechfin());
                cstm1.setTimestamp(6, lista_detalle.get(i).getDat_fechinicio());
                cstm1.execute();
            }
            // Registrar Pagos
            String sql2 = "call spI_Pagos_ByAlquiler(?,?);";
            cstm2 = cnn.prepareCall(sql2);
            cstm2.setInt(1, 1); // es el codigo del usuario cambiar despues
            cstm2.setInt(2, id_alquiler);
            cstm2.execute();
            
            cnn.commit();
            resultado=true;
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
        return resultado;
    }
}
