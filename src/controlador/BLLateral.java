/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Lateral;
import entidad.ListaLateral;
import entidad.SubLateral;
import java.util.ArrayList;
import modelo.BDLateral;

/**
 *
 * @author joseph
 */
public class BLLateral {

    public ArrayList<ListaLateral> get_lateral_byactivocliente(String condicion, int idCliente) {
        ArrayList<ListaLateral> listLateral = new ArrayList<>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_listalateral_byactivocliente(" var_descripcion like '%" + condicion + "%'", idCliente);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public ArrayList<Lateral> get_lateral_all() {
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_lateral_all();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public ArrayList<SubLateral> get_sublateral_all() {
        ArrayList<SubLateral> listLateral = new ArrayList<SubLateral>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_sublateral_all();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
}
