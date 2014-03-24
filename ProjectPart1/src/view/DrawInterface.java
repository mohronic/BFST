/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

/**
 *
 * @author Adam Engsig (adae@itu.dk)
 */
public interface DrawInterface
{
    public void drawLine(double x1, double y1, double x2, double y2);
    public void drawRect(double x1, double y1, double width, double height);
    public void setRed();
    public void setBlue();
    public void setGreen();
    public void setBlack();
}
