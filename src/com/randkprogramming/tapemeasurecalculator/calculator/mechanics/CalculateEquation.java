package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.calculator.Fraction;

import java.text.DecimalFormat;

public class CalculateEquation {

    public static void updateDisplay() {

        if(CalculatorState.numbers.size() == 1) {

            if(CalculatorState.lastResult == null) {
                CalculatorState.lastResult = parseNumber(CalculatorState.numbers.get(0));
            }
            CalculatorState.numbers.set(0, formatNumber(CalculatorState.lastResult));
        }
    }


    /**
     * Builds the equation string from the numbers and operators currently stored.
     */
    public static String buildString() {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < CalculatorState.numbers.size(); i++) {

            sb.append(CalculatorState.numbers.get(i));

            if(i < CalculatorState.operators.size()) {
                sb.append(CalculatorState.operators.get(i).toString());
            }
        }
        return sb.toString();
    }

    /**
     * Figures the order of operation. Then solves the equation. The equation must end with numbers.
     * This method will do nothing if the equation ends with an operator.
     */
    public static void solveEquation() {

        if ( CalculatorState.isOperatorNext()) {

            CalculatorState.verifyUnits();

            multiplyAndDivide();
            addAndSubtract();

            String answer = CalculatorState.numbers.get(0);
            CalculatorState.clear();
            double answerDouble = parseNumber(answer);
            CalculatorState.numbers.set(0, formatNumber(answerDouble));
            CalculatorState.lastResult = answerDouble;
        }
    }

    /**
     * Runs through numbers and operators and performs multiplication and division and shifts
     * them to the left each time an operation happens.
     */
    private static void multiplyAndDivide() {

        for (int i = 0; i < CalculatorState.operators.size(); i++) {

            double first = parseNumber(CalculatorState.numbers.get(i));
            double second = parseNumber(CalculatorState.numbers.get(i + 1));

            switch(CalculatorState.operators.get(i)) {

                case DIVIDE: { CalculatorState.numbers.set(i, "" + (first / second) + "\""); break; }
                case  TIMES: { CalculatorState.numbers.set(i, "" + (first * second) + "\""); break; }
                default: { continue; }
            }

            CalculatorState.numbers.remove(i + 1);
            CalculatorState.operators.remove(i);
            i--;
        }
    }

    /**
     * Runs through numbers and operators and performs addition and subtraction and shifts
     * them to the left each time an operation happens.
     */
    private static void addAndSubtract() {

        for (int i = 0; i < CalculatorState.operators.size(); i++) {

            double first = parseNumber(CalculatorState.numbers.get(i));
            double second = parseNumber(CalculatorState.numbers.get(i + 1));

            switch(CalculatorState.operators.get(i)) {

                case  PLUS: { CalculatorState.numbers.set(i, "" + (first + second) + "\"" ); break; }
                case MINUS: { CalculatorState.numbers.set(i, "" + (first - second) + "\"" ); break; }
                default: { continue; }
            }

            CalculatorState.numbers.remove(i + 1);
            CalculatorState.operators.remove(i);
            i--;
        }
    }

    /**
     * Returns a fraction object according to the currently selected precision button.
     * @return The corresponding precision in the form of a Fraction object.
     */
    public static Fraction getPrecision() {

        switch (CalculatorState.precisionMode) {
            case SIXTEENTH: return new Fraction(1, 16);
            case THIRTY_SECOND: return new Fraction(1, 32);
            case SIXTY_FOURTH: return new Fraction(1, 64);
        }
        return null;
    }

    /**
     * Converts a number into a string of text based on the currently selected display mode.
     * (Inches only, Feet + Inches, or Decimal)
     * @param number The number that needs to be converted
     * @return The formatted result
     */
    public static String formatNumber(double number) {

        final int MAX_DECIMAL_DIGITS = 6;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(MAX_DECIMAL_DIGITS);

        String text = "";

        if(CalculatorState.precisionMode == Button.PrecisionMode.DECIMAL) {
            if(CalculatorState.displayMode == Button.DisplayMode.FEET_AND_INCHES) {
                number /= 12;
            }
            text = df.format(number);
            text += (CalculatorState.displayMode == Button.DisplayMode.FEET_AND_INCHES) ? "\'" : "\"";
        }
        else {
            if(CalculatorState.displayMode == Button.DisplayMode.FEET_AND_INCHES) {

                // Parse Feet
                int feet = (int) number / 12;
                number -= feet * 12;
                text += feet;
                text += "\' ";
            }

            // Parse Inches
            int inches = (int) number;
            number -= inches;
            text += inches;

            // Parse Decimal
            String decimal = Fraction.getFractionStringFromDecimal(number,getPrecision());
            if(decimal.length() > 0) {
                text += "  ";
                text += decimal;
            }
            text += "\"";
        }

        return text;
    }

    /**
     * Converts a string into a decimal value that can be stored and perform operations on.
     * This method will take into account decimals, fractions, feet, and inches.
     * @param s The string that needs to be converted
     * @return The converted result
     */
    public static double parseNumber(String s) {

        // Assumptions:
        // Ft. symbol always there ' when describing feet.
        // No space between number and feet symbol.
        // boolean readingFraction will be turned on when I see the / mark

        double value = 0;
        String read = "";
        Fraction f = new Fraction();
        boolean readingFraction = false;

        for(int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if(Character.isSpaceChar(c)) {

                if(read.length() > 0) {
                    value += Double.parseDouble(read);
                    read = "";
                }
                continue;
            }

            switch (c) {
                case '\'': {
                    value += Double.parseDouble(read) * 12;
                    read = "";
                    break;
                }
                case '\"': {
                    if(readingFraction) {
                        f = new Fraction(f.getNumerator(), Integer.parseInt(read));
                        value += f.getDecimalRepresentation();
                    }
                    else {
                        value += Double.parseDouble(read);
                        read = "";
                    }
                    break;
                }
                case '/': {
                    readingFraction = true;
                    f = new Fraction(Integer.parseInt(read), 1); // Denominator will get set later
                    read = "";
                    break;
                }
                default: {
                    read += c;
                    break;
                }
            }
        }

        return value;
    }

}
