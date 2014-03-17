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
    public NodeData[] nodeList;
    public int currentNodes = -1;


    public QuadTree(NSEW direction) {
        boundary = new Boundary(direction, boundary);
        nodeList = new NodeData[sizeLimit];
    }

    public void insert(NodeData nd) {
        if (boundary.containsPoint(nd.X_COORD, nd.Y_COORD)) {
            if (!(northeast == null)) {
                if (northeast.boundary.containsPoint(nd.X_COORD, nd.Y_COORD)) {
                    northeast.insert(nd);
                } else if (northwest.boundary.containsPoint(nd.X_COORD, nd.Y_COORD)) {
                    northwest.insert(nd);
                } else if (southeast.boundary.containsPoint(nd.X_COORD, nd.Y_COORD)) {
                    southeast.insert(nd);
                } else if (southwest.boundary.containsPoint(nd.X_COORD, nd.Y_COORD)) {
                    southwest.insert(nd);
                }
            } else if (currentNodes == sizeLimit - 1) {
                divide();
            } else if (currentNodes < sizeLimit) {
                nodeList[currentNodes + 1] = nd;
                currentNodes++;
            }

        }
    }

    private void divide() {
        northeast = new QuadTree(NSEW.NORTHEAST);
        northwest = new QuadTree(NSEW.NORTHWEST);
        southeast = new QuadTree(NSEW.SOUTHEAST);
        southwest = new QuadTree(NSEW.SOUTHWEST);
        
        for(NodeData nd: nodeList){
            insert(nd);
        }
    }

}
