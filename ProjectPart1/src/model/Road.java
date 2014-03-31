package model;

import krakloader.NodeData;
import krakloader.EdgeData;

/**
 * Class able to contain the data of a road, consisting of an EdgeData and two
 * NodeData. It also calculates a midpoint for the Road object.
 * @author Gruppe A
 */
public class Road
{

    private final EdgeData ed;
    public final double midX, midY;
    private final NodeData fn;
    private final NodeData tn;
    
    /**
     * Contructor for the road object.
     * @param ed, EdgeData for the road
     * @param fn, the start NodeData (from node)
     * @param tn, the end NodeData (to node)
     */
    public Road(EdgeData ed, NodeData fn, NodeData tn)
    {
        this.ed = ed;
        this.fn = fn;
        this.tn = tn;
        midX = (fn.getX_COORD() + tn.getX_COORD()) / 2;
        midY = (fn.getY_COORD() + tn.getY_COORD()) / 2;
    }
    
    /**
     * Returns the EdgeData
     * @return Edgedata ed.
     */
    public EdgeData getEd()
    {
        return ed;
    }
    
    /**
     * Returns the from NodeData
     * @return NodeData fn
     */
    public NodeData getFn()
    {
        return fn;
    }
    
    /**
     * Returns the to NodeData
     * @return NodeData tn
     */
    public NodeData getTn()
    {
        return tn;
    }

}
