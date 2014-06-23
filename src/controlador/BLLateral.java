/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Lateral;
import java.util.ArrayList;
import modelo.BDLateral;

/**
 *
 * @author joseph
 */
public class BLLateral {

    public ArrayList<Lateral> get_lateral_byactivocliente(String condicion, int idCliente) {
        ArrayList<Lateral> listLateral = new ArrayList<>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_lateral_byactivocliente(" var_lateral like '%" + condicion + "%'", idCliente);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
}
