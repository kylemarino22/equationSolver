package Evaluator;

import Utils.ComplexDouble;
import Utils.StringManipulator;

public class EquationLoader extends StringManipulator {

    private static String mainString = "Hello ((( asdfa ((( hell)o world";

    public static EquationObject equation;

    public static void setMainString(String s){
        mainString = s;
    }

    public static void test(){

        /*


        parser needs to
            - remove spaces
            - replace -(x-5) with -1*(x-5)
            - Check if there are any x's that are farther than 1 operator apart and without parentheses in between
              Should seperate them with parentheses

        start compiler
        if there is an open parentheses, new compiler
        compile line
            - compiled line output becomes r1, r2, r3
            - substitute with x


        r1 = (x-5)
        r2 = (3*r1^2+2)
        r3 = (3-x^2)
        r4 = r2^0.5 - 2*r3^0.5

        r1: x
        command (x) a 0.0 -5.0
        r2: r1
        command (x) e 0.0 2.0
        command (0t) m 3.0 t
        command (t) a 0.0 2
        r3: x
        command (-x) e 0.0 2
        command (0t) a 3 0.0
        r4: r2, r3
        command (x) e 0.0 0.5
        command


        (3*(x-5)^2+2)^0.5-2*(3-x^2)^0.5


        equation object
             - subEquations
                   - String Representation
                   - operatorList
             - finalEquation (String)

        Preliminary Compilation:

        t1 = (x-5) replaces with "[" + new memory location + "]" eg: [t1]
            -->final = (3*[t1]^2+2)^0.5-2*(3-x^2)^0.5
        t2 = (3*[t1]^2+2)
            -->final = [t2]^0.5-2*(3-x^2)^0.5
        t3 = (3-x^2)
            -->final = [t2]^0.5-2*[t3]^0.5


        Secondary Compilation:

        Command Layout

        ---- 0000 possible variables 0000 - 0111, 1000 is x, 1111 is previous answer
        0000 -000 negative or positive

        0000 0000 ---- 0000 possible variables 0000 - 1111, 1000 is x, 1111 is previous answer
        0000 0000 0000 -000 negative or positive

        Evaluator.SubEquation Compilation:
        command: 1000 0000 0000 0000 a 0.0 -5.0  x-5     --> [t1]
        command: 0100 0000 0000 0000 e 0.0 2.0  [t1]^2   --> t
        command: 0000 0000 1111 0000 m 3.0 0.0   3*t     --> t
        command: 1111 0000 0000 0000 a 0.0 2.0   t+2     --> [t2]
        command: 1000 1000 0000 0000 e 0.0 2.0  -x^2     --> t
        command: 0000 0000 1111 0000 a 3.0 0.0   3-t     --> [t3]

        Equation Compilation:
        command: 1100 0000 0000 0000 e 0.0 0.5  [t2]^0.5 --> [t2]
        command: 0010 0000 0000 0000 e 0.0 0.5  [t3]^0.5 --> [t3]
        command: 0000 0000 0010 0000 m -2.0 0.0 -2*[t3]  --> [t3]
        command: 1100 0000 0010 0000 a 0.0 0.0  [t2]+[t3]--> [f]

        evaluate



        Imaginary Workspace

        4*(((1+2i)*(3+5i))^(-3i)+(6-3i))^(3.23*x-3i)-13.23+0.434i
        <4,0>*((<1,2>*<3,5>^x)^<0,-3>+<6,3>)^(<3.23,0>*x+<0,-3>)+<-13.23,0.434>
        i0 = <4,0>
        i1 = <1,2>
        i2 = <3,5>
        i3 = <0,-3>
        i4 = <6,3>
        i5 = <3.23,0>
        i6 = <0,-3>
        i7 = <-13.23,0.434>

        [i0]*(([i1]*[i2]^x)^[i3]+[i4])^([i5]*x+[i6])+[i7]

        t1 = [i1]*[i2]^x
            -->[i0]*([t1]^[i3]+[i4])^([i5]*x+[i6])
        t2 = [t1]^[i3]+[i4]
            -->[i0]*[t2]^([i5]*x+[i6])
        t3 = [i5]*x+[i6]
            -->[i0]*[t2]^[t3]+[i7]


        0 = i, 1 = x, 10 = t
                   |
        0000 0000 0-00 0000
        Evaluator.SubEquation Compilation:
        command: 0100 0000 0000 0010 op: e dest: 1000 0000   | [i2]^x --> t
        command: 1000 0000 0000 0001 op: m dest: 1000 0001   | [i1]*t --> t1

        command: 0000 0011 1000 0001 op: e dest: 1000 0000   | [t1]^[i3] --> t
        command: 0000 0100 1000 0000 op: a dest: 1000 0010   | t+[i4] --> t2

        command: 0100 0000 0000 0101 op: e dest: 1000 0000   | [i5]*x --> t
        command: 0000 0110 1000 0000 op: a dest: 1000 0011   | t+[i6] --> t3

        Equation Compilation
        command: 1000 0010 1000 0011 op: e dest: 1000 0000   | [t2]^[t3] --> t
        command: 1000 0000 0000 0000 op: m dest: 1000 0000   | [i0]*t --> t
        command: 0000 0111 1000 0000 op: a dest: 1000 0000   | t+[i7] --> t


         */
//        compileEquation(s);
        equation = new EquationObject();
        String test1 = "((2*x)*(4*x^2-23)+(4*x))^2*(x-3)";
        String test2 = "((2.7182818284^(7*3.14159265/12*((x^2-3)^0.5)))^2)*((x/3)-((x^2-3)^0.5)/3)-((x/3)-((x^2-3)^0.5)/3)";
        String test3 = "(2.718281828459045^(5*x))-1";
        String test4 = "(2.7182818284^(7*3.14159265/12*((x^2-3)^0.5)))^2-1";
        String test5 = "(x^2)-(4*x)+5";
        String test6 = "x-3";
        String test7 = "3.2i*(x^2)+(4*x)-2i";
        String test8 = "(4-(x^3))/2i";
        String test9 = "((3*(x-5)^2+2)^0.5)-(2*(3-(x^2))^0.5)";
        //()

        //(-0.002926826477 + 2.95898723)
//        ((2.7182818284^(7*3.14159265/12*((x^2-3)^0.5)))^2)*((x/3)-((x^2-3)^0.5)/3)-((x/3)-((x^2-3)^0.5)/3)

        preliminaryCompiler(imaginaryParser(test6));
//        System.out.println(equation.toString());
        subCompiler();
//        System.out.println("\n");
        System.out.println(equation.toString());
//        System.out.println(equation.evaluator(1));
//        System.out.println(addCompile(multCompile(exponentCompile(s))));
////        System.out.println(leftofOperator(2, s));
        //(9.3+5.3i)

//        System.out.println(equation.evaluator(new Utils.ComplexDouble(0, 0)));
//        System.out.println(Utils.ComplexDouble.pwr(new Utils.ComplexDouble(0,0), new Utils.ComplexDouble(2,0)));
        System.out.println(equation.evaluator(new ComplexDouble(0, 0)));
//        System.out.println(equation.evaluator(new Utils.ComplexDouble(0.12616, 123.32)));
//        System.out.println(equation.evaluator(new Utils.ComplexDouble(-0.645, 356.61)));
//        System.out.println(equation.evaluator(new Utils.ComplexDouble(0.23412351341, -0.0102301023)));
//        System.out.println(equation.evaluator(new Utils.ComplexDouble(1.73205080756887729352, 0)));
//
//        System.out.println();
//        Evaluator.EquationSolver.loadBox(equation);

//        System.out.println(equation.evaluator(new Utils.ComplexDouble(0, -1.1557289045985133)));
//
//        while(true);
        EquationSolver asdf = new EquationSolver();
        asdf.solve(equation);
//        System.out.println();
//        Evaluator.EquationSolver.printSolveList();
//        System.out.println();
//        asdf.printSolveList(asdf.sL);
//        asdf.plotRot();
//        System.out.println();
//        System.out.println(equation.evaluator(new Utils.ComplexDouble(2, 1)));
//        System.out.println(Evaluator.EquationSolver.windingValid());


    }

    public static void setup(String s){
        equation = new EquationObject();
        preliminaryCompiler(imaginaryParser(s));
//        System.out.println(equation.toString());
        subCompiler();
    }

    private static String imaginaryParser(String input){
        //4*(((1+2i)*(3+5i))^(-3i)+(6-3i))^(3.23*x-3i)-13.23+0.434i
        //<4,0>*((<1,2>*<3,5>^x)^<0,-3>+<6,3>)^(<3.23,0>*x+<0,-3>)+<-13.23,0.434>

        /*

        Repeat:
            Find the inner most set of parentheses
            if the only operator is + or -
                Check if i- (indicates there is more than 1 imaginary number)
                Convert into imaginary format
                Replace with imaginary format

        */

        while(true){
            int openPos = findLastChar('(', input);

            if(openPos == -1){
                break;
            }

            int closePos = findFirstChar(openPos, ')', input);
            String workString = input.substring(openPos, closePos+1);

            int numberCounter = 0;
            if (findFirstChar(0,'*', workString) == -1 &&
                    findFirstChar(0,'/', workString) == -1 &&
                    findFirstChar(0,'^', workString) == -1 &&
                    findFirstChar(0,'x', workString) == -1){

                if(findFirstChar(findFirstChar(0, 'i', workString) +1, 'i', workString) != -1){
                    //break if there are more than 1 i
                    input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
                    break;
                }

                //if the only operator is + or -

                int numberFlag = 0;
                for(int i = 0; i < workString.length(); i++){


                    /*if char is in character array --> numberFlag = 1
                      else
                        if(numberFlag = 1){
                            numberCounter++;

                        }
                        numberFlag = 0;


                     */

                    if (!(workString.charAt(i) == '-' || workString.charAt(i) == '+')){
                        numberFlag = 1;
                    }
                    else {

                        if (numberFlag == 1) {

                            numberCounter++;
                        }
                        numberFlag = 0;
                    }

                }
                System.out.println(numberCounter);

            }
            else{
                input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
                continue;
            }

            if(numberCounter < 2){

                //get operator position
                int addOp = findLastChar('+', workString);
                int subOp = findLastChar('-', workString);
                int mainOp = 0;
                String tempReplace = "";

                if(subOp > addOp){
                    if(workString.charAt(subOp + rightofOperator(subOp, workString).length()+1) == 'i'){
                        tempReplace = leftofOperator(subOp, workString) + ",-" + rightofOperator(subOp, workString);
                        input = input.substring(0,openPos) +"<" + tempReplace+ ">"+ input.substring(closePos+1);

                    }
                    else{
                        input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);

                    }

                }
                else if(subOp < addOp){
                    if(workString.charAt(addOp + rightofOperator(addOp, workString).length()+1) == 'i'){
                        tempReplace = leftofOperator(addOp, workString) + "," + rightofOperator(addOp, workString);
                        input = input.substring(0,openPos) +"<" + tempReplace+ ">"+ input.substring(closePos+1);
                    }
                    else{
                        input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
                    }
                }
                else{
                    input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
                }


            }
            else{
                input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
            }


            System.out.println(workString);
            System.out.println(input);
        }

        /*

        Repeat:
            if inside <> continue
            Search for first number
                Get sign of number
                Check if number has an i after it
                Convert to imaginary format
                Replace with imaginary format
                Reset Repeat

         */
        boolean insideBracket = false;
        for(int i = 0; i < input.length(); i++){
            //Continue if inside of brackets
            if(input.charAt(i) == '>'){
                insideBracket = false;
            }

            if(insideBracket){
                continue;
            }
            if(input.charAt(i) == '<'){
                insideBracket = true;
                continue;
            }


            String validChar = "1234567890.";
            if(findFirstChar(0, input.charAt(i), validChar) != -1){
                String value = rightofOperator(i -1, input);

                if(i + value.length() < input.length()){
                    if(input.charAt(i + value.length()) == 'i'){
                        //it is imaginary
                        input = input.substring(0,i) +"<," + value + ">"+ input.substring(i + value.length()+1);
                    }
                    else {
                        //it is real
                        input = input.substring(0,i) +"<" + value + ",>"+ input.substring(i + value.length());
                    }
                }
                else{
                    //it is real
                    input = input.substring(0,i) +"<" + value + ",>"+ input.substring(i + value.length());
                }

                System.out.println(input);
                i = -1;
                //i gets incremented at the bottom so is 0 at the top
                insideBracket = false;
            }
        }






        /*

        Repeat:
            Find set of <>
            Find ,
            create new using R and L of operator
            Push to iList
            replace


         */

        while(true){
            int openPos = findFirstChar(0, '<', input);

            if(openPos == -1){
                break;
            }

            int closePos = findFirstChar(0, '>', input);
            String workString = input.substring(openPos, closePos+1);

            String rString = leftofOperator(findFirstChar(0, ',', workString), workString);
            String iString = rightofOperator(findFirstChar(0, ',', workString), workString);
            double r;
            if(rString.length() == 0){
                r = 0;
            }
            else{
                r = Double.parseDouble(rString);
            }

            double i;
            if(iString.length() == 0){
                i = 0;
            }
            else{
                i = Double.parseDouble(iString);
            }

            input = input.substring(0,openPos) +"[i" + equation.getiListID() + "]"+ input.substring(closePos+1);
            equation.addiList(new ComplexDouble(r, i));
            System.out.println(input);

        }

        equation.printiList();
        return input;


    }

    private static void preliminaryCompiler(String equationString){
        while(true){
            int pos = findLastChar('{', equationString);
            if(pos == -1){
                break;
            }
            int endPos = findFirstChar(pos, '}', equationString);

            equation.addSubEquation(equationString.substring(pos+1, endPos));
            equationString = equationString.substring(0, pos) +"[t" + equation.getSubEquationID() + "]"+equationString.substring(endPos+1);
        }
        equation.setFinalEquation(new SubEquation(equationString));
    }

    private static void subCompiler(){
        String s = "";

        for (int i = 0; i < equation.getSubEquationID(); i++){

            s = exponentCompile(equation.getSubEquation(i), i, false);
            s = multCompile(s, i, false);
            addCompile(s, i, i+1);
        }

        s = exponentCompile(equation.getFinalEquation(), -1, false);
        s = multCompile(s, -1, false);
        addCompile(s, -1, 0);
    }

    private static String exponentCompile(String phrase, int index, boolean replace){

        String workString = phrase;
        boolean firstNegative = false;
        String replaceString = "t";
        int destination = 0;
        int leftLength = 0;
        int rightLength = 0;
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

                // 0100 0000
                command |= 0b0100_0000;
                leftLength = 1;

            }
            else if(inputA.equals("]")){



                String value = leftofOperator(caratPos-1, workString);
                leftLength = value.length() + 3;

                if(workString.charAt(caratPos - 2 - value.length()) == 't'){
                    //| 1000
                    command |= 0b1000_0000;
                }
                else{
                    // 0000
                }
                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val;

            }

            String inputB = rightofOperator(caratPos, workString);
            if(inputB.equals("x")){

                // 0100 0000
                command |= 0b0100_0000_0000_0000;
                rightLength = 1;

            }
            else if(inputB.equals("[")){

                String value = rightofOperator(caratPos + 2, workString);
                rightLength = value.length() + 3;

                if(workString.charAt(caratPos + 2) == 't'){
                    //| 1000
                    command |= 0b1000_0000_0000_0000;
                }
                else{
                    // 0000
                }

                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val << 8;

            }

            if((command & 0b1100_0000_1100_0000) == 0){
                int bLoc = Integer.parseInt(rightofOperator(caratPos + 2, workString));
                int aLoc = Integer.parseInt(leftofOperator(caratPos - 1, workString));
                ComplexDouble val = ComplexDouble.pwr(equation.iList.get(aLoc), equation.iList.get(bLoc));
                equation.iList.set(aLoc, val);

                workString = workString.substring(0, caratPos - leftLength) + "[i"+aLoc+"]" + workString.substring(caratPos + rightLength+1);
                System.out.println(workString);

            }
            else {
                System.out.println(workString);
                workString = workString.substring(0, caratPos - leftLength) + "[t0]" + workString.substring(caratPos + rightLength+1);
                System.out.println(workString);


                equation.addOperator(index,command, 'e', 0);
                System.out.println(Integer.toString(command,2));
            }
        }
    }

    private static String multCompile(String phrase, int index, boolean replace){
        String workString = phrase;
        boolean firstNegative = false;
        String replaceString = "t";
        int destination = 0;
        int leftLength = 0;
        int rightLength = 0;
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

                // 0100 0000
                command |= 0b0100_0000;
                leftLength = 1;


            }
            else if(inputA.equals("]")){



                String value = leftofOperator(operatorPos-1, workString);
                leftLength = value.length() + 3;

                if(workString.charAt(operatorPos - 2 - value.length()) == 't'){
                    //| 1000
                    command |= 0b1000_0000;
                }
                else{
                    // 0000
                }
                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val;

            }

            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){

                // 0100 0000
                command |= 0b0100_0000_0000_0000;
                rightLength = 1;


            }
            else if(inputB.equals("[")){

                String value = rightofOperator(operatorPos + 2, workString);
                rightLength = value.length() + 3;
                if(workString.charAt(operatorPos + 2) == 't'){
                    //| 1000
                    command |= 0b1000_0000_0000_0000;
                }
                else{
                    // 0000
                }

                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val << 8;

            }



            if((command & 0b1100_0000_1100_0000) == 0){
                int bLoc = Integer.parseInt(rightofOperator(operatorPos + 2, workString));
                int aLoc = Integer.parseInt(leftofOperator(operatorPos - 1, workString));
                if(op == 'm'){
                    ComplexDouble val = ComplexDouble.mult(equation.iList.get(aLoc), equation.iList.get(bLoc));
                    equation.iList.set(aLoc, val);
                    workString = workString.substring(0, operatorPos - leftLength) + "[i"+aLoc+"]" + workString.substring(operatorPos + rightLength+1);
                }
                else{
                    ComplexDouble val = ComplexDouble.div(equation.iList.get(aLoc), equation.iList.get(bLoc));
                    equation.iList.set(aLoc, val);
                    workString = workString.substring(0, operatorPos - leftLength) + "[i"+aLoc+"]" + workString.substring(operatorPos + rightLength+1);                }

            }
            else{
//                if(operatorPos - inputA.length() == 1 || numericalA > 0 || numericalA == 0){
//                    workString = workString.substring(0, operatorPos - inputA.length()) + replaceString + workString.substring(operatorPos + inputB.length() + 1);
//
//                }
//                else{
//                    workString = workString.substring(0, operatorPos - inputA.length()) + "+" + replaceString + workString.substring(operatorPos + inputB.length() + 1);
//
//                }

                workString = workString.substring(0, operatorPos - leftLength) + "[t0]"  + workString.substring(operatorPos + rightLength+1);


                if(op == 'm'){
                    equation.addOperator(index, command, 'm', 0);



                }
                else{
                    equation.addOperator(index, command, 'd', 0);
                }
            }
            System.out.println(workString);


        }
    }

    private static String addCompile(String phrase, int index, int replace){
        String workString = phrase;
        String replaceString = "t";
        int destination = 0;
        int leftLength = 0;
        int rightLength = 0;
        while(true) {
            int addPos = findFirstChar(1, '+', workString);
            int subPos = findFirstChar(1, '-', workString);
            int operatorPos;
            char op;

            if(subPos == -1 && addPos == -1){
                //end if there are no add/sub operators
                //set the last destination to be the appropriate t
                if(replace != 0){
                    equation.setLastDestination(index, replace);
                }
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

                // 0100 0000
                command |= 0b0100_0000;
                leftLength = 1;


            }
            else if(inputA.equals("]")){



                String value = leftofOperator(operatorPos-1, workString);
                leftLength = value.length() + 3;

                if(workString.charAt(operatorPos - 2 - value.length()) == 't'){
                    //| 1000
                    command |= 0b1000_0000;
                }
                else{
                    // 0000
                }
                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val;

            }

            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){

                // 0100 0000
                command |= 0b0100_0000_0000_0000;
                rightLength = 1;


            }
            else if(inputB.equals("[")){

                String value = rightofOperator(operatorPos + 2, workString);
                rightLength = value.length() + 3;
                if(workString.charAt(operatorPos + 2) == 't'){
                    //| 1000
                    command |= 0b1000_0000_0000_0000;
                }
                else{
                    // 0000
                }

                int val = Integer.parseInt(value);

                //| value into first bits
                command |= val << 8;

            }

            if((command & 0b1100_0000_1100_0000) == 0){
                int bLoc = Integer.parseInt(rightofOperator(operatorPos + 2, workString));
                int aLoc = Integer.parseInt(leftofOperator(operatorPos - 1, workString));
                if(op == 'a'){
                    ComplexDouble val = ComplexDouble.add(equation.iList.get(aLoc), equation.iList.get(bLoc));
                    equation.iList.set(aLoc, val);
                    workString = workString.substring(0, operatorPos - leftLength) + "[i"+aLoc+"]" + workString.substring(operatorPos + rightLength+1);
                }
                else{
                    ComplexDouble val = ComplexDouble.sub(equation.iList.get(aLoc), equation.iList.get(bLoc));
                    equation.iList.set(aLoc, val);
                    workString = workString.substring(0, operatorPos - leftLength) + "[i"+aLoc+"]" + workString.substring(operatorPos + rightLength+1);                }

            }
            else{

                workString = workString.substring(0, operatorPos - leftLength) + "[t0]"  + workString.substring(operatorPos + rightLength+1);


                if(op == 'a'){
                    equation.addOperator(index, command, 'a', 0);

                }
                else{
                    equation.addOperator(index, command, 's', 0);
                }
            }
        }


    }

}
