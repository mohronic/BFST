/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import krakloader.*;
import model.CurrentData;
import model.Road;

/**
 *
 * @author KristianMohr
 */
public class Canvas extends JComponent implements Observer
{

    private List<Road> rd;
    private CurrentData cd;
    private double xmax, ymax, xmin;
    private double scale = 1;
    private DrawInterface j2d;

    public Canvas(CurrentData cd)
    {
        this.cd = cd;
        xmax = cd.getXmax();
        ymax = cd.getYmax();
        xmin = cd.getXmin();
        rd = cd.getRoads();
        j2d = new Java2DDraw();
    }

    private void drawMap()
    {
        scale = ymax / (double) this.getHeight();
        for (Road r : rd)
        {
            double x1, x2, y1, y2;
            NodeData n1 = r.getFn();
            NodeData n2 = r.getTn();
            x1 = n1.getX_COORD() / scale;
            y1 = n1.getY_COORD() / scale;
            x2 = n2.getX_COORD() / scale;
            y2 = n2.getY_COORD() / scale;
            //Road colering:
            switch (r.getEd().TYP)
            {
                case 1:
                    j2d.setRed(); //Highway
                    break;
                case 3:
                    j2d.setBlue(); //Main roads
                    break;
                case 8:
                    j2d.setGreen(); //Path
                    break;
                default:
                    j2d.setBlack(); //Other
                    break;
            }
            j2d.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        j2d.setGraphics(g);
        drawMap();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        rd = cd.getRoads();
        xmax = cd.getXmax();
        ymax = cd.getYmax();
        xmin = cd.getXmin();
        repaint();
    }

    public double getScale()
    {
        return scale;
    }

    

}
