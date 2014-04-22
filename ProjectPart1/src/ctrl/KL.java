/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import SearchEngine.SearchLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class KL implements KeyListener
{
    private final SearchLabel searchLabel;
    
    public KL(SearchLabel searchLabel)
    {
        this.searchLabel = searchLabel;
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        //Does Nothing
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        
//        if(searchLabel.getCurrentText().length() > 0)
//        {
//            System.out.println("Called...");
//            searchLabel.updateCurrentString();
//            searchLabel.autoComplete();
//        }
        
        System.out.println(e.getKeyCode());
        //if(e.getKeyCode() == 17)
        //{
            //System.out.println("Called...");
            //searchLabel.updateCurrentString();
            //searchLabel.autoComplete();
        //}

        
        if(e.getKeyCode() == 10) //10 = keycode for Enter-button
        {
            //Should be written to a method call
            System.out.println(e.getKeyCode());
            System.out.println(searchLabel.getText());
            System.out.println(searchLabel.checkRoadName(searchLabel.getText()));
            //System.out.println(searchLabel.autoComplete(searchLabel.getText()));
            //System.out.println((searchLabel.autoComplete(searchLabel.getText()))-(searchLabel.getText()));
        
            System.out.println("Called1");
            
        
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()!=8)
        {
            searchLabel.updateCurrentString();
            searchLabel.autoComplete();
        }
    }
    
}
