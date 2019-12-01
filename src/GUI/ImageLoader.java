package GUI;

import Evaluator.*;
import GUI.Events.NewEquationListener;
import Utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static GUI.Events.EventManager.addNewEquationListener;

public class ImageLoader implements NewEquationListener{


    BufferedImage calculated;

    private String equation = "(2.71828^(x*(0+1i)))+1";
    private ComplexDouble origin = new ComplexDouble(-4.01,-4.01);
    private double planeHeight = 8;
    private double planeWidth = 8;
    private int imgHeight;
    private int imgWidth;

    public EquationLoader EL = new EquationLoader();

    ArrayList<GraphicsTile> gtList = new ArrayList<>();


    public ImageLoader(int imgWidth, int imgHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        addNewEquationListener(this);
        setupImage();
    }

    private void setupImage () {
        //generate graphics tiles
        calculated = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);


        int boxHeight = imgHeight/4;
        int boxWidth = imgWidth/4;

        double numWidth = this.planeWidth/4;
        double numHeight = this.planeHeight/4;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                gtList.add(new GraphicsTile(new Pixel(j*boxWidth, i*boxHeight), boxHeight, boxWidth,
                        origin.r + numWidth * j, origin.i + numHeight * i,
                        numWidth, numHeight));
            }

        }

        //setup equation

        EquationLoader.setup(equation);
    }

    public BufferedImage updateImage(){

        for(int j = 0; j < 32; j++){
            //iterate through tiles
            //The last row of the pixel list won't have the max number of points because it is cutoff
            if (gtList.get(0).getPixelListSize() != 0) {
                for (int i = 0; i < 16; i++) {

                    Pixel p = gtList.get(i).getAvailable();
                    if (p == null) continue;

                    Pixel gP = gtList.get(i).globalPixel(p);
                    ComplexDouble cd = gtList.get(i).getComplexDouble(p);

                    Color temp = calcPixel(cd);
                    calculated.setRGB(gP.x, gP.y, temp.getRGB());
                }
            } else {
                return calculated;
            }
        }

        return calculated;
    }

    @Override
    public void setNewEquation(String equation) {
        this.equation = equation;
        gtList = new ArrayList<>();
        setupImage();
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
