package Utils;

public class ComplexDouble {
    public double r;
    public double i;

    public ComplexDouble(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public static ComplexDouble add(ComplexDouble a, ComplexDouble b){
        return new ComplexDouble(a.r + b.r,a.i + b.i);
    }

    public static ComplexDouble sub(ComplexDouble a, ComplexDouble b){
        return new ComplexDouble(a.r - b.r,a.i - b.i);
    }

    public static ComplexDouble mult(ComplexDouble a, ComplexDouble b){
        return new ComplexDouble((a.r * b.r - a.i * b.i),(a.r * b.i + a.i * b.r));
    }

    public static ComplexDouble div(ComplexDouble a, ComplexDouble b){

        /*
        a/b

        a.r + a.i   b.r - b.i   (b.i *= -1; mult(a,b))
        --------- * --------- = ----------------------
        b.r + b.i   b.r - b.i   (b.r * b.r) - (b.i * b.i)
         */

        double denominator  = (b.r * b.r) + (b.i * b.i);
        b.i *= -1;
        ComplexDouble finalCD = mult(a,b);
        finalCD.r /= denominator;
        finalCD.i /= denominator;

        return finalCD;
    }

    public static ComplexDouble pwr(ComplexDouble a, ComplexDouble b){
        //http://mathworld.wolfram.com/ComplexExponentiation.html

        //setup
        double absq = (a.r * a.r + a.i * a.i);
        if(absq == 0){
            return new ComplexDouble(0, 0);
        }
        double arg = Math.atan2(a.i, a.r);
        double scalar = Math.pow(absq, b.r/2) * Math.exp(-b.i * arg);
        double temp = b.r*arg + 0.5*b.i*Math.log(absq);
        //calculation
        double finalR = Math.cos(temp) * scalar;
        double finalI = Math.sin(temp) * scalar;

        return new ComplexDouble(finalR, finalI);

    }

    public String toString(){
        return r +"\t"+ i;
    }

    public ComplexDouble clone(){
        return new ComplexDouble(r,i);
    }

}

