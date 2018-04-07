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

        String s = "t*32";
        System.out.println(leftofOperator(findFirstChar(0, '*', s), s));
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
           if(inputA == "x"){
               //10
               command = command | 1;
           }
           else if(inputA == "t"){
               command = command | 2;
               //01

           }
           else{
               numericalA = Double.parseDouble(inputA);
           }
           String inputB = rightofOperator(caratPos, workString);
            if(inputB == "x"){
                //0010
                command = command | 4;
            }
            else if(inputB == "t"){
                //0001
                command = command | 8;

            }
            else{
                numericalB = Double.parseDouble(inputB);
            }

            equation.addOperator(command, 'e', numericalA, numericalB);
            
            workString = workString.substring(0, caratPos - inputA.length()) + "t" +  workString.substring(caratPos + inputB.length());

       }
    }

//    private static String multCompile(String phrase){
//
//    }
//
//    private static String addCompile(String phrase){
//
//    }




}
