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
public class Documento {
    private int int_id;
    private int pago_id;
    private String serie;
    private String numero;
    private int int_tipodocumento;
    private Date dat_fechemision;

    public int getInt_id() {
        return int_id;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getInt_tipodocumento() {
        return int_tipodocumento;
    }

    public void setInt_tipodocumento(int int_tipodocumento) {
        this.int_tipodocumento = int_tipodocumento;
    }

    public Date getDat_fechemision() {
        return dat_fechemision;
    }

    public void setDat_fechemision(Date dat_fechemision) {
        this.dat_fechemision = dat_fechemision;
    }

    public int getPago_id() {
        return pago_id;
    }

    public void setPago_id(int pago_id) {
        this.pago_id = pago_id;
    }
    
    
}
