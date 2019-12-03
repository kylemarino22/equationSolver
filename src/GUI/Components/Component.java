package GUI.Components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static GUI.GraphicsUtils.CenterString.drawCenteredString;

public abstract class Component {

    public int x;
    public int y;
    public int height;
    public int width;
    public String text = "";

    public Color color = new Color (255,255,255);
    public Color textColor = new Color (0,0,0);
    public Font font = new Font("Sans Serif", Font.BOLD, 30);

    public int cornerRadius = 0;
    private int shadowSize = 0;
    private float borderSize = 0;
    private Color borderColor;
    private Stroke defaultStroke = new BasicStroke(1);

    public Component (int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(Graphics2D g) {
        Rectangle defaultRect = new Rectangle(x,y,width,height);
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderSize,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,10f));
        if (cornerRadius > 0) {
            RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, cornerRadius, cornerRadius);
            if (borderSize > 0) {
                g.draw(rect);
            }
            if (shadowSize > 0) {
                RoundRectangle2D shadow = new RoundRectangle2D.Double(
                        x+2,y+height-2, width - 4, shadowSize + 2, cornerRadius, cornerRadius);
                g.setColor(new Color(185,185,185,200));
                g.fill(shadow);
            }
            g.setColor(color);
            g.fill(rect);
        } else {
            if (borderSize > 0) {
                g.draw(defaultRect);
            }
            if (shadowSize > 0) {
                Rectangle shadow = new Rectangle(
                        x+2,y+height-2, width - 4, shadowSize + 2);
                g.setColor(new Color(185,185,185,200));
                g.fill(shadow);
            }
            g.setColor(color);
            g.fill(defaultRect);
        }
        g.setStroke(defaultStroke);
        g.setColor(textColor);
        g.setFont(font);
        drawCenteredString(g, text, defaultRect, font);
    }

    public void setRoundCorners (int radius) {
        cornerRadius = radius;
    }

    public void setShadowSize (int shadowSize) {
        this.shadowSize = shadowSize;
    }

    public void setText (String text) {
        this.text = text;
    }

    public void setColor (Color c) {
        color = c;
    }

    public void setBorderSize (float size) {
        borderSize = size;
    }

    public float getBorderSize () {
        return borderSize;
    }

    public void setBorderColor (Color c) {
        borderColor = c;
    }


    public  void setFontAttributes (int size, int style) {
        font = new Font("Sans Serif", style, size);
    }
}
