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
public class ListaAlquiler {
    private String var_nombre_cliente;
    private String var_apepaterno;
    private String var_apematerno;
    private String var_nombre_material;
    private Timestamp dat_fechinicio;
    private Timestamp dat_fechfin;
    private int int_cantidad;
    private double dec_monto;
    private Timestamp dat_fechaRegistro;
    private int idCliente;
    private String var_numero;
    
    
    
    
    
    public String getVar_nombre_cliente() {
        return var_nombre_cliente;
    }

    public void setVar_nombre_cliente(String var_nombre_cliente) {
        this.var_nombre_cliente = var_nombre_cliente;
    }

    public String getVar_apepaterno() {
        return var_apepaterno;
    }

    public void setVar_apepaterno(String var_apepaterno) {
        this.var_apepaterno = var_apepaterno;
    }

    public String getVar_apematerno() {
        return var_apematerno;
    }

    public void setVar_apematerno(String var_apematerno) {
        this.var_apematerno = var_apematerno;
    }

    public String getVar_nombre_material() {
        return var_nombre_material;
    }

    public void setVar_nombre_material(String var_nombre_material) {
        this.var_nombre_material = var_nombre_material;
    }

    public Timestamp getDat_fechinicio() {
        return dat_fechinicio;
    }

    public void setDat_fechinicio(Timestamp dat_fechinicio) {
        this.dat_fechinicio = dat_fechinicio;
    }

    public Timestamp getDat_fechfin() {
        return dat_fechfin;
    }

    public void setDat_fechfin(Timestamp dat_fechfin) {
        this.dat_fechfin = dat_fechfin;
    }

    public int getInt_cantidad() {
        return int_cantidad;
    }

    public void setInt_cantidad(int int_cantidad) {
        this.int_cantidad = int_cantidad;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }

    public Timestamp getDat_fechaRegistro() {
        return dat_fechaRegistro;
    }

    public void setDat_fechaRegistro(Timestamp dat_fechaRegistro) {
        this.dat_fechaRegistro = dat_fechaRegistro;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getVar_numero() {
        return var_numero;
    }

    public void setVar_numero(String var_numero) {
        this.var_numero = var_numero;
    }
    
    
}
