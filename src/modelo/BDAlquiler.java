/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Alquiler;
import entidad.Detalle_Alquiler;
import entidad.ListaAlquiler;
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
    
    public boolean insertarAlquiler(int idtrabajador,int idCliente,ArrayList<Detalle_Alquiler> lista_detalle,int identificador){
        boolean resultado = false;
        Connection cnn = null;
        CallableStatement cstmt = null;
        CallableStatement cstm1=null;
        CallableStatement cstm2=null;
        int id_alquiler=0;
        try {
            cnn = BD.getConnection();
            cnn.setAutoCommit(false);
            String sql="call spI_Alquiler (?,?,?,?);";
            cstmt = cnn.prepareCall(sql);
            cstmt.setInt(1, 2);
            cstmt.setInt(2, idCliente);
            cstmt.setInt(3, idtrabajador);
            cstmt.setInt(4, identificador);
            ResultSet rs=cstmt.executeQuery();
            if(rs.next()){
                id_alquiler = rs.getInt("int_id");
            }
            for (int i = 0; i < lista_detalle.size(); i++) {
                String sql1 = "call spI_DetalleAlquiler(?,?,?,?,?,?,?);";
                cstm1 = cnn.prepareCall(sql1);
                cstm1.setInt(1, id_alquiler);
                cstm1.setInt(2, lista_detalle.get(i).getMaterial_id());
                cstm1.setInt(3, lista_detalle.get(i).getInt_cantidad());
                cstm1.setDouble(4, lista_detalle.get(i).getDec_monto());
                cstm1.setTimestamp(5, lista_detalle.get(i).getDat_fechfin());
                cstm1.setTimestamp(6, lista_detalle.get(i).getDat_fechinicio());
                cstm1.setInt(7, lista_detalle.get(i).getInt_horas());
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
    public ArrayList<ListaAlquiler> get_alquiler_byclientefecha(String condicion){
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaAlquiler> listAlquiler = new ArrayList<ListaAlquiler>();
        try {
            cnn = BD.getConnection();
            String sql="select * from  get_alquiler_byclientefecha where " + condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()) {
                ListaAlquiler a = new ListaAlquiler();
                a.setAlquiler_id(rs.getInt("alquiler"));
                a.setIdCliente(rs.getInt("int_id"));
                a.setVar_nombre_cliente(rs.getString("var_nombre_cliente"));
                a.setVar_apepaterno(rs.getString("var_apepaterno"));
                a.setVar_apematerno(rs.getString("var_apematerno"));
                a.setInt_cantidad(rs.getInt("int_cantidad"));
                a.setDec_monto(rs.getDouble("dec_monto"));
                a.setDat_fechaRegistro(rs.getTimestamp("dat_fechaRegistro"));
                a.setVar_numero(rs.getString("var_numero"));
                listAlquiler.add(a);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listAlquiler;
    }
}
