package GUI.GraphicsUtils;

import GUI.Components.ClickableComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GUIMouseListener implements MouseListener, MouseMotionListener {
    private static ArrayList<ClickableComponent> componentList = new ArrayList<>();
    private int mouseX;
    private int mouseY;

    public static void attachMouseComponent (ClickableComponent c) {
        componentList.add(0,c);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        //Click only one element, prioritizing the newest elements (Ones on top)
        boolean elementClicked = false;
        for (ClickableComponent c: componentList) {
            if (c.highlighted && !elementClicked) {
                c.onClick(mouseX, mouseY);
                elementClicked = true;
            } else {
                c.offClick();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ClickableComponent c: componentList) {
            c.mouseReleased();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        for (ClickableComponent c : componentList) {

            if (e.getX() > c.x && e.getX() < (c.x+c.width) &&
                    e.getY() > c.y && e.getY() < (c.y+c.height)) {
                c.highlighted = true;
            }
            else {
                c.highlighted = false;
            }
        }
    }
}
