package AddressSearchEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import krakloader.DataLine;

/**
 * The class ZipToCityNameParser is used to parse bynavne.txt to a 
 * HashMap which maps zip to city name.
 * ZipToCityNameParser is a Singelton, meaning only one instantiation of this
 * class can exist. This is done so that bynavne.txt is not parsed more than
 * once.
 * @author Gruppe A
 */
public class ZipToCityNameParser
{
    private static ZipToCityNameParser instance = null;
    private final HashMap<Integer, String> zipToCity = new HashMap<>();
    
    /**
     * Private constructor is only called once to avoid parsing textfile
     * more than once.
     * To get an instance of this class. Call 
     * ZipToCityNameParser.getInstance().
     */
    private ZipToCityNameParser()
    {
        String dir = "./data/";
        ArrayList<String> input = readFile(dir+"bynavne.txt");
        makeZipToCityHashMap(input);
    }
    
    /**
     * Call this method to get an instance of ZipToCityNameParser.
     * @return ZipToCityNameParser instance
     */
    public static ZipToCityNameParser getInstance() {
        if (instance == null) {
            instance = new ZipToCityNameParser();
        }
        return instance;
    }
    
    /**
     * Reads from input text file, each line becomes a String,
     * returns ArrayLists of Strings.
     * Used to parse bynavn.txt to an ArrayList
     * @param fileName String directory to file
     * @return ArrayList of parsed Strings
     */
    private ArrayList<String> readFile(String fileName)
    {
        ArrayList<String> arr = new ArrayList<>();
        BufferedReader br;
        try
        {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "iso8859-1"));
            String line;
            while ((line = br.readLine()) != null)
            {
                arr.add(line);
            }
            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Problem in CityNameParser, what went wrong: " + e.getMessage());
        }
        DataLine.resetInterner();
        System.gc();
        
        return arr;
    }
    
    /**
     * Creates a HashMap with zip mapping to City name.
     * @param input ArrayList with Strings of data in format:
     * "zip  cityname" E.G: "5320  Agedrup" or.
     * "zipfrom-zipto  cityname" E.G: "1000-1499  København K"
     */
    private void makeZipToCityHashMap(ArrayList<String> input)
    {
        for(String s : input)
        {
            if(s.matches("^[0-9]{4}(?=[-]).+")) //Input matches "zipfrom-zipto  cityname"
            {
                String postalcode = s.substring(0,9);
                String[] split = postalcode.split("-");
                int postalcodeFrom = Integer.parseInt(split[0]);
                int postalcodeTo = Integer.parseInt(split[1]);
                
                String cityName = s.substring(11);
                
                while(postalcodeFrom <= postalcodeTo)
                {
                    zipToCity.put(postalcodeFrom++, cityName);
                }
            }
            
            else //Input matches "zip  cityname"
            {
                int postalcode = Integer.parseInt(s.substring(0,4));
                String cityName = s.substring(6);
                zipToCity.put(postalcode, cityName);
            }
        }
    }
    
    /**
     * Returns HashMap of Integer zip mapping to String city name.
     * @return HashMap<Integer, String> zip mapping to city name.
     */
    public HashMap<Integer, String> getZipToCityHashMap()
    {
        return zipToCity;
    }
}
