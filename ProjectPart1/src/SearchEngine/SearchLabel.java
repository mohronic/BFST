/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchEngine;

import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import model.CurrentData;
import model.Road;
import ctrl.StartMap;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//import java.util.Collections; Overvej at sorterer RoadList.

/**
 * Class SearchLabel, extends JTextField, used to search for Roads from a
 * string.
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchLabel extends JTextField implements FocusListener, DocumentListener
{
    public CurrentData currentData;
    private String currentString;
    private final String hint;
    private boolean showingHint;
    private final AutoCompleter autoCompleter = AutoCompleter.getInstance();
    public ArrayList<Road> roadList = StartMap.allRoads; //Soon to be sorted

    /**
     * Constructor for SearchLabel. Initilises variables in SearchLabel, sets
     * starttext and selects it.
     *
     * @param roadList Recieves a roadList for road name searching
     * @param hint The guiding text
     */
    public SearchLabel(ArrayList<Road> roadList, String hint)
    {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
        currentString = this.getText();
    }

    /**
     * Autocompletes searchTag if the full roadname is found in roadList
     */
    public void autoComplete()
    {
        updateCurrentString();
        System.out.println("After update: " + currentString);
        if(currentString.length() > 0)
        {
            String autoCompletedAddress = autoCompleter.autoCompleteUserInput(currentString);
            if(currentString.length() < autoCompletedAddress.length() && autoCompletedAddress.length() > 0)
            {
                setText(autoCompletedAddress);
                select(currentString.length(), autoCompletedAddress.length());
            }
        }
    }
    
    /**
     * Returns road if road with recieved string is in RoadList
     *
     * @param roadName String roadname
     * @return Boolean found
     */
    public Road checkRoadName(String roadName)
    {
        for (Road road : roadList)
        {
            //System.out.println(road.midX);
            if (roadName.equals(road.getEd().VEJNAVN))
            {
                return road;
            }
        }
        return null;
    }
    
    public Road checkRoadName(String roadName, int postalCode)
    {
        for (Road road : roadList)
        {
            //System.out.println(road.midX);
            if (roadName.equals(road.getEd().VEJNAVN) && postalCode == road.getEd().V_POSTNR)
            {
                return road;
            }
        }
        return null;
    }
        
//    public Road searchRoad(String s)
//    {
//        Road road = binaryRoadSearch(s);
//        return road;
//    }
//    
//    private Road binaryRoadSearch(String s)
//    {
//        int n = roadList.size();
//        int a = 0, b = n-1;
//        boolean found = false;
//        int i = 0;
//        
//        while(!found && a <= b)
//        {
//            i = (a+b) / 2;
//            String StreetName = roadList.get(i).getEd().VEJNAVN;
//            if(s.compareToIgnoreCase(StreetName) < 0) b = i-1;
//            else if(s.compareTo(StreetName) > 0) a=i+1;
//                found = true;
//        }
//        return roadList.get(i);
//    }

    /**
     * Updates field currentString
     */
    public void updateCurrentString()
    {
        currentString = super.getText();
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        if (this.getText().isEmpty())
        {
            this.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if (this.getText().isEmpty())
        {
            this.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText()
    {
        if(showingHint) return "";
        else return super.getText();
    }
    
    private DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                System.out.println("Inserted");
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                System.out.println("Removed");
            }
            
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                System.out.println("Changed");
            }
    };

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        System.out.println("Inserted");
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        System.out.println("Removed");
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        System.out.println("Changed");
    }
}
