/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuadTreePack;

import java.util.ArrayList;
import model.Road;

/**
 *
 * @author archigo
 */
public interface QuadTreeInterace {
    
     public void insert(Road rd);
     
     public ArrayList<Road> getRoads(double x1, double x2, double y1, double y2);
    
}
