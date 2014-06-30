/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import java.io.FileInputStream;
import java.io.IOException;

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
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    String nombre = row.getCell(0).getStringCellValue();
                    String horario = row.getCell(1).getStringCellValue();
                    String estado = row.getCell(4).getStringCellValue();
                    if (estado.equalsIgnoreCase("Registro normal")) {
                        //Asistio
                    }
                    if (estado.equalsIgnoreCase("Invalido")) {
                        //Tardanza
                    }
                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + nombre + " | " + horario + " | " + estado + "\n";
                }
            } else {
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    //int num_emp = (int) row.getCell(0).getNumericCellValue();
                    String nombre = row.getCell(0).getStringCellValue();
                    String horario = row.getCell(1).getStringCellValue();
                    String estado = row.getCell(4).getStringCellValue();
                    if (estado.equalsIgnoreCase("Registro normal") && estado.equalsIgnoreCase("Invalido")) {
                        //Asistio
                    }
                    //Evaluar dentro de la base de datos los que no asistieron y generar multa
                    resultado = resultado + nombre + " | " + horario + " | " + estado + "\n";
                }
            }
            archivo_entrada.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return resultado;

    }

}
