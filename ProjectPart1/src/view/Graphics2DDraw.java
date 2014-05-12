package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Implementation of DrawInterface, which is a singleton, since only one is
 * needed.
 *
 * @author Gruppe A
 */
public class Graphics2DDraw implements DrawInterface {

    private static Graphics2DDraw instance = null;
    private BufferedImage bi;
    private Graphics2D bg;
    private Canvas c;
    private double xK = 0, yK = 0;

    private Graphics2DDraw() {
    }

    public static Graphics2DDraw getInstance() {
        if (instance == null) {
            instance = new Graphics2DDraw();
        }

        return instance;
    }

    @Override
    public void setGraphics(Graphics g) {
        //bg = (Graphics2D) g;
        bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        Shape road = new Line2D.Double(x1, y1, x2, y2);
        bg.draw(road);
    }

    @Override
    public void drawRect(double x1, double y1, double width, double height) {
        Shape rect = new Rectangle2D.Double(x1, y1, width, height);
        setBlack();
        bg.draw(rect);
    }

    @Override
    public void setRed() {
        bg.setColor(Color.RED);
    }

    @Override
    public void setBlue() {
        bg.setColor(Color.BLUE);
    }

    @Override
    public void setGreen() {
        bg.setColor(Color.GREEN);
    }

    @Override
    public void setBlack() {
        bg.setColor(Color.BLACK);
        bg.setStroke(new BasicStroke(1));
    }

    @Override
    public void setOrange() {
        bg.setColor(Color.ORANGE);
        bg.setStroke(new BasicStroke(2));
    }

    @Override
    public BufferedImage getImage() {
        return bi;
    }

    @Override
    public void startDraw() {
        bi = new BufferedImage(256,256, BufferedImage.TYPE_INT_ARGB);
        bg = bi.createGraphics();
    }

    @Override
    public void endDraw() {
        bg.dispose();
    }
}
