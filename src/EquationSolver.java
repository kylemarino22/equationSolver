import java.util.ArrayList;

public class EquationSolver {
    //Box
    private static ComplexDouble origin = new ComplexDouble(0,0);
    private static double height = 3;
    private static double width = 3;
    private static int numSteps = 50;

    private static ArrayList<ComplexDouble> solveList = new ArrayList<>(); //imaginary numbers

    public static void loadBox(EquationObject equation){
        //left starting from bL going to tL (25 calculations)
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r, + origin.i + (i*height/numSteps));
            System.out.println(input);
            solveList.add((equation.evaluator(input)));

        }

        System.out.println("A");

        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + (i*width/numSteps),  origin.i + height);
            System.out.println(input);
            solveList.add((equation.evaluator(input)));

        }
        System.out.println("A");
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + width,  origin.i + height - (i*height/numSteps));
            System.out.println(input);
            solveList.add((equation.evaluator(input)));

        }
        System.out.println("A");
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + width - (i*width/numSteps),  origin.i);
            System.out.println(input);
            solveList.add((equation.evaluator(input)));

        }
        System.out.println("A");
    }

    public static void printSolveList(){
        for (ComplexDouble p : solveList) {
            System.out.println(p.toString());
        }
    }

    public static void plotRot(){
        for(int i = 0; i < solveList.size(); i++){
            Polar p = Polar.toPolar(solveList.get(i));
            System.out.println(i + "\t" + p.t);
        }
    }



}
