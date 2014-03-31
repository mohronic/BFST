package QuadTreePack;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author Gruppe A
 */
public class Boundary
{

    public double xdim, ydim;
    private Center center = new Center();

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

    public Boundary(NSEW direction)
    {
        center.xc = ctrl.StartMap.xmax / 2;
        center.yc = ctrl.StartMap.ymax / 2;
        xdim = ctrl.StartMap.xmax / 2;
        ydim = ctrl.StartMap.ymax / 2;

    }

    public boolean containsPoint(double x, double y)
    {
        return ((center.xc - xdim) <= x && x <= (center.xc + xdim)) && ((center.yc - ydim) <= y && y <= (center.yc + ydim));
    }

    public boolean containsBox(double x1, double y1, double x2, double y2)
    {
        Rectangle2D rt2 = new Rectangle2D.Double(center.xc - xdim, center.yc - ydim, 2 * xdim, 2 * ydim);
        return rt2.intersects(x1, y1, x2 - x1, y2 - y1) || rt2.contains(x1, y1, x2 - x1, y2 - y1);
    }

}
