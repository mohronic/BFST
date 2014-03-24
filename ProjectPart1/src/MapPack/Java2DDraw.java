/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapPack;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class Java2DDraw extends JComponent implements DrawInterface
{
    Graphics2D g2;
    
    public Java2DDraw(){
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g2 = (Graphics2D) g;
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2)
    {
        Shape road = new Line2D.Double(x1, y1, x2, y2);
        g2.draw(road);
    }

    @Override
    public void drawRect(double x1, double y1, double width, double height)
    {
        Shape rect = new Rectangle2D.Double(x1, y1, width, height);
        g2.draw(rect);
    }
    

}
