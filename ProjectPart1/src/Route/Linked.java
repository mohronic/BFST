package Route;

import ctrl.StartMap;
import java.awt.geom.Point2D;
import java.util.ArrayList;
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
    private int from; // Only null where the road search starts
    private Road edge; // edge which contains the original road
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
        from = -1;
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
    public int getFrom()
    {
        return from;
    }

    /**
     * Sets the former point
     *
     * @param f Point2D.Double From
     */
    public void setFrom(int f)
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
    public void setEdge(Road r)
    {
        edge = r;
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
    public Road getEdge()
    {
        return edge;
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

        if (from != -1)
        {
            Point2D.Double toNode;
            Point2D.Double fromNode;

            if (from == edge.getFn().getKDV())
            {
                toNode = edge.to();
            } else
            {
                toNode = edge.from();
            }

            ArrayList<Road> roads = StartMap.adj.get(from);
            Road r = roads.get(0);

            if (r.getFn().getKDV() == from)
            {
                fromNode = r.from();
            } else
            {
                fromNode = r.to();
            }

            if (fromNode.getY() < toNode.getY())
            {
                if (fromNode.getX() < toNode.getX())
                {
                    turn = Turn.LEFT;
                } else
                {
                    turn = Turn.RIGHT;
                }
            } else
            {
                if (fromNode.getX() < toNode.getX())
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
