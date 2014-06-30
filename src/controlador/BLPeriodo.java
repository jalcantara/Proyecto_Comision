/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Periodo;
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
    public boolean Registrar(String desc,int mesinicio,int mesfin)throws Exception{
        boolean resultado=false;
        try {
            Periodo p=new Periodo();
            p.setVar_periodo(desc);
            p.setInt_mesInicio(mesinicio);
            p.setInt_mesFin(mesfin);
            BDPeriodo pe=new BDPeriodo();
            resultado=pe.Registrar(p);
        } 
        catch (Exception e) {
            System.out.println("Error de Registro-Controlador"+e.getMessage());
            e.printStackTrace();
            
        }
        return resultado;
    }
}
