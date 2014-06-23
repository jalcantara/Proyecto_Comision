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
public class Constante {

    private int int_id;
    private int int_clase;
    private String var_descripcion;
    private int int_valor;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public int getInt_clase() {
        return int_clase;
    }

    public void setInt_clase(int int_clase) {
        this.int_clase = int_clase;
    }

    public String getVar_descripcion() {
        return var_descripcion;
    }

    public void setVar_descripcion(String var_descripcion) {
        this.var_descripcion = var_descripcion;
    }

    public int getInt_valor() {
        return int_valor;
    }

    public void setInt_valor(int int_valor) {
        this.int_valor = int_valor;
    }

    public String toString() {
        return this.var_descripcion;
    }

}
