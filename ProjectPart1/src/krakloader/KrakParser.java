package krakloader;

import static QuadTreePack.NSEW.ROOT;
import QuadTreePack.QuadTree;
import Route.DijkstraSP;
import ctrl.StartMap;
import static ctrl.StartMap.allRoads;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import model.Road;

/**
 * Class for parsing and converting Krak data to the right format.
 * 
 * @author Group A
 */
public class KrakParser {

    public static double xmax, ymax, xmin, ymin;
    private StartMap sm;
    
    /**
     * Constructor with pointer to StartMap as parameter
     * @param sm 
     */
    public KrakParser(StartMap sm) {
        this.sm = sm;
    }

    /*
     * Loads the data with the use of KrakLoader and LoadCoast, recalculates the
     * coordinates and inserts the data into the Quadtree.
     * @throws IOException 
     */
    public void setData() throws IOException {
        final ArrayList<NodeData> nodes = new ArrayList<>();
        final ArrayList<EdgeData> edges = new ArrayList<>();
        final ArrayList<Road> roads = new ArrayList<>();

        String dir = "./data/";

        KrakLoader kl = new KrakLoader() {
            @Override
            public void processNode(NodeData nd) {
                nodes.add(nd);
            }

            @Override
            public void processEdge(EdgeData ed) {
                edges.add(ed);
            }
        };
        kl.load(dir + "kdv_node_unload.txt", dir + "kdv_unload.txt");

        //loads coastline data
        LoadCoast lc = new LoadCoast();
        ArrayList<Road> coastList = lc.load(dir + "correctedCoastLine.txt");
        coastList.addAll(lc.load(dir + "correctedPoliticalBorder.txt"));
        coastList.addAll(lc.load(dir + "correctedRiverLine.txt"));
        loadCoastHelper(kl, lc, coastList);

        for (NodeData n : nodes) {
            n.recalc(kl.ymax, kl.xmin);
        }

        sm.setBounds(new Rectangle2D.Double(0, 0, xmax, ymax));

        QuadTree qtlvl1 = new QuadTree(ROOT, null);
        QuadTree qtlvl2 = new QuadTree(ROOT, null);
        QuadTree qtlvl3 = new QuadTree(ROOT, null);
        QuadTree qtlvl4 = new QuadTree(ROOT, null);

        for (EdgeData ed : edges) {
            Road r = new Road(ed, nodes.get(ed.FNODE - 1), nodes.get(ed.TNODE - 1));
            roads.add(r);
            allRoads.add(r);
            DijkstraSP.addEdgeToAdj(r);
            int typ = r.getEd().TYP;
            if (typ == 1 || typ == 3 || typ == 2 || typ == 48 || typ == 80 || typ == 42 || typ == 41) {
                qtlvl1.insert(r);
            } else if (typ == 4) {
                qtlvl2.insert(r);
            } else if (typ == 5 || typ == 6) {
                qtlvl3.insert(r);
            } else {
                qtlvl4.insert(r);
            }
        }
        for (Road r : coastList) {
            qtlvl1.insert(r);
        }

        QuadTree[] qts = new QuadTree[]{qtlvl1, qtlvl2, qtlvl3, qtlvl4};

        sm.setData(qts);

    }
    
    /**
     * Configures the coast data and recalculates bounds.
     * @param kl
     * @param lc
     * @param coastList 
     */
    private void loadCoastHelper(KrakLoader kl, LoadCoast lc, ArrayList<Road> coastList) {
        if (kl.xmin > lc.xmin) {
            xmin = lc.xmin;
        } else {
            xmin = kl.xmin;
        }
        if (kl.ymin > lc.ymin) {
            ymin = lc.ymin;
        } else {
            ymin = kl.ymin;
        }
        if (kl.xmax > lc.xmax) {
            xmax = kl.xmax - xmin;
        } else {
            xmax = lc.xmax - xmin;
        }
        if (kl.ymax > lc.ymax) {
            ymax = (-ymin) + kl.ymax;
        } else {
            ymax = (-ymin) + lc.ymax;
        }

        for (Road r : coastList) {
            NodeData tnd = r.getFn();
            tnd.recalc(kl.ymax, kl.xmin);
            r.setFn(tnd);

            NodeData tnd2 = r.getTn();
            tnd2.recalc(kl.ymax, kl.xmin);
            r.setTn(tnd2);
        }
    }
}
