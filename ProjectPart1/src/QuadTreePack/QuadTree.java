package QuadTreePack;


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
    public Road[] roadList;
    public int currentRoads = -1;

    //test
    public QuadTree(NSEW direction) {
        boundary = new Boundary(direction, boundary);
        roadList = new Road[sizeLimit];
    }

    public void insert(Road rd) {
        if (checkBounds(rd)) {
            if (!(northeast == null)) {
                if (checkBounds(rd)) {
                    northeast.insert(rd);
                } else if (checkBounds(rd)) {
                    northwest.insert(rd);
                } else if (checkBounds(rd)) {
                    southeast.insert(rd);
                } else if (checkBounds(rd)) {
                    southwest.insert(rd);
                }
                
            } else if (currentRoads == sizeLimit - 1) {
                divide();
            } else if (currentRoads < sizeLimit) {
                roadList[++currentRoads] = rd;
            }

        }
    }

    private void divide() {
        northeast = new QuadTree(NSEW.NORTHEAST);
        northwest = new QuadTree(NSEW.NORTHWEST);
        southeast = new QuadTree(NSEW.SOUTHEAST);
        southwest = new QuadTree(NSEW.SOUTHWEST);

        for (Road rd : roadList) {
            insert(rd);
        }
    }
    
    private boolean checkBounds(Road rd){
        
        boundary.containsPoint(rd.getMidX(), rd.getMidY());
        return false;
        
    }

}
