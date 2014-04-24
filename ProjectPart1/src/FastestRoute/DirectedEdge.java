package FastestRoute;

import java.awt.geom.Point2D;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.Road;

/**
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

    public DirectedEdge(Road r, boolean b) // if b == true normal, if false inverted
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

    public DirectedEdge(Road r)
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
        source.setLocation(nodeFrom.getX_COORD(), nodeFrom.getY_COORD());
        target.setLocation(nodeTo.getX_COORD(), nodeTo.getY_COORD());
    }

    public Point2D.Double from()
    {
        return source;
    }

    public Point2D.Double to()
    {
        return target;
    }

    public Road getRoad()
    {
        return road;
    }

    public double length()
    {
        return length;
    }

    public String getName()
    {
        return name;
    }

    public double drivetime()
    {
        return drivetime;
    }

}
