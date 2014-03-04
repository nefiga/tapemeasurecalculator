package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class FractionThirtysecondScreen extends Screen{

    CalculatorInputManager manager;

    public FractionThirtysecondScreen(Calculator calculator, CalculatorInputManager manager) {
        super(calculator);
        this.manager = manager;
    }

    @Override public void update(float deltaTime) {}
    @Override public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.drawPixmap(Assets.thirtyseconds_screen, 0, 0);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
