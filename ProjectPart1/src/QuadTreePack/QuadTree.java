package QuadTreePack;

import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Gruppe A
 */

public class QuadTree implements QuadTreeInterace
{

    private QuadTree northeast, northwest, southeast, southwest;
    private Boundary boundary;
    private final int sizeLimit = 1000;
    private Road[] roadList;
    private int currentRoads = -1;

    public QuadTree(NSEW direction, Boundary bd)
    {
        if (direction == NSEW.ROOT)
        {
            boundary = new Boundary(direction);
            roadList = new Road[sizeLimit];
            return;
        }
        boundary = new Boundary(direction, bd);
        roadList = new Road[sizeLimit];
    }

    @Override
    public void insert(Road rd)
    {
        if (checkBounds(rd))
        {
            if (northeast != null)
            {
                if (northeast.checkBounds(rd))
                {
                    northeast.insert(rd);
                } else if (northwest.checkBounds(rd))
                {
                    northwest.insert(rd);
                } else if (southeast.checkBounds(rd))
                {
                    southeast.insert(rd);
                } else if (southwest.checkBounds(rd))
                {
                    southwest.insert(rd);
                }

            } else if (currentRoads == sizeLimit - 1)
            {
                divide();
            } else if (currentRoads < sizeLimit)
            {
                roadList[++currentRoads] = rd;
            }

        }
    }

    @Override
    public ArrayList<Road> search(double x1, double x2, double y1, double y2)
    {

        ArrayList<Road> rl = new ArrayList<>();
        getRoads(x1, x2, y1, y2, rl);
        return rl;
    }

    private void getRoads(double x1, double x2, double y1, double y2, ArrayList<Road> rl)
    {

        if (northeast != null)
        {

            if (northeast.boundary.containsBox(x1, y1, x2, y2))
            {
                northeast.getRoads(x1, x2, y1, y2, rl);
            }
            if (northwest.boundary.containsBox(x1, y1, x2, y2))
            {
                northwest.getRoads(x1, x2, y1, y2, rl);
            }
            if (southeast.boundary.containsBox(x1, y1, x2, y2))
            {
                southeast.getRoads(x1, x2, y1, y2, rl);
            }
            if (southwest.boundary.containsBox(x1, y1, x2, y2))
            {
                southwest.getRoads(x1, x2, y1, y2, rl);
            }

        } else
        {
            if (roadList[0] != null)
            {
                for (Road road : roadList)
                {
                    if (road == null)
                    {
                        break;
                    }
                    rl.add(road);
                }
            }
        }

    }

    private void divide()
    {
        northeast = new QuadTree(NSEW.NORTHEAST, boundary);
        northwest = new QuadTree(NSEW.NORTHWEST, boundary);
        southeast = new QuadTree(NSEW.SOUTHEAST, boundary);
        southwest = new QuadTree(NSEW.SOUTHWEST, boundary);

        for (Road rd : roadList)
        {
            insert(rd);
        }
        roadList = null;
    }

    private boolean checkBounds(Road rd)
    {
        return boundary.containsPoint(rd.midX, rd.midY);
    }
}
