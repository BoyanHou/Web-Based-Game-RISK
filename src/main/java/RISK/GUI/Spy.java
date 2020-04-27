package RISK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spy extends JLabel implements ActionListener {
    private Timer timer = new Timer(2, this);
    private Point currentPos;
    private Point targetPos;
    private int v;
    private JLabel img;
    private JLabel num;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        num.setBounds(currentPos.getX(), currentPos.getY() - 25, 50, 30);
        img.setBounds(currentPos.getX(), currentPos.getY(), 50, 30);
        timer.start();
    }

    public Spy(String num, Point position, Point target, int v) {
        this.num = new JLabel(num);
        this.img = new JLabel("SPY");
        currentPos = position;
        targetPos = target;
        this.v = v;
    }

    public void setDestination(Point target) {
        targetPos = target;
    }

    public void actionPerformed(ActionEvent e) {
        int x = targetPos.getX() - currentPos.getX();
        int y = targetPos.getY() - currentPos.getY();
        double rate = y/x;
        int Vy = (int) Math.round(rate);
        int newX = currentPos.getX();
        int newY = currentPos.getY();
        if (Math.abs(currentPos.getX() - targetPos.getX()) < v) {
            newX = targetPos.getX();
        } else {
            newX += v;
        }
        if (Math.abs(currentPos.getY() - targetPos.getY()) < Vy) {
            newY = targetPos.getY();
        } else {
            newY += Vy;
        }
        currentPos = new Point(newX, newY);
        repaint();
    }
}
