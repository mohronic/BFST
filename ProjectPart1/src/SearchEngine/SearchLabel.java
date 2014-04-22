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
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchLabel extends JTextField
{   
    public CurrentData currentData;
    public ArrayList<Road> roadList; //Soon to be sorted
    private String currentString;
    
    public SearchLabel(ArrayList<Road> roadList)
    {
        this.roadList = roadList;
        this.setText("Search here");
        this.selectAll();
        currentString = this.getText();
    }
    
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
    
    public String searchRoadName()
    {
        String temp = null;
        for(Road road: roadList)
        {
            //Overvej: 
            //contains(currentString);
            
            if(road.getEd().VEJNAVN.startsWith(currentString))
            {
                temp = road.getEd().VEJNAVN;
                break;
            }
        }
        return temp;
    }
    
    public void updateCurrentString()
    {
        if(!this.getText().equals(currentString))
        {
            currentString = this.getText();
        }
    }
    
    public String getCurrentText()
    {
        return currentString;
    }
}
