/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AddressSearchEngine;

import ctrl.StartMap;
import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class RoadNameSearcher
{
    public ArrayList<Road> roadList = StartMap.allRoads;
    
    public Road searchRoad(String roadNamePrefix)
    {
        Road temp = null;
        for (Road road : roadList)
        {
            if (road.getEd().VEJNAVN.startsWith(roadNamePrefix))
            {
                temp = road;
                break;
            }
        }
        return temp;
    }
    
    public Road searchRoad(String roadNamePrefix, int zipCode)
    {
        Road temp = null;
        if(!StartMap.osm) //Krak
        {
            for (Road road : roadList)
            {
                if (road.getEd().VEJNAVN.startsWith(roadNamePrefix) && road.getEd().V_POSTNR == zipCode)
                {
                    temp = road;
                    break;
                }
            }
        }
        else //OSM
                {
            temp = searchRoad(roadNamePrefix);
        }
        return temp;
    }
}
