/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import ctrl.StartMap;
import java.io.IOException;
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
    //public HashMap<String, int> stringToInt = AlphabetParser.get;
    //public LinkedList<String , >
    public AlphabetParser alphabetParser = new AlphabetParser();
    public HashMap<Character, Integer> charToIndex = alphabetParser.getHashMap();
    private int alphabetSize = charToIndex.size();
    private Trie root;
    
    private SearchTrie()
    {
        //makeSearchTrie();
    }
    
    public Road searchRoad(String prefix)
    {
        Road temp = null;
        for (Road road : roadList)
        {
            if (road.getEd().VEJNAVN.startsWith(prefix))
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
        if(!StartMap.osm)
        {
            for (Road road : roadList)
            {
                if (road.getEd().VEJNAVN.startsWith(prefix) && road.getEd().V_POSTNR == zipCode)
                {
                    temp = road;
                    break;
                }
            }
        }
        else //OSM
        {
            temp = searchRoad(prefix);
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
    
    //    public ArrayList<Road> searchRoad(String prefix)
//    {
//        return null;
//    }
    
//    public ArrayList<Road> searchRoad(String prefix, int zipCode)
//    {
//        return null;
//    }
    
    
    private void makeSearchTrie()
    {
        ArrayList<Character> emptyAL = new ArrayList<>(0);
        root = new Trie(emptyAL, 0 , -1);
        int roadListIndex = 0;
        for(Road road : roadList)
        {
            if(road.getEd().VEJNAVN.length()>0) //Ignore roads with no name
            {
                char[] roadNameArr = road.getEd().VEJNAVN.toLowerCase().toCharArray();
                ArrayList<Character> roadNameAL = new ArrayList<>(0);
                for(char c : roadNameArr)
                {
                    roadNameAL.add(c);
                }
                root.insert(roadNameAL, 0, roadListIndex++);
            }    
            //Trie trie = new Trie(roadNameArr, roadNameAL, roadListIndex++);
            //root.insert(trie);
        }
    }
    
    private class Trie
    {
        char letter;
        ArrayList<Integer> indexArr;
        Trie[] trieArr;
        //Trie[] trieArr;
        
        public Trie(ArrayList<Character> roadNameAL, int roadNameCharIndex, int roadListIndex)
        {
            trieArr = new Trie[alphabetSize];
            if(roadNameAL.size() > roadNameCharIndex) //Not root
            {
                insert(roadNameAL , roadNameCharIndex, roadListIndex);
            }
            else //Root or end
            { 
                indexArr = new ArrayList<>(0);
                if(roadListIndex != -1)
                    indexArr.add(roadListIndex);
            }
        }
        
        public void insert(ArrayList<Character> roadNameAL, int roadNameCharIndex, int roadListIndex)
        {
            letter = roadNameAL.get(roadNameCharIndex);
            Trie nextTrie = trieArr[charToIndex.get(letter)];
            if(nextTrie != null) //Such Trie already exists
            {
                nextTrie.insert(roadNameAL, ++roadNameCharIndex, roadListIndex);
            }
            else //Such Trie does not exist
            {
                Trie trie = new Trie(roadNameAL, ++roadNameCharIndex, roadListIndex);
                trieArr[charToIndex.get(trie.letter)] = trie;
            }
        }
        
        
//        public void insert(Trie trie)
//        {
//            int indexOfChar = charToIndex.get(trie.letter);
//            if(trieArr[indexOfChar] != null)
//            {
//                trieArr[indexOfChar] = trie;
//            }
//            else
//            {
//                trieArr[indexOfChar].insert(trie);
//            }
//        }
        
        public ArrayList<Integer> getRoadIndexes()
        {
            return indexArr;
        }
        
        public Trie getSubTrie(char c)
        {
            return trieArr[charToIndex.get(c)];
        }
    }
}
