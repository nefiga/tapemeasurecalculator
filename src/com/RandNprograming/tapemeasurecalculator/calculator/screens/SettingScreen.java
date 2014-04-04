package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.Button;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.DisplayModes;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class SettingScreen extends Screen{

    private CalculatorInputManager manager = new CalculatorInputManager();

    public SettingScreen(Calculator calculator) {
        super(calculator);
    }

    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {

            Input.TouchEvent event = touchEvents.get(i);
            if(event != null) {
                checkTouchEvent(event);
            }
        }
    }

    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
    }

    /** Checks to see if the event fired is in bounds of a button. If it is, then it lets
     * the manager know which button was pressed or released.
     * @param event The event being fired. */
    private void checkTouchEvent(Input.TouchEvent event) {

        switch(event.type) {
            case Input.TouchEvent.TOUCH_DOWN: {
                for(int i = 0; i < ButtonLayout.mainScreenButtons.size(); i++) {

                    Button button = ButtonLayout.mainScreenButtons.get(i);

                    // Don't press fraction precision button on decimal mode
                    if(CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.DECIMAL_OPTION &&
                            (button.getIconPressed() == Assets.pressed_buttons_special[2] ||
                                    button.getIconPressed() == Assets.pressed_buttons_special[3] ||
                                    button.getIconPressed() == Assets.pressed_buttons_special[4])) {
                        continue;
                    }

                    if(button.inBounds(event)) {
                        manager.onTouchDown(event, button);
                        return;
                    }
                }
                break;
            }
            case Input.TouchEvent.TOUCH_UP:      manager.onLift(event); break;
            case Input.TouchEvent.TOUCH_DRAGGED: manager.onMovement(event); break;
        }
    }

    public void pause() {}
    public void resume() {}
    public void dispose() {}
    public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
