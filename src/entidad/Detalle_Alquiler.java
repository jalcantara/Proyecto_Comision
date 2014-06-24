/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.sql.Timestamp;

/**
 *
 * @author joseph
 */
public class Detalle_Alquiler {
    private int iddetalle_alquiler;
    private int alquiler_id;
    private int material_id;
    private int int_cantidad;
    private double dec_monto;
    private Timestamp dat_fechfin;
    private Timestamp dat_fechinicio;
    
    
    
    public int getIddetalle_alquiler() {
        return iddetalle_alquiler;
    }

    public void setIddetalle_alquiler(int iddetalle_alquiler) {
        this.iddetalle_alquiler = iddetalle_alquiler;
    }

    public int getAlquiler_id() {
        return alquiler_id;
    }

    public void setAlquiler_id(int alquiler_id) {
        this.alquiler_id = alquiler_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getInt_cantidad() {
        return int_cantidad;
    }

    public void setInt_cantidad(int int_cantidad) {
        this.int_cantidad = int_cantidad;
    }

    public double getDec_monto() {
        return dec_monto;
    }

    public void setDec_monto(double dec_monto) {
        this.dec_monto = dec_monto;
    }

    public Timestamp getDat_fechfin() {
        return dat_fechfin;
    }

    public void setDat_fechfin(Timestamp dat_fechfin) {
        this.dat_fechfin = dat_fechfin;
    }

    public Timestamp getDat_fechinicio() {
        return dat_fechinicio;
    }

    public void setDat_fechinicio(Timestamp dat_fechinicio) {
        this.dat_fechinicio = dat_fechinicio;
    }
    
    
    
    
    
    
    
}
