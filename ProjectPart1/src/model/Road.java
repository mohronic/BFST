package model;

import ctrl.StartMap;
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
    private final int ID;
    private final EdgeData ed;
    public double midX, midY;
    private NodeData fn;
    private NodeData tn;
    private final Point2D.Double source; //coordinate
    private final Point2D.Double target; //coordiante
    private final double length;
    private final String name;
    private final double drivetime;

    /**
     * Contructor for the road object.
     *
     * @param ed, EdgeData for the road
     * @param fn, the start NodeData (from node)
     * @param tn, the end NodeData (to node)
     */
    public Road(EdgeData ed, NodeData fn, NodeData tn)
    {
        this.ed = ed;
        this.fn = fn;
        this.tn = tn;
        midX = (fn.getX_COORD() + tn.getX_COORD()) / 2;
        midY = (fn.getY_COORD() + tn.getY_COORD()) / 2;
        source = new Point2D.Double();
        target = new Point2D.Double();
        drivetime = ed.DRIVETIME;
        length = ed.LENGTH;
        name = ed.VEJNAVN;
        source.setLocation(fn.getX_COORD(), fn.getY_COORD());
        target.setLocation(tn.getX_COORD(), tn.getY_COORD());
        ID = StartMap.roadID++;
    }

    public Road(Road r)
    {
        this.ed = r.ed;
        this.fn = r.fn;
        this.tn = r.tn;
        midX = (r.fn.getX_COORD() + r.tn.getX_COORD()) / 2;
        midY = (r.fn.getY_COORD() + r.tn.getY_COORD()) / 2;
        source = new Point2D.Double();
        target = new Point2D.Double();
        drivetime = r.ed.DRIVETIME;
        length = r.ed.LENGTH;
        name = r.ed.VEJNAVN;
        target.setLocation(r.fn.getX_COORD(), r.fn.getY_COORD());
        source.setLocation(r.tn.getX_COORD(), r.tn.getY_COORD());
        ID = StartMap.roadID++;
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

    public Point2D.Double from()
    {
        return source;
    }

    public Point2D.Double to()
    {
        return target;
    }

    public double getLength()
    {
        return length;
    }

    public String getName()
    {
        return name;
    }

    public double getDrivetime()
    {
        return drivetime;
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
        if(ed.VEJNAVN.compareTo(o.ed.VEJNAVN) > 0)
        {
            return 1;
        }
        else if(ed.VEJNAVN.compareTo(o.ed.VEJNAVN) < 0)
        {
            return -1;
        }
        else if(ed.V_POSTNR > o.ed.V_POSTNR)
        {
            return 1;
        }
        else if(ed.V_POSTNR < o.ed.V_POSTNR)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    public int ID()
    {
        return ID;
    }
}
