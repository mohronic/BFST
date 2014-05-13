
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class PopUpExperiment
{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout());
    JTextArea textArea = new JTextArea();
    JTextArea textArea2 = new JTextArea();
    JPopupMenu popupMenu = new JPopupMenu();
    PopUpMenuListener puml = new PopUpMenuListener();
    JLabel label = new JLabel("EmptySpace");
    
    FocusListener fl = new FocusListener() {

        @Override
        public void focusGained(FocusEvent e)
        {
            createPopUpMenu();
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    

    
    public PopUpExperiment()
    {
        Dimension dimension = new Dimension(200, 200);
        frame.setMinimumSize(dimension);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PopUpExperiment");
        
        textArea.setSize(100, 100);
        textArea.setText("Test");
        textArea.addFocusListener(fl);
        
        textArea2.setSize(100, 100);
        textArea2.setText("Another Text Area");
        
        panel.add(textArea);
        panel.add(label);
        
        
        popupMenu.addPopupMenuListener(puml);
        popupMenu.setSize(textArea.getWidth(), textArea.getHeight());
        popupMenu.setPopupSize(100, 200);
        popupMenu.setLocation(textArea.getX(), textArea.getY() + textArea.getHeight());
        popupMenu.add("Test2");
        
        textArea.add(popupMenu);
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void createPopUpMenu()
    {
        popupMenu.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        PopUpExperiment pue = new PopUpExperiment();
    }
    
}
