package MapPack;

import krakloader.NodeData;
import krakloader.EdgeData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class Canvas extends JComponent implements MouseListener, MouseMotionListener
{

    ArrayList<NodeData> n;
    ArrayList<EdgeData> e;
    public JFrame jf;

    Dimension current;
    double scale;
    Dimension d = new Dimension(700, 500);

    int mouseXStart;
    int mouseYStart;
    int mouseXEnd;
    int mouseYEnd;

    AffineTransform transformer;
    double originalX;
    double originalY;
    
    int currentX;
    int currentY;
    boolean mouseDragged;
    boolean mousePressed;

    public Canvas()
    {
        jf = new JFrame();

        current = d;
        jf.setPreferredSize(d);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();

        scale = 1.0;

        jf.addMouseListener(this);
        jf.addMouseMotionListener(this);
        mouseXStart = 1;
        mouseYStart = 1;
        mouseXEnd = 1000000;
        mouseYEnd = 1000000;

        transformer = new AffineTransform();

        originalX = transformer.getTranslateX();
        originalY = transformer.getTranslateY();

    }

    /**
     * Takes lists of NodeData and EdgeData and saves in the canvas class
     *
     * @param nt
     * @param et
     */
    public void store(ArrayList<NodeData> nt, ArrayList<EdgeData> et)
    {
        n = nt;
        e = et;

        repaint();
    }

    /**
     * Calculates the scale modifier used to set the correct size of the map
     */
    public void calScale()
    {
        current = jf.getSize();
        double c = current.getHeight();
        scale = (c / d.height);
    }

    /**
     * Paints the map. Called by repaint
     *
     * @param g
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.transform(transformer);

        calScale();
        
        for (EdgeData ed : e)
        {
            double x1, x2, y1, y2;
            x1 = (n.get(ed.FNODE - 1).X_COORD - 442254.35659) / 800;
            y1 = (-n.get(ed.FNODE - 1).Y_COORD + 6402050.98297) / 800;
            x2 = (n.get(ed.TNODE - 1).X_COORD - 442254.35659) / 800;
            y2 = (-n.get(ed.TNODE - 1).Y_COORD + 6402050.98297) / 800;
            //checks whether the whole map or a dragged rectangle should be showed.
            if (x1 * scale > mouseXStart && x2 * scale < mouseXEnd && y1 * scale > mouseYStart && y2 * scale < mouseYEnd)
            {
                Shape road = new Line2D.Double(x1 * scale, y1 * scale, x2 * scale, y2 * scale);

                //Road colering:
                switch (ed.TYP)
                {
                    case 1:
                        g2.setColor(Color.RED); //Highway
                        break;
                    case 3:
                        g2.setColor(Color.BLUE); //Main Roads
                        break;
                    case 8:
                        g2.setColor(Color.GREEN); //Path
                        break;
                    default:
                        g2.setColor(Color.BLACK); //Other
                        break;
                }

                g2.draw(road);
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        //s
    }

    /**
     * Sets the top left values of a dragged rectangle
     *
     * @param me
     */
    @Override
    public void mousePressed(MouseEvent me)
    {
        mouseXStart = me.getX();
        mouseYStart = me.getY();
        
        mousePressed = true;
        
        drawZoomArea(me);
    }

    /**
     * set the bottom left corner of a dragged rectangle
     *
     * @param me
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
        mouseXEnd = me.getX();
        mouseYEnd = me.getY();
        current = jf.getSize();
        int width = (int) current.getWidth();
        
        int tmp;

        if (mouseXStart > mouseXEnd)
        {
            tmp = mouseXStart;
            mouseXStart = mouseXEnd;
            mouseXEnd = tmp;
        }
        if (mouseYStart > mouseYEnd)
        {
            tmp = mouseYStart;
            mouseYStart = mouseYEnd;
            mouseYEnd = tmp;
        }

        if (mouseXStart == mouseXEnd || mouseYStart == mouseYEnd)
        {
            mouseXStart = 1;
            mouseYStart = 1;
            mouseXEnd = 1000000;
            mouseYEnd = 1000000;
            transformer.setToTranslation(originalX, originalY);
            transformer.scale(1, 1);
        } else
        {
            transformer.scale((width / (mouseXEnd - mouseXStart)), (width / (mouseXEnd - mouseXStart)));
            transformer.translate(-mouseXStart, -mouseYStart);
        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        //s
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        //s
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        currentX = e.getX();
        currentY = e.getY();        
        
        mouseDragged = true;
        
        drawZoomArea(e);
        
        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        currentX = e.getX();
        currentY = e.getY();
        
        mouseDragged = false;
        
        System.out.println("X-coordinate: " + e.getX() + ", Y-coodinate: " + e.getY());
        e.consume();//Stops the event when not in use, makes program run faster
    }
    
    public void drawZoomArea(MouseEvent e)
    {
        Graphics g = getGraphics();
        
        if(mousePressed)
        {
            g.drawRect(mouseXStart, mouseYStart, currentX - mouseXStart, currentY - mouseYStart);
        }
        
        repaint();
    }

}