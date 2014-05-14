/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import ctrl.StartMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import model.Road;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchTrie
{
    private static SearchTrie instance = null;
    public ArrayList<Road> roadList = StartMap.allRoads; //Soon to be sorted
    public HashMap<String, int> stringToInt = AlphabetParser.get;
    public LinkedList<String , >
    private int alphabetSize = 31;
    
    private SearchTrie()
    {
        makeSearchTrie();
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
    
    private void makeSearchTrie()
    {
        for(Road road : roadList)
        {
            
        }
    }
    
    
    }

    private class Trie
    {
        String letter;
        ArrayList<Integer> indexArr;
        Trie[] trieArr;
        
        public Trie()
        {
            trieArr = new Trie[alphabetSize];
        }
        
        public void insert(String roadName, int index)
        {
            
            for(Trie trie : trieArr)
            {
                
            }
        }
        
        
        
        public ArrayList<Road> getSubRoads()
        {
            
        }
        
        public void makeArrayList()
        {
            
        }
    }
}
