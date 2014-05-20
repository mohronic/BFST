package model;

import java.awt.geom.Point2D;
import krakloader.NodeData;
import krakloader.EdgeData;

/**
 * Class able to contain the data of a road, consisting of an EdgeData and two
 * NodeData. It also calculates a midpoint for the Road object.
 *
 * @author Gruppe A
 */
public class Road implements Comparable<Road>
{
    private final EdgeData ed;
    public double midX, midY;
    private NodeData fn;
    private NodeData tn;

    /**
     * Contructor for the road object.
     *
     * @param ed EdgeData for the road
     * @param fn the start NodeData (from node)
     * @param tn the end NodeData (to node)
     */
    public Road(EdgeData ed, NodeData fn, NodeData tn)
    {
        this.ed = ed;
        this.fn = fn;
        this.tn = tn;
        midX = (fn.getX_COORD() + tn.getX_COORD()) / 2;
        midY = (fn.getY_COORD() + tn.getY_COORD()) / 2;
    }

    /**
     * Returns the EdgeData
     *
     * @return Edgedata ed.
     */
    public EdgeData getEd()
    {
        return ed;
    }
    
    /**
     * Returns the coordinates of the from node (fn) as a Point2D object
     * @return 
     */
    public Point2D.Double from()
    {
        return new Point2D.Double(fn.getX_COORD(), fn.getY_COORD());
    }
    
    /**
     * Returns the coordinates of the to node (tn) as a Point2D object
     * @return 
     */
    public Point2D.Double to()
    {
        return new Point2D.Double(tn.getX_COORD(), tn.getY_COORD());
    }
    
    /**
     * Returns the length of the road
     * @return 
     */
    public double getLength()
    {
        return ed.LENGTH;
    }

    public String getName()
    {
        return ed.VEJNAVN;
    }

    public double getDrivetime()
    {
        return ed.DRIVETIME;
    }

    /**
     * Returns the from NodeData
     *
     * @return NodeData fn
     */
    public NodeData getFn()
    {
        return fn;
    }

    /**
     * Returns the to NodeData
     *
     * @return NodeData tn
     */
    public NodeData getTn()
    {
        return tn;
    }

    public void setFn(NodeData nodedata)
    {
        fn = nodedata;
    }

    public void setTn(NodeData nodedata)
    {
        tn = nodedata;

        midX = (fn.getX_COORD() + tn.getX_COORD()) / 2;
        midY = (fn.getY_COORD() + tn.getY_COORD()) / 2;
    }

    @Override
    public int compareTo(Road o)
    {
        if (ed.VEJNAVN.compareTo(o.ed.VEJNAVN) > 0)
        {
            return 1;
        } else if (ed.VEJNAVN.compareTo(o.ed.VEJNAVN) < 0)
        {
            return -1;
        } else if (ed.V_POSTNR > o.ed.V_POSTNR)
        {
            return 1;
        } else if (ed.V_POSTNR < o.ed.V_POSTNR)
        {
            return -1;
        } else
        {
            return 0;
        }
    }
}
