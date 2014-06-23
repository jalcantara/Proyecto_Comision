/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.util.Date;

/**
 *
 * @author joseph
 */
public class Ticket {
    private int int_id;
    private String var_serie;
    private String var_numero;
    private Date dat_fechemision;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public String getVar_serie() {
        return var_serie;
    }

    public void setVar_serie(String var_serie) {
        this.var_serie = var_serie;
    }

    public String getVar_numero() {
        return var_numero;
    }

    public void setVar_numero(String var_numero) {
        this.var_numero = var_numero;
    }

    public Date getDat_fechemision() {
        return dat_fechemision;
    }

    public void setDat_fechemision(Date dat_fechemision) {
        this.dat_fechemision = dat_fechemision;
    }
    
    
}
