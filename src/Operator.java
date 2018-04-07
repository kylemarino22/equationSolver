public class Operator {

    private double a;
    private double b;
    private char op;
    private char command;

    public Operator(char command, char op, double a, double b) {
        this.a = a;
        this.b = b;
        this.op = op;
        this.command = command;
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public double getA() {

        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public char getCommand() {
        return command;
    }

    public void setCommand(char command) {
        this.command = command;
    }
}
