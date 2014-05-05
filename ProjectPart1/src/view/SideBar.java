/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import FastestRoute.Linked;
import FastestRoute.MapRoute;
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

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public class SideBar
{

    private JPanel sideBar;
    private final static String newline = "\n";

    public SideBar()
    {
        sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(300, 0));
        JButton bSearch = new JButton("Search");

        final SearchLabel slTo = new SearchLabel(StartMap.allRoads, "To");
        KL klTo = new KL(slTo);
        slTo.addKeyListener(klTo);
        slTo.setColumns(25);
        final SearchLabel slFrom = new SearchLabel(StartMap.allRoads, "From");
        KL klFrom = new KL(slFrom);
        slFrom.addKeyListener(klFrom);
        slFrom.setColumns(25);

//        final JTextField tFrom = new HintTextField("From");
//        final JTextField tTo = new HintTextField("To");
//        tFrom.setColumns(25);
//        tTo.setColumns(25);
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
//        sideBar.add(tFrom);
//        sideBar.add(tTo);
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
                if (from.matches("[0-9]+") || to.matches("[0-9]+"))
                {
                    JOptionPane.showMessageDialog(sideBar, "Numbers are not allowed");
                } else if (from.trim().matches("") || to.trim().matches(""))
                {
                    JOptionPane.showMessageDialog(sideBar, "Nothing was typed in");
                } else
                {
                    ButtonModel routeAnswer = route.getSelection();
                    ButtonModel dataAnswer = data.getSelection();
                    if (routeAnswer.getActionCommand().equals("Fastest"))
                    {
                        ArrayList<Linked> route = MapRoute.getRoute();
                        int length = (int) route.get(0).getLength();
                        int time = (int) route.get(0).getDrivetime();
                        area.setText("Total længde: " + length + " " + "Total tid: " + time + newline);
                        String s = route.get(route.size() - 1).getRoad().getEd().VEJNAVN;
                        Linked l;
                        int tmpLength = 0;
                        for (int i = route.size() - 1; i > 0; i--)
                        {
                            l = route.get(i);
                            if (l.getEdge().getName().equals(route.get(i - 1).getEdge().getName()))
                            {
                                tmpLength = (int) (tmpLength + l.getEdge().length());
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

                                tmpLength = (int) (tmpLength + l.getEdge().length());
                                area.append("Tag " + turn + " " + l.getEdge().getName() + " - " + tmpLength + " m" + newline);
                                tmpLength = 0;
                                s = route.get(i - 1).getEdge().getName();
                            }
                        }

                    } else
                    {
                        System.out.println("Shortest");
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
        });

    }

    public JPanel getSideBar()
    {
        return sideBar;
    }
}
