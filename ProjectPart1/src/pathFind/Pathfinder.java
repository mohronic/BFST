/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathFind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import model.Road;
import view.Canvas;

public class Pathfinder {

    private ArrayList<Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private PriorityQueue<Integer> pq;
    private ArrayList<ArrayList<Road>> graph;
    private ArrayList<Road> edgeTo;
    private ArrayList<Road> roads;
    private int s, e;

    public Pathfinder(ArrayList<Road> roads) {
        pq = new PriorityQueue(10, getComparator());
        distTo = new ArrayList<>();
        edgeTo = new ArrayList<>();
        this.roads = roads;
    }

    public ArrayList<Road> getPath(Road f, Road t) {
        createGraph();
        s = f.getFn().getKDV();
        e = t.getFn().getKDV();
        distTo.set(s, 0.0);
        pq.add(s);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            ArrayList<Road> temp = graph.get(v);
            for (Road r : temp) {
                if (relax(r, v)) {
                    setRoute();
                    return null;
                }
            }
        }
        return null;
    }

    private Comparator<Integer> getComparator() {

        Comparator<Integer> comp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o, Integer t) //Doesnt work with A-Stary
            {
                if (distTo.get(o) < distTo.get(t)) {
                    return -1;
                } else if (distTo.get(o) == distTo.get(t)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };

        return comp;
    }

    private void createGraph() {
        graph = new ArrayList<>();
        for (Road r : roads) {
            int fID = r.getFn().getKDV();
            int tID = r.getTn().getKDV();
            int l = Math.max(fID, tID);
            if (graph.size() < l + 1) {
                for (int i = graph.size(); i < l + 1; i++) {
                    graph.add(null);
                    distTo.add(Double.POSITIVE_INFINITY);
                    edgeTo.add(null);
                }
            }

            switch (r.getEd().ONE_WAY) {
                case "":
                    if (graph.get(fID) == null) {
                        graph.set(fID, new ArrayList<Road>());
                    }
                    if (graph.get(tID) == null) {
                        graph.set(tID, new ArrayList<Road>());
                    }
                    graph.get(fID).add(r);
                    graph.get(tID).add(r);
                    break;
                case "tf":
                    if (graph.get(tID) == null) {
                        graph.set(tID, new ArrayList<Road>());
                    }
                    graph.get(tID).add(r);
                    break;
                case "ft":
                    if (graph.get(fID) == null) {
                        graph.set(fID, new ArrayList<Road>());
                    }
                    graph.get(fID).add(r);
                    break;
            }
        }
    }

// relax edge e and update pq if changed
    private boolean relax(Road r, int v) {
        int w = 0;
        if (r.getFn().getKDV() != v) {
            w = r.getFn().getKDV();
        } else {
            w = r.getTn().getKDV();
        }
        if (distTo.get(w) > distTo.get(v) + r.getLength()) {
            distTo.set(w, distTo.get(v) + r.getLength());
            edgeTo.set(w, r);
            if (w == e) {
                return true;
            }
            if (pq.contains(w)) {
                pq.remove(w);
                pq.add(w);
            } else {
                pq.add(w);
            }
        }
        return false;
    }

    private void setRoute() {
       ArrayList<Road> path = new ArrayList<>();
        int cNode = e;
        int i = 0;
        Road temp = edgeTo.get(cNode);;
        while (temp != null) {
            path.add(temp);
            if (temp.getFn().getKDV() == cNode) {
               cNode = temp.getTn().getKDV();
            } else {
               cNode = temp.getFn().getKDV();
            }
            temp = edgeTo.get(cNode);
            System.out.println(""+i++);
        }
        Canvas.path = path;
    }

    /*public double distTo(int v) {
     return distTo[v];
     }

     public boolean hasPathTo(int v) {
     return distTo[v] < Double.POSITIVE_INFINITY;
     }

     public Iterable<DirectedEdge> pathTo(int v) {
     if (!hasPathTo(v)) {
     return null;
     }
     Stack<DirectedEdge> path = new Stack<DirectedEdge>();
     for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
     path.push(e);
     }
     return path;
     }*/
}
