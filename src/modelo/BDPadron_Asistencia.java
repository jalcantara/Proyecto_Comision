/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Detalle_Padron_Asistencia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDPadron_Asistencia {
    public boolean Registrar_PadronAsistencia(ArrayList<Detalle_Padron_Asistencia> list,int idUsuario){
        boolean resultado = false;
        Connection cnn = null;
        CallableStatement cstmt = null;
        CallableStatement cstm1=null;
        CallableStatement cstm2=null;
        int id_padronasistencia=0;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql="call spI_PadronAsistencia_Sufragio(?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, idUsuario);
            ResultSet rs=cstmt.executeQuery();
            if(rs.next()){
                id_padronasistencia = rs.getInt("int_id");
            }
            for (int i = 0; i < list.size(); i++) {
                String sql1 = "call spI_DetallePadronAsistencia_Sufragio(?,?,?);";
                cstm1 = cnn.prepareCall(sql1);
                cstm1.setString(1, list.get(i).getVar_dni());
                cstm1.setInt(2, id_padronasistencia);
                cstm1.setString(3, list.get(i).getVar_estado());
                cstm1.execute();
            }            
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
