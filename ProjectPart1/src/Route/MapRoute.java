/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Route;

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

    private final ArrayList<Road> allRoads;
    private static ArrayList<Linked> route;

    /**
     *
     * @param roads
     */
    public MapRoute(ArrayList<Road> roads)
    {
        route = null;
        this.allRoads = roads;

        DijkstraSP SP = new FastestRoad(roads);
        route = SP.mapRoute(roads.get(509400), roads.get(715000));
    }

    /**
     *
     * @return
     */
    public static ArrayList<Linked> getRoute()
    {
        if (route == null)
        {
            return null;
        } else
        {
            return route;
        }

    }

    private void sortRoads()
    {
        Collections.sort(allRoads, new Comparator<Road>()
        {
            @Override
            public int compare(Road r1, Road r2)
            {
                return r1.getEd().VEJNAVN.compareToIgnoreCase(r2.getEd().VEJNAVN);
            }
        });
    }
}
