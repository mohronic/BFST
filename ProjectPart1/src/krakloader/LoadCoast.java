/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krakloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Archigo
 */
public class LoadCoast {
    
    public double xmax = 0, ymax = 0, xmin = 0, ymin = 0;

//    public abstract void processNode(NodeData nd);
//
//    public abstract void processEdge(EdgeData ed);
    
    
    public ArrayList<Road> load(String inputFile) throws IOException {
        
        ArrayList<Road> rlist = new ArrayList<>();
        
        BufferedReader br;
        br = new BufferedReader(new FileReader(inputFile));
        

        String line;
        String line2;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            line = split[0] + "," + split[1];
            line2 = split[2] + "," +split[3];
            
            NodeData nd = new NodeData(line, "overload");
            setMax(nd);
            NodeData nd2 = new NodeData(line2, "overload");
            setMax(nd2);
            
            EdgeData ed = new EdgeData();
            
            Road rd = new Road(ed, nd, nd2);
            
            rlist.add(rd);
            

            

            

        }
        br.close();

//        /* Edges. */
//        br = new BufferedReader(new FileReader(edgeFile));
//        br.readLine(); // Again, first line is column names, not data.
//
//        while ((line = br.readLine()) != null) {
//            processEdge(new EdgeData(line));
//
//        }
//        br.close();

        DataLine.resetInterner();
        System.gc();
        
        return rlist;
        
    }
    
    private void setMax(NodeData nd){
        if (nd.getX_COORD() > xmax) {
                xmax = nd.getX_COORD();
            }
            if (xmin == 0 || nd.getX_COORD() < xmin) {
                xmin = nd.getX_COORD();
            }
            if (nd.getY_COORD() > ymax) {
                ymax = nd.getY_COORD();
            }
            if (ymin == 0 || nd.getY_COORD() < ymin) {
                ymin = nd.getY_COORD();
            }
    }
    
}
