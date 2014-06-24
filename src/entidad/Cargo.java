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
public class Cargo {
    private int int_id;
    private String var_descripcion;
    private int int_estado;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public String getVar_descripcion() {
        return var_descripcion;
    }

    public void setVar_descripcion(String var_descripcion) {
        this.var_descripcion = var_descripcion;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }
    
    public String toString(){
        return this.var_descripcion;
    } 
    
    

    
    
    
}
