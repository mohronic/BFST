/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FastestRoute;

import java.awt.Point;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Linked
{
    private double length;
    private Point from; // Only null where the road search starts
    
    public Linked()
    {
        length = Double.POSITIVE_INFINITY;
        from = null;
    }

    public double getLength()
    {
        return length;
    }

    public Point getFrom()
    {
        return from;
    }
    
    public void setFrom(Point f)
    {
        from = f;
    }
    
    public void setLength(double l)
    {
        length = l;
    }
}
