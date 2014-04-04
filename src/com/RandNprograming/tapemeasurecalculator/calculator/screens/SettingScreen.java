package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

public class SettingScreen extends Screen{

    private Paint paint = new Paint();
    private static final Typeface font = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    public SettingScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(font);
        paint.setTextSize(40);
    }

    public void update(float deltaTime) {}

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
