/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Agricultor;
import entidad.Lateral;
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author joseph
 */
public class BLAgricultor {

    public ArrayList<Agricultor> get_agricultores_byActivos(String condicion) {
        ArrayList<Agricultor> lista_agricultores = new ArrayList<Agricultor>();
        try {
            lista_agricultores = new BDAgricultor().get_agricultores_byActivos(" var_nombre like '%" + condicion + "%'");
        } catch (Exception e) {
            System.out.println("Error de ListarClientesActivos-Controlador" + e.getMessage());
        }
        return lista_agricultores;
    }

    public ArrayList<String> get_latreles_all() {
        ArrayList<String> lista_laterales = new ArrayList<>();
        try {
            lista_laterales = new BDAgricultor().get_latreles_all();
        } catch (Exception e) {
            System.out.println("Error de Listar Laterales - Controlador" + e.getMessage());
        }
        return lista_laterales;
    }

    public ArrayList<String> get_sublatreles_all() {
        ArrayList<String> lista_sublaterales = new ArrayList<>();
        try {
            lista_sublaterales = new BDAgricultor().get_sublatreles_all();
        } catch (Exception e) {
            System.out.println("Error de Listar de SubLaterales - Controlador" + e.getMessage());
        }
        return lista_sublaterales;
    }

    public ArrayList<Agricultor> get_agricultores_antiguos() {
        ArrayList<Agricultor> listAgricultor = new ArrayList<Agricultor>();
        try {
            listAgricultor = new BDAgricultor().get_agricultores_antiguos();
        } catch (Exception e) {
            System.out.println("Error de Listado- Agricultores Antiguos - Controlador" + e.getMessage());
        }
        return listAgricultor;
    }

    public ArrayList<Agricultor> get_agricultores_nuevos() {
        ArrayList<Agricultor> listAgricultor = new ArrayList<Agricultor>();
        try {
            listAgricultor = new BDAgricultor().get_agricultores_nuevos();
        } catch (Exception e) {
            System.out.println("Error de Listado- Agricultores Antiguos - Controlador" + e.getMessage());
        }
        return listAgricultor;
    }


    public boolean RegistrarAgricultor(int id_agricultor, String nom,
            String apemat, String apepat, String dir, String email, String dni,
            String sexo, String tel, String cel, ArrayList<Lateral> listaLaterales) {
        boolean resultado = false;
    
    /*public boolean RegistrarAgricultor(String nom,String apepat,String apemat,String dir,
            String email,String dni,String sexo,String tel,String cel){
        boolean resultado=false;*/

        try {
            Agricultor a = new Agricultor();
            a.setInt_id(id_agricultor);
            a.setVar_nombre(nom);
            a.setVar_apepaterno(apepat);
            a.setVar_apematerno(apemat);            
            a.setVar_direccion(dir);
            a.setVar_email(email);
            a.setVar_dni(dni);
            a.setVar_sexo(sexo);
            a.setVar_telefono(tel);
            a.setVar_celular(cel);
            resultado = new BDAgricultor().RegistrarAgricultor(a, listaLaterales);
        } catch (Exception e) {
            System.out.println("Error de Registro Agricultores -  Controlador" + e.getMessage());
        }
        return resultado;
    }
}
