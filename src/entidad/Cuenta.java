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
public class Cuenta {
    private int int_id;
    private String var_codigo;
    private String var_nombre;
    private String var_numcuenta;
    private String var_estado;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public String getVar_codigo() {
        return var_codigo;
    }

    public void setVar_codigo(String var_codigo) {
        this.var_codigo = var_codigo;
    }

    public String getVar_nombre() {
        return var_nombre;
    }

    public void setVar_nombre(String var_nombre) {
        this.var_nombre = var_nombre;
    }

    public String getVar_numcuenta() {
        return var_numcuenta;
    }

    public void setVar_numcuenta(String var_numcuenta) {
        this.var_numcuenta = var_numcuenta;
    }

    public String getVar_estado() {
        return var_estado;
    }

    public void setVar_estado(String var_estado) {
        this.var_estado = var_estado;
    }
    
    public String toString(){
        return this.var_nombre;
    }
    
}
