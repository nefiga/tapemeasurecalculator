package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import java.util.LinkedList;

public class CalcHistory {

    private static final int MAX_ENTRIES = 10;
    private static LinkedList<Equation> history = new LinkedList<Equation>();

    /** Adds an equation to the history. New equations get pushed to the front. */
    public static void add(Equation equation) {

        Equation historic = equation.copy();
        history.addFirst(historic);
        while(history.size() > MAX_ENTRIES) {
            history.removeLast();
        }
    }

    /** Returns a copy of the history. */
    public static LinkedList<Equation> getHistory() {

        LinkedList<Equation> result = new LinkedList<Equation>();
        for(Equation e : history) {
            result.addLast(e);
        }
        return result;
    }

    /** Returns the equation at the position i */
    public static Equation getHistoryAt(int i) {

        if(history.size() > i) {
            return history.get(i);
        }
        return null;
    }

    /** Returns a string representation of the current history. Used for debugging. */
    public static String print() {

        StringBuilder sb = new StringBuilder();

        sb.append("Calc History Size: " + history.size() + "\n");
        for(Equation e : history) {
            sb.append(e.getString());
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

}