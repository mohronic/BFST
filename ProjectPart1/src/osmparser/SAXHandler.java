package osmparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * brug Attributes atts.getValue("maxlon") f.eks.;
 *
 * @author z3ss
 */
class SAXHandler extends DefaultHandler {

    HashMap<Long, Node> nodes = new HashMap<>();
    List<Edge> edges = new ArrayList<>();
    Node cNode = null;
    Way cWay = null;
    Edge cEdge = null;
    String content = null;
    ArrayList<Edge> cEdges = null;
    double minlat, maxlat, minlon, maxlon;

    private Hashtable tags;

    @Override
    public void startDocument() throws SAXException {
        tags = new Hashtable();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        String key = localName;
        Object value = tags.get(key);
        int c = 1;
        boolean isWay = false;

        switch (key) {
            case "bounds":
                minlat = Double.parseDouble(atts.getValue("minlat"));
                maxlat = Double.parseDouble(atts.getValue("maxlat"));
                minlon = Double.parseDouble(atts.getValue("minlon"));
                maxlon = Double.parseDouble(atts.getValue("maxlon"));
                break;
            case "node":
                long id = Long.parseLong(atts.getValue("id"));
                double lat = Double.parseDouble(atts.getValue("lat"));
                double lon = Double.parseDouble(atts.getValue("lon"));
                cNode = new Node(id, lat, lon);
                break;
            case "way":
                cWay = new Way(Long.parseLong(atts.getValue("id")));
                cEdges = new ArrayList<>();
                isWay = true;
                break;
            case "nd":
                cWay.addNode(Long.parseLong(atts.getValue("ref")));
                if (c % 2 == 1) {
                    c++;
                    cEdge = new Edge(cWay.getID());
                    cEdge.addNode(nodes.get(Long.parseLong(atts.getValue("ref"))));
                } else {
                    c++;
                    cEdge.addNode(nodes.get(Long.parseLong(atts.getValue("ref"))));
                    cEdges.add(cEdge);
                }
                break;
            case "tag":
                if (isWay) {
                    switch (atts.getValue("k")) {
                        case "highway":
                            for (Edge e : cEdges) {
                                e.setType(atts.getValue("v"));
                            }
                            break;
                        case "name":
                            for (Edge e : cEdges) {
                                e.setType(atts.getValue("v"));
                            }
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
                nodes.put(cNode.getID(), cNode);
                break;
            case "way":
                edges.addAll(cEdges);
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
}
