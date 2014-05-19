/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import java.util.HashMap;
import model.Road;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class AutoCompleter
{
    private static final HashMap<Integer, String> zipToCityHM = CityNameParser.getZipToCityHashMap();
    private static AutoCompleter instance = null;
    private static final SearchTrie searchTrie = SearchTrie.getInstance();
    
    private AutoCompleter()
    {   
        String address = "Rued Langgaards Vej,"; //Test, adresse skal gives
    
        String [] addressArray = AddressParser.parse(address);
        
        for(String s : addressArray) System.out.print(s + " | "); //Test
        System.out.println("");
        
        if(addressArray[4] != null)
        {
            int postnummer = Integer.parseInt(addressArray[4]);
            System.out.println(postnummer);
        }
    }
    
    public String autoCompleteUserInput(String userInput)
    {
        System.out.println("User input" + userInput);
        String[] addressArray = AddressParser.parse(userInput);

        String autoCompletedAddress = "";

        Road found = null;

        if (addressArray[0] != null && addressArray[4] != null)
        {
            found = searchTrie.searchRoad(addressArray[0], Integer.parseInt(addressArray[4]));
        } else if (found == null && addressArray[0] != null)
        {
            found = searchTrie.searchRoad(addressArray[0]);
        }

        if (found != null)
        {
            autoCompletedAddress += found.getEd().VEJNAVN + " ";    //Street name

            if (addressArray[1] != null)                            //Building number
            {
                autoCompletedAddress += addressArray[1] + " ";
            } 
            else if (addressArray[2] != null)                     //Building letter
            {
                autoCompletedAddress += addressArray[2] + ", ";
            } 
            else if (addressArray[3] != null)                     //Floor
            {
                autoCompletedAddress += addressArray[3] + ". Sal ";
            } 
            else if (addressArray[4] != null)                     //Zip
            {
                autoCompletedAddress += addressArray[4] + " ";
            } //String city = zipToCityHM.get(addressArray[4]);
            //if (city != null) autoCompletedAddress += city;
            else if (addressArray[5] != null)                       //City
            {
                autoCompletedAddress += addressArray[5];
            }
        }
        return autoCompletedAddress;
    }
    
    public Road searchRoad(String userInput)
    {
        System.out.println("User input" + userInput);
        String[] addressArray = AddressParser.parse(userInput);

        Road found = null;

        if (addressArray[0] != null && addressArray[4] != null)
        {
            found = searchTrie.searchRoad(addressArray[0], Integer.parseInt(addressArray[4]));
        } else if (found == null && addressArray[0] != null)
        {
            found = searchTrie.searchRoad(addressArray[0]);
        }
        
        return found;
    }
    
    public static AutoCompleter getInstance() {
        if (instance == null) {
            instance = new AutoCompleter();
        }
        return instance;
    }
    
    //Kald addressParser
    //Kald zip to city hvis adress har et Postnummer
    //Kald SearchTrie for at returnerer Road.
    //Returnér road
}
