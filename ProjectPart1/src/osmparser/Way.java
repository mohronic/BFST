package osmparser;

import java.util.ArrayList;

/**
 * This class represent a way structure from OpenStreetMap data
 * @author Group A
 */
public class Way {
    private ArrayList<Integer> nodes = new ArrayList<>();
    private String name = "";
    private int typ = 0;
    private int speed = 0;
    private int postal = 0;
    
    /**
     * Constructor for the way
     */
    public Way() {
    }
    
    /**
     * Add a reference to a node
     * @param nID 
     */
    public void addNode(int nID){
        nodes.add(nID);
    } 
    
    /**
     * Return an ArrayList with all the node references
     * @return 
     */
    public ArrayList<Integer> getNodes() {
        return nodes;
    }
    
    /**
     * Sets the name of the way.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the way
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the type of the way
     * @return 
     */
    public int getTyp() {
        return typ;
    }
    
    /**
     * Sets the type of the way
     * @param typ 
     */
    public void setTyp(int typ) {
        this.typ = typ;
    } 
    
    /**
     * Returns the speed limit of the way
     * @return 
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Sets the speed limit for the way
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Returns the postal code for the way
     * @return 
     */
    public int getPostal() {
        return postal;
    }
    
    /**
     * Sets the postal code for the way
     * @param postal 
     */
    public void setPostal(int postal) {
        this.postal = postal;
    }
}
