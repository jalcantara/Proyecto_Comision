/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Lateral;
import entidad.ListaLateral;
import entidad.SubLateral;
import java.util.ArrayList;
import modelo.BDLateral;

/**
 *
 * @author joseph
 */
public class BLLateral {

    public ArrayList<ListaLateral> get_lateral_byactivocliente(String condicion, int idCliente) {
        ArrayList<ListaLateral> listLateral = new ArrayList<>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_listalateral_byactivocliente(" var_descripcion like '%" + condicion + "%'", idCliente);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public ArrayList<Lateral> get_lateral_all() {
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_lateral_all(" var_estado='1' ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public ArrayList<Lateral> gettabla_lateral_all(String palabra) {
        ArrayList<Lateral> listLateral = new ArrayList<Lateral>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.gettabla_lateral_all(" var_estado='1' and var_descripcion like '%" + palabra + "%'");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public ArrayList<SubLateral> get_sublateral_all(String condicion) {
        ArrayList<SubLateral> listLateral = new ArrayList<SubLateral>();
        try {
            BDLateral lateral = new BDLateral();
            listLateral = lateral.get_sublateral_all(" var_estado='1' and var_descripcion like '%" + condicion + "%' ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Listar LateraL_ByActivoCliente" + e.getMessage());
        }
        return listLateral;
    }
    public boolean Registrar(String descripcion)throws Exception{
        boolean resultado=false;
        try {
            BDLateral la=new BDLateral();
            Lateral lateral=new Lateral();
            lateral.setVar_descripcion(descripcion);
            resultado=la.Registrar(lateral);
        } 
        catch (Exception e) {
            System.out.println("Error de Ingreso"+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
    public boolean QuitarLateral(int id)throws Exception{
        boolean resultado=false;
        try {
            BDLateral l=new BDLateral();
            resultado=l.QuitarLateral(id);
        } 
        catch (Exception e) {
            System.out.println("Error de Edicion");
            e.printStackTrace();
        }
        return resultado;
    }
    public boolean Registrar_SubLateral(String descripcion)throws Exception{
        boolean resultado=false;
        try {
            BDLateral l=new BDLateral();
            resultado=l.Registrar_SubLateral(descripcion);
        } 
        catch (Exception e) {
            System.out.println("Error de Ingreso SubLateral "+e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
    public boolean QuitarSubLateral(int id)throws Exception{
        boolean resultado=false;
        try {
            BDLateral l=new BDLateral();
            resultado=l.QuitarSubLateral(id);
        } 
        catch (Exception e) {
            System.out.println("Error de Edicion");
            e.printStackTrace();
        }
        return resultado;
    }
}
