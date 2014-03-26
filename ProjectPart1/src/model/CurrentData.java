/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import QuadTreePack.QuadTree;
import ctrl.StartMap;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author z3ss
 */
public class CurrentData extends Observable {

    private static CurrentData instance = null;
    private final QuadTree qt;
    private double xmax = 0, ymax = 0;
    private double oldx = 0, oldy = 0;
    private List<Road> roads = new ArrayList<>();
    private Rectangle2D view;

    public static CurrentData getInstance() {
        if (instance == null) {
            instance = new CurrentData();
        }
        return instance;
    }

    private CurrentData() {
        qt = StartMap.getQuadTree();
        view = new Rectangle2D.Double();
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void updateArea(Rectangle2D r) {
        view = r;
        oldy = view.getMinY();
        oldx = view.getMinX();
        roads.clear();
        roads = qt.search(r.getX(), r.getX() + r.getWidth(), r.getY(), r.getY() + r.getHeight());
        setChanged();
        notifyObservers();
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
    }

    public double getXmax() {
        return xmax;
    }

    public double getYmax() {
        return ymax;
    }

    public Rectangle2D getView() {
        return view;
    }
    public QuadTree getQT(){
        return qt;
    }

    public double getOldx() {
        return oldx;
    }

    public double getOldy() {
        return oldy;
    }
}
