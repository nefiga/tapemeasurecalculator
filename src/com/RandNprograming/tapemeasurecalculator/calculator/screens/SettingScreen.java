package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class SettingScreen extends Screen {

    public SettingScreen(Calculator calculator) {
        super(calculator);
    }

    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {

            Input.TouchEvent event = touchEvents.get(i);
            if (event != null) {
                checkBounds(event);
            }
        }
    }

    public void checkBounds(Input.TouchEvent event) {

        if (touchIsInBounds(event, 570, 330, 160, 100)) {
            if (event.type == Input.TouchEvent.TOUCH_DOWN)
                CalcState.orderOfOps = !CalcState.orderOfOps;
        }

        if (touchIsInBounds(event, 570, 330, 300, 100)) {
            if (event.type == Input.TouchEvent.TOUCH_DOWN)
                CalcState.displayTapeImage = !CalcState.displayTapeImage;
        }
    }

    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.settings_screen, 0, 0);

        if (CalcState.orderOfOps)
            g.drawPixmap(Assets.orderOfOperations[0], 20, 305);
        else
            g.drawPixmap(Assets.orderOfOperations[1], 20, 305);

        g.drawPixmap(Assets.toggleTapeMeasureBackground, 20, 410);

        if (CalcState.displayTapeImage)
            g.drawPixmap(Assets.toggleTapeMeasureButton[0], 20, 445);
        else
            g.drawPixmap(Assets.toggleTapeMeasureButton[1], 20, 445);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }

    @Override
    public void androidOptionButton() {
    }

    public void androidBackButton(AndroidTapemeasureCalculator activity) {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
