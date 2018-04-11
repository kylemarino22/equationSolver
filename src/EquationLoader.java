import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;

public class EquationLoader extends StringManipulator{

    private static String mainString = "Hello ((( asdfa ((( hell)o world";

    public static EquationObject equation = new EquationObject();

    public static void setMainString(String s){
        mainString = s;
    }

    public static void test(){

        String s = "";
        String as = "(3*(x-5)^2+2)^0.5-2*(3-x^2)^0.5";

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

        SubEquation Compilation:
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
        0000 0000 0000 0000
        SubEquation Compilation:
        command: 0100 0000 0000 0010 op: e dest: 1000 0000   | [i2]^x --> t
        command: 1000 0000 0000 0001 op: * dest: 1000 0001   | [i1]*t --> t1

        command: 0000 0011 1000 0001 op: e dest: 1000 0000   | [t1]^[i3] --> t
        command: 0000 0100 1000 0000 op: a dest: 1000 0010   | t+[i4] --> t2

        command: 0100 0000 0000 0101 op: e dest: 1000 0000   | [i5]*x --> t
        command: 0000 0110 1000 0000 op: a dest: 1000 0011   | t+[i6] --> t3

        Equation Compilation
        command: 1000 0010 1000 0011 op: e dest: 1000 0000   | [t2]^[t3] --> t
        command: 1000 0000 0000 0000 op: m dest: 1000 0000   | [i0]*t --> t
        command: 0000 0111 1000 0000 op: m dest: 1000 0000   | t+[i7] --> t


         */
//        compileEquation(s);
        String asd = "((2*x)*(4*x^2-23)+(4*x))^2*(x-3)";
        String p = "[t2]+[t1]-3";

        String asad = "4*(((1+2i)*(3+5i-3))^(-3i)+(6-3i))^(3.23*x-3i)-13.23+0.434i";


        imaginaryParser(asad);
//        preliminaryCompiler(asd);
//        subCompiler();
//        System.out.println("\n");
//        System.out.println(equation.toString());
//        System.out.println(equation.evaluator(1));
//        System.out.println(addCompile(multCompile(exponentCompile(s))));
////        System.out.println(leftofOperator(2, s));

//        System.out.println(equation.evaluator(1.3));
    }

    public static void preliminaryCompiler(String equationString){
        while(true){
            int pos = findLastChar('(', equationString);
            if(pos == -1){
                break;
            }
            int endPos = findFirstChar(pos, ')', equationString);

            equation.addSubEquation(equationString.substring(pos+1, endPos));
            equationString = equationString.substring(0, pos) +"[t" + equation.getSubEquationID() + "]"+equationString.substring(endPos+1);
        }
        equation.setFinalEquation(new SubEquation(equationString));
    }

    public static void subCompiler(){
        String s = "";
        for (int i = 0; i < equation.getSubEquationID(); i++){

            s = exponentCompile(equation.getSubEquation(i), i, false);
            s = multCompile(s, i, false);
            addCompile(s, i, false);
        }

        s = exponentCompile(equation.getFinalEquation(), -1, true);
        s = multCompile(s, -1, true);
        addCompile(s, -1, true);
    }

    public static String imaginaryParser(String input){
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
                findFirstChar(0,'^', workString) == -1){

                System.out.println("IM HERESASDFASDFASDf");

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
                    tempReplace = leftofOperator(subOp, workString) + ",-" + rightofOperator(subOp, workString);
                }
                else{
                    tempReplace = leftofOperator(addOp, workString) + "," + rightofOperator(addOp, workString);

                }

                input = input.substring(0,openPos) +"<" + tempReplace+ ">"+ input.substring(closePos+1);

            }
            else{
                input = input.substring(0,openPos) +"{" + workString.substring(1, workString.length()-1)+"}"+ input.substring(closePos+1);
            }


            System.out.println("TESTING");
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
        return "0";


    }

//    public static void compileEquation(String equation){
//        double t = 0;
//        while(true) {
//            int openPos = findLastChar('(', equation);
//
//            if(openPos == -1){
//                break;
//            }
//            int closePos = findFirstChar(openPos, ')', equation);
//
//            System.out.println(equation.substring(openPos, closePos + 1));
//            compilePhrase(equation.substring(openPos, closePos + 1));
//
//            equation = equation.substring(0, openPos) + "t" + equation.substring(closePos+ 1);
//        }
//
//        compilePhrase(equation);
//    }


    // 3 + 5 / 6 * 3^x
    // 3 + 5 / 6 * t
    // 3 + 0.8333 * t
    // 3 + t
    // t

    // 3*-4

//    public static void compilePhrase(String phrase){
//
//        addCompile(multCompile(exponentCompile(phrase)));
//
//    }

    private static String exponentCompile(String phrase, int index, boolean replace){

        String workString = phrase;
        boolean firstNegative = false;
        String replaceString = "t";
        int destination = 0;
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
                command = command | 15;
                //01

            }
            else if(inputA.equals("X")){
//                if(caratPos - 1 == 0){
                    firstNegative = true;
//                }
                workString = workString.substring(0, caratPos - 2)+ workString.substring(caratPos - 1);
                caratPos --;
                command = command | 1;
                command = command | 16;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(0, caratPos - 2) + workString.substring(caratPos - 1);
                caratPos --;
                System.out.println(workString);
                command = command | 15;
                command = command | 16;
            }
            else if(inputA.equals("]")){
                if(replace){
                    replaceString = "[t" + workString.charAt(caratPos - 2) + "]";
                    destination = workString.charAt(caratPos - 2) - '0';
                }

                int mask = (workString.charAt(caratPos - 2) - '0' +1);

                if(caratPos > 5 && workString.charAt(caratPos - 5) == '-'){
                    mask = mask | 16;
                }
                command = command | mask;

                workString = workString.substring(0, caratPos - 4) + "t" + workString.substring(caratPos);
                caratPos -= 3;
            }
            else{
                numericalA = Double.parseDouble(inputA);


            }

            String inputB = rightofOperator(caratPos, workString);

            if(inputB.equals("x")){
                //001
                command = command | 256;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 3840;

            }
            else if(inputB.equals("X")){

                firstNegative = !firstNegative;
                workString = workString.substring(0, caratPos)+ workString.substring(caratPos + 1);


                command = command | 256;
                command = command | 4352;
            }
            else if(inputB.equals("T")){
                command = command | 3840; //0000 0000 1111 0000
                command = command | 4352; //0000 0000 0000 1000
            }
            else if(inputB.equals("[")){
                if(replace && replaceString.equals("t")){
                    replaceString = "[t" + workString.charAt(caratPos + 3) + "]";
                    destination = workString.charAt(caratPos+3) - '0';
                }

                int mask = workString.charAt(caratPos+3) - '0'+1;

                if(workString.charAt(caratPos + 1) == '-'){
                    mask = workString.charAt(caratPos+4) - '0'+1;
                    mask = mask | 16;
                    destination = workString.charAt(caratPos+4) - '0';
                }

                command = command | (mask << 8);
                workString = workString.substring(0, caratPos) + "t" + workString.substring(caratPos + 4);
            }
            else{
                System.out.println(workString);
                numericalB = Double.parseDouble(inputB);

            }

            if(command == 0){
                workString = workString.substring(0, caratPos - inputA.length()) + Math.pow(numericalA, numericalB) +  workString.substring(caratPos + inputB.length()+1);

            }
            else{
                equation.addOperator(index, command, 'e', numericalA, numericalB, destination);

                if(!(inputA.equals("X") || inputA.equals("T")) && (caratPos - inputA.length() == 0 || numericalA >= 0 )){
                    workString = workString.substring(0, caratPos - inputA.length()) + replaceString + workString.substring(caratPos + inputB.length() + 1);

                }
                else{
                    workString = workString.substring(0, caratPos - inputA.length()) + "+" + replaceString + workString.substring(caratPos + inputB.length() + 1);
                }



            }
            System.out.println(workString);
       }
    }
//
    private static String multCompile(String phrase, int index, boolean replace){
        String workString = phrase;
        boolean firstNegative = false;
        String replaceString = "t";
        int destination = 0;
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
                command = command | 15;
                //01

            }
            else if(inputA.equals("X")){
//                if(operatorPos - 1 == 0){
                firstNegative = true;
//                }
                workString = workString.substring(0, operatorPos - 2)+ workString.substring(operatorPos - 1);
                operatorPos --;
                command = command | 1;
                command = command | 16;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(0, operatorPos - 2) + workString.substring(operatorPos - 1);
                operatorPos --;
                System.out.println(workString);
                command = command | 15;
                command = command | 16;
            }
            else if(inputA.equals("]")){
                if(replace){
                    replaceString = "[t" + workString.charAt(operatorPos - 2) + "]";
                    destination = workString.charAt(operatorPos - 2) - '0';
                }

                int mask = (workString.charAt(operatorPos - 2) - '0' +1);

                if(operatorPos > 5 && workString.charAt(operatorPos - 5) == '-'){
                    mask = mask | 16;
                }
                command = command | mask;

                workString = workString.substring(0, operatorPos - 4) + "t" + workString.substring(operatorPos);
                operatorPos -= 3;
            }
            else{
                numericalA = Double.parseDouble(inputA);


            }

            String inputB = rightofOperator(operatorPos, workString);

            if(inputB.equals("x")){
                //001
                command = command | 256;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 3840;

            }
            else if(inputB.equals("X")){

                firstNegative = !firstNegative;
                workString = workString.substring(0, operatorPos)+ workString.substring(operatorPos + 1);


                command = command | 256;
                command = command | 4352;
            }
            else if(inputB.equals("T")){
                command = command | 3840;
                command = command | 4352;
            }
            else if(inputB.equals("[")){
                if(replace && replaceString.equals("t")){
                    replaceString = "[t" + workString.charAt(operatorPos + 3) + "]";
                    destination = workString.charAt(operatorPos+3) - '0';
                }
                int mask = workString.charAt(operatorPos+3) - '0'+1;

                if(workString.charAt(operatorPos + 1) == '-'){
                    mask = workString.charAt(operatorPos+4) - '0'+1;
                    mask = mask | 16;
                    destination = workString.charAt(operatorPos+4) - '0';
                }
                command = command | (mask << 8);
                workString = workString.substring(0, operatorPos) + "t" + workString.substring(operatorPos + 4);
            }
            else{
                System.out.println(workString);
                numericalB = Double.parseDouble(inputB);

            }

            if(command == 0){
                if(op == 'm'){
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA * numericalB) +  workString.substring(operatorPos + inputB.length()+1);
                }
                else{
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA/ numericalB) +  workString.substring(operatorPos + inputB.length()+1);
                }

            }
            else{
                if(operatorPos - inputA.length() == 1 || numericalA > 0 || numericalA == 0){
                    workString = workString.substring(0, operatorPos - inputA.length()) + replaceString + workString.substring(operatorPos + inputB.length() + 1);

                }
                else{
                    workString = workString.substring(0, operatorPos - inputA.length()) + "+" + replaceString + workString.substring(operatorPos + inputB.length() + 1);

                }

                if(op == 'm'){
                    equation.addOperator(index, command, 'm', numericalA, numericalB, destination);



                }
                else{
                    equation.addOperator(index, command, 'd', numericalA, numericalB, destination);
                }
            }
            System.out.println(workString);


        }
    }
//
    private static String addCompile(String phrase, int index, boolean replace){
        String workString = phrase;
        String replaceString = "t";
        int destination = 0;
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
                command = command | 15;
                //01

            }
            else if(inputA.equals("X")){
//                if(operatorPos - 1 == 0){
//                }
                workString = workString.substring(0, operatorPos - 2)+ workString.substring(operatorPos - 1);
                operatorPos --;
                command = command | 1;
                command = command | 16;
            }
            else if(inputA.equals("T")){

                workString = workString.substring(0, operatorPos - 2) + workString.substring(operatorPos - 1);
                operatorPos --;
                System.out.println(workString);
                command = command | 15;
                command = command | 16;
            }
            else if(inputA.equals("]")){
                if(replace){
                    replaceString = "[t" + workString.charAt(operatorPos - 2) + "]";
                    destination = workString.charAt(operatorPos - 2) - '0';
                }
                int mask = (workString.charAt(operatorPos - 2) - '0' +1);

                if(operatorPos > 5 && workString.charAt(operatorPos - 5) == '-'){
                    mask = mask | 16;
                }
                command = command | mask;
                workString = workString.substring(0, operatorPos - 4) + "t" + workString.substring(operatorPos);
                operatorPos -= 3;
            }
            else{
                numericalA = Double.parseDouble(inputA);


            }

            String inputB = rightofOperator(operatorPos, workString);

            if(inputB.equals("x")){
                //001
                command = command | 256;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 3840;

            }
            else if(inputB.equals("X")){

                workString = workString.substring(0, operatorPos)+ "+" +  workString.substring(operatorPos + 1);


                command = command | 256;
                command = command | 4352;
            }
            else if(inputB.equals("T")){
                command = command | 3840;
                command = command | 4352;
            }
            else if(inputB.equals("[")){
                if(replace && replaceString.equals("t")){
                    replaceString = "[t" + workString.charAt(operatorPos + 3) + "]";
                    destination = workString.charAt(operatorPos+3) - '0';
                }
                int mask = workString.charAt(operatorPos+3) - '0'+1;

                if(workString.charAt(operatorPos + 1) == '-' || op == 's'){
                    mask = mask | 16;
                }

                command = command | (mask << 8);
                workString = workString.substring(0, operatorPos) + "t" + workString.substring(operatorPos + 4);
            }
            else{
                System.out.println(workString);
                numericalB = Double.parseDouble(inputB);
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
                    equation.addOperator(index, command, 'a', numericalA, numericalB, destination);
                    workString = workString.substring(0, operatorPos - inputA.length()) + replaceString +  workString.substring(operatorPos + inputB.length()+1);
//                }
//                else{
//                    equation.addOperator(command, 's', numericalA, numericalB);
//                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() + 1);
//                }
            }
        }
    }
////
////    private static String addCompile(String phrase){
////
////    }




}
