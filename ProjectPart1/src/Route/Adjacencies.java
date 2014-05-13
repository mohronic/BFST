/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Route;

import ctrl.StartMap;
import java.util.ArrayList;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Adjacencies
{

    public Adjacencies()
    {

    }
    
    public static void addEdge(Road r)
    {
         switch (r.getEd().ONE_WAY) // One way routes, should be put in the correct direction
        {
            case "":
            {
                buildAdj(r);
                Road r2 = new Road(r);
                buildAdj(r2);
                break;
            }
            case "tf":
            {
                buildAdj(r);
                break;
            }
            case "ft":
            {
                Road r2 = new Road(r);
                buildAdj(r2);
                break;
            }
            default:
            {
                //should not happen
                break;
            }
        }
    }

    private static void buildAdj(Road r)
    {
        if (StartMap.adj.get(r.from()) == null)
        {
            ArrayList<Road> list = new ArrayList<>();
            list.add(r);
            list.trimToSize();
            StartMap.adj.put(r.from(), list);
        } else
        {
            ArrayList<Road> list = StartMap.adj.get(r.from());
            list.add(r);
            list.trimToSize();
            StartMap.adj.put(r.from(), list);
        }
    }

}
