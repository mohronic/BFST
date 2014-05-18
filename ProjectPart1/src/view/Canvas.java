package view;

import Route.Linked;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComponent;
import krakloader.*;
import model.CurrentData;
import model.Road;

/**
 * The class Canvas extends JComponent and implements Observer. It is used to
 * draw the data in the class CurrentData.
 *
 * @author Gruppe A
 */
public class Canvas extends JComponent implements ObserverC, FocusListener {

    private List<Road> rd;
    private final CurrentData cd;
    private Rectangle2D view;
    private double scale = 1, oldScale = 1;
    private boolean dragbool = false;
    private Rectangle2D dragrect, tempImg, tempView;
    private static Canvas instance = null;
    private final double tSize = 256;
    private int[][] grid;
    private final Rectangle2D oView;
    private HashMap<Integer, BufferedImage> tiles;
    private int tileNr = 1;
    private int i = 0, j = 0;
    private boolean newGrid, isFocus;
    public static ArrayList<Linked> routeND = SideBar.getRoute();
    public static ArrayList<Road> lrs;

    /**
     * Constructor for Canvas, getting the data to draw and instantiates the
     * DrawInterface.
     *
     * @param cd
     */
    private Canvas(CurrentData cd) {
        this.cd = cd;
        oView = new Rectangle2D.Double(cd.getXmin(), cd.getYmin(), cd.getXmax() - cd.getXmin(), cd.getYmax() - cd.getYmin());
        view = oView;
        rd = new ArrayList<>();
        tiles = new HashMap<>();
        lrs = new ArrayList<>();
        newGrid = true;
        isFocus = true;
    }

    public static Canvas getInstance(CurrentData cd) {
        if (instance == null) {
            instance = new Canvas(cd);
        }
        return instance;
    }

    private void createGrid() {
        int xGrid = (int) Math.ceil(tempImg.getWidth() / tSize);
        int yGrid = (int) Math.ceil(tempImg.getHeight() / tSize);
        grid = new int[xGrid][yGrid];
    }

    private void drawMap(Graphics2D g2) {
        tempImg = new Rectangle2D.Double(oView.getX() / scale, oView.getY() / scale, oView.getWidth() / scale, oView.getHeight() / scale);
        tempView = new Rectangle2D.Double(view.getX() / scale, view.getY() / scale, view.getWidth() / scale, view.getHeight() / scale);
        if (newGrid) {
            grid = null;
            tiles.clear();
            tileNr = 1;
            createGrid();
            newGrid = false;
            routeND = SideBar.getRoute();
        }
        if (tiles.size() > 400) {
            tiles.clear();
            grid = new int[grid.length][grid[0].length];
        }
        int sX = Math.max((int) Math.floor((tempView.getX() - tempImg.getX()) / tSize), 0);
        int sY = Math.max((int) Math.floor((tempView.getY() - tempImg.getY()) / tSize), 0);
        int eX = Math.min((int) Math.ceil((tempView.getMaxX() - tempImg.getX()) / tSize), grid.length);
        int eY = Math.min((int) Math.ceil((tempView.getMaxY() - tempImg.getY()) / tSize), grid[0].length);
        i = sX;
        while (i < eX) {
            j = sY;
            while (j < eY) {
                if (grid[i][j] == 0) {
                    Rectangle2D tileArea = new Rectangle2D.Double((i * tSize + tempImg.getX()) * scale, (j * tSize + tempImg.getY()) * scale, tSize * scale, tSize * scale);
                    rd = cd.getTile(tileArea);
                    BufferedImage temp = drawTile(rd);
                    drawRoute(temp, tileArea);
                    tiles.put(tileNr, temp);
                    grid[i][j] = tileNr++;
                    Rectangle2D tile = new Rectangle2D.Double(i * tSize - (tempView.getX() - tempImg.getX()), j * tSize - (tempView.getY() - tempImg.getY()), tSize, tSize);
                    g2.draw(tile);
                    int xOut = (int) (i * tSize - Math.round(tempView.getX() - tempImg.getX()));
                    int yOut = (int) (j * tSize - Math.round(tempView.getY() - tempImg.getY()));
                    g2.drawImage(temp, xOut, yOut, this);
                } else {
                    Rectangle2D tile = new Rectangle2D.Double(i * tSize - (tempView.getX() - tempImg.getX()), j * tSize - (tempView.getY() - tempImg.getY()), tSize, tSize);
                    g2.draw(tile);
                    int xOut = (int) (i * tSize - Math.round(tempView.getX() - tempImg.getX()));
                    int yOut = (int) (j * tSize - Math.round(tempView.getY() - tempImg.getY()));
                    g2.drawImage(tiles.get(grid[i][j]), xOut, yOut, this);
                }
                j++;
            }
            i++;
        }
    }

    private BufferedImage drawTile(List<Road> rds) {
        BufferedImage bimg = new BufferedImage((int) tSize, (int) tSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bimg.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        big.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        big.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        big.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        for (Road r : rds) {
            double x1, x2, y1, y2;
            NodeData n1 = r.getFn();
            NodeData n2 = r.getTn();
            x1 = ((n1.getX_COORD() - view.getMinX()) / scale) - (i * tSize - (tempView.getX() - tempImg.getX()));
            y1 = ((n1.getY_COORD() - view.getMinY()) / scale) - (j * tSize - (tempView.getY() - tempImg.getY()));
            x2 = ((n2.getX_COORD() - view.getMinX()) / scale) - (i * tSize - (tempView.getX() - tempImg.getX()));
            y2 = ((n2.getY_COORD() - view.getMinY()) / scale) - (j * tSize - (tempView.getY() - tempImg.getY()));
            //Road colering:
            switch (r.getEd().TYP) {
                case 1:
                    big.setColor(Color.red); //Highway
                    break;
                case 3:
                    big.setColor(Color.blue); //Main roads
                    break;
                case 8:
                    big.setColor(Color.green); //Path
                    break;
                default:
                    big.setColor(Color.black); //Other
                    break;
            }
            Line2D line = new Line2D.Double(x1, y1, x2, y2);
            big.draw(line);
            big.setColor(Color.black);
        }

        return bimg;

    }

    private void drawRoute(BufferedImage temp, Rectangle2D tileArea) {
        Graphics2D g2 = null;
        if (routeND != null) {
            ArrayList<Linked> tRoute = new ArrayList<>();
            g2 = temp.createGraphics();
            g2.setColor(Color.orange);
            g2.setStroke(new BasicStroke(2));
            for (Linked l : routeND) {
                Road r = l.getEdge();
                if (tileArea.contains(r.midX, r.midY)) {
                    double x1, x2, y1, y2;
                    NodeData n1 = r.getFn();
                    NodeData n2 = r.getTn();
                    x1 = ((n1.getX_COORD() - view.getMinX()) / scale) - (i * tSize - (tempView.getX() - tempImg.getX()));
                    y1 = ((n1.getY_COORD() - view.getMinY()) / scale) - (j * tSize - (tempView.getY() - tempImg.getY()));
                    x2 = ((n2.getX_COORD() - view.getMinX()) / scale) - (i * tSize - (tempView.getX() - tempImg.getX()));
                    y2 = ((n2.getY_COORD() - view.getMinY()) / scale) - (j * tSize - (tempView.getY() - tempImg.getY()));
                    Line2D line = new Line2D.Double(x1, y1, x2, y2);
                    g2.draw(line);
                } else {
                    tRoute.add(l);
                }
            }
            g2.dispose();
            routeND = tRoute;
        }
    }

    /**
     * Overrides paintComponent from JComponent, to also draw the map.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        calcScale(view);
        newGrid = scale != oldScale;
        drawMap(g2);
        if (dragbool) {                       //paints the dragzoom rectangle
            g2.draw(dragrect);
        }
    }

    /**
     * Overrides update from Observer pattern, to get the data from CurrentData
     * and repaint.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update() {
        rd.clear();
        view = cd.getView();
        repaint();
    }

    /**
     * Returns the current scale of the map.
     *
     * @return double scale.
     */
    public double getScale() {
        return scale;
    }

    public void setDragrect(Rectangle2D rect) {
        dragrect = rect;
    }

    public void setDragbool(boolean bool) {
        dragbool = bool;
    }

    public void updateRoute() {
        newGrid = true;
        repaint();
    }

    public void calcScale(Rectangle2D view) {
        oldScale = scale;
        double scaley = view.getHeight() / (double) this.getHeight();
        double scalex = view.getWidth() / (double) this.getWidth();
        if (scaley > scalex) {
            scale = view.getHeight() / (double) this.getHeight();
        } else {
            scale = view.getWidth() / (double) this.getWidth();
        }
        if (scale < 1) {
            scale = 1;
        }
    }

    @Override
    public boolean hasFocus() {
        return isFocus;
    }

    @Override
    public void focusGained(FocusEvent fe) {
        isFocus = true;
    }

    @Override
    public void focusLost(FocusEvent fe) {
        isFocus = false;
    }

    public void setFocus(boolean b) {
        isFocus = b;
    }
}
