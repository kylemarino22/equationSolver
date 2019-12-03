package GUI.Components;

import GUI.Components.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CheckBox extends Button {

    private static BufferedImage generalCheckImage;
    private static BufferedImage internalCheckImage;
    private static final Color transparent = new Color (0,0,0,0);

    private boolean checked = false;
    public clickFunction onToggle;
    public clickFunction offToggle;
    private int checkImageInset = 3;
    private float borderSize;
    private int constX, constY, constHeight, constWidth;

    public CheckBox (int x, int y, int height, int width) {
        super(x,y,height,width);
        constX = x;
        constY = y;
        constHeight = height;
        constWidth = width;
        setBorderSize(2);
        setBorderColor(Color.decode("#B9B9B9"));
        internalCheckImage = new BufferedImage(generalCheckImage.getWidth(),
                generalCheckImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        setCheckColor(Color.black);
        onToggle = () -> {};
        offToggle = () -> {};
    }

    @Override
    public void draw(Graphics2D g) {
        if (highlighted && getHighlightColor() != null) {
            g.setColor(getHighlightColor());
            g.fill(new Ellipse2D.Double(constX-constWidth/1.333,
                    constY-constHeight/1.333, constWidth*2.5, constHeight*2.5));

        }
        super.draw(g);
        if (checked) {
            g.drawImage(internalCheckImage, x+checkImageInset, y+checkImageInset,
                    x+width-checkImageInset,y+height-checkImageInset,
                    0,0,internalCheckImage.getWidth(),internalCheckImage.getHeight(), null);
        }
    }

    @Override
    public void colorManager() {
        if (checked) {
            color = this.getDefaultColor();
        } else {
            color = transparent;
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        checked = !checked;
        int borderChange = (int) this.borderSize/2;
        if (checked) {
            x -= borderChange;
            y -= borderChange;
            width += borderChange*2;
            height += borderChange*2;
            super.setBorderSize(0);
            onToggle.func();
        } else {
            x = constX;
            y = constY;
            width = constWidth;
            height = constHeight;
            super.setBorderSize(2);
            offToggle.func();
        }
        super.onClick(mouseX, mouseY);
    }

    public static void loadCheckImage(String filename) {
        try {
            generalCheckImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
        }
    }

    public void setCheckColor (Color c) {
        for (int i = 0; i < generalCheckImage.getWidth(); i++) {
            for (int j = 0; j < generalCheckImage.getHeight(); j++) {
                if (generalCheckImage.getRGB(i,j) != 0) {
                    internalCheckImage.setRGB(i,j,c.getRGB());
                }
            }
        }
    }

    @Override
    public void setBorderSize(float size) {
        super.setBorderSize(size);
        this.borderSize = size;
    }

    public void setCheckImageInset (int inset) {
        checkImageInset = inset;
    }

    public boolean isChecked () {
        return checked;
    }

    public interface clickFunction {
        void func();
    }

}
