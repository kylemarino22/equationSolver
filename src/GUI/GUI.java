package GUI;

import GUI.Components.CheckBox;
import GUI.Components.TextBox;
import GUI.GraphicsUtils.*;
import GUI.Components.Button;
import GUI.Components.TextField;
import com.sun.corba.se.impl.orbutil.graph.Graph;

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
    private static TextBox xRangeText;
    private static TextField minXRange;
    private static TextField maxXRange;
    private static TextBox yRangeText;
    private static TextField minYRange;
    private static TextField maxYRange;
    private static CheckBox gridToggle;
    private static TextBox gridToggleText;

    public static void setup(int width, int height) {
        scr_height = height;
        scr_width  = width;
        IL = new ImageLoader(scr_width - MARGIN_RIGHT, scr_height - MARGIN_BOTTOM);
        CheckBox.loadCheckImage("res/check.png");

        equationInputField = new TextField(0,scr_height-MARGIN_BOTTOM, MARGIN_BOTTOM, scr_width-MARGIN_RIGHT);
        equationInputField.setDefaultText("Enter Equation");

        enterButton = new Button(scr_width-MARGIN_RIGHT-120, scr_height-67, 35, 100 );
        enterButton.setText("Enter");
        enterButton.setDefaultColor(new Color (92, 191, 95));
        enterButton.setHighlightColor(new Color (110,191,110));
        enterButton.setRoundCorners(10);
        enterButton.setFontAttributes(15, Font.PLAIN);
        enterButton.setShadowSize(1);
        equationInputField.addSubmitButton(enterButton);
        enterButton.func = () -> {
            ImageLoader.planeXmin = Double.parseDouble(minXRange.text);
            ImageLoader.planeXmax = Double.parseDouble(maxXRange.text);
            ImageLoader.planeYmin = Double.parseDouble(minYRange.text);
            ImageLoader.planeYmax = Double.parseDouble(maxYRange.text);
            setNewEquation(equationInputField.text);
        };

        settingText = new TextBox(scr_width-MARGIN_RIGHT, 30, 30, MARGIN_RIGHT);
        settingText.setText("Settings");
        settingText.setFontAttributes(15,Font.PLAIN);

        xRangeText = new TextBox(scr_width-MARGIN_RIGHT, 60, 20, MARGIN_RIGHT);
        xRangeText.setText("Real-Axis:                 to             ");
        xRangeText.setFontAttributes(10,Font.PLAIN);

        minXRange = new TextField(scr_width - 170, 60, 20, 40);
        minXRange.setDefaultText("-4");
        minXRange.setShadowSize(1);
        minXRange.setFontAttributes(10,Font.PLAIN);

        maxXRange = new TextField(scr_width - 110, 60, 20, 40);
        maxXRange.setDefaultText("4");
        maxXRange.setShadowSize(1);
        maxXRange.setFontAttributes(10,Font.PLAIN);

        yRangeText = new TextBox(scr_width- MARGIN_RIGHT, 90, 20, MARGIN_RIGHT-30);
        yRangeText.setText("Imaginary-Axis:                 to             ");
        yRangeText.setFontAttributes(10,Font.PLAIN);

        minYRange = new TextField(scr_width - 170, 90, 20, 40);
        minYRange.setDefaultText("-4");
        minYRange.setShadowSize(1);
        minYRange.setFontAttributes(10,Font.PLAIN);

        maxYRange = new TextField(scr_width - 110, 90, 20, 40);
        maxYRange.setDefaultText("4");
        maxYRange.setShadowSize(1);
        maxYRange.setFontAttributes(10,Font.PLAIN);

        gridToggleText = new TextBox(scr_width-MARGIN_RIGHT+63, 130, 15, 100);
        gridToggleText.setText("Grid:");
        gridToggleText.setFontAttributes(10,Font.PLAIN);

        gridToggle = new CheckBox(scr_width - 162, 130, 15, 15);
        gridToggle.setRoundCorners(5);
        gridToggle.setCheckImageInset(2);
        gridToggle.setDefaultColor(new Color (45, 177, 207));
        gridToggle.setHighlightColor(new Color (45, 177, 207,255/5));
        gridToggle.setCheckColor(Color.white);
    }

    public static void render(Graphics2D g) throws NullCanvasException {
        g.setBackground(Color.decode("#F5F5F5"));
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,scr_width,scr_height);

        BufferedImage canvas;
        try {
             canvas = IL.updateImage();
             g.drawImage(canvas,0,0, null);

        } catch (NullPointerException e) {

        }

        g.setColor(new Color(255,255,255)); //white
        g.fillRect(scr_width-MARGIN_RIGHT,0, MARGIN_RIGHT, scr_height);

        equationInputField.draw(g);
        enterButton.draw(g);
        settingText.draw(g);
        xRangeText.draw(g);
        minXRange.draw(g);
        maxXRange.draw(g);
        yRangeText.draw(g);
        minYRange.draw(g);
        maxYRange.draw(g);
        gridToggleText.draw(g);
        gridToggle.draw(g);

        if (gridToggle.isChecked()) {
            drawGridLines(g);
        }
        g.setColor(Color.decode("#B9B9B9"));

//        g.setStroke(new BasicStroke(1));
        g.drawLine(0,scr_height-MARGIN_BOTTOM,scr_width-MARGIN_RIGHT, scr_height-MARGIN_BOTTOM);
        g.drawLine(scr_width-MARGIN_RIGHT,0,scr_width-MARGIN_RIGHT, scr_height);

    }

    private static void drawGridLines (Graphics2D g) {
        int imgWidth = scr_width-MARGIN_RIGHT;
        int imgHeight = scr_height-MARGIN_BOTTOM;
        g.setStroke(new BasicStroke(1));
        g.setColor(new Color(255,255,255,150));
        for (int i = 0; imgWidth/2 + i*imgWidth/10 < imgWidth; i++) {
            g.drawLine(imgWidth/2 + i*imgWidth/10,0,imgWidth/2 + i*imgWidth/10,imgHeight);
        }
        for (int i = 1; imgWidth/2 - i*imgWidth/10 > 0; i++) {
            g.drawLine(imgWidth/2 - i*imgWidth/10,0,imgWidth/2 - i*imgWidth/10,imgHeight);
        }
        for (int i = 0; imgHeight/2 + i*imgHeight/10 < imgHeight; i++) {
            g.drawLine(0,imgHeight/2 + i*imgHeight/10,imgWidth, imgHeight/2 + i*imgHeight/10);
        }
        for (int i = 1; imgHeight/2 - i*imgHeight/10 > 0; i++) {
            g.drawLine(0,imgHeight/2 - i*imgHeight/10,imgWidth, imgHeight/2 - i*imgHeight/10);
        }
    }
}
