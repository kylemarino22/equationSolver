import java.util.ArrayList;

public class EquationObject {
    private ArrayList<SubEquation> equationList = new ArrayList<>();

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

    public int getSubEquationID(){
        return equationList.size();
    }
    public String getSubEquation(int index){
        return equationList.get(index).getEquation();
    }
    public void addOperator(int index, int command, char op, double a, double b){
        if(index != -1) {
            equationList.get(index).addOperator(command, op, a, b);
        }
        else{
            finalEquation.addOperator(command, op, a, b);
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
//        for (Operator o : operatorList) {
//            switch (o.getOp()){
//                case 'e':
//                    t = evalE(o, x, t);
//                    break;
//                case 'm':
//                    t = evalM(o, x, t);
//                    break;
//                case 'a':
//                    t = evalA(o, x, t);
//                    break;
//            }
//        }
//        return t;
//    }

    private double evalE(Operator op, double x, double t){
        double base = op.getA();
        double exp = op.getB();

        //base
        if((op.getCommand() & 1) == 1){
            base = x;
        }
        else if((op.getCommand() & 2) == 2){
            base = t;
        }
        if((op.getCommand() & 4) == 4){
            base *= -1;
        }

        //exponent
        if((op.getCommand() & 8) == 8){
            exp = x;
        }
        else if((op.getCommand() & 16) == 16){
            exp = t;
        }
        if((op.getCommand() & 32) == 32){
            exp *= -1;
        }

        return Math.pow(base, exp);
    }

    private double evalM(Operator op, double x, double t){
        double a = op.getA();
        double b = op.getB();

        //base
        if((op.getCommand() & 1) == 1){
            a = x;
        }
        else if((op.getCommand() & 2) == 2){
            a = t;
        }
        if((op.getCommand() & 4) == 4){
            a *= -1;
        }

        //exponent
        if((op.getCommand() & 8) == 8){
            b = x;
        }
        else if((op.getCommand() & 16) == 16){
            b = t;
        }
        if((op.getCommand() & 32) == 32){
            b *= -1;
        }

        return a*b;
    }

    private double evalA(Operator op, double x, double t){
        double a = op.getA();
        double b = op.getB();

        //base
        if((op.getCommand() & 1) == 1){
            a = x;
        }
        else if((op.getCommand() & 2) == 2){
            a = t;
        }
        if((op.getCommand() & 4) == 4){
            a *= -1;
        }

        //exponent
        if((op.getCommand() & 8) == 8){
            b = x;
        }
        else if((op.getCommand() & 16) == 16){
            b = t;
        }
        if((op.getCommand() & 32) == 32){
            b *= -1;
        }

        return a+b;
    }


}
