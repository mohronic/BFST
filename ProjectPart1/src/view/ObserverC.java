/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

/**
 * Simple replacement for java.util.Observer which contained confusing
 * functionality.
 * @author gruppe A
 */
public interface ObserverC {
    
    /**
     * Method that defines what should happen when an observed class changes.
     */
    public void update();
    
}
