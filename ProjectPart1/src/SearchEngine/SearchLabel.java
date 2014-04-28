/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import java.util.ArrayList;
import javax.swing.JTextField;
import model.CurrentData;
import model.Road;
//import java.util.Collections; Overvej at sorterer RoadList.

/**
 * Class SearchLabel, extends JTextField, used to search for Roads from a 
 * string.
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchLabel extends JTextField
{   
    public CurrentData currentData;
    public ArrayList<Road> roadList; //Soon to be sorted
    private String currentString;
    
    /**
     * Constructor for SearchLabel.
     * Initilises variables in SearchLabel, sets starttext and selects it.
     * @param roadList Recieves a roadList for road name searching
     */
    public SearchLabel(ArrayList<Road> roadList)
    {
        this.roadList = roadList;
        this.setText("Search here");
        this.selectAll();
        currentString = this.getText();
    }
    
    /**
     * Returns true if road with recieved string is in RoadList
     * @param s String roadname
     * @return Boolean found
     */
    public boolean checkRoadName(String s)
    {
        boolean found = false;
        for(Road road: roadList)
        {
            //System.out.println(road.midX);
            if(!found && s.equals(road.getEd().VEJNAVN))
            {
                found = true;
                break;
            }
        }
        return found;
    }
    
    /**
     * Autocompletes searchTag if the full roadname is found in roadList
     */
    public void autoComplete()
    {
        String searchRoadName = searchRoadName();
        
        if(searchRoadName != null && searchRoadName.length() > currentString.length())
        {
            String autoCompleted = currentString + searchRoadName.substring(currentString.length());
            this.setText(autoCompleted);
            this.select(currentString.length(), autoCompleted.length());
        }
    }
    
    /**
     * Searches for road name in roadlist and returns full road name if found,
     * used in autocomplete method.
     * @return String - full road name if found
     */
    public String searchRoadName()
    {
        String temp = null;
        for(Road road: roadList)
        {
            //Overvej: 
            //contains(currentString);
            
            if(road.getEd().VEJNAVN.startsWith(currentString))
            {
                temp = road.getEd().VEJNAVN; //Skal returnere Road object i stedet
                break;
            }
        }
        return temp;
    }
    
    /**
     * Updates field currentString
     */
    public void updateCurrentString()
    {
        if(!this.getText().equals(currentString))
        {
            currentString = this.getText();
        }
    }
    
    /**
     * Returns what is written in searchLabel
     * @return String - currentString
     */
    public String getCurrentText()
    {
        return currentString;
    }
}
