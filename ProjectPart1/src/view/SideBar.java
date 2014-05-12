package view;

import Route.DijkstraSP;
import Route.FastestRoad;
import Route.Linked;
import Route.MapRoute;
import Route.ShortestRoad;
import SearchEngine.SearchLabel;
import ctrl.KL;
import ctrl.StartMap;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Road;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class SideBar
{

    private JPanel sideBar;
    private final static String newline = "\n";
    private static ArrayList<Linked> roadRoute;
    private static SearchLabel slTo, slFrom;

    public SideBar()
    {
        sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(300, 0));
        JButton bSearch = new JButton("Search");

        slTo = new SearchLabel(StartMap.allRoads, "To");
        KL klTo = new KL();
        klTo.setSearchLabel(slTo);
        slTo.addKeyListener(klTo);
        slTo.setColumns(25);
        slFrom = new SearchLabel(StartMap.allRoads, "From");
        KL klFrom = new KL();
        klFrom.setSearchLabel(slFrom);
        slFrom.addKeyListener(klFrom);
        slFrom.setColumns(25);

        JRadioButton rFastest = new JRadioButton("Fastest Road");
        JRadioButton rShortest = new JRadioButton("Shortest Road");
        rFastest.setSelected(true);
        rFastest.setActionCommand("Fastest");
        rShortest.setActionCommand("Shortest");

        JRadioButton rKrak = new JRadioButton("Krak Kortdata");
        JRadioButton rOSM = new JRadioButton("Open Streetmaps");
        rKrak.setSelected(true);
        rKrak.setActionCommand("Krak");
        rOSM.setActionCommand("OSM");

        final ButtonGroup route = new ButtonGroup();
        final ButtonGroup data = new ButtonGroup();

        route.add(rFastest);
        route.add(rShortest);
        data.add(rOSM);
        data.add(rKrak);

        final JTextArea area = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);;
        area.setEditable(false);

        sideBar.add(slTo);
        sideBar.add(slFrom);
        sideBar.add(rFastest);
        sideBar.add(rShortest);
        sideBar.add(rKrak);
        sideBar.add(rOSM);
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
                    Road roadFrom = slFrom.checkRoadName(from);
                    Road roadTo = slTo.checkRoadName(to);
                    if (roadFrom == null || roadTo == null) // Road name does not exist
                    {
                        JOptionPane.showMessageDialog(sideBar, "Road was not found");
                    } else
                    {

                        ButtonModel routeAnswer = route.getSelection();
                        ButtonModel dataAnswer = data.getSelection();
                        if (routeAnswer.getActionCommand().equals("Fastest")) // Fastest route
                        {
                            DijkstraSP SP = new FastestRoad(StartMap.allRoads);
                            roadRoute = SP.mapRoute(roadFrom, roadTo);

                            int length = (int) roadRoute.get(0).getLength();
                            int time = (int) roadRoute.get(0).getDrivetime();
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

                        } else //Shortest road
                        {
                            DijkstraSP SP = new ShortestRoad(StartMap.allRoads);
                            roadRoute = SP.mapRoute(roadFrom, roadTo);

                            int length = (int) roadRoute.get(0).getLength();
                            int time = (int) roadRoute.get(0).getDrivetime();
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

                        if (dataAnswer.getActionCommand().equals("OSM"))
                        {
                            System.out.println("Open Street Maps");
                        } else
                        {
                            System.out.println("Krak");
                        }
                    }
                }
            }

        }
        );

    }

    public JPanel getSideBar()
    {
        return sideBar;
    }

    public static ArrayList<Linked> getRoute()
    {
        return roadRoute;

    }

    public static SearchLabel getSlTo()
    {
        return slTo;
    }

    public static SearchLabel getSlFrom()
    {
        return slFrom;
    }
    
    
}
