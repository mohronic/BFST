/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FastestRoute;

import java.awt.geom.Point2D;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Linked
{

    private double length;
    private double drivetime;
    private Point2D.Double from; // Only null where the road search starts
    private DirectedEdge edge; // edge which contains the original road
    private Turn turn; //Actually the turn from the edge before, to this. So this is turn-1

    /**
     *
     */
    public Linked()
    {
        length = Double.POSITIVE_INFINITY;
        drivetime = Double.POSITIVE_INFINITY;
        from = null;
        edge = null;
    }

    /**
     *
     * @return
     */
    public double getLength()
    {
        return length;
    }

    /**
     *
     * @return
     */
    public Point2D.Double getFrom()
    {
        return from;
    }

    /**
     *
     * @param f
     */
    public void setFrom(Point2D.Double f)
    {
        from = f;
    }

    /**
     *
     * @param l
     */
    public void setLength(double l)
    {
        length = l;
    }

    /**
     *
     * @param e
     */
    public void setEdge(DirectedEdge e)
    {
        edge = e;
        calTurn();
    }

    /**
     *
     * @param d
     */
    public void setDrivetime(double d)
    {
        drivetime = d;
    }

    /**
     *
     * @return
     */
    public DirectedEdge getEdge()
    {
        return edge;
    }

    /**
     *
     * @return
     */
    public Road getRoad()
    {
        return edge.getRoad();
    }

    /**
     *
     * @return
     */
    public double getDrivetime()
    {
        return drivetime;
    }

    /**
     *
     * @param t
     */
    public void setTurn(Turn t)
    {
        turn = t;
    }

    /**
     *
     * @return
     */
    public Turn getTurn()
    {
        return turn;
    }

    private void calTurn()
    {
        Point2D.Double to = edge.to();

        if (to != null && from != null)
        {
            if (to.getX() == from.getX() && to.getY() == from.getY())
            {
                to = edge.from();
            }

            if (from.getY() < to.getY())
            {
                if (from.getX() < to.getX())
                {
                    turn = Turn.LEFT;
                } else
                {
                    turn = Turn.RIGHT;
                }
            } else
            {
                if (from.getX() > to.getX())
                {
                    turn = Turn.LEFT;
                } else
                {
                    turn = Turn.RIGHT;
                }
            }
        }
    }
}
