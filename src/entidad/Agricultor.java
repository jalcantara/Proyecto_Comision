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
public class Agricultor {
    private int int_id;
    private String var_nombre;
    private String var_apematerno;
    private String var_apepaterno;
    private String var_direccion;
    private String var_email;
    private String var_dni;
    private String var_sexo;
    private int int_estado;
    private String var_telefono;
    private String var_celular;
    private String var_estado;

    
    
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
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

    public String getVar_direccion() {
        return var_direccion;
    }

    public void setVar_direccion(String var_direccion) {
        this.var_direccion = var_direccion;
    }

    public String getVar_email() {
        return var_email;
    }

    public void setVar_email(String var_email) {
        this.var_email = var_email;
    }

    public String getVar_dni() {
        return var_dni;
    }

    public void setVar_dni(String var_dni) {
        this.var_dni = var_dni;
    }

    public String getVar_sexo() {
        return var_sexo;
    }

    public void setVar_sexo(String var_sexo) {
        this.var_sexo = var_sexo;
    }

    

    public String getVar_telefono() {
        return var_telefono;
    }

    public void setVar_telefono(String var_telefono) {
        this.var_telefono = var_telefono;
    }

    public String getVar_celular() {
        return var_celular;
    }

    public void setVar_celular(String var_celular) {
        this.var_celular = var_celular;
    }

    public String getVar_estado() {
        return var_estado;
    }

    public void setVar_estado(String var_estado) {
        this.var_estado = var_estado;
    }

    public int getInt_estado() {
        return int_estado;
    }

    public void setInt_estado(int int_estado) {
        this.int_estado = int_estado;
    }
    public String toString(){
        return this.var_nombre+" "+var_apepaterno+" "+var_apematerno;
    } 
    
    
}
