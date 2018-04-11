public class Operator {

    private char op;
    private int command;
    private int destination;

    public Operator(int command, char op, int destination) {
        this.op = op;
        this.command = command;
        this.destination = destination;
    }

    public String toString(){
        return "command: " + Integer.toString(command,2) + " op: " +op+ " dest: " + destination;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
