package QuadTreePack;

import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Gruppe A
 */
public interface QuadTreeInterace
{

    public void insert(Road rd);

    public ArrayList<Road> search(double x1, double x2, double y1, double y2);

}
