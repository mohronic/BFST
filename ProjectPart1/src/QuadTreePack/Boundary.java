/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuadTreePack;

/**
 *
 * @author Archigo
 */
public class Boundary {

    public double xdim, ydim;
    Center center;

    public Boundary(NSEW direction, Boundary bd) {
        switch (direction) {
            case NORTHEAST:
                //System.out.println("ne");
                center.xc = 1.5 * bd.center.xc;
                center.yc = 0.5 * bd.center.yc;
                break;
            case NORTHWEST:
                //System.out.println("nw");
                center.xc = 0.5 * bd.center.xc;
                center.yc = 0.5 * bd.center.yc;
                break;
            case SOUTHEAST:
                //System.out.println("se");
                center.xc = 1.5 * bd.center.xc;
                center.yc = 1.5 * bd.center.xc;
                break;
            case SOUTHWEST:
                //System.out.println("sw");
                center.xc = 0.5 * bd.center.xc;
                center.yc = 1.5 * bd.center.yc;
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
                center = new Center();
                center.xc = ctrl.StartMap.xmax/2;
                center.yc = ctrl.StartMap.ymax/2;
                xdim = ctrl.StartMap.xmax;
                ydim = ctrl.StartMap.ymax;

    }

    public boolean containsPoint(double x, double y) {
        return (center.xc - xdim) < x && x < (center.xc + xdim)
                && (center.yc - ydim) < y && y < (center.yc + ydim);
    }
    
    public boolean containsBox(double x1, double y1, double x2, double y2){
        return false;
    }

}
