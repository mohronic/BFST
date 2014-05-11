/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package osmparser;

import java.util.ArrayList;
/**
 *
 * @author z3ss
 */
public class Way {
    private long id;
    private ArrayList<Long> nodes = new ArrayList<>();
    private String name = "";
    private int typ = 0;
    private int speed = 0;
    private int postal = 0;
    
    public Way(long id) {
        this.id = id;
    }
    
    public void addNode(long nID){
        nodes.add(nID);
    } 
    
    public long getID(){
        return id;
    }

    public ArrayList<Long> getNodes() {
        return nodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    } 

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPostal() {
        return postal;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }
}
