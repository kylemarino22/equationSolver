package GUI;

import GUI.GraphicsUtils.GUIKeyListener;
import GUI.GraphicsUtils.GUIMouseListener;
import GUI.GraphicsUtils.NullCanvasException;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DisplayManager extends JPanel implements ActionListener, FocusListener, MouseListener {

    private Graphics g;


    BufferedImage canvas;

    public DisplayManager(){
        addFocusListener(this);
        addMouseListener(this);

        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        int scrW = env.getMaximumWindowBounds().width;
        int scrH = env.getMaximumWindowBounds().height;


        f.setSize(scrW,scrH);
        f.add(this);
        f.setFocusable(true);
        f.setVisible(true);
        GUIMouseListener mouselisten = new GUIMouseListener();
        GUIKeyListener keyListen = new GUIKeyListener();
        addMouseListener (mouselisten);
        addMouseMotionListener (mouselisten);
        addKeyListener (keyListen);

        int contentSizeW = f.getContentPane().getSize().width;
        int contentSizeH = f.getContentPane().getSize().height;


        GUI.setup(contentSizeW,contentSizeH);

    }

    public void mousePressed(MouseEvent evt) {
        // Request that the input focus be given to the
        // canvas when the user clicks on the applet.
        requestFocus();
    }

    public void focusGained(FocusEvent evt) {
        // The applet now has the input focus.

//        while(true);
        repaint();  // redraw with cyan border
    }


    public void focusLost(FocusEvent evt) {
        // The applet has now lost the input focus.
        System.out.println("focus lost");
        repaint();  // redraw without cyan border
    }

    public void keyTyped(KeyEvent evt){ }
    public void mouseEntered(MouseEvent evt) { }  // Required by the
    public void mouseExited(MouseEvent evt) { }   //    MouseListener
    public void mouseReleased(MouseEvent evt) { } //       interface.
    public void mouseClicked(MouseEvent evt) { }


    Timer tm = new Timer(25, this);


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;

        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            GUI.render(g2);
        } catch (NullCanvasException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        tm.start();


    }

    public void actionPerformed(ActionEvent e){
        //main loop
//        p1.get(0).center.y = x;
//        p1.get(0).angle.setPitch(x);
//        p1.get(0).angle.setYaw(x);




        repaint();

    }

}
