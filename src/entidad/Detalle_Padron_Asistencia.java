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
public class Detalle_Padron_Asistencia {
   private int int_id;
   private int padron_asistencia_id;
   private int cliente_id;
   private String var_estado;
   private String var_dni;
   
   
   
    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public int getPadron_asistencia_id() {
        return padron_asistencia_id;
    }

    public void setPadron_asistencia_id(int padron_asistencia_id) {
        this.padron_asistencia_id = padron_asistencia_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
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

    
    
    
}
