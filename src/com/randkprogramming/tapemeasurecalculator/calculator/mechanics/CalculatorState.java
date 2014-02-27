package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to keep track of the current state of the calculator.
 */
public class CalculatorState {

    public static Button.PrecisionMode precisionMode = Button.PrecisionMode.SIXTEENTH;
    public static Button.DisplayMode displayMode = Button.DisplayMode.FEET_AND_INCHES;

    public static List<String> numbers = new ArrayList<String>();
    public static List<Button.Operator> operators = new ArrayList<Button.Operator>();

    public static Double lastResult = null;

    static {
        clear();
    }

    public static void clear() {
        numbers.clear();
        operators.clear();
        numbers.add("");
        lastResult = null;
    }

    public static void convertUnitsToSymbols() {

        int current = numbers.size() - 1;

        if(current < 0) {
            return;
        }

        // Replace words with corresponding symbols
        numbers.set(current,numbers.get(current).replaceAll(" Feet", "\'"));
        numbers.set(current, numbers.get(current).replaceAll(" Inches", "\""));
    }

    public static void addNumber(int n) {

        int current = numbers.size() - 1;
        String s = numbers.get(current);

        if( ! s.contains("\"") && ! s.contains("Inches") && (! s.contains("Feet") || ! s.contains(".")) ) {

            s = s.replaceAll(" Feet","\'");

            if(s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
                s += " ";
            }

            s += n;
            numbers.set(current, s);
        }

    }

    public static void addOperator(Button.Operator op) {

        if (isOperatorNext()) {

            convertUnitsToSymbols();
            verifyUnits();
            operators.add(op);
            numbers.add("");
        }
    }

    public static void addFeet() {

        if(isOperatorNext() && numbers.size() > 0) {

            int current = numbers.size() - 1;
            String s = numbers.get(current);

            if(s.length() > 0 && ! s.contains("\'") && ! s.contains("\"") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                numbers.set(current,numbers.get(current) + "\'");
            }
        }
    }

    public static void addInches() {

        if(isOperatorNext() && numbers.size() > 0) {

            int current = numbers.size() - 1;
            String s = numbers.get(current);

            if(s.length() > 0 && ! s.contains("\"") &&
                    ! s.substring(s.length() - 1).equals("\'") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                numbers.set(current,numbers.get(current) + "\"");
            }
        }
    }

    public static void addDecimal() {

        int current = numbers.size() - 1;
        String s = numbers.get(current);

        if ( ! s.contains(".") && ! s.contains("\'") && ! s.contains("\"")) {

            s = s.replaceAll(" Feet","");
            s = s.replaceAll(" Inches","");

            if (s.length() == 0) {
                s = "0";
            }

            numbers.set(current, s + ".");
        }
    }

    /**
     * Makes sure that the user specified units (feet or inches), if not, insert inches by default.
     */
    public static void verifyUnits() {

        int current = numbers.size() - 1;
        String num = numbers.get(current);

        if(numbers.size() == 0 || num.length() == 0)
            return;

        // If user didn't specify feet or inches, insert inches by default.
        if( ! num.contains("\"") && ! num.contains("\'") ) {
            numbers.set(current, num + "\"");
        }
    }

    /**
     * Checks to see if a number was the last thing entered.
     * @return (boolean) Returns true if a number was the last thing entered.
     */
    public static boolean isOperatorNext() {

        assert numbers.size() > 0;
        String value = numbers.get(numbers.size() - 1);

        if(value == null || value.length() == 0) {
            return false;
        }
        return operators.size() < numbers.size();
    }

}