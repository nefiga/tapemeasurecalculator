package com.RandNprograming.tapemeasurecalculator.calculator.mechanics;

//----------------------------------
// Calculator State Class
//----------------------------------

/**
 * This class is used to keep track of and to change the current state of the calculator.
 */
public class CalcState {

    public static final int NORMAL = 0, SQUARED = 1, CUBED = 2;
    private static int inches = NORMAL, feet = NORMAL;

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
    public static boolean orderOfOps = true;

    //----------------------------------
    //  Add Number
    //----------------------------------

    /**
     * Tries to append a number to the equation if the current state of the calculator will allow it
     */
    public static void addNumber(int n) {

        String s = equation.getLastNumber();

        if (!s.contains("\"") && !s.contains("/") && !s.contains("Inches") &&
                (!s.contains("Feet") || !s.contains(".")) && !s.contains("ft")) {

            if (s.contains("\'") && s.contains(".")) {
                return;
            }

            if (equation.getEquation().length() < DIGIT_EQUATION_LIMIT && s.length() < DIGIT_LINE_LIMIT) {

                s = s.replaceAll(" Feet", "\'");

                if (s.length() > 0 && (s.substring(s.length() - 1).equals("\'"))) {
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

    /**
     * Tries to append an operator to the equation if the current state of the calculator will allow it
     */
    public static void addOperator(Operator op) {

        if (equation.getEquation().length() >= DIGIT_EQUATION_LIMIT) {
            return;
        }

        // Limits them to only one operator for now.
//        if(equation.getOperators().size() >= 1) {
//            return;
//        }

        if (equation.isOperatorNext()) {

            feet = NORMAL;
            inches = NORMAL;
            equation.convertUnitsToSymbols();
            equation.getOperators().add(op);
            equation.verifyUnits();
            equation.getNumbers().add("");
        } else {
            equation.setLastOperator(op);
        }

        equation.updateEquation();
        paint.update(equation.getEquation());
    }

    //----------------------------------
    //  Add Feet
    //----------------------------------

    /**
     * Tries to append a feet symbol (apostrophe) to the equation if possible.
     */
    public static void addFeet() {

        if (equation.isOperatorNext() && equation.getNumbers().size() > 0) {

            // Don't allow them to add unit symbols on multiplications or divisions
            /*if(equation.mostRecentOperatorIsTimesOrDivide()) {
                return;
            }*/

            String s = equation.getLastNumber();

            if (s.length() > 0 && !s.contains("\'") && !s.contains("\"") &&
                    !s.contains("Feet") && !s.contains("Inches") &&
                    !s.contains("ft") && !s.contains("in")) {

                // Remove trailing period if there are no digits after it
                if (s.contains(".") && s.charAt(s.length() - 1) == '.') {
                    s = s.substring(0,s.length()-1);
                    equation.setLastNumber(s);
                }

                equation.appendToLastNum("\'");
                equation.updateEquation();
                paint.update(equation.getEquation());
            }

        }
    }

    //----------------------------------
    //  Add Inches
    //----------------------------------

    /**
     * Tries to add an inches symbol (double quotes) to the equation if possible.
     */
    public static void addInches() {

        if (equation.isOperatorNext() && equation.getNumbers().size() > 0) {

            String s = equation.getLastNumber();

            if (s.length() > 0 && !s.contains("\"") &&
                    !s.substring(s.length() - 1).equals("\'") &&
                    !s.contains("Feet") && !s.contains("Inches") &&
                    !s.contains("ft") && !s.contains("in")) {

                // Remove trailing period if there are no digits after it
                if (s.contains(".") && s.charAt(s.length() - 1) == '.') {
                    s = s.substring(0,s.length()-1);
                    equation.setLastNumber(s);
                }

                equation.appendToLastNum("\"");
                equation.updateEquation();
                paint.update(equation.getEquation());
            }
        }
    }

    public static void cycleFeet() {

        String s = equation.getLastNumber();

        if(s.contains("\'") && s.charAt(s.length()-1) != '\'') {
            return;
        }

        // Replace inches with feet if it is the last one (like how the operators do, they replace eachother)
        if( ! s.contains("/") && s.contains("\"") && s.charAt(s.length()-1) == '\"') {
            inches = NORMAL;
            s = s.replaceAll("\"","");
        }
        else if(s.contains(" in2") && s.substring(s.length() - 4).equals(" in2")) {
            inches = NORMAL;
            s = s.replaceAll(" in2","");
        }
        else if(s.contains(" in3") && s.substring(s.length() - 4).equals(" in3")) {
            inches = NORMAL;
            s = s.replaceAll(" in3","");
        }

        equation.setLastNumber(s);

        if (feet == NORMAL && s.contains("\'")) {

            feet = SQUARED;
            s = s.replaceAll("\'"," ft2");
            equation.setLastNumber(s);
        }
        else if (feet == SQUARED && s.contains("ft2")) {

            feet = CUBED;
            s = s.replaceAll(" ft2"," ft3");
            equation.setLastNumber(s);
        }
        else if (feet == CUBED && s.contains("ft3")) {

            feet = NORMAL;
            s = s.replaceAll(" ft3","\'");
            equation.setLastNumber(s);
        }
        else {
            addFeet();
        }

        equation.updateEquation();
        paint.update(equation.getEquation());
    }

    public static void cycleInches() {

        String s = equation.getLastNumber();

        // Replace foot with inches if it is the last one (kind of how the operators do the same thing, they replace eachother)
        if(s.contains("'") && s.charAt(s.length()-1) == '\'') {
            feet = NORMAL;
            s = s.replaceAll("\'","");
        }
        else if(s.contains(" ft2") && s.substring(s.length() - 4).equals(" ft2")) {
            feet = NORMAL;
            s = s.replaceAll(" ft2","");
        }
        else if(s.contains(" ft3") && s.substring(s.length() - 4).equals(" ft3")) {
            feet = NORMAL;
            s = s.replaceAll(" ft3","");
        }

        equation.setLastNumber(s);

        if (inches == NORMAL && s.contains("\"") && ! s.contains("\'") && ! s.contains("/")) {

            inches = SQUARED;
            s = s.replaceAll("\""," in2");
            equation.setLastNumber(s);
        }
        else if (inches == SQUARED && s.contains("in2")) {

            inches = CUBED;
            s = s.replaceAll(" in2"," in3");
            equation.setLastNumber(s);
        }
        else if (inches == CUBED && s.contains("in3")) {

            inches = NORMAL;
            s = s.replaceAll(" in3","\"");
            equation.setLastNumber(s);
        }
        else {
            addInches();
        }

        equation.updateEquation();
        paint.update(equation.getEquation());
    }

    //----------------------------------
    //  Add Decimal
    //----------------------------------

    /**
     * Adds a decimal to the equation if possible.
     */
    public static void addDecimal() {

        String s = equation.getLastNumber();

        if (!s.contains(".") && !s.contains("\'") && !s.contains("\"") && !s.contains("ft") && !s.contains("in")) {

            s = s.replaceAll(" Feet", "");
            s = s.replaceAll(" Inches", "");

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

        if (equation.getNumbers().size() > 0) {

            equation.setResult(null);
            equation.convertUnitsToSymbols();
            String num = equation.getLastNumber();
            if (num.length() > 0) {

                int i = num.length() - 1;

                // Ignore trailing spaces
                while (i > 0 && Character.isSpaceChar(num.charAt(i))) {
                    i--;
                }

                inches = NORMAL;
                feet = NORMAL;

                // Special case for  0.  then remove the zero as well
                if (num.charAt(i) == '.' && num.length() == 2 && num.charAt(i - 1) == '0') {
                    i--;
                }

                // Remove entire fraction by going back until you find a space.
                if (num.contains("/")) {
                    while (i > 0 && !Character.isSpaceChar(num.charAt(i))) {
                        i--;
                    }
                }

                // Remove entire word of dimensional units by going back until you find a space.
                if (num.contains("ft") || num.contains("in")) {
                    while (i > 0 && !Character.isSpaceChar(num.charAt(i))) {
                        i--;
                    }
                }

                // Remove extra spaces
                while (i > 0 && Character.isSpaceChar(num.charAt(i - 1))) {
                    i--;
                }

                equation.setLastNumber(num.substring(0, i));
            } else {
                if (equation.getOperators().size() > 0) {
                    equation.getNumbers().remove(equation.getNumbers().size() - 1);
                    equation.getOperators().remove(equation.getOperators().size() - 1);

                    if(equation.getLastNumber().contains("ft2")) {
                        feet = SQUARED;
                    }
                    else if(equation.getLastNumber().contains("ft3")) {
                        feet = CUBED;
                    }
                    else if(equation.getLastNumber().contains("in2")) {
                        inches = SQUARED;
                    }
                    else if(equation.getLastNumber().contains("in3")) {
                        inches = CUBED;
                    }
                }
            }
            equation.updateEquation();
            paint.update(equation.getEquation());
        }

    }

    public static void clear() {
        inches = NORMAL;
        feet = NORMAL;
        equation.clear();
        paint.update(equation.getEquation());
    }

    //----------------------------------
    //  Add Fraction
    //----------------------------------
    public static void addFraction(String fraction) {

        if (fraction.length() > 0) {

            String lastNum = equation.getLastNumber();

            // Remove inches symbol to allow adding a fraction if a fraction isn't already there.
            if( lastNum.contains("\"") && ! lastNum.contains("/") ) {
                lastNum = lastNum.replaceAll("\"","");
                equation.setLastNumber(lastNum);
            }

            if (equation.getLastNumber().length() > 0) {

                equation.appendToLastNum("  ");
            }
            equation.appendToLastNum(fraction + "\"");
            equation.updateEquation();
            paint.update(equation.getEquation());
        }
    }
}