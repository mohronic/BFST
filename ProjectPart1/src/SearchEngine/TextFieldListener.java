/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class TextFieldListener implements DocumentListener
{
    private JTextField textField;
    
    TextFieldListener(JTextField searchLabel)
    {
        this.textField = searchLabel;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        System.out.println("InsertUpdate!");
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        System.out.println("RemoveUpdate!");
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        System.out.println("ChangedUpdate");
    }
    
}
