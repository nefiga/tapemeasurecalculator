package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

//----------------------------------
// Calculator State Class
//----------------------------------

/** This class is used to keep track of and to change the current state of the calculator. */
public class CalcState {

    //----------------------------------
    // Fields
    //----------------------------------
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

        if( ! s.contains("\"") && ! s.contains("Inches") && (! s.contains("Feet") || ! s.contains(".")) ) {

            s = s.replaceAll(" Feet","\'");

            if(s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
                s += " ";
            }

            s += n;
            equation.setLastNumber(s);
            equation.updateEquation();
        }

    }

    //----------------------------------
    //  Add Operator
    //----------------------------------
    /** Tries to append an operator to the equation if the current state of the calculator will allow it */
    public static void addOperator(Button.Operator op) {

        if (equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();
            equation.operators.add(op);
            equation.numbers.add("");
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

}