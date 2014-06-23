/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.sql.Timestamp;

/**
 *
 * @author joseph
 */
public class ListaPagos {
    private String var_numcuenta;
    private String var_nombre;
    private Timestamp dat_fechRegistro;
    private double dec_monto;

    public String getVar_numcuenta() {
        return var_numcuenta;
    }

    public void setVar_numcuenta(String var_numcuenta) {
        this.var_numcuenta = var_numcuenta;
    }

    public String getVar_nombre() {
        return var_nombre;
    }

    public void setVar_nombre(String var_nombre) {
        this.var_nombre = var_nombre;
    }

    public Timestamp getDat_fechRegistro() {
        return dat_fechRegistro;
    }

    public void setDat_fechRegistro(Timestamp dat_fechRegistro) {
        this.dat_fechRegistro = dat_fechRegistro;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }
    
    
}
