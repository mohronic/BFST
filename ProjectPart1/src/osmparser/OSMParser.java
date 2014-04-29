package osmparser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import krakloader.EdgeData;
import krakloader.NodeData;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author z3ss
 */
public class OSMParser {
    
    public static List<EdgeData> edges;
    public static HashMap<Long, NodeData> nodes;
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String filename = "D:\\ITU\\out.osm";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAXHandler());
        xmlReader.parse(SAXHandler.convertToFileURL(filename));
    }
    
    public static void setData(List<EdgeData> edges, HashMap<Long, NodeData> nodes){
        OSMParser.edges = edges;
        OSMParser.nodes = nodes;
    }
}
