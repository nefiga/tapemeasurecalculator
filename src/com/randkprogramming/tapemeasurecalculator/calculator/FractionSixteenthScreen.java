package com.randkprogramming.tapemeasurecalculator.calculator;

import android.graphics.Color;
import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class FractionSixteenthScreen extends Screen{

    CalculatorInputManager manager;

    public FractionSixteenthScreen(Calculator calculator, CalculatorInputManager manager) {
        super(calculator);
        this.manager = manager;
        System.out.println("ChangedScreen!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        // Just adding some comments to see if the commit and push are working!!!!!
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
