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
public class Movimiento {

    private int int_id;
    private int usuario_id;
    private String var_concepto;
    private double dec_monto;
    private int int_tipoOperacion;
    private Timestamp dat_fecregistro;
    private int int_estado;
    private int int_tipoComprobante;
    private String var_numeroComprobante;
    private double dec_cantidad;
    private int int_proveedor;

    public Timestamp getDat_fecregistro() {
        return dat_fecregistro;
    }

    public void setDat_fecregistro(Timestamp dat_fecregistro) {
        this.dat_fecregistro = dat_fecregistro;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public String getVar_numeroComprobante() {
        return var_numeroComprobante;
    }

    public void setVar_numeroComprobante(String var_numeroComprobante) {
        this.var_numeroComprobante = var_numeroComprobante;
    }

    public double getDec_cantidad() {
        return dec_cantidad;
    }

    public void setDec_cantidad(double dec_cantidad) {
        this.dec_cantidad = dec_cantidad;
    }

    public int getInt_proveedor() {
        return int_proveedor;
    }

    public void setInt_proveedor(int int_proveedor) {
        this.int_proveedor = int_proveedor;
    }

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public int getInt_tipoOperacion() {
        return int_tipoOperacion;
    }

    public void setInt_tipoOperacion(int int_tipoOperacion) {
        this.int_tipoOperacion = int_tipoOperacion;
    }

    public int getInt_tipoComprobante() {
        return int_tipoComprobante;
    }

    public void setInt_tipoComprobante(int int_tipoComprobante) {
        this.int_tipoComprobante = int_tipoComprobante;
    }

}
