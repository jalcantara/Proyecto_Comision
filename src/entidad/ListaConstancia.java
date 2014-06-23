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
public class ListaConstancia {

    private int constancia_id;
    private String var_serie;
    private String var_numero;
    private int cliente_id;
    private String var_nombre;
    private String var_apematerno;
    private String var_apepaterno;
    private int periodo_id;
    private String var_periodo;
    private Date dat_fechRegistro;
    private String var_lateral;
    private double nroHectarea;
    private int nroCamapania;
    private String tipoSiembra;
    private Date fechaSiembra;

    public int getConstancia_id() {
        return constancia_id;
    }

    public void setConstancia_id(int constancia_id) {
        this.constancia_id = constancia_id;
    }

    public String getVar_serie() {
        return var_serie;
    }

    public void setVar_serie(String var_serie) {
        this.var_serie = var_serie;
    }

    public String getVar_numero() {
        return var_numero;
    }

    public void setVar_numero(String var_numero) {
        this.var_numero = var_numero;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getVar_nombre() {
        return var_nombre;
    }

    public void setVar_nombre(String var_nombre) {
        this.var_nombre = var_nombre;
    }

    public String getVar_apematerno() {
        return var_apematerno;
    }

    public void setVar_apematerno(String var_apematerno) {
        this.var_apematerno = var_apematerno;
    }

    public String getVar_apepaterno() {
        return var_apepaterno;
    }

    public void setVar_apepaterno(String var_apepaterno) {
        this.var_apepaterno = var_apepaterno;
    }

    public int getPeriodo_id() {
        return periodo_id;
    }

    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }

    public String getVar_periodo() {
        return var_periodo;
    }

    public void setVar_periodo(String var_periodo) {
        this.var_periodo = var_periodo;
    }

    public Date getDat_fechRegistro() {
        return dat_fechRegistro;
    }

    public void setDat_fechRegistro(Date dat_fechRegistro) {
        this.dat_fechRegistro = dat_fechRegistro;
    }

    public String getVar_lateral() {
        return var_lateral;
    }

    public void setVar_lateral(String var_lateral) {
        this.var_lateral = var_lateral;
    }

    public double getNroHectarea() {
        return nroHectarea;
    }

    public void setNroHectarea(double nroHectarea) {
        this.nroHectarea = nroHectarea;
    }

    public int getNroCamapania() {
        return nroCamapania;
    }

    public void setNroCamapania(int nroCamapania) {
        this.nroCamapania = nroCamapania;
    }

    public String getTipoSiembra() {
        return tipoSiembra;
    }

    public void setTipoSiembra(String tipoSiembra) {
        this.tipoSiembra = tipoSiembra;
    }

    public Date getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(Date fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

}
