package AddressSearchEngine;

import java.util.HashMap;
import model.Road;

/**
 * AutoCompleter concatenate String address input from SearchField and links
 * SearchField to AddressParser, ZipToCityNameParser and RoadNameSearcher.
 * 
 * Contains method for type-ahead feature of input address String for Search-
 * as-you-type functionallity in SearchLabel. Contains method for road name
 * searching.
 * 
 * @author Gruppe A.
 */
public class AutoCompleter
{
    private static final RoadNameSearcher roadNameSearcher = 
            new RoadNameSearcher();
    private static final ZipToCityNameParser zipToCityNameParser = 
            ZipToCityNameParser.getInstance();
    private static final HashMap<Integer, String> zipToCityHM = 
            zipToCityNameParser.getZipToCityHashMap();
    
    /**
     * Takes an address input String and autocompletes it if posible.
     * @param userInput Address input from user in SearchField
     * @return Contatenated type-ahead String.
     */
    public String autoCompleteUserInput(String userInput)
    {
        System.out.println("User input" + userInput);
        String[] addressArray = AddressParser.parse(userInput);

        String autoCompletedAddress = "";

        Road found = null;

        if (addressArray[0] != null && addressArray[4] != null)
        {
            found = roadNameSearcher.searchRoad(addressArray[0], 
                    Integer.parseInt(addressArray[4]));
        } else if (found == null && addressArray[0] != null)
        {
            found = roadNameSearcher.searchRoad(addressArray[0]);
        }

        if (found != null)
        {
            autoCompletedAddress += found.getEd().VEJNAVN + " "; //Street name

            if (addressArray[1] != null)                     //Building number
            {
                autoCompletedAddress += addressArray[1];
            } 
            if (addressArray[2] != null)                     //Building letter
            {
                autoCompletedAddress += addressArray[2];
            } 
            if (addressArray[3] != null)                               //Floor
            {
                autoCompletedAddress += " " + addressArray[3] + ". sal";
            } 
            if (addressArray[4] != null)                                 //Zip
            {
                autoCompletedAddress += ", " + addressArray[4];
            }
            if (addressArray[5] != null)                                //City
            {
                autoCompletedAddress += " " + addressArray[5];
            }
            else if(addressArray[4] != null)          //City if Zip is entered
            {
                autoCompletedAddress += " " + 
                        zipToCityHM.get(Integer.parseInt(addressArray[4]));
            }
        }
        return autoCompletedAddress;
    }
    
    /**
     * Switch between Road object search, if streetname or Streetname and zip 
     * is typed in.
     * in input address.
     * @param userInput
     * @return Road object that was searched for or null if no such Road was 
     * found
     */
    public Road searchRoad(String userInput)
    {
        System.out.println("User input" + userInput);
        String[] addressArray = AddressParser.parse(userInput);

        Road found = null;

        if (addressArray[0] != null && addressArray[4] != null)
        {
            found = roadNameSearcher.searchRoad(addressArray[0], 
                    Integer.parseInt(addressArray[4]));
        } else if (found == null && addressArray[0] != null)
        {
            found = roadNameSearcher.searchRoad(addressArray[0]);
        }
        
        return found;
    }
}
