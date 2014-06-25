/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import entidad.Lateral;
import entidad.ListaTraspasos;
import entidad.Traspaso;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joseph
 */
public class BDTraspaso {
    public boolean RegistrarTraspaso(Traspaso t,int idClienteAntiguo,Lateral l){
        Connection cn=null;
        CallableStatement cstm=null;
//        CallableStatement cstm1=null;
        boolean resultado=false;
        try {
            cn=BD.getConnection();
            cn.setAutoCommit(false);
            String sql="call spI_Traspaso(?,?,?,?);";
            cstm=cn.prepareCall(sql);
            cstm.setInt(1, t.getCliente_id());
            cstm.setInt(2, t.getUsuario_id());
            cstm.setInt(3, t.getInt_cantidadtraspaso());
            cstm.setInt(4, t.getInt_clienteAntiguo_id());
            cstm.execute();
            //DESCONTAR HECTAREAS
            String sql1="call spU_Traspaso_RestarHectareas(?,?,?);";
            cstm=cn.prepareCall(sql1);
            cstm.setInt(1, idClienteAntiguo);
            cstm.setInt(2, l.getInt_id());
            cstm.setInt(3, t.getInt_cantidadtraspaso());
            cstm.execute();            
            
            //INSERTAR EN LISTA_LATERAL EL NUEVO CLIENTE
            String sql2="call spI_ListaLateral_Traspaso(?,?,?,?,?);";
            cstm=cn.prepareCall(sql2);
            cstm.setInt(1, l.getCliente_id());
            cstm.setString(2, l.getVar_lateral());
            cstm.setString(3, l.getVar_sublateral());
            cstm.setDouble(4, l.getDec_conmedida());
            cstm.setDouble(5, l.getDec_sinmedida()); 
            cstm.execute();
            resultado=true;
            System.out.println("Exito de Registro");
            cn.commit();
            
        } 
        catch (SQLException e) {
            try {
                cn.rollback();                
            } catch (SQLException ex) {
                System.out.println("Error de Rollback"+ex.getMessage());
            }finally{
                resultado=false;
            }
            System.out.println("Error de Registro de Traspaso"+e.getMessage());
            
        }finally{
            try {
                cstm.close();
                //cstm1.close();
                cn.close();
            } 
            catch (SQLException e) {
                System.out.println("Error de Finally"+e.getMessage());
            }
        }
        return resultado;
    }
    public ArrayList<ListaTraspasos> get_traspaso_byclientenuevoantiguo(String condicion) {
        Connection cnn = null;
        CallableStatement cstmt = null;
        ArrayList<ListaTraspasos> listTraspasos = new ArrayList<ListaTraspasos>();
        try {
            cnn = BD.getConnection();
            String sql = "select * from get_traspaso_bylientenuevoantiguo where "+condicion;
            cstmt = cnn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                ListaTraspasos t = new ListaTraspasos();
                t.setInt_id_traspaso(rs.getInt("int_id_traspaso"));
                t.setInt_id_nuevo(rs.getInt("int_id_nuevo"));
                t.setVar_nombre_nuevo(rs.getString("var_nombre_nuevo"));
                t.setVar_apepaterno_nuevo(rs.getString("var_apepaterno_nuevo"));
                t.setVar_apematerno_nuevo(rs.getString("var_apematerno_nuevo"));
                t.setInt_clienteAntiguo_id(rs.getInt("int_clienteAntiguo_id"));
                t.setVar_nombre_antiguo(rs.getString("var_nombre_antiguo"));
                t.setVar_apepaterno_antiguo(rs.getString("var_apepaterno_antiguo"));
                t.setVar_apematerno_antiguo(rs.getString("var_apematerno_antiguo"));
                t.setInt_cantidadtraspaso(rs.getInt("int_cantidadtraspaso"));
                t.setVar_lateral(rs.getString("var_lateral"));
                t.setVar_sublateral(rs.getString("var_sublateral"));
                t.setDec_conmedida(rs.getDouble("dec_conmedida"));
                t.setDec_sinmedida(rs.getDouble("dec_sinmedida"));
                listTraspasos.add(t);
            }
            cstmt.close();
            cnn.close();
        } catch (SQLException a) {
            System.out.println("" + a);
        }
        return listTraspasos;
    }
}
