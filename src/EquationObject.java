import java.util.ArrayList;

public class EquationObject {
    private ArrayList<SubEquation> equationList = new ArrayList<>();
    private ArrayList<ComplexDouble> varList = new ArrayList<>(); //real variables
    public ArrayList<ComplexDouble> iList = new ArrayList<>(); //imaginary numbers
    private SubEquation finalEquation;


    public EquationObject(){
        varList.add(new ComplexDouble(0,0));
    }
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
            System.out.println("[i" + i + "]\t" + iList.get(i).toString());
        }
    }

    public int getSubEquationID(){
        return equationList.size();
    }
    public int getiListID(){
        return iList.size();
    }

    public void setLastDestination(int index, int dest){
        int size = equationList.get(index).getOperatorList().size();
        if(size != 0){
            equationList.get(index).getOperatorList().get(size - 1).setDestination(dest);
        }
        else{

            //compilation step of reassigning [i] value to a [t] destination - only if ([i])
            int loc = Integer.parseInt(StringManipulator.rightofOperator(1, equationList.get(index).getEquation()));
            //only need loc of i
            addOperator(index, loc,'@', dest);
        }
    }
    public String getSubEquation(int index){
        return equationList.get(index).getEquation();
    }
    public void addOperator(int index, int command, char op, int destination){
        if(index != -1) {
            equationList.get(index).addOperator(command, op, destination);
        }
        else{
            finalEquation.addOperator(command, op, destination);
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

    public ComplexDouble evaluator (ComplexDouble x){
        varList.clear();
        varList.add(new ComplexDouble(0,0));



        for(int i = 0; i < equationList.size(); i++){
            for(Operator op : equationList.get(i).getOperatorList()) {
                evalInstruction(op, x);
            }
        }

        for(Operator op : finalEquation.getOperatorList()) {
            evalInstruction(op, x);
        }
        return varList.get(0);
    }

    private void evalInstruction(Operator op, ComplexDouble x){
        //base
        ComplexDouble base = new ComplexDouble(0,0);
        ComplexDouble exp = new ComplexDouble(0,0);

        //0000 0000 0000 0000
        //0000 0000 1100 1000 | 0000 1111 1111 1111 == 65521 --> Default
        //1111 0000 0000 0000 | 0000 1111 1111 1111 == 1111 1111 1111 1111
        //0b0110_1001_1100_0010


        /*
        100         001
        010         010
        110         011
        001         100
        101         101

         */

        //& clear execpt for specified bits

        if((op.getCommand() & 0b0000_0000_1100_0000) == 0b0000_0000_0100_0000){
            //default number
            base = x;
        }
        else if((op.getCommand() & 0b0000_0000_1100_0000) == 0b0000_0000_0000_0000){
            int index = op.getCommand() & 0b0000_0000_0011_1111;
            base = iList.get(index);
        }
        else if((op.getCommand() & 0b0000_0000_1100_0000) == 0b0000_0000_1000_0000){
            int index = op.getCommand() & 0b0000_0000_0011_1111;
            base = varList.get(index);
        }

        //exponent
        if((op.getCommand() & 0b1100_0000_0000_0000) == 0b0100_0000_0000_0000){
            //default number
            exp = x;
        }
        else if((op.getCommand() & 0b1100_0000_0000_0000) == 0b0000_0000_0000_0000){
            int index = (op.getCommand() & 0b0011_1111_0000_0000) >> 8;
            exp = iList.get(index);
        }
        else if((op.getCommand() & 0b1100_0000_0000_0000) == 0b1000_0000_0000_0000){
            int index = (op.getCommand() & 0b0011_1111_0000_0000) >> 8;
            exp = varList.get(index);
        }

        //calculate value
        ComplexDouble val = new ComplexDouble(0,0);
        switch (op.getOp()) {
            case 'e':
                val = ComplexDouble.pwr(base,exp);
                break;
            case 'm':
                val = ComplexDouble.mult(base,exp);
                break;
            case 'd':
                val = ComplexDouble.div(base,exp);
                break;
            case 'a':
                val = ComplexDouble.add(base,exp);
                break;
            case 's':
                val = ComplexDouble.sub(base,exp);
                break;
            case '@':
                val = base;
                break;
        }

        if(op.getDestination() == 0){
            varList.set(0,val);
        }
        else{
            varList.add(val);
        }

    }




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
