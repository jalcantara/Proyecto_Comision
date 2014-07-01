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
public class Constancia {

    private int int_id;
    private String var_serie;
    private String var_numero;
    private int comite_id;
    private int lateral_id;
    private int periodo_id;
    private int int_campania;
    private int int_estado;
    private Timestamp dat_fechRegistro;
    private String var_tipoconstancia;
    private Timestamp dat_fechRealizacion;
    private double dec_nrohectaria;
    private int int_tipocultivo;
    private double dec_montoJunta;
    private double dec_montoComision;
    
    
    
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
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

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public int getComite_id() {
        return comite_id;
    }

    public void setComite_id(int comite_id) {
        this.comite_id = comite_id;
    }

   

    public Timestamp getDat_fechRegistro() {
        return dat_fechRegistro;
    }

    public void setDat_fechRegistro(Timestamp dat_fechRegistro) {
        this.dat_fechRegistro = dat_fechRegistro;
    }

    public String getVar_tipoconstancia() {
        return var_tipoconstancia;
    }

    public void setVar_tipoconstancia(String var_tipoconstancia) {
        this.var_tipoconstancia = var_tipoconstancia;
    }

    public Timestamp getDat_fechRealizacion() {
        return dat_fechRealizacion;
    }

    public void setDat_fechRealizacion(Timestamp dat_fechRealizacion) {
        this.dat_fechRealizacion = dat_fechRealizacion;
    }

    public double getDec_nrohectaria() {
        return dec_nrohectaria;
    }

    public void setDec_nrohectaria(double dec_nrohectaria) {
        this.dec_nrohectaria = dec_nrohectaria;
    }

    public int getPeriodo_id() {
        return periodo_id;
    }

    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }

    public int getInt_campania() {
        return int_campania;
    }

    public void setInt_campania(int int_campania) {
        this.int_campania = int_campania;
    }

    public int getLateral_id() {
        return lateral_id;
    }

    public void setLateral_id(int lateral_id) {
        this.lateral_id = lateral_id;
    }

    public int getInt_tipocultivo() {
        return int_tipocultivo;
    }

    public void setInt_tipocultivo(int int_tipocultivo) {
        this.int_tipocultivo = int_tipocultivo;
    }

    public double getDec_montoJunta() {
        return dec_montoJunta;
    }

    public void setDec_montoJunta(double dec_montoJunta) {
        this.dec_montoJunta = dec_montoJunta;
    }

    public double getDec_montoComision() {
        return dec_montoComision;
    }

    public void setDec_montoComision(double dec_montoComision) {
        this.dec_montoComision = dec_montoComision;
    }

}
