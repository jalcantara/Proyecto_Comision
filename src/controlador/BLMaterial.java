/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import entidad.Material;
import java.util.ArrayList;
import modelo.BDMaterial;

/**
 *
 * @author joseph
 */
public class BLMaterial {
    public ArrayList<Material> get_material_all(String condicion) {
        ArrayList<Material> lista=new ArrayList<Material>();
        try {
            BDMaterial m=new BDMaterial();
            lista=m.get_material_all(" var_nombre like '%" + condicion + "%'");
            
        } catch (Exception e) {
            System.out.println("Error de Listado"+e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    public boolean Registrar(String nom,int  cantidad,String desc)throws Exception{
        boolean resultado=false;
        try {
            BDMaterial bdm=new BDMaterial();
            Material m=new Material();
            m.setVar_nombre(nom);
            m.setInt_cantidad(cantidad);
            m.setVar_descripcion(desc);
            resultado=bdm.Registrar(m);
            System.out.println("Exito de Registro");
        } 
        catch (Exception e) {
            System.out.println("Error de Registro Material "+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
