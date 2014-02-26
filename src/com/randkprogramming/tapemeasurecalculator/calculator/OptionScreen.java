package com.randkprogramming.tapemeasurecalculator.calculator;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Input;
import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class OptionScreen extends Screen{

    CalculatorInputManager manager;
    Paint paint;
    Typeface tf;

    public OptionScreen(Calculator calculator, CalculatorInputManager manager) {
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
        g.drawString("OptionScreen", 20, 10, paint);
    }

    // Checks to see if your finger is within an area
    public boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1) return true;
        else return false;
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
