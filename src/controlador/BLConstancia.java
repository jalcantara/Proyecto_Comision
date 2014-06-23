/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Constancia;
import entidad.ListaConstancia;
import java.util.ArrayList;
import modelo.BDConstancia;

/**
 *
 * @author joseph
 */
public class BLConstancia {

    public boolean insertarConstancia(Constancia c) {
        boolean resultado = false;
        try {
            BDConstancia constancia = new BDConstancia();
            ArrayList<Integer> nro_cuentas = new ArrayList();
            nro_cuentas.add(3);
            nro_cuentas.add(4);
            nro_cuentas.add(5);
            nro_cuentas.add(6);
            resultado = constancia.insertarConstancia(c, nro_cuentas);
        } catch (Exception e) {
            System.out.println("error enviar al modelo : " + e.toString());
        }
        return resultado;
    }

    public ArrayList<ListaConstancia> get_constancia_byfiltro(String condicion) {
        ArrayList<ListaConstancia> list = null;
        try {
            BDConstancia c = new BDConstancia();
            list = c.get_constancia_byfiltro(condicion);
        }catch(Exception e){
            System.out.println("error consultar al modelo : " + e.toString());
        }
        return list;
    }
}
