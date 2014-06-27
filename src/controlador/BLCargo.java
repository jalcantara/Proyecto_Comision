/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.Cargo;
import java.util.ArrayList;
import modelo.BDCargo;

/**
 *
 * @author joseph
 */
public class BLCargo {
    public ArrayList<Cargo> get_cargo_all(String condicion) {
        ArrayList<Cargo> listCargo=new ArrayList<Cargo>();
        try {
            BDCargo c=new BDCargo();
            listCargo=c.get_cargo_all(" var_descripcion like '%" + condicion + "%'");
        } 
        catch (Exception e) {
            System.out.println("Error de Listado Cargo -Controlador"+e.getMessage());
            e.printStackTrace();
        }
        return listCargo;
    }
    public boolean Registrar(String descripcion)throws Exception{
        boolean resultado=false;
        try {
            BDCargo c=new BDCargo();
            resultado=c.Registrar(descripcion);
        } 
        catch (Exception e) {
            System.out.println("Error de Registro en el Controlador"+e.getMessage());
        }
        return resultado;
    }
}
