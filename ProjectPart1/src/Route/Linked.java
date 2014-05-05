package Route;

import java.awt.geom.Point2D;
import model.Road;

/**
 * This class is used to make a linked list of the final fastest/shortest route.
 * Each instance will point to its former instance.
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Linked
{

    private double length;
    private double drivetime;
    private Point2D.Double from; // Only null where the road search starts
    private DirectedEdge edge; // edge which contains the original road
    private Turn turn;

    /**
     * Initially, as a part of the Dijkstra algorithm, the length and drivetime
     * will be infinity.
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
     * Returns the length
     *
     * @return double length
     */
    public double getLength()
    {
        return length;
    }

    /**
     * Returns the former point of the linked list
     *
     * @return Point2D.Double From
     */
    public Point2D.Double getFrom()
    {
        return from;
    }

    /**
     * Sets the former point
     *
     * @param f Point2D.Double From
     */
    public void setFrom(Point2D.Double f)
    {
        from = f;
    }

    /**
     * Sets length
     *
     * @param l double Length
     */
    public void setLength(double l)
    {
        length = l;
    }

    /**
     * Set the corresponding edge, and calculates which way the turn is
     *
     * @param e DirectedEdge edge
     */
    public void setEdge(DirectedEdge e)
    {
        edge = e;
        calTurn();
    }

    /**
     * Set drivetime
     * 
     * @param d double drivetime
     */
    public void setDrivetime(double d)
    {
        drivetime = d;
    }

    /**
     * Returns the edge
     * 
     * @return DirectedEdge edge
     */
    public DirectedEdge getEdge()
    {
        return edge;
    }

    /**
     * Returns the road, which is stored in the edge
     * 
     * @return Road
     */
    public Road getRoad()
    {
        return edge.getRoad();
    }

    /**
     * Gets the drivetime
     * 
     * @return double Drivetime
     */
    public double getDrivetime()
    {
        return drivetime;
    }

    /**
     * Set next turn
     * 
     * @param t Turn
     */
    public void setTurn(Turn t)
    {
        turn = t;
    }

    /**
     * Get turn
     * 
     * @return Turn
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
                if (from.getX() < to.getX())
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
