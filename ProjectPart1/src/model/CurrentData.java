package model;

import QuadTreePack.QuadTreeInterface;
import ctrl.StartMap;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import view.Canvas;
import view.ObservableC;

/**
 * Class containing the current area, which is to be drawn by Canvas. It
 * implements Observable. It is also follows the singleton design pattern.
 * It also contains methods to get data from the QuadTree.
 *
 * @author Gruppe A
 */
public class CurrentData extends ObservableC {

    private static CurrentData instance = null;
    private final QuadTreeInterface qtlvl1, qtlvl2, qtlvl3, qtlvl4;
    private double xmin = 0, ymin = 0, xmax = 0, ymax = 0;
    private double oldx = 0, oldy = 0;
    private List<Road> rds = new ArrayList<>();
    private Rectangle2D view;
    private static JLabel crl = new JLabel("Ingen vej fundet");

    /**
     * To be used instead of the constructor, to prevent multiple instances of
     * CurrentData.
     *
     * @return the instance of CurrentData
     */
    public static CurrentData getInstance() {
        if (instance == null) {
            instance = new CurrentData();
        }
        return instance;
    }

    /* Constructor for CurrentData, gets the quadtree and instaciates view.
     */
    private CurrentData() {
        QuadTreeInterface[] qtlist = StartMap.getQuadTree();
        qtlvl1 = qtlist[0];
        qtlvl2 = qtlist[1];
        qtlvl3 = qtlist[2];
        qtlvl4 = qtlist[3];
        view = new Rectangle2D.Double();
    }

    /**
     * Updates the area to be drawn.
     * @param r Rectangle2D which is the area to be drawn.
     */
    public void updateArea(Rectangle2D r) {
        view = r;
        oldy = r.getMinY();
        oldx = r.getMinX();
        setChanged();
        notifyObservers();
    }
    
    /**
     * Return an List of Road object relevant to the tile defined 
     * by Rectangle2D r.
     * @param r
     * @return 
     */
    public List<Road> getTile(Rectangle2D r) {
        rds = new ArrayList<>();
        Canvas c = Canvas.getInstance(null);
        double maxScale = xmax / (double) c.getWidth();
        if (maxScale < ymax / (double) c.getHeight()) {
            maxScale = (ymax - ymin) / (double) c.getHeight();
        }
        rds = qtlvl1.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight()));

        if (c.getScale() < maxScale * 0.75 && c.getScale() > maxScale * 0.05) {
            rds.addAll(qtlvl2.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
        } else if (c.getScale() <= maxScale * 0.05 && c.getScale() > maxScale * 0.025) {
            rds.addAll(qtlvl2.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
            rds.addAll(qtlvl3.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
        } else if (c.getScale() <= maxScale * 0.025) {
            rds.addAll(qtlvl2.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
            rds.addAll(qtlvl3.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
            rds.addAll(qtlvl4.search(r.getX(), (r.getX() + r.getWidth()), r.getY(), (r.getY() + r.getHeight())));
        }
        
        return rds;
    }

    /**
     * Sets the max value of x.
     *
     * @param xmax
     */
    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    /**
     * Sets the max value of y.
     *
     * @param ymax
     */
    public void setYmax(double ymax) {
        this.ymax = ymax;
    }

    /**
     * Returns the max value of x.
     *
     * @return double xmax.
     */
    public double getXmax() {
        return xmax;
    }

    /**
     * Returns the max value of y.
     *
     * @return double ymax.
     */
    public double getYmax() {
        return ymax;
    }

    /**
     * Returns the current area to be drawn
     *
     * @return Rectangle2D view.
     */
    public Rectangle2D getView() {
        return view;
    }

    /**
     * Returns the value x value of the upper left corner, of the area which is
     * drawn.
     *
     * @return double oldx.
     */
    public double getOldx() {
        return oldx;
    }

    /**
     * Returns the value y value of the upper left corner, of the area which is
     * drawn.
     *
     * @return double oldy
     */
    public double getOldy() {
        return oldy;
    }

    /**
     * Updates the road label.
     *
     * @param s String containing the new text.
     */
    public static void setCurrentRoadLabel(String s) {
        crl.setText(s);
        crl.repaint();
    }

    /**
     * Returns the JLabel.
     *
     * @return JLabel crl.
     */
    public static JLabel getCurrentRoadLabel() {
        return crl;
    }
    
    /**
     * Sets the minimum x value for the bounds of the map
     * @param xmin 
     */
    public void setXmin(double xmin) {
        this.xmin = xmin;
    }
    
     /**
     * Sets the minimum y value for the bounds of the map
     * @param ymin 
     */
    public void setYmin(double ymin) {
        this.ymin = ymin;
    }
    
    /**
     * Returns the maximum x value for the bounds of the map
     * @return 
     */
    public double getXmin() {
        return xmin;
    }
    
    /**
     * Returns the minimum y value for the bounds of the map
     * @return 
     */
    public double getYmin() {
        return ymin;
    }
}
