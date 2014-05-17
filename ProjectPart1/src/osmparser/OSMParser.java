package osmparser;

import static QuadTreePack.NSEW.ROOT;
import QuadTreePack.QuadTree;
import Route.DijkstraSP;
import ctrl.StartMap;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import krakloader.EdgeData;
import krakloader.NodeData;
import model.Road;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author z3ss
 */
public class OSMParser {

    public static List<EdgeData> edges = new ArrayList<>();
    public static List<Way> ways;
    public static HashMap<Integer, NodeData> nodes;
    public static Rectangle2D bounds;
    private StartMap sm;

    public OSMParser(StartMap sm) {
        this.sm = sm;
    }

    public void parseOSM() throws ParserConfigurationException, SAXException, IOException {
        String filename = "./data/osm.osm";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAXHandler());
        xmlReader.parse(SAXHandler.convertToFileURL(filename));
        setupData();
    }

    private void setupData() {
        sm.setBounds(OSMParser.bounds);
        QuadTree qtlvl1 = new QuadTree(ROOT, null);
        QuadTree qtlvl2 = new QuadTree(ROOT, null);
        QuadTree qtlvl3 = new QuadTree(ROOT, null);
        QuadTree qtlvl4 = new QuadTree(ROOT, null);

        int refOne = 0, refTwo = 0;
        for (Way w : ways) {
            boolean isFirst = true;
            ArrayList<Integer> nodeList = w.getNodes();
            for (Integer s : nodeList) {
                if (isFirst) {
                    refOne = s;
                    isFirst = false;
                } else {
                    refTwo = s;
                    NodeData tOne = nodes.get(refOne);
                    NodeData tTwo = nodes.get(refTwo);
                    double length = Math.sqrt(Math.pow(tTwo.getX_COORD() - tOne.getX_COORD(), 2) + Math.pow(tTwo.getY_COORD() - tOne.getY_COORD(), 2));
                    double dTime = ((length/1000)/(double)w.getSpeed())*60;
                    EdgeData e = new EdgeData(refOne, refTwo, w.getName(), w.getTyp(), w.getSpeed(), dTime, length);
                    refOne = refTwo;
                    Road r = new Road(e, tOne, tTwo);
                    StartMap.allRoads.add(r);
                    DijkstraSP.addEdgeToAdj(r);
                    if (e.TYP == 1 || e.TYP == 3 || e.TYP == 2 || e.TYP == 48) {
                        qtlvl1.insert(r);
                    } else if (e.TYP == 4) {
                        qtlvl2.insert(r);
                    } else if (e.TYP == 5 || e.TYP == 6 || e.TYP == 8) {
                        qtlvl3.insert(r);
                    } else {
                        qtlvl4.insert(r);
                    }
                }
            }
        }
        
        QuadTree[] qts = new QuadTree[]{qtlvl1, qtlvl2, qtlvl3, qtlvl4};
        sm.setData(qts);
    }

    public static void setData(List<Way> ways, HashMap<Integer, NodeData> nodes, Rectangle2D bounds) {
        OSMParser.ways = ways;
        OSMParser.nodes = nodes;
        OSMParser.bounds = bounds;
    }
}
