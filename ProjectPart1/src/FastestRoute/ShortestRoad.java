/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FastestRoute;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import model.Road;

/**
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
     *
     * @return
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
                Linked tmp2 = (Linked) distTo.get(t1.from()); // SKAL DET VÆRE TO ELLER FROM HER? SAMME LIGE OVER, TO ELLER FROM?

                return Double.compare(tmp.getLength(), tmp2.getLength());
            }
        };

        return comp;
    }

    /**
     *
     * @param p
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
                if (to.getLength() > from.getLength() + e.length()) // er t ikke det samme for alle, siden alle i den bag netop har samme udgangspunkt
                {
                    to.setFrom(p);
                    to.setLength(from.getLength() + e.length());
                    to.setDrivetime(from.getDrivetime()+ e.drivetime());
                    to.setEdge(e);
                    distTo.put(t, to);
                    if (!pq.contains(e)) // hvorfor er det her if statment ikke uden for det andet ovenstående if statment?
                    {
                        pq.add(e);
                    }
                }

                //KOPI TIL ALTERNATIV LØSNING HVOR MAN IKKE TILFØJER SLUT PUNKTER, MEN TJEKKER FOR DET HER OGSÅ GØR
//            if (distTo.get(t) == null) // slut punkter ikke tilføjet
//            {
//                distTo.put(t, (double) distTo.get(p) + e.weight());
//
//            } else if ((double) distTo.get(t) > (double) distTo.get(p) + e.weight()) // er t ikke det samme for alle, siden alle i den bag netop har samme udgangspunkt
//            {
//                distTo.put(t, (double) distTo.get(p) + e.weight());
//                //edgeTo[t] = e;
//                if (!pq.contains(e))
//                {
//                    pq.add(e);
//                }
//            }
            }
        }

    }
}
