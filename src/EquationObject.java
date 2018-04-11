import java.util.ArrayList;

public class EquationObject {
    private ArrayList<SubEquation> equationList = new ArrayList<>();
    private ArrayList<ComplexDouble> varList = new ArrayList<>(); //real variables
    private ArrayList<ComplexDouble> iList = new ArrayList<>(); //imaginary numbers
    private SubEquation finalEquation;

    public String getFinalEquation() {
        return finalEquation.getEquation();
    }

    public void setFinalEquation(SubEquation finalEquation) {
        this.finalEquation = finalEquation;
    }

    public void addSubEquation(String equation){
        this.equationList.add(new SubEquation(equation));
    }

    public void addiList(ComplexDouble c){
        this.iList.add(c);
    }

    public void printiList(){

        for(int i = 0; i < iList.size(); i++){
            System.out.println("[i" + i + "]  " + iList.get(i).toString());
        }
    }

    public int getSubEquationID(){
        return equationList.size();
    }
    public int getiListID(){
        return iList.size();
    }
    public String getSubEquation(int index){
        return equationList.get(index).getEquation();
    }
    public void addOperator(int index, int command, char op, double a, double b, int destination){
        if(index != -1) {
            equationList.get(index).addOperator(command, op, a, b, destination);
        }
        else{
            finalEquation.addOperator(command, op, a, b, destination);
        }
    }


    public String toString(){
        String s = "";
        for (SubEquation sub : equationList) {
            s += sub.toString();
        }
        s+= "FinalEquation: \n";
        s+= finalEquation.toString();
        return s;
    }

//    public double evaluator (double x){
//        double t = 0;
//
//        for(int i = 0; i < equationList.size(); i++){
//            t = 0;
//            for(Operator op : equationList.get(i).getOperatorList()) {
//                switch (op.getOp()) {
//                    case 'e':
//                        t = evalE(op, x, t);
//                        break;
//                    case 'm':
//                        t = evalM(op, x, t);
//                        break;
//                    case 'a':
//                        t = evalA(op, x, t);
//                        break;
//                }
//            }
//
////            varList.add(t);
//        }
//
//        for(Operator op : finalEquation.getOperatorList()) {
//            switch (op.getOp()) {
//                case 'e':
//                    t = evalE(op, x, t);
//                    break;
//                case 'm':
//                    t = evalM(op, x, t);
//                    break;
//                case 'a':
//                    t = evalA(op, x, t);
//                    break;
//            }
//        }
//        return t;
//    }

//    private double evalE(Operator op, double x, double t){
//        double base = op.getA();
//        double exp = op.getB();
//
//        //base
//
//        //0000 0000 0000 0000
//        //0000 0000 1100 1000 | 0000 1111 1111 1111 == 65521 --> Default
//        //1111 0000 0000 0000 | 0000 1111 1111 1111 == 1111 1111 1111 1111
//        //0b0110_1001_1100_0010
//
//
//        /*
//        100         001
//        010         010
//        110         011
//        001         100
//        101         101
//
//         */
//
//        if((op.getCommand() | 65520) == 65520){
//            //default number
//            base = op.getA();
//        }
//        else if((op.getCommand() | 65521) == 65521){
//            base = x;
//        }
//        else if((op.getCommand() | 65521) == 65535){
//            base = t;
//        }
//        else{
//            int loc = op.getCommand() & 15; // 0100 0000 1111 0000 & 1111 0000 0000 0000 = 0100 0000 0000 0000
//            base = memoryList.get(loc-2);
//        }
//        if((op.getCommand() & 16) == 16){
//            base *= -1;
//        }
//
//
//        //1111 0000 0000 0000 | 1111 1111 0000 1111 == 1111 1111 0000 1111 (61695)
//        //1010 0000
//
//        //exponent
//        if((op.getCommand() | 61695) == 61695){
//            exp = op.getB();
//        }
//        else if((op.getCommand() | 61951) == 61951 ){
//            exp = x;
//        }
//        else if((op.getCommand() | 61951) == 65535){
//            exp = t;
//        }
//        else{
//            int loc = (op.getCommand() & 3840) >> 8; // 1111 0000 0100 0000 & 0000 0000 1111 0000 = 0000 0000 0100 0000
//            exp = memoryList.get(loc-2);
//        }
//
//        if((op.getCommand() & 4096) == 4096){
//            exp *= -1;
//        }
//
//        if(op.getDestination() != 0){
//            memoryList.set(op.getDestination() -1, Math.pow(base, exp));
//        }
//        return Math.pow(base, exp);
//    }
//
//    private double evalM(Operator op, double x, double t){
//        double a, b;
//        if((op.getCommand() | 65520) == 65520){
//            //default number
//            a = op.getA();
//        }
//        else if((op.getCommand() | 65521) == 65521){
//            a = x;
//        }
//        else if((op.getCommand() | 65521) == 65535){
//            a = t;
//        }
//        else{
//            int loc = op.getCommand() & 15; // 0100 0000 1111 0000 & 1111 0000 0000 0000 = 0100 0000 0000 0000
//            a = memoryList.get(loc-2);
//        }
//        if((op.getCommand() & 16) == 16){
//            a *= -1;
//        }
//
//
//        //1111 0000 0000 0000 | 1111 1111 0000 1111 == 1111 1111 0000 1111 (61695)
//
//        //exponent
//        if((op.getCommand() | 61695) == 61695){
//            b = op.getB();
//        }
//        else if((op.getCommand() | 61951) == 61951 ){
//            b = x;
//        }
//        else if((op.getCommand() | 61951) == 65535){
//            b = t;
//        }
//        else{
//            int loc = (op.getCommand() & 3840) >> 8; // 1111 0000 0100 0000 & 0000 0000 1111 0000 = 0000 0000 0100 0000
//            b = memoryList.get(loc-2);
//        }
//
//        if((op.getCommand() & 4096) == 4096){
//            b *= -1;
//        }
//
//        if(op.getDestination() != 0){
//            memoryList.set(op.getDestination() -1, a*b);
//        }
//
//        return a*b;
//    }
//
//    private double evalA(Operator op, double x, double t){
//
//        double a, b;
//        if((op.getCommand() | 65520) == 65520){
//            //default number
//            a = op.getA();
//        }
//        else if((op.getCommand() | 65521) == 65521){
//            a = x;
//        }
//        else if((op.getCommand() | 65521) == 65535){
//            a = t;
//        }
//        else{
//            int loc = op.getCommand() & 15; // 0100 0000 1111 0000 & 1111 0000 0000 0000 = 0100 0000 0000 0000
//            a = memoryList.get(loc-2);
//        }
//        if((op.getCommand() & 16) == 16){
//            a *= -1;
//        }
//
//
//        //1111 0000 0000 0000 | 1111 1111 0000 1111 == 1111 1111 0000 1111 (61695)
//
//        //exponent
//        if((op.getCommand() | 61695) == 61695){
//            b = op.getB();
//        }
//        else if((op.getCommand() | 61951) == 61951 ){
//            b = x;
//        }
//        else if((op.getCommand() | 61951) == 65535){
//            b = t;
//        }
//        else{
//            int loc = (op.getCommand() & 3840) >> 8; // 1111 0000 0100 0000 & 0000 0000 1111 0000 = 0000 0000 0100 0000
//            b = memoryList.get(loc-2);
//        }
//
//        if((op.getCommand() & 4096) == 4096){
//            b *= -1;
//        }
//
//        if(op.getDestination() != 0){
//            memoryList.set(op.getDestination() -1, a+b);
//        }
//
//
//        return a + b;
//    }
//
//
}
