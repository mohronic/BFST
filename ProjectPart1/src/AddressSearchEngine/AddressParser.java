/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressSearchEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AddressParser parses an address input string into an address array using
 * regular expressions.
 * 
 * @author pvcl@itu.dk
 */
public class AddressParser {
    private static Pattern p;
    private static Matcher m;
    /**
     * Parses input String address, returns address array.
     * @param adr input adress String.
     * @return String[] address array which contains:
     * [Street name, Building number, Building letter, Floor, PostCode, City].
     * Arrayspots = null if no such String was reconized by regex.
     */
    public static String[] parse(String adr)
    {
        String streetName = parseStreetName(adr);
        String buildingNumber = parseBuildingNumber(adr);
        String buildingLetter = parseBuildingLetter(adr);
        String floor = parseFloor(adr);
        String zip = parseZip(adr);
        String city = parseCity(adr);
        
        String[] addressArray = {streetName, buildingNumber, buildingLetter, 
            floor, zip, city};

        for(String qwe : addressArray)
        {
            System.out.print(qwe + "#");
        }
        System.out.println("\n");
        
        return addressArray;
    }
    
    /**
     * Parses the streetname from input address.
     * @param adr full address String
     * @return String containing only streetname and null if not reconized.
     */
    private static String parseStreetName(String adr)
    {
        String streetName = null;
        p = Pattern.compile(".\\d+.");
        m = p.matcher(adr);        
        
        if(m.find())                                                       //1
        {
            String[] temp = adr.split("\\d+");
            String address = temp[0];
            
            if(address.charAt(address.length()-1) == ' ')                  //2
                address = address.substring(0, address.length()-1);
            if(address.charAt(address.length()-1) == ',')
                address = address.substring(0, address.length()-1);        //12
            
            streetName = address;
        }
        else                                                               
        {
            String[] temp = adr.split("\\d+");
            String address = temp[0];
            
            if(address.charAt(address.length()-1) == ' ')                   //3
            {
                address = address.substring(0, address.length()-1);
            }
            if(address.matches(".*[,].*"))                                 //13  
            {
                String[] temp2 = adr.split(",");
                address = temp2[0];
            }
            streetName = address;
        }
        return streetName;
    }
    
    /**
     * Parses the building number from input address.
     * @param adr full address String
     * @return String containing only buildingnumber and null if not reconized.
     */
    private static String parseBuildingNumber(String adr)
    {
        String number = null;
        p = Pattern.compile("\\b\\d{1,3}[,]\\b|\\b\\d{1,3}"
                + "[a-zA-Z]\\b|\\b\\d{1,3}\\b");
        m = p.matcher(adr);
        if(m.find())                                                       //4
        {
            number = m.group().replaceAll("[a-zA-ZæøåÆØÅéÉäÄöÖ]", "");
        }
        return number;
    }
    
    /**
     * Parses the building letter from input address.
     * @param adr full address String
     * @return String containing only building letter and null if not 
     * reconized.
     */
    private static String parseBuildingLetter(String adr)
    {
        String buildingLetter = null;
        p = Pattern.compile("\\d+\\w");
        m = p.matcher(adr);
        if(m.find())                                                       //5
        {
            buildingLetter = m.group().replaceAll("\\d+", "");
        }
        return buildingLetter;
    }
    
    /**
     * Parses the floor number from input address.
     * @param adr full address String
     * @return String containing only floor number and null if not reconized.
     */
    public static String parseFloor(String adr)
    {
        String floor = null;
        p = Pattern.compile("\\d+[.]");
        m = p.matcher(adr);
        if(m.find())                                                       //6
        {
            floor = m.group().replaceAll("\\.", "");
        }
        return floor;
    }
    
    /**
     * Parses the zip from input address. (NB: Only 4-digit zip is supporten)
     * @param adr full address String
     * @return String containing only zip and null if not reconized.
     */
    public static String parseZip(String adr)
    {
        String zip = null;
        p = Pattern.compile("\\d{4}");
        m = p.matcher(adr);
        if(m.find())                                                       //7
        {
            zip = m.group();
        }
        return zip;
    }
    
    /**
     * Parses the city from input address.
     * @param adr full address String
     * @return String containing only city and null if not reconized.
     */
    public static String parseCity(String adr)
    {
        String city = null;
        p = Pattern.compile("\\s[A-Za-zæøåÆØÅéÉäÄöÖ]+");
        m = p.matcher(adr);
        
        if(m.find())                                                       //8
        {
            p = Pattern.compile("\\d{4}\\s[A-Za-zæøåÆØÅéÉäÄöÖ]+");
            m = p.matcher(adr);
            if (m.find())                                                  //9
            {
                String [] temp = adr.split("\\d{4}\\s");
                city = temp[temp.length-1];
            }
            else if(adr.contains(", "))                                    //10
            {
                String[] temp = adr.split("[,]\\s");
                if(!temp[temp.length-1].matches(".*\\d.*"))                //11
                {
                    city = temp[temp.length-1];
                }
            }
        }
        return city;
    }
}