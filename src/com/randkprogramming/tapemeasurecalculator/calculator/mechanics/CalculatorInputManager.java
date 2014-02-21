package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public class CalculatorInputManager {

    CalculateEquation equation;
    ButtonActions buttonActions;
    FractionActions fractionActions;

    private boolean pressed = false, buttonHeld = false;
    private float buttonHeldTime = 0;
    private int pressedButton = CalculatorButtons.NONE;

    public CalculatorInputManager(CalculateEquation equation, ButtonActions buttonActions, FractionActions fractionActions) {
        this.equation = equation;
        this.buttonActions = buttonActions;
        this.fractionActions = fractionActions;
    }

    /**
        Sets <code>pressedButton</code> to the pressedButton that was pressed.
     * @param button (int) Button that was pressed.
     */
    public void setButtonPressed(int button) {
        buttonHeldTime = 0;
        pressedButton = button;
        pressed = true;
    }

    /**
        Checks if button that was released is the same as the button that was pressed.
     If so calls the method pressedButton(pressedButton) with the current pressedButton.
     * @param button (int) Button that was released.
     */
    public void setButtonReleased(int button) {
        buttonHeldTime = 0;
        pressed = false;

        if (releasedInBounds(button) && !buttonHeld) {
            pressedButton(button);
        }
        pressedButton = CalculatorButtons.NONE;
        buttonHeld = false;
    }

    /**
        Checks to see if the TouchEvent has moved outside of the bounds of the pressedButton that was pressed.
     Every second the TouchEvent stays within the bounds of the pressedButton pressed, holdingButton(pressedButton) is
     called with the current pressedButton.
     * @param button (int) Button where TouchEvent currently is.
     */
    public void setButtonDragged(int button) {
        if (button != pressedButton) {
            pressed = false;
            buttonHeldTime = 0;
            pressedButton = CalculatorButtons.NONE;
        }
    }

    /**
     Calls the method corresponding to the button that was pressed and released.
      */
    private void pressedButton(int button) {
        if (button < CalculatorButtons.CALCULATE_BUTTONS && button > CalculatorButtons.NONE) {
            equation.sortButton(button, CalculatorButtons.BUTTON_PRESSED);
        }
        else if (button == CalculatorButtons.FRACTION_CHANGE_BUTTON) {
            buttonActions.sortButton(button, CalculatorButtons.BUTTON_PRESSED);
        }
        else if (button < CalculatorButtons.FRACTION_BUTTONS && button > CalculatorButtons.FRACTION_CHANGE_BUTTON) {
            fractionActions.sortButton(button, CalculatorButtons.BUTTON_PRESSED);
        }
    }

    /**
     Calls the method corresponding to the button that is pressed and then held.
      */
    private void holdingButton(int button) {
        if (button < CalculatorButtons.CALCULATE_BUTTONS && button > CalculatorButtons.NONE) {
            equation.sortButton(button, CalculatorButtons.BUTTON_HELD);
        }
        else if (button == CalculatorButtons.FRACTION_CHANGE_BUTTON) {
            buttonActions.sortButton(button, CalculatorButtons.BUTTON_HELD);
        }
        else if (button < CalculatorButtons.FRACTION_BUTTONS) {
            fractionActions.sortButton(button, CalculatorButtons.BUTTON_HELD);
        }
    }

    /**
        Returns true if the button that was released is the same as the button that was pressed
     * @param button (int) Button that was pressed.
     * @return (boolean
     */
    private boolean releasedInBounds(int button) {
        if (pressedButton == button) {
            return true;
        }
        else {
            return false;
        }
    }

    public void update(float deltaTime) {
        if (pressed) buttonHeldTime += deltaTime;
        if (buttonHeldTime >= 1.5f) {
            holdingButton(pressedButton);
            buttonHeld = true;
            buttonHeldTime = 0;
        }

    }

}
