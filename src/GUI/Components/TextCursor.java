package GUI.Components;

import java.awt.*;

public class TextCursor {

    public int x;
    public int y;
    public Color color = new Color (0,0,0);

    private int pulseCounter = 0;
    private static final int PULSE_LEN = 10;
    public void draw (Graphics2D g) {
        if (pulseCounter < PULSE_LEN) {
            g.setColor(color);
            int height = g.getFontMetrics().getHeight();
            g.fillRect(x,y,1,height);
        }
        pulseCounter ++;
        if (pulseCounter > PULSE_LEN * 2) {
            pulseCounter = 0;
        }
    }

}
