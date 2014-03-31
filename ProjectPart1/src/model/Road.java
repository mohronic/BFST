package model;

import krakloader.NodeData;
import krakloader.EdgeData;

/**
 *
 * @author Gruppe A
 */
public class Road
{

    private final EdgeData ed;
    public final double midX, midY;
    private final NodeData fn;
    private final NodeData tn;

    public Road(EdgeData ed, NodeData fn, NodeData tn)
    {
        this.ed = ed;
        this.fn = fn;
        this.tn = tn;
        midX = (fn.getX_COORD() + tn.getX_COORD()) / 2;
        midY = (fn.getY_COORD() + tn.getY_COORD()) / 2;
    }

    public EdgeData getEd()
    {
        return ed;
    }

    public NodeData getFn()
    {
        return fn;
    }

    public NodeData getTn()
    {
        return tn;
    }

}
