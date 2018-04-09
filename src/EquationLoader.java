import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;

public class EquationLoader extends StringManipulator{

    private static String mainString = "Hello ((( asdfa ((( hell)o world";

    public static EquationObject equation = new EquationObject();

    public static void setMainString(String s){
        mainString = s;
    }

    public static void test(){
        System.out.println(findLastChar('(', mainString));
        System.out.println(findFirstChar(18, 'l', mainString));
        System.out.println(selectParentheses(mainString));

        String s = "(3*(x-5)^2+2)^0.5-3";
        compileEquation(s);
//        System.out.println(addCompile(multCompile(exponentCompile(s))));
////        System.out.println(leftofOperator(2, s));
        System.out.println(equation.toString());
        System.out.println(equation.evaluator(-3.123434));
    }



    public static void compileEquation(String equation){
        double t = 0;
        while(true) {
            int openPos = findLastChar('(', equation);

            if(openPos == -1){
                break;
            }
            int closePos = findFirstChar(openPos, ')', equation);

            System.out.println(equation.substring(openPos, closePos + 1));
            compilePhrase(equation.substring(openPos, closePos + 1));

            equation = equation.substring(0, openPos) + "t" + equation.substring(closePos+ 1);
        }

        compilePhrase(equation);
    }


    // 3 + 5 / 6 * 3^x
    // 3 + 5 / 6 * t
    // 3 + 0.8333 * t
    // 3 + t
    // t

    // 3*-4

    public static void compilePhrase(String phrase){

        addCompile(multCompile(exponentCompile(phrase)));

    }

    private static String exponentCompile(String phrase){

        String workString = phrase;
        while(true){
            int caratPos = findFirstChar(0,'^', workString);
            int command = 0;
            double numericalA = 0;
            double numericalB = 0;
            if(caratPos == -1){
                return workString;
            }
            String inputA = leftofOperator(caratPos, workString);
            if(inputA.equals("x")){
                //10
                command = command | 1;

            }
            else if(inputA.equals("t")){
                command = command | 2;
                //01

            }
            else if(inputA.equals("X")){
                workString = workString.substring(caratPos - 1);
                caratPos --;
                command = command | 1;
                command = command | 4;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(caratPos - 1);
                caratPos --;
                System.out.println(workString);
                command = command | 2;
                command = command | 4;
            }
            else{
                System.out.println(inputA);
                System.out.println("here");
                numericalA = Double.parseDouble(inputA);
                System.out.println(numericalA);

            }


            String inputB = rightofOperator(caratPos, workString);
            System.out.println(inputB);
            if(inputB.equals("x")){
                //001
                command = command | 8;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 16;

            }
            else if(inputB.equals("X")){

                command = command | 8;
                command = command | 32;
            }
            else if(inputB.equals("T")){
                command = command | 16;
                command = command | 32;
            }
            else{
                numericalB = Double.parseDouble(inputB);
            }

            if(command == 0){
                workString = workString.substring(0, caratPos - inputA.length()) + Math.pow(numericalA, numericalB) +  workString.substring(caratPos + inputB.length()+1);

            }
            else{
                equation.addOperator(command, 'e', numericalA, numericalB);

                workString = workString.substring(0, caratPos - inputA.length()) + "t" +  workString.substring(caratPos + inputB.length()+1);

            }

       }
    }

    private static String multCompile(String phrase){
        String workString = phrase;

        while(true) {
            int multPos = findFirstChar(0, '*', workString);
            int divPos = findFirstChar(0, '/', workString);
            int operatorPos;
            char op;

            if(divPos == -1 && multPos == -1){
                //end if there are not mult/div operators
                return workString;
            }

            //if it doesn't exist, set it to be larger than string length so comparison is easy
            if(divPos == -1) divPos = workString.length();
            if(multPos == -1) multPos = workString.length();

            if(divPos < multPos){
                operatorPos = divPos;
                op = 'd';
            }
            else{
                //multPos < divPos - They can't be equal definitionally
                operatorPos = multPos;
                op = 'm';
            }



            int command = 0;
            double numericalA = 0;
            double numericalB = 0;

            String inputA = leftofOperator(operatorPos, workString);
            if(inputA.equals("x")){
                //10
                command = command | 1;

            }
            else if(inputA.equals("t")){
                command = command | 2;
                //01

            }
            else if(inputA.equals("X")){
                workString = workString.substring(operatorPos - 1);
                operatorPos --;
                command = command | 1;
                command = command | 4;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(operatorPos - 1);
                operatorPos --;
                System.out.println(workString);
                command = command | 2;
                command = command | 4;
            }
            else{
                System.out.println(inputA);
                System.out.println("here");
                numericalA = Double.parseDouble(inputA);
                System.out.println(numericalA);

            }
            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){
                //001
                command = command | 8;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 16;

            }
            else if(inputB.equals("X")){

                command = command | 8;
                command = command | 32;
            }
            else if(inputB.equals("T")){
                command = command | 16;
                command = command | 32;
            }
            else{
                numericalB = Double.parseDouble(inputB);
            }

            if(command == 0){
                if(op == 'm'){
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA * numericalB) +  workString.substring(operatorPos + inputB.length());
                }
                else{
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA/ numericalB) +  workString.substring(operatorPos + inputB.length());
                }

            }
            else{

                if(op == 'm'){
                    equation.addOperator(command, 'm', numericalA, numericalB);
                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() +1);
                }
                else{
                    equation.addOperator(command, 'd', numericalA, numericalB);
                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() + 1);
                }
            }

        }
    }

    private static String addCompile(String phrase){
        String workString = phrase;

        while(true) {
            int addPos = findFirstChar(1, '+', workString);
            int subPos = findFirstChar(1, '-', workString);
            int operatorPos;
            char op;

            if(subPos == -1 && addPos == -1){
                //end if there are no add/sub operators
                return workString;
            }

            //if it doesn't exist, set it to be larger than string length so comparison is easy
            if(subPos == -1) subPos = workString.length();
            if(addPos == -1) addPos = workString.length();

            if(subPos < addPos){
                operatorPos = subPos;
                op = 's';
            }
            else{
                //addPos < subPos - They can't be equal definitionally
                operatorPos = addPos;
                op = 'a';
            }



            int command = 0;
            double numericalA = 0;
            double numericalB = 0;

            String inputA = leftofOperator(operatorPos, workString);

            if(inputA.equals("x")){
                //10
                command = command | 1;

            }
            else if(inputA.equals("t")){
                command = command | 2;
                //01

            }
            else if(inputA.equals("X")){
                command = command | 1;
                command = command | 4;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(operatorPos - 2);
                operatorPos --;
                System.out.println(workString);
                command = command | 2;
                command = command | 4;
            }
            else{
                System.out.println(inputA);
                System.out.println("here");
                numericalA = Double.parseDouble(inputA);
                System.out.println(numericalA);

            }
            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){
                //001
                command = command | 8;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 16;

            }
            else if(inputB.equals("X")){

                command = command | 8;
                command = command | 32;
            }
            else if(inputB.equals("T")){
                command = command | 16;
                command = command | 32;
            }
            else{
                numericalB = Double.parseDouble(inputB);
                System.out.println(numericalB);
                if(op == 's'){
                    numericalB *= -1;
                }
            }

            if(command == 0){
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA + numericalB) +  workString.substring(operatorPos + inputB.length()+1);

//                else{
//                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA - numericalB) +  workString.substring(operatorPos + inputB.length());
//                }

            }
            else{

//                if(op == 'a'){
                    equation.addOperator(command, 'a', numericalA, numericalB);
                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length()+1);
//                }
//                else{
//                    equation.addOperator(command, 's', numericalA, numericalB);
//                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() + 1);
//                }
            }
        }
    }
//
//    private static String addCompile(String phrase){
//
//    }




}
