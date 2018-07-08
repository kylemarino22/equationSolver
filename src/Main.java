import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

    /*equation gets inputted as a string
     "y = xˆ3 - 5x + 3"
     Gets Parsed into correct format (Parser Class)
     "y = xˆ3 - 5*x + 3"
     Gets compiled into an equation object (Compiler Class)
      - A list of steps for equation to be solved
      - Finds innermost parentheses
      - Works from right to left

     Class eqOb{

     Stack Overflow info on how to do:
     https://stackoverflow.com/questions/395816/function-pointers-delegates-in-java
     Hashmap of functions:
     a 6,x
     d c,3
     m c,4
     e 4,c

     functions:

     e (a,b)
     ln (a,b)
     a (a,b)
     s (a,b)
     m (a,b)
     d (a,b)
     sin (a,b)
     asin (a,b)
     cos (a,b)
     acos (a,b)
     tan (a,b)
     atan (a,b)
     }




        */
	// write your code here
//        EquationLoader.test();



        draw a = new draw();

        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                .getDefaultScreenDevice();
////        device.setFullScreenWindow(f);
//
//        device.setDisplayMode(new DisplayMode(2560, 1600, 32, 60));


        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();




        f.setSize((int) screenSize.getWidth(),(int) screenSize.getHeight());
        f.setVisible(true);
        f.add(a);
        f.setFocusable(true);






    }
}
