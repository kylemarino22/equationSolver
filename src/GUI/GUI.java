package GUI;

import GUI.GraphicsUtils.*;
import GUI.GraphicsUtils.Button;
import GUI.GraphicsUtils.TextField;

import java.awt.image.BufferedImage;
import java.awt.*;

import static GUI.Events.EventManager.setNewEquation;

public class GUI {

    private static final int MARGIN_BOTTOM = 100;
    private static final int MARGIN_RIGHT  = 300;

    private static int scr_height;
    private static int scr_width;
    private static ImageLoader IL;
    private static TextField equationInputField;
    private static Button enterButton;
    private static TextBox settingText;

    public static void setup(int width, int height) {
        scr_height = height;
        scr_width  = width;
        IL = new ImageLoader(scr_width - MARGIN_RIGHT, scr_height - MARGIN_BOTTOM+2);

        equationInputField = new TextField(0,scr_height-MARGIN_BOTTOM, MARGIN_BOTTOM, scr_width-MARGIN_RIGHT);
        equationInputField.setDefaultText("Enter Equation");

        enterButton = new Button(scr_width - MARGIN_RIGHT - 120, scr_height-67, 35, 100 );
        enterButton.setText("Enter");
        enterButton.setDefaultColor(new Color (92, 191, 95));
        enterButton.setHighlightColor(new Color (110,191,110));
        enterButton.setRoundCorners(10);
        enterButton.setFontAttributes(15, Font.PLAIN);
        equationInputField.addSubmitButton(enterButton);
        enterButton.func = () -> {
            setNewEquation(equationInputField.text);
        };

        settingText = new TextBox(scr_width - MARGIN_RIGHT, 30, 30, MARGIN_RIGHT);
        settingText.setText("Settings");
        settingText.setFontAttributes(15,Font.PLAIN);

    }

    public static void render(Graphics2D g) throws NullCanvasException {
        g.setBackground(Color.decode("#F5F5F5"));
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,scr_width,scr_height);

        BufferedImage canvas;
        try {
             canvas = IL.updateImage();
             g.drawImage(canvas, null, null);

        } catch (NullPointerException e) {
            throw new NullCanvasException();
        }

        g.setColor(new Color(255,255,255)); //white
        g.fillRect(scr_width-MARGIN_RIGHT,0, MARGIN_RIGHT, scr_height);

        equationInputField.draw(g);
        enterButton.draw(g);
        settingText.draw(g);


        g.setColor(Color.decode("#B9B9B9"));
        g.setStroke(new BasicStroke(1));
        g.drawLine(0,scr_height-MARGIN_BOTTOM,scr_width-MARGIN_RIGHT, scr_height-MARGIN_BOTTOM);
        g.drawLine(scr_width-MARGIN_RIGHT,0,scr_width-MARGIN_RIGHT, scr_height);

    }
}
