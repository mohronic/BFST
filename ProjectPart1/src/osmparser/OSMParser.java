package osmparser;

import static QuadTreePack.NSEW.ROOT;
import QuadTreePack.QuadTree;
import SearchEngine.SearchLabel;
import ctrl.KL;
import ctrl.ML;
import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.CurrentData;
import model.Road;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import view.Canvas;
import view.SideBar;

/**
 *
 * @author z3ss
 */
public class OSMParser
{

    public static List<EdgeData> edges = new ArrayList<>();
    public static List<Way> ways;
    public static HashMap<Long, NodeData> nodes;
    public static Rectangle2D bounds;
    private static JFrame frame;
    private static CurrentData cd;
    public static QuadTree qt;
    private static final ArrayList<Road> allRoads = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        String filename = "D:\\ITU\\out.osm";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAXHandler());
        xmlReader.parse(SAXHandler.convertToFileURL(filename));
        setupData();
        setup();
    }

    private static void setupData()
    {
        qt = new QuadTree(ROOT, null);
        cd = CurrentData.getInstance();
        cd.setXmax(bounds.getMaxX());
        cd.setXmin(bounds.getMinX());
        cd.setYmax(bounds.getMaxY());
        cd.setYmin(bounds.getMinY());
        long refOne = 0, refTwo = 0;
        for (Way w : ways)
        {
            boolean isFirst = true;
            ArrayList<Long> nodeList = w.getNodes();
            for (Long s : nodeList)
            {
                if (isFirst)
                {
                    refOne = s;
                    isFirst = false;
                } else
                {
                    refTwo = s;
                    EdgeData e = new EdgeData(w.getID(), refOne, refTwo);
                    e.VEJNAVN = w.getName();
                    e.TYP = w.getTyp();
                    edges.add(e);
                    refOne = refTwo;
                }
            }
        }

        for (EdgeData e : edges)
        {
            Road r = new Road(e, nodes.get(e.NODEONE), nodes.get(e.NODETWO));
            allRoads.add(r);
            qt.insert(r);
        }

        cd.updateArea(bounds);
    }

    private static void setup() throws IOException
    {
        frame = new JFrame("Map Draw");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(970, 770);
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
    }

    public static void setData(List<Way> ways, HashMap<Long, NodeData> nodes, Rectangle2D bounds)
    {
        OSMParser.ways = ways;
        OSMParser.nodes = nodes;
        OSMParser.bounds = bounds;
    }
}
