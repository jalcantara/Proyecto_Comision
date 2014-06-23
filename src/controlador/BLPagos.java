/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Pago;
import java.util.ArrayList;
import modelo.BDPago;

/**
 *
 * @author joseph
 */
public class BLPagos {

    public ArrayList<Pago> get_pagos_bycliente(String dni, int id, int estado) {
        ArrayList<Pago> listPago = new ArrayList<>();
        try {
            BDPago pago = new BDPago();
            listPago = pago.get_pagos_bycliente(" (var_dni='" + dni + "' or int_id=" + id + ") and int_estado='" + estado + "' ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de Pagos_ByCliente" + e.getMessage());
        }
        return listPago;
    }

    public boolean RegistrarPagos(Pago p) {
        boolean resultado = false;
        try {
            BDPago pa = new BDPago();
            resultado = pa.RegistrarPagos(p);
        } catch (Exception e) {
            System.out.println("Error de Registrar Pagos - Controlador " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean AnularPago(int id) {
        boolean resultado = false;
        try {
            BDPago p = new BDPago();
            resultado = p.AnularPago(id);
        } catch (Exception e) {
            System.out.println("Error de Anular Pago - Controlador" + e.getMessage());
        }
        return resultado;

    }
}
