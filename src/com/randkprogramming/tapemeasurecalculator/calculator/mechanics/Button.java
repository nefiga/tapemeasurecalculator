package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public interface Button {

    public abstract void pressedButton();
    public abstract void holdingButton();

    //---------------------
    //  Number Button
    //---------------------
    public static enum Number implements Button {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

        @Override public void pressedButton() { CalcState.addNumber(ordinal()); }
        @Override public void holdingButton(){}
    }

    //---------------------
    //  Operator Button
    //---------------------
    public static enum Operator implements Button {
        PLUS, MINUS, DIVIDE, TIMES;

        @Override public void pressedButton() { CalcState.addOperator(this); }
        @Override public void holdingButton(){}
        @Override public String toString() {

            switch(this) {
                case PLUS: { return " + "; }
                case MINUS: { return " - "; }
                case DIVIDE: { return " ÷ "; }
                case TIMES: { return " x "; }
                default: { return ""; }
            }
        }
    }

    //---------------------
    //  Calculate Button
    //---------------------
    public static enum Calculate implements Button {
        DECIMAL_POINT, EQUALS, CLEAR, BACKSPACE, FEET, INCHES;

        @Override
        public void pressedButton() {
            switch(this) {
                case DECIMAL_POINT: { CalcState.addDecimal(); break; }
                case EQUALS: { CalculateEquation.solveEquation(CalcState.equation); break; }
                case CLEAR: { CalcState.equation.clear(); break; }
                case BACKSPACE: { break; } // TODO: Implement Backspace!
                case FEET: { CalcState.addFeet(); break; }
                case INCHES: { CalcState.addInches(); break; }
            }
        }
        @Override public void holdingButton(){}
    }

    //---------------------
    //  Special Button
    //---------------------
    public static enum Special implements Button {
        FRACTION, PRECISION, DISPLAY, INFO;

        @Override
        public void pressedButton() {
            switch(this) {
                case FRACTION: { break; } // TODO: Make Fractions Page!
                case PRECISION: {
                    CalcState.equation.verifyUnits();
                    CalcState.precisionMode = CalcState.precisionMode.next();
                    CalcState.equation.updateDisplay();
                    break;
                }
                case DISPLAY: {
                    CalcState.equation.verifyUnits();
                    CalcState.displayMode = CalcState.displayMode.next();
                    CalcState.equation.updateDisplay();
                    break; }
                case INFO: { break; } // TODO: Make Info/Options Page!
            }
        }
        @Override public void holdingButton(){}
    }

    //----------------------------------------------------------------
    //  The following are not buttons, instead, they are values that
    //  certain buttons will use to keep track of it's current state.
    //----------------------------------------------------------------

    //---------------------
    //  Display Mode
    //---------------------
    public static enum DisplayMode {
        INCHES_ONLY, FEET_AND_INCHES;

        public DisplayMode next() { return values()[(ordinal() + 1) % values().length]; }
    }

    //---------------------
    //  Precision Mode
    //---------------------
    public static enum PrecisionMode {
        SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH, DECIMAL;

        public PrecisionMode next() { return values()[(ordinal() + 1) % values().length]; }

        /** Returns a fraction object according to the currently selected precision button.
         * @return The corresponding precision in the form of a Fraction object. */
        public Fraction getFraction() {

            switch (this) {
                case SIXTEENTH: return new Fraction(1, 16);
                case THIRTY_SECOND: return new Fraction(1, 32);
                case SIXTY_FOURTH: return new Fraction(1, 64);
            }
            return null;
        }
    }

}
