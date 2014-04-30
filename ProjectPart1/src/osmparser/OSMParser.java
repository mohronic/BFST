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

/**
 *
 * @author z3ss
 */
public class OSMParser {

    public static List<EdgeData> edges;
    public static HashMap<Long, NodeData> nodes;
    public static Rectangle2D bounds;
    private static JFrame frame;
    private static CurrentData cd;
    public static QuadTree qt;
    private static SearchLabel sl;
    private static final ArrayList<Road> allRoads = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
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
    
    private static void setupData(){
        qt = new QuadTree(ROOT, null);
        cd = CurrentData.getInstance();
        for(EdgeData e : edges){
            Road r = new Road(e, nodes.get(e.NODEONE), nodes.get(e.NODETWO));
            allRoads.add(r);
            qt.insert(r);
        }
        
        cd.updateArea(bounds);
        sl = new SearchLabel(allRoads);
    }
    
    private static void setup() throws IOException {
        frame = new JFrame("Map Draw");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(970, 770);

        Canvas c = new Canvas(cd);
        ML ml = new ML(c);
        c.addMouseListener(ml);
        c.addMouseMotionListener(ml);
        cd.addObserver(c);

        KL kl = new KL(sl, c);
        sl.addKeyListener(kl);
        frame.setLayout(new BorderLayout());
        frame.add(c, BorderLayout.CENTER);
        frame.add(CurrentData.getCurrentRoadLabel(), BorderLayout.SOUTH);
        frame.add(sl, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    public static void setData(List<EdgeData> edges, HashMap<Long, NodeData> nodes, Rectangle2D bounds) {
        OSMParser.edges = edges;
        OSMParser.nodes = nodes;
        OSMParser.bounds = bounds;
    }
}
