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
    public static PaintEquation paint = new PaintEquation();

    //----------------------------------
    //  Add Number
    //----------------------------------
    /** Tries to append a number to the equation if the current state of the calculator will allow it */
    public static void addNumber(int n) {

        String s = equation.getLastNumber();

        if( ! s.contains("\"") && ! s.contains("/") && ! s.contains("Inches") &&
                (! s.contains("Feet") || ! s.contains("."))) {

            if(equation.getEquation().length() < DIGIT_EQUATION_LIMIT && s.length() < DIGIT_LINE_LIMIT) {

                s = s.replaceAll(" Feet","\'");

                if(s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
                    s += " ";
                }

                s += n;
                equation.setLastNumber(s);
                equation.updateEquation();
                paint.update(equation.getEquation());
            }

        }

    }

    //----------------------------------
    //  Add Operator
    //----------------------------------
    /** Tries to append an operator to the equation if the current state of the calculator will allow it */
    public static void addOperator(Button.Operator op) {

        if(equation.getEquation().length() >=  DIGIT_EQUATION_LIMIT) {
            return;
        }

        if (equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();
            equation.getOperators().add(op);
            equation.getNumbers().add("");
        }
        else {
            equation.setLastOperator(op);
        }

        equation.updateEquation();
        paint.update(equation.getEquation());
    }

    //----------------------------------
    //  Add Feet
    //----------------------------------
    /** Tries to append a feet symbol (apostrophe) to the equation if possible. */
    public static void addFeet() {

        if(equation.isOperatorNext() && equation.getNumbers().size() > 0) {

            // Don't allow them to add unit symbols on multiplications or divisions
            if(equation.mostRecentOperatorIsTimesOrDivide()) {
                return;
            }

            String s = equation.getLastNumber();

            if(s.length() > 0 && ! s.contains("\'") && ! s.contains("\"") &&
                    ! s.contains("Feet") && ! s.contains("Inches")) {

                equation.appendToLastNum("\'");
                equation.updateEquation();
                paint.update(equation.getEquation());
            }

        }
    }

    //----------------------------------
    //  Add Inches
    //----------------------------------
    /** Tries to add an inches symbol (double quotes) to the equation if possible. */
    public static void addInches() {

        if(equation.isOperatorNext() && equation.getNumbers().size() > 0) {

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
                paint.update(equation.getEquation());
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
            paint.update(equation.getEquation());
        }
    }

    //----------------------------------
    //  Backspace
    //----------------------------------
    public static void backspace() {

        if(equation.getNumbers().size() > 0) {

            equation.setResult(null);
            equation.convertUnitsToSymbols();
            String num = equation.getLastNumber();
            if(num.length() > 0) {

                int i = num.length()-1;

                // Ignore trailing spaces
                while(i > 0 && Character.isSpaceChar(num.charAt(i))) {
                    i--;
                }

                if(num.contains("\'") && num.charAt(i) == '"') {
                    i--;
                }

                // Special case for  0.  then remove the zero as well
                if(num.charAt(i) == '.' && num.length() == 2 && num.charAt(i-1) == '0') {
                    i--;
                }

                // Remove entire fraction by going back until you find a space.
                if(num.contains("/")) {
                    while(i > 0 && ! Character.isSpaceChar(num.charAt(i))) {
                        i--;
                    }
                }

                // Remove extra spaces
                while(i > 0 && Character.isSpaceChar(num.charAt(i-1))) {
                    i--;
                }

                equation.setLastNumber(num.substring(0,i));
            }
            else {
                if(equation.getOperators().size() > 0) {
                    equation.getNumbers().remove(equation.getNumbers().size()-1);
                    equation.getOperators().remove(equation.getOperators().size()-1);
                }
            }
            equation.updateEquation();
            paint.update(equation.getEquation());
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
            paint.update(equation.getEquation());
        }
    }



}