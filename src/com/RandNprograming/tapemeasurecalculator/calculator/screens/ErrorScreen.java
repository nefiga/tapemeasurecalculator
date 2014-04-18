package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class ErrorScreen extends Screen {

    public ErrorScreen(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);

            if (i == Input.TouchEvent.TOUCH_DOWN && touchIsInBounds(event,80,800,620,100)) {
                calculator.setScreen(new MainCalculatorScreen(calculator));
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.error_screen, 0, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void androidBackButton(AndroidTapemeasureCalculator activity) {

    }

    @Override
    public void androidOptionButton() {

    }

}
