/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package osmparser;

/**
 *
 * @author z3ss
 */
public class Node {
    private long id;
    public Node(long id, double lat, double lon){
        this.id = id;
    }
    
    public long getID(){
        return id;
    }
}
