package GUI.Components;

import java.awt.*;

public class Button extends ClickableComponent {

    public clickFunction func;
    private Color highlightColor;
    private Color defaultColor;
    private boolean isClicked = false;

    public Button (int x, int y, int height, int width) {
        super(x,y,height,width);
        func = () -> {};
    }

    public void setDefaultColor (Color c) {
        defaultColor = c;
    }

    public Color getDefaultColor () { return defaultColor; }

    public void setHighlightColor (Color c) {
        highlightColor = c;
    }

    public Color getHighlightColor () { return highlightColor; }


    @Override
    public void onClick(int mouseX, int mouseY) {
        isClicked = true;
        func.func();
    }

    @Override
    public void draw(Graphics2D g) {
        colorManager();
        super.draw(g);
    }

    public void colorManager () {
        if (isClicked) {
            color = new Color (defaultColor.getRed() + 60, defaultColor.getGreen(), defaultColor.getBlue() + 60);
        }
        else if (highlighted && highlightColor != null) {
            color = highlightColor;
        }
        else {
            color = defaultColor;
        }
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