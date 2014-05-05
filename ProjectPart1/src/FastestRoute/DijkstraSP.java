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
 * This class uses an algorithem inspired by Dijkstra for Shortest Path. This
 * class has 2 childs, one which finds the shortest road, and one which finds
 * the fastest road.
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public abstract class DijkstraSP
{

    /**
     * HashMap with Point as key and Linked as value. The length/drivetime to
     * this point is accumulated, so it is the total distance from "From"
     */
    protected HashMap distTo;

    /**
     * A PriorityQueue sorted by either the accumulated Drivetime or Length.
     */
    protected PriorityQueue<DirectedEdge> pq;

    /**
     * A Hashmap with Point as key and a Bag as value. The Bag contains all the
     * Directededges that uses the point
     */
    protected HashMap adj; // naboer

    /**
     * Sets up the HashMaps with data from allEdges.
     *
     * @param allEdges ArrayList<Road> with all edges to build the DirectedGraph
     */
    public DijkstraSP(ArrayList<Road> allEdges)
    {
        adj = new HashMap();
        distTo = new HashMap();
        pq = new PriorityQueue<>(10, getComparator());
        buildHashMaps(allEdges);
    }

    /**
     * Makes the Comparator used by the priorityqueue.
     *
     * @return Comparator<DirectedEdge>
     */
    protected abstract Comparator<DirectedEdge> getComparator();

    /**
     * Finds the shortest path to a point.
     *
     * @param p Point in which the bag will be retrieved, and going through
     */
    protected abstract void relax(Point2D.Double p);

    /**
     * Gets the route from at point to another.
     *
     * @param s Point From
     * @param t Point To
     * @return ArrayList<Linked> The route
     */
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
            if (e.from().getX() == t.from().getX() && e.from().getY() == t.from().getY()) //Can compare the 'to' values aswell.
            {
                break;
            } else
            {
                relax(e.to());
            }
        }
        return getRoute(t);
    }

    /**
     * Backtrack the route through the linked list
     *
     * @return ArrayList<Linked> The route
     */
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

    /**
     * Adds the directed edges to the bag from their "from" point
     *
     */
    private void addEdge(DirectedEdge e)
    {
        if (adj.get(e.from()) == null)
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
            switch (r.getEd().ONE_WAY) // One way routes, should be put in the correct direction
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
