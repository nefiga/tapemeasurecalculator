package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public class CalculatorInputManager {

    private boolean pressed = false, buttonHeld = false;
    private float buttonHeldTime = 0;
    private Button pressedButton = null;

    /**
        Sets <code>pressedButton</code> to the pressedButton that was pressed.
     * @param button (int) Button that was pressed.
     */
    public void setButtonPressed(Button button) {
        buttonHeldTime = 0;
        pressedButton = button;
        pressed = true;
    }

    /**
        Checks if button that was released is the same as the button that was pressed.
     If so calls the method pressedButton(pressedButton) with the current pressedButton.
     * @param button (int) Button that was released.
     */
    public void setButtonReleased(Button button) {
        buttonHeldTime = 0;
        pressed = false;

        if (releasedInBounds(button) && ! buttonHeld) {
            button.pressedButton();
        }

        pressedButton = null;
        buttonHeld = false;
    }

    /**
        Checks to see if the TouchEvent has moved outside of the bounds of the pressedButton that was pressed.
     Every second the TouchEvent stays within the bounds of the pressedButton pressed, holdingButton(pressedButton) is
     called with the current pressedButton.
     * @param button (int) Button where TouchEvent currently is.
     */
    public void setButtonDragged(Button button) {
        if (button != pressedButton) {
            pressed = false;
            buttonHeldTime = 0;
            pressedButton = null;
        }
    }

    /**
        Returns true if the button that was released is the same as the button that was pressed
     * @param button (int) Button that was pressed.
     * @return (boolean)
     */
    private boolean releasedInBounds(Button button) {
        return pressedButton == button;
    }

    /**
     * Updates the time for button holding.
     * @param deltaTime The amount of time that has elapsed
     */
    public void updateHoldTime(float deltaTime) {
        if (pressed) buttonHeldTime += deltaTime;
        if (buttonHeldTime >= 1.5f) {
            pressedButton.holdingButton();
            buttonHeld = true;
            buttonHeldTime = 0;
        }

    }

}