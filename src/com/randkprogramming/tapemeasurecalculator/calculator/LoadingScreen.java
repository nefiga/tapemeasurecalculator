package com.randkprogramming.tapemeasurecalculator.calculator;

import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Screen;

public class LoadingScreen extends Screen {

    public LoadingScreen(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = calculator.getGraphics();

        //load Images here

        //example
        //Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);

        Assets.testing = g.newPixmap("testing.png", Graphics.PixmapFormat.ARGB4444);
        Assets.main_calculator = g.newPixmap("main_calculator.png", Graphics.PixmapFormat.RGB565);

        //Loads the main calculator screen
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

    @Override
    public void present(float deltaTime) {

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
    public void androidBackButton() {

    }
}
