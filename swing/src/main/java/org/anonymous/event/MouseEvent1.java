package org.anonymous.event;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * shadow
 * 2019/3/17 8:51
 * 找不到主类... 可能要配合其他组件使用
 */
public class MouseEvent1 extends Applet
        implements MouseListener, MouseMotionListener {
    String msg;
    int mouseX, mouseY; //coordinates of mouse

    public void init() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = 0;
        mouseY = 10;
        msg = "Mouse clicked.";
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        msg = "Down";
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        msg = "Up";
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseX = 0;
        mouseY = 10;
        msg = "Mouse entered.";
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = 0;
        mouseY = 10;
        msg = "Mouse exited";
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        msg = "*";
        showStatus("Dragging mouse at" + mouseX + "," + mouseY);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        showStatus("Moving mouse at" + e.getX() + "," + e.getY());
    }

    public void paint(Graphics g) {
        g.drawString(msg, mouseX, mouseY);
    }
}
