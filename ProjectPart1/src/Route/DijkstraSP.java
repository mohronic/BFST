package Route;

import ctrl.StartMap;
import java.util.ArrayList;
import java.util.Comparator;
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
    protected ArrayList<Linked> distTo;

    /**
     * A PriorityQueue sorted by either the accumulated Drivetime or Length.
     */
    protected PriorityQueue<Integer> pq;

    /**
     * A Hashmap with Point as key and a Bag as value. The Bag contains all the
     * Directededges that uses the point
     */
    protected ArrayList<ArrayList<Road>> adj; // naboer

    /**
     * Sets up the HashMaps with data from allEdges.
     *
     * @param allEdges ArrayList<Road> with all edges to build the DirectedGraph
     */
    public DijkstraSP(ArrayList<Road> allEdges)
    {
        adj = StartMap.adj;
        distTo = new ArrayList<>();
        pq = new PriorityQueue<>(10, getComparator());
        buildDistTo(allEdges);
    }

    /**
     * Makes the Comparator used by the priorityqueue.
     *
     * @return Comparator<DirectedEdge>
     */
    protected abstract Comparator<Integer> getComparator();

    /**
     * Finds the shortest path to a point.
     *
     * @param p Point in which the bag will be retrieved, and going through
     */
    protected abstract void relax(int p);

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
        distTo.set(sourceRoad.getFn().getKDV(), source);
        relax(sourceRoad.getFn().getKDV());
        while (!pq.isEmpty())
        {
            int i = pq.poll();
            if (targetRoad.getTn().getKDV() == i || targetRoad.getFn().getKDV() == i)//Can compare the 'to' values aswell.
            {
                return getRoute(targetRoad);
            } else
            {
                relax(i);
            }
        }
        return null; // The route was not found
    }

    /**
     * Backtrack the route through the linked list
     *
     * @return ArrayList<Linked> The route
     */
    private ArrayList<Linked> getRoute(Road targetRoad)
    {
        ArrayList<Linked> list = new ArrayList<>();
        int loc;
        if (distTo.get(targetRoad.getFn().getKDV()) == null)
        {
            loc = targetRoad.getTn().getKDV();
        } else
        {
            loc = targetRoad.getFn().getKDV();
        }
        while (loc != -1)
        {
            Linked l = distTo.get(loc);
            list.add(l);
            loc = l.getFrom();
        }
        return list;
    }

    private void buildDistTo(ArrayList<Road> allEdges)
    {
        for (Road r : allEdges)
        {
            distTo.add(null);

        }

    }

    public static void addEdgeToAdj(Road r)
    {
        int fID = r.getFn().getKDV();
        int tID = r.getTn().getKDV();
        int l = Math.max(fID, tID);
        if (StartMap.adj.size() < l + 1)
        {
            for (int i = StartMap.adj.size(); i < l + 1; i++)
            {
                StartMap.adj.add(null);
            }
        }
        switch (r.getEd().ONE_WAY) // One way routes, should be put in the correct direction
        {
            case "":
            {
                if (StartMap.adj.get(fID) == null)
                {
                    StartMap.adj.set(fID, new ArrayList<Road>());
                }
                if (StartMap.adj.get(tID) == null)
                {
                    StartMap.adj.set(tID, new ArrayList<Road>());
                }
                StartMap.adj.get(fID).add(r);
                StartMap.adj.get(tID).add(r);
                break;
            }
            case "tf":
            {
                if (StartMap.adj.get(tID) == null)
                {
                    StartMap.adj.set(tID, new ArrayList<Road>());
                }
                StartMap.adj.get(tID).add(r);
                break;
            }
            case "ft":
            {
                if (StartMap.adj.get(fID) == null)
                {
                    StartMap.adj.set(fID, new ArrayList<Road>());
                }
                StartMap.adj.get(fID).add(r);
                break;
            }
            default:
            {
                //should not happen
                break;
            }
        }
    }
}
