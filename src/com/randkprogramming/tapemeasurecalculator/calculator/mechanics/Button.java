package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public interface Button {

    public abstract void pressedButton();
    public abstract void holdingButton();

    //---------------------
    //  Number Button
    //---------------------
    public static enum Number implements Button {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

        @Override public void pressedButton() { CalculatorState.addNumber(ordinal()); }
        @Override public void holdingButton(){}
    }

    //---------------------
    //  Operator Button
    //---------------------
    public static enum Operator implements Button {
        PLUS, MINUS, DIVIDE, TIMES;

        @Override public void pressedButton() { CalculatorState.addOperator(this); }
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
                case DECIMAL_POINT: { CalculatorState.addDecimal(); break; }
                case EQUALS: { CalculateEquation.solveEquation(); break; }
                case CLEAR: { CalculatorState.clear(); break; }
                case BACKSPACE: { break; } // TODO: Implement Backspace!
                case FEET: { CalculatorState.addFeet(); break; }
                case INCHES: { CalculatorState.addInches(); break; }
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
                    CalculatorState.verifyUnits();
                    CalculatorState.precisionMode = CalculatorState.precisionMode.next();
                    CalculateEquation.updateDisplay();
                    break;
                }
                case DISPLAY: {
                    CalculatorState.verifyUnits();
                    CalculatorState.displayMode = CalculatorState.displayMode.next();
                    CalculateEquation.updateDisplay();
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

    public static enum DisplayMode {
        INCHES_ONLY, FEET_AND_INCHES, DECIMAL;

        public DisplayMode next() { return values()[(ordinal() + 1) % values().length]; }
    }

    public static enum PrecisionMode {
        SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH;

        public PrecisionMode next() { return values()[(ordinal() + 1) % values().length]; }
    }

}
