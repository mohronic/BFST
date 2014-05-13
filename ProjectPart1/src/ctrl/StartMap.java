package ctrl;

import QuadTreePack.QuadTree;
import SearchEngine.CityNameParser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import model.CurrentData;
import model.Road;
import org.xml.sax.SAXException;
import osmparser.OSMParser;
import view.Canvas;
import view.SideBar;

/**
 * Class containing main method. It loads the data from the Krak files and
 * creates the GUI.
 *
 * @author Gruppe A
 */
public class StartMap {

    private JFrame frame;
    private CurrentData cd;
    private static QuadTree[] qts = new QuadTree[4];
    public final static ArrayList<Road> allRoads = new ArrayList<>();
    public final static HashMap<Point2D.Double, ArrayList<Road>> adj = new HashMap<>();
    public static Rectangle2D bounds;
    public static int roadID = 0;
    public static HashMap<Integer, String> zipToCityHashMap;

    /**
     * Constructor for the StartMap object.
     *
     * @throws IOException
     */
    public StartMap(boolean osm) throws IOException, SAXException, ParserConfigurationException {
        if (osm) {
            OSMParser op = new OSMParser(this);
            op.parseOSM();
            op = null;
        } else {
            KrakParser kp = new KrakParser(this);
            kp.setData();
            kp = null;
        }
        Collections.sort(allRoads);

        CityNameParser cityNameParser = new CityNameParser();
        zipToCityHashMap = cityNameParser.getZipToCityHashMap();

        cd = CurrentData.getInstance();
        cd.setXmax(bounds.getMaxX());
        cd.setXmin(bounds.getMinX());
        cd.setYmax(bounds.getMaxY());
        cd.setYmin(bounds.getMinY());
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
        frame.setMinimumSize(new Dimension(880, 720));
        frame.setSize(1110, 720);

        Canvas c = Canvas.getInstance(cd);
        SideBar SB = new SideBar();
        ML ml = new ML();
        KL kl = new KL();
        c.addMouseListener(ml);
        c.addMouseMotionListener(ml);
        c.addMouseWheelListener(ml);
        c.addKeyListener(kl);
        cd.addObserver(c);
        CanvasML cml = new CanvasML();
        c.addMouseListener(cml);

        frame.setLayout(new BorderLayout());
        frame.add(c, BorderLayout.CENTER);
        frame.add(CurrentData.getCurrentRoadLabel(), BorderLayout.SOUTH);
        frame.add(SB.getSideBar(), BorderLayout.WEST);
        frame.setVisible(true);
        cd.updateArea(StartMap.bounds);

    }

    public void setData(QuadTree[] qts) {
        StartMap.qts = qts;
    }

    public void setBounds(Rectangle2D bounds) {
        StartMap.bounds = bounds;
    }

    /**
     * Returns the instance of the Quadtree root.
     *
     * @return The root of the Quadtree.
     */
    public static QuadTree[] getQuadTree() {
        return StartMap.qts;
    }

    /**
     * Creates an instance of StartMap.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        StartMap sm = new StartMap(false);
    }
}
