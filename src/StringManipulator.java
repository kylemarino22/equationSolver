public abstract class StringManipulator {



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
}
