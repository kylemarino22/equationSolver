package Utils;

import java.awt.*;

public class Pixel {
    public int x;
    public int y;
    public Color c;

    public Pixel(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return x + " " + y;
    }
}
