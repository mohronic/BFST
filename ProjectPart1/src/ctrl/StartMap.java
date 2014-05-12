package ctrl;

import QuadTreePack.QuadTree;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
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
    public static Rectangle2D bounds;

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
        frame.setMinimumSize(new Dimension(880, 700));
        frame.setSize(1110, 700);

        Canvas c = Canvas.getInstance(cd);
        SideBar SB = new SideBar();
        ML ml = new ML();
        KL kl = new KL();
        c.addMouseListener(ml);
        c.addMouseMotionListener(ml);
        c.addMouseWheelListener(ml);
        c.addKeyListener(kl);
        cd.addObserver(c);

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
        StartMap sm = new StartMap(true);
    }

}
