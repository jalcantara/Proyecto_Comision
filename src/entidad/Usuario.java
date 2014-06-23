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
public class Usuario {
    private int int_id;
    private int trabajador_id;
    private String var_user;
    private String var_password;
    private String var_estado;
    
    
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    

    public String getVar_user() {
        return var_user;
    }

    public void setVar_user(String var_user) {
        this.var_user = var_user;
    }

    public String getVar_password() {
        return var_password;
    }

    public void setVar_password(String var_password) {
        this.var_password = var_password;
    }

    public String getVar_estado() {
        return var_estado;
    }

    public void setVar_estado(String var_estado) {
        this.var_estado = var_estado;
    }

    public int getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(int trabajador_id) {
        this.trabajador_id = trabajador_id;
    }
    
    
    
}
