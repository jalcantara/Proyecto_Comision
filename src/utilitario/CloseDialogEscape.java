/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

/**
 *
 * @author Richard
 */
public class CloseDialogEscape implements ActionListener {

    private JDialog dialog = null;

    public CloseDialogEscape(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       dialog.dispose();
    }
}