package com.randkprogramming.tapemeasurecalculator.calculator;

import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class InfoScreen extends Screen{

    CalculatorInputManager manager;
    Paint paint;
    Typeface tf;

    public InfoScreen(Calculator calculator, CalculatorInputManager manager) {
        super(calculator);
        this.manager = manager;
        tf = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);
        paint = new Paint();
        paint.setTypeface(tf);
        paint.setTextSize(50);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.drawString("InforScreen", 20, 10, paint);
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
