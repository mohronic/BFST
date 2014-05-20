package ctrl;

import krakloader.KrakParser;
import QuadTreePack.QuadTree;
import QuadTreePack.QuadTreeInterface;
import SearchEngine.CityNameParser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private static QuadTreeInterface[] qts = new QuadTreeInterface[4];
    public final static ArrayList<Road> allRoads = new ArrayList<>();
    public final static ArrayList<ArrayList<Road>> adj = new ArrayList<>();
    public static Rectangle2D bounds;
    public static HashMap<Integer, String> zipToCityHashMap;
    public static boolean osm;

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
        //Collections.sort(allRoads);
        
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
        CanvasFocusML cml = new CanvasFocusML();
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
    public static QuadTreeInterface[] getQuadTree() {
        return StartMap.qts;
    }

    /**
     * Creates an instance of StartMap.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        int choose = JOptionPane.showOptionDialog(null, 
        "Do you want OpenStreetMaps (osm), or Krak data?\n Osm loads slower but has more detail.", 
        "Dataset", 
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.INFORMATION_MESSAGE, 
        null, 
        new String[]{"OSM", "Krak"},
        "default");
        if(choose == 1) osm = false;
        else osm = true;
        StartMap sm = new StartMap(osm);
    }
}
