package Utils;

public class Polar {

    public double r;
    public double t;

    public Polar(double r, double t) {
        this.r = r;
        this.t = t;
    }

    public static Polar toPolar(ComplexDouble c){
        double t = Math.atan(c.i/ c.r);
        // _____________
        //V(x*x) + (y*y)
        double r = Math.pow((c.i * c.i+ c.r * c.r), 0.5);
        return new Polar(r,t);
    }

    public static double radius(ComplexDouble c){
        double r = Math.pow((c.i * c.i+ c.r * c.r), 0.5);
        return r;

    }

    public String toString(){
        return "r: " + r + " t: " + t;
    }
}
