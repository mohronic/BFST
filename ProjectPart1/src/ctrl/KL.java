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
 * The class KL stands for KeyListener. It implements the KeyListener interface,
 * and is used to interact with the program using a keyboard.
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class KL implements KeyListener
{

    private SearchLabel searchLabel;
    private final CurrentData cd = CurrentData.getInstance();
    private final Canvas c;

    /**
     * Constructor for KL, setting up current searchlabel and canvas.
     *
     * @param searchLabel SearchLabel which key listener is connected to.
     * @param canvas Canvas which it is connected to.
     */
    public KL()
    {
//        this.searchLabel = searchLabel;
        this.c = Canvas.getInstance(cd);
    }

    /**
     * Overwritten method from mouselistener interface
     *
     * @param e keyevent for key typed
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        //Do nothing
    }

    /**
     * Overwritten method from mouselistener interface
     *
     * @param e keyevent for key pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == 10 && !c.hasFocus()) //10 = keycode for Enter-button
        {

        }
        
            if(e.getKeyCode() == 38 && c.hasFocus()) //Op
            {
                keyPanUp();
            }

            if (e.getKeyCode() == 40 && c.hasFocus()) //Ned
            {
                keyPanDown();
            }

            if (e.getKeyCode() == 39 && c.hasFocus()) //Højre
            {
                keyPanRight();
            }

            if (e.getKeyCode() == 37 && c.hasFocus()) //Venstre
            {
                keyPanLeft();
            }
    }

    /**
     * Overwritten method from mouselistener interface
     *
     * @param e keyevent for key realeased
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(searchLabel != null && searchLabel.oldtext < searchLabel.getText().length()){
            searchLabel.autoComplete();
        }
        else if(searchLabel != null)
        {
            searchLabel.oldtext = searchLabel.getText().length();
        }
    }

    /**
     * Pans up when called
     */
    public void keyPanUp()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX();
        double y = temp.getY() - ((c.getHeight() / 50) * c.getScale()); //50 = antal gange der skal trykkes før bund = top
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
        double y = temp.getY() + ((c.getHeight() / 50) * c.getScale()); //50 = antal gange der skal trykkes før bund = top
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
        double x = temp.getX() + ((c.getWidth() / 50) * c.getScale()); //50 = antal gange der skal trykkes før bund = top
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
        double x = temp.getX() - ((c.getWidth() / 50) * c.getScale()); //50 = antal gange der skal trykkes før bund = top
        double y = temp.getY();
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }
    
    public void setSearchLabel(SearchLabel sl){
        searchLabel = sl;
    }
}