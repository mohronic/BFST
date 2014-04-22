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
        DirectedEdge to = new DirectedEdge(roads.get(459389));
        System.out.println(from.getName() + " " + to.getName());
        
        DijkstraSP SP = new DijkstraSP(roads);
        ArrayList<Linked> route = SP.mapRoute(from, to);
        for (Linked l : route)
        {
            System.out.println(l.getFrom());
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