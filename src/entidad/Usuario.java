/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.sql.Date;

/**
 *
 * @author joseph
 */
public class Usuario {

    private int int_id;
    private String var_user;
    private String var_password;
    private String var_estado;
    private String var_dni;
    private String var_nombres;
    private String var_apellidos;
    private int cargo_id;
    private String var_direccion;
    private String var_email;
    private String var_telefono;
    private Date dat_fechanacimiento;

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

    public int getCargo_id() {
        return cargo_id;
    }

    public void setCargo_id(int cargo_id) {
        this.cargo_id = cargo_id;
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

    public String getVar_telefono() {
        return var_telefono;
    }

    public void setVar_telefono(String var_telefono) {
        this.var_telefono = var_telefono;
    }

    public Date getDat_fechanacimiento() {
        return dat_fechanacimiento;
    }

    public void setDat_fechanacimiento(Date dat_fechanacimiento) {
        this.dat_fechanacimiento = dat_fechanacimiento;
    }

}
