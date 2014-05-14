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
    protected Comparator<Integer> getComparator()
    {
        Comparator<Integer> comp = new Comparator<Integer>()
        {

            @Override
            public int compare(Integer t, Integer t1) //Doesnt work with A-Stary
            {
                Linked tmp = distTo.get(t);
                Linked tmp2 = distTo.get(t1);
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
    protected void relax(int p)
    {
        ArrayList<Road> list = adj.get(p);
        if (list != null) // Blindvej, slutpunkt
        {
            for (Road r : list)
            {
                int q;
                if (p == r.getTn().getKDV())
                {
                    q = r.getFn().getKDV();
                } else
                {
                    q = r.getTn().getKDV();
                }

                Linked from = distTo.get(p);
                if (distTo.get(q) == null)
                {
                    distTo.set(q, new Linked());
                }
                Linked to = distTo.get(q);
                
                if (to.getDrivetime() > from.getDrivetime() + r.getDrivetime())
                {
                    to.setFrom(p);
                    to.setLength(from.getLength() + r.getLength());
                    to.setDrivetime(from.getDrivetime() + r.getDrivetime());
                    to.setEdge(r);
                    distTo.set(q, to);
                    if (!pq.contains(q))
                    {
                        pq.add(q);
                    }
                }
            }
        }

    }

//    @Override
//    protected void relax(int p)
//    {
//        ArrayList<Road> list = adj.get(p);
//        if (list != null) // Blindvej, slutpunkt
//        {
//            for (Road r : list)
//            {
//                Linked from = distTo.get(p);
//                Linked to;
//                int kdv;
//                if (r.getTn().getKDV() == p) //The road is reversed
//                {
//                    if (distTo.get(r.getFn().getKDV()) == null)
//                    {
//                        distTo.set(r.getFn().getKDV(), new Linked());
//                    }
//                    kdv = r.getTn().getKDV();
//                    to = distTo.get(r.getFn().getKDV());
//                } else
//                {
//                    if (distTo.get(r.getTn().getKDV()) == null)
//                    {
//                        distTo.set(r.getTn().getKDV(), new Linked());
//                    }
//                    to = distTo.get(r.getTn().getKDV());
//                    kdv = r.getFn().getKDV();
//                }
//
//                if (to.getDrivetime() > from.getDrivetime() + r.getDrivetime())
//                {
//                    to.setFrom(p);
//                    to.setLength(from.getLength() + r.getLength());
//                    to.setDrivetime(from.getDrivetime() + r.getDrivetime());
//                    to.setEdge(r);
//                    distTo.set(kdv, to);
//                    if (!pq.contains(r))
//                    {
//                        pq.add(r);
//                    }
//                }
//            }
//        }
//
//    }
}
