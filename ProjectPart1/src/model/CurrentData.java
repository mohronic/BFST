package model;

import QuadTreePack.QuadTree;
import ctrl.StartMap;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import view.Canvas;
import view.ObservableC;

/**
 * Class containing the current data, which is to be drawn by Canvas. It
 * implements Observable. It is also follows the singleton design pattern.
 *
 * @author Gruppe A
 */
public class CurrentData extends ObservableC {

    private static CurrentData instance = null;
    private final QuadTree qtlvl1, qtlvl2, qtlvl3, qtlvl4;
    private double xmin = 0, ymin = 0, xmax = 0, ymax = 0;
    private double oldx = 0, oldy = 0;
    private List<Road> roads = new ArrayList<>();
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
        QuadTree[] qtlist = StartMap.getQuadTree();
        qtlvl1 = qtlist[0];
        qtlvl2 = qtlist[1];
        qtlvl3 = qtlist[2];
        qtlvl4 = qtlist[3];
        view = new Rectangle2D.Double();
    }

    /**
     * Return the roads contained in CurrentData.
     *
     * @return The list of roads in CurrentData.
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     * Updates the area to be drawn and gets a new list of relevant roads, from
     * the Quadtree.
     *
     * @param r, Rectangle2D which is the area to be drawn.
     */
    public void updateArea(Rectangle2D r) {
//        double vdiffx, vdiffy;
//        vdiffx = (Math.abs(view.getMaxX()) - Math.abs(view.getMinX()))
//                - (Math.abs(r.getMaxX()) - Math.abs(r.getMinX()));
//        vdiffy = (Math.abs(view.getMaxY()) - Math.abs(view.getMinY()))
//                - (Math.abs(r.getMaxY()) - Math.abs(r.getMinY()));
//
//        for (int i = 0; i < 4; i++) {
//
//            view = new Rectangle2D.Double(0,0,100000,100000);
////            view = new Rectangle2D.Double(
////                    view.getMinX() + (0.25 * vdiffx),
////                    view.getMinY() + (0.25 * vdiffy),
////                    view.getMaxX() - (0.25 * vdiffx),
////                    view.getMaxY() - (0.25 * vdiffy)
////            );
//            setChanged();
//            notifyObservers();
//            try{
//            Thread.sleep(200);
//            } catch(InterruptedException e){
//                System.out.println(e);
//            }
//        }
        view = r;
        Canvas c = Canvas.getInstance(null);
        c.calcScale(view);

        double maxScale = xmax / (double) c.getWidth();
        if (maxScale < ymax / (double) c.getHeight()) {
            maxScale = (ymax - ymin) / (double) c.getHeight();
        }

        
        oldy = view.getMinY();
        oldx = view.getMinX();
        roads.clear();
        roads = qtlvl1.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25);
        

        if (c.getScale() < maxScale * 0.75 && c.getScale() > maxScale * 0.05) {
            roads.addAll(qtlvl2.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
        } else if (c.getScale() <= maxScale * 0.05 && c.getScale() > maxScale * 0.025) {
            roads.addAll(qtlvl2.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
            roads.addAll(qtlvl3.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
        } else if (c.getScale() <= maxScale * 0.025) {
            roads.addAll(qtlvl2.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
            roads.addAll(qtlvl3.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
            roads.addAll(qtlvl4.search(r.getX() * 0.875, (r.getX() + r.getWidth()) * 1.25, r.getY() * 0.875, (r.getY() + r.getHeight()) * 1.25));
        }
        setChanged();
        notifyObservers();

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
     * Returns the Quadtree used in CurrentData
     *
     * @return Quadtree qt.
     */
    public QuadTree getQT() {
        return qtlvl3;
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
     * @param s, String containing the new text.
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

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public void setYmin(double ymin) {
        this.ymin = ymin;
    }

    public double getXmin() {
        return xmin;
    }

    public double getYmin() {
        return ymin;
    }
}
