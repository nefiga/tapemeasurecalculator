package com.RandNprograming.tapemeasurecalculator.calculator.buttons;

import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcHistory;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.DisplayModes;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.Operator;
import com.RandNprograming.tapemeasurecalculator.calculator.screens.FractionManualScreen;
import com.RandNprograming.tapemeasurecalculator.calculator.screens.InfoScreen;
import com.RandNprograming.tapemeasurecalculator.calculator.utilities.CalculateEquation;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Pixmap;

public interface ButtonAction {

    void performAction();
    Pixmap getIconPressed();

    //-----------------------
    //  Number Actions
    //-----------------------

    public enum NumberAction implements ButtonAction {
        ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE;

        @Override public void performAction() {
            CalcState.addNumber(ordinal());
        }

        @Override public Pixmap getIconPressed() {
            return Assets.pressed_buttons_numbers[ordinal()];
        }
    }

    //-----------------------
    //  Operator Actions
    //-----------------------
    public enum OperatorAction implements ButtonAction {
        PLUS, MINUS, DIVIDE, TIMES;


        @Override
        public void performAction() {
            CalcState.addOperator(Operator.values()[ordinal()]);
        }

        @Override
        public Pixmap getIconPressed() {
            return Assets.pressed_buttons_operators[ordinal()];
        }
    }

    //----------------------
    //  Calculate Actions
    //----------------------
    public enum CalculateAction implements ButtonAction {
        FRACTION, DECIMAL_POINT, EQUALS, CLEAR, BACKSPACE, FEET, INCHES;

        @Override
        public void performAction() {
            switch (this) {

                case FRACTION: {

                    String lastNum = CalcState.equation.getLastNumber();

                    // Only allow pressing fraction in certain cases...
                    if( ! lastNum.contains("\"") && ! lastNum.contains("/") && ! lastNum.contains(".") &&
                        ! lastNum.contains("ft") && ! lastNum.contains("in")) {
                        Calculator c = AndroidFastRenderView.getCalculator();
                        c.setScreen(new FractionManualScreen(c));
                    }

                    break;
                }
                case DECIMAL_POINT: { CalcState.addDecimal(); break; }
                case EQUALS: {
                    CalculateEquation.solveEquation(CalcState.equation, CalcState.orderOfOps);
                    CalcState.paint.update(CalcState.equation.getEquation());
                    break;
                }
                case CLEAR: { CalcState.clear(); break; }
                case BACKSPACE: { CalcState.backspace(); break; }
                case FEET: { CalcState.addFeet(); break; }
                case INCHES: { CalcState.addInches(); break; }
            }
        }

        @Override
        public Pixmap getIconPressed() {
            return Assets.pressed_buttons_calculate[ordinal()];
        }
    }

    //---------------------
    //  Special Actions
    //---------------------
    public static enum SpecialAction implements ButtonAction {
        FRACTION_OR_DECIMAL, FRACTION_PRECISION, DISPLAY_UNITS, INFO;

        public void performAction() {

            switch (this) {
                case FRACTION_OR_DECIMAL: {
                    CalcState.equation.verifyUnits();
                    CalcState.fractionOrDecimal = CalcState.fractionOrDecimal.next();
                    CalcState.equation.updateDisplay();
                    CalcState.paint.update(CalcState.equation.getEquation());
                    break;
                }
                case FRACTION_PRECISION: {
                    if (CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
                        CalcState.equation.verifyUnits();
                        CalcState.fractionPrecision = CalcState.fractionPrecision.next();
                        CalcState.equation.updateDisplay();
                        CalcState.paint.update(CalcState.equation.getEquation());
                    }
                    break;
                }
                case DISPLAY_UNITS: {
                    CalcState.equation.verifyUnits();
                    CalcState.displayUnits = CalcState.displayUnits.next();
                    CalcState.equation.updateDisplay();
                    CalcState.paint.update(CalcState.equation.getEquation());
                    break;
                }
                // TODO: Make Info/Options Page!
                case INFO: {
                    Calculator c = AndroidFastRenderView.getCalculator();
                    c.setScreen(new InfoScreen(c));
                    break;
                }
            }
        }

        @Override
        public Pixmap getIconPressed() {

            switch(this) {
                case FRACTION_OR_DECIMAL: {
                    return Assets.pressed_buttons_special[CalcState.fractionOrDecimal.ordinal()];
                }
                case FRACTION_PRECISION: {
                    return Assets.pressed_buttons_special[CalcState.fractionPrecision.ordinal()+2];
                }
                case DISPLAY_UNITS: {
                    return Assets.pressed_buttons_special[CalcState.displayUnits.ordinal()+5];
                }
                case INFO: {
                    return Assets.pressed_buttons_special[7];
                }
            }
            return null;
        }

    }

    //------------------------------
    //  Manual Fraction Action
    //------------------------------
    public static enum ManualFractionAction implements ButtonAction {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, FRACTION, BACK, CLEAR, ENTER;

        @Override
        public void performAction() {
            switch (this) {
                case ZERO: case ONE: case TWO: case THREE: case FOUR:
                case FIVE: case SIX: case SEVEN: case EIGHT: case NINE: {
                    FractionManualScreen.addNumber("" + ordinal()); break;
                }
                case FRACTION: FractionManualScreen.addForwardSlash(); break;
                case BACK: FractionManualScreen.backspace(); break;
                case CLEAR: FractionManualScreen.clear(); break;
                case ENTER: FractionManualScreen.enter(); break;
            }
        }

        @Override
        public Pixmap getIconPressed() {

            if(ordinal() < 10) {
                return Assets.pressed_buttons_numbers[ordinal()];
            }
            switch(this) {
                case CLEAR:{ return Assets.pressed_buttons_calculate[3]; }
                case BACK:{ return Assets.pressed_buttons_calculate[4]; }
                case FRACTION: { return Assets.pressed_buttons_fractionScreen[0]; }
                case ENTER:{ return Assets.pressed_buttons_fractionScreen[1]; }
            }
            return null;
        }

    }

    //------------------------------
    //  History Action
    //------------------------------
    public static enum HistoryAction implements ButtonAction {
        SAVE, ENTER;

        @Override
        public void performAction() {
            switch (this) {
                case ENTER: { CalcHistory.enter(); break; }
                case SAVE: {
                    break;
                }
            }
        }

        @Override
        public Pixmap getIconPressed() {
            return Assets.pressed_buttons_historyScreen[ordinal()];
        }

    }

}


/*
    public static enum ThirtySecondsActions implements ButtonAction {
        ONE, THREE, FIVE, SEVEN, NINE, ELEVEN, THIRTEEN, FIFTEEN, SEVENTEEN, NINETEEN,
        TWENTY_ONE, TWENTY_THREE, TWENTY_FIVE, TWENTY_SEVEN, TWENTY_NINE, THIRTY_ONE;

        @Override
        public void performAction() {
            switch (this) {
                case ONE:  break;
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
                case TWENTY_NINE:   break;
                case THIRTY_ONE:      break;
            }
        }
    }

    public static enum SixteenthsActions implements ButtonAction {
        ONE, THREE, FIVE, SEVEN, NINE, ELEVEN, THIRTEEN, FIFTEEN;

        @Override
        public void performAction() {
            switch (this) {
                case ONE:   break;
                case THREE: break;
                case FIVE:  break;
                case SEVEN: break;
                case NINE:  break;
                case ELEVEN:    break;
                case THIRTEEN:  break;
                case FIFTEEN:   break;
            }
        }
    }

    public static enum OtherFractionsActions implements ButtonAction {
        ONE_EIGHTH, THREE_EIGHTHS, FIVE_EIGHTHS, SEVEN_EIGHTHS, ONE_QUARTER, THREE_QUARTERS, ONE_HALF;

        @Override
        public void performAction() {
            switch (this) {
                case ONE_EIGHTH:    break;
                case THREE_EIGHTHS: break;
                case FIVE_EIGHTHS:  break;
                case SEVEN_EIGHTHS:     break;
                case ONE_QUARTER:   break;
                case THREE_QUARTERS:    break;
                case ONE_HALF:  break;
            }
        }
    }
    */