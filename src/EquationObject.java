import java.util.ArrayList;

public class EquationObject {
    private ArrayList<Operator> operatorList;

    public EquationObject(ArrayList<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public void addOperator(char command, char op, double a, double b){
        operatorList.add(new Operator(command, op, a, b));
    }


}
