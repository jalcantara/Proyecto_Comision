/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import entidad.ListaUsuario;
import entidad.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import modelo.BDUsuario;

/**
 *
 * @author Richard
 */
class BLUsuario {

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
            u.setVar_user(usuario);
            u.setDat_fechanacimiento(fechaNacimiento);
            u.setCargo_id(idcargo);
            resultado = new BDUsuario().Registrar(u);
        } catch (Exception e) {
            System.out.println("error en controlador al registrar usuario " + e.toString());
        }
        return resultado;
    }
    public ArrayList<ListaUsuario> get_usuario_all(String user_filtro,int indice){
        ArrayList<ListaUsuario> listUsuario=new ArrayList<ListaUsuario>();
        try {
            BDUsuario u=new BDUsuario();
            if(indice == 0){
                listUsuario=u.get_usuario_all(" var_dni like '%" + user_filtro + "%' ");
            }
            if(indice == 1){
                listUsuario=u.get_usuario_all(" var_nombres like '%" + user_filtro + "%' ");
            }
            if(indice == 2){
                listUsuario=u.get_usuario_all(" var_apellidos like '%" + user_filtro + "%' ");
            }
            if(indice == 3){
                listUsuario=u.get_usuario_all(" var_user like '%" + user_filtro + "%' ");
            }
            
        } 
        catch (Exception e) {
            System.out.println("Error de Listado");
            e.printStackTrace();
        }
        return listUsuario;
        
    }
   
}
