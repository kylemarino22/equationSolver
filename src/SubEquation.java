import java.util.ArrayList;

public class SubEquation {

    private ArrayList<Operator> operatorList = new ArrayList<>();

    private String equation = "";

    public SubEquation(String equation) {
        this.equation = equation;
    }

    public void addOperator(int command, char op, double a, double b){
        this.operatorList.add(new Operator(command, op, a, b));
    }

    public Operator getOperatorList(int index) {
        return operatorList.get(index);
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }


    public String toString(){
        String s = equation.toString() + "\n";

        for (Operator o : operatorList) {
            s += "\t" + o.toString() + "\n";
        }
        return s;
    }
}
