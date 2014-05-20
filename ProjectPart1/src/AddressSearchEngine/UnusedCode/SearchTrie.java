/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AddressSearchEngine.UnusedCode;

import ctrl.StartMap;
import java.util.ArrayList;
import java.util.HashMap;
import model.Road;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchTrie
{
    private final ArrayList<Road> roadList = StartMap.allRoads;
    private final AlphabetParser alphabetParser = new AlphabetParser();
    private final HashMap<Character, Integer> charToIndex = alphabetParser.getHashMap();
    private final int alphabetSize = charToIndex.size();
    private Trie root;
    
    private SearchTrie()
    {
        makeSearchTrie();
    }
    
    private void makeSearchTrie()
    {
        ArrayList<Character> emptyAL = new ArrayList<>(0);
        root = new Trie(emptyAL, 0 , -1);
        int roadListIndex = 0;
        for(Road road : roadList)
        {
            if(road.getEd().VEJNAVN.length() > 0) //Ignore roads with no name
            {
                char[] roadNameArr = road.getEd().VEJNAVN.toLowerCase().toCharArray();
                ArrayList<Character> roadNameAL = new ArrayList<>(0);
                for(char c : roadNameArr)
                {
                    roadNameAL.add(c);
                }
                root.insert(roadNameAL, 0, roadListIndex++);
            }    
        }
    }
    
    private class Trie
    {
        char letter;
        ArrayList<Integer> indexArr;
        Trie[] trieArr;
        
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
