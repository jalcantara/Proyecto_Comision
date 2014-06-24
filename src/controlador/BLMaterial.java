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
    public ArrayList<Material> get_material_all() {
        ArrayList<Material> lista=new ArrayList<Material>();
        try {
            BDMaterial m=new BDMaterial();
            lista=m.get_material_all();
            
        } catch (Exception e) {
            System.out.println("Error de Listado"+e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}
