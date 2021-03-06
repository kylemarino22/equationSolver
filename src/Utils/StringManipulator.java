package Utils;

public abstract class StringManipulator {


    final static private String validChar = "1234567890.";

    public static int findLastChar(char c, String mainString){

        int loc = -1;
        for(int i = 0; i < mainString.length(); i++) {
            if (mainString.charAt(i) == c) {
                loc = i;
            }
        }
        return loc;

    }

    public static int findFirstChar(int startPos, char c, String mainString){
        for(int i = startPos; i < mainString.length(); i++) {
            if (mainString.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    //returns the string of the inner most parentheses
    public static String selectParentheses(String mainString){

        int startPos = findLastChar('(', mainString);
        int endPos = findFirstChar(startPos, ')', mainString);

        if(endPos == -1){
            //if there is no ending parentheses
            return "\0";
        }
        return mainString.substring(startPos, endPos + 1);
    }

    public static String leftofOperator(int operatorPos, String s){

        for(int i = 1; i < 20; i++){

            if(s.charAt(operatorPos - 1) == ']'){
                return "]";
            }


            if(operatorPos - i< 0){
                return s.substring(operatorPos - i+1, operatorPos);
            }
            else{
                char c = s.charAt(operatorPos - i);
                if(findFirstChar(0, c, validChar) < 0){
                    //not in the valid array
                    if(c == '-'){
                        return s.substring(operatorPos - i, operatorPos);
                    }

                    if(c == 'x'){
                        if(operatorPos - i - 1 >= 0){
                            //check if the (i - 1)th term exists
                            if(s.charAt(operatorPos - i - 1) == '-'){
                                return "X";
                            }
                        }
                        return "x";
                    }

                    return s.substring(operatorPos - i + 1, operatorPos);

                }
            }

        }

        return "\0";
    }
    public static String rightofOperator(int operatorPos, String s){
        for(int i = 1; i < 20; i++){

            if(s.charAt(operatorPos + 1) == '['){
                return "[";
            }

            if(i == 1 && s.charAt(operatorPos + 1) == '-'){
                if(s.charAt(operatorPos + 2) == '['){
                    return "[";
                }
                continue;
            }



            if(operatorPos + i+1> s.length()){
                return s.substring(operatorPos+1, operatorPos + i );
            }
            else{
                char c = s.charAt(operatorPos + i);
                if(findFirstChar(0, c, validChar) < 0){
                    //not in the valid array
//                    if(c == '-'){
//                        return s.substring(operatorPos, i + operatorPos);
//                    }
//
                    if(c == 'x'){
                        if(s.charAt(operatorPos + i - 1) == '-'){
                            return "X";
                        }
                        return "x";
                    }

                    return s.substring(operatorPos+1, i + operatorPos);

                }
            }

        }

        return "\0";
    }

    public static <T> boolean contains2(final T[] array, final T v) {
        if (v == null) {
            for (final T e : array)
                if (e == null)
                    return true;
        } else {
            for (final T e : array)
                if (e == v || v.equals(e))
                    return true;
        }

        return false;
    }
}
