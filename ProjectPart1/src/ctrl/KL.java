/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import SearchEngine.SearchLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import model.CurrentData;
import view.Canvas;

/**
 * The class KL stands for KeyListener.
 * It implements the KeyListener interface, and is used to interact with
 * the program using a keyboard.
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class KL implements KeyListener
{
    private final SearchLabel searchLabel;
    private final CurrentData cd = CurrentData.getInstance();
    private final Canvas c;
    
    /**
     * Constructor for KL, setting up current searchlabel and canvas.
     * @param searchLabel SearchLabel which key listener is connected to.
     * @param canvas Canvas which it is connected to.
     */
    public KL(SearchLabel searchLabel, Canvas canvas)
    {
        this.searchLabel = searchLabel;
        this.c = canvas;
    }
    
    /**
     * Overwritten method from mouselistener interface
     * @param e keyevent for key typed
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        //Does Nothing
    }

    /**
     * Overwritten method from mouselistener interface
     * @param e keyevent for key pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        
//        if(searchLabel.getCurrentText().length() > 0)
//        {
//            System.out.println("Called...");
//            searchLabel.updateCurrentString();
//            searchLabel.autoComplete();
//        }
        
        //System.out.println(e.getKeyCode());
        //if(e.getKeyCode() == 17)
        //{
            //System.out.println("Called...");
            //searchLabel.updateCurrentString();
            //searchLabel.autoComplete();
        //}
                
        if(e.getKeyCode() == 10) //10 = keycode for Enter-button
        {
            //Should be written to a method call
            System.out.println("Searching for: " + searchLabel.getText());
            System.out.println("Found: " + searchLabel.checkRoadName(searchLabel.getText()));
        }
                
        if(e.getKeyCode() == 38) //Op
        {
            keyPanUp();
        }
        
        if(e.getKeyCode() == 40) //Ned
        {
            keyPanDown();
        }
        
        if(e.getKeyCode() == 39) //Højre
        {
            keyPanRight();
        }
        
        if(e.getKeyCode() == 37) //Venstre
        {
            keyPanLeft();
        }
    }

    /**
      * Overwritten method from mouselistener interface
      * @param e keyevent for key realeased
      */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()!=8)
        {
            searchLabel.updateCurrentString();
            searchLabel.autoComplete();
        }
    }
    
    /**
     * Pans up when called
     */
    public void keyPanUp()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX();
        double y = temp.getY() - (c.getHeight()/50 * c.getScale()); //50 = antal gange der skal trykkes før bund = top
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }
    
    /**
     * Pans down when called
     */
    public void keyPanDown()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX();
        double y = temp.getY() + (c.getHeight()/50 * c.getScale()); //50 = antal gange der skal trykkes før bund = top
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }
    
    /**
     * Pans right when called
     */
    public void keyPanRight()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX() + (c.getWidth()/50 * c.getScale()); //50 = antal gange der skal trykkes før bund = top
        double y = temp.getY();
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }
    
    /**
     * Pans left when called
     */
    public void keyPanLeft()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX() - (c.getWidth()/50 * c.getScale()); //50 = antal gange der skal trykkes før bund = top
        double y = temp.getY();
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }  
}
