import java.util.ArrayList;

public class EquationObject {
    private ArrayList<Operator> operatorList = new ArrayList<>();

    public void addOperator(int command, char op, double a, double b){
        this.operatorList.add(new Operator(command, op, a, b));
    }

    public String toString(){
        String s = "";
        for (Operator o : operatorList) {
            s += o.toString() + "\n";
        }
        return s;
    }

    public double evaluator (double x){
        double t = 0;
        for (Operator o : operatorList) {
            switch (o.getOp()){
                case 'e':
                    t = evalE(o, x, t);
                    break;
                case 'm':
                    t = evalM(o, x, t);
                    break;
                case 'a':
                    t = evalA(o, x, t);
                    break;
            }
        }
        return t;
    }

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
