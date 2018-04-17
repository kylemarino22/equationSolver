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
    private static ComplexDouble origin = new ComplexDouble(-2,-2);
    private static double height = 4;
    private static double width = 4;
    private static int numSteps = 12;


//    private static ArrayList<ComplexDouble> solveList = new ArrayList<>(); //imaginary numbers

    private solveList originalSolveList = new solveList(origin, height, width);
    private static ArrayList<Polar> polarList = new ArrayList<>(); //imaginary numbers




    public void solve(EquationObject equation){
        System.out.println("asdfasdfasdf");
        //loadBox
        originalSolveList.sList = loadBox(equation, origin, height, width);
        //if is winding
        System.out.println("HAHAHAHA");
        printSolveList(originalSolveList.sList);

        if(!windingValid(originalSolveList.sList)){
            plotRot(originalSolveList.sList);
            return;
        }

        ArrayList<solveList> solutionList = new ArrayList<>();
        solutionList.add(originalSolveList);

        boolean cutVert = true;

        //refine solutionlist --> new solutionList
        System.out.println("HAEAFasdf");

        for(int i = 0; i <0; i++){
            refineAnswers(solutionList, cutVert, equation);
            cutVert = !cutVert;
        }


        System.out.println("\n");
        for(int i = 0; i < solutionList.size(); i++){
            System.out.println(centerBox(solutionList.get(i).origin, solutionList.get(i).height, solutionList.get(i).width));
        }

        printSolveList(solutionList.get(0).sList);

        System.out.println();

        System.out.println(solutionList.get(0).origin);
        System.out.println(solutionList.get(0).height);
        System.out.println(solutionList.get(0).width);
        System.out.println(!cutVert);

    }

    //

    private void refineAnswers(ArrayList<solveList> solutionList, boolean cut, EquationObject equation){
        int size = solutionList.size();
        int shift = 0;
        for(int i = 0; i < size - shift; i++){
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
                printSolveList(solutionA.sList);
//                System.out.println();
                System.out.println();
                printSolveList(solutionB.sList);
//                System.out.println("HELLO");
//                System.out.println(windingValid(solutionA.sList));
                plotRot(solutionA.sList);
                plotRot(solutionB.sList);


                System.out.println();
                System.out.println("HEREIA");
                System.out.println(windingValid(solutionB.sList));
                System.out.println();


                if(windingValid(solutionA.sList)){
                    solutionList.set(i, solutionA);
                    System.out.println("HERE1");
                    if(windingValid(solutionB.sList)){
                        System.out.println("HERE2");
                        solutionList.add(solutionB);
                    }
                }
                else if(windingValid(solutionB.sList)){
                    solutionList.set(i, solutionB);

                    System.out.println("HERE3");
                }
                else{
                    shift++;
                    solutionList.remove(i);

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

                printSolveList(solutionA.sList);
//                System.out.println();
                System.out.println();
                printSolveList(solutionB.sList);
                plotRot(solutionA.sList);
                plotRot(solutionB.sList);


                System.out.println();
                System.out.println("HEREIO");
                System.out.println(windingValid(solutionA.sList));
                System.out.println();


                if(windingValid(solutionA.sList)){
                    solutionList.set(i, solutionA);
                    if(windingValid(solutionB.sList)){
                        solutionList.add(solutionB);
                    }
                }
                else if(windingValid(solutionB.sList)){

                    solutionList.set(i, solutionB);
                }
                else{
                    shift++;
                    solutionList.remove(i);
                }
            }
        }
    }

    public ArrayList<ComplexDouble> loadBox(EquationObject equation, ComplexDouble origin, double height, double width){


        /*
        Flow Design
        Find Initial Two points - Distance height/Constant
        Measure angle between nth point and n-1 point;
        Shift += (DesiredAngle - measuredAngle) * -constant;


        Next Point - Distance height/shift - Decreases when shift increases



         */

        ArrayList<ComplexDouble> sList = new ArrayList<>();

        // Left up
        double positionIteration = 0;
        double incrementScalar = 10;
        double desiredAngle = Math.PI/6;
        double shift = 0;

        //calculate 1st point
        ComplexDouble input = new ComplexDouble(origin.r, origin.i);
        sList.add((equation.evaluator(input)));

        //calculate 2nd point
        input = new ComplexDouble(origin.r, origin.i + height/incrementScalar);
        sList.add((equation.evaluator(input)));

        for(int i = 2; positionIteration < height + origin.i; i++){

            double measuredAngleDiff = Math.atan(sList.get(i - 2).i/sList.get(i - 2).r) - Math.atan(sList.get(i - 1).i/sList.get(i - 1).r);
            shift += -(desiredAngle - measuredAngleDiff);

            //
            if(shift > 100){
                shift = 100;
            }
            if(shift < 10){
                shift = 10;
            }

            positionIteration = origin.i + (i*height/shift);
            System.out.println(positionIteration);

            input = new ComplexDouble(origin.r, positionIteration);
            sList.add((equation.evaluator(input)));

        }

        input = new ComplexDouble(origin.r + width/incrementScalar, origin.i + height);
        sList.add((equation.evaluator(input)));

        positionIteration = 0;
        for(int i = 1; positionIteration < height + origin.r; i++){

            double measuredAngleDiff = Math.atan(sList.get(sList.size() - 3).i/sList.get(sList.size() - 3).r) - Math.atan(sList.get(sList.size() - 2).i/sList.get(sList.size() - 2).r);
            shift += -(desiredAngle - measuredAngleDiff);

            //
            if(shift > 100){
                shift = 100;
            }
            if(shift < 10){
                shift = 10;
            }

            positionIteration = origin.r + (i*height/shift);
            System.out.println(positionIteration);

            input = new ComplexDouble(positionIteration, origin.i + height);
            sList.add((equation.evaluator(input)));

        }

        input = new ComplexDouble(origin.r + width, origin.i + height - height/incrementScalar);
        sList.add((equation.evaluator(input)));

        for(int i = 1; positionIteration > origin.i; i++){

            double measuredAngleDiff = Math.atan(sList.get(sList.size() - 3).i/sList.get(sList.size() - 3).r) - Math.atan(sList.get(sList.size() - 2).i/sList.get(sList.size() - 2).r);
            shift += -(desiredAngle - measuredAngleDiff);

            //
            if(shift > 100){
                shift = 100;
            }
            if(shift < 10){
                shift = 10;
            }
            System.out.println(shift);
            positionIteration = origin.i - (i*height/shift);
            System.out.println(positionIteration);
            System.out.println("HELLO");

            input = new ComplexDouble(origin.r + width, positionIteration);
            sList.add((equation.evaluator(input)));

        }

















//        for(int i = 0; i < numSteps; i++){
//            ComplexDouble input = new ComplexDouble(origin.r + (i*width/numSteps),  origin.i + height);
//            sList.add((equation.evaluator(input)));
//
//        }
//        for(int i = 0; i < numSteps; i++){
//            ComplexDouble input = new ComplexDouble(origin.r + width,  origin.i + height - (i*height/numSteps));
//            sList.add((equation.evaluator(input)));
//
//        }
//        for(int i = 0; i < numSteps; i++){
//            ComplexDouble input = new ComplexDouble(origin.r + width - (i*width/numSteps),  origin.i);
//            sList.add((equation.evaluator(input)));
//
//        }
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
        ArrayList<Double> slopeList = new ArrayList<>();

        /*
        10, -10, -50, -100, 170, 69, 3

         */
        for(int i = 1; i < box.size()+2; i++) {
            double currAngle = Polar.toPolar(box.get(i%box.size())).t *2;
            double prevAngle = Polar.toPolar(box.get((i+1)%box.size())).t *2;

            slopeList.add(currAngle - prevAngle);
//            double tempAngle = newAngle;
//            double currCopy = currentAngle;
//
//            if(tempAngle < 0){
//                tempAngle += Math.PI * 2;
//            }
//
//            if(currentAngle < 0){
//                currentAngle += Math.PI * 2;
//            }
//
//            double ccwDistance;
//            double cwDistance;
//
//            if(tempAngle > currentAngle){
//                ccwDistance = Math.abs(tempAngle - currentAngle);
//                cwDistance = Math.abs(2*Math.PI - ccwDistance);
//            }
//            else{
//                cwDistance = Math.abs(tempAngle - currentAngle);
//                ccwDistance = Math.abs(2*Math.PI - cwDistance);
//            }
//            boolean CCW = false;
//            if(ccwDistance < cwDistance){
//                CCW = true;
//            }
//
//            if(CCW){
//                System.out.println(i + ": CCW");
//                //ccw
//                if(currCopy < 0 && newAngle >0){
//                    System.out.println("HERE4");
//                    winding++;
//                }
//            }
//            else{
//                //cw
//                System.out.println(i+ ": CW");
//                if(currCopy > 0 && newAngle <0){
//                    System.out.println("HERE5");
//                    System.out.println("tA: " + tempAngle + "\tcA: " + currentAngle);
//
//                    winding--;
//                }
//
//            }


//            if(ccwDistance < cwDistance){
//                System.out.println(ccwDistance);
//                if(currCopy > 0 && newAngle <0){
//                    System.out.println("HERE4");
//                    System.out.println(currCopy);
//                    System.out.println(newAngle);
//                    System.out.println();
//                    winding++;
//                }
//            }
//            else{
//                System.out.println(cwDistance);
//                if(currCopy > 0 && newAngle <0){
//                    System.out.println("HERE5");
//                    System.out.println(currCopy);
//                    System.out.println(newAngle);
//                    System.out.println();
//                    winding--;
//                }
//            }

            //determine ccw or cw


            //if ccw and - to +, winding++

            //if cw and + to -, winding --

        }

        for(int i = 1; i < slopeList.size(); i++){

            if(slopeList.get(i) > 2.5){
                winding++;
            }
            else if((slopeList.get(i) < -2.5)){
                winding--;
            }
            else if(slopeList.get(i) > 1.333){
                if(slopeList.get((i+1)%slopeList.size()) <= 0.0000001){
                    //next slope has to switch directions

                    if(slopeList.get((i-1)) <= 0.0000001) {
                        //prev slope has to have the same
                        winding++;
                    }
                }

            }
            else if(slopeList.get(i) < -1.333){
                if(slopeList.get((i+1)%slopeList.size()) >= -0.0000001){
                    //next slope has to switch directions
                    if(slopeList.get((i-1)) >= -0.0000001) {
                        //prev slope has to have the same
                        winding--;
                    }

                }
            }

        }


        System.out.println("WINDING: "+  winding);
        return (winding != 0);
    }

    public static ComplexDouble centerBox(ComplexDouble origin, double height, double width){
        return new ComplexDouble(origin.r + width/2, origin.i + height/2);
    }
}
