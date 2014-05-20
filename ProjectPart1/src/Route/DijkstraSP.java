package Route;

import ctrl.StartMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import model.Road;

/**
 * This class uses an algorithem inspired by Dijkstra for Path Finding. This
 * class has 2 childs, one which finds the shortest road, and one which finds
 * the fastest road.
 *
 */
public abstract class DijkstraSP
{
    /**
     * Used in A* to compare to the target coordinates.
     *
     */
    protected Road t;

    /**
     * ArrayList of all nodes. The length/drivetime to this a give note is
     * accumulated, so it is the total distance from "From". Furthere more is
     * the untouched notes distance and drivetime infinity
     */
    protected ArrayList<Linked> distTo;

    /**
     * A PriorityQueue sorted by either the accumulated Drivetime or Length.
     */
    protected PriorityQueue<Integer> pq;

    /**
     * Arraylist of all neighbors. A bag for a given node contains all Roads the
     * uses this node.
     */
    protected ArrayList<ArrayList<Road>> adj; // naboer

    /**
     * Basic setup
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
     * @return Comparator<Integer>
     */
    protected abstract Comparator<Integer> getComparator();

    /**
     * Finds the shortest or fastest path to a point.
     *
     * @param p Point in which the bag will be retrieved
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
        source.setEdge(sourceRoad, null);
        distTo.set(sourceRoad.getFn().getKDV(), source);
        relax(sourceRoad.getFn().getKDV());
        while (!pq.isEmpty())
        {
            int i = pq.poll();
            if (targetRoad.getTn().getKDV() == i || targetRoad.getFn().getKDV() == i)
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

    /**
     * Builds the adjencies list. Adds the roads either both way or only one way
     * depending on the Roads direction
     *
     * @param r Road
     */
    public static void addEdgeToAdj(Road r)
    {
        int fID = r.getFn().getKDV();
        int tID = r.getTn().getKDV();
        int l = Math.max(fID, tID);
        if (StartMap.adj.size() < l + 1)
        {
            for (int i = StartMap.adj.size(); i < l + 1; i++) // If the arraylist is not long enough yet to contain the node
            {
                StartMap.adj.add(null);
            }
        }
        switch (r.getEd().ONE_WAY) // One way routes, should be put in the correct direction
        {
            case "": //not onewayed
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

    /**
     *
     * @return
     */
    public ArrayList<Linked> getDistTo() 
    {
        return distTo;
    }
}
