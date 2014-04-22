/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private /*final*/ Road road;
    private final double length;
    private /*final*/ String name;
    
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
        source.setLocation(nodeFrom.getX_COORD(), nodeFrom.getY_COORD());
        target.setLocation(nodeTo.getX_COORD(), nodeTo.getY_COORD());
    }
    
    public DirectedEdge(Point2D.Double start, Point2D.Double end, double length) //Bruges til testing, skal slettes samt felter skal laves final igen
    {
        source = start;
        target = end;
        this.length = length;
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

    public double weight()
    {
        return length;
    }

    public String getName()
    {
        return name;
    }
    
}
