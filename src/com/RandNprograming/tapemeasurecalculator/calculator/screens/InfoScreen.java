package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class InfoScreen extends Screen {

    public InfoScreen(Calculator calculator) {
        super(calculator);
    }

    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);

            if (i == Input.TouchEvent.TOUCH_DOWN) {
                calculator.setScreen(new MainCalculatorScreen(calculator));
            }
        }
    }

    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.info_screen, 0, 0);
    }
    public void pause() {}
    public void resume() {}
    public void dispose() {}
    @Override public void androidOptionButton() {}
    public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
