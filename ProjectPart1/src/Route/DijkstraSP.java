package Route;

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
     * Used in A* to compare to the target coordinates.
     * 
     */
    protected Road t;

    /**
     * HashMap with Point as key and Linked as value. The length/drivetime to
     * this point is accumulated, so it is the total distance from "From"
     */
    protected HashMap<Point2D.Double, Linked> distTo;

    /**
     * A PriorityQueue sorted by either the accumulated Drivetime or Length.
     */
    protected PriorityQueue<Road> pq;

    /**
     * A Hashmap with Point as key and a Bag as value. The Bag contains all the
     * Directededges that uses the point
     */
    protected HashMap<Point2D.Double, ArrayList<Road>> adj; // naboer

    /**
     * Sets up the HashMaps with data from allEdges.
     *
     * @param allEdges ArrayList<Road> with all edges to build the DirectedGraph
     */
    public DijkstraSP(ArrayList<Road> allEdges)
    {
        adj = new HashMap<>();
        distTo = new HashMap<>();
        pq = new PriorityQueue<>(10, getComparator());
        buildHashMaps(allEdges);
    }

    /**
     * Makes the Comparator used by the priorityqueue.
     *
     * @return Comparator<DirectedEdge>
     */
    protected abstract Comparator<Road> getComparator();

    /**
     * Finds the shortest path to a point.
     *
     * @param p Point in which the bag will be retrieved, and going through
     */
    protected abstract void relax(Point2D.Double p);

    /**
     * Gets the route from at point to another.
     *
     * @param sourceRoad
     * @param targetRoad
     * @return ArrayList<Linked> The route
     */
    public ArrayList<Linked> mapRoute(Road sourceRoad, Road targetRoad)
    {
        t = targetRoad;
        Linked source = new Linked();
        source.setLength(0.0);
        source.setDrivetime(0.0);
        source.setEdge(sourceRoad);
        distTo.put(sourceRoad.from(), source);
        relax(sourceRoad.from());
        while (!pq.isEmpty())
        {
            Road r = pq.poll();
            if (r.from().getX() == targetRoad.from().getX() && r.from().getY() == targetRoad.from().getY()) //Can compare the 'to' values aswell.
            {
                break;
            } else
            {
                relax(r.to());
            }
        }
        return getRoute(targetRoad);
    }

    /**
     * Backtrack the route through the linked list
     *
     * @return ArrayList<Linked> The route
     */
    private ArrayList<Linked> getRoute(Road targetRoad)
    {
        ArrayList<Linked> list = new ArrayList<>();
        Point2D.Double loc = targetRoad.to();
        while (loc != null)
        {
            Linked l = distTo.get(loc);
            list.add(l);
            loc = l.getFrom();
        }
        return list;
    }

    /**
     * Adds the directed edges to the bag from their "from" point
     *
     */
    private void addEdge(Road r)
    {
        if (adj.get(r.from()) == null)
        {
            ArrayList<Road> list = new ArrayList<>();
            list.add(r);
            adj.put(r.from(), list);
        } else
        {
            ArrayList<Road> list = adj.get(r.from());
            list.add(r);
            adj.put(r.from(), list);
        }
    }

    private void buildHashMaps(ArrayList<Road> allEdges)
    {
        for (Road r : allEdges)
        {
            switch (r.getEd().ONE_WAY) // One way routes, should be put in the correct direction
            {
                case "":
                {
                    addEdge(r);
                    Road r2 = new Road(r);
                    addEdge(r2);
                    buildDistTo(r);
                    break;
                }
                case "tf":
                    addEdge(r);
                    buildDistTo(r);
                    break;
                case "ft":
                {
                    Road r2 = new Road(r);
                    addEdge(r2);
                    buildDistTo(r);
                    break;
                }
                default: 
                    System.out.println("hello 165");
                    //should not happen
                    break;
            }
        }
    }

    private void buildDistTo(Road r)
    {
        Point2D.Double s = r.from();
        Point2D.Double t = r.to();
        distTo.put(s, new Linked());
        distTo.put(t, new Linked());
    }
}
