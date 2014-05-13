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
    private static SearchTrie searchTrie = SearchTrie.getInstance();
    
    public static void main(String[] args)
    {
        AutoCompleter ac = AutoCompleter.getInstance();
    }
    
    private AutoCompleter()
    {   
        String address = "Nørregårdsvej 134, 2610 Rødovre"; //Test, adresse skal gives
    
        String [] addressArray = AdressParser.parse(address);
        
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
        String[] addressArray = AdressParser.parse(userInput);

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
       

        
        
//       Road roadWithFullName = searchFullRoadNameWithPrefix(userInput);
//        
//        if(roadWithFullName != null && roadWithFullName.getEd().VEJNAVN.length() > userInput.length())
//        {
//            String roadNameSuffix = roadWithFullName.getEd().VEJNAVN.substring(userInput.length());
//            String postalCode = Integer.toString(roadWithFullName.getEd().V_POSTNR);
//            String cityName = zipToCityHM.get(roadWithFullName.getEd().V_POSTNR);
//            
//            String autoCompletedRoadName = userInput + roadNameSuffix + ", " + postalCode + " " + cityName;
//            
//            return autoCompletedRoadName;
//        }
    
    
//    /**
//     * Searches for road name in roadlist and returns full road name if found,
//     * used in autocomplete method.
//     *
//     * @return String - full road name if found
//     */
//    private String searchForFullRoadName()
//    {
//        String temp = null;
//        for (Road road : roadList)
//        {
//            //Overvej: 
//            //contains(currentString);
//
//            if (road.getEd().VEJNAVN.startsWith(currentString))
//            {
//                temp = road.getEd().VEJNAVN; //Skal returnere Road object i stedet
//                break;
//            }
//        }
//        return temp;
//    }
    
//    //RETURNS ROAD!!!!
//    private Road searchFullRoadNameWithPrefix(String prefix)
//    {
//        Road temp = null;
//        for (Road road : roadList)
//        {
//            if (road.getEd().VEJNAVN.startsWith(prefix)) //Overvej contains(prefix);
//            {
//                temp = road;
//                break;
//            }
//        }
//        return temp;
//    }
    
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
