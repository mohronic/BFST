/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuadTreePack;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Archigo
 */
public class Boundary {

    public double xdim, ydim;
    Center center = new Center();

    public Boundary(NSEW direction, Boundary bd) {
        
        xdim = bd.xdim/2;
        ydim = bd.ydim/2;
        
        switch (direction) {
            case NORTHEAST:
                //System.out.println("ne");
                center.xc = bd.center.xc + xdim;
                center.yc = bd.center.yc - ydim;
                
                break;
            case NORTHWEST:
                //System.out.println("nw");
                center.xc = bd.center.xc - xdim;
                center.yc = bd.center.yc - ydim;
                break;
            case SOUTHEAST:
                //System.out.println("se");
                center.xc = bd.center.xc + xdim;
                center.yc = bd.center.yc + ydim;
                break;
            case SOUTHWEST:
                //System.out.println("sw");
                center.xc = bd.center.xc - xdim;
                center.yc = bd.center.yc + ydim;
                break;
            case ROOT:
                
                System.out.println("Should not happen");
//                center.xc = MapPack.Main.xmax/2;
//                center.yc = MapPack.Main.ymax/2;
//                xdim = MapPack.Main.xmax;
//                ydim = MapPack.Main.ymax;
                break;

        }
        
    }
        
        public Boundary(NSEW direction) {
                center.xc = ctrl.StartMap.xmax/2;
                center.yc = ctrl.StartMap.ymax/2;
                xdim = ctrl.StartMap.xmax/2;
                ydim = ctrl.StartMap.ymax/2;

    }

    public boolean containsPoint(double x, double y) {
        return ((center.xc - xdim) <= x && x <= (center.xc + xdim))
                && ((center.yc - ydim) <= y && y <= (center.yc + ydim));
    }
    
    public boolean containsBox(double x1, double y1, double x2, double y2){
        //Rectangle2D rt1 = new Rectangle2D.Double(x1 ,y1, x2-x1, y2-y1);
        Rectangle2D rt2 = new Rectangle2D.Double(center.xc-xdim ,center.yc-ydim, 2*xdim ,2*ydim);
        return rt2.intersects(x1 ,y1, x2-x1, y2-y1);
        
        //return rt1.contains(rt2);
    }

}
