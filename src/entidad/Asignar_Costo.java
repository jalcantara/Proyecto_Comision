/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author joseph
 */
public class Asignar_Costo {

    private int int_id;
    private int cuenta_id;
    private String var_nombre;
    private String var_concepto;
    private double dec_monto;
    private int int_estado;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public int getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(int cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public String getVar_concepto() {
        return var_concepto;
    }

    public void setVar_concepto(String var_concepto) {
        this.var_concepto = var_concepto;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public String getVar_nombre() {
        return var_nombre;
    }

    public void setVar_nombre(String var_nombre) {
        this.var_nombre = var_nombre;
    }

}