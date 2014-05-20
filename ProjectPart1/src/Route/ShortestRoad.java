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
     * length. A* inspired comparison
     *
     * @return Comparator<Integer>
     */
    @Override
    protected Comparator<Integer> getComparator()
    {
        Comparator<Integer> comp = new Comparator<Integer>()
        {

            @Override
            public int compare(Integer t1, Integer t2)
            {
                Linked tmp, tmp2;
                tmp = distTo.get(t1);
                tmp2 = distTo.get(t2);

                Point2D.Double first, second;
                first = tmp.getEdge().to();
                second = tmp2.getEdge().to();

                double length1, length2;
                length1 = Math.sqrt(Math.pow(first.getX() - t.from().getX(), 2) + Math.pow(first.getY() - t.from().getY(), 2)); //fulgeflugt længde til slut punkt
                length2 = Math.sqrt(Math.pow(second.getX() - t.from().getX(), 2) + Math.pow(second.getY() - t.from().getY(), 2)); //fugleflugt længde til slut punkt

                length1 += tmp.getLength();
                length2 += tmp2.getLength();

                return Double.compare(length1, length2);
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

            if (to.getLength() > from.getLength() + r.getLength())          
            {
                to.setFrom(p);
                to.setLength(from.getLength() + r.getLength());
                to.setDrivetime(from.getDrivetime() + r.getDrivetime());
                to.setEdge(r, distTo.get(p).getEdge());
                distTo.set(q, to);
                if (!pq.contains(q))                                        
                {
                    pq.add(q);
                }
            }
        }
     }
    }

}
