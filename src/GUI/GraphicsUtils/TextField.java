package GUI.GraphicsUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

import static GUI.GraphicsUtils.GUIKeyListener.attachTextField;

public class TextField extends ClickableComponent{

    public boolean editing = false;
    public TextCursor cursor = new TextCursor();
    public int cursorIndex = 0;
    private FontMetrics fm;
    private String defaultText = "";

    private Button submitButton;


    public TextField (int x, int y, int height, int width) {
        super(x,y,height,width);
        attachTextField(this);
        cursor.color = textColor;
    }

    public void setDefaultText (String defaultText) {
        this.defaultText = defaultText;
        text = defaultText;
    }

    public void addSubmitButton (Button submitButton) {
        this.submitButton = submitButton;
    }

    public void onKeyPress (int keyCode) {
        if (keyCode == 8) {
            String newText = text.substring(0,cursorIndex-1);
            newText += text.substring(cursorIndex);
            text = newText;
            cursorIndex--;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (cursorIndex > 0) cursorIndex--;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (cursorIndex < text.length()) cursorIndex++;
        } else if (keyCode == KeyEvent.VK_UP) {
            cursorIndex = 0;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            cursorIndex = text.length();
        } else if (keyCode == KeyEvent.VK_ENTER) {
            submitButton.onClick(0,0);
        }
        calcCursorPos(cursorIndex);

    }

    public void onKeyRelease (int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER && submitButton != null) {
            submitButton.mouseReleased();
            offClick();
        }
    }

    public void onKeyTyped (char c) {
        String newText = text.substring(0,cursorIndex);
        newText += c;
        newText += text.substring(cursorIndex);
        text = newText;
        cursorIndex++;
        calcCursorPos(cursorIndex);

    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (!editing) {
            if (text.equals(defaultText)) {
                text = "";
            }
        }
        editing = true;

        cursorIndex = calcCursorIndex(mouseX);
        calcCursorPos(cursorIndex);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        this.fm = g.getFontMetrics();
        if (editing) {
            cursor.draw(g);
        }
    }

    @Override
    public void offClick() {
        if (text.equals("")) {
            text = defaultText;
        }
        editing = false;
    }

    @Override
    public void mouseReleased() {

    }

    int calcCursorIndex (int mouseX) {
        int stringWidth = fm.stringWidth(text);
        if (text.length() == 0) return 0;
        if (mouseX < x + (width - stringWidth + fm.stringWidth(text.substring(0,1)))/2 ) {
            return 0;
        } else if (mouseX > x + (width+ stringWidth - fm.stringWidth(text.substring(text.length() - 1,text.length())))/2 ) {
            return text.length();
        } else {
            int stringStart = x + (width - stringWidth)/2;
            //n-1 because after the last character we don't care
            for (int i = 1; i < text.length(); i++) {
                //check if the mouse is greater than the middle of two characters
                if (mouseX < stringStart + (fm.stringWidth(text.substring(0,i)) + fm.stringWidth(text.substring(0,i+1)))/2) {
                    return i;
                }
            }
        }
        return -1;
    }
    void calcCursorPos (int cursorIndex) {
        int stringWidth = fm.stringWidth(text);
        int stringStart = x + (width - stringWidth)/2;
        cursor.x = stringStart + fm.stringWidth(text.substring(0,cursorIndex));
        cursor.y = y + ((height - fm.getHeight()) / 2);
    }
}