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
//    BufferedImage finalImage;

    private String equation = "(2.71828^(x*(0+1i)))+1"; //(2.71828^(x*(0+1i)))+1
    private int imgHeight;
    private int imgWidth;

    public static double planeXmin = -4;
    public static double planeXmax = 4;
    public static double planeYmin = -4;
    public static double planeYmax = 4;

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
        System.out.println(calculated.getRGB(0,0));
//        finalImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

        int constBoxHeight = imgHeight/4;
        int constBoxWidth = imgWidth/4;
        int boxHeight;
        int boxWidth;

        double boxGridWidth = (planeXmax - planeXmin)/4;
        double boxGridHeight = (planeYmax - planeYmin)/4;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                //Fill up the last boxes to the image size
                if (j==3) {
                    boxWidth = imgWidth/4 + imgWidth % 4;
                } else {
                    boxWidth = imgWidth/4;
                }
                if (i==3) {
                    boxHeight = imgHeight/4 + imgHeight % 4;
                } else {
                    boxHeight = imgHeight/4;
                }
                gtList.add(new GraphicsTile(new Pixel(j*constBoxWidth, i*constBoxHeight), boxHeight, boxWidth,
                        planeXmin+ boxGridWidth * j, planeYmin + boxGridHeight * i,
                        boxGridHeight, boxGridWidth));
            }

        }

        //setup equation

        EquationLoader.setup(equation);
    }

    public BufferedImage updateImage(){

        for(int j = 0; j < 32; j++){
            //iterate through tiles
            //15 will have the most pixels because of int division errors of the other graphicsTiles
            if (gtList.get(15).getPixelListSize() != 0) {
                for (int i = 0; i < 16; i++) {

                    Pixel p = gtList.get(i).getAvailable();
                    if (p == null) continue;

                    Pixel gP = gtList.get(i).globalPixel(p);
                    ComplexDouble cd = gtList.get(i).getComplexDouble(p);

                    Color temp = calcPixel(cd);
                    //flip image vertically

                    try {
                        calculated.setRGB(gP.x, imgHeight-gP.y-1, temp.getRGB());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("a");
                    }
                }
            } else {
                return calculated;
            }
        }

//        smoothImage(calculated);

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

        if(theta >= 0 && theta < 60){
            rgb =(int) (theta / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (rgb, 255, 0);
        }

        else if(theta >= 60 && theta < 120){
            rgb =(int) ((theta - 60) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255, 255 - rgb, 0);
        }

        else if(theta >= 120 && theta < 180){
            rgb =(int) ((theta - 120) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }

            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255, 0, rgb);
        }

        else if(theta >= 180 && theta < 240){
            rgb =(int) ((theta - 180) / 60 * 255);
            if(rgb > 255){
                rgb = 255;
            }
            if(rgb < 0){
                rgb = 0;
            }
            return new Color (255 - rgb, 0, 255);
        }

        else if(theta >= 240 && theta < 300){
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

    //This runs too slow
//    //outputs into finalImage
//    void smoothImage (BufferedImage img) {
//        //i is row, j is col
//
//        for (int i = 0; i < finalImage.getHeight(); i++) {
//            for (int j = 0; j < finalImage.getWidth(); j++) {
//                if (img.getRGB(j,i) != 0) {
//                    finalImage.setRGB(j,i,img.getRGB(j,i));
//                    continue;
//                }
//                //9x9 grid around each pixel.
//                int numPixelAverage = 0;
//                int r = 0, g = 0, b = 0, a = 0;
//                for (int k = Integer.max(0, i-4); k < Integer.min(finalImage.getHeight(), i+4); k++) {
//                    for (int l = Integer.max(0,j-4); l < Integer.min(finalImage.getWidth(), j+4); l++) {
//
//                        if (img.getRGB(l,k) == 0) {
//                            continue;
//                        }
//                        Color rgb = new Color(img.getRGB(l,k));
//                        r += rgb.getRed();
//                        g += rgb.getGreen();
//                        b += rgb.getBlue();
//                        a += rgb.getAlpha();
//                        numPixelAverage++;
//                    }
//                }
//                if (numPixelAverage == 0) {
//                    finalImage.setRGB(j,i, 0);
//                    continue;
//                }
//                r /= numPixelAverage;
//                g /= numPixelAverage;
//                b /= numPixelAverage;
//                a /= numPixelAverage;
//
//                finalImage.setRGB(j,i, new Color (r,g,b,a).getRGB());
//
//            }
//        }
//    }

}
