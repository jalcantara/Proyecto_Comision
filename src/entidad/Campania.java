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
public class Campania {
    private int int_id;
    private String var_campania;
    private int int_estado;
    private String var_estado;
    private Timestamp dat_fecInicio;
    private Timestamp dat_fecFin;
    private int int_periodo;
    
    
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public String getVar_campania() {
        return var_campania;
    }

    public void setVar_campania(String var_campania) {
        this.var_campania = var_campania;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public String getVar_estado() {
        return var_estado;
    }

    public void setVar_estado(String var_estado) {
        this.var_estado = var_estado;
    }
   

    public Timestamp getDat_fecInicio() {
        return dat_fecInicio;
    }

    public void setDat_fecInicio(Timestamp dat_fecInicio) {
        this.dat_fecInicio = dat_fecInicio;
    }

    public Timestamp getDat_fecFin() {
        return dat_fecFin;
    }

    public void setDat_fecFin(Timestamp dat_fecFin) {
        this.dat_fecFin = dat_fecFin;
    }

    public int getInt_periodo() {
        return int_periodo;
    }

    public void setInt_periodo(int int_periodo) {
        this.int_periodo = int_periodo;
    }
    
     public String toString(){
        return this.var_campania;
    } 
    
    
    
}
