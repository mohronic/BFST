package ctrl;

import Route.MapRoute;
import static QuadTreePack.NSEW.ROOT;
import javax.swing.JFrame;
import java.io.IOException;
import java.util.ArrayList;
import krakloader.EdgeData;
import krakloader.KrakLoader;
import krakloader.NodeData;
import QuadTreePack.QuadTree;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import krakloader.LoadCoast;
import model.Road;
import model.CurrentData;
import view.Canvas;
import view.SideBar;

/**
 * Class containing main method. It loads the data from the Krak files and
 * creates the GUI.
 * 
 * @author Gruppe A
 */
public class StartMap {
    
    public static double xmax, ymax, xmin, ymin;
    private JFrame frame;
    private CurrentData cd;
    private static QuadTree qtlvl1;
    private static QuadTree qtlvl2;
    private static QuadTree qtlvl3;
    private static QuadTree qtlvl4;
    public final static ArrayList<Road> allRoads = new ArrayList<>();
    
    /**
     * Constructor for the StartMap object.
     * @throws IOException 
     */
    public StartMap() throws IOException {
        setData();
        setup();
    }
    
    /*
     * A setup for the GUI, connecting the frame, canvas and mouseListener/
     * mouseMotionListener.
     * @throws IOException 
     */
    private void setup() throws IOException {
        frame = new JFrame("Map Draw");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(880, 700));
        frame.setSize(1110, 700);
        
        SideBar SB = new SideBar();
        Canvas c = Canvas.getInstance(cd);
        ML ml = new ML();
        c.addMouseListener(ml);
        c.addMouseMotionListener(ml);
        cd.addObserver(c);
        
        frame.setLayout(new BorderLayout());
        frame.add(c, BorderLayout.CENTER);
        frame.add(CurrentData.getCurrentRoadLabel(), BorderLayout.SOUTH);
        frame.add(SB.getSideBar(), BorderLayout.WEST);
        frame.setVisible(true);
        cd.updateArea(new Rectangle2D.Double(0, 0, xmax, ymax));
        
    }
    
    /*
     * Loads the data with the use of the krakloader package, recalculates the
     * coordinates and inserts the data into the Quadtree.
     * @throws IOException 
     */
    private void setData() throws IOException {
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
        for (EdgeData ed : edges) {
            Road rd = new Road(ed, nodes.get(ed.FNODE - 1), nodes.get(ed.TNODE - 1));
            roads.add(rd);
            allRoads.add(rd);
        }
        
        qtlvl1 = new QuadTree(ROOT, null);
        qtlvl2 = new QuadTree(ROOT, null);
        qtlvl3 = new QuadTree(ROOT, null);
        qtlvl4 = new QuadTree(ROOT, null);
        cd = CurrentData.getInstance();
        cd.setXmax(xmax);
        cd.setYmax(ymax);
        for (Road r : roads) {
            int typ = r.getEd().TYP;
            if(typ == 1 || typ == 3 || typ == 2 || typ == 48){  
            qtlvl1.insert(r);
            }
            else if(typ == 4){  
            qtlvl2.insert(r);
            }
            else if(typ == 5 || typ == 6){  
            qtlvl3.insert(r);
            }
            else {  
            qtlvl4.insert(r);
            }
        }
        for (Road r : coastList) {
            qtlvl1.insert(r);
        }
        System.out.println(xmax + " " + ymax);
        System.out.println(kl.xmax + " " + kl.ymax);
        
        
    }
    
    /**
     * Returns the instance of the Quadtree root.
     * @return The root of the Quadtree.
     */
    public static QuadTree[] getQuadTree() {
        
        QuadTree[] qtlist = new QuadTree[4];
        qtlist[0] = qtlvl1;
        qtlist[1] = qtlvl2;
        qtlist[2] = qtlvl3;
        qtlist[3] = qtlvl4;
        return qtlist;
    }
    
    /**
     * Creates an instance of StartMap.
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        StartMap sm = new StartMap();
    }
    
    private void loadCoastHelper(KrakLoader kl, LoadCoast lc, ArrayList<Road> coastList){
        
        
        if(kl.xmin > lc.xmin) xmin = lc.xmin;
        else xmin = kl.xmin;
        if(kl.ymin > lc.ymin) ymin = lc.ymin;
        else ymin = kl.ymin;
        if(kl.xmax > lc.xmax) xmax = kl.xmax - xmin;
        else xmax = lc.xmax - xmin;
        if(kl.ymax > lc.ymax) ymax = (-ymin) + kl.ymax;
        else ymax = (-ymin) + lc.ymax;
        
        for(Road r: coastList){
            NodeData tnd = r.getFn();
            tnd.recalcCoast(kl.ymax, kl.xmin);
            r.setFn(tnd);
            
            NodeData tnd2 = r.getTn();
            tnd2.recalcCoast(kl.ymax, kl.xmin);
            r.setTn(tnd2);
            
            
        }
    }
}
