package view;

import Route.DijkstraSP;
import Route.FastestRoad;
import Route.Linked;
import Route.ShortestRoad;
import AddressSearchEngine.SearchField;
import ctrl.KL;
import ctrl.StartMap;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Road;

/**
 *The sidebar from the gui which contains the route search propeties
 * 
 * @author Adam Engsig (adae@itu.dk)
 */
public class SideBar
{

    private JPanel sideBar;
    private final static String newline = "\n";
    private static ArrayList<Linked> roadRoute;
    private static SearchField slTo, slFrom;
    private static JLabel info;

    /**
     * Initializes the sidebar
     */
    public SideBar()
    {
        sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(300, 0));
        JButton bSearch = new JButton("Search");

        info = new JLabel();
        info.setFont(new Font("Plain", 0, 11));
        info.setText("Adress format: 'Streetname Number, ZipCode Cityname'");

        slTo = new SearchField(StartMap.allRoads, "From");
        KL klTo = new KL();
        klTo.setSearchField(slTo);
        slTo.addKeyListener(klTo);
        slTo.setColumns(25);

        slFrom = new SearchField(StartMap.allRoads, "To");
        KL klFrom = new KL();
        klFrom.setSearchField(slFrom);
        slFrom.addKeyListener(klFrom);
        slFrom.setColumns(25);

        JRadioButton rFastest = new JRadioButton("Fastest Road");
        JRadioButton rShortest = new JRadioButton("Shortest Road");
        rFastest.setSelected(true);
        rFastest.setActionCommand("Fastest");
        rShortest.setActionCommand("Shortest");

        final ButtonGroup route = new ButtonGroup();

        route.add(rFastest);
        route.add(rShortest);

        final JTextArea area = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);;
        area.setEditable(false);

        sideBar.add(info);
        sideBar.add(slTo);
        sideBar.add(slFrom);
        sideBar.add(rFastest);
        sideBar.add(rShortest);
        sideBar.add(scrollPane);
        sideBar.add(bSearch);

        bSearch.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent ae)
            {
                String from = slFrom.getText();
                String to = slTo.getText();
                if (from.matches("[0-9]+") || to.matches("[0-9]+")) //Searchfield contains illegal characters
                {
                    JOptionPane.showMessageDialog(sideBar, "Numbers are not allowed");
                } else if (from.trim().matches("") || to.trim().matches("")) //Searchfield contains illegal characters
                {
                    JOptionPane.showMessageDialog(sideBar, "Nothing was typed in");
                } else
                {
                    Road roadFrom = slFrom.searchRoad(from);
                    Road roadTo = slTo.searchRoad(to);

                    if (roadFrom == null || roadTo == null) // Road name does not exist
                    {
                        JOptionPane.showMessageDialog(sideBar, "Road was not found");
                    } else
                    {

                        ButtonModel routeAnswer = route.getSelection();
                        if (routeAnswer.getActionCommand().equals("Fastest")) // Fastest route
                        {
                            DijkstraSP SP = new FastestRoad(StartMap.allRoads);
                            roadRoute = SP.mapRoute(roadFrom, roadTo);
                            if (roadRoute == null)
                            {
                                JOptionPane.showMessageDialog(sideBar, "Route was not found");
                            } else
                            {
                                int length = (int) roadRoute.get(0).getLength();
                                int time = (int) Math.round(roadRoute.get(0).getDrivetime());
                                area.setText("Total længde: " + length + " m " + "Total tid: " + time + " min" + newline);

                                Linked l;
                                int tmpLength = 0;

                                for (int i = 0; i < roadRoute.size() - 1; i++)
                                {
                                    l = roadRoute.get(i);
                                    if (l.getEdge().getName().equals(roadRoute.get(i + 1).getEdge().getName())) //If the edge has the same name as the next, just add up the distance and only print road name once
                                    {
                                        tmpLength = (int) (tmpLength + l.getEdge().getLength());
                                    } else
                                    {
                                        String turn;
                                        switch (l.getTurn())
                                        {
                                            case RIGHT:
                                                turn = "højre";
                                                break;
                                            case LEFT:
                                                turn = "venstre";
                                                break;
                                            default:
                                                turn = " ";
                                                break;
                                        }

                                        tmpLength = (int) (tmpLength + l.getEdge().getLength());
                                        area.append(l.getEdge().getName() + ": " + "Tag " + turn + " om " + tmpLength + " m" + " mod" + newline);

                                        tmpLength = 0;
                                    }
                                    if (i == roadRoute.size() - 2) //Last edge in linked list, different text
                                    {
                                        Linked l2 = roadRoute.get(i + 1);
                                        area.append(l2.getEdge().getName() + " og destinationen er nået!");
                                    }
                                }
                            }

                        } else //Shortest road
                        {
                            DijkstraSP SP = new ShortestRoad(StartMap.allRoads);
                            roadRoute = SP.mapRoute(roadFrom, roadTo);
                            if (roadRoute == null)
                            {
                                JOptionPane.showMessageDialog(sideBar, "Route was not found");
                            } else
                            {
                                int length = (int) roadRoute.get(0).getLength();
                                int time = (int) Math.round(roadRoute.get(0).getDrivetime());
                                area.setText("Total længde: " + length + " m " + "Total tid: " + time + " min" + newline);

                                Linked l;
                                int tmpLength = 0;

                                for (int i = 0; i < roadRoute.size() - 1; i++)
                                {
                                    l = roadRoute.get(i);
                                    if (l.getEdge().getName().equals(roadRoute.get(i + 1).getEdge().getName())) //If the edge has the same name as the next, just add up the distance and only print road name once
                                    {
                                        tmpLength = (int) (tmpLength + l.getEdge().getLength());
                                    } else
                                    {
                                        String turn;
                                        switch (l.getTurn())
                                        {
                                            case RIGHT:
                                                turn = "højre";
                                                break;
                                            case LEFT:
                                                turn = "venstre";
                                                break;
                                            default:
                                                turn = " ";
                                                break;
                                        }

                                        tmpLength = (int) (tmpLength + l.getEdge().getLength());
                                        area.append(l.getEdge().getName() + ": " + "Tag " + turn + " om " + tmpLength + " m" + " mod" + newline);

                                        tmpLength = 0;
                                    }
                                    if (i == roadRoute.size() - 2) //Last edge in linked list, different text
                                    {
                                        Linked l2 = roadRoute.get(i + 1);
                                        area.append(l2.getEdge().getName() + " og destinationen er nået!");
                                    }
                                }
                            }
                        }
                    }
                }
                Canvas c = Canvas.getInstance(null);
                c.newGrid();
                c.repaint();
            }

        }
        );

    }

    /**
     * Returns the sidebar
     * 
     * @return JPanel sidebar
     */
    public JPanel getSideBar()
    {
        return sideBar;
    }

    /**
     * Returns the route from fastest/shortest road
     * 
     * @return ArrayList<Linked>
     */
    public static ArrayList<Linked> getRoute()
    {
        return roadRoute;
    }

    /**
     * SearchField
     * 
     * @return SearchField
     */
    public static SearchField getSlTo()
    {
        return slTo;
    }

    /**
     * SearchField
     * 
     * @return SearchField
     */
    public static SearchField getSlFrom()
    {
        return slFrom;
    }
}
