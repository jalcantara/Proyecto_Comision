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
public class Telefono {
    int int_id;
    Agricultor cliente;
    int int_tipotelefono;
    String var_telefono;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public Agricultor getCliente() {
        return cliente;
    }

    public void setCliente(Agricultor cliente) {
        this.cliente = cliente;
    }

    public int getInt_tipotelefono() {
        return int_tipotelefono;
    }

    public void setInt_tipotelefono(int int_tipotelefono) {
        this.int_tipotelefono = int_tipotelefono;
    }

    public String getVar_telefono() {
        return var_telefono;
    }

    public void setVar_telefono(String var_telefono) {
        this.var_telefono = var_telefono;
    }
    
    
}
