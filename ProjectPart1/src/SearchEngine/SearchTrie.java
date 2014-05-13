/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import ctrl.StartMap;
import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchTrie
{
    private static SearchTrie instance = null;
    public ArrayList<Road> roadList = StartMap.allRoads; //Soon to be sorted
    
    private SearchTrie()
    {
        
    }
    
    public Road searchRoad(String prefix)
    {
        Road temp = null;
        for (Road road : roadList)
        {
            if (road.getEd().VEJNAVN.startsWith(prefix)) //Overvej contains(prefix);
            {
                temp = road;
                break;
            }
        }
        return temp;
    }
    
    public Road searchRoad(String prefix, int zipCode)
    {
        Road temp = null;
        for (Road road : roadList)
        {
            if (road.getEd().VEJNAVN.startsWith(prefix) && road.getEd().V_POSTNR == zipCode) //Overvej contains(prefix);
            {
                temp = road;
                break;
            }
        }
        return temp;
    }
    
    public static SearchTrie getInstance()
    {
        if (instance == null) {
            instance = new SearchTrie();
        }
        return instance;
    }
}
