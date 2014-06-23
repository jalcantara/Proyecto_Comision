/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.PeriodoCampania;
import java.sql.Date;
import java.util.ArrayList;
import modelo.BDPeriodo;

/**
 *
 * @author joseph
 */
public class BLPeriodo {

    public ArrayList<PeriodoCampania> get_periodo_all_byactivos() {
        ArrayList<PeriodoCampania> lista_periodo = null;
        try {
            BDPeriodo p = new BDPeriodo();
            lista_periodo = p.get_periodo_all_byactivos();
        } catch (Exception e) {
            System.out.println("error al obtener la lista de periodos :" + e.toString());
        }
        return lista_periodo;

    }

    public PeriodoCampania get_peridocampania_byagricultor(int idCliente, Date fechaRegistro) {
        PeriodoCampania pc = null;
        try {
            BDPeriodo p = new BDPeriodo();
            pc = p.get_peridocampania_byagricultor(idCliente, fechaRegistro);
        } catch (Exception e) {
            System.out.println("error periodocampania_bycliente :" + e.toString());
        }
        return pc;
    }
}
