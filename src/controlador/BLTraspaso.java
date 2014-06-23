/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Lateral;
import entidad.ListaTraspasos;
import entidad.Traspaso;
import java.util.ArrayList;
import modelo.BDTraspaso;

/**
 *
 * @author joseph
 */
public class BLTraspaso {

    public boolean RegistrarTraspaso(int idCliente_Nuevo, int idUsuario, int numhectareas, int idClienteAntiguo,
            int idLateral, String lateral, String sublateral, double conmedida, double sinmedida) {

        boolean resultado = false;
        try {
            //OBJETO TRASPASO
            Traspaso t = new Traspaso();
            t.setCliente_id(idCliente_Nuevo);
            t.setUsuario_id(idUsuario);
            t.setInt_cantidadtraspaso(numhectareas);
            t.setInt_clienteAntiguo_id(idClienteAntiguo);
            //OBJETO LATERAL
            Lateral l = new Lateral();
            l.setInt_id(idLateral);
            l.setCliente_id(idCliente_Nuevo);
            l.setVar_lateral(lateral);
            l.setVar_sublateral(sublateral);
            l.setDec_conmedida(conmedida);
            l.setDec_sinmedida(sinmedida);
            resultado = new BDTraspaso().RegistrarTraspaso(t, idClienteAntiguo, l);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Controlador" + e.getMessage());
        }
        return resultado;
    }
    public ArrayList<ListaTraspasos> get_cliente_all_byclientenuevoantiguo(String condicion) {
        ArrayList<ListaTraspasos> list=new ArrayList<>();
        try {
            BDTraspaso t=new BDTraspaso();
            list=t.get_traspaso_byclientenuevoantiguo(condicion);
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listado traspasos -  Controlador"+e.getMessage());
        }
        return  list;
    }
}
