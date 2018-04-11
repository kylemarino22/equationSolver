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

    public String toString(){
        return "r: " + r + " i: " + i;
    }

}

