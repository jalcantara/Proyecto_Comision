/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Usuario;
import java.sql.Date;
import modelo.BDUsuario;

/**
 *
 * @author Richard
 */
public class BLUsuario {

    public boolean Registrar(String usuario, String pass, String dni, String nombres, String apellidos,
            Date fechaNacimiento, String telefon, int idcargo, String direccion, String email) {
        boolean resultado = false;
        try {
            Usuario u = new Usuario();
            u.setVar_apellidos(apellidos);
            u.setVar_direccion(direccion);
            u.setVar_dni(dni);
            u.setVar_email(email);
            u.setVar_nombres(nombres);
            u.setVar_password(pass);
            u.setVar_telefono(telefon);
            u.setVar_user(pass);
            u.setDat_fechanacimiento(fechaNacimiento);
            u.setCargo_id(idcargo);
            resultado = new BDUsuario().Registrar(u);
        } catch (Exception e) {
            System.out.println("error en controlador al registrar usuario " + e.toString());
        }
        return resultado;
    }

}
