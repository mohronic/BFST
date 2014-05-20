/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import view.Canvas;

/**
 * Mouselistener which makes all click actions request focus.
 * @author archigo
 */
public class CanvasFocusML implements MouseListener{

    private Canvas canvas;
    
    public CanvasFocusML(){
        canvas = Canvas.getInstance(null);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        canvas.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        canvas.requestFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
       //do nothing
    }
    
}
