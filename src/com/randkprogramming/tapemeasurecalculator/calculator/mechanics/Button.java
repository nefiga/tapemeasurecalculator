package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import com.randkprogramming.tapemeasurecalculator.calculator.screens.HistoryScreen;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;

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
        FRACTION, DECIMAL_POINT, EQUALS, CLEAR, BACKSPACE, FEET, INCHES;

        @Override
        public void pressedButton() {
            switch(this) {
                case FRACTION: { break; }  // TODO: Make Fractions Page!
                case DECIMAL_POINT: { CalcState.addDecimal(); break; }
                case EQUALS: { CalculateEquation.solveEquation(CalcState.equation); break; }
                case CLEAR: { CalcState.equation.clear(); break; }
                case BACKSPACE: { CalcState.backspace(); break; }
                case FEET: { CalcState.addFeet(); break; }
            }
        }
        @Override public void holdingButton(){}
    }

    //---------------------
    //  Special Button
    //---------------------
    public static enum Special implements Button {
        FRACTION_OR_DECIMAL, FRACTION_PRECISION, DISPLAY_UNITS, INFO;

        @Override
        public void pressedButton() {

            switch(this) {
                case FRACTION_OR_DECIMAL: {
                    CalcState.equation.verifyUnits();
                    CalcState.fractionOrDecimal = CalcState.fractionOrDecimal.next();
                    CalcState.equation.updateDisplay();
                    break;
                }
                case FRACTION_PRECISION: {
                    if(CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
                        CalcState.equation.verifyUnits();
                        CalcState.fractionPrecision = CalcState.fractionPrecision.next();
                        CalcState.equation.updateDisplay();
                    }
                    break;
                }
                case DISPLAY_UNITS: {
                    CalcState.equation.verifyUnits();
                    CalcState.displayUnits = CalcState.displayUnits.next();
                    CalcState.equation.updateDisplay();
                    break;
                }
                // TODO: Make Info/Options Page!
                case INFO: {
                    break;
                }
            }
        }
        @Override public void holdingButton(){}
    }

}
