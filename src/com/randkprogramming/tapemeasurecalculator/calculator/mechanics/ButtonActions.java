package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public class ButtonActions {

    private int currentFractionButton = CalculatorButtons.SIXTEENTH;
    private int currentDisplayButton = CalculatorButtons.DISPLAY_FEET_AND_INCHES;

    public void sortButton(int button, int buttonState) {
        if (button == CalculatorButtons.FRACTION_CHANGE_BUTTON && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            setCurrentFractionButton();
        }
        if (button == CalculatorButtons.DISPLAY_CHANGE_BUTTON && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            setCurrentDisplayButton();

        }
    }

    /**
     Changes whether the displayed answer is rounded to the nearest 16ths, 32seconds, or 64ths).
     */
    public void setCurrentFractionButton() {
        if (currentFractionButton == CalculatorButtons.SIXTEENTH) currentFractionButton = CalculatorButtons.THIRTYSECOND;
        else if(currentFractionButton == CalculatorButtons.THIRTYSECOND) currentFractionButton = CalculatorButtons.SIXTYFOURTH;
        else currentFractionButton = CalculatorButtons.SIXTEENTH;
        System.out.println("CurrentFractionButton " + currentFractionButton);
    }

    /**
     Changes whether the answer is shown in inches only, feet and inches, or decimal format.
     */
    public void setCurrentDisplayButton() {
        if (currentDisplayButton == CalculatorButtons.DISPLAY_INCHES_ONLY) currentDisplayButton = CalculatorButtons.DISPLAY_FEET_AND_INCHES;
        else if(currentDisplayButton == CalculatorButtons.DISPLAY_FEET_AND_INCHES) currentDisplayButton = CalculatorButtons.DECIMAL;
        else currentDisplayButton = CalculatorButtons.DISPLAY_INCHES_ONLY;
        System.out.println("CurrentDisplayButton " + currentDisplayButton);
    }

    public int getCurrentFractionButton() {
        return currentFractionButton;
    }
    public int getCurrentDisplayButton() {
        return currentDisplayButton;
    }
}
