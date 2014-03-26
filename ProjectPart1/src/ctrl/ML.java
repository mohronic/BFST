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
import java.util.ArrayList;
import model.CurrentData;
import model.Road;
import view.Canvas;

import view.Java2DDraw;
//import view.Canvas;

/**
 *
 * @author z3ss
 */
public class ML implements MouseListener, MouseMotionListener {

    private final Canvas c;
    private final CurrentData cd = CurrentData.getInstance();
    private Point mouseStart;
    private boolean mousePressed;
    private Point mouseEnd;
    private Point currentMouse;
    private boolean mouseDragged;
    private Java2DDraw j2d = null;

    public ML(Canvas c) {
        this.c = c;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //Unused
    }

    @Override
    public void mousePressed(MouseEvent me) {

        mouseStart = me.getPoint();
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        mouseEnd = me.getPoint();
        double x = mouseStart.getX();
        double y = mouseStart.getY();
        double w = mouseEnd.getX() - mouseStart.getX();
        double h = mouseEnd.getY() - mouseStart.getY();
        Rectangle2D r = new Rectangle2D.Double(x, y, w, h);
        calcView(r);
        mousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getX());
        currentMouse = e.getPoint();
        mouseDragged = true;
        drawZoomArea();

        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentMouse = e.getPoint();
        mouseDragged = false; //Unused right now
        getClosestRoad(e);

        e.consume();//Stops the event when not in use, makes program run faster

    }
            


    private void getClosestRoad(MouseEvent e) {

        //Skal ændres til at tage scale med (Scale skal ganges på koordinaterne).
        double eX, eY;
        eX = e.getPoint().getX() + c.;
        eY = e.getPoint().getY();
        Road closestRoad = null;

        ArrayList<Road> rl = CurrentData.getInstance().getQT().search(eX, eY, eX + 0.1, eY + 0.1);
        if (rl.get(0) != null) {
            //We use pythagoras to calculate distance:
            double dist = Math.sqrt((Math.pow(rl.get(0).midX - eX, 2)) + (Math.pow(rl.get(0).midY - eY, 2)));
            for (Road road : rl) {
                double distX, distY;
                distX = Math.abs(road.midX - eX);
                distY = Math.abs(road.midY - eY);
                if (Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)) < dist) {
                    dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
                    closestRoad = road;
                }
            }
        }
        //Finds closest road, but does not do anything else
        //return closestRoad;
        //FDS: A drawing method should call this to recieve the closest road.
    }

    public void drawZoomArea() {

//        if (j2d == null)
//        {
//            j2d = Java2DDraw.getInstance();
//        }
        Graphics2D g = (Graphics2D) c.getGraphics();

        if (mousePressed) {
            Shape rect = new Rectangle2D.Double(mouseStart.getX(), mouseStart.getY(), currentMouse.getX() - mouseStart.getX(), currentMouse.getY() - mouseStart.getY());
            g.draw(rect);

            //j2d.drawRect(mouseStart.getX(), mouseStart.getY(), currentMouse.getX() - mouseStart.getX(), currentMouse.getY() - mouseStart.getY());
        }
        c.repaint();

    }

    private void calcView(Rectangle2D r) {
        double x = r.getMinX() * c.getScale() + cd.getOldx();
        double y = r.getMinY() * c.getScale() + cd.getOldy();
        double w = r.getWidth() * c.getScale();
        double h = r.getHeight() * c.getScale();
        System.out.println(x + " " + y);
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
    }

    @Override
    public void mouseEntered(MouseEvent me) {
//donothing
    }

    @Override
    public void mouseExited(MouseEvent me) {
//do nothing
    }

}
