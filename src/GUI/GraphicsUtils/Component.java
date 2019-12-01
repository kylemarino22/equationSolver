package GUI.GraphicsUtils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static GUI.GraphicsUtils.CenterString.drawCenteredString;

public abstract class Component {

    public int x;
    public int y;
    public int height;
    public int width;
    public String text = "";
    public int cornerRadius = 0;

    public Color color = new Color (255,255,255);
    public Color textColor = new Color (0,0,0);
    public Font font = new Font("Sans Serif", Font.BOLD, 30);

    public Component (int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        Rectangle defaultRect = new Rectangle(x,y,width,height);
        if (cornerRadius > 0) {
            RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, cornerRadius, cornerRadius);
            g.fill(rect);
        } else {
            g.fill(defaultRect);
        }
        g.setColor(textColor);
        g.setFont(font);
        drawCenteredString(g, text, defaultRect, font);
    }

    public void setRoundCorners (int radius) {
        cornerRadius = radius;
    }

    public void setText (String text) {
        this.text = text;
    }

    public void setColor (Color c) {
        color = c;
    }

    public  void setFontAttributes (int size, int style) {
        font = new Font("Sans Serif", style, size);
    }
}
