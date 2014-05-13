package Route;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import model.Road;

/**
 * Finds the fastest road (drivetime). Extends DijkstraSP, and overrides the
 * abstract methods
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class FastestRoad extends DijkstraSP
{

    /**
     *
     * @param allEdges
     */
    public FastestRoad(ArrayList<Road> allEdges)
    {
        super(allEdges);
    }

    /**
     * Makes the Comparator used by the priorityqueue. Compares the DriveTime
     *
     * @return Comparator<DirectedEdge>
     */
    @Override
    protected Comparator<Road> getComparator()
    {
        Comparator<Road> comp = new Comparator<Road>()
        {

            @Override
            public int compare(Road t, Road t1) //Doesnt work with A-Stary
            {
                Linked tmp = distTo.get(t.from());
                Linked tmp2 = distTo.get(t1.from());

                return Double.compare(tmp.getDrivetime(), tmp2.getDrivetime());
            }
        };

        return comp;
    }

    /**
     * Finds the fastest path to all points from Point p, by known edges. If a
     * faster path to Point t is found, t will be upated and added to the
     * PriorityQueue
     *
     * @param p Point in which the bag will be retrieved, and going through
     */
    @Override
    protected void relax(Point2D.Double p)
    {
        ArrayList<Road> list = adj.get(p);
        if (list != null) // Blindvej, slutpunkt
        {
            for (Road r : list)
            {
                Point2D.Double t = r.to();
                Linked from = distTo.get(p);
                Linked to = distTo.get(t);
                if (to.getDrivetime() > from.getDrivetime() + r.getDrivetime())
                {
                    to.setFrom(p);
                    to.setLength(from.getLength() + r.getLength());
                    to.setDrivetime(from.getDrivetime() + r.getDrivetime());
                    to.setEdge(r);
                    distTo.put(t, to);
                    if (!pq.contains(r))
                    {
                        pq.add(r);
                    }
                }
            }
        }

    }
}
