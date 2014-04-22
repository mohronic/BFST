/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FastestRoute;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class DijkstraSP
{

    private HashMap distTo; //(point, Linked) distTo[w] = edgeTo[w].weight();
    private PriorityQueue<DirectedEdge> pq;
    private HashMap adj; // naboer

    public DijkstraSP(ArrayList<Road> allEdges) // konstruktor tager kun allEdges, laver ny metode med til og fra som retunere listen med Linked
    {
        adj = new HashMap();
        distTo = new HashMap();
        pq = new PriorityQueue<DirectedEdge>(10, new Comparator<DirectedEdge>()
        {

            @Override
            public int compare(DirectedEdge t, DirectedEdge t1) //omvendt?
            {
                Linked tmp = (Linked) distTo.get(t.from());
                Linked tmp2 = (Linked) distTo.get(t1.from()); // SKAL DET VÆRE TO ELLER FROM HER? SAMME LIGE OVER, TO ELLER FROM?

                return Double.compare(tmp.getLength(), tmp2.getLength());
                //return Double.compare(t.weight(), t1.weight());
            }

        });

        for (Road r : allEdges)
        {
            DirectedEdge e1 = new DirectedEdge(r, true);
            addEdge(e1);
            buildDistTo(e1);
            DirectedEdge e2 = new DirectedEdge(r, false);
            addEdge(e2);
        }
    }

    public ArrayList<Linked> mapRoute(DirectedEdge s, DirectedEdge t)
    {
        Linked source = new Linked();
        source.setLength(0.0);
        distTo.put(s.from(), source);
        relax(s.from());
        while (!pq.isEmpty())
        {
            DirectedEdge e = pq.poll();
            if (e.from().getX() == t.from().getX() && e.from().getY() == t.from().getY())
            {
                break;
            } else
            {
                relax(e.to());
            }
        }
        return getRoute(t);
    }

    private ArrayList<Linked> getRoute(DirectedEdge t) // når aldrig ud til den her edge af en eller anden grund????
    {
        ArrayList<Linked> list = new ArrayList<>();
        Point2D.Double loc = t.to();
        while (loc != null)
        {
            Linked l = (Linked) distTo.get(loc);
            list.add(l);
            loc = l.getFrom();
        }
        return list;
    }

    private void relax(Point2D.Double p)
    {
        Bag<DirectedEdge> b = (Bag<DirectedEdge>) adj.get(p);
        if (b != null) // Blindvej, slutpunkt
        {
            for (DirectedEdge e : b)
            {
                Point2D.Double t = e.to();
                Linked from = (Linked) distTo.get(p);
                Linked to = (Linked) distTo.get(t);
                if (to.getLength() > from.getLength() + e.weight()) // er t ikke det samme for alle, siden alle i den bag netop har samme udgangspunkt
                {
                    to.setFrom(p);
                    to.setLength(from.getLength() + e.weight());
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

    private void addEdge(DirectedEdge e)
    {
        if (adj.get(e.from()) == null) // Contains key istedet?? OGSÅ BAGS FOR e.to HVAD NU HVIS 2 veje peger mod hinanden som --> <--- så vil midten aldrig få en bag
        {
            Bag<DirectedEdge> bag = new Bag<>();
            bag.add(e);
            adj.put(e.from(), bag);
        } else
        {
            Bag<DirectedEdge> bag = (Bag<DirectedEdge>) adj.get(e.from());
            bag.add(e);
            adj.put(e.from(), bag);
        }
    }

    private void buildDistTo(DirectedEdge e)
    {
        Point2D.Double s = e.from();
        Point2D.Double t = e.to();
        distTo.put(s, new Linked());
        distTo.put(t, new Linked());
    }
}
