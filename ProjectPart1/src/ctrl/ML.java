/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.awt.Graphics2D;
import java.awt.Point;

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
public class ML implements MouseListener, MouseMotionListener
{

    private final Canvas c;
    private final CurrentData cd = CurrentData.getInstance();
    private Point mouseStart;
    private boolean mousePressed;
    private Point mouseEnd;
    private Point currentMouse;
    private final Java2DDraw j2d = null;
    private Rectangle2D currentView;
    private final Rectangle2D originalView;
    private int mouseButton;

    public ML(Canvas c)
    {
        currentView = new Rectangle2D.Double(0, 0, cd.getXmax(), cd.getYmax());
        originalView = new Rectangle2D.Double(0, 0, cd.getXmax(), cd.getYmax());
        this.c = c;
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        //Unused
    }

    @Override
    public void mousePressed(MouseEvent me)
    {

        mouseStart = me.getPoint();
        mousePressed = true;
        mouseButton = me.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        mouseEnd = me.getPoint();
        if (mouseButton == 1)
        {
            if (mouseStart.getX() == mouseEnd.getX() || mouseStart.getY() == mouseEnd.getY())
            {
                cd.updateArea(originalView);
                currentView.setRect(originalView);
            } else
            {

                double startx = mouseStart.getX();
                double starty = mouseStart.getY();
                double endx = mouseEnd.getX();
                double endy = mouseEnd.getY();

                double tmp;

                if (startx > endx)
                {
                    tmp = startx;
                    startx = endx;
                    endx = tmp;
                }

                if (starty > endy)
                {
                    tmp = starty;
                    starty = endy;
                    endy = tmp;
                }

                double w = endx - startx;
                double h = endy - starty;

                currentView = new Rectangle2D.Double(startx, starty, w, h);
                calcView(currentView);

            }
        }
        mousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        currentMouse = e.getPoint();
        if (mouseButton == 1)
        {
            drawZoomArea();
        }

        if (mouseButton == 3)
        {
            pan();
        }

        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        currentMouse = e.getPoint();
        getClosestRoad(e);

        e.consume();//Stops the event when not in use, makes program run faster

    }

    private void pan()
    {
        Rectangle2D temp = cd.getView();
        double x = temp.getX() - ((currentMouse.getX() - mouseStart.getX()) * c.getScale());
        double y = temp.getY() - ((currentMouse.getY() - mouseStart.getY()) * c.getScale());
        double w = temp.getWidth();
        double h = temp.getHeight();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));
        mouseStart = currentMouse;
    }

    private void getClosestRoad(MouseEvent e)
    {

        //Skal ændres til at tage scale med (Scale skal ganges på koordinaterne).
        double eX, eY;
        eX = (e.getPoint().getX() * c.getScale()) + cd.getOldx();
        eY = (e.getPoint().getY() * c.getScale()) + cd.getOldy();
        Road closestRoad = null;

        ArrayList<Road> rl = CurrentData.getInstance().getQT().search(eX-0.5, eX + 1, eY-0.5, eY + 1);
        if (rl.size() > 0)
        {
            //We use pythagoras to calculate distance:
            double dist = Math.sqrt((Math.pow(rl.get(0).midX - eX, 2)) + (Math.pow(rl.get(0).midY - eY, 2)));

            closestRoad = rl.get(0);
            for (Road road : rl)
            {

                double distX, distY;
                distX = Math.abs(road.midX - eX);
                distY = Math.abs(road.midY - eY);
                if (Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)) < dist)
                {
                    dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
                    closestRoad = road;
                }

            }

            if (!closestRoad.getEd().VEJNAVN.isEmpty())
            {
                CurrentData.setCurrentRoadLabel(closestRoad.getEd().VEJNAVN);
            } else
            {
                CurrentData.setCurrentRoadLabel("Vejen kunne ikke findes!");
            }

        }
        //Finds closest road, but does not do anything else
        //return closestRoad;
        //FDS: A drawing method should call this to recieve the closest road.
    }

    public void drawZoomArea()
    {
        Graphics2D g = (Graphics2D) c.getGraphics();

        if (mousePressed)
        {
            double startx = mouseStart.getX();
            double starty = mouseStart.getY();
            double endx = currentMouse.getX();
            double endy = currentMouse.getY();

            double tmp;

            if (startx > endx)
            {
                tmp = startx;
                startx = endx;
                endx = tmp;
            }

            if (starty > endy)
            {
                tmp = starty;
                starty = endy;
                endy = tmp;
            }

            double w = endx - startx;
            double h = endy - starty;
            Shape rect = new Rectangle2D.Double(startx, starty, w, h);
            g.draw(rect);
        }
        c.repaint();

    }

    private void calcView(Rectangle2D r)
    {
        double x = r.getMinX() * c.getScale() + cd.getOldx();
        double y = r.getMinY() * c.getScale() + cd.getOldy();
        double w = r.getWidth() * c.getScale();
        double h = r.getHeight() * c.getScale();
        cd.updateArea(new Rectangle2D.Double(x, y, w, h));

    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
//donothing
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
//do nothing
    }

}
