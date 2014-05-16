/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Route;

import ctrl.StartMap;
import java.util.ArrayList;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class test
{

    /**
     *
     */
    public test()
    {
        EdgeData e00 = new EdgeData(0, 0, 99, 99, "");
        EdgeData e12 = new EdgeData(1, 2, 2, 2, "ft");
        EdgeData e13 = new EdgeData(1, 3, 1, 1, "ft");
        EdgeData e24 = new EdgeData(2, 4, 1, 2, "ft");
        EdgeData e45 = new EdgeData(4, 5, 2, 2, "");
        EdgeData e35 = new EdgeData(3, 5, 5, 2, "");
        EdgeData e56 = new EdgeData(5, 6, 1, 1, "tf"); //MÅSKE FT?
        EdgeData e57 = new EdgeData(5, 7, 2, 4, "");
        EdgeData e78 = new EdgeData(7, 8, 1, 1, "");
        EdgeData e59 = new EdgeData(5, 9, 2, 2, "");
        EdgeData e97 = new EdgeData(9, 7, 2, 1, "");

        NodeData n0 = new NodeData(0, 0, 0);
        NodeData n1 = new NodeData(1, 2, 2);
        NodeData n2 = new NodeData(2, 5, 2);
        NodeData n3 = new NodeData(3, 3, 4);
        NodeData n4 = new NodeData(4, 7, 4);
        NodeData n5 = new NodeData(5, 6, 6);
        NodeData n6 = new NodeData(6, 9, 6);
        NodeData n7 = new NodeData(7, 5, 8);
        NodeData n8 = new NodeData(8, 3, 8);
        NodeData n9 = new NodeData(9, 8, 8);

        Road r00 = new Road(e00, n0, n0);
        Road r12 = new Road(e12, n1, n2);
        Road r13 = new Road(e13, n1, n3);
        Road r24 = new Road(e24, n2, n4);
        Road r45 = new Road(e45, n4, n5);
        Road r53 = new Road(e35, n5, n3); // Reverse
        Road r56 = new Road(e56, n5, n6);
        Road r57 = new Road(e57, n5, n7);
        Road r78 = new Road(e78, n7, n8);
        Road r95 = new Road(e59, n9, n5); // Reverse
        Road r97 = new Road(e97, n9, n7);

        ArrayList<Road> roads = new ArrayList<>();

        if (true) //Data set A & B
        {
            roads.add(r00);
            roads.add(r12);
            roads.add(r13);
            roads.add(r24);
            roads.add(r45);

        }
        
        
        if (false)
        {
            roads.add(r12);
            roads.add(r13);
            roads.add(r24);
            roads.add(r45);
            roads.add(r53);
            roads.add(r56);
            roads.add(r57);
            roads.add(r78);
            roads.add(r95);
            roads.add(r97);

        }

        StartMap.adj.clear();

        for (Road r : roads)
        {
            DijkstraSP.addEdgeToAdj(r);
        }

        DijkstraSP SP = new ShortestRoad(roads);
        //DijkstraSP FP = new FastestRoad(roads);

        ArrayList<Linked> LSP = SP.mapRoute(r12, r45);
        //ArrayList<Linked> LFP = FP.mapRoute(r12, r97);

        for (Linked l : LSP)
        {
            System.out.println("Drivetime: " + l.getDrivetime() + " Length: " + l.getLength() + " From: " + l.getFrom() + " turn: " + l.getTurn());
        }

//        for (Linked l : LFP)
//        {
//            System.out.println("Drivetime: " + l.getDrivetime() + " Length: " + l.getLength() + " From: " + l.getFrom() + " turn: " + l.getTurn());
//        }
    }

}
