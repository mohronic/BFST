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
import view.Graphics2DDraw;

/**
 * A mouseListener and mouseMotionListener, used to zoom, pan and find nearest
 * road.
 * @author Gruppe A
 */
public class ML implements MouseListener, MouseMotionListener
{

    private final Canvas c;
    private final CurrentData cd = CurrentData.getInstance();
    private final Graphics2DDraw j2d = null;
    private Point mouseStart;
    private Point mouseEnd;
    private Point currentMouse;
    private int mouseButton;
    private boolean mousePressed;
    private Rectangle2D currentView;
    private final Rectangle2D originalView;
    
    /**
     * Constructor for ML, setting the current view and the original view. It
     * takes a Canvas 'c' as parameter, which it uses to calculate the scale.
     * @param c Canvas which it is connected too.
     */
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
    /**
     * Method to handle the MouseEvent occurring when a mouse button is 
     * released. If it was the left mousebutton & it was dragged, it will zoom
     * on the selected area else if it wasn't dragged it will reset to the
     * original view. If it was any other button, nothing happens.
     * @param me 
     */
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
        c.setDragbool(false);
        mousePressed = false;
    }
    
    /**
     * Method to handle the MouseEvent occurring when the mouse is dragged. If
     * button pushed, while dragging is left mousebutton, it will draw the
     * area which is going to be zoomed in to. If is the right mousebutton,
     * it will pan the the map.
     * @param e 
     */
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
    
    /**
     * when the mouse is moved, it will check for the nearest road.
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        currentMouse = e.getPoint();
        getClosestRoad(e);

        e.consume();//Stops the event when not in use, makes program run faster

    }
    
    /*
     * Method to pan the view, which add the distance the mouse has moved to
     * the upper left corner, of the view.
     */
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
    
    /* Method for finding the closest road, by distance to the midpoint.
     */
    private void getClosestRoad(MouseEvent e)
    {
        double eX, eY;
        eX = (e.getPoint().getX() * c.getScale()) + cd.getOldx();
        eY = (e.getPoint().getY() * c.getScale()) + cd.getOldy();
        Road closestRoad = null;

        ArrayList<Road> rl = CurrentData.getInstance().getQT().search(eX - 0.5, eX + 1, eY - 0.5, eY + 1);
        if (rl.size() > 0)
        {
            //We use pythagoras to calculate distance:
            double dist = Math.sqrt((Math.pow(rl.get(0).midX - eX, 2)) + (Math.pow(rl.get(0).midY - eY, 2)));

            closestRoad = rl.get(0);
            for (Road road : rl)
            {
                if (closestRoad.getEd().VEJNAVN.isEmpty() && !road.getEd().VEJNAVN.isEmpty())
                {
                    closestRoad = road;
                    dist = Math.sqrt((Math.pow(road.midX - eX, 2)) + (Math.pow(road.midY - eY, 2)));
                }
                double distX, distY;
                distX = Math.abs(road.midX - eX);
                distY = Math.abs(road.midY - eY);
                if (Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)) < dist && !road.getEd().VEJNAVN.isEmpty())
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
    }
    
    /* Draws the rectangle which is to zoomed in to.
     */
    private void drawZoomArea()
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
            Rectangle2D rect = new Rectangle2D.Double(startx, starty, w, h);
            c.setDragrect(rect);    //Set up rectangle for nondisapearing draw
            c.setDragbool(true);
            c.repaint();

        }

    }
    
    /* Scales the rectangle from ML to fit with the coordinates in the Quadtree.
     */
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
        //does nothing
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        //does nothing
    }

}
