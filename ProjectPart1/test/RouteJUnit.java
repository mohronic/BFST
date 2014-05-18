/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Route.DijkstraSP;
import Route.FastestRoad;
import Route.Linked;
import Route.ShortestRoad;
import ctrl.StartMap;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.Road;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class RouteJUnit
{

    private ArrayList<Road> roads = new ArrayList<>();
    private Road start;
    private Road end;

    public RouteJUnit() throws IOException, SAXException, ParserConfigurationException
    {
        StartMap SM = new StartMap(false);
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        EdgeData e00 = new EdgeData(0, 0, 99, 99, "");
        EdgeData e12 = new EdgeData(1, 2, 2, 2, "");
        EdgeData e24 = new EdgeData(2, 4, 2, 2, "");
        EdgeData e45 = new EdgeData(4, 5, 2, 2, "");
        EdgeData e28 = new EdgeData(2, 8, 2, 1, "");
        EdgeData e58 = new EdgeData(5, 8, 2, 3, "");
        EdgeData e26 = new EdgeData(2, 6, 1, 1, "tf");
        EdgeData e13 = new EdgeData(1, 3, 4, 1, "");
        EdgeData e37 = new EdgeData(3, 7, 1, 1, "ft");
        EdgeData e35 = new EdgeData(3, 5, 3, 2, "");

        EdgeData e59 = new EdgeData(5, 9, 1, 1, "");

        NodeData n0 = new NodeData(0, 0, 0);
        NodeData n1 = new NodeData(1, 2, 2);
        NodeData n2 = new NodeData(2, 5, 2);
        NodeData n3 = new NodeData(3, 3, 4);
        NodeData n4 = new NodeData(4, 13, 3);
        NodeData n5 = new NodeData(5, 6, 6);
        NodeData n6 = new NodeData(6, 7, 1);
        NodeData n7 = new NodeData(7, 2, 5);
        NodeData n8 = new NodeData(8, 6, 4);

        NodeData n9 = new NodeData(9, 6, 7);

        Road r00 = new Road(e00, n0, n0);
        Road r12 = new Road(e12, n1, n2);
        Road r26 = new Road(e26, n2, n6);
        Road r24 = new Road(e24, n2, n4);
        Road r45 = new Road(e45, n4, n5);
        Road r28 = new Road(e28, n2, n8);
        Road r58 = new Road(e58, n5, n8);
        Road r13 = new Road(e13, n1, n3);
        Road r35 = new Road(e35, n3, n5);
        Road r37 = new Road(e37, n3, n7);

        Road r59 = new Road(e59, n5, n9);

        roads.add(r00);
        roads.add(r12);
        roads.add(r26);
        roads.add(r24);
        roads.add(r45);
        roads.add(r28);
        roads.add(r58);
        roads.add(r13);
        roads.add(r35);
        roads.add(r37);

        roads.add(r59);

        start = r12; // doesnt matter, it just uses the from point
        end = r59;

        StartMap.adj.clear();

        for (Road r : roads)
        {
            DijkstraSP.addEdgeToAdj(r);
        }
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void fastest()
    {
        DijkstraSP SP = new FastestRoad(roads);
        ArrayList<Linked> list = SP.mapRoute(start, end);

        Linked l1 = list.get(0);
        Linked l2 = list.get(1);

        for (Linked l : list)
        {
            System.out.println("Drivetime: " + l.getDrivetime() + " Length: " + l.getLength() + " From: " + l.getFrom() + " turn: " + l.getTurn());
        }

        //a in report
        assertEquals(3, l1.getFrom());
        assertEquals(1, l2.getFrom());
        assertEquals(3, l1.getDrivetime(), 0);

        //e in report
        assertEquals(null, StartMap.adj.get(7));
    }

    @Test
    public void shortest()
    {
        DijkstraSP SP = new ShortestRoad(roads);
        ArrayList<Linked> list = SP.mapRoute(start, end);

        for (Linked l : list)
        {
            System.out.println("Drivetime: " + l.getDrivetime() + " Length: " + l.getLength() + " From: " + l.getFrom() + " turn: " + l.getTurn());
        }

        Linked l1 = list.get(0);
        Linked l2 = list.get(1);
        Linked l3 = list.get(2);

        //b in report
        assertEquals(8, l1.getFrom());
        assertEquals(2, l2.getFrom());
        assertEquals(1, l3.getFrom());
        assertEquals(6, l1.getLength(), 0);

        ArrayList<Linked> distTo = SP.getDistTo();

        //c in report
        assertEquals(null, distTo.get(6));
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
