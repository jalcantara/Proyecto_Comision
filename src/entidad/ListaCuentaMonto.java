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
public class ListaCuentaMonto {
    private int int_id;
    private String var_nombre;
    private String var_numcuenta;
    private double dec_monto;
    private double montojunta;
    private double montocomision;
    
    
    
    public String getVar_nombre() {
        return var_nombre;
    }

    public void setVar_nombre(String var_nombre) {
        this.var_nombre = var_nombre;
    }

    public String getVar_numcuenta() {
        return var_numcuenta;
    }

    public void setVar_numcuenta(String var_numcuenta) {
        this.var_numcuenta = var_numcuenta;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public double getMontojunta() {
        return montojunta;
    }

    public void setMontojunta(double montojunta) {
        this.montojunta = montojunta;
    }

    public double getMontocomision() {
        return montocomision;
    }

    public void setMontocomision(double montocomision) {
        this.montocomision = montocomision;
    }
    
    
}
