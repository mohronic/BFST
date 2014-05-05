package view;

import Route.Linked;
import Route.MapRoute;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JComponent;
import krakloader.*;
import model.CurrentData;
import model.Road;

/**
 * The class Canvas extends JComponent and implements Observer. It is used to
 * draw the data in the class CurrentData.
 *
 * @author Gruppe A
 */
public class Canvas extends JComponent implements ObserverC, FocusListener
{

    private List<Road> rd;
    private final CurrentData cd;
    private Rectangle2D view;
    private double scale = 1;
    private final DrawInterface j2d;
    private boolean dragbool = false;
    private Rectangle2D dragrect;
    private static Canvas instance = null;
    private boolean isFocused = false;

    /**
     * Constructor for Canvas, getting the data to draw and instantiates the
     * DrawInterface.
     *
     * @param cd
     */
    private Canvas(CurrentData cd)
    {
        this.cd = cd;
        this.view = cd.getView();
        rd = cd.getRoads();
        j2d = Graphics2DDraw.getInstance();
    }

    public static Canvas getInstance(CurrentData cd) {
        if (instance == null) {
            instance = new Canvas(cd);
        }
        return instance;
    }
    
    /* Method that scales and draws the relevant Road objects from CurrentData,
     * in correct colors.
     */
    private void drawMap()
    {
        
        for (Road r : rd)
        {
//            if (!filterRoad(r))
//            {
//                continue;
//            }
            double x1, x2, y1, y2;
            NodeData n1 = r.getFn();
            NodeData n2 = r.getTn();
            x1 = (n1.getX_COORD() - view.getMinX()) / scale;
            y1 = (n1.getY_COORD() - view.getMinY()) / scale;
            x2 = (n2.getX_COORD() - view.getMinX()) / scale;
            y2 = (n2.getY_COORD() - view.getMinY()) / scale;
            //Road colering:
            switch (r.getEd().TYP)
            {
                case 1:
                    j2d.setRed(); //Highway
                    break;
                case 3:
                    j2d.setBlue(); //Main roads
                    break;
                case 8:
                    j2d.setGreen(); //Path
                    break;
                default:
                    j2d.setBlack(); //Other
                    break;
            }
            j2d.drawLine(x1, y1, x2, y2);
        }
        
        // draws the fastest road
        if (MapRoute.getRoute() != null)
        {
            j2d.setOrange();
            for (Linked l : MapRoute.getRoute())
            {
                Road r = l.getRoad();
                double x1, x2, y1, y2;
                NodeData n1 = r.getFn();
                NodeData n2 = r.getTn();
                x1 = (n1.getX_COORD() - view.getMinX()) / scale;
                y1 = (n1.getY_COORD() - view.getMinY()) / scale;
                x2 = (n2.getX_COORD() - view.getMinX()) / scale;
                y2 = (n2.getY_COORD() - view.getMinY()) / scale;
                j2d.drawLine(x1, y1, x2, y2);
            }
            j2d.setBlack();
        }
    }

    /**
     * Overrides paintComponent from JComponent, to also draw the map.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        j2d.setGraphics(g);
        drawMap();
        if (dragbool)
        {                       //paints the dragzoom rectangle
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(dragrect);
        }
    }

    /**
     * Overrides update from Observer pattern, to get the data from CurrentData
     * and repaint.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update()
    {
        rd.clear();
        rd = cd.getRoads();
        view = cd.getView();
        repaint();
    }

    /* Method to check if a road is to be drawn.
     *
     */
    private boolean filterRoad(Road r)
    {
        int typ = r.getEd().TYP;
        double maxScale = cd.getXmax() / (double) this.getWidth();
        if (maxScale < cd.getYmax() / (double) this.getHeight())
        {
            maxScale = (cd.getYmax()-cd.getYmin()) / (double) this.getHeight();
        }
        if (typ == 1 || typ == 3 || typ == 2 || typ == 48) //type 48 represents coastlines.
        {
            return true;
        }
        if (scale < maxScale * 0.75 && scale > maxScale * 0.15)
        {
            if (typ == 4)
            {
                return true;
            }
        } else if (scale <= maxScale * 0.15 && scale > maxScale * 0.08)
        {
            if (typ != 8)
            {
                return true;
            }
        } else if (scale <= maxScale * 0.08)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the current scale of the map.
     *
     * @return double scale.
     */
    public double getScale()
    {
        return scale;
    }

    public void setDragrect(Rectangle2D rect)
    {
        dragrect = rect;
    }

    public void setDragbool(boolean bool)
    {
        dragbool = bool;
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        System.out.println("Focus gained:");
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isFocused()
    {
        return isFocused;
    }
    
    public void calcScale(Rectangle2D view){
        double scaley = view.getHeight() / (double) this.getHeight();
        double scalex = view.getWidth() / (double) this.getWidth();
        if (scaley > scalex)
        {
            scale = view.getHeight() / (double) this.getHeight();
        } else
        {
            scale = view.getWidth() / (double) this.getWidth();
        }
    }
}
