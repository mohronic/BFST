package AddressSearchEngine.UnusedCode;

import AddressSearchEngine.ZipToCityNameParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import krakloader.DataLine;

/**
 * Alphabet parser is used to parse alphabet.txt into HashMap of Character to
 * Integer values. E.G. a maps to 0, b maps to 1 and so on.
 * The class was supposed to be used in SearchTrie, but is now never
 * instatiated.
 * @author Gruppe A
 */
public class AlphabetParser
{
    static HashMap<Character, Integer> letterToIndex = new HashMap<>();
    private static AlphabetParser instance = null;    
    
    /**
     * Private constructor is only called once to avoid parsing textfile
     * more than once.
     * To get an instance of this class. Call 
     * AlphabetParser.getInstance().
     */
    private AlphabetParser()
    {
        String dir = "./data/";
        ArrayList<Character> input = readFile(dir+"alphabet.txt");
        makeLetterToIndexHashMap(input);
    }
    
    /**
     * Call this method to get an instance of AlphabetParser.
     * @return AlphabetParser instance
     */
    public static AlphabetParser getInstance() {
        if (instance == null) {
            instance = new AlphabetParser();
        }
        return instance;
    }
    
    private ArrayList<Character> readFile(String fileName) 
    {
        ArrayList<Character> arr = new ArrayList<>();
        BufferedReader br;
        try
        {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "iso8859-1"));
            String line;
            while ((line = br.readLine()) != null)
            {
                arr.add(line.charAt(0));
            }
            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Problem in AlphabetParser, what went wrong: " + e.getMessage());
        }
        DataLine.resetInterner();
        System.gc();
        
        return arr;
    }
    
    private void makeLetterToIndexHashMap(ArrayList<Character> input)
    {
        int i = 0;
        for(Character c : input)
        {
            letterToIndex.put(c , i++);
        }
        System.out.println("letterToIndex: " + letterToIndex.toString());
    }
    
    public HashMap <Character, Integer> getHashMap()
    {
        return letterToIndex;
    }
}