/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Constante;
import java.util.ArrayList;
import modelo.BDConstante;

/**
 *
 * @author Richard
 */
public class BLConstante {

    public ArrayList<Constante> get_constante_all_byClase(int clase) {
        ArrayList<Constante> lista_constante = null;
        try {
            lista_constante = new BDConstante().get_constante_all_byClase(clase);
        } catch (Exception e) {
            System.out.println("error en controlador al consultar constante " + e.toString());
        }
        return lista_constante;
    }

    public ArrayList<Constante> get_tipocultivo_all(int clase) {
        ArrayList<Constante> listCultivo = new ArrayList<>();
        try {
            listCultivo = new BDConstante().get_constante_all_byClase(clase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCultivo;
    }

}
