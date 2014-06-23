/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author Richard
 */
public class Funciones {

    public void soloDecimales(KeyEvent e) {
        String text = ((JTextField) e.getSource()).getText();
        if (text.equals("") && (e.getKeyChar() == '.')) {
            e.consume();
            return;
        }
        if ((text.indexOf(".") >= 0) && (e.getKeyChar() == '.')) {
            e.consume();
            return;
        }
        if (e.getKeyChar() != '.') {
            soloNumeros(e);
        }
    }

    public void soloNumeros(KeyEvent e) {
        char caracter = e.getKeyChar();
        if ((caracter > '9') || (caracter < '0')) {
            e.consume();
        }
    }

    public void soloLetras(KeyEvent e) {
        if (!String.valueOf(e.getKeyChar()).matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]|\\s")) {
            e.consume();
        }
    }

    public Date convertirFecha(String data) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = (Date) formatter.parse(data);
        } catch (ParseException ex) {
        }
        return date;
    }
    
    public Date convertirHora(String data) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        try {
            date = (Date) formatter.parse(data);
        } catch (ParseException ex) {
        }
        return date;
    }
    
    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null; 
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        }else{
            return false;
        }     
    }
    
    public Date sumarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.PM,1);
        cal.setTime(fch);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 30);
        cal.add(Calendar.DATE, dias);
        return cal.getTime();
    }
    
}