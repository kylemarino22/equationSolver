public abstract class StringManipulator {


    final static private char[] validChar = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'};

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

        System.out.println(operatorPos);
        for(int i = 1; i < 20; i++){
            char c = s.charAt(operatorPos - i);

            if(operatorPos - i -1< 0){
                return s.substring(operatorPos - i, i );
            }
            else{
                if(findFirstChar(0, c, s) < 0){
                    //not in the valid array
                    if(c == '-'){
                        return s.substring(operatorPos - i, i);
                    }
                    if(c == 't'){
                        return "t";
                    }
                    if(c == 'x'){
                        return "x";
                    }

                    return s.substring(operatorPos - i, i);

                }
            }

        }

        return "\0";
    }
    public static String rightofOperator(int operatorPos, String s){
        for(int i = 1; i < 20; i++){

            if(i == 1 && s.charAt(operatorPos + 1) == '-'){
                continue;
            }

            char c = s.charAt(operatorPos + i);

            if(operatorPos + i -1< 0){
                return s.substring(i, operatorPos + i );
            }
            else{
                if(findFirstChar(0, c, s) < 0){
                    //not in the valid array
                    if(c == '-'){
                        return s.substring(i, i + operatorPos);
                    }
                    if(c == 't'){
                        return "t";
                    }
                    if(c == 'x'){
                        return "x";
                    }

                    return s.substring(i, i + operatorPos);

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
