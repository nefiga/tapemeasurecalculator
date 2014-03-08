package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import com.randkprogramming.tapemeasurecalculator.calculator.screens.MainCalculatorScreen;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;

import java.util.LinkedList;

public class CalcHistory {

    public static final int MAX_ENTRIES = 10;
    private static LinkedList<Equation> history = new LinkedList<Equation>();
    public static int selectedIndex = -1;

    /** Adds an equation to the history_screen. New equations get pushed to the front. */
    public static void add(Equation equation) {

        // Prevent duplicates from showing up in the history
        if(history.contains(equation)) {
            return;
        }

        Equation historic = equation.copy();
        history.addFirst(historic);
        while(history.size() > MAX_ENTRIES) {
            history.removeLast();
        }
    }

    /** Returns a copy of the history_screen. */
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

    /** Returns a string representation of the current history_screen. Used for debugging. */
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

    public static void enter() {

        if(selectedIndex < 0 || selectedIndex > CalcHistory.MAX_ENTRIES) {
            return;
        }

        Equation e = CalcHistory.getHistoryAt(selectedIndex);
        CalcState.equation = e.copy();
        CalcState.equation.updateEquation();
        CalcState.paint.update(CalcState.equation.getEquation());
        selectedIndex = -1;
        Calculator c = AndroidFastRenderView.getCalculator();
        c.setScreen(new MainCalculatorScreen(c));
    }

    public static void save() {

        if(selectedIndex < 0 || selectedIndex > CalcHistory.MAX_ENTRIES) {
            return;
        }

        // Currently does nothing
    }

}