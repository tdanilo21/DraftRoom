package raf.draft.dsw.gui.swing.dialogs;

import javax.swing.*;

public class CreateNodePane {

    public static String[] showDialog(String title, String[] fields){
        Object[] message = new Object[2*fields.length];
        for (int i = 0; i < fields.length; i++){
            message[2*i] = STR."\{fields[i]}:";
            message[2*i+1] = new JTextField();
        }
        int choice = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION){
            String[] result = new String[fields.length];
            for (int i = 0; i < fields.length; i++)
                result[i] = ((JTextField)message[2*i+1]).getText();
            return result;
        }
        return null;
    }
}
