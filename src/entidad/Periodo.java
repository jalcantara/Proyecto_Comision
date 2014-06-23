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
public class Periodo {
    private int int_id;
    private String var_periodo;
    private int int_estado;
    private int int_mesInicio;
    private int int_mesFin;
    
    
    
    
    
     
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public String getVar_periodo() {
        return var_periodo;
    }

    public void setVar_periodo(String var_periodo) {
        this.var_periodo = var_periodo;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public int getInt_mesInicio() {
        return int_mesInicio;
    }

    public void setInt_mesInicio(int int_mesInicio) {
        this.int_mesInicio = int_mesInicio;
    }

    public int getInt_mesFin() {
        return int_mesFin;
    }

    public void setInt_mesFin(int int_mesFin) {
        this.int_mesFin = int_mesFin;
    }
    public String toString(){
        return this.var_periodo;
    } 

    
    
}
