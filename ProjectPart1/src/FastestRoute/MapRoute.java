/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FastestRoute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class MapRoute
{

    private final ArrayList<Road> roads;

    public MapRoute(ArrayList<Road> roads)
    {
        this.roads = roads;
        sortRoads();
        DirectedEdge from = new DirectedEdge(roads.get(509400));
        DirectedEdge to = new DirectedEdge(roads.get(715000));
        System.out.println(from.getName() + " " + to.getName());

        DijkstraSP SP = new FastestRoad(roads);
        ArrayList<Linked> route = SP.mapRoute(from, to);
        String old = "hej";
        double time = 0.0;
        for (Linked l : route)
        {
            time = time + l.getRoad().getEd().DRIVETIME;
            System.out.println("køretid: " + time);
            if (!old.matches(l.getEdge().getName()))
            {
                System.out.println(l.getEdge().getName());
            }
            old = l.getEdge().getName();
        }
    }

    private void sortRoads()
    {
        Collections.sort(roads, new Comparator<Road>()
        {
            @Override
            public int compare(Road r1, Road r2)
            {
                return r1.getEd().VEJNAVN.compareToIgnoreCase(r2.getEd().VEJNAVN);
            }
        });
    }

//    private DirectedEdge getDirectedEdgeFromName(String s)
//    {
//        Collections.binarySearch(roads, s);
//        
//    }
}
