package QuadTreePack;

import java.awt.geom.Rectangle2D;
import osmparser.OSMParser;

/**
 * Class which contains a boundary and methods to check if something if is inside the borders
 * @author Gruppe A
 */
public class Boundary
{

    public double xdim, ydim;
    private Center center = new Center();

    /**
     * Constructor which sets up the boundary values depending on the given direction and Boundary.
     * Direction represents which quad out of the given boundary the new boundary needs to represent.
     * @param enum direction
     * @param Boundary bd 
     */
    public Boundary(NSEW direction, Boundary bd)
    {

        xdim = bd.xdim / 2;
        ydim = bd.ydim / 2;

        switch (direction)
        {
            case NORTHEAST:
                center.xc = bd.center.xc + xdim;
                center.yc = bd.center.yc - ydim;

                break;
            case NORTHWEST:
                center.xc = bd.center.xc - xdim;
                center.yc = bd.center.yc - ydim;
                break;
            case SOUTHEAST:
                center.xc = bd.center.xc + xdim;
                center.yc = bd.center.yc + ydim;
                break;
            case SOUTHWEST:
                center.xc = bd.center.xc - xdim;
                center.yc = bd.center.yc + ydim;
                break;
            case ROOT:
                System.out.println("Should not happen");
                break;

        }

    }

    /**
     * Overloaded constructor which creates the first boundary object when a quadtree is initiated.
     * @param direction
     */
    public Boundary(NSEW direction)
    {
        
        center.xc = ctrl.StartMap.bounds.getMinX()+(ctrl.StartMap.bounds.getWidth() / 2);
        center.yc = ctrl.StartMap.bounds.getMinY()+(ctrl.StartMap.bounds.getHeight() / 2);
        xdim = ctrl.StartMap.bounds.getWidth() / 2;
        ydim = ctrl.StartMap.bounds.getHeight() / 2;

    }

    /**
     * Checks whether the given point is within the boundary borders
     * @param x
     * @param y
     * @return boolean
     */
    public boolean containsPoint(double x, double y)
    {
        return ((center.xc - xdim) <= x && x <= (center.xc + xdim)) && ((center.yc - ydim) <= y && y <= (center.yc + ydim));
    }

    /**
     * Checks whether the given two points form a rectangle that intersects with the boundary area.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean
     */
    public boolean containsBox(double x1, double y1, double x2, double y2)
    {
        Rectangle2D rt2 = new Rectangle2D.Double(center.xc - xdim, center.yc - ydim, 2 * xdim, 2 * ydim);
        return rt2.intersects(x1, y1, x2 - x1, y2 - y1) || rt2.contains(x1, y1, x2 - x1, y2 - y1);
    }

}
