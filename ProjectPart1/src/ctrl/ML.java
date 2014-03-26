/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import model.CurrentData;
import model.Road;
import view.Canvas;

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
        Rectangle2D r = new Rectangle2D.Double(mouseStart.x, mouseStart.y, mouseEnd.x, mouseEnd.y);
        cd.updateArea(r);
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //Unused
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //Unused
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentMouse = e.getPoint();
        mouseDragged = true; //Unused right now
        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentMouse = e.getPoint();
        mouseDragged = false; //Unused right now
        getClosestRoad(e);
        
        e.consume();//Stops the event when not in use, makes program run faster

    }

//    private Rectangle createRect(Point start, Point end){
//        return new Rectangle((int)((start.x-c.getOffset())*c.getScale()), 
//                (int)((start.y-c.getOffset())*c.getScale()), 
//                (int)((end.x-start.x)*c.getScale()), 
//                (int)((end.y-start.y)*c.getScale()));
//    }
    
    private void getClosestRoad(MouseEvent e) {

        //Skal ændres til at tage scale med (Scale skal ganges på koordinaterne).
        double eX, eY;
        eX = e.getPoint().getX();
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
}
