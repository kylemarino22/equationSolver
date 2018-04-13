import java.lang.reflect.Array;
import java.util.ArrayList;

public class EquationSolver {


    private class solveList{
        public ArrayList<ComplexDouble> sList = new ArrayList<>(); //imaginary numbers
        public ComplexDouble origin = new ComplexDouble(0,0 );
        public double height;
        public double width;

        public solveList(ComplexDouble origin, double height, double width) {
//            this.sList = sList;
            this.origin = origin;
            this.height = height;
            this.width = width;
        }
    }



    //Box
    private static ComplexDouble origin = new ComplexDouble(-3,-3);
    private static double height = 6;
    private static double width = 6;
    private static int numSteps = 30;


//    private static ArrayList<ComplexDouble> solveList = new ArrayList<>(); //imaginary numbers

    private solveList originalSolveList = new solveList(origin, height, width);
    private static ArrayList<Polar> polarList = new ArrayList<>(); //imaginary numbers




    public void solve(EquationObject equation){
        //loadBox
        originalSolveList.sList = loadBox(equation, origin, height, width);
        //if is winding
        if(!windingValid(originalSolveList.sList)){
            return;
        }

        ArrayList<solveList> solutionList = new ArrayList<>();
        solutionList.add(originalSolveList);

        boolean cutVert = true;

        //refine solutionlist --> new solutionList

        for(int i = 0; i <40; i++){
            refineAnswers(solutionList, cutVert, equation);
            cutVert = !cutVert;
        }
//        printSolveList(solutionList.get(0).sList);

        System.out.println("\n");
        for(int i = 0; i < solutionList.size(); i++){
            System.out.println(centerBox(solutionList.get(i).origin, solutionList.get(i).height, solutionList.get(i).width));
        }


    }

    //

    private void refineAnswers(ArrayList<solveList> solutionList, boolean cut, EquationObject equation){
        int size = solutionList.size();
        for(int i = 0; i < size; i++){
            //create two solution boxes
            if(cut){
                //vertical cut
                solutionList.get(i).width /= 2;


                solveList solutionA = new solveList(
                        new ComplexDouble(
                                solutionList.get(i).origin.r + solutionList.get(i).width,
                                solutionList.get(i).origin.i),
                        solutionList.get(i).height,
                        solutionList.get(i).width);

                solveList solutionB = new solveList(
                        new ComplexDouble(
                                solutionList.get(i).origin.r,
                                solutionList.get(i).origin.i),
                        solutionList.get(i).height,
                        solutionList.get(i).width);


                //copy into new arrays
                System.out.println("here");
                System.out.println(i);
//                for(int j = 0; j < solutionList.get(i).sList.size(); j++){
//                    solutionA.sList.add(solutionList.get(i).sList.get(j).clone());
//                    solutionB.sList.add(solutionList.get(i).sList.get(j).clone());
//                }


                /*
                 _______________
                |       |       |
                |       |       |
                |   B   |   A   |
                |       |       |
                |_______|_______|
                 */

//                for(int j = 0; j < numSteps; j++){
//                    //(j*height/numSteps)
//                    ComplexDouble input = new ComplexDouble(solutionList.get(i).origin.r + solutionList.get(i).width,  solutionList.get(i).origin.i + (j*height/numSteps));
//                    System.out.println(input.toString());
//                    ComplexDouble solution = equation.evaluator(input);
//
//                    //set border
//                    solutionA.sList.set(j, solution);
//                    solutionB.sList.set(numSteps *2 + j, solution);
//                }

                solutionA.sList = loadBox(equation, solutionA.origin, solutionA.height, solutionA.width);
                solutionB.sList = loadBox(equation, solutionB.origin, solutionB.height, solutionB.width);


//                System.out.println("KEKISTANITE");
//                printSolveList(solutionA.sList);
//                System.out.println();
//                printSolveList(solutionB.sList);
//                System.out.println("HELLO");
//                System.out.println(windingValid(solutionA.sList));
//                plotRot(solutionA.sList);

                if(windingValid(solutionA.sList)){
                    solutionList.set(i, solutionA);
//                    System.out.println("ASDFASDFASDFASDFASDF");
                    if(windingValid(solutionB.sList)){
//                        System.out.println("BLASDFASDF");
                        solutionList.add(solutionB);
                    }
                }
                else if(windingValid(solutionB.sList)){
                    solutionList.set(i, solutionB);
//                    System.out.println("CHASDFASD");

                }
            }
            else{
                //horizontal cut
                solutionList.get(i).height /= 2;


                solveList solutionA = new solveList(
                        new ComplexDouble(
                                solutionList.get(i).origin.r,
                                solutionList.get(i).origin.i + solutionList.get(i).height),
                        solutionList.get(i).height,
                        solutionList.get(i).width);

                solveList solutionB = new solveList(
                        new ComplexDouble(
                                solutionList.get(i).origin.r,
                                solutionList.get(i).origin.i),
                        solutionList.get(i).height,
                        solutionList.get(i).width);


                //copy into new arrays
//                System.out.println("here");
//                System.out.println(i);

                /*
                 _______________
                |               |
                |               |
                |_______________|
                |               |
                |_______________|
                 */

                solutionA.sList = loadBox(equation, solutionA.origin, solutionA.height, solutionA.width);
                solutionB.sList = loadBox(equation, solutionB.origin, solutionB.height, solutionB.width);

                if(windingValid(solutionA.sList)){
                    solutionList.set(i, solutionA);
                    if(windingValid(solutionB.sList)){
                        solutionList.add(solutionB);
                    }
                }
                else if(windingValid(solutionB.sList)){
                    solutionList.set(i, solutionB);
                }
            }
        }
    }

    public ArrayList<ComplexDouble> loadBox(EquationObject equation, ComplexDouble origin, double height, double width){

        ArrayList<ComplexDouble> sList = new ArrayList<>();

        //left starting from bL going to tL (25 calculations)
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r, + origin.i + (i*height/numSteps));
            sList.add((equation.evaluator(input)));

        }
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + (i*width/numSteps),  origin.i + height);
            sList.add((equation.evaluator(input)));

        }
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + width,  origin.i + height - (i*height/numSteps));
            sList.add((equation.evaluator(input)));

        }
        for(int i = 0; i < numSteps; i++){
            ComplexDouble input = new ComplexDouble(origin.r + width - (i*width/numSteps),  origin.i);
            sList.add((equation.evaluator(input)));

        }
        return sList;
    }

    public static void printSolveList(ArrayList<ComplexDouble> Ac){
        for (ComplexDouble p : Ac) {
            System.out.println(p.toString());
        }
    }

    public void plotRot(ArrayList<ComplexDouble> sList){
        for(int i = 0; i < sList.size(); i++){
            Polar p = Polar.toPolar(sList.get(i));
            System.out.println(i + "\t" + p.t);
        }
    }


    public static boolean windingValid(ArrayList<ComplexDouble> box){
        int winding = 0;
        for(int i = 1; i < box.size(); i++){
            Polar currP = Polar.toPolar(box.get(i));
            Polar prevP = Polar.toPolar(box.get(i-1));
            if(currP.t - prevP.t > 3.14159265/4){
                winding++;
            }
            else if(currP.t - prevP.t <  -3.14159265/4){
                winding--;
            }

        }
        return (winding != 0);
    }

    public static ComplexDouble centerBox(ComplexDouble origin, double height, double width){
        return new ComplexDouble(origin.r + width/2, origin.i + height/2);
    }
}
