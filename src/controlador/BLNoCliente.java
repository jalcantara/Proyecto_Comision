/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.NoCliente;
import java.util.ArrayList;
import modelo.BDNoCliente;

/**
 *
 * @author joseph
 */
public class BLNoCliente {
    public boolean Registrar(String nom,String apemat,String apepat,String dir,String dni,String tel,String cel,
            String ruc,String razon)throws Exception{
        boolean resultado=false;
        try {
            BDNoCliente bdnc=new BDNoCliente();
            NoCliente c=new NoCliente();
            c.setVar_nombre(nom);
            c.setVar_apematerno(apemat);
            c.setVar_apepaterno(apepat);
            c.setVar_direccion(dir);
            c.setVar_dni(dni);
            c.setVar_telefono(tel);
            c.setVar_celular(cel);
            c.setVar_ruc(ruc);
            c.setVar_razonsocial(razon);
            resultado=bdnc.Registrar(c);
            
        } catch (Exception e) {
            System.out.println("Error de COntrolador "+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
        
    }
     public ArrayList<NoCliente> get_nocliente_all(String condicion) {
        ArrayList<NoCliente> list=new ArrayList<NoCliente>();
        try {
            BDNoCliente c=new BDNoCliente();
            list=c.get_nocliente_all(" var_nocliente like '%" + condicion + "%'");
        } 
        catch (Exception e) {
            System.out.println("Error de Listado Cargo -Controlador"+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
