/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.Detalle_Padron_Asistencia;
import java.util.ArrayList;
import modelo.BDDetalle_Padron_Asistencia;
import modelo.BDPadron_Asistencia;

/**
 *
 * @author joseph
 */
public class BLPadron_Asistencia {
    public boolean Registrar_PadronAsistencia(ArrayList<Detalle_Padron_Asistencia> list,int idUsuario){
        boolean resultado=false;
        try {
            BDPadron_Asistencia d=new BDPadron_Asistencia();
            resultado=d.Registrar_PadronAsistencia(list, idUsuario);
        } 
        catch (Exception e) {
            System.out.println("Error de Ingreso");
            e.printStackTrace();
        }
        return resultado;
    }
}
