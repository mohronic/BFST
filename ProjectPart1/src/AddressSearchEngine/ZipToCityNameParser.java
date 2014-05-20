package AddressSearchEngine;

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
public class ZipToCityNameParser
{
    private static ZipToCityNameParser instance = null;
    private final HashMap<Integer, String> zipToCity = new HashMap<>();
    
    private ZipToCityNameParser()
    {
        String dir = "./data/";
        ArrayList<String> input = readFile(dir+"bynavne.txt");
        makeZipToCityHashMap(input);
    }
    
    public static ZipToCityNameParser getInstance() {
        if (instance == null) {
            instance = new ZipToCityNameParser();
        }
        return instance;
    }
    
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
    
    private void makeZipToCityHashMap(ArrayList<String> input)
    {
        for(String s : input)
        {
            if(s.matches("^[0-9]{4}(?=[-]).+"))
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
            
            else
            {
                int postalcode = Integer.parseInt(s.substring(0,4));
                String cityName = s.substring(6);
                zipToCity.put(postalcode, cityName);
            }
        }
    }
    
    public HashMap<Integer, String> getZipToCityHashMap()
    {
        return zipToCity;
    }
}
