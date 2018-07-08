import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageLoader {


    BufferedImage calculated;
    BufferedImage finalImage;

    private String mainString = "(x^2)+(4*x)+5";
    private ComplexDouble origin = new ComplexDouble(-4.01,-4.01);
    private double height = 8;
    private double width = 8;

    private EquationLoader EL = new EquationLoader();

    ArrayList<GraphicsTile> gtList = new ArrayList<>();


    public ImageLoader(int width, int height) {
        calculated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //generate graphics tiles

        int boxHeight = height/4;
        int boxWidth = width/4;

        double numWidth = this.width/4;
        double numHeight = this.height/4;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                gtList.add(new GraphicsTile(new Pixel(j*boxWidth, i*boxHeight), boxHeight, boxWidth,
                    origin.r + numWidth * j, origin.i + numHeight * i,
                        numWidth, numHeight));
            }

        }

        //setup equation

        EquationLoader.setup(mainString);
    }

    public BufferedImage updateImage(){

        //number of pixels per square before smoothing

        boolean isNull = false;
        for(int j = 0; j < 32; j++){
            //iterate through tiles
            for(int i = 0; i < 16; i++){
                Pixel p = gtList.get(i).getAvailable();

                if(p == null){
                    isNull = true;
                    break;
                }

                Pixel gP = gtList.get(i).globalPixel(p);
                ComplexDouble cd = gtList.get(i).getComplexDouble(p);


                Color temp = calcPixel(cd);

                calculated.setRGB(gP.x, gP.y, temp.getRGB());
            }
        }

        if(isNull){
            return null;
        }

        return calculated;
        //smoothing stuff

    }

    private Color calcPixel(ComplexDouble cd){

        ComplexDouble result = EquationLoader.equation.evaluator(cd);

        Polar p = Polar.toPolar(result);

        double a = -255/(Math.pow((p.r+1), 1)) +255;

//        Color b = new Color(1,2,4,123);
//        return new Color(255,0,0,200);

        Color temp = thetaRGB(p.t);
        return new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), (int)a);
    }

    private Color thetaRGB(double theta){

        int rgb;
        theta = (2* theta+ Math.PI) * 180/ Math.PI;

//        System.out.println(theta);

        if(theta > 0 && theta < 60){
            rgb =(int) (theta / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (rgb, 255, 0);
        }

        else if(theta > 60 && theta < 120){
            rgb =(int) ((theta - 60) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255, 255 - rgb, 0);
        }

        else if(theta > 120 && theta < 180){
            rgb =(int) ((theta - 120) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }

            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255, 0, rgb);
        }

        else if(theta > 180 && theta < 240){
            rgb =(int) ((theta - 180) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255 - rgb, 0, 255);
        }

        else if(theta > 240 && theta < 300){
            rgb =(int) ((theta - 240) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (0, rgb, 255);
        }
        else{
            rgb =(int) ((theta - 300) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (0, 255, 255-rgb);
        }
    }

}
