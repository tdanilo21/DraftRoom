package raf.draft.dsw.gui.swing.dialogs;

import javax.swing.*;

public class RequestDimensionsPane {

    public static double[] showDialog(String title, String[] fields, double[] defaults) throws NumberFormatException{
        Object[] message = new Object[2*fields.length];
        for (int i = 0; i < fields.length; i++){
            message[2*i] = STR."\{fields[i]}:";
            JTextField textField = new JTextField();
            if (defaults != null) textField.setText(String.valueOf(defaults[i]));
            message[2*i+1] = textField;
        }
        int choice = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION){
            double[] result = new double[fields.length];
            for (int i = 0; i < fields.length; i++)
                result[i] = Double.parseDouble(((JTextField) message[2 * i + 1]).getText());
            return result;
        }
        return null;
    }
}
