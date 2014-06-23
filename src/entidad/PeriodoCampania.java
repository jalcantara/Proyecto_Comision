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
public class PeriodoCampania {

    private int int_campania;
    private int periodo_id;
    private String var_periodo;
    private String nom_mesInicio;
    private int int_mesInicio;
    private String nom_mesFin;
    private int int_mesFin;
    private int int_estado;
    private String nom_estado;

    public int getInt_campania() {
        return int_campania;
    }

    public void setInt_campania(int int_campania) {
        this.int_campania = int_campania;
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

    public int getInt_mesFin() {
        return int_mesFin;
    }

    public void setInt_mesFin(int int_mesFin) {
        this.int_mesFin = int_mesFin;
    }

    public String getNom_mesInicio() {
        return nom_mesInicio;
    }

    public void setNom_mesInicio(String nom_mesInicio) {
        this.nom_mesInicio = nom_mesInicio;
    }

    public int getInt_mesInicio() {
        return int_mesInicio;
    }

    public void setInt_mesInicio(int int_mesInicio) {
        this.int_mesInicio = int_mesInicio;
    }

    public String getNom_mesFin() {
        return nom_mesFin;
    }

    public void setNom_mesFin(String nom_mesFin) {
        this.nom_mesFin = nom_mesFin;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public String getNom_estado() {
        return nom_estado;
    }

    public void setNom_estado(String nom_estado) {
        this.nom_estado = nom_estado;
    }

    public String toString(){
        return this.getVar_periodo();
    }
}
