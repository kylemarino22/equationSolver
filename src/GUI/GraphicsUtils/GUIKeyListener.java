package GUI.GraphicsUtils;

import javax.xml.soap.Text;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUIKeyListener implements KeyListener {

    private static ArrayList<TextField> fieldList = new ArrayList<>();
    private static final char[] validChars = {'+', '-', '*', '/', '^', '@', '(', ')'};

    public static void attachTextField (TextField f) {
        fieldList.add(f);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (TextField f: fieldList) {
            if (f.editing) {
                f.onKeyPress(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (TextField f: fieldList) {
            if (f.editing) {
                f.onKeyRelease(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (Character.isLetterOrDigit(e.getKeyChar()) | contains(validChars, e.getKeyChar())) {
            for (TextField f : fieldList) {
                if (f.editing) {
                    f.onKeyTyped(e.getKeyChar());
                }
            }
        }
    }

    private boolean contains (char[] arr, char c) {
        for (char cn : arr) {
            if (c == cn) return true;
        }
        return false;
    }
}
