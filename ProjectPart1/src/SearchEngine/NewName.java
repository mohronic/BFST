/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class NewName
{
    public int findPostalNumber(String s)
    {        
        for(int i = 0 ; i < s.length() - 5 ; i++)
        {
            String temp = s.substring(i, i+4);
            if(temp.matches("[0-9]{4}"))
            {
                return Integer.parseInt(temp);
            }
        }
        return -1;
    }
    
//    public static void main(String[] args)
//    {
//        NewName ap = new NewName();
//        System.out.println("Postal code: " + ap.findPostalNumber("Nørregårdsvej 134, 2610 Rødovre"));
//    }
    
    public String findAddress(String s)
    {
        if(s.contains(","))
        {
            String[] addressPlace = s.split(",");
            String temp = addressPlace[0];
        }
        return null;
    }
    
//    public boolean isAValidAdress(String s)
//    {
//        boolean valid = false;
//        if(s.matches("([A-ZÉÆØÅÄÖa-zéæøåäö]+|\\s)*([]{0,3})(,.)\\s(\\w)*\\d{0,5}\\s*([A-ZÉÆØÅÄÖa-zéæøåäö]+|\\s)*"))
//            valid = true;
//        return valid;
//    }
    
}
