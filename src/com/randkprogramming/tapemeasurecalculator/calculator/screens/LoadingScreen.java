package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;

public class LoadingScreen extends Screen {

    public LoadingScreen(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void update(float deltaTime) {

        Assets.loadImages();
        MainCalculatorScreen.setupLayout();

        // Load the main calculator screen
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

    @Override public void present(float deltaTime) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {}

}
