package ctrl;

import static QuadTreePack.NSEW.ROOT;
import javax.swing.JFrame;
import java.io.IOException;
import java.util.ArrayList;
import krakloader.EdgeData;
import krakloader.KrakLoader;
import krakloader.NodeData;
import QuadTreePack.QuadTree;
import SearchEngine.SearchLabel;
import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import model.Road;
import model.CurrentData;
import view.Canvas;

/**
 * Class containing main method. It loads the data from the Krak files and
 * creates the GUI.
 * 
 * @author Gruppe A
 */
public class StartMap {
    
    public static double xmax, ymax;
    private JFrame frame;
    private CurrentData cd;
    private static QuadTree qt;
    private static SearchLabel sl;
    
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
        frame.setSize(970, 770);
        
        Canvas c = new Canvas(cd);
        ML ml = new ML(c);
        c.addMouseListener(ml);
        c.addMouseMotionListener(ml);
        cd.addObserver(c);
        
        KL kl = new KL(sl);
        sl.addKeyListener(kl);
        frame.setLayout(new BorderLayout());
        frame.add(c, BorderLayout.CENTER);
        frame.add(CurrentData.getCurrentRoadLabel(), BorderLayout.SOUTH);
        frame.add(sl, BorderLayout.NORTH);
        frame.setVisible(true);
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

        for (NodeData n : nodes) {
            n.recalc(kl.ymax, kl.xmin);
        }
        for (EdgeData ed : edges) {
            Road rd = new Road(ed, nodes.get(ed.FNODE - 1), nodes.get(ed.TNODE - 1));
            roads.add(rd);
        }
        xmax = kl.xmax - kl.xmin;
        ymax = (-kl.ymin) + kl.ymax;
        qt = new QuadTree(ROOT, null);
        cd = CurrentData.getInstance();
        cd.setXmax(xmax);
        cd.setYmax(ymax);
        for (Road r : roads) {
            qt.insert(r);
        }
        cd.updateArea(new Rectangle2D.Double(0, 0, xmax, ymax));
        sl = new SearchLabel(roads);
    }
    
    /**
     * Returns the instance of the Quadtree root.
     * @return The root of the Quadtree.
     */
    public static QuadTree getQuadTree() {
        return qt;
    }
    
    /**
     * Creates an instance of StartMap.
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        StartMap sm = new StartMap();
    }
}
