package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public class ButtonActions {


    private int currentFractionButton = CalculatorButtons.SIXTEENTH;

    public void sortButton(int button, int buttonState) {
        if (button == CalculatorButtons.FRACTION_CHANGE_BUTTON && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            setCurrentFractionButton();
        }
    }

    /**
     Changes whether the answer is shown in decimal or fraction format. If show in fraction format
     it will change how accurate the fractions will be(16ths, 32seconds, 64ths).
     */
    public void setCurrentFractionButton() {
        if (currentFractionButton == CalculatorButtons.SIXTEENTH) currentFractionButton = CalculatorButtons.THIRTYSECOND;
        else if(currentFractionButton == CalculatorButtons.THIRTYSECOND) currentFractionButton = CalculatorButtons.SIXTYFOURTH;
        else if(currentFractionButton == CalculatorButtons.SIXTYFOURTH) currentFractionButton = CalculatorButtons.DECIMAL;
        else currentFractionButton = CalculatorButtons.SIXTEENTH;
        System.out.println("CurrentFractionButton " + currentFractionButton);
    }

    public int getCurrentFractionButton() {
        return currentFractionButton;
    }
}
