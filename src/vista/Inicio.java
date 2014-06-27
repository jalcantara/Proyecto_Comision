package vista;

import controlador.BLAgricultor;
import controlador.BLAlquiler;
import controlador.BLCargo;
import controlador.BLComite;
import controlador.BLConstancia;
import controlador.BLConstante;
import controlador.BLCuenta;
import controlador.BLLateral;
import controlador.BLMaterial;
import controlador.BLMovimiento;
import controlador.BLPagos;
import controlador.BLPeriodo;
import controlador.BLTraspaso;
import entidad.Agricultor;
import entidad.Asignar_Costo;
import entidad.Cargo;
import entidad.Comite;
import entidad.Constancia;
import entidad.Constante;
import entidad.Cuenta;
import entidad.Detalle_Alquiler;
import entidad.Lateral;
import entidad.ListaAgricultorLateral;
import entidad.ListaAlquiler;
import entidad.ListaConstancia;
import entidad.ListaCuentaMonto;
import entidad.ListaTraspasos;
import entidad.ListaUsuario;
import entidad.Material;
import entidad.Pago;
import entidad.PeriodoCampania;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.BDAgricultor;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import utilitario.CloseDialogEscape;
import utilitario.Funciones;

/**
 *
 * @author joseph
 */
public class Inicio extends javax.swing.JFrame {

    //OBTENER LA DIMENSION DE TU PANTALLA
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension df = this.getSize();
    //VARIABLES GLOBALES
    int idComite_Constancia = 0;
    int idCliente_Constancia = 0;
    int idLateral_Constancia = 0;
    int idPeriodo_Constancia = 0;
    int idAgricultor_Traspaso = 0;
    int idLateral_Traspaso = 0;
    int idNuevoAgricultor_Traspaso = 0;
    int idEmpleado_Alquiler = 0;
    int idAgricultor_Alquiler = 0;
    int idPago = 0;
    int idAgri_Traspaso = 0;
    int idLat_Traspaso = 0;
    int idAgricultor_Edit = 0;
    double vcuota = 0;
    double vintangible = 0;
    double vseguro = 0;
    double vtarifa = 0;

    public Inicio() {
        initComponents();
        this.setDefaultCloseOperation(0);
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        //this.setSize(d.width - 100, d.height - 50);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        formatear_estructura_todas_tablas();

        //colocar icono a una ventana
    }
    /*METOO LIMPIAR TABLA*/

    private void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    /*FIN LIMPIAR TABLAS*/

    /*FORMATEO DE STRUCTURA DE TABLAS DEL SISTEMA*/
    private void formatear_estructura_todas_tablas() {
        //TABLA CONSTANCIA
        ((DefaultTableCellRenderer) jtBusqueda_Constancia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        jtBusqueda_Constancia.setRowHeight(22);
        jtBusqueda_Constancia.getColumnModel().getColumn(0).setMaxWidth(0);
        jtBusqueda_Constancia.getColumnModel().getColumn(0).setMinWidth(0);
        jtBusqueda_Constancia.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtBusqueda_Constancia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    /*FIN FORMATEO DE STRUCTURA DE TABLAS DEL SISTEMA*/

    /*CONSTANCIA*/
    private void buscar_constancia_byfiltro() {
        int contador = 0;
        ArrayList<String> lista = new ArrayList();
        String condicionFinal = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean fecha = chkFecha_Constancia.isSelected();
        boolean campania = chkCampania_Constancia.isSelected();
        boolean cliente = chkCliente_Constancia.isSelected();

        if (fecha == true) {
            lista.add(" ( date(dat_fechRegistro) between '" + sdf.format(txtFiltroInicio_Constancia.getDate()) + "' and '" + sdf.format(txtFiltroFin_Constancia.getDate()) + "' ) ");
            contador++;
        }
        if (campania == true) {
            lista.add(" ( int_periodo =" + ((PeriodoCampania) cboPeriodoFiltro_Constancia.getSelectedItem()).getPeriodo_id() + " )");
            contador++;
        }
        if (cliente == true) {
            lista.add(" ( cliente_id =" + ((Agricultor) cboAgricultorFiltro_Constancia.getSelectedItem()).getInt_id() + " )");
            contador++;
        }
        switch (contador) {
            case 1:
                condicionFinal = lista.get(0);
                break;
            case 2:
                condicionFinal = lista.get(0) + " and " + lista.get(1);
                break;
            case 3:
                condicionFinal = lista.get(0) + " and " + lista.get(1) + " and " + lista.get(2);
                break;
            case 4:
                condicionFinal = lista.get(0) + " and " + lista.get(1) + " and " + lista.get(2) + " and " + lista.get(3);
                break;
        }
        DefaultTableModel tempConstancia = (DefaultTableModel) jtBusqueda_Constancia.getModel();
        tempConstancia.setRowCount(0);
        for (ListaConstancia l : new BLConstancia().get_constancia_byfiltro(condicionFinal)) {
            Object datos[] = {l.getConstancia_id(), l.getVar_serie() + " - " + l.getVar_numero(),
                l.getVar_nombre() + " " + l.getVar_apepaterno() + " " + l.getVar_apematerno(),
                l.getVar_periodo(), l.getNroCamapania(), l.getVar_lateral(), l.getNroHectarea(), l.getDat_fechRegistro(), l.getTipoSiembra(), l.getFechaSiembra()
            };
            tempConstancia.addRow(datos);
        }

    }

    private void limpiarFomulario_Constancia() {
        idPeriodo_Constancia = 0;
        idCliente_Constancia = 0;
        idComite_Constancia = 0;
        idLateral_Constancia = 0;
        txtFecha_Constancia.setDate(new Date());
        txtComite_Constancia.setText("");
        txtCliente_Constancia.setText("");
        txtCampania_Constancia.setText("");
        txtLateral_Constancia.setText("");
        txtFiltroInicio_Constancia.setDate(new Date());
        txtFiltroFin_Constancia.setDate(new Date());
        txtFiltroInicio_Constancia.setDate(new Date());
        txtFiltroFin_Constancia.setDate(new Date());
        //cboPeriodoFiltro_Constancia.setSelectedIndex(0);
        cboAgricultorFiltro_Constancia.setSelectedIndex(0);
        txtMontoComision_Constancia.setText("");
        txtMontoJunta_Constancia.setText("");
        txtHectareas_Constancia.setText("");
        txtPeriodoRango_Constancia.setText("");
    }

    private void getcombo_tipocultivo_all() {
        cboTipoCultivo_Constancia.removeAllItems();
        for (Constante c : new BLConstante().get_tipocultivo_all(6)) {
            cboTipoCultivo_Constancia.addItem(c);

        }
        AutoCompleteDecorator.decorate(cboTipoCultivo_Constancia);
    }
    /*FIN CONSTANCIA*/

    /*LATERALES*/
    private void get_latreles_all() {
        cboLateral_Agricultor.removeAllItems();
        ArrayList<String> lista_lat = new BLAgricultor().get_latreles_all();
        cboLateral_Agricultor.addItem("");
        for (int i = 0; i < lista_lat.size(); i++) {
            cboLateral_Agricultor.addItem(lista_lat.get(i));
        }
        AutoCompleteDecorator.decorate(cboLateral_Agricultor);
    }

    private void get_sublatreles_all() {
        cboSubLateral_Agricultor.removeAllItems();
        ArrayList<String> lista_lat = new BLAgricultor().get_sublatreles_all();
        cboSubLateral_Agricultor.addItem("");
        for (int i = 0; i < lista_lat.size(); i++) {
            cboSubLateral_Agricultor.addItem(lista_lat.get(i));
        }
        AutoCompleteDecorator.decorate(cboSubLateral_Agricultor);
    }

    private void gettabla_lateral_byagricultoractivos(String palabra, int id) {
        DefaultTableModel temp = (DefaultTableModel) jtModalLateral_Constancia.getModel();
        DefaultTableModel temp1 = (DefaultTableModel) jtModalLateral_Traspaso.getModel();
        temp.setRowCount(0);
        temp1.setRowCount(0);
        for (Lateral l : new BLLateral().get_lateral_byactivocliente(palabra, id)) {
            Object[] datos = {l.getInt_id(), l.getVar_lateral(), l.getVar_sublateral(), l.getDec_conmedida(), l.getDec_sinmedida(), l.getInt_numhectareas()};
            temp.addRow(datos);
            temp1.addRow(datos);
        }
    }
    /*LATERALES*/

    /*PAGOS*/
    private void gettabla_verpagos_byAgricultor(String dni, int id, int estado) {
        DefaultTableModel temp = (DefaultTableModel) jtVerPagos.getModel();
        temp.setRowCount(0);
        for (Pago p : new BLPagos().get_pagos_bycliente(dni, id, estado)) {
            Object[] datos = {p.getInt_id(), p.getVar_cuenta(), p.getVar_descripcion(), p.getDat_fechregistro(),
                p.getDec_monto(), p.getVar_observacion(), p.getVar_boucherpago(), p.getInt_estado()};
            temp.addRow(datos);
        }
    }
    /*FIN PAGOS*/

    /*TRASPASO*/
    private void limpiarFomulario_Traspaso() {
        idAgricultor_Traspaso = 0;
        idLateral_Traspaso = 0;
        idNuevoAgricultor_Traspaso = 0;
        txtAgricultor_Traspaso.setText("");
        txtLateralCliente_Traspaso.setText("");
        txtSubLateralAgricultor_Traspo.setText("");
        txtNroHectares_Traspaso.setText("");
        txtNuevoAgricultor_Traspaso.setText("");
        txtCantidadHectaria_Traspaso.setText("");
        txtNuevoLateral_Traspaso.setText("");
        txtNuevoSubLateral_Traspaso.setText("");
        txtNuevoConMedida_Traspaso.setText("");
        txtNuevoSinMedida_Traspaso.setText("");
        txtObservacion_Traspaso.setText("");
        txtNumDocumento_Traspaso.setText("");
        //cboAntiguoAgricultor_Traspaso.setSelectedIndex(0);
        //cboNuevoAgricultor_Traspaso.setSelectedIndex(0);
    }
    /*FIN TRASPASO*/

    //*ALQUILER*/
    private void limpiarFomulario_Alquiler() {
        idEmpleado_Alquiler = 0;
        idAgricultor_Alquiler = 0;
        txtEmpleadoAgricultor_Alquiler.setText("");
        txtAgricultor_Alquiler.setText("");
        txtFechaDesde_Alquiler.setDate(new Date());
        txtFechaHasta_Alquiler.setDate(new Date());
        txtCantidad_Alquiler.setValue(1);
        //cboTipoMaterial_Alquiler.setSelectedIndex(0);
    }

    private void gettabla_agricultor_alquiler_byActivos(String palabra) {
        DefaultTableModel temp = (DefaultTableModel) jtModalAgricultor_Alquiler.getModel();
        temp.setRowCount(0);
        for (Agricultor c : new BLAgricultor().get_agricultores_byActivos(palabra)) {
            Object[] datos = {c.getInt_id(), c.getVar_nombre() + ' ' + c.getVar_apepaterno() + ' ' + c.getVar_apematerno()};
            temp.addRow(datos);
        }
    }
    /*FIN ALQUILER*/

    /*COMITE*/
    private void gettabla_comite_byActivos(String palabra) {
        DefaultTableModel temp = (DefaultTableModel) jtModalComite_Constancia.getModel();
        DefaultTableModel temp1 = (DefaultTableModel) jtComite_Administracion.getModel();
        temp.setRowCount(0);
        temp1.setRowCount(0);
        for (Comite c : new BLComite().get_comite_byActivos(palabra)) {
            Object[] datos = {c.getInt_id(), c.getVar_nombre()};
            Object[] datos1 = {c.getVar_nombre(), c.getVar_estado()};
            temp.addRow(datos);
            temp1.addRow(datos1);
        }
    }
    /*FIN COMITE*/
    /*CUENTA*/

    private void gettabla_asignacioncosto_cuenta_all() {
        DefaultTableModel temp = (DefaultTableModel) jtAsignarCosto_Cuentas.getModel();
        temp.setRowCount(0);
        for (Asignar_Costo c : new BLCuenta().get_asignarcosto_cuenta_all()) {
            Object[] datos = {c.getCuenta_id(), c.getVar_nombre(), c.getDec_monto(), c.getVar_concepto()};
            temp.addRow(datos);
        }
    }

    private void limpiarFomulario_Cuenta() {
        txtCodigo_Cuenta.setText("");
        txtNumCuenta_Registrar.setText("");
        txtNombre_Cuentas.setText("");
    }

    private void limpiarFomulario_AsignacionCosto_Cuenta() {
        txtMonto_AsignarCuenta.setText("");
        txtConcepto_AsignarCosto.setText("");
        cboCuentas_AsignarCostos.setSelectedIndex(0);
    }

    private void gettabla_cuenta_all(String palabra) {
        cboCuentas_AsignarCostos.removeAllItems();
        DefaultTableModel temp = (DefaultTableModel) jtCuentas.getModel();
        temp.setRowCount(0);
        for (Cuenta c : new BLCuenta().get_cuenta_all(palabra)) {
            Object[] datos = {c.getVar_codigo(), c.getVar_nombre(), c.getVar_numcuenta()};
            temp.addRow(datos);
            cboCuentas_AsignarCostos.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboCuentas_AsignarCostos);
    }
    /*FIN CUENTA*/


    /*AGRICULTOR*/
    private void gettabla_agricultor_constancia_byActivos(String palabra) {
        DefaultTableModel temp = (DefaultTableModel) jtModalAgricultor_Constancia.getModel();
        temp.setRowCount(0);
        for (Agricultor c : new BLAgricultor().get_agricultores_byActivos(palabra)) {
            Object[] datos = {c.getInt_id(), c.getVar_nombre() + ' ' + c.getVar_apepaterno() + ' ' + c.getVar_apematerno()};
            temp.addRow(datos);
        }
    }

    private void getcombo_cliente_all() {
        cboAgricultorFiltro_Constancia.removeAllItems();
        cboFiltroAgricultor_VerPagos.removeAllItems();
        cboAgricultor_Alquiler.removeAllItems();
        for (Agricultor c : new BLAgricultor().get_agricultores_byActivos("")) {
            cboAgricultorFiltro_Constancia.addItem(c);
            cboFiltroAgricultor_VerPagos.addItem(c);
            cboAgricultor_Alquiler.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboAgricultorFiltro_Constancia);
        AutoCompleteDecorator.decorate(cboFiltroAgricultor_VerPagos);
        AutoCompleteDecorator.decorate(cboAgricultor_Alquiler);

        DefaultTableModel temp = (DefaultTableModel) jtModalAgricultor_Alquiler.getModel();
        temp.setRowCount(0);
        for (Agricultor a : new BLAgricultor().get_agricultores_byActivos("")) {
            Object[] datos = {a.getInt_id(), a.getVar_nombre() + ' ' + a.getVar_apepaterno() + ' ' + a.getVar_apematerno()};
            temp.addRow(datos);
        }
    }

    private void gettabla_agricultor_all(String condicion, int indicecombo) {
        DefaultTableModel temp = (DefaultTableModel) jtAgricultor.getModel();
        temp.setRowCount(0);
        for (Agricultor a : new BLAgricultor().get_agricultor_all(condicion, indicecombo)) {
            Object[] datos = {a.getInt_id(), a.getVar_dni(), a.getVar_nombre() + ' ' + a.getVar_apepaterno(),
                a.getVar_telefono() + '/' + a.getVar_celular(), a.getVar_direccion(), a.getNumLaterales(),
                a.getInt_numhectareas()};
            temp.addRow(datos);
        }
    }

    private void get_agricultores_byActivos(String palabra) {
        DefaultTableModel temp = (DefaultTableModel) jtModalAgricultor_Constancia.getModel();
        DefaultTableModel temp1 = (DefaultTableModel) jtModalAgricultor_Traspaso.getModel();
        DefaultTableModel temp2 = (DefaultTableModel) jtModalAgricultorNuevo_Traspaso.getModel();

        temp.setRowCount(0);
        temp1.setRowCount(0);
        temp2.setRowCount(0);
        for (Agricultor c : new BLAgricultor().get_agricultores_byActivos(palabra)) {
            Object[] datos = {c.getInt_id(), c.getVar_nombre() + ' ' + c.getVar_apepaterno() + ' ' + c.getVar_apematerno()};
            temp.addRow(datos);
            temp1.addRow(datos);
            temp2.addRow(datos);
        }
    }

    private void getcombo_agricultor_antiguos() {
        cboAntiguoAgricultor_Traspaso.removeAllItems();
        for (Agricultor c : new BLAgricultor().get_agricultores_antiguos()) {
            cboAntiguoAgricultor_Traspaso.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboAntiguoAgricultor_Traspaso);

    }

    private void getcombo_agricultor_nuevos() {
        cboNuevoAgricultor_Traspaso.removeAllItems();
        for (Agricultor c : new BLAgricultor().get_agricultores_nuevos()) {
            cboNuevoAgricultor_Traspaso.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboNuevoAgricultor_Traspaso);

    }
    /*FIN AGRICULTOR*/

    /*PERIODO*/
    private void getcombo_periodo_all() {
        cboPeriodoFiltro_Constancia.removeAllItems();
        for (PeriodoCampania pc : new BLPeriodo().get_periodo_all_byactivos()) {
            cboPeriodoFiltro_Constancia.addItem(pc);
        }
        AutoCompleteDecorator.decorate(cboPeriodoFiltro_Constancia);
    }
    /*FIN PERIODO*/

    /*TRASPASO*/
    private void gettabla_traspaso_byclientenuevoantiguo(String condicion) {
        DefaultTableModel temp = (DefaultTableModel) jtTraspaso.getModel();
        temp.setRowCount(0);
        for (ListaTraspasos t : new BLTraspaso().get_cliente_all_byclientenuevoantiguo(condicion)) {
            Object[] datos = {t.getInt_id_traspaso(),
                t.getVar_nombre_antiguo() + ' ' + t.getVar_apepaterno_antiguo() + ' ' + t.getVar_apematerno_antiguo(),
                t.getVar_nombre_nuevo() + ' ' + t.getVar_apepaterno_nuevo() + ' ' + t.getVar_apematerno_nuevo(),
                t.getInt_cantidadtraspaso(), t.getVar_lateral(), t.getVar_sublateral(),
                t.getDec_conmedida(), t.getDec_sinmedida()};
            temp.addRow(datos);
        }
    }
    /*FIN TRASPASO*/

    /*MOVIMIENTO*/
    private void limpiarFomulario_Movimiento() {
        txtFecha_Movimiento.setDate(new Date());
        txtNroComprobante_Movimiento.setText("");
        txtCantidad_Movimientos.setValue(1);
        txtMonto_Movimiento.setText("");
        txtConcepto_Movimiento.setText("");
    }

    private void getcombo_tipodocumento_all() {
        cboTipoComprobante_Movimiento.removeAllItems();
        for (Constante c : new BLConstante().get_constante_all_byClase(4)) {
            cboTipoComprobante_Movimiento.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboTipoComprobante_Movimiento);
    }

    private void getcombo_tipooperacion_all() {
        cboTipoOperacion_Movimiento.removeAllItems();
        for (Constante c : new BLConstante().get_constante_all_byClase(5)) {
            cboTipoOperacion_Movimiento.addItem(c);
        }
        AutoCompleteDecorator.decorate(cboTipoOperacion_Movimiento);
    }
    /*FIN MOVIMIENTO*/

    /*MATERIAL*/
    private void getcombo_material_all() {
        cboTipoMaterial_Alquiler.removeAllItems();
        for (Material m : new BLMaterial().get_material_all()) {
            cboTipoMaterial_Alquiler.addItem(m);
        }
        AutoCompleteDecorator.decorate(cboTipoMaterial_Alquiler);

    }
    /*FIN MATERIAL*/
    /*USUARIO*/

    private void limpiarFomulario_Usuario() {
        txtID_Usuario.setText("");
        txtpass_usuario.setText("");
        txtdni_usuario.setText("");
        txtnombres_usuario.setText("");
        txtapellidos_usuario.setText("");
        txtTeleCelular_Usuario.setText("");
        //cboCargo_Usuario.setSelectedIndex(0);
        txtDireccion_Usuario.setText("");
        txtEmail_Usuario.setText("");
        txtFechaNacimiento_Usuario.setDate(new Date());
    }
    /*FIN USUARIO*/

    /*CARGO*/
    private void getcombo_cargo_all(String condicion) {
        DefaultTableModel temp = (DefaultTableModel) jtCargos_Administracion.getModel();
        temp.setRowCount(0);
        cboCargo_Usuario.removeAllItems();
        for (Cargo c : new BLCargo().get_cargo_all(condicion)) {
            cboCargo_Usuario.addItem(c);
            Object[] datos = {c.getVar_descripcion(), c.getVar_estado()};
            temp.addRow(datos);
        }
        //AutoCompleteDecorator.decorate(cboTipoOperacion_Movimiento);
    }
    /*FIN CARGO*/
    /*Usuario*/

    private void gettabla_usuario_byfiltro(String filtro, int indice) {
        DefaultTableModel temp = (DefaultTableModel) jtLista_Usuario.getModel();
        temp.setRowCount(0);
        for (ListaUsuario u : new BLUsuario().get_usuario_all(filtro, indice)) {
            Object[] datos = {u.getInt_id(), u.getVar_user(), u.getVar_nombres() + ' ' + u.getVar_apellidos(),
                u.getVar_dni(), u.getVar_telefono(), u.getVar_descripcion()};
            temp.addRow(datos);
        }
    }

    /*FIN USUARIO*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jifConstancia = new javax.swing.JInternalFrame();
        jpConstancia_Registro = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtComite_Constancia = new javax.swing.JTextField();
        txtCliente_Constancia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFecha_Constancia = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtLateral_Constancia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHectareas_Constancia = new javax.swing.JTextField();
        btn_ModalComite_Constancia = new javax.swing.JButton();
        btn_ModalCliente_Constancia = new javax.swing.JButton();
        btn_ModalLateral_Constancia = new javax.swing.JButton();
        txtPeriodoRango_Constancia = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtCampania_Constancia = new javax.swing.JTextField();
        rbAlmacigo_Constancia = new javax.swing.JRadioButton();
        txtFechaAlmacigo_constancia = new com.toedter.calendar.JDateChooser();
        rbBoleo_Constancia = new javax.swing.JRadioButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        cboTipoCultivo_Constancia = new org.jdesktop.swingx.JXComboBox();
        jLabel60 = new javax.swing.JLabel();
        txtMontoComision_Constancia = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txtMontoJunta_Constancia = new javax.swing.JTextField();
        btn_Cancelar_Constancia = new javax.swing.JButton();
        btn_Guardar_Constancia = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtFiltroInicio_Constancia = new com.toedter.calendar.JDateChooser();
        txtFiltroFin_Constancia = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        chkFecha_Constancia = new javax.swing.JCheckBox();
        chkCampania_Constancia = new javax.swing.JCheckBox();
        chkCliente_Constancia = new javax.swing.JCheckBox();
        cboPeriodoFiltro_Constancia = new org.jdesktop.swingx.JXComboBox();
        btnBuscarFiltro_Constancia = new javax.swing.JButton();
        cboAgricultorFiltro_Constancia = new org.jdesktop.swingx.JXComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtBusqueda_Constancia = new javax.swing.JTable();
        jLabel81 = new javax.swing.JLabel();
        jifTraspaso = new javax.swing.JInternalFrame();
        jpTraspaso = new javax.swing.JPanel();
        jtbTraspaso = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTraspaso = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        chkAntiguoDuenio_Agricultor = new javax.swing.JCheckBox();
        btn_Buscar_Traspaso = new javax.swing.JButton();
        chkAntiguoNuevo_Agricultor = new javax.swing.JCheckBox();
        cboAntiguoAgricultor_Traspaso = new org.jdesktop.swingx.JXComboBox();
        cboNuevoAgricultor_Traspaso = new org.jdesktop.swingx.JXComboBox();
        jLabel83 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtAgricultor_Traspaso = new javax.swing.JTextField();
        btn_Traspaso_ModalAgricultor = new javax.swing.JButton();
        txtLateralCliente_Traspaso = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSubLateralAgricultor_Traspo = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtNroHectares_Traspaso = new javax.swing.JTextField();
        btn_Traspaso_ModalLateral = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNuevoAgricultor_Traspaso = new javax.swing.JTextField();
        txtCantidadHectaria_Traspaso = new javax.swing.JTextField();
        btn_Traspaso_ModalNuevoAgricultor = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNuevoLateral_Traspaso = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtNuevoConMedida_Traspaso = new javax.swing.JTextField();
        txtNuevoSinMedida_Traspaso = new javax.swing.JTextField();
        txtNuevoSubLateral_Traspaso = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtObservacion_Traspaso = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        txtNumDocumento_Traspaso = new javax.swing.JTextField();
        btn_Cancelar_Traspaso = new javax.swing.JButton();
        btn_Guardar_Traspaso = new javax.swing.JButton();
        jifVerPagos = new javax.swing.JInternalFrame();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtVerPagos = new javax.swing.JTable();
        jLabel84 = new javax.swing.JLabel();
        jpVerPagos = new javax.swing.JPanel();
        cboFiltroAgricultor_VerPagos = new org.jdesktop.swingx.JXComboBox();
        txtFiltroDni_VerPagos = new javax.swing.JTextField();
        btn_buscar_pagos = new javax.swing.JButton();
        btn_Imprimir_pagos = new javax.swing.JButton();
        jrbDni_VerPagos = new javax.swing.JRadioButton();
        jrbAgricultor_VerPagos = new javax.swing.JRadioButton();
        jLabel86 = new javax.swing.JLabel();
        cboEstado_VerPagos = new javax.swing.JComboBox();
        jifIngresarAlquiler = new javax.swing.JInternalFrame();
        jifRegistrarAlquiler = new javax.swing.JPanel();
        jtbAlquiler = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtLista_Alquileres = new javax.swing.JTable();
        jLabel82 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        chkFiltroFecha_Alquiler = new javax.swing.JCheckBox();
        txtFechaInicio_Alquiler = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        txtFechaFin_Alquiler = new com.toedter.calendar.JDateChooser();
        chkFiltroAgricultor_Alquiler = new javax.swing.JCheckBox();
        cboAgricultor_Alquiler = new org.jdesktop.swingx.JXComboBox();
        btn_buscar_alquileres = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        cboTipoMaterial_Alquiler = new org.jdesktop.swingx.JXComboBox();
        jLabel28 = new javax.swing.JLabel();
        txtMonto_Alquiler = new org.jdesktop.swingx.JXTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCantidad_Alquiler = new com.toedter.components.JSpinField();
        jLabel23 = new javax.swing.JLabel();
        txtFechaDesde_Alquiler = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        txtFechaHasta_Alquiler = new com.toedter.calendar.JDateChooser();
        btnAgregarDet_Alquiler = new javax.swing.JButton();
        btnEliminarDet_Alquiler = new javax.swing.JButton();
        txtHoras_Alquiler = new com.toedter.components.JSpinField();
        jLabel42 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtAgricultor_Alquiler = new javax.swing.JTextField();
        txtEmpleadoAgricultor_Alquiler = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        jtbDetalle_Alquiler = new javax.swing.JTable();
        btn_Cancelar1 = new javax.swing.JButton();
        btn_Registrar1 = new javax.swing.JButton();
        btnBuscarAgricultor_Alquiler = new javax.swing.JButton();
        jifIngresarPadronAsistencia = new javax.swing.JInternalFrame();
        jifAgricultores = new javax.swing.JInternalFrame();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtAgricultor = new javax.swing.JTable();
        jLabel70 = new javax.swing.JLabel();
        txtFiltroAgricultor = new org.jdesktop.swingx.JXSearchField();
        cboFiltroAgricultor = new javax.swing.JComboBox();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel31 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtNombres_Agricultor = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtApePaterno_Agricultor = new javax.swing.JTextField();
        txtApeMaterno_Agricultor = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtEmail_Agricultor = new javax.swing.JTextField();
        txtDireccion_Agricultor = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        txtDNI_Agricultor = new org.jdesktop.swingx.JXSearchField();
        txtTelefono_Agricultor = new javax.swing.JTextField();
        txtCelular_Agricultor = new javax.swing.JTextField();
        cboSexo_Agricultor = new org.jdesktop.swingx.JXComboBox();
        jpLaterales = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        btnEliminar_DetLateales = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtDetalleLaterales_Agricultor = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        cboLateral_Agricultor = new org.jdesktop.swingx.JXComboBox();
        cboSubLateral_Agricultor = new org.jdesktop.swingx.JXComboBox();
        txtSinMedida_Agricultor = new javax.swing.JTextField();
        txtConMedida_Agricultor = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtNumHectareas_Agricultor = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jifPeriodos = new javax.swing.JInternalFrame();
        jPanel26 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jComboBox6 = new javax.swing.JComboBox();
        jXSearchField5 = new org.jdesktop.swingx.JXSearchField();
        jLabel78 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtNombre_Periodo = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jXComboBox2 = new org.jdesktop.swingx.JXComboBox();
        jXComboBox5 = new org.jdesktop.swingx.JXComboBox();
        jLabel79 = new javax.swing.JLabel();
        jifCargos = new javax.swing.JInternalFrame();
        jPanel19 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        txtDescripcionCargo = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jLabel75 = new javax.swing.JLabel();
        txtBuscarCargo = new org.jdesktop.swingx.JXSearchField();
        jScrollPane9 = new javax.swing.JScrollPane();
        jtCargos_Administracion = new javax.swing.JTable();
        jifDocumento = new javax.swing.JInternalFrame();
        jifInicioCierreCaja = new javax.swing.JInternalFrame();
        jpInicioCierre = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtMontoInicial_InicioCierreCaja = new org.jdesktop.swingx.JXTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jifMovimientos = new javax.swing.JInternalFrame();
        jpMovimiento = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        cboTipoOperacion_Movimiento = new org.jdesktop.swingx.JXComboBox();
        jLabel71 = new javax.swing.JLabel();
        txtMonto_Movimiento = new org.jdesktop.swingx.JXTextField();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtConcepto_Movimiento = new javax.swing.JTextArea();
        jLabel73 = new javax.swing.JLabel();
        txtFecha_Movimiento = new com.toedter.calendar.JDateChooser();
        btn_Cancelar_movimiento = new javax.swing.JButton();
        btn_Guardar_movimiento = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboTipoComprobante_Movimiento = new javax.swing.JComboBox();
        txtNroComprobante_Movimiento = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jXComboBox1 = new org.jdesktop.swingx.JXComboBox();
        jLabel66 = new javax.swing.JLabel();
        txtCantidad_Movimientos = new com.toedter.components.JSpinField();
        txtRucProveedor_Movimiento = new javax.swing.JTextField();
        jdConstanciaComite = new javax.swing.JDialog();
        jpBuscarComite = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtModalComite_Constancia = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        jtModalComite_Constancia = new javax.swing.JTable();
        jdConstanciaAgricultor = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtModalCliente_Constancia = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        jtModalAgricultor_Constancia = new javax.swing.JTable();
        jdConstanciaLateral = new javax.swing.JDialog();
        jpConstanciaLateral = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtModalLateral_Constancia = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        jtModalLateral_Constancia = new javax.swing.JTable();
        jdTraspasoAgricultor = new javax.swing.JDialog();
        jpBuscarAgricultor_Traspaso = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        txtModalAgricultor_Traspaso = new javax.swing.JTextField();
        jScrollPane16 = new javax.swing.JScrollPane();
        jtModalAgricultor_Traspaso = new javax.swing.JTable();
        jdTraspasoNuevoAgricultor = new javax.swing.JDialog();
        jpBuscarAgricultorNuevo_Traspaso = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        txtModalAgricultorNuevo_Traspaso = new javax.swing.JTextField();
        jScrollPane17 = new javax.swing.JScrollPane();
        jtModalAgricultorNuevo_Traspaso = new javax.swing.JTable();
        btnTipodeSembrio = new javax.swing.ButtonGroup();
        jdAlquilerAgricultor = new javax.swing.JDialog();
        jpBuscarAgricultor_Traspaso1 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        txtModalAgricultor_Alquiler = new javax.swing.JTextField();
        jScrollPane18 = new javax.swing.JScrollPane();
        jtModalAgricultor_Alquiler = new javax.swing.JTable();
        rb_group = new javax.swing.ButtonGroup();
        jpmAgricultor = new javax.swing.JPopupMenu();
        jmiEditar = new javax.swing.JMenuItem();
        jpmVerPagos = new javax.swing.JPopupMenu();
        jmip_Pagar = new javax.swing.JMenuItem();
        jmip_GenerarDocumento = new javax.swing.JMenuItem();
        jmip_Anular = new javax.swing.JMenuItem();
        jdPagar = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        txtFecha_RegistrarPagos = new com.toedter.calendar.JDateChooser();
        txtVoucher_RegistrarPago = new javax.swing.JTextField();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtObservacion_RegistrarPagos = new javax.swing.JTextArea();
        btn_Cancelar_Pago = new javax.swing.JButton();
        btn_Guardar_pago = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txtMonto_Pago = new javax.swing.JTextField();
        jdTraspasoLateral = new javax.swing.JDialog();
        jpTraspasoLateral = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        txtModalLateral_Traspaso = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        jtModalLateral_Traspaso = new javax.swing.JTable();
        jifCuentas = new javax.swing.JInternalFrame();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        btnGuardar5 = new javax.swing.JButton();
        btnCancelar5 = new javax.swing.JButton();
        jLabel98 = new javax.swing.JLabel();
        txtFiltroNombre_Cuenta2 = new org.jdesktop.swingx.JXSearchField();
        jComboBox8 = new javax.swing.JComboBox();
        jScrollPane23 = new javax.swing.JScrollPane();
        jtCuentas = new javax.swing.JTable();
        jLabel99 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        Codigo2 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        txtNombre_Cuentas = new javax.swing.JTextField();
        txtCodigo_Cuenta = new org.jdesktop.swingx.JXTextField();
        txtNumCuenta_Registrar = new org.jdesktop.swingx.JXTextField();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        Codigo3 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        txtMonto_AsignarCuenta = new org.jdesktop.swingx.JXTextField();
        jLabel104 = new javax.swing.JLabel();
        cboCuentas_AsignarCostos = new org.jdesktop.swingx.JXComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtConcepto_AsignarCosto = new javax.swing.JTextArea();
        btnGuardar6 = new javax.swing.JButton();
        btnCancelar6 = new javax.swing.JButton();
        jLabel105 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jtAsignarCosto_Cuentas = new javax.swing.JTable();
        jifUsuario = new javax.swing.JInternalFrame();
        jpUsurio = new javax.swing.JPanel();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel45 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jtLista_Usuario = new javax.swing.JTable();
        txtFiltro_Usuario = new org.jdesktop.swingx.JXSearchField();
        cboTipoFiltro_Usuario = new javax.swing.JComboBox();
        jLabel77 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        txtnombres_usuario = new javax.swing.JTextField();
        txtapellidos_usuario = new javax.swing.JTextField();
        txtID_Usuario = new javax.swing.JTextField();
        txtDireccion_Usuario = new javax.swing.JTextField();
        txtEmail_Usuario = new javax.swing.JTextField();
        txtTeleCelular_Usuario = new javax.swing.JTextField();
        txtFechaNacimiento_Usuario = new com.toedter.calendar.JDateChooser();
        cboCargo_Usuario = new org.jdesktop.swingx.JXComboBox();
        btn_Guardar_Usuario = new javax.swing.JButton();
        btn_Cancelar_Usuario = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        txtpass_usuario = new javax.swing.JPasswordField();
        txtdni_usuario = new javax.swing.JTextField();
        jifComites = new javax.swing.JInternalFrame();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        txtComite_Registrar = new javax.swing.JTextField();
        btnGuardar_Comite = new javax.swing.JButton();
        jScrollPane25 = new javax.swing.JScrollPane();
        jtComite_Administracion = new javax.swing.JTable();
        jLabel111 = new javax.swing.JLabel();
        txtFiltroComite_Administracion = new org.jdesktop.swingx.JXSearchField();
        jpInicio = new javax.swing.JPanel();
        jdeskpanInicio = new javax.swing.JDesktopPane();
        jmbPrincipal = new javax.swing.JMenuBar();
        jmInicio = new javax.swing.JMenu();
        jmiSalir = new javax.swing.JMenuItem();
        jmCaja = new javax.swing.JMenu();
        jmiInicioCierre = new javax.swing.JMenuItem();
        jmiMovimiento = new javax.swing.JMenuItem();
        jmConstancia = new javax.swing.JMenu();
        jmiRegistro = new javax.swing.JMenuItem();
        jmiTraspaso = new javax.swing.JMenuItem();
        jmPagos = new javax.swing.JMenu();
        jmiVerPagos = new javax.swing.JMenuItem();
        jmiAlquiler = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jpReportes = new javax.swing.JMenu();
        jmiPagos = new javax.swing.JMenuItem();
        jmiMovimientos = new javax.swing.JMenuItem();
        jmiClientes = new javax.swing.JMenuItem();
        jmAdministracion = new javax.swing.JMenu();
        jmiUsuario = new javax.swing.JMenuItem();
        jmiAgricultor = new javax.swing.JMenuItem();
        jmiCuentas = new javax.swing.JMenuItem();
        jmiPeriodo = new javax.swing.JMenuItem();
        jmiCargos = new javax.swing.JMenuItem();
        jmiComite = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        jifConstancia.setBackground(new java.awt.Color(225, 253, 203));
        jifConstancia.setClosable(true);
        jifConstancia.setIconifiable(true);
        jifConstancia.setResizable(true);
        jifConstancia.setTitle("CONSTANCIA");
        jifConstancia.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifConstancia.setOpaque(true);
        jifConstancia.setPreferredSize(new java.awt.Dimension(1014, 650));
        jifConstancia.setVisible(true);

        jpConstancia_Registro.setBackground(new java.awt.Color(225, 253, 203));
        jpConstancia_Registro.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Comite :");

        txtComite_Constancia.setEditable(false);
        txtComite_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCliente_Constancia.setEditable(false);
        txtCliente_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Usuario:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Periodo :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Fecha:");

        txtFecha_Constancia.setDateFormatString("dd 'de' MMMM 'de' yyyy");
        txtFecha_Constancia.setFocusable(false);
        txtFecha_Constancia.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFecha_ConstanciaPropertyChange(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Lateral :");

        txtLateral_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLateral_Constancia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLateral_Constancia.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("# Hectareas :");

        txtHectareas_Constancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtHectareas_Constancia.setForeground(new java.awt.Color(255, 153, 0));
        txtHectareas_Constancia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHectareas_Constancia.setText("0");
        txtHectareas_Constancia.setToolTipText("");
        txtHectareas_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHectareas_ConstanciaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHectareas_ConstanciaKeyTyped(evt);
            }
        });

        btn_ModalComite_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ModalComite_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_ModalComite_Constancia.setText("Buscar Comite");
        btn_ModalComite_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModalComite_ConstanciaActionPerformed(evt);
            }
        });

        btn_ModalCliente_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ModalCliente_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_ModalCliente_Constancia.setText("Buscar Usuario");
        btn_ModalCliente_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModalCliente_ConstanciaActionPerformed(evt);
            }
        });

        btn_ModalLateral_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ModalLateral_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_ModalLateral_Constancia.setText("Buscar Lateral");
        btn_ModalLateral_Constancia.setEnabled(false);
        btn_ModalLateral_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModalLateral_ConstanciaActionPerformed(evt);
            }
        });

        txtPeriodoRango_Constancia.setEditable(false);
        txtPeriodoRango_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPeriodoRango_Constancia.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("# Campaña:");

        txtCampania_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCampania_Constancia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCampania_Constancia.setEnabled(false);

        rbAlmacigo_Constancia.setBackground(new java.awt.Color(225, 253, 203));
        btnTipodeSembrio.add(rbAlmacigo_Constancia);
        rbAlmacigo_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbAlmacigo_Constancia.setSelected(true);
        rbAlmacigo_Constancia.setText("Almacigo");
        rbAlmacigo_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAlmacigo_ConstanciaActionPerformed(evt);
            }
        });

        txtFechaAlmacigo_constancia.setDateFormatString("dd 'de' MMMM 'de' yyyy");

        rbBoleo_Constancia.setBackground(new java.awt.Color(225, 253, 203));
        btnTipodeSembrio.add(rbBoleo_Constancia);
        rbBoleo_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbBoleo_Constancia.setText("Boleto");
        rbBoleo_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBoleo_ConstanciaActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Sembrio :");

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel89.setText("Tipo Cultivo:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Monto Comision:");

        txtMontoComision_Constancia.setEditable(false);
        txtMontoComision_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMontoComision_Constancia.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setText("Monto Junta:");

        txtMontoJunta_Constancia.setEditable(false);
        txtMontoJunta_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMontoJunta_Constancia.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jpConstancia_RegistroLayout = new javax.swing.GroupLayout(jpConstancia_Registro);
        jpConstancia_Registro.setLayout(jpConstancia_RegistroLayout);
        jpConstancia_RegistroLayout.setHorizontalGroup(
            jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8)
                            .addComponent(jLabel48))
                        .addGap(481, 481, 481)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCampania_Constancia, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTipoCultivo_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(txtPeriodoRango_Constancia, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                        .addComponent(rbAlmacigo_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFechaAlmacigo_constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbBoleo_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLateral_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtCliente_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(txtFecha_Constancia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btn_ModalComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn_ModalCliente_Constancia)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpConstancia_RegistroLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btn_ModalLateral_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtMontoComision_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5))
                        .addGap(40, 40, 40)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel89)
                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHectareas_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMontoJunta_Constancia)))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );
        jpConstancia_RegistroLayout.setVerticalGroup(
            jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFecha_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_ModalComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtPeriodoRango_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(13, 13, 13)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_ModalCliente_Constancia, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtCliente_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtCampania_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbBoleo_Constancia)
                            .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel89)
                                .addComponent(cboTipoCultivo_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFechaAlmacigo_constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel48)
                                .addComponent(rbAlmacigo_Constancia)))))
                .addGap(14, 14, 14)
                .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpConstancia_RegistroLayout.createSequentialGroup()
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLateral_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(btn_ModalLateral_Constancia)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addGroup(jpConstancia_RegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtMontoComision_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)
                            .addComponent(txtMontoJunta_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtHectareas_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        btn_Cancelar_Constancia.setBackground(new java.awt.Color(255, 102, 0));
        btn_Cancelar_Constancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btn_Cancelar_Constancia.setText("CANCELAR");
        btn_Cancelar_Constancia.setIconTextGap(8);

        btn_Guardar_Constancia.setBackground(new java.awt.Color(255, 102, 0));
        btn_Guardar_Constancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Guardar_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btn_Guardar_Constancia.setText("GUARDAR");
        btn_Guardar_Constancia.setIconTextGap(8);
        btn_Guardar_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_ConstanciaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("AL");

        chkFecha_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkFecha_Constancia.setSelected(true);
        chkFecha_Constancia.setText("Fecha :");
        chkFecha_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFecha_ConstanciaActionPerformed(evt);
            }
        });

        chkCampania_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkCampania_Constancia.setText("Periodo :");
        chkCampania_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCampania_ConstanciaActionPerformed(evt);
            }
        });

        chkCliente_Constancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkCliente_Constancia.setText("Usuario");
        chkCliente_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliente_ConstanciaActionPerformed(evt);
            }
        });

        cboPeriodoFiltro_Constancia.setEnabled(false);

        btnBuscarFiltro_Constancia.setBackground(new java.awt.Color(153, 255, 153));
        btnBuscarFiltro_Constancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscarFiltro_Constancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search-icon.png"))); // NOI18N
        btnBuscarFiltro_Constancia.setText("BUSCAR");
        btnBuscarFiltro_Constancia.setIconTextGap(8);
        btnBuscarFiltro_Constancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltro_ConstanciaActionPerformed(evt);
            }
        });

        cboAgricultorFiltro_Constancia.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkFecha_Constancia)
                    .addComponent(chkCampania_Constancia))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboPeriodoFiltro_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFiltroInicio_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chkCliente_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtFiltroFin_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cboAgricultorFiltro_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61)
                .addComponent(btnBuscarFiltro_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFiltroFin_Constancia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFecha_Constancia, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFiltroInicio_Constancia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCampania_Constancia)
                    .addComponent(cboPeriodoFiltro_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkCliente_Constancia)
                    .addComponent(cboAgricultorFiltro_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuscarFiltro_Constancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtBusqueda_Constancia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Constancia_id", "# Documento", "Usuario", "Periodo", "# Campaña", "Lateral", "# Hectareas", "Fecha Registro", "Tipo Siembra", "Fecha Siembra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtBusqueda_Constancia.setFocusable(false);
        jScrollPane1.setViewportView(jtBusqueda_Constancia);
        if (jtBusqueda_Constancia.getColumnModel().getColumnCount() > 0) {
            jtBusqueda_Constancia.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtBusqueda_Constancia.getColumnModel().getColumn(2).setPreferredWidth(180);
            jtBusqueda_Constancia.getColumnModel().getColumn(3).setPreferredWidth(30);
            jtBusqueda_Constancia.getColumnModel().getColumn(4).setPreferredWidth(30);
            jtBusqueda_Constancia.getColumnModel().getColumn(5).setPreferredWidth(30);
            jtBusqueda_Constancia.getColumnModel().getColumn(6).setPreferredWidth(30);
            jtBusqueda_Constancia.getColumnModel().getColumn(7).setPreferredWidth(40);
            jtBusqueda_Constancia.getColumnModel().getColumn(8).setPreferredWidth(40);
            jtBusqueda_Constancia.getColumnModel().getColumn(9).setPreferredWidth(40);
        }

        jLabel81.setBackground(new java.awt.Color(0, 0, 0));
        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setText("LISTA CONSTANCIAS");
        jLabel81.setOpaque(true);

        javax.swing.GroupLayout jifConstanciaLayout = new javax.swing.GroupLayout(jifConstancia.getContentPane());
        jifConstancia.getContentPane().setLayout(jifConstanciaLayout);
        jifConstanciaLayout.setHorizontalGroup(
            jifConstanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifConstanciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jifConstanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifConstanciaLayout.createSequentialGroup()
                        .addComponent(jpConstancia_Registro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jifConstanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Guardar_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Cancelar_Constancia)))
                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jifConstanciaLayout.setVerticalGroup(
            jifConstanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifConstanciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jifConstanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpConstancia_Registro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jifConstanciaLayout.createSequentialGroup()
                        .addComponent(btn_Guardar_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancelar_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jifTraspaso.setClosable(true);
        jifTraspaso.setIconifiable(true);
        jifTraspaso.setResizable(true);
        jifTraspaso.setTitle("TRASPASO AREA CULTIVO");
        jifTraspaso.setToolTipText("");
        jifTraspaso.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifTraspaso.setVisible(true);

        jtbTraspaso.setBackground(new java.awt.Color(225, 253, 203));
        jtbTraspaso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtbTraspaso.setOpaque(true);

        jPanel5.setBackground(new java.awt.Color(225, 253, 203));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtTraspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Antiguo Dueño", "Nuevo Dueño ", "Cantidad Traspaso", "Lateral", "Sub Lateral"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtTraspaso);
        if (jtTraspaso.getColumnModel().getColumnCount() > 0) {
            jtTraspaso.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtTraspaso.getColumnModel().getColumn(1).setPreferredWidth(100);
            jtTraspaso.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtTraspaso.getColumnModel().getColumn(3).setPreferredWidth(30);
            jtTraspaso.getColumnModel().getColumn(4).setPreferredWidth(30);
            jtTraspaso.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkAntiguoDuenio_Agricultor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkAntiguoDuenio_Agricultor.setText(" Antiguo Dueño :");
        chkAntiguoDuenio_Agricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAntiguoDuenio_AgricultorActionPerformed(evt);
            }
        });

        btn_Buscar_Traspaso.setBackground(new java.awt.Color(204, 255, 204));
        btn_Buscar_Traspaso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Buscar_Traspaso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search-icon.png"))); // NOI18N
        btn_Buscar_Traspaso.setText("CONSULTAR");
        btn_Buscar_Traspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Buscar_TraspasoActionPerformed(evt);
            }
        });

        chkAntiguoNuevo_Agricultor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkAntiguoNuevo_Agricultor.setText(" Nuevo Dueño :");
        chkAntiguoNuevo_Agricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAntiguoNuevo_AgricultorActionPerformed(evt);
            }
        });

        cboAntiguoAgricultor_Traspaso.setEnabled(false);
        cboAntiguoAgricultor_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboNuevoAgricultor_Traspaso.setEnabled(false);
        cboNuevoAgricultor_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkAntiguoDuenio_Agricultor)
                    .addComponent(chkAntiguoNuevo_Agricultor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboAntiguoAgricultor_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addComponent(cboNuevoAgricultor_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Buscar_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkAntiguoDuenio_Agricultor)
                            .addComponent(cboAntiguoAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkAntiguoNuevo_Agricultor)
                            .addComponent(cboNuevoAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btn_Buscar_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jLabel83.setBackground(new java.awt.Color(0, 153, 153));
        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("LISTA DE TRASPASOS DE ÁREA DE CULTIVO");
        jLabel83.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbTraspaso.addTab("CONSULTAR   ", new javax.swing.ImageIcon(getClass().getResource("/recurso/Consultar.png")), jPanel5); // NOI18N

        jPanel6.setBackground(new java.awt.Color(225, 253, 203));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel3.setBackground(new java.awt.Color(225, 253, 203));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dueño Antiguo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Usuario:");

        txtAgricultor_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAgricultor_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAgricultor_Traspaso.setEnabled(false);

        btn_Traspaso_ModalAgricultor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_Traspaso_ModalAgricultor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_Traspaso_ModalAgricultor.setText("Buscar Usuario");
        btn_Traspaso_ModalAgricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Traspaso_ModalAgricultorActionPerformed(evt);
            }
        });

        txtLateralCliente_Traspaso.setEditable(false);
        txtLateralCliente_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLateralCliente_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Lateral:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Sub Lateral:");

        txtSubLateralAgricultor_Traspo.setEditable(false);
        txtSubLateralAgricultor_Traspo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSubLateralAgricultor_Traspo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel67.setText("# Hectareas :");

        txtNroHectares_Traspaso.setEditable(false);
        txtNroHectares_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNroHectares_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_Traspaso_ModalLateral.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_Traspaso_ModalLateral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_Traspaso_ModalLateral.setText("Buscar Lateral");
        btn_Traspaso_ModalLateral.setEnabled(false);
        btn_Traspaso_ModalLateral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Traspaso_ModalLateralActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtSubLateralAgricultor_Traspo, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLateralCliente_Traspaso)
                            .addComponent(txtAgricultor_Traspaso))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_Traspaso_ModalLateral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Traspaso_ModalAgricultor, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNroHectares_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_Traspaso_ModalAgricultor))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtLateralCliente_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Traspaso_ModalLateral))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNroHectares_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSubLateralAgricultor_Traspo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addGap(7, 7, 7))
        );

        jPanel4.setBackground(new java.awt.Color(225, 253, 203));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Traspaso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Cantidad Hectario a Traspaso:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Nuevo Usuario:");

        txtNuevoAgricultor_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNuevoAgricultor_Traspaso.setEnabled(false);

        txtCantidadHectaria_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCantidadHectaria_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidadHectaria_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadHectaria_TraspasoKeyTyped(evt);
            }
        });

        btn_Traspaso_ModalNuevoAgricultor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_Traspaso_ModalNuevoAgricultor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btn_Traspaso_ModalNuevoAgricultor.setText("Buscar Usuario");
        btn_Traspaso_ModalNuevoAgricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Traspaso_ModalNuevoAgricultorActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Lateral:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Sub Lateral:");

        txtNuevoLateral_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNuevoLateral_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevoLateral_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoLateral_TraspasoKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Con Medida:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Sin Medida:");

        txtNuevoConMedida_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNuevoConMedida_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevoConMedida_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNuevoConMedida_TraspasoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNuevoConMedida_TraspasoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoConMedida_TraspasoKeyTyped(evt);
            }
        });

        txtNuevoSinMedida_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNuevoSinMedida_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevoSinMedida_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoSinMedida_TraspasoKeyTyped(evt);
            }
        });

        txtNuevoSubLateral_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNuevoSubLateral_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevoSubLateral_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoSubLateral_TraspasoKeyTyped(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Observacion:");

        txtObservacion_Traspaso.setColumns(20);
        txtObservacion_Traspaso.setRows(5);
        jScrollPane10.setViewportView(txtObservacion_Traspaso);

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("N° Documento:");

        txtNumDocumento_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNumDocumento_Traspaso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumDocumento_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumDocumento_TraspasoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCantidadHectaria_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNuevoConMedida_Traspaso))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(46, 46, 46)
                                    .addComponent(txtNuevoLateral_Traspaso)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNuevoSinMedida_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNuevoSubLateral_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_Traspaso_ModalNuevoAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(18, 18, 18)
                        .addComponent(txtNumDocumento_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumDocumento_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNuevoAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Traspaso_ModalNuevoAgricultor))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCantidadHectaria_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoLateral_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(txtNuevoSubLateral_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNuevoConMedida_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtNuevoSinMedida_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_Cancelar_Traspaso.setBackground(new java.awt.Color(255, 102, 0));
        btn_Cancelar_Traspaso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar_Traspaso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btn_Cancelar_Traspaso.setText("CANCELAR");
        btn_Cancelar_Traspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_TraspasoActionPerformed(evt);
            }
        });

        btn_Guardar_Traspaso.setBackground(new java.awt.Color(255, 102, 0));
        btn_Guardar_Traspaso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Guardar_Traspaso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btn_Guardar_Traspaso.setText("GUARDAR");
        btn_Guardar_Traspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_TraspasoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Guardar_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Cancelar_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btn_Cancelar_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Guardar_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(153, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jtbTraspaso.addTab("REGISTRAR  ", new javax.swing.ImageIcon(getClass().getResource("/recurso/registro_2.png")), jPanel6); // NOI18N

        javax.swing.GroupLayout jpTraspasoLayout = new javax.swing.GroupLayout(jpTraspaso);
        jpTraspaso.setLayout(jpTraspasoLayout);
        jpTraspasoLayout.setHorizontalGroup(
            jpTraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTraspasoLayout.createSequentialGroup()
                .addComponent(jtbTraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpTraspasoLayout.setVerticalGroup(
            jpTraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTraspasoLayout.createSequentialGroup()
                .addComponent(jtbTraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jtbTraspaso.getAccessibleContext().setAccessibleName("CONSULTAR   ");

        javax.swing.GroupLayout jifTraspasoLayout = new javax.swing.GroupLayout(jifTraspaso.getContentPane());
        jifTraspaso.getContentPane().setLayout(jifTraspasoLayout);
        jifTraspasoLayout.setHorizontalGroup(
            jifTraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jifTraspasoLayout.setVerticalGroup(
            jifTraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jifVerPagos.setClosable(true);
        jifVerPagos.setIconifiable(true);
        jifVerPagos.setResizable(true);
        jifVerPagos.setTitle("VER PAGOS");
        jifVerPagos.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifVerPagos.setVisible(true);

        jPanel8.setBackground(new java.awt.Color(225, 253, 203));

        jtVerPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "N° Cuenta", "Descripción", "Fecha", "Monto", "Observación", "# Boucher", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtVerPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtVerPagosMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtVerPagosMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jtVerPagos);
        if (jtVerPagos.getColumnModel().getColumnCount() > 0) {
            jtVerPagos.getColumnModel().getColumn(1).setPreferredWidth(20);
            jtVerPagos.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtVerPagos.getColumnModel().getColumn(3).setPreferredWidth(30);
            jtVerPagos.getColumnModel().getColumn(4).setPreferredWidth(30);
            jtVerPagos.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtVerPagos.getColumnModel().getColumn(6).setPreferredWidth(30);
            jtVerPagos.getColumnModel().getColumn(7).setPreferredWidth(30);
        }

        jLabel84.setBackground(new java.awt.Color(0, 153, 153));
        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("VER PAGOS");
        jLabel84.setOpaque(true);

        jpVerPagos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cboFiltroAgricultor_VerPagos.setEnabled(false);
        cboFiltroAgricultor_VerPagos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtFiltroDni_VerPagos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFiltroDni_VerPagos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFiltroDni_VerPagos.setEnabled(false);
        txtFiltroDni_VerPagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroDni_VerPagosKeyTyped(evt);
            }
        });

        btn_buscar_pagos.setBackground(new java.awt.Color(204, 255, 204));
        btn_buscar_pagos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_buscar_pagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search-icon.png"))); // NOI18N
        btn_buscar_pagos.setText("BUSCAR");
        btn_buscar_pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_pagosActionPerformed(evt);
            }
        });

        btn_Imprimir_pagos.setBackground(new java.awt.Color(204, 255, 204));
        btn_Imprimir_pagos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Imprimir_pagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/print_1.png"))); // NOI18N
        btn_Imprimir_pagos.setText("IMPRIMIR");
        btn_Imprimir_pagos.setEnabled(false);

        rb_group.add(jrbDni_VerPagos);
        jrbDni_VerPagos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jrbDni_VerPagos.setLabel("DNI:");
        jrbDni_VerPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbDni_VerPagosActionPerformed(evt);
            }
        });

        rb_group.add(jrbAgricultor_VerPagos);
        jrbAgricultor_VerPagos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jrbAgricultor_VerPagos.setText("Usuario:");
        jrbAgricultor_VerPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAgricultor_VerPagosActionPerformed(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel86.setText("Estado:");

        cboEstado_VerPagos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Programado", "Cancelado", "Anulado" }));

        javax.swing.GroupLayout jpVerPagosLayout = new javax.swing.GroupLayout(jpVerPagos);
        jpVerPagos.setLayout(jpVerPagosLayout);
        jpVerPagosLayout.setHorizontalGroup(
            jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpVerPagosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbDni_VerPagos)
                    .addComponent(jrbAgricultor_VerPagos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFiltroDni_VerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpVerPagosLayout.createSequentialGroup()
                        .addComponent(cboFiltroAgricultor_VerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel86)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEstado_VerPagos, 0, 145, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(btn_buscar_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Imprimir_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpVerPagosLayout.setVerticalGroup(
            jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpVerPagosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_buscar_pagos, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpVerPagosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFiltroDni_VerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrbDni_VerPagos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboFiltroAgricultor_VerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrbAgricultor_VerPagos)
                            .addComponent(jLabel86)
                            .addComponent(cboEstado_VerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_Imprimir_pagos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpVerPagos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpVerPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jifVerPagosLayout = new javax.swing.GroupLayout(jifVerPagos.getContentPane());
        jifVerPagos.getContentPane().setLayout(jifVerPagosLayout);
        jifVerPagosLayout.setHorizontalGroup(
            jifVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifVerPagosLayout.setVerticalGroup(
            jifVerPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifIngresarAlquiler.setClosable(true);
        jifIngresarAlquiler.setIconifiable(true);
        jifIngresarAlquiler.setResizable(true);
        jifIngresarAlquiler.setTitle("ALQUILER");
        jifIngresarAlquiler.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifIngresarAlquiler.setVisible(true);

        jifRegistrarAlquiler.setBackground(new java.awt.Color(255, 255, 255));

        jtbAlquiler.setBackground(new java.awt.Color(225, 253, 203));
        jtbAlquiler.setOpaque(true);

        jPanel12.setBackground(new java.awt.Color(225, 253, 203));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtLista_Alquileres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Tipo Material", "Fecha Inicio", "Fecha Fin", "Cantidad", "Monto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtLista_Alquileres);
        if (jtLista_Alquileres.getColumnModel().getColumnCount() > 0) {
            jtLista_Alquileres.getColumnModel().getColumn(0).setPreferredWidth(150);
            jtLista_Alquileres.getColumnModel().getColumn(1).setPreferredWidth(60);
            jtLista_Alquileres.getColumnModel().getColumn(2).setPreferredWidth(40);
            jtLista_Alquileres.getColumnModel().getColumn(3).setPreferredWidth(40);
            jtLista_Alquileres.getColumnModel().getColumn(4).setPreferredWidth(40);
            jtLista_Alquileres.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        jLabel82.setBackground(new java.awt.Color(0, 153, 153));
        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setText("LISTA DE ALQUILERES");
        jLabel82.setOpaque(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkFiltroFecha_Alquiler.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkFiltroFecha_Alquiler.setText("Fecha:");
        chkFiltroFecha_Alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFiltroFecha_AlquilerActionPerformed(evt);
            }
        });

        txtFechaInicio_Alquiler.setDateFormatString("dd 'de' MMMM 'de' yyyy");
        txtFechaInicio_Alquiler.setEnabled(false);
        txtFechaInicio_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Al");

        txtFechaFin_Alquiler.setDateFormatString("dd 'de' MMMM 'de' yyyy");
        txtFechaFin_Alquiler.setEnabled(false);
        txtFechaFin_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chkFiltroAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkFiltroAgricultor_Alquiler.setText("Usuario:");
        chkFiltroAgricultor_Alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFiltroAgricultor_AlquilerActionPerformed(evt);
            }
        });

        cboAgricultor_Alquiler.setEnabled(false);
        cboAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_buscar_alquileres.setBackground(new java.awt.Color(204, 255, 204));
        btn_buscar_alquileres.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_buscar_alquileres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search-icon.png"))); // NOI18N
        btn_buscar_alquileres.setText("BUSCAR");
        btn_buscar_alquileres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_alquileresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkFiltroFecha_Alquiler)
                    .addComponent(chkFiltroAgricultor_Alquiler))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFechaInicio_Alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtFechaFin_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboAgricultor_Alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52)
                .addComponent(btn_buscar_alquileres, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFechaInicio_Alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(chkFiltroFecha_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFin_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkFiltroAgricultor_Alquiler)
                            .addComponent(cboAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_buscar_alquileres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbAlquiler.addTab("CONSULTAR   ", new javax.swing.ImageIcon(getClass().getResource("/recurso/Consultar.png")), jPanel12); // NOI18N

        jPanel13.setBackground(new java.awt.Color(225, 253, 203));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel11.setBackground(new java.awt.Color(225, 253, 203));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Material :");

        cboTipoMaterial_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Monto :");

        txtMonto_Alquiler.setForeground(new java.awt.Color(51, 153, 255));
        txtMonto_Alquiler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto_Alquiler.setDisabledTextColor(new java.awt.Color(51, 153, 255));
        txtMonto_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMonto_Alquiler.setPrompt("S/. 0.00");
        txtMonto_Alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonto_AlquilerKeyTyped(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Cantidad:");

        txtCantidad_Alquiler.setMaximum(100);
        txtCantidad_Alquiler.setMinimum(1);
        txtCantidad_Alquiler.setValue(1);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Desde:");

        txtFechaDesde_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Hasta:");

        txtFechaHasta_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnAgregarDet_Alquiler.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarDet_Alquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Create.png"))); // NOI18N
        btnAgregarDet_Alquiler.setText("Agregar");
        btnAgregarDet_Alquiler.setIconTextGap(8);
        btnAgregarDet_Alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDet_AlquilerActionPerformed(evt);
            }
        });

        btnEliminarDet_Alquiler.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarDet_Alquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Remove.png"))); // NOI18N
        btnEliminarDet_Alquiler.setText("Eliminar");
        btnEliminarDet_Alquiler.setEnabled(false);
        btnEliminarDet_Alquiler.setIconTextGap(8);
        btnEliminarDet_Alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDet_AlquilerActionPerformed(evt);
            }
        });

        txtHoras_Alquiler.setMaximum(100);
        txtHoras_Alquiler.setMinimum(1);
        txtHoras_Alquiler.setValue(1);

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Horas:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(txtFechaDesde_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFechaHasta_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnAgregarDet_Alquiler))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(cboTipoMaterial_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMonto_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoras_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminarDet_Alquiler))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCantidad_Alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(cboTipoMaterial_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28)
                        .addComponent(txtMonto_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addComponent(txtHoras_Alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaDesde_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaHasta_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarDet_Alquiler)
                        .addComponent(btnEliminarDet_Alquiler)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Trabajador:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Usuario:");

        txtAgricultor_Alquiler.setEditable(false);
        txtAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtEmpleadoAgricultor_Alquiler.setEditable(false);
        txtEmpleadoAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jtbDetalle_Alquiler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Material", "Cantidad", "Desde", "Hasta", "Horas", "Monto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jtbDetalle_Alquiler);
        if (jtbDetalle_Alquiler.getColumnModel().getColumnCount() > 0) {
            jtbDetalle_Alquiler.getColumnModel().getColumn(1).setPreferredWidth(100);
            jtbDetalle_Alquiler.getColumnModel().getColumn(2).setPreferredWidth(40);
            jtbDetalle_Alquiler.getColumnModel().getColumn(3).setPreferredWidth(40);
            jtbDetalle_Alquiler.getColumnModel().getColumn(4).setPreferredWidth(40);
            jtbDetalle_Alquiler.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

        btn_Cancelar1.setBackground(new java.awt.Color(255, 102, 0));
        btn_Cancelar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btn_Cancelar1.setText("CANCELAR");
        btn_Cancelar1.setIconTextGap(8);
        btn_Cancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar1ActionPerformed(evt);
            }
        });

        btn_Registrar1.setBackground(new java.awt.Color(255, 102, 0));
        btn_Registrar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Registrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btn_Registrar1.setText("GUARDAR");
        btn_Registrar1.setIconTextGap(8);
        btn_Registrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Registrar1ActionPerformed(evt);
            }
        });

        btnBuscarAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscarAgricultor_Alquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Search.png"))); // NOI18N
        btnBuscarAgricultor_Alquiler.setText("Buscar Usuario");
        btnBuscarAgricultor_Alquiler.setIconTextGap(8);
        btnBuscarAgricultor_Alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAgricultor_AlquilerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAgricultor_Alquiler)
                                    .addComponent(txtEmpleadoAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarAgricultor_Alquiler)
                                .addGap(73, 73, 73))
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane12))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Registrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_Cancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpleadoAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(btnBuscarAgricultor_Alquiler))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Cancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Registrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jtbAlquiler.addTab("REGISTRAR", new javax.swing.ImageIcon(getClass().getResource("/recurso/registro_2.png")), jPanel13); // NOI18N

        javax.swing.GroupLayout jifRegistrarAlquilerLayout = new javax.swing.GroupLayout(jifRegistrarAlquiler);
        jifRegistrarAlquiler.setLayout(jifRegistrarAlquilerLayout);
        jifRegistrarAlquilerLayout.setHorizontalGroup(
            jifRegistrarAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtbAlquiler)
        );
        jifRegistrarAlquilerLayout.setVerticalGroup(
            jifRegistrarAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifRegistrarAlquilerLayout.createSequentialGroup()
                .addComponent(jtbAlquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jifIngresarAlquilerLayout = new javax.swing.GroupLayout(jifIngresarAlquiler.getContentPane());
        jifIngresarAlquiler.getContentPane().setLayout(jifIngresarAlquilerLayout);
        jifIngresarAlquilerLayout.setHorizontalGroup(
            jifIngresarAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jifRegistrarAlquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifIngresarAlquilerLayout.setVerticalGroup(
            jifIngresarAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jifRegistrarAlquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifIngresarPadronAsistencia.setClosable(true);
        jifIngresarPadronAsistencia.setIconifiable(true);
        jifIngresarPadronAsistencia.setMaximizable(true);
        jifIngresarPadronAsistencia.setTitle("PADRON - ASISTENCIA");
        jifIngresarPadronAsistencia.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifIngresarPadronAsistencia.setVisible(true);

        javax.swing.GroupLayout jifIngresarPadronAsistenciaLayout = new javax.swing.GroupLayout(jifIngresarPadronAsistencia.getContentPane());
        jifIngresarPadronAsistencia.getContentPane().setLayout(jifIngresarPadronAsistenciaLayout);
        jifIngresarPadronAsistenciaLayout.setHorizontalGroup(
            jifIngresarPadronAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
        );
        jifIngresarPadronAsistenciaLayout.setVerticalGroup(
            jifIngresarPadronAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        jifAgricultores.setClosable(true);
        jifAgricultores.setIconifiable(true);
        jifAgricultores.setResizable(true);
        jifAgricultores.setTitle("USUARIO");
        jifAgricultores.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifAgricultores.setVisible(true);

        jTabbedPane4.setBackground(new java.awt.Color(225, 253, 203));
        jTabbedPane4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane4.setOpaque(true);

        jPanel17.setBackground(new java.awt.Color(195, 233, 164));
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtAgricultor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "DNI", "USUARIO", "TELÉFONO / CELULAR", "DIRECCIÓN", "# LATERALES", "N° HECTAREAS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtAgricultor.setToolTipText("");
        jtAgricultor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtAgricultorMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtAgricultorMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(jtAgricultor);
        if (jtAgricultor.getColumnModel().getColumnCount() > 0) {
            jtAgricultor.getColumnModel().getColumn(0).setPreferredWidth(10);
            jtAgricultor.getColumnModel().getColumn(1).setPreferredWidth(20);
            jtAgricultor.getColumnModel().getColumn(2).setPreferredWidth(140);
            jtAgricultor.getColumnModel().getColumn(3).setPreferredWidth(70);
            jtAgricultor.getColumnModel().getColumn(4).setPreferredWidth(140);
            jtAgricultor.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jLabel70.setBackground(new java.awt.Color(0, 153, 153));
        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("LISTA DE USUARIOS");
        jLabel70.setOpaque(true);

        txtFiltroAgricultor.setToolTipText("Buscar Usuario");
        txtFiltroAgricultor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFiltroAgricultor.setPrompt("Buscar Usuario");
        txtFiltroAgricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroAgricultorKeyReleased(evt);
            }
        });

        cboFiltroAgricultor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboFiltroAgricultor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "DNI" }));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                        .addComponent(txtFiltroAgricultor, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFiltroAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFiltroAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltroAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jTabbedPane4.addTab(" CONSULTAR    ", new javax.swing.ImageIcon(getClass().getResource("/recurso/Consultar.png")), jPanel17); // NOI18N

        jPanel18.setBackground(new java.awt.Color(225, 253, 203));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTabbedPane7.setBackground(new java.awt.Color(225, 253, 203));
        jTabbedPane7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jTabbedPane7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane7.setOpaque(true);

        jPanel31.setBackground(new java.awt.Color(225, 253, 203));
        jPanel31.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Nombres:");

        txtNombres_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombres_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombres_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombres_AgricultorKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Ape. Paterno:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Ape. Materno:");

        txtApePaterno_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtApePaterno_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApePaterno_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApePaterno_AgricultorKeyTyped(evt);
            }
        });

        txtApeMaterno_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtApeMaterno_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApeMaterno_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApeMaterno_AgricultorKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Direccion:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Email:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("DNI:");

        txtEmail_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmail_AgricultorKeyTyped(evt);
            }
        });

        txtDireccion_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDireccion_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccion_AgricultorKeyTyped(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Sexo:");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setText("Teléfono :");

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setText("Celular :");

        txtDNI_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDNI_Agricultor.setText("47197204");
        txtDNI_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDNI_Agricultor.setPrompt("ingrese D.N.I");
        txtDNI_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDNI_AgricultorKeyTyped(evt);
            }
        });

        txtTelefono_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTelefono_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono_AgricultorKeyTyped(evt);
            }
        });

        txtCelular_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCelular_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelular_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelular_AgricultorKeyTyped(evt);
            }
        });

        cboSexo_Agricultor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FEMENINO", "MASCULINO" }));
        cboSexo_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel35)
                    .addComponent(jLabel68)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmail_Agricultor)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                                .addComponent(txtDNI_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
                            .addComponent(txtApePaterno_Agricultor)
                            .addComponent(txtTelefono_Agricultor))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel69))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApeMaterno_Agricultor, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                            .addComponent(txtCelular_Agricultor)))
                    .addComponent(txtDireccion_Agricultor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombres_Agricultor)
                    .addComponent(cboSexo_Agricultor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtDNI_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtNombres_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtApePaterno_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtApeMaterno_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txtTelefono_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(txtCelular_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(cboSexo_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Datos Personales", jPanel31);

        jpLaterales.setBackground(new java.awt.Color(225, 253, 203));
        jpLaterales.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Lateral:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("Sub Lateral:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Con Medida:");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setText("Sin Medida:");

        btnEliminar_DetLateales.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminar_DetLateales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Remove.png"))); // NOI18N
        btnEliminar_DetLateales.setText("Eliminar");
        btnEliminar_DetLateales.setEnabled(false);
        btnEliminar_DetLateales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar_DetLatealesActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Create.png"))); // NOI18N
        jButton17.setText("Agregar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jtDetalleLaterales_Agricultor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Lateral", "Sub Lateral", "Sin Medida", "Con Medida", "N° Hectareas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jtDetalleLaterales_Agricultor);

        jLabel37.setBackground(new java.awt.Color(0, 153, 153));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("LISTA DE LATERALES");
        jLabel37.setOpaque(true);

        cboLateral_Agricultor.setEditable(true);
        cboLateral_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboSubLateral_Agricultor.setEditable(true);
        cboSubLateral_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSinMedida_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSinMedida_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSinMedida_Agricultor.setText("0.0");
        txtSinMedida_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSinMedida_AgricultorKeyTyped(evt);
            }
        });

        txtConMedida_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtConMedida_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtConMedida_Agricultor.setText("0.0");
        txtConMedida_Agricultor.setToolTipText("");
        txtConMedida_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConMedida_AgricultorKeyTyped(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("N° Hectareas");

        txtNumHectareas_Agricultor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNumHectareas_Agricultor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumHectareas_Agricultor.setText("0.0");
        txtNumHectareas_Agricultor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumHectareas_AgricultorKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpLateralesLayout = new javax.swing.GroupLayout(jpLaterales);
        jpLaterales.setLayout(jpLateralesLayout);
        jpLateralesLayout.setHorizontalGroup(
            jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLateralesLayout.createSequentialGroup()
                .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLateralesLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSubLateral_Agricultor, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(cboLateral_Agricultor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addGroup(jpLateralesLayout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(34, 34, 34)
                                .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtConMedida_Agricultor)
                                    .addComponent(txtSinMedida_Agricultor, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))))
                        .addGap(42, 42, 42)
                        .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpLateralesLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNumHectareas_Agricultor))
                            .addGroup(jpLateralesLayout.createSequentialGroup()
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar_DetLateales, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 76, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpLateralesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpLateralesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5)))
                .addContainerGap())
        );
        jpLateralesLayout.setVerticalGroup(
            jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLateralesLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel56)
                        .addComponent(txtNumHectareas_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel40)
                        .addComponent(cboLateral_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49)
                        .addComponent(txtSinMedida_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jpLateralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(cboSubLateral_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(btnEliminar_DetLateales)
                    .addComponent(txtConMedida_Agricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        jTabbedPane7.addTab("Laterales", jpLaterales);

        btnGuardar.setBackground(new java.awt.Color(255, 102, 0));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setIconTextGap(8);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 102, 0));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setIconTextGap(8);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane7)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("REGISTRAR  ", new javax.swing.ImageIcon(getClass().getResource("/recurso/agricultor.png")), jPanel18); // NOI18N

        javax.swing.GroupLayout jifAgricultoresLayout = new javax.swing.GroupLayout(jifAgricultores.getContentPane());
        jifAgricultores.getContentPane().setLayout(jifAgricultoresLayout);
        jifAgricultoresLayout.setHorizontalGroup(
            jifAgricultoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        jifAgricultoresLayout.setVerticalGroup(
            jifAgricultoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jifPeriodos.setClosable(true);
        jifPeriodos.setIconifiable(true);
        jifPeriodos.setResizable(true);
        jifPeriodos.setTitle("PERIODOS");
        jifPeriodos.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifPeriodos.setVisible(true);

        jPanel26.setBackground(new java.awt.Color(225, 253, 203));
        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        jButton2.setText("GUARDAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/cancelar.png"))); // NOI18N
        jButton1.setText("CANCELAR");

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Periodo", "Fecha Inicio", "Fecha Fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable8);

        jComboBox6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jXSearchField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jXSearchField5.setPrompt("Buscar Campañas");

        jLabel78.setBackground(new java.awt.Color(0, 153, 153));
        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("LISTA CAMPAÑAS");
        jLabel78.setOpaque(true);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtNombre_Periodo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre_Periodo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Nombre :");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setText("Mes Inicio :");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Mes Fin :");

        jXComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jXComboBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(42, 42, 42)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jXComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(txtNombre_Periodo))
                .addGap(27, 27, 27)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombre_Periodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel44))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jXComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel47))
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jXComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel79.setBackground(new java.awt.Color(225, 253, 203));
        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("REGISTRO DE PERIODO");
        jLabel79.setOpaque(true);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jXSearchField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXSearchField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jifPeriodosLayout = new javax.swing.GroupLayout(jifPeriodos.getContentPane());
        jifPeriodos.getContentPane().setLayout(jifPeriodosLayout);
        jifPeriodosLayout.setHorizontalGroup(
            jifPeriodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifPeriodosLayout.setVerticalGroup(
            jifPeriodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jifCargos.setClosable(true);
        jifCargos.setIconifiable(true);
        jifCargos.setResizable(true);
        jifCargos.setTitle("CARGOS");
        jifCargos.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifCargos.setVisible(true);

        jPanel19.setBackground(new java.awt.Color(225, 253, 203));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel74.setBackground(new java.awt.Color(225, 253, 203));
        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("REGISTRO DE CARGOS");
        jLabel74.setOpaque(true);

        txtDescripcionCargo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDescripcionCargo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescripcionCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionCargoKeyTyped(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("Descripción :");

        jButton19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        jButton19.setText("GUARDAR");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel75.setBackground(new java.awt.Color(0, 153, 153));
        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("LISTA DE CARGOS");
        jLabel75.setOpaque(true);

        txtBuscarCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCargoKeyReleased(evt);
            }
        });

        jtCargos_Administracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jtCargos_Administracion);
        if (jtCargos_Administracion.getColumnModel().getColumnCount() > 0) {
            jtCargos_Administracion.getColumnModel().getColumn(0).setPreferredWidth(150);
            jtCargos_Administracion.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(18, 18, 18)
                        .addComponent(txtDescripcionCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescripcionCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel51))
                    .addComponent(jButton19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jifCargosLayout = new javax.swing.GroupLayout(jifCargos.getContentPane());
        jifCargos.getContentPane().setLayout(jifCargosLayout);
        jifCargosLayout.setHorizontalGroup(
            jifCargosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifCargosLayout.setVerticalGroup(
            jifCargosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifDocumento.setClosable(true);
        jifDocumento.setIconifiable(true);
        jifDocumento.setResizable(true);
        jifDocumento.setTitle("DOCUMENTOS");
        jifDocumento.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifDocumento.setVisible(true);

        javax.swing.GroupLayout jifDocumentoLayout = new javax.swing.GroupLayout(jifDocumento.getContentPane());
        jifDocumento.getContentPane().setLayout(jifDocumentoLayout);
        jifDocumentoLayout.setHorizontalGroup(
            jifDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
        );
        jifDocumentoLayout.setVerticalGroup(
            jifDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        jifInicioCierreCaja.setClosable(true);
        jifInicioCierreCaja.setIconifiable(true);
        jifInicioCierreCaja.setResizable(true);
        jifInicioCierreCaja.setTitle("INICIO / CIERRE CAJA");
        jifInicioCierreCaja.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifInicioCierreCaja.setVisible(true);

        jpInicioCierre.setBackground(new java.awt.Color(225, 253, 203));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Monto Incial :");

        txtMontoInicial_InicioCierreCaja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoInicial_InicioCierreCaja.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtMontoInicial_InicioCierreCaja.setPrompt("S/. 0.00");
        txtMontoInicial_InicioCierreCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoInicial_InicioCierreCajaKeyTyped(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/money.png"))); // NOI18N
        jButton4.setText("INICIAR CAJA");
        jButton4.setIconTextGap(20);

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Exit.png"))); // NOI18N
        jButton5.setText("CERRAR CAJA");
        jButton5.setIconTextGap(20);

        javax.swing.GroupLayout jpInicioCierreLayout = new javax.swing.GroupLayout(jpInicioCierre);
        jpInicioCierre.setLayout(jpInicioCierreLayout);
        jpInicioCierreLayout.setHorizontalGroup(
            jpInicioCierreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInicioCierreLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jpInicioCierreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpInicioCierreLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(18, 18, 18)
                        .addComponent(txtMontoInicial_InicioCierreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jpInicioCierreLayout.setVerticalGroup(
            jpInicioCierreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInicioCierreLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jpInicioCierreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMontoInicial_InicioCierreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jifInicioCierreCajaLayout = new javax.swing.GroupLayout(jifInicioCierreCaja.getContentPane());
        jifInicioCierreCaja.getContentPane().setLayout(jifInicioCierreCajaLayout);
        jifInicioCierreCajaLayout.setHorizontalGroup(
            jifInicioCierreCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpInicioCierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifInicioCierreCajaLayout.setVerticalGroup(
            jifInicioCierreCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpInicioCierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifMovimientos.setClosable(true);
        jifMovimientos.setIconifiable(true);
        jifMovimientos.setResizable(true);
        jifMovimientos.setTitle("MOVIMIENTOS");
        jifMovimientos.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifMovimientos.setVisible(true);

        jpMovimiento.setBackground(new java.awt.Color(225, 253, 203));
        jpMovimiento.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setText("TIPO OPERACION :");

        cboTipoOperacion_Movimiento.setEditable(true);
        cboTipoOperacion_Movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setText("CANTIDAD :");

        txtMonto_Movimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto_Movimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMonto_Movimiento.setPrompt("S/. 0.00");
        txtMonto_Movimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonto_MovimientoKeyTyped(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setText("CONCEPTO :");

        txtConcepto_Movimiento.setColumns(20);
        txtConcepto_Movimiento.setRows(5);
        txtConcepto_Movimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConcepto_MovimientoKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(txtConcepto_Movimiento);

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setText("FECHA :");

        txtFecha_Movimiento.setDateFormatString("dd 'de' MMMM 'de' yyyy");
        txtFecha_Movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_Cancelar_movimiento.setBackground(new java.awt.Color(255, 51, 0));
        btn_Cancelar_movimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btn_Cancelar_movimiento.setText("CANCELAR");
        btn_Cancelar_movimiento.setIconTextGap(8);
        btn_Cancelar_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_movimientoActionPerformed(evt);
            }
        });

        btn_Guardar_movimiento.setBackground(new java.awt.Color(255, 51, 0));
        btn_Guardar_movimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Guardar_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btn_Guardar_movimiento.setText("GUARDAR");
        btn_Guardar_movimiento.setIconTextGap(8);
        btn_Guardar_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_movimientoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("TIPO COMPROBANTE :");

        cboTipoComprobante_Movimiento.setEditable(true);
        cboTipoComprobante_Movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNroComprobante_Movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNroComprobante_Movimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setText("PROVEEDOR:");

        jXComboBox1.setEditable(true);
        jXComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("MONTO :");

        txtRucProveedor_Movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRucProveedor_Movimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jpMovimientoLayout = new javax.swing.GroupLayout(jpMovimiento);
        jpMovimiento.setLayout(jpMovimientoLayout);
        jpMovimientoLayout.setHorizontalGroup(
            jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMovimientoLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMovimientoLayout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpMovimientoLayout.createSequentialGroup()
                                .addGap(269, 269, 269)
                                .addComponent(btn_Cancelar_movimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMovimientoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_Guardar_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(188, 188, 188))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMovimientoLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jpMovimientoLayout.createSequentialGroup()
                                        .addComponent(txtCantidad_Movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel66)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMonto_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane11)))))
                    .addGroup(jpMovimientoLayout.createSequentialGroup()
                        .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpMovimientoLayout.createSequentialGroup()
                                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel71)
                                    .addComponent(jLabel73))
                                .addGap(31, 31, 31)
                                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTipoOperacion_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFecha_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpMovimientoLayout.createSequentialGroup()
                                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel63))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jpMovimientoLayout.createSequentialGroup()
                                        .addComponent(cboTipoComprobante_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNroComprobante_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpMovimientoLayout.createSequentialGroup()
                                        .addComponent(jXComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRucProveedor_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(1, 1, 1)))
                .addGap(43, 43, 43))
        );
        jpMovimientoLayout.setVerticalGroup(
            jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMovimientoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addGap(18, 18, 18)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboTipoComprobante_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNroComprobante_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jXComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRucProveedor_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(cboTipoOperacion_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel71)
                        .addComponent(txtMonto_Movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel66))
                    .addComponent(txtCantidad_Movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Guardar_movimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Cancelar_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jifMovimientosLayout = new javax.swing.GroupLayout(jifMovimientos.getContentPane());
        jifMovimientos.getContentPane().setLayout(jifMovimientosLayout);
        jifMovimientosLayout.setHorizontalGroup(
            jifMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifMovimientosLayout.createSequentialGroup()
                .addComponent(jpMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jifMovimientosLayout.setVerticalGroup(
            jifMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jdConstanciaComite.setTitle("Comite");

        jpBuscarComite.setBackground(new java.awt.Color(225, 253, 203));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Comite :");

        txtModalComite_Constancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtModalComite_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalComite_ConstanciaKeyReleased(evt);
            }
        });

        jtModalComite_Constancia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Comite"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalComite_Constancia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalComite_ConstanciaMouseClicked(evt);
            }
        });
        jtModalComite_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtModalComite_ConstanciaKeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(jtModalComite_Constancia);

        javax.swing.GroupLayout jpBuscarComiteLayout = new javax.swing.GroupLayout(jpBuscarComite);
        jpBuscarComite.setLayout(jpBuscarComiteLayout);
        jpBuscarComiteLayout.setHorizontalGroup(
            jpBuscarComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarComiteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane13)
                    .addGroup(jpBuscarComiteLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModalComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpBuscarComiteLayout.setVerticalGroup(
            jpBuscarComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarComiteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtModalComite_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jdConstanciaComiteLayout = new javax.swing.GroupLayout(jdConstanciaComite.getContentPane());
        jdConstanciaComite.getContentPane().setLayout(jdConstanciaComiteLayout);
        jdConstanciaComiteLayout.setHorizontalGroup(
            jdConstanciaComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarComite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdConstanciaComiteLayout.setVerticalGroup(
            jdConstanciaComiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarComite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jdConstanciaAgricultor.setTitle("BUSCAR AGRICULTOR");

        jPanel10.setBackground(new java.awt.Color(225, 253, 203));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Usuario:");

        txtModalCliente_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalCliente_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalCliente_ConstanciaKeyReleased(evt);
            }
        });

        jtModalAgricultor_Constancia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalAgricultor_Constancia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalAgricultor_ConstanciaMouseClicked(evt);
            }
        });
        jtModalAgricultor_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtModalAgricultor_ConstanciaKeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(jtModalAgricultor_Constancia);
        if (jtModalAgricultor_Constancia.getColumnModel().getColumnCount() > 0) {
            jtModalAgricultor_Constancia.getColumnModel().getColumn(0).setPreferredWidth(30);
            jtModalAgricultor_Constancia.getColumnModel().getColumn(1).setPreferredWidth(230);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(txtModalCliente_Constancia)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtModalCliente_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdConstanciaAgricultorLayout = new javax.swing.GroupLayout(jdConstanciaAgricultor.getContentPane());
        jdConstanciaAgricultor.getContentPane().setLayout(jdConstanciaAgricultorLayout);
        jdConstanciaAgricultorLayout.setHorizontalGroup(
            jdConstanciaAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdConstanciaAgricultorLayout.setVerticalGroup(
            jdConstanciaAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jdConstanciaLateral.setTitle("Lateral");

        jpConstanciaLateral.setBackground(new java.awt.Color(225, 253, 203));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Lateral:");

        txtModalLateral_Constancia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalLateral_Constancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalLateral_ConstanciaKeyReleased(evt);
            }
        });

        jtModalLateral_Constancia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Lateral", "Sub Lateral", "Con Medida", "Sin Medida", "N° Hectareas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalLateral_Constancia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalLateral_ConstanciaMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(jtModalLateral_Constancia);

        javax.swing.GroupLayout jpConstanciaLateralLayout = new javax.swing.GroupLayout(jpConstanciaLateral);
        jpConstanciaLateral.setLayout(jpConstanciaLateralLayout);
        jpConstanciaLateralLayout.setHorizontalGroup(
            jpConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConstanciaLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane15)
                    .addGroup(jpConstanciaLateralLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtModalLateral_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpConstanciaLateralLayout.setVerticalGroup(
            jpConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConstanciaLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtModalLateral_Constancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdConstanciaLateralLayout = new javax.swing.GroupLayout(jdConstanciaLateral.getContentPane());
        jdConstanciaLateral.getContentPane().setLayout(jdConstanciaLateralLayout);
        jdConstanciaLateralLayout.setHorizontalGroup(
            jdConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpConstanciaLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdConstanciaLateralLayout.setVerticalGroup(
            jdConstanciaLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpConstanciaLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpBuscarAgricultor_Traspaso.setBackground(new java.awt.Color(225, 253, 203));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setText("Usuario:");

        txtModalAgricultor_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalAgricultor_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalAgricultor_TraspasoKeyReleased(evt);
            }
        });

        jtModalAgricultor_Traspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalAgricultor_Traspaso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalAgricultor_TraspasoMouseClicked(evt);
            }
        });
        jtModalAgricultor_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtModalAgricultor_TraspasoKeyPressed(evt);
            }
        });
        jScrollPane16.setViewportView(jtModalAgricultor_Traspaso);

        javax.swing.GroupLayout jpBuscarAgricultor_TraspasoLayout = new javax.swing.GroupLayout(jpBuscarAgricultor_Traspaso);
        jpBuscarAgricultor_Traspaso.setLayout(jpBuscarAgricultor_TraspasoLayout);
        jpBuscarAgricultor_TraspasoLayout.setHorizontalGroup(
            jpBuscarAgricultor_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultor_TraspasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultor_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addGroup(jpBuscarAgricultor_TraspasoLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtModalAgricultor_Traspaso)))
                .addContainerGap())
        );
        jpBuscarAgricultor_TraspasoLayout.setVerticalGroup(
            jpBuscarAgricultor_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultor_TraspasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultor_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txtModalAgricultor_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdTraspasoAgricultorLayout = new javax.swing.GroupLayout(jdTraspasoAgricultor.getContentPane());
        jdTraspasoAgricultor.getContentPane().setLayout(jdTraspasoAgricultorLayout);
        jdTraspasoAgricultorLayout.setHorizontalGroup(
            jdTraspasoAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultor_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdTraspasoAgricultorLayout.setVerticalGroup(
            jdTraspasoAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultor_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpBuscarAgricultorNuevo_Traspaso.setBackground(new java.awt.Color(225, 253, 203));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Usuario:");

        txtModalAgricultorNuevo_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalAgricultorNuevo_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalAgricultorNuevo_TraspasoKeyReleased(evt);
            }
        });

        jtModalAgricultorNuevo_Traspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalAgricultorNuevo_Traspaso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalAgricultorNuevo_TraspasoMouseClicked(evt);
            }
        });
        jtModalAgricultorNuevo_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtModalAgricultorNuevo_TraspasoKeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(jtModalAgricultorNuevo_Traspaso);

        javax.swing.GroupLayout jpBuscarAgricultorNuevo_TraspasoLayout = new javax.swing.GroupLayout(jpBuscarAgricultorNuevo_Traspaso);
        jpBuscarAgricultorNuevo_Traspaso.setLayout(jpBuscarAgricultorNuevo_TraspasoLayout);
        jpBuscarAgricultorNuevo_TraspasoLayout.setHorizontalGroup(
            jpBuscarAgricultorNuevo_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultorNuevo_TraspasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultorNuevo_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane17)
                    .addGroup(jpBuscarAgricultorNuevo_TraspasoLayout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jLabel53)
                        .addGap(18, 18, 18)
                        .addComponent(txtModalAgricultorNuevo_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpBuscarAgricultorNuevo_TraspasoLayout.setVerticalGroup(
            jpBuscarAgricultorNuevo_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultorNuevo_TraspasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultorNuevo_TraspasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txtModalAgricultorNuevo_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdTraspasoNuevoAgricultorLayout = new javax.swing.GroupLayout(jdTraspasoNuevoAgricultor.getContentPane());
        jdTraspasoNuevoAgricultor.getContentPane().setLayout(jdTraspasoNuevoAgricultorLayout);
        jdTraspasoNuevoAgricultorLayout.setHorizontalGroup(
            jdTraspasoNuevoAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultorNuevo_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdTraspasoNuevoAgricultorLayout.setVerticalGroup(
            jdTraspasoNuevoAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultorNuevo_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpBuscarAgricultor_Traspaso1.setBackground(new java.awt.Color(225, 253, 203));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setText("Usuario:");

        txtModalAgricultor_Alquiler.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalAgricultor_Alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalAgricultor_AlquilerKeyReleased(evt);
            }
        });

        jtModalAgricultor_Alquiler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalAgricultor_Alquiler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalAgricultor_AlquilerMouseClicked(evt);
            }
        });
        jtModalAgricultor_Alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtModalAgricultor_AlquilerKeyPressed(evt);
            }
        });
        jScrollPane18.setViewportView(jtModalAgricultor_Alquiler);
        if (jtModalAgricultor_Alquiler.getColumnModel().getColumnCount() > 0) {
            jtModalAgricultor_Alquiler.getColumnModel().getColumn(0).setPreferredWidth(30);
            jtModalAgricultor_Alquiler.getColumnModel().getColumn(1).setPreferredWidth(230);
        }

        javax.swing.GroupLayout jpBuscarAgricultor_Traspaso1Layout = new javax.swing.GroupLayout(jpBuscarAgricultor_Traspaso1);
        jpBuscarAgricultor_Traspaso1.setLayout(jpBuscarAgricultor_Traspaso1Layout);
        jpBuscarAgricultor_Traspaso1Layout.setHorizontalGroup(
            jpBuscarAgricultor_Traspaso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultor_Traspaso1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultor_Traspaso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane18)
                    .addGroup(jpBuscarAgricultor_Traspaso1Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModalAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpBuscarAgricultor_Traspaso1Layout.setVerticalGroup(
            jpBuscarAgricultor_Traspaso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscarAgricultor_Traspaso1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscarAgricultor_Traspaso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtModalAgricultor_Alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdAlquilerAgricultorLayout = new javax.swing.GroupLayout(jdAlquilerAgricultor.getContentPane());
        jdAlquilerAgricultor.getContentPane().setLayout(jdAlquilerAgricultorLayout);
        jdAlquilerAgricultorLayout.setHorizontalGroup(
            jdAlquilerAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultor_Traspaso1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdAlquilerAgricultorLayout.setVerticalGroup(
            jdAlquilerAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscarAgricultor_Traspaso1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jmiEditar.setText("Editar");
        jmiEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiEditarActionPerformed(evt);
            }
        });
        jpmAgricultor.add(jmiEditar);

        jmip_Pagar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jmip_Pagar.setText("Realizar Pago");
        jmip_Pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmip_PagarActionPerformed(evt);
            }
        });
        jpmVerPagos.add(jmip_Pagar);

        jmip_GenerarDocumento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jmip_GenerarDocumento.setText("Ver Documento");
        jmip_GenerarDocumento.setToolTipText("");
        jpmVerPagos.add(jmip_GenerarDocumento);

        jmip_Anular.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jmip_Anular.setText("Anular");
        jmip_Anular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmip_AnularActionPerformed(evt);
            }
        });
        jpmVerPagos.add(jmip_Anular);

        jdPagar.setResizable(false);

        jPanel9.setBackground(new java.awt.Color(225, 253, 203));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Fecha:");

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel85.setText("Boucher:");

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel87.setText("Observacion:");

        txtFecha_RegistrarPagos.setEnabled(false);

        txtObservacion_RegistrarPagos.setColumns(20);
        txtObservacion_RegistrarPagos.setRows(5);
        txtObservacion_RegistrarPagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObservacion_RegistrarPagosKeyPressed(evt);
            }
        });
        jScrollPane19.setViewportView(txtObservacion_RegistrarPagos);

        btn_Cancelar_Pago.setBackground(new java.awt.Color(255, 51, 0));
        btn_Cancelar_Pago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar_Pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/cancelar.png"))); // NOI18N
        btn_Cancelar_Pago.setText("Cancelar");
        btn_Cancelar_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_PagoActionPerformed(evt);
            }
        });

        btn_Guardar_pago.setBackground(new java.awt.Color(255, 51, 0));
        btn_Guardar_pago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Guardar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        btn_Guardar_pago.setText("Guardar");
        btn_Guardar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_pagoActionPerformed(evt);
            }
        });

        jLabel88.setBackground(new java.awt.Color(0, 153, 153));
        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("REALIZAR PAGOS");
        jLabel88.setOpaque(true);

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel91.setText("Monto:");

        txtMonto_Pago.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel85)
                            .addComponent(jLabel91))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel87)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btn_Guardar_pago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(btn_Cancelar_Pago))
                    .addComponent(jScrollPane19)
                    .addComponent(txtVoucher_RegistrarPago)
                    .addComponent(txtFecha_RegistrarPagos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMonto_Pago))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(txtFecha_RegistrarPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(txtMonto_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(txtVoucher_RegistrarPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Guardar_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Cancelar_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jdPagarLayout = new javax.swing.GroupLayout(jdPagar.getContentPane());
        jdPagar.getContentPane().setLayout(jdPagarLayout);
        jdPagarLayout.setHorizontalGroup(
            jdPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdPagarLayout.setVerticalGroup(
            jdPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpTraspasoLateral.setBackground(new java.awt.Color(225, 253, 203));

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel90.setText("Lateral:");

        txtModalLateral_Traspaso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModalLateral_Traspaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtModalLateral_TraspasoKeyReleased(evt);
            }
        });

        jtModalLateral_Traspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Lateral", "Sub Lateral", "Con Medida", "Sin Medida", "N° Hectareas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtModalLateral_Traspaso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtModalLateral_TraspasoMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(jtModalLateral_Traspaso);

        javax.swing.GroupLayout jpTraspasoLateralLayout = new javax.swing.GroupLayout(jpTraspasoLateral);
        jpTraspasoLateral.setLayout(jpTraspasoLateralLayout);
        jpTraspasoLateralLayout.setHorizontalGroup(
            jpTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTraspasoLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTraspasoLateralLayout.createSequentialGroup()
                        .addComponent(jLabel90)
                        .addGap(14, 14, 14)
                        .addComponent(txtModalLateral_Traspaso))
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpTraspasoLateralLayout.setVerticalGroup(
            jpTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTraspasoLateralLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(txtModalLateral_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdTraspasoLateralLayout = new javax.swing.GroupLayout(jdTraspasoLateral.getContentPane());
        jdTraspasoLateral.getContentPane().setLayout(jdTraspasoLateralLayout);
        jdTraspasoLateralLayout.setHorizontalGroup(
            jdTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTraspasoLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdTraspasoLateralLayout.setVerticalGroup(
            jdTraspasoLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTraspasoLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifCuentas.setClosable(true);
        jifCuentas.setIconifiable(true);
        jifCuentas.setResizable(true);
        jifCuentas.setTitle("CUENTAS");
        jifCuentas.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifCuentas.setVisible(true);

        jTabbedPane2.setBackground(new java.awt.Color(225, 253, 203));
        jTabbedPane2.setOpaque(true);

        jPanel41.setBackground(new java.awt.Color(225, 253, 203));

        btnGuardar5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        btnGuardar5.setText("GUARDAR");
        btnGuardar5.setIconTextGap(8);
        btnGuardar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar5ActionPerformed(evt);
            }
        });

        btnCancelar5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/cancelar.png"))); // NOI18N
        btnCancelar5.setText("CANCELAR");
        btnCancelar5.setIconTextGap(8);

        jLabel98.setBackground(new java.awt.Color(0, 153, 153));
        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setText("LISTA DE CUENTAS");
        jLabel98.setOpaque(true);

        txtFiltroNombre_Cuenta2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFiltroNombre_Cuenta2.setPrompt("Buscar Cuentas");
        txtFiltroNombre_Cuenta2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroNombre_Cuenta2KeyReleased(evt);
            }
        });

        jComboBox8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "N° Cuenta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane23.setViewportView(jtCuentas);

        jLabel99.setBackground(new java.awt.Color(225, 253, 203));
        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setText("REGISTRO DE CUENTAS");
        jLabel99.setOpaque(true);

        jPanel42.setBackground(new java.awt.Color(225, 253, 203));
        jPanel42.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Codigo2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Codigo2.setText("Codigo:");

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel100.setText("Nombre:");

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel101.setText("N° Cuenta:");

        txtNombre_Cuentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre_Cuentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre_Cuentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre_CuentasKeyTyped(evt);
            }
        });

        txtCodigo_Cuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo_Cuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo_Cuenta.setPrompt("Ejemplo: CU1");
        txtCodigo_Cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigo_CuentaKeyTyped(evt);
            }
        });

        txtNumCuenta_Registrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumCuenta_Registrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNumCuenta_Registrar.setPrompt("Ejemplo: 4567");
        txtNumCuenta_Registrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumCuenta_RegistrarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Codigo2)
                    .addComponent(jLabel100))
                .addGap(25, 25, 25)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(txtCodigo_Cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumCuenta_Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombre_Cuentas))
                .addGap(59, 59, 59))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Codigo2)
                    .addComponent(jLabel101)
                    .addComponent(txtCodigo_Cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumCuenta_Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(txtNombre_Cuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar5, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                        .addGap(94, 94, 94))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(txtFiltroNombre_Cuenta2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(btnGuardar5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFiltroNombre_Cuenta2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("CUENTAS", new javax.swing.ImageIcon(getClass().getResource("/recurso/cuenta.png")), jPanel39); // NOI18N

        jPanel44.setBackground(new java.awt.Color(225, 253, 203));

        jLabel102.setBackground(new java.awt.Color(225, 253, 203));
        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("ASIGNAR COSTOS A CUENTAS");
        jLabel102.setOpaque(true);

        Codigo3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Codigo3.setText("Cuentas:");

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel103.setText("Monto:");

        txtMonto_AsignarCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto_AsignarCuenta.setToolTipText("S/. 0.00");
        txtMonto_AsignarCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMonto_AsignarCuenta.setPrompt("S/. 0.00");
        txtMonto_AsignarCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonto_AsignarCuentaKeyTyped(evt);
            }
        });

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel104.setText("Concepto:");

        txtConcepto_AsignarCosto.setColumns(20);
        txtConcepto_AsignarCosto.setRows(5);
        txtConcepto_AsignarCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConcepto_AsignarCostoKeyTyped(evt);
            }
        });
        jScrollPane6.setViewportView(txtConcepto_AsignarCosto);

        btnGuardar6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        btnGuardar6.setText("GUARDAR");
        btnGuardar6.setIconTextGap(8);
        btnGuardar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar6ActionPerformed(evt);
            }
        });

        btnCancelar6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/cancelar.png"))); // NOI18N
        btnCancelar6.setText("CANCELAR");
        btnCancelar6.setIconTextGap(8);

        jLabel105.setBackground(new java.awt.Color(0, 153, 153));
        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText("LISTA DE COSTOS POR CUENTA");
        jLabel105.setOpaque(true);

        jtAsignarCosto_Cuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "N° Cuenta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane24.setViewportView(jtAsignarCosto_Cuentas);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel102, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Codigo3)
                            .addComponent(jLabel104))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(cboCuentas_AsignarCostos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCancelar6, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(jLabel103)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMonto_AsignarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane24)
                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Codigo3)
                            .addComponent(cboCuentas_AsignarCostos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel103)
                            .addComponent(txtMonto_AsignarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel104)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("ASIGNAR COSTOS", new javax.swing.ImageIcon(getClass().getResource("/recurso/asignar_costo.png")), jPanel43); // NOI18N

        javax.swing.GroupLayout jifCuentasLayout = new javax.swing.GroupLayout(jifCuentas.getContentPane());
        jifCuentas.getContentPane().setLayout(jifCuentasLayout);
        jifCuentasLayout.setHorizontalGroup(
            jifCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifCuentasLayout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jifCuentasLayout.setVerticalGroup(
            jifCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jifUsuario.setClosable(true);
        jifUsuario.setIconifiable(true);
        jifUsuario.setResizable(true);
        jifUsuario.setTitle("TRABAJADORES");
        jifUsuario.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifUsuario.setVisible(true);

        jTabbedPane10.setBackground(new java.awt.Color(225, 253, 203));
        jTabbedPane10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane10.setOpaque(true);

        jPanel45.setBackground(new java.awt.Color(225, 253, 203));
        jPanel45.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtLista_Usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Usuario", "Nombre y Apellido", "DNI", "Teléfono/Celular", "Cargo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane22.setViewportView(jtLista_Usuario);

        txtFiltro_Usuario.setToolTipText("");
        txtFiltro_Usuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFiltro_Usuario.setPrompt("Buscar Usuario");
        txtFiltro_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltro_UsuarioKeyReleased(evt);
            }
        });

        cboTipoFiltro_Usuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboTipoFiltro_Usuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "NOMBRES", "APELLIDOS", "USUARIO" }));

        jLabel77.setBackground(new java.awt.Color(0, 153, 153));
        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("LISTA DE TRABAJADORES");
        jLabel77.setOpaque(true);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane22)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(txtFiltro_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTipoFiltro_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipoFiltro_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltro_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane10.addTab("CONSULTAR   ", new javax.swing.ImageIcon(getClass().getResource("/recurso/Consultar.png")), jPanel45); // NOI18N

        jPanel46.setBackground(new java.awt.Color(225, 253, 203));
        jPanel46.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setText("Nombres:");

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel94.setText("Apellidos:");

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel95.setText("ID:");

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel96.setText("DNI:");

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel97.setText("Fec. Nacimiento:");

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel106.setText("Email:");

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel107.setText("Teléfono/Celular :");

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel108.setText("Direccion:");

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel109.setText("Cargo :");

        txtnombres_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombres_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnombres_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombres_usuarioKeyTyped(evt);
            }
        });

        txtapellidos_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtapellidos_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtapellidos_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapellidos_usuarioKeyTyped(evt);
            }
        });

        txtID_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtID_Usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtID_UsuarioKeyTyped(evt);
            }
        });

        txtDireccion_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDireccion_Usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccion_UsuarioKeyTyped(evt);
            }
        });

        txtEmail_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail_Usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmail_UsuarioKeyTyped(evt);
            }
        });

        txtTeleCelular_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTeleCelular_Usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTeleCelular_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTeleCelular_UsuarioKeyTyped(evt);
            }
        });

        txtFechaNacimiento_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboCargo_Usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_Guardar_Usuario.setBackground(new java.awt.Color(255, 102, 0));
        btn_Guardar_Usuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Guardar_Usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save-icon.png"))); // NOI18N
        btn_Guardar_Usuario.setText("GUARDAR");
        btn_Guardar_Usuario.setIconTextGap(8);
        btn_Guardar_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Guardar_UsuarioActionPerformed(evt);
            }
        });

        btn_Cancelar_Usuario.setBackground(new java.awt.Color(255, 102, 0));
        btn_Cancelar_Usuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Cancelar_Usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Cancel-icon.png"))); // NOI18N
        btn_Cancelar_Usuario.setText("CANCELAR");
        btn_Cancelar_Usuario.setIconTextGap(8);
        btn_Cancelar_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cancelar_UsuarioActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("PASSWORD:");

        txtpass_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtpass_usuario.setForeground(new java.awt.Color(255, 153, 0));
        txtpass_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpass_usuario.setToolTipText("");
        txtpass_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpass_usuarioKeyTyped(evt);
            }
        });

        txtdni_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdni_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdni_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdni_usuarioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel94)
                            .addComponent(jLabel80))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtapellidos_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnombres_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel46Layout.createSequentialGroup()
                            .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel96)
                                .addComponent(jLabel95))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel46Layout.createSequentialGroup()
                                    .addGap(267, 267, 267)
                                    .addComponent(jLabel41)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtpass_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel46Layout.createSequentialGroup()
                                    .addGap(70, 70, 70)
                                    .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtID_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtdni_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel46Layout.createSequentialGroup()
                                    .addGap(70, 70, 70)
                                    .addComponent(cboCargo_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel46Layout.createSequentialGroup()
                            .addComponent(jLabel97)
                            .addGap(29, 29, 29)
                            .addComponent(txtFechaNacimiento_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel107)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTeleCelular_Usuario))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addComponent(btn_Guardar_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_Cancelar_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel108)
                                    .addComponent(jLabel106)
                                    .addComponent(jLabel109))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(txtID_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(txtpass_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(txtdni_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombres_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtapellidos_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtFechaNacimiento_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel107)
                        .addComponent(txtTeleCelular_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(cboCargo_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel108)
                    .addComponent(txtDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106)
                    .addComponent(txtEmail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Guardar_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Cancelar_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane10.addTab("REGISTRAR  ", new javax.swing.ImageIcon(getClass().getResource("/recurso/agricultor.png")), jPanel46); // NOI18N

        javax.swing.GroupLayout jpUsurioLayout = new javax.swing.GroupLayout(jpUsurio);
        jpUsurio.setLayout(jpUsurioLayout);
        jpUsurioLayout.setHorizontalGroup(
            jpUsurioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane10)
        );
        jpUsurioLayout.setVerticalGroup(
            jpUsurioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane10)
        );

        javax.swing.GroupLayout jifUsuarioLayout = new javax.swing.GroupLayout(jifUsuario.getContentPane());
        jifUsuario.getContentPane().setLayout(jifUsuarioLayout);
        jifUsuarioLayout.setHorizontalGroup(
            jifUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpUsurio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jifUsuarioLayout.setVerticalGroup(
            jifUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpUsurio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jifComites.setClosable(true);
        jifComites.setIconifiable(true);
        jifComites.setResizable(true);
        jifComites.setTitle("COMITES");
        jifComites.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/comision_logo.jpg"))); // NOI18N
        jifComites.setVisible(true);

        jPanel28.setBackground(new java.awt.Color(225, 253, 203));

        jPanel29.setBackground(new java.awt.Color(225, 253, 203));
        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel110.setText("Comite :");

        txtComite_Registrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtComite_Registrar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComite_Registrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtComite_RegistrarKeyTyped(evt);
            }
        });

        btnGuardar_Comite.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar_Comite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Save.png"))); // NOI18N
        btnGuardar_Comite.setText("GUARDAR");
        btnGuardar_Comite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar_ComiteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel110)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtComite_Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar_Comite, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar_Comite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtComite_Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110))
                .addContainerGap())
        );

        jtComite_Administracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane25.setViewportView(jtComite_Administracion);

        jLabel111.setBackground(new java.awt.Color(0, 153, 153));
        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("LISTA DE COMITES");
        jLabel111.setOpaque(true);

        txtFiltroComite_Administracion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFiltroComite_Administracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroComite_AdministracionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFiltroComite_Administracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltroComite_Administracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jifComitesLayout = new javax.swing.GroupLayout(jifComites.getContentPane());
        jifComites.getContentPane().setLayout(jifComitesLayout);
        jifComitesLayout.setHorizontalGroup(
            jifComitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jifComitesLayout.setVerticalGroup(
            jifComitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Cobranza Comisión de Usuarios Perla del Huallaga");
        setIconImage(new ImageIcon(getClass().getResource("/recurso/comision_logo.jpg")).getImage());

        jdeskpanInicio.setBackground(new java.awt.Color(255, 255, 255));
        jdeskpanInicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jdeskpanInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jdeskpanInicioLayout = new javax.swing.GroupLayout(jdeskpanInicio);
        jdeskpanInicio.setLayout(jdeskpanInicioLayout);
        jdeskpanInicioLayout.setHorizontalGroup(
            jdeskpanInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 983, Short.MAX_VALUE)
        );
        jdeskpanInicioLayout.setVerticalGroup(
            jdeskpanInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpInicioLayout = new javax.swing.GroupLayout(jpInicio);
        jpInicio.setLayout(jpInicioLayout);
        jpInicioLayout.setHorizontalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdeskpanInicio, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jpInicioLayout.setVerticalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdeskpanInicio)
        );

        jmbPrincipal.setBackground(new java.awt.Color(225, 253, 203));
        jmbPrincipal.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jmbPrincipal.setPreferredSize(new java.awt.Dimension(0, 40));

        jmInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Home.png"))); // NOI18N
        jmInicio.setText("INICIO");
        jmInicio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jmInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jmiSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/Exit.png"))); // NOI18N
        jmiSalir.setText("CERRAR SESION");
        jmiSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSalirActionPerformed(evt);
            }
        });
        jmInicio.add(jmiSalir);

        jmbPrincipal.add(jmInicio);

        jmCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/caja.png"))); // NOI18N
        jmCaja.setText("CAJA");
        jmCaja.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jmiInicioCierre.setText("INICIAR / CIERRE");
        jmiInicioCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInicioCierreActionPerformed(evt);
            }
        });
        jmCaja.add(jmiInicioCierre);

        jmiMovimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/money.png"))); // NOI18N
        jmiMovimiento.setText("MOVIMIENTOS");
        jmiMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiMovimientoActionPerformed(evt);
            }
        });
        jmCaja.add(jmiMovimiento);

        jmbPrincipal.add(jmCaja);

        jmConstancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/constancia.png"))); // NOI18N
        jmConstancia.setText("CONSTANCIA");
        jmConstancia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jmiRegistro.setText("REGISTRO");
        jmiRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRegistroActionPerformed(evt);
            }
        });
        jmConstancia.add(jmiRegistro);

        jmiTraspaso.setText("TRASPASO");
        jmiTraspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTraspasoActionPerformed(evt);
            }
        });
        jmConstancia.add(jmiTraspaso);

        jmbPrincipal.add(jmConstancia);

        jmPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/pagos.png"))); // NOI18N
        jmPagos.setText("PAGOS");
        jmPagos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jmiVerPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/money.png"))); // NOI18N
        jmiVerPagos.setText("VER PAGOS");
        jmiVerPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiVerPagosActionPerformed(evt);
            }
        });
        jmPagos.add(jmiVerPagos);

        jmiAlquiler.setText("ALQUILER");
        jmiAlquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAlquilerActionPerformed(evt);
            }
        });
        jmPagos.add(jmiAlquiler);

        jMenu1.setText("PAGO MULTAS");

        jMenuItem3.setText("ASAMBLEA");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem8.setText("SUFRAGIO");
        jMenu1.add(jMenuItem8);

        jmPagos.add(jMenu1);

        jmbPrincipal.add(jmPagos);

        jpReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/report_menu.png"))); // NOI18N
        jpReportes.setText("REPORTES");
        jpReportes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jmiPagos.setText("PAGOS");
        jpReportes.add(jmiPagos);

        jmiMovimientos.setText("MOVIMIENTOS");
        jpReportes.add(jmiMovimientos);

        jmiClientes.setText("CLIENTES");
        jpReportes.add(jmiClientes);

        jmbPrincipal.add(jpReportes);

        jmAdministracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/configuracion.png"))); // NOI18N
        jmAdministracion.setText("ADMINISTRACIÓN");
        jmAdministracion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jmiUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/icon_usuario.png"))); // NOI18N
        jmiUsuario.setText("TRABAJADOR");
        jmiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUsuarioActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiUsuario);

        jmiAgricultor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/icon_agricultor.png"))); // NOI18N
        jmiAgricultor.setText("USUARIO");
        jmiAgricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAgricultorActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiAgricultor);

        jmiCuentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/lista.png"))); // NOI18N
        jmiCuentas.setText("CUENTAS");
        jmiCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCuentasActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiCuentas);

        jmiPeriodo.setText("PERIODO");
        jmiPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPeriodoActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiPeriodo);

        jmiCargos.setText("CARGOS");
        jmiCargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCargosActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiCargos);

        jmiComite.setText("COMITES");
        jmiComite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiComiteActionPerformed(evt);
            }
        });
        jmAdministracion.add(jmiComite);

        jMenuItem9.setText("DOCUMENTO");
        jmAdministracion.add(jMenuItem9);

        jmbPrincipal.add(jmAdministracion);

        setJMenuBar(jmbPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCargosActionPerformed
        getcombo_cargo_all("");
        iniciarFomrulario_Cargo(jifCargos);
    }//GEN-LAST:event_jmiCargosActionPerformed

    private void jmiAlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAlquilerActionPerformed
        limpiarFomulario_Alquiler();
        getcombo_material_all();
        getcombo_cliente_all();
        iniciarFomrulario_Alquiler(jifIngresarAlquiler);
    }//GEN-LAST:event_jmiAlquilerActionPerformed

    private void jmiInicioCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInicioCierreActionPerformed
        iniciarFomrulario_CierreInicioCaja(jifInicioCierreCaja);
    }//GEN-LAST:event_jmiInicioCierreActionPerformed

    private void jmiRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRegistroActionPerformed
        getcombo_periodo_all();
        getcombo_cliente_all();
        //gettabla_comite_byActivos("");
        getcombo_tipocultivo_all();
        limpiarFomulario_Constancia();
        iniciarFomrulario_Constancia(jifConstancia);
    }//GEN-LAST:event_jmiRegistroActionPerformed

    private void jmiTraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTraspasoActionPerformed
        limpiarFomulario_Traspaso();
        get_agricultores_byActivos("");
        getcombo_agricultor_antiguos();
        getcombo_agricultor_nuevos();
        iniciarFomrulario_Traspaso(jifTraspaso);
    }//GEN-LAST:event_jmiTraspasoActionPerformed

    private void jmiVerPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiVerPagosActionPerformed
        getcombo_cliente_all();
        iniciarFomrulario_VerPagos(jifVerPagos);
    }//GEN-LAST:event_jmiVerPagosActionPerformed

    private void jmiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUsuarioActionPerformed
        limpiarFomulario_Usuario();
        getcombo_cargo_all("");
        gettabla_usuario_byfiltro("", 0);
        iniciarFomrulario_Usuario(jifUsuario);
    }//GEN-LAST:event_jmiUsuarioActionPerformed

    private void jmiAgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAgricultorActionPerformed
        get_latreles_all();
        get_sublatreles_all();
        gettabla_agricultor_all("", 1);
        iniciarFomrulario_Agricultor(jifAgricultores);
    }//GEN-LAST:event_jmiAgricultorActionPerformed

    private void jmiCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCuentasActionPerformed
        gettabla_cuenta_all("");
        gettabla_asignacioncosto_cuenta_all();
        limpiarFomulario_Cuenta();
        limpiarFomulario_AsignacionCosto_Cuenta();
        iniciarFomrulario_Cuentas(jifCuentas);
    }//GEN-LAST:event_jmiCuentasActionPerformed

    private void jmiPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPeriodoActionPerformed
        iniciarFomrulario_Periodo(jifPeriodos);
    }//GEN-LAST:event_jmiPeriodoActionPerformed

    private void jmiComiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiComiteActionPerformed
        gettabla_comite_byActivos("");
        iniciarFomrulario_Comite(jifComites);
    }//GEN-LAST:event_jmiComiteActionPerformed

    private void jmiMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiMovimientoActionPerformed
        getcombo_tipodocumento_all();
        getcombo_tipooperacion_all();
        limpiarFomulario_Movimiento();
        iniciarFomrulario_Movimiento(jifMovimientos);
    }//GEN-LAST:event_jmiMovimientoActionPerformed

    private void jmiSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmiSalirActionPerformed


    private void btn_ModalComite_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModalComite_ConstanciaActionPerformed
        try {
            gettabla_comite_byActivos("");
            jdConstanciaComite.pack();
            jdConstanciaComite.setLocationRelativeTo(null);
            jdConstanciaComite.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdConstanciaComite),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdConstanciaComite.setModal(true);
            jdConstanciaComite.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }//GEN-LAST:event_btn_ModalComite_ConstanciaActionPerformed

    private void btn_ModalCliente_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModalCliente_ConstanciaActionPerformed
        try {
            gettabla_agricultor_constancia_byActivos("");
            jdConstanciaAgricultor.pack();
            jdConstanciaAgricultor.setLocationRelativeTo(null);
            jdConstanciaAgricultor.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdConstanciaAgricultor),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdConstanciaAgricultor.setModal(true);
            jdConstanciaAgricultor.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }//GEN-LAST:event_btn_ModalCliente_ConstanciaActionPerformed

    private void btn_ModalLateral_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModalLateral_ConstanciaActionPerformed
        try {
            gettabla_lateral_byagricultoractivos("", idCliente_Constancia);
            jdConstanciaLateral.pack();
            jdConstanciaLateral.setLocationRelativeTo(null);
            jdConstanciaLateral.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdConstanciaLateral),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdConstanciaLateral.setModal(true);
            jdConstanciaLateral.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error cargar modal lateral" + e.toString());
        } finally {
        }
    }//GEN-LAST:event_btn_ModalLateral_ConstanciaActionPerformed

    private void btn_Traspaso_ModalAgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Traspaso_ModalAgricultorActionPerformed
        try {
            jdTraspasoAgricultor.pack();
            jdTraspasoAgricultor.setLocationRelativeTo(null);
            jdTraspasoAgricultor.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdTraspasoAgricultor),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdTraspasoAgricultor.setModal(true);
            jdTraspasoAgricultor.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error al cargar Traspsado" + e.toString());
        }
    }//GEN-LAST:event_btn_Traspaso_ModalAgricultorActionPerformed

    private void btn_Traspaso_ModalNuevoAgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Traspaso_ModalNuevoAgricultorActionPerformed
        try {
            jdTraspasoNuevoAgricultor.pack();
            jdTraspasoNuevoAgricultor.setLocationRelativeTo(null);
            jdTraspasoNuevoAgricultor.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdTraspasoNuevoAgricultor),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdTraspasoNuevoAgricultor.setModal(true);
            jdTraspasoNuevoAgricultor.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error al cargar Traspaso Nuevo " + e.toString());
        }
    }//GEN-LAST:event_btn_Traspaso_ModalNuevoAgricultorActionPerformed

    private void txtModalCliente_ConstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalCliente_ConstanciaKeyReleased
        gettabla_agricultor_constancia_byActivos(txtModalCliente_Constancia.getText());
    }//GEN-LAST:event_txtModalCliente_ConstanciaKeyReleased

    private void jtModalAgricultor_ConstanciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalAgricultor_ConstanciaMouseClicked
        try {
            idCliente_Constancia = Integer.parseInt(String.valueOf(jtModalAgricultor_Constancia.getModel().getValueAt(jtModalAgricultor_Constancia.getSelectedRow(), 0)));
            txtCliente_Constancia.setText(String.valueOf(jtModalAgricultor_Constancia.getModel().getValueAt(jtModalAgricultor_Constancia.getSelectedRow(), 1)));
            BLPeriodo p = new BLPeriodo();
            PeriodoCampania pc = p.get_peridocampania_byagricultor(idCliente_Constancia, new java.sql.Date(txtFecha_Constancia.getDate().getTime()));
            idPeriodo_Constancia = pc.getPeriodo_id();
            txtPeriodoRango_Constancia.setText(pc.getVar_periodo() + " : " + pc.getNom_mesInicio() + " - " + pc.getNom_mesFin());
            txtCampania_Constancia.setText(String.valueOf(pc.getInt_campania()));
            btn_ModalLateral_Constancia.setEnabled(true);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            jdConstanciaAgricultor.dispose();
        }
    }//GEN-LAST:event_jtModalAgricultor_ConstanciaMouseClicked

    private void jtModalAgricultor_ConstanciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtModalAgricultor_ConstanciaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                idCliente_Constancia = Integer.parseInt(String.valueOf(jtModalAgricultor_Constancia.getModel().getValueAt(jtModalAgricultor_Constancia.getSelectedRow(), 0)));
                txtCliente_Constancia.setText(String.valueOf(jtModalAgricultor_Constancia.getModel().getValueAt(jtModalAgricultor_Constancia.getSelectedRow(), 1)));
                BLPeriodo p = new BLPeriodo();
                PeriodoCampania pc = p.get_peridocampania_byagricultor(idCliente_Constancia, new java.sql.Date(txtFecha_Constancia.getDate().getTime()));
                idPeriodo_Constancia = pc.getPeriodo_id();
                idPeriodo_Constancia = pc.getInt_campania();
                txtPeriodoRango_Constancia.setText(pc.getVar_periodo() + " : " + pc.getNom_mesInicio() + " - " + pc.getNom_mesFin());
                txtCampania_Constancia.setText(String.valueOf(pc.getInt_campania()));
                btn_ModalLateral_Constancia.setEnabled(true);
            } catch (Exception e) {
                System.out.println("" + e.toString());
            } finally {
                jdConstanciaAgricultor.dispose();
            }
        }
    }//GEN-LAST:event_jtModalAgricultor_ConstanciaKeyPressed

    private void btn_Guardar_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_ConstanciaActionPerformed
        try {
            if (txtFecha_Constancia.getDate() != null && txtComite_Constancia.getText().compareTo("") != 0
                    && txtCliente_Constancia.getText().compareTo("") != 0 && txtPeriodoRango_Constancia.getText().compareTo("") != 0
                    && txtLateral_Constancia.getText().compareTo("") != 0 && txtHectareas_Constancia.getText().compareTo("") != 0) {
                Constancia c = new Constancia();
                c.setComite_id(idComite_Constancia);
                c.setLateral_id(idLateral_Constancia);
                c.setPeriodo_id(idPeriodo_Constancia);
                c.setInt_campania(Integer.parseInt(txtCampania_Constancia.getText()));
                c.setDec_nrohectaria(Double.parseDouble(txtHectareas_Constancia.getText()));
                if (rbAlmacigo_Constancia.isSelected()) {
                    c.setVar_tipoconstancia("A"); // Almacigo
                    c.setDat_fechRealizacion(new java.sql.Timestamp(txtFechaAlmacigo_constancia.getDate().getTime()));
                } else {
                    c.setVar_tipoconstancia("B"); // Boleo
                    c.setDat_fechRealizacion(null);
                }
                c.setDat_fechRegistro(new java.sql.Timestamp(txtFecha_Constancia.getDate().getTime()));
                c.setInt_tipocultivo(((Constante) cboTipoCultivo_Constancia.getSelectedItem()).getInt_valor());
                BLConstancia co = new BLConstancia();
                if (co.insertarConstancia(c)) {
                    limpiarFomulario_Constancia();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar", "MENSAJE", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }//GEN-LAST:event_btn_Guardar_ConstanciaActionPerformed

    private void chkCampania_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCampania_ConstanciaActionPerformed
        if (chkCampania_Constancia.isSelected()) {
            cboPeriodoFiltro_Constancia.setEnabled(true);
        } else if (!chkCampania_Constancia.isSelected()) {
            cboPeriodoFiltro_Constancia.setEnabled(false);
        }
    }//GEN-LAST:event_chkCampania_ConstanciaActionPerformed

    private void chkCliente_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliente_ConstanciaActionPerformed
        if (chkCliente_Constancia.isSelected()) {
            cboAgricultorFiltro_Constancia.setEnabled(true);
        } else if (!chkCliente_Constancia.isSelected()) {
            cboAgricultorFiltro_Constancia.setEnabled(false);
        }
    }//GEN-LAST:event_chkCliente_ConstanciaActionPerformed

    private void btnBuscarFiltro_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFiltro_ConstanciaActionPerformed
        buscar_constancia_byfiltro();
    }//GEN-LAST:event_btnBuscarFiltro_ConstanciaActionPerformed

    private void chkFecha_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFecha_ConstanciaActionPerformed
        if (chkFecha_Constancia.isSelected()) {
            txtFiltroInicio_Constancia.setEnabled(true);
            txtFiltroFin_Constancia.setEnabled(true);
        } else if (!chkFecha_Constancia.isSelected()) {
            txtFiltroInicio_Constancia.setEnabled(false);
            txtFiltroFin_Constancia.setEnabled(false);
        }
    }//GEN-LAST:event_chkFecha_ConstanciaActionPerformed

    private void txtFecha_ConstanciaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFecha_ConstanciaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFecha_ConstanciaPropertyChange

    private void rbAlmacigo_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAlmacigo_ConstanciaActionPerformed
        if (rbAlmacigo_Constancia.isSelected()) {
            txtFechaAlmacigo_constancia.setEnabled(true);
        }
    }//GEN-LAST:event_rbAlmacigo_ConstanciaActionPerformed

    private void rbBoleo_ConstanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBoleo_ConstanciaActionPerformed
        if (rbBoleo_Constancia.isSelected()) {
            txtFechaAlmacigo_constancia.setEnabled(false);
        }
    }//GEN-LAST:event_rbBoleo_ConstanciaActionPerformed

    private void txtHectareas_ConstanciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHectareas_ConstanciaKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtHectareas_Constancia.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtHectareas_ConstanciaKeyTyped

    private void chkAntiguoDuenio_AgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAntiguoDuenio_AgricultorActionPerformed
        if (chkAntiguoDuenio_Agricultor.isSelected()) {
            cboAntiguoAgricultor_Traspaso.setEnabled(true);
        } else {
            cboAntiguoAgricultor_Traspaso.setEnabled(false);
        }
    }//GEN-LAST:event_chkAntiguoDuenio_AgricultorActionPerformed

    private void chkAntiguoNuevo_AgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAntiguoNuevo_AgricultorActionPerformed
        if (chkAntiguoNuevo_Agricultor.isSelected()) {
            cboNuevoAgricultor_Traspaso.setEnabled(true);
        } else {
            cboNuevoAgricultor_Traspaso.setEnabled(false);
        }
    }//GEN-LAST:event_chkAntiguoNuevo_AgricultorActionPerformed

    private void btn_Guardar_TraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_TraspasoActionPerformed
        try {
            if (txtCantidadHectaria_Traspaso.getText().compareTo("") != 0 && txtNumDocumento_Traspaso.getText().compareTo("") != 0
                    && txtNuevoLateral_Traspaso.getText().compareTo("") != 0 && txtNuevoSubLateral_Traspaso.getText().compareTo("") != 0
                    && txtNuevoConMedida_Traspaso.getText().compareTo("") != 0 && txtNuevoSinMedida_Traspaso.getText().compareTo("") != 0
                    && txtAgricultor_Traspaso.getText().compareTo("") != 0 && txtLateralCliente_Traspaso.getText().compareTo("") != 0
                    && txtSubLateralAgricultor_Traspo.getText().compareTo("") != 0 && txtNroHectares_Traspaso.getText().compareTo("") != 0
                    && txtNuevoAgricultor_Traspaso.getText().compareTo("") != 0) {
                BLTraspaso t = new BLTraspaso();
                int cant = Integer.parseInt(txtCantidadHectaria_Traspaso.getText());
                boolean resultado = t.RegistrarTraspaso(idNuevoAgricultor_Traspaso, 1, cant, idAgri_Traspaso, idLat_Traspaso,
                        txtNuevoLateral_Traspaso.getText(), txtNuevoSubLateral_Traspaso.getText(),
                        Double.parseDouble(txtNuevoConMedida_Traspaso.getText()), Double.parseDouble(txtNuevoSinMedida_Traspaso.getText()),
                        txtObservacion_Traspaso.getText(), txtNumDocumento_Traspaso.getText());

                if (resultado == true) {
                    JOptionPane.showMessageDialog(null, "Se realizo el Traspaso Correctamente");
                    limpiarFomulario_Traspaso();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo traspasar");
                    limpiarFomulario_Traspaso();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error" + e.getMessage());
        }
    }//GEN-LAST:event_btn_Guardar_TraspasoActionPerformed

    private void btn_Cancelar_TraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_TraspasoActionPerformed
        limpiarFomulario_Traspaso();
    }//GEN-LAST:event_btn_Cancelar_TraspasoActionPerformed

    private void txtCantidadHectaria_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadHectaria_TraspasoKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtCantidadHectaria_Traspaso.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadHectaria_TraspasoKeyTyped

    private void txtNuevoConMedida_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoConMedida_TraspasoKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtNuevoConMedida_Traspaso.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoConMedida_TraspasoKeyTyped

    private void txtNuevoSinMedida_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoSinMedida_TraspasoKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtNuevoSinMedida_Traspaso.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoSinMedida_TraspasoKeyTyped

    private void btn_buscar_pagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_pagosActionPerformed
        int indice = cboEstado_VerPagos.getSelectedIndex();
        int estado = 0;
        if (indice == 0) {
            estado = 1;
        }
        if (indice == 1) {
            estado = 2;
        }
        if (indice == 2) {
            estado = 3;
        }
        if (jrbDni_VerPagos.isSelected()) {
            limpiarTabla(jtVerPagos);
            gettabla_verpagos_byAgricultor(txtFiltroDni_VerPagos.getText(), 0, estado);
        }
        if (jrbAgricultor_VerPagos.isSelected()) {
            limpiarTabla(jtVerPagos);
            int id = ((Agricultor) cboFiltroAgricultor_VerPagos.getSelectedItem()).getInt_id();
            gettabla_verpagos_byAgricultor("", id, estado);
        }

    }//GEN-LAST:event_btn_buscar_pagosActionPerformed

    private void txtMonto_AlquilerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonto_AlquilerKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtMonto_Alquiler.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMonto_AlquilerKeyTyped

    private void txtModalAgricultor_AlquilerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalAgricultor_AlquilerKeyReleased
        gettabla_agricultor_alquiler_byActivos(txtModalAgricultor_Alquiler.getText());
    }//GEN-LAST:event_txtModalAgricultor_AlquilerKeyReleased

    private void jtModalAgricultor_AlquilerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalAgricultor_AlquilerMouseClicked
        try {
            idAgricultor_Alquiler = Integer.parseInt(String.valueOf(jtModalAgricultor_Alquiler.getModel().getValueAt(jtModalAgricultor_Alquiler.getSelectedRow(), 0)));
            txtAgricultor_Alquiler.setText(String.valueOf(jtModalAgricultor_Alquiler.getModel().getValueAt(jtModalAgricultor_Alquiler.getSelectedRow(), 1)));
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            jdAlquilerAgricultor.dispose();
        }
    }//GEN-LAST:event_jtModalAgricultor_AlquilerMouseClicked

    private void jtModalAgricultor_AlquilerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtModalAgricultor_AlquilerKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                idAgricultor_Alquiler = Integer.parseInt(String.valueOf(jtModalAgricultor_Alquiler.getModel().getValueAt(jtModalAgricultor_Alquiler.getSelectedRow(), 0)));
                txtAgricultor_Alquiler.setText(String.valueOf(jtModalAgricultor_Alquiler.getModel().getValueAt(jtModalAgricultor_Alquiler.getSelectedRow(), 1)));
            } catch (Exception e) {
                System.out.println("" + e.toString());
            } finally {
                jdAlquilerAgricultor.dispose();
            }
        }
    }//GEN-LAST:event_jtModalAgricultor_AlquilerKeyPressed

    private void btnBuscarAgricultor_AlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAgricultor_AlquilerActionPerformed
        try {
            getcombo_cliente_all();
            jdAlquilerAgricultor.pack();
            jdAlquilerAgricultor.setLocationRelativeTo(null);
            jdAlquilerAgricultor.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdAlquilerAgricultor),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdAlquilerAgricultor.setModal(true);
            jdAlquilerAgricultor.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error al cargar Alquiler" + e.toString());
        }
    }//GEN-LAST:event_btnBuscarAgricultor_AlquilerActionPerformed

    private void btn_buscar_alquileresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_alquileresActionPerformed
        try {
            int contador = 0;
            ArrayList<String> lista = new ArrayList();
            String condicionFinal = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            boolean fecha = chkFiltroFecha_Alquiler.isSelected();
            boolean cliente = chkFiltroAgricultor_Alquiler.isSelected();

            if (fecha == true) {
                lista.add(" ( date(dat_fechaRegistro) between '" + sdf.format(txtFechaInicio_Alquiler.getDate()) + "' and '" + sdf.format(txtFechaFin_Alquiler.getDate()) + "' ) ");
                contador++;
            }
            if (cliente == true) {
                lista.add(" ( int_id =" + ((Agricultor) cboAgricultor_Alquiler.getSelectedItem()).getInt_id() + " )");
                contador++;
            }
            switch (contador) {
                case 1:
                    condicionFinal = lista.get(0);
                    break;
                case 2:
                    condicionFinal = lista.get(0) + " and " + lista.get(1);
                    break;
                /*case 3:
                 condicionFinal = lista.get(0) + " and " + lista.get(1) + " and " + lista.get(2);
                 break;
                 case 4:
                 condicionFinal = lista.get(0) + " and " + lista.get(1) + " and " + lista.get(2) + " and " + lista.get(3);
                 break;*/
            }
            DefaultTableModel tempConstancia = (DefaultTableModel) jtLista_Alquileres.getModel();
            tempConstancia.setRowCount(0);
            for (ListaAlquiler l : new BLAlquiler().get_alquiler_byclientefecha(condicionFinal)) {
                Object datos[] = {l.getVar_nombre_cliente() + ' ' + l.getVar_apepaterno() + ' ' + l.getVar_apematerno(),
                    l.getVar_nombre_material(), l.getDat_fechinicio(), l.getDat_fechfin(), l.getInt_cantidad(), l.getDec_monto()};
                tempConstancia.addRow(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_buscar_alquileresActionPerformed

    private void btn_Cancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar1ActionPerformed
        limpiarFomulario_Alquiler();
    }//GEN-LAST:event_btn_Cancelar1ActionPerformed

    private void btn_Registrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Registrar1ActionPerformed

        boolean resultado = false;
        try {
            if (txtAgricultor_Alquiler.getText().compareTo("") != 0) {
                BLAlquiler a = new BLAlquiler();
                ArrayList<Detalle_Alquiler> lista_detalle = new ArrayList<Detalle_Alquiler>();
                int nroFilas = ((DefaultTableModel) jtbDetalle_Alquiler.getModel()).getRowCount();
                for (int f = 0; f < nroFilas; f++) {
                    Detalle_Alquiler l = new Detalle_Alquiler();
                    l.setMaterial_id(Integer.parseInt(jtbDetalle_Alquiler.getModel().getValueAt(f, 0).toString()));
                    l.setInt_cantidad(Integer.parseInt(jtbDetalle_Alquiler.getModel().getValueAt(f, 2).toString()));
                    l.setDec_monto(Double.parseDouble(jtbDetalle_Alquiler.getModel().getValueAt(f, 6).toString()));
                    l.setDat_fechinicio(Timestamp.valueOf(jtbDetalle_Alquiler.getModel().getValueAt(f, 3).toString()));
                    l.setDat_fechfin(Timestamp.valueOf(jtbDetalle_Alquiler.getModel().getValueAt(f, 4).toString()));
                    l.setInt_horas(Integer.parseInt(jtbDetalle_Alquiler.getModel().getValueAt(f, 5).toString()));
                    lista_detalle.add(l);
                }

                resultado = a.insertarAlquiler(idAgricultor_Alquiler, lista_detalle);
                if (resultado == true) {
                    JOptionPane.showMessageDialog(null, "Se registro Correctamente");
                    limpiarFomulario_Alquiler();
                    limpiarTabla(jtbDetalle_Alquiler);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo Registrar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se Admiten Campos Vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error de Ingreso" + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_Registrar1ActionPerformed

    private void jrbDni_VerPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbDni_VerPagosActionPerformed
        if (jrbDni_VerPagos.isSelected()) {
            txtFiltroDni_VerPagos.setEnabled(true);
            cboFiltroAgricultor_VerPagos.setEnabled(false);
        }

    }//GEN-LAST:event_jrbDni_VerPagosActionPerformed

    private void jrbAgricultor_VerPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAgricultor_VerPagosActionPerformed
        if (jrbAgricultor_VerPagos.isSelected()) {
            cboFiltroAgricultor_VerPagos.setEnabled(true);
            txtFiltroDni_VerPagos.setEnabled(false);
        }
    }//GEN-LAST:event_jrbAgricultor_VerPagosActionPerformed

    private void jtVerPagosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtVerPagosMousePressed
        if (evt.isPopupTrigger() && jtVerPagos.getModel().getRowCount() != 0
                && jtVerPagos.getSelectedRow() != -1) {
            jpmVerPagos.show(jtVerPagos, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jtVerPagosMousePressed

    private void jtVerPagosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtVerPagosMouseReleased
        if (evt.isPopupTrigger() && jtVerPagos.getModel().getRowCount() != 0
                && jtVerPagos.getSelectedRow() != -1) {
            jpmVerPagos.show(jtVerPagos, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jtVerPagosMouseReleased

    private void jmip_PagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmip_PagarActionPerformed
        try {
            if (cboEstado_VerPagos.getSelectedIndex() == 0) {
                txtFecha_RegistrarPagos.setDate(new Date());
                txtMonto_Pago.setText(jtVerPagos.getModel().getValueAt(jtVerPagos.getSelectedRow(), 4).toString());
                idPago = Integer.parseInt(jtVerPagos.getValueAt(jtVerPagos.getSelectedRow(), 0).toString());
                jdPagar.pack();
                jdPagar.setLocationRelativeTo(null);
                jdPagar.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdConstanciaAgricultor),
                        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
                jdPagar.setModal(true);
                jdPagar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "El Servicio Ya Esta Pagado");
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }//GEN-LAST:event_jmip_PagarActionPerformed

    private void jmip_AnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmip_AnularActionPerformed
        try {
            idPago = Integer.parseInt(jtVerPagos.getValueAt(jtVerPagos.getSelectedRow(), 0).toString());
            if (new BLPagos().AnularPago(idPago)) {
                JOptionPane.showMessageDialog(null, "Se Anulo Correctamente", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                if (jrbDni_VerPagos.isSelected()) {
                    gettabla_verpagos_byAgricultor(txtFiltroDni_VerPagos.getText(), 0, 2);
                }
                if (jrbAgricultor_VerPagos.isSelected()) {
                    int id = ((Agricultor) cboFiltroAgricultor_VerPagos.getSelectedItem()).getInt_id();
                    gettabla_verpagos_byAgricultor("", id, 2);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al Anular", "MENSAJE", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }//GEN-LAST:event_jmip_AnularActionPerformed

    private void btn_Traspaso_ModalLateralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Traspaso_ModalLateralActionPerformed
        try {
            gettabla_lateral_byagricultoractivos("", idAgri_Traspaso);
            jdTraspasoLateral.pack();
            jdTraspasoLateral.setLocationRelativeTo(null);
            jdTraspasoLateral.getRootPane().registerKeyboardAction(new CloseDialogEscape(jdTraspasoAgricultor),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            jdTraspasoLateral.setModal(true);
            jdTraspasoLateral.setVisible(true);
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }//GEN-LAST:event_btn_Traspaso_ModalLateralActionPerformed

    private void txtNuevoSubLateral_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoSubLateral_TraspasoKeyTyped
       if (txtNuevoSubLateral_Traspaso.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoSubLateral_TraspasoKeyTyped

    private void btn_Buscar_TraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Buscar_TraspasoActionPerformed
        try {
            if (chkAntiguoNuevo_Agricultor.isSelected()) {
                int idAgri_Nuevo = ((Agricultor) cboNuevoAgricultor_Traspaso.getSelectedItem()).getInt_id();
                gettabla_traspaso_byclientenuevoantiguo(" int_id_nuevo=" + idAgri_Nuevo);
            }
            if (chkAntiguoDuenio_Agricultor.isSelected()) {
                int idAgri_Antiguo = ((Agricultor) cboAntiguoAgricultor_Traspaso.getSelectedItem()).getInt_id();
                gettabla_traspaso_byclientenuevoantiguo(" int_clienteAntiguo_id=" + idAgri_Antiguo);
            }
        } catch (Exception e) {
            System.out.println("Error de Listado-Vista" + e.getMessage());
        }
    }//GEN-LAST:event_btn_Buscar_TraspasoActionPerformed

    private void btnGuardar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar5ActionPerformed
        try {
            if (txtCodigo_Cuenta.getText().compareTo("") != 0 && txtNombre_Cuentas.getText().compareTo("") != 0
                    && txtNumCuenta_Registrar.getText().compareTo("") != 0) {
                if (new BLCuenta().Registrar(txtCodigo_Cuenta.getText(), txtNombre_Cuentas.getText(), txtNumCuenta_Registrar.getText())) {
                    gettabla_cuenta_all("");
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar", "MENSAJE", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar Cuenta" + e.toString());
        }
    }//GEN-LAST:event_btnGuardar5ActionPerformed

    private void txtFiltroNombre_Cuenta2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroNombre_Cuenta2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroNombre_Cuenta2KeyReleased

    private void btnGuardar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar6ActionPerformed
        try {
            int id_cuenta = 0;
            if (txtMonto_AsignarCuenta.getText().compareTo("") != 0 && txtConcepto_AsignarCosto.getText().compareTo("") != 0) {
                BLCuenta cu = new BLCuenta();
                id_cuenta = ((Cuenta) cboCuentas_AsignarCostos.getSelectedItem()).getInt_id();
                if (cu.AsignarCosto(id_cuenta, Double.parseDouble(txtMonto_AsignarCuenta.getText()),
                        txtConcepto_AsignarCosto.getText().trim())) {
                    limpiarTabla(jtAsignarCosto_Cuentas);
                    gettabla_asignacioncosto_cuenta_all();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar", "MENSAJE", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar Cuenta" + e.toString());
        }
    }//GEN-LAST:event_btnGuardar6ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try {
            if (txtSinMedida_Agricultor.getText().compareTo("")!=0 && txtConMedida_Agricultor.getText().compareTo("")!= 0
               && txtNumHectareas_Agricultor.getText().compareTo("")!=0
               && (cboLateral_Agricultor.getSelectedItem().toString()).compareTo("")!=0 && (cboSubLateral_Agricultor.getSelectedItem().toString()).compareTo("")!=0) {
                DefaultTableModel temporal = (DefaultTableModel) jtDetalleLaterales_Agricultor.getModel();
                Object datos[] = {
                    0,
                    cboLateral_Agricultor.getSelectedItem().toString(),
                    cboSubLateral_Agricultor.getSelectedItem().toString(),
                    txtSinMedida_Agricultor.getText(),
                    txtConMedida_Agricultor.getText(),
                    txtNumHectareas_Agricultor.getText()
                };
                temporal.addRow(datos);
                txtSinMedida_Agricultor.setText("0.0");
                txtConMedida_Agricultor.setText("0.0");
                btnEliminar_DetLateales.setEnabled(true);
            }else{
                JOptionPane.showMessageDialog(null,"No se Admiten Campos Vacios","ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void btnEliminar_DetLatealesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar_DetLatealesActionPerformed
        int fila = jtDetalleLaterales_Agricultor.getSelectedRow();
        DefaultTableModel temporal = (DefaultTableModel) jtDetalleLaterales_Agricultor.getModel();
        if (fila > 0) {
            temporal.removeRow(fila);
        } else if (fila == 0) {
            temporal.removeRow(fila);
        }
        btnEliminar_DetLateales.setEnabled(true);
    }//GEN-LAST:event_btnEliminar_DetLatealesActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (txtDNI_Agricultor.getText().compareTo("") != 0
                    && txtNombres_Agricultor.getText().compareTo("") != 0
                    && txtApePaterno_Agricultor.getText().compareTo("") != 0
                    && txtApeMaterno_Agricultor.getText().compareTo("") != 0) {
                String sexo = cboSexo_Agricultor.getSelectedItem().toString().equalsIgnoreCase("FEMENINO") ? "F" : "M";

                ArrayList<Lateral> lista_laterales = new ArrayList<Lateral>();
                int nroFilas = ((DefaultTableModel) jtDetalleLaterales_Agricultor.getModel()).getRowCount();
                for (int f = 0; f < nroFilas; f++) {
                    Lateral l = new Lateral();
                    l.setInt_id(Integer.parseInt(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 0).toString()));
                    l.setVar_lateral(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 1).toString());
                    l.setVar_sublateral(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 2).toString());
                    l.setDec_sinmedida(Double.parseDouble(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 3).toString()));
                    l.setDec_conmedida(Double.parseDouble(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 4).toString()));
                    l.setInt_numhectareas(Integer.parseInt(jtDetalleLaterales_Agricultor.getModel().getValueAt(f, 5).toString()));
                    lista_laterales.add(l);
                }
                //REGISTRAR AGRICULTOR                
                if (new BLAgricultor().RegistrarAgricultor(idAgricultor_Edit, txtNombres_Agricultor.getText(),
                        txtApeMaterno_Agricultor.getText(), txtApePaterno_Agricultor.getText(),
                        txtDireccion_Agricultor.getText(), txtEmail_Agricultor.getText(), txtDNI_Agricultor.getText(),
                        sexo, txtTelefono_Agricultor.getText(),
                        txtCelular_Agricultor.getText(), lista_laterales)) {

                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    limpiarTabla(jtAgricultor);
                    gettabla_agricultor_all("", 1);
                    idAgricultor_Edit = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "Registro Fallido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, " No se admiten campos vacios ", "Mensaje", 1);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar Orden Compra" + e.toString());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane.showMessageDialog(null, "Solo es Prueba");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtAgricultorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtAgricultorMousePressed
        if (evt.isPopupTrigger() && jtAgricultor.getModel().getRowCount() != 0
                && jtAgricultor.getSelectedRow() != -1) {
            jpmAgricultor.show(jtAgricultor, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jtAgricultorMousePressed

    private void jtAgricultorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtAgricultorMouseReleased
        if (evt.isPopupTrigger() && jtAgricultor.getModel().getRowCount() != 0
                && jtAgricultor.getSelectedRow() != -1) {
            jpmAgricultor.show(jtAgricultor, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jtAgricultorMouseReleased

    private void jmiEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEditarActionPerformed
        try {
            int idAgricultor_Edit = Integer.parseInt(jtAgricultor.getValueAt(jtAgricultor.getSelectedRow(), 0).toString());
            DefaultTableModel temp = (DefaultTableModel) jtDetalleLaterales_Agricultor.getModel();
            temp.setRowCount(0);
            for (ListaAgricultorLateral lista : new BDAgricultor().get_agricultorlateral_byid(idAgricultor_Edit)) {
                Object[] datos = {lista.getInt_idlateral(), lista.getVar_lateral(), lista.getVar_sublateral(),
                    lista.getDec_sinmedida(), lista.getDec_conmedida()};
                temp.addRow(datos);
                txtDNI_Agricultor.setText(lista.getVar_dni());
                txtNombres_Agricultor.setText(lista.getVar_nombre());
                txtApePaterno_Agricultor.setText(lista.getVar_apepaterno());
                txtApeMaterno_Agricultor.setText(lista.getVar_apematerno());
                txtDireccion_Agricultor.setText(lista.getVar_direccion());
                txtTelefono_Agricultor.setText(lista.getVar_telefono());
                txtCelular_Agricultor.setText(lista.getVar_celular());
                txtEmail_Agricultor.setText(lista.getVar_email());
                //btnEliminar_DetLateales.setEnabled(true);
            }
        } catch (Exception e) {
            System.out.println("Error de Listado Editar -vISTA" + e.getMessage());
        }
    }//GEN-LAST:event_jmiEditarActionPerformed

    private void txtFiltroAgricultorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroAgricultorKeyReleased
        gettabla_agricultor_all(txtFiltroAgricultor.getText(), cboFiltroAgricultor.getSelectedIndex());
    }//GEN-LAST:event_txtFiltroAgricultorKeyReleased

    private void btn_Guardar_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_UsuarioActionPerformed
        try {
            if (txtID_Usuario.getText().compareTo("") != 0 && txtpass_usuario.getPassword().toString().compareTo("") != 0
                    && txtdni_usuario.getText().compareTo("") != 0 && txtnombres_usuario.getText().compareTo("") != 0
                    && txtapellidos_usuario.getText().compareTo("") != 0 && txtTeleCelular_Usuario.getText().compareTo("") != 0
                    && txtFechaNacimiento_Usuario.getDate() != null) {
                if (new BLUsuario().Registrar(txtID_Usuario.getText(),
                        txtpass_usuario.getPassword().toString(),
                        txtdni_usuario.getText(),
                        txtnombres_usuario.getText(),
                        txtapellidos_usuario.getText(),
                        new java.sql.Date(txtFechaNacimiento_Usuario.getDate().getTime()),
                        txtTeleCelular_Usuario.getText(),
                        ((Cargo) cboCargo_Usuario.getSelectedItem()).getInt_id(),
                        txtDireccion_Usuario.getText(),
                        txtEmail_Usuario.getText())) {
                    limpiarFomulario_Usuario();
                    limpiarTabla(jtLista_Usuario);
                    gettabla_usuario_byfiltro("", 0);
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Registro Fallido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, " No se admiten campos vacios ", "Mensaje", 1);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar comite" + e.getMessage());
        }
    }//GEN-LAST:event_btn_Guardar_UsuarioActionPerformed

    private void btnGuardar_ComiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar_ComiteActionPerformed
        try {
            if (txtComite_Registrar.getText().compareTo("") != 0) {
                if (new BLComite().Registrar(txtComite_Registrar.getText())) {
                    limpiarTabla(jtComite_Administracion);
                    gettabla_comite_byActivos("");
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Registro Fallido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, " No se admiten campos vacios ", "Mensaje", 1);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar comite" + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardar_ComiteActionPerformed

    private void txtObservacion_RegistrarPagosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacion_RegistrarPagosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtObservacion_RegistrarPagos.setText(txtObservacion_RegistrarPagos.getText().trim());
            btn_Guardar_movimiento.requestFocus();
        }
    }//GEN-LAST:event_txtObservacion_RegistrarPagosKeyPressed

    private void btn_Cancelar_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_PagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Cancelar_PagoActionPerformed

    private void btn_Guardar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_pagoActionPerformed
        try {
            if (txtVoucher_RegistrarPago.getText().compareTo("") != 0) {
                Pago p = new Pago();
                p.setInt_id(idPago);
                p.setVar_boucherpago(txtVoucher_RegistrarPago.getText());
                p.setVar_observacion(txtObservacion_RegistrarPagos.getText());
                if (new BLPagos().RegistrarPagos(p)) {
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                    if (jrbDni_VerPagos.isSelected()) {
                        gettabla_verpagos_byAgricultor(txtFiltroDni_VerPagos.getText(), 0, 1);
                    }
                    if (jrbAgricultor_VerPagos.isSelected()) {
                        int id = ((Agricultor) cboFiltroAgricultor_VerPagos.getSelectedItem()).getInt_id();
                        gettabla_verpagos_byAgricultor("", id, 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar", "MENSAJE", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al Realizar Pago: " + e.getMessage());
        } finally {
            jdPagar.dispose();
        }
    }//GEN-LAST:event_btn_Guardar_pagoActionPerformed

    private void txtModalLateral_TraspasoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalLateral_TraspasoKeyReleased
        gettabla_lateral_byagricultoractivos(txtModalLateral_Traspaso.getText(), idAgri_Traspaso);
    }//GEN-LAST:event_txtModalLateral_TraspasoKeyReleased

    private void jtModalLateral_TraspasoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalLateral_TraspasoMouseClicked
        try {
            idLat_Traspaso = Integer.parseInt(String.valueOf(jtModalLateral_Traspaso.getModel().getValueAt(jtModalLateral_Traspaso.getSelectedRow(), 0)));
            String lateral = String.valueOf(jtModalLateral_Traspaso.getModel().getValueAt(jtModalLateral_Traspaso.getSelectedRow(), 1));
            String sublateral = String.valueOf(jtModalLateral_Traspaso.getModel().getValueAt(jtModalLateral_Traspaso.getSelectedRow(), 2));
            String hec = String.valueOf(jtModalLateral_Traspaso.getModel().getValueAt(jtModalLateral_Traspaso.getSelectedRow(), 5));
            txtLateralCliente_Traspaso.setText(lateral);
            txtSubLateralAgricultor_Traspo.setText(sublateral);
            txtNroHectares_Traspaso.setText(hec);
        } catch (Exception e) {
            System.out.println("Error al obtener los datos de Lateral for Traspaso" + e.getMessage());
        } finally {
            jdTraspasoLateral.dispose();
        }
    }//GEN-LAST:event_jtModalLateral_TraspasoMouseClicked

    private void txtModalAgricultorNuevo_TraspasoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalAgricultorNuevo_TraspasoKeyReleased
        try {
            get_agricultores_byActivos(txtModalAgricultorNuevo_Traspaso.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtModalAgricultorNuevo_TraspasoKeyReleased

    private void jtModalAgricultorNuevo_TraspasoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalAgricultorNuevo_TraspasoMouseClicked
        try {
            idNuevoAgricultor_Traspaso = Integer.parseInt(String.valueOf(jtModalAgricultorNuevo_Traspaso.getModel().getValueAt(jtModalAgricultorNuevo_Traspaso.getSelectedRow(), 0)));
            txtNuevoAgricultor_Traspaso.setText(String.valueOf(jtModalAgricultorNuevo_Traspaso.getModel().getValueAt(jtModalAgricultorNuevo_Traspaso.getSelectedRow(), 1)));
            System.out.println(idNuevoAgricultor_Traspaso);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            jdTraspasoNuevoAgricultor.dispose();
        }
    }//GEN-LAST:event_jtModalAgricultorNuevo_TraspasoMouseClicked

    private void jtModalAgricultorNuevo_TraspasoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtModalAgricultorNuevo_TraspasoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtModalAgricultorNuevo_TraspasoKeyPressed

    private void txtModalAgricultor_TraspasoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalAgricultor_TraspasoKeyReleased
        get_agricultores_byActivos(txtModalAgricultor_Traspaso.getText());
    }//GEN-LAST:event_txtModalAgricultor_TraspasoKeyReleased

    private void jtModalAgricultor_TraspasoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalAgricultor_TraspasoMouseClicked
        try {
            idAgri_Traspaso = Integer.parseInt(String.valueOf(jtModalAgricultor_Traspaso.getModel().getValueAt(jtModalAgricultor_Traspaso.getSelectedRow(), 0)));
            txtAgricultor_Traspaso.setText(String.valueOf(jtModalAgricultor_Traspaso.getModel().getValueAt(jtModalAgricultor_Traspaso.getSelectedRow(), 1)));
            btn_Traspaso_ModalLateral.setEnabled(true);
            System.out.println(idAgri_Traspaso);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            jdTraspasoAgricultor.dispose();
        }
    }//GEN-LAST:event_jtModalAgricultor_TraspasoMouseClicked

    private void jtModalAgricultor_TraspasoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtModalAgricultor_TraspasoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtModalAgricultor_TraspasoKeyPressed

    private void txtModalLateral_ConstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalLateral_ConstanciaKeyReleased
        gettabla_lateral_byagricultoractivos(txtModalLateral_Constancia.getText(), idCliente_Constancia);
    }//GEN-LAST:event_txtModalLateral_ConstanciaKeyReleased

    private void jtModalLateral_ConstanciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalLateral_ConstanciaMouseClicked
        try {
            idLateral_Constancia = Integer.parseInt(String.valueOf(jtModalLateral_Constancia.getModel().getValueAt(jtModalLateral_Constancia.getSelectedRow(), 0)));
            String lateral = String.valueOf(jtModalLateral_Constancia.getModel().getValueAt(jtModalLateral_Constancia.getSelectedRow(), 1));
            String sublateral = String.valueOf(jtModalLateral_Constancia.getModel().getValueAt(jtModalLateral_Constancia.getSelectedRow(), 2));
            String concat = lateral + " - " + sublateral;
            String hec = String.valueOf(jtModalLateral_Constancia.getModel().getValueAt(jtModalLateral_Constancia.getSelectedRow(), 5));
            txtLateral_Constancia.setText(concat);
            txtHectareas_Constancia.setText(hec);

            ListaCuentaMonto c = new ListaCuentaMonto();
            c = new BLCuenta().get_cuentamonto_all(Double.parseDouble(hec));
            txtMontoComision_Constancia.setText(String.valueOf(c.getMontocomision()));
            txtMontoJunta_Constancia.setText(String.valueOf(c.getMontojunta()));
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        } finally {
            jdConstanciaLateral.dispose();
        }
    }//GEN-LAST:event_jtModalLateral_ConstanciaMouseClicked

    private void txtModalComite_ConstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModalComite_ConstanciaKeyReleased
        gettabla_comite_byActivos(txtModalComite_Constancia.getText());
    }//GEN-LAST:event_txtModalComite_ConstanciaKeyReleased

    private void jtModalComite_ConstanciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtModalComite_ConstanciaMouseClicked
        try {
            idComite_Constancia = Integer.parseInt(String.valueOf(jtModalComite_Constancia.getModel().getValueAt(jtModalComite_Constancia.getSelectedRow(), 0)));
            txtComite_Constancia.setText(String.valueOf(jtModalComite_Constancia.getModel().getValueAt(jtModalComite_Constancia.getSelectedRow(), 1)));
            jdConstanciaComite.dispose();
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }//GEN-LAST:event_jtModalComite_ConstanciaMouseClicked

    private void jtModalComite_ConstanciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtModalComite_ConstanciaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                idComite_Constancia = Integer.parseInt(String.valueOf(jtModalComite_Constancia.getModel().getValueAt(jtModalComite_Constancia.getSelectedRow(), 0)));
                txtComite_Constancia.setText(String.valueOf(jtModalComite_Constancia.getModel().getValueAt(jtModalComite_Constancia.getSelectedRow(), 1)));
                jdConstanciaComite.dispose();
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
    }//GEN-LAST:event_jtModalComite_ConstanciaKeyPressed

    private void txtMonto_MovimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonto_MovimientoKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtMonto_Movimiento.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMonto_MovimientoKeyTyped

    private void txtConcepto_MovimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConcepto_MovimientoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtConcepto_Movimiento.setText(txtConcepto_Movimiento.getText().trim());
            btn_Guardar_movimiento.requestFocus();
        }
    }//GEN-LAST:event_txtConcepto_MovimientoKeyPressed

    private void btn_Cancelar_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_movimientoActionPerformed
        limpiarFomulario_Movimiento();
    }//GEN-LAST:event_btn_Cancelar_movimientoActionPerformed

    private void btn_Guardar_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Guardar_movimientoActionPerformed
        try {
            if (txtFecha_Movimiento.getDate() != null && txtMonto_Movimiento.getText().compareTo("") != 0
                    && txtConcepto_Movimiento.getText().compareTo("") != 0) {
                Date fecha = txtFecha_Movimiento.getDate();
                int tipo_comprobante = ((Constante) cboTipoComprobante_Movimiento.getSelectedItem()).getInt_valor();
                int proveedor = 1;
                int tipo_operacion = ((Constante) cboTipoOperacion_Movimiento.getSelectedItem()).getInt_valor();
                double monto = Double.parseDouble(txtRucProveedor_Movimiento.getText());
                String nro_comprobante = txtNroComprobante_Movimiento.getText();
                double cantidad = txtCantidad_Movimientos.getValue();
                String concepto = txtConcepto_Movimiento.getText();
                if (new BLMovimiento().insertarMovimiento(1, monto, tipo_operacion, tipo_comprobante, nro_comprobante,
                        cantidad, proveedor, concepto, new java.sql.Timestamp(fecha.getTime()))) {
                    JOptionPane.showMessageDialog(null, "Registro Exitoso", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar", "MENSAJE", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se admite campos vacios", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar Cuenta" + e.toString());
        }
    }//GEN-LAST:event_btn_Guardar_movimientoActionPerformed

    private void btnAgregarDet_AlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDet_AlquilerActionPerformed
        try {
            if (txtMonto_Alquiler.getText().compareTo("") != 0 && txtFechaDesde_Alquiler.getDate() != null
                    && txtFechaHasta_Alquiler.getDate() != null) {
                DefaultTableModel temporal = (DefaultTableModel) jtbDetalle_Alquiler.getModel();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Object datos[] = {
                    ((Material) cboTipoMaterial_Alquiler.getSelectedItem()).getInt_id(),
                    ((Material) cboTipoMaterial_Alquiler.getSelectedItem()).getVar_nombre(),
                    txtCantidad_Alquiler.getValue(),
                    sdf.format(txtFechaDesde_Alquiler.getDate()),
                    sdf.format(txtFechaHasta_Alquiler.getDate()),
                    txtHoras_Alquiler.getValue(),
                    txtMonto_Alquiler.getText()
                };
                temporal.addRow(datos);
                txtMonto_Alquiler.setText("");
                btnEliminarDet_Alquiler.setEnabled(true);
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }//GEN-LAST:event_btnAgregarDet_AlquilerActionPerformed

    private void btn_Cancelar_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cancelar_UsuarioActionPerformed
        limpiarFomulario_Usuario();
    }//GEN-LAST:event_btn_Cancelar_UsuarioActionPerformed

    private void btnEliminarDet_AlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDet_AlquilerActionPerformed
        int fila = jtbDetalle_Alquiler.getSelectedRow();
        DefaultTableModel temporal = (DefaultTableModel) jtbDetalle_Alquiler.getModel();
        if (fila > 0) {
            temporal.removeRow(fila);
        } else if (fila == 0) {
            temporal.removeRow(fila);
        }
        btnEliminar_DetLateales.setEnabled(true);
    }//GEN-LAST:event_btnEliminarDet_AlquilerActionPerformed

    private void chkFiltroFecha_AlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFiltroFecha_AlquilerActionPerformed
        if (chkFiltroFecha_Alquiler.isSelected()) {
            txtFechaInicio_Alquiler.setEnabled(true);
            txtFechaFin_Alquiler.setEnabled(true);
        } else {
            txtFechaInicio_Alquiler.setEnabled(false);
            txtFechaFin_Alquiler.setEnabled(false);
        }
    }//GEN-LAST:event_chkFiltroFecha_AlquilerActionPerformed

    private void chkFiltroAgricultor_AlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFiltroAgricultor_AlquilerActionPerformed
        if (chkFiltroAgricultor_Alquiler.isSelected()) {
            cboAgricultor_Alquiler.setEnabled(true);
        } else {
            cboAgricultor_Alquiler.setEnabled(false);
        }
    }//GEN-LAST:event_chkFiltroAgricultor_AlquilerActionPerformed

    private void txtFiltro_UsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltro_UsuarioKeyReleased
        gettabla_usuario_byfiltro(txtFiltro_Usuario.getText(), cboTipoFiltro_Usuario.getSelectedIndex());
    }//GEN-LAST:event_txtFiltro_UsuarioKeyReleased

    private void txtFiltroComite_AdministracionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroComite_AdministracionKeyReleased
        gettabla_comite_byActivos(txtFiltroComite_Administracion.getText());
    }//GEN-LAST:event_txtFiltroComite_AdministracionKeyReleased

    private void txtBuscarCargoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCargoKeyReleased
        getcombo_cargo_all(txtBuscarCargo.getText());
    }//GEN-LAST:event_txtBuscarCargoKeyReleased

    private void txtHectareas_ConstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHectareas_ConstanciaKeyReleased
        try {
            BLCuenta c = new BLCuenta();
            ListaCuentaMonto objcumon = new ListaCuentaMonto();
            objcumon = c.get_cuentamonto_all(Double.parseDouble(txtHectareas_Constancia.getText()));
            txtMontoComision_Constancia.setText(String.valueOf(objcumon.getMontocomision()));
            txtMontoJunta_Constancia.setText(String.valueOf(objcumon.getMontojunta()));
        } catch (Exception e) {
            System.out.println("Error de Calculo" + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtHectareas_ConstanciaKeyReleased

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void txtFiltroDni_VerPagosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroDni_VerPagosKeyTyped
        new Funciones().soloNumeros(evt);
        if (txtFiltroDni_VerPagos.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFiltroDni_VerPagosKeyTyped

    private void txtNombres_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombres_AgricultorKeyTyped
         new Funciones().soloLetras(evt);
        if (txtNombres_Agricultor.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombres_AgricultorKeyTyped

    private void txtApePaterno_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApePaterno_AgricultorKeyTyped
         new Funciones().soloLetras(evt);
        if (txtApeMaterno_Agricultor.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApePaterno_AgricultorKeyTyped

    private void txtApeMaterno_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeMaterno_AgricultorKeyTyped
         new Funciones().soloLetras(evt);
        if (txtFiltroDni_VerPagos.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApeMaterno_AgricultorKeyTyped

    private void txtSinMedida_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSinMedida_AgricultorKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtSinMedida_Agricultor.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSinMedida_AgricultorKeyTyped

    private void txtConMedida_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConMedida_AgricultorKeyTyped
        new Funciones().soloDecimales(evt);
        if (txtConMedida_Agricultor.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtConMedida_AgricultorKeyTyped

    private void txtNumHectareas_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumHectareas_AgricultorKeyTyped
         new Funciones().soloDecimales(evt);
        if (txtNumHectareas_Agricultor.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumHectareas_AgricultorKeyTyped

    private void txtDescripcionCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionCargoKeyTyped
         new Funciones().soloLetras(evt);
        if (txtFiltroDni_VerPagos.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescripcionCargoKeyTyped

    private void txtMontoInicial_InicioCierreCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoInicial_InicioCierreCajaKeyTyped
         new Funciones().soloDecimales(evt);
        if (txtMontoInicial_InicioCierreCaja.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoInicial_InicioCierreCajaKeyTyped

    private void txtMonto_AsignarCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonto_AsignarCuentaKeyTyped
         new Funciones().soloDecimales(evt);
        if (txtMonto_AsignarCuenta.getText().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMonto_AsignarCuentaKeyTyped

    private void txtdni_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdni_usuarioKeyTyped
         new Funciones().soloNumeros(evt);
        if (txtdni_usuario.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtdni_usuarioKeyTyped

    private void txtnombres_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombres_usuarioKeyTyped
         new Funciones().soloLetras(evt);
        if (txtnombres_usuario.getText().length() == 80) {
            evt.consume();
        }
    }//GEN-LAST:event_txtnombres_usuarioKeyTyped

    private void txtapellidos_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapellidos_usuarioKeyTyped
         new Funciones().soloLetras(evt);
        if (txtapellidos_usuario.getText().length() == 80) {
            evt.consume();
        }
    }//GEN-LAST:event_txtapellidos_usuarioKeyTyped

    private void txtID_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtID_UsuarioKeyTyped

        if (txtID_Usuario.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtID_UsuarioKeyTyped

    private void txtpass_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpass_usuarioKeyTyped
        if (txtpass_usuario.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtpass_usuarioKeyTyped

    private void txtTeleCelular_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTeleCelular_UsuarioKeyTyped
       if (txtTeleCelular_Usuario.getText().length() == 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTeleCelular_UsuarioKeyTyped

    private void txtDireccion_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccion_UsuarioKeyTyped
        if (txtDireccion_Usuario.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccion_UsuarioKeyTyped

    private void txtEmail_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmail_UsuarioKeyTyped
       if (txtEmail_Usuario.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmail_UsuarioKeyTyped

    private void txtNumDocumento_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocumento_TraspasoKeyTyped
        if (txtNumDocumento_Traspaso.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumDocumento_TraspasoKeyTyped

    private void txtNuevoLateral_TraspasoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoLateral_TraspasoKeyTyped
        if (txtNuevoLateral_Traspaso.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoLateral_TraspasoKeyTyped

    private void txtDNI_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNI_AgricultorKeyTyped
        if (txtDNI_Agricultor.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDNI_AgricultorKeyTyped

    private void txtNuevoConMedida_TraspasoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoConMedida_TraspasoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoConMedida_TraspasoKeyPressed

    private void txtNuevoConMedida_TraspasoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoConMedida_TraspasoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoConMedida_TraspasoKeyReleased

    private void txtTelefono_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono_AgricultorKeyTyped
       if (txtTelefono_Agricultor.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefono_AgricultorKeyTyped

    private void txtCelular_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelular_AgricultorKeyTyped
        if (txtCelular_Agricultor.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCelular_AgricultorKeyTyped

    private void txtDireccion_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccion_AgricultorKeyTyped
        if (txtDireccion_Agricultor.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccion_AgricultorKeyTyped

    private void txtEmail_AgricultorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmail_AgricultorKeyTyped
        if (txtEmail_Agricultor.getText().length() == 35) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmail_AgricultorKeyTyped

    private void txtCodigo_CuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigo_CuentaKeyTyped
        if (txtCodigo_Cuenta.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigo_CuentaKeyTyped

    private void txtNumCuenta_RegistrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumCuenta_RegistrarKeyTyped
         if (txtNumCuenta_Registrar.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumCuenta_RegistrarKeyTyped

    private void txtNombre_CuentasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre_CuentasKeyTyped
        if (txtNombre_Cuentas.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombre_CuentasKeyTyped

    private void txtConcepto_AsignarCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConcepto_AsignarCostoKeyTyped
       if (txtConcepto_AsignarCosto.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtConcepto_AsignarCostoKeyTyped

    private void txtComite_RegistrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComite_RegistrarKeyTyped
        if (txtComite_Registrar.getText().length() == 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txtComite_RegistrarKeyTyped

    /*METODOS PARA MOSTRAR EL FORMULARIO*/
    public void iniciarFomrulario(JInternalFrame jif) {
        try {
            jif.setSize(1014, 650);
            jdeskpanInicio.add(jif);
            //jif.setMaximum(true);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
            System.out.println("" + e.getMessage());
        }
    }

    public void iniciarFomrulario_Constancia(JInternalFrame jif) {
        try {
            jif.setSize(1120, 650);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_VerPagos(JInternalFrame jif) {
        try {
            jif.setSize(1013, 516);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Traspaso(JInternalFrame jif) {
        try {
            jif.setSize(915, 590);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Alquiler(JInternalFrame jif) {
        try {
            jif.setSize(822, 535);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_PadronAsistencia(JInternalFrame jif) {
        try {
            jif.setSize(833, 485);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Movimiento(JInternalFrame jif) {
        try {
            jif.setSize(610, 461);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Cuentas(JInternalFrame jif) {
        try {
            jif.setSize(678, 531);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Agricultor(JInternalFrame jif) {
        try {
            jif.setSize(920, 530);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Cargo(JInternalFrame jif) {
        try {
            jif.setSize(532, 431);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Usuario(JInternalFrame jif) {
        try {
            jif.setSize(689, 534);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_CierreInicioCaja(JInternalFrame jif) {
        try {
            jif.setSize(472, 239);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Periodo(JInternalFrame jif) {
        try {
            jif.setSize(711, 471);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    public void iniciarFomrulario_Comite(JInternalFrame jif) {
        try {
            jif.setSize(709, 457);
            jdeskpanInicio.add(jif);
            jif.setVisible(true);
            int x = (jdeskpanInicio.getWidth() / 2) - (jif.getWidth() / 2);
            int y = (jdeskpanInicio.getHeight() / 2) - (jif.getHeight() / 2);
            jif.setLocation(x, y);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }
    /*FIN DE METODOS PARA MOSTRAR EL FORMULARIO*/

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Codigo2;
    private javax.swing.JLabel Codigo3;
    private javax.swing.JButton btnAgregarDet_Alquiler;
    private javax.swing.JButton btnBuscarAgricultor_Alquiler;
    private javax.swing.JButton btnBuscarFiltro_Constancia;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelar5;
    private javax.swing.JButton btnCancelar6;
    private javax.swing.JButton btnEliminarDet_Alquiler;
    private javax.swing.JButton btnEliminar_DetLateales;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardar5;
    private javax.swing.JButton btnGuardar6;
    private javax.swing.JButton btnGuardar_Comite;
    private javax.swing.ButtonGroup btnTipodeSembrio;
    private javax.swing.JButton btn_Buscar_Traspaso;
    private javax.swing.JButton btn_Cancelar1;
    private javax.swing.JButton btn_Cancelar_Constancia;
    private javax.swing.JButton btn_Cancelar_Pago;
    private javax.swing.JButton btn_Cancelar_Traspaso;
    private javax.swing.JButton btn_Cancelar_Usuario;
    private javax.swing.JButton btn_Cancelar_movimiento;
    private javax.swing.JButton btn_Guardar_Constancia;
    private javax.swing.JButton btn_Guardar_Traspaso;
    private javax.swing.JButton btn_Guardar_Usuario;
    private javax.swing.JButton btn_Guardar_movimiento;
    private javax.swing.JButton btn_Guardar_pago;
    private javax.swing.JButton btn_Imprimir_pagos;
    private javax.swing.JButton btn_ModalCliente_Constancia;
    private javax.swing.JButton btn_ModalComite_Constancia;
    private javax.swing.JButton btn_ModalLateral_Constancia;
    private javax.swing.JButton btn_Registrar1;
    private javax.swing.JButton btn_Traspaso_ModalAgricultor;
    private javax.swing.JButton btn_Traspaso_ModalLateral;
    private javax.swing.JButton btn_Traspaso_ModalNuevoAgricultor;
    private javax.swing.JButton btn_buscar_alquileres;
    private javax.swing.JButton btn_buscar_pagos;
    private org.jdesktop.swingx.JXComboBox cboAgricultorFiltro_Constancia;
    private org.jdesktop.swingx.JXComboBox cboAgricultor_Alquiler;
    private org.jdesktop.swingx.JXComboBox cboAntiguoAgricultor_Traspaso;
    private org.jdesktop.swingx.JXComboBox cboCargo_Usuario;
    private org.jdesktop.swingx.JXComboBox cboCuentas_AsignarCostos;
    private javax.swing.JComboBox cboEstado_VerPagos;
    private javax.swing.JComboBox cboFiltroAgricultor;
    private org.jdesktop.swingx.JXComboBox cboFiltroAgricultor_VerPagos;
    private org.jdesktop.swingx.JXComboBox cboLateral_Agricultor;
    private org.jdesktop.swingx.JXComboBox cboNuevoAgricultor_Traspaso;
    private org.jdesktop.swingx.JXComboBox cboPeriodoFiltro_Constancia;
    private org.jdesktop.swingx.JXComboBox cboSexo_Agricultor;
    private org.jdesktop.swingx.JXComboBox cboSubLateral_Agricultor;
    private javax.swing.JComboBox cboTipoComprobante_Movimiento;
    private org.jdesktop.swingx.JXComboBox cboTipoCultivo_Constancia;
    private javax.swing.JComboBox cboTipoFiltro_Usuario;
    private org.jdesktop.swingx.JXComboBox cboTipoMaterial_Alquiler;
    private org.jdesktop.swingx.JXComboBox cboTipoOperacion_Movimiento;
    private javax.swing.JCheckBox chkAntiguoDuenio_Agricultor;
    private javax.swing.JCheckBox chkAntiguoNuevo_Agricultor;
    private javax.swing.JCheckBox chkCampania_Constancia;
    private javax.swing.JCheckBox chkCliente_Constancia;
    private javax.swing.JCheckBox chkFecha_Constancia;
    private javax.swing.JCheckBox chkFiltroAgricultor_Alquiler;
    private javax.swing.JCheckBox chkFiltroFecha_Alquiler;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTable jTable8;
    private org.jdesktop.swingx.JXComboBox jXComboBox1;
    private org.jdesktop.swingx.JXComboBox jXComboBox2;
    private org.jdesktop.swingx.JXComboBox jXComboBox5;
    private org.jdesktop.swingx.JXSearchField jXSearchField5;
    private javax.swing.JDialog jdAlquilerAgricultor;
    private javax.swing.JDialog jdConstanciaAgricultor;
    private javax.swing.JDialog jdConstanciaComite;
    private javax.swing.JDialog jdConstanciaLateral;
    private javax.swing.JDialog jdPagar;
    private javax.swing.JDialog jdTraspasoAgricultor;
    private javax.swing.JDialog jdTraspasoLateral;
    private javax.swing.JDialog jdTraspasoNuevoAgricultor;
    private javax.swing.JDesktopPane jdeskpanInicio;
    private javax.swing.JInternalFrame jifAgricultores;
    private javax.swing.JInternalFrame jifCargos;
    private javax.swing.JInternalFrame jifComites;
    private javax.swing.JInternalFrame jifConstancia;
    private javax.swing.JInternalFrame jifCuentas;
    private javax.swing.JInternalFrame jifDocumento;
    private javax.swing.JInternalFrame jifIngresarAlquiler;
    private javax.swing.JInternalFrame jifIngresarPadronAsistencia;
    private javax.swing.JInternalFrame jifInicioCierreCaja;
    private javax.swing.JInternalFrame jifMovimientos;
    private javax.swing.JInternalFrame jifPeriodos;
    private javax.swing.JPanel jifRegistrarAlquiler;
    private javax.swing.JInternalFrame jifTraspaso;
    private javax.swing.JInternalFrame jifUsuario;
    private javax.swing.JInternalFrame jifVerPagos;
    private javax.swing.JMenu jmAdministracion;
    private javax.swing.JMenu jmCaja;
    private javax.swing.JMenu jmConstancia;
    private javax.swing.JMenu jmInicio;
    private javax.swing.JMenu jmPagos;
    private javax.swing.JMenuBar jmbPrincipal;
    private javax.swing.JMenuItem jmiAgricultor;
    private javax.swing.JMenuItem jmiAlquiler;
    private javax.swing.JMenuItem jmiCargos;
    private javax.swing.JMenuItem jmiClientes;
    private javax.swing.JMenuItem jmiComite;
    private javax.swing.JMenuItem jmiCuentas;
    private javax.swing.JMenuItem jmiEditar;
    private javax.swing.JMenuItem jmiInicioCierre;
    private javax.swing.JMenuItem jmiMovimiento;
    private javax.swing.JMenuItem jmiMovimientos;
    private javax.swing.JMenuItem jmiPagos;
    private javax.swing.JMenuItem jmiPeriodo;
    private javax.swing.JMenuItem jmiRegistro;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuItem jmiTraspaso;
    private javax.swing.JMenuItem jmiUsuario;
    private javax.swing.JMenuItem jmiVerPagos;
    private javax.swing.JMenuItem jmip_Anular;
    private javax.swing.JMenuItem jmip_GenerarDocumento;
    private javax.swing.JMenuItem jmip_Pagar;
    private javax.swing.JPanel jpBuscarAgricultorNuevo_Traspaso;
    private javax.swing.JPanel jpBuscarAgricultor_Traspaso;
    private javax.swing.JPanel jpBuscarAgricultor_Traspaso1;
    private javax.swing.JPanel jpBuscarComite;
    private javax.swing.JPanel jpConstanciaLateral;
    private javax.swing.JPanel jpConstancia_Registro;
    private javax.swing.JPanel jpInicio;
    private javax.swing.JPanel jpInicioCierre;
    private javax.swing.JPanel jpLaterales;
    private javax.swing.JPanel jpMovimiento;
    private javax.swing.JMenu jpReportes;
    private javax.swing.JPanel jpTraspaso;
    private javax.swing.JPanel jpTraspasoLateral;
    private javax.swing.JPanel jpUsurio;
    private javax.swing.JPanel jpVerPagos;
    private javax.swing.JPopupMenu jpmAgricultor;
    private javax.swing.JPopupMenu jpmVerPagos;
    private javax.swing.JRadioButton jrbAgricultor_VerPagos;
    private javax.swing.JRadioButton jrbDni_VerPagos;
    private javax.swing.JTable jtAgricultor;
    private javax.swing.JTable jtAsignarCosto_Cuentas;
    private javax.swing.JTable jtBusqueda_Constancia;
    private javax.swing.JTable jtCargos_Administracion;
    private javax.swing.JTable jtComite_Administracion;
    private javax.swing.JTable jtCuentas;
    private javax.swing.JTable jtDetalleLaterales_Agricultor;
    private javax.swing.JTable jtLista_Alquileres;
    private javax.swing.JTable jtLista_Usuario;
    private javax.swing.JTable jtModalAgricultorNuevo_Traspaso;
    private javax.swing.JTable jtModalAgricultor_Alquiler;
    private javax.swing.JTable jtModalAgricultor_Constancia;
    private javax.swing.JTable jtModalAgricultor_Traspaso;
    private javax.swing.JTable jtModalComite_Constancia;
    private javax.swing.JTable jtModalLateral_Constancia;
    private javax.swing.JTable jtModalLateral_Traspaso;
    private javax.swing.JTable jtTraspaso;
    private javax.swing.JTable jtVerPagos;
    private javax.swing.JTabbedPane jtbAlquiler;
    private javax.swing.JTable jtbDetalle_Alquiler;
    private javax.swing.JTabbedPane jtbTraspaso;
    private javax.swing.JRadioButton rbAlmacigo_Constancia;
    private javax.swing.JRadioButton rbBoleo_Constancia;
    private javax.swing.ButtonGroup rb_group;
    private javax.swing.JTextField txtAgricultor_Alquiler;
    private javax.swing.JTextField txtAgricultor_Traspaso;
    private javax.swing.JTextField txtApeMaterno_Agricultor;
    private javax.swing.JTextField txtApePaterno_Agricultor;
    private org.jdesktop.swingx.JXSearchField txtBuscarCargo;
    private javax.swing.JTextField txtCampania_Constancia;
    private javax.swing.JTextField txtCantidadHectaria_Traspaso;
    private com.toedter.components.JSpinField txtCantidad_Alquiler;
    private com.toedter.components.JSpinField txtCantidad_Movimientos;
    private javax.swing.JTextField txtCelular_Agricultor;
    private javax.swing.JTextField txtCliente_Constancia;
    private org.jdesktop.swingx.JXTextField txtCodigo_Cuenta;
    private javax.swing.JTextField txtComite_Constancia;
    private javax.swing.JTextField txtComite_Registrar;
    private javax.swing.JTextField txtConMedida_Agricultor;
    private javax.swing.JTextArea txtConcepto_AsignarCosto;
    private javax.swing.JTextArea txtConcepto_Movimiento;
    private org.jdesktop.swingx.JXSearchField txtDNI_Agricultor;
    private javax.swing.JTextField txtDescripcionCargo;
    private javax.swing.JTextField txtDireccion_Agricultor;
    private javax.swing.JTextField txtDireccion_Usuario;
    private javax.swing.JTextField txtEmail_Agricultor;
    private javax.swing.JTextField txtEmail_Usuario;
    private javax.swing.JTextField txtEmpleadoAgricultor_Alquiler;
    private com.toedter.calendar.JDateChooser txtFechaAlmacigo_constancia;
    private com.toedter.calendar.JDateChooser txtFechaDesde_Alquiler;
    private com.toedter.calendar.JDateChooser txtFechaFin_Alquiler;
    private com.toedter.calendar.JDateChooser txtFechaHasta_Alquiler;
    private com.toedter.calendar.JDateChooser txtFechaInicio_Alquiler;
    private com.toedter.calendar.JDateChooser txtFechaNacimiento_Usuario;
    private com.toedter.calendar.JDateChooser txtFecha_Constancia;
    private com.toedter.calendar.JDateChooser txtFecha_Movimiento;
    private com.toedter.calendar.JDateChooser txtFecha_RegistrarPagos;
    private org.jdesktop.swingx.JXSearchField txtFiltroAgricultor;
    private org.jdesktop.swingx.JXSearchField txtFiltroComite_Administracion;
    private javax.swing.JTextField txtFiltroDni_VerPagos;
    private com.toedter.calendar.JDateChooser txtFiltroFin_Constancia;
    private com.toedter.calendar.JDateChooser txtFiltroInicio_Constancia;
    private org.jdesktop.swingx.JXSearchField txtFiltroNombre_Cuenta2;
    private org.jdesktop.swingx.JXSearchField txtFiltro_Usuario;
    private javax.swing.JTextField txtHectareas_Constancia;
    private com.toedter.components.JSpinField txtHoras_Alquiler;
    private javax.swing.JTextField txtID_Usuario;
    private javax.swing.JTextField txtLateralCliente_Traspaso;
    private javax.swing.JTextField txtLateral_Constancia;
    private javax.swing.JTextField txtModalAgricultorNuevo_Traspaso;
    private javax.swing.JTextField txtModalAgricultor_Alquiler;
    private javax.swing.JTextField txtModalAgricultor_Traspaso;
    private javax.swing.JTextField txtModalCliente_Constancia;
    private javax.swing.JTextField txtModalComite_Constancia;
    private javax.swing.JTextField txtModalLateral_Constancia;
    private javax.swing.JTextField txtModalLateral_Traspaso;
    private javax.swing.JTextField txtMontoComision_Constancia;
    private org.jdesktop.swingx.JXTextField txtMontoInicial_InicioCierreCaja;
    private javax.swing.JTextField txtMontoJunta_Constancia;
    private org.jdesktop.swingx.JXTextField txtMonto_Alquiler;
    private org.jdesktop.swingx.JXTextField txtMonto_AsignarCuenta;
    private org.jdesktop.swingx.JXTextField txtMonto_Movimiento;
    private javax.swing.JTextField txtMonto_Pago;
    private javax.swing.JTextField txtNombre_Cuentas;
    private javax.swing.JTextField txtNombre_Periodo;
    private javax.swing.JTextField txtNombres_Agricultor;
    private javax.swing.JTextField txtNroComprobante_Movimiento;
    private javax.swing.JTextField txtNroHectares_Traspaso;
    private javax.swing.JTextField txtNuevoAgricultor_Traspaso;
    private javax.swing.JTextField txtNuevoConMedida_Traspaso;
    private javax.swing.JTextField txtNuevoLateral_Traspaso;
    private javax.swing.JTextField txtNuevoSinMedida_Traspaso;
    private javax.swing.JTextField txtNuevoSubLateral_Traspaso;
    private org.jdesktop.swingx.JXTextField txtNumCuenta_Registrar;
    private javax.swing.JTextField txtNumDocumento_Traspaso;
    private javax.swing.JTextField txtNumHectareas_Agricultor;
    private javax.swing.JTextArea txtObservacion_RegistrarPagos;
    private javax.swing.JTextArea txtObservacion_Traspaso;
    private javax.swing.JTextField txtPeriodoRango_Constancia;
    private javax.swing.JTextField txtRucProveedor_Movimiento;
    private javax.swing.JTextField txtSinMedida_Agricultor;
    private javax.swing.JTextField txtSubLateralAgricultor_Traspo;
    private javax.swing.JTextField txtTeleCelular_Usuario;
    private javax.swing.JTextField txtTelefono_Agricultor;
    private javax.swing.JTextField txtVoucher_RegistrarPago;
    private javax.swing.JTextField txtapellidos_usuario;
    private javax.swing.JTextField txtdni_usuario;
    private javax.swing.JTextField txtnombres_usuario;
    private javax.swing.JPasswordField txtpass_usuario;
    // End of variables declaration//GEN-END:variables
}
