/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import QuadTreePack.NSEW;
import QuadTreePack.QuadTree;
import java.util.ArrayList;
import krakloader.*;
import model.Road;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author archigo
 */
public class QuadTreeTest {

    ArrayList<Road> rdl;
    QuadTree qt;

    public QuadTreeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        rdl = new ArrayList<Road>();
        makeRoads(rdl);
        qt = new QuadTree(NSEW.TEST, null);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestA() {

        qt.insert(rdl.get(0));
        assertEquals(rdl.get(0), qt.search(rdl.get(1).midX - 10, rdl.get(1).midX + 0, rdl.get(1).midY - 10, rdl.get(1).midY + 10).get(0));
    }

    @Test
    public void B() {
        EdgeData ed = new EdgeData("546404,546334,44.56884,5,5,6,'Christiansø',0,0,0,0,'','','','',0,0,3740,3740,411,5,0,'',0,0,30,0.103,'','','',10804679,'10/02/01',4144926");
        NodeData nd = new NodeData("433405,1,1,595527.51786,6402050.98297");
        Road rd = new Road(ed, nd, nd);
        rd.midX = -10;
        rd.midY = -10;
        qt.insert(rd);
        assertTrue(qt.search(0, 1000, 0, 1000).isEmpty());
    }

    @Test
    public void TestC() {
        
        for(Road road: rdl){
            qt.insert(road);
        }
        assertTrue(true);

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    void makeRoads(ArrayList<Road> rdl) {
        EdgeData ed = new EdgeData("546404,546334,44.56884,5,5,6,'Christiansø',0,0,0,0,'','','','',0,0,3740,3740,411,5,0,'',0,0,30,0.103,'','','',10804679,'10/02/01',4144926");
        NodeData nd = new NodeData("433405,1,1,595527.51786,6402050.98297");
        for (int i = 0; i < 2000; i++) {
            rdl.add(new Road(ed, nd, nd));
        }
        int i = 1;
        int y = 250;
        int x = 250;

        for (Road road : rdl) {
            if (i <= 500) {
                road.midX = x;
                road.midY = y;
                if (i == 250) {
                    x = 750;
                }
                i++;
            } else if (i <= 1000) {
                road.midX = x;
                road.midY = y;
                if (i == 500) {
                    y = 750;
                    x = 250;
                }
                i++;
            } else if (i <= 1500) {
                road.midX = x;
                road.midY = y;
                if (i == 750) {
                    y = 750;
                    x = 750;
                }
                i++;
            } else if (i <= 2000) {
                road.midX = x;
                road.midY = y;
                i++;
            }
        }
    }
}
