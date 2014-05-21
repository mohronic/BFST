package AddressSearchEngine.UnusedCode;

import ctrl.StartMap;
import java.util.ArrayList;
import java.util.HashMap;
import model.Road;

/**
 * Class SearchTrie is a non-finished class of the known datastructure of a Trie.
 * It was supposed to map VEJNAVN to an arrayList of indexvalues for
 * Road objects with the searched VEJNAVN.
 * The class is never instatiated.
 * The class is a singelston, to only get instantiated once (but never is).
 * 
 * We have choosen to include this class in our project because it is almost 
 * done, and we want to talk about it for the exams if the oppotunity
 * strikes.
 * @author Gruppe A
 */
public class SearchTrie
{
    private final ArrayList<Road> roadList = StartMap.allRoads;
    private final AlphabetParser alphabetParser = AlphabetParser.getInstance();
    private final HashMap<Character, Integer> charToIndex = alphabetParser.getHashMap();
    private final int alphabetSize = charToIndex.size();
    private Node root;
    private static SearchTrie instance = null;
    
    /**
     * Constructor is only called once to make structure of Trie.
     */
    private SearchTrie()
    {
        makeSearchTrie();
    }
    
    /**
     * Call this method to get an instance of SearchTrie.
     * @return SearchTrie instance
     */
    public static SearchTrie getInstance() {
        if (instance == null) {
            instance = new SearchTrie();
        }
        return instance;
    }
    
    /**
     * Builds searchTrie
     */
    private void makeSearchTrie()
    {
        ArrayList<Character> emptyAL = new ArrayList<>(0);
        root = new Node(emptyAL, 0 , -1);
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
    
    /**
     * Innerclass of Nodes in Trie.
     */
    private class Node
    {
        char letter;
        ArrayList<Integer> indexArr;
        Node[] nextNodeArr;
        
        /**
         * Constructor for a new Node in Trie.
         * @param roadNameAL ArrayList of the full Street name with each Character on a new index
         * @param roadNameCharIndex int value that maps to place in roadNameAL how far it has ben parsed
         * @param roadListIndex int value for allList Road index, of inserted Road object.
         */
        public Node(ArrayList<Character> roadNameAL, int roadNameCharIndex, int roadListIndex)
        {
            nextNodeArr = new Node[alphabetSize];
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
        
        /**
         * Inserts a new Road into Trie.
         * @param roadNameAL ArrayList of the full Street name with each Character on a new index
         * @param roadNameCharIndex int value that maps to place in roadNameAL how far it has ben parsed
         * @param roadListIndex int value for allList Road index, of inserted Road object.
         */
        public void insert(ArrayList<Character> roadNameAL, int roadNameCharIndex, int roadListIndex)
        {
            letter = roadNameAL.get(roadNameCharIndex);
            Node nextTrie = nextNodeArr[charToIndex.get(letter)];
            if(nextTrie != null) //Such Trie already exists
            {
                nextTrie.insert(roadNameAL, ++roadNameCharIndex, roadListIndex);
            }
            else //Such Trie does not exist
                                                {
                Node trie = new Node(roadNameAL, ++roadNameCharIndex, roadListIndex);
                nextNodeArr[charToIndex.get(trie.letter)] = trie;
            }
        }
        
        /**
         * Returns Nodes RoadIndexes.
         * @return ArrayList of Integers mapping to indexes in allRoads.
         */
        public ArrayList<Integer> getRoadIndexes()
        {
            return indexArr;
        }
        
        /**
         * Is called to move to Node with Character letter.
         * @param c SubNode with this char
         * @return Node subNode.
         */
        public Node getSubTrie(char c)
        {
            return nextNodeArr[charToIndex.get(c)];
        }
    }
}
