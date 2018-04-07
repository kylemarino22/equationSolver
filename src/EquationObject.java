import java.util.ArrayList;

public class EquationObject {
    private ArrayList<Operator> operatorList = new ArrayList<>();

    public void addOperator(int command, char op, double a, double b){
        this.operatorList.add(new Operator(command, op, a, b));
    }


}
