package Route;

import java.awt.geom.Point2D;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.Road;

/**
 * This class makes a directededge of a Road object.
 * 
 * @author Adam Engsig (adae@itu.dk)
 */
public class DirectedEdge
{

    private final Point2D.Double source; //coordinate
    private final Point2D.Double target; //coordiante
    private final Road road;
    private final double length;
    private final String name;
    private final double drivetime;

    /**
     * Makes a DirectedEdge from a road, either
     *
     * @param r Road
     * @param b Boolean wether the road should be added from a to b, or b to a.
     * If true the directed edge will be the same as the road, is false it will
     * be inverted
     */
    public DirectedEdge(Road r, boolean b)
    {
        road = r;
        EdgeData edge = road.getEd();
        NodeData nodeFrom = road.getFn();
        NodeData nodeTo = road.getTn();
        length = edge.LENGTH;
        name = edge.VEJNAVN;
        source = new Point2D.Double();
        target = new Point2D.Double();
        drivetime = edge.DRIVETIME;
        if (b)
        {
            source.setLocation(nodeFrom.getX_COORD(), nodeFrom.getY_COORD());
            target.setLocation(nodeTo.getX_COORD(), nodeTo.getY_COORD());
        } else
        {
            target.setLocation(nodeFrom.getX_COORD(), nodeFrom.getY_COORD());
            source.setLocation(nodeTo.getX_COORD(), nodeTo.getY_COORD());
        }

    }

    /**
     * Returns the Point From
     * 
     * @return Point from
     */
    public Point2D.Double from()
    {
        return source;
    }

    /**
     * Returns the Point To
     * 
     * @return To
     */
    public Point2D.Double to()
    {
        return target;
    }

    /**
     * Returns the road
     * 
     * @return Road
     */
    public Road getRoad()
    {
        return road;
    }

    /**
     * Returns the length og the road
     * 
     * @return double
     */
    public double length()
    {
        return length;
    }

    /**
     * Returns the name of the road
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the drive time of the road
     * 
     * @return double
     */
    public double drivetime()
    {
        return drivetime;
    }

}
