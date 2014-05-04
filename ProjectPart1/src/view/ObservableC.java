/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;

/**
 * Simple observable superclass without confusing functionality, for use instead
 * of the standard java.utill library
 * @author Archigo
 */
public class ObservableC {

    private ArrayList<ObserverC> observers = new ArrayList<>();
    private boolean changed = false;
/**
 * Changes the "changed" boolean to false to allow, "notifyObservers" to be run.
 * Serves as a safety to make sure the is something to notify about.
 */
    public void setChanged() {
        changed = true;
    }

    /**
     * Calls "update" on all added observers. Will only work if "setChanged"
     * has been called first.
     */
    public void notifyObservers() {
        if (changed) {
            for (ObserverC observer : observers) {
                observer.update();
            }
            changed = false;
        }

    }
    
    /**
     * adds an observer to this observable. Observer must implement "ObserverC"
     * from the "view" package.
     * @param oc 
     */
    public void addObserver(ObserverC oc){
        observers.add(oc);
    }

}
