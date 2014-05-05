package osmparser;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
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

    private HashMap<Long, NodeData> nodes = new HashMap<>();
    private List<Way> ways = new ArrayList<>();
    private NodeData cNode = null;
    private Way cWay = null;
    private boolean isWay = false;

    private double miny, maxy, minx, maxx;

    private Hashtable tags;

    @Override
    public void startDocument() throws SAXException {
        tags = new Hashtable();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        String key = localName;
        Object value = tags.get(key);

        switch (key) {
            case "bounds":
                minx = Double.parseDouble(atts.getValue("minlon"));
                miny = Double.parseDouble(atts.getValue("minlat"));
                maxx = Double.parseDouble(atts.getValue("maxlon"));
                maxy = Double.parseDouble(atts.getValue("maxlat"));
                break;
            case "node":
                long id = Long.parseLong(atts.getValue("id"));
                double lat = Double.parseDouble(atts.getValue("lat"));
                lat = (-lat) + maxy + miny;
                double lon = Double.parseDouble(atts.getValue("lon"));
                cNode = new NodeData(id, lon, lat);
                break;
            case "way":
                cWay = new Way(Long.parseLong(atts.getValue("id")));
                isWay = true;
                break;
            case "nd":
                cWay.addNode(Long.parseLong(atts.getValue("ref")));
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
                    }
                }
                break;
        }

        if (value == null) {
            tags.put(key, new Integer(1));
        } else {
            int count = ((Integer) value).intValue();
            count++;
            tags.put(key, new Integer(count));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String key = localName;
        switch (key) {
            case "node":
                nodes.put(cNode.getOSMID(), cNode);
                break;
            case "way":
                if(cWay.getTyp() != 49)
                ways.add(cWay);
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        Enumeration e = tags.keys();
        while (e.hasMoreElements()) {
            String tag = (String) e.nextElement();
            int count = ((Integer) tags.get(tag)).intValue();
            System.out.println("Local Name \"" + tag + "\" occurs "
                    + count + " times");
        }
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
            case "trunk" :
                type = 1;
                break;
            case "primary" :
                type = 1;
                break;
            case "path":
                type = 8;
                break;
            case "coastline":
                type = 48;
                break;
            default :
                type = 4;
                break;
        }
        return type;
    }
}
