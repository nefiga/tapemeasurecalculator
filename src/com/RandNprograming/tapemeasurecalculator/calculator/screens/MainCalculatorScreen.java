package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import android.app.Activity;
import android.view.Menu;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.Button;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input.TouchEvent;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;
import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.*;

import java.util.List;

public class MainCalculatorScreen extends Screen {

    //--------------------------
    // Fields
    //--------------------------
    private CalculatorInputManager manager = new CalculatorInputManager();
    private float debugTimer = 0;

    //--------------------------
    // Constructor
    //--------------------------
    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
    }

    public void printDebugStatements(float deltaTime) {

        debugTimer += deltaTime;
        if (debugTimer >= 5.0f) {
            debugTimer = 0;

            System.out.println(CalcHistory.print());
        }
    }

    @Override
    public void update(float deltaTime) {
//        printDebugStatements(deltaTime);

        List<TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {

            TouchEvent event = touchEvents.get(i);
            if(event != null) {
                checkTouchEvent(event);
            }
        }
    }

    @Override
    public void present(float deltaTime) {

        // Draw Images
        Graphics g = calculator.getGraphics();

        g.clear(0xffffff);
        g.drawPixmap(Assets.main_screen, 0, 0);
        drawEquation(g);

        g.drawPixmap(Assets.fractionOrDecimal[CalcState.fractionOrDecimal.ordinal()], 68, 1135);
        if(CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
            g.drawPixmap(Assets.fractionPrecision[CalcState.fractionPrecision.ordinal()], 251, 1135);
        }
        g.drawPixmap(Assets.units[CalcState.displayUnits.ordinal()], 434, 1135);

        drawPressedButtons(g);
    }

    public void drawPressedButtons(Graphics g) {

        for(Button b : manager.getPressedButtons().values()) {
            if(b.isPressedDown()) {
                g.drawPixmap(b.getIconPressed(), b.getX(), b.getY());
            }
        }
    }

    public static final int[] yCoordsMultiple = {75,135,195,255};
    public void drawEquation(Graphics g) {

        String equation = CalcState.equation.getString();
        PaintEquation p = CalcState.paint;
        if(p.hasMultipleLines()) {
            for(int i = 0; i < 4; i++) {

                String line = p.getLines()[i];
                g.drawString(line,p.getXCoords()[i],yCoordsMultiple[i],p.getPaint());
            }
        }
        else {
            g.drawString(equation,p.getXCoords()[0],180,p.getPaint());
        }
    }

    /** Checks to see if the event fired is in bounds of a button. If it is, then it lets
     * the manager know which button was pressed or released.
     * @param event The event being fired. */
    private void checkTouchEvent(TouchEvent event) {

        switch(event.type) {
            case TouchEvent.TOUCH_DOWN: {
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
            case TouchEvent.TOUCH_UP:      manager.onLift(event); break;
            case TouchEvent.TOUCH_DRAGGED: manager.onMovement(event); break;
        }

        // If user touches equation screen...
        if (touchIsInBounds(event,0,0,800,300)) {
            if (event.type == TouchEvent.TOUCH_DOWN) {
                calculator.setScreen(new HistoryScreen(calculator));
            }
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton(AndroidTapemeasureCalculator activity) {
        activity.onPause();
        activity.onDestroy();
        activity.finish();
    }
    @Override
    public void androidOptionButton() {
        calculator.setScreen(new SettingScreen(calculator));
    }
}
