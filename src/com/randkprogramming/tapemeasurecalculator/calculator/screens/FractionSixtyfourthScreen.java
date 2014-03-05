package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class FractionSixtyfourthScreen extends Screen{

    CalculatorInputManager manager;

    public FractionSixtyfourthScreen(Calculator calculator) {
        super(calculator);
        manager = new CalculatorInputManager();
    }

    @Override public void update(float deltaTime) {}
    @Override public void present(float deltaTime) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
