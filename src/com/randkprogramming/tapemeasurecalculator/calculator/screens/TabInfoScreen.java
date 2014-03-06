package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class TabInfoScreen extends Screen {

    public TabInfoScreen(Calculator calculator) {
        super(calculator);
    }

    public boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    public void update(float deltaTime) {
    }

    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
    }
    public void pause() {}
    public void resume() {}
    public void dispose() {}
    public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
