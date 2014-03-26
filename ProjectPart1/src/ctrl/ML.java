/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import model.CurrentData;
import view.Canvas;
import view.Java2DDraw;
//import view.Canvas;

/**
 *
 * @author z3ss
 */
public class ML implements MouseListener, MouseMotionListener
{

    private final Canvas c;
    private final CurrentData cd = CurrentData.getInstance();
    private Point mouseStart;
    private boolean mousePressed;
    private Point mouseEnd;
    private Point currentMouse;
    private boolean mouseDragged;
    private Java2DDraw j2d = null;

    public ML(Canvas c)
    {
        this.c = c;
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        //s
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        
        mouseStart = me.getPoint();
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        mouseEnd = me.getPoint();
        double x = mouseStart.getX();
        double y = mouseStart.getY();
        double w = mouseEnd.getX() - mouseStart.getX();
        double h = mouseEnd.getY() - mouseStart.getY();
        Rectangle2D r = new Rectangle2D.Double(x,y,w,h);
        calcView(r);
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        //nothing
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        //nothing
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        System.out.println(e.getX());
        currentMouse = e.getPoint();
        mouseDragged = true;
        drawZoomArea();
        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        // nothing
    }

    public void drawZoomArea()
    {

//        if (j2d == null)
//        {
//            j2d = Java2DDraw.getInstance();
//        }
        Graphics2D g = (Graphics2D) c.getGraphics();

        if (mousePressed)
        {
            Shape rect = new Rectangle2D.Double(mouseStart.getX(), mouseStart.getY(), currentMouse.getX() - mouseStart.getX(), currentMouse.getY() - mouseStart.getY());
            g.draw(rect);

            //j2d.drawRect(mouseStart.getX(), mouseStart.getY(), currentMouse.getX() - mouseStart.getX(), currentMouse.getY() - mouseStart.getY());
        }
        c.repaint();

    }

    private void calcView(Rectangle2D r){
        double x = r.getMinX()*c.getScale()+cd.getOldx();
        double y = r.getMinY()*c.getScale()+cd.getOldy();
        double w = r.getWidth()*c.getScale();
        double h = r.getHeight()*c.getScale();
        System.out.println(x+" "+y);
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }
    
}
