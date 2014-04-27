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
public abstract class DijkstraSP
{

    protected HashMap distTo; //(point, Linked) distTo[w] = edgeTo[w].weight();
    protected PriorityQueue<DirectedEdge> pq;
    protected HashMap adj; // naboer

    public DijkstraSP(ArrayList<Road> allEdges)
    {
        adj = new HashMap();
        distTo = new HashMap();
        pq = new PriorityQueue<>(10, getComparator());
        buildHashMaps(allEdges);
    }

    protected abstract Comparator<DirectedEdge> getComparator();

    protected abstract void relax(Point2D.Double p);

    public ArrayList<Linked> mapRoute(DirectedEdge s, DirectedEdge t)
    {
        Linked source = new Linked();
        source.setLength(0.0);
        source.setDrivetime(0.0);
        source.setEdge(s);
        distTo.put(s.from(), source);
        relax(s.from());
        while (!pq.isEmpty())
        {
            DirectedEdge e = pq.poll();
            if (e.from().getX() == t.from().getX() && e.from().getY() == t.from().getY()) //Skal det være from eller to?
            {
                break;
            } else
            {
                relax(e.to());
            }
        }
        return getRoute(t);
    }

    private ArrayList<Linked> getRoute(DirectedEdge t)
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

    private void buildHashMaps(ArrayList<Road> allEdges)
    {
        for (Road r : allEdges)
        {
            DirectedEdge e1 = new DirectedEdge(r, true);
            switch (r.getEd().ONE_WAY)
            {
                case "":
                {
                    addEdge(e1);
                    DirectedEdge e2 = new DirectedEdge(r, false);
                    addEdge(e2);
                    buildDistTo(e1);
                    break;
                }
                case "tf":
                    addEdge(e1);
                    buildDistTo(e1);
                    break;
                case "ft":
                {
                    DirectedEdge e2 = new DirectedEdge(r, false);
                    addEdge(e2);
                    buildDistTo(e1);
                    break;
                }
                default:
                    //should not happen
                    break;
            }
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
