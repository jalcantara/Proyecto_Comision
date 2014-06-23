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
public class Traspaso {
    private int int_id;
    private int cliente_id;
    private int usuario_id;
    private int int_cantidadtraspaso;
    private int int_clienteAntiguo_id;

    public int getInt_clienteAntiguo_id() {
        return int_clienteAntiguo_id;
    }

    public void setInt_clienteAntiguo_id(int int_clienteAntiguo_id) {
        this.int_clienteAntiguo_id = int_clienteAntiguo_id;
    }  

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    

    public int getInt_cantidadtraspaso() {
        return int_cantidadtraspaso;
    }

    public void setInt_cantidadtraspaso(int int_cantidadtraspaso) {
        this.int_cantidadtraspaso = int_cantidadtraspaso;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    
    
}
