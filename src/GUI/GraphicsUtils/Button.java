package GUI.GraphicsUtils;

import java.awt.*;

public class Button extends ClickableComponent{

    public clickFunction func;
    private Color highlightColor;
    private Color defaultColor;
    private boolean isClicked = false;

    public Button (int x, int y, int height, int width) {
        super(x,y,height,width);
    }

    public void setDefaultColor (Color c) {
        defaultColor = c;
    }

    public void setHighlightColor (Color c) {
        highlightColor = c;
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        isClicked = true;
        func.func();
    }

    @Override
    public void draw(Graphics2D g) {
        if (isClicked) {
            color = new Color (defaultColor.getRed() + 60, defaultColor.getGreen(), defaultColor.getBlue() + 60);
        }
        else if (highlighted && highlightColor != null) {
            color = highlightColor;
        }
        else {
            color = defaultColor;
        }
        super.draw(g);
    }

    @Override
    public void offClick() {
        isClicked = false;
    }

    @Override
    public void mouseReleased() {
        isClicked = false;
    }

    public interface clickFunction {
        void func();
    }
}