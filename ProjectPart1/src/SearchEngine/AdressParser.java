/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author johaa
 */
public class AdressParser {
    
    public AdressParser() {   
    }
    
    public static void main(String[] args) {
        AdressParser parseaddress = new AdressParser();
        parseaddress.parse("Rued Langgaards Vej");
        parseaddress.parse("Rued Langgaards Vej 7, 5. sal, København S");
        parseaddress.parse("Rued Langgaards Vej 7 2300 København S");
        parseaddress.parse("Rued Langgaards Vej 7, 5.");
        parseaddress.parse("Rued Langgaards Vej 7A København S");
        parseaddress.parse("Rued Langgaards Vej i København");
    }
    
    public static String[] parse(String adr){
        String[] addressArray = new String[6];
        
        Pattern p;
        Matcher m;
        
        //Street Name
        p = Pattern.compile(".\\d+.");
        m = p.matcher(adr);        
        
        if(m.find())
        {
            String[] temp = adr.split("\\d+");
            String address = temp[0];
            
            System.out.println(address);
            if(address.charAt(address.length()-1) == ' ')
            {
                address = address.substring(0, address.length()-1);
            }
            addressArray[0] = address;
        } //else if(!m.find() && (adr.contains("i") || adr.contains("I"))){
          //  String[] temp = adr.split("i|I");
          //  addressArray[0] = temp[0];}
        else 
        {
            String[] temp = adr.split("\\d+");
            String address = temp[0];
            
            System.out.println(address);
            if(address.charAt(address.length()-1) == ' ')
            {
                address = address.substring(0, address.length()-1);
            }
            addressArray[0] = address;
        }
        
        //Building Number
        p = Pattern.compile("\\b\\d{1,3}[,]\\b|\\b\\d{1,3}[a-zA-Z]\\b|\\b\\d{1,3}\\b");
        m = p.matcher(adr);
        if(m.find())
        {
        addressArray[1] = m.group().replaceAll("[a-zA-Z]", "");
        }
        
        //Building Letter
        p = Pattern.compile("\\d+\\w");
        m = p.matcher(adr);
        if(m.find())
        {
        addressArray[2] = m.group().replaceAll("\\d+", "");
        }
        
        //Floor
        p = Pattern.compile("\\d+[.]");
        m = p.matcher(adr);
        if(m.find())
        {
        addressArray[3] = m.group().replaceAll("\\.", "");
        }
        
        //Postcode
        p = Pattern.compile("\\d{4}");
        m = p.matcher(adr);
        if(m.find()) 
        {
        addressArray[4] = m.group();
        }
        
        //City V2:
        p = Pattern.compile("\\s[A-Za-z]+");
        m = p.matcher(adr);
        
        if(m.find())
        {
            p = Pattern.compile("\\d{4}\\s[A-Za-z]+");
            m = p.matcher(adr);
            if (m.find())
            {
                String [] temp = adr.split("\\d{4}\\s");
                addressArray[5] = temp[temp.length-1];
            }
            else if(adr.contains(", "))
            {
                String[] temp = adr.split("[,]\\s");
                addressArray[5] = temp[temp.length-1];
            }
        }
        
//        //City 
//        p = Pattern.compile("\\d+");
//        m = p.matcher(adr); 
//        
//        if(m.find())
//        {
//            String[] temp = adr.split("\\d+");
//            
//            if(temp.length-1 > 0)
//            {
//                temp = temp[temp.length-1].split(" ");
//                if(temp.length > 2)
//                {
//                    addressArray[5] = temp[temp.length-2] + " " + temp[temp.length-1];
//                }
//                else{
//                    addressArray[5] = temp[temp.length-1];
//                }
//            }
//        } 
//        else if(!m.find() && (adr.contains("i") || adr.contains("I"))){
//            String[] temp = adr.split("i|I");
//
//            temp = temp[temp.length-1].split(" ");
//            
//            if(temp.length < 2);
//            if(temp.length < 3)
//            {
//                addressArray[5] = temp[1];
//            } else {
//            addressArray[5] = temp[temp.length-2] + " " + temp[temp.length-1];
//            }
//        }
               
        for(String qwe : addressArray)
        {
            System.out.print(qwe + "#");
        }
        System.out.println("\n");
        
        return addressArray;
    }
}
