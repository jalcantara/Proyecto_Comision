/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.util.Date;

/**
 *
 * @author joseph
 */
public class Pago {
    private int int_id;
    private int usuario_id;
    private Date dat_fechregistro;
    private double dec_monto;
    private int int_tipooperacion;
    private int int_codigooperacion;
    private String int_estado;
    private String var_cuenta;
    private String var_boucherpago;
    private String var_observacion;
    private String var_descripcion;
    
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

    public Date getDat_fechregistro() {
        return dat_fechregistro;
    }

    public void setDat_fechregistro(Date dat_fechregistro) {
        this.dat_fechregistro = dat_fechregistro;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }

    public int getInt_tipooperacion() {
        return int_tipooperacion;
    }

    public void setInt_tipooperacion(int int_tipooperacion) {
        this.int_tipooperacion = int_tipooperacion;
    }

    public int getInt_codigooperacion() {
        return int_codigooperacion;
    }

    public void setInt_codigooperacion(int int_codigooperacion) {
        this.int_codigooperacion = int_codigooperacion;
    }

    public String getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(String int_estado) {
        this.int_estado = int_estado;
    }

    public String getVar_cuenta() {
        return var_cuenta;
    }

    public void setVar_cuenta(String var_cuenta) {
        this.var_cuenta = var_cuenta;
    }

    public String getVar_boucherpago() {
        return var_boucherpago;
    }

    public void setVar_boucherpago(String var_boucherpago) {
        this.var_boucherpago = var_boucherpago;
    }

    public String getVar_observacion() {
        return var_observacion;
    }

    public void setVar_observacion(String var_observacion) {
        this.var_observacion = var_observacion;
    }

    public String getVar_descripcion() {
        return var_descripcion;
    }

    public void setVar_descripcion(String var_descripcion) {
        this.var_descripcion = var_descripcion;
    }
    
    
    
}
