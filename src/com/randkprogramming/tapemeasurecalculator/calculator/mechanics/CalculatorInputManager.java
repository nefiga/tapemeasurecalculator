package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import com.randkprogramming.tapemeasurecalculator.calculator.buttons.Button;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;

import java.util.HashMap;
import java.util.Map;

//----------------------------------
// Calculator Input Manager
//----------------------------------

/**
 * This class handles the input of the touch screen. It allows multi-touches to occur. Only fires button actions
 * when the user releases their finger on the same button that that finger originally pressed on. If the user
 * drags away from the button, the button will not look pressed down. If they happen to drag their finger back
 * onto the button, it will look pressed down.
 */
public class CalculatorInputManager {

    /** Maps TouchEvent ids to the Button that was originally pressed */
    private Map<Integer,Button> pressedButtons = new HashMap<Integer,Button>();
    public Map<Integer,Button> getPressedButtons() { return this.pressedButtons; }

    /** Makes the button in the 'pressed down' state which means it will display the
     * pressed down icon and nothing else.
     * @param event The motion event being fired.
     * @param button The button the user pressed down on. */
    public void onTouchDown(Input.TouchEvent event, Button button) {

        if( ! pressedButtons.containsKey(event.pointer)) {
            pressedButtons.put(event.pointer, button);
            button.setPressedDown(true);
        }
    }

    /** Called when the user lifts their finger off the screen. Checks the bounds, if the button
     * it started on and ended on are the same, the button perform action method is called.
     * Removes it from the list of currently pressed buttons either way. */
    public void onLift(Input.TouchEvent event) {

        if(pressedButtons.containsKey(event.pointer)) {
            Button button = pressedButtons.get(event.pointer);

            if(button.inBounds(event)) {
                button.performAction();
            }

            button.setPressedDown(false);
            pressedButtons.remove(event.pointer);
        }
    }

    /** Called while the user is dragging. Checks the bounds. If it stays within bounds,
     * the button is considered 'pressed down' (which means it will display the
     * pressed down icon and nothing else.) Otherwise it is 'unpressed' which means it doesn't
     * display the pressed down icon. */
    public void onMovement(Input.TouchEvent event) {

        if (pressedButtons.containsKey(event.pointer)) {
            Button button = pressedButtons.get(event.pointer);
            if(button.inBounds(event)) {
                button.setPressedDown(true);
            }
            else {
                button.setPressedDown(false);
            }
        }

    }

}