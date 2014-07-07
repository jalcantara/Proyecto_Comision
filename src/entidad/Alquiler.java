
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author joseph
 */
public class Alquiler {
    private int int_id;
    private int cuenta_id;
    private int cliente_id;
    private int int_estado;   
    private String var_numero;
    
    
    
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public int getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(int cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    } 

    

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }

    public String getVar_numero() {
        return var_numero;
    }

    public void setVar_numero(String var_numero) {
        this.var_numero = var_numero;
    }

    
    
}
