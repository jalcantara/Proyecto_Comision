/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import controlador.BLPadron_Asistencia;
import entidad.Detalle_Padron_Asistencia;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Richard
 */
public class Excel_Reader {

    public String leer_excel(FileInputStream archivo_entrada, int tipo_asistencia) {
        String resultado = "";
        try {
            POIFSFileSystem fs = new POIFSFileSystem(archivo_entrada);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            if (tipo_asistencia == 1) {
                //ArrayList<Detalle_Padron_Asistencia> listdetpadasis=new ArrayList<Detalle_Padron_Asistencia>();
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    //Detalle_Padron_Asistencia d=new Detalle_Padron_Asistencia();

                    String nombre = row.getCell(2).getStringCellValue();
                    String horario = row.getCell(3).getStringCellValue();
                    String estado = row.getCell(6).getStringCellValue();
                    String valor_estado = "";
                    if (estado.equalsIgnoreCase("Registro normal")) {
                        valor_estado = "A";
                        //d.setVar_estado(valor_estado);
                    }
                    if (estado.equalsIgnoreCase("Invalido")) {
                        valor_estado = "T";
                        //d.setVar_estado(valor_estado);
                    }
                    String dni = "";
                    if (i == 0) {
                        dni = String.valueOf(row.getCell(0).getStringCellValue());
                    } else {
                        dni = String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 9).replace(".", "");
                        //d.setVar_dni(dni);
                        //listdetpadasis.add(d);
                    }

                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + dni + " | " + nombre + " | " + horario + " | " + estado + "\n";
                }
                /*BLPadron_Asistencia p=new BLPadron_Asistencia();
                 boolean result=false;
                 result=p.Registrar_PadronAsistencia(listdetpadasis, 1);
                 if(result==true){
                 JOptionPane.showMessageDialog(null, "Registro Correcto");
                 }*/
            } else {
                //ArrayList<Detalle_Padron_Asistencia> listdetpadasis=new ArrayList<Detalle_Padron_Asistencia>();
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    //Detalle_Padron_Asistencia d=new Detalle_Padron_Asistencia();

                    String nombre = row.getCell(2).getStringCellValue();
                    String horario = row.getCell(3).getStringCellValue();
                    String estado = row.getCell(6).getStringCellValue();
                    String valor_estado = "";
                    if (estado.equalsIgnoreCase("Registro normal")) {
                        valor_estado = "A";
                        //d.setVar_estado(valor_estado);
                    }
                    if (estado.equalsIgnoreCase("Invalido")) {
                        valor_estado = "T";
                        //d.setVar_estado(valor_estado);
                    }
                    String dni = "";
                    if (i == 0) {
                        dni = String.valueOf(row.getCell(0).getStringCellValue());
                    } else {
                        dni = String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 9).replace(".", "");
                        //d.setVar_dni(dni);
                        //listdetpadasis.add(d);
                    }

                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + dni + " | " + nombre + " | " + horario + " | " + estado + "\n";
                }
                /*BLPadron_Asistencia p=new BLPadron_Asistencia();
                 boolean result=false;
                 result=p.Registrar_PadronAsistencia(listdetpadasis, 1);
                 if(result==true){
                 JOptionPane.showMessageDialog(null, "Registro Correcto");
                 }*/
            }
            archivo_entrada.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return resultado;

    }

    public String leer_registrar_excel(FileInputStream archivo_entrada, int tipo_asistencia) {
        String resultado = "";
        try {
            POIFSFileSystem fs = new POIFSFileSystem(archivo_entrada);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            if (tipo_asistencia == 1) {
                ArrayList<Detalle_Padron_Asistencia> listdetpadasis = new ArrayList<Detalle_Padron_Asistencia>();
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    Detalle_Padron_Asistencia d = new Detalle_Padron_Asistencia();

                    String nombre = row.getCell(2).getStringCellValue();
                    String horario = row.getCell(3).getStringCellValue();
                    String estado = row.getCell(6).getStringCellValue();
                    String valor_estado = "";
                    if (estado.equalsIgnoreCase("Registro normal") || estado.equalsIgnoreCase("Invalido")) {
                        valor_estado = "A";
                        d.setVar_estado(valor_estado);
                    }
                    String dni = "";
                    if (i == 0) {
                        dni = String.valueOf(row.getCell(0).getStringCellValue());
                    } else {
                        dni = String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 9).replace(".", "");
                        d.setVar_dni(dni);
                        listdetpadasis.add(d);
                    }

                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + dni + " | " + nombre + " | " + horario + " | " + estado + "\n";
                }
                BLPadron_Asistencia p = new BLPadron_Asistencia();
                boolean result = false;
                result = p.Registrar_PadronAsistencia(listdetpadasis, 1);
                if (result == true) {
                    JOptionPane.showMessageDialog(null, "Registro Correcto");
                }
            } else {
                ArrayList<Detalle_Padron_Asistencia> listdetpadasis = new ArrayList<Detalle_Padron_Asistencia>();
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    Detalle_Padron_Asistencia d = new Detalle_Padron_Asistencia();

                    String nombre = row.getCell(2).getStringCellValue();
                    String horario = row.getCell(3).getStringCellValue();
                    String estado = row.getCell(6).getStringCellValue();
                    String valor_estado = "";
                    if (estado.equalsIgnoreCase("Registro normal") || estado.equalsIgnoreCase("Invalido")) {
                        valor_estado = "A";
                        d.setVar_estado(valor_estado);
                    }
                    String dni = "";
                    if (i == 0) {
                        dni = String.valueOf(row.getCell(0).getStringCellValue());
                    } else {
                        dni = String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 9).replace(".", "");
                        d.setVar_dni(dni);
                        listdetpadasis.add(d);
                    }

                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + dni + " | " + nombre + " | " + horario + " | " + estado + "\n";
                }
                BLPadron_Asistencia p = new BLPadron_Asistencia();
                boolean result = false;
                result = p.Registrar_PadronAsistencia(listdetpadasis, 1);
                if (result == true) {
                    JOptionPane.showMessageDialog(null, "Registro Correcto");
                }
            }
            archivo_entrada.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return resultado;

    }

}
