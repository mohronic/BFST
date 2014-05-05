package Route;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import model.Road;

/**
 * Finds the shortest route. Extends DijkstraSP, and overrides the abstract
 * methods
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class ShortestRoad extends DijkstraSP
{

    /**
     *
     * @param allEdges
     */
    public ShortestRoad(ArrayList<Road> allEdges)
    {
        super(allEdges);
    }

    /**
     * Makes the Comparator used by the priorityqueue. Compares the accumulated
     * length
     *
     * @return Comparator<DirectedEdge>
     */
    @Override
    protected Comparator<DirectedEdge> getComparator()
    {
        Comparator<DirectedEdge> comp = new Comparator<DirectedEdge>()
        {

            @Override
            public int compare(DirectedEdge t, DirectedEdge t1)
            {
                Linked tmp = (Linked) distTo.get(t.from());
                Linked tmp2 = (Linked) distTo.get(t1.from());

                return Double.compare(tmp.getLength(), tmp2.getLength());
            }
        };

        return comp;
    }

    /**
     * Finds the shortest path to all points from Point p, by known edges. If a
     * shorter path to Point t is found, t will be upated and added to the
     * PriorityQueue
     *
     * @param p Point in which the bag will be retrieved, and going through
     */
    @Override
    protected void relax(Point2D.Double p)
    {
        Bag<DirectedEdge> b = (Bag<DirectedEdge>) adj.get(p);
        if (b != null) // Blindvej, slutpunkt
        {
            for (DirectedEdge e : b)
            {
                Point2D.Double t = e.to();
                Linked from = (Linked) distTo.get(p);
                Linked to = (Linked) distTo.get(t);
                if (to.getLength() > from.getLength() + e.length())
                {
                    to.setFrom(p);
                    to.setLength(from.getLength() + e.length());
                    to.setDrivetime(from.getDrivetime() + e.drivetime());
                    to.setEdge(e);
                    distTo.put(t, to);
                    if (!pq.contains(e))
                    {
                        pq.add(e);
                    }
                }
            }
        }

    }
}