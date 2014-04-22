package model;

import QuadTreePack.QuadTree;
import ctrl.StartMap;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JLabel;

/**
 * Class containing the current data, which is to be drawn by Canvas.
 * It implements Observable. It is also follows the singleton design pattern.
 * @author Gruppe A
 */
public class CurrentData extends Observable
{
    
    private static CurrentData instance = null;
    private final QuadTree qt;
    private double xmax = 0, ymax = 0;
    private double oldx = 0, oldy = 0;
    private List<Road> roads = new ArrayList<>();
    private Rectangle2D view;
    private static JLabel crl = new JLabel("Ingen vej fundet");

    /**
     * To be used instead of the constructor, to prevent multiple instances of
     * CurrentData.
     * @return the instance of CurrentData
     */
    public static CurrentData getInstance()
    {
        if (instance == null)
        {
            instance = new CurrentData();
        }
        return instance;
    }
    
    /* Constructor for CurrentData, gets the quadtree and instaciates view.
     */
    private CurrentData()
    {
        qt = StartMap.getQuadTree();
        view = new Rectangle2D.Double();
    }
    
    /**
     * Return the roads contained in CurrentData.
     * @return The list of roads in CurrentData.
     */
    public List<Road> getRoads()
    {
        return roads;
    }
    
    /**
     * Updates the area to be drawn and gets a new list of relevant roads, from
     * the Quadtree.
     * @param r, Rectangle2D which is the area to be drawn.
     */
    public void updateArea(Rectangle2D r)
    {
        view = r;
        oldy = view.getMinY();
        oldx = view.getMinX();
        roads.clear();
        roads = qt.search(r.getX() * 0.75, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.75, (r.getY() + r.getHeight()) * 1.25);
        setChanged();
        notifyObservers();
    }
    
    /**
     * Sets the max value of x.
     * @param xmax 
     */
    public void setXmax(double xmax)
    {
        this.xmax = xmax;
    }
    
    /**
     * Sets the max value of y.
     * @param ymax 
     */
    public void setYmax(double ymax)
    {
        this.ymax = ymax;
    }
    
    /**
     * Returns the max value of x.
     * @return double xmax.
     */
    public double getXmax()
    {
        return xmax;
    }
    
    /**
     * Returns the max value of y.
     * @return double ymax.
     */
    public double getYmax()
    {
        return ymax;
    }
    
    /**
     * Returns the current area to be drawn
     * @return Rectangle2D view.
     */
    public Rectangle2D getView()
    {
        return view;
    }
    
    /**
     * Returns the Quadtree used in CurrentData
     * @return Quadtree qt.
     */
    public QuadTree getQT()
    {
        return qt;
    }
    
    /**
     * Returns the value x value of the upper left corner, of the area 
     * which is drawn.
     * @return double oldx.
     */
    public double getOldx()
    {
        return oldx;
    }
    
    /**
     * Returns the value y value of the upper left corner, of the area 
     * which is drawn.
     * @return double oldy
     */
    public double getOldy()
    {
        return oldy;
    }
    
    /**
     * Updates the road label.
     * @param s, String containing the new text. 
     */
    public static void setCurrentRoadLabel(String s)
    {
        crl.setText(s);
        crl.repaint();
    }
    
    /**
     * Returns the JLabel.
     * @return JLabel crl.
     */
    public static JLabel getCurrentRoadLabel()
    {
        return crl;
    }
}
