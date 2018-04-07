import java.util.ArrayList;

public class EquationObject {
    private ArrayList<Operator> operatorList;

    public EquationObject(ArrayList<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public void addOperator(char command, int a, int b){
        operatorList.add(new Operator(command, a, b));
    }


}
