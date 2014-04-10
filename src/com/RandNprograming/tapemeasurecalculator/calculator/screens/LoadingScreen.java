package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import android.app.Activity;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;
import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;

public class LoadingScreen extends Screen {

    public LoadingScreen(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void update(float deltaTime) {

        Assets.loadImages();
        ButtonLayout.setupButtons();
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

    @Override public void present(float deltaTime) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidOptionButton() {}
    @Override public void androidBackButton(AndroidTapemeasureCalculator activity) {}

}
