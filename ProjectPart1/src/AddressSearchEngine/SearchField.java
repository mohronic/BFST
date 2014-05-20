/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressSearchEngine;

import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import model.Road;
import ctrl.StartMap;
import java.awt.event.FocusEvent;
import view.Canvas;

/**
 * Class SearchField, extends JTextField, used to search for Roads from a
 string.
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class SearchField extends JTextField implements FocusListener
{
    public String currentString;
    private final String hint;
    private boolean showingHint;
    private final AutoCompleter autoCompleter = new AutoCompleter();
    public ArrayList<Road> roadList = StartMap.allRoads; //Soon to be sorted
    public int oldtext = 0;

    /**
     * Constructor for SearchLabel. Initilises variables in SearchLabel, sets
     * starttext and selects it.
     *
     * @param roadList Recieves a roadList for road name searching
     * @param hint The guiding text
     */
    public SearchField(ArrayList<Road> roadList, String hint)
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
        oldtext = getText().length();
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
     * @param searchQuery
     * @return Boolean found
     */
    public Road searchRoad(String searchQuery)
    {
        Road found = autoCompleter.searchRoad(searchQuery);
        return found;
    }
    
    /**
     * Updates field currentString
     */
    public void updateCurrentString()
    {
        currentString = super.getText();
    }

        @Override
    public String getText()
    {
        if(showingHint) return "";
        else return super.getText();
    }   
    
    @Override
    public void focusGained(FocusEvent e)
    {
        if (this.getText().isEmpty())
        {
            this.setText("");
            showingHint = false;
        }
        Canvas.getInstance(null).setFocus(false);
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if (this.getText().isEmpty())
        {
            this.setText(hint);
            showingHint = true;
        }
        Canvas.getInstance(null).setFocus(true);     
    }
}
