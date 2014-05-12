package view;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * An interface containing the methods to draw lines and rectangles, using
 * doubles and changing colors.
 *
 * @author Gruppe A
 */
public interface DrawInterface {

    /**
     * Draws a line between two points, with double values.
     *
     * @param x1, x coordinate of first point.
     * @param y1, y coordinate of first point.
     * @param x2, x coordinate of second point.
     * @param y2, y coordinate of second point.
     */
    public void drawLine(double x1, double y1, double x2, double y2);

    /**
     * Draws a rectangle, with the upper left corner at (x1, y1), with double
     * values
     *
     * @param x1, x coordinate of upper left corner.
     * @param y1, y coordinate of upper left corner.
     * @param width, width of the rectangle.
     * @param height, height of the rectangle.
     */
    public void drawRect(double x1, double y1, double width, double height);

    /**
     * Changes the color being drawn with to red.
     */
    public void setRed();

    /**
     * Changes the color being drawn with to blue.
     */
    public void setBlue();

    /**
     * Changes the color being drawn with to green.
     */
    public void setGreen();

    /**
     * Changes the color being drawn with to black.
     */
    public void setBlack();
    
     /**
     * Changes the color being drawn with to black.
     */
    public void setOrange();

    /**
     * set the graphics object to use (since it implementation uses Graphics2D).
     * @param g 
     */
    public void setGraphics(Graphics g);
    
    public BufferedImage getImage();
    
    public void startDraw();
    
    public void endDraw();
}
