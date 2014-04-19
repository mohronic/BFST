/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FastestRoute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
        
        DijkstraSP SP = new DijkstraSP(roads, new DirectedEdge(roads.get(40600)));
        HashMap hash = SP.getDistTo();
        ArrayList<Linked> route = SP.getRoute(hash, new DirectedEdge(roads.get(70400)));
        for(Linked l : route)
        {
            System.out.println(l.getFrom());
        }
    }

    
    
    
//    private void sortRoads()
//    {
//        System.out.println(roadsTest.get(0).getEd().VEJNAVN + " " + roadsTest.get(1).getEd().VEJNAVN + " " + roadsTest.get(2).getEd().VEJNAVN + " " + roadsTest.get(3).getEd().VEJNAVN);
//        Collections.sort(roadsTest, new Comparator<Road>()
//        {
//            @Override
//            public int compare(Road r1, Road r2)
//            {
//                return r1.getEd().VEJNAVN.compareToIgnoreCase(r2.getEd().VEJNAVN);
//            }
//        });
//        System.out.println(roadsTest.get(0).getEd().VEJNAVN + " " + roadsTest.get(1).getEd().VEJNAVN + " " + roadsTest.get(2).getEd().VEJNAVN + " " + roadsTest.get(3).getEd().VEJNAVN);
//    }

    public void getRoute(String from, String to)
    {
        
    }
    
//    private Point getCoordinateFromName(String s)
//    {
//        Road r = Collections.binarySearch(roads, s);
//    }
}
