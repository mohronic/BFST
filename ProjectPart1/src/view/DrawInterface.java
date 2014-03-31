package view;

import java.awt.Graphics;

/**
 *
 * @author Gruppe A
 */
public interface DrawInterface
{

    public void drawLine(double x1, double y1, double x2, double y2);

    public void drawRect(double x1, double y1, double width, double height);

    public void setRed();

    public void setBlue();

    public void setGreen();

    public void setBlack();

    public void setGraphics(Graphics g);
}
