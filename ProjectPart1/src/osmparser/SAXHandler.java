package osmparser;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import krakloader.NodeData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * brug Attributes atts.getValue("maxlon") f.eks.;
 *
 * @author z3ss
 */
public class SAXHandler extends DefaultHandler {

    //private HashMap<Integer, NodeData> nodes = new HashMap<>();
    private ArrayList<NodeData> nodes = new ArrayList<>();
    private List<Way> ways = new ArrayList<>();
    private NodeData cNode = null;
    private Way cWay = null;
    private boolean isWay = false;

    private double miny, maxy, minx, maxx;

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        String key = localName;

        switch (key) {
            case "bounds":
                minx = Double.parseDouble(atts.getValue("minlon"));
                miny = Double.parseDouble(atts.getValue("minlat"));
                maxx = Double.parseDouble(atts.getValue("maxlon"));
                maxy = Double.parseDouble(atts.getValue("maxlat"));
                break;
            case "node":
                int id = Integer.parseInt(atts.getValue("id"));
                double lat = Double.parseDouble(atts.getValue("lat"));
                double lon = Double.parseDouble(atts.getValue("lon"));
                if(lon > maxx)maxx = lon;
                if(lat > maxy)maxy = lat;
                cNode = new NodeData(id, lon, lat);
                break;
            case "way":
                cWay = new Way();
                isWay = true;
                break;
            case "nd":
                cWay.addNode(Integer.parseInt(atts.getValue("ref")));
                break;
            case "tag":
                if (isWay) {
                    switch (atts.getValue("k")) {
                        case "highway":
                            cWay.setTyp(toTyp(atts.getValue("v")));
                            break;
                        case "name":
                            cWay.setName(atts.getValue("v"));
                            break;
                        case "natural":
                            cWay.setTyp(toTyp(atts.getValue("v")));
                            break;
                        case "maxspeed" :
                            cWay.setSpeed(Integer.parseInt(atts.getValue("v")));
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String key = localName;
        switch (key) {
            case "node":
                nodes.add(cNode);
                break;
            case "way":
                if (cWay.getSpeed() == 0) cWay.setSpeed(50);
                ways.add(cWay);
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        Rectangle2D rect = new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
        OSMParser.setData(ways, nodes, rect);
    }

    public static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    private int toTyp(String typ) {
        int type;
        switch (typ) {
            case "motorway":
                type = 1;
                break;
            case "trunk":
                type = 2;
                break;
            case "primary":
                type = 3;
                break;
            case "path":
                type = 8;
                break;
            case "coastline":
                type = 48;
                break;
            case "secondary":
                type = 4;
                break;
            case "tertiary":
                type = 5;
                break;
            case "unclassified":
                type = 6;
                break;
            default:
                type = 6;
                break;
        }
        return type;
    }
}

