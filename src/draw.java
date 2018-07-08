import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class draw extends JPanel implements ActionListener, FocusListener, MouseListener {

    private Graphics g;

    private ImageLoader iL;

    BufferedImage canvas;

    public draw(){
        addFocusListener(this);
        addMouseListener(this);
        iL = new ImageLoader(768,768);
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

        this.setBackground(new Color(224, 219, 208));

        g2.setColor(Color.RED);


        canvas = iL.updateImage();

        if(canvas == null){
            while(true);
        }

        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,768,768);
        g2.drawImage(canvas, null, null);



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
