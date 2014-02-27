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

    public static String getLastNumber() {
        if(numbers.size() > 0) {
            return numbers.get(numbers.size()-1);
        }
        return "";
    }

    public static Button.Operator getLastOperator() {
        if(operators.size() > 0) {
            return operators.get(operators.size()-1);
        }
        return null;
    }

    public static void setLastNumber(String num) {

        if(numbers.size() > 0) {
            numbers.set(numbers.size() - 1, num);
        }
    }

    public static void appendToLastNum(String num) {

        setLastNumber(getLastNumber() + num);
    }

    public static void convertUnitsToSymbols() {

        // Replace words with corresponding symbols
        setLastNumber(getLastNumber().replaceAll(" Feet", "\'"));
        setLastNumber(getLastNumber().replaceAll(" Inches", "\""));
    }

    public static void addNumber(int n) {

        String s = getLastNumber();

        if( ! s.contains("\"") && ! s.contains("Inches") && (! s.contains("Feet") || ! s.contains(".")) ) {

            s = s.replaceAll(" Feet","\'");

            if(s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
                s += " ";
            }

            s += n;
            setLastNumber(s);
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

            // Don't allow them to add unit symbols on multiplications or divisions
            if(mostRecentOperatorIsTimesOrDivide()) {
                return;
            }

            String s = getLastNumber();

            if(s.length() > 0 && ! s.contains("\'") && ! s.contains("\"") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                appendToLastNum("\'");
            }
        }
    }

    public static void addInches() {

        if(isOperatorNext() && numbers.size() > 0) {

            // Don't allow them to add unit symbols on multiplications or divisions
            if(mostRecentOperatorIsTimesOrDivide()) {
                return;
            }

            String s = getLastNumber();

            if(s.length() > 0 && ! s.contains("\"") &&
                    ! s.substring(s.length() - 1).equals("\'") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                appendToLastNum("\"");
            }
        }
    }

    public static void addDecimal() {

        String s = getLastNumber();

        if ( ! s.contains(".") && ! s.contains("\'") && ! s.contains("\"")) {

            s = s.replaceAll(" Feet","");
            s = s.replaceAll(" Inches","");

            if (s.length() == 0) {
                s = "0";
            }

            setLastNumber(s + ".");
        }
    }

    /**
     * Checks to see whether the last operator entered in was a times or a divide.
     * @return True if the latest one is a times or a divide. False otherwise.
     */
    public static boolean mostRecentOperatorIsTimesOrDivide() {

        boolean result = false;
        if(operators.size() > 0) {
            Button.Operator mostRecentOp = operators.get(operators.size()-1);
            result = mostRecentOp.equals(Button.Operator.TIMES) || mostRecentOp.equals(Button.Operator.DIVIDE);
        }
        return result;
    }

    /**
     * If user didn't specify feet or inches, insert inches by default.
     */
    public static void verifyUnits() {

        String num = getLastNumber();

        // Don't allow units until they have at least one number
        if(numbers.size() == 0 || num.length() == 0)
            return;

        if( ! num.contains("\"") && Character.isDigit(num.charAt(num.length()-1)) ) {

            // Ignore adding the unit symbols for multiplications or divisions
            if(mostRecentOperatorIsTimesOrDivide()) {
                return;
            }
            appendToLastNum("\"");
        }
    }

    /**
     * Checks to see if a number was the last thing entered.
     * @return (boolean) Returns true if a number was the last thing entered.
     */
    public static boolean isOperatorNext() {

        String s = getLastNumber();

        if(s == null || s.length() == 0) {
            return false;
        }
        return operators.size() < numbers.size();
    }

}