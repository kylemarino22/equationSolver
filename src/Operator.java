public class Operator {

    private int a;
    private int b;
    private char command;

    public Operator(char command, int a, int b) {
        this.a = a;
        this.b = b;
        this.command = command;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public char getCommand() {
        return command;
    }

    public void setCommand(char command) {
        this.command = command;
    }
}
