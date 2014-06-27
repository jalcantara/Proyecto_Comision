/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Asignar_Costo;
import entidad.Cuenta;
import entidad.ListaCuentaMonto;
import java.util.ArrayList;
import modelo.BDAsignar_Costo;
import modelo.BDCuenta;

/**
 *
 * @author joseph
 */
public class BLCuenta {

    public boolean Registrar(String codigo, String nombre, String num_cuenta) {
        boolean resultado = false;
        try {
            Cuenta c = new Cuenta();
            c.setVar_codigo(codigo);
            c.setVar_nombre(nombre);
            c.setVar_numcuenta(num_cuenta);
            resultado = new BDCuenta().Registrar(c);
        } catch (Exception e) {
            System.out.println("error en controlador al registrar cuenta " + e.toString());
        }
        return resultado;
    }

    public boolean AsignarCosto(int id_cuenta, double monto, String concepto) {
        boolean resultado = false;
        try {
            Asignar_Costo c = new Asignar_Costo();
            c.setCuenta_id(id_cuenta);
            c.setDec_monto(monto);
            c.setVar_concepto(concepto);
            resultado = new BDAsignar_Costo().Registrar(c);
        } catch (Exception e) {
            System.out.println("error en controlador al registrar asignar_cuenta " + e.toString());
        }
        return resultado;
    }

    public ArrayList<Cuenta> get_cuenta_all(String condicion) {
        ArrayList<Cuenta> listCuenta = null;
        try {
            BDCuenta cu = new BDCuenta();
            listCuenta = cu.get_cuenta_all(" var_nombre like '%" + condicion + "%'");
        } catch (Exception e) {
            System.out.println("error en controlador al consultar cuenta " + e.toString());
        }
        return listCuenta;
    }

    public ArrayList<Asignar_Costo> get_asignarcosto_cuenta_all() {
        ArrayList<Asignar_Costo> listCuenta = null;
        try {
            BDAsignar_Costo cu = new BDAsignar_Costo();
            listCuenta = cu.get_asignarcosto_cuenta_all();
        } catch (Exception e) {
            System.out.println("error en controlador al consultar costo_cuenta " + e.toString());
        }
        return listCuenta;
    }
    public ListaCuentaMonto get_cuentamonto_all(double numhectareas) {
        ListaCuentaMonto objcuentamonto=new ListaCuentaMonto();
        try {
            BDCuenta c=new BDCuenta();
            objcuentamonto=c.get_cuentamonto_all(numhectareas);
        } 
        catch (Exception e) {
            System.out.println("Error de Listado"+e.getMessage());
            e.printStackTrace();
        }
        return objcuentamonto;
    }

}
