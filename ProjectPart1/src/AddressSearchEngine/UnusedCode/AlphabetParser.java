/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AddressSearchEngine.UnusedCode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import krakloader.DataLine;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class AlphabetParser
{
    static HashMap<Character, Integer> letterToIndex = new HashMap<>();
    
    public AlphabetParser()
    {
        String dir = "./data/";
        ArrayList<Character> input = readFile(dir+"alphabet.txt");
        makeLetterToIndexHashMap(input);
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