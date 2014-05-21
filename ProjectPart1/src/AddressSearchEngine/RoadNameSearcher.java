package AddressSearchEngine;

import ctrl.StartMap;
import java.util.ArrayList;
import model.Road;

/**
 * The RoadNameSearcher class is used for linear search through allRoads 
 * ArrayList to return Road objects with seachquery of street name, or
 * street name and zip.
 * @author Gruppe A.
 */
public class RoadNameSearcher
{
    public ArrayList<Road> roadList = StartMap.allRoads;
    
    /**
     * SearchRoad is used for linear search through allRoads using street 
     * name prefix.
     * One of two overloaded methods.
     * @param roadNamePrefix prefix of street name
     * @return Road object with street name prefix as VEJNAVN.
     */
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
    
    /**
     * SearchRoad is used for linear search through allRoads using
     * street name prefix and zip.
     * @param roadNamePrefix
     * @param zipCode
     * @return Road object with street name as VEJNAVN, and zip as
     * V_POSTNR
     */
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
