/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.Alquiler;
import entidad.Detalle_Alquiler;
import java.sql.Timestamp;
import java.util.ArrayList;
import modelo.BDAlquiler;

/**
 *
 * @author joseph
 */
public class BLAlquiler {
    public boolean insertarAlquiler(int idCliente,ArrayList<Detalle_Alquiler> lista_detalle){
        boolean resultado=false;
        try {
            BDAlquiler a=new BDAlquiler();
            resultado=a.insertarAlquiler(idCliente,lista_detalle);                    
        } 
        catch (Exception e) {
            System.out.println("Error de Ingreso"+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
