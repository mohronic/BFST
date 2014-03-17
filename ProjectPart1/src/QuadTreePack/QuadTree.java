package QuadTreePack;

import krakloader.EdgeData;
import krakloader.NodeData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Archigo
 */
public class QuadTree {

    public QuadTree northeast, northwest, southeast, southwest;
    public Boundary boundary;
    private final int sizeLimit = 100;
    public EdgeData[] edgeList;
    public int currentEdges = -1;

    //test
    public QuadTree(NSEW direction) {
        boundary = new Boundary(direction, boundary);
        edgeList = new EdgeData[sizeLimit];
    }

    public void insert(EdgeData ed) {
        if (checkBounds(ed)) {
            if (!(northeast == null)) {
                if (checkBounds(ed)) {
                    northeast.insert(ed);
                } else if (checkBounds(ed)) {
                    northwest.insert(ed);
                } else if (checkBounds(ed)) {
                    southeast.insert(ed);
                } else if (checkBounds(ed)) {
                    southwest.insert(ed);
                }
                MapPack.Main.nodes.get(ed.FNODE - 1);
            } else if (currentEdges == sizeLimit - 1) {
                divide();
            } else if (currentEdges < sizeLimit) {
                edgeList[currentEdges + 1] = ed;
                currentEdges++;
            }

        }
    }

    private void divide() {
        northeast = new QuadTree(NSEW.NORTHEAST);
        northwest = new QuadTree(NSEW.NORTHWEST);
        southeast = new QuadTree(NSEW.SOUTHEAST);
        southwest = new QuadTree(NSEW.SOUTHWEST);

        for (EdgeData ed : edgeList) {
            insert(ed);
        }
    }
    
    private boolean checkBounds(EdgeData ed){
        
        //todo
        return false;
        
    }

}
