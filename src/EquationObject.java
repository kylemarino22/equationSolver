import java.util.ArrayList;

public class EquationObject {
    private ArrayList<Operator> operatorList;

    public void addOperator(int command, char op, double a, double b){
        operatorList.add(new Operator(command, op, a, b));
    }


}
