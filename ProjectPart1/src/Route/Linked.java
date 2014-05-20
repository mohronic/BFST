package Route;

import java.awt.geom.Point2D;
import model.Road;

/**
 * This class is used to make a linked list of the final fastest/shortest route.
 * Each instance will point to its former Linked in the route.
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Linked
{

    private double length;
    private double drivetime;
    private int from; // Only -1 where the road search starts
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
     * @return int From
     */
    public int getFrom()
    {
        return from;
    }

    /**
     * Sets the former point
     *
     * @param f int From
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
     * @param r
     * @param prev
     */
    public void setEdge(Road r, Road prev)
    {
        edge = r;
        if (prev != null)
        {
            calTurn(prev);
        }

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
     * @return Road edge
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

    /**
     * Calculates the turn be calculating the angle between the two vectors
     *
     */
    private void calTurn(Road prev)
    {

        if (from != -1)
        {

            Point2D.Double C;
            Point2D.Double B;
            Point2D.Double A;

            if (from == edge.getFn().getKDV())
            {
                B = edge.from();
                C = edge.to();
            } else
            {
                C = edge.from();
                B = edge.to();
            }

            if (from == prev.getTn().getKDV())
            {
                A = prev.from();
            } else
            {
                A = prev.to();
            }

            Point2D.Double AB = new Point2D.Double(A.getX() - B.getX(), A.getY() - B.getY());
            Point2D.Double CB = new Point2D.Double(C.getX() - B.getX(), C.getY() - B.getY());

            double theta = Math.atan2(AB.getY(), AB.getX()) - Math.atan2(CB.getY(), CB.getX());

            //Normalize angle
            if (theta < 0)
            {
                theta += 2 * Math.PI;
            }

            if (theta == 0)
            {
                turn = Turn.FORWARD;
            } else if (theta < Math.PI)
            {
                turn = Turn.LEFT;
            } else
            {
                turn = Turn.RIGHT;
            }

        }
    }
}
