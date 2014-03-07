package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

//----------------------------------
// Calculator State Class
//----------------------------------

/** This class is used to keep track of and to change the current state of the calculator. */
public class CalcState {

    //----------------------------------
    // Fields
    //----------------------------------
    public static final int DIGIT_LINE_LIMIT = 28;
    public static final int DIGIT_EQUATION_LIMIT = 120;
    public static DisplayModes.FractionOrDecimal fractionOrDecimal = DisplayModes.FractionOrDecimal.FRACTION_OPTION;
    public static DisplayModes.FractionPrecision fractionPrecision = DisplayModes.FractionPrecision.SIXTEENTH;
    public static DisplayModes.DisplayUnits displayUnits = DisplayModes.DisplayUnits.FEET_AND_INCHES;
    public static Equation equation = new Equation();

    //----------------------------------
    //  Add Number
    //----------------------------------
    /** Tries to append a number to the equation if the current state of the calculator will allow it */
    public static void addNumber(int n) {

        String s = equation.getLastNumber();

                if( ! s.contains("\"") && ! s.contains("Inches") && (! s.contains("Feet") || ! s.contains("."))) {

                    if(equation.equation.length() < DIGIT_EQUATION_LIMIT && s.length() < DIGIT_LINE_LIMIT) {

                        s = s.replaceAll(" Feet","\'");

                        if(s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
                            s += " ";
                        }

                        s += n;
                equation.setLastNumber(s);
                equation.updateEquation();
            }
            else {
//                MainCalculatorScreen.flashScreen();
            }

        }

    }

    //----------------------------------
    //  Add Operator
    //----------------------------------
    /** Tries to append an operator to the equation if the current state of the calculator will allow it */
    public static void addOperator(Button.Operator op) {

        if(equation.equation.length() >=  DIGIT_EQUATION_LIMIT) {
            return;
        }

        if (equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();
            equation.operators.add(op);
            equation.numbers.add("");
            equation.updateEquation();
        }
        else {

            equation.setLastOperator(op);
            equation.updateEquation();
        }
    }

    //----------------------------------
    //  Add Feet
    //----------------------------------
    /** Tries to append a feet symbol (apostrophe) to the equation if possible. */
    public static void addFeet() {

        if(equation.isOperatorNext() && equation.numbers.size() > 0) {

            // Don't allow them to add unit symbols on multiplications or divisions
            if(equation.mostRecentOperatorIsTimesOrDivide()) {
                return;
            }

            String s = equation.getLastNumber();

            if(s.length() > 0 && ! s.contains("\'") && ! s.contains("\"") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                equation.appendToLastNum("\'");
                equation.updateEquation();
            }

        }
    }

    //----------------------------------
    //  Add Inches
    //----------------------------------
    /** Tries to add an inches symbol (double quotes) to the equation if possible. */
    public static void addInches() {

        if(equation.isOperatorNext() && equation.numbers.size() > 0) {

            // Don't allow them to add unit symbols on multiplications or divisions
            if(equation.mostRecentOperatorIsTimesOrDivide()) {
                return;
            }

            String s = equation.getLastNumber();

            if(s.length() > 0 && ! s.contains("\"") &&
                    ! s.substring(s.length() - 1).equals("\'") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                equation.appendToLastNum("\"");
                equation.updateEquation();
            }
        }
    }

    //----------------------------------
    //  Add Decimal
    //----------------------------------
    /** Adds a decimal to the equation if possible. */
    public static void addDecimal() {

        String s = equation.getLastNumber();

        if ( ! s.contains(".") && ! s.contains("\'") && ! s.contains("\"")) {

            s = s.replaceAll(" Feet","");
            s = s.replaceAll(" Inches","");

            if (s.length() == 0) {
                s = "0";
            }

            equation.setLastNumber(s + ".");
            equation.updateEquation();
        }
    }

    //----------------------------------
    //  Backspace
    //----------------------------------
    public static void backspace() {

        if(equation.numbers.size() > 0) {

            equation.result = null;
            equation.convertUnitsToSymbols();
            String num = equation.getLastNumber();
            if(num.length() > 0) {

                int i = num.length()-1;

                // Ignore trailing spaces
                while(i > 0 && Character.isSpaceChar(num.charAt(i))) {
                    i--;
                }

                // Remove entire fraction by going back until you find a space.
                if(num.contains("/") && Character.isDigit(num.charAt(i))) {
                    while(i > 0 && ! Character.isSpaceChar(num.charAt(i))) {
                        i--;
                    }
                    if(i > 0)
                        i--; // Go back one more when you find a space because we have two spaces before fractions
                }
                equation.setLastNumber(num.substring(0,i));
            }
            else {
                if(equation.operators.size() > 0) {
                    equation.numbers.remove(equation.numbers.size()-1);
                    equation.operators.remove(equation.operators.size()-1);
                }
            }
            equation.updateEquation();
        }

    }

    //----------------------------------
    //  Add Fraction
    //----------------------------------
    public static void addFraction(String fraction){

        if(fraction.length() > 0) {
            if(equation.getLastNumber().length() > 0) {
                equation.appendToLastNum("  ");
            }
            equation.appendToLastNum(fraction + "\"");
            equation.updateEquation();
        }
    }
}