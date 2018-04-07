public class EquationLoader extends StringManipulator{

    private static String mainString = "Hello ((( asdfa ((( hell)o world";

    public static void setMainString(String s){
        mainString = s;
    }

    public static void test(){
        System.out.println(findLastChar('(', mainString));
        System.out.println(findFirstChar(18, 'l', mainString));
        System.out.println(selectParentheses(mainString));
    }



    public static void compileEquation(String equation){

//        create new equation here

    }

    public static void compilePhrase(String phrase){

        for(int i = 1; i < phrase.length() - 1; i++){

            






        }

    }


}
