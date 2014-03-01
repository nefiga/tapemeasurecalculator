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
        Graphics g = calculator.getGraphics();

        // Load Images
        Assets.testing = g.newPixmap("testing.png", Graphics.PixmapFormat.ARGB4444);
        Assets.main_calculator = g.newPixmap("main_calc.png", Graphics.PixmapFormat.RGB565);

        Assets.precision[0] = g.newPixmap("p16.png", Graphics.PixmapFormat.RGB565);
        Assets.precision[1] = g.newPixmap("p32.png", Graphics.PixmapFormat.RGB565);
        Assets.precision[2] = g.newPixmap("p64.png", Graphics.PixmapFormat.RGB565);
        Assets.precision[3] = g.newPixmap("decimal.png", Graphics.PixmapFormat.RGB565);

        Assets.displayIn[0] = g.newPixmap("inches.png", Graphics.PixmapFormat.RGB565);
        Assets.displayIn[1] = g.newPixmap("feet.png", Graphics.PixmapFormat.RGB565);

        Assets.history = g.newPixmap("history.png", Graphics.PixmapFormat.RGB565);

        // Load the main calculator screen
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

    @Override public void present(float deltaTime) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {}

}
