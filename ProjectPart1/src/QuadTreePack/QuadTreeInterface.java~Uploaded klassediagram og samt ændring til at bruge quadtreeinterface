package QuadTreePack;

import java.util.ArrayList;
import model.Road;

/**
 * An interface for our Quadtree, containing the two used methods.
 * @author Gruppe A
 */
public interface QuadTreeInterface
{
    
    /**
     * Inserts a Road object into the Quadtree.
     * @param rd 
     */
    public void insert(Road rd);
    
    /**
     * Returns the list of Road objects relevant for the rectangle between the
     * points (x1, y1) in the upper left corner and the point (x2, y2) in the
     * lower right corner.
     * 
     * @param x1, x coordinate of the upper left point.
     * @param x2, x coordinate of the lower right point.
     * @param y1, y coordinate of the upper left point.
     * @param y2, y coordinate of the lower right point.
     * @return ArrayList of Road objects.
     */
    public ArrayList<Road> search(double x1, double x2, double y1, double y2);

}
