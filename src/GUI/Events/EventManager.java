package GUI.Events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static List<NewEquationListener> equationListeners = new ArrayList<>();

    public static void addNewEquationListener (NewEquationListener toAdd) {
        equationListeners.add(toAdd);
    }

    public static void setNewEquation (String equation) {
        for (NewEquationListener e: equationListeners) {
            e.setNewEquation(equation);
        }
    }
}
