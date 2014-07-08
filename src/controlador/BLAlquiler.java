/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.Alquiler;
import entidad.Detalle_Alquiler;
import entidad.ListaAlquiler;
import java.sql.Timestamp;
import java.util.ArrayList;
import modelo.BDAlquiler;

/**
 *
 * @author joseph
 */
public class BLAlquiler {
    public boolean insertarAlquiler(int idtrabajador,int idCliente,ArrayList<Detalle_Alquiler> lista_detalle,int identificador){
        boolean resultado=false;
        try {
            BDAlquiler a=new BDAlquiler();
            resultado=a.insertarAlquiler(idtrabajador,idCliente,lista_detalle,identificador);                    
        } 
        catch (Exception e) {
            System.out.println("Error de Ingreso"+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
    public ArrayList<ListaAlquiler> get_alquiler_byclientefecha(String condicion){
        ArrayList<ListaAlquiler> listAlquiler=new ArrayList<ListaAlquiler>();
        try {
            BDAlquiler a=new BDAlquiler();
            listAlquiler=a.get_alquiler_byclientefecha(condicion);
        } 
        catch (Exception e) {
            System.out.println("Error de Listado - Controlador"+e.getMessage());
            e.printStackTrace();
        }
        return listAlquiler;
    }
}
