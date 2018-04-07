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

        String s = "3/x+69^2.134123-3/x*6/4";
        System.out.println(multCompile(exponentCompile(s)));
        System.out.println(equation.toString());
    }



    public static void compileEquation(String equation){

//        create new equation here

    }


    // 3 + 5 / 6 * 3^x
    // 3 + 5 / 6 * t
    // 3 + 0.8333 * t
    // 3 + t
    // t

    public static void compilePhrase(String phrase){

        for(int i = 1; i < phrase.length() - 1; i++){


        }

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
            else{
                numericalA = Double.parseDouble(inputA);
            }
            String inputB = rightofOperator(caratPos, workString);
            if(inputB.equals("x")){
                //0010
                command = command | 4;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 8;

            }
            else{
                numericalB = Double.parseDouble(inputB);
            }

            if(command == 0){
                workString = workString.substring(0, caratPos - inputA.length()) + Math.pow(numericalA, numericalB) +  workString.substring(caratPos + inputB.length());

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
            else{
                numericalA = Double.parseDouble(inputA);
            }
            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){
                //0010
                command = command | 4;
            }
            else if(inputB.equals("t")){
                //0001
                command = command | 8;

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
            int addPos = findFirstChar(0, '+', workString);
            int subPos = findFirstChar(0, '-', workString);
            int operatorPos;
            char op;

            if(subPos == -1 && addPos == -1){
                //end if there are not mult/div operators
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
            int negative = 0;
            if(workString.charAt(operatorPos - inputA.length() - 1) == '-'){
                negative = 1;
            }

            if(inputA.equals("x")){
                //10
                command = command | 1;

                if(negative == 1){
                    //010
                    command = command | 2
                }
            }
            else if(charAt(operatorPos - inputA.length() - 1) == '-'){

            }
            else if(inputA.equals("t")){
                command = command | 4;
                //001

            }
            else{
                numericalA = Double.parseDouble(inputA);
            }
            String inputB = rightofOperator(operatorPos, workString);
            if(inputB.equals("x")){
                //0001
                command = command | 8;

                if(negative == 1){
                    //00001
                    command = command | 16;
                }
            }
            else if(inputB.equals("t")){
                //000001
                command = command | 32;

            }
            else{
                numericalB = Double.parseDouble(inputB);
                if(negative == 1){
                    numericalB *= -1;
                }
            }

            if(command == 0){
                if(op == 'a'){
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA + numericalB) +  workString.substring(operatorPos + inputB.length());
                }
                else{
                    workString = workString.substring(0, operatorPos - inputA.length()) + (numericalA - numericalB) +  workString.substring(operatorPos + inputB.length());
                }

            }
            else{

                if(op == 'a'){
                    equation.addOperator(command, 'a', numericalA, numericalB);
                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() +1);
                }
                else{
                    equation.addOperator(command, 's', numericalA, numericalB);
                    workString = workString.substring(0, operatorPos - inputA.length()) + "t" +  workString.substring(operatorPos + inputB.length() + 1);
                }
            }
        }
    }
//
//    private static String addCompile(String phrase){
//
//    }




}
