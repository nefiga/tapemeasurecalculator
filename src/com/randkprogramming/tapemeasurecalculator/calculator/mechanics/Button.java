package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import com.randkprogramming.tapemeasurecalculator.calculator.screens.FractionManualScreen;
import com.randkprogramming.tapemeasurecalculator.calculator.screens.FractionSixteenthScreen;
import com.randkprogramming.tapemeasurecalculator.calculator.screens.FractionThirtysecondScreen;
import com.randkprogramming.tapemeasurecalculator.calculator.screens.TabInfoScreen;
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

        @Override
        public void pressedButton() {
            CalcState.addNumber(ordinal());
        }

        @Override
        public void holdingButton() {
        }
    }

    //---------------------
    //  Operator Button
    //---------------------
    public static enum Operator implements Button {
        PLUS, MINUS, DIVIDE, TIMES;

        @Override
        public void pressedButton() {
            CalcState.addOperator(this);
        }

        @Override
        public void holdingButton() {
        }

        @Override
        public String toString() {

            switch (this) {
                case PLUS: {
                    return " + ";
                }
                case MINUS: {
                    return " - ";
                }
                case DIVIDE: {
                    return " ÷ ";
                }
                case TIMES: {
                    return " x ";
                }
                default: {
                    return "";
                }
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
            switch (this) {
                case FRACTION: {

                    // Prevent more than one fraction by only allowing you to press the button if...
                    if( ! CalcState.equation.getLastNumber().contains("\"") &&
                            ! CalcState.equation.getLastNumber().contains("/")) {
                        Calculator c = AndroidFastRenderView.getCalculator();
                        c.setScreen(new FractionManualScreen(c));
                    }

                    break;
                }
                case DECIMAL_POINT: {
                    CalcState.addDecimal();
                    break;
                }
                case EQUALS: {
                    CalculateEquation.solveEquation(CalcState.equation);
                    break;
                }
                case CLEAR: {
                    CalcState.equation.clear();
                    break;
                }
                case BACKSPACE: {
                    CalcState.backspace();
                    break;
                }
                case FEET: {
                    CalcState.addFeet();
                    break;
                }
            }
        }

        @Override
        public void holdingButton() {
        }
    }

    //---------------------
    //  Special Button
    //---------------------
    public static enum Special implements Button {
        FRACTION_OR_DECIMAL, FRACTION_PRECISION, DISPLAY_UNITS, INFO;

        @Override
        public void pressedButton() {

            switch (this) {
                case FRACTION_OR_DECIMAL: {
                    CalcState.equation.verifyUnits();
                    CalcState.fractionOrDecimal = CalcState.fractionOrDecimal.next();
                    CalcState.equation.updateDisplay();
                    break;
                }
                case FRACTION_PRECISION: {
                    if (CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
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
                    Calculator c = AndroidFastRenderView.getCalculator();
                    c.setScreen(new FractionManualScreen(c));
                    break;
                }
            }
        }

        @Override
        public void holdingButton() {
        }
    }

    public static enum ManualFractions implements Button {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, HALF, QUARTER, EIGHTH, SIXTEENTH,
        THIRTYSECOND, SIXTYFOURTH, BACK, CLEAR, ENTER;

        @Override
        public void pressedButton() {
            switch (this) {
                case ZERO:
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                    String f = "";
                    f += ordinal();
                    FractionManualScreen.addNumerator(f);
                    break;
                case HALF:
                    FractionManualScreen.addDenominator("/2");
                    break;
                case QUARTER:
                    FractionManualScreen.addDenominator("/4");
                    break;
                case EIGHTH:
                    FractionManualScreen.addDenominator("/8");
                    break;
                case SIXTEENTH:
                    FractionManualScreen.addDenominator("/16");
                    break;
                case THIRTYSECOND:
                    FractionManualScreen.addDenominator("/32");
                    break;
                case SIXTYFOURTH:
                    FractionManualScreen.addDenominator("/64");
                    break;
                case BACK:
                    FractionManualScreen.backspace();
                    break;
                case CLEAR:
                    FractionManualScreen.clear();
                    break;
                case ENTER:
                    FractionManualScreen.enter();
                    break;
            }
        }

        @Override
        public void holdingButton() {

        }
    }

    public static enum ThirtySeconds implements Button {
        ONE, THREE, FIVE, SEVEN, NINE, ELEVEN, THIRTEEN, FIFTEEN, SEVENTEEN, NINETEEN, TWENTY_ONE, TWENTY_THREE, TWENTY_FIVE, TWENTY_SEVEN, TWENTY_NINE, THIRTY_ONE;

        @Override
        public void pressedButton() {
            switch (this) {
                case ONE:   System.out.println("one thirtysecond"); break;
                case THREE: break;
                case FIVE:  break;
                case SEVEN: break;
                case NINE:  break;
                case ELEVEN:    break;
                case THIRTEEN:  break;
                case FIFTEEN:   break;
                case SEVENTEEN:     break;
                case NINETEEN:     break;
                case TWENTY_ONE:     break;
                case TWENTY_THREE:   break;
                case TWENTY_FIVE:    break;
                case TWENTY_SEVEN:   break;
                case TWENTY_NINE:  System.out.println("twenty nine thirtyseconds");  break;
                case THIRTY_ONE:      break;
            }
        }

        @Override
        public void holdingButton() {
        }
    }

    public static enum Sixteenths implements Button {
        ONE, THREE, FIVE, SEVEN, NINE, ELEVEN, THIRTEEN, FIFTEEN;

        @Override
        public void pressedButton() {
            switch (this) {
                case ONE:   break;
                case THREE: break;
                case FIVE: System.out.println("five sixteenths");  break;
                case SEVEN: break;
                case NINE:  break;
                case ELEVEN:    break;
                case THIRTEEN:  break;
                case FIFTEEN:  System.out.println("fifteen sixteenths"); break;
            }
        }

        @Override
        public void holdingButton() {
        }
    }

    public static enum OtherFractions implements Button {
        ONE_EIGHTH, THREE_EIGHTHS, FIVE_EIGHTHS, SEVEN_EIGHTHS, ONE_QUARTER, THREE_QUARTERS, ONE_HALF;

        @Override
        public void pressedButton() {
            switch (this) {
                case ONE_EIGHTH:    break;
                case THREE_EIGHTHS: break;
                case FIVE_EIGHTHS:  break;
                case SEVEN_EIGHTHS:     break;
                case ONE_QUARTER:   break;
                case THREE_QUARTERS:    break;
                case ONE_HALF: System.out.println("one half"); break;
            }
        }

        @Override
        public void holdingButton() {
        }
    }
}
