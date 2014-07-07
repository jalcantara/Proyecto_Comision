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
public class ListaUsuario {
    private int int_id;
    private String var_user;
    private String var_password;
    private String var_dni;
    private String var_nombres;
    private String var_apellidos;
    private String var_descripcion;
    private String var_telefono;

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

    public String getVar_dni() {
        return var_dni;
    }

    public void setVar_dni(String var_dni) {
        this.var_dni = var_dni;
    }

    public String getVar_nombres() {
        return var_nombres;
    }

    public void setVar_nombres(String var_nombres) {
        this.var_nombres = var_nombres;
    }

    public String getVar_apellidos() {
        return var_apellidos;
    }

    public void setVar_apellidos(String var_apellidos) {
        this.var_apellidos = var_apellidos;
    }

    public String getVar_descripcion() {
        return var_descripcion;
    }

    public void setVar_descripcion(String var_descripcion) {
        this.var_descripcion = var_descripcion;
    }

    public String getVar_telefono() {
        return var_telefono;
    }

    public void setVar_telefono(String var_telefono) {
        this.var_telefono = var_telefono;
    }
    public String toString(){
        return this.var_nombres+" "+var_apellidos;
    } 
    
    
    
}
