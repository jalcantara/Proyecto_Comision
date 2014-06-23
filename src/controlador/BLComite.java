/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Comite;
import java.util.ArrayList;
import modelo.BDComite;

/**
 *
 * @author joseph
 */
public class BLComite {

    public ArrayList<Comite> get_comite_byActivos(String condicion) {
        ArrayList<Comite> listComite = new ArrayList<>();
        try {
            BDComite comite = new BDComite();
            listComite = comite.get_comite_byActivos(" var_nombre like '%" + condicion + "%'");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar Comites Activos- cOntrolador" + e.getMessage());
        }
        return listComite;
    }

    public boolean Registrar(String comite) throws Exception {
        boolean resultado = false;
        try {
            Comite c = new Comite();
            c.setVar_nombre(comite);
            resultado = new BDComite().Registrar(c);
        } catch (Exception e) {
            System.out.println("Error de Registrar Comite - Controlador" + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
