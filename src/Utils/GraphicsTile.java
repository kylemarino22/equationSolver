package Utils;

import Utils.ComplexDouble;
import Utils.Pixel;

import java.util.ArrayList;
import java.util.Collections;

public class GraphicsTile {

    private Box tile;
    private ComplexDouble loc;
    private double width;
    private double height;

    private ArrayList<Pixel> pixelList = new ArrayList<>();


    public GraphicsTile(Pixel p, double height, double width, double originX, double originY, double numHeight, double numWidth){
        tile = new Box(p, width, height);
        this.loc = new ComplexDouble(originX, originY);
        this.height = numHeight;
        this.width = numWidth;

        genPixelList();
    }

    public void genPixelList(){

        for(int i = 0; i < tile.height; i++){
            for(int j = 0; j < tile.width; j++){
                pixelList.add(new Pixel(j,i));
            }
        }
        Collections.shuffle(pixelList);

    }

    public Pixel getAvailable(){

        if(pixelList.size() == 0){
            return null;
        }
        Pixel p = pixelList.get(pixelList.size()-1);
        pixelList.remove(pixelList.size()-1);
        return p;
    }

    public int getPixelListSize () {
        return pixelList.size();
    }

    public ComplexDouble getComplexDouble(Pixel p){
        //origin + pixelNum/TotalPixels * full width of box

        double r = loc.r + (double)p.x/tile.width * width;
        double i = loc.i + (double)p.y/tile.height * height;



        return new ComplexDouble(r, i);
    }

    public Pixel globalPixel(Pixel p){
        //converts an internal pixel to a global pixeloc.r
        return new Pixel(tile.pos.x + p.x, tile.pos.y + p.y);

    }




}
