package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Gruppe A
 */
public class Graphics2DDraw implements DrawInterface
{

    private Graphics2D g2;
    private static Graphics2DDraw instance = null;

    private Graphics2DDraw()
    {
        g2 = null;
    }

    public static Graphics2DDraw getInstance()
    {
        if (instance == null)
        {
            instance = new Graphics2DDraw();
        }
        
        return instance;
    }

    @Override
    public void setGraphics(Graphics g)
    {
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        setBlack();
        g2.draw(rect);
    }

    @Override
    public void setRed()
    {
        g2.setColor(Color.RED);
    }

    @Override
    public void setBlue()
    {
        g2.setColor(Color.BLUE);
    }

    @Override
    public void setGreen()
    {
        g2.setColor(Color.GREEN);
    }

    @Override
    public void setBlack()
    {
        g2.setColor(Color.BLACK);
    }

}
